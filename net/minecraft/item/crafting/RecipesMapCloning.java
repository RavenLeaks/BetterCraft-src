/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipesMapCloning
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  16 */     int i = 0;
/*  17 */     ItemStack itemstack = ItemStack.field_190927_a;
/*     */     
/*  19 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/*  21 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  23 */       if (!itemstack1.func_190926_b())
/*     */       {
/*  25 */         if (itemstack1.getItem() == Items.FILLED_MAP) {
/*     */           
/*  27 */           if (!itemstack.func_190926_b())
/*     */           {
/*  29 */             return false;
/*     */           }
/*     */           
/*  32 */           itemstack = itemstack1;
/*     */         }
/*     */         else {
/*     */           
/*  36 */           if (itemstack1.getItem() != Items.MAP)
/*     */           {
/*  38 */             return false;
/*     */           }
/*     */           
/*  41 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  46 */     return (!itemstack.func_190926_b() && i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  54 */     int i = 0;
/*  55 */     ItemStack itemstack = ItemStack.field_190927_a;
/*     */     
/*  57 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/*  59 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  61 */       if (!itemstack1.func_190926_b())
/*     */       {
/*  63 */         if (itemstack1.getItem() == Items.FILLED_MAP) {
/*     */           
/*  65 */           if (!itemstack.func_190926_b())
/*     */           {
/*  67 */             return ItemStack.field_190927_a;
/*     */           }
/*     */           
/*  70 */           itemstack = itemstack1;
/*     */         }
/*     */         else {
/*     */           
/*  74 */           if (itemstack1.getItem() != Items.MAP)
/*     */           {
/*  76 */             return ItemStack.field_190927_a;
/*     */           }
/*     */           
/*  79 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  84 */     if (!itemstack.func_190926_b() && i >= 1) {
/*     */       
/*  86 */       ItemStack itemstack2 = new ItemStack((Item)Items.FILLED_MAP, i + 1, itemstack.getMetadata());
/*     */       
/*  88 */       if (itemstack.hasDisplayName())
/*     */       {
/*  90 */         itemstack2.setStackDisplayName(itemstack.getDisplayName());
/*     */       }
/*     */       
/*  93 */       if (itemstack.hasTagCompound())
/*     */       {
/*  95 */         itemstack2.setTagCompound(itemstack.getTagCompound());
/*     */       }
/*     */       
/*  98 */       return itemstack2;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 108 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 113 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 115 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/* 117 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 119 */       if (itemstack.getItem().hasContainerItem())
/*     */       {
/* 121 */         nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 135 */     return (p_194133_1_ >= 3 && p_194133_2_ >= 3);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipesMapCloning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */