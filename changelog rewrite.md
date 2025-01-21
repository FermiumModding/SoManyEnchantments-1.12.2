## 1.0.0 Rewrite
- broad rewrite/refactor of entire mod by fonnymunkey and nischhelm
- see commits for full details, this document is shortened and will be missing full info
- setup config groups such as enchantment application, enchantability, incompatibilities, upgrading, etc.
- merged existing fixes/features from RLMixins
- enchantment compatibility/incompatibility with other enchantments is now defined through config
- enchantment application to weapons/weapon types as well as what defines the weapon types is now defined through config
- rebalanced all enchantments, including default compatibilities and damage values
- rewrote all enchantments to clean up bugs and standardize handling
- added enchantment upgrading system accessible through the vanilla enchanting table
- moved from ASM to Mixin
- add optional feature to make villagers rerolled with zombies keep their trades
- add ability to blacklist enchantments from appearing through various sources such as loot or trades
- fully reworked how some enchantments behave such as unsheathing, true strike, or subject enchants
- add compat for multiple mods such as RLCombat, Lycanites Mobs, Spartan Weaponry, SpawnerControl, etc.

## Enchantment Renames
- Well Tilled: Moisturized
- Tilling Power: Plowing
- Sharper(ed) Edge: Reinforced Sharpness
- Pulling/Drag: Dragging
- Clear Skies' Favor: Clearskies' Favor
- Lunars Blessin: Lunas Blessing
- Freezing: Cryogenic
- Combat Veterancy: Combat Medic
- Underwater Strider: Swift Swimming

## Enchantment Removals
- Quarry
- Subject Science

## Enchantment additions
- Supreme Protection
- Breached Plating
- Ascetic
- Subject Biology
- Subject Chemistry
- Subject Physics

## More detailed changes (Not fully encompassing rewrite)
- Pushing.repelEntitiesInAABBFromPoint math cleanup
  - capped denominators to avoid dividing by 0 or negative numbers
- Both Ancient enchants implement IAncient
- Adv Thorns and Burning Thorns search through the whole equipment, not just armor to do dura dmg
  - this is to allow them on shields for example or on any other equipment on main/offhand if wanted
- Burning Thorns properly does dura dmg now (was searching for an item that has Thorns, not burn thorns)
- Added IEnchantmentFire to move fireticks per level to enchant and be able to call that from arc slash
- Cryogenic code cleanup having 5 lines per block thats turned to ice and having lots of code to differentiate between players and non-players just to add one extra amplifier
- Cleaned up OtherHandler."enchHand", renamed to subjectHandler
- Flame Tier Types set to Bow (why was it Weapon=Sword?)
- Cleaned up TierDamage - only actual change is entitydamagedalt mistakingly checking 5 and 6 instead of 4 and 5 (both BoA's)
- Cleaned up Weather Enchants except for rain having an if bracket thats never called - unsure whether to fix and thus nerf it or to delete it
- Cleaned up CombatMedic code - seems op
- Cleaned up CounterAttack - mainly deleting commented out code and replaced 20 with maxhurtresistanttime
- fixed unreasonable always proccing (roll was wrong) and not stopping to go through entity list after one random target was found
- cleaned up rune magical blessing and fixed it only working (and crashing) if the player DOESNT have it
- Refactored and simplified everything
- Buffed Sol/Luna blessing to not be effectively curses, gives buff in correct time, lesser buff if underground, no buff if incorrect time
- Reworked Rune Revival to be mixin to vastly simplify/fix handling
- Fixed bugs with evasion potion divide by 0 and hurt resistance timings
- Reworked magmawalker into mixin
- Reworked and fixed attribute handling of strengthened vitality and underwater strider
- Simplified strafe draw speed calculations
- Added fixes for curses handling from RLMixins
- Reworked rune enchants, magical/resurrection/revival should now work properly, resurrection is now a shield enchant
- Reworked and improved smelter
- Removed quarrying because it seemingly is only a dupe generator
- Add config option for making advanced mending prioritize damaged items
- Rework adept to give a boost to boss xp and compat for blights
- Fix advanced mending being applicable with infinity
- Rework ancient sealed curses to work better
- Rework and simplify ancient sword mastery with mixin, increases damage increase of damage increasing enchants by 25% per level
- Rework dark shadows to only be based on attacker's light level, and make blindness chance scaling
- Add cooled strength/low damage checks to enchants to prevent triggered from click spam
- merged advanced sharp/smite/arthro into EnchantmentTierDamage
- simplify and buff damage from Butchering
- improve defusing from DefusingEdge
- buff penetrating edge to scale better with more armor
- buff reinforced sharpness and remove iframe abuse glitch
- fix supreme smite missing extra effects
- heavily simplify water aspect
- Slightly buff LuckMagnification to bring it a little closer to CriticalStrike
- Critical Strike and Luck Magnification moved to their own incompat list since they affect crits and not base damage
- Add Hardcore mode buff to DifficultysEndowment
- First pass rebalance damagemult enchants, assuming max level:
- DifficultysEndowment: Hard 1.5x Hardcore 2x
- AshDestroyer: 2x when victim is on fire
- ReviledBlade: 1x - 3x depending on victim health, increasing with less health
- CursedEdge: 3x but self-damaging at 0.25x of dealt damage as magic
- Instability: 1x - 3.25x depending on item health, increasing with more item damage, still incompat with unbreaking, deals additional damage to item
- Rework Culling to properly give all skulls, make it autokill targets if an attack would leave them below a certain % of health instead of nonsense jumping mechanics
- Make TrueStrike bypass both Evasion and Parry, give it a slight chance to iframe bypass so its useful outside of pvp
- Rework Unsheathing to give bonus damage to the first attack after unsheathing a weapon for a small period of time
- Add death messages for culling and atomic deconstructor
- Moved Combat Medic from hand enchant to head enchant

## Todo
- Futher balancing
- More sound effects? (Like resurrection)