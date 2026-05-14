package net.isabel.aeroscapes.mixin;


import net.minecraft.structure.MineshaftGenerator;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.MineshaftStructure;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(MineshaftStructure.class)
public class AeroscapesMineshaftStructureMixin {

    private int surfaceHeight;

    @Shadow @Final
    private
    MineshaftStructure.Type type; // Used to get the mineshaft piece.

    @Redirect(method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;Lnet/minecraft/world/gen/structure/Structure$Context;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getSeaLevel()I"))
    private int mineshaftHeightFix(ChunkGenerator instance) {
        return surfaceHeight-8;
    }

    @Redirect(method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;Lnet/minecraft/world/gen/structure/Structure$Context;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getMinimumY()I"))
    private int mineshaftHeight(ChunkGenerator instance) {
        return surfaceHeight-32;
    }

    @Inject(method = "addPieces(Lnet/minecraft/structure/StructurePiecesCollector;Lnet/minecraft/world/gen/structure/Structure$Context;)I", at = @At("HEAD"))
    private void getMineshaftHeight(StructurePiecesCollector collector, Structure.Context context, CallbackInfoReturnable<Integer> cir) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkRandom chunkRandom = context.random();
        MineshaftGenerator.MineshaftRoom mineshaftRoom = new MineshaftGenerator.MineshaftRoom(
                0, chunkRandom, chunkPos.getOffsetX(2), chunkPos.getOffsetZ(2), this.type
        );
        collector.addPiece(mineshaftRoom);
        mineshaftRoom.fillOpenings(mineshaftRoom, collector, chunkRandom);
        if (!collector.isEmpty()) {
            surfaceHeight = context.chunkGenerator().getHeight(collector.getBoundingBox().getCenter().getX(), collector.getBoundingBox().getCenter().getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world(), context.noiseConfig());
        }
    }

}