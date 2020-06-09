/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAreaEffectCloud;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDragonFireball
/*     */   extends EntityFireball {
/*     */   public EntityDragonFireball(World worldIn) {
/*  19 */     super(worldIn);
/*  20 */     setSize(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDragonFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/*  25 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/*  26 */     setSize(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDragonFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/*  31 */     super(worldIn, shooter, accelX, accelY, accelZ);
/*  32 */     setSize(1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesDragonFireball(DataFixer fixer) {
/*  37 */     EntityFireball.registerFixesFireball(fixer, "DragonFireball");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult result) {
/*  45 */     if (result.entityHit == null || !result.entityHit.isEntityEqual((Entity)this.shootingEntity))
/*     */     {
/*  47 */       if (!this.world.isRemote) {
/*     */         
/*  49 */         List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D));
/*  50 */         EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
/*  51 */         entityareaeffectcloud.setOwner(this.shootingEntity);
/*  52 */         entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
/*  53 */         entityareaeffectcloud.setRadius(3.0F);
/*  54 */         entityareaeffectcloud.setDuration(600);
/*  55 */         entityareaeffectcloud.setRadiusPerTick((7.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
/*  56 */         entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 1));
/*     */         
/*  58 */         if (!list.isEmpty())
/*     */         {
/*  60 */           for (EntityLivingBase entitylivingbase : list) {
/*     */             
/*  62 */             double d0 = getDistanceSqToEntity((Entity)entitylivingbase);
/*     */             
/*  64 */             if (d0 < 16.0D) {
/*     */               
/*  66 */               entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*  72 */         this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
/*  73 */         this.world.spawnEntityInWorld((Entity)entityareaeffectcloud);
/*  74 */         setDead();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/*  97 */     return EnumParticleTypes.DRAGON_BREATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFireballFiery() {
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityDragonFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */