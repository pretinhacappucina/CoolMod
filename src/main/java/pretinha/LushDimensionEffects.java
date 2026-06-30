package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;

public class LushDimensionEffects {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            ServerWorld world = server.getWorld(ModDimensions.LUSH_DIMENSION);

            if (world == null) return;

            for (Entity entity : world.iterateEntities()) {

                entity.setGlowing(true);
            }
        });
    }
}