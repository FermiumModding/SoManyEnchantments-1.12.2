package com.shultrea.rin.enchantments.tool;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.config.folders.EnchantabilityConfig;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentSmelter extends EnchantmentBase {
	
	public EnchantmentSmelter(String name, Rarity rarity, EntityEquipmentSlot... slots) {
		super(name, rarity, slots);
	}
	
	@Override
	public boolean isEnabled() {
		return ModConfig.enabled.smelter;
	}
	
	@Override
	public boolean hasSubscriber() {
		return true;
	}
	
	@Override
	public int getMaxLevel() {
		return ModConfig.level.smelter;
	}

	@Override
	public int getMinEnchantability(int level) {
		return EnchantabilityConfig.getMinEnchantability(ModConfig.enchantability.smelter, level);
	}

	@Override
	public int getMaxEnchantability(int level) {
		return EnchantabilityConfig.getMaxEnchantability(ModConfig.enchantability.smelter, level);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApply.smelter, stack) && super.canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApply(ItemStack stack){
		return ConfigProvider.canItemApply(this, ModConfig.canApplyAnvil.smelter, stack) || super.canApply(stack);
	}
	
	@Override
	public boolean isTreasureEnchantment() {
		return ModConfig.treasure.smelter;
	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onHarvestDropsEvent(HarvestDropsEvent event) {
		if(!this.isEnabled()) return;
		EntityPlayer player = event.getHarvester();
		if(player == null) return;
		ItemStack tool = player.getHeldItemMainhand();
		if(tool.isEmpty()) return;
		if(event.isSilkTouching()) return;
		if(player.isSneaking()) return;
		if(event.getDrops().isEmpty()) return;

		int level = EnchantmentHelper.getEnchantmentLevel(this, tool);
		if (level <= 0) return;
		if (!tool.canHarvestBlock(event.getState()) && !ForgeHooks.isToolEffective(player.world, event.getPos(), tool)) return;
		List<ItemStack> drops = new ArrayList<>();
		for (ItemStack origDrop : event.getDrops()) {
			if (origDrop.isEmpty()) continue;

			int origAmount = origDrop.getCount();
			ItemStack smeltResult = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(origDrop.getItem(), 1, origDrop.getMetadata()));
			if (smeltResult.isEmpty()) {
				//If theres no smelting, drop unsmelted item
				drops.add(origDrop);
				continue;
			}

			int levelFortune = event.getFortuneLevel();
			if (levelFortune > 0 && !(smeltResult.getItem() instanceof ItemBlock)) {
				//Fortune based on amount of original drops
				origAmount *= 1 + player.getRNG().nextInt(levelFortune + 1);
			}
			//Account for if something smelts into multiple items
			int dropAmount = origAmount * smeltResult.getCount();

			while (dropAmount > 0) {
				//Account for returning more than the stack size of the smelted item
				int toDrop = Math.min(dropAmount, smeltResult.getMaxStackSize());
				dropAmount -= toDrop;
				drops.add(new ItemStack(smeltResult.getItem(), toDrop, smeltResult.getMetadata()));
				event.getWorld().spawnParticle(EnumParticleTypes.FLAME, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), 0, 0, 0);
			}
		}
		//Replace original drops with potentially smelted drops
		event.getDrops().clear();
		event.getDrops().addAll(drops);
	}
}