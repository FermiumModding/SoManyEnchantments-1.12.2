package com.shultrea.rin.enchantments.weapon.crits;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.SoundRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCriticalStrike extends EnchantmentBase {
	
	public EnchantmentCriticalStrike(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.criticalStrike;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.criticalStrike;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.criticalStrike, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.criticalStrike, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.criticalStrike, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.criticalStrike, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.criticalStrike;
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onCriticalHitEvent(CriticalHitEvent event) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isCriticalHitEventStrong(event)) return;
		EntityLivingBase attacker = event.getEntityLiving();
		if(attacker == null) return;
		if(!(event.getTarget() instanceof EntityLivingBase)) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getCriticalHitEventStack(event, attacker);
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(event.getResult() == Event.Result.DENY) return;
			if(event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT)) {
				NBTTagCompound compound = stack.getTagCompound();
				if(compound == null) compound = new NBTTagCompound();
				int counter = 1 + compound.getInteger("CriticalStrikeFailCount");
				int maxChance = 1000 - 50 * level;
				int chance = 32 * counter;
				
				if(level >=20 || attacker.getRNG().nextInt(maxChance) >= chance) {
					compound.setInteger("CriticalStrikeFailCount", counter);
					attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundRegistry.CRITICAL_STRIKE_FAIL, SoundCategory.PLAYERS, 0.8F, 1.0F + (float)(2.0F * chance / maxChance));
					event.setDamageModifier(Math.max(1.5F, event.getDamageModifier()));
				}
				else {
					compound.setInteger("CriticalStrikeFailCount", 0);
					float crit = 1.0F + 0.5F * (float)level + 0.5F * (float)level * attacker.getRNG().nextFloat();
					attacker.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundRegistry.CRITICAL_STRIKE, SoundCategory.PLAYERS, 0.8F, 1.0F / (1.2F + 0.4F * attacker.getRNG().nextFloat()) * 1.6F);
					event.setDamageModifier(event.getDamageModifier() + crit);
				}
				event.setResult(Event.Result.ALLOW);
				stack.setTagCompound(compound);
			}
		}
	}
}
