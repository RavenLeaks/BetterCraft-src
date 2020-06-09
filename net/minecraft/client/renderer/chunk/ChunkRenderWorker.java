/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkRenderWorker
/*     */   implements Runnable {
/*  24 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final ChunkRenderDispatcher chunkRenderDispatcher;
/*     */   private final RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   private boolean shouldRun;
/*     */   
/*     */   public ChunkRenderWorker(ChunkRenderDispatcher p_i46201_1_) {
/*  31 */     this(p_i46201_1_, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkRenderWorker(ChunkRenderDispatcher chunkRenderDispatcherIn, @Nullable RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  36 */     this.shouldRun = true;
/*  37 */     this.chunkRenderDispatcher = chunkRenderDispatcherIn;
/*  38 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  43 */     while (this.shouldRun) {
/*     */ 
/*     */       
/*     */       try {
/*  47 */         processTask(this.chunkRenderDispatcher.getNextChunkUpdate());
/*     */       }
/*  49 */       catch (InterruptedException var3) {
/*     */         
/*  51 */         LOGGER.debug("Stopping chunk worker due to interrupt");
/*     */         
/*     */         return;
/*  54 */       } catch (Throwable throwable) {
/*     */         
/*  56 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Batching chunks");
/*  57 */         Minecraft.getMinecraft().crashed(Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(crashreport));
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processTask(final ChunkCompileTaskGenerator generator) throws InterruptedException {
/*  65 */     generator.getLock().lock();
/*     */ 
/*     */     
/*     */     try {
/*  69 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
/*     */         
/*  71 */         if (!generator.isFinished())
/*     */         {
/*  73 */           LOGGER.warn("Chunk render task was {} when I expected it to be pending; ignoring task", generator.getStatus());
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  79 */       BlockPos blockpos = new BlockPos((Entity)(Minecraft.getMinecraft()).player);
/*  80 */       BlockPos blockpos1 = generator.getRenderChunk().getPosition();
/*  81 */       int i = 16;
/*  82 */       int j = 8;
/*  83 */       int k = 24;
/*     */       
/*  85 */       if (blockpos1.add(8, 8, 8).distanceSq((Vec3i)blockpos) > 576.0D) {
/*     */         
/*  87 */         World world = generator.getRenderChunk().getWorld();
/*  88 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(blockpos1);
/*     */         
/*  90 */         if (!isChunkExisting((BlockPos)blockpos$mutableblockpos.setPos((Vec3i)blockpos1).move(EnumFacing.WEST, 16), world) || !isChunkExisting((BlockPos)blockpos$mutableblockpos.setPos((Vec3i)blockpos1).move(EnumFacing.NORTH, 16), world) || !isChunkExisting((BlockPos)blockpos$mutableblockpos.setPos((Vec3i)blockpos1).move(EnumFacing.EAST, 16), world) || !isChunkExisting((BlockPos)blockpos$mutableblockpos.setPos((Vec3i)blockpos1).move(EnumFacing.SOUTH, 16), world)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  96 */       generator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);
/*     */     }
/*     */     finally {
/*     */       
/* 100 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/* 103 */     Entity entity1 = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/* 105 */     if (entity1 == null) {
/*     */       
/* 107 */       generator.finish();
/*     */     }
/*     */     else {
/*     */       
/* 111 */       generator.setRegionRenderCacheBuilder(getRegionRenderCacheBuilder());
/* 112 */       float f = (float)entity1.posX;
/* 113 */       float f1 = (float)entity1.posY + entity1.getEyeHeight();
/* 114 */       float f2 = (float)entity1.posZ;
/* 115 */       ChunkCompileTaskGenerator.Type chunkcompiletaskgenerator$type = generator.getType();
/*     */       
/* 117 */       if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
/*     */         
/* 119 */         generator.getRenderChunk().rebuildChunk(f, f1, f2, generator);
/*     */       }
/* 121 */       else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
/*     */         
/* 123 */         generator.getRenderChunk().resortTransparency(f, f1, f2, generator);
/*     */       } 
/*     */       
/* 126 */       generator.getLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 130 */         if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */           
/* 132 */           if (!generator.isFinished())
/*     */           {
/* 134 */             LOGGER.warn("Chunk render task was {} when I expected it to be compiling; aborting task", generator.getStatus());
/*     */           }
/*     */           
/* 137 */           freeRenderBuilder(generator);
/*     */           
/*     */           return;
/*     */         } 
/* 141 */         generator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);
/*     */       }
/*     */       finally {
/*     */         
/* 145 */         generator.getLock().unlock();
/*     */       } 
/*     */       
/* 148 */       final CompiledChunk compiledchunk1 = generator.getCompiledChunk();
/* 149 */       ArrayList<ListenableFuture<Object>> arraylist1 = Lists.newArrayList();
/*     */       
/* 151 */       if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
/*     */         byte b; int i; BlockRenderLayer[] arrayOfBlockRenderLayer;
/* 153 */         for (i = (arrayOfBlockRenderLayer = BlockRenderLayer.values()).length, b = 0; b < i; ) { BlockRenderLayer blockrenderlayer = arrayOfBlockRenderLayer[b];
/*     */           
/* 155 */           if (compiledchunk1.isLayerStarted(blockrenderlayer))
/*     */           {
/* 157 */             arraylist1.add(this.chunkRenderDispatcher.uploadChunk(blockrenderlayer, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer), generator.getRenderChunk(), compiledchunk1, generator.getDistanceSq()));
/*     */           }
/*     */           b++; }
/*     */       
/* 161 */       } else if (chunkcompiletaskgenerator$type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
/*     */         
/* 163 */         arraylist1.add(this.chunkRenderDispatcher.uploadChunk(BlockRenderLayer.TRANSLUCENT, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(BlockRenderLayer.TRANSLUCENT), generator.getRenderChunk(), compiledchunk1, generator.getDistanceSq()));
/*     */       } 
/*     */       
/* 166 */       final ListenableFuture<List<Object>> listenablefuture = Futures.allAsList(arraylist1);
/* 167 */       generator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 171 */               listenablefuture.cancel(false);
/*     */             }
/*     */           });
/* 174 */       Futures.addCallback(listenablefuture, new FutureCallback<List<Object>>()
/*     */           {
/*     */             public void onSuccess(@Nullable List<Object> p_onSuccess_1_)
/*     */             {
/* 178 */               ChunkRenderWorker.this.freeRenderBuilder(generator);
/* 179 */               generator.getLock().lock();
/*     */ 
/*     */               
/*     */               try {
/* 183 */                 if (generator.getStatus() != ChunkCompileTaskGenerator.Status.UPLOADING) {
/*     */                   
/* 185 */                   if (!generator.isFinished())
/*     */                   {
/* 187 */                     ChunkRenderWorker.LOGGER.warn("Chunk render task was {} when I expected it to be uploading; aborting task", generator.getStatus());
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 193 */                 generator.setStatus(ChunkCompileTaskGenerator.Status.DONE);
/*     */               }
/*     */               finally {
/*     */                 
/* 197 */                 generator.getLock().unlock();
/*     */               } 
/*     */               
/* 200 */               generator.getRenderChunk().setCompiledChunk(compiledchunk1);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 204 */               ChunkRenderWorker.this.freeRenderBuilder(generator);
/*     */               
/* 206 */               if (!(p_onFailure_1_ instanceof java.util.concurrent.CancellationException) && !(p_onFailure_1_ instanceof InterruptedException))
/*     */               {
/* 208 */                 Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(p_onFailure_1_, "Rendering chunk"));
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChunkExisting(BlockPos p_188263_1_, World p_188263_2_) {
/* 217 */     if (p_188263_2_ == null)
/*     */     {
/* 219 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 223 */     return !p_188263_2_.getChunkFromChunkCoords(p_188263_1_.getX() >> 4, p_188263_1_.getZ() >> 4).isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
/* 229 */     return (this.regionRenderCacheBuilder != null) ? this.regionRenderCacheBuilder : this.chunkRenderDispatcher.allocateRenderBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   private void freeRenderBuilder(ChunkCompileTaskGenerator taskGenerator) {
/* 234 */     if (this.regionRenderCacheBuilder == null)
/*     */     {
/* 236 */       this.chunkRenderDispatcher.freeRenderBuilder(taskGenerator.getRegionRenderCacheBuilder());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyToStop() {
/* 242 */     this.shouldRun = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\ChunkRenderWorker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */