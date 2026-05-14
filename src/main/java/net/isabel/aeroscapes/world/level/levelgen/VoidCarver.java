package net.isabel.aeroscapes.world.level.levelgen;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.carver.NetherCaveCarver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.function.Function;

public class VoidCarver extends NetherCaveCarver {
    public VoidCarver(Codec<CaveCarverConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean carveAtPoint(CarverContext carverContext, CaveCarverConfig caveCarverConfig, Chunk chunk, Function<BlockPos, RegistryEntry<Biome>> function, CarvingMask carvingMask, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, AquiferSampler aquiferSampler, MutableBoolean mutableBoolean) {
        if (this.canAlwaysCarveBlock(caveCarverConfig, chunk.getBlockState(mutable))) {
            chunk.setBlockState(mutable, CAVE_AIR, false);
            return true;
        } else {
            return false;
        }
    }
}
