package net.isabel.aeroscapes.mixin;

import net.isabel.aeroscapes.registry.AeroscapesItems;
import net.isabel.aeroscapes.registry.AeroscapesTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class AeroscapesAeroliteTwigItemEntityFloat {
    @Shadow public abstract ItemStack getStack();

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void SetAeroliteTwigFloat(CallbackInfo ci) {
        ItemStack s = this.getStack();
        if(s.isIn(AeroscapesTags.Items.IS_FLOATING_ITEM)) {
            ((Entity)(Object)this).setNoGravity(true);
        }
    }
}
