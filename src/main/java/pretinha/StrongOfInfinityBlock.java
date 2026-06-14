package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StrongOfInfinityBlock extends Block {

    public StrongOfInfinityBlock(Settings settings) {
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

        if (stack.isOf(ModItems.STRONGER_REACTIVER)) {

            if (!world.isClient) {

                StrengthManager.add(player.getUuid());

                player.sendMessage(
                        Text.literal(
                                "Força extra: "
                                        + StrengthManager.get(player.getUuid())
                        ),
                        true
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