/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapedRecipes
/*     */   implements IRecipe
/*     */ {
/*     */   private final int recipeWidth;
/*     */   private final int recipeHeight;
/*     */   private final NonNullList<Ingredient> recipeItems;
/*     */   private final ItemStack recipeOutput;
/*     */   private final String field_194137_e;
/*     */   
/*     */   public ShapedRecipes(String p_i47501_1_, int p_i47501_2_, int p_i47501_3_, NonNullList<Ingredient> p_i47501_4_, ItemStack p_i47501_5_) {
/*  38 */     this.field_194137_e = p_i47501_1_;
/*  39 */     this.recipeWidth = p_i47501_2_;
/*  40 */     this.recipeHeight = p_i47501_3_;
/*  41 */     this.recipeItems = p_i47501_4_;
/*  42 */     this.recipeOutput = p_i47501_5_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_193358_e() {
/*  47 */     return this.field_194137_e;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  52 */     return this.recipeOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
/*  57 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(inv.getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/*  59 */     for (int i = 0; i < nonnulllist.size(); i++) {
/*     */       
/*  61 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  63 */       if (itemstack.getItem().hasContainerItem())
/*     */       {
/*  65 */         nonnulllist.set(i, new ItemStack(itemstack.getItem().getContainerItem()));
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<Ingredient> func_192400_c() {
/*  74 */     return this.recipeItems;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_194133_a(int p_194133_1_, int p_194133_2_) {
/*  79 */     return (p_194133_1_ >= this.recipeWidth && p_194133_2_ >= this.recipeHeight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  87 */     for (int i = 0; i <= 3 - this.recipeWidth; i++) {
/*     */       
/*  89 */       for (int j = 0; j <= 3 - this.recipeHeight; j++) {
/*     */         
/*  91 */         if (checkMatch(inv, i, j, true))
/*     */         {
/*  93 */           return true;
/*     */         }
/*     */         
/*  96 */         if (checkMatch(inv, i, j, false))
/*     */         {
/*  98 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
/* 111 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 113 */       for (int j = 0; j < 3; j++) {
/*     */         
/* 115 */         int k = i - p_77573_2_;
/* 116 */         int l = j - p_77573_3_;
/* 117 */         Ingredient ingredient = Ingredient.field_193370_a;
/*     */         
/* 119 */         if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight)
/*     */         {
/* 121 */           if (p_77573_4_) {
/*     */             
/* 123 */             ingredient = (Ingredient)this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
/*     */           }
/*     */           else {
/*     */             
/* 127 */             ingredient = (Ingredient)this.recipeItems.get(k + l * this.recipeWidth);
/*     */           } 
/*     */         }
/*     */         
/* 131 */         if (!ingredient.apply(p_77573_1_.getStackInRowAndColumn(i, j)))
/*     */         {
/* 133 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 146 */     return getRecipeOutput().copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_192403_f() {
/* 151 */     return this.recipeWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_192404_g() {
/* 156 */     return this.recipeHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShapedRecipes func_193362_a(JsonObject p_193362_0_) {
/* 161 */     String s = JsonUtils.getString(p_193362_0_, "group", "");
/* 162 */     Map<String, Ingredient> map = func_192408_a(JsonUtils.getJsonObject(p_193362_0_, "key"));
/* 163 */     String[] astring = func_194134_a(func_192407_a(JsonUtils.getJsonArray(p_193362_0_, "pattern")));
/* 164 */     int i = astring[0].length();
/* 165 */     int j = astring.length;
/* 166 */     NonNullList<Ingredient> nonnulllist = func_192402_a(astring, map, i, j);
/* 167 */     ItemStack itemstack = func_192405_a(JsonUtils.getJsonObject(p_193362_0_, "result"), true);
/* 168 */     return new ShapedRecipes(s, i, j, nonnulllist, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   private static NonNullList<Ingredient> func_192402_a(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_) {
/* 173 */     NonNullList<Ingredient> nonnulllist = NonNullList.func_191197_a(p_192402_2_ * p_192402_3_, Ingredient.field_193370_a);
/* 174 */     Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
/* 175 */     set.remove(" ");
/*     */     
/* 177 */     for (int i = 0; i < p_192402_0_.length; i++) {
/*     */       
/* 179 */       for (int j = 0; j < p_192402_0_[i].length(); j++) {
/*     */         
/* 181 */         String s = p_192402_0_[i].substring(j, j + 1);
/* 182 */         Ingredient ingredient = p_192402_1_.get(s);
/*     */         
/* 184 */         if (ingredient == null)
/*     */         {
/* 186 */           throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
/*     */         }
/*     */         
/* 189 */         set.remove(s);
/* 190 */         nonnulllist.set(j + p_192402_2_ * i, ingredient);
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     if (!set.isEmpty())
/*     */     {
/* 196 */       throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
/*     */     }
/*     */ 
/*     */     
/* 200 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static String[] func_194134_a(String... p_194134_0_) {
/* 207 */     int i = Integer.MAX_VALUE;
/* 208 */     int j = 0;
/* 209 */     int k = 0;
/* 210 */     int l = 0;
/*     */     
/* 212 */     for (int i1 = 0; i1 < p_194134_0_.length; i1++) {
/*     */       
/* 214 */       String s = p_194134_0_[i1];
/* 215 */       i = Math.min(i, func_194135_a(s));
/* 216 */       int j1 = func_194136_b(s);
/* 217 */       j = Math.max(j, j1);
/*     */       
/* 219 */       if (j1 < 0) {
/*     */         
/* 221 */         if (k == i1)
/*     */         {
/* 223 */           k++;
/*     */         }
/*     */         
/* 226 */         l++;
/*     */       }
/*     */       else {
/*     */         
/* 230 */         l = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     if (p_194134_0_.length == l)
/*     */     {
/* 236 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 240 */     String[] astring = new String[p_194134_0_.length - l - k];
/*     */     
/* 242 */     for (int k1 = 0; k1 < astring.length; k1++)
/*     */     {
/* 244 */       astring[k1] = p_194134_0_[k1 + k].substring(i, j + 1);
/*     */     }
/*     */     
/* 247 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int func_194135_a(String p_194135_0_) {
/* 255 */     for (int i = 0; i < p_194135_0_.length() && p_194135_0_.charAt(i) == ' '; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int func_194136_b(String p_194136_0_) {
/* 267 */     for (int i = p_194136_0_.length() - 1; i >= 0 && p_194136_0_.charAt(i) == ' '; i--);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] func_192407_a(JsonArray p_192407_0_) {
/* 277 */     String[] astring = new String[p_192407_0_.size()];
/*     */     
/* 279 */     if (astring.length > 3)
/*     */     {
/* 281 */       throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
/*     */     }
/* 283 */     if (astring.length == 0)
/*     */     {
/* 285 */       throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
/*     */     }
/*     */ 
/*     */     
/* 289 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 291 */       String s = JsonUtils.getString(p_192407_0_.get(i), "pattern[" + i + "]");
/*     */       
/* 293 */       if (s.length() > 3)
/*     */       {
/* 295 */         throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
/*     */       }
/*     */       
/* 298 */       if (i > 0 && astring[0].length() != s.length())
/*     */       {
/* 300 */         throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
/*     */       }
/*     */       
/* 303 */       astring[i] = s;
/*     */     } 
/*     */     
/* 306 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Ingredient> func_192408_a(JsonObject p_192408_0_) {
/* 312 */     Map<String, Ingredient> map = Maps.newHashMap();
/*     */     
/* 314 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)p_192408_0_.entrySet()) {
/*     */       
/* 316 */       if (((String)entry.getKey()).length() != 1)
/*     */       {
/* 318 */         throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
/*     */       }
/*     */       
/* 321 */       if (" ".equals(entry.getKey()))
/*     */       {
/* 323 */         throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
/*     */       }
/*     */       
/* 326 */       map.put(entry.getKey(), func_193361_a(entry.getValue()));
/*     */     } 
/*     */     
/* 329 */     map.put(" ", Ingredient.field_193370_a);
/* 330 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Ingredient func_193361_a(@Nullable JsonElement p_193361_0_) {
/* 335 */     if (p_193361_0_ != null && !p_193361_0_.isJsonNull()) {
/*     */       
/* 337 */       if (p_193361_0_.isJsonObject())
/*     */       {
/* 339 */         return Ingredient.func_193369_a(new ItemStack[] { func_192405_a(p_193361_0_.getAsJsonObject(), false) });
/*     */       }
/* 341 */       if (!p_193361_0_.isJsonArray())
/*     */       {
/* 343 */         throw new JsonSyntaxException("Expected item to be object or array of objects");
/*     */       }
/*     */ 
/*     */       
/* 347 */       JsonArray jsonarray = p_193361_0_.getAsJsonArray();
/*     */       
/* 349 */       if (jsonarray.size() == 0)
/*     */       {
/* 351 */         throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
/*     */       }
/*     */ 
/*     */       
/* 355 */       ItemStack[] aitemstack = new ItemStack[jsonarray.size()];
/*     */       
/* 357 */       for (int i = 0; i < jsonarray.size(); i++)
/*     */       {
/* 359 */         aitemstack[i] = func_192405_a(JsonUtils.getJsonObject(jsonarray.get(i), "item"), false);
/*     */       }
/*     */       
/* 362 */       return Ingredient.func_193369_a(aitemstack);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     throw new JsonSyntaxException("Item cannot be null");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack func_192405_a(JsonObject p_192405_0_, boolean p_192405_1_) {
/* 374 */     String s = JsonUtils.getString(p_192405_0_, "item");
/* 375 */     Item item = (Item)Item.REGISTRY.getObject(new ResourceLocation(s));
/*     */     
/* 377 */     if (item == null)
/*     */     {
/* 379 */       throw new JsonSyntaxException("Unknown item '" + s + "'");
/*     */     }
/* 381 */     if (item.getHasSubtypes() && !p_192405_0_.has("data"))
/*     */     {
/* 383 */       throw new JsonParseException("Missing data for item '" + s + "'");
/*     */     }
/*     */ 
/*     */     
/* 387 */     int i = JsonUtils.getInt(p_192405_0_, "data", 0);
/* 388 */     int j = p_192405_1_ ? JsonUtils.getInt(p_192405_0_, "count", 1) : 1;
/* 389 */     return new ItemStack(item, j, i);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\item\crafting\ShapedRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */