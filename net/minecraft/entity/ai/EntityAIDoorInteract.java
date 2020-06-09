/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public abstract class EntityAIDoorInteract extends EntityAIBase {
/*     */   protected EntityLiving theEntity;
/*  16 */   protected BlockPos doorPosition = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   protected BlockDoor doorBlock;
/*     */ 
/*     */   
/*     */   boolean hasStoppedDoorInteraction;
/*     */   
/*     */   float entityPositionX;
/*     */   
/*     */   float entityPositionZ;
/*     */ 
/*     */   
/*     */   public EntityAIDoorInteract(EntityLiving entityIn) {
/*  30 */     this.theEntity = entityIn;
/*     */     
/*  32 */     if (!(entityIn.getNavigator() instanceof PathNavigateGround))
/*     */     {
/*  34 */       throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  43 */     if (!this.theEntity.isCollidedHorizontally)
/*     */     {
/*  45 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  49 */     PathNavigateGround pathnavigateground = (PathNavigateGround)this.theEntity.getNavigator();
/*  50 */     Path path = pathnavigateground.getPath();
/*     */     
/*  52 */     if (path != null && !path.isFinished() && pathnavigateground.getEnterDoors()) {
/*     */       
/*  54 */       for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); i++) {
/*     */         
/*  56 */         PathPoint pathpoint = path.getPathPointFromIndex(i);
/*  57 */         this.doorPosition = new BlockPos(pathpoint.xCoord, pathpoint.yCoord + 1, pathpoint.zCoord);
/*     */         
/*  59 */         if (this.theEntity.getDistanceSq(this.doorPosition.getX(), this.theEntity.posY, this.doorPosition.getZ()) <= 2.25D) {
/*     */           
/*  61 */           this.doorBlock = getBlockDoor(this.doorPosition);
/*     */           
/*  63 */           if (this.doorBlock != null)
/*     */           {
/*  65 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  70 */       this.doorPosition = (new BlockPos((Entity)this.theEntity)).up();
/*  71 */       this.doorBlock = getBlockDoor(this.doorPosition);
/*  72 */       return (this.doorBlock != null);
/*     */     } 
/*     */ 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  86 */     return !this.hasStoppedDoorInteraction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  94 */     this.hasStoppedDoorInteraction = false;
/*  95 */     this.entityPositionX = (float)((this.doorPosition.getX() + 0.5F) - this.theEntity.posX);
/*  96 */     this.entityPositionZ = (float)((this.doorPosition.getZ() + 0.5F) - this.theEntity.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 104 */     float f = (float)((this.doorPosition.getX() + 0.5F) - this.theEntity.posX);
/* 105 */     float f1 = (float)((this.doorPosition.getZ() + 0.5F) - this.theEntity.posZ);
/* 106 */     float f2 = this.entityPositionX * f + this.entityPositionZ * f1;
/*     */     
/* 108 */     if (f2 < 0.0F)
/*     */     {
/* 110 */       this.hasStoppedDoorInteraction = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockDoor getBlockDoor(BlockPos pos) {
/* 116 */     IBlockState iblockstate = this.theEntity.world.getBlockState(pos);
/* 117 */     Block block = iblockstate.getBlock();
/* 118 */     return (block instanceof BlockDoor && iblockstate.getMaterial() == Material.WOOD) ? (BlockDoor)block : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIDoorInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */