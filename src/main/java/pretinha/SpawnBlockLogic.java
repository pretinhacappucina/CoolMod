package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class SpawnBlockLogic {

    private static final Set<String> used = new HashSet<>();

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                World world = player.getWorld();

                if (world.getBlockState(player.getBlockPos().down())
                        .isOf(ModBlocks.SPAWN_BEDROCK)) {

                    String id = player.getUuidAsString();

                    if (used.contains(id)) continue;

                    used.add(id);

                    world.setBlockState(
                            player.getBlockPos().down(),
                            Blocks.AIR.getDefaultState()
                    );
                }
            }
        });
    }
}