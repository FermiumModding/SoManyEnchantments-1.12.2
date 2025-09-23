package com.shultrea.rin.properties;

import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.util.IEntityDamageSourceMixin;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.SpartanWeaponryCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ArrowPropertiesHandler {
	
	private static final ResourceLocation ARROWPROPERTIES_CAP_RESOURCE = new ResourceLocation(SoManyEnchantments.MODID + ":arrow_capabilities");
	
	private static final Random RANDOM = new Random();
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
		if(event.getObject() instanceof EntityArrow && !event.getObject().hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) {
			event.addCapability(ARROWPROPERTIES_CAP_RESOURCE, new ArrowPropertiesProvider());
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(event.getWorld().isRemote) return;
		if(!(event.getEntity() instanceof EntityArrow)) return;
		EntityArrow entityArrow = (EntityArrow)event.getEntity();
		if(!(entityArrow.shootingEntity instanceof EntityLivingBase)) return;
		EntityLivingBase shooter = (EntityLivingBase)entityArrow.shootingEntity;

		//I dislike handling this on EntityJoinWorld but mixin'ing into ItemBow has compatibility issues due to other mod bows overriding needed methods
		ItemStack bow = shooter.getHeldItemMainhand();
		if(!(bow.getItem() instanceof ItemBow || CompatUtil.isSpartanWeaponryLoaded() && SpartanWeaponryCompat.itemIsCrossbow(bow.getItem()))) {
			bow = shooter.getHeldItemOffhand();
		}
		if(!(bow.getItem() instanceof ItemBow || CompatUtil.isSpartanWeaponryLoaded() && SpartanWeaponryCompat.itemIsCrossbow(bow.getItem()))) {
			return;
		}

		IArrowProperties properties = entityArrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(properties == null) return;
		
		//Avoid overwriting if properties were already set for this entity for whatever reason
		if(properties.getPropertiesHandled()) return;
		setArrowEnchantmentsFromStack(bow, entityArrow, properties);
		properties.setPropertiesHandled(true);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onArrowHitHurt(LivingHurtEvent event) {
		if(!(event.getSource().getImmediateSource() instanceof EntityArrow)) return;
		if(!"arrow".equals(event.getSource().damageType)) return;
		
		EntityArrow arrow = (EntityArrow)event.getSource().getImmediateSource();
		EntityLivingBase victim = event.getEntityLiving();
		
		IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(properties == null) return;
		
		if(EnchantmentRegistry.runeArrowPiercing.isEnabled()) {
			int pierceLevel = properties.getArmorPiercingLevel();
			if(pierceLevel > 0) {
				if(event.getSource() instanceof EntityDamageSource) {
					float currPercent = ((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$getPiercingPercent();
					float percent = Math.min(currPercent + 0.25F * (float)pierceLevel, 1.0F);
					((IEntityDamageSourceMixin)event.getSource()).soManyEnchantments$setPiercingPercent(percent);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onArrowHitDamage(LivingDamageEvent event) {
		if(!(event.getSource().getImmediateSource() instanceof EntityArrow)) return;
		if(!"arrow".equals(event.getSource().damageType)) return;
		
		EntityArrow arrow = (EntityArrow)event.getSource().getImmediateSource();
		EntityLivingBase victim = event.getEntityLiving();
		
		IArrowProperties properties = arrow.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		if(properties == null) return;

		if(EnchantmentRegistry.lesserFlame.isEnabled() || EnchantmentRegistry.advancedFlame.isEnabled() || EnchantmentRegistry.supremeFlame.isEnabled()) {
			int seconds = getFireSeconds(properties.getFlameLevel());
			if(seconds > 0) victim.setFire(seconds);
		}
		
		if(EnchantmentRegistry.extinguish.isEnabled()) {
			int flameLevel = properties.getFlameLevel();
			if(flameLevel == -1) victim.extinguish();
		}
		
		if(EnchantmentRegistry.dragging.isEnabled()) {
			float draggingPower = properties.getDraggingPower();
			if(draggingPower > 0) {
				double velocityMultiplier = -0.6F * draggingPower / MathHelper.sqrt(arrow.motionX * arrow.motionX + arrow.motionZ * arrow.motionZ);
				victim.addVelocity(arrow.motionX * velocityMultiplier, 0.1, arrow.motionZ * velocityMultiplier);
				victim.velocityChanged = true;
			}
		}
		
		if(EnchantmentRegistry.strafe.isEnabled()) {
			if(properties.getArrowResetsIFrames()) {
				victim.hurtResistantTime = 0;
			}
		}
	}
	
	private static int getFireSeconds(int tier) {
		switch(tier) {
			case 1:
				return 2;
			case 2:
				return 15;
			case 3:
				return 30;
		}
		return 0;
	}

	public static void setArrowEnchantmentsFromStack(ItemStack bow, EntityArrow arrow, IArrowProperties properties) {
		if(EnchantmentRegistry.powerless.isEnabled()) {
			int powerlessLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.powerless, bow);
			if(powerlessLevel > 0) {
				arrow.setDamage(arrow.getDamage() - 0.5D - powerlessLevel * 0.5D);
				if(powerlessLevel > 2 || RANDOM.nextFloat() < powerlessLevel * 0.4F) {
					arrow.setIsCritical(false);
				}
			}
		}
		
		if(EnchantmentRegistry.advancedPower.isEnabled()) {
			int advPowerLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedPower, bow);
			if(advPowerLevel > 0) {
				arrow.setDamage(arrow.getDamage() + 1.25D + (double)advPowerLevel * 0.75D);
				if(advPowerLevel >= 4 || RANDOM.nextFloat() < advPowerLevel * 0.25F) {
					arrow.setIsCritical(true);
				}
			}
		}

		if(EnchantmentRegistry.advancedPunch.isEnabled()) {
			int advPunchLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedPunch, bow);
			if(advPunchLevel > 0) {
				arrow.setKnockbackStrength(1 + advPunchLevel * 2);
			}
		}

		if(EnchantmentRegistry.runeArrowPiercing.isEnabled()) {
			int runeArrowPiercingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeArrowPiercing, bow);
			if(runeArrowPiercingLevel > 0) {
				properties.setArmorPiercingLevel(runeArrowPiercingLevel);
			}
		}

		if(EnchantmentRegistry.dragging.isEnabled()) {
			int draggingLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.dragging, bow);
			if(draggingLevel > 0) {
				properties.setDraggingPower(1.25F + draggingLevel * 1.75F);
			}
		}

		if(EnchantmentRegistry.strafe.isEnabled()) {
			int levelStrafe = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.strafe, bow);
			if(RANDOM.nextFloat() < 0.125F * levelStrafe) {
				properties.setArrowResetsIFrames(true);
			}
		}

		if(EnchantmentRegistry.lesserFlame.isEnabled()) {
			int levelLessFlame = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.lesserFlame, bow);
			if(levelLessFlame > 0) {
				//arrow.setFire(50);
				properties.setFlameLevel(1);
			}
		}

		if(EnchantmentRegistry.advancedFlame.isEnabled()) {
			int levelAdvFlame = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.advancedFlame, bow);
			if(levelAdvFlame > 0) {
				arrow.setFire(200);
				properties.setFlameLevel(2);
			}
		}
		
		if(EnchantmentRegistry.supremeFlame.isEnabled()) {
			int levelSupFlame = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.supremeFlame, bow);
			if(levelSupFlame > 0) {
				arrow.setFire(400);
				properties.setFlameLevel(3);
			}
		}
		
		if(EnchantmentRegistry.extinguish.isEnabled()) {
			int levelExtinguish = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.extinguish, bow);
			if(levelExtinguish > 0) {
				arrow.extinguish();
				properties.setFlameLevel(-1);
			}
		}
	}
}