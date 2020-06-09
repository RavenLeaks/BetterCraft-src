/*     */ package net.minecraft.client.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ import net.minecraft.util.NonNullList;
/*     */ 
/*     */ public class RecipeItemHelper {
/*  23 */   public final Int2IntMap field_194124_a = (Int2IntMap)new Int2IntOpenHashMap();
/*     */ 
/*     */   
/*     */   public void func_194112_a(ItemStack p_194112_1_) {
/*  27 */     if (!p_194112_1_.func_190926_b() && !p_194112_1_.isItemDamaged() && !p_194112_1_.isItemEnchanted() && !p_194112_1_.hasDisplayName()) {
/*     */       
/*  29 */       int i = func_194113_b(p_194112_1_);
/*  30 */       int j = p_194112_1_.func_190916_E();
/*  31 */       func_194117_b(i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int func_194113_b(ItemStack p_194113_0_) {
/*  37 */     Item item = p_194113_0_.getItem();
/*  38 */     int i = item.getHasSubtypes() ? p_194113_0_.getMetadata() : 0;
/*  39 */     return Item.REGISTRY.getIDForObject(item) << 16 | i & 0xFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194120_a(int p_194120_1_) {
/*  44 */     return (this.field_194124_a.get(p_194120_1_) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_194122_a(int p_194122_1_, int p_194122_2_) {
/*  49 */     int i = this.field_194124_a.get(p_194122_1_);
/*     */     
/*  51 */     if (i >= p_194122_2_) {
/*     */       
/*  53 */       this.field_194124_a.put(p_194122_1_, i - p_194122_2_);
/*  54 */       return p_194122_1_;
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_194117_b(int p_194117_1_, int p_194117_2_) {
/*  64 */     this.field_194124_a.put(p_194117_1_, this.field_194124_a.get(p_194117_1_) + p_194117_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194116_a(IRecipe p_194116_1_, @Nullable IntList p_194116_2_) {
/*  69 */     return func_194118_a(p_194116_1_, p_194116_2_, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194118_a(IRecipe p_194118_1_, @Nullable IntList p_194118_2_, int p_194118_3_) {
/*  74 */     return (new RecipePicker(p_194118_1_)).func_194092_a(p_194118_3_, p_194118_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_194114_b(IRecipe p_194114_1_, @Nullable IntList p_194114_2_) {
/*  79 */     return func_194121_a(p_194114_1_, 2147483647, p_194114_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_194121_a(IRecipe p_194121_1_, int p_194121_2_, @Nullable IntList p_194121_3_) {
/*  84 */     return (new RecipePicker(p_194121_1_)).func_194102_b(p_194121_2_, p_194121_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack func_194115_b(int p_194115_0_) {
/*  89 */     return (p_194115_0_ == 0) ? ItemStack.field_190927_a : new ItemStack(Item.getItemById(p_194115_0_ >> 16 & 0xFFFF), 1, p_194115_0_ & 0xFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194119_a() {
/*  94 */     this.field_194124_a.clear();
/*     */   }
/*     */   
/*     */   class RecipePicker
/*     */   {
/*     */     private final IRecipe field_194105_b;
/* 100 */     private final List<Ingredient> field_194106_c = Lists.newArrayList();
/*     */     private final int field_194107_d;
/*     */     private final int[] field_194108_e;
/*     */     private final int field_194109_f;
/*     */     private final BitSet field_194110_g;
/* 105 */     private IntList field_194111_h = (IntList)new IntArrayList();
/*     */ 
/*     */     
/*     */     public RecipePicker(IRecipe p_i47608_2_) {
/* 109 */       this.field_194105_b = p_i47608_2_;
/* 110 */       this.field_194106_c.addAll((Collection<? extends Ingredient>)p_i47608_2_.func_192400_c());
/* 111 */       this.field_194106_c.removeIf(p_194103_0_ -> (p_194103_0_ == Ingredient.field_193370_a));
/*     */ 
/*     */ 
/*     */       
/* 115 */       this.field_194107_d = this.field_194106_c.size();
/* 116 */       this.field_194108_e = func_194097_a();
/* 117 */       this.field_194109_f = this.field_194108_e.length;
/* 118 */       this.field_194110_g = new BitSet(this.field_194107_d + this.field_194109_f + this.field_194107_d + this.field_194107_d * this.field_194109_f);
/*     */       
/* 120 */       for (int i = 0; i < this.field_194106_c.size(); i++) {
/*     */         
/* 122 */         IntList intlist = ((Ingredient)this.field_194106_c.get(i)).func_194139_b();
/*     */         
/* 124 */         for (int j = 0; j < this.field_194109_f; j++) {
/*     */           
/* 126 */           if (intlist.contains(this.field_194108_e[j]))
/*     */           {
/* 128 */             this.field_194110_g.set(func_194095_d(true, j, i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_194092_a(int p_194092_1_, @Nullable IntList p_194092_2_) {
/* 136 */       if (p_194092_1_ <= 0)
/*     */       {
/* 138 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       for (int k = 0; func_194098_a(p_194092_1_); k++) {
/*     */         
/* 146 */         RecipeItemHelper.this.func_194122_a(this.field_194108_e[this.field_194111_h.getInt(0)], p_194092_1_);
/* 147 */         int l = this.field_194111_h.size() - 1;
/* 148 */         func_194096_c(this.field_194111_h.getInt(l));
/*     */         
/* 150 */         for (int i1 = 0; i1 < l; i1++)
/*     */         {
/* 152 */           func_194089_c(((i1 & 0x1) == 0), ((Integer)this.field_194111_h.get(i1)).intValue(), ((Integer)this.field_194111_h.get(i1 + 1)).intValue());
/*     */         }
/*     */         
/* 155 */         this.field_194111_h.clear();
/* 156 */         this.field_194110_g.clear(0, this.field_194107_d + this.field_194109_f);
/*     */       } 
/*     */       
/* 159 */       boolean flag = (k == this.field_194107_d);
/* 160 */       boolean flag1 = (flag && p_194092_2_ != null);
/*     */       
/* 162 */       if (flag1)
/*     */       {
/* 164 */         p_194092_2_.clear();
/*     */       }
/*     */       
/* 167 */       this.field_194110_g.clear(0, this.field_194107_d + this.field_194109_f + this.field_194107_d);
/* 168 */       int j1 = 0;
/* 169 */       NonNullList<Ingredient> nonNullList = this.field_194105_b.func_192400_c();
/*     */       
/* 171 */       for (int k1 = 0; k1 < nonNullList.size(); k1++) {
/*     */         
/* 173 */         if (flag1 && nonNullList.get(k1) == Ingredient.field_193370_a) {
/*     */           
/* 175 */           p_194092_2_.add(0);
/*     */         }
/*     */         else {
/*     */           
/* 179 */           for (int l1 = 0; l1 < this.field_194109_f; l1++) {
/*     */             
/* 181 */             if (func_194100_b(false, j1, l1)) {
/*     */               
/* 183 */               func_194089_c(true, l1, j1);
/* 184 */               RecipeItemHelper.this.func_194117_b(this.field_194108_e[l1], p_194092_1_);
/*     */               
/* 186 */               if (flag1)
/*     */               {
/* 188 */                 p_194092_2_.add(this.field_194108_e[l1]);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 193 */           j1++;
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       return flag;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private int[] func_194097_a() {
/* 203 */       IntAVLTreeSet intAVLTreeSet = new IntAVLTreeSet();
/*     */       
/* 205 */       for (Ingredient ingredient : this.field_194106_c)
/*     */       {
/* 207 */         intAVLTreeSet.addAll((IntCollection)ingredient.func_194139_b());
/*     */       }
/*     */       
/* 210 */       IntIterator intiterator = intAVLTreeSet.iterator();
/*     */       
/* 212 */       while (intiterator.hasNext()) {
/*     */         
/* 214 */         if (!RecipeItemHelper.this.func_194120_a(intiterator.nextInt()))
/*     */         {
/* 216 */           intiterator.remove();
/*     */         }
/*     */       } 
/*     */       
/* 220 */       return intAVLTreeSet.toIntArray();
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_194098_a(int p_194098_1_) {
/* 225 */       int k = this.field_194109_f;
/*     */       
/* 227 */       for (int l = 0; l < k; l++) {
/*     */         
/* 229 */         if (RecipeItemHelper.this.field_194124_a.get(this.field_194108_e[l]) >= p_194098_1_) {
/*     */           
/* 231 */           func_194088_a(false, l);
/*     */           
/* 233 */           while (!this.field_194111_h.isEmpty()) {
/*     */             
/* 235 */             int i1 = this.field_194111_h.size();
/* 236 */             boolean flag = ((i1 & 0x1) == 1);
/* 237 */             int j1 = this.field_194111_h.getInt(i1 - 1);
/*     */             
/* 239 */             if (!flag && !func_194091_b(j1)) {
/*     */               break;
/*     */             }
/*     */ 
/*     */             
/* 244 */             int k1 = flag ? this.field_194107_d : k;
/*     */             
/* 246 */             for (int l1 = 0; l1 < k1; l1++) {
/*     */               
/* 248 */               if (!func_194101_b(flag, l1) && func_194093_a(flag, j1, l1) && func_194100_b(flag, j1, l1)) {
/*     */                 
/* 250 */                 func_194088_a(flag, l1);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 255 */             int i2 = this.field_194111_h.size();
/*     */             
/* 257 */             if (i2 == i1)
/*     */             {
/* 259 */               this.field_194111_h.removeInt(i2 - 1);
/*     */             }
/*     */           } 
/*     */           
/* 263 */           if (!this.field_194111_h.isEmpty())
/*     */           {
/* 265 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_194091_b(int p_194091_1_) {
/* 275 */       return this.field_194110_g.get(func_194094_d(p_194091_1_));
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_194096_c(int p_194096_1_) {
/* 280 */       this.field_194110_g.set(func_194094_d(p_194096_1_));
/*     */     }
/*     */ 
/*     */     
/*     */     private int func_194094_d(int p_194094_1_) {
/* 285 */       return this.field_194107_d + this.field_194109_f + p_194094_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_194093_a(boolean p_194093_1_, int p_194093_2_, int p_194093_3_) {
/* 290 */       return this.field_194110_g.get(func_194095_d(p_194093_1_, p_194093_2_, p_194093_3_));
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_194100_b(boolean p_194100_1_, int p_194100_2_, int p_194100_3_) {
/* 295 */       return p_194100_1_ ^ this.field_194110_g.get(1 + func_194095_d(p_194100_1_, p_194100_2_, p_194100_3_));
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_194089_c(boolean p_194089_1_, int p_194089_2_, int p_194089_3_) {
/* 300 */       this.field_194110_g.flip(1 + func_194095_d(p_194089_1_, p_194089_2_, p_194089_3_));
/*     */     }
/*     */ 
/*     */     
/*     */     private int func_194095_d(boolean p_194095_1_, int p_194095_2_, int p_194095_3_) {
/* 305 */       int k = p_194095_1_ ? (p_194095_2_ * this.field_194107_d + p_194095_3_) : (p_194095_3_ * this.field_194107_d + p_194095_2_);
/* 306 */       return this.field_194107_d + this.field_194109_f + this.field_194107_d + 2 * k;
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_194088_a(boolean p_194088_1_, int p_194088_2_) {
/* 311 */       this.field_194110_g.set(func_194099_c(p_194088_1_, p_194088_2_));
/* 312 */       this.field_194111_h.add(p_194088_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean func_194101_b(boolean p_194101_1_, int p_194101_2_) {
/* 317 */       return this.field_194110_g.get(func_194099_c(p_194101_1_, p_194101_2_));
/*     */     }
/*     */ 
/*     */     
/*     */     private int func_194099_c(boolean p_194099_1_, int p_194099_2_) {
/* 322 */       return (p_194099_1_ ? 0 : this.field_194107_d) + p_194099_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_194102_b(int p_194102_1_, @Nullable IntList p_194102_2_) {
/* 327 */       int k = 0;
/* 328 */       int l = Math.min(p_194102_1_, func_194090_b()) + 1;
/*     */ 
/*     */       
/*     */       while (true) {
/* 332 */         int i1 = (k + l) / 2;
/*     */         
/* 334 */         if (func_194092_a(i1, null)) {
/*     */           
/* 336 */           if (l - k <= 1) {
/*     */             
/* 338 */             if (i1 > 0)
/*     */             {
/* 340 */               func_194092_a(i1, p_194102_2_);
/*     */             }
/*     */             
/* 343 */             return i1;
/*     */           } 
/*     */           
/* 346 */           k = i1;
/*     */           
/*     */           continue;
/*     */         } 
/* 350 */         l = i1;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private int func_194090_b() {
/* 357 */       int k = Integer.MAX_VALUE;
/*     */       
/* 359 */       for (Ingredient ingredient : this.field_194106_c) {
/*     */         
/* 361 */         int l = 0;
/*     */ 
/*     */         
/* 364 */         for (IntListIterator intlistiterator = ingredient.func_194139_b().iterator(); intlistiterator.hasNext(); l = Math.max(l, RecipeItemHelper.this.field_194124_a.get(i1)))
/*     */         {
/* 366 */           int i1 = ((Integer)intlistiterator.next()).intValue();
/*     */         }
/*     */         
/* 369 */         if (k > 0)
/*     */         {
/* 371 */           k = Math.min(k, l);
/*     */         }
/*     */       } 
/*     */       
/* 375 */       return k;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\RecipeItemHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */