/*    */ package net.minecraft.init;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class Enchantments
/*    */ {
/*  9 */   public static final Enchantment PROTECTION = getRegisteredEnchantment("protection");
/*    */ 
/*    */   
/* 12 */   public static final Enchantment FIRE_PROTECTION = getRegisteredEnchantment("fire_protection");
/* 13 */   public static final Enchantment FEATHER_FALLING = getRegisteredEnchantment("feather_falling");
/* 14 */   public static final Enchantment BLAST_PROTECTION = getRegisteredEnchantment("blast_protection");
/* 15 */   public static final Enchantment PROJECTILE_PROTECTION = getRegisteredEnchantment("projectile_protection");
/* 16 */   public static final Enchantment RESPIRATION = getRegisteredEnchantment("respiration");
/* 17 */   public static final Enchantment AQUA_AFFINITY = getRegisteredEnchantment("aqua_affinity");
/* 18 */   public static final Enchantment THORNS = getRegisteredEnchantment("thorns");
/* 19 */   public static final Enchantment DEPTH_STRIDER = getRegisteredEnchantment("depth_strider");
/* 20 */   public static final Enchantment FROST_WALKER = getRegisteredEnchantment("frost_walker");
/* 21 */   public static final Enchantment field_190941_k = getRegisteredEnchantment("binding_curse");
/* 22 */   public static final Enchantment SHARPNESS = getRegisteredEnchantment("sharpness");
/* 23 */   public static final Enchantment SMITE = getRegisteredEnchantment("smite");
/* 24 */   public static final Enchantment BANE_OF_ARTHROPODS = getRegisteredEnchantment("bane_of_arthropods");
/* 25 */   public static final Enchantment KNOCKBACK = getRegisteredEnchantment("knockback");
/*    */ 
/*    */   
/* 28 */   public static final Enchantment FIRE_ASPECT = getRegisteredEnchantment("fire_aspect");
/* 29 */   public static final Enchantment LOOTING = getRegisteredEnchantment("looting");
/* 30 */   public static final Enchantment field_191530_r = getRegisteredEnchantment("sweeping");
/* 31 */   public static final Enchantment EFFICIENCY = getRegisteredEnchantment("efficiency");
/* 32 */   public static final Enchantment SILK_TOUCH = getRegisteredEnchantment("silk_touch");
/* 33 */   public static final Enchantment UNBREAKING = getRegisteredEnchantment("unbreaking");
/* 34 */   public static final Enchantment FORTUNE = getRegisteredEnchantment("fortune");
/* 35 */   public static final Enchantment POWER = getRegisteredEnchantment("power");
/* 36 */   public static final Enchantment PUNCH = getRegisteredEnchantment("punch");
/* 37 */   public static final Enchantment FLAME = getRegisteredEnchantment("flame");
/* 38 */   public static final Enchantment INFINITY = getRegisteredEnchantment("infinity");
/* 39 */   public static final Enchantment LUCK_OF_THE_SEA = getRegisteredEnchantment("luck_of_the_sea");
/* 40 */   public static final Enchantment LURE = getRegisteredEnchantment("lure");
/* 41 */   public static final Enchantment MENDING = getRegisteredEnchantment("mending");
/* 42 */   public static final Enchantment field_190940_C = getRegisteredEnchantment("vanishing_curse");
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private static Enchantment getRegisteredEnchantment(String id) {
/* 47 */     Enchantment enchantment = (Enchantment)Enchantment.REGISTRY.getObject(new ResourceLocation(id));
/*    */     
/* 49 */     if (enchantment == null)
/*    */     {
/* 51 */       throw new IllegalStateException("Invalid Enchantment requested: " + id);
/*    */     }
/*    */ 
/*    */     
/* 55 */     return enchantment;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 61 */     if (!Bootstrap.isRegistered())
/*    */     {
/* 63 */       throw new RuntimeException("Accessed Enchantments before Bootstrap!");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\Enchantments.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */