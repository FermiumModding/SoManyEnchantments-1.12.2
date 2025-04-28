package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.LycanitesMobsCompat;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentCryogenic extends EnchantmentBase {
	
	public EnchantmentCryogenic(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.cryogenic;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.cryogenic;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.cryogenic, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.cryogenic, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApply.cryogenic, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(ModConfig.canApplyAnvil.cryogenic, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.cryogenic;
	}

	public static int getLevelValue(EntityLivingBase entity) {
		if(!EnchantmentRegistry.cryogenic.isEnabled()) return 0;
		if(entity == null) return 0;
		ItemStack stack = entity.getHeldItemMainhand();
		if(CompatUtil.isRLCombatLoaded()) stack = RLCombatCompat.getFireAspectStack(entity);
		return EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.cryogenic, stack);
	}

	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if(!this.isEnabled()) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isOnEntityDamagedAltStrong()) return;
		if(attacker == null) return;
		if(!(target instanceof EntityLivingBase)) return;
		EntityLivingBase victim = (EntityLivingBase)target;
		if(weapon.isEmpty()) return;
		
		if(!attacker.world.isRemote) {
			if(attacker.getRNG().nextFloat() <= 0.2F * (float)level) {
				PotionEffect slowness = victim.getActivePotionEffect(MobEffects.SLOWNESS);
				PotionEffect fatigue = victim.getActivePotionEffect(MobEffects.MINING_FATIGUE);
				
				int slownessAmp = -1;
				int fatigueAmp = -1;
				if(slowness != null) slownessAmp = slowness.getAmplifier();
				if(fatigue != null) fatigueAmp = fatigue.getAmplifier();
				slownessAmp = Math.min(3, slownessAmp + 1);
				fatigueAmp = Math.min(3, fatigueAmp + 1);
				
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40 + (10 * level), slownessAmp));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 40 + (10 * level), fatigueAmp));
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if(!this.isEnabled()) return;
		if(!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if(CompatUtil.isRLCombatLoaded() && !RLCombatCompat.isAttackEntityFromStrong()) return;
		if(event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase)event.getSource().getTrueSource();
		if(attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if(victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if(stack.isEmpty()) return;
		
		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if(level > 0) {
			PotionEffect slowness = victim.getActivePotionEffect(MobEffects.SLOWNESS);
			PotionEffect fatigue = victim.getActivePotionEffect(MobEffects.MINING_FATIGUE);
			
			if(slowness != null && slowness.getAmplifier() > 2 && fatigue != null && fatigue.getAmplifier() > 2) {
				victim.extinguish();
				if(attacker.getRNG().nextFloat() <= 0.2F * (float)level) {
					event.setAmount(event.getAmount() * (1.0F + 0.2F * (float)level));
					
					BlockPos pos = new BlockPos(victim.posX, victim.posY, victim.posZ);
					for(BlockPos.MutableBlockPos mutablePos1 : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 2, 1))) {
						if(attacker.world.getBlockState(mutablePos1).getMaterial() == Material.AIR) {
							Block freezeBlock = null;
							if(CompatUtil.isLycanitesMobsLoaded()) freezeBlock = LycanitesMobsCompat.getFrostCloud();
							if(freezeBlock == null) freezeBlock = Blocks.FROSTED_ICE;
							attacker.world.setBlockState(mutablePos1, freezeBlock.getDefaultState());
							if(freezeBlock == Blocks.FROSTED_ICE) {
								attacker.world.scheduleUpdate(mutablePos1.toImmutable(), Blocks.FROSTED_ICE, MathHelper.getInt(attacker.getRNG(), 60, 120));
							}
						}
					}
					attacker.world.playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.8f, -1f);
				}
			}
		}
	}
}