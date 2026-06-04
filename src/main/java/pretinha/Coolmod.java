package pretinha;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coolmod implements ModInitializer {

    public static final String MOD_ID = "coolmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModItems.register();
        ModBlocks.register();
        DimensionalReactivatorHandler.register();
        // 🔥 registra teleport system (1 vez só)
        DimensionTeleportHandler.register();

        // 🔥 tick global separado (limpo)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList()
                    .forEach(ExitDimensionerBlock::tick);
        });
        LOGGER.info("Coolmod inicializado com sucesso!");
    }
}