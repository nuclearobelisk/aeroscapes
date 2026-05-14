package net.isabel.aeroscapes.world.level.levelgen.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public record CheckedBlockPlacement(List<Pair<BlockPredicate, BlockStateProvider>> blockPlacement) {
    public static final Codec<CheckedBlockPlacement> CODEC = Codec.pair(BlockPredicate.BASE_CODEC.fieldOf("check").codec(), BlockStateProvider.TYPE_CODEC.fieldOf("blockStateProvider").codec()).listOf().xmap(CheckedBlockPlacement::new, CheckedBlockPlacement::blockPlacement);
}
