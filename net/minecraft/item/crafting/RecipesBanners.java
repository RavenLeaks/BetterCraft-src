/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemBanner;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.BannerPattern;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RecipesBanners
/*     */ {
/*     */   public static class RecipeAddPattern
/*     */     implements IRecipe {
/*     */     public boolean matches(InventoryCrafting inv, World worldIn) {
/*  22 */       boolean flag = false;
/*     */       
/*  24 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/*  26 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/*  28 */         if (itemstack.getItem() == Items.BANNER) {
/*     */           
/*  30 */           if (flag)
/*     */           {
/*  32 */             return false;
/*     */           }
/*     */           
/*  35 */           if (TileEntityBanner.getPatterns(itemstack) >= 6)
/*     */           {
/*  37 */             return false;
/*     */           }
/*     */           
/*  40 */           flag = true;
/*     */         } 
/*     */       } 
/*     */       
/*  44 */       if (!flag)
/*     */       {
/*  46 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  50 */       return (func_190933_c(inv) != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  56 */       ItemStack itemstack = ItemStack.field_190927_a;
/*     */       
/*  58 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/*  60 */         ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */         
/*  62 */         if (!itemstack1.func_190926_b() && itemstack1.getItem() == Items.BANNER) {
/*     */           
/*  64 */           itemstack = itemstack1.copy();
/*  65 */           itemstack.func_190920_e(1);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  70 */       BannerPattern bannerpattern = func_190933_c(inv);
/*     */       
/*  72 */       if (bannerpattern != null) {
/*     */         NBTTagList nbttaglist;
/*  74 */         int k = 0;
/*     */         
/*  76 */         for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */           
/*  78 */           ItemStack itemstack2 = inv.getStackInSlot(j);
/*     */           
/*  80 */           if (itemstack2.getItem() == Items.DYE) {
/*     */             
/*  82 */             k = itemstack2.getMetadata();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  87 */         NBTTagCompound nbttagcompound1 = itemstack.func_190925_c("BlockEntityTag");
/*     */ 
/*     */         
/*  90 */         if (nbttagcompound1.hasKey("Patterns", 9)) {
/*     */           
/*  92 */           nbttaglist = nbttagcompound1.getTagList("Patterns", 10);
/*     */         }
/*     */         else {
/*     */           
/*  96 */           nbttaglist = new NBTTagList();
/*  97 */           nbttagcompound1.setTag("Patterns", (NBTBase)nbttaglist);
/*     */         } 
/*     */         
/* 100 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 101 */         nbttagcompound.setString("Pattern", bannerpattern.func_190993_b());
/* 102 */         nbttagcompound.setInteger("Color", k);
/* 103 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       
/* 106 */       return itemstack;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/* 111 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 116 */       NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */       
/* 118 */       for (int i = 0; i < nonnulllist.size(); i++) {
/*     */         
/* 120 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 122 */         if (itemstack.getItem().hasContainerItem())
/*     */         {
/* 124 */           nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */         }
/*     */       } 
/*     */       
/* 128 */       return nonnulllist;
/*     */     } @Nullable
/*     */     private BannerPattern func_190933_c(InventoryCrafting p_190933_1_) {
/*     */       byte b;
/*     */       int i;
/*     */       BannerPattern[] arrayOfBannerPattern;
/* 134 */       for (i = (arrayOfBannerPattern = BannerPattern.values()).length, b = 0; b < i; ) { BannerPattern bannerpattern = arrayOfBannerPattern[b];
/*     */         
/* 136 */         if (bannerpattern.func_191000_d()) {
/*     */           
/* 138 */           boolean flag = true;
/*     */           
/* 140 */           if (bannerpattern.func_190999_e()) {
/*     */             
/* 142 */             boolean flag1 = false;
/* 143 */             boolean flag2 = false;
/*     */             
/* 145 */             for (int j = 0; j < p_190933_1_.getSizeInventory() && flag; j++) {
/*     */               
/* 147 */               ItemStack itemstack = p_190933_1_.getStackInSlot(j);
/*     */               
/* 149 */               if (!itemstack.func_190926_b() && itemstack.getItem() != Items.BANNER)
/*     */               {
/* 151 */                 if (itemstack.getItem() == Items.DYE) {
/*     */                   
/* 153 */                   if (flag2) {
/*     */                     
/* 155 */                     flag = false;
/*     */                     
/*     */                     break;
/*     */                   } 
/* 159 */                   flag2 = true;
/*     */                 }
/*     */                 else {
/*     */                   
/* 163 */                   if (flag1 || !itemstack.isItemEqual(bannerpattern.func_190998_f())) {
/*     */                     
/* 165 */                     flag = false;
/*     */                     
/*     */                     break;
/*     */                   } 
/* 169 */                   flag1 = true;
/*     */                 } 
/*     */               }
/*     */             } 
/*     */             
/* 174 */             if (!flag1 || !flag2)
/*     */             {
/* 176 */               flag = false;
/*     */             }
/*     */           }
/* 179 */           else if (p_190933_1_.getSizeInventory() == (bannerpattern.func_190996_c()).length * bannerpattern.func_190996_c()[0].length()) {
/*     */             
/* 181 */             int j = -1;
/*     */             
/* 183 */             for (int k = 0; k < p_190933_1_.getSizeInventory() && flag; k++) {
/*     */               
/* 185 */               int l = k / 3;
/* 186 */               int i1 = k % 3;
/* 187 */               ItemStack itemstack1 = p_190933_1_.getStackInSlot(k);
/*     */               
/* 189 */               if (!itemstack1.func_190926_b() && itemstack1.getItem() != Items.BANNER) {
/*     */                 
/* 191 */                 if (itemstack1.getItem() != Items.DYE) {
/*     */                   
/* 193 */                   flag = false;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 197 */                 if (j != -1 && j != itemstack1.getMetadata()) {
/*     */                   
/* 199 */                   flag = false;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 203 */                 if (bannerpattern.func_190996_c()[l].charAt(i1) == ' ') {
/*     */                   
/* 205 */                   flag = false;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 209 */                 j = itemstack1.getMetadata();
/*     */               }
/* 211 */               else if (bannerpattern.func_190996_c()[l].charAt(i1) != ' ') {
/*     */                 
/* 213 */                 flag = false;
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 220 */             flag = false;
/*     */           } 
/*     */           
/* 223 */           if (flag)
/*     */           {
/* 225 */             return bannerpattern;
/*     */           }
/*     */         } 
/*     */         b++; }
/*     */       
/* 230 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192399_d() {
/* 235 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 240 */       return (p_194133_1_ >= 3 && p_194133_2_ >= 3);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RecipeDuplicatePattern
/*     */     implements IRecipe
/*     */   {
/*     */     public boolean matches(InventoryCrafting inv, World worldIn) {
/* 248 */       ItemStack itemstack = ItemStack.field_190927_a;
/* 249 */       ItemStack itemstack1 = ItemStack.field_190927_a;
/*     */       
/* 251 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/* 253 */         ItemStack itemstack2 = inv.getStackInSlot(i);
/*     */         
/* 255 */         if (!itemstack2.func_190926_b()) {
/*     */           
/* 257 */           if (itemstack2.getItem() != Items.BANNER)
/*     */           {
/* 259 */             return false;
/*     */           }
/*     */           
/* 262 */           if (!itemstack.func_190926_b() && !itemstack1.func_190926_b())
/*     */           {
/* 264 */             return false;
/*     */           }
/*     */           
/* 267 */           EnumDyeColor enumdyecolor = ItemBanner.getBaseColor(itemstack2);
/* 268 */           boolean flag = (TileEntityBanner.getPatterns(itemstack2) > 0);
/*     */           
/* 270 */           if (!itemstack.func_190926_b()) {
/*     */             
/* 272 */             if (flag)
/*     */             {
/* 274 */               return false;
/*     */             }
/*     */             
/* 277 */             if (enumdyecolor != ItemBanner.getBaseColor(itemstack))
/*     */             {
/* 279 */               return false;
/*     */             }
/*     */             
/* 282 */             itemstack1 = itemstack2;
/*     */           }
/* 284 */           else if (!itemstack1.func_190926_b()) {
/*     */             
/* 286 */             if (!flag)
/*     */             {
/* 288 */               return false;
/*     */             }
/*     */             
/* 291 */             if (enumdyecolor != ItemBanner.getBaseColor(itemstack1))
/*     */             {
/* 293 */               return false;
/*     */             }
/*     */             
/* 296 */             itemstack = itemstack2;
/*     */           }
/* 298 */           else if (flag) {
/*     */             
/* 300 */             itemstack = itemstack2;
/*     */           }
/*     */           else {
/*     */             
/* 304 */             itemstack1 = itemstack2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 309 */       return (!itemstack.func_190926_b() && !itemstack1.func_190926_b());
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 314 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*     */         
/* 316 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 318 */         if (!itemstack.func_190926_b() && TileEntityBanner.getPatterns(itemstack) > 0) {
/*     */           
/* 320 */           ItemStack itemstack1 = itemstack.copy();
/* 321 */           itemstack1.func_190920_e(1);
/* 322 */           return itemstack1;
/*     */         } 
/*     */       } 
/*     */       
/* 326 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/* 331 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/*     */     public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/* 336 */       NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */       
/* 338 */       for (int i = 0; i < nonnulllist.size(); i++) {
/*     */         
/* 340 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 342 */         if (!itemstack.func_190926_b())
/*     */         {
/* 344 */           if (itemstack.getItem().hasContainerItem()) {
/*     */             
/* 346 */             nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */           }
/* 348 */           else if (itemstack.hasTagCompound() && TileEntityBanner.getPatterns(itemstack) > 0) {
/*     */             
/* 350 */             ItemStack itemstack1 = itemstack.copy();
/* 351 */             itemstack1.func_190920_e(1);
/* 352 */             nonnulllist.set(i, itemstack1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 357 */       return nonnulllist;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_192399_d() {
/* 362 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 367 */       return (p_194133_1_ * p_194133_2_ >= 2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\RecipesBanners.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */