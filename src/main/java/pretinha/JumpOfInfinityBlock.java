package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class JumpOfInfinityBlock extends Block {

    public JumpOfInfinityBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(
            BlockState state,
            World world,
            net.minecraft.util.math.BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit
    ) {

        ItemStack stack = player.getMainHandStack();

        if (stack.isOf(ModItems.PARKOUR_REACTIVER)) {

            if (!world.isClient) {

                JumpManager.add(player.getUuid());

                player.sendMessage(
                        Text.literal(
                                "Altura extra de pulo: "
                                        + JumpManager.get(player.getUuid())
                                        + " bloco(s)"
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