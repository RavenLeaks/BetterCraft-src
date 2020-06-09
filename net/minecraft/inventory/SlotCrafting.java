/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotCrafting
/*     */   extends Slot
/*     */ {
/*     */   private final InventoryCrafting craftMatrix;
/*     */   private final EntityPlayer thePlayer;
/*     */   private int amountCrafted;
/*     */   
/*     */   public SlotCrafting(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
/*  25 */     super(inventoryIn, slotIndex, xPosition, yPosition);
/*  26 */     this.thePlayer = player;
/*  27 */     this.craftMatrix = craftingInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/*  44 */     if (getHasStack())
/*     */     {
/*  46 */       this.amountCrafted += Math.min(amount, getStack().func_190916_E());
/*     */     }
/*     */     
/*  49 */     return super.decrStackSize(amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {
/*  58 */     this.amountCrafted += amount;
/*  59 */     onCrafting(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190900_b(int p_190900_1_) {
/*  64 */     this.amountCrafted += p_190900_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {
/*  72 */     if (this.amountCrafted > 0)
/*     */     {
/*  74 */       stack.onCrafting(this.thePlayer.world, this.thePlayer, this.amountCrafted);
/*     */     }
/*     */     
/*  77 */     this.amountCrafted = 0;
/*  78 */     InventoryCraftResult inventorycraftresult = (InventoryCraftResult)this.inventory;
/*  79 */     IRecipe irecipe = inventorycraftresult.func_193055_i();
/*     */     
/*  81 */     if (irecipe != null && !irecipe.func_192399_d()) {
/*     */       
/*  83 */       this.thePlayer.func_192021_a(Lists.newArrayList((Object[])new IRecipe[] { irecipe }));
/*  84 */       inventorycraftresult.func_193056_a(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/*  90 */     onCrafting(p_190901_2_);
/*  91 */     NonNullList<ItemStack> nonnulllist = CraftingManager.getRemainingItems(this.craftMatrix, p_190901_1_.world);
/*     */     
/*  93 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/*  95 */       ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
/*  96 */       ItemStack itemstack1 = (ItemStack)nonnulllist.get(i);
/*     */       
/*  98 */       if (!itemstack.func_190926_b()) {
/*     */         
/* 100 */         this.craftMatrix.decrStackSize(i, 1);
/* 101 */         itemstack = this.craftMatrix.getStackInSlot(i);
/*     */       } 
/*     */       
/* 104 */       if (!itemstack1.func_190926_b())
/*     */       {
/* 106 */         if (itemstack.func_190926_b()) {
/*     */           
/* 108 */           this.craftMatrix.setInventorySlotContents(i, itemstack1);
/*     */         }
/* 110 */         else if (ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
/*     */           
/* 112 */           itemstack1.func_190917_f(itemstack.func_190916_E());
/* 113 */           this.craftMatrix.setInventorySlotContents(i, itemstack1);
/*     */         }
/* 115 */         else if (!this.thePlayer.inventory.addItemStackToInventory(itemstack1)) {
/*     */           
/* 117 */           this.thePlayer.dropItem(itemstack1, false);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return p_190901_2_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\SlotCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */