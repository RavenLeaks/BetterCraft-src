/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWitherSkull
/*     */   extends EntityFireball {
/*  22 */   private static final DataParameter<Boolean> INVULNERABLE = EntityDataManager.createKey(EntityWitherSkull.class, DataSerializers.BOOLEAN);
/*     */ 
/*     */   
/*     */   public EntityWitherSkull(World worldIn) {
/*  26 */     super(worldIn);
/*  27 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityWitherSkull(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/*  32 */     super(worldIn, shooter, accelX, accelY, accelZ);
/*  33 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesWitherSkull(DataFixer fixer) {
/*  38 */     EntityFireball.registerFixesFireball(fixer, "WitherSkull");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getMotionFactor() {
/*  46 */     return isInvulnerable() ? 0.73F : super.getMotionFactor();
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityWitherSkull(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/*  51 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/*  52 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/*  68 */     float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
/*  69 */     Block block = blockStateIn.getBlock();
/*     */     
/*  71 */     if (isInvulnerable() && EntityWither.canDestroyBlock(block))
/*     */     {
/*  73 */       f = Math.min(0.8F, f);
/*     */     }
/*     */     
/*  76 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult result) {
/*  84 */     if (!this.world.isRemote) {
/*     */       
/*  86 */       if (result.entityHit != null) {
/*     */         
/*  88 */         if (this.shootingEntity != null) {
/*     */           
/*  90 */           if (result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F))
/*     */           {
/*  92 */             if (result.entityHit.isEntityAlive())
/*     */             {
/*  94 */               applyEnchantments(this.shootingEntity, result.entityHit);
/*     */             }
/*     */             else
/*     */             {
/*  98 */               this.shootingEntity.heal(5.0F);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 104 */           result.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
/*     */         } 
/*     */         
/* 107 */         if (result.entityHit instanceof EntityLivingBase) {
/*     */           
/* 109 */           int i = 0;
/*     */           
/* 111 */           if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
/*     */             
/* 113 */             i = 10;
/*     */           }
/* 115 */           else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
/*     */             
/* 117 */             i = 40;
/*     */           } 
/*     */           
/* 120 */           if (i > 0)
/*     */           {
/* 122 */             ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * i, 1));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 127 */       this.world.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
/* 128 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 150 */     this.dataManager.register(INVULNERABLE, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvulnerable() {
/* 158 */     return ((Boolean)this.dataManager.get(INVULNERABLE)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvulnerable(boolean invulnerable) {
/* 166 */     this.dataManager.set(INVULNERABLE, Boolean.valueOf(invulnerable));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFireballFiery() {
/* 171 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */