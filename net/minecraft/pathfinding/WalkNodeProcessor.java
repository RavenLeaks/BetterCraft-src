/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.BlockFenceGate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ public class WalkNodeProcessor
/*     */   extends NodeProcessor
/*     */ {
/*     */   protected float avoidsWater;
/*     */   
/*     */   public void initProcessor(IBlockAccess sourceIn, EntityLiving mob) {
/*  29 */     super.initProcessor(sourceIn, mob);
/*  30 */     this.avoidsWater = mob.getPathPriority(PathNodeType.WATER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess() {
/*  40 */     this.entity.setPathPriority(PathNodeType.WATER, this.avoidsWater);
/*  41 */     super.postProcess();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getStart() {
/*     */     int i;
/*  48 */     if (getCanSwim() && this.entity.isInWater()) {
/*     */       
/*  50 */       i = (int)(this.entity.getEntityBoundingBox()).minY;
/*  51 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ));
/*     */       
/*  53 */       for (Block block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock(); block == Blocks.FLOWING_WATER || block == Blocks.WATER; block = this.blockaccess.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock())
/*     */       {
/*  55 */         i++;
/*  56 */         blockpos$mutableblockpos.setPos(MathHelper.floor(this.entity.posX), i, MathHelper.floor(this.entity.posZ));
/*     */       }
/*     */     
/*  59 */     } else if (this.entity.onGround) {
/*     */       
/*  61 */       i = MathHelper.floor((this.entity.getEntityBoundingBox()).minY + 0.5D);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  67 */       for (BlockPos blockpos = new BlockPos((Entity)this.entity); (this.blockaccess.getBlockState(blockpos).getMaterial() == Material.AIR || this.blockaccess.getBlockState(blockpos).getBlock().isPassable(this.blockaccess, blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  72 */       i = blockpos.up().getY();
/*     */     } 
/*     */     
/*  75 */     BlockPos blockpos2 = new BlockPos((Entity)this.entity);
/*  76 */     PathNodeType pathnodetype1 = getPathNodeType(this.entity, blockpos2.getX(), i, blockpos2.getZ());
/*     */     
/*  78 */     if (this.entity.getPathPriority(pathnodetype1) < 0.0F) {
/*     */       
/*  80 */       Set<BlockPos> set = Sets.newHashSet();
/*  81 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).minX, i, (this.entity.getEntityBoundingBox()).minZ));
/*  82 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).minX, i, (this.entity.getEntityBoundingBox()).maxZ));
/*  83 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).maxX, i, (this.entity.getEntityBoundingBox()).minZ));
/*  84 */       set.add(new BlockPos((this.entity.getEntityBoundingBox()).maxX, i, (this.entity.getEntityBoundingBox()).maxZ));
/*     */       
/*  86 */       for (BlockPos blockpos1 : set) {
/*     */         
/*  88 */         PathNodeType pathnodetype = getPathNodeType(this.entity, blockpos1);
/*     */         
/*  90 */         if (this.entity.getPathPriority(pathnodetype) >= 0.0F)
/*     */         {
/*  92 */           return openPoint(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return openPoint(blockpos2.getX(), i, blockpos2.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointToCoords(double x, double y, double z) {
/* 105 */     return openPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
/*     */   }
/*     */ 
/*     */   
/*     */   public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance) {
/* 110 */     int i = 0;
/* 111 */     int j = 0;
/* 112 */     PathNodeType pathnodetype = getPathNodeType(this.entity, currentPoint.xCoord, currentPoint.yCoord + 1, currentPoint.zCoord);
/*     */     
/* 114 */     if (this.entity.getPathPriority(pathnodetype) >= 0.0F)
/*     */     {
/* 116 */       j = MathHelper.floor(Math.max(1.0F, this.entity.stepHeight));
/*     */     }
/*     */     
/* 119 */     BlockPos blockpos = (new BlockPos(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord)).down();
/* 120 */     double d0 = currentPoint.yCoord - 1.0D - (this.blockaccess.getBlockState(blockpos).getBoundingBox(this.blockaccess, blockpos)).maxY;
/* 121 */     PathPoint pathpoint = getSafePoint(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord + 1, j, d0, EnumFacing.SOUTH);
/* 122 */     PathPoint pathpoint1 = getSafePoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord, j, d0, EnumFacing.WEST);
/* 123 */     PathPoint pathpoint2 = getSafePoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord, j, d0, EnumFacing.EAST);
/* 124 */     PathPoint pathpoint3 = getSafePoint(currentPoint.xCoord, currentPoint.yCoord, currentPoint.zCoord - 1, j, d0, EnumFacing.NORTH);
/*     */     
/* 126 */     if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 128 */       pathOptions[i++] = pathpoint;
/*     */     }
/*     */     
/* 131 */     if (pathpoint1 != null && !pathpoint1.visited && pathpoint1.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 133 */       pathOptions[i++] = pathpoint1;
/*     */     }
/*     */     
/* 136 */     if (pathpoint2 != null && !pathpoint2.visited && pathpoint2.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 138 */       pathOptions[i++] = pathpoint2;
/*     */     }
/*     */     
/* 141 */     if (pathpoint3 != null && !pathpoint3.visited && pathpoint3.distanceTo(targetPoint) < maxDistance)
/*     */     {
/* 143 */       pathOptions[i++] = pathpoint3;
/*     */     }
/*     */     
/* 146 */     boolean flag = !(pathpoint3 != null && pathpoint3.nodeType != PathNodeType.OPEN && pathpoint3.costMalus == 0.0F);
/* 147 */     boolean flag1 = !(pathpoint != null && pathpoint.nodeType != PathNodeType.OPEN && pathpoint.costMalus == 0.0F);
/* 148 */     boolean flag2 = !(pathpoint2 != null && pathpoint2.nodeType != PathNodeType.OPEN && pathpoint2.costMalus == 0.0F);
/* 149 */     boolean flag3 = !(pathpoint1 != null && pathpoint1.nodeType != PathNodeType.OPEN && pathpoint1.costMalus == 0.0F);
/*     */     
/* 151 */     if (flag && flag3) {
/*     */       
/* 153 */       PathPoint pathpoint4 = getSafePoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord - 1, j, d0, EnumFacing.NORTH);
/*     */       
/* 155 */       if (pathpoint4 != null && !pathpoint4.visited && pathpoint4.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 157 */         pathOptions[i++] = pathpoint4;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     if (flag && flag2) {
/*     */       
/* 163 */       PathPoint pathpoint5 = getSafePoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord - 1, j, d0, EnumFacing.NORTH);
/*     */       
/* 165 */       if (pathpoint5 != null && !pathpoint5.visited && pathpoint5.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 167 */         pathOptions[i++] = pathpoint5;
/*     */       }
/*     */     } 
/*     */     
/* 171 */     if (flag1 && flag3) {
/*     */       
/* 173 */       PathPoint pathpoint6 = getSafePoint(currentPoint.xCoord - 1, currentPoint.yCoord, currentPoint.zCoord + 1, j, d0, EnumFacing.SOUTH);
/*     */       
/* 175 */       if (pathpoint6 != null && !pathpoint6.visited && pathpoint6.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 177 */         pathOptions[i++] = pathpoint6;
/*     */       }
/*     */     } 
/*     */     
/* 181 */     if (flag1 && flag2) {
/*     */       
/* 183 */       PathPoint pathpoint7 = getSafePoint(currentPoint.xCoord + 1, currentPoint.yCoord, currentPoint.zCoord + 1, j, d0, EnumFacing.SOUTH);
/*     */       
/* 185 */       if (pathpoint7 != null && !pathpoint7.visited && pathpoint7.distanceTo(targetPoint) < maxDistance)
/*     */       {
/* 187 */         pathOptions[i++] = pathpoint7;
/*     */       }
/*     */     } 
/*     */     
/* 191 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private PathPoint getSafePoint(int x, int y, int z, int p_186332_4_, double p_186332_5_, EnumFacing facing) {
/* 201 */     PathPoint pathpoint = null;
/* 202 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 203 */     BlockPos blockpos1 = blockpos.down();
/* 204 */     double d0 = y - 1.0D - (this.blockaccess.getBlockState(blockpos1).getBoundingBox(this.blockaccess, blockpos1)).maxY;
/*     */     
/* 206 */     if (d0 - p_186332_5_ > 1.125D)
/*     */     {
/* 208 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 212 */     PathNodeType pathnodetype = getPathNodeType(this.entity, x, y, z);
/* 213 */     float f = this.entity.getPathPriority(pathnodetype);
/* 214 */     double d1 = this.entity.width / 2.0D;
/*     */     
/* 216 */     if (f >= 0.0F) {
/*     */       
/* 218 */       pathpoint = openPoint(x, y, z);
/* 219 */       pathpoint.nodeType = pathnodetype;
/* 220 */       pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
/*     */     } 
/*     */     
/* 223 */     if (pathnodetype == PathNodeType.WALKABLE)
/*     */     {
/* 225 */       return pathpoint;
/*     */     }
/*     */ 
/*     */     
/* 229 */     if (pathpoint == null && p_186332_4_ > 0 && pathnodetype != PathNodeType.FENCE && pathnodetype != PathNodeType.TRAPDOOR) {
/*     */       
/* 231 */       pathpoint = getSafePoint(x, y + 1, z, p_186332_4_ - 1, p_186332_5_, facing);
/*     */       
/* 233 */       if (pathpoint != null && (pathpoint.nodeType == PathNodeType.OPEN || pathpoint.nodeType == PathNodeType.WALKABLE) && this.entity.width < 1.0F) {
/*     */         
/* 235 */         double d2 = (x - facing.getFrontOffsetX()) + 0.5D;
/* 236 */         double d3 = (z - facing.getFrontOffsetZ()) + 0.5D;
/* 237 */         AxisAlignedBB axisalignedbb = new AxisAlignedBB(d2 - d1, y + 0.001D, d3 - d1, d2 + d1, (y + this.entity.height), d3 + d1);
/* 238 */         AxisAlignedBB axisalignedbb1 = this.blockaccess.getBlockState(blockpos).getBoundingBox(this.blockaccess, blockpos);
/* 239 */         AxisAlignedBB axisalignedbb2 = axisalignedbb.addCoord(0.0D, axisalignedbb1.maxY - 0.002D, 0.0D);
/*     */         
/* 241 */         if (this.entity.world.collidesWithAnyBlock(axisalignedbb2))
/*     */         {
/* 243 */           pathpoint = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     if (pathnodetype == PathNodeType.OPEN) {
/*     */       
/* 250 */       AxisAlignedBB axisalignedbb3 = new AxisAlignedBB(x - d1 + 0.5D, y + 0.001D, z - d1 + 0.5D, x + d1 + 0.5D, (y + this.entity.height), z + d1 + 0.5D);
/*     */       
/* 252 */       if (this.entity.world.collidesWithAnyBlock(axisalignedbb3))
/*     */       {
/* 254 */         return null;
/*     */       }
/*     */       
/* 257 */       if (this.entity.width >= 1.0F) {
/*     */         
/* 259 */         PathNodeType pathnodetype1 = getPathNodeType(this.entity, x, y - 1, z);
/*     */         
/* 261 */         if (pathnodetype1 == PathNodeType.BLOCKED) {
/*     */           
/* 263 */           pathpoint = openPoint(x, y, z);
/* 264 */           pathpoint.nodeType = PathNodeType.WALKABLE;
/* 265 */           pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
/* 266 */           return pathpoint;
/*     */         } 
/*     */       } 
/*     */       
/* 270 */       int i = 0;
/*     */       
/* 272 */       while (y > 0 && pathnodetype == PathNodeType.OPEN) {
/*     */         
/* 274 */         y--;
/*     */         
/* 276 */         if (i++ >= this.entity.getMaxFallHeight())
/*     */         {
/* 278 */           return null;
/*     */         }
/*     */         
/* 281 */         pathnodetype = getPathNodeType(this.entity, x, y, z);
/* 282 */         f = this.entity.getPathPriority(pathnodetype);
/*     */         
/* 284 */         if (pathnodetype != PathNodeType.OPEN && f >= 0.0F) {
/*     */           
/* 286 */           pathpoint = openPoint(x, y, z);
/* 287 */           pathpoint.nodeType = pathnodetype;
/* 288 */           pathpoint.costMalus = Math.max(pathpoint.costMalus, f);
/*     */           
/*     */           break;
/*     */         } 
/* 292 */         if (f < 0.0F)
/*     */         {
/* 294 */           return null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     return pathpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z, EntityLiving entitylivingIn, int xSize, int ySize, int zSize, boolean canBreakDoorsIn, boolean canEnterDoorsIn) {
/* 306 */     EnumSet<PathNodeType> enumset = EnumSet.noneOf(PathNodeType.class);
/* 307 */     PathNodeType pathnodetype = PathNodeType.BLOCKED;
/* 308 */     double d0 = entitylivingIn.width / 2.0D;
/* 309 */     BlockPos blockpos = new BlockPos((Entity)entitylivingIn);
/* 310 */     pathnodetype = func_193577_a(blockaccessIn, x, y, z, xSize, ySize, zSize, canBreakDoorsIn, canEnterDoorsIn, enumset, pathnodetype, blockpos);
/*     */     
/* 312 */     if (enumset.contains(PathNodeType.FENCE))
/*     */     {
/* 314 */       return PathNodeType.FENCE;
/*     */     }
/*     */ 
/*     */     
/* 318 */     PathNodeType pathnodetype1 = PathNodeType.BLOCKED;
/*     */     
/* 320 */     for (PathNodeType pathnodetype2 : enumset) {
/*     */       
/* 322 */       if (entitylivingIn.getPathPriority(pathnodetype2) < 0.0F)
/*     */       {
/* 324 */         return pathnodetype2;
/*     */       }
/*     */       
/* 327 */       if (entitylivingIn.getPathPriority(pathnodetype2) >= entitylivingIn.getPathPriority(pathnodetype1))
/*     */       {
/* 329 */         pathnodetype1 = pathnodetype2;
/*     */       }
/*     */     } 
/*     */     
/* 333 */     if (pathnodetype == PathNodeType.OPEN && entitylivingIn.getPathPriority(pathnodetype1) == 0.0F)
/*     */     {
/* 335 */       return PathNodeType.OPEN;
/*     */     }
/*     */ 
/*     */     
/* 339 */     return pathnodetype1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathNodeType func_193577_a(IBlockAccess p_193577_1_, int p_193577_2_, int p_193577_3_, int p_193577_4_, int p_193577_5_, int p_193577_6_, int p_193577_7_, boolean p_193577_8_, boolean p_193577_9_, EnumSet<PathNodeType> p_193577_10_, PathNodeType p_193577_11_, BlockPos p_193577_12_) {
/* 346 */     for (int i = 0; i < p_193577_5_; i++) {
/*     */       
/* 348 */       for (int j = 0; j < p_193577_6_; j++) {
/*     */         
/* 350 */         for (int k = 0; k < p_193577_7_; k++) {
/*     */           
/* 352 */           int l = i + p_193577_2_;
/* 353 */           int i1 = j + p_193577_3_;
/* 354 */           int j1 = k + p_193577_4_;
/* 355 */           PathNodeType pathnodetype = getPathNodeType(p_193577_1_, l, i1, j1);
/*     */           
/* 357 */           if (pathnodetype == PathNodeType.DOOR_WOOD_CLOSED && p_193577_8_ && p_193577_9_)
/*     */           {
/* 359 */             pathnodetype = PathNodeType.WALKABLE;
/*     */           }
/*     */           
/* 362 */           if (pathnodetype == PathNodeType.DOOR_OPEN && !p_193577_9_)
/*     */           {
/* 364 */             pathnodetype = PathNodeType.BLOCKED;
/*     */           }
/*     */           
/* 367 */           if (pathnodetype == PathNodeType.RAIL && !(p_193577_1_.getBlockState(p_193577_12_).getBlock() instanceof net.minecraft.block.BlockRailBase) && !(p_193577_1_.getBlockState(p_193577_12_.down()).getBlock() instanceof net.minecraft.block.BlockRailBase))
/*     */           {
/* 369 */             pathnodetype = PathNodeType.FENCE;
/*     */           }
/*     */           
/* 372 */           if (i == 0 && j == 0 && k == 0)
/*     */           {
/* 374 */             p_193577_11_ = pathnodetype;
/*     */           }
/*     */           
/* 377 */           p_193577_10_.add(pathnodetype);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     return p_193577_11_;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathNodeType getPathNodeType(EntityLiving entitylivingIn, BlockPos pos) {
/* 387 */     return getPathNodeType(entitylivingIn, pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private PathNodeType getPathNodeType(EntityLiving entitylivingIn, int x, int y, int z) {
/* 392 */     return getPathNodeType(this.blockaccess, x, y, z, entitylivingIn, this.entitySizeX, this.entitySizeY, this.entitySizeZ, getCanBreakDoors(), getCanEnterDoors());
/*     */   }
/*     */ 
/*     */   
/*     */   public PathNodeType getPathNodeType(IBlockAccess blockaccessIn, int x, int y, int z) {
/* 397 */     PathNodeType pathnodetype = getPathNodeTypeRaw(blockaccessIn, x, y, z);
/*     */     
/* 399 */     if (pathnodetype == PathNodeType.OPEN && y >= 1) {
/*     */       
/* 401 */       Block block = blockaccessIn.getBlockState(new BlockPos(x, y - 1, z)).getBlock();
/* 402 */       PathNodeType pathnodetype1 = getPathNodeTypeRaw(blockaccessIn, x, y - 1, z);
/* 403 */       pathnodetype = (pathnodetype1 != PathNodeType.WALKABLE && pathnodetype1 != PathNodeType.OPEN && pathnodetype1 != PathNodeType.WATER && pathnodetype1 != PathNodeType.LAVA) ? PathNodeType.WALKABLE : PathNodeType.OPEN;
/*     */       
/* 405 */       if (pathnodetype1 == PathNodeType.DAMAGE_FIRE || block == Blocks.MAGMA)
/*     */       {
/* 407 */         pathnodetype = PathNodeType.DAMAGE_FIRE;
/*     */       }
/*     */       
/* 410 */       if (pathnodetype1 == PathNodeType.DAMAGE_CACTUS)
/*     */       {
/* 412 */         pathnodetype = PathNodeType.DAMAGE_CACTUS;
/*     */       }
/*     */     } 
/*     */     
/* 416 */     pathnodetype = func_193578_a(blockaccessIn, x, y, z, pathnodetype);
/* 417 */     return pathnodetype;
/*     */   }
/*     */ 
/*     */   
/*     */   public PathNodeType func_193578_a(IBlockAccess p_193578_1_, int p_193578_2_, int p_193578_3_, int p_193578_4_, PathNodeType p_193578_5_) {
/* 422 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*     */     
/* 424 */     if (p_193578_5_ == PathNodeType.WALKABLE)
/*     */     {
/* 426 */       for (int i = -1; i <= 1; i++) {
/*     */         
/* 428 */         for (int j = -1; j <= 1; j++) {
/*     */           
/* 430 */           if (i != 0 || j != 0) {
/*     */             
/* 432 */             Block block = p_193578_1_.getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos(i + p_193578_2_, p_193578_3_, j + p_193578_4_)).getBlock();
/*     */             
/* 434 */             if (block == Blocks.CACTUS) {
/*     */               
/* 436 */               p_193578_5_ = PathNodeType.DANGER_CACTUS;
/*     */             }
/* 438 */             else if (block == Blocks.FIRE) {
/*     */               
/* 440 */               p_193578_5_ = PathNodeType.DANGER_FIRE;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 447 */     blockpos$pooledmutableblockpos.release();
/* 448 */     return p_193578_5_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathNodeType getPathNodeTypeRaw(IBlockAccess p_189553_1_, int p_189553_2_, int p_189553_3_, int p_189553_4_) {
/* 453 */     BlockPos blockpos = new BlockPos(p_189553_2_, p_189553_3_, p_189553_4_);
/* 454 */     IBlockState iblockstate = p_189553_1_.getBlockState(blockpos);
/* 455 */     Block block = iblockstate.getBlock();
/* 456 */     Material material = iblockstate.getMaterial();
/*     */     
/* 458 */     if (material == Material.AIR)
/*     */     {
/* 460 */       return PathNodeType.OPEN;
/*     */     }
/* 462 */     if (block != Blocks.TRAPDOOR && block != Blocks.IRON_TRAPDOOR && block != Blocks.WATERLILY) {
/*     */       
/* 464 */       if (block == Blocks.FIRE)
/*     */       {
/* 466 */         return PathNodeType.DAMAGE_FIRE;
/*     */       }
/* 468 */       if (block == Blocks.CACTUS)
/*     */       {
/* 470 */         return PathNodeType.DAMAGE_CACTUS;
/*     */       }
/* 472 */       if (block instanceof BlockDoor && material == Material.WOOD && !((Boolean)iblockstate.getValue((IProperty)BlockDoor.OPEN)).booleanValue())
/*     */       {
/* 474 */         return PathNodeType.DOOR_WOOD_CLOSED;
/*     */       }
/* 476 */       if (block instanceof BlockDoor && material == Material.IRON && !((Boolean)iblockstate.getValue((IProperty)BlockDoor.OPEN)).booleanValue())
/*     */       {
/* 478 */         return PathNodeType.DOOR_IRON_CLOSED;
/*     */       }
/* 480 */       if (block instanceof BlockDoor && ((Boolean)iblockstate.getValue((IProperty)BlockDoor.OPEN)).booleanValue())
/*     */       {
/* 482 */         return PathNodeType.DOOR_OPEN;
/*     */       }
/* 484 */       if (block instanceof net.minecraft.block.BlockRailBase)
/*     */       {
/* 486 */         return PathNodeType.RAIL;
/*     */       }
/* 488 */       if (!(block instanceof net.minecraft.block.BlockFence) && !(block instanceof net.minecraft.block.BlockWall) && (!(block instanceof BlockFenceGate) || ((Boolean)iblockstate.getValue((IProperty)BlockFenceGate.OPEN)).booleanValue())) {
/*     */         
/* 490 */         if (material == Material.WATER)
/*     */         {
/* 492 */           return PathNodeType.WATER;
/*     */         }
/* 494 */         if (material == Material.LAVA)
/*     */         {
/* 496 */           return PathNodeType.LAVA;
/*     */         }
/*     */ 
/*     */         
/* 500 */         return block.isPassable(p_189553_1_, blockpos) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 505 */       return PathNodeType.FENCE;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 510 */     return PathNodeType.TRAPDOOR;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\WalkNodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */