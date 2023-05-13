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
import sour.toolfarming.item.tools.axes.LevelingAxeItem;
import sour.toolfarming.item.tools.hoes.LevelingHoeItem;
import sour.toolfarming.item.tools.pickaxes.LevelingPickaxeItem;
import sour.toolfarming.item.tools.shovels.LevelingShovelItem;
import sour.toolfarming.item.tools.swords.LevelingSwordItem;
import sour.toolfarming.item.tools.tool_items.ModToolMaterials;

public class ModItems {


    public static final Item LARYN_CRYSTAL = registerItem("laryn_crystal",
            new Item(new FabricItemSettings()));
    public static final Item ZAON_CRYSTAL = registerItem("zaon_crystal",
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

    public static final Item LARYN_AXE = registerItem("laryn_axe",
            new LevelingAxeItem(ModToolMaterials.LARYN, "Laryn Axe", new FabricItemSettings(),
                                LevelingToolsData.LARYN_AXE_DAMAGES, LevelingToolsData.LARYN_AXE_ATTACK_SPEEDS, LevelingToolsData.LARYN_AXE_MINING_SPEEDS,
                                LevelingToolsData.LARYN_TOOL_LEVELS, LevelingToolsData.LARYN_SWORD_EXP
    ));

    public static final Item LARYN_SHOVEL = registerItem("laryn_shovel",
            new LevelingShovelItem(ModToolMaterials.LARYN, "Laryn Shovel", new FabricItemSettings(),
                    LevelingToolsData.LARYN_SHOVEL_DAMAGES, LevelingToolsData.LARYN_SHOVEL_ATTACK_SPEEDS, LevelingToolsData.LARYN_SHOVEL_MINING_SPEEDS,
                    LevelingToolsData.LARYN_TOOL_LEVELS, LevelingToolsData.LARYN_SWORD_EXP
   ));

   public static final Item LARYN_HOE = registerItem("laryn_hoe",
            new LevelingHoeItem(ModToolMaterials.LARYN, "Laryn Hoe", new FabricItemSettings(),
                    LevelingToolsData.LARYN_HOE_DAMAGES, LevelingToolsData.LARYN_HOE_ATTACK_SPEEDS, LevelingToolsData.LARYN_HOE_MINING_SPEEDS,
                    LevelingToolsData.LARYN_TOOL_LEVELS, LevelingToolsData.LARYN_SWORD_EXP
            ));

   //ZAON
    public static final Item ZAON_SWORD = registerItem("zaon_sword",
            new LevelingSwordItem(ModToolMaterials.ZAON,"Zaon Sword",
                    LevelingToolsData.LARYN_SWORD_DAMAGES,  LevelingToolsData.LARYN_SWORD_SPEEDS, LevelingToolsData.LARYN_TOOL_LEVELS, LevelingToolsData.LARYN_SWORD_EXP,
                    new FabricItemSettings()));

    public static final Item ZAON_PICKAXE = registerItem("zaon_pickaxe",
            new LevelingPickaxeItem(ModToolMaterials.ZAON,"Zaon Pickaxe",  new FabricItemSettings(),
                    LevelingToolsData.LARYN_PICKAXE_DAMAGES,  LevelingToolsData.LARYN_PICKAXE_ATTACK_SPEEDS, LevelingToolsData.LARYN_PICKAXE_MINING_SPEEDS,
                    LevelingToolsData.ZAON_TOOL_LEVELS, LevelingToolsData.ZAON_EXP
                   ));

    public static final Item ZAON_AXE = registerItem("zaon_axe",
            new LevelingAxeItem(ModToolMaterials.ZAON,"Zaon Axe", new FabricItemSettings(),
                    LevelingToolsData.LARYN_AXE_DAMAGES,  LevelingToolsData.LARYN_AXE_ATTACK_SPEEDS, LevelingToolsData.LARYN_AXE_MINING_SPEEDS,
                    LevelingToolsData.ZAON_TOOL_LEVELS, LevelingToolsData.ZAON_EXP
                    ));

    public static final Item ZAON_SHOVEL = registerItem("zaon_shovel",
            new LevelingShovelItem(ModToolMaterials.ZAON,"Zaon Shovel", new FabricItemSettings(),
                    LevelingToolsData.LARYN_SHOVEL_DAMAGES,  LevelingToolsData.LARYN_SHOVEL_ATTACK_SPEEDS, LevelingToolsData.LARYN_SHOVEL_MINING_SPEEDS,
                    LevelingToolsData.ZAON_TOOL_LEVELS, LevelingToolsData.ZAON_EXP
                   ));

    public static final Item ZAON_HOE = registerItem("zaon_hoe",
            new LevelingHoeItem(ModToolMaterials.ZAON,"Zaon Hoe", new FabricItemSettings(),
                    LevelingToolsData.LARYN_HOE_DAMAGES,  LevelingToolsData.LARYN_HOE_ATTACK_SPEEDS, LevelingToolsData.LARYN_HOE_MINING_SPEEDS,
                    LevelingToolsData.ZAON_TOOL_LEVELS, LevelingToolsData.ZAON_EXP
                    ));



    //UTILS
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ToolFarming.MOD_ID, name), item);
    }



    public static void addItemsToItemGroup(){
        addToItemGroup(ModItemGroup.LARYN, LARYN_CRYSTAL);
        addToItemGroup(ModItemGroup.LARYN, LARYN_SWORD);
        addToItemGroup(ModItemGroup.LARYN, LARYN_PICKAXE);
        addToItemGroup(ModItemGroup.LARYN, LARYN_AXE);
        addToItemGroup(ModItemGroup.LARYN, LARYN_SHOVEL);
        addToItemGroup(ModItemGroup.LARYN, LARYN_HOE);
        addToItemGroup(ModItemGroup.LARYN, ZAON_CRYSTAL);
        addToItemGroup(ModItemGroup.LARYN, ZAON_SWORD);
        addToItemGroup(ModItemGroup.LARYN, ZAON_HOE);
        addToItemGroup(ModItemGroup.LARYN, ZAON_AXE);
        addToItemGroup(ModItemGroup.LARYN, ZAON_SHOVEL);
        addToItemGroup(ModItemGroup.LARYN, ZAON_PICKAXE);
    }

    public static void addToItemGroup(ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems(){
        ToolFarming.LOGGER.info("Registering Mod Items for" + ToolFarming.MOD_ID);
        addItemsToItemGroup();

    }

}
