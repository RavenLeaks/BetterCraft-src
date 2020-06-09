/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIFleeSun
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityCreature theCreature;
/*    */   private double shelterX;
/*    */   private double shelterY;
/*    */   private double shelterZ;
/*    */   private final double movementSpeed;
/*    */   private final World theWorld;
/*    */   
/*    */   public EntityAIFleeSun(EntityCreature theCreatureIn, double movementSpeedIn) {
/* 22 */     this.theCreature = theCreatureIn;
/* 23 */     this.movementSpeed = movementSpeedIn;
/* 24 */     this.theWorld = theCreatureIn.world;
/* 25 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 33 */     if (!this.theWorld.isDaytime())
/*    */     {
/* 35 */       return false;
/*    */     }
/* 37 */     if (!this.theCreature.isBurning())
/*    */     {
/* 39 */       return false;
/*    */     }
/* 41 */     if (!this.theWorld.canSeeSky(new BlockPos(this.theCreature.posX, (this.theCreature.getEntityBoundingBox()).minY, this.theCreature.posZ)))
/*    */     {
/* 43 */       return false;
/*    */     }
/* 45 */     if (!this.theCreature.getItemStackFromSlot(EntityEquipmentSlot.HEAD).func_190926_b())
/*    */     {
/* 47 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 51 */     Vec3d vec3d = findPossibleShelter();
/*    */     
/* 53 */     if (vec3d == null)
/*    */     {
/* 55 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 59 */     this.shelterX = vec3d.xCoord;
/* 60 */     this.shelterY = vec3d.yCoord;
/* 61 */     this.shelterZ = vec3d.zCoord;
/* 62 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 72 */     return !this.theCreature.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 80 */     this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private Vec3d findPossibleShelter() {
/* 86 */     Random random = this.theCreature.getRNG();
/* 87 */     BlockPos blockpos = new BlockPos(this.theCreature.posX, (this.theCreature.getEntityBoundingBox()).minY, this.theCreature.posZ);
/*    */     
/* 89 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 91 */       BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
/*    */       
/* 93 */       if (!this.theWorld.canSeeSky(blockpos1) && this.theCreature.getBlockPathWeight(blockpos1) < 0.0F)
/*    */       {
/* 95 */         return new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
/*    */       }
/*    */     } 
/*    */     
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIFleeSun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */