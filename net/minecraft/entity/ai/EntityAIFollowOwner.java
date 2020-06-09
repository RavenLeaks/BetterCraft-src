/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityTameable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNodeType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityAIFollowOwner
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityTameable thePet;
/*     */   private EntityLivingBase theOwner;
/*     */   World theWorld;
/*     */   private final double followSpeed;
/*     */   private final PathNavigate petPathfinder;
/*     */   private int timeToRecalcPath;
/*     */   float maxDist;
/*     */   float minDist;
/*     */   private float oldWaterCost;
/*     */   
/*     */   public EntityAIFollowOwner(EntityTameable thePetIn, double followSpeedIn, float minDistIn, float maxDistIn) {
/*  31 */     this.thePet = thePetIn;
/*  32 */     this.theWorld = thePetIn.world;
/*  33 */     this.followSpeed = followSpeedIn;
/*  34 */     this.petPathfinder = thePetIn.getNavigator();
/*  35 */     this.minDist = minDistIn;
/*  36 */     this.maxDist = maxDistIn;
/*  37 */     setMutexBits(3);
/*     */     
/*  39 */     if (!(thePetIn.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateGround) && !(thePetIn.getNavigator() instanceof net.minecraft.pathfinding.PathNavigateFlying))
/*     */     {
/*  41 */       throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  50 */     EntityLivingBase entitylivingbase = this.thePet.getOwner();
/*     */     
/*  52 */     if (entitylivingbase == null)
/*     */     {
/*  54 */       return false;
/*     */     }
/*  56 */     if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).isSpectator())
/*     */     {
/*  58 */       return false;
/*     */     }
/*  60 */     if (this.thePet.isSitting())
/*     */     {
/*  62 */       return false;
/*     */     }
/*  64 */     if (this.thePet.getDistanceSqToEntity((Entity)entitylivingbase) < (this.minDist * this.minDist))
/*     */     {
/*  66 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  70 */     this.theOwner = entitylivingbase;
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  80 */     return (!this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity((Entity)this.theOwner) > (this.maxDist * this.maxDist) && !this.thePet.isSitting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  88 */     this.timeToRecalcPath = 0;
/*  89 */     this.oldWaterCost = this.thePet.getPathPriority(PathNodeType.WATER);
/*  90 */     this.thePet.setPathPriority(PathNodeType.WATER, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  98 */     this.theOwner = null;
/*  99 */     this.petPathfinder.clearPathEntity();
/* 100 */     this.thePet.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 108 */     this.thePet.getLookHelper().setLookPositionWithEntity((Entity)this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
/*     */     
/* 110 */     if (!this.thePet.isSitting())
/*     */     {
/* 112 */       if (--this.timeToRecalcPath <= 0) {
/*     */         
/* 114 */         this.timeToRecalcPath = 10;
/*     */         
/* 116 */         if (!this.petPathfinder.tryMoveToEntityLiving((Entity)this.theOwner, this.followSpeed))
/*     */         {
/* 118 */           if (!this.thePet.getLeashed() && !this.thePet.isRiding())
/*     */           {
/* 120 */             if (this.thePet.getDistanceSqToEntity((Entity)this.theOwner) >= 144.0D) {
/*     */               
/* 122 */               int i = MathHelper.floor(this.theOwner.posX) - 2;
/* 123 */               int j = MathHelper.floor(this.theOwner.posZ) - 2;
/* 124 */               int k = MathHelper.floor((this.theOwner.getEntityBoundingBox()).minY);
/*     */               
/* 126 */               for (int l = 0; l <= 4; l++) {
/*     */                 
/* 128 */                 for (int i1 = 0; i1 <= 4; i1++) {
/*     */                   
/* 130 */                   if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && func_192381_a(i, j, k, l, i1)) {
/*     */                     
/* 132 */                     this.thePet.setLocationAndAngles(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
/* 133 */                     this.petPathfinder.clearPathEntity();
/*     */                     return;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_192381_a(int p_192381_1_, int p_192381_2_, int p_192381_3_, int p_192381_4_, int p_192381_5_) {
/* 147 */     BlockPos blockpos = new BlockPos(p_192381_1_ + p_192381_4_, p_192381_3_ - 1, p_192381_2_ + p_192381_5_);
/* 148 */     IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
/* 149 */     return (iblockstate.func_193401_d((IBlockAccess)this.theWorld, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn((Entity)this.thePet) && this.theWorld.isAirBlock(blockpos.up()) && this.theWorld.isAirBlock(blockpos.up(2)));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIFollowOwner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */