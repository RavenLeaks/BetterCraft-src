/*     */ package net.minecraft.entity.boss.dragon.phase;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAreaEffectCloud;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class PhaseSittingFlaming extends PhaseSittingBase {
/*     */   private int flameTicks;
/*     */   private int flameCount;
/*     */   private EntityAreaEffectCloud areaEffectCloud;
/*     */   
/*     */   public PhaseSittingFlaming(EntityDragon dragonIn) {
/*  20 */     super(dragonIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doClientRenderEffects() {
/*  29 */     this.flameTicks++;
/*     */     
/*  31 */     if (this.flameTicks % 2 == 0 && this.flameTicks < 10) {
/*     */       
/*  33 */       Vec3d vec3d = this.dragon.getHeadLookVec(1.0F).normalize();
/*  34 */       vec3d.rotateYaw(-0.7853982F);
/*  35 */       double d0 = this.dragon.dragonPartHead.posX;
/*  36 */       double d1 = this.dragon.dragonPartHead.posY + (this.dragon.dragonPartHead.height / 2.0F);
/*  37 */       double d2 = this.dragon.dragonPartHead.posZ;
/*     */       
/*  39 */       for (int i = 0; i < 8; i++) {
/*     */         
/*  41 */         double d3 = d0 + this.dragon.getRNG().nextGaussian() / 2.0D;
/*  42 */         double d4 = d1 + this.dragon.getRNG().nextGaussian() / 2.0D;
/*  43 */         double d5 = d2 + this.dragon.getRNG().nextGaussian() / 2.0D;
/*     */         
/*  45 */         for (int j = 0; j < 6; j++)
/*     */         {
/*  47 */           this.dragon.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.xCoord * 0.07999999821186066D * j, -vec3d.yCoord * 0.6000000238418579D, -vec3d.zCoord * 0.07999999821186066D * j, new int[0]);
/*     */         }
/*     */         
/*  50 */         vec3d.rotateYaw(0.19634955F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLocalUpdate() {
/*  61 */     this.flameTicks++;
/*     */     
/*  63 */     if (this.flameTicks >= 200) {
/*     */       
/*  65 */       if (this.flameCount >= 4)
/*     */       {
/*  67 */         this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
/*     */       }
/*     */       else
/*     */       {
/*  71 */         this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
/*     */       }
/*     */     
/*  74 */     } else if (this.flameTicks == 10) {
/*     */       
/*  76 */       Vec3d vec3d = (new Vec3d(this.dragon.dragonPartHead.posX - this.dragon.posX, 0.0D, this.dragon.dragonPartHead.posZ - this.dragon.posZ)).normalize();
/*  77 */       float f = 5.0F;
/*  78 */       double d0 = this.dragon.dragonPartHead.posX + vec3d.xCoord * 5.0D / 2.0D;
/*  79 */       double d1 = this.dragon.dragonPartHead.posZ + vec3d.zCoord * 5.0D / 2.0D;
/*  80 */       double d2 = this.dragon.dragonPartHead.posY + (this.dragon.dragonPartHead.height / 2.0F);
/*  81 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(d0), MathHelper.floor(d2), MathHelper.floor(d1));
/*     */       
/*  83 */       while (this.dragon.world.isAirBlock((BlockPos)blockpos$mutableblockpos)) {
/*     */         
/*  85 */         d2--;
/*  86 */         blockpos$mutableblockpos.setPos(MathHelper.floor(d0), MathHelper.floor(d2), MathHelper.floor(d1));
/*     */       } 
/*     */       
/*  89 */       d2 = (MathHelper.floor(d2) + 1);
/*  90 */       this.areaEffectCloud = new EntityAreaEffectCloud(this.dragon.world, d0, d2, d1);
/*  91 */       this.areaEffectCloud.setOwner((EntityLivingBase)this.dragon);
/*  92 */       this.areaEffectCloud.setRadius(5.0F);
/*  93 */       this.areaEffectCloud.setDuration(200);
/*  94 */       this.areaEffectCloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
/*  95 */       this.areaEffectCloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE));
/*  96 */       this.dragon.world.spawnEntityInWorld((Entity)this.areaEffectCloud);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPhase() {
/* 105 */     this.flameTicks = 0;
/* 106 */     this.flameCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAreaEffect() {
/* 111 */     if (this.areaEffectCloud != null) {
/*     */       
/* 113 */       this.areaEffectCloud.setDead();
/* 114 */       this.areaEffectCloud = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PhaseList<PhaseSittingFlaming> getPhaseList() {
/* 120 */     return PhaseList.SITTING_FLAMING;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetFlameCount() {
/* 125 */     this.flameCount = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseSittingFlaming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */