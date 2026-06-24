package pretinha;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.PickaxeItem;
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


    private static Item register(String name) {
        Item item = new Item(new Item.Settings());

        Registry.register(
                Registries.ITEM,
                Identifier.of(Coolmod.MOD_ID, name),
                item
        );

        return item;
    }
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
                    entries.add(BARRIER_BREAKER);
                    entries.add(FRAGMENTS_OF_EXIT);
                    entries.add(EXIT_HAMMER);
                });

        Coolmod.LOGGER.info("Items registrados");
    }
}