/*     */ package net.minecraft.village;
/*     */ 
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VillageDoorInfo
/*     */ {
/*     */   private final BlockPos doorBlockPos;
/*     */   private final BlockPos insideBlock;
/*     */   private final EnumFacing insideDirection;
/*     */   private int lastActivityTimestamp;
/*     */   private boolean isDetachedFromVillageFlag;
/*     */   private int doorOpeningRestrictionCounter;
/*     */   
/*     */   public VillageDoorInfo(BlockPos pos, int deltaX, int deltaZ, int timestamp) {
/*  20 */     this(pos, getFaceDirection(deltaX, deltaZ), timestamp);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumFacing getFaceDirection(int deltaX, int deltaZ) {
/*  25 */     if (deltaX < 0)
/*     */     {
/*  27 */       return EnumFacing.WEST;
/*     */     }
/*  29 */     if (deltaX > 0)
/*     */     {
/*  31 */       return EnumFacing.EAST;
/*     */     }
/*     */ 
/*     */     
/*  35 */     return (deltaZ < 0) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VillageDoorInfo(BlockPos pos, EnumFacing facing, int timestamp) {
/*  41 */     this.doorBlockPos = pos;
/*  42 */     this.insideDirection = facing;
/*  43 */     this.insideBlock = pos.offset(facing, 2);
/*  44 */     this.lastActivityTimestamp = timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDistanceSquared(int x, int y, int z) {
/*  52 */     return (int)this.doorBlockPos.distanceSq(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDistanceToDoorBlockSq(BlockPos pos) {
/*  57 */     return (int)pos.distanceSq((Vec3i)getDoorBlockPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDistanceToInsideBlockSq(BlockPos pos) {
/*  62 */     return (int)this.insideBlock.distanceSq((Vec3i)pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSide(BlockPos pos) {
/*  67 */     int i = pos.getX() - this.doorBlockPos.getX();
/*  68 */     int j = pos.getZ() - this.doorBlockPos.getY();
/*  69 */     return (i * this.insideDirection.getFrontOffsetX() + j * this.insideDirection.getFrontOffsetZ() >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetDoorOpeningRestrictionCounter() {
/*  74 */     this.doorOpeningRestrictionCounter = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementDoorOpeningRestrictionCounter() {
/*  79 */     this.doorOpeningRestrictionCounter++;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDoorOpeningRestrictionCounter() {
/*  84 */     return this.doorOpeningRestrictionCounter;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getDoorBlockPos() {
/*  89 */     return this.doorBlockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getInsideBlockPos() {
/*  94 */     return this.insideBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsideOffsetX() {
/*  99 */     return this.insideDirection.getFrontOffsetX() * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsideOffsetZ() {
/* 104 */     return this.insideDirection.getFrontOffsetZ() * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInsidePosY() {
/* 109 */     return this.lastActivityTimestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastActivityTimestamp(int timestamp) {
/* 114 */     this.lastActivityTimestamp = timestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsDetachedFromVillageFlag() {
/* 119 */     return this.isDetachedFromVillageFlag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIsDetachedFromVillageFlag(boolean detached) {
/* 124 */     this.isDetachedFromVillageFlag = detached;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getInsideDirection() {
/* 129 */     return this.insideDirection;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\village\VillageDoorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */