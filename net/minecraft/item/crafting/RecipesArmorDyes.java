/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipesArmorDyes
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  20 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  21 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  23 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */       
/*  25 */       ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */       
/*  27 */       if (!itemstack1.func_190926_b())
/*     */       {
/*  29 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*     */           
/*  31 */           ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  33 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || !itemstack.func_190926_b())
/*     */           {
/*  35 */             return false;
/*     */           }
/*     */           
/*  38 */           itemstack = itemstack1;
/*     */         }
/*     */         else {
/*     */           
/*  42 */           if (itemstack1.getItem() != Items.DYE)
/*     */           {
/*  44 */             return false;
/*     */           }
/*     */           
/*  47 */           list.add(itemstack1);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return (!itemstack.func_190926_b() && !list.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  60 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  61 */     int[] aint = new int[3];
/*  62 */     int i = 0;
/*  63 */     int j = 0;
/*  64 */     ItemArmor itemarmor = null;
/*     */     
/*  66 */     for (int k = 0; k < inv.getSizeInventory(); k++) {
/*     */       
/*  68 */       ItemStack itemstack1 = inv.getStackInSlot(k);
/*     */       
/*  70 */       if (!itemstack1.func_190926_b())
/*     */       {
/*  72 */         if (itemstack1.getItem() instanceof ItemArmor) {
/*     */           
/*  74 */           itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  76 */           if (itemarmor.getArmorMaterial() != ItemArmor.ArmorMaterial.LEATHER || !itemstack.func_190926_b())
/*     */           {
/*  78 */             return ItemStack.field_190927_a;
/*     */           }
/*     */           
/*  81 */           itemstack = itemstack1.copy();
/*  82 */           itemstack.func_190920_e(1);
/*     */           
/*  84 */           if (itemarmor.hasColor(itemstack1))
/*     */           {
/*  86 */             int l = itemarmor.getColor(itemstack);
/*  87 */             float f = (l >> 16 & 0xFF) / 255.0F;
/*  88 */             float f1 = (l >> 8 & 0xFF) / 255.0F;
/*  89 */             float f2 = (l & 0xFF) / 255.0F;
/*  90 */             i = (int)(i + Math.max(f, Math.max(f1, f2)) * 255.0F);
/*  91 */             aint[0] = (int)(aint[0] + f * 255.0F);
/*  92 */             aint[1] = (int)(aint[1] + f1 * 255.0F);
/*  93 */             aint[2] = (int)(aint[2] + f2 * 255.0F);
/*  94 */             j++;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  99 */           if (itemstack1.getItem() != Items.DYE)
/*     */           {
/* 101 */             return ItemStack.field_190927_a;
/*     */           }
/*     */           
/* 104 */           float[] afloat = EnumDyeColor.byDyeDamage(itemstack1.getMetadata()).func_193349_f();
/* 105 */           int l1 = (int)(afloat[0] * 255.0F);
/* 106 */           int i2 = (int)(afloat[1] * 255.0F);
/* 107 */           int j2 = (int)(afloat[2] * 255.0F);
/* 108 */           i += Math.max(l1, Math.max(i2, j2));
/* 109 */           aint[0] = aint[0] + l1;
/* 110 */           aint[1] = aint[1] + i2;
/* 111 */           aint[2] = aint[2] + j2;
/* 112 */           j++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if (itemarmor == null)
/*     */     {
/* 119 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/* 123 */     int i1 = aint[0] / j;
/* 124 */     int j1 = aint[1] / j;
/* 125 */     int k1 = aint[2] / j;
/* 126 */     float f3 = i / j;
/* 127 */     float f4 = Math.max(i1, Math.max(j1, k1));
/* 128 */     i1 = (int)(i1 * f3 / f4);
/* 129 */     j1 = (int)(j1 * f3 / f4);
/* 130 */     k1 = (int)(k1 * f3 / f4);
/* 131 */     int k2 = (i1 << 8) + j1;
/* 132 */     k2 = (k2 << 8) + k1;
/* 133 */     itemarmor.setColor(itemstack, k2);
/* 134 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 140 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 145 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 147 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/* 149 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 151 */       if (itemstack.getItem().hasContainerItem())
/*     */       {
/* 153 */         nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 167 */     return (p_194133_1_ * p_194133_2_ >= 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipesArmorDyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */