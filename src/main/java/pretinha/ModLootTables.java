package pretinha;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTables {

    public static void register() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (!source.isBuiltin()) {
                return;
            }

            Identifier id = key.getValue();

            String path = id.getPath();

            if (path.startsWith("chests/")) {

                LootPool pool = LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(1.0f, 4.0f))
                        .with(ItemEntry.builder(ModItems.FRAGMENTS_OF_EXIT))
                        .build();

                tableBuilder.pool(pool);
            }
        });
    }
}