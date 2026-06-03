package net.isabel.aeroscapes.registry;

import net.isabel.aeroscapes.Aeroscapes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class AeroscapesTags {
    public static class Blocks {
        public static final TagKey<Block> IS_AEROLITE = tag("is_aerolite");

        private static TagKey<Block> tag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Aeroscapes.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> IS_FLOATING_ITEM = tag("is_floating_item");

        private static TagKey<Item> tag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Aeroscapes.MOD_ID, name));
        }
    }
}
