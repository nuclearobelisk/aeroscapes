package net.isabel.aeroscapes.world.level.levelgen.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;

public enum DistanceTestType {
    EUCLIDEAN(BlockPos::isWithinDistance),
    MANHATTAN(((origin, from, radius) -> origin.getManhattanDistance(from) < radius)),
    CHEBYSHEV((origin, from, radius) -> {
        int dx = Math.abs(origin.getX() - from.getX());
        int dy = Math.abs(origin.getY() - from.getY());
        int dz = Math.abs(origin.getZ() - from.getZ());

        return Math.max(Math.max(dx, dy), dz) < radius;
    });

    public static final Codec<DistanceTestType> CODEC = Codec.STRING.xmap(DistanceTestType::valueOf, DistanceTestType::name);


    private final DistanceTester distanceTester;

    DistanceTestType(DistanceTester distanceTester) {
        this.distanceTester = distanceTester;
    }

    public DistanceTester getDistanceTester() {
        return distanceTester;
    }
}