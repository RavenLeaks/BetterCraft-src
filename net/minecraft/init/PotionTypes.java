/*     */ package net.minecraft.init;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PotionTypes
/*     */ {
/*     */   private static PotionType getRegisteredPotionType(String id) {
/*  50 */     PotionType potiontype = (PotionType)PotionType.REGISTRY.getObject(new ResourceLocation(id));
/*     */     
/*  52 */     if (!CACHE.add(potiontype))
/*     */     {
/*  54 */       throw new IllegalStateException("Invalid Potion requested: " + id);
/*     */     }
/*     */ 
/*     */     
/*  58 */     return potiontype;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  64 */     if (!Bootstrap.isRegistered())
/*     */     {
/*  66 */       throw new RuntimeException("Accessed Potions before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/*  70 */   private static final Set<PotionType> CACHE = Sets.newHashSet();
/*  71 */   public static final PotionType EMPTY = getRegisteredPotionType("empty");
/*  72 */   public static final PotionType WATER = getRegisteredPotionType("water");
/*  73 */   public static final PotionType MUNDANE = getRegisteredPotionType("mundane");
/*  74 */   public static final PotionType THICK = getRegisteredPotionType("thick");
/*  75 */   public static final PotionType AWKWARD = getRegisteredPotionType("awkward");
/*  76 */   public static final PotionType NIGHT_VISION = getRegisteredPotionType("night_vision");
/*  77 */   public static final PotionType LONG_NIGHT_VISION = getRegisteredPotionType("long_night_vision");
/*  78 */   public static final PotionType INVISIBILITY = getRegisteredPotionType("invisibility");
/*  79 */   public static final PotionType LONG_INVISIBILITY = getRegisteredPotionType("long_invisibility");
/*  80 */   public static final PotionType LEAPING = getRegisteredPotionType("leaping");
/*  81 */   public static final PotionType LONG_LEAPING = getRegisteredPotionType("long_leaping");
/*  82 */   public static final PotionType STRONG_LEAPING = getRegisteredPotionType("strong_leaping");
/*  83 */   public static final PotionType FIRE_RESISTANCE = getRegisteredPotionType("fire_resistance");
/*  84 */   public static final PotionType LONG_FIRE_RESISTANCE = getRegisteredPotionType("long_fire_resistance");
/*  85 */   public static final PotionType SWIFTNESS = getRegisteredPotionType("swiftness");
/*  86 */   public static final PotionType LONG_SWIFTNESS = getRegisteredPotionType("long_swiftness");
/*  87 */   public static final PotionType STRONG_SWIFTNESS = getRegisteredPotionType("strong_swiftness");
/*  88 */   public static final PotionType SLOWNESS = getRegisteredPotionType("slowness");
/*  89 */   public static final PotionType LONG_SLOWNESS = getRegisteredPotionType("long_slowness");
/*  90 */   public static final PotionType WATER_BREATHING = getRegisteredPotionType("water_breathing");
/*  91 */   public static final PotionType LONG_WATER_BREATHING = getRegisteredPotionType("long_water_breathing");
/*  92 */   public static final PotionType HEALING = getRegisteredPotionType("healing");
/*  93 */   public static final PotionType STRONG_HEALING = getRegisteredPotionType("strong_healing");
/*  94 */   public static final PotionType HARMING = getRegisteredPotionType("harming");
/*  95 */   public static final PotionType STRONG_HARMING = getRegisteredPotionType("strong_harming");
/*  96 */   public static final PotionType POISON = getRegisteredPotionType("poison");
/*  97 */   public static final PotionType LONG_POISON = getRegisteredPotionType("long_poison");
/*  98 */   public static final PotionType STRONG_POISON = getRegisteredPotionType("strong_poison");
/*  99 */   public static final PotionType REGENERATION = getRegisteredPotionType("regeneration");
/* 100 */   public static final PotionType LONG_REGENERATION = getRegisteredPotionType("long_regeneration");
/* 101 */   public static final PotionType STRONG_REGENERATION = getRegisteredPotionType("strong_regeneration");
/* 102 */   public static final PotionType STRENGTH = getRegisteredPotionType("strength");
/* 103 */   public static final PotionType LONG_STRENGTH = getRegisteredPotionType("long_strength");
/* 104 */   public static final PotionType STRONG_STRENGTH = getRegisteredPotionType("strong_strength");
/* 105 */   public static final PotionType WEAKNESS = getRegisteredPotionType("weakness");
/* 106 */   public static final PotionType LONG_WEAKNESS = getRegisteredPotionType("long_weakness"); static {
/* 107 */     CACHE.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\PotionTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */