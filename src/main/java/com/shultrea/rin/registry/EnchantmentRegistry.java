package com.shultrea.rin.registry;

import com.shultrea.rin.config.ConfigProvider;
import com.shultrea.rin.enchantments.weapon.selfheal.*;
import com.shultrea.rin.util.Slots;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.enchantments.*;
import com.shultrea.rin.enchantments.armor.*;
import com.shultrea.rin.enchantments.armor.protection.*;
import com.shultrea.rin.enchantments.armor.thorns.*;
import com.shultrea.rin.enchantments.base.*;
import com.shultrea.rin.enchantments.bow.*;
import com.shultrea.rin.enchantments.curses.*;
import com.shultrea.rin.enchantments.fishing.*;
import com.shultrea.rin.enchantments.hoe.*;
import com.shultrea.rin.enchantments.rune.*;
import com.shultrea.rin.enchantments.shield.*;
import com.shultrea.rin.enchantments.tool.*;
import com.shultrea.rin.enchantments.weapon.*;
import com.shultrea.rin.enchantments.weapon.ancient.*;
import com.shultrea.rin.enchantments.weapon.conditionaldamage.*;
import com.shultrea.rin.enchantments.weapon.crits.*;
import com.shultrea.rin.enchantments.weapon.damage.*;
import com.shultrea.rin.enchantments.weapon.damagemultiplier.*;
import com.shultrea.rin.enchantments.weapon.potiondebuffer.*;
import com.shultrea.rin.enchantments.weapon.subject.EnchantmentSubjectEnchantments;
import com.shultrea.rin.enchantments.weapon.weather.*;
import com.shultrea.rin.util.compat.CompatUtil;
import com.shultrea.rin.util.compat.RLCombatCompat;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashSet;
import java.util.Set;

public class EnchantmentRegistry {

	private static final Set<EnchantmentBase> enchantmentSet = new LinkedHashSet<>();

	public static EnchantmentBase adept = register(new EnchantmentAdept("adept", Enchantment.Rarity.valueOf(ModConfig.rarity.adept), Slots.HAND));
	public static EnchantmentBase ancientSealedCurses = register(new EnchantmentAncientSealedCurses("ancientsealedcurses", Enchantment.Rarity.valueOf(ModConfig.rarity.ancientSealedCurses), Slots.HAND));
	public static EnchantmentBase ancientSwordMastery = register(new EnchantmentAncientSwordMastery("ancientswordmastery", Enchantment.Rarity.valueOf(ModConfig.rarity.ancientSwordMastery), Slots.HAND));
	public static EnchantmentBase arcSlash = register(new EnchantmentArcSlash("arcslash", Enchantment.Rarity.valueOf(ModConfig.rarity.arcSlash), Slots.HAND));
	public static EnchantmentBase ashDestroyer = register(new EnchantmentAshDestroyer("ashdestroyer", Enchantment.Rarity.valueOf(ModConfig.rarity.ashDestroyer), Slots.HAND));
	public static EnchantmentBase atomicDeconstructor = register(new EnchantmentAtomicDeconstructor("atomicdeconstructor", Enchantment.Rarity.valueOf(ModConfig.rarity.atomicDeconstructor), Slots.HAND));
	public static EnchantmentBase blessedEdge = register(new EnchantmentBlessedEdge("blessededge", Enchantment.Rarity.valueOf(ModConfig.rarity.blessedEdge), Slots.HAND));
	public static EnchantmentBase brutality = register(new EnchantmentBrutality("brutality", Enchantment.Rarity.valueOf(ModConfig.rarity.brutality), Slots.HAND));
	public static EnchantmentBase burningShield = register(new EnchantmentBurningShield("burningshield", Enchantment.Rarity.valueOf(ModConfig.rarity.burningShield), Slots.HAND));
	public static EnchantmentBase burningThorns = register(new EnchantmentBurningThorns("burningthorns", Enchantment.Rarity.valueOf(ModConfig.rarity.burningThorns), Slots.ALL));
	public static EnchantmentBase butchering = register(new EnchantmentButchering("butchering", Enchantment.Rarity.valueOf(ModConfig.rarity.butchering), Slots.HAND));
	public static EnchantmentBase clearskiesFavor = register(new EnchantmentClearskiesFavor("clearskiesfavor", Enchantment.Rarity.valueOf(ModConfig.rarity.clearskiesFavor), Slots.HAND));
	public static EnchantmentBase combatMedic = register(new EnchantmentCombatMedic("combatmedic", Enchantment.Rarity.valueOf(ModConfig.rarity.combatMedic), Slots.HEAD));
	public static EnchantmentBase counterAttack = register(new EnchantmentCounterAttack("counterattack", Enchantment.Rarity.valueOf(ModConfig.rarity.counterAttack), Slots.HAND));
	public static EnchantmentBase criticalStrike = register(new EnchantmentCriticalStrike("criticalstrike", Enchantment.Rarity.valueOf(ModConfig.rarity.criticalStrike), Slots.HAND));
	public static EnchantmentBase cryogenic = register(new EnchantmentCryogenic("cryogenic", Enchantment.Rarity.valueOf(ModConfig.rarity.cryogenic), Slots.HAND));
	public static EnchantmentBase culling = register(new EnchantmentCulling("culling", Enchantment.Rarity.valueOf(ModConfig.rarity.culling), Slots.HAND));
	public static EnchantmentBase darkShadows = register(new EnchantmentDarkShadows("darkshadows", Enchantment.Rarity.valueOf(ModConfig.rarity.darkShadows), Slots.HAND));
	public static EnchantmentBase defusingEdge = register(new EnchantmentDefusingEdge("defusingedge", Enchantment.Rarity.valueOf(ModConfig.rarity.defusingEdge), Slots.HAND));
	public static EnchantmentBase desolator = register(new EnchantmentDesolator("desolator", Enchantment.Rarity.valueOf(ModConfig.rarity.desolator), Slots.HAND));
	public static EnchantmentBase difficultysEndowment = register(new EnchantmentDifficultysEndowment("difficultysendowment", Enchantment.Rarity.valueOf(ModConfig.rarity.difficultysEndowment), Slots.HAND));
	public static EnchantmentBase disarmament = register(new EnchantmentDisarmament("disarmament", Enchantment.Rarity.valueOf(ModConfig.rarity.disarmament), Slots.HAND));
	public static EnchantmentBase disorientatingBlade = register(new EnchantmentDisorientatingBlade("disorientatingblade", Enchantment.Rarity.valueOf(ModConfig.rarity.disorientatingBlade), Slots.HAND));
	public static EnchantmentBase empoweredDefence = register(new EnchantmentEmpoweredDefence("empowereddefence", Enchantment.Rarity.valueOf(ModConfig.rarity.empoweredDefence), Slots.HAND));
	public static EnchantmentBase envenomed = register(new EnchantmentEnvenomed("envenomed", Enchantment.Rarity.valueOf(ModConfig.rarity.envenomed), Slots.HAND));
	public static EnchantmentBase evasion = register(new EnchantmentEvasion("evasion", Enchantment.Rarity.valueOf(ModConfig.rarity.evasion), Slots.LEGS));
	public static EnchantmentBase fieryEdge = register(new EnchantmentFieryEdge("fieryedge", Enchantment.Rarity.valueOf(ModConfig.rarity.fieryEdge), Slots.HAND));
	public static EnchantmentBase flinging = register(new EnchantmentFlinging("flinging", Enchantment.Rarity.valueOf(ModConfig.rarity.flinging), Slots.HAND));
	public static EnchantmentBase horsDeCombat = register(new EnchantmentHorsDeCombat("horsdecombat", Enchantment.Rarity.valueOf(ModConfig.rarity.horsDeCombat), Slots.HAND));
	public static EnchantmentBase inhumane = register(new EnchantmentInhumane("inhumane", Enchantment.Rarity.valueOf(ModConfig.rarity.inhumane), Slots.HAND));
	public static EnchantmentBase innerBerserk = register(new EnchantmentInnerBerserk("innerberserk", Enchantment.Rarity.valueOf(ModConfig.rarity.innerBerserk), Slots.CHEST));
	public static EnchantmentBase jaggedRake = register(new EnchantmentJaggedRake("jaggedrake", Enchantment.Rarity.valueOf(ModConfig.rarity.jaggedRake), Slots.MAINHAND));
	public static EnchantmentBase levitator = register(new EnchantmentLevitator("levitator", Enchantment.Rarity.valueOf(ModConfig.rarity.levitator), Slots.HAND));
	public static EnchantmentBase lifesteal = register(new EnchantmentLifesteal("lifesteal", Enchantment.Rarity.valueOf(ModConfig.rarity.lifesteal), Slots.HAND));
	public static EnchantmentBase lightWeight = register(new EnchantmentLightWeight("lightweight", Enchantment.Rarity.valueOf(ModConfig.rarity.lightWeight), Slots.FEET));
	public static EnchantmentBase luckMagnification = register(new EnchantmentLuckMagnification("luckmagnification", Enchantment.Rarity.valueOf(ModConfig.rarity.luckMagnification), Slots.HAND));
	public static EnchantmentBase lunasBlessing = register(new EnchantmentLunasBlessing("lunasblessing", Enchantment.Rarity.valueOf(ModConfig.rarity.lunasBlessing), Slots.HAND));
	public static EnchantmentBase magicProtection = register(new EnchantmentMagicProtection("magicprotection", Enchantment.Rarity.valueOf(ModConfig.rarity.magicProtection), Slots.BODY));
	public static EnchantmentBase magmaWalker = register(new EnchantmentMagmaWalker("magmawalker", Enchantment.Rarity.valueOf(ModConfig.rarity.magmaWalker), Slots.FEET));
	public static EnchantmentBase moisturized = register(new EnchantmentMoisturized("moisturized", Enchantment.Rarity.valueOf(ModConfig.rarity.moisturized), Slots.MAINHAND));
	public static EnchantmentBase mortalitas = register(new EnchantmentMortalitas("mortalitas", Enchantment.Rarity.valueOf(ModConfig.rarity.mortalitas), Slots.HAND));
	public static EnchantmentBase naturalBlocking = register(new EnchantmentNaturalBlocking("naturalblocking", Enchantment.Rarity.valueOf(ModConfig.rarity.naturalBlocking), Slots.HAND));
	public static EnchantmentBase parry = register(new EnchantmentParry("parry", Enchantment.Rarity.valueOf(ModConfig.rarity.parry), Slots.HAND));
	public static EnchantmentBase penetratingEdge = register(new EnchantmentPenetratingEdge("penetratingedge", Enchantment.Rarity.valueOf(ModConfig.rarity.penetratingEdge), Slots.HAND));
	public static EnchantmentBase physicalProtection = register(new EnchantmentPhysicalProtection("physicalprotection", Enchantment.Rarity.valueOf(ModConfig.rarity.physicalProtection), Slots.BODY));
	public static EnchantmentBase purgingBlade = register(new EnchantmentPurgingBlade("purgingblade", Enchantment.Rarity.valueOf(ModConfig.rarity.purgingBlade), Slots.HAND));
	public static EnchantmentBase purification = register(new EnchantmentPurification("purification", Enchantment.Rarity.valueOf(ModConfig.rarity.purification), Slots.HAND));
	public static EnchantmentBase pushing = register(new EnchantmentPushing("pushing", Enchantment.Rarity.valueOf(ModConfig.rarity.pushing), Slots.HAND));
	public static EnchantmentBase rainsBestowment = register(new EnchantmentRainsBestowment("rainsbestowment", Enchantment.Rarity.valueOf(ModConfig.rarity.rainsBestowment), Slots.HAND));
	public static EnchantmentBase reviledBlade = register(new EnchantmentReviledBlade("reviledblade", Enchantment.Rarity.valueOf(ModConfig.rarity.reviledBlade), Slots.HAND));
	public static EnchantmentBase reinforcedsharpness = register(new EnchantmentReinforcedSharpness("reinforcedsharpness", Enchantment.Rarity.valueOf(ModConfig.rarity.reinforcedsharpness), Slots.MAINHAND));
	public static EnchantmentBase smelter = register(new EnchantmentSmelter("smelter", Enchantment.Rarity.valueOf(ModConfig.rarity.smelter), Slots.MAINHAND));
	public static EnchantmentBase solsBlessing = register(new EnchantmentSolsBlessing("solsblessing", Enchantment.Rarity.valueOf(ModConfig.rarity.solsBlessing), Slots.HAND));
	public static EnchantmentBase spellBreaker = register(new EnchantmentSpellBreaker("spellbreaker", Enchantment.Rarity.valueOf(ModConfig.rarity.spellBreaker), Slots.HAND));
	public static EnchantmentBase splitShot = register(new EnchantmentSplitshot("splitshot", Enchantment.Rarity.valueOf(ModConfig.rarity.splitShot), Slots.HAND));
	public static EnchantmentBase strafe = register(new EnchantmentStrafe("strafe", Enchantment.Rarity.valueOf(ModConfig.rarity.strafe), Slots.HAND));
	public static EnchantmentBase strengthenedVitality = register(new EnchantmentStrengthenedVitality("strengthenedvitality", Enchantment.Rarity.valueOf(ModConfig.rarity.strengthenedVitality), Slots.CHEST));
	public static EnchantmentBase swifterSlashes = register(new EnchantmentSwifterSlashes("swifterslashes", Enchantment.Rarity.valueOf(ModConfig.rarity.swifterSlashes), Slots.HAND));
	public static EnchantmentBase thunderstormsBestowment = register(new EnchantmentThunderstormsBestowment("thunderstormsbestowment", Enchantment.Rarity.valueOf(ModConfig.rarity.thunderstormsBestowment), Slots.HAND));
	public static EnchantmentBase trueStrike = register(new EnchantmentTrueStrike("truestrike", Enchantment.Rarity.valueOf(ModConfig.rarity.trueStrike), Slots.HAND));
	public static EnchantmentBase swiftSwimming = register(new EnchantmentSwiftSwimming("swiftswimming", Enchantment.Rarity.valueOf(ModConfig.rarity.swiftSwimming), Slots.FEET));
	public static EnchantmentBase unreasonable = register(new EnchantmentUnreasonable("unreasonable", Enchantment.Rarity.valueOf(ModConfig.rarity.unreasonable), Slots.HAND));
	public static EnchantmentBase unsheathing = register(new EnchantmentUnsheathing("unsheathing", Enchantment.Rarity.valueOf(ModConfig.rarity.unsheathing), Slots.MAINHAND));
	public static EnchantmentBase upgradedPotentials = register(new EnchantmentUpgradedPotentials("upgradedpotentials", Enchantment.Rarity.valueOf(ModConfig.rarity.upgradedPotentials), Slots.MAINHAND));
	public static EnchantmentBase viper = register(new EnchantmentViper("viper", Enchantment.Rarity.valueOf(ModConfig.rarity.viper), Slots.HAND));
	public static EnchantmentBase waterAspect = register(new EnchantmentWaterAspect("wateraspect", Enchantment.Rarity.valueOf(ModConfig.rarity.waterAspect), Slots.HAND));
	public static EnchantmentBase plowing = register(new EnchantmentPlowing("plowing", Enchantment.Rarity.valueOf(ModConfig.rarity.plowing), Slots.MAINHAND));
	public static EnchantmentBase wintersGrace = register(new EnchantmentWintersGrace("wintersgrace", Enchantment.Rarity.valueOf(ModConfig.rarity.wintersGrace), Slots.HAND));

	//Curses
	public static EnchantmentBase ascetic = register(new EnchantmentAscetic("ascetic", Enchantment.Rarity.valueOf(ModConfig.rarity.ascetic), Slots.HAND));
	public static EnchantmentBase bluntness = register(new EnchantmentBluntness("bluntness", Enchantment.Rarity.valueOf(ModConfig.rarity.bluntness), Slots.HAND));
	public static EnchantmentBase breachedPlating = register(new EnchantmentBreachedPlating("breachedplating", Enchantment.Rarity.valueOf(ModConfig.rarity.breachedPlating), Slots.BODY));
	public static EnchantmentBase cursedEdge = register(new EnchantmentCursedEdge("cursededge", Enchantment.Rarity.valueOf(ModConfig.rarity.cursedEdge), Slots.HAND));
	public static EnchantmentBase curseOfDecay = register(new EnchantmentCurseofDecay("curseofdecay", Enchantment.Rarity.valueOf(ModConfig.rarity.curseOfDecay), Slots.ALL));
	public static EnchantmentBase curseOfHolding = register(new EnchantmentCurseofHolding("curseofholding", Enchantment.Rarity.valueOf(ModConfig.rarity.curseOfHolding), Slots.ALL));
	public static EnchantmentBase curseOfInaccuracy = register(new EnchantmentCurseofInaccuracy("curseofinaccuracy", Enchantment.Rarity.valueOf(ModConfig.rarity.curseOfInaccuracy), Slots.HAND));
	public static EnchantmentBase curseOfPossession = register(new EnchantmentCurseofPossession("curseofpossession", Enchantment.Rarity.valueOf(ModConfig.rarity.curseOfPossession), Slots.ALL));
	public static EnchantmentBase curseOfVulnerability = register(new EnchantmentCurseofVulnerability("curseofvulnerability", Enchantment.Rarity.valueOf(ModConfig.rarity.curseOfVulnerability), Slots.BODY));
	public static EnchantmentBase dragging = register(new EnchantmentDragging("dragging", Enchantment.Rarity.valueOf(ModConfig.rarity.dragging), Slots.HAND));
	public static EnchantmentBase extinguish = register(new EnchantmentExtinguish("extinguish", Enchantment.Rarity.valueOf(ModConfig.rarity.extinguish), Slots.HAND));
	public static EnchantmentBase heavyWeight = register(new EnchantmentHeavyWeight("heavyweight", Enchantment.Rarity.valueOf(ModConfig.rarity.heavyWeight), Slots.ALL));
	public static EnchantmentBase inefficient = register(new EnchantmentInefficient("inefficient", Enchantment.Rarity.valueOf(ModConfig.rarity.inefficient), Slots.MAINHAND));
	public static EnchantmentBase instability = register(new EnchantmentInstability("instability", Enchantment.Rarity.valueOf(ModConfig.rarity.instability), Slots.HAND));
	public static EnchantmentBase meltdown = register(new EnchantmentMeltdown("meltdown", Enchantment.Rarity.valueOf(ModConfig.rarity.meltdown), Slots.CHEST));
	public static EnchantmentBase pandorasCurse = register(new EnchantmentPandorasCurse("pandorascurse", Enchantment.Rarity.valueOf(ModConfig.rarity.pandorasCurse), Slots.ALL));
	public static EnchantmentBase powerless = register(new EnchantmentPowerless("powerless", Enchantment.Rarity.valueOf(ModConfig.rarity.powerless), Slots.HAND));
	public static EnchantmentBase rusted = register(new EnchantmentRusted("rusted", Enchantment.Rarity.valueOf(ModConfig.rarity.rusted), Slots.ALL));
	public static EnchantmentBase unpredictable = register(new EnchantmentUnpredictable("unpredictable", Enchantment.Rarity.valueOf(ModConfig.rarity.unpredictable), Slots.HAND));

