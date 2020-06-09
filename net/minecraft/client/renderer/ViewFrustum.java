/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ViewFrustum
/*     */ {
/*     */   protected final RenderGlobal renderGlobal;
/*     */   protected final World world;
/*     */   protected int countChunksY;
/*     */   protected int countChunksX;
/*     */   protected int countChunksZ;
/*     */   public RenderChunk[] renderChunks;
/*     */   
/*     */   public ViewFrustum(World worldIn, int renderDistanceChunks, RenderGlobal renderGlobalIn, IRenderChunkFactory renderChunkFactory) {
/*  21 */     this.renderGlobal = renderGlobalIn;
/*  22 */     this.world = worldIn;
/*  23 */     setCountChunksXYZ(renderDistanceChunks);
/*  24 */     createRenderChunks(renderChunkFactory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createRenderChunks(IRenderChunkFactory renderChunkFactory) {
/*  29 */     int i = this.countChunksX * this.countChunksY * this.countChunksZ;
/*  30 */     this.renderChunks = new RenderChunk[i];
/*  31 */     int j = 0;
/*     */     
/*  33 */     for (int k = 0; k < this.countChunksX; k++) {
/*     */       
/*  35 */       for (int l = 0; l < this.countChunksY; l++) {
/*     */         
/*  37 */         for (int i1 = 0; i1 < this.countChunksZ; i1++) {
/*     */           
/*  39 */           int j1 = (i1 * this.countChunksY + l) * this.countChunksX + k;
/*  40 */           this.renderChunks[j1] = renderChunkFactory.create(this.world, this.renderGlobal, j++);
/*  41 */           this.renderChunks[j1].setPosition(k * 16, l * 16, i1 * 16);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public void deleteGlResources() {
/*     */     byte b;
/*     */     int i;
/*     */     RenderChunk[] arrayOfRenderChunk;
/*  49 */     for (i = (arrayOfRenderChunk = this.renderChunks).length, b = 0; b < i; ) { RenderChunk renderchunk = arrayOfRenderChunk[b];
/*     */       
/*  51 */       renderchunk.deleteGlResources();
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   protected void setCountChunksXYZ(int renderDistanceChunks) {
/*  57 */     int i = renderDistanceChunks * 2 + 1;
/*  58 */     this.countChunksX = i;
/*  59 */     this.countChunksY = 16;
/*  60 */     this.countChunksZ = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChunkPositions(double viewEntityX, double viewEntityZ) {
/*  65 */     int i = MathHelper.floor(viewEntityX) - 8;
/*  66 */     int j = MathHelper.floor(viewEntityZ) - 8;
/*  67 */     int k = this.countChunksX * 16;
/*     */     
/*  69 */     for (int l = 0; l < this.countChunksX; l++) {
/*     */       
/*  71 */       int i1 = getBaseCoordinate(i, k, l);
/*     */       
/*  73 */       for (int j1 = 0; j1 < this.countChunksZ; j1++) {
/*     */         
/*  75 */         int k1 = getBaseCoordinate(j, k, j1);
/*     */         
/*  77 */         for (int l1 = 0; l1 < this.countChunksY; l1++) {
/*     */           
/*  79 */           int i2 = l1 * 16;
/*  80 */           RenderChunk renderchunk = this.renderChunks[(j1 * this.countChunksY + l1) * this.countChunksX + l];
/*  81 */           renderchunk.setPosition(i1, i2, k1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getBaseCoordinate(int p_178157_1_, int p_178157_2_, int p_178157_3_) {
/*  89 */     int i = p_178157_3_ * 16;
/*  90 */     int j = i - p_178157_1_ + p_178157_2_ / 2;
/*     */     
/*  92 */     if (j < 0)
/*     */     {
/*  94 */       j -= p_178157_2_ - 1;
/*     */     }
/*     */     
/*  97 */     return i - j / p_178157_2_ * p_178157_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markBlocksForUpdate(int p_187474_1_, int p_187474_2_, int p_187474_3_, int p_187474_4_, int p_187474_5_, int p_187474_6_, boolean p_187474_7_) {
/* 102 */     int i = MathHelper.intFloorDiv(p_187474_1_, 16);
/* 103 */     int j = MathHelper.intFloorDiv(p_187474_2_, 16);
/* 104 */     int k = MathHelper.intFloorDiv(p_187474_3_, 16);
/* 105 */     int l = MathHelper.intFloorDiv(p_187474_4_, 16);
/* 106 */     int i1 = MathHelper.intFloorDiv(p_187474_5_, 16);
/* 107 */     int j1 = MathHelper.intFloorDiv(p_187474_6_, 16);
/*     */     
/* 109 */     for (int k1 = i; k1 <= l; k1++) {
/*     */       
/* 111 */       int l1 = k1 % this.countChunksX;
/*     */       
/* 113 */       if (l1 < 0)
/*     */       {
/* 115 */         l1 += this.countChunksX;
/*     */       }
/*     */       
/* 118 */       for (int i2 = j; i2 <= i1; i2++) {
/*     */         
/* 120 */         int j2 = i2 % this.countChunksY;
/*     */         
/* 122 */         if (j2 < 0)
/*     */         {
/* 124 */           j2 += this.countChunksY;
/*     */         }
/*     */         
/* 127 */         for (int k2 = k; k2 <= j1; k2++) {
/*     */           
/* 129 */           int l2 = k2 % this.countChunksZ;
/*     */           
/* 131 */           if (l2 < 0)
/*     */           {
/* 133 */             l2 += this.countChunksZ;
/*     */           }
/*     */           
/* 136 */           int i3 = (l2 * this.countChunksY + j2) * this.countChunksX + l1;
/* 137 */           RenderChunk renderchunk = this.renderChunks[i3];
/* 138 */           renderchunk.setNeedsUpdate(p_187474_7_);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RenderChunk getRenderChunk(BlockPos pos) {
/* 147 */     int i = pos.getX() >> 4;
/* 148 */     int j = pos.getY() >> 4;
/* 149 */     int k = pos.getZ() >> 4;
/*     */     
/* 151 */     if (j >= 0 && j < this.countChunksY) {
/*     */       
/* 153 */       i %= this.countChunksX;
/*     */       
/* 155 */       if (i < 0)
/*     */       {
/* 157 */         i += this.countChunksX;
/*     */       }
/*     */       
/* 160 */       k %= this.countChunksZ;
/*     */       
/* 162 */       if (k < 0)
/*     */       {
/* 164 */         k += this.countChunksZ;
/*     */       }
/*     */       
/* 167 */       int l = (k * this.countChunksY + j) * this.countChunksX + i;
/* 168 */       return this.renderChunks[l];
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ViewFrustum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */