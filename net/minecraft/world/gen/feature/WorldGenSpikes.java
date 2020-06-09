/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenSpikes
/*     */   extends WorldGenerator {
/*     */   private boolean crystalInvulnerable;
/*     */   private EndSpike spike;
/*     */   private BlockPos beamTarget;
/*     */   
/*     */   public void setSpike(EndSpike p_186143_1_) {
/*  20 */     this.spike = p_186143_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCrystalInvulnerable(boolean p_186144_1_) {
/*  25 */     this.crystalInvulnerable = p_186144_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  30 */     if (this.spike == null)
/*     */     {
/*  32 */       throw new IllegalStateException("Decoration requires priming with a spike");
/*     */     }
/*     */ 
/*     */     
/*  36 */     int i = this.spike.getRadius();
/*     */     
/*  38 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(new BlockPos(position.getX() - i, 0, position.getZ() - i), new BlockPos(position.getX() + i, this.spike.getHeight() + 10, position.getZ() + i))) {
/*     */       
/*  40 */       if (blockpos$mutableblockpos.distanceSq(position.getX(), blockpos$mutableblockpos.getY(), position.getZ()) <= (i * i + 1) && blockpos$mutableblockpos.getY() < this.spike.getHeight()) {
/*     */         
/*  42 */         setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.OBSIDIAN.getDefaultState()); continue;
/*     */       } 
/*  44 */       if (blockpos$mutableblockpos.getY() > 65)
/*     */       {
/*  46 */         setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
/*     */       }
/*     */     } 
/*     */     
/*  50 */     if (this.spike.isGuarded())
/*     */     {
/*  52 */       for (int j = -2; j <= 2; j++) {
/*     */         
/*  54 */         for (int k = -2; k <= 2; k++) {
/*     */           
/*  56 */           if (MathHelper.abs(j) == 2 || MathHelper.abs(k) == 2) {
/*     */             
/*  58 */             setBlockAndNotifyAdequately(worldIn, new BlockPos(position.getX() + j, this.spike.getHeight(), position.getZ() + k), Blocks.IRON_BARS.getDefaultState());
/*  59 */             setBlockAndNotifyAdequately(worldIn, new BlockPos(position.getX() + j, this.spike.getHeight() + 1, position.getZ() + k), Blocks.IRON_BARS.getDefaultState());
/*  60 */             setBlockAndNotifyAdequately(worldIn, new BlockPos(position.getX() + j, this.spike.getHeight() + 2, position.getZ() + k), Blocks.IRON_BARS.getDefaultState());
/*     */           } 
/*     */           
/*  63 */           setBlockAndNotifyAdequately(worldIn, new BlockPos(position.getX() + j, this.spike.getHeight() + 3, position.getZ() + k), Blocks.IRON_BARS.getDefaultState());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*  68 */     EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(worldIn);
/*  69 */     entityendercrystal.setBeamTarget(this.beamTarget);
/*  70 */     entityendercrystal.setEntityInvulnerable(this.crystalInvulnerable);
/*  71 */     entityendercrystal.setLocationAndAngles((position.getX() + 0.5F), (this.spike.getHeight() + 1), (position.getZ() + 0.5F), rand.nextFloat() * 360.0F, 0.0F);
/*  72 */     worldIn.spawnEntityInWorld((Entity)entityendercrystal);
/*  73 */     setBlockAndNotifyAdequately(worldIn, new BlockPos(position.getX(), this.spike.getHeight(), position.getZ()), Blocks.BEDROCK.getDefaultState());
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeamTarget(@Nullable BlockPos pos) {
/*  84 */     this.beamTarget = pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EndSpike
/*     */   {
/*     */     private final int centerX;
/*     */     private final int centerZ;
/*     */     private final int radius;
/*     */     private final int height;
/*     */     private final boolean guarded;
/*     */     private final AxisAlignedBB topBoundingBox;
/*     */     
/*     */     public EndSpike(int p_i47020_1_, int p_i47020_2_, int p_i47020_3_, int p_i47020_4_, boolean p_i47020_5_) {
/*  98 */       this.centerX = p_i47020_1_;
/*  99 */       this.centerZ = p_i47020_2_;
/* 100 */       this.radius = p_i47020_3_;
/* 101 */       this.height = p_i47020_4_;
/* 102 */       this.guarded = p_i47020_5_;
/* 103 */       this.topBoundingBox = new AxisAlignedBB((p_i47020_1_ - p_i47020_3_), 0.0D, (p_i47020_2_ - p_i47020_3_), (p_i47020_1_ + p_i47020_3_), 256.0D, (p_i47020_2_ + p_i47020_3_));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean doesStartInChunk(BlockPos p_186154_1_) {
/* 108 */       int i = this.centerX - this.radius;
/* 109 */       int j = this.centerZ - this.radius;
/* 110 */       return (p_186154_1_.getX() == (i & 0xFFFFFFF0) && p_186154_1_.getZ() == (j & 0xFFFFFFF0));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCenterX() {
/* 115 */       return this.centerX;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCenterZ() {
/* 120 */       return this.centerZ;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRadius() {
/* 125 */       return this.radius;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 130 */       return this.height;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isGuarded() {
/* 135 */       return this.guarded;
/*     */     }
/*     */ 
/*     */     
/*     */     public AxisAlignedBB getTopBoundingBox() {
/* 140 */       return this.topBoundingBox;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenSpikes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */