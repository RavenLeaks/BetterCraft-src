/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemFishFood;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.Ingredient;
/*     */ 
/*     */ public class PotionHelper
/*     */ {
/*  16 */   private static final List<MixPredicate<PotionType>> POTION_TYPE_CONVERSIONS = Lists.newArrayList();
/*  17 */   private static final List<MixPredicate<Item>> POTION_ITEM_CONVERSIONS = Lists.newArrayList();
/*  18 */   private static final List<Ingredient> POTION_ITEMS = Lists.newArrayList();
/*  19 */   private static final Predicate<ItemStack> IS_POTION_ITEM = new Predicate<ItemStack>()
/*     */     {
/*     */       public boolean apply(ItemStack p_apply_1_)
/*     */       {
/*  23 */         for (Ingredient ingredient : PotionHelper.POTION_ITEMS) {
/*     */           
/*  25 */           if (ingredient.apply(p_apply_1_))
/*     */           {
/*  27 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*  31 */         return false;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public static boolean isReagent(ItemStack stack) {
/*  37 */     return !(!isItemConversionReagent(stack) && !isTypeConversionReagent(stack));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isItemConversionReagent(ItemStack stack) {
/*  42 */     int i = 0;
/*     */     
/*  44 */     for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; i++) {
/*     */       
/*  46 */       if (((MixPredicate)POTION_ITEM_CONVERSIONS.get(i)).reagent.apply(stack))
/*     */       {
/*  48 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isTypeConversionReagent(ItemStack stack) {
/*  57 */     int i = 0;
/*     */     
/*  59 */     for (int j = POTION_TYPE_CONVERSIONS.size(); i < j; i++) {
/*     */       
/*  61 */       if (((MixPredicate)POTION_TYPE_CONVERSIONS.get(i)).reagent.apply(stack))
/*     */       {
/*  63 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean hasConversions(ItemStack input, ItemStack reagent) {
/*  72 */     if (!IS_POTION_ITEM.apply(input))
/*     */     {
/*  74 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  78 */     return !(!hasItemConversions(input, reagent) && !hasTypeConversions(input, reagent));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean hasItemConversions(ItemStack p_185206_0_, ItemStack p_185206_1_) {
/*  84 */     Item item = p_185206_0_.getItem();
/*  85 */     int i = 0;
/*     */     
/*  87 */     for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; i++) {
/*     */       
/*  89 */       MixPredicate<Item> mixpredicate = POTION_ITEM_CONVERSIONS.get(i);
/*     */       
/*  91 */       if (mixpredicate.input == item && mixpredicate.reagent.apply(p_185206_1_))
/*     */       {
/*  93 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean hasTypeConversions(ItemStack p_185209_0_, ItemStack p_185209_1_) {
/* 102 */     PotionType potiontype = PotionUtils.getPotionFromItem(p_185209_0_);
/* 103 */     int i = 0;
/*     */     
/* 105 */     for (int j = POTION_TYPE_CONVERSIONS.size(); i < j; i++) {
/*     */       
/* 107 */       MixPredicate<PotionType> mixpredicate = POTION_TYPE_CONVERSIONS.get(i);
/*     */       
/* 109 */       if (mixpredicate.input == potiontype && mixpredicate.reagent.apply(p_185209_1_))
/*     */       {
/* 111 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack doReaction(ItemStack reagent, ItemStack potionIn) {
/* 120 */     if (!potionIn.func_190926_b()) {
/*     */       
/* 122 */       PotionType potiontype = PotionUtils.getPotionFromItem(potionIn);
/* 123 */       Item item = potionIn.getItem();
/* 124 */       int i = 0;
/*     */       
/* 126 */       for (int j = POTION_ITEM_CONVERSIONS.size(); i < j; i++) {
/*     */         
/* 128 */         MixPredicate<Item> mixpredicate = POTION_ITEM_CONVERSIONS.get(i);
/*     */         
/* 130 */         if (mixpredicate.input == item && mixpredicate.reagent.apply(reagent))
/*     */         {
/* 132 */           return PotionUtils.addPotionToItemStack(new ItemStack((Item)mixpredicate.output), potiontype);
/*     */         }
/*     */       } 
/*     */       
/* 136 */       i = 0;
/*     */       
/* 138 */       for (int k = POTION_TYPE_CONVERSIONS.size(); i < k; i++) {
/*     */         
/* 140 */         MixPredicate<PotionType> mixpredicate1 = POTION_TYPE_CONVERSIONS.get(i);
/*     */         
/* 142 */         if (mixpredicate1.input == potiontype && mixpredicate1.reagent.apply(reagent))
/*     */         {
/* 144 */           return PotionUtils.addPotionToItemStack(new ItemStack(item), (PotionType)mixpredicate1.output);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     return potionIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/* 154 */     func_193354_a(Items.POTIONITEM);
/* 155 */     func_193354_a(Items.SPLASH_POTION);
/* 156 */     func_193354_a(Items.LINGERING_POTION);
/* 157 */     func_193355_a(Items.POTIONITEM, Items.GUNPOWDER, Items.SPLASH_POTION);
/* 158 */     func_193355_a(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION);
/* 159 */     func_193357_a(PotionTypes.WATER, Items.SPECKLED_MELON, PotionTypes.MUNDANE);
/* 160 */     func_193357_a(PotionTypes.WATER, Items.GHAST_TEAR, PotionTypes.MUNDANE);
/* 161 */     func_193357_a(PotionTypes.WATER, Items.RABBIT_FOOT, PotionTypes.MUNDANE);
/* 162 */     func_193357_a(PotionTypes.WATER, Items.BLAZE_POWDER, PotionTypes.MUNDANE);
/* 163 */     func_193357_a(PotionTypes.WATER, Items.SPIDER_EYE, PotionTypes.MUNDANE);
/* 164 */     func_193357_a(PotionTypes.WATER, Items.SUGAR, PotionTypes.MUNDANE);
/* 165 */     func_193357_a(PotionTypes.WATER, Items.MAGMA_CREAM, PotionTypes.MUNDANE);
/* 166 */     func_193357_a(PotionTypes.WATER, Items.GLOWSTONE_DUST, PotionTypes.THICK);
/* 167 */     func_193357_a(PotionTypes.WATER, Items.REDSTONE, PotionTypes.MUNDANE);
/* 168 */     func_193357_a(PotionTypes.WATER, Items.NETHER_WART, PotionTypes.AWKWARD);
/* 169 */     func_193357_a(PotionTypes.AWKWARD, Items.GOLDEN_CARROT, PotionTypes.NIGHT_VISION);
/* 170 */     func_193357_a(PotionTypes.NIGHT_VISION, Items.REDSTONE, PotionTypes.LONG_NIGHT_VISION);
/* 171 */     func_193357_a(PotionTypes.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, PotionTypes.INVISIBILITY);
/* 172 */     func_193357_a(PotionTypes.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, PotionTypes.LONG_INVISIBILITY);
/* 173 */     func_193357_a(PotionTypes.INVISIBILITY, Items.REDSTONE, PotionTypes.LONG_INVISIBILITY);
/* 174 */     func_193357_a(PotionTypes.AWKWARD, Items.MAGMA_CREAM, PotionTypes.FIRE_RESISTANCE);
/* 175 */     func_193357_a(PotionTypes.FIRE_RESISTANCE, Items.REDSTONE, PotionTypes.LONG_FIRE_RESISTANCE);
/* 176 */     func_193357_a(PotionTypes.AWKWARD, Items.RABBIT_FOOT, PotionTypes.LEAPING);
/* 177 */     func_193357_a(PotionTypes.LEAPING, Items.REDSTONE, PotionTypes.LONG_LEAPING);
/* 178 */     func_193357_a(PotionTypes.LEAPING, Items.GLOWSTONE_DUST, PotionTypes.STRONG_LEAPING);
/* 179 */     func_193357_a(PotionTypes.LEAPING, Items.FERMENTED_SPIDER_EYE, PotionTypes.SLOWNESS);
/* 180 */     func_193357_a(PotionTypes.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, PotionTypes.LONG_SLOWNESS);
/* 181 */     func_193357_a(PotionTypes.SLOWNESS, Items.REDSTONE, PotionTypes.LONG_SLOWNESS);
/* 182 */     func_193357_a(PotionTypes.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, PotionTypes.SLOWNESS);
/* 183 */     func_193357_a(PotionTypes.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, PotionTypes.LONG_SLOWNESS);
/* 184 */     func_193357_a(PotionTypes.AWKWARD, Items.SUGAR, PotionTypes.SWIFTNESS);
/* 185 */     func_193357_a(PotionTypes.SWIFTNESS, Items.REDSTONE, PotionTypes.LONG_SWIFTNESS);
/* 186 */     func_193357_a(PotionTypes.SWIFTNESS, Items.GLOWSTONE_DUST, PotionTypes.STRONG_SWIFTNESS);
/* 187 */     func_193356_a(PotionTypes.AWKWARD, Ingredient.func_193369_a(new ItemStack[] { new ItemStack(Items.FISH, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()) }), PotionTypes.WATER_BREATHING);
/* 188 */     func_193357_a(PotionTypes.WATER_BREATHING, Items.REDSTONE, PotionTypes.LONG_WATER_BREATHING);
/* 189 */     func_193357_a(PotionTypes.AWKWARD, Items.SPECKLED_MELON, PotionTypes.HEALING);
/* 190 */     func_193357_a(PotionTypes.HEALING, Items.GLOWSTONE_DUST, PotionTypes.STRONG_HEALING);
/* 191 */     func_193357_a(PotionTypes.HEALING, Items.FERMENTED_SPIDER_EYE, PotionTypes.HARMING);
/* 192 */     func_193357_a(PotionTypes.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, PotionTypes.STRONG_HARMING);
/* 193 */     func_193357_a(PotionTypes.HARMING, Items.GLOWSTONE_DUST, PotionTypes.STRONG_HARMING);
/* 194 */     func_193357_a(PotionTypes.POISON, Items.FERMENTED_SPIDER_EYE, PotionTypes.HARMING);
/* 195 */     func_193357_a(PotionTypes.LONG_POISON, Items.FERMENTED_SPIDER_EYE, PotionTypes.HARMING);
/* 196 */     func_193357_a(PotionTypes.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, PotionTypes.STRONG_HARMING);
/* 197 */     func_193357_a(PotionTypes.AWKWARD, Items.SPIDER_EYE, PotionTypes.POISON);
/* 198 */     func_193357_a(PotionTypes.POISON, Items.REDSTONE, PotionTypes.LONG_POISON);
/* 199 */     func_193357_a(PotionTypes.POISON, Items.GLOWSTONE_DUST, PotionTypes.STRONG_POISON);
/* 200 */     func_193357_a(PotionTypes.AWKWARD, Items.GHAST_TEAR, PotionTypes.REGENERATION);
/* 201 */     func_193357_a(PotionTypes.REGENERATION, Items.REDSTONE, PotionTypes.LONG_REGENERATION);
/* 202 */     func_193357_a(PotionTypes.REGENERATION, Items.GLOWSTONE_DUST, PotionTypes.STRONG_REGENERATION);
/* 203 */     func_193357_a(PotionTypes.AWKWARD, Items.BLAZE_POWDER, PotionTypes.STRENGTH);
/* 204 */     func_193357_a(PotionTypes.STRENGTH, Items.REDSTONE, PotionTypes.LONG_STRENGTH);
/* 205 */     func_193357_a(PotionTypes.STRENGTH, Items.GLOWSTONE_DUST, PotionTypes.STRONG_STRENGTH);
/* 206 */     func_193357_a(PotionTypes.WATER, Items.FERMENTED_SPIDER_EYE, PotionTypes.WEAKNESS);
/* 207 */     func_193357_a(PotionTypes.WEAKNESS, Items.REDSTONE, PotionTypes.LONG_WEAKNESS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void func_193355_a(ItemPotion p_193355_0_, Item p_193355_1_, ItemPotion p_193355_2_) {
/* 212 */     POTION_ITEM_CONVERSIONS.add(new MixPredicate(p_193355_0_, Ingredient.func_193368_a(new Item[] { p_193355_1_ }, ), p_193355_2_));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void func_193354_a(ItemPotion p_193354_0_) {
/* 217 */     POTION_ITEMS.add(Ingredient.func_193368_a(new Item[] { (Item)p_193354_0_ }));
/*     */   }
/*     */ 
/*     */   
/*     */   private static void func_193357_a(PotionType p_193357_0_, Item p_193357_1_, PotionType p_193357_2_) {
/* 222 */     func_193356_a(p_193357_0_, Ingredient.func_193368_a(new Item[] { p_193357_1_ }, ), p_193357_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void func_193356_a(PotionType p_193356_0_, Ingredient p_193356_1_, PotionType p_193356_2_) {
/* 227 */     POTION_TYPE_CONVERSIONS.add(new MixPredicate<>(p_193356_0_, p_193356_1_, p_193356_2_));
/*     */   }
/*     */ 
/*     */   
/*     */   static class MixPredicate<T>
/*     */   {
/*     */     final T input;
/*     */     final Ingredient reagent;
/*     */     final T output;
/*     */     
/*     */     public MixPredicate(T p_i47570_1_, Ingredient p_i47570_2_, T p_i47570_3_) {
/* 238 */       this.input = p_i47570_1_;
/* 239 */       this.reagent = p_i47570_2_;
/* 240 */       this.output = p_i47570_3_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */