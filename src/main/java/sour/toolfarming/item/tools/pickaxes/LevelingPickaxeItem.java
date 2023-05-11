package sour.toolfarming.item.tools.pickaxes;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import sour.toolfarming.item.tools.LevelingMiningToolItem;

import java.util.ArrayList;

public class LevelingPickaxeItem extends LevelingMiningToolItem {
    public LevelingPickaxeItem(ToolMaterial material, String name, Settings settings, ArrayList<Float> attackDamages, ArrayList<Float> attackSpeeds, ArrayList<Float> miningSpeeds, int maxLevel, ArrayList<Float> levelsXp) {
        super(material, name, settings, attackDamages, attackSpeeds, BlockTags.PICKAXE_MINEABLE, miningSpeeds, maxLevel, levelsXp);
    }
}
