package pretinha;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item DIMENSIONAL_REACTIVER = registerItem(
            "dimensional_reactiver",
            new Item(new Item.Settings())
    );

    private static Item registerItem(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(Coolmod.MOD_ID, name),
                item
        );
    }

    public static void register() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> {
                    entries.add(DIMENSIONAL_REACTIVER);
                });

        Coolmod.LOGGER.info("Itens registrados.");
    }
}