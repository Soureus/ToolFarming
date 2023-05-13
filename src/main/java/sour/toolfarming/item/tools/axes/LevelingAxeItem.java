package sour.toolfarming.item.tools.axes;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import sour.toolfarming.item.tools.LevelingMiningToolItem;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class LevelingAxeItem extends LevelingMiningToolItem {

    protected static final Map<Block, Block> STRIPPED_BLOCKS = new ImmutableMap.Builder<Block, Block>().put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD).put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE).put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD).put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG).put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK).build();

    public LevelingAxeItem(ToolMaterial material, String name, Settings settings, ArrayList<Float> attackDamages, ArrayList<Float> attackSpeeds, ArrayList<Float> miningSpeeds, int maxLevel, ArrayList<Float> levelsXp) {
        super(material, name, settings, attackDamages, attackSpeeds, BlockTags.AXE_MINEABLE, miningSpeeds, maxLevel, levelsXp);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);

        if (target.getHealth() == 0)
             increaseXpHit(stack, attacker, target);

        return true;
    }

    protected void increaseXpHit(ItemStack stack, LivingEntity attacker, LivingEntity target) {

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
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState = world.getBlockState(blockPos);
        Optional<BlockState> optional = this.getStrippedState(blockState);
        Optional<BlockState> optional2 = Oxidizable.getDecreasedOxidationState(blockState);
        Optional<BlockState> optional3 = Optional.ofNullable((Block)HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().get(blockState.getBlock())).map(block -> block.getStateWithProperties(blockState));
        ItemStack itemStack = context.getStack();
        Optional<BlockState> optional4 = Optional.empty();
        if (optional.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            optional4 = optional;
        } else if (optional2.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0);
            optional4 = optional2;
        } else if (optional3.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0);
            optional4 = optional3;
        }
        if (optional4.isPresent()) {
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
            }
            world.setBlockState(blockPos, (BlockState)optional4.get(), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, (BlockState)optional4.get()));
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map(block -> (BlockState)block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
    }
}
