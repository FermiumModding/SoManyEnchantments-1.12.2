Last change: May 25 2026, unfinished

### General

* Added configs for enchantment rarity, enchantability, incompatibility, item applicability, etc.
* Added enchantment upgrading system accessible through the vanilla enchanting table. Only works for tier upgrades, costs biotite and lvls, doesnt increase anvil use cost
* Blacklisted some enchantments from appearing through various sources such as loot or trades (see SME.cfg @ blacklists)
* Merged existing fixes/features from RLMixins & RLTweaker
* Added compat for multiple mods such as RLCombat, Lycanites Mobs, Spartan Weaponry, SpawnerControl, etc.

### Enchantment (re-)additions

* Subject Biology (more dmg on infernal/blight/champion, through rlmixins. SME behavior: more dmg depending on ai task count/"smarter")
* Subject Physics (more dmg if big height difference)
* Subject Mathematics (more dmg if lots of xp on the player)
* Subject History (more dmg the longer you stay at one spot, through rlmixins. SME behavior: more dmg with local difficulty)
* Subject Geography (changes DDD dmg type of weapon depending on biome type through rlmixins. SME behavior: applies potion effects depending on biome type)
* Combat Medic (Helmet, increased healing)
* Rune: Magical Blessing (weapon: magic dmg, some armor ignore, random debuffs)
* Rune: Resurrection (totem on shields)
* Rune: Revival (not on armor: chance to not break the item on 0 dura)
* Ancient Sealed Curses (apply curses on target)
* Ancient Sword Mastery (more dmg the higher the targets atk stat)
* Counter Attack (non-magic thorns on weapon, boosted slightly if parry is also present)
* Supreme Protection (lol)

### New Curses

* Breached Plating (gives 10% op2 armor attribute debuff per lvl, stacking on the entire body)
* Ascetic (less/no loot/fish loot)
* Extinguish (removes fire)

### Enchantment removals (disabled through config)

* Subject English (more dmg the longer the mobs name)
* Quarrying (was disabled and didnt work anyway)

### New Incompat Rules (rest in individual groups)
* Made lesser/normal/adv/supreme enchants be applicable independently of each other so a weapon can have BoA + Adv Smite + Supr Sharp (but only one per tier and one per type)
    * Spell Breaker and Penetrating Edge are part of the supreme group
    * Blessed Edge is in the smite group
    * Bluntness is in the sharpness group
* Made Butchering/Defusing Edge/Inhumane/Water Aspect their own separate incompatibility group, not incompat with sharpness-likes anymore
* Made Crit Strike and Luck Magnification incompat with each other but with no other enchants (crit enchants)
* Simplified Dmg Multi Incompat Group, now contains Ash, Reviled, Instability, Cursed Edge
* Simplified Conditional Dmg Incompat Group, now only contains Mortalitas, Viper, Dark Shadows, those have no other incompats anymore

### Enchantment changes (incomplete):

#### armor - protection (mutually exclusive)

* Adv Blast/Fire/Proj/Prot
* Supr Prot - new
* Magic Prot
* Phys Prot

#### armor - thorns (mutually exclusive)

* Adv Thorns
* Burn Thorns - fixed not taking dura dmg (more exactly: only if another piece had normal thorns)

#### armor - other

* Combat Medic - new, Helmet, increased healing
* Evasion - fixed iframe change if max iframes are modified (now max +0/+5/+10, was 20/25/30)
* Inner Berserk
* Light Weight
* Magma Walker - swapped real magma with temporary magma blocks that re-melt when lava is nearby, now incompat with frost walker
* Str Vit - simplified code
* Swift Swimming - renamed from UW Strider, simplified code

#### bow

* Adv Pow
* Adv Punch
* Pushing
* Splitshot
* Strafe - Simplified draw speed calculation
* Lesser/Adv/Supr Flame - fix item type being sword

#### curses

* Ascetic
* Bluntness
* Breached Plating - incompat with phys prot
* Cursed Edge - changed self hit dmg type from generic to magic, buffed to up to x3 dmg (from x2), simplified calcs
* C.o. Decay
* C.o. Holding
* C.o. Inacc
* C.o. Poss
* C.o. Vuln - incompat with Combat Medic, Str Vit
* Dragging - now a curse, fixed potential crash if lvl >5
* Extinguish
* Heavy Weight - incompat with Swifter Slashes, Evasion, Light Weight
* Inefficient
* Instability - still 1x - 3.25x (max if item 0 dura left). added divide by zero safety for high lvl instability
* Meltdown - now a curse, now incompat with any thorns
* Pandoras Curse - avoid cursing stackables
* Rusted - incompat with Depth Str., Swift Swimming, Instability
* Unpredictable

#### fishing

* Adv Luck of the Sea
* Adv Lure

#### hoe

* Jagged Rake
* Moisturized
* Plowing

#### rune

* R: Arrow Piercing
* R: Magical Blessing, re-added and fixed, weapon: magic dmg, some armor ignore, random debuffs
* R: Piercing Cap
* R: Resurrection, re-added and fixed, totem on shields
* R: Revival, re-added and fixed, not on armor: chance to not break the item on 0 dura, added sound

#### shield

* Burn Shield
* Emp Def
* Nat Block - Natural Blocking now triggers before armor calc (so it also reduces durability damage on armor), but got nerfed to max 20% DR

#### tool

* Adv Eff - Fixed Advanced Efficiency to apply correctly on items that aren't mining the block they are the correct tool for (an L for saw gang)
* Reinf Sharpness - buffed (now +3.3/+4.6/+5.9/+7.2/+8.5). removed iframe abuse glitch
* Smelter - simplified and restricted. now checks if tool can break the block. also fixed soft incompat with magnetic

#### weapon - ancient

* Anc Sealed Curses - re-added and fixed, apply curses on target
* Anc Sword Mastery - re-added and fixed, more dmg the higher the targets atk stat

#### weapon - conditionaldamage (mutually exclusive)

* Dark Shadows - Increased dmg (+1+2.5*lvl, was +0.75*lvl), simplified conditions, only attacker needs to be in darkness, added scaling to blindness chance (10%/20%/30%, was 10%)
* Mortalitas - Made Mortalitas only incompatible with Viper and Dark Shadows (conditional dmg enchants)
* Viper - Fixed Viper adding 1 dmg flat at any lvl

#### weapon - crits (mutually exclusive)

* Crit Strike - simplified calc, now x4.5-x6.5 when successful (was x3.5-x4)
* Luck Magn - buffed to be closer to crit strike. added increased crit chance (1%/2% per luck per lvl, up to 20% at 10 luck & lvl 2). if crit, now has 2%/4% per luck per lvl (up to 20% at 5 luck & lvl 2) chance to increase crit dmg multi by +0.1 per luck per lvl (max +2 so x3.5 instead of x1.5)

#### weapon - damage (mutually exclusive subgroup: butchering, defusingedge, inhumane, wateraspect)

* Butchering - fixed and buffed damage (+2 per lvl), added looting for animals
* Defusing Edge - improved defusion handling
* Inhumane - now also triggers on Vexes and Witches
* Water Aspect - heavily simplified. add up: if target wet +0.75*lvl, if attacker wet +0.75*lvl, if enderman, blaze or magma cube +2.5*lvl
* Penetrating Edge - added dmg cap (+15), buff to scale better with more armor (from +0.5 +0.33*armor +0.16*lvl to +0.0833*armor*lvl, so up to +0.5*armor)
* Spell Breaker - added dmg cap (+15), increased witch+evoker dmg from +1.5*lvl to +1.75*lvl, re-increased dmg per effect from 0.5*lvl to original SME 0.5.5 0.625*lvl (rlmixins override)
* Less/Adv/Sup Sharp/Smite/BoA - Fixed additional potion effects (slowness on all boa, weakness on adv+sup smite)

#### weapon - damagemultiplier (mutually exclusive + instability, cursed edge)

* Ash Destroyer - multiplier now depends on target burn duration (up to x2 at 32s)
* Difficultys Endowment - disabled, dmg multi depends on Game Difficulty, added hardcore mode multi (up to x2)
* Reviled Blade - buffed from x1-x2.3 to x1-x3 dmg (max ench lvl, enemy at 0% health), TODO prob needs to reach the max earlier than at 0% HP

#### weapon - potiondebuffer (mutually exclusive)

* Cryogenic - new Freezing. will now create ice blocks that turn into air when melting. code for ice block dramatically simplified, now symmetric
* Desolator
* Disor Blade
* Envenomed
* Hors De Combat - will now give various vanilla potion effects randomly
* Levitator
* Purification

#### weapon - selfheal (mutually exclusive)

* Blessed Edge
* Lifesteal

#### weapon - subject (mutually exclusive)

* Subject Biology - new, more dmg on infernal/blight/champion, through rlmixins. SME behavior: more dmg depending on ai task count/"smarter"
* Subject Chemistry - renamed from Subject Science, now explode size depends on potion effect count on target
* Subject English - disabled, now more dmg the longer the mobs name
* Subject History - re-added, now more dmg the longer you stay at one spot, through rlmixins. SME behavior: more dmg with local difficulty
* Subject Mathematics - re-added, now more dmg if lots of xp on the player
* Subject P.E.
* Subject Physics - new, more dmg if big height difference
* Subject Geography - new, changes DDD dmg type of weapon depending on biome type through rlmixins. SME behavior: applies potion effects depending on biome type

#### weapon - weather (mutually exclusive)

* Fully overhauled all calcs of all weather enchants.
* None of them give debuffs in the wrong conditions anymore
* If right conditions but underground they have a weak buff of dmg +0.5+0.25*lvl instead of their normal buff. winters has x2 (cold biome) or x3 (if also rain) that amount
* Clearskies - dmg +1+0.75*lvl, 0.3%*lvl chance to clear weather
* Rains - dmg +1.25+1*lvl, 0.2%*lvl chance to rain
* Thunderstorms - dmg +1.5+1.25*lvl, 0.1%*lvl chance to thunder
* Winters - if cold biome (biometemp <= 0.3): dmg +1.5+1.25*lvl (or dmg +2+1.5*lvl if also rain), apply slowness+mining fatigue with 4%*lvl chance
* Sols & Lunas (prev Lunars) - if correct daytime dmg +1.5+0.75*lvl, apply glowing or blindness if not underground

#### weapon - other

* Adv Knockback
* Adv Looting
* Arc  Slash
* At. Decon. - New death message "was reduced to atoms/was deconstructed by"
* Brutality - Fixed not scaling with enchant lvl
* Counter Atk - re-added + fully overhauled: non-magic thorns on weapon, boosted slightly if parry is also present
* Culling - Fixed: Properly gives all skulls, sets dmg to targetMaxHealth x 10 if an attack would leave them below 10%/12.5%/15% of health instead of jumping mechanics.
* Disarmament
* Fiery Edge
* Flinging
* Parry - boosted slightly if counter attack is also present, ignored if attacker has true strike
* Purging Blade - Fully overhauled. Now gives a dmg increase if a potion effect is removed, multiplier depending on that effects lvl (capped at total x2)
* Swifter Slashes
* Less/Adv/Supr Fire Aspect
* True Strike - Fixed being basically useless. Now gives 1%/2%/3% chance for iframe bypass, ignores enemy evasion+parry
* Unreasonable - Fixed various issues making it often not work, at least in survival
* Unsheathing - Fully overhauled behavior to not auto pull the weapon but instead increase dmg when hitting right after pulling the weapon out

#### other

* Adept - Nerf: removed adding a lot of xp on top of its percentage increase (added +2+level flat, now adds at least +(level+1)/2, but usually 15% per lvl / 50% per lvl for bosses/blights)
* Adv Mending - Added config option to prioritize damaged items, default incompatible with infinity
* Upg Potentials




### Attack Strength Scaling

#### general idea
- dmg multi -> no strength limit
- dmg add -> scale with strength
- potion add always -> do it with chance = strMulti, except if it applies only for certain creature types (like vanilla BoA)
- potion add chance -> chance scales with strength
- any crazy effects only on full hit
- iframe bypass on any strength

#### limited to full hits
- Atomic Deconstructor
- Culling
- Disarmament
- Cryogenic
- Critical Strike
- Luck Magnification
- Brutality
- Ancient Sealed Curses
- Purification conversion

#### scale with strength
- Rune Magical Blessing (%pierc + chance for debuffs)
- Rune Piercing Capabilities (%pierc)
- Purging Blade (chance)
- Unreasonable (chance)
- Desolator (chance for potion)
- Disorientating Blade (chance for potion)
- Envenomed (chance for potion)
- Hors de Combat (chance for potion)
- Purification (chance for potion)
- Blessed Edge (chance for potion)
- Subject (pe chance for potion, rest for added dmg)
- Mortalitas (added dmg)
- Viper (added dmg)
- Butchering (added dmg)
- Defusing Edge (added dmg, defusion as chance)
- Inhumane (added dmg)
- Penetrating Edge (added dmg)
- Spellbreaker (added dmg)
- Water Aspect (added dmg)
- Weather enchants (added dmg, weather change and potion effects with strength as chance)
- Dark Shadows (added dmg, blindness as chance)

#### no strength requirement
- Cursed Edge (dmg multi)
- Instability (dmg multi)
- Lifesteal (dmg multi)
- Ash Destroyer (dmg multi)
- Difficultys Endowment (dmg multi)
- Reviled Blade (dmg multi)
- Blessed Edge (dmg multi)
- Unsheathing (dmg multi)
- Flinging (knockback like)
- Levitator (knockback like)
- Swifter Slashes (iframe)
- Fiery Edge (iframe)
- True Strike (iframe)
- TierDamages (potion effects like vanilla BoA)
- Inhumane (potion effects like vanilla BoA)



### Enchantment Renames

* Lunars Blessing: Lunas Blessing
* Freezing: Cryogenic
* Underwater Strider: Swift Swimming
* Subject Science: Subject Chemistry (explosions if lots of potion effects on user)

### Enchantment Id Renames

* science → subjectchemistry
* english → subjectenglish
* mathematics → subjectmathematics
* pe → subjectpe
* history → subjecthistory
* lfl → lesserflame
* afl → advancedflame
* sfl → supremeflame
* lfa → lesserfireaspect
* afa → advancedfireaspect
* sfa → supremefireaspect
* underwaterstrider → swiftswimming
* scythedamage → jaggedrake
* welltilled → moisturized
* tillingpower → plowing
* clearsky → clearskiesfavor
* raining → rainsbestowment
* thunderstorm → thunderstormsbestowment
* winter → wintersgrace
* sunshine → solsblessing
* moonlight → lunasblessing
* ancientcurseinflicter → ancientsealedcurses
* swordmastery → ancientswordmastery
* hors_de_combat → horsdecombat
* advancedefficency → advancedefficiency
* inefficent → inefficient
* freezing → cryogenic
* disorientation → disorientatingblade
* defusion → defusingedge
* rune_armorpiercing → rune_arrowpiercing
* frenzy → unreasonable
* swiper → arcslash
* sharperedge → reinforcedsharpness
* fieryshield → burningshield
* upgrade → upgradedpotentials
* pandora → pandorascurse
* pulling → dragging