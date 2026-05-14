package net.isabel.aeroscapes.registry;

import net.isabel.aeroscapes.Aeroscapes;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class AeroscapesTags {
    public static final TagKey<Block> IS_AEROLITE = tag("is_aerolite");

    private static TagKey<Block> tag(String name) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Aeroscapes.MOD_ID, name));
    }
}
