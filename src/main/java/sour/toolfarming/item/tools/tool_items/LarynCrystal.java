package sour.toolfarming.item.tools.tool_items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class LarynCrystal implements ToolMaterial {

    @Override
    public int getDurability() {
        return 1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 2;
    }

    @Override
    public float getAttackDamage() {
        return 2;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