	//Rune
	public static EnchantmentBase runeArrowPiercing = register(new EnchantmentRuneArrowPiercing("rune_arrowpiercing", Enchantment.Rarity.valueOf(ModConfig.rarity.runeArrowPiercing), Slots.HAND));
	public static EnchantmentBase runeMagicalBlessing = register(new EnchantmentRuneMagicalBlessing("rune_magicalblessing", Enchantment.Rarity.valueOf(ModConfig.rarity.runeMagicalBlessing), Slots.HAND));
	public static EnchantmentBase runePiercingCapabilities = register(new EnchantmentRunePiercingCapabilities("rune_piercingcapabilities", Enchantment.Rarity.valueOf(ModConfig.rarity.runePiercingCapabilities), Slots.HAND));
	public static EnchantmentBase runeResurrection = register(new EnchantmentRuneResurrection("rune_resurrection", Enchantment.Rarity.valueOf(ModConfig.rarity.runeResurrection), Slots.HAND));
	public static EnchantmentBase runeRevival = register(new EnchantmentRuneRevival("rune_revival", Enchantment.Rarity.valueOf(ModConfig.rarity.runeRevival), Slots.HAND));

	//Subject
	public static EnchantmentBase subjectBiology = register(new EnchantmentSubjectEnchantments("subjectbiology", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectBiology),0, Slots.HAND));
	public static EnchantmentBase subjectChemistry = register(new EnchantmentSubjectEnchantments("subjectchemistry", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectChemistry),1, Slots.HAND));
	public static EnchantmentBase subjectEnglish = register(new EnchantmentSubjectEnchantments("subjectenglish", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectEnglish),2, Slots.HAND));
	public static EnchantmentBase subjectHistory = register(new EnchantmentSubjectEnchantments("subjecthistory", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectHistory),3, Slots.HAND));
	public static EnchantmentBase subjectMathematics = register(new EnchantmentSubjectEnchantments("subjectmathematics", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectMathematics),4, Slots.HAND));
	public static EnchantmentBase subjectPE = register(new EnchantmentSubjectEnchantments("subjectpe", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectPE),5, Slots.HAND));
	public static EnchantmentBase subjectPhysics = register(new EnchantmentSubjectEnchantments("subjectphysics", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectPhysics),6, Slots.HAND));
	public static EnchantmentBase subjectGeography = register(new EnchantmentSubjectEnchantments("subjectgeography", Enchantment.Rarity.valueOf(ModConfig.rarity.subjectGeography),7, Slots.HAND));

	//Lesser
	public static EnchantmentBase lesserBaneOfArthropods = register(new EnchantmentTierDamage("lesserbaneofarthropods", Enchantment.Rarity.valueOf(ModConfig.rarity.lesserBaneOfArthropods),6, Slots.HAND));
	public static EnchantmentBase lesserFireAspect = register(new EnchantmentTierFA("lesserfireaspect", Enchantment.Rarity.valueOf(ModConfig.rarity.lesserFireAspect),0, Slots.HAND));
	public static EnchantmentBase lesserFlame = register(new EnchantmentTierFlame("lesserflame", Enchantment.Rarity.valueOf(ModConfig.rarity.lesserFlame),0, Slots.HAND));
	public static EnchantmentBase lesserSharpness = register(new EnchantmentTierDamage("lessersharpness", Enchantment.Rarity.valueOf(ModConfig.rarity.lesserSharpness),0, Slots.HAND));
	public static EnchantmentBase lesserSmite = register(new EnchantmentTierDamage("lessersmite", Enchantment.Rarity.valueOf(ModConfig.rarity.lesserSmite),3, Slots.HAND));

	//Advanced
	public static EnchantmentBase advancedBaneOfArthropods = register(new EnchantmentTierDamage("advancedbaneofarthropods", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedBaneOfArthropods), 7, Slots.HAND));
	public static EnchantmentBase advancedBlastProtection = register(new EnchantmentAdvancedBlastProtection("advancedblastprotection", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedBlastProtection), Slots.BODY));
	public static EnchantmentBase advancedEfficiency = register(new EnchantmentAdvancedEfficiency("advancedefficiency", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedEfficiency), Slots.MAINHAND));
	public static EnchantmentBase advancedFeatherFalling = register(new EnchantmentAdvancedFeatherFalling("advancedfeatherfalling", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedFeatherFalling), Slots.FEET));
	public static EnchantmentBase advancedFireAspect = register(new EnchantmentTierFA("advancedfireaspect", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedFireAspect),1, Slots.HAND));
	public static EnchantmentBase advancedFireProtection = register(new EnchantmentAdvancedFireProtection("advancedfireprotection", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedFireProtection), Slots.BODY));
	public static EnchantmentBase advancedFlame = register(new EnchantmentTierFlame("advancedflame", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedFlame),1, Slots.HAND));
	public static EnchantmentBase advancedKnockback = register(new EnchantmentAdvancedKnockback("advancedknockback", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedKnockback), Slots.HAND));
	public static EnchantmentBase advancedLooting = register(new EnchantmentAdvancedLooting("advancedlooting", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedLooting), Slots.HAND));
	public static EnchantmentBase advancedLuckOfTheSea = register(new EnchantmentAdvancedLuckOfTheSea("advancedluckofthesea", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedLuckOfTheSea), Slots.HAND));
	public static EnchantmentBase advancedLure = register(new EnchantmentAdvancedLure("advancedlure", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedLure), Slots.HAND));
	public static EnchantmentBase advancedMending = register(new EnchantmentAdvancedMending("advancedmending", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedMending), Slots.ALL));
	public static EnchantmentBase advancedPower = register(new EnchantmentAdvancedPower("advancedpower", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedPower), Slots.HAND));
	public static EnchantmentBase advancedProjectileProtection = register(new EnchantmentAdvancedProjectileProtection("advancedprojectileprotection", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedProjectileProtection), Slots.BODY));
	public static EnchantmentBase advancedProtection = register(new EnchantmentAdvancedProtection("advancedprotection", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedProtection), Slots.BODY));
	public static EnchantmentBase advancedPunch = register(new EnchantmentAdvancedPunch("advancedpunch", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedPunch), Slots.HAND));
	public static EnchantmentBase advancedSharpness = register(new EnchantmentTierDamage("advancedsharpness", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedSharpness), 1, Slots.HAND));
	public static EnchantmentBase advancedSmite = register(new EnchantmentTierDamage("advancedsmite", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedSmite), 4, Slots.HAND));
	public static EnchantmentBase advancedThorns = register(new EnchantmentAdvancedThorns("advancedthorns", Enchantment.Rarity.valueOf(ModConfig.rarity.advancedThorns), Slots.ALL));
	//Supreme
	public static EnchantmentBase supremeBaneOfArthropods = register(new EnchantmentTierDamage("supremebaneofarthropods", Enchantment.Rarity.valueOf(ModConfig.rarity.supremeBaneOfArthropods),8, Slots.HAND));
	public static EnchantmentBase supremeFireAspect = register(new EnchantmentTierFA("supremefireaspect", Enchantment.Rarity.valueOf(ModConfig.rarity.supremeFireAspect),2, Slots.HAND));
	public static EnchantmentBase supremeFlame = register(new EnchantmentTierFlame("supremeflame", Enchantment.Rarity.valueOf(ModConfig.rarity.supremeFlame),2, Slots.HAND));
	public static EnchantmentBase supremeProtection = register(new EnchantmentSupremeProtection("supremeprotection", Enchantment.Rarity.VERY_RARE, Slots.BODY));
	public static EnchantmentBase supremeSharpness = register(new EnchantmentTierDamage("supremesharpness", Enchantment.Rarity.valueOf(ModConfig.rarity.supremeSharpness),2, Slots.HAND));
	public static EnchantmentBase supremeSmite = register(new EnchantmentTierDamage("supremesmite", Enchantment.Rarity.valueOf(ModConfig.rarity.supremeSmite),5, Slots.HAND));
	
	private static EnchantmentBase register(EnchantmentBase enchantment) {
		if(!ModConfig.miscellaneous.dontRegisterDisabledEnchants || enchantment.isEnabled()) {
			enchantmentSet.add(enchantment);
		}
		return enchantment;
	}

	public static void handleSubscribers() {
		for(EnchantmentBase enchantment : enchantmentSet) {
			if(enchantment.isEnabled() && enchantment.hasSubscriber()) MinecraftForge.EVENT_BUS.register(enchantment);
		}
		if(CompatUtil.isRLCombatLoaded()) {
			MinecraftForge.EVENT_BUS.register(RLCombatCompat.class);
		}
	}

	public static void initIncompatLists() {
		for(EnchantmentBase enchantment: enchantmentSet) {
			enchantment.incompatibleEnchantments = ConfigProvider.getIncompatibleEnchantmentsFromConfig(enchantment);
		}
	}

	@Mod.EventBusSubscriber(modid = SoManyEnchantments.MODID)
	public static class EventSubscriber {

		@SubscribeEvent
		public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
			IForgeRegistry<Enchantment> registry = event.getRegistry();
			for(EnchantmentBase enchant : enchantmentSet) {
				registry.register(enchant);
			}
		}
	}
}