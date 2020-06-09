/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListenableFutureTask;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.PriorityBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.VertexBufferUploader;
/*     */ import net.minecraft.client.renderer.WorldVertexBufferUploader;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkRenderDispatcher
/*     */ {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*  31 */   private static final ThreadFactory THREAD_FACTORY = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int countRenderBuilders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkRenderDispatcher() {
/*  44 */     this(-1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  49 */   private final List<Thread> listWorkerThreads = Lists.newArrayList();
/*  50 */   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
/*  51 */   private final PriorityBlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates = Queues.newPriorityBlockingQueue(); private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
/*  52 */   private final WorldVertexBufferUploader worldVertexUploader = new WorldVertexBufferUploader();
/*  53 */   private final VertexBufferUploader vertexUploader = new VertexBufferUploader();
/*  54 */   private final Queue<PendingUpload> queueChunkUploads = Queues.newPriorityQueue(); public ChunkRenderDispatcher(int p_i7_1_) {
/*  55 */     int i = Math.max(1, (int)(Runtime.getRuntime().maxMemory() * 0.3D) / 10485760);
/*  56 */     int j = Math.max(1, MathHelper.clamp(Runtime.getRuntime().availableProcessors(), 1, i / 5));
/*     */     
/*  58 */     if (p_i7_1_ < 0) {
/*     */       
/*  60 */       this.countRenderBuilders = MathHelper.clamp(j, 1, i);
/*     */     }
/*     */     else {
/*     */       
/*  64 */       this.countRenderBuilders = p_i7_1_;
/*     */     } 
/*     */     
/*  67 */     if (j > 1)
/*     */     {
/*  69 */       for (int k = 0; k < j; k++) {
/*     */         
/*  71 */         ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
/*  72 */         Thread thread = THREAD_FACTORY.newThread(chunkrenderworker);
/*  73 */         thread.start();
/*  74 */         this.listThreadedWorkers.add(chunkrenderworker);
/*  75 */         this.listWorkerThreads.add(thread);
/*     */       } 
/*     */     }
/*     */     
/*  79 */     this.queueFreeRenderBuilders = Queues.newArrayBlockingQueue(this.countRenderBuilders);
/*     */     
/*  81 */     for (int l = 0; l < this.countRenderBuilders; l++)
/*     */     {
/*  83 */       this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
/*     */     }
/*     */     
/*  86 */     this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
/*     */   }
/*     */   private final ChunkRenderWorker renderWorker;
/*     */   
/*     */   public String getDebugInfo() {
/*  91 */     return this.listWorkerThreads.isEmpty() ? String.format("pC: %03d, single-threaded", new Object[] { Integer.valueOf(this.queueChunkUpdates.size()) }) : String.format("pC: %03d, pU: %1d, aB: %1d", new Object[] { Integer.valueOf(this.queueChunkUpdates.size()), Integer.valueOf(this.queueChunkUploads.size()), Integer.valueOf(this.queueFreeRenderBuilders.size()) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean runChunkUploads(long p_178516_1_) {
/*  96 */     boolean flag1, flag = false;
/*     */ 
/*     */     
/*     */     do {
/* 100 */       flag1 = false;
/*     */       
/* 102 */       if (this.listWorkerThreads.isEmpty()) {
/*     */         
/* 104 */         ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
/*     */         
/* 106 */         if (chunkcompiletaskgenerator != null) {
/*     */           
/*     */           try {
/*     */             
/* 110 */             this.renderWorker.processTask(chunkcompiletaskgenerator);
/* 111 */             flag1 = true;
/*     */           }
/* 113 */           catch (InterruptedException var8) {
/*     */             
/* 115 */             LOGGER.warn("Skipped task due to interrupt");
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 120 */       synchronized (this.queueChunkUploads) {
/*     */         
/* 122 */         if (!this.queueChunkUploads.isEmpty())
/*     */         {
/* 124 */           (this.queueChunkUploads.poll()).uploadTask.run();
/* 125 */           flag1 = true;
/* 126 */           flag = true;
/*     */         }
/*     */       
/*     */       } 
/* 130 */     } while (p_178516_1_ != 0L && flag1 && p_178516_1_ >= System.nanoTime());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkLater(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 141 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 146 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/* 147 */       chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 151 */               ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */             }
/*     */           });
/* 154 */       boolean flag1 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/*     */       
/* 156 */       if (!flag1)
/*     */       {
/* 158 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */       
/* 161 */       flag = flag1;
/*     */     }
/*     */     finally {
/*     */       
/* 165 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 168 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkNow(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 173 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 178 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/*     */ 
/*     */       
/*     */       try {
/* 182 */         this.renderWorker.processTask(chunkcompiletaskgenerator);
/*     */       }
/* 184 */       catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       flag = true;
/*     */     }
/*     */     finally {
/*     */       
/* 193 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 196 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopChunkUpdates() {
/* 201 */     clearChunkUpdates();
/* 202 */     List<RegionRenderCacheBuilder> list = Lists.newArrayList();
/*     */     
/* 204 */     while (list.size() != this.countRenderBuilders) {
/*     */       
/* 206 */       runChunkUploads(Long.MAX_VALUE);
/*     */ 
/*     */       
/*     */       try {
/* 210 */         list.add(allocateRenderBuilder());
/*     */       }
/* 212 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     this.queueFreeRenderBuilders.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeRenderBuilder(RegionRenderCacheBuilder p_178512_1_) {
/* 223 */     this.queueFreeRenderBuilders.add(p_178512_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
/* 228 */     return this.queueFreeRenderBuilders.take();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
/* 233 */     return this.queueChunkUpdates.take();
/*     */   }
/*     */   
/*     */   public boolean updateTransparencyLater(RenderChunk chunkRenderer) {
/*     */     boolean flag1;
/* 238 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     try { final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskTransparency();
/*     */       
/* 245 */       if (chunkcompiletaskgenerator != null) {
/*     */         
/* 247 */         chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 251 */                 ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */               }
/*     */             });
/* 254 */         boolean flag2 = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/* 255 */         return flag2;
/*     */       } 
/*     */       
/* 258 */       boolean flag = true;
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 263 */     { chunkRenderer.getLockCompileTask().unlock(); }  chunkRenderer.getLockCompileTask().unlock();
/*     */ 
/*     */     
/* 266 */     return flag1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> uploadChunk(final BlockRenderLayer p_188245_1_, final BufferBuilder p_188245_2_, final RenderChunk p_188245_3_, final CompiledChunk p_188245_4_, final double p_188245_5_) {
/* 271 */     if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */       
/* 273 */       if (OpenGlHelper.useVbo()) {
/*     */         
/* 275 */         uploadVertexBuffer(p_188245_2_, p_188245_3_.getVertexBufferByLayer(p_188245_1_.ordinal()));
/*     */       }
/*     */       else {
/*     */         
/* 279 */         uploadDisplayList(p_188245_2_, ((ListedRenderChunk)p_188245_3_).getDisplayList(p_188245_1_, p_188245_4_), p_188245_3_);
/*     */       } 
/*     */       
/* 282 */       p_188245_2_.setTranslation(0.0D, 0.0D, 0.0D);
/* 283 */       return Futures.immediateFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 287 */     ListenableFutureTask<Object> listenablefuturetask = ListenableFutureTask.create(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 291 */             ChunkRenderDispatcher.this.uploadChunk(p_188245_1_, p_188245_2_, p_188245_3_, p_188245_4_, p_188245_5_);
/*     */           }
/* 293 */         }null);
/*     */     
/* 295 */     synchronized (this.queueChunkUploads) {
/*     */       
/* 297 */       this.queueChunkUploads.add(new PendingUpload(listenablefuturetask, p_188245_5_));
/* 298 */       return (ListenableFuture<Object>)listenablefuturetask;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadDisplayList(BufferBuilder p_178510_1_, int p_178510_2_, RenderChunk chunkRenderer) {
/* 305 */     GlStateManager.glNewList(p_178510_2_, 4864);
/* 306 */     GlStateManager.pushMatrix();
/* 307 */     chunkRenderer.multModelviewMatrix();
/* 308 */     this.worldVertexUploader.draw(p_178510_1_);
/* 309 */     GlStateManager.popMatrix();
/* 310 */     GlStateManager.glEndList();
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadVertexBuffer(BufferBuilder p_178506_1_, VertexBuffer vertexBufferIn) {
/* 315 */     this.vertexUploader.setVertexBuffer(vertexBufferIn);
/* 316 */     this.vertexUploader.draw(p_178506_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearChunkUpdates() {
/* 321 */     while (!this.queueChunkUpdates.isEmpty()) {
/*     */       
/* 323 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
/*     */       
/* 325 */       if (chunkcompiletaskgenerator != null)
/*     */       {
/* 327 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChunkUpdates() {
/* 334 */     return (this.queueChunkUpdates.isEmpty() && this.queueChunkUploads.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopWorkerThreads() {
/* 339 */     clearChunkUpdates();
/*     */     
/* 341 */     for (ChunkRenderWorker chunkrenderworker : this.listThreadedWorkers)
/*     */     {
/* 343 */       chunkrenderworker.notifyToStop();
/*     */     }
/*     */     
/* 346 */     for (Thread thread : this.listWorkerThreads) {
/*     */ 
/*     */       
/*     */       try {
/* 350 */         thread.interrupt();
/* 351 */         thread.join();
/*     */       }
/* 353 */       catch (InterruptedException interruptedexception) {
/*     */         
/* 355 */         LOGGER.warn("Interrupted whilst waiting for worker to die", interruptedexception);
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     this.queueFreeRenderBuilders.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoFreeRenderBuilders() {
/* 364 */     return this.queueFreeRenderBuilders.isEmpty();
/*     */   }
/*     */   
/*     */   class PendingUpload
/*     */     implements Comparable<PendingUpload>
/*     */   {
/*     */     private final ListenableFutureTask<Object> uploadTask;
/*     */     private final double distanceSq;
/*     */     
/*     */     public PendingUpload(ListenableFutureTask<Object> p_i46994_2_, double p_i46994_3_) {
/* 374 */       this.uploadTask = p_i46994_2_;
/* 375 */       this.distanceSq = p_i46994_3_;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(PendingUpload p_compareTo_1_) {
/* 380 */       return Doubles.compare(this.distanceSq, p_compareTo_1_.distanceSq);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\ChunkRenderDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */