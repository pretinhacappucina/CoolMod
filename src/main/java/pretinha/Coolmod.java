package pretinha;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coolmod implements ModInitializer {

    public static final String MOD_ID = "coolmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModItems.register();
        ModBlocks.register();

        DimensionalReactivatorHandler.register();
        DimensionTeleportHandler.register();

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            server.getPlayerManager().getPlayerList().forEach(player -> {

                int speedLevel = SpeedManager.get(player.getUuid());

                if (speedLevel > 0) {
                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.SPEED,
                                    40,
                                    speedLevel - 1,
                                    true,
                                    false,
                                    false
                            )
                    );
                }
                int strengthLevel = StrengthManager.get(player.getUuid());

                if (strengthLevel > 0) {

                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.STRENGTH,
                                    40,
                                    strengthLevel - 1,
                                    true,
                                    false,
                                    false
                            )
                    );
                }

                int level = JumpManager.get(player.getUuid());

                if (level > 0) {
                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.JUMP_BOOST,
                                    40,
                                    level - 1,
                                    true,
                                    false,
                                    false
                            )
                    );
                }

                ExitDimensionerBlock.tick(player);
            });
        });

        LOGGER.info("Coolmod inicializado com sucesso!");
    }
}