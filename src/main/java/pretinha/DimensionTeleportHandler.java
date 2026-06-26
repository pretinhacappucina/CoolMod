package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;

import java.util.HashMap;
import java.util.UUID;

public class DimensionTeleportHandler {

    private static final HashMap<UUID, Integer> countdown = new HashMap<>();
    private static final HashMap<UUID, Long> timer = new HashMap<>();

    private static final HashMap<UUID, Boolean> mineTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> exitTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> strongerTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> loveTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> parkourTeleport = new HashMap<>();
    private static final HashMap<UUID, Boolean> speedrunTeleport = new HashMap<>();

    public static void register() {

        UseItemCallback.EVENT.register((player, world, hand) -> {

            if (world.isClient()) return TypedActionResult.pass(player.getStackInHand(hand));
            if (!(player instanceof ServerPlayerEntity sp)) return TypedActionResult.pass(player.getStackInHand(hand));

            ItemStack item = sp.getStackInHand(hand);

            boolean valid =
                    item.getItem() == ModItems.OVERWORLD_REACTIVER ||
                            item.getItem() == ModItems.MINER_REACTIVER ||
                            item.getItem() == ModItems.EXIT_REACTIVER ||
                            item.getItem() == ModItems.STRONGER_REACTIVER ||
                            item.getItem() == ModItems.REGENERATION_REACTIVER ||
                            item.getItem() == ModItems.PARKOUR_REACTIVER ||
                            item.getItem() == ModItems.SPEEDRUN_REACTIVER;

            if (!valid) return TypedActionResult.pass(item);

            UUID id = sp.getUuid();

            WarpState.start(id);

            countdown.put(id, 3);
            timer.put(id, System.currentTimeMillis());

            mineTeleport.put(id, item.getItem() == ModItems.MINER_REACTIVER);
            exitTeleport.put(id, item.getItem() == ModItems.EXIT_REACTIVER);
            strongerTeleport.put(id, item.getItem() == ModItems.STRONGER_REACTIVER);
            loveTeleport.put(id, item.getItem() == ModItems.REGENERATION_REACTIVER);
            parkourTeleport.put(id, item.getItem() == ModItems.PARKOUR_REACTIVER);
            speedrunTeleport.put(id, item.getItem() == ModItems.SPEEDRUN_REACTIVER);

            // 🔥 ISSO AQUI FAZ O ITEM SER CONSUMIDO DE VERDADE
            item.decrement(1);

            return TypedActionResult.success(item);
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

                boolean mine = mineTeleport.getOrDefault(id, false);
                boolean exit = exitTeleport.getOrDefault(id, false);
                boolean stronger = strongerTeleport.getOrDefault(id, false);
                boolean love = loveTeleport.getOrDefault(id, false);
                boolean parkour = parkourTeleport.getOrDefault(id, false);
                boolean speedrun = speedrunTeleport.getOrDefault(id, false);

                ServerWorld target;

                if (speedrun) {
                    target = server.getWorld(ModDimensions.SPEEDRUN_DIMENSION);

                } else if (parkour) {
                    target = server.getWorld(ModDimensions.PARKOUR_DIMENSION);

                } else if (mine) {
                    target = server.getWorld(ModDimensions.MINE_DIMENSION);

                } else if (exit) {
                    target = server.getWorld(ModDimensions.EXIT_DIMENSION);

                } else if (stronger) {
                    target = server.getWorld(ModDimensions.STRONGER_DIMENSION);

                } else if (love) {
                    target = server.getWorld(ModDimensions.LOVE_DIMENSION);

                } else {
                    target = server.getOverworld();
                }

                if (target == null) {
                    cleanup(id);
                    WarpState.stop(id);
                    continue;
                }

                player.teleport(
                        target,
                        0.5,
                        90,
                        0.5,
                        player.getYaw(),
                        player.getPitch()
                );

                cleanup(id);
                WarpState.stop(id);

                player.sendMessage(Text.literal("Teleported!"), true);
            }
        });
    }

    private static void cleanup(UUID id) {

        countdown.remove(id);
        timer.remove(id);

        mineTeleport.remove(id);
        exitTeleport.remove(id);
        strongerTeleport.remove(id);
        loveTeleport.remove(id);
        parkourTeleport.remove(id);
        speedrunTeleport.remove(id);
    }
}