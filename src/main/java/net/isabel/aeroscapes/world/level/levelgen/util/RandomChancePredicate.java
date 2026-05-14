package net.isabel.aeroscapes.world.level.levelgen.util;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.isabel.aeroscapes.registry.AeroscapesFeatures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.blockpredicate.BlockPredicateType;
import org.jetbrains.annotations.NotNull;

public class RandomChancePredicate implements BlockPredicate {
    private final FloatProvider chance;

    public RandomChancePredicate(FloatProvider chance) {
        this.chance = chance;
    }
    public static RandomChancePredicate of(FloatProvider chance) {
        return new RandomChancePredicate(chance);
    }

    public static final MapCodec<RandomChancePredicate> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    FloatProvider.VALUE_CODEC.fieldOf("chance").forGetter(randomChancePredicate -> randomChancePredicate.chance)
            ).apply(instance, RandomChancePredicate::new));

    @Override
    public @NotNull BlockPredicateType<?> getType() {
        return AeroscapesFeatures.RANDOM_CHANCE;
    }

    @Override
    public boolean test(StructureWorldAccess level, BlockPos blockPos) {
        Xoroshiro128PlusPlusRandom xoroshiroRandomSource = new Xoroshiro128PlusPlusRandom(blockPos.asLong() + level.getSeed());
        return xoroshiroRandomSource.nextDouble() < this.chance.get(xoroshiroRandomSource);
    }
}
