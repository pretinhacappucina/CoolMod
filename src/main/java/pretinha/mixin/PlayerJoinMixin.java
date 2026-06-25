package pretinha.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import pretinha.DimensionRunManager;
import pretinha.JumpManager;
import pretinha.ModDimensions;
import pretinha.SpeedManager;
import pretinha.StrengthManager;
import pretinha.FallDamageManager;
import pretinha.RegenerationManager;
import pretinha.MiningManager;

@Mixin(ServerPlayerEntity.class)
public class PlayerJoinMixin {

    @Inject(method = "onSpawn", at = @At("TAIL"))
    private void onSpawn(CallbackInfo ci) {

        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        ServerWorld world =
                player.getServer().getWorld(ModDimensions.DIMENSIONAL_BARRIERS);

        if (world == null) return;

        DimensionRunManager.create(player.getUuid());
        JumpManager.create(player.getUuid());
        SpeedManager.create(player.getUuid());
        StrengthManager.create(player.getUuid());
        FallDamageManager.create(player.getUuid());
        RegenerationManager.create(player.getUuid());
        MiningManager.create(player.getUuid());

        // SPAWN FIXO
        BlockPos spawn = new BlockPos(0, 90, 0);

        player.teleport(
                world,
                spawn.getX() + 0.5,
                spawn.getY(),
                spawn.getZ() + 0.5,
                player.getYaw(),
                player.getPitch()
        );
    }
}