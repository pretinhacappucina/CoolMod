package pretinha;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Random;

public class SkyChunkGenerator {

    public static void generateIsland(WorldChunk chunk) {

        ServerWorld world = (ServerWorld) chunk.getWorld();

        Random random = new Random(chunk.getPos().hashCode());

        int baseY = 120;

        int islands = 2 + random.nextInt(4);

        for (int i = 0; i < islands; i++) {

            int centerX = chunk.getPos().getStartX() + random.nextInt(16);
            int centerZ = chunk.getPos().getStartZ() + random.nextInt(16);

            int radius = 3 + random.nextInt(4);

            int height = baseY + random.nextInt(20);

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {

                    if (x * x + z * z > radius * radius) continue;

                    for (int y = 0; y < 3; y++) {

                        BlockPos pos = new BlockPos(
                                centerX + x,
                                height - y,
                                centerZ + z
                        );

                        if (y == 0) {
                            world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
                        } else {
                            world.setBlockState(pos, Blocks.STONE.getDefaultState(), 3);
                        }
                    }

                    world.setBlockState(
                            new BlockPos(centerX + x, height + 1, centerZ + z),
                            Blocks.GRASS_BLOCK.getDefaultState(),
                            3
                    );
                }
            }
        }
    }
}