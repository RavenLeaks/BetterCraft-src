/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkPos
/*     */ {
/*     */   public final int chunkXPos;
/*     */   public final int chunkZPos;
/*  12 */   private int cachedHashCode = 0;
/*     */ 
/*     */   
/*     */   public ChunkPos(int x, int z) {
/*  16 */     this.chunkXPos = x;
/*  17 */     this.chunkZPos = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkPos(BlockPos pos) {
/*  22 */     this.chunkXPos = pos.getX() >> 4;
/*  23 */     this.chunkZPos = pos.getZ() >> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long asLong(int x, int z) {
/*  31 */     return x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32L;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  36 */     if (this.cachedHashCode != 0)
/*     */     {
/*  38 */       return this.cachedHashCode;
/*     */     }
/*     */ 
/*     */     
/*  42 */     int i = 1664525 * this.chunkXPos + 1013904223;
/*  43 */     int j = 1664525 * (this.chunkZPos ^ 0xDEADBEEF) + 1013904223;
/*  44 */     this.cachedHashCode = i ^ j;
/*  45 */     return this.cachedHashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  51 */     if (this == p_equals_1_)
/*     */     {
/*  53 */       return true;
/*     */     }
/*  55 */     if (!(p_equals_1_ instanceof ChunkPos))
/*     */     {
/*  57 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  61 */     ChunkPos chunkpos = (ChunkPos)p_equals_1_;
/*  62 */     return (this.chunkXPos == chunkpos.chunkXPos && this.chunkZPos == chunkpos.chunkZPos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceSq(Entity entityIn) {
/*  68 */     double d0 = (this.chunkXPos * 16 + 8);
/*  69 */     double d1 = (this.chunkZPos * 16 + 8);
/*  70 */     double d2 = d0 - entityIn.posX;
/*  71 */     double d3 = d1 - entityIn.posZ;
/*  72 */     return d2 * d2 + d3 * d3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXStart() {
/*  80 */     return this.chunkXPos << 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZStart() {
/*  88 */     return this.chunkZPos << 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXEnd() {
/*  96 */     return (this.chunkXPos << 4) + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZEnd() {
/* 104 */     return (this.chunkZPos << 4) + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getBlock(int x, int y, int z) {
/* 112 */     return new BlockPos((this.chunkXPos << 4) + x, y, (this.chunkZPos << 4) + z);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\ChunkPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */