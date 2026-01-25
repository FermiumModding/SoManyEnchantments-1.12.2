package com.shultrea.rin.config.folders;

import net.minecraftforge.common.config.Config;

public class CanApplyConfig {

	@Config.Name("-Custom Item Types")
	@Config.Comment("Pattern: \n" +
			"    Name to use; Regex to match against item ids\n" +
			"Custom types can also be used in canApplyAnvil config")
	public String[] customTypes = {
			"BATTLEAXE; (mujmajnkraftsbettersurvival\\:item.*battleaxe)|(spartan(defiled|fire|weaponry)\\:battleaxe.*)",
			"LYCANITES_EQUIPMENT; lycanitesmobs:equipment",
			"SW_CROSSBOW; spartan(defiled|fire|weaponry)\\:crossbow.*"
	};

	@Config.Comment("Some modded items only pretend to be a specific item type without actually being them (wolf armor being SWORD and ARMOR_FEET, better survival items being SWORD etc).\n" +
			"Enabling this toggle will make the given item type match those too.\n" +
			"Disabling will instead only look at the actual checks of each EnumEnchantmentType (like SWORD = has to be instanceof ItemSword) to match.")
	@Config.Name("-Allow Custom Items")
	public boolean allowCustomItems = true;

	@Config.Name("Adept")
	public String[] adept = {"SWORD", "AXE", "BOW", "BATTLEAXE", "SW_CROSSBOW"};
	@Config.Name("Ancient Sealed Curses")
	public String[] ancientSealedCurses = {"SWORD"};
	@Config.Name("Ancient Sword Mastery")
	public String[] ancientSwordMastery = {"SWORD"};
	@Config.Name("Arc Slash")
	public String[] arcSlash = {"SWORD", "!LYCANITES_EQUIPMENT"};
	@Config.Name("Ash Destroyer")
	public String[] ashDestroyer = {"SWORD"};
	@Config.Name("Atomic Deconstructor")
	public String[] atomicDeconstructor = {"SWORD"};
	@Config.Name("Blessed Edge")
	public String[] blessedEdge = {"SWORD"};
	@Config.Name("Brutality")
	public String[] brutality = {"AXE", "BATTLEAXE"};
	@Config.Name("Burning Shield")
	public String[] burningShield = {"SHIELD"};
	@Config.Name("Burning Thorns")
	public String[] burningThorns = {"ARMOR_CHEST"};
	@Config.Name("Butchering")
	public String[] butchering = {"SWORD"};
	@Config.Name("Clearskies' Favor")
	public String[] clearskiesFavor = {"SWORD"};
	@Config.Name("Combat Medic")
	public String[] combatMedic = {"ARMOR_HEAD"};
	@Config.Name("Counter Attack")
	public String[] counterAttack = {"SWORD"};
	@Config.Name("Critical Strike")
	public String[] criticalStrike = {"SWORD", "AXE", "BATTLEAXE"};
	@Config.Name("Culling")
	public String[] culling = {"AXE", "BATTLEAXE"};
	@Config.Name("Dark Shadows")
	public String[] darkShadows = {"SWORD"};
	@Config.Name("Defusing Edge")
	public String[] defusingEdge = {"SWORD"};
	@Config.Name("Desolator")
	public String[] desolator = {"AXE", "BATTLEAXE"};
	@Config.Name("Difficulty's Endowment")
	public String[] difficultysEndowment = {"SWORD"};
	@Config.Name("Disarmament")
	public String[] disarmament = {"AXE", "BATTLEAXE"};
	@Config.Name("Disorientating Blade")
	public String[] disorientatingBlade = {"SWORD"};
	@Config.Name("Empowered Defence")
	public String[] empoweredDefence = {"SHIELD"};
	@Config.Name("Envenomed")
	public String[] envenomed = {"SWORD"};
	@Config.Name("Evasion")
	public String[] evasion = {"ARMOR_LEGS"};
	@Config.Name("Fiery Edge")
	public String[] fieryEdge = {"SWORD"};
	@Config.Name("Flinging")
	public String[] flinging = {"SWORD"};
	@Config.Name("Cryogenic")
	public String[] cryogenic = {"SWORD"};
	@Config.Name("Hors De Combat")
	public String[] horsDeCombat = {"SWORD"};
	@Config.Name("Inhumane")
	public String[] inhumane = {"SWORD"};
	@Config.Name("Inner Berserk")
	public String[] innerBerserk = {"ARMOR_CHEST"};
	@Config.Name("Jagged Rake")
	public String[] jaggedRake = {"HOE"};
	@Config.Name("Levitator")
	public String[] levitator = {"SWORD"};
	@Config.Name("Lifesteal")
	public String[] lifesteal = {"SWORD"};
	@Config.Name("Light Weight")
	public String[] lightWeight = {"ARMOR_FEET"};
	@Config.Name("Luck Magnification")
	public String[] luckMagnification = {"SWORD", "AXE", "BATTLEAXE"};
	@Config.Name("Lunas Blessing")
	public String[] lunasBlessing = {"SWORD"};
	@Config.Name("Magic Protection")
	public String[] magicProtection = {"ARMOR"};
	@Config.Name("Magma Walker")
	public String[] magmaWalker = {"ARMOR_FEET"};
	@Config.Name("Meltdown")
	public String[] meltdown = {"ARMOR_CHEST"};
	@Config.Name("Moisturized")
	public String[] moisturized = {"HOE"};
	@Config.Name("Mortalitas")
	public String[] mortalitas = {"SWORD", "AXE", "BATTLEAXE"};
	@Config.Name("Natural Blocking")
	public String[] naturalBlocking = {"SHIELD"};
	@Config.Name("Parry")
	public String[] parry = {"SWORD"};
	@Config.Name("Penetrating Edge")
	public String[] penetratingEdge = {"AXE", "BATTLEAXE"};
	@Config.Name("Physical Protection")
	public String[] physicalProtection = {"ARMOR"};
	@Config.Name("Dragging")
	public String[] dragging = {"BOW","SW_CROSSBOW"};
	@Config.Name("Purging Blade")
	public String[] purgingBlade = {"SWORD"};
	@Config.Name("Purification")
	public String[] purification = {"AXE", "BATTLEAXE"};
	@Config.Name("Pushing")
	public String[] pushing = {"BOW"};
	@Config.Name("Rain's Bestowment")
	public String[] rainsBestowment = {"SWORD"};
	@Config.Name("Reviled Blade")
	public String[] reviledBlade = {"SWORD"};
	@Config.Name("Reinforced Sharpness")
	public String[] reinforcedsharpness = {"TOOL", "LYCANITES_EQUIPMENT"};
	@Config.Name("Smelter")
	public String[] smelter = {"TOOL"};
	@Config.Name("Sol's Blessing")
	public String[] solsBlessing = {"SWORD"};
	@Config.Name("Spell Breaker")
	public String[] spellBreaker = {"SWORD", "AXE", "BATTLEAXE"};
	@Config.Name("Splitshot")
	public String[] splitShot = {"BOW"};
	@Config.Name("Strafe")
	public String[] strafe = {"BOW"};
	@Config.Name("Strengthened Vitality")
	public String[] strengthenedVitality = {"ARMOR_CHEST"};
	@Config.Name("Swifter Slashes")
	public String[] swifterSlashes = {"SWORD"};
	@Config.Name("Thunderstorm's Bestowment")
	public String[] thunderstormsBestowment = {"SWORD"};
	@Config.Name("True Strike")
	public String[] trueStrike = {"SWORD"};
	@Config.Name("Swift Swimming")
	public String[] swiftSwimming = {"ARMOR_FEET"};
	@Config.Name("Unreasonable")
	public String[] unreasonable = {"SWORD"};
	@Config.Name("Unsheathing")
	public String[] unsheathing = {"SWORD"};
	@Config.Name("Upgraded Potentials")
	public String[] upgradedPotentials = {"NONE"};
	@Config.Name("Viper")
	public String[] viper = {"SWORD"};
	@Config.Name("Water Aspect")
	public String[] waterAspect = {"SWORD"};
	@Config.Name("Plowing")
	public String[] plowing = {"HOE"};
	@Config.Name("Winter's Grace")
	public String[] wintersGrace = {"SWORD"};

	//Curses
	@Config.Name("Bluntness")
	public String[] bluntness = {"SWORD", "BATTLEAXE"};
	@Config.Name("Breached Plating")
	public String[] breachedPlating = {"ARMOR"};
	@Config.Name("Cursed Edge")
	public String[] cursedEdge = {"SWORD"};
	@Config.Name("Curse of Decay")
	public String[] curseOfDecay = {"ALL_ITEMS"};
	@Config.Name("Curse of Holding")
	public String[] curseOfHolding = {"ALL_ITEMS"};
	@Config.Name("Curse of Inaccuracy")
	public String[] curseOfInaccuracy = {"SWORD", "AXE","BOW", "BATTLEAXE"};
	@Config.Name("Curse of Possession")
	public String[] curseOfPossession = {"ALL_TYPES"};
	@Config.Name("Curse of Vulnerability")
	public String[] curseOfVulnerability = {"ARMOR"};
	@Config.Name("Heavy Weight")
	public String[] heavyWeight = {"SWORD", "ARMOR"};
	@Config.Name("Inefficient")
	public String[] inefficient = {"TOOL", "LYCANITES_EQUIPMENT"};
	@Config.Name("Instability")
	public String[] instability = {"SWORD", "TOOL"};
	@Config.Name("Pandora's Curse")
	public String[] pandorasCurse = {"ALL_ITEMS"};
	@Config.Name("Powerless")
	public String[] powerless = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Rusted")
	public String[] rusted = {"BREAKABLE"};
	@Config.Name("Unpredictable")
	public String[] unpredictable = {"SWORD"};
	@Config.Name("Ascetic")
	public String[] ascetic = {"SWORD", "AXE", "FISHING_ROD"};
	@Config.Name("Extinguish")
	public String[] extinguish = {"SWORD", "AXE","BOW", "SW_CROSSBOW"};

	//Rune
	@Config.Name("Rune: Arrow Piercing")
	public String[] runeArrowPiercing = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Rune: Magical Blessing")
	public String[] runeMagicalBlessing = {"SWORD"};
	@Config.Name("Rune: Piercing Capabilities")
	public String[] runePiercingCapabilities = {"SWORD"};
	@Config.Name("Rune: Resurrection")
	public String[] runeResurrection = {"SHIELD"};
	@Config.Name("Rune: Revival")
	public String[] runeRevival = {"BREAKABLE", "!ARMOR"};
	
	//Subject
	@Config.Name("Subject Biology")
	public String[] subjectBiology = {"SWORD"};
	@Config.Name("Subject Chemistry")
	public String[] subjectChemistry = {"SWORD"};
	@Config.Name("Subject English")
	public String[] subjectEnglish = {"SWORD"};
	@Config.Name("Subject History")
	public String[] subjectHistory = {"SWORD"};
	@Config.Name("Subject Mathematics")
	public String[] subjectMathematics = {"SWORD"};
	@Config.Name("Subject P.E.")
	public String[] subjectPE = {"SWORD"};
	@Config.Name("Subject Physics")
	public String[] subjectPhysics = {"SWORD"};
	@Config.Name("Subject Geography")
	public String[] subjectGeography = {"SWORD"};
	//Lesser
	@Config.Name("Lesser Bane of Arthropods")
	public String[] lesserBaneOfArthropods = {"SWORD"};
	@Config.Name("Lesser Fire Aspect")
	public String[] lesserFireAspect = {"SWORD"};
	@Config.Name("Lesser Flame")
	public String[] lesserFlame = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Lesser Sharpness")
	public String[] lesserSharpness = {"SWORD"};
	@Config.Name("Lesser Smite")
	public String[] lesserSmite = {"SWORD"};
	
	//Advanced
	@Config.Name("Advanced Bane of Arthropods")
	public String[] advancedBaneOfArthropods = {"SWORD"};
	@Config.Name("Advanced Blast Protection")
	public String[] advancedBlastProtection = {"ARMOR"};
	@Config.Name("Advanced Efficiency")
	public String[] advancedEfficiency = {"TOOL", "LYCANITES_EQUIPMENT"};
	@Config.Name("Advanced Feather Falling")
	public String[] advancedFeatherFalling = {"ARMOR_FEET"};
	@Config.Name("Advanced Fire Aspect")
	public String[] advancedFireAspect = {"SWORD"};
	@Config.Name("Advanced Fire Protection")
	public String[] advancedFireProtection = {"ARMOR"};
	@Config.Name("Advanced Flame")
	public String[] advancedFlame = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Advanced Knockback")
	public String[] advancedKnockback = {"SWORD"};
	@Config.Name("Advanced Looting")
	public String[] advancedLooting = {"SWORD"};
	@Config.Name("Advanced Luck of the Sea")
	public String[] advancedLuckOfTheSea = {"FISHING_ROD"};
	@Config.Name("Advanced Lure")
	public String[] advancedLure = {"FISHING_ROD"};
	@Config.Name("Advanced Mending")
	public String[] advancedMending = {"BREAKABLE"};
	@Config.Name("Advanced Power")
	public String[] advancedPower = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Advanced Projectile Protection")
	public String[] advancedProjectileProtection = {"ARMOR"};
	@Config.Name("Advanced Protection")
	public String[] advancedProtection = {"ARMOR"};
	@Config.Name("Advanced Punch")
	public String[] advancedPunch = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Advanced Sharpness")
	public String[] advancedSharpness = {"SWORD"};
	@Config.Name("Advanced Smite")
	public String[] advancedSmite = {"SWORD"};
	@Config.Name("Advanced Thorns")
	public String[] advancedThorns = {"ARMOR_CHEST"};
	//Supreme
	@Config.Name("Supreme Bane of Arthropods")
	public String[] supremeBaneOfArthropods = {"SWORD"};
	@Config.Name("Supreme Fire Aspect")
	public String[] supremeFireAspect = {"SWORD"};
	@Config.Name("Supreme Flame")
	public String[] supremeFlame = {"BOW", "SW_CROSSBOW"};
	@Config.Name("Supreme Sharpness")
	public String[] supremeSharpness = {"SWORD"};
	@Config.Name("Supreme Smite")
	public String[] supremeSmite = {"SWORD"};
}