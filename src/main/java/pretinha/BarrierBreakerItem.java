package pretinha;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class BarrierBreakerItem extends PickaxeItem {

    private static final HashMap<UUID, Long> COOLDOWN = new HashMap<>();

    private static final long COOLDOWN_TICKS = 20L * 60L * 10L; // 10 min
    private static final int DURABILITY_COST = 100;

    public BarrierBreakerItem(ToolMaterial material, Settings settings) {
        super(material, settings.maxDamage(2500));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        if (world.isClient()) return ActionResult.SUCCESS;

        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
        if (player == null) return ActionResult.PASS;

        ServerWorld serverWorld = (ServerWorld) world;
        BlockPos pos = context.getBlockPos();

        BlockState state = serverWorld.getBlockState(pos);

        if (state.isAir()) return ActionResult.FAIL;

        UUID id = player.getUuid();
        long now = player.getServer().getTicks();

        // 🔥 cooldown check
        if (COOLDOWN.containsKey(id)) {
            long left = COOLDOWN_TICKS - (now - COOLDOWN.get(id));

            if (left > 0) {
                player.sendMessage(
                        Text.literal("Cooldown: " + (left / 20) + "s")
                                .formatted(Formatting.RED),
                        true
                );
                return ActionResult.FAIL;
            }
        }

        // 💥 remove block FIRST
        serverWorld.removeBlock(pos, false);

        // 📦 convert block → item (forma correta 1.21.1)
        ItemStack drop = new ItemStack(state.getBlock().asItem());

        // se o bloco não tiver item, fallback seguro
        if (drop.isEmpty()) {
            return ActionResult.FAIL;
        }

        // spawn item no chão
        serverWorld.spawnEntity(new ItemEntity(
                serverWorld,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                drop
        ));

        // 🔧 durabilidade
        player.getMainHandStack().damage(DURABILITY_COST, player, null);

        // 🔥 agora sim ativa cooldown (SÓ SE FUNCIONOU)
        COOLDOWN.put(id, now);

        return ActionResult.SUCCESS;
    }
}