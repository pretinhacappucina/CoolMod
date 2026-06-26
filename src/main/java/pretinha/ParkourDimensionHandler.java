package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class ParkourDimensionHandler {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (player.getWorld().getRegistryKey().equals(ModDimensions.PARKOUR_DIMENSION)) {

                    // Jump Boost III (nível 3)
                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.JUMP_BOOST,
                                    60,   // duração maior pra evitar flicker
                                    2,    // 0 = I, 1 = II, 2 = III
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