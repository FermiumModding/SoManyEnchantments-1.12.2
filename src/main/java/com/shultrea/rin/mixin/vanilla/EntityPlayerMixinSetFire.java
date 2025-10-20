package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixinSetFire {
    @ModifyArg(
            method = "attackTargetEntityWithCurrentItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFire(I)V", ordinal = 0)
    )
    private int soManyEnchantments_vanillaEntityPlayer_attackTargetEntityWithCurrentItem(int seconds, @Local(name = "j") int fireAspectLevel){
        return fireAspectLevel * 4;
    }
}
