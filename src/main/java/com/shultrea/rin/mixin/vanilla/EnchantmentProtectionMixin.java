package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.shultrea.rin.enchantments.armor.protection.EnchantmentAdvancedBlastProtection;
import com.shultrea.rin.enchantments.armor.protection.EnchantmentAdvancedFireProtection;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentProtection.class)
public abstract class EnchantmentProtectionMixin {

	//fix blast protection flooring reduction making it not effective
	@ModifyExpressionValue(
			method = "getBlastDamageReduction",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I")
	)
	private static int soManyEnchantments_vanillaEnchantmentProtection_getBlastDamageReduction(int original, @Local(argsOnly = true) LocalDoubleRef damage, @Local int i){
		damage.set(damage.get() * Math.max(0., 1.0 - (double)i * 0.15)); //reduce by 15% per lvl (2 lvls per adv blast prot), max reduction to 0%
		return 0; //dont reduce using original truncated calc
	}

	/**
	 * Handling for Advanced Blast Protection enchant
	 */
	@ModifyExpressionValue(
			method = "getBlastDamageReduction",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getMaxEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/EntityLivingBase;)I")
	)
	private static int soManyEnchantments_vanillaEnchantmentProtection_getBlastDamageReduction(int original, EntityLivingBase entity) {
		return original + 2 * EnchantmentAdvancedBlastProtection.getValue(entity);
	}
	
	/**
	 * Handling for Advanced Fire Protection enchant
	 */
	@ModifyExpressionValue(
			method = "getFireTimeForEntity",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getMaxEnchantmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/EntityLivingBase;)I")
	)
	private static int soManyEnchantments_vanillaEnchantmentProtection_getFireTimeForEntity(int original, EntityLivingBase entity) {
		return original + 2 * EnchantmentAdvancedFireProtection.getValue(entity);
	}
}