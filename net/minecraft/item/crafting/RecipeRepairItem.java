/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeRepairItem
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  18 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  20 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */       
/*  22 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  24 */       if (!itemstack.func_190926_b()) {
/*     */         
/*  26 */         list.add(itemstack);
/*     */         
/*  28 */         if (list.size() > 1) {
/*     */           
/*  30 */           ItemStack itemstack1 = list.get(0);
/*     */           
/*  32 */           if (itemstack.getItem() != itemstack1.getItem() || itemstack1.func_190916_E() != 1 || itemstack.func_190916_E() != 1 || !itemstack1.getItem().isDamageable())
/*     */           {
/*  34 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  40 */     return (list.size() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  48 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  50 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */       
/*  52 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  54 */       if (!itemstack.func_190926_b()) {
/*     */         
/*  56 */         list.add(itemstack);
/*     */         
/*  58 */         if (list.size() > 1) {
/*     */           
/*  60 */           ItemStack itemstack1 = list.get(0);
/*     */           
/*  62 */           if (itemstack.getItem() != itemstack1.getItem() || itemstack1.func_190916_E() != 1 || itemstack.func_190916_E() != 1 || !itemstack1.getItem().isDamageable())
/*     */           {
/*  64 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     if (list.size() == 2) {
/*     */       
/*  72 */       ItemStack itemstack2 = list.get(0);
/*  73 */       ItemStack itemstack3 = list.get(1);
/*     */       
/*  75 */       if (itemstack2.getItem() == itemstack3.getItem() && itemstack2.func_190916_E() == 1 && itemstack3.func_190916_E() == 1 && itemstack2.getItem().isDamageable()) {
/*     */         
/*  77 */         Item item = itemstack2.getItem();
/*  78 */         int j = item.getMaxDamage() - itemstack2.getItemDamage();
/*  79 */         int k = item.getMaxDamage() - itemstack3.getItemDamage();
/*  80 */         int l = j + k + item.getMaxDamage() * 5 / 100;
/*  81 */         int i1 = item.getMaxDamage() - l;
/*     */         
/*  83 */         if (i1 < 0)
/*     */         {
/*  85 */           i1 = 0;
/*     */         }
/*     */         
/*  88 */         return new ItemStack(itemstack2.getItem(), 1, i1);
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  97 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 102 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 104 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/* 106 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 108 */       if (itemstack.getItem().hasContainerItem())
/*     */       {
/* 110 */         nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 124 */     return (p_194133_1_ * p_194133_2_ >= 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipeRepairItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */