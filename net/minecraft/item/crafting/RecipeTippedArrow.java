/*    */ package net.minecraft.item.crafting;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.potion.PotionUtils;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeTippedArrow
/*    */   implements IRecipe
/*    */ {
/*    */   public boolean matches(InventoryCrafting inv, World worldIn) {
/* 18 */     if (inv.getWidth() == 3 && inv.getHeight() == 3) {
/*    */       
/* 20 */       for (int i = 0; i < inv.getWidth(); i++) {
/*    */         
/* 22 */         for (int j = 0; j < inv.getHeight(); j++) {
/*    */           
/* 24 */           ItemStack itemstack = inv.getStackInRowAndColumn(i, j);
/*    */           
/* 26 */           if (itemstack.func_190926_b())
/*    */           {
/* 28 */             return false;
/*    */           }
/*    */           
/* 31 */           Item item = itemstack.getItem();
/*    */           
/* 33 */           if (i == 1 && j == 1) {
/*    */             
/* 35 */             if (item != Items.LINGERING_POTION)
/*    */             {
/* 37 */               return false;
/*    */             }
/*    */           }
/* 40 */           else if (item != Items.ARROW) {
/*    */             
/* 42 */             return false;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 47 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 60 */     ItemStack itemstack = inv.getStackInRowAndColumn(1, 1);
/*    */     
/* 62 */     if (itemstack.getItem() != Items.LINGERING_POTION)
/*    */     {
/* 64 */       return ItemStack.field_190927_a;
/*    */     }
/*    */ 
/*    */     
/* 68 */     ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
/* 69 */     PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
/* 70 */     PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
/* 71 */     return itemstack1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getRecipeOutput() {
/* 77 */     return ItemStack.field_190927_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 82 */     return NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_192399_d() {
/* 87 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 92 */     return (p_194133_1_ >= 2 && p_194133_2_ >= 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipeTippedArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */