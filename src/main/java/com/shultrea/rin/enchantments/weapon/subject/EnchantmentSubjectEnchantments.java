package com.shultrea.rin.enchantments.weapon.subject;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.silentchaos512.scalinghealth.config.Config;

import java.util.Set;

public class EnchantmentSubjectEnchantments extends EnchantmentBase {

	private static final int BIOLOGY = 0;
	private static final int CHEMISTRY = 1;
	private static final int ENGLISH = 2;
	private static final int HISTORY = 3;
	private static final int MATHEMATICS = 4;
	private static final int PE = 5;
	private static final int PHYSICS = 6;
	private static final int GEOGRAPHY = 7;

	private final int damageType;

	public EnchantmentSubjectEnchantments(String name, Rarity rarity, int damageTypeIn, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
		this.damageType = damageTypeIn;
	}

	@Override
	public boolean isEnabled() {
		switch (this.damageType) {
			case BIOLOGY:
				return ModConfig.enabled.subjectBiology;
			case CHEMISTRY:
				return ModConfig.enabled.subjectChemistry;
			case ENGLISH:
				return ModConfig.enabled.subjectEnglish;
			case HISTORY:
				return ModConfig.enabled.subjectHistory;
			case MATHEMATICS:
				return ModConfig.enabled.subjectMathematics;
			case PE:
				return ModConfig.enabled.subjectPE;
			case PHYSICS:
				return ModConfig.enabled.subjectPhysics;
			case GEOGRAPHY:
				return ModConfig.enabled.subjectGeography;
			default:
				return false;
		}
	}

	@Override
	public boolean hasSubscriber() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		switch (this.damageType) {
			case BIOLOGY:
				return ModConfig.level.subjectBiology;
			case CHEMISTRY:
				return ModConfig.level.subjectChemistry;
			case ENGLISH:
				return ModConfig.level.subjectEnglish;
			case HISTORY:
				return ModConfig.level.subjectHistory;
			case MATHEMATICS:
				return ModConfig.level.subjectMathematics;
			case PE:
				return ModConfig.level.subjectPE;
			case PHYSICS:
				return ModConfig.level.subjectPhysics;
			case GEOGRAPHY:
				return ModConfig.level.subjectGeography;
			default:
				return 5;
		}
	}

	@Override
	public int getMinEnchantability(int level) {
		switch (this.damageType) {
			case BIOLOGY:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectBiology, level);
			case CHEMISTRY:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectChemistry, level);
			case ENGLISH:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case HISTORY:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectHistory, level);
			case MATHEMATICS:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case PE:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPE, level);
			case PHYSICS:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectPhysics, level);
			case GEOGRAPHY:
				return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.subjectGeography, level);
			default:
				return 0;
		}
	}

	@Override
	public int getMaxEnchantability(int level) {
		switch (this.damageType) {
			case BIOLOGY:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectBiology, level);
			case CHEMISTRY:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectChemistry, level);
			case ENGLISH:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectEnglish, level);
			case HISTORY:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectHistory, level);
			case MATHEMATICS:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectMathematics, level);
			case PE:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPE, level);
			case PHYSICS:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectPhysics, level);
			case GEOGRAPHY:
				return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.subjectGeography, level);
			default:
				return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		switch (this.damageType) {
			case BIOLOGY:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectBiology, stack) && super.canApplyAtEnchantingTable(stack);
			case CHEMISTRY:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectChemistry, stack) && super.canApplyAtEnchantingTable(stack);
			case ENGLISH:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectEnglish, stack) && super.canApplyAtEnchantingTable(stack);
			case HISTORY:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectHistory, stack) && super.canApplyAtEnchantingTable(stack);
			case MATHEMATICS:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectMathematics, stack) && super.canApplyAtEnchantingTable(stack);
			case PE:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectPE, stack) && super.canApplyAtEnchantingTable(stack);
			case PHYSICS:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectPhysics, stack) && super.canApplyAtEnchantingTable(stack);
			case GEOGRAPHY:
				return ConfigProvider.canItemApply(this, ModConfig.canApply.subjectGeography, stack) && super.canApplyAtEnchantingTable(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean canApply(ItemStack stack) {
		switch (this.damageType) {
			case BIOLOGY:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectBiology, stack) || super.canApply(stack);
			case CHEMISTRY:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectChemistry, stack) || super.canApply(stack);
			case ENGLISH:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectEnglish, stack) || super.canApply(stack);
			case HISTORY:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectHistory, stack) || super.canApply(stack);
			case MATHEMATICS:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectMathematics, stack) || super.canApply(stack);
			case PE:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectPE, stack) || super.canApply(stack);
			case PHYSICS:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectPhysics, stack) || super.canApply(stack);
			case GEOGRAPHY:
				return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.subjectGeography, stack) || super.canApply(stack);
			default:
				return false;
		}
	}

	@Override
	public boolean isTreasureEnchantment() {
		switch (this.damageType) {
			case BIOLOGY:
				return ModConfig.treasure.subjectBiology;
			case CHEMISTRY:
				return ModConfig.treasure.subjectChemistry;
			case ENGLISH:
				return ModConfig.treasure.subjectEnglish;
			case HISTORY:
				return ModConfig.treasure.subjectHistory;
			case MATHEMATICS:
				return ModConfig.treasure.subjectMathematics;
			case PE:
				return ModConfig.treasure.subjectPE;
			case PHYSICS:
				return ModConfig.treasure.subjectPhysics;
			case GEOGRAPHY:
				return ModConfig.treasure.subjectGeography;
			default:
				return true;
		}
	}

	@Override
	public void onEntityDamagedAlt(EntityLivingBase attacker, Entity target, ItemStack weapon, int level) {
		if (!this.isEnabled()) return;
		if (attacker == null) return;
		if (attacker.world.isRemote) return;
		if (!(target instanceof EntityLivingBase)) return;
		if (weapon.isEmpty()) return;

		if (CompatUtil.isRLCombatLoaded() && attacker.getRNG().nextFloat() > RLCombatCompat.getOnEntityDamagedAltStrength())
			return;
		if (this.damageType == PE) {
			if (attacker.getRNG().nextFloat() < 0.05F * (float) level) {
				attacker.addPotionEffect(new PotionEffect(MobEffects.HASTE, 120 + (level * 20), Math.min(3, level - 1)));
				attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED, 80 + (level * 20), Math.min(3, level - 1)));
				if (level > 2) {
					attacker.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60 + (level * 20), level - 3));
					attacker.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 60 + (level * 20), level - 3));
				}
				if (level > 4) {
					attacker.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 + (level * 20), level - 5));
				}
			}
		} else if (this.damageType == GEOGRAPHY) {
			EntityLivingBase victim = (EntityLivingBase) target;
			Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(attacker.world.getBiome(attacker.getPosition()));
			int amp = 1; //lvl II
			int dur = (200 * level) / getMaxLevel(); //up to 10 seconds of the effects
			if (types.contains(BiomeDictionary.Type.HOT) || types.contains(BiomeDictionary.Type.NETHER)) {
				target.setFire(level * 2); //up to 10 secs of fire
			} else if (types.contains(BiomeDictionary.Type.COLD) || types.contains(BiomeDictionary.Type.SNOWY)) {
				victim.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, dur, amp));
				victim.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, dur, amp));
			} else if (types.contains(BiomeDictionary.Type.SWAMP))
				victim.addPotionEffect(new PotionEffect(MobEffects.POISON, dur, amp));
			else if (types.contains(BiomeDictionary.Type.WASTELAND))
				victim.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, dur * 2, 0));
			else if (types.contains(BiomeDictionary.Type.SPOOKY) || types.contains(BiomeDictionary.Type.DEAD))
				victim.addPotionEffect(new PotionEffect(MobEffects.WITHER, dur, amp));
			else if (types.contains(BiomeDictionary.Type.MAGICAL))
				attacker.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, dur / 2, 0));
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if (!this.isEnabled()) return;
		if (!EnchantmentBase.isDamageSourceAllowed(event.getSource())) return;
		if (event.getAmount() <= 1.0F) return;
		EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
		if (attacker == null) return;
		EntityLivingBase victim = event.getEntityLiving();
		if (victim == null) return;
		ItemStack stack = attacker.getHeldItemMainhand();
		if (stack.isEmpty()) return;

		int level = EnchantmentHelper.getEnchantmentLevel(this, stack);
		if (level > 0) {
			float strengthMulti = CompatUtil.isRLCombatLoaded() ? RLCombatCompat.getAttackEntityFromStrength() : 1.0F;

			if (this.damageType == BIOLOGY) {
				if (!(victim instanceof EntityLiving)) return;
				int taskCount = ((EntityLiving) victim).tasks.taskEntries.size();
				if (taskCount > 15 - level) {
					//+7.5 at lvl 5 with >10 tasks
					float dmg = Math.min(2.5F * (float) level, 7.5F);
					event.setAmount(event.getAmount() + dmg * strengthMulti);
				}
			} else if (this.damageType == CHEMISTRY) {
				int count = attacker.getActivePotionEffects().size();
				if (count > 0) {
					//+1.5 per active potion effect, +6 with 9 effects
					float dmg = Math.min(0.3F * (float) level * (float) count, 6.0F);
					event.setAmount(event.getAmount() + dmg * strengthMulti);

					if(attacker.getRNG().nextFloat() < 0.04 * level) { //up to 20% chance
						BlockPos explosionPos = victim.getPosition();
						//Explosion strength max 4 (= TNT)
						attacker.world.createExplosion(attacker, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), dmg * strengthMulti * 2F / 3F, false);
					}
				}
			} else if (this.damageType == ENGLISH) {
				int count = victim.getName().length();
				if (count > 0) {
					//+0.375 per letter, +7.5 at 20 letters
					float dmg = Math.min(0.075F * (float) level * (float) count, 7.5F);
					event.setAmount(event.getAmount() + dmg * strengthMulti);
				}
			} else if (this.damageType == HISTORY) {
				float perc = attacker.world.getDifficultyForLocation(attacker.getPosition()).getAdditionalDifficulty() / 6.75F;
				if (perc > 0) {
					//+7.5 at max local difficulty
					float dmg = 1.5F * (float) level * perc;
					event.setAmount(event.getAmount() + dmg * strengthMulti);
				}
			} else if (this.damageType == MATHEMATICS) {
				int count;
				if (attacker instanceof EntityPlayer)
					//xp lvls, not raw xp, divided by 2 to start adding dmg from xp lvl 4. maxes out at xp lvl 128
					count = ((EntityPlayer) attacker).experienceLevel / 2;
				else
					//half minutes alive. dmg maxes out at 32 minutes
					count = attacker.ticksExisted / 600;
				float dmg = 0.25F * (float) level * (float) MathHelper.log2(Math.max(1, count));
				if (dmg > 0) {
					//+0 dmg until xp lvl 4, then +0.35*enchlvl per doubling of xp lvl
					//<lvl 4: +0, lvl 4: +1.25, lvl 8: +2.5, lvl 16: +3.25, lvl 32: +5, lvl 64: +6.25, lvl 128: +7.5
					dmg = Math.min(dmg, 7.5F);
					event.setAmount(event.getAmount() + dmg * strengthMulti);
				}
			} else if (this.damageType == PHYSICS) {
				AxisAlignedBB victimAABB = victim.getEntityBoundingBox();
				AxisAlignedBB attackerAABB = attacker.getEntityBoundingBox();
				double victimSize = (victimAABB.maxX - victimAABB.minX) * (victimAABB.maxY - victimAABB.minY) * (victimAABB.maxZ - victimAABB.minZ);
				double attackerSize = (attackerAABB.maxX - attackerAABB.minX) * (attackerAABB.maxY - attackerAABB.minY) * (attackerAABB.maxZ - attackerAABB.minZ);
				if (victimSize > 0 && attackerSize > 0) {
					float perc = (float) (victimSize / attackerSize);
					if (perc < 1) perc = 1.0F / perc;
					//+1.5 if same size, +3 if double/half size, +7.5 if x5 or 1/5x size
					float dmg = Math.min(0.3F * (float) level * perc, 7.5F);
					event.setAmount(event.getAmount() + dmg * strengthMulti);
				}
			}
		}
	}
}