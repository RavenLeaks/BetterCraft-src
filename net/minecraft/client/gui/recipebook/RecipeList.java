/*     */ package net.minecraft.client.gui.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.util.RecipeItemHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ 
/*     */ 
/*     */ public class RecipeList
/*     */ {
/*  14 */   private List<IRecipe> field_192713_b = Lists.newArrayList();
/*  15 */   private final BitSet field_194215_b = new BitSet();
/*  16 */   private final BitSet field_194216_c = new BitSet();
/*  17 */   private final BitSet field_194217_d = new BitSet();
/*     */   
/*     */   private boolean field_194218_e = true;
/*     */   
/*     */   public boolean func_194209_a() {
/*  22 */     return !this.field_194217_d.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194214_a(RecipeBook p_194214_1_) {
/*  27 */     for (int i = 0; i < this.field_192713_b.size(); i++)
/*     */     {
/*  29 */       this.field_194217_d.set(i, p_194214_1_.func_193830_f(this.field_192713_b.get(i)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194210_a(RecipeItemHelper p_194210_1_, int p_194210_2_, int p_194210_3_, RecipeBook p_194210_4_) {
/*  35 */     for (int i = 0; i < this.field_192713_b.size(); i++) {
/*     */       
/*  37 */       IRecipe irecipe = this.field_192713_b.get(i);
/*  38 */       boolean flag = (irecipe.func_194133_a(p_194210_2_, p_194210_3_) && p_194210_4_.func_193830_f(irecipe));
/*  39 */       this.field_194216_c.set(i, flag);
/*  40 */       this.field_194215_b.set(i, (flag && p_194210_1_.func_194116_a(irecipe, null)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194213_a(IRecipe p_194213_1_) {
/*  46 */     return this.field_194215_b.get(this.field_192713_b.indexOf(p_194213_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192708_c() {
/*  51 */     return !this.field_194215_b.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194212_c() {
/*  56 */     return !this.field_194216_c.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IRecipe> func_192711_b() {
/*  61 */     return this.field_192713_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IRecipe> func_194208_a(boolean p_194208_1_) {
/*  66 */     List<IRecipe> list = Lists.newArrayList();
/*     */     
/*  68 */     for (int i = this.field_194217_d.nextSetBit(0); i >= 0; i = this.field_194217_d.nextSetBit(i + 1)) {
/*     */       
/*  70 */       if ((p_194208_1_ ? this.field_194215_b : this.field_194216_c).get(i))
/*     */       {
/*  72 */         list.add(this.field_192713_b.get(i));
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IRecipe> func_194207_b(boolean p_194207_1_) {
/*  81 */     List<IRecipe> list = Lists.newArrayList();
/*     */     
/*  83 */     for (int i = this.field_194217_d.nextSetBit(0); i >= 0; i = this.field_194217_d.nextSetBit(i + 1)) {
/*     */       
/*  85 */       if (this.field_194216_c.get(i) && this.field_194215_b.get(i) == p_194207_1_)
/*     */       {
/*  87 */         list.add(this.field_192713_b.get(i));
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192709_a(IRecipe p_192709_1_) {
/*  96 */     this.field_192713_b.add(p_192709_1_);
/*     */     
/*  98 */     if (this.field_194218_e) {
/*     */       
/* 100 */       ItemStack itemstack = ((IRecipe)this.field_192713_b.get(0)).getRecipeOutput();
/* 101 */       ItemStack itemstack1 = p_192709_1_.getRecipeOutput();
/* 102 */       this.field_194218_e = (ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194211_e() {
/* 108 */     return this.field_194218_e;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\recipebook\RecipeList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */