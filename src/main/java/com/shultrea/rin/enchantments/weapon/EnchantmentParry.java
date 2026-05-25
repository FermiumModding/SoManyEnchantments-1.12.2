package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentParry extends EnchantmentBase {
	
	public EnchantmentParry(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.parry;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.parry;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.parry, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.parry, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.parry, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.parry, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.parry;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(event.getSource().isProjectile()) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim.world.isRemote) return;
		if(!(event.getSource().getImmediateSource() instanceof EntityLivingBase)) return;
		//Only affect actual attacks
		if(!"player".equals(event.getSource().damageType) && !"mob".equals(event.getSource().damageType)) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getImmediateSource();
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, victim);
		if(level > 0) {
			if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.trueStrike, attacker.getHeldItemMainhand()) > 0) return;
			//Slightly boost chance of proc when combined with Counter Attack
			int levelCounter = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.counterAttack, victim);
			
			if(victim.getRNG().nextFloat() < 0.05F + (0.05F * (float)level) + (0.01F * (float)levelCounter)) {
				if(!victim.world.isRemote) {
					EnchantUtil.knockBackIgnoreKBRes(attacker, 0.3F + 0.15F * (float)level, victim.posX - attacker.posX, victim.posZ - attacker.posZ);
				}
				victim.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.3F, 3.0F);
				event.setCanceled(true);
				if((float)victim.hurtResistantTime <= (float)victim.maxHurtResistantTime / 2.0F) {
					victim.hurtResistantTime = victim.maxHurtResistantTime + (5 * (level - 1));
				}
			}
		}
	}
}