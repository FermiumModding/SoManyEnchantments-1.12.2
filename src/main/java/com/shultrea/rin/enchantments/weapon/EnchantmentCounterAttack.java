package com.shultrea.rin.enchantments.weapon;

import com.shultrea.rin.Main_Sector.EnchantabilityConfig;
import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentCounterAttack extends EnchantmentBase {
	
	public EnchantmentCounterAttack(String name, Rarity rarity, EnumEnchantmentType type, EntityEquipmentSlot[] slots) {
		super(name, rarity, type, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.counterAttack;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.counterAttack;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.counterAttack, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.counterAttack, level);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.counterAttack;
	}
	
	//TODO
	@Override
	public void onUserHurt(EntityLivingBase user, Entity target, int level) {
		//TODO figure out how the crash works
		if(target instanceof EntityLivingBase) {
			EntityLivingBase attacker = (EntityLivingBase)target;
			if(user instanceof EntityPlayer) {
				if(attacker.getRNG().nextInt(100) < 20 + (level * 5)) {
					EntityPlayer player = (EntityPlayer)user;
					player.hurtResistantTime = 20;
					player.attackTargetEntityWithCurrentItem(attacker);
				}
			}
		}
	}
    /*
  @SubscribeEvent(priority = EventPriority.LOW) 
  public void HandleEnchant(LivingAttackEvent fEvent)
  {
	  	if(fEvent.getSource().damageType != "player" && fEvent.getSource().damageType != "mob")
	  		return;
  	
   		if(!(fEvent.getEntityLiving().attackable()))
   			return;
  	
   		if(fEvent.getEntity() == null)
   			return;
   		
  		if(!(fEvent.getEntity() instanceof EntityLivingBase))
  			return;
  	
  		EntityLivingBase victim = (EntityLivingBase)fEvent.getEntity();
		ItemStack weaponSword = victim.getHeldItemMainhand();
				
		if(weaponSword == null)
			return;
		
		Entity attacker = fEvent.getSource().getTrueSource();
		
		if(attacker == null || !(attacker instanceof EntityLivingBase))
			return;
		
		if(EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CounterAttack, weaponSword) <= 0)
			return;
		
		int levelCA = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.CounterAttack, weaponSword);
		if(victim instanceof EntityPlayer){
		
		if(fEvent.getEntity().world.rand.nextInt(100) < 20 + (levelCA * 5)){
			
		EntityPlayer player = (EntityPlayer) fEvent.getEntityLiving();
		
		player.attackTargetEntityWithCurrentItem(attacker);
		
		player.hurtResistantTime = 15;
		
		
  
		}
		}
		else if(!(victim instanceof EntityPlayer)){
			if(fEvent.getEntity().world.rand.nextInt(100) < 16 + (levelCA * 8)){
			
			fEvent.getEntityLiving().attackEntityAsMob(attacker);
			
			fEvent.getEntityLiving().hurtResistantTime = 20;
		}
}
  }
  */
}