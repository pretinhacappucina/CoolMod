package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StrongerMobHandler {

    private static final Set<UUID> EQUIPPED = new HashSet<>();

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            ServerWorld strongerWorld = server.getWorld(ModDimensions.STRONGER_DIMENSION);

            // Jogadores
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (player.getWorld().getRegistryKey().equals(ModDimensions.STRONGER_DIMENSION)) {

                    player.addStatusEffect(
                            new StatusEffectInstance(
                                    StatusEffects.STRENGTH,
                                    40,
                                    9,
                                    true,
                                    false,
                                    false
                            )
                    );

                } else {

                    player.removeStatusEffect(StatusEffects.STRENGTH);

                }
            }

            if (strongerWorld == null) {
                return;
            }

            // Todas as entidades da dimensão
            for (Entity entity : strongerWorld.iterateEntities()) {

                if (!(entity instanceof LivingEntity living)) {
                    continue;
                }

                // Força X
                living.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.STRENGTH,
                                40,
                                9,
                                true,
                                false,
                                false
                        )
                );

                // Equipa apenas uma vez
                if (!(living instanceof MobEntity mob)) {
                    continue;
                }

                if (EQUIPPED.contains(mob.getUuid())) {
                    continue;
                }

                EQUIPPED.add(mob.getUuid());

                equip(mob);
            }
        });
    }

    private static void equip(MobEntity mob) {

        tryEquip(mob, EquipmentSlot.HEAD,
                new ItemStack(Items.NETHERITE_HELMET));

        tryEquip(mob, EquipmentSlot.CHEST,
                new ItemStack(Items.NETHERITE_CHESTPLATE));

        tryEquip(mob, EquipmentSlot.LEGS,
                new ItemStack(Items.NETHERITE_LEGGINGS));

        tryEquip(mob, EquipmentSlot.FEET,
                new ItemStack(Items.NETHERITE_BOOTS));

        tryEquip(mob, EquipmentSlot.MAINHAND,
                new ItemStack(Items.NETHERITE_SWORD));
    }

    private static void tryEquip(
            MobEntity mob,
            EquipmentSlot slot,
            ItemStack stack
    ) {

        if (!mob.canEquip(stack)) {
            return;
        }

        mob.equipStack(slot, stack);
        mob.setEquipmentDropChance(slot, 0.0F);
    }
}