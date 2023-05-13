package sour.toolfarming.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class LevelingMiningToolItem extends LevelingToolItem{

    ArrayList<Float> miningSpeeds;
    private final TagKey<Block> effectiveBlocks;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final String NAME;
    ArrayList<Float> attackDamages;
    ArrayList<Float> attackSpeeds;
    public LevelingMiningToolItem(ToolMaterial material, String name, Settings settings,ArrayList<Float> attackDamages, ArrayList<Float> attackSpeeds, TagKey<Block> effectiveBlocks, ArrayList<Float> miningSpeeds, int maxLevel, ArrayList<Float> levelsXp) {
        super(material, settings, maxLevel, levelsXp);

        this.miningSpeeds = miningSpeeds;
        this.effectiveBlocks = effectiveBlocks;
        this.attackDamages = attackDamages;
        this.NAME = name;
        this.attackSpeeds = attackSpeeds;

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double)this.attackDamages.get(0) + material.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", (double) this.attackSpeeds.get(0), EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        NbtCompound nbt = stack.getOrCreateNbt();
        return state.isIn(this.effectiveBlocks) ? this.getCurrentMiningSpeed(nbt) : 1.0f;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        if (getCurrentLevel(stack.getOrCreateNbt()) != getMAX_LEVEL())
            increaseXp(stack, state, (PlayerEntity)miner);

        return true;
    }



    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        int i = this.getMaterial().getMiningLevel();
        if (i < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        if (i < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        }
        if (i < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL)) {
            return false;
        }
        return state.isIn(this.effectiveBlocks);
    }

    //XP

    protected void increaseXp(ItemStack stack, BlockState state, PlayerEntity miner) {

        NbtCompound nbt = stack.getOrCreateNbt();

        boolean diamondTool = state.isIn(BlockTags.NEEDS_DIAMOND_TOOL);
        boolean ironTool = state.isIn(BlockTags.NEEDS_IRON_TOOL);
        boolean stoneTool = state.isIn(BlockTags.NEEDS_STONE_TOOL);

        if (stoneTool) {
            setCurrentXp(nbt, getCurrentXp(nbt) + 2f);
        }else if(ironTool){
            setCurrentXp(nbt, getCurrentXp(nbt) + 50f);
        } else if (diamondTool) {
            setCurrentXp(nbt, getCurrentXp(nbt) + 10f);
        }else {
            setCurrentXp(nbt, getCurrentXp(nbt) + 1f);
        }

        //setNbtData(stack);

        //setStackModifiers(stack);

        if (getCurrentXp(nbt) >= getCurrentLvlXp(nbt)) {
            levelUp(miner, nbt, stack);
        }
    }

    //TOOLTIP THINGS

    public void setStackModifiers(ItemStack stack){

        NbtCompound nbt = stack.getOrCreateNbt();

        if (nbt.contains("AttributeModifiers", NbtElement.LIST_TYPE)) {
            nbt.remove("AttributeModifiers");
        }

        stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", getCurrentAttackDamage(nbt), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", getCurrentAttackSpeed(nbt), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);

    }
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound nbt = stack.getOrCreateNbt();
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);

        tooltip.add(Text.literal("----------------").formatted(Formatting.GRAY));

        if (getCurrentLevel(nbt) != getMAX_LEVEL()) {
            nbt.putString("toolfarming.levelTool.xp_progression", "XP: " + this.getCurrentXp(nbt) + "/" + this.getCurrentLvlXp(nbt));
        } else {
            nbt.putString("toolfarming.levelTool.xp_progression", "MAX LVL");
        }

        nbt.putString("toolfarming.levelTool.levelProgression", "Level " + this.getCurrentLevel(nbt) + "/" + this.getMAX_LEVEL());

        assert stack.getNbt() != null;
        String currentXp = stack.getNbt().getString("toolfarming.levelTool.xp_progression");
        String currentLvl = stack.getNbt().getString("toolfarming.levelTool.levelProgression");

        tooltip.add(Text.translatable(currentLvl).formatted(Formatting.DARK_AQUA));
        tooltip.add(Text.literal(currentXp).formatted(Formatting.AQUA));

            double currentSpeedReal = Math.round((getCurrentAttackSpeed(nbt) + 4.0f) * 100)/100.00;
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.modifiers.mainhand").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("toolfarming.currentAttackDamageTooltip", getCurrentAttackDamage(nbt) + 1).formatted(Formatting.DARK_GREEN));
            tooltip.add(Text.translatable("toolfarming.currentSpeedTooltip", currentSpeedReal).formatted(Formatting.DARK_GREEN));
            tooltip.add(Text.translatable("toolfarming.currentMiningSpeedTooltip", getCurrentMiningSpeed(nbt)).formatted(Formatting.DARK_GREEN));
        tooltip.add(Text.literal(""));

    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {

            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    //GETTERS & SETTERS

        //not used anymore now but keeping it just in case
    private void setMiningSpeeds() {
        this.miningSpeeds.replaceAll(ignored -> this.miningSpeeds.get(1) + getMaterial().getMiningSpeedMultiplier());
    }

    public float getCurrentMiningSpeed(NbtCompound nbt) {
        return miningSpeeds.get(getCurrentLevel(nbt) - 1) + getMaterial().getMiningSpeedMultiplier();
    }

    //not used anymore now but keeping it just in case
    private void setAttackDamages() {
        this.attackDamages.replaceAll(ignored -> this.attackDamages.get(1) + getMaterial().getAttackDamage());
    }

    public float getCurrentAttackDamage(NbtCompound nbt) {
        return attackDamages.get(getCurrentLevel(nbt) - 1) + getMaterial().getAttackDamage();
    }

    public float getCurrentAttackSpeed(NbtCompound nbt) {
        return attackSpeeds.get(getCurrentLevel(nbt) - 1);
    }

    @Override
    public String getStringName(ItemStack stack, @Nullable String name) {
        return super.getStringName(stack, this.NAME);
    }
}
