/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ShieldRecipes
/*     */ {
/*     */   public static class Decoration
/*     */     implements IRecipe {
/*     */     public boolean matches(InventoryCrafting inv, World worldIn) {
/*  16 */       ItemStack itemstack = ItemStack.field_190927_a;
/*  17 */       ItemStack itemstack1 = ItemStack.field_190927_a;
/*     */       
/*  19 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/*  21 */         ItemStack itemstack2 = inv.getStackInSlot(i);
/*     */         
/*  23 */         if (!itemstack2.func_190926_b())
/*     */         {
/*  25 */           if (itemstack2.getItem() == Items.BANNER) {
/*     */             
/*  27 */             if (!itemstack1.func_190926_b())
/*     */             {
/*  29 */               return false;
/*     */             }
/*     */             
/*  32 */             itemstack1 = itemstack2;
/*     */           }
/*     */           else {
/*     */             
/*  36 */             if (itemstack2.getItem() != Items.SHIELD)
/*     */             {
/*  38 */               return false;
/*     */             }
/*     */             
/*  41 */             if (!itemstack.func_190926_b())
/*     */             {
/*  43 */               return false;
/*     */             }
/*     */             
/*  46 */             if (itemstack2.getSubCompound("BlockEntityTag") != null)
/*     */             {
/*  48 */               return false;
/*     */             }
/*     */             
/*  51 */             itemstack = itemstack2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/*  56 */       if (!itemstack.func_190926_b() && !itemstack1.func_190926_b())
/*     */       {
/*  58 */         return true;
/*     */       }
/*     */ 
/*     */       
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  68 */       ItemStack itemstack = ItemStack.field_190927_a;
/*  69 */       ItemStack itemstack1 = ItemStack.field_190927_a;
/*     */       
/*  71 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/*  73 */         ItemStack itemstack2 = inv.getStackInSlot(i);
/*     */         
/*  75 */         if (!itemstack2.func_190926_b())
/*     */         {
/*  77 */           if (itemstack2.getItem() == Items.BANNER) {
/*     */             
/*  79 */             itemstack = itemstack2;
/*     */           }
/*  81 */           else if (itemstack2.getItem() == Items.SHIELD) {
/*     */             
/*  83 */             itemstack1 = itemstack2.copy();
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/*  88 */       if (itemstack1.func_190926_b())
/*     */       {
/*  90 */         return itemstack1;
/*     */       }
/*     */ 
/*     */       
/*  94 */       NBTTagCompound nbttagcompound = itemstack.getSubCompound("BlockEntityTag");
/*  95 */       NBTTagCompound nbttagcompound1 = (nbttagcompound == null) ? new NBTTagCompound() : nbttagcompound.copy();
/*  96 */       nbttagcompound1.setInteger("Base", itemstack.getMetadata() & 0xF);
/*  97 */       itemstack1.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound1);
/*  98 */       return itemstack1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/* 104 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 109 */       NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */       
/* 111 */       for (int i = 0; i < nonnulllist.size(); i++) {
/*     */         
/* 113 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 115 */         if (itemstack.getItem().hasContainerItem())
/*     */         {
/* 117 */           nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */         }
/*     */       } 
/*     */       
/* 121 */       return nonnulllist;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192399_d() {
/* 126 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 131 */       return (p_194133_1_ * p_194133_2_ >= 2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\ShieldRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */