package sour.toolfarming.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sour.toolfarming.item.tools.LevelingToolItem;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Shadow public abstract NbtCompound getOrCreateNbt();

    @Shadow private @Nullable NbtCompound nbt;
    private static final String EXPERIENCE_KEY = "toolfarming.levelTool.lvl_xp_current";

    @Inject( method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("RETURN"))
        public void setUpXpNbt(ItemConvertible item, int count, CallbackInfo ci){
            if (this.getItem().getClass() == LevelingToolItem.class){

                NbtCompound nbt = this.getOrCreateNbt();

                LevelingToolItem newItem = (LevelingToolItem) item;
               // nbt.putInt("toolfarming.levelTool.lvl_current", 1);
                //nbt.putFloat(EXPERIENCE_KEY, newItem.getLevelsXp().get(1));
                //nbt.putFloat("toolfarming.levelTool.xp_current", 0);

               // nbt.putString("toolfarming.levelTool.xp_progression", "XP: " + nbt.getFloat("toolfarming.levelTool.xp_current") + "/" + nbt.getFloat(EXPERIENCE_KEY));
            }
    }

}
