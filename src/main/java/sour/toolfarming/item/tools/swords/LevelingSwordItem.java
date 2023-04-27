package sour.toolfarming.item.tools.swords;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
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
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
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

    public LevelingSwordItem(ToolMaterial material, ArrayList<Float> attackDamages, ArrayList<Float> attackSpeeds, int maxLevel, ArrayList<Float> levelsXp, Item.Settings settings) {
        super(material, settings, maxLevel, levelsXp);
        this.attackDamages = attackDamages;
        this.attackSpeeds = attackSpeeds;

        setAttackDamages();

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamages.get(0), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeeds.get(0), EntityAttributeModifier.Operation.ADDITION));
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

        increaseXp(stack, attacker, target);

        return true;
    }


    protected void increaseXp(ItemStack stack, LivingEntity attacker, LivingEntity target){

        NbtCompound nbt = stack.getOrCreateNbt();

        SpawnGroup spawnGroup = target.getType().getSpawnGroup();

        PlayerEntity player = (PlayerEntity) attacker;

        if (spawnGroup != SpawnGroup.MISC) {
            if (spawnGroup.isPeaceful()){
                setCurrentXp(nbt, getCurrentXp(nbt)+0.5f);
            }else {
                setCurrentXp(nbt, getCurrentXp(nbt)+50f);
            }
        }

        setNbtData(stack);

        if (getCurrentXp(nbt) == getCurrentLvlXp(nbt)){
            levelUp(nbt);
            player.sendMessage(Text.literal(stack.getName().getContent() + "Has leveled up to level " + getCurrentLevel(nbt)));
        }

    }

    protected void setNbtData(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();

        assert nbt != null;
        if(getCurrentLevel(nbt) != getMAX_LEVEL()) {
            nbt.putFloat("toolfarming.levelTool.lvl_xp_current", this.getCurrentLvlXp(nbt));
        }


    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound nbt = stack.getOrCreateNbt();

            if(getCurrentLevel(nbt) != getMAX_LEVEL()) {
                nbt.putString("toolfarming.levelTool.xp_progression", "XP: " + this.getCurrentXp(nbt) + "/" + this.getCurrentLvlXp(nbt));
            }else {
                nbt.putString("toolfarming.levelTool.xp_progression", "MAX LVL");
            }

            nbt.putString("toolfarming.levelTool.levelProgression", "Level " + this.getCurrentLevel(nbt) + "/" + this.getMAX_LEVEL());

            assert stack.getNbt() != null;
            String currentXp = stack.getNbt().getString("toolfarming.levelTool.xp_progression");
            String currentLvl = stack.getNbt().getString("toolfarming.levelTool.levelProgression");
            tooltip.add(Text.translatable(currentLvl));
            tooltip.add(Text.literal(currentXp));

    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0f) {
            stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    private void setAttackDamages(){
        attackDamages.forEach((n) -> n += getMaterial().getAttackDamage());
    }

    public float getCurrentAttackDamage(NbtCompound nbt){
        return attackDamages.get(getCurrentLevel(nbt));
    }


}

