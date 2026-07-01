package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class DimensionTeleportHandler {

    private static final HashMap<UUID, Integer> countdown = new HashMap<>();
    private static final HashMap<UUID, Long> timer = new HashMap<>();

    private static final HashMap<UUID, Boolean> randomTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> mountainsTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> sculkTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> lushTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> waterTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> lavaTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> tntTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> desertTeleport = new HashMap<>();

    // Novos reactivers
    private static final HashMap<UUID, Boolean> regenerationTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> minerTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> strongerTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> parkourTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> speedrunTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> overworldTeleport = new HashMap<>();

    public static void register() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            if (world.isClient())
                return ActionResult.PASS;

            if (!(player instanceof ServerPlayerEntity sp))
                return ActionResult.PASS;

            // só ativa se o bloco clicado for o Dimensions Teleporter
            if (world.getBlockState(hitResult.getBlockPos()).getBlock() != ModBlocks.DIMENSIONS_TELEPORTER)
                return ActionResult.PASS;

            ItemStack item = sp.getStackInHand(hand);

            boolean valid =
                    item.getItem() == ModItems.RANDOM_REACTIVER ||
                            item.getItem() == ModItems.MOUNTAINS_REACTIVER ||
                            item.getItem() == ModItems.SCULK_REACTIVER ||
                            item.getItem() == ModItems.LUSH_REACTIVATOR ||
                            item.getItem() == ModItems.WATER_REACTIVER ||
                            item.getItem() == ModItems.LAVA_REACTIVER ||
                            item.getItem() == ModItems.DESERT_REACTIVER ||
                            item.getItem() == ModItems.TNT_REACTIVER ||
                            item.getItem() == ModItems.REGENERATION_REACTIVER ||
                            item.getItem() == ModItems.MINER_REACTIVER ||
                            item.getItem() == ModItems.STRONGER_REACTIVER ||
                            item.getItem() == ModItems.PARKOUR_REACTIVER ||
                            item.getItem() == ModItems.SPEEDRUN_REACTIVER ||
                            item.getItem() == ModItems.OVERWORLD_REACTIVER;


            if (!valid)
                return ActionResult.PASS;

            UUID id = sp.getUuid();

            WarpState.start(id);

            countdown.put(id, 3);
            timer.put(id, System.currentTimeMillis());

            randomTeleport.put(id, item.getItem() == ModItems.RANDOM_REACTIVER);
            mountainsTeleport.put(id, item.getItem() == ModItems.MOUNTAINS_REACTIVER);
            sculkTeleport.put(id, item.getItem() == ModItems.SCULK_REACTIVER);
            lushTeleport.put(id, item.getItem() == ModItems.LUSH_REACTIVATOR);
            waterTeleport.put(id, item.getItem() == ModItems.WATER_REACTIVER);
            lavaTeleport.put(id, item.getItem() == ModItems.LAVA_REACTIVER);
            tntTeleport.put(id, item.getItem() == ModItems.TNT_REACTIVER);
            desertTeleport.put(id, item.getItem() == ModItems.DESERT_REACTIVER);

            regenerationTeleport.put(id, item.getItem() == ModItems.REGENERATION_REACTIVER);
            minerTeleport.put(id, item.getItem() == ModItems.MINER_REACTIVER);
            strongerTeleport.put(id, item.getItem() == ModItems.STRONGER_REACTIVER);
            parkourTeleport.put(id, item.getItem() == ModItems.PARKOUR_REACTIVER);
            speedrunTeleport.put(id, item.getItem() == ModItems.SPEEDRUN_REACTIVER);
            overworldTeleport.put(id, item.getItem() == ModItems.OVERWORLD_REACTIVER);

            item.decrement(1);

            sp.getWorld().playSound(
                    null,
                    sp.getBlockPos(),
                    SoundEvents.BLOCK_END_PORTAL_SPAWN,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            return ActionResult.SUCCESS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                UUID id = player.getUuid();

                if (!countdown.containsKey(id))
                    continue;

                long now = System.currentTimeMillis();

                if (now - timer.get(id) < 1000)
                    continue;

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

                boolean random = randomTeleport.getOrDefault(id, false);
                boolean mountains = mountainsTeleport.getOrDefault(id, false);
                boolean sculk = sculkTeleport.getOrDefault(id, false);
                boolean lush = lushTeleport.getOrDefault(id, false);
                boolean water = waterTeleport.getOrDefault(id, false);
                boolean lava = lavaTeleport.getOrDefault(id, false);
                boolean tnt = tntTeleport.getOrDefault(id, false);
                boolean desert = desertTeleport.getOrDefault(id, false);

                boolean regeneration = regenerationTeleport.getOrDefault(id, false);
                boolean miner = minerTeleport.getOrDefault(id, false);
                boolean stronger = strongerTeleport.getOrDefault(id, false);
                boolean parkour = parkourTeleport.getOrDefault(id, false);
                boolean speedrun = speedrunTeleport.getOrDefault(id, false);
                boolean overworld = overworldTeleport.getOrDefault(id, false);

                ServerWorld target;

                if (tnt) {

                    target = server.getWorld(ModDimensions.TNT_DIMENSION);

                } else if (lava) {

                    target = server.getWorld(ModDimensions.LAVA_DIMENSION);

                } else if (desert) {

                    target = server.getWorld(ModDimensions.DESERT_DIMENSION);

                } else if (water) {

                    target = server.getWorld(ModDimensions.WATER_DIMENSION);

                } else if (lush) {

                    target = server.getWorld(ModDimensions.LUSH_DIMENSION);

                } else if (sculk) {

                    target = server.getWorld(ModDimensions.SCULK_DIMENSION);

                } else if (mountains) {

                    target = server.getWorld(ModDimensions.SKY_DIMENSION);

                } else if (regeneration) {

                    target = server.getWorld(ModDimensions.LOVE_DIMENSION);

                } else if (miner) {

                    target = server.getWorld(ModDimensions.MINE_DIMENSION);

                } else if (stronger) {

                    target = server.getWorld(ModDimensions.STRONGER_DIMENSION);

                } else if (parkour) {

                    target = server.getWorld(ModDimensions.PARKOUR_DIMENSION);

                } else if (speedrun) {

                    target = server.getWorld(ModDimensions.SPEEDRUN_DIMENSION);

                } else if (overworld) {

                    target = server.getOverworld();

                } else if (random) {

                    List<ServerWorld> worlds = new ArrayList<>();
                    server.getWorlds().forEach(worlds::add);

                    target = worlds.get(new Random().nextInt(worlds.size()));

                } else {

                    target = server.getOverworld();
                }

                if (target == null) {
                    cleanup(id);
                    WarpState.stop(id);
                    continue;
                }

                BlockPos destination = new BlockPos(25, 115, 16);

                // Limpa uma área 3x3 de ar, com 3 blocos de altura, no destino,
                // pra garantir que o jogador não sufoque nem tenha problema
                // ao chegar (mesmo se o chunk já tiver blocos gerados ali).
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        for (int y = 0; y <= 2; y++) {
                            target.setBlockState(
                                    destination.add(x, y, z),
                                    Blocks.AIR.getDefaultState(),
                                    3
                            );
                        }
                    }
                }

                player.teleport(
                        target,
                        destination.getX(),
                        destination.getY(),
                        destination.getZ(),
                        player.getYaw(),
                        player.getPitch()
                );
                BlockPos center = player.getBlockPos();


                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        target.setBlockState(
                                center.add(x, -1, z),
                                Blocks.STONE.getDefaultState(),
                                3
                        );
                    }
                }


                target.setBlockState(
                        center.down(),
                        Blocks.WATER.getDefaultState(),
                        3
                );
                cleanup(id);
                WarpState.stop(id);

                player.sendMessage(
                        Text.literal("Teleported!"),
                        true
                );
            }
        });
    }

    private static void cleanup(UUID id) {

        countdown.remove(id);
        timer.remove(id);

        randomTeleport.remove(id);
        mountainsTeleport.remove(id);
        sculkTeleport.remove(id);
        lushTeleport.remove(id);
        waterTeleport.remove(id);
        lavaTeleport.remove(id);
        tntTeleport.remove(id);
        desertTeleport.remove(id);

        regenerationTeleport.remove(id);
        minerTeleport.remove(id);
        strongerTeleport.remove(id);
        parkourTeleport.remove(id);
        speedrunTeleport.remove(id);
        overworldTeleport.remove(id);
    }
}