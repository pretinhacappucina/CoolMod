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


    public static final Block BLACK_BEDROCK =
            registerBlackBedrock("black_bedrock");

    public static final Block DIMENSIONS_TELEPORTER =
            register("dimensions_teleporter");

    public static final Block GOLDEN_BEDROCK =
            registerGoldenBedrock("golden_bedrock");

    public static final Block EXIT_DIMENSIONER =
            registerExit("exit_dimensioner");

    public static final Block BEDROCK_ILUMINER =
            registerBedrockIluminer("bedrock_iluminer");

    public static final Block SPAWN_BEDROCK =
            register("spawn_bedrock");

    public static final Block DIAMOND_BEDROCK =
            registerDiamondBedrock("diamond_bedrock");

    public static final Block XP_TRANSFORMER =
            registerXPTransformer("xp_transformer");



    private static Block register(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new Block(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    private static Block registerXPTransformer(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new XPTransformerBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    private static Block registerBlackBedrock(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new BlackBedrockBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    private static Block registerGoldenBedrock(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new GoldenBedrockBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    private static Block registerDiamondBedrock(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new DiamondBedrockBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    private static Block registerBedrockIluminer(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new BedrockIluminerBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
                        .luminance(state -> 15)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    private static Block registerExit(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new ExitDimensionerBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }



    public static void register() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register(entries -> {

                    entries.add(BLACK_BEDROCK);
                    entries.add(DIMENSIONS_TELEPORTER);
                    entries.add(GOLDEN_BEDROCK);
                    entries.add(EXIT_DIMENSIONER);
                    entries.add(BEDROCK_ILUMINER);
                    entries.add(SPAWN_BEDROCK);
                    entries.add(DIAMOND_BEDROCK);
                    entries.add(XP_TRANSFORMER);
                });

        Coolmod.LOGGER.info("Blocks registrados!");
    }


}
