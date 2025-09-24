package com.shultrea.rin.enchantments.weapon.potiondebuffer;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.BlockRegistry;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
		return ConfigProvider.canItemApply(this, ModConfig.canApply.cryogenic, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.cryogenic, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.cryogenic;
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
			PotionEffect slowness = victim.getActivePotionEffect(MobEffects.SLOWNESS);
			PotionEffect fatigue = victim.getActivePotionEffect(MobEffects.MINING_FATIGUE);

			int ampIncrease = target instanceof EntityPlayer ? 1 : 2;
			int slownessAmp = slowness != null ? Math.min(slowness.getAmplifier() + ampIncrease, 3) : ampIncrease-1;
			int fatigueAmp = fatigue != null ? Math.min(fatigue.getAmplifier() + ampIncrease, 3) : ampIncrease-1;

			victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, slownessAmp));
			victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80, fatigueAmp));
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
			
			if(slowness != null && slowness.getAmplifier() >= 1 && fatigue != null && fatigue.getAmplifier() >= 1) {
				victim.extinguish();
				if(attacker.getRNG().nextFloat() <= 0.1F * (float)level) {
					event.setAmount(event.getAmount() * (1.0F + 0.2F * (float)level));

					int range = Math.min(4, (level + 1) / 2);

					for(BlockPos.MutableBlockPos mutablePosInBox : BlockPos.getAllInBoxMutable(-range, -range, -range, range, range, range)) {
						if(taxiCabDistance(mutablePosInBox) <= range) {
							BlockPos freezePos = victim.getPosition().add(mutablePosInBox);
							if (attacker.world.getBlockState(freezePos).getMaterial() == Material.AIR) {
								attacker.world.setBlockState(freezePos, BlockRegistry.tempIce.getDefaultState());
								attacker.world.scheduleUpdate(freezePos, BlockRegistry.tempIce, MathHelper.getInt(attacker.getRNG(), 60, 120));
							}
						}
					}
					attacker.world.playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.8f, -1f);
				}
			}
		}
	}

	private static int taxiCabDistance(BlockPos pos){
		return Math.abs(pos.getX()) + Math.abs(pos.getY()) + Math.abs(pos.getZ());
	}
}