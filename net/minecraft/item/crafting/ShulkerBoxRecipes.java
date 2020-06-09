/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockShulkerBox;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ShulkerBoxRecipes
/*     */ {
/*     */   public static class ShulkerBoxColoring
/*     */     implements IRecipe
/*     */   {
/*     */     public boolean matches(InventoryCrafting inv, World worldIn) {
/*  18 */       int i = 0;
/*  19 */       int j = 0;
/*     */       
/*  21 */       for (int k = 0; k < inv.getSizeInventory(); k++) {
/*     */         
/*  23 */         ItemStack itemstack = inv.getStackInSlot(k);
/*     */         
/*  25 */         if (!itemstack.func_190926_b()) {
/*     */           
/*  27 */           if (Block.getBlockFromItem(itemstack.getItem()) instanceof BlockShulkerBox) {
/*     */             
/*  29 */             i++;
/*     */           }
/*     */           else {
/*     */             
/*  33 */             if (itemstack.getItem() != Items.DYE)
/*     */             {
/*  35 */               return false;
/*     */             }
/*     */             
/*  38 */             j++;
/*     */           } 
/*     */           
/*  41 */           if (j > 1 || i > 1)
/*     */           {
/*  43 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  48 */       return (i == 1 && j == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  53 */       ItemStack itemstack = ItemStack.field_190927_a;
/*  54 */       ItemStack itemstack1 = ItemStack.field_190927_a;
/*     */       
/*  56 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/*  58 */         ItemStack itemstack2 = inv.getStackInSlot(i);
/*     */         
/*  60 */         if (!itemstack2.func_190926_b())
/*     */         {
/*  62 */           if (Block.getBlockFromItem(itemstack2.getItem()) instanceof BlockShulkerBox) {
/*     */             
/*  64 */             itemstack = itemstack2;
/*     */           }
/*  66 */           else if (itemstack2.getItem() == Items.DYE) {
/*     */             
/*  68 */             itemstack1 = itemstack2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/*  73 */       ItemStack itemstack3 = BlockShulkerBox.func_190953_b(EnumDyeColor.byDyeDamage(itemstack1.getMetadata()));
/*     */       
/*  75 */       if (itemstack.hasTagCompound())
/*     */       {
/*  77 */         itemstack3.setTagCompound(itemstack.getTagCompound().copy());
/*     */       }
/*     */       
/*  80 */       return itemstack3;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/*  85 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/*  90 */       NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */       
/*  92 */       for (int i = 0; i < nonnulllist.size(); i++) {
/*     */         
/*  94 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/*  96 */         if (itemstack.getItem().hasContainerItem())
/*     */         {
/*  98 */           nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */         }
/*     */       } 
/*     */       
/* 102 */       return nonnulllist;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192399_d() {
/* 107 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 112 */       return (p_194133_1_ * p_194133_2_ >= 2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\ShulkerBoxRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */