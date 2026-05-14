package net.isabel.aeroscapes.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.isabel.aeroscapes.Aeroscapes;
import net.isabel.aeroscapes.registry.AeroscapesBlocks;
import net.minecraft.client.render.RenderLayer;

public class AeroscapesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(AeroscapesBlocks.AEROLITE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AeroscapesBlocks.GOLD_AEROLITE, RenderLayer.getCutout());

		Aeroscapes.LOGGER.info("Aeroscapes Client Loaded!");
	}
}