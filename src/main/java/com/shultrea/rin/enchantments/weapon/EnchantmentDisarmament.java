package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.mixin.vanilla.IEntityLivingMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentDisarmament extends EnchantmentBase {
	
	public EnchantmentDisarmament(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.disarmament;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.disarmament;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.disarmament, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.disarmament, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.disarmament, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.disarmament, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.disarmament;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingAttackEvent(LivingAttackEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		if(victim.world.isRemote) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			if(attacker.getRNG().nextFloat() < 0.02F * (float)level) {
				if(!victim.getHeldItemMainhand().isEmpty()) {
					if(victim instanceof EntityLiving && attacker.getRNG().nextFloat() >= ((IEntityLivingMixin)victim).getInventoryHandsDropChances()[0]) return;
					ItemStack heldItem = victim.getHeldItemMainhand();
					victim.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
					victim.entityDropItem(heldItem, 0.5F);
				}
				else if(!victim.getHeldItemOffhand().isEmpty()) {
					if(victim instanceof EntityLiving && attacker.getRNG().nextFloat() >= ((IEntityLivingMixin)victim).getInventoryHandsDropChances()[1]) return;
					ItemStack heldItem = victim.getHeldItemOffhand();
					victim.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
					victim.entityDropItem(heldItem, 0.5F);
				}
			}
		}
	}
}