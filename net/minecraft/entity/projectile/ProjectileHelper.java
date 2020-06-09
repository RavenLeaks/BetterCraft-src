/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public final class ProjectileHelper
/*    */ {
/*    */   public static RayTraceResult forwardsRaycast(Entity p_188802_0_, boolean includeEntities, boolean p_188802_2_, Entity excludedEntity) {
/* 15 */     double d0 = p_188802_0_.posX;
/* 16 */     double d1 = p_188802_0_.posY;
/* 17 */     double d2 = p_188802_0_.posZ;
/* 18 */     double d3 = p_188802_0_.motionX;
/* 19 */     double d4 = p_188802_0_.motionY;
/* 20 */     double d5 = p_188802_0_.motionZ;
/* 21 */     World world = p_188802_0_.world;
/* 22 */     Vec3d vec3d = new Vec3d(d0, d1, d2);
/* 23 */     Vec3d vec3d1 = new Vec3d(d0 + d3, d1 + d4, d2 + d5);
/* 24 */     RayTraceResult raytraceresult = world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
/*    */     
/* 26 */     if (includeEntities) {
/*    */       
/* 28 */       if (raytraceresult != null)
/*    */       {
/* 30 */         vec3d1 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
/*    */       }
/*    */       
/* 33 */       Entity entity = null;
/* 34 */       List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(p_188802_0_, p_188802_0_.getEntityBoundingBox().addCoord(d3, d4, d5).expandXyz(1.0D));
/* 35 */       double d6 = 0.0D;
/*    */       
/* 37 */       for (int i = 0; i < list.size(); i++) {
/*    */         
/* 39 */         Entity entity1 = list.get(i);
/*    */         
/* 41 */         if (entity1.canBeCollidedWith() && (p_188802_2_ || !entity1.isEntityEqual(excludedEntity)) && !entity1.noClip) {
/*    */           
/* 43 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
/* 44 */           RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
/*    */           
/* 46 */           if (raytraceresult1 != null) {
/*    */             
/* 48 */             double d7 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
/*    */             
/* 50 */             if (d7 < d6 || d6 == 0.0D) {
/*    */               
/* 52 */               entity = entity1;
/* 53 */               d6 = d7;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 59 */       if (entity != null)
/*    */       {
/* 61 */         raytraceresult = new RayTraceResult(entity);
/*    */       }
/*    */     } 
/*    */     
/* 65 */     return raytraceresult;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final void rotateTowardsMovement(Entity p_188803_0_, float p_188803_1_) {
/* 70 */     double d0 = p_188803_0_.motionX;
/* 71 */     double d1 = p_188803_0_.motionY;
/* 72 */     double d2 = p_188803_0_.motionZ;
/* 73 */     float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 74 */     p_188803_0_.rotationYaw = (float)(MathHelper.atan2(d2, d0) * 57.29577951308232D) + 90.0F;
/*    */     
/* 76 */     for (p_188803_0_.rotationPitch = (float)(MathHelper.atan2(f, d1) * 57.29577951308232D) - 90.0F; p_188803_0_.rotationPitch - p_188803_0_.prevRotationPitch < -180.0F; p_188803_0_.prevRotationPitch -= 360.0F);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 81 */     while (p_188803_0_.rotationPitch - p_188803_0_.prevRotationPitch >= 180.0F)
/*    */     {
/* 83 */       p_188803_0_.prevRotationPitch += 360.0F;
/*    */     }
/*    */     
/* 86 */     while (p_188803_0_.rotationYaw - p_188803_0_.prevRotationYaw < -180.0F)
/*    */     {
/* 88 */       p_188803_0_.prevRotationYaw -= 360.0F;
/*    */     }
/*    */     
/* 91 */     while (p_188803_0_.rotationYaw - p_188803_0_.prevRotationYaw >= 180.0F)
/*    */     {
/* 93 */       p_188803_0_.prevRotationYaw += 360.0F;
/*    */     }
/*    */     
/* 96 */     p_188803_0_.rotationPitch = p_188803_0_.prevRotationPitch + (p_188803_0_.rotationPitch - p_188803_0_.prevRotationPitch) * p_188803_1_;
/* 97 */     p_188803_0_.rotationYaw = p_188803_0_.prevRotationYaw + (p_188803_0_.rotationYaw - p_188803_0_.prevRotationYaw) * p_188803_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\ProjectileHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */