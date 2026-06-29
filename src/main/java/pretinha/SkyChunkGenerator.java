package pretinha;

import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Random;

public class SkyChunkGenerator {

    public static void generateIsland(Chunk chunk) {

        Random random = new Random(chunk.getPos().hashCode());

        int baseY = 120;

        int islands = 3 + random.nextInt(4);

        for (int i = 0; i < islands; i++) {

            int centerX = random.nextInt(16);
            int centerZ = random.nextInt(16);

            int radius = 3 + random.nextInt(4);

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {

                    if (x * x + z * z > radius * radius) continue;

                    int worldX = chunk.getPos().getStartX() + centerX + x;
                    int worldZ = chunk.getPos().getStartZ() + centerZ + z;

                    int worldY = baseY + random.nextInt(20);

                    BlockPos pos = new BlockPos(worldX, worldY, worldZ);

                    if (random.nextBoolean()) {
                        chunk.setBlockState(pos, Blocks.DIRT.getDefaultState(), false);
                    } else {
                        chunk.setBlockState(pos, Blocks.STONE.getDefaultState(), false);
                    }

                    // camada superior de grass
                    chunk.setBlockState(pos.up(), Blocks.GRASS_BLOCK.getDefaultState(), false);
                }
            }
        }
    }
}