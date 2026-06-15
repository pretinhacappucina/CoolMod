package pretinha;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

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
                });

        Coolmod.LOGGER.info("Items registrados");
    }
}