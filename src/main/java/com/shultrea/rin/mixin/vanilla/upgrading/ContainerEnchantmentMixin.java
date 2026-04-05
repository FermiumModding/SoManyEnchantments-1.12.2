package com.shultrea.rin.mixin.vanilla.upgrading;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.shultrea.rin.SoManyEnchantments;
import com.shultrea.rin.config.ModConfig;
import com.shultrea.rin.util.IContainerEnchantmentMixin;
import com.shultrea.rin.util.UpgradeRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Mixin(value = ContainerEnchantment.class, priority = 500)
public abstract class ContainerEnchantmentMixin extends Container implements IContainerEnchantmentMixin {

    @Shadow public IInventory tableInventory;
    @Shadow public int[] enchantLevels;
    @Shadow public int[] enchantClue;
    @Shadow public int[] worldClue;
    @Shadow @Final private World world;
    @Shadow @Final private BlockPos position;
    @Shadow @Final private Random rand;
    @Shadow public int xpSeed;

    @Shadow public abstract void detectAndSendChanges();

    @Unique private final UpgradeRecipe[] soManyEnchantments$currentRecipes = new UpgradeRecipe[3]; //NOT SYNCED
    @Unique private IInventory soManyEnchantments$upgradeTokenInventory;
    @Unique private int soManyEnchantments$bookshelfPower = 0;
    @Unique private boolean soManyEnchantments$tokenIsLapis = false;
    @Unique private final boolean[] soManyEnchantments$tokenIsCorrect = new boolean[3];

    @WrapOperation(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at= @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ContainerEnchantment;addSlotToContainer(Lnet/minecraft/inventory/Slot;)Lnet/minecraft/inventory/Slot;", ordinal = 1)
    )
    private Slot soManyEnchantments_vanillaContainerEnchantment_init(ContainerEnchantment instance, Slot slot, Operation<Slot> original) {
        //Make Lapis slot also allow upgrade tokens
        if(slot.getSlotIndex() == 1 && slot.xPos == 35 && slot.yPos == 47) {
            return original.call(instance, new Slot(this.tableInventory, 1, 35, 47) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return soManyEnchantments$isTokenLapis(stack) || UpgradeRecipe.stackIsValidUpgradeToken(stack);
                }
            });
        }
        return original.call(instance, slot);
    }

    @Inject(
            method = "<init>(Lnet/minecraft/entity/player/InventoryPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
            at = @At("TAIL")
    )
    private void setSoManyEnchantments_vanillaContainerEnchantment_init_addTokenSlots(InventoryPlayer playerInv, World worldIn, BlockPos pos, CallbackInfo ci) {
        //Add slots for the token item to allow easier syncing
        this.soManyEnchantments$upgradeTokenInventory = new InventoryBasic("Upgrade Tokens", true, 3);
        for (int i = 0; i < 3; i++) {
            this.addSlotToContainer(new Slot(this.soManyEnchantments$upgradeTokenInventory, i, 61, 15 + i * 19) {
                @Override public boolean canTakeStack(@Nonnull EntityPlayer playerIn) {
                    return false;
                }
                @Override public boolean isItemValid(@Nonnull ItemStack stack){
                    return false;
                }
                @Override @SideOnly(Side.CLIENT) public boolean isEnabled() {
                    return false;
                }
            });
        }
    }

    @Unique
    private void soManyEnchantments$setUpgradeToken(ItemStack stack, int slotId){
        Slot slot = this.getSlotFromInventory(this.soManyEnchantments$upgradeTokenInventory,slotId);
        if(slot != null) {
            if (stack.getMetadata() == 32767)
                slot.putStack(new ItemStack(stack.getItem(), stack.getCount(), 0));
            else
                slot.putStack(stack);
        } else {
            SoManyEnchantments.LOGGER.warn("Unable to find Upgrade Token Slot with id {}",slotId);
        }
    }
    
    @Inject(
            method = "broadcastData",
            at = @At(value = "RETURN")
    )
    private void soManyEnchantments_vanillaContainerEnchantment_broadcastData(IContainerListener crafting, CallbackInfo ci) {
        //Send data to client
        ContainerEnchantment thisContainer = (ContainerEnchantment) (Object) this;
        crafting.sendWindowProperty(thisContainer, 10, this.soManyEnchantments$bookshelfPower);
        crafting.sendWindowProperty(thisContainer, 11, this.soManyEnchantments$tokenIsLapis?1:0);
        crafting.sendWindowProperty(thisContainer, 12, this.soManyEnchantments$tokenIsCorrect[0]?1:0);
        crafting.sendWindowProperty(thisContainer, 13, this.soManyEnchantments$tokenIsCorrect[1]?1:0);
        crafting.sendWindowProperty(thisContainer, 14, this.soManyEnchantments$tokenIsCorrect[2]?1:0);
    }
    
    @Inject(
            method = "updateProgressBar",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void soManyEnchantments_vanillaContainerEnchantment_updateProgressBar(int id, int data, CallbackInfo ci) {
        //Update data on client
        switch (id){
            case 10: this.soManyEnchantments$bookshelfPower = data; ci.cancel(); break;
            case 11: this.soManyEnchantments$tokenIsLapis = data == 1; ci.cancel(); break;
            case 12: this.soManyEnchantments$tokenIsCorrect[0] = data == 1; ci.cancel(); break;
            case 13: this.soManyEnchantments$tokenIsCorrect[1] = data == 1; ci.cancel(); break;
            case 14: this.soManyEnchantments$tokenIsCorrect[2] = data == 1; ci.cancel(); break;
        }
    }

    /**---------------------- onCraftMatrixChanged ----------------------**/

    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/IInventory;getStackInSlot(I)Lnet/minecraft/item/ItemStack;")
    )
    private void soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_resetTokenValues(IInventory inventoryIn, CallbackInfo ci, @Share("tokenItem") LocalRef<ItemStack> tokenItem){
        //Client checks token as well, but gets updated by server once server finishes this method. Only matters for stack desyncs
        tokenItem.set(inventoryIn.getStackInSlot(1));
        this.soManyEnchantments$tokenIsLapis = soManyEnchantments$isTokenLapis(tokenItem.get());
        this.soManyEnchantments$tokenIsCorrect[0] = false;
        this.soManyEnchantments$tokenIsCorrect[1] = false;
        this.soManyEnchantments$tokenIsCorrect[2] = false;
    }

    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "FIELD", target = "Lnet/minecraft/inventory/ContainerEnchantment;worldClue:[I", ordinal = 2)
    )
    private void soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_resetIfFail(IInventory inventoryIn, CallbackInfo ci, @Share("alreadyReset") LocalBooleanRef alreadyReset){
        if(alreadyReset.get()) return;
        //Both client+serverside reset all their values if left slot has nothing or nothing enchantable/upgradeable
        soManyEnchantments$resetValues();
        alreadyReset.set(true);
    }

    @ModifyExpressionValue(
            method = "onCraftMatrixChanged",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemEnchantable()Z")
    )
    private boolean soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_allowUpgradingGenerally(boolean isEnchantable, @Local(name = "itemstack") ItemStack targetItem, @Share("itemIsEnchantable") LocalBooleanRef itemIsEnchantable, @Share("itemIsUpgradeable") LocalBooleanRef itemIsUpgradeable){
        itemIsEnchantable.set(isEnchantable);
        itemIsUpgradeable.set((targetItem.isItemEnchanted() || targetItem.getItem() == Items.ENCHANTED_BOOK) && (ModConfig.upgrade.allowLevelUpgrades || ModConfig.upgrade.allowTierUpgrades));

        return isEnchantable || itemIsUpgradeable.get();
    }

    @ModifyExpressionValue(
            method = "onCraftMatrixChanged",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z")
    )
    private boolean soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_resetValues(boolean original){
        if(!original)
            //Serverside reset of stored values, sent to client via detectAndSendChanges -> broadcastData
            soManyEnchantments$resetValues();

        return original;
    }

    @ModifyVariable(
            method = "onCraftMatrixChanged",
            at = @At("STORE"),
            name = "power"
    )
    private float soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_saveBookshelfPower(float original, @Share("bookshelfPower") LocalFloatRef bookshelfPower){
        //absolutely unnecessary, this is only to appease the intellij linter (not even the compiler)
        bookshelfPower.set(original);
        return original;
    }

    @Inject(
            method = "onCraftMatrixChanged",
            at = @At(value = "FIELD", target = "Lnet/minecraft/inventory/ContainerEnchantment;rand:Ljava/util/Random;", ordinal = 0),
            cancellable = true
    )
    private void soManyEnchantments_vanillaContainerEnchantment_onCraftMatrixChanged_upgrading(
            IInventory inventoryIn,
            CallbackInfo ci,
            //@Local(ordinal = 0) float bookshelfPower, //idk why intellij complains, debug log shows it finds the local, using Share instead now
            @Share("bookshelfPower") LocalFloatRef bookshelfPower,
            @Share("itemIsEnchantable") LocalBooleanRef itemIsEnchantable,
            @Share("itemIsUpgradeable") LocalBooleanRef itemIsUpgradeable,
            @Share("tokenItem") LocalRef<ItemStack> tokenItem
    ){
        ItemStack targetItem = inventoryIn.getStackInSlot(0); //could easily @Local it but intellij complains for no reason

        if(itemIsEnchantable.get()) return; //Default handling

        if(!itemIsUpgradeable.get()) { //Technically not needed
            ci.cancel();
            return;
        }

        //Check book only
        if(ModConfig.upgrade.onlyAllowOnBooks && targetItem.getItem() != Items.ENCHANTED_BOOK){
            detectAndSendChanges();
            ci.cancel();
            return;
        }

        //Get upgradeable enchant entries
        List<UpgradeRecipe> upgradeRecipes = new ArrayList<>();
        List<Integer> upgradeLevels = new ArrayList<>();
        for(UpgradeRecipe recipe : UpgradeRecipe.UPGRADE_RECIPES){
            int upgradedLevel = recipe.canUpgrade(targetItem);
            if(upgradedLevel != UpgradeRecipe.DENY) {
                upgradeRecipes.add(recipe);
                upgradeLevels.add(upgradedLevel);
            }
        }
        //Cancel if no valid entries were found
        if(upgradeRecipes.isEmpty()){
            detectAndSendChanges();
            ci.cancel();
            return;
        }

        //We have valid possible upgrade paths only now dependent on xp cost
        this.rand.setSeed((long)this.xpSeed);

        for(int i = 0; i < 3; i++) {
            //Recheck empty in case there is less potentials than buttons
            if(upgradeRecipes.isEmpty()) {
                this.soManyEnchantments$currentRecipes[i] = null;
                this.soManyEnchantments$setUpgradeToken(ItemStack.EMPTY, i);
                continue;
            }

            //Determine which possible upgrade to pick
            int randomIndex = this.rand.nextInt(upgradeRecipes.size());
            //TODO: weighted roll?
            UpgradeRecipe pickedRecipe = upgradeRecipes.get(randomIndex);
            int pickedLevel = upgradeLevels.get(randomIndex);
            //Remove picked potential so multiple buttons don't get duplicates
            upgradeRecipes.remove(randomIndex);
            upgradeLevels.remove(randomIndex);

            //Save if current token is correct for this recipe
            this.soManyEnchantments$tokenIsCorrect[i] = pickedRecipe.upgradeTokenIsValid(tokenItem.get());

            //Get xp level cost
            int levelCost;
            switch(ModConfig.upgrade.levelCostMode) {
                case 0: levelCost = 1; break;
                case 2: levelCost = pickedRecipe.getOutputEnchant().getMinEnchantability(pickedLevel); break;
                default:
                case 1: levelCost = pickedLevel * soManyEnchantments$getRarityMultiplier(pickedRecipe.getOutputEnchant().getRarity(), targetItem.getItem() == Items.ENCHANTED_BOOK); break;
            }
            levelCost = (int)(ModConfig.upgrade.levelCostMultiplier * levelCost);

            //Finally set arrays ready for upgrading
            this.enchantLevels[i] = levelCost;
            this.enchantClue[i] = ModConfig.upgrade.allowEnchantmentClues ? Enchantment.getEnchantmentID(pickedRecipe.getOutputEnchant()) : -1;
            this.worldClue[i] = ModConfig.upgrade.allowEnchantmentClues ? pickedLevel : -1;
            this.soManyEnchantments$currentRecipes[i] = pickedRecipe;
            this.soManyEnchantments$setUpgradeToken(pickedRecipe.getTokenCost(),i);
        }

        this.soManyEnchantments$bookshelfPower = (int)bookshelfPower.get();

        this.detectAndSendChanges();

        //Don't run enchanting code
        ci.cancel();
    }

    @Unique
    private void soManyEnchantments$resetValues(){
        for(int i = 0; i < 3; ++i) {
            this.enchantLevels[i] = 0;
            this.enchantClue[i] = -1;
            this.worldClue[i] = -1;
            this.soManyEnchantments$currentRecipes[i] = null;
            this.soManyEnchantments$setUpgradeToken(ItemStack.EMPTY, i);
        }
        this.soManyEnchantments$bookshelfPower = 0;
    }

    /**---------------------- enchantItem ----------------------**/

    @Inject(
            method = "enchantItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0),
            cancellable = true
    )
    private void soManyEnchantments_vanillaContainerEnchantment_enchantItem_sanityChecks(EntityPlayer playerIn, int id, CallbackInfoReturnable<Boolean> cir, @Local(name = "itemstack") ItemStack targetItem) {
        //Sanity checks for both enchanting and upgrading
        if (id < 0 || id > 2) cir.setReturnValue(false);
        if(targetItem.isEmpty()) cir.setReturnValue(false);
    }

    @WrapOperation(
            method = "enchantItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0)
    )
    private boolean soManyEnchantments_vanillaContainerEnchantment_enchantItem_switchToUpgrading(
            ItemStack instance,
            Operation<Boolean> original,
            @Local(name = "itemstack") ItemStack targetItem,
            @Local(name = "itemstack1") ItemStack tokenItem,
            @Local(argsOnly = true) int id,
            @Local(argsOnly = true) EntityPlayer playerIn,
            @Cancellable CallbackInfoReturnable<Boolean> cir
    ) {
        //Return early for normal enchanting, add condition that the lapis slot needs to have actual lapis
        if (this.soManyEnchantments$getUpgradeTokenCost(id).isEmpty())
            return original.call(instance) || !soManyEnchantments$tokenIsLapis;

        boolean isCreative = playerIn.capabilities.isCreativeMode;

        //Check bookshelf power
        if (!isCreative && this.soManyEnchantments$bookshelfPower < ModConfig.upgrade.bookshelvesNeeded) {
            cir.setReturnValue(false);
            return true; //don't run normal enchanting, return that tokenItem is empty
        }

        //Check xp cost
        int xpCost = this.enchantLevels[id];
        if (xpCost <= 0 || (!isCreative && playerIn.experienceLevel < xpCost)) {
            cir.setReturnValue(false);
            return true;
        }

        if (!this.world.isRemote) {
            UpgradeRecipe usedRecipe = this.soManyEnchantments$currentRecipes[id].getUsedRecipe(this.world.rand, false);
            //Sanity null check
            if (usedRecipe == null) {
                cir.setReturnValue(false);
                return true;
            }

            //Check token validity
            if (!isCreative && (tokenItem.isEmpty() || !usedRecipe.upgradeTokenIsValid(tokenItem))) {
                cir.setReturnValue(false);
                return true;
            }

            //Upgrade the enchants
            Map<Enchantment, Integer> resultingEnchants = usedRecipe.getOutputStackEnchants(targetItem);

            //Enchanted books don't get properly cleared first when setting new enchantments
            if (targetItem.getItem() == Items.ENCHANTED_BOOK) {
                NBTTagCompound tag = targetItem.getTagCompound();
                if (tag != null) {
                    tag.setTag("StoredEnchantments", new NBTTagList());
                    targetItem.setTagCompound(tag);
                }
            }

            //Set the new enchantments to the item
            EnchantmentHelper.setEnchantments(resultingEnchants, targetItem);

            //Remove levels
            playerIn.onEnchant(targetItem, xpCost);

            //Take tokens
            if (!isCreative) {
                tokenItem.shrink(usedRecipe.getTokenCost().getCount());
                if (tokenItem.isEmpty())
                    this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
            }

            //Anvil repair costs
            if (ModConfig.upgrade.anvilRepairMode > 0) {
                int repairCost = targetItem.getRepairCost();
                switch (ModConfig.upgrade.anvilRepairMode) {
                    case 2:
                        repairCost = (int) (repairCost + ModConfig.upgrade.anvilRepairCostAmount);
                        break;
                    case 3:
                        repairCost = (int) (repairCost * ModConfig.upgrade.anvilRepairCostAmount);
                        break;
                    case 1:
                    default:
                        repairCost = 1 + 2 * repairCost;
                        break;
                }
                targetItem.setRepairCost(repairCost);
            }

            this.tableInventory.markDirty();
            this.xpSeed = playerIn.getXPSeed();
            this.onCraftMatrixChanged(this.tableInventory);
            //TODO: upgrade-specific sound?
            this.world.playSound(null, this.position, usedRecipe.getIsCursingRecipe() ? SoundEvents.BLOCK_FIRE_EXTINGUISH : SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        cir.setReturnValue(true);
        return true;
    }


    @Inject(
            method = "transferStackInSlot",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0),
            cancellable = true
    )
    private void soManyEnchantments_vanillaContainerEnchantment_transferStackInSlot_alsoAllowUpgradeTokens(EntityPlayer playerIn, int index, CallbackInfoReturnable<ItemStack> cir, @Local(name = "itemstack1") ItemStack itemstack1){
        if(soManyEnchantments$isTokenLapis(itemstack1) || UpgradeRecipe.stackIsValidUpgradeToken(itemstack1))
            if(!this.mergeItemStack(itemstack1, 1, 2, true))
                cir.setReturnValue(ItemStack.EMPTY);
    }

    @Unique
    private static boolean soManyEnchantments$isTokenLapis(ItemStack tokenStack) {
        return OreDictionary.getOres("gemLapis").stream().anyMatch(ore -> OreDictionary.itemMatches(ore, tokenStack, false));
    }

    @Unique
    private static int soManyEnchantments$getRarityMultiplier(Enchantment.Rarity rarity, boolean isBook) {
        //Vanilla lvl cost multipliers when using anvil
        switch(rarity){
            case COMMON: return 1;
            case UNCOMMON: return isBook ? 1 : 2;
            case RARE: return isBook ? 2 : 4;
            case VERY_RARE: return isBook ? 4 : 8;
            default: return 0;
        }
    }
    
    @Override
    @Unique
    public ItemStack soManyEnchantments$getUpgradeTokenCost(int slotId) {
        return this.getSlotFromInventory(this.soManyEnchantments$upgradeTokenInventory,slotId).getStack();
    }
    
    @Override
    @Unique
    public int soManyEnchantments$getBookshelfPower() {
        return this.soManyEnchantments$bookshelfPower;
    }

    @Override
    @Unique
    public boolean soManyEnchantments$getTokenIsLapis(){
        return this.soManyEnchantments$tokenIsLapis;
    }

    @Override
    @Unique
    public boolean soManyEnchantments$getIsValidAndEnoughToken(int id){
        return this.soManyEnchantments$tokenIsCorrect[id];
    }
}