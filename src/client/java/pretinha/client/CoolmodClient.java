package pretinha.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import pretinha.WarpState;

public class CoolmodClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		WarpHud.register();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			if (client.player == null) return;

			if (WarpState.isActive(client.player.getUuid())) {

				WarpHud.setTexture(WarpTextures.DIMENSOES);

			} else {

				WarpHud.setTexture(null);
			}
		});
	}
}