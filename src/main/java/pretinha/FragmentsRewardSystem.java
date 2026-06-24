package pretinha;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FragmentsRewardSystem {

    private static final Map<UUID, Integer> lastLevel = new HashMap<>();
    private static final Map<UUID, Integer> progress = new HashMap<>();
    private static final Map<UUID, Integer> requiredLevels = new HashMap<>();

    public static void register() {

        ServerTickEvents.END_WORLD_TICK.register(FragmentsRewardSystem::tick);
    }

    private static void tick(ServerWorld world) {

        for (ServerPlayerEntity player : world.getPlayers()) {

            if (player.experienceLevel > 1000) {
                continue;
            }

            UUID uuid = player.getUuid();

            int currentLevel = player.experienceLevel;

            lastLevel.putIfAbsent(uuid, currentLevel);
            progress.putIfAbsent(uuid, 0);
            requiredLevels.putIfAbsent(uuid, 2);

            int oldLevel = lastLevel.get(uuid);

            if (currentLevel > oldLevel) {

                int gained = currentLevel - oldLevel;

                int currentProgress = progress.get(uuid) + gained;
                int required = requiredLevels.get(uuid);

                while (currentProgress >= required) {

                    currentProgress -= required;

                    int amount = Random.create().nextInt(9) + 1;

                    player.giveItemStack(
                            new ItemStack(
                                    ModItems.FRAGMENTS_OF_EXIT,
                                    amount
                            )
                    );

                    required += 1;
                }

                progress.put(uuid, currentProgress);
                requiredLevels.put(uuid, required);
            }

            lastLevel.put(uuid, currentLevel);
        }
    }
}