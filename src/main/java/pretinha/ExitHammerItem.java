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
import net.minecraft.server.world.ServerWorld;

import java.util.HashMap;
import java.util.UUID;

public class ExitHammerItem extends Item {

    private static final HashMap<UUID, Integer> COUNTDOWN = new HashMap<>();
    private static final HashMap<UUID, Long> TIMER = new HashMap<>();

    public ExitHammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (world.isClient()) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (!(player instanceof ServerPlayerEntity sp)) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        UUID id = sp.getUuid();

        WarpState.start(id);

        COUNTDOWN.put(id, 5);
        TIMER.put(id, System.currentTimeMillis());

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    public static void tick(ServerPlayerEntity player) {

        UUID id = player.getUuid();

        if (!COUNTDOWN.containsKey(id)) return;

        long now = System.currentTimeMillis();

        if (now - TIMER.get(id) < 1000) return;

        TIMER.put(id, now);

        int time = COUNTDOWN.get(id);

        if (time > 0) {

            player.sendMessage(
                    Text.literal("Teleporting in " + time),
                    true
            );

            COUNTDOWN.put(id, time - 1);
            return;
        }

        COUNTDOWN.remove(id);
        TIMER.remove(id);

        ServerWorld target =
                player.getServer().getWorld(ModDimensions.DIMENSIONAL_BARRIERS);

        if (target == null) {
            WarpState.stop(id);
            return;
        }

        player.teleport(
                target,
                0.5,
                90,
                0.5,
                player.getYaw(),
                player.getPitch()
        );

        WarpState.stop(id);

        player.sendMessage(
                Text.literal("Teleported!").formatted(Formatting.GREEN, Formatting.BOLD),
                true
        );
    }
}