package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DimensionalFirefliesBlock extends Block {

    public DimensionalFirefliesBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit
    ) {

        ItemStack stack = player.getMainHandStack();

        if (!stack.isOf(ModItems.DIMENSIONAL_REACTIVER)) {
            return ActionResult.PASS;
        }

        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (!player.isCreative()) {
            stack.decrement(1);
        }

        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(pos);

        while (!queue.isEmpty()) {

            BlockPos current = queue.poll();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);

            if (visited.size() > 10000) {
                break;
            }

            if (world.getBlockState(current).isOf(ModBlocks.DIMENSIONAL_FIREFLIES)) {

                world.setBlockState(
                        current,
                        Blocks.AIR.getDefaultState()
                );

                queue.add(current.north());
                queue.add(current.south());
                queue.add(current.east());
                queue.add(current.west());
                queue.add(current.up());
                queue.add(current.down());
            }
        }

        return ActionResult.SUCCESS;
    }
}