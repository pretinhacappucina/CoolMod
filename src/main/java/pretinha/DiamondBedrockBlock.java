package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DiamondBedrockBlock extends Block {

    public DiamondBedrockBlock(Settings settings) {
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

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.isOf(Items.LAPIS_LAZULI)) {

            if (!world.isClient) {

                world.setBlockState(
                        pos,
                        ModBlocks.BLACK_BEDROCK.getDefaultState()
                );

                if (!player.isCreative()) {
                    stack.decrement(1);
                }
            }

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}