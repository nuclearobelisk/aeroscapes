package net.isabel.aeroscapes;

import net.fabricmc.api.ModInitializer;

import net.isabel.aeroscapes.registry.AeroscapesBlocks;
import net.isabel.aeroscapes.registry.AeroscapesItems;
import net.isabel.aeroscapes.registry.AeroscapesSounds;
import net.isabel.aeroscapes.registry.AeroscapesFeatures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Aeroscapes implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "aeroscapes";
	@Override
	public void onInitialize() {
		AeroscapesItems.registerItems();
		AeroscapesBlocks.registerBlocks();
		AeroscapesSounds.registerSounds();
		AeroscapesFeatures.registerFeatures();
		LOGGER.info("Aeroscapes Loaded!");
	}
}