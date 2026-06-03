package net.isabel.aeroscapes.registry;

import net.isabel.aeroscapes.Aeroscapes;
import net.isabel.aeroscapes.world.level.levelgen.VoidCarver;
import net.isabel.aeroscapes.world.level.levelgen.cluster.BasaltClusterFeature;
import net.isabel.aeroscapes.world.level.levelgen.pillar.PillarFeature;
import net.isabel.aeroscapes.world.level.levelgen.pillar.PillarFeatureConfig;
import net.isabel.aeroscapes.world.level.levelgen.util.RandomChancePredicate;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.blockpredicate.BlockPredicateType;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.feature.DripstoneClusterFeatureConfig;

public class AeroscapesFeatures {

    public static final PillarFeature PILLAR = new PillarFeature(PillarFeatureConfig.CODEC);
    public static final VoidCarver VOID = new VoidCarver(CaveCarverConfig.CAVE_CODEC);
    public static final BasaltClusterFeature BASALT_CLUSTER = new BasaltClusterFeature(DripstoneClusterFeatureConfig.CODEC);

    public static final BlockPredicateType<RandomChancePredicate> RANDOM_CHANCE = Registry.register(Registries.BLOCK_PREDICATE_TYPE, Identifier.of(Aeroscapes.MOD_ID, "random_chance"), () -> RandomChancePredicate.CODEC);

    public static void registerFeatures() {
        Registry.register(Registries.FEATURE, Identifier.of(Aeroscapes.MOD_ID, "pillar"), PILLAR);
        Registry.register(Registries.CARVER, Identifier.of(Aeroscapes.MOD_ID, "void"), VOID);
        Registry.register(Registries.FEATURE, Identifier.of(Aeroscapes.MOD_ID, "basalt_cluster"), BASALT_CLUSTER);

        Aeroscapes.LOGGER.info("Registering Aeroscapes Features...");
    }
}
