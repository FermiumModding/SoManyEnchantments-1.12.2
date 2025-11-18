package com.shultrea.rin.mixin.vanilla;

import com.shultrea.rin.registry.EnchantmentRegistry;
import com.shultrea.rin.registry.SoundRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	
	/**
	 * Handling for Rusted enchant
	 */
	@ModifyVariable(
			method = "damageItem",
			at = @At("HEAD"),
			argsOnly = true
	)
	private int soManyEnchantments_vanillaItemStack_damageItem_head(int amount) {
		if(amount > 0 && EnchantmentRegistry.rusted.isEnabled()) {
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.rusted, (ItemStack)(Object)this);
			if(level > 0) return amount * (level+1);
		}
		return amount;
	}
	
	/**
	 * Handling for Rune Revival
	 */
	@Redirect(
			method = "damageItem",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;attemptDamageItem(ILjava/util/Random;Lnet/minecraft/entity/player/EntityPlayerMP;)Z")
	)
	private boolean soManyEnchantments_vanillaItemStack_damageItem_attemptDamageItem(ItemStack instance, int amount, Random rand, EntityPlayerMP damager) {
		boolean broken = instance.attemptDamageItem(amount, rand, damager);
		if(broken && EnchantmentRegistry.runeRevival.isEnabled()) {
			int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.runeRevival, instance);
			if(level > 0) {
				int durability = instance.getMaxDamage();
				float extraChance = durability > 1250 ? 0 :
									durability > 750 ? 4 :
									durability > 200 ? 6 :
									durability > 80 ? 8 :
									10;
				extraChance = extraChance / 100F;
				boolean shouldSave = rand.nextFloat() < (0.15F + ((float)level * 0.15F)) + extraChance;
				if(shouldSave) {
					broken = false;
					instance.setItemDamage(Math.max(0, instance.getMaxDamage() - (int)((float)instance.getMaxDamage() * (0.25F + ((float)level * rand.nextFloat() / 3.0F)))));
					damager.world.playSound(null, damager.posX, damager.posY, damager.posZ, SoundRegistry.RUNE_REVIVAL, SoundCategory.PLAYERS, 2F, 0.8F + 0.4F * rand.nextFloat());
				}
			}
		}
		return broken;
	}
}