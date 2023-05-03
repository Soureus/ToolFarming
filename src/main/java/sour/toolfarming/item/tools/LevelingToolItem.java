package sour.toolfarming.item.tools;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.EventListener;

public class LevelingToolItem extends ToolItem {

    private final int MAX_LEVEL;
    private int currentLevel;
    private ArrayList<Float> levelsXp;
    private float currentXp;
    NbtCompound nbt;

    private final ToolMaterial MATERIAL;

    public LevelingToolItem(ToolMaterial material, Settings settings, int maxLevel, ArrayList<Float> levelsXp) {
        super(material, settings);

        this.MATERIAL = material;
        this.MAX_LEVEL = maxLevel;
        this.levelsXp = levelsXp;

        currentLevel = 1;
        currentXp = 0;

        nbt = this.getDefaultStack().getOrCreateNbt();


    }

    public void levelUp(PlayerEntity player, NbtCompound nbt, ItemStack stack){
        this.setCurrentLevel(nbt, getCurrentLevel(nbt));
        this.setCurrentXp(nbt, 0);
        player.sendMessage(Text.translatable(getStringName(stack, null) + " Has leveled up to level " + getCurrentLevel(nbt)));
    }

    //GETTERS & SETTERS




    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public ToolMaterial getMaterial() {
        return MATERIAL;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    //XP


    public ArrayList<Float> getLevelsXp() {
        return levelsXp;
    }

    public void setLevelXp(ArrayList<Float> levelXp) {
        this.levelsXp = levelXp;
    }

    public float getCurrentXp(NbtCompound nbt) {
        return nbt.getFloat("toolfarming.levelTool.xp_current");
    }

    public float getCurrentLvlXp(NbtCompound nbt){
        return levelsXp.get(getCurrentLevel(nbt) - 1);
    }

    public void setCurrentXp(NbtCompound nbt, float currentXp) {

        nbt.putFloat("toolfarming.levelTool.xp_current", currentXp);

    }

    //LEVELS

    public int getMAX_LEVEL() {
        return MAX_LEVEL;
    }

    public int getCurrentLevel(NbtCompound nbt) {
        return nbt.getInt("toolfarming.levelTool.lvl_current") + 1;
    }

    public void setCurrentLevel(NbtCompound nbt, int currentLevel) {
        nbt.putInt("toolfarming.levelTool.lvl_current", currentLevel);
    }

    public String getStringName(ItemStack stack, @Nullable String name) {
//        if (stack.hasCustomName()){
//            return stack.getName().toString();
//        }else {
            return name;
        //}
    }
}
