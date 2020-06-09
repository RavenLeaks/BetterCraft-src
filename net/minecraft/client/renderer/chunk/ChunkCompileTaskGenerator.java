/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ 
/*     */ public class ChunkCompileTaskGenerator
/*     */   implements Comparable<ChunkCompileTaskGenerator> {
/*     */   private final RenderChunk renderChunk;
/*  12 */   private final ReentrantLock lock = new ReentrantLock();
/*  13 */   private final List<Runnable> listFinishRunnables = Lists.newArrayList();
/*     */   private final Type type;
/*     */   private final double distanceSq;
/*     */   private RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   private CompiledChunk compiledChunk;
/*  18 */   private Status status = Status.PENDING;
/*     */   
/*     */   private boolean finished;
/*     */   
/*     */   public ChunkCompileTaskGenerator(RenderChunk p_i46560_1_, Type p_i46560_2_, double p_i46560_3_) {
/*  23 */     this.renderChunk = p_i46560_1_;
/*  24 */     this.type = p_i46560_2_;
/*  25 */     this.distanceSq = p_i46560_3_;
/*     */   }
/*     */ 
/*     */   
/*     */   public Status getStatus() {
/*  30 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunk() {
/*  35 */     return this.renderChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/*  40 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/*  45 */     this.compiledChunk = compiledChunkIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/*  50 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  55 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(Status statusIn) {
/*  60 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/*  64 */       this.status = statusIn;
/*     */     }
/*     */     finally {
/*     */       
/*  68 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {
/*  74 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/*  78 */       if (this.type == Type.REBUILD_CHUNK && this.status != Status.DONE)
/*     */       {
/*  80 */         this.renderChunk.setNeedsUpdate(false);
/*     */       }
/*     */       
/*  83 */       this.finished = true;
/*  84 */       this.status = Status.DONE;
/*     */       
/*  86 */       for (Runnable runnable : this.listFinishRunnables)
/*     */       {
/*  88 */         runnable.run();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/*  93 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFinishRunnable(Runnable runnable) {
/*  99 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 103 */       this.listFinishRunnables.add(runnable);
/*     */       
/* 105 */       if (this.finished)
/*     */       {
/* 107 */         runnable.run();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 112 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLock() {
/* 118 */     return this.lock;
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 123 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 128 */     return this.finished;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(ChunkCompileTaskGenerator p_compareTo_1_) {
/* 133 */     return Doubles.compare(this.distanceSq, p_compareTo_1_.distanceSq);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistanceSq() {
/* 138 */     return this.distanceSq;
/*     */   }
/*     */   
/*     */   public enum Status
/*     */   {
/* 143 */     PENDING,
/* 144 */     COMPILING,
/* 145 */     UPLOADING,
/* 146 */     DONE;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 151 */     REBUILD_CHUNK,
/* 152 */     RESORT_TRANSPARENCY;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\ChunkCompileTaskGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */