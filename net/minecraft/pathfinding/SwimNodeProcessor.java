/*    */ package net.minecraft.pathfinding;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class SwimNodeProcessor
/*    */   extends NodeProcessor
/*    */ {
/*    */   public PathPoint getStart() {
/* 16 */     return openPoint(MathHelper.floor((this.entity.getEntityBoundingBox()).minX), MathHelper.floor((this.entity.getEntityBoundingBox()).minY + 0.5D), MathHelper.floor((this.entity.getEntityBoundingBox()).minZ));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PathPoint getPathPointToCoords(double x, double y, double z) {
/* 24 */     return openPoint(MathHelper.floor(x - (this.entity.width / 2.0F)), MathHelper.floor(y + 0.5D), MathHelper.floor(z - (this.entity.width / 2.0F)));
/*    */   }
/*    */ 
/*    */   
/*    */   public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/* 29 */     int i = 0; byte b; int j;
/*    */     EnumFacing[] arrayOfEnumFacing;
/* 31 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*    */       
/* 33 */       PathPoint pathpoint = getWaterNode(currentPoint.xCoord + enumfacing.getFrontOffsetX(), currentPoint.yCoord + enumfacing.getFrontOffsetY(), currentPoint.zCoord + enumfacing.getFrontOffsetZ());
/*    */       
/* 35 */       if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
/*    */       {
/* 37 */         pathOptions[i++] = pathpoint;
/*    */       }
/*    */       b++; }
/*    */     
/* 41 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn, boolean canEnterDoorsIn) {
/* 46 */     return PathNodeType.WATER;
/*    */   }
/*    */ 
/*    */   
/*    */   public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
/* 51 */     return PathNodeType.WATER;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private PathPoint getWaterNode(int p_186328_1_, int p_186328_2_, int p_186328_3_) {
/* 57 */     PathNodeType pathnodetype = isFree(p_186328_1_, p_186328_2_, p_186328_3_);
/* 58 */     return (pathnodetype == PathNodeType.WATER) ? openPoint(p_186328_1_, p_186328_2_, p_186328_3_) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   private PathNodeType isFree(int p_186327_1_, int p_186327_2_, int p_186327_3_) {
/* 63 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*    */     
/* 65 */     for (int i = p_186327_1_; i < p_186327_1_ + this.entitySizeX; i++) {
/*    */       
/* 67 */       for (int j = p_186327_2_; j < p_186327_2_ + this.entitySizeY; j++) {
/*    */         
/* 69 */         for (int k = p_186327_3_; k < p_186327_3_ + this.entitySizeZ; k++) {
/*    */           
/* 71 */           IBlockState iblockstate = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(i, j, k));
/*    */           
/* 73 */           if (iblockstate.getMaterial() != Material.WATER)
/*    */           {
/* 75 */             return PathNodeType.BLOCKED;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 81 */     return PathNodeType.WATER;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\SwimNodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */