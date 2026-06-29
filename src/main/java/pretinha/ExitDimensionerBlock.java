package pretinha;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class ExitDimensionerBlock extends Block {

    private static final HashMap<UUID, Integer> COUNTDOWN = new HashMap<>();
    private static final HashMap<UUID, Long> TIMER = new HashMap<>();

    public ExitDimensionerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(
            BlockState state,
            World world,
            BlockPos pos,
            PlayerEntity player,
            BlockHitResult hit
    ) {

        if (!world.isClient && player instanceof ServerPlayerEntity sp) {

            UUID id = sp.getUuid();

            WarpState.start(id);

            COUNTDOWN.put(id, 5);
            TIMER.put(id, System.currentTimeMillis());
        }

        return ActionResult.SUCCESS;
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

        ServerWorld target = player.getServer().getWorld(ModDimensions.DIMENSIONAL_BARRIERS);

        if (target == null) {
            WarpState.stop(id);
            return;
        }

        player.teleport(
                target,
                25,
                115,
                16,
                player.getYaw(),
                player.getPitch()
        );

        player.playSound(
                SoundEvents.BLOCK_PORTAL_TRAVEL,
                1.0F,
                1.0F
        );

        WarpState.stop(id);

        player.sendMessage(
                Text.literal("Teleported!")
                        .formatted(Formatting.GREEN, Formatting.BOLD),
                true
        );
    }
}