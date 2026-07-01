package pretinha;



import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.damage.DamageSource;

import net.minecraft.item.ItemStack;

import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.text.Text;



import java.util.HashSet;

import java.util.Set;

import java.util.UUID;



public class DeathRewardHandler {



    private static final int MAX_REWARDED_DEATHS = 5;



    // Jogadores aguardando respawn

    private static final Set<UUID> PENDING_REWARD = new HashSet<>();



    public static void register() {



        ServerLivingEntityEvents.AFTER_DEATH.register(DeathRewardHandler::onDeath);



        ServerTickEvents.END_SERVER_TICK.register(server -> {



            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {



                UUID uuid = player.getUuid();



                if (!PENDING_REWARD.contains(uuid))

                    continue;



                // Espera o jogador realmente voltar ao mundo

                if (player.isDead())

                    continue;



                PENDING_REWARD.remove(uuid);



                giveReward(player);

            }

        });

    }



    private static void onDeath(LivingEntity entity, DamageSource source) {



        if (!(entity instanceof ServerPlayerEntity player))

            return;



        PENDING_REWARD.add(player.getUuid());

    }



    private static void giveReward(ServerPlayerEntity player) {



        for (int i = 1; i <= MAX_REWARDED_DEATHS; i++) {



            String tag = "coolmod_death_" + i;



            if (player.getCommandTags().contains(tag))

                continue;



            player.addCommandTag(tag);



            player.getInventory().offerOrDrop(

                    new ItemStack(ModItems.RANDOM_REACTIVER)

            );



            player.sendMessage(

                    Text.literal("You received 1 Random Reactiver for dying (" + i + "/" + MAX_REWARDED_DEATHS + ")"),

                    false

            );



            Coolmod.LOGGER.info(

                    "Jogador {} recebeu Random Reactiver pela morte {}/{}.",

                    player.getName().getString(),

                    i,

                    MAX_REWARDED_DEATHS

            );



            break;

        }

    }

}