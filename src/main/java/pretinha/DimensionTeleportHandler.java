package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.UUID;

public class DimensionTeleportHandler {

    private static final HashMap<UUID, Integer> countdown = new HashMap<>();
    private static final HashMap<UUID, Long> timer = new HashMap<>();
    private static final HashMap<UUID, Boolean> mineTeleport = new HashMap<>();

    public static void register() {

        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {

            if (world.isClient()) return ActionResult.PASS;
            if (!(player instanceof ServerPlayerEntity sp)) return ActionResult.PASS;

            if (!world.getBlockState(hit.getBlockPos())
                    .isOf(ModBlocks.DIMENSIONS_TELEPORTER)) {
                return ActionResult.PASS;
            }

            ItemStack item = sp.getStackInHand(hand);

            boolean overworld =
                    item.isOf(ModItems.OVERWORLD_REACTIVER);

            boolean mine =
                    item.isOf(ModItems.MINER_REACTIVER);

            if (!overworld && !mine) {
                return ActionResult.PASS;
            }

            if (!sp.isCreative()) {
                item.decrement(1);
            }

            UUID id = sp.getUuid();

            WarpState.start(id);

            countdown.put(id, 3);
            timer.put(id, System.currentTimeMillis());
            mineTeleport.put(id, mine);

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

                    player.sendMessage(
                            Text.literal("Teleporting in " + time),
                            true
                    );

                    countdown.put(id, time - 1);
                    continue;
                }

                boolean mine =
                        mineTeleport.getOrDefault(id, false);

                ServerWorld target;

                if (mine) {
                    target = server.getWorld(ModDimensions.MINE_DIMENSION);
                } else {
                    target = server.getOverworld();
                }

                if (target == null) {
                    countdown.remove(id);
                    timer.remove(id);
                    mineTeleport.remove(id);
                    WarpState.stop(id);
                    continue;
                }

                BlockPos safe = new BlockPos(0, 90, 0);

                createSafeArea(target, safe);

                player.teleport(
                        target,
                        safe.getX() + 0.5,
                        safe.getY(),
                        safe.getZ() + 0.5,
                        player.getYaw(),
                        player.getPitch()
                );

                countdown.remove(id);
                timer.remove(id);
                mineTeleport.remove(id);

                WarpState.stop(id);

                player.sendMessage(
                        Text.literal(
                                mine
                                        ? "Teleported to Mine Dimension"
                                        : "Teleported to Overworld"
                        ),
                        true
                );
            }
        });
    }

    private static void createSafeArea(
            ServerWorld world,
            BlockPos center
    ) {

        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();

        world.setBlockState(
                new BlockPos(x, y - 1, z),
                Blocks.COBBLESTONE.getDefaultState()
        );

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = 0; dy <= 2; dy++) {

                    world.setBlockState(
                            new BlockPos(x + dx, y + dy, z + dz),
                            Blocks.AIR.getDefaultState()
                    );
                }
            }
        }
    }
}