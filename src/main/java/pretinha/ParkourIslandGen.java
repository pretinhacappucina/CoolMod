package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ParkourIslandGen {

    private static final Set<UUID> GENERATED = new HashSet<>();

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            ServerWorld world = server.getWorld(ModDimensions.PARKOUR_DIMENSION);
            if (world == null) return;

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (!player.getWorld().getRegistryKey().equals(ModDimensions.PARKOUR_DIMENSION))
                    continue;

                UUID id = player.getUuid();

                if (GENERATED.contains(id)) continue;

                GENERATED.add(id);

                generateIslands(world, player.getBlockPos());
            }
        });
    }

    private static void generateIslands(ServerWorld world, BlockPos center) {

        int baseY = 80;

        int radius = 60;

        for (int x = -radius; x <= radius; x += 6) {
            for (int z = -radius; z <= radius; z += 6) {

                if (Math.random() > 0.35) continue;

                int heightOffset = (int)(Math.random() * 6);

                BlockPos pos = new BlockPos(
                        center.getX() + x,
                        baseY + heightOffset,
                        center.getZ() + z
                );

                // ilha pequena
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {

                        world.setBlockState(
                                pos.add(dx, 0, dz),
                                Blocks.GRASS_BLOCK.getDefaultState()
                        );
                    }
                }
            }
        }
    }
}