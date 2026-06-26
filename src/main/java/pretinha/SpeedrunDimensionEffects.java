package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class SpeedrunDimensionEffects {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (player.getWorld().getRegistryKey().equals(ModDimensions.SPEEDRUN_DIMENSION)) {

                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.SPEED,
                                    40,
                                    9, // Speed X
                                    true,
                                    false,
                                    false
                            )
                    );

                } else {

                    player.removeStatusEffect(StatusEffects.SPEED);
                }
            }
        });
    }
}