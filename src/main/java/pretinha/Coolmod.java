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
        DimensionTeleportHandler.register();
        FragmentsRewardSystem.register();
        ModLootTables.register();
        MobDropHandler.register();
        OreDropHandler.register();
        LoveDimensionHandler.register();
        StrongerMobHandler.register();
        ExitDimensionEffects.register();
        MineDimensionHandler.register();
        ParkourIslandGen.register();
        ParkourDimensionEffects.register();
        SpeedrunDimensionEffects.register();

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            server.getPlayerManager().getPlayerList().forEach(player -> {

                if (FallDamageManager.has(player.getUuid())) {
                    player.fallDistance = 0;
                }

                int regenLevel = RegenerationManager.get(player.getUuid());
                if (regenLevel > 0) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            40,
                            regenLevel - 1,
                            true,
                            false,
                            false
                    ));
                }

                int speedLevel = SpeedManager.get(player.getUuid());
                if (speedLevel > 0) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.SPEED,
                            40,
                            speedLevel - 1,
                            true,
                            false,
                            false
                    ));
                }

                int strengthLevel = StrengthManager.get(player.getUuid());
                if (strengthLevel > 0) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.STRENGTH,
                            40,
                            strengthLevel - 1,
                            true,
                            false,
                            false
                    ));
                }

                int miningLevel = MiningManager.get(player.getUuid());
                if (miningLevel > 0) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.HASTE,
                            40,
                            miningLevel - 1,
                            true,
                            false,
                            false
                    ));
                }

                int jumpLevel = JumpManager.get(player.getUuid());
                if (jumpLevel > 0) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.JUMP_BOOST,
                            40,
                            jumpLevel - 1,
                            true,
                            false,
                            false
                    ));
                }

                ExitDimensionerBlock.tick(player);
                ExitHammerItem.tick(player);

            });


        });

        LOGGER.info("Coolmod inicializado com sucesso!");
    }
}