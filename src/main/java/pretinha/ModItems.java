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
                });

        Coolmod.LOGGER.info("Items registrados");
    }
}