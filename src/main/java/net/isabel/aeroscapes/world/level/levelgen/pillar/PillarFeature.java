package net.isabel.aeroscapes.world.level.levelgen.pillar;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.isabel.aeroscapes.world.level.levelgen.util.DistanceTestType;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.joml.Math;

public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<PillarFeatureConfig> context) {

        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        StructureWorldAccess level = context.getWorld();

        PillarFeatureConfig config = context.getConfig();
        int height = config.height().get(random);
        int vine_length = config.vine_length().get(random);
        int height_offset = config.height_offset().get(random);

        int radius = config.radius().get(random);
        double frequency = config.noisefreq().get(random);

        double minRadiusScale = config.minRadiusScale().get(random);

        DistanceTestType tester = config.distanceTestType().getDataOrEmpty(random).orElseThrow();

        PerlinNoiseSampler noise = new PerlinNoiseSampler(random);

        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();

        LongSet cache = new LongOpenHashSet();

        for (int xOffset = -radius; xOffset <= radius; xOffset++) {
            for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                mutableBlockPos.set(origin, xOffset, 0, zOffset);
                if (tester.getDistanceTester().withinDistance(origin, mutableBlockPos, radius)) {
                    int heightmap = (height_offset == 0) ? level.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, mutableBlockPos.getX(), mutableBlockPos.getZ()) : 5;

                    int radius1 = radius;
                    int maxY = origin.getY() + height + height_offset;
                    for (int worldY = heightmap - 5; worldY <= maxY; worldY++) {
                        mutableBlockPos.set(origin.getX() + xOffset, worldY, origin.getZ() + zOffset);

                        double pillarNoise = (noise.sample(mutableBlockPos.getX() * frequency, mutableBlockPos.getY() * frequency, mutableBlockPos.getZ() * frequency) + 1) * 0.5; // 0-1 range
                        double localRadius = MathHelper.clampedLerp(radius1 * minRadiusScale, radius1, pillarNoise);
                        double taperedRadius = MathHelper.clampedLerp(0.0, localRadius, (float) Math.max(worldY, -16) / (origin.getY() - 16));

                        if (taperedRadius > 0 && tester.getDistanceTester().withinDistance(origin.withY(worldY), mutableBlockPos, taperedRadius)) {
                            cache.add(mutableBlockPos.asLong());
                        } else if (taperedRadius == 0) {
                            break;
                        }
                    }
                }
            }
        }

        for (Pair<BlockPredicate, BlockStateProvider> blockPlacement : config.checkedBlockPlacement().blockPlacement()) {
            cache.forEach(pos -> {
                mutableBlockPos.set(pos);

                if (blockPlacement.getFirst().test(level, mutableBlockPos)) {
                    level.setBlockState(mutableBlockPos, blockPlacement.getSecond().get(random, mutableBlockPos), 2);
                }
            });
        }
        cache.forEach(pos -> {
            mutableBlockPos.set(pos);
            Direction.Type horizontal = Direction.Type.HORIZONTAL;

            for (Direction direction : horizontal) {

                if (random.nextDouble() < 0.1) {
                    mutableBlockPos.set(pos).move(direction);
                    for (int i = 0; i < vine_length; i++) {
                        if (level.getBlockState(mutableBlockPos).isAir() && !cache.contains(mutableBlockPos.asLong())) {

                            level.setBlockState(mutableBlockPos, Blocks.VINE.getDefaultState().with(VineBlock.getFacingProperty(direction.getOpposite()), true), 2);
                        }
                        mutableBlockPos.move(Direction.DOWN);
                    }
                }
            }
        });

        return true;
    }

}