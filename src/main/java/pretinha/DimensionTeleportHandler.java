package pretinha;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.block.Blocks;

import java.util.HashMap;
import java.util.UUID;

public class DimensionTeleportHandler {

    private static final HashMap<UUID, Integer> countdown = new HashMap<>();
    private static final HashMap<UUID, Long> timer = new HashMap<>();

    public static void register() {

        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {

            if (world.isClient()) return ActionResult.PASS;
            if (!(player instanceof ServerPlayerEntity sp)) return ActionResult.PASS;

            if (!world.getBlockState(hit.getBlockPos())
                    .isOf(ModBlocks.DIMENSIONS_TELEPORTER)) {
                return ActionResult.PASS;
            }

            ItemStack item = sp.getStackInHand(hand);

            if (!item.isOf(ModItems.DIMENSIONAL_REACTIVER)) {
                return ActionResult.PASS;
            }

            if (!sp.isCreative()) {
                item.decrement(1);
            }

            UUID id = sp.getUuid();

            WarpState.start(id);

            countdown.put(id, 3);
            timer.put(id, System.currentTimeMillis());

            return ActionResult.SUCCESS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                UUID id = player.getUuid();

                if (!countdown.containsKey(id)) continue;

                long now = System.currentTimeMillis();

                if (now - timer.get(id) < 1000) continue;

                timer.put(id, now);

                int time = countdown.get(id);

                if (time > 0) {
                    player.sendMessage(Text.literal("Teleporting in " + time), true);
                    countdown.put(id, time - 1);
                    continue;
                }

                ServerWorld target = server.getWorld(ModDimensions.DIMENSIONS);

                if (target == null) {
                    WarpState.stop(id);
                    return;
                }

                int run = DimensionRunManager.get(id);

                int x = run * 150000;
                int z = run * 150000;

                BlockPos safe = findSafeSpawn(target, x, z);

                player.teleport(
                        target,
                        safe.getX() + 0.5,
                        safe.getY(),
                        safe.getZ() + 0.5,
                        player.getYaw(),
                        player.getPitch()
                );

                // ✔ CHAMADA CORRETA (cluster system)
                StructureSpawner.spawnCluster(target, safe);

                BlockPos last = LeavefoltManager.LEAVEFOLTS.get(
                        LeavefoltManager.LEAVEFOLTS.size() - 1
                );

                player.sendMessage(
                        net.minecraft.text.Text.literal(
                                "Leavefolt: "
                                        + last.getX()
                                        + " "
                                        + last.getY()
                                        + " "
                                        + last.getZ()
                        ),
                        false
                );

                countdown.remove(id);
                timer.remove(id);

                WarpState.stop(id);

                player.sendMessage(Text.literal("Run " + run + " started!"), true);
            }
        });
    }

    private static BlockPos findSafeSpawn(ServerWorld world, int x, int z) {

        int minY = Math.max(world.getBottomY(), 0);

        for (int radius = 0; radius <= 256; radius += 16) {

            for (int dx = -radius; dx <= radius; dx += 16) {
                for (int dz = -radius; dz <= radius; dz += 16) {

                    int checkX = x + dx;
                    int checkZ = z + dz;

                    BlockPos surface = world.getTopPosition(
                            Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                            new BlockPos(checkX, 0, checkZ)
                    );

                    int y = surface.getY();

                    for (int i = y; i >= minY; i--) {

                        BlockPos pos = new BlockPos(checkX, i, checkZ);

                        BlockPos above1 = pos.up();
                        BlockPos above2 = pos.up(2);

                        var block = world.getBlockState(pos).getBlock();

                        boolean validFloor =
                                block == Blocks.NETHERRACK
                                        || block == Blocks.COBBLESTONE
                                        || block == Blocks.SCULK
                                        || block == Blocks.SCULK_CATALYST
                                        || block == Blocks.SCULK_SENSOR
                                        || block == Blocks.SCULK_SHRIEKER
                                        || block == Blocks.SCULK_VEIN;

                        if (!validFloor) continue;

                        if (world.getBlockState(above1).isAir()
                                && world.getBlockState(above2).isAir()) {
                            return above1;
                        }
                    }
                }
            }
        }

        return new BlockPos(x, 90, z);
    }
}