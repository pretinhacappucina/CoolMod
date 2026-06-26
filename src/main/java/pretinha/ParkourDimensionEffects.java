package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class ParkourDimensionEffects {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (player.getWorld().getRegistryKey().equals(ModDimensions.PARKOUR_DIMENSION)) {

                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.JUMP_BOOST,
                                    40,
                                    9, // nível 10 (0 = I ... 9 = X)
                                    true,
                                    false,
                                    false
                            )
                    );

                } else {

                    player.removeStatusEffect(StatusEffects.JUMP_BOOST);
                }
            }

        });
    }
}