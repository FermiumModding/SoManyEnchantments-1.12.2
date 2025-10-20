package com.shultrea.rin.mixin.bettercombat;

import bettercombat.mod.util.Helpers;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Helpers.class)
public abstract class HelpersMixinSetFire {
    @ModifyArg(
            method = "attackTargetEntityItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFire(I)V", ordinal = 0)
    )
    private static int soManyEnchantments_rlCombatHelpers_attackTargetEntityItem(int seconds, @Local(name = "fireAspect") int fireAspectLevel){
        return fireAspectLevel * 4;
    }
}
