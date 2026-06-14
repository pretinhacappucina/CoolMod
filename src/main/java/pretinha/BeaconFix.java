package pretinha;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class BeaconFix {

    public static void fixBeaconsAround(ServerWorld world, BlockPos center, int radius) {

        int minX = center.getX() - radius;
        int maxX = center.getX() + radius;
        int minZ = center.getZ() - radius;
        int maxZ = center.getZ() + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {

                for (int y = world.getBottomY(); y < world.getTopY(); y++) {

                    BlockPos pos = new BlockPos(x, y, z);

                    // se achou beacon
                    if (world.getBlockState(pos).isOf(Blocks.BEACON)) {

                        openSkyAboveBeacon(world, pos);
                    }
                }
            }
        }
    }

    public static void openSkyAboveBeacon(ServerWorld world, BlockPos beaconPos) {

        for (int y = beaconPos.getY() + 1; y < world.getTopY(); y++) {

            BlockPos pos = new BlockPos(
                    beaconPos.getX(),
                    y,
                    beaconPos.getZ()
            );

            // 🔥 NÃO substitui beacon nem base dele
            if (world.getBlockState(pos).isOf(Blocks.BEACON)) {
                continue;
            }

            // só troca blocos sólidos acima
            if (!world.isAir(pos)) {
                world.setBlockState(
                        pos,
                        Blocks.GLASS.getDefaultState()
                );
            }
        }
    }
}