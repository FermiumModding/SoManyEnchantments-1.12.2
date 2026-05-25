package com.shultrea.rin.enchantments.weapon.crits;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EnchantmentLuckMagnification extends EnchantmentBase {
	
	public EnchantmentLuckMagnification(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.luckMagnification;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.luckMagnification;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.luckMagnification, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.luckMagnification, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.luckMagnification, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.luckMagnification, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.luckMagnification;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onCriticalHitEvent(CriticalHitEvent event) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isCriticalHitEventStrong(event)) return;
		EntityLivingBase attacker = event.getEntityLiving();
		if(!(attacker instanceof EntityPlayer)) return;
		if(!(event.getTarget() instanceof EntityLivingBase)) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getCriticalHitEventStack(event, attacker);
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(event.getResult() == Event.Result.DENY) return;
			IAttributeInstance luck = attacker.getEntityAttribute(SharedMonsterAttributes.LUCK);
			float amount = (float)luck.getAttributeValue();
			if(amount <= 0) return;
			
			if(event.getResult() != Event.Result.ALLOW && !event.isVanillaCritical()) {
				if(attacker.getRNG().nextFloat() < Math.min(0.2F, 0.01F * amount * (float)level)) {
					event.setResult(Event.Result.ALLOW);
				}
			}
			if(event.getResult() == Event.Result.ALLOW || (event.isVanillaCritical() && event.getResult() == Event.Result.DEFAULT)) {
				if(attacker.getRNG().nextFloat() < Math.min(0.2F, 0.02F * amount * (float)level)) {
					event.setDamageModifier(event.getDamageModifier() + Math.min(amount * 0.1F * (float)level, 2.0F));
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLootingLevelEvent(LootingLevelEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getDamageSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getDamageSource().getTrueSource();
		if(!(attacker instanceof EntityPlayer)) return;
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, attacker);
		if(level > 0) {
			IAttributeInstance luck = attacker.getEntityAttribute(SharedMonsterAttributes.LUCK);
			event.setLootingLevel(event.getLootingLevel() + (int)(luck.getAttributeValue() * (double)level / 2.0D));
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onPlayerTickEvent(PlayerTickEvent event) {
		if(!this.isEnabled()) return;
		if(event.phase != Phase.END) return;
		EntityPlayer player = event.player;
		if(player == null) return;
		if(player.world.isRemote) return;
		if(player.ticksExisted%9 != 0) return;
		
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, player);
		if(level > 0) {
			player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 10, level - 1, true, false));
		}
	}
}