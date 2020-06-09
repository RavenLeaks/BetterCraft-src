/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class PathNavigateGround
/*     */   extends PathNavigate {
/*     */   private boolean shouldAvoidSun;
/*     */   
/*     */   public PathNavigateGround(EntityLiving entitylivingIn, World worldIn) {
/*  19 */     super(entitylivingIn, worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathFinder getPathFinder() {
/*  24 */     this.nodeProcessor = new WalkNodeProcessor();
/*  25 */     this.nodeProcessor.setCanEnterDoors(true);
/*  26 */     return new PathFinder(this.nodeProcessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canNavigate() {
/*  34 */     return !(!this.theEntity.onGround && (!getCanSwim() || !isInLiquid()) && !this.theEntity.isRiding());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3d getEntityPosition() {
/*  39 */     return new Vec3d(this.theEntity.posX, getPathablePosY(), this.theEntity.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getPathToPos(BlockPos pos) {
/*  47 */     if (this.worldObj.getBlockState(pos).getMaterial() == Material.AIR) {
/*     */       BlockPos blockpos;
/*     */ 
/*     */       
/*  51 */       for (blockpos = pos.down(); blockpos.getY() > 0 && this.worldObj.getBlockState(blockpos).getMaterial() == Material.AIR; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  56 */       if (blockpos.getY() > 0)
/*     */       {
/*  58 */         return super.getPathToPos(blockpos.up());
/*     */       }
/*     */       
/*  61 */       while (blockpos.getY() < this.worldObj.getHeight() && this.worldObj.getBlockState(blockpos).getMaterial() == Material.AIR)
/*     */       {
/*  63 */         blockpos = blockpos.up();
/*     */       }
/*     */       
/*  66 */       pos = blockpos;
/*     */     } 
/*     */     
/*  69 */     if (!this.worldObj.getBlockState(pos).getMaterial().isSolid())
/*     */     {
/*  71 */       return super.getPathToPos(pos);
/*     */     }
/*     */ 
/*     */     
/*     */     BlockPos blockpos1;
/*     */     
/*  77 */     for (blockpos1 = pos.up(); blockpos1.getY() < this.worldObj.getHeight() && this.worldObj.getBlockState(blockpos1).getMaterial().isSolid(); blockpos1 = blockpos1.up());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     return super.getPathToPos(blockpos1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getPathToEntityLiving(Entity entityIn) {
/*  91 */     return getPathToPos(new BlockPos(entityIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPathablePosY() {
/*  99 */     if (this.theEntity.isInWater() && getCanSwim()) {
/*     */       
/* 101 */       int i = (int)(this.theEntity.getEntityBoundingBox()).minY;
/* 102 */       Block block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor(this.theEntity.posX), i, MathHelper.floor(this.theEntity.posZ))).getBlock();
/* 103 */       int j = 0;
/*     */       
/* 105 */       while (block == Blocks.FLOWING_WATER || block == Blocks.WATER) {
/*     */         
/* 107 */         i++;
/* 108 */         block = this.worldObj.getBlockState(new BlockPos(MathHelper.floor(this.theEntity.posX), i, MathHelper.floor(this.theEntity.posZ))).getBlock();
/* 109 */         j++;
/*     */         
/* 111 */         if (j > 16)
/*     */         {
/* 113 */           return (int)(this.theEntity.getEntityBoundingBox()).minY;
/*     */         }
/*     */       } 
/*     */       
/* 117 */       return i;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     return (int)((this.theEntity.getEntityBoundingBox()).minY + 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeSunnyPath() {
/* 130 */     super.removeSunnyPath();
/*     */     
/* 132 */     if (this.shouldAvoidSun) {
/*     */       
/* 134 */       if (this.worldObj.canSeeSky(new BlockPos(MathHelper.floor(this.theEntity.posX), (int)((this.theEntity.getEntityBoundingBox()).minY + 0.5D), MathHelper.floor(this.theEntity.posZ)))) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 139 */       for (int i = 0; i < this.currentPath.getCurrentPathLength(); i++) {
/*     */         
/* 141 */         PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
/*     */         
/* 143 */         if (this.worldObj.canSeeSky(new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord))) {
/*     */           
/* 145 */           this.currentPath.setCurrentPathLength(i - 1);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
/* 157 */     int i = MathHelper.floor(posVec31.xCoord);
/* 158 */     int j = MathHelper.floor(posVec31.zCoord);
/* 159 */     double d0 = posVec32.xCoord - posVec31.xCoord;
/* 160 */     double d1 = posVec32.zCoord - posVec31.zCoord;
/* 161 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 163 */     if (d2 < 1.0E-8D)
/*     */     {
/* 165 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 169 */     double d3 = 1.0D / Math.sqrt(d2);
/* 170 */     d0 *= d3;
/* 171 */     d1 *= d3;
/* 172 */     sizeX += 2;
/* 173 */     sizeZ += 2;
/*     */     
/* 175 */     if (!isSafeToStandAt(i, (int)posVec31.yCoord, j, sizeX, sizeY, sizeZ, posVec31, d0, d1))
/*     */     {
/* 177 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 181 */     sizeX -= 2;
/* 182 */     sizeZ -= 2;
/* 183 */     double d4 = 1.0D / Math.abs(d0);
/* 184 */     double d5 = 1.0D / Math.abs(d1);
/* 185 */     double d6 = i - posVec31.xCoord;
/* 186 */     double d7 = j - posVec31.zCoord;
/*     */     
/* 188 */     if (d0 >= 0.0D)
/*     */     {
/* 190 */       d6++;
/*     */     }
/*     */     
/* 193 */     if (d1 >= 0.0D)
/*     */     {
/* 195 */       d7++;
/*     */     }
/*     */     
/* 198 */     d6 /= d0;
/* 199 */     d7 /= d1;
/* 200 */     int k = (d0 < 0.0D) ? -1 : 1;
/* 201 */     int l = (d1 < 0.0D) ? -1 : 1;
/* 202 */     int i1 = MathHelper.floor(posVec32.xCoord);
/* 203 */     int j1 = MathHelper.floor(posVec32.zCoord);
/* 204 */     int k1 = i1 - i;
/* 205 */     int l1 = j1 - j;
/*     */     
/* 207 */     while (k1 * k > 0 || l1 * l > 0) {
/*     */       
/* 209 */       if (d6 < d7) {
/*     */         
/* 211 */         d6 += d4;
/* 212 */         i += k;
/* 213 */         k1 = i1 - i;
/*     */       }
/*     */       else {
/*     */         
/* 217 */         d7 += d5;
/* 218 */         j += l;
/* 219 */         l1 = j1 - j;
/*     */       } 
/*     */       
/* 222 */       if (!isSafeToStandAt(i, (int)posVec31.yCoord, j, sizeX, sizeY, sizeZ, posVec31, d0, d1))
/*     */       {
/* 224 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 228 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d vec31, double p_179683_8_, double p_179683_10_) {
/* 238 */     int i = x - sizeX / 2;
/* 239 */     int j = z - sizeZ / 2;
/*     */     
/* 241 */     if (!isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_))
/*     */     {
/* 243 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 247 */     for (int k = i; k < i + sizeX; k++) {
/*     */       
/* 249 */       for (int l = j; l < j + sizeZ; l++) {
/*     */         
/* 251 */         double d0 = k + 0.5D - vec31.xCoord;
/* 252 */         double d1 = l + 0.5D - vec31.zCoord;
/*     */         
/* 254 */         if (d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0D) {
/*     */           
/* 256 */           PathNodeType pathnodetype = this.nodeProcessor.getPathNodeType((IBlockAccess)this.worldObj, k, y - 1, l, this.theEntity, sizeX, sizeY, sizeZ, true, true);
/*     */           
/* 258 */           if (pathnodetype == PathNodeType.WATER)
/*     */           {
/* 260 */             return false;
/*     */           }
/*     */           
/* 263 */           if (pathnodetype == PathNodeType.LAVA)
/*     */           {
/* 265 */             return false;
/*     */           }
/*     */           
/* 268 */           if (pathnodetype == PathNodeType.OPEN)
/*     */           {
/* 270 */             return false;
/*     */           }
/*     */           
/* 273 */           pathnodetype = this.nodeProcessor.getPathNodeType((IBlockAccess)this.worldObj, k, y, l, this.theEntity, sizeX, sizeY, sizeZ, true, true);
/* 274 */           float f = this.theEntity.getPathPriority(pathnodetype);
/*     */           
/* 276 */           if (f < 0.0F || f >= 8.0F)
/*     */           {
/* 278 */             return false;
/*     */           }
/*     */           
/* 281 */           if (pathnodetype == PathNodeType.DAMAGE_FIRE || pathnodetype == PathNodeType.DANGER_FIRE || pathnodetype == PathNodeType.DAMAGE_OTHER)
/*     */           {
/* 283 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPositionClear(int p_179692_1_, int p_179692_2_, int p_179692_3_, int p_179692_4_, int p_179692_5_, int p_179692_6_, Vec3d p_179692_7_, double p_179692_8_, double p_179692_10_) {
/* 298 */     for (BlockPos blockpos : BlockPos.getAllInBox(new BlockPos(p_179692_1_, p_179692_2_, p_179692_3_), new BlockPos(p_179692_1_ + p_179692_4_ - 1, p_179692_2_ + p_179692_5_ - 1, p_179692_3_ + p_179692_6_ - 1))) {
/*     */       
/* 300 */       double d0 = blockpos.getX() + 0.5D - p_179692_7_.xCoord;
/* 301 */       double d1 = blockpos.getZ() + 0.5D - p_179692_7_.zCoord;
/*     */       
/* 303 */       if (d0 * p_179692_8_ + d1 * p_179692_10_ >= 0.0D) {
/*     */         
/* 305 */         Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*     */         
/* 307 */         if (!block.isPassable((IBlockAccess)this.worldObj, blockpos))
/*     */         {
/* 309 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBreakDoors(boolean canBreakDoors) {
/* 319 */     this.nodeProcessor.setCanBreakDoors(canBreakDoors);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnterDoors(boolean enterDoors) {
/* 324 */     this.nodeProcessor.setCanEnterDoors(enterDoors);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnterDoors() {
/* 329 */     return this.nodeProcessor.getCanEnterDoors();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCanSwim(boolean canSwim) {
/* 334 */     this.nodeProcessor.setCanSwim(canSwim);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCanSwim() {
/* 339 */     return this.nodeProcessor.getCanSwim();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAvoidSun(boolean avoidSun) {
/* 344 */     this.shouldAvoidSun = avoidSun;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathNavigateGround.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */