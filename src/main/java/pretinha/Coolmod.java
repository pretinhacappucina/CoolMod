package pretinha;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
        DeathRewardHandler.register();

        // Entrega os itens iniciais na primeira vez que o jogador entra no mundo
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            if (!player.getCommandTags().contains("coolmod_starter_given")) {
                player.getInventory().offerOrDrop(new ItemStack(ModItems.RANDOM_REACTIVER, 2));
                player.addCommandTag("coolmod_starter_given");

                LOGGER.info("Jogador {} recebeu os itens iniciais do Coolmod.", player.getName().getString());
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            // Chamado todo tick do servidor. Dentro de trySpawn() há checagens
            // de null, de dimensão e de estado persistente, então é seguro
            // chamar isso a cada tick — ele só vai realmente colocar a
            // estrutura uma única vez.
            MotherBarrierSpawner.trySpawn(server.getWorld(ModDimensions.DIMENSIONAL_BARRIERS));

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