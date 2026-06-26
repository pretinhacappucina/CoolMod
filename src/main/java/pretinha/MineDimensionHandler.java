package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class MineDimensionHandler {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            ServerWorld mineWorld = server.getWorld(ModDimensions.MINE_DIMENSION);

            // Jogadores
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (player.getWorld().getRegistryKey().equals(ModDimensions.MINE_DIMENSION)) {

                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.HASTE,
                                    40,
                                    9, // Haste X
                                    true,
                                    false,
                                    false
                            )
                    );

                } else {

                    player.removeStatusEffect(StatusEffects.HASTE);

                }
            }

            if (mineWorld == null) {
                return;
            }

            // Todas as entidades vivas
            for (Entity entity : mineWorld.iterateEntities()) {

                if (!(entity instanceof LivingEntity living)) {
                    continue;
                }

                living.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.HASTE,
                                40,
                                9,
                                true,
                                false,
                                false
                        )
                );
            }
        });
    }
}