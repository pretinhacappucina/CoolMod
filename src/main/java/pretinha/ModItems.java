package pretinha;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.ToolMaterials;

public class ModItems {

    public static final Item PARKOUR_REACTIVER =
            register("parkour_reactiver");

    public static final Item DIMENSIONAL_REACTIVER =
            register("dimensional_reactiver");

    public static final Item SPEEDRUN_REACTIVER =
            register("speedrun_reactiver");

    public static final Item STRONGER_REACTIVER =
            register("stronger_reactiver");

    public static final Item REGENERATION_REACTIVER =
            register("regeneration_reactiver");

    public static final Item MINER_REACTIVER =
            register("miner_reactiver");

    public static final Item DIMENSIONAL_DUST =
            register("dimensional_dust");

    public static final Item FRAGMENTS_OF_EXIT =
            register("fragments_of_exit");

    public static final Item OVERWORLD_REACTIVER =
            register("overworld_reactiver");

    public static final Item EXIT_REACTIVER =
            register("exit_reactiver");

    public static final Item RANDOM_REACTIVER =
            register("random_reactiver");
    public static final Item MOUNTAINS_REACTIVER = register("mountains_reactiver");
    public static final Item SCULK_REACTIVER =
            register("sculk_reactiver");
    public static final Item LUSH_REACTIVATOR =
            register("lush_reactivator");
    public static final Item WATER_REACTIVER =
            register("water_reactiver");
    public static final Item LAVA_REACTIVER =
            register("lava_reactivator");
    public static final Item TNT_REACTIVER =
            register("tnt_reactivator");
    public static final Item DESERT_REACTIVER =
            register("desert_reactiver");

    public static final Item EXIT_HAMMER =
            Registry.register(
                    Registries.ITEM,
                    Identifier.of(Coolmod.MOD_ID, "exit_hammer"),
                    new ExitHammerItem(
                            new Item.Settings()
                                    .maxDamage(1500)
                    )
            );

    public static final Item BARRIER_BREAKER =
            Registry.register(
                    Registries.ITEM,
                    Identifier.of(Coolmod.MOD_ID, "barrier_breaker"),
                    new BarrierBreakerItem(
                            ToolMaterials.NETHERITE,
                            new Item.Settings()
                    )
            );

    private static Item register(String name) {
        Item item = new Item(new Item.Settings());

        Registry.register(
                Registries.ITEM,
                Identifier.of(Coolmod.MOD_ID, name),
                item
        );

        return item;
    }

    public static void register() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> {
                    entries.add(PARKOUR_REACTIVER);
                    entries.add(DIMENSIONAL_REACTIVER);
                    entries.add(SPEEDRUN_REACTIVER);
                    entries.add(STRONGER_REACTIVER);
                    entries.add(REGENERATION_REACTIVER);
                    entries.add(MINER_REACTIVER);
                    entries.add(DIMENSIONAL_DUST);
                    entries.add(FRAGMENTS_OF_EXIT);
                    entries.add(EXIT_HAMMER);
                    entries.add(BARRIER_BREAKER);
                    entries.add(OVERWORLD_REACTIVER);
                    entries.add(EXIT_REACTIVER);
                    entries.add(RANDOM_REACTIVER);
                    entries.add(MOUNTAINS_REACTIVER);
                    entries.add(SCULK_REACTIVER);
                    entries.add(LUSH_REACTIVATOR);
                    entries.add(WATER_REACTIVER);
                    entries.add(LAVA_REACTIVER);
                    entries.add(TNT_REACTIVER);
                    entries.add(DESERT_REACTIVER);
                });

        Coolmod.LOGGER.info("Items registrados");
    }
}