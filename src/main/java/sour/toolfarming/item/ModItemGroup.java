package sour.toolfarming.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sour.toolfarming.ToolFarming;

public class ModItemGroup {

    public static ItemGroup CITRINE;
    public static ItemGroup LARYN;

    public static void registerItemGroups(){

        LARYN = FabricItemGroup.builder(new Identifier(ToolFarming.MOD_ID, "laryn"))
                .displayName(Text.literal("Tool Farming"))
                .icon(() -> new ItemStack(ModItems.LARYN_CRYSTAL)).build();

    }

}
