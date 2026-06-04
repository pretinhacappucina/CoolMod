package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class XPTransformerBlock extends Block {

    private static final Map<Item, Integer> XP_VALUES = new HashMap<>();

    static {

        XP_VALUES.put(Items.DIAMOND, 10);

        XP_VALUES.put(Items.EMERALD, 15);

        XP_VALUES.put(Items.NETHERITE_INGOT, 50);

        XP_VALUES.put(Items.ELYTRA, 75);

        XP_VALUES.put(Items.BEACON, 100);

        XP_VALUES.put(Items.DRAGON_EGG, 250);
    }

    public XPTransformerBlock(Settings settings) {
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

        // Não faz nada se estiver sem item
        if (stack.isEmpty()) {
            return ActionResult.FAIL;
        }

        if (!world.isClient) {

            Item item = stack.getItem();

            // Itens especiais usam valor definido
            // Qualquer outro item vale 1 XP
            int xp = XP_VALUES.getOrDefault(item, 1);

            // Remove 1 item
            stack.decrement(1);

            // Cria XP 2 blocos acima
            ExperienceOrbEntity orb =
                    new ExperienceOrbEntity(
                            world,
                            pos.getX() + 0.5,
                            pos.getY() + 2.0,
                            pos.getZ() + 0.5,
                            xp
                    );

            world.spawnEntity(orb);
        }

        return ActionResult.SUCCESS;
    }
}