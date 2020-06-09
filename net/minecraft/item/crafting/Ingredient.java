/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntComparators;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.Comparator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.util.RecipeItemHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class Ingredient implements Predicate<ItemStack> {
/*  14 */   public static final Ingredient field_193370_a = new Ingredient(new ItemStack[0])
/*     */     {
/*     */       public boolean apply(@Nullable ItemStack p_apply_1_)
/*     */       {
/*  18 */         return p_apply_1_.func_190926_b();
/*     */       }
/*     */     };
/*     */   
/*     */   private final ItemStack[] field_193371_b;
/*     */   private IntList field_194140_c;
/*     */   
/*     */   private Ingredient(ItemStack... p_i47503_1_) {
/*  26 */     this.field_193371_b = p_i47503_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] func_193365_a() {
/*  31 */     return this.field_193371_b;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean apply(@Nullable ItemStack p_apply_1_) {
/*  36 */     if (p_apply_1_ == null)
/*     */     {
/*  38 */       return false; } 
/*     */     byte b;
/*     */     int i;
/*     */     ItemStack[] arrayOfItemStack;
/*  42 */     for (i = (arrayOfItemStack = this.field_193371_b).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/*     */       
/*  44 */       if (itemstack.getItem() == p_apply_1_.getItem()) {
/*     */         
/*  46 */         int j = itemstack.getMetadata();
/*     */         
/*  48 */         if (j == 32767 || j == p_apply_1_.getMetadata())
/*     */         {
/*  50 */           return true;
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntList func_194139_b() {
/*  61 */     if (this.field_194140_c == null) {
/*     */       
/*  63 */       this.field_194140_c = (IntList)new IntArrayList(this.field_193371_b.length); byte b; int i;
/*     */       ItemStack[] arrayOfItemStack;
/*  65 */       for (i = (arrayOfItemStack = this.field_193371_b).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/*     */         
/*  67 */         this.field_194140_c.add(RecipeItemHelper.func_194113_b(itemstack));
/*     */         b++; }
/*     */       
/*  70 */       this.field_194140_c.sort((Comparator)IntComparators.NATURAL_COMPARATOR);
/*     */     } 
/*     */     
/*  73 */     return this.field_194140_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ingredient func_193367_a(Item p_193367_0_) {
/*  78 */     return func_193369_a(new ItemStack[] { new ItemStack(p_193367_0_, 1, 32767) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ingredient func_193368_a(Item... p_193368_0_) {
/*  83 */     ItemStack[] aitemstack = new ItemStack[p_193368_0_.length];
/*     */     
/*  85 */     for (int i = 0; i < p_193368_0_.length; i++)
/*     */     {
/*  87 */       aitemstack[i] = new ItemStack(p_193368_0_[i]);
/*     */     }
/*     */     
/*  90 */     return func_193369_a(aitemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ingredient func_193369_a(ItemStack... p_193369_0_) {
/*  95 */     if (p_193369_0_.length > 0) {
/*     */       byte b; int i; ItemStack[] arrayOfItemStack;
/*  97 */       for (i = (arrayOfItemStack = p_193369_0_).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/*     */         
/*  99 */         if (!itemstack.func_190926_b())
/*     */         {
/* 101 */           return new Ingredient(p_193369_0_);
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/* 106 */     return field_193370_a;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\Ingredient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */