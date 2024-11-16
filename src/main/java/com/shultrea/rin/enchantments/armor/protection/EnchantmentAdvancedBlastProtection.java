package com.shultrea.rin.enchantments.armor.protection;

import com.shultrea.rin.Config.EnchantabilityConfig;
import com.shultrea.rin.Config.ModConfig;
import com.shultrea.rin.Utility_Sector.EnchantmentsUtility;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.registry.EnchantmentRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EnchantmentAdvancedBlastProtection extends EnchantmentBase {
	
	public EnchantmentAdvancedBlastProtection(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	//TODO
	public static double getKnockbackValue(double damage, EntityLivingBase entity) {
		//Return damage if nothing is applied here
		if(!EnchantmentRegistry.advancedBlastProtection.isEnabled()) return damage;
		int i = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.advancedBlastProtection, entity);
		if(i > 0) {
			//Normal blast protection is a modifier of 0.15f
			//This is inaccurate to the original SME, but it's hard to figure out what SME does
			damage -= MathHelper.floor(damage * (double)((float)i * 0.3F));
		}
		return damage;
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.advancedBlastProtection;
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return ModConfig.level.advancedBlastProtection;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.advancedBlastProtection, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.advancedBlastProtection, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApply.advancedBlastProtection, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ModConfig.canApply.isItemValid(ModConfig.canApplyAnvil.advancedBlastProtection, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.advancedBlastProtection;
	}
	
	//TODO
	@Override
	public int calcModifierDamage(int level, DamageSource source) {
		return source.canHarmInCreative() ? 0 : source.isExplosion() ? level * 3 : 0;
	}

	@SubscribeEvent(priority = EventPriority.LOW, receiveCanceled = true)
	public void extraProtectionEffect(LivingHurtEvent fEvent) {
		if(!ModConfig.miscellaneous.extraProtectionEffects) return;
		//System.out.println("EVENT EEEEEEEEEEEEEEE");
		if(!(ModConfig.enabled.advancedBlastProtection)) return;
		if(!(fEvent.getSource().isExplosion())) return;
		int modifier = (int) (7.5f * EnchantmentsUtility.getTotalEnchantmentLevel(EnchantmentRegistry.advancedBlastProtection, fEvent.getEntityLiving()));
		float damage = EnchantmentsUtility.getDamageAfterMagicAbsorb(fEvent.getAmount(), modifier);
		fEvent.setAmount(damage);
	}
 
	/* This handler was bugged and was causing damage whenever a detonation happened even if the detonation did not do damage, so this system has been overhauled instead
	 * Handling is now done in SMEASM and HookHelper
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void DetonateKnockBackReduction(ExplosionEvent.Detonate e)
	{
	
		List victims = e.getAffectedEntities();
		int size = victims.size();
		for(int j = 0; j < size; ++j){
			if(victims.isEmpty())
				return;
			
			if(!(victims.get(j) instanceof EntityLivingBase))
				continue;
			
			EntityLivingBase individual = (EntityLivingBase) victims.get(j);
	
			int enchLevel = EnchantmentHelper.getMaxEnchantmentLevel(this, individual);
			if(enchLevel <= 0)
				continue;

			e.getAffectedEntities().remove(j);
			size = size - 1;
			
			float explosionSize = 0;
		
			Explosion explosion = e.getExplosion();
		   
			if(explosion == null)
				return;
			
			try
			{
				//Create and cache power
				if(power==null)
				{
					power = ObfuscationReflectionHelper.findField(Explosion.class, "field_77280_f");
					power.setAccessible(true);
				}
				
				explosionSize = power.getFloat(explosion);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			EnchantmentsUtility.damageExplosion(individual, explosion, explosionSize, individual.world, this);
			
			//System.out.println(explosionSize);
			
			
			//  motionX *= EnchantmentsUtility.getAdvancedKnockBackReduction(individual);
		   // motionY *= EnchantmentsUtility.getAdvancedKnockBackReduction(individual);
		  //  motionZ *= EnchantmentsUtility.getAdvancedKnockBackReduction(individual);
			
		}
	}
	*/
	
	/*
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void DetonateKnockBackReduce(LivingUpdateEvent fEvent) {
		if(!(fEvent.getEntity() instanceof EntityLivingBase))
			return;
		
		EntityLivingBase wearer = fEvent.getEntityLiving();
		
		int enchLevel = EnchantmentHelper.getMaxEnchantmentLevel(EnchantmentRegistry.AdvancedBlastProtection, wearer);
		if(enchLevel <= 0){
			RemoveKnockBack(wearer);
			return;
		}
			
		level = enchLevel - 1;
		
		
		AddKnockBack(wearer);
	   
	
	}
	private void AddKnockBack(EntityLivingBase fEntity)
	{
		ItemStack weapon = fEntity.getHeldItemMainhand();	
		//int level = potyon.getAmplifier() + 1;
		IAttributeInstance attackAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
		
		AttributeModifier attackDamage = new AttributeModifier(UUID.fromString("e23481-134f-4c54-a535-29c3a241c7a21"),"blastProtectKnockBack", 0.1f + (level * 0.30f), 0);
		//attackAttr.removeModifier(attackDamage);
		
	
		  if(attackAttr.getModifier(UUID.fromString("e23481-134f-4c54-a535-29c3a241c7a21")) != null)
				return;
		  attackAttr.applyModifier(attackDamage);
		
	}
	
	private void RemoveKnockBack(EntityLivingBase fEntity)
	{
		ItemStack weapon = fEntity.getHeldItemMainhand();	
		//int level = potyon.getAmplifier() + 1;
		IAttributeInstance attackAttr = fEntity.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
				
		if(attackAttr.getModifier(UUID.fromString("e23481-134f-4c54-a535-29c3a241c7a21")) == null)
			return;
	
		
		
			AttributeModifier attackDamage = new AttributeModifier(UUID.fromString("e23481-134f-4c54-a535-29c3a241c7a21"),"blastProtectKnockBack", 0.1f + (level * 0.30f), 0);
			attackAttr.removeModifier(attackDamage);	
		

}
*/
	/**
	 @Override public void onEntityDamaged(EntityLivingBase user, Entity target, int level){
	 
	 user.motionX = user.motionX - (user.motionX * level * 0.15f + 0.4f);
	 user.motionY = user.motionY - (user.motionY * level * 0.15f + 0.4f);
	 user.motionZ = user.motionZ - (user.motionZ * level * 0.15f + 0.4f);
		
		
	 }
	 */
	
	/*
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onAttack(LivingAttackEvent e) {
		
		if(!e.getSource().isExplosion())
			return;
		
		if(e.getSource().canHarmInCreative())
			return;
	
		if(!(e.getEntityLiving() instanceof EntityPlayer))
			return;
		
		EntityPlayer user = (EntityPlayer) e.getEntityLiving();
		
		if(user == null)
			return;
	
		int level = EnchantmentHelper.getMaxEnchantmentLevel(this, user);
		
		if(level <= 0)
			return;
		
		System.out.println(e.getAmount());
			
	}
		/*
		e.setCanceled(true);
		
		if(e.getSource().getTrueSource() != null && e.getSource().getTrueSource() instanceof EntityLivingBase && !user.isCreative())
		user.attackEntityFrom(DamageSource.causeExplosionDamage((EntityLivingBase) e.getSource().getTrueSource()).setDamageAllowedInCreativeMode(), e.getAmount());
		
		else user.attackEntityFrom(new DamageSource("explosion").setExplosion().setDifficultyScaled(), e.getAmount());
		//user.velocityChanged = true;
		
		double x = user.motionX - (user.motionX * (level * 0.15f + 0.4f));
		double y = user.motionY - (user.motionY * (level * 0.15f + 0.4f));
		double z = user.motionZ - (user.motionZ * (level * 0.15f + 0.4f));
		
		if(!user.world.isRemote) {
		//System.out.println(user.motionX + " - X BEFORE");
		//System.out.println(user.motionY + " - Y BEFORE");
		//System.out.println(user.motionZ + " - Z BEFORE");
		
		user.motionX = x;
		user.motionY = y;
		user.motionZ = z;
		
		//System.out.println(user.vel + " - X AFTER");
		//System.out.println(user.motionY + " - Y AFTER");
		//System.out.println(user.motionZ + " - Z AFTER");
		user.velocityChanged = true;
		} 
		if(user.world.isRemote) user.setVelocity(x, y, z);
	}
	
	
	}
*/
/*
	@SubscribeEvent
	public void onKnock(LivingKnockBackEvent e) {
		e.setCanceled(true);
	}
*/
}



