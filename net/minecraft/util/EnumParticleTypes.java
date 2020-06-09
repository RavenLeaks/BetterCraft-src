/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public enum EnumParticleTypes
/*     */ {
/*  10 */   EXPLOSION_NORMAL("explode", 0, true),
/*  11 */   EXPLOSION_LARGE("largeexplode", 1, true),
/*  12 */   EXPLOSION_HUGE("hugeexplosion", 2, true),
/*  13 */   FIREWORKS_SPARK("fireworksSpark", 3, false),
/*  14 */   WATER_BUBBLE("bubble", 4, false),
/*  15 */   WATER_SPLASH("splash", 5, false),
/*  16 */   WATER_WAKE("wake", 6, false),
/*  17 */   SUSPENDED("suspended", 7, false),
/*  18 */   SUSPENDED_DEPTH("depthsuspend", 8, false),
/*  19 */   CRIT("crit", 9, false),
/*  20 */   CRIT_MAGIC("magicCrit", 10, false),
/*  21 */   SMOKE_NORMAL("smoke", 11, false),
/*  22 */   SMOKE_LARGE("largesmoke", 12, false),
/*  23 */   SPELL("spell", 13, false),
/*  24 */   SPELL_INSTANT("instantSpell", 14, false),
/*  25 */   SPELL_MOB("mobSpell", 15, false),
/*  26 */   SPELL_MOB_AMBIENT("mobSpellAmbient", 16, false),
/*  27 */   SPELL_WITCH("witchMagic", 17, false),
/*  28 */   DRIP_WATER("dripWater", 18, false),
/*  29 */   DRIP_LAVA("dripLava", 19, false),
/*  30 */   VILLAGER_ANGRY("angryVillager", 20, false),
/*  31 */   VILLAGER_HAPPY("happyVillager", 21, false),
/*  32 */   TOWN_AURA("townaura", 22, false),
/*  33 */   NOTE("note", 23, false),
/*  34 */   PORTAL("portal", 24, false),
/*  35 */   ENCHANTMENT_TABLE("enchantmenttable", 25, false),
/*  36 */   FLAME("flame", 26, false),
/*  37 */   LAVA("lava", 27, false),
/*  38 */   FOOTSTEP("footstep", 28, false),
/*  39 */   CLOUD("cloud", 29, false),
/*  40 */   REDSTONE("reddust", 30, false),
/*  41 */   SNOWBALL("snowballpoof", 31, false),
/*  42 */   SNOW_SHOVEL("snowshovel", 32, false),
/*  43 */   SLIME("slime", 33, false),
/*  44 */   HEART("heart", 34, false),
/*  45 */   BARRIER("barrier", 35, false),
/*  46 */   ITEM_CRACK("iconcrack", 36, false, 2),
/*  47 */   BLOCK_CRACK("blockcrack", 37, false, 1),
/*  48 */   BLOCK_DUST("blockdust", 38, false, 1),
/*  49 */   WATER_DROP("droplet", 39, false),
/*  50 */   ITEM_TAKE("take", 40, false),
/*  51 */   MOB_APPEARANCE("mobappearance", 41, true),
/*  52 */   DRAGON_BREATH("dragonbreath", 42, false),
/*  53 */   END_ROD("endRod", 43, false),
/*  54 */   DAMAGE_INDICATOR("damageIndicator", 44, true),
/*  55 */   SWEEP_ATTACK("sweepAttack", 45, true),
/*  56 */   FALLING_DUST("fallingdust", 46, false, 1),
/*  57 */   TOTEM("totem", 47, false),
/*  58 */   SPIT("spit", 48, true);
/*     */   private final String particleName;
/*     */   private final int particleID;
/*     */   private final boolean shouldIgnoreRange;
/*     */   
/*     */   static {
/*  64 */     PARTICLES = Maps.newHashMap();
/*  65 */     BY_NAME = Maps.newHashMap();
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
/*     */     byte b;
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
/*     */     int i;
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
/*     */     EnumParticleTypes[] arrayOfEnumParticleTypes;
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
/* 122 */     for (i = (arrayOfEnumParticleTypes = values()).length, b = 0; b < i; ) { EnumParticleTypes enumparticletypes = arrayOfEnumParticleTypes[b];
/*     */       
/* 124 */       PARTICLES.put(Integer.valueOf(enumparticletypes.getParticleID()), enumparticletypes);
/* 125 */       BY_NAME.put(enumparticletypes.getParticleName(), enumparticletypes);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private final int argumentCount;
/*     */   private static final Map<Integer, EnumParticleTypes> PARTICLES;
/*     */   private static final Map<String, EnumParticleTypes> BY_NAME;
/*     */   
/*     */   EnumParticleTypes(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn, int argumentCountIn) {
/*     */     this.particleName = particleNameIn;
/*     */     this.particleID = particleIDIn;
/*     */     this.shouldIgnoreRange = shouldIgnoreRangeIn;
/*     */     this.argumentCount = argumentCountIn;
/*     */   }
/*     */   
/*     */   public static Set<String> getParticleNames() {
/*     */     return BY_NAME.keySet();
/*     */   }
/*     */   
/*     */   public String getParticleName() {
/*     */     return this.particleName;
/*     */   }
/*     */   
/*     */   public int getParticleID() {
/*     */     return this.particleID;
/*     */   }
/*     */   
/*     */   public int getArgumentCount() {
/*     */     return this.argumentCount;
/*     */   }
/*     */   
/*     */   public boolean getShouldIgnoreRange() {
/*     */     return this.shouldIgnoreRange;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EnumParticleTypes getParticleFromId(int particleId) {
/*     */     return PARTICLES.get(Integer.valueOf(particleId));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EnumParticleTypes getByName(String nameIn) {
/*     */     return BY_NAME.get(nameIn);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EnumParticleTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */