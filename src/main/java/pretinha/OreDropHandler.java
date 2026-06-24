package pretinha;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class OreDropHandler {

    public static void register() {

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {

            if (!(world instanceof ServerWorld serverWorld)) return;

            if (player == null) return;

            if (!isOre(state)) return;

            // 25% chance
            if (serverWorld.random.nextFloat() > 0.25f) return;

            int amount = 1 + serverWorld.random.nextInt(5);

            ItemEntity drop = new ItemEntity(
                    serverWorld,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    new ItemStack(ModItems.FRAGMENTS_OF_EXIT, amount)
            );

            serverWorld.spawnEntity(drop);
        });
    }

    private static boolean isOre(BlockState state) {

        return state.isOf(Blocks.COAL_ORE)
                || state.isOf(Blocks.DEEPSLATE_COAL_ORE)
                || state.isOf(Blocks.IRON_ORE)
                || state.isOf(Blocks.DEEPSLATE_IRON_ORE)
                || state.isOf(Blocks.GOLD_ORE)
                || state.isOf(Blocks.DEEPSLATE_GOLD_ORE)
                || state.isOf(Blocks.DIAMOND_ORE)
                || state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE)
                || state.isOf(Blocks.EMERALD_ORE)
                || state.isOf(Blocks.DEEPSLATE_EMERALD_ORE)
                || state.isOf(Blocks.LAPIS_ORE)
                || state.isOf(Blocks.DEEPSLATE_LAPIS_ORE)
                || state.isOf(Blocks.REDSTONE_ORE)
                || state.isOf(Blocks.DEEPSLATE_REDSTONE_ORE)
                || state.isOf(Blocks.NETHER_QUARTZ_ORE)
                || state.isOf(Blocks.NETHER_GOLD_ORE)
                || state.isOf(Blocks.ANCIENT_DEBRIS);
    }
}