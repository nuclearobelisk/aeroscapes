package net.isabel.aeroscapes.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.isabel.aeroscapes.Aeroscapes;
import net.isabel.aeroscapes.block.*;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.registry.Registry;

public class AeroscapesBlocks {



    public static final Block QUICKSLATE = registerBlock("quickslate",
            new Block(AbstractBlock.Settings.copy(Blocks.STONE)
                    .strength(3.0F, 4.0F)
                    .requiresTool().sounds(BlockSoundGroup.SHROOMLIGHT)
                    .velocityMultiplier(1.25F)
                    .jumpVelocityMultiplier(1.5F)));

    public static final Block QUICKSLATE_SLAB = registerBlock("quickslate_slab",
            new SlabBlock(AbstractBlock.Settings.copy(QUICKSLATE)));

    public static final Block QUICKSLATE_STAIRS = registerBlock("quickslate_stairs",
            new StairsBlock(QUICKSLATE.getDefaultState(), AbstractBlock.Settings.copy(QUICKSLATE)));

    public static final Block QUICKSLATE_WALL = registerBlock("quickslate_wall",
            new WallBlock(AbstractBlock.Settings.copy(QUICKSLATE)));

    public static final Block CHISELED_QUICKSLATE = registerBlock("chiseled_quickslate",
            new Block(AbstractBlock.Settings.copy(QUICKSLATE)));

    public static final Block POLISHED_QUICKSLATE = registerBlock("polished_quickslate",
            new Block(AbstractBlock.Settings.copy(QUICKSLATE)));

    public static final Block POLISHED_QUICKSLATE_SLAB = registerBlock("polished_quickslate_slab",
            new SlabBlock(AbstractBlock.Settings.copy(POLISHED_QUICKSLATE)));

    public static final Block POLISHED_QUICKSLATE_STAIRS = registerBlock("polished_quickslate_stairs",
            new StairsBlock(POLISHED_QUICKSLATE.getDefaultState(), AbstractBlock.Settings.copy(POLISHED_QUICKSLATE)));

    public static final Block POLISHED_QUICKSLATE_WALL = registerBlock("polished_quickslate_wall",
            new WallBlock(AbstractBlock.Settings.copy(POLISHED_QUICKSLATE)));


    public static final Block AEROLITE = registerBlock("aerolite",
            new AeroliteBlock(UniformIntProvider.create(0, 1), AbstractBlock.Settings.copy(Blocks.STONE)
                    .strength(1.0f, 3.0f).requiresTool()
                    .sounds(BlockSoundGroup.SHROOMLIGHT)
                    .ticksRandomly()
                    .nonOpaque()
                    .allowsSpawning((state, world, pos, type) -> false)
                    .solidBlock((state, world, pos) -> false)
                    .suffocates((state, world, pos) -> false)
                    .blockVision((state, world, pos) -> false)));

    public static final Block GOLD_AEROLITE = registerBlock("gold_aerolite",
            new AeroliteBlock(UniformIntProvider.create(0, 2), AbstractBlock.Settings.copy(Blocks.GOLD_ORE)
                    .strength(1.0f, 3.0f).requiresTool()
                    .sounds(BlockSoundGroup.SHROOMLIGHT)
                    .ticksRandomly()
                    .nonOpaque()
                    .allowsSpawning((state, world, pos, type) -> false)
                    .solidBlock((state, world, pos) -> false)
                    .suffocates((state, world, pos) -> false)
                    .blockVision((state, world, pos) -> false)));

    public static final Block POINTED_BASALT = registerBlock("pointed_basalt",
            new PointedDripstoneBlock(AbstractBlock.Settings.copy(Blocks.POINTED_DRIPSTONE)
                    .mapColor(MapColor.BLACK)
                    .sounds(BlockSoundGroup.BASALT)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Aeroscapes.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Aeroscapes.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(QUICKSLATE);
            fabricItemGroupEntries.add(QUICKSLATE_SLAB);
            fabricItemGroupEntries.add(QUICKSLATE_STAIRS);
            fabricItemGroupEntries.add(QUICKSLATE_WALL);
            fabricItemGroupEntries.add(CHISELED_QUICKSLATE);
            fabricItemGroupEntries.add(POLISHED_QUICKSLATE);
            fabricItemGroupEntries.add(POLISHED_QUICKSLATE_SLAB);
            fabricItemGroupEntries.add(POLISHED_QUICKSLATE_STAIRS);
            fabricItemGroupEntries.add(POLISHED_QUICKSLATE_WALL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(AEROLITE);
            fabricItemGroupEntries.add(GOLD_AEROLITE);
            fabricItemGroupEntries.add(POINTED_BASALT);
        });

        Aeroscapes.LOGGER.info("Registering Aeroscapes Blocks...");
    }
}
