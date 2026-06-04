package pretinha;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class SpawnManager {

    public static BlockPos findSpawn(ServerWorld world, int centerX, int centerZ) {

        // posição fixa obrigatória
        int x = 0;
        int z = 0;
        int y = 90;

        BlockPos pos = new BlockPos(x, y, z);

        // evita cair dentro de bloco sólido ou bedrock
        if (world.getBlockState(pos).isSolidBlock(world, pos)
                || world.getBlockState(pos).isOf(Blocks.BEDROCK)) {

            // sobe até achar ar livre (sem “garantir segurança”, só evitar bug)
            for (int i = 90; i < world.getTopY(); i++) {

                BlockPos test = new BlockPos(x, i, z);

                if (!world.getBlockState(test).isSolidBlock(world, test)
                        && !world.getBlockState(test).isOf(Blocks.BEDROCK)) {
                    return test;
                }
            }
        }

        // padrão absoluto
        return pos;
    }
}