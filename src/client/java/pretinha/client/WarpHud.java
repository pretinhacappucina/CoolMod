package pretinha.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class WarpScreen {

    private static Identifier texture;

    public static void setTexture(Identifier tex) {
        texture = tex;
    }

    public static void register() {
        HudRenderCallback.EVENT.register((DrawContext context, RenderTickCounter tickCounter) -> {

            if (texture == null) return;

            MinecraftClient client = MinecraftClient.getInstance();

            int w = client.getWindow().getScaledWidth();
            int h = client.getWindow().getScaledHeight();

            context.drawTexture(texture, 0, 0, 0, 0, w, h, w, h);
        });
    }
}