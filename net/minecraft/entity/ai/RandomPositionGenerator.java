/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomPositionGenerator
/*     */ {
/*  18 */   private static Vec3d staticVector = Vec3d.ZERO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d findRandomTarget(EntityCreature entitycreatureIn, int xz, int y) {
/*  27 */     return findRandomTargetBlock(entitycreatureIn, xz, y, null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d func_191377_b(EntityCreature p_191377_0_, int p_191377_1_, int p_191377_2_) {
/*  33 */     return func_191379_a(p_191377_0_, p_191377_1_, p_191377_2_, null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d findRandomTargetBlockTowards(EntityCreature entitycreatureIn, int xz, int y, Vec3d targetVec3) {
/*  43 */     staticVector = targetVec3.subtract(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ);
/*  44 */     return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Vec3d findRandomTargetBlockAwayFrom(EntityCreature entitycreatureIn, int xz, int y, Vec3d targetVec3) {
/*  54 */     staticVector = (new Vec3d(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ)).subtract(targetVec3);
/*  55 */     return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Vec3d findRandomTargetBlock(EntityCreature entitycreatureIn, int xz, int y, @Nullable Vec3d targetVec3) {
/*  66 */     return func_191379_a(entitycreatureIn, xz, y, targetVec3, true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Vec3d func_191379_a(EntityCreature p_191379_0_, int p_191379_1_, int p_191379_2_, @Nullable Vec3d p_191379_3_, boolean p_191379_4_) {
/*     */     boolean flag;
/*  72 */     PathNavigate pathnavigate = p_191379_0_.getNavigator();
/*  73 */     Random random = p_191379_0_.getRNG();
/*     */ 
/*     */     
/*  76 */     if (p_191379_0_.hasHome()) {
/*     */       
/*  78 */       double d0 = p_191379_0_.getHomePosition().distanceSq(MathHelper.floor(p_191379_0_.posX), MathHelper.floor(p_191379_0_.posY), MathHelper.floor(p_191379_0_.posZ)) + 4.0D;
/*  79 */       double d1 = (p_191379_0_.getMaximumHomeDistance() + p_191379_1_);
/*  80 */       flag = (d0 < d1 * d1);
/*     */     }
/*     */     else {
/*     */       
/*  84 */       flag = false;
/*     */     } 
/*     */     
/*  87 */     boolean flag1 = false;
/*  88 */     float f = -99999.0F;
/*  89 */     int k1 = 0;
/*  90 */     int i = 0;
/*  91 */     int j = 0;
/*     */     
/*  93 */     for (int k = 0; k < 10; k++) {
/*     */       
/*  95 */       int l = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;
/*  96 */       int i1 = random.nextInt(2 * p_191379_2_ + 1) - p_191379_2_;
/*  97 */       int j1 = random.nextInt(2 * p_191379_1_ + 1) - p_191379_1_;
/*     */       
/*  99 */       if (p_191379_3_ == null || l * p_191379_3_.xCoord + j1 * p_191379_3_.zCoord >= 0.0D) {
/*     */         
/* 101 */         if (p_191379_0_.hasHome() && p_191379_1_ > 1) {
/*     */           
/* 103 */           BlockPos blockpos = p_191379_0_.getHomePosition();
/*     */           
/* 105 */           if (p_191379_0_.posX > blockpos.getX()) {
/*     */             
/* 107 */             l -= random.nextInt(p_191379_1_ / 2);
/*     */           }
/*     */           else {
/*     */             
/* 111 */             l += random.nextInt(p_191379_1_ / 2);
/*     */           } 
/*     */           
/* 114 */           if (p_191379_0_.posZ > blockpos.getZ()) {
/*     */             
/* 116 */             j1 -= random.nextInt(p_191379_1_ / 2);
/*     */           }
/*     */           else {
/*     */             
/* 120 */             j1 += random.nextInt(p_191379_1_ / 2);
/*     */           } 
/*     */         } 
/*     */         
/* 124 */         BlockPos blockpos1 = new BlockPos(l + p_191379_0_.posX, i1 + p_191379_0_.posY, j1 + p_191379_0_.posZ);
/*     */         
/* 126 */         if ((!flag || p_191379_0_.isWithinHomeDistanceFromPosition(blockpos1)) && pathnavigate.canEntityStandOnPos(blockpos1)) {
/*     */           
/* 128 */           if (!p_191379_4_) {
/*     */             
/* 130 */             blockpos1 = func_191378_a(blockpos1, p_191379_0_);
/*     */             
/* 132 */             if (func_191380_b(blockpos1, p_191379_0_)) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 138 */           float f1 = p_191379_0_.getBlockPathWeight(blockpos1);
/*     */           
/* 140 */           if (f1 > f) {
/*     */             
/* 142 */             f = f1;
/* 143 */             k1 = l;
/* 144 */             i = i1;
/* 145 */             j = j1;
/* 146 */             flag1 = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       continue;
/*     */     } 
/* 152 */     if (flag1)
/*     */     {
/* 154 */       return new Vec3d(k1 + p_191379_0_.posX, i + p_191379_0_.posY, j + p_191379_0_.posZ);
/*     */     }
/*     */ 
/*     */     
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BlockPos func_191378_a(BlockPos p_191378_0_, EntityCreature p_191378_1_) {
/* 164 */     if (!p_191378_1_.world.getBlockState(p_191378_0_).getMaterial().isSolid())
/*     */     {
/* 166 */       return p_191378_0_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     for (BlockPos blockpos = p_191378_0_.up(); blockpos.getY() < p_191378_1_.world.getHeight() && p_191378_1_.world.getBlockState(blockpos).getMaterial().isSolid(); blockpos = blockpos.up());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 177 */     return blockpos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean func_191380_b(BlockPos p_191380_0_, EntityCreature p_191380_1_) {
/* 183 */     return (p_191380_1_.world.getBlockState(p_191380_0_).getMaterial() == Material.WATER);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\RandomPositionGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */