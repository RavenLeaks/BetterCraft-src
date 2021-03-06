/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.village.VillageDoorInfo;
/*     */ 
/*     */ public class EntityAIMoveThroughVillage
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityCreature theEntity;
/*     */   private final double movementSpeed;
/*     */   private Path entityPathNavigate;
/*     */   private VillageDoorInfo doorInfo;
/*     */   private final boolean isNocturnal;
/*  23 */   private final List<VillageDoorInfo> doorList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public EntityAIMoveThroughVillage(EntityCreature theEntityIn, double movementSpeedIn, boolean isNocturnalIn) {
/*  27 */     this.theEntity = theEntityIn;
/*  28 */     this.movementSpeed = movementSpeedIn;
/*  29 */     this.isNocturnal = isNocturnalIn;
/*  30 */     setMutexBits(1);
/*     */     
/*  32 */     if (!(theEntityIn.getNavigator() instanceof PathNavigateGround))
/*     */     {
/*  34 */       throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  43 */     resizeDoorList();
/*     */     
/*  45 */     if (this.isNocturnal && this.theEntity.world.isDaytime())
/*     */     {
/*  47 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  51 */     Village village = this.theEntity.world.getVillageCollection().getNearestVillage(new BlockPos((Entity)this.theEntity), 0);
/*     */     
/*  53 */     if (village == null)
/*     */     {
/*  55 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  59 */     this.doorInfo = findNearestDoor(village);
/*     */     
/*  61 */     if (this.doorInfo == null)
/*     */     {
/*  63 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  67 */     PathNavigateGround pathnavigateground = (PathNavigateGround)this.theEntity.getNavigator();
/*  68 */     boolean flag = pathnavigateground.getEnterDoors();
/*  69 */     pathnavigateground.setBreakDoors(false);
/*  70 */     this.entityPathNavigate = pathnavigateground.getPathToPos(this.doorInfo.getDoorBlockPos());
/*  71 */     pathnavigateground.setBreakDoors(flag);
/*     */     
/*  73 */     if (this.entityPathNavigate != null)
/*     */     {
/*  75 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  79 */     Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, new Vec3d(this.doorInfo.getDoorBlockPos().getX(), this.doorInfo.getDoorBlockPos().getY(), this.doorInfo.getDoorBlockPos().getZ()));
/*     */     
/*  81 */     if (vec3d == null)
/*     */     {
/*  83 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  87 */     pathnavigateground.setBreakDoors(false);
/*  88 */     this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
/*  89 */     pathnavigateground.setBreakDoors(flag);
/*  90 */     return (this.entityPathNavigate != null);
/*     */   }
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
/*     */   public boolean continueExecuting() {
/* 103 */     if (this.theEntity.getNavigator().noPath())
/*     */     {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 109 */     float f = this.theEntity.width + 4.0F;
/* 110 */     return (this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > (f * f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 119 */     this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 127 */     if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) < 16.0D)
/*     */     {
/* 129 */       this.doorList.add(this.doorInfo);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private VillageDoorInfo findNearestDoor(Village villageIn) {
/* 135 */     VillageDoorInfo villagedoorinfo = null;
/* 136 */     int i = Integer.MAX_VALUE;
/*     */     
/* 138 */     for (VillageDoorInfo villagedoorinfo1 : villageIn.getVillageDoorInfoList()) {
/*     */       
/* 140 */       int j = villagedoorinfo1.getDistanceSquared(MathHelper.floor(this.theEntity.posX), MathHelper.floor(this.theEntity.posY), MathHelper.floor(this.theEntity.posZ));
/*     */       
/* 142 */       if (j < i && !doesDoorListContain(villagedoorinfo1)) {
/*     */         
/* 144 */         villagedoorinfo = villagedoorinfo1;
/* 145 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     return villagedoorinfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean doesDoorListContain(VillageDoorInfo doorInfoIn) {
/* 154 */     for (VillageDoorInfo villagedoorinfo : this.doorList) {
/*     */       
/* 156 */       if (doorInfoIn.getDoorBlockPos().equals(villagedoorinfo.getDoorBlockPos()))
/*     */       {
/* 158 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resizeDoorList() {
/* 167 */     if (this.doorList.size() > 15)
/*     */     {
/* 169 */       this.doorList.remove(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIMoveThroughVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */