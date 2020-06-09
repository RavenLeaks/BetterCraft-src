/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class FlyingNodeProcessor
/*     */   extends WalkNodeProcessor {
/*     */   public void initProcessor(IBlockAccess sourceIn, EntityLiving mob) {
/*  18 */     super.initProcessor(sourceIn, mob);
/*  19 */     this.avoidsWater = mob.getPathPriority(PathNodeType.WATER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess() {
/*  29 */     this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
/*  30 */     super.postProcess();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getStart() {
/*     */     int i;
/*  37 */     if (getCanSwim() && this.entity.isInWater()) {
/*     */       
/*  39 */       i = (int)(this.entity.getEntityBoundingBox()).minY;
/*  40 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ));
/*     */       
/*  42 */       for (Block block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock(); block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock())
/*     */       {
/*  44 */         i++;
/*  45 */         blockpos$mutableblockpos.setPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  50 */       i = MathHelper.floor((this.entity.getEntityBoundingBox()).minY + 0.5D);
/*     */     } 
/*     */     
/*  53 */     BlockPos blockpos1 = new BlockPos((Entity)this.entity);
/*  54 */     PathNodeType pathnodetype1 = func_192558_a(this.entity, blockpos1.getX(), i, blockpos1.getZ());
/*     */     
/*  56 */     if (this.entity.getPathPriority(pathnodetype1) < 0.0F) {
/*     */       
/*  58 */       Set<BlockPos> set = Sets.newHashSet();
/*  59 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).minX, i, (this.entity.getEntityBoundingBox()).minZ));
/*  60 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).minX, i, (this.entity.getEntityBoundingBox()).maxZ));
/*  61 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).maxX, i, (this.entity.getEntityBoundingBox()).minZ));
/*  62 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).maxX, i, (this.entity.getEntityBoundingBox()).maxZ));
/*     */       
/*  64 */       for (BlockPos blockpos : set) {
/*     */         
/*  66 */         PathNodeType pathnodetype = func_192559_a(this.entity, blockpos);
/*     */         
/*  68 */         if (this.entity.getPathPriority(pathnodetype) >= 0.0F)
/*     */         {
/*  70 */           return super.openPoint(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     return super.openPoint(blockpos1.getX(), i, blockpos1.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointToCoords(double x, double y, double z) {
/*  83 */     return super.openPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
/*     */   }
/*     */ 
/*     */   
/*     */   public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/*  88 */     int i = 0;
/*  89 */     PathPoint pathpoint = openPoint(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1);
/*  90 */     PathPoint pathpoint1 = openPoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord);
/*  91 */     PathPoint pathpoint2 = openPoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord);
/*  92 */     PathPoint pathpoint3 = openPoint(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1);
/*  93 */     PathPoint pathpoint4 = openPoint(currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord);
/*  94 */     PathPoint pathpoint5 = openPoint(currentPoint.xCoord, currentPoint.yCoord - 1, currentPoint.zCoord);
/*     */     
/*  96 */     if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
/*     */     {
/*  98 */       pathOptions[i++] = pathpoint;
/*     */     }
/*     */     
/* 101 */     if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 103 */       pathOptions[i++] = pathpoint1;
/*     */     }
/*     */     
/* 106 */     if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 108 */       pathOptions[i++] = pathpoint2;
/*     */     }
/*     */     
/* 111 */     if (pathpoint3 != null && !pathpoint3.visited && pathpoint3.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 113 */       pathOptions[i++] = pathpoint3;
/*     */     }
/*     */     
/* 116 */     if (pathpoint4 != null && !pathpoint4.visited && pathpoint4.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 118 */       pathOptions[i++] = pathpoint4;
/*     */     }
/*     */     
/* 121 */     if (pathpoint5 != null && !pathpoint5.visited && pathpoint5.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 123 */       pathOptions[i++] = pathpoint5;
/*     */     }
/*     */     
/* 126 */     boolean flag = !(pathpoint3 != null && pathpoint3.costMalus == 0.0F);
/* 127 */     boolean flag1 = !(pathpoint != null && pathpoint.costMalus == 0.0F);
/* 128 */     boolean flag2 = !(pathpoint2 != null && pathpoint2.costMalus == 0.0F);
/* 129 */     boolean flag3 = !(pathpoint1 != null && pathpoint1.costMalus == 0.0F);
/* 130 */     boolean flag4 = !(pathpoint4 != null && pathpoint4.costMalus == 0.0F);
/* 131 */     boolean flag5 = !(pathpoint5 != null && pathpoint5.costMalus == 0.0F);
/*     */     
/* 133 */     if (flag && flag3) {
/*     */       
/* 135 */       PathPoint pathpoint6 = openPoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord - 1);
/*     */       
/* 137 */       if (pathpoint6 != null && !pathpoint6.visited && pathpoint6.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 139 */         pathOptions[i++] = pathpoint6;
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if (flag && flag2) {
/*     */       
/* 145 */       PathPoint pathpoint7 = openPoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord - 1);
/*     */       
/* 147 */       if (pathpoint7 != null && !pathpoint7.visited && pathpoint7.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 149 */         pathOptions[i++] = pathpoint7;
/*     */       }
/*     */     } 
/*     */     
/* 153 */     if (flag1 && flag3) {
/*     */       
/* 155 */       PathPoint pathpoint8 = openPoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord + 1);
/*     */       
/* 157 */       if (pathpoint8 != null && !pathpoint8.visited && pathpoint8.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 159 */         pathOptions[i++] = pathpoint8;
/*     */       }
/*     */     } 
/*     */     
/* 163 */     if (flag1 && flag2) {
/*     */       
/* 165 */       PathPoint pathpoint9 = openPoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord + 1);
/*     */       
/* 167 */       if (pathpoint9 != null && !pathpoint9.visited && pathpoint9.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 169 */         pathOptions[i++] = pathpoint9;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     if (flag && flag4) {
/*     */       
/* 175 */       PathPoint pathpoint10 = openPoint(currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord - 1);
/*     */       
/* 177 */       if (pathpoint10 != null && !pathpoint10.visited && pathpoint10.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 179 */         pathOptions[i++] = pathpoint10;
/*     */       }
/*     */     } 
/*     */     
/* 183 */     if (flag1 && flag4) {
/*     */       
/* 185 */       PathPoint pathpoint11 = openPoint(currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord + 1);
/*     */       
/* 187 */       if (pathpoint11 != null && !pathpoint11.visited && pathpoint11.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 189 */         pathOptions[i++] = pathpoint11;
/*     */       }
/*     */     } 
/*     */     
/* 193 */     if (flag2 && flag4) {
/*     */       
/* 195 */       PathPoint pathpoint12 = openPoint(currentPoint.xCoord + 1, currentPoint.yCoord + 1, currentPoint.zCoord);
/*     */       
/* 197 */       if (pathpoint12 != null && !pathpoint12.visited && pathpoint12.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 199 */         pathOptions[i++] = pathpoint12;
/*     */       }
/*     */     } 
/*     */     
/* 203 */     if (flag3 && flag4) {
/*     */       
/* 205 */       PathPoint pathpoint13 = openPoint(currentPoint.xCoord - 1, currentPoint.yCoord + 1, currentPoint.zCoord);
/*     */       
/* 207 */       if (pathpoint13 != null && !pathpoint13.visited && pathpoint13.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 209 */         pathOptions[i++] = pathpoint13;
/*     */       }
/*     */     } 
/*     */     
/* 213 */     if (flag && flag5) {
/*     */       
/* 215 */       PathPoint pathpoint14 = openPoint(currentPoint.xCoord, currentPoint.yCoord - 1, currentPoint.zCoord - 1);
/*     */       
/* 217 */       if (pathpoint14 != null && !pathpoint14.visited && pathpoint14.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 219 */         pathOptions[i++] = pathpoint14;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (flag1 && flag5) {
/*     */       
/* 225 */       PathPoint pathpoint15 = openPoint(currentPoint.xCoord, currentPoint.yCoord - 1, currentPoint.zCoord + 1);
/*     */       
/* 227 */       if (pathpoint15 != null && !pathpoint15.visited && pathpoint15.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 229 */         pathOptions[i++] = pathpoint15;
/*     */       }
/*     */     } 
/*     */     
/* 233 */     if (flag2 && flag5) {
/*     */       
/* 235 */       PathPoint pathpoint16 = openPoint(currentPoint.xCoord + 1, currentPoint.yCoord - 1, currentPoint.zCoord);
/*     */       
/* 237 */       if (pathpoint16 != null && !pathpoint16.visited && pathpoint16.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 239 */         pathOptions[i++] = pathpoint16;
/*     */       }
/*     */     } 
/*     */     
/* 243 */     if (flag3 && flag5) {
/*     */       
/* 245 */       PathPoint pathpoint17 = openPoint(currentPoint.xCoord - 1, currentPoint.yCoord - 1, currentPoint.zCoord);
/*     */       
/* 247 */       if (pathpoint17 != null && !pathpoint17.visited && pathpoint17.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 249 */         pathOptions[i++] = pathpoint17;
/*     */       }
/*     */     } 
/*     */     
/* 253 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected PathPoint openPoint(int x, int y, int z) {
/* 263 */     PathPoint pathpoint = null;
/* 264 */     PathNodeType pathnodetype = func_192558_a(this.entity, x, y, z);
/* 265 */     float f = this.entity.getPathPriority(pathnodetype);
/*     */     
/* 267 */     if (f >= 0.0F) {
/*     */       
/* 269 */       pathpoint = super.openPoint(x, y, z);
/* 270 */       pathpoint.nodeType = pathnodetype;
/* 271 */       pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
/*     */       
/* 273 */       if (pathnodetype == PathNodeType.WALKABLE)
/*     */       {
/* 275 */         pathpoint.costMalus++;
/*     */       }
/*     */     } 
/*     */     
/* 279 */     return (pathnodetype != PathNodeType.OPEN && pathnodetype != PathNodeType.WALKABLE) ? pathpoint : pathpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn, boolean canEnterDoorsIn) {
/* 284 */     EnumSet<PathNodeType> enumset = EnumSet.noneOf(PathNodeType.class);
/* 285 */     PathNodeType pathnodetype = PathNodeType.BLOCKED;
/* 286 */     BlockPos blockpos = new BlockPos((Entity)entitylivingIn);
/* 287 */     pathnodetype = func_193577_a(blockaccessIn, x, y, z, xSize, ySize, zSize, canBreakDoorsIn, canEnterDoorsIn, enumset, pathnodetype, blockpos);
/*     */     
/* 289 */     if (enumset.contains(PathNodeType.FENCE))
/*     */     {
/* 291 */       return PathNodeType.FENCE;
/*     */     }
/*     */ 
/*     */     
/* 295 */     PathNodeType pathnodetype1 = PathNodeType.BLOCKED;
/*     */     
/* 297 */     for (PathNodeType pathnodetype2 : enumset) {
/*     */       
/* 299 */       if (entitylivingIn.getPathPriority(pathnodetype2) < 0.0F)
/*     */       {
/* 301 */         return pathnodetype2;
/*     */       }
/*     */       
/* 304 */       if (entitylivingIn.getPathPriority(pathnodetype2) >= entitylivingIn.getPathPriority(pathnodetype1))
/*     */       {
/* 306 */         pathnodetype1 = pathnodetype2;
/*     */       }
/*     */     } 
/*     */     
/* 310 */     if (pathnodetype == PathNodeType.OPEN && entitylivingIn.getPathPriority(pathnodetype1) == 0.0F)
/*     */     {
/* 312 */       return PathNodeType.OPEN;
/*     */     }
/*     */ 
/*     */     
/* 316 */     return pathnodetype1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
/* 323 */     PathNodeType pathnodetype = getPathNodeTypeRaw(blockaccessIn, x, y, z);
/*     */     
/* 325 */     if (pathnodetype == PathNodeType.OPEN && y >= 1) {
/*     */       
/* 327 */       Block block = blockaccessIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
/* 328 */       PathNodeType pathnodetype1 = getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
/*     */       
/* 330 */       if (pathnodetype1 != PathNodeType.DAMAGE_FIRE && block != Blocks.MAGMA && pathnodetype1 != PathNodeType.LAVA) {
/*     */         
/* 332 */         if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS)
/*     */         {
/* 334 */           pathnodetype = PathNodeType.DAMAGE_CACTUS;
/*     */         }
/*     */         else
/*     */         {
/* 338 */           pathnodetype = (pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER) ? PathNodeType.WALKABLE : PathNodeType.OPEN;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 343 */         pathnodetype = PathNodeType.DAMAGE_FIRE;
/*     */       } 
/*     */     } 
/*     */     
/* 347 */     pathnodetype = func_193578_a(blockaccessIn, x, y, z, pathnodetype);
/* 348 */     return pathnodetype;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathNodeType func_192559_a(EntityLiving p_192559_1_, BlockPos p_192559_2_) {
/* 353 */     return func_192558_a(p_192559_1_, p_192559_2_.getX(), p_192559_2_.getY(), p_192559_2_.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private PathNodeType func_192558_a(EntityLiving p_192558_1_, int p_192558_2_, int p_192558_3_, int p_192558_4_) {
/* 358 */     return getPathNodeType(this.blockaccess, p_192558_2_, p_192558_3_, p_192558_4_, p_192558_1_, this.entitySizeX, this.entitySizeY, this.entitySizeZ, getCanBreakDoors(), getCanEnterDoors());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\FlyingNodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */