package pretinha;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block BLACK_BEDROCK = registerBlock(
            "black_bedrock",
            new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );

    public static final Block DIMENSIONS_TELEPORTER = registerBlock(
            "dimensions_teleporter",
            new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );

    public static final Block GOLDEN_BEDROCK = registerBlock(
            "golden_bedrock",
            new Block(AbstractBlock.Settings.copy(Blocks.BEDROCK))
    );

    private static Block registerBlock(String name, Block block) {

        Registry.register(
                Registries.ITEM,
                Identifier.of(Coolmod.MOD_ID, name),
                new BlockItem(block, new Item.Settings())
        );

        return Registry.register(
                Registries.BLOCK,
                Identifier.of(Coolmod.MOD_ID, name),
                block
        );
    }

    public static void register() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register(entries -> {

                    entries.add(BLACK_BEDROCK);
                    entries.add(DIMENSIONS_TELEPORTER);
                    entries.add(GOLDEN_BEDROCK);

                });

        Coolmod.LOGGER.info("Blocos registrados.");
    }
}