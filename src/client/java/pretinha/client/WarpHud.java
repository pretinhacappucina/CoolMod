package pretinha.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class WarpHud {

    private static Identifier texture;

    private static float alpha = 0f;
    private static boolean active = false;

    private static float shakeIntensity = 0f;

    public static void setTexture(Identifier tex) {
        texture = tex;
        active = tex != null;
    }

    public static void register() {

        HudRenderCallback.EVENT.register((DrawContext context, RenderTickCounter tickCounter) -> {

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || texture == null) return;

            float speed = 0.07f;

            // fade + shake control
            if (active) {
                alpha += speed;
                shakeIntensity += 0.4f;
            } else {
                alpha -= speed;
                shakeIntensity -= 0.05f;
            }

            if (alpha > 1f) alpha = 1f;
            if (alpha < 0f) alpha = 0f;

            if (shakeIntensity > 1f) shakeIntensity = 1f;
            if (shakeIntensity < 0f) shakeIntensity = 0f;

            if (alpha <= 0f) return;

            int w = client.getWindow().getScaledWidth();
            int h = client.getWindow().getScaledHeight();

            // tremor forte
            float shakeX = (client.world.random.nextFloat() - 0.5f) * 20f * shakeIntensity;
            float shakeY = (client.world.random.nextFloat() - 0.5f) * 20f * shakeIntensity;

            context.setShaderColor(1f, 1f, 1f, alpha);

            context.drawTexture(
                    texture,
                    (int) shakeX,
                    (int) shakeY,
                    0, 0,
                    w, h,
                    w, h
            );

            context.setShaderColor(1f, 1f, 1f, 1f);
        });
    }
}