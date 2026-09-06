package com.shultrea.rin.enchantments.weapon.ancient;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.EnchantUtil;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;
import java.util.Map;

public class EnchantmentAncientSealedCurses extends EnchantmentBase {
	
	public EnchantmentAncientSealedCurses(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.ancientSealedCurses;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.ancientSealedCurses;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.ancientSealedCurses, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.ancientSealedCurses, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.ancientSealedCurses, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.ancientSealedCurses, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.ancientSealedCurses;
	}

	@Override
	public String getPrefix() {
		return TextFormatting.YELLOW.toString();
	}
	
	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		Iterable<ItemStack> equipmentList = victim.getEquipmentAndArmor();
		List<Enchantment> curses = EnchantUtil.getCurses();
		if(curses.isEmpty()) return;
		for(ItemStack equipment : equipmentList) {
			if(equipment.isEmpty()) continue;
			if(!equipment.isItemEnchantable()) continue;
			if(attacker.getRNG().nextFloat() < 0.125F) {
				Enchantment curse = curses.get(attacker.getRNG().nextInt(curses.size()));
				int curseLevel = 1 + attacker.getRNG().nextInt(curse.getMaxLevel());
				if(!curse.canApply(equipment)) continue;
				
				Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(equipment);
				boolean compatible = true;
				for(Enchantment enchant : enchants.keySet()) {
					if(curse == enchant || !curse.isCompatibleWith(enchant)) {
						compatible = false;
						break;
					}
				}
				if(compatible) {
					equipment.addEnchantment(curse, curseLevel);
				}
			}
		}
	}
}