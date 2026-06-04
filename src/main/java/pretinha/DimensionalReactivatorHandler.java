package pretinha;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class DimensionalReactivatorHandler {

    public static void register() {

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {

            ItemStack stack = player.getStackInHand(hand);

            if (!stack.isOf(ModItems.DIMENSIONAL_REACTIVER)) {
                return ActionResult.PASS;
            }

            if (world.isClient()) {
                return ActionResult.SUCCESS;
            }

            if (!(entity instanceof MobEntity mob)) {
                return ActionResult.PASS;
            }

            ServerWorld targetWorld =
                    player.getServer().getWorld(ModDimensions.DIMENSIONS);

            if (targetWorld == null) {
                return ActionResult.FAIL;
            }

            int x = mob.getBlockX();
            int z = mob.getBlockZ();

            BlockPos safePos =
                    SpawnManager.findSpawn(targetWorld, x, z);

            teleportMob(mob, targetWorld, safePos);

            // Consome 1 item no modo Survival
            if (!player.isCreative()) {
                stack.decrement(1);
            }

            return ActionResult.SUCCESS;
        });
    }

    private static void teleportMob(
            MobEntity mob,
            ServerWorld targetWorld,
            BlockPos pos
    ) {

        Entity newEntity = mob.getType().create(targetWorld);

        if (newEntity == null) {
            return;
        }

        newEntity.copyFrom(mob);

        newEntity.refreshPositionAndAngles(
                pos.getX() + 0.5,
                pos.getY(),
                pos.getZ() + 0.5,
                mob.getYaw(),
                mob.getPitch()
        );

        targetWorld.spawnEntity(newEntity);

        mob.discard();
    }
}