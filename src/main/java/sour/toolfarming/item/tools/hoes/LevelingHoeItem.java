package sour.toolfarming.item.tools.hoes;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import sour.toolfarming.item.tools.LevelingMiningToolItem;

import java.util.ArrayList;

public class LevelingHoeItem extends LevelingMiningToolItem {
    public LevelingHoeItem(ToolMaterial material, String name, Settings settings, ArrayList<Float> attackDamages, ArrayList<Float> attackSpeeds, ArrayList<Float> miningSpeeds, int maxLevel, ArrayList<Float> levelsXp) {
        super(material, name, settings, attackDamages, attackSpeeds, BlockTags.HOE_MINEABLE, miningSpeeds, maxLevel, levelsXp);
    }
}
