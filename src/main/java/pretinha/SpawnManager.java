package pretinha;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import pretinha.util.SpawnUtil;

public class SpawnManager {

    public static void register() {

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

            ServerPlayerEntity player = handler.getPlayer();

            server.execute(() -> {
                SpawnUtil.teleport(player);
            });

        });
    }
}