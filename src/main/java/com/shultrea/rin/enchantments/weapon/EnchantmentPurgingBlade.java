package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentPurgingBlade extends EnchantmentBase {

	public EnchantmentPurgingBlade(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.purgingBlade;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.purgingBlade;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.purgingBlade, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.purgingBlade, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return ConfigProvider.canItemApply(this, ModConfig.canApply.purgingBlade, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.purgingBlade, stack) || super.canApply(stack);
	}

	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.purgingBlade;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		if(attacker.world.isRemote) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;

		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level <= 0) return;
		if(CompatUtil.isRLCombatLoaded() && attacker.getRNG().nextFloat() > RLCombatCompat.getAttackEntityFromStrength()) return;
		if(attacker.getRNG().nextFloat() < 0.1F + 0.06F * (float)level) {
			PotionEffect[] arr = victim.getActivePotionEffects().toArray(new PotionEffect[0]);
			if(arr.length == 0) return;
			PotionEffect effect = arr[attacker.getRNG().nextInt(arr.length)];

			event.setAmount(event.getAmount() * Math.min(2.0F, (1.0F + 0.02F * (float)(1 + effect.getAmplifier()) * (float)level)));
			victim.removePotionEffect(effect.getPotion());
		}
	}
}