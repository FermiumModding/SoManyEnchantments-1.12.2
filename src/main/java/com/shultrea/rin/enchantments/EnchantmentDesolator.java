package com.shultrea.rin.enchantments;

import com.shultrea.rin.Interfaces.IPotionDebuffer;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.Smc_030;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDesolator extends EnchantmentBase implements IPotionDebuffer {
	
	public EnchantmentDesolator(String name, Rarity rarity, EnumEnchantmentType type) {
		super(name, rarity, type, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.desolator;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.desolator;
	}
	
	//TODO
	@Override
	public int getMinEnchantability(int par1) {
		return 17 + 8 * (par1 - 1);
	}
	
	//TODO
	@Override
	public int getMaxEnchantability(int par1) {
		return super.getMinEnchantability(par1) + 30;
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.desolator;
	}
	
	//TODO
	@Override
	public boolean canApplyTogether(Enchantment fTest) {
		return super.canApplyTogether(fTest) && !(fTest instanceof IPotionDebuffer);
	}
	
	//TODO
	@SubscribeEvent(priority = EventPriority.LOW)
	public void HandleEnchant(LivingHurtEvent fEvent) {
		if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob") return;
		if(!(fEvent.getSource().getTrueSource() instanceof EntityLivingBase)) return;
		EntityLivingBase attacker = (EntityLivingBase)fEvent.getSource().getTrueSource();
		ItemStack weapon = attacker.getHeldItemMainhand();
		if(weapon == null) return;
		int level = EnchantmentHelper.getEnchantmentLevel(Smc_030.Desolator, weapon);
		if(EnchantmentHelper.getEnchantmentLevel(Smc_030.Desolator, weapon) <= 0) return;
		if(this.isOffensivePetDisallowed(fEvent.getSource().getImmediateSource(), fEvent.getSource().getTrueSource()))
			return;
		if(fEvent.getEntity().world.rand.nextInt(100) < 8 * level) {
			if(level >= 3) {
				fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 140, level - 3));
			}
			fEvent.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 140, -level));
			if(fEvent.getEntityLiving().getActivePotionEffect(MobEffects.RESISTANCE) == null) {
				fEvent.setAmount(fEvent.getAmount() * 1.2f * level);
			}
		}
	}
}