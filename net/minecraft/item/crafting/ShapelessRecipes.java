/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ShapelessRecipes
/*     */   implements IRecipe
/*     */ {
/*     */   private final ItemStack recipeOutput;
/*     */   private final NonNullList<Ingredient> recipeItems;
/*     */   private final String field_194138_c;
/*     */   
/*     */   public ShapelessRecipes(String p_i47500_1_, ItemStack p_i47500_2_, NonNullList<Ingredient> p_i47500_3_) {
/*  23 */     this.field_194138_c = p_i47500_1_;
/*  24 */     this.recipeOutput = p_i47500_2_;
/*  25 */     this.recipeItems = p_i47500_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_193358_e() {
/*  30 */     return this.field_194138_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  35 */     return this.recipeOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<Ingredient> func_192400_c() {
/*  40 */     return this.recipeItems;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/*  45 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/*  47 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/*  49 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  51 */       if (itemstack.getItem().hasContainerItem())
/*     */       {
/*  53 */         nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */       }
/*     */     } 
/*     */     
/*  57 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  65 */     List<Ingredient> list = Lists.newArrayList((Iterable)this.recipeItems);
/*     */     
/*  67 */     for (int i = 0; i < inv.getHeight(); i++) {
/*     */       
/*  69 */       for (int j = 0; j < inv.getWidth(); j++) {
/*     */         
/*  71 */         ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
/*     */         
/*  73 */         if (!itemstack.func_190926_b()) {
/*     */           
/*  75 */           boolean flag = false;
/*     */           
/*  77 */           for (Ingredient ingredient : list) {
/*     */             
/*  79 */             if (ingredient.apply(itemstack)) {
/*     */               
/*  81 */               flag = true;
/*  82 */               list.remove(ingredient);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*  87 */           if (!flag)
/*     */           {
/*  89 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return list.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 103 */     return this.recipeOutput.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShapelessRecipes func_193363_a(JsonObject p_193363_0_) {
/* 108 */     String s = JsonUtils.getString(p_193363_0_, "group", "");
/* 109 */     NonNullList<Ingredient> nonnulllist = func_193364_a(JsonUtils.getJsonArray(p_193363_0_, "ingredients"));
/*     */     
/* 111 */     if (nonnulllist.isEmpty())
/*     */     {
/* 113 */       throw new JsonParseException("No ingredients for shapeless recipe");
/*     */     }
/* 115 */     if (nonnulllist.size() > 9)
/*     */     {
/* 117 */       throw new JsonParseException("Too many ingredients for shapeless recipe");
/*     */     }
/*     */ 
/*     */     
/* 121 */     ItemStack itemstack = ShapedRecipes.func_192405_a(JsonUtils.getJsonObject(p_193363_0_, "result"), true);
/* 122 */     return new ShapelessRecipes(s, itemstack, nonnulllist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static NonNullList<Ingredient> func_193364_a(JsonArray p_193364_0_) {
/* 128 */     NonNullList<Ingredient> nonnulllist = NonNullList.func_191196_a();
/*     */     
/* 130 */     for (int i = 0; i < p_193364_0_.size(); i++) {
/*     */       
/* 132 */       Ingredient ingredient = ShapedRecipes.func_193361_a(p_193364_0_.get(i));
/*     */       
/* 134 */       if (ingredient != Ingredient.field_193370_a)
/*     */       {
/* 136 */         nonnulllist.add(ingredient);
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/* 145 */     return (p_194133_1_ * p_194133_2_ >= this.recipeItems.size());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\ShapelessRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */