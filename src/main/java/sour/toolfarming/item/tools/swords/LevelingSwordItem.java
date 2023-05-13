package sour.toolfarming.item.tools.swords;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sour.toolfarming.item.tools.LevelingToolItem;

import java.util.ArrayList;
import java.util.List;

public class LevelingSwordItem extends LevelingToolItem {

    ArrayList<Float> attackDamages;
    ArrayList<Float> attackSpeeds;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final String NAME;
    ToolMaterial material;

    public LevelingSwordItem(ToolMaterial material, String name, ArrayList<Float> attackDamages, ArrayList<Float> attackSpeeds, int maxLevel, ArrayList<Float> levelsXp, Item.Settings settings) {
        super(material, settings, maxLevel, levelsXp);
        this.attackDamages = attackDamages;
        this.attackSpeeds = attackSpeeds;
        this.material = material;
        this.NAME = name;

        //setAttackDamages();

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) this.attackDamages.get(0) + material.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double) attackSpeeds.get(0), EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        if (state.isOf(Blocks.COBWEB)) {
            return 15.0f;
        }
        Material material = state.getMaterial();
        if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || state.isIn(BlockTags.LEAVES) || material == Material.GOURD) {
            return 1.5f;
        }
        return 1.0f;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

        if (target.getHealth() == 0) {
            increaseXp(stack, attacker, target);
            setStackModifiers(stack);
        }

        return true;
    }


    protected void increaseXp(ItemStack stack, LivingEntity attacker, LivingEntity target) {

        NbtCompound nbt = stack.getOrCreateNbt();

        SpawnGroup spawnGroup = target.getType().getSpawnGroup();

        PlayerEntity player = (PlayerEntity) attacker;

        if (spawnGroup != SpawnGroup.MISC) {
            if (spawnGroup.equals(SpawnGroup.AMBIENT) || spawnGroup.equals(SpawnGroup.WATER_AMBIENT) || spawnGroup.equals(SpawnGroup.AXOLOTLS) ||
                    spawnGroup.equals(SpawnGroup.UNDERGROUND_WATER_CREATURE) || target.getWidth() < 0.5){
                setCurrentXp(nbt, getCurrentXp(nbt) + 0.5f);
            }
            else if (spawnGroup.isPeaceful()) {
                setCurrentXp(nbt, getCurrentXp(nbt) + 1f);
            }else if (target.getType() == EntityType.GHAST || target.getType() == EntityType.ENDERMAN || target.getType() == EntityType.BLAZE ||
                    target.getType() == EntityType.ZOMBIE_VILLAGER || target.getType() == EntityType.WITCH || target.getType() == EntityType.WITHER_SKELETON ||
                    target.getType() == EntityType.ZOMBIE_VILLAGER) {
                setCurrentXp(nbt, getCurrentXp(nbt) + 2f);
            }else if (target.getType() == EntityType.ENDER_DRAGON || target.getType() == EntityType.WARDEN){
                setCurrentXp(nbt, getCurrentXp(nbt) + 50f);
            }else if(target.getType() == EntityType.WITHER || target.getType() == EntityType.ELDER_GUARDIAN){
                setCurrentXp(nbt, getCurrentXp(nbt) + 25f);
            }else{
                setCurrentXp(nbt, getCurrentXp(nbt) + 1f);
            }
            }

        setNbtData(stack);

        if (getCurrentXp(nbt) >= getCurrentLvlXp(nbt)) {
            levelUp(player, nbt, stack);
        }

    }

    @Override
    public void levelUp(PlayerEntity player, NbtCompound nbt, ItemStack stack) {
        super.levelUp(player, nbt, stack);
       setStackModifiers(stack);
    }

    public void setStackModifiers(ItemStack stack){

        NbtCompound nbt = stack.getOrCreateNbt();

        if (nbt.contains("AttributeModifiers", NbtElement.LIST_TYPE)) {
            nbt.remove("AttributeModifiers");
        }

        stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", getCurrentAttackDamage(nbt), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", getCurrentAttackSpeed(nbt), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        stack.addHideFlag(ItemStack.TooltipSection.MODIFIERS);

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound nbt = stack.getOrCreateNbt();

        tooltip.add(Text.literal(""));

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

        if (getCurrentLevel(nbt) != 1 || getCurrentXp(nbt) != 0){
            float  currentAttackReal = getCurrentAttackDamage(nbt) + 1;
            float currentSpeedReal = getCurrentAttackSpeed(nbt) + 4;
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.modifiers.mainhand").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("toolfarming.currentAttackDamageTooltip", currentAttackReal).formatted(Formatting.DARK_GREEN));
            tooltip.add(Text.translatable("toolfarming.currentSpeedTooltip", currentSpeedReal).formatted(Formatting.DARK_GREEN));
            tooltip.add(Text.literal(""));
        }

    }



    //


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0f) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {

            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
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

