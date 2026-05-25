package com.shultrea.rin.enchantments.weapon.conditionaldamage;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentMortalitas extends EnchantmentBase {
	
	public EnchantmentMortalitas(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.mortalitas;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.mortalitas;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.mortalitas, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.mortalitas, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.mortalitas, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.mortalitas, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.mortalitas;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			NBTTagCompound compound = stack.getTagCompound();
			if(compound == null) return;
			float damage = compound.getFloat("MortalitasDamage");
			float strengthMulti = CompatUtil.isRLCombatLoaded() ? RLCombatCompat.getAttackEntityFromStrength() : 1.0F;
			event.setAmount(event.getAmount() + damage * strengthMulti);
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		if(!this.isEnabled()) return;
		EntityLivingBase entity = event.getEntityLiving();
		if(entity == null) return;
		if(entity.world.isRemote) return;
		if(entity.ticksExisted%20 != 0) return;
		ItemStack stack = entity.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		if(!stack.hasTagCompound()) return;
		NBTTagCompound compound = stack.getTagCompound();
		if(!compound.hasKey("MortalitasDamage")) return;
		float damage = compound.getFloat("MortalitasDamage");
		//reduces by 1 kill (0.05) per minute at max ench lvl, faster on lower lvl
		damage -= 0.05F/60F;

		if(damage <= 0) compound.removeTag("MortalitasDamage");
		else compound.setFloat("MortalitasDamage", damage);
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			NBTTagCompound compound = stack.getTagCompound();
			if(compound == null) compound = new NBTTagCompound();
			float amount = compound.getFloat("MortalitasDamage");
			amount += (0.05F / (float)this.getMaxLevel()) * (float)level;
			amount = MathHelper.clamp(amount, 0, level);
			compound.setFloat("MortalitasDamage", amount);
			stack.setTagCompound(compound);
		}
	}
}