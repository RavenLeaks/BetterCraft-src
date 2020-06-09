/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RecipeFireworks implements IRecipe {
/*  16 */   private ItemStack resultItem = ItemStack.field_190927_a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  23 */     this.resultItem = ItemStack.field_190927_a;
/*  24 */     int i = 0;
/*  25 */     int j = 0;
/*  26 */     int k = 0;
/*  27 */     int l = 0;
/*  28 */     int i1 = 0;
/*  29 */     int j1 = 0;
/*     */     
/*  31 */     for (int k1 = 0; k1 < inv.getSizeInventory(); k1++) {
/*     */       
/*  33 */       ItemStack itemstack = inv.getStackInSlot(k1);
/*     */       
/*  35 */       if (!itemstack.func_190926_b())
/*     */       {
/*  37 */         if (itemstack.getItem() == Items.GUNPOWDER) {
/*     */           
/*  39 */           j++;
/*     */         }
/*  41 */         else if (itemstack.getItem() == Items.FIREWORK_CHARGE) {
/*     */           
/*  43 */           l++;
/*     */         }
/*  45 */         else if (itemstack.getItem() == Items.DYE) {
/*     */           
/*  47 */           k++;
/*     */         }
/*  49 */         else if (itemstack.getItem() == Items.PAPER) {
/*     */           
/*  51 */           i++;
/*     */         }
/*  53 */         else if (itemstack.getItem() == Items.GLOWSTONE_DUST) {
/*     */           
/*  55 */           i1++;
/*     */         }
/*  57 */         else if (itemstack.getItem() == Items.DIAMOND) {
/*     */           
/*  59 */           i1++;
/*     */         }
/*  61 */         else if (itemstack.getItem() == Items.FIRE_CHARGE) {
/*     */           
/*  63 */           j1++;
/*     */         }
/*  65 */         else if (itemstack.getItem() == Items.FEATHER) {
/*     */           
/*  67 */           j1++;
/*     */         }
/*  69 */         else if (itemstack.getItem() == Items.GOLD_NUGGET) {
/*     */           
/*  71 */           j1++;
/*     */         }
/*     */         else {
/*     */           
/*  75 */           if (itemstack.getItem() != Items.SKULL)
/*     */           {
/*  77 */             return false;
/*     */           }
/*     */           
/*  80 */           j1++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  85 */     i1 = i1 + k + j1;
/*     */     
/*  87 */     if (j <= 3 && i <= 1) {
/*     */       
/*  89 */       if (j >= 1 && i == 1 && i1 == 0) {
/*     */         
/*  91 */         this.resultItem = new ItemStack(Items.FIREWORKS, 3);
/*  92 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/*  94 */         if (l > 0) {
/*     */           
/*  96 */           NBTTagList nbttaglist = new NBTTagList();
/*     */           
/*  98 */           for (int k2 = 0; k2 < inv.getSizeInventory(); k2++) {
/*     */             
/* 100 */             ItemStack itemstack3 = inv.getStackInSlot(k2);
/*     */             
/* 102 */             if (itemstack3.getItem() == Items.FIREWORK_CHARGE && itemstack3.hasTagCompound() && itemstack3.getTagCompound().hasKey("Explosion", 10))
/*     */             {
/* 104 */               nbttaglist.appendTag((NBTBase)itemstack3.getTagCompound().getCompoundTag("Explosion"));
/*     */             }
/*     */           } 
/*     */           
/* 108 */           nbttagcompound1.setTag("Explosions", (NBTBase)nbttaglist);
/*     */         } 
/*     */         
/* 111 */         nbttagcompound1.setByte("Flight", (byte)j);
/* 112 */         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 113 */         nbttagcompound3.setTag("Fireworks", (NBTBase)nbttagcompound1);
/* 114 */         this.resultItem.setTagCompound(nbttagcompound3);
/* 115 */         return true;
/*     */       } 
/* 117 */       if (j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1) {
/*     */         
/* 119 */         this.resultItem = new ItemStack(Items.FIREWORK_CHARGE);
/* 120 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 121 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 122 */         byte b0 = 0;
/* 123 */         List<Integer> list = Lists.newArrayList();
/*     */         
/* 125 */         for (int l1 = 0; l1 < inv.getSizeInventory(); l1++) {
/*     */           
/* 127 */           ItemStack itemstack2 = inv.getStackInSlot(l1);
/*     */           
/* 129 */           if (!itemstack2.func_190926_b())
/*     */           {
/* 131 */             if (itemstack2.getItem() == Items.DYE) {
/*     */               
/* 133 */               list.add(Integer.valueOf(ItemDye.DYE_COLORS[itemstack2.getMetadata() & 0xF]));
/*     */             }
/* 135 */             else if (itemstack2.getItem() == Items.GLOWSTONE_DUST) {
/*     */               
/* 137 */               nbttagcompound2.setBoolean("Flicker", true);
/*     */             }
/* 139 */             else if (itemstack2.getItem() == Items.DIAMOND) {
/*     */               
/* 141 */               nbttagcompound2.setBoolean("Trail", true);
/*     */             }
/* 143 */             else if (itemstack2.getItem() == Items.FIRE_CHARGE) {
/*     */               
/* 145 */               b0 = 1;
/*     */             }
/* 147 */             else if (itemstack2.getItem() == Items.FEATHER) {
/*     */               
/* 149 */               b0 = 4;
/*     */             }
/* 151 */             else if (itemstack2.getItem() == Items.GOLD_NUGGET) {
/*     */               
/* 153 */               b0 = 2;
/*     */             }
/* 155 */             else if (itemstack2.getItem() == Items.SKULL) {
/*     */               
/* 157 */               b0 = 3;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 162 */         int[] aint1 = new int[list.size()];
/*     */         
/* 164 */         for (int l2 = 0; l2 < aint1.length; l2++)
/*     */         {
/* 166 */           aint1[l2] = ((Integer)list.get(l2)).intValue();
/*     */         }
/*     */         
/* 169 */         nbttagcompound2.setIntArray("Colors", aint1);
/* 170 */         nbttagcompound2.setByte("Type", b0);
/* 171 */         nbttagcompound.setTag("Explosion", (NBTBase)nbttagcompound2);
/* 172 */         this.resultItem.setTagCompound(nbttagcompound);
/* 173 */         return true;
/*     */       } 
/* 175 */       if (j == 0 && i == 0 && l == 1 && k > 0 && k == i1) {
/*     */         
/* 177 */         List<Integer> list1 = Lists.newArrayList();
/*     */         
/* 179 */         for (int i2 = 0; i2 < inv.getSizeInventory(); i2++) {
/*     */           
/* 181 */           ItemStack itemstack1 = inv.getStackInSlot(i2);
/*     */           
/* 183 */           if (!itemstack1.func_190926_b())
/*     */           {
/* 185 */             if (itemstack1.getItem() == Items.DYE) {
/*     */               
/* 187 */               list1.add(Integer.valueOf(ItemDye.DYE_COLORS[itemstack1.getMetadata() & 0xF]));
/*     */             }
/* 189 */             else if (itemstack1.getItem() == Items.FIREWORK_CHARGE) {
/*     */               
/* 191 */               this.resultItem = itemstack1.copy();
/* 192 */               this.resultItem.func_190920_e(1);
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 197 */         int[] aint = new int[list1.size()];
/*     */         
/* 199 */         for (int j2 = 0; j2 < aint.length; j2++)
/*     */         {
/* 201 */           aint[j2] = ((Integer)list1.get(j2)).intValue();
/*     */         }
/*     */         
/* 204 */         if (!this.resultItem.func_190926_b() && this.resultItem.hasTagCompound()) {
/*     */           
/* 206 */           NBTTagCompound nbttagcompound4 = this.resultItem.getTagCompound().getCompoundTag("Explosion");
/*     */           
/* 208 */           if (nbttagcompound4 == null)
/*     */           {
/* 210 */             return false;
/*     */           }
/*     */ 
/*     */           
/* 214 */           nbttagcompound4.setIntArray("FadeColors", aint);
/* 215 */           return true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 220 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 225 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 239 */     return this.resultItem.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 244 */     return this.resultItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 249 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 251 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/* 253 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 255 */       if (itemstack.getItem().hasContainerItem())
/*     */       {
/* 257 */         nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */       }
/*     */     } 
/*     */     
/* 261 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192399_d() {
/* 266 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 271 */     return (p_194133_1_ * p_194133_2_ >= 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipeFireworks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */