package com.shultrea.rin.enchantments;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.ScalingHealthCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdept extends EnchantmentBase {
	
	public EnchantmentAdept(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.adept;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.adept;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.adept, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.adept, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.adept, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.adept, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.adept;
	}
	
	@SubscribeEvent
	public void onLivingExperienceDropEvent(LivingExperienceDropEvent event) {
		if(!this.isEnabled()) return;
		EntityPlayer player = event.getAttackingPlayer();
		if(player == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(event.getDroppedExperience() <= 0) return;

		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, player);
		if(level > 0) {
			boolean isBoss = !victim.isNonBoss() || (CompatUtil.isScalingHealthLoaded() && ScalingHealthCompat.isEntityBlight(victim));
			float multi = isBoss ? 0.5F : 0.15F;
			int addedXp = Math.max((level + 1) / 2, (int) (event.getDroppedExperience() * level * multi));
			event.setDroppedExperience(event.getDroppedExperience() + addedXp);
		}
	}
}