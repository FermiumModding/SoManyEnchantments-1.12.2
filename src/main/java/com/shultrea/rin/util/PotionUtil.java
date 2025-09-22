package com.shultrea.rin.util;

import com.shultrea.rin.config.ModConfig;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class PotionUtil {
	
	public static final Random RANDOM = new Random();

	public static final List<Potion> BUFFS = new ArrayList<>();
	public static final List<Potion> DEBUFFS = new ArrayList<>();
	
	@Nullable
	public static Potion getNegativePotion(Random rand) {
		if(DEBUFFS.isEmpty()) return null;
		return DEBUFFS.get(rand.nextInt(DEBUFFS.size()));
	}
	
	@Nullable
	public static Potion getPositivePotion(Random rand) {
		if(BUFFS.isEmpty()) return null;
		return BUFFS.get(rand.nextInt(BUFFS.size()));
	}

	public static void initializePotionLists() {
		List<ResourceLocation> potionBlacklist = new ArrayList<>();

		for(String str : ModConfig.miscellaneous.potionBlacklist) {
			str = str.trim();
			if(!str.isEmpty()) {
				ResourceLocation loc = new ResourceLocation(str);
				potionBlacklist.add(loc);
			}
		}

		for(Potion potion : Potion.REGISTRY) {
			ResourceLocation potionResource = potion.getRegistryName();
			boolean listMatch = potionBlacklist.contains(potionResource);

			if(listMatch == ModConfig.miscellaneous.potionBlacklistAsWhitelist) {
				if(potion.isBadEffect()) {
					DEBUFFS.add(potion);
				}
				else {
					BUFFS.add(potion);
				}
			}
		}
	}
}