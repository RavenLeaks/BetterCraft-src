/*     */ package net.minecraft.init;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.potion.Potion;
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
/*     */ public class MobEffects
/*     */ {
/*     */   @Nullable
/*     */   private static Potion getRegisteredMobEffect(String id) {
/*  66 */     Potion potion = (Potion)Potion.REGISTRY.getObject(new ResourceLocation(id));
/*     */     
/*  68 */     if (potion == null)
/*     */     {
/*  70 */       throw new IllegalStateException("Invalid MobEffect requested: " + id);
/*     */     }
/*     */ 
/*     */     
/*  74 */     return potion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  80 */     if (!Bootstrap.isRegistered())
/*     */     {
/*  82 */       throw new RuntimeException("Accessed MobEffects before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/*  86 */   public static final Potion SPEED = getRegisteredMobEffect("speed");
/*  87 */   public static final Potion SLOWNESS = getRegisteredMobEffect("slowness");
/*  88 */   public static final Potion HASTE = getRegisteredMobEffect("haste");
/*  89 */   public static final Potion MINING_FATIGUE = getRegisteredMobEffect("mining_fatigue");
/*  90 */   public static final Potion STRENGTH = getRegisteredMobEffect("strength");
/*  91 */   public static final Potion INSTANT_HEALTH = getRegisteredMobEffect("instant_health");
/*  92 */   public static final Potion INSTANT_DAMAGE = getRegisteredMobEffect("instant_damage");
/*  93 */   public static final Potion JUMP_BOOST = getRegisteredMobEffect("jump_boost");
/*  94 */   public static final Potion NAUSEA = getRegisteredMobEffect("nausea");
/*  95 */   public static final Potion REGENERATION = getRegisteredMobEffect("regeneration");
/*  96 */   public static final Potion RESISTANCE = getRegisteredMobEffect("resistance");
/*  97 */   public static final Potion FIRE_RESISTANCE = getRegisteredMobEffect("fire_resistance");
/*  98 */   public static final Potion WATER_BREATHING = getRegisteredMobEffect("water_breathing");
/*  99 */   public static final Potion INVISIBILITY = getRegisteredMobEffect("invisibility");
/* 100 */   public static final Potion BLINDNESS = getRegisteredMobEffect("blindness");
/* 101 */   public static final Potion NIGHT_VISION = getRegisteredMobEffect("night_vision");
/* 102 */   public static final Potion HUNGER = getRegisteredMobEffect("hunger");
/* 103 */   public static final Potion WEAKNESS = getRegisteredMobEffect("weakness");
/* 104 */   public static final Potion POISON = getRegisteredMobEffect("poison");
/* 105 */   public static final Potion WITHER = getRegisteredMobEffect("wither");
/* 106 */   public static final Potion HEALTH_BOOST = getRegisteredMobEffect("health_boost");
/* 107 */   public static final Potion ABSORPTION = getRegisteredMobEffect("absorption");
/* 108 */   public static final Potion SATURATION = getRegisteredMobEffect("saturation");
/* 109 */   public static final Potion GLOWING = getRegisteredMobEffect("glowing");
/* 110 */   public static final Potion LEVITATION = getRegisteredMobEffect("levitation");
/* 111 */   public static final Potion LUCK = getRegisteredMobEffect("luck");
/* 112 */   public static final Potion UNLUCK = getRegisteredMobEffect("unluck");
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\MobEffects.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */