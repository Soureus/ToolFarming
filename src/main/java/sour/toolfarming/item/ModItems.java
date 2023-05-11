package sour.toolfarming.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import sour.toolfarming.ToolFarming;
import sour.toolfarming.item.tools.LevelingToolsData;
import sour.toolfarming.item.tools.pickaxes.LevelingPickaxeItem;
import sour.toolfarming.item.tools.swords.LevelingSwordItem;
import sour.toolfarming.item.tools.tool_items.ModToolMaterials;

public class ModItems {


    public static final Item LARYN_CRYSTAL = registerItem("laryn_crystal",
            new Item(new FabricItemSettings()));

    public static final Item LARYN_SWORD = registerItem("laryn_sword",
            new LevelingSwordItem(ModToolMaterials.LARYN,"Laryn Sword",
                    LevelingToolsData.LARYN_SWORD_DAMAGES,  LevelingToolsData.LARYN_SWORD_SPEEDS, LevelingToolsData.LARYN_TOOL_LEVELS, LevelingToolsData.LARYN_SWORD_EXP,
                    new FabricItemSettings()));

    public static final Item LARYN_PICKAXE = registerItem("laryn_pickaxe",
            new LevelingPickaxeItem(ModToolMaterials.LARYN, "Laryn Pickaxe", new FabricItemSettings(),
                                    LevelingToolsData.LARYN_PICKAXE_DAMAGES, LevelingToolsData.LARYN_PICKAXE_ATTACK_SPEEDS, LevelingToolsData.LARYN_PICKAXE_MINING_SPEEDS,
                                    LevelingToolsData.LARYN_TOOL_LEVELS, LevelingToolsData.LARYN_SWORD_EXP
    ));


    //UTILS
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ToolFarming.MOD_ID, name), item);
    }



    public static void addItemsToItemGroup(){
        addToItemGroup(ModItemGroup.LARYN, LARYN_CRYSTAL);
        addToItemGroup(ModItemGroup.LARYN, LARYN_SWORD);
        addToItemGroup(ModItemGroup.LARYN, LARYN_PICKAXE);
    }

    public static void addToItemGroup(ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems(){
        ToolFarming.LOGGER.info("Registering Mod Items for" + ToolFarming.MOD_ID);
        addItemsToItemGroup();

    }

}
