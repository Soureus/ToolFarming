package sour.toolfarming;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sour.toolfarming.block.ModBlocks;
import sour.toolfarming.item.ModItemGroup;
import sour.toolfarming.item.ModItems;

public class ToolFarming implements ModInitializer {

	public static final String MOD_ID = "toolfarming";
	public static final Logger LOGGER = LoggerFactory.getLogger("toolfarming");

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		//FabricDefaultAttributeRegistry.register(ModEntities.FAT_BOY, FatBoy.setAttributes());
	}
}