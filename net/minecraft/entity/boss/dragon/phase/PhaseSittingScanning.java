/*    */ package net.minecraft.entity.boss.dragon.phase;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class PhaseSittingScanning extends PhaseSittingBase {
/*    */   private int scanningTime;
/*    */   
/*    */   public PhaseSittingScanning(EntityDragon dragonIn) {
/* 14 */     super(dragonIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doLocalUpdate() {
/* 23 */     this.scanningTime++;
/* 24 */     EntityPlayer entityPlayer = this.dragon.world.getNearestAttackablePlayer((Entity)this.dragon, 20.0D, 10.0D);
/*    */     
/* 26 */     if (entityPlayer != null) {
/*    */       
/* 28 */       if (this.scanningTime > 25) {
/*    */         
/* 30 */         this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_ATTACKING);
/*    */       }
/*    */       else {
/*    */         
/* 34 */         Vec3d vec3d = (new Vec3d(((EntityLivingBase)entityPlayer).posX - this.dragon.posX, 0.0D, ((EntityLivingBase)entityPlayer).posZ - this.dragon.posZ)).normalize();
/* 35 */         Vec3d vec3d1 = (new Vec3d(MathHelper.sin(this.dragon.rotationYaw * 0.017453292F), 0.0D, -MathHelper.cos(this.dragon.rotationYaw * 0.017453292F))).normalize();
/* 36 */         float f = (float)vec3d1.dotProduct(vec3d);
/* 37 */         float f1 = (float)(Math.acos(f) * 57.29577951308232D) + 0.5F;
/*    */         
/* 39 */         if (f1 < 0.0F || f1 > 10.0F)
/*    */         {
/* 41 */           double d0 = ((EntityLivingBase)entityPlayer).posX - this.dragon.dragonPartHead.posX;
/* 42 */           double d1 = ((EntityLivingBase)entityPlayer).posZ - this.dragon.dragonPartHead.posZ;
/* 43 */           double d2 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d0, d1) * 57.29577951308232D - this.dragon.rotationYaw), -100.0D, 100.0D);
/* 44 */           this.dragon.randomYawVelocity *= 0.8F;
/* 45 */           float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
/* 46 */           float f3 = f2;
/*    */           
/* 48 */           if (f2 > 40.0F)
/*    */           {
/* 50 */             f2 = 40.0F;
/*    */           }
/*    */           
/* 53 */           this.dragon.randomYawVelocity = (float)(this.dragon.randomYawVelocity + d2 * (0.7F / f2 / f3));
/* 54 */           this.dragon.rotationYaw += this.dragon.randomYawVelocity;
/*    */         }
/*    */       
/*    */       } 
/* 58 */     } else if (this.scanningTime >= 100) {
/*    */       
/* 60 */       entityPlayer = this.dragon.world.getNearestAttackablePlayer((Entity)this.dragon, 150.0D, 150.0D);
/* 61 */       this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
/*    */       
/* 63 */       if (entityPlayer != null) {
/*    */         
/* 65 */         this.dragon.getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
/* 66 */         ((PhaseChargingPlayer)this.dragon.getPhaseManager().<PhaseChargingPlayer>getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d(((EntityLivingBase)entityPlayer).posX, ((EntityLivingBase)entityPlayer).posY, ((EntityLivingBase)entityPlayer).posZ));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPhase() {
/* 76 */     this.scanningTime = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public PhaseList<PhaseSittingScanning> getPhaseList() {
/* 81 */     return PhaseList.SITTING_SCANNING;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\dragon\phase\PhaseSittingScanning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */