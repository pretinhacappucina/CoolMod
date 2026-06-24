package pretinha;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class MobDropHandler {

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {

            if (!(entity.getWorld() instanceof ServerWorld world)) {
                return;
            }

            if (!(damageSource.getAttacker() instanceof PlayerEntity)) {
                return;
            }

            // 0,1% = 1 em 1000
            if (world.random.nextInt(1000) == 0) {

                ItemEntity drop = new ItemEntity(
                        world,
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        new ItemStack(ModItems.FRAGMENTS_OF_EXIT)
                );

                world.spawnEntity(drop);
            }
        });
    }
}