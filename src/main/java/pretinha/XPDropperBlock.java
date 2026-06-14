package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class XPDropperBlock extends Block {

    private static final Random RANDOM = new Random();

    public XPDropperBlock(Settings settings) {
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

        if (!world.isClient) {

            ServerWorld serverWorld = (ServerWorld) world;

            // Entre ~10 e ~25 níveis (nível 0)
            int totalXP = 160 + RANDOM.nextInt(555);

            while (totalXP > 0) {

                int orbXP = Math.min(totalXP, 2477);

                ExperienceOrbEntity orb = new ExperienceOrbEntity(
                        serverWorld,
                        pos.getX() + 0.5,
                        pos.getY() + 1.0,
                        pos.getZ() + 0.5,
                        orbXP
                );

                serverWorld.spawnEntity(orb);

                totalXP -= orbXP;
            }

            world.breakBlock(pos, false);
        }

        return ActionResult.SUCCESS;
    }
}