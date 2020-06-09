/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
/*     */ 
/*     */ public class PotionType
/*     */ {
/*  13 */   private static final ResourceLocation WATER = new ResourceLocation("empty");
/*  14 */   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, PotionType> REGISTRY = new RegistryNamespacedDefaultedByKey(WATER);
/*     */ 
/*     */   
/*     */   private static int nextPotionTypeId;
/*     */   
/*     */   private final String baseName;
/*     */   
/*     */   private final ImmutableList<PotionEffect> effects;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static PotionType getPotionTypeForName(String p_185168_0_) {
/*  26 */     return (PotionType)REGISTRY.getObject(new ResourceLocation(p_185168_0_));
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionType(PotionEffect... p_i46739_1_) {
/*  31 */     this(null, p_i46739_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public PotionType(@Nullable String p_i46740_1_, PotionEffect... p_i46740_2_) {
/*  36 */     this.baseName = p_i46740_1_;
/*  37 */     this.effects = ImmutableList.copyOf((Object[])p_i46740_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamePrefixed(String p_185174_1_) {
/*  45 */     return (this.baseName == null) ? (String.valueOf(p_185174_1_) + ((ResourceLocation)REGISTRY.getNameForObject(this)).getResourcePath()) : (String.valueOf(p_185174_1_) + this.baseName);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PotionEffect> getEffects() {
/*  50 */     return (List<PotionEffect>)this.effects;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerPotionTypes() {
/*  55 */     registerPotionType("empty", new PotionType(new PotionEffect[0]));
/*  56 */     registerPotionType("water", new PotionType(new PotionEffect[0]));
/*  57 */     registerPotionType("mundane", new PotionType(new PotionEffect[0]));
/*  58 */     registerPotionType("thick", new PotionType(new PotionEffect[0]));
/*  59 */     registerPotionType("awkward", new PotionType(new PotionEffect[0]));
/*  60 */     registerPotionType("night_vision", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.NIGHT_VISION, 3600) }));
/*  61 */     registerPotionType("long_night_vision", new PotionType("night_vision", new PotionEffect[] { new PotionEffect(MobEffects.NIGHT_VISION, 9600) }));
/*  62 */     registerPotionType("invisibility", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.INVISIBILITY, 3600) }));
/*  63 */     registerPotionType("long_invisibility", new PotionType("invisibility", new PotionEffect[] { new PotionEffect(MobEffects.INVISIBILITY, 9600) }));
/*  64 */     registerPotionType("leaping", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.JUMP_BOOST, 3600) }));
/*  65 */     registerPotionType("long_leaping", new PotionType("leaping", new PotionEffect[] { new PotionEffect(MobEffects.JUMP_BOOST, 9600) }));
/*  66 */     registerPotionType("strong_leaping", new PotionType("leaping", new PotionEffect[] { new PotionEffect(MobEffects.JUMP_BOOST, 1800, 1) }));
/*  67 */     registerPotionType("fire_resistance", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600) }));
/*  68 */     registerPotionType("long_fire_resistance", new PotionType("fire_resistance", new PotionEffect[] { new PotionEffect(MobEffects.FIRE_RESISTANCE, 9600) }));
/*  69 */     registerPotionType("swiftness", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.SPEED, 3600) }));
/*  70 */     registerPotionType("long_swiftness", new PotionType("swiftness", new PotionEffect[] { new PotionEffect(MobEffects.SPEED, 9600) }));
/*  71 */     registerPotionType("strong_swiftness", new PotionType("swiftness", new PotionEffect[] { new PotionEffect(MobEffects.SPEED, 1800, 1) }));
/*  72 */     registerPotionType("slowness", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.SLOWNESS, 1800) }));
/*  73 */     registerPotionType("long_slowness", new PotionType("slowness", new PotionEffect[] { new PotionEffect(MobEffects.SLOWNESS, 4800) }));
/*  74 */     registerPotionType("water_breathing", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.WATER_BREATHING, 3600) }));
/*  75 */     registerPotionType("long_water_breathing", new PotionType("water_breathing", new PotionEffect[] { new PotionEffect(MobEffects.WATER_BREATHING, 9600) }));
/*  76 */     registerPotionType("healing", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.INSTANT_HEALTH, 1) }));
/*  77 */     registerPotionType("strong_healing", new PotionType("healing", new PotionEffect[] { new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 1) }));
/*  78 */     registerPotionType("harming", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.INSTANT_DAMAGE, 1) }));
/*  79 */     registerPotionType("strong_harming", new PotionType("harming", new PotionEffect[] { new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1) }));
/*  80 */     registerPotionType("poison", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.POISON, 900) }));
/*  81 */     registerPotionType("long_poison", new PotionType("poison", new PotionEffect[] { new PotionEffect(MobEffects.POISON, 1800) }));
/*  82 */     registerPotionType("strong_poison", new PotionType("poison", new PotionEffect[] { new PotionEffect(MobEffects.POISON, 432, 1) }));
/*  83 */     registerPotionType("regeneration", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.REGENERATION, 900) }));
/*  84 */     registerPotionType("long_regeneration", new PotionType("regeneration", new PotionEffect[] { new PotionEffect(MobEffects.REGENERATION, 1800) }));
/*  85 */     registerPotionType("strong_regeneration", new PotionType("regeneration", new PotionEffect[] { new PotionEffect(MobEffects.REGENERATION, 450, 1) }));
/*  86 */     registerPotionType("strength", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.STRENGTH, 3600) }));
/*  87 */     registerPotionType("long_strength", new PotionType("strength", new PotionEffect[] { new PotionEffect(MobEffects.STRENGTH, 9600) }));
/*  88 */     registerPotionType("strong_strength", new PotionType("strength", new PotionEffect[] { new PotionEffect(MobEffects.STRENGTH, 1800, 1) }));
/*  89 */     registerPotionType("weakness", new PotionType(new PotionEffect[] { new PotionEffect(MobEffects.WEAKNESS, 1800) }));
/*  90 */     registerPotionType("long_weakness", new PotionType("weakness", new PotionEffect[] { new PotionEffect(MobEffects.WEAKNESS, 4800) }));
/*  91 */     registerPotionType("luck", new PotionType("luck", new PotionEffect[] { new PotionEffect(MobEffects.LUCK, 6000) }));
/*  92 */     REGISTRY.validateKey();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void registerPotionType(String p_185173_0_, PotionType p_185173_1_) {
/*  97 */     REGISTRY.register(nextPotionTypeId++, new ResourceLocation(p_185173_0_), p_185173_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasInstantEffect() {
/* 102 */     if (!this.effects.isEmpty()) {
/*     */       
/* 104 */       UnmodifiableIterator unmodifiableiterator = this.effects.iterator();
/*     */       
/* 106 */       while (unmodifiableiterator.hasNext()) {
/*     */         
/* 108 */         PotionEffect potioneffect = (PotionEffect)unmodifiableiterator.next();
/*     */         
/* 110 */         if (potioneffect.getPotion().isInstant())
/*     */         {
/* 112 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */