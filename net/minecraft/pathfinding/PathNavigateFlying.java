/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class PathNavigateFlying
/*     */   extends PathNavigate
/*     */ {
/*     */   public PathNavigateFlying(EntityLiving p_i47412_1_, World p_i47412_2_) {
/*  14 */     super(p_i47412_1_, p_i47412_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected PathFinder getPathFinder() {
/*  19 */     this.nodeProcessor = new FlyingNodeProcessor();
/*  20 */     this.nodeProcessor.setCanEnterDoors(true);
/*  21 */     return new PathFinder(this.nodeProcessor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canNavigate() {
/*  29 */     return !((!func_192880_g() || !isInLiquid()) && this.theEntity.isRiding());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3d getEntityPosition() {
/*  34 */     return new Vec3d(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getPathToEntityLiving(Entity entityIn) {
/*  42 */     return getPathToPos(new BlockPos(entityIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateNavigation() {
/*  47 */     this.totalTicks++;
/*     */     
/*  49 */     if (this.tryUpdatePath)
/*     */     {
/*  51 */       updatePath();
/*     */     }
/*     */     
/*  54 */     if (!noPath()) {
/*     */       
/*  56 */       if (canNavigate()) {
/*     */         
/*  58 */         pathFollow();
/*     */       }
/*  60 */       else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
/*     */         
/*  62 */         Vec3d vec3d = this.currentPath.getVectorFromIndex((Entity)this.theEntity, this.currentPath.getCurrentPathIndex());
/*     */         
/*  64 */         if (MathHelper.floor(this.theEntity.posX) == MathHelper.floor(vec3d.xCoord) && MathHelper.floor(this.theEntity.posY) == MathHelper.floor(vec3d.yCoord) && MathHelper.floor(this.theEntity.posZ) == MathHelper.floor(vec3d.zCoord))
/*     */         {
/*  66 */           this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
/*     */         }
/*     */       } 
/*     */       
/*  70 */       func_192876_m();
/*     */       
/*  72 */       if (!noPath()) {
/*     */         
/*  74 */         Vec3d vec3d1 = this.currentPath.getPosition((Entity)this.theEntity);
/*  75 */         this.theEntity.getMoveHelper().setMoveTo(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, this.speed);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
/*  85 */     int i = MathHelper.floor(posVec31.xCoord);
/*  86 */     int j = MathHelper.floor(posVec31.yCoord);
/*  87 */     int k = MathHelper.floor(posVec31.zCoord);
/*  88 */     double d0 = posVec32.xCoord - posVec31.xCoord;
/*  89 */     double d1 = posVec32.yCoord - posVec31.yCoord;
/*  90 */     double d2 = posVec32.zCoord - posVec31.zCoord;
/*  91 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */     
/*  93 */     if (d3 < 1.0E-8D)
/*     */     {
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  99 */     double d4 = 1.0D / Math.sqrt(d3);
/* 100 */     d0 *= d4;
/* 101 */     d1 *= d4;
/* 102 */     d2 *= d4;
/* 103 */     double d5 = 1.0D / Math.abs(d0);
/* 104 */     double d6 = 1.0D / Math.abs(d1);
/* 105 */     double d7 = 1.0D / Math.abs(d2);
/* 106 */     double d8 = i - posVec31.xCoord;
/* 107 */     double d9 = j - posVec31.yCoord;
/* 108 */     double d10 = k - posVec31.zCoord;
/*     */     
/* 110 */     if (d0 >= 0.0D)
/*     */     {
/* 112 */       d8++;
/*     */     }
/*     */     
/* 115 */     if (d1 >= 0.0D)
/*     */     {
/* 117 */       d9++;
/*     */     }
/*     */     
/* 120 */     if (d2 >= 0.0D)
/*     */     {
/* 122 */       d10++;
/*     */     }
/*     */     
/* 125 */     d8 /= d0;
/* 126 */     d9 /= d1;
/* 127 */     d10 /= d2;
/* 128 */     int l = (d0 < 0.0D) ? -1 : 1;
/* 129 */     int i1 = (d1 < 0.0D) ? -1 : 1;
/* 130 */     int j1 = (d2 < 0.0D) ? -1 : 1;
/* 131 */     int k1 = MathHelper.floor(posVec32.xCoord);
/* 132 */     int l1 = MathHelper.floor(posVec32.yCoord);
/* 133 */     int i2 = MathHelper.floor(posVec32.zCoord);
/* 134 */     int j2 = k1 - i;
/* 135 */     int k2 = l1 - j;
/* 136 */     int l2 = i2 - k;
/*     */     
/* 138 */     while (j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
/*     */       
/* 140 */       if (d8 < d10 && d8 <= d9) {
/*     */         
/* 142 */         d8 += d5;
/* 143 */         i += l;
/* 144 */         j2 = k1 - i; continue;
/*     */       } 
/* 146 */       if (d9 < d8 && d9 <= d10) {
/*     */         
/* 148 */         d9 += d6;
/* 149 */         j += i1;
/* 150 */         k2 = l1 - j;
/*     */         
/*     */         continue;
/*     */       } 
/* 154 */       d10 += d7;
/* 155 */       k += j1;
/* 156 */       l2 = i2 - k;
/*     */     } 
/*     */ 
/*     */     
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_192879_a(boolean p_192879_1_) {
/* 166 */     this.nodeProcessor.setCanBreakDoors(p_192879_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192878_b(boolean p_192878_1_) {
/* 171 */     this.nodeProcessor.setCanEnterDoors(p_192878_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192877_c(boolean p_192877_1_) {
/* 176 */     this.nodeProcessor.setCanSwim(p_192877_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192880_g() {
/* 181 */     return this.nodeProcessor.getCanSwim();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canEntityStandOnPos(BlockPos pos) {
/* 186 */     return this.worldObj.getBlockState(pos).isFullyOpaque();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathNavigateFlying.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */