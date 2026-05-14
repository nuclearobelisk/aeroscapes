package net.isabel.aeroscapes.world.level.levelgen.util;

import net.minecraft.util.math.BlockPos;

@FunctionalInterface
public interface DistanceTester {
    boolean withinDistance(BlockPos origin, BlockPos from, double radius);
}
