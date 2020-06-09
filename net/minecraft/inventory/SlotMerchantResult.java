/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotMerchantResult
/*     */   extends Slot
/*     */ {
/*     */   private final InventoryMerchant theMerchantInventory;
/*     */   private final EntityPlayer thePlayer;
/*     */   private int removeCount;
/*     */   private final IMerchant theMerchant;
/*     */   
/*     */   public SlotMerchantResult(EntityPlayer player, IMerchant merchant, InventoryMerchant merchantInventory, int slotIndex, int xPosition, int yPosition) {
/*  23 */     super(merchantInventory, slotIndex, xPosition, yPosition);
/*  24 */     this.thePlayer = player;
/*  25 */     this.theMerchant = merchant;
/*  26 */     this.theMerchantInventory = merchantInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  34 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/*  43 */     if (getHasStack())
/*     */     {
/*  45 */       this.removeCount += Math.min(amount, getStack().func_190916_E());
/*     */     }
/*     */     
/*  48 */     return super.decrStackSize(amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {
/*  57 */     this.removeCount += amount;
/*  58 */     onCrafting(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {
/*  66 */     stack.onCrafting(this.thePlayer.world, this.thePlayer, this.removeCount);
/*  67 */     this.removeCount = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/*  72 */     onCrafting(p_190901_2_);
/*  73 */     MerchantRecipe merchantrecipe = this.theMerchantInventory.getCurrentRecipe();
/*     */     
/*  75 */     if (merchantrecipe != null) {
/*     */       
/*  77 */       ItemStack itemstack = this.theMerchantInventory.getStackInSlot(0);
/*  78 */       ItemStack itemstack1 = this.theMerchantInventory.getStackInSlot(1);
/*     */       
/*  80 */       if (doTrade(merchantrecipe, itemstack, itemstack1) || doTrade(merchantrecipe, itemstack1, itemstack)) {
/*     */         
/*  82 */         this.theMerchant.useRecipe(merchantrecipe);
/*  83 */         p_190901_1_.addStat(StatList.TRADED_WITH_VILLAGER);
/*  84 */         this.theMerchantInventory.setInventorySlotContents(0, itemstack);
/*  85 */         this.theMerchantInventory.setInventorySlotContents(1, itemstack1);
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return p_190901_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean doTrade(MerchantRecipe trade, ItemStack firstItem, ItemStack secondItem) {
/*  94 */     ItemStack itemstack = trade.getItemToBuy();
/*  95 */     ItemStack itemstack1 = trade.getSecondItemToBuy();
/*     */     
/*  97 */     if (firstItem.getItem() == itemstack.getItem() && firstItem.func_190916_E() >= itemstack.func_190916_E()) {
/*     */       
/*  99 */       if (!itemstack1.func_190926_b() && !secondItem.func_190926_b() && itemstack1.getItem() == secondItem.getItem() && secondItem.func_190916_E() >= itemstack1.func_190916_E()) {
/*     */         
/* 101 */         firstItem.func_190918_g(itemstack.func_190916_E());
/* 102 */         secondItem.func_190918_g(itemstack1.func_190916_E());
/* 103 */         return true;
/*     */       } 
/*     */       
/* 106 */       if (itemstack1.func_190926_b() && secondItem.func_190926_b()) {
/*     */         
/* 108 */         firstItem.func_190918_g(itemstack.func_190916_E());
/* 109 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\SlotMerchantResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */