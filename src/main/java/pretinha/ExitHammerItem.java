package pretinha;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class ExitHammerItem extends Item {

    private static final HashMap<UUID, Integer> COUNTDOWN = new HashMap<>();
    private static final HashMap<UUID, Long> TIMER = new HashMap<>();
    private static final HashMap<UUID, Long> COOLDOWN = new HashMap<>();

    private static final long COOLDOWN_TIME = 5 * 60 * 1000; // 5 minutos

    public ExitHammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient && player instanceof ServerPlayerEntity sp) {

            UUID id = sp.getUuid();
            long now = System.currentTimeMillis();

            // COOLDOWN
            if (COOLDOWN.containsKey(id)) {
                long last = COOLDOWN.get(id);

                if (now - last < COOLDOWN_TIME) {
                    long left = (COOLDOWN_TIME - (now - last)) / 1000;

                    player.sendMessage(
                            Text.literal("Cooldown: " + left + "s"),
                            true
                    );

                    return TypedActionResult.pass(player.getStackInHand(hand));
                }
            }

            COOLDOWN.put(id, now);

            // inicia warp
            WarpState.start(id);
            COUNTDOWN.put(id, 5);
            TIMER.put(id, now);
        }

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public static void tick(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!COUNTDOWN.containsKey(id)) return;

        long now = System.currentTimeMillis();

        if (now - TIMER.get(id) < 1000) return;

        TIMER.put(id, now);

        int time = COUNTDOWN.get(id);

        // CONTAGEM
        if (time > 0) {

            player.sendMessage(
                    Text.literal("Teleporting in " + time),
                    true
            );

            COUNTDOWN.put(id, time - 1);
            return;
        }

        // FINALIZA
        COUNTDOWN.remove(id);
        TIMER.remove(id);

        var target = player.getServer()
                .getWorld(ModDimensions.DIMENSIONAL_BARRIERS);

        if (target == null) {
            WarpState.stop(id);
            return;
        }

        ItemStack stack = player.getMainHandStack();

        if (!(stack.getItem() instanceof ExitHammerItem)) {
            WarpState.stop(id);
            return;
        }

        // TELEPORT
        player.teleport(
                target,
                0.5,
                90,
                0.5,
                player.getYaw(),
                player.getPitch()
        );

        // DANO NO PLAYER
        player.damage(
                player.getDamageSources().magic(),
                6.0F
        );

        // DURABILIDADE
        stack.damage(
                100,
                player,
                PlayerEntity.getSlotForHand(player.getActiveHand())
        );

        WarpState.stop(id);

        player.sendMessage(
                Text.literal("Teleported!")
                        .formatted(Formatting.GREEN, Formatting.BOLD),
                true
        );
    }
}