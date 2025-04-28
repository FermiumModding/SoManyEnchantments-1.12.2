package com.shultrea.rin.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.enchantments.curses.EnchantmentDragging;
import com.shultrea.rin.enchantments.curses.EnchantmentExtinguish;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLuckOfTheSea;
import com.shultrea.rin.enchantments.fishing.EnchantmentAdvancedLure;
import com.shultrea.rin.enchantments.tool.EnchantmentAdvancedEfficiency;
import com.shultrea.rin.enchantments.weapon.EnchantmentAdvancedKnockback;
import com.shultrea.rin.enchantments.weapon.EnchantmentAdvancedLooting;
import com.shultrea.rin.enchantments.weapon.EnchantmentFieryEdge;
import com.shultrea.rin.enchantments.weapon.EnchantmentTierFA;
import com.shultrea.rin.enchantments.weapon.damage.EnchantmentWaterAspect;
import com.shultrea.rin.enchantments.weapon.potiondebuffer.EnchantmentCryogenic;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EnchantmentHelper.class, priority = 2000)
public abstract class EnchantmentHelperMixin {
	
	/**
	 * Handling for Ancient Sword Mastery
	 */
	@ModifyReturnValue(
			method = "getModifierForCreature",
			at = @At("RETURN")
	)
	private static float soManyEnchantments_vanillaEnchantmentHelper_getModifierForCreature(float original, ItemStack stack, EnumCreatureAttribute creatureAttribute) {
		if(stack.isEmpty() || original < 0.0F || !EnchantmentRegistry.ancientSwordMastery.isEnabled()) return original;
		int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.ancientSwordMastery, stack);
		if(level > 0) {
			return original * (1.0F + 0.25F * (float)level);
		}
		return original;
	}
	
	/**
	 * Handling for onEntityDamagedAlt
	 */
	@Inject(
			method = "applyArthropodEnchantments",
			at = @At(value = "HEAD")
	)
	private static void soManyEnchantments_vanillaEnchantmentHelper_applyArthropodEnchantments(EntityLivingBase user, Entity target, CallbackInfo ci) {
		if(user != null) {
			ItemStack stack = user.getHeldItemMainhand();
			if(CompatUtil.isRLCombatLoaded()) {
				stack = RLCombatCompat.getArthropodStack(user);
			}
			if(!stack.isEmpty()) {
				NBTTagList nbttaglist = stack.getEnchantmentTagList();
				for(int i = 0; i < nbttaglist.tagCount(); ++i) {
					int enchID = nbttaglist.getCompoundTagAt(i).getShort("id");
					int enchLvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");
					Enchantment ench = Enchantment.getEnchantmentByID(enchID);
					if(ench != null) {
						if(ench instanceof EnchantmentBase) {
							EnchantmentBase enchant = (EnchantmentBase)ench;
							if(enchant.isEnabled()) enchant.onEntityDamagedAlt(user, target, stack, enchLvl);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Handling for Advanced Knockback and Dragging enchant
	 */
	@ModifyReturnValue(
			method = "getKnockbackModifier",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getKnockbackModifier(int original, EntityLivingBase entity) {
		boolean hasDragging = EnchantmentDragging.getLevelValue(entity) > 0;
		return hasDragging ? 0 : original + EnchantmentAdvancedKnockback.getLevelValue(entity);
	}
	
	/**
	 * Handling for Fire Aspect Tier, Extinguish and Fiery Edge enchants
	 */
	@ModifyReturnValue(
			method = "getFireAspectModifier",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getFireAspectModifier(int original, EntityLivingBase entity) {
		boolean hasExtinguish = EnchantmentExtinguish.getLevelValue(entity)>0
									|| EnchantmentWaterAspect.getLevelValue(entity)>0
									|| EnchantmentCryogenic.getLevelValue(entity)>0;
		return hasExtinguish ? 0 : original + EnchantmentTierFA.getLevelValue(entity) + EnchantmentFieryEdge.getLevelValue(entity);
	}
	
	/**
	 * Handling for Advanced Efficiency enchant
	 */
	@ModifyReturnValue(
			method = "getEfficiencyModifier",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getEfficiencyModifier(int original, EntityLivingBase entity) {
		return original + EnchantmentAdvancedEfficiency.getLevelValue(entity);
	}
	
	/**
	 * Handling for Advanced Luck of the Sea enchant
	 */
	@ModifyReturnValue(
			method = "getFishingLuckBonus",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getFishingLuckBonus(int original, ItemStack stack) {
		return original + EnchantmentAdvancedLuckOfTheSea.getLevelValue(stack);
	}
	
	/**
	 * Handling for Advanced Lure enchant
	 */
	@ModifyReturnValue(
			method = "getFishingSpeedBonus",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getFishingSpeedBonus(int original, ItemStack stack) {
		return original + EnchantmentAdvancedLure.getLevelValue(stack);
	}
	
	/**
	 * Handling for Advanced Looting enchant
	 */
	@ModifyReturnValue(
			method = "getLootingModifier",
			at = @At("RETURN")
	)
	private static int soManyEnchantments_vanillaEnchantmentHelper_getLootingModifier(int original, EntityLivingBase entity) {
		return original + EnchantmentAdvancedLooting.getLevelValue(entity);
	}
}