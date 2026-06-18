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
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.util.math.intprovider.ConstantIntProvider;

public class ModBlocks {


    public static final Block BLACK_BEDROCK =
            registerBlackBedrock("black_bedrock");
    public static final Block JUMP_OF_INFINITY =
            registerJumpOfInfinity("jump_of_infinity");
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
    public static final Block INVENTORY_CLEANER =
            registerInventoryCleaner("inventory_cleaner");
    public static final Block XP_DROPPER =
            registerXPDropper("xp_dropper");
    public static final Block EMERALD_BEDROCK =
            registerEmeraldBedrock("emerald_bedrock");
    public static final Block SPEED_OF_INFINITY =
            registerSpeedOfInfinity("speed_of_infinity");
    public static final Block STRONG_OF_INFINITY =
            registerStrongOfInfinity("strong_of_infinity");
    public static final Block REGENERATION_OF_INFINITY =
            registerRegenerationOfInfinity("regeneration_of_infinity");
    public static final Block MINE_OF_INFINITY =
            registerMineOfInfinity("mine_of_infinity");
    public static final Block DIMENSIONAL_FIREFLIES =
            registerDimensionalFireflies("dimensional_fireflies");
    public static final Block DIMENSIONAL_ORE =
            registerDimensionalOre("dimensional_ore");



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
    private static Block registerDimensionalOre(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new ExperienceDroppingBlock(
                ConstantIntProvider.create(3),
                AbstractBlock.Settings.copy(Blocks.ANCIENT_DEBRIS)
                        .requiresTool()
        );

        Registry.register(Registries.BLOCK, id, block);

        Registry.register(
                Registries.ITEM,
                id,
                new BlockItem(block, new Item.Settings())
        );

        return block;
    }
    private static Block registerStrongOfInfinity(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new StrongOfInfinityBlock(
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
    private static Block registerDimensionalFireflies(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new DimensionalFirefliesBlock(
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
    private static Block registerRegenerationOfInfinity(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new RegenerationOfInfinityBlock(
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
    private static Block registerMineOfInfinity(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new MineOfInfinityBlock(
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
    private static Block registerJumpOfInfinity(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new JumpOfInfinityBlock(
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
    private static Block registerSpeedOfInfinity(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new SpeedOfInfinityBlock(
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
    private static Block registerInventoryCleaner(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new InventoryCleanerBlock(
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
    private static Block registerEmeraldBedrock(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new EmeraldBedrockBlock(
                AbstractBlock.Settings.copy(Blocks.BEDROCK)
                        .strength(-1.0F, 3600000.0F)
                        .luminance(state -> 6)
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
    private static Block registerXPDropper(String name) {

        Identifier id = Identifier.of(Coolmod.MOD_ID, name);

        Block block = new XPDropperBlock(
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
                    entries.add(INVENTORY_CLEANER);
                    entries.add(XP_DROPPER);
                    entries.add(EMERALD_BEDROCK);
                    entries.add(JUMP_OF_INFINITY);
                    entries.add(SPEED_OF_INFINITY);
                    entries.add(STRONG_OF_INFINITY);
                    entries.add(REGENERATION_OF_INFINITY);
                    entries.add(MINE_OF_INFINITY);
                    entries.add(DIMENSIONAL_FIREFLIES);
                    entries.add(DIMENSIONAL_ORE);
                });

        Coolmod.LOGGER.info("Blocks registrados!");
    }


}
