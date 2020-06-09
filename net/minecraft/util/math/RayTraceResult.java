/*    */ package net.minecraft.util.math;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RayTraceResult
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   public Type typeOfHit;
/*    */   public EnumFacing sideHit;
/*    */   public Vec3d hitVec;
/*    */   public Entity entityHit;
/*    */   
/*    */   public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
/* 24 */     this(Type.BLOCK, hitVecIn, sideHitIn, blockPosIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public RayTraceResult(Vec3d hitVecIn, EnumFacing sideHitIn) {
/* 29 */     this(Type.BLOCK, hitVecIn, sideHitIn, BlockPos.ORIGIN);
/*    */   }
/*    */ 
/*    */   
/*    */   public RayTraceResult(Entity entityIn) {
/* 34 */     this(entityIn, new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ));
/*    */   }
/*    */ 
/*    */   
/*    */   public RayTraceResult(Type typeIn, Vec3d hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
/* 39 */     this.typeOfHit = typeIn;
/* 40 */     this.blockPos = blockPosIn;
/* 41 */     this.sideHit = sideHitIn;
/* 42 */     this.hitVec = new Vec3d(hitVecIn.xCoord, hitVecIn.yCoord, hitVecIn.zCoord);
/*    */   }
/*    */ 
/*    */   
/*    */   public RayTraceResult(Entity entityHitIn, Vec3d hitVecIn) {
/* 47 */     this.typeOfHit = Type.ENTITY;
/* 48 */     this.entityHit = entityHitIn;
/* 49 */     this.hitVec = hitVecIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 54 */     return this.blockPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 64 */     MISS,
/* 65 */     BLOCK,
/* 66 */     ENTITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\RayTraceResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */