/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PathNavigate
/*     */ {
/*     */   protected EntityLiving theEntity;
/*     */   protected World worldObj;
/*     */   @Nullable
/*     */   protected Path currentPath;
/*     */   protected double speed;
/*     */   private final IAttributeInstance pathSearchRange;
/*     */   protected int totalTicks;
/*     */   private int ticksAtLastPos;
/*  44 */   private Vec3d lastPosCheck = Vec3d.ZERO;
/*  45 */   private Vec3d timeoutCachedNode = Vec3d.ZERO;
/*     */   private long timeoutTimer;
/*     */   private long lastTimeoutCheck;
/*     */   private double timeoutLimit;
/*  49 */   protected float maxDistanceToWaypoint = 0.5F;
/*     */   
/*     */   protected boolean tryUpdatePath;
/*     */   private long lastTimeUpdated;
/*     */   protected NodeProcessor nodeProcessor;
/*     */   private BlockPos targetPos;
/*     */   private final PathFinder pathFinder;
/*     */   
/*     */   public PathNavigate(EntityLiving entitylivingIn, World worldIn) {
/*  58 */     this.theEntity = entitylivingIn;
/*  59 */     this.worldObj = worldIn;
/*  60 */     this.pathSearchRange = entitylivingIn.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
/*  61 */     this.pathFinder = getPathFinder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract PathFinder getPathFinder();
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpeed(double speedIn) {
/*  71 */     this.speed = speedIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getPathSearchRange() {
/*  79 */     return (float)this.pathSearchRange.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdatePathOnTimeout() {
/*  88 */     return this.tryUpdatePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePath() {
/*  93 */     if (this.worldObj.getTotalWorldTime() - this.lastTimeUpdated > 20L) {
/*     */       
/*  95 */       if (this.targetPos != null)
/*     */       {
/*  97 */         this.currentPath = null;
/*  98 */         this.currentPath = getPathToPos(this.targetPos);
/*  99 */         this.lastTimeUpdated = this.worldObj.getTotalWorldTime();
/* 100 */         this.tryUpdatePath = false;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 105 */       this.tryUpdatePath = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public final Path getPathToXYZ(double x, double y, double z) {
/* 116 */     return getPathToPos(new BlockPos(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path getPathToPos(BlockPos pos) {
/* 126 */     if (!canNavigate())
/*     */     {
/* 128 */       return null;
/*     */     }
/* 130 */     if (this.currentPath != null && !this.currentPath.isFinished() && pos.equals(this.targetPos))
/*     */     {
/* 132 */       return this.currentPath;
/*     */     }
/*     */ 
/*     */     
/* 136 */     this.targetPos = pos;
/* 137 */     float f = getPathSearchRange();
/* 138 */     this.worldObj.theProfiler.startSection("pathfind");
/* 139 */     BlockPos blockpos = new BlockPos((Entity)this.theEntity);
/* 140 */     int i = (int)(f + 8.0F);
/* 141 */     ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos.add(-i, -i, -i), blockpos.add(i, i, i), 0);
/* 142 */     Path path = this.pathFinder.findPath((IBlockAccess)chunkcache, this.theEntity, this.targetPos, f);
/* 143 */     this.worldObj.theProfiler.endSection();
/* 144 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path getPathToEntityLiving(Entity entityIn) {
/* 155 */     if (!canNavigate())
/*     */     {
/* 157 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 161 */     BlockPos blockpos = new BlockPos(entityIn);
/*     */     
/* 163 */     if (this.currentPath != null && !this.currentPath.isFinished() && blockpos.equals(this.targetPos))
/*     */     {
/* 165 */       return this.currentPath;
/*     */     }
/*     */ 
/*     */     
/* 169 */     this.targetPos = blockpos;
/* 170 */     float f = getPathSearchRange();
/* 171 */     this.worldObj.theProfiler.startSection("pathfind");
/* 172 */     BlockPos blockpos1 = (new BlockPos((Entity)this.theEntity)).up();
/* 173 */     int i = (int)(f + 16.0F);
/* 174 */     ChunkCache chunkcache = new ChunkCache(this.worldObj, blockpos1.add(-i, -i, -i), blockpos1.add(i, i, i), 0);
/* 175 */     Path path = this.pathFinder.findPath((IBlockAccess)chunkcache, this.theEntity, entityIn, f);
/* 176 */     this.worldObj.theProfiler.endSection();
/* 177 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
/* 187 */     return setPath(getPathToXYZ(x, y, z), speedIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
/* 195 */     Path path = getPathToEntityLiving(entityIn);
/* 196 */     return (path != null && setPath(path, speedIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setPath(@Nullable Path pathentityIn, double speedIn) {
/* 205 */     if (pathentityIn == null) {
/*     */       
/* 207 */       this.currentPath = null;
/* 208 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 212 */     if (!pathentityIn.isSamePath(this.currentPath))
/*     */     {
/* 214 */       this.currentPath = pathentityIn;
/*     */     }
/*     */     
/* 217 */     removeSunnyPath();
/*     */     
/* 219 */     if (this.currentPath.getCurrentPathLength() <= 0)
/*     */     {
/* 221 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 225 */     this.speed = speedIn;
/* 226 */     Vec3d vec3d = getEntityPosition();
/* 227 */     this.ticksAtLastPos = this.totalTicks;
/* 228 */     this.lastPosCheck = vec3d;
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path getPath() {
/* 241 */     return this.currentPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateNavigation() {
/* 246 */     this.totalTicks++;
/*     */     
/* 248 */     if (this.tryUpdatePath)
/*     */     {
/* 250 */       updatePath();
/*     */     }
/*     */     
/* 253 */     if (!noPath()) {
/*     */       
/* 255 */       if (canNavigate()) {
/*     */         
/* 257 */         pathFollow();
/*     */       }
/* 259 */       else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
/*     */         
/* 261 */         Vec3d vec3d = getEntityPosition();
/* 262 */         Vec3d vec3d1 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, this.currentPath.getCurrentPathIndex());
/*     */         
/* 264 */         if (vec3d.yCoord > vec3d1.yCoord && !this.theEntity.onGround && MathHelper.floor(vec3d.xCoord) == MathHelper.floor(vec3d1.xCoord) && MathHelper.floor(vec3d.zCoord) == MathHelper.floor(vec3d1.zCoord))
/*     */         {
/* 266 */           this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
/*     */         }
/*     */       } 
/*     */       
/* 270 */       func_192876_m();
/*     */       
/* 272 */       if (!noPath()) {
/*     */         
/* 274 */         Vec3d vec3d2 = this.currentPath.getPosition((Entity)this.theEntity);
/* 275 */         BlockPos blockpos = (new BlockPos(vec3d2)).down();
/* 276 */         AxisAlignedBB axisalignedbb = this.worldObj.getBlockState(blockpos).getBoundingBox((IBlockAccess)this.worldObj, blockpos);
/* 277 */         vec3d2 = vec3d2.subtract(0.0D, 1.0D - axisalignedbb.maxY, 0.0D);
/* 278 */         this.theEntity.getMoveHelper().setMoveTo(vec3d2.xCoord, vec3d2.yCoord, vec3d2.zCoord, this.speed);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_192876_m() {}
/*     */ 
/*     */   
/*     */   protected void pathFollow() {
/* 289 */     Vec3d vec3d = getEntityPosition();
/* 290 */     int i = this.currentPath.getCurrentPathLength();
/*     */     
/* 292 */     for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); j++) {
/*     */       
/* 294 */       if ((this.currentPath.getPathPointFromIndex(j)).yCoord != Math.floor(vec3d.yCoord)) {
/*     */         
/* 296 */         i = j;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 301 */     this.maxDistanceToWaypoint = (this.theEntity.width > 0.75F) ? (this.theEntity.width / 2.0F) : (0.75F - this.theEntity.width / 2.0F);
/* 302 */     Vec3d vec3d1 = this.currentPath.getCurrentPos();
/*     */     
/* 304 */     if (MathHelper.abs((float)(this.theEntity.posX - vec3d1.xCoord + 0.5D)) < this.maxDistanceToWaypoint && MathHelper.abs((float)(this.theEntity.posZ - vec3d1.zCoord + 0.5D)) < this.maxDistanceToWaypoint && Math.abs(this.theEntity.posY - vec3d1.yCoord) < 1.0D)
/*     */     {
/* 306 */       this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
/*     */     }
/*     */     
/* 309 */     int k = MathHelper.ceil(this.theEntity.width);
/* 310 */     int l = MathHelper.ceil(this.theEntity.height);
/* 311 */     int i1 = k;
/*     */     
/* 313 */     for (int j1 = i - 1; j1 >= this.currentPath.getCurrentPathIndex(); j1--) {
/*     */       
/* 315 */       if (isDirectPathBetweenPoints(vec3d, this.currentPath.getVectorFromIndex((Entity)this.theEntity, j1), k, l, i1)) {
/*     */         
/* 317 */         this.currentPath.setCurrentPathIndex(j1);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 322 */     checkForStuck(vec3d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkForStuck(Vec3d positionVec3) {
/* 331 */     if (this.totalTicks - this.ticksAtLastPos > 100) {
/*     */       
/* 333 */       if (positionVec3.squareDistanceTo(this.lastPosCheck) < 2.25D)
/*     */       {
/* 335 */         clearPathEntity();
/*     */       }
/*     */       
/* 338 */       this.ticksAtLastPos = this.totalTicks;
/* 339 */       this.lastPosCheck = positionVec3;
/*     */     } 
/*     */     
/* 342 */     if (this.currentPath != null && !this.currentPath.isFinished()) {
/*     */       
/* 344 */       Vec3d vec3d = this.currentPath.getCurrentPos();
/*     */       
/* 346 */       if (vec3d.equals(this.timeoutCachedNode)) {
/*     */         
/* 348 */         this.timeoutTimer += System.currentTimeMillis() - this.lastTimeoutCheck;
/*     */       }
/*     */       else {
/*     */         
/* 352 */         this.timeoutCachedNode = vec3d;
/* 353 */         double d0 = positionVec3.distanceTo(this.timeoutCachedNode);
/* 354 */         this.timeoutLimit = (this.theEntity.getAIMoveSpeed() > 0.0F) ? (d0 / this.theEntity.getAIMoveSpeed() * 1000.0D) : 0.0D;
/*     */       } 
/*     */       
/* 357 */       if (this.timeoutLimit > 0.0D && this.timeoutTimer > this.timeoutLimit * 3.0D) {
/*     */         
/* 359 */         this.timeoutCachedNode = Vec3d.ZERO;
/* 360 */         this.timeoutTimer = 0L;
/* 361 */         this.timeoutLimit = 0.0D;
/* 362 */         clearPathEntity();
/*     */       } 
/*     */       
/* 365 */       this.lastTimeoutCheck = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean noPath() {
/* 374 */     return !(this.currentPath != null && !this.currentPath.isFinished());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPathEntity() {
/* 382 */     this.currentPath = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Vec3d getEntityPosition();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean canNavigate();
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isInLiquid() {
/* 397 */     return !(!this.theEntity.isInWater() && !this.theEntity.isInLava());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeSunnyPath() {
/* 405 */     if (this.currentPath != null)
/*     */     {
/* 407 */       for (int i = 0; i < this.currentPath.getCurrentPathLength(); i++) {
/*     */         
/* 409 */         PathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);
/* 410 */         PathPoint pathpoint1 = (i + 1 < this.currentPath.getCurrentPathLength()) ? this.currentPath.getPathPointFromIndex(i + 1) : null;
/* 411 */         IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord));
/* 412 */         Block block = iblockstate.getBlock();
/*     */         
/* 414 */         if (block == Blocks.CAULDRON) {
/*     */           
/* 416 */           this.currentPath.setPoint(i, pathpoint.cloneMove(pathpoint.xCoord, pathpoint.yCoord + 1, pathpoint.zCoord));
/*     */           
/* 418 */           if (pathpoint1 != null && pathpoint.yCoord >= pathpoint1.yCoord)
/*     */           {
/* 420 */             this.currentPath.setPoint(i + 1, pathpoint1.cloneMove(pathpoint1.xCoord, pathpoint.yCoord + 1, pathpoint1.zCoord));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isDirectPathBetweenPoints(Vec3d paramVec3d1, Vec3d paramVec3d2, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canEntityStandOnPos(BlockPos pos) {
/* 434 */     return this.worldObj.getBlockState(pos.down()).isFullBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public NodeProcessor getNodeProcessor() {
/* 439 */     return this.nodeProcessor;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathNavigate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */