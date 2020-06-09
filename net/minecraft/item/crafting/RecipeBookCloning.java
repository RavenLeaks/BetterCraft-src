/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemWrittenBook;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeBookCloning
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  17 */     int i = 0;
/*  18 */     ItemStack itemstack = ItemStack.field_190927_a;
/*     */     
/*  20 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/*  22 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  24 */       if (!itemstack1.func_190926_b())
/*     */       {
/*  26 */         if (itemstack1.getItem() == Items.WRITTEN_BOOK) {
/*     */           
/*  28 */           if (!itemstack.func_190926_b())
/*     */           {
/*  30 */             return false;
/*     */           }
/*     */           
/*  33 */           itemstack = itemstack1;
/*     */         }
/*     */         else {
/*     */           
/*  37 */           if (itemstack1.getItem() != Items.WRITABLE_BOOK)
/*     */           {
/*  39 */             return false;
/*     */           }
/*     */           
/*  42 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  47 */     return (!itemstack.func_190926_b() && itemstack.hasTagCompound() && i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  55 */     int i = 0;
/*  56 */     ItemStack itemstack = ItemStack.field_190927_a;
/*     */     
/*  58 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/*  60 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  62 */       if (!itemstack1.func_190926_b())
/*     */       {
/*  64 */         if (itemstack1.getItem() == Items.WRITTEN_BOOK) {
/*     */           
/*  66 */           if (!itemstack.func_190926_b())
/*     */           {
/*  68 */             return ItemStack.field_190927_a;
/*     */           }
/*     */           
/*  71 */           itemstack = itemstack1;
/*     */         }
/*     */         else {
/*     */           
/*  75 */           if (itemstack1.getItem() != Items.WRITABLE_BOOK)
/*     */           {
/*  77 */             return ItemStack.field_190927_a;
/*     */           }
/*     */           
/*  80 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  85 */     if (!itemstack.func_190926_b() && itemstack.hasTagCompound() && i >= 1 && ItemWrittenBook.getGeneration(itemstack) < 2) {
/*     */       
/*  87 */       ItemStack itemstack2 = new ItemStack(Items.WRITTEN_BOOK, i);
/*  88 */       itemstack2.setTagCompound(itemstack.getTagCompound().copy());
/*  89 */       itemstack2.getTagCompound().setInteger("generation", ItemWrittenBook.getGeneration(itemstack) + 1);
/*     */       
/*  91 */       if (itemstack.hasDisplayName())
/*     */       {
/*  93 */         itemstack2.setStackDisplayName(itemstack.getDisplayName());
/*     */       }
/*     */       
/*  96 */       return itemstack2;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 106 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 111 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 113 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/* 115 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 117 */       if (itemstack.getItem() instanceof ItemWrittenBook) {
/*     */         
/* 119 */         ItemStack itemstack1 = itemstack.copy();
/* 120 */         itemstack1.func_190920_e(1);
/* 121 */         nonnulllist.set(i, itemstack1);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 126 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 136 */     return (p_194133_1_ >= 3 && p_194133_2_ >= 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipeBookCloning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */