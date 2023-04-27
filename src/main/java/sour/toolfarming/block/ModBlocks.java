package sour.toolfarming.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import sour.toolfarming.ToolFarming;
import sour.toolfarming.item.ModItemGroup;

public class ModBlocks {

    //BLOCKS

    //HELPERS
    private static Block registerBlock(String name, Block block, ItemGroup group){

        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(ToolFarming.MOD_ID,name),block);

    }
    private static Item registerBlockItem(String name, Block block, ItemGroup group){
        Item item = Registry.register(Registries.ITEM, new Identifier(ToolFarming.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }
    public static void registerModBlocks(){
        ToolFarming.LOGGER.info("Registering ModBlocks for " + ToolFarming.MOD_ID);
    }

}
