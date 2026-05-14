package net.isabel.aeroscapes.world.level.levelgen.pillar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.isabel.aeroscapes.world.level.levelgen.util.CheckedBlockPlacement;
import net.isabel.aeroscapes.world.level.levelgen.util.DistanceTestType;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public record PillarFeatureConfig(
        CheckedBlockPlacement checkedBlockPlacement,
        IntProvider height,
        IntProvider radius,
        IntProvider vine_length,
        IntProvider height_offset,
        FloatProvider noisefreq,
        FloatProvider minRadiusScale,
        DataPool<DistanceTestType> distanceTestType
) implements FeatureConfig {
    public static final Codec<PillarFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    CheckedBlockPlacement.CODEC.fieldOf("block_placement").forGetter(PillarFeatureConfig::checkedBlockPlacement),
                    IntProvider.VALUE_CODEC.fieldOf("height").forGetter(PillarFeatureConfig::height),
                    IntProvider.VALUE_CODEC.fieldOf("radius").forGetter(PillarFeatureConfig::radius),
                    IntProvider.VALUE_CODEC.fieldOf("vine_length").forGetter(PillarFeatureConfig::vine_length),
                    IntProvider.VALUE_CODEC.fieldOf("height_offset").forGetter(PillarFeatureConfig::height_offset),
                    FloatProvider.VALUE_CODEC.fieldOf("noise_frequency").forGetter(PillarFeatureConfig::noisefreq),
                    FloatProvider.VALUE_CODEC.fieldOf("min_radius_scale").forGetter(PillarFeatureConfig::minRadiusScale),
                    DataPool.createCodec(DistanceTestType.CODEC).fieldOf("distance_test_type").forGetter(PillarFeatureConfig::distanceTestType)
            ).apply(instance, PillarFeatureConfig::new)
    );
}