/*     */ package net.minecraft.util;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.Explosion;
/*     */ 
/*     */ public class DamageSource
/*     */ {
/*  17 */   public static final DamageSource inFire = (new DamageSource("inFire")).setFireDamage();
/*  18 */   public static final DamageSource lightningBolt = new DamageSource("lightningBolt");
/*  19 */   public static final DamageSource onFire = (new DamageSource("onFire")).setDamageBypassesArmor().setFireDamage();
/*  20 */   public static final DamageSource lava = (new DamageSource("lava")).setFireDamage();
/*  21 */   public static final DamageSource hotFloor = (new DamageSource("hotFloor")).setFireDamage();
/*  22 */   public static final DamageSource inWall = (new DamageSource("inWall")).setDamageBypassesArmor();
/*  23 */   public static final DamageSource field_191291_g = (new DamageSource("cramming")).setDamageBypassesArmor();
/*  24 */   public static final DamageSource drown = (new DamageSource("drown")).setDamageBypassesArmor();
/*  25 */   public static final DamageSource starve = (new DamageSource("starve")).setDamageBypassesArmor().setDamageIsAbsolute();
/*  26 */   public static final DamageSource cactus = new DamageSource("cactus");
/*  27 */   public static final DamageSource fall = (new DamageSource("fall")).setDamageBypassesArmor();
/*  28 */   public static final DamageSource flyIntoWall = (new DamageSource("flyIntoWall")).setDamageBypassesArmor();
/*  29 */   public static final DamageSource outOfWorld = (new DamageSource("outOfWorld")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
/*  30 */   public static final DamageSource generic = (new DamageSource("generic")).setDamageBypassesArmor();
/*  31 */   public static final DamageSource magic = (new DamageSource("magic")).setDamageBypassesArmor().setMagicDamage();
/*  32 */   public static final DamageSource wither = (new DamageSource("wither")).setDamageBypassesArmor();
/*  33 */   public static final DamageSource anvil = new DamageSource("anvil");
/*  34 */   public static final DamageSource fallingBlock = new DamageSource("fallingBlock");
/*  35 */   public static final DamageSource dragonBreath = (new DamageSource("dragonBreath")).setDamageBypassesArmor();
/*  36 */   public static final DamageSource field_191552_t = (new DamageSource("fireworks")).setExplosion();
/*     */ 
/*     */   
/*     */   private boolean isUnblockable;
/*     */ 
/*     */   
/*     */   private boolean isDamageAllowedInCreativeMode;
/*     */   
/*     */   private boolean damageIsAbsolute;
/*     */   
/*  46 */   private float hungerDamage = 0.1F;
/*     */ 
/*     */   
/*     */   private boolean fireDamage;
/*     */ 
/*     */   
/*     */   private boolean projectile;
/*     */ 
/*     */   
/*     */   private boolean difficultyScaled;
/*     */ 
/*     */   
/*     */   private boolean magicDamage;
/*     */   
/*     */   private boolean explosion;
/*     */   
/*     */   public String damageType;
/*     */ 
/*     */   
/*     */   public static DamageSource causeMobDamage(EntityLivingBase mob) {
/*  66 */     return new EntityDamageSource("mob", (Entity)mob);
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeIndirectDamage(Entity source, EntityLivingBase indirectEntityIn) {
/*  71 */     return new EntityDamageSourceIndirect("mob", source, (Entity)indirectEntityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causePlayerDamage(EntityPlayer player) {
/*  79 */     return new EntityDamageSource("player", (Entity)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeArrowDamage(EntityArrow arrow, @Nullable Entity indirectEntityIn) {
/*  87 */     return (new EntityDamageSourceIndirect("arrow", (Entity)arrow, indirectEntityIn)).setProjectile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeFireballDamage(EntityFireball fireball, @Nullable Entity indirectEntityIn) {
/*  95 */     return (indirectEntityIn == null) ? (new EntityDamageSourceIndirect("onFire", (Entity)fireball, (Entity)fireball)).setFireDamage().setProjectile() : (new EntityDamageSourceIndirect("fireball", (Entity)fireball, indirectEntityIn)).setFireDamage().setProjectile();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeThrownDamage(Entity source, @Nullable Entity indirectEntityIn) {
/* 100 */     return (new EntityDamageSourceIndirect("thrown", source, indirectEntityIn)).setProjectile();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeIndirectMagicDamage(Entity source, @Nullable Entity indirectEntityIn) {
/* 105 */     return (new EntityDamageSourceIndirect("indirectMagic", source, indirectEntityIn)).setDamageBypassesArmor().setMagicDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeThornsDamage(Entity source) {
/* 113 */     return (new EntityDamageSource("thorns", source)).setIsThornsDamage().setMagicDamage();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeExplosionDamage(@Nullable Explosion explosionIn) {
/* 118 */     return (explosionIn != null && explosionIn.getExplosivePlacedBy() != null) ? (new EntityDamageSource("explosion.player", (Entity)explosionIn.getExplosivePlacedBy())).setDifficultyScaled().setExplosion() : (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
/*     */   }
/*     */ 
/*     */   
/*     */   public static DamageSource causeExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn) {
/* 123 */     return (entityLivingBaseIn != null) ? (new EntityDamageSource("explosion.player", (Entity)entityLivingBaseIn)).setDifficultyScaled().setExplosion() : (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProjectile() {
/* 131 */     return this.projectile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setProjectile() {
/* 139 */     this.projectile = true;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExplosion() {
/* 145 */     return this.explosion;
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageSource setExplosion() {
/* 150 */     this.explosion = true;
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnblockable() {
/* 156 */     return this.isUnblockable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHungerDamage() {
/* 164 */     return this.hungerDamage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHarmInCreative() {
/* 169 */     return this.isDamageAllowedInCreativeMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDamageAbsolute() {
/* 177 */     return this.damageIsAbsolute;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DamageSource(String damageTypeIn) {
/* 182 */     this.damageType = damageTypeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getSourceOfDamage() {
/* 188 */     return getEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntity() {
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageBypassesArmor() {
/* 199 */     this.isUnblockable = true;
/* 200 */     this.hungerDamage = 0.0F;
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageAllowedInCreativeMode() {
/* 206 */     this.isDamageAllowedInCreativeMode = true;
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageIsAbsolute() {
/* 216 */     this.damageIsAbsolute = true;
/* 217 */     this.hungerDamage = 0.0F;
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource setFireDamage() {
/* 226 */     this.fireDamage = true;
/* 227 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 235 */     EntityLivingBase entitylivingbase = entityLivingBaseIn.getAttackingEntity();
/* 236 */     String s = "death.attack." + this.damageType;
/* 237 */     String s1 = String.valueOf(s) + ".player";
/* 238 */     return (entitylivingbase != null && I18n.canTranslate(s1)) ? (ITextComponent)new TextComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), entitylivingbase.getDisplayName() }) : (ITextComponent)new TextComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFireDamage() {
/* 246 */     return this.fireDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDamageType() {
/* 254 */     return this.damageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setDifficultyScaled() {
/* 262 */     this.difficultyScaled = true;
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDifficultyScaled() {
/* 271 */     return this.difficultyScaled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMagicDamage() {
/* 279 */     return this.magicDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setMagicDamage() {
/* 287 */     this.magicDamage = true;
/* 288 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCreativePlayer() {
/* 293 */     Entity entity = getEntity();
/* 294 */     return (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getDamageLocation() {
/* 304 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\DamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */