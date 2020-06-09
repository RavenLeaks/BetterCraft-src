/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.annotation.Nullable;
/*     */ import me.nzxter.bettercraft.mods.chunkanimator.ChunkAnimator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.ViewFrustum;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import optifine.BlockPosM;
/*     */ import optifine.ChunkCacheOF;
/*     */ import optifine.Config;
/*     */ import optifine.Reflector;
/*     */ import optifine.ReflectorForge;
/*     */ import optifine.RenderEnv;
/*     */ import shadersmod.client.SVertexBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderChunk
/*     */ {
/*     */   private World world;
/*     */   private final RenderGlobal renderGlobal;
/*     */   public static int renderChunksUpdated;
/*  53 */   public CompiledChunk compiledChunk = CompiledChunk.DUMMY;
/*  54 */   private final ReentrantLock lockCompileTask = new ReentrantLock();
/*  55 */   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
/*     */   private ChunkCompileTaskGenerator compileTask;
/*  57 */   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
/*     */   private final int index;
/*  59 */   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
/*  60 */   private final VertexBuffer[] vertexBuffers = new VertexBuffer[(BlockRenderLayer.values()).length];
/*     */   public AxisAlignedBB boundingBox;
/*  62 */   private int frameIndex = -1;
/*     */   private boolean needsUpdate = true;
/*  64 */   private final BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos(-1, -1, -1);
/*  65 */   private final BlockPos.MutableBlockPos[] mapEnumFacing = new BlockPos.MutableBlockPos[6];
/*     */   private boolean needsUpdateCustom;
/*  67 */   private static BlockRenderLayer[] ENUM_WORLD_BLOCK_LAYERS = BlockRenderLayer.values();
/*  68 */   private BlockRenderLayer[] blockLayersSingle = new BlockRenderLayer[1];
/*  69 */   private boolean isMipmaps = Config.isMipmaps();
/*  70 */   private boolean fixBlockLayer = !Reflector.BetterFoliageClient.exists();
/*     */   private boolean playerUpdate = false;
/*  72 */   private RenderChunk[] renderChunksOfset16 = new RenderChunk[6];
/*     */   
/*     */   private Chunk chunk;
/*     */   
/*     */   public RenderChunk(World p_i47120_1_, RenderGlobal p_i47120_2_, int p_i47120_3_) {
/*  77 */     for (int i = 0; i < this.mapEnumFacing.length; i++)
/*     */     {
/*  79 */       this.mapEnumFacing[i] = new BlockPos.MutableBlockPos();
/*     */     }
/*     */     
/*  82 */     this.world = p_i47120_1_;
/*  83 */     this.renderGlobal = p_i47120_2_;
/*  84 */     this.index = p_i47120_3_;
/*     */     
/*  86 */     if (OpenGlHelper.useVbo())
/*     */     {
/*  88 */       for (int j = 0; j < (BlockRenderLayer.values()).length; j++)
/*     */       {
/*  90 */         this.vertexBuffers[j] = new VertexBuffer(DefaultVertexFormats.BLOCK);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setFrameIndex(int frameIndexIn) {
/*  97 */     if (this.frameIndex == frameIndexIn)
/*     */     {
/*  99 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 103 */     this.frameIndex = frameIndexIn;
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexBuffer getVertexBufferByLayer(int layer) {
/* 110 */     return this.vertexBuffers[layer];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(int p_189562_1_, int p_189562_2_, int p_189562_3_) {
/* 118 */     if (p_189562_1_ != this.position.getX() || p_189562_2_ != this.position.getY() || p_189562_3_ != this.position.getZ()) {
/*     */       
/* 120 */       stopCompileTask();
/* 121 */       this.position.setPos(p_189562_1_, p_189562_2_, p_189562_3_);
/* 122 */       this.boundingBox = new AxisAlignedBB(p_189562_1_, p_189562_2_, p_189562_3_, (p_189562_1_ + 16), (p_189562_2_ + 16), (p_189562_3_ + 16)); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 124 */       for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 126 */         this.mapEnumFacing[enumfacing.ordinal()].setPos((Vec3i)this.position).move(enumfacing, 16);
/* 127 */         this.renderChunksOfset16[enumfacing.ordinal()] = null;
/*     */         b++; }
/*     */       
/* 130 */       ChunkAnimator.INSTANCE.animationHandler.setOrigin(this, new BlockPos(p_189562_1_, p_189562_2_, p_189562_3_));
/*     */       
/* 132 */       this.chunk = null;
/* 133 */       initModelviewMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resortTransparency(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 139 */     CompiledChunk compiledchunk = generator.getCompiledChunk();
/*     */     
/* 141 */     if (compiledchunk.getState() != null && !compiledchunk.isLayerEmpty(BlockRenderLayer.TRANSLUCENT)) {
/*     */       
/* 143 */       BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(BlockRenderLayer.TRANSLUCENT);
/* 144 */       preRenderBlocks(bufferbuilder, (BlockPos)this.position);
/* 145 */       bufferbuilder.setVertexState(compiledchunk.getState());
/* 146 */       postRenderBlocks(BlockRenderLayer.TRANSLUCENT, x, y, z, bufferbuilder, compiledchunk);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 152 */     CompiledChunk compiledchunk = new CompiledChunk();
/* 153 */     int i = 1;
/* 154 */     BlockPos.MutableBlockPos mutableBlockPos = this.position;
/* 155 */     BlockPos blockpos1 = mutableBlockPos.add(15, 15, 15);
/* 156 */     generator.getLock().lock();
/*     */ 
/*     */     
/*     */     try {
/* 160 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 165 */       generator.setCompiledChunk(compiledchunk);
/*     */     }
/*     */     finally {
/*     */       
/* 169 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/* 172 */     VisGraph lvt_9_1_ = new VisGraph();
/* 173 */     HashSet<TileEntity> lvt_10_1_ = Sets.newHashSet();
/*     */     
/* 175 */     if (this.world != null) {
/*     */       
/* 177 */       ChunkCacheOF chunkcacheof = makeChunkCacheOF();
/*     */       
/* 179 */       if (!chunkcacheof.isEmpty()) {
/*     */         
/* 181 */         renderChunksUpdated++;
/* 182 */         chunkcacheof.renderStart();
/* 183 */         boolean[] aboolean = new boolean[ENUM_WORLD_BLOCK_LAYERS.length];
/* 184 */         BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 185 */         boolean flag = Reflector.ForgeBlock_canRenderInLayer.exists();
/* 186 */         boolean flag1 = Reflector.ForgeHooksClient_setRenderLayer.exists();
/*     */         
/* 188 */         for (Object blockposm0 : BlockPosM.getAllInBoxMutable((BlockPos)mutableBlockPos, blockpos1)) {
/*     */           BlockRenderLayer[] ablockrenderlayer;
/* 190 */           BlockPosM blockposm = (BlockPosM)blockposm0;
/* 191 */           IBlockState iblockstate = chunkcacheof.getBlockState((BlockPos)blockposm);
/* 192 */           Block block = iblockstate.getBlock();
/*     */           
/* 194 */           if (iblockstate.isOpaqueCube())
/*     */           {
/* 196 */             lvt_9_1_.setOpaqueCube((BlockPos)blockposm);
/*     */           }
/*     */           
/* 199 */           if (ReflectorForge.blockHasTileEntity(iblockstate)) {
/*     */             
/* 201 */             TileEntity tileentity = chunkcacheof.getTileEntity((BlockPos)blockposm, Chunk.EnumCreateEntityType.CHECK);
/*     */             
/* 203 */             if (tileentity != null) {
/*     */               
/* 205 */               TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tileentity);
/*     */               
/* 207 */               if (tileentityspecialrenderer != null)
/*     */               {
/* 209 */                 if (tileentityspecialrenderer.isGlobalRenderer(tileentity)) {
/*     */                   
/* 211 */                   lvt_10_1_.add(tileentity);
/*     */                 }
/*     */                 else {
/*     */                   
/* 215 */                   compiledchunk.addTileEntity(tileentity);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 223 */           if (flag) {
/*     */             
/* 225 */             ablockrenderlayer = ENUM_WORLD_BLOCK_LAYERS;
/*     */           }
/*     */           else {
/*     */             
/* 229 */             ablockrenderlayer = this.blockLayersSingle;
/* 230 */             ablockrenderlayer[0] = block.getBlockLayer();
/*     */           } 
/*     */           
/* 233 */           for (int k = 0; k < ablockrenderlayer.length; k++) {
/*     */             
/* 235 */             BlockRenderLayer blockrenderlayer = ablockrenderlayer[k];
/*     */             
/* 237 */             if (flag) {
/*     */               
/* 239 */               boolean flag2 = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, new Object[] { iblockstate, blockrenderlayer });
/*     */               
/* 241 */               if (!flag2) {
/*     */                 continue;
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 247 */             if (flag1)
/*     */             {
/* 249 */               Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { blockrenderlayer });
/*     */             }
/*     */             
/* 252 */             if (this.fixBlockLayer)
/*     */             {
/* 254 */               blockrenderlayer = fixBlockLayer(block, blockrenderlayer);
/*     */             }
/*     */             
/* 257 */             int m = blockrenderlayer.ordinal();
/*     */             
/* 259 */             if (block.getDefaultState().getRenderType() != EnumBlockRenderType.INVISIBLE) {
/*     */               
/* 261 */               BufferBuilder bufferbuilder = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(m);
/* 262 */               bufferbuilder.setBlockLayer(blockrenderlayer);
/* 263 */               RenderEnv renderenv = bufferbuilder.getRenderEnv((IBlockAccess)chunkcacheof, iblockstate, (BlockPos)blockposm);
/* 264 */               renderenv.setRegionRenderCacheBuilder(generator.getRegionRenderCacheBuilder());
/*     */               
/* 266 */               if (!compiledchunk.isLayerStarted(blockrenderlayer)) {
/*     */                 
/* 268 */                 compiledchunk.setLayerStarted(blockrenderlayer);
/* 269 */                 preRenderBlocks(bufferbuilder, (BlockPos)mutableBlockPos);
/*     */               } 
/*     */               
/* 272 */               aboolean[m] = aboolean[m] | blockrendererdispatcher.renderBlock(iblockstate, (BlockPos)blockposm, (IBlockAccess)chunkcacheof, bufferbuilder);
/*     */               
/* 274 */               if (renderenv.isOverlaysRendered()) {
/*     */                 
/* 276 */                 postRenderOverlays(generator.getRegionRenderCacheBuilder(), compiledchunk, aboolean);
/* 277 */                 renderenv.setOverlaysRendered(false);
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/* 282 */           if (flag1)
/*     */           {
/* 284 */             Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, null); } 
/*     */         }  byte b;
/*     */         int j;
/*     */         BlockRenderLayer[] arrayOfBlockRenderLayer;
/* 288 */         for (j = (arrayOfBlockRenderLayer = ENUM_WORLD_BLOCK_LAYERS).length, b = 0; b < j; ) { BlockRenderLayer blockrenderlayer1 = arrayOfBlockRenderLayer[b];
/*     */           
/* 290 */           if (aboolean[blockrenderlayer1.ordinal()])
/*     */           {
/* 292 */             compiledchunk.setLayerUsed(blockrenderlayer1);
/*     */           }
/*     */           
/* 295 */           if (compiledchunk.isLayerStarted(blockrenderlayer1)) {
/*     */             
/* 297 */             if (Config.isShaders())
/*     */             {
/* 299 */               SVertexBuilder.calcNormalChunkLayer(generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer1));
/*     */             }
/*     */             
/* 302 */             postRenderBlocks(blockrenderlayer1, x, y, z, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(blockrenderlayer1), compiledchunk);
/*     */           } 
/*     */           b++; }
/*     */         
/* 306 */         chunkcacheof.renderFinish();
/*     */       } 
/*     */       
/* 309 */       compiledchunk.setVisibility(lvt_9_1_.computeVisibility());
/* 310 */       this.lockCompileTask.lock();
/*     */ 
/*     */       
/*     */       try {
/* 314 */         Set<TileEntity> set = Sets.newHashSet(lvt_10_1_);
/* 315 */         Set<TileEntity> set1 = Sets.newHashSet(this.setTileEntities);
/* 316 */         set.removeAll(this.setTileEntities);
/* 317 */         set1.removeAll(lvt_10_1_);
/* 318 */         this.setTileEntities.clear();
/* 319 */         this.setTileEntities.addAll(lvt_10_1_);
/* 320 */         this.renderGlobal.updateTileEntities(set1, set);
/*     */       }
/*     */       finally {
/*     */         
/* 324 */         this.lockCompileTask.unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finishCompileTask() {
/* 331 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 335 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE)
/*     */       {
/* 337 */         this.compileTask.finish();
/* 338 */         this.compileTask = null;
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 343 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLockCompileTask() {
/* 349 */     return this.lockCompileTask;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator;
/* 354 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 359 */       finishCompileTask();
/* 360 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK, getDistanceSq());
/* 361 */       resetChunkCache();
/* 362 */       chunkcompiletaskgenerator = this.compileTask;
/*     */     }
/*     */     finally {
/*     */       
/* 366 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 369 */     return chunkcompiletaskgenerator;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetChunkCache() {
/* 374 */     int i = 1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator1;
/* 380 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     try { if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING) {
/*     */         
/* 387 */         ChunkCompileTaskGenerator chunkcompiletaskgenerator2 = null;
/* 388 */         return chunkcompiletaskgenerator2;
/*     */       } 
/*     */       
/* 391 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
/*     */         
/* 393 */         this.compileTask.finish();
/* 394 */         this.compileTask = null;
/*     */       } 
/*     */       
/* 397 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY, getDistanceSq());
/* 398 */       this.compileTask.setCompiledChunk(this.compiledChunk);
/* 399 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.compileTask;
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 404 */     { this.lockCompileTask.unlock(); }  this.lockCompileTask.unlock();
/*     */ 
/*     */     
/* 407 */     return chunkcompiletaskgenerator1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getDistanceSq() {
/* 412 */     EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).player;
/* 413 */     double d0 = this.boundingBox.minX + 8.0D - entityplayersp.posX;
/* 414 */     double d1 = this.boundingBox.minY + 8.0D - entityplayersp.posY;
/* 415 */     double d2 = this.boundingBox.minZ + 8.0D - entityplayersp.posZ;
/* 416 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   
/*     */   private void preRenderBlocks(BufferBuilder worldRendererIn, BlockPos pos) {
/* 421 */     worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/* 422 */     worldRendererIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderBlocks(BlockRenderLayer layer, float x, float y, float z, BufferBuilder worldRendererIn, CompiledChunk compiledChunkIn) {
/* 427 */     if (layer == BlockRenderLayer.TRANSLUCENT && !compiledChunkIn.isLayerEmpty(layer)) {
/*     */       
/* 429 */       worldRendererIn.sortVertexData(x, y, z);
/* 430 */       compiledChunkIn.setState(worldRendererIn.getVertexState());
/*     */     } 
/*     */     
/* 433 */     worldRendererIn.finishDrawing();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initModelviewMatrix() {
/* 438 */     GlStateManager.pushMatrix();
/* 439 */     GlStateManager.loadIdentity();
/* 440 */     float f = 1.000001F;
/* 441 */     GlStateManager.translate(-8.0F, -8.0F, -8.0F);
/* 442 */     GlStateManager.scale(1.000001F, 1.000001F, 1.000001F);
/* 443 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 444 */     GlStateManager.getFloat(2982, this.modelviewMatrix);
/* 445 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void multModelviewMatrix() {
/* 450 */     GlStateManager.multMatrix(this.modelviewMatrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/* 455 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/* 460 */     this.lockCompiledChunk.lock();
/*     */ 
/*     */     
/*     */     try {
/* 464 */       this.compiledChunk = compiledChunkIn;
/*     */     }
/*     */     finally {
/*     */       
/* 468 */       this.lockCompiledChunk.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopCompileTask() {
/* 474 */     finishCompileTask();
/* 475 */     this.compiledChunk = CompiledChunk.DUMMY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlResources() {
/* 480 */     stopCompileTask();
/* 481 */     this.world = null;
/*     */     
/* 483 */     for (int i = 0; i < (BlockRenderLayer.values()).length; i++) {
/*     */       
/* 485 */       if (this.vertexBuffers[i] != null)
/*     */       {
/* 487 */         this.vertexBuffers[i].deleteGlBuffers();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 494 */     return (BlockPos)this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedsUpdate(boolean needsUpdateIn) {
/* 499 */     if (this.needsUpdate)
/*     */     {
/* 501 */       needsUpdateIn |= this.needsUpdateCustom;
/*     */     }
/*     */     
/* 504 */     this.needsUpdate = true;
/* 505 */     this.needsUpdateCustom = needsUpdateIn;
/*     */     
/* 507 */     if (isWorldPlayerUpdate())
/*     */     {
/* 509 */       this.playerUpdate = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearNeedsUpdate() {
/* 515 */     this.needsUpdate = false;
/* 516 */     this.needsUpdateCustom = false;
/* 517 */     this.playerUpdate = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNeedsUpdate() {
/* 522 */     return this.needsUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNeedsUpdateCustom() {
/* 527 */     return (this.needsUpdate && this.needsUpdateCustom);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getBlockPosOffset16(EnumFacing p_181701_1_) {
/* 532 */     return (BlockPos)this.mapEnumFacing[p_181701_1_.ordinal()];
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 537 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isWorldPlayerUpdate() {
/* 542 */     if (this.world instanceof WorldClient) {
/*     */       
/* 544 */       WorldClient worldclient = (WorldClient)this.world;
/* 545 */       return worldclient.isPlayerUpdate();
/*     */     } 
/*     */ 
/*     */     
/* 549 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 555 */     return this.playerUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockRenderLayer fixBlockLayer(Block p_fixBlockLayer_1_, BlockRenderLayer p_fixBlockLayer_2_) {
/* 560 */     if (this.isMipmaps) {
/*     */       
/* 562 */       if (p_fixBlockLayer_2_ == BlockRenderLayer.CUTOUT)
/*     */       {
/* 564 */         if (p_fixBlockLayer_1_ instanceof net.minecraft.block.BlockRedstoneWire)
/*     */         {
/* 566 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 569 */         if (p_fixBlockLayer_1_ instanceof net.minecraft.block.BlockCactus)
/*     */         {
/* 571 */           return p_fixBlockLayer_2_;
/*     */         }
/*     */         
/* 574 */         return BlockRenderLayer.CUTOUT_MIPPED;
/*     */       }
/*     */     
/* 577 */     } else if (p_fixBlockLayer_2_ == BlockRenderLayer.CUTOUT_MIPPED) {
/*     */       
/* 579 */       return BlockRenderLayer.CUTOUT;
/*     */     } 
/*     */     
/* 582 */     return p_fixBlockLayer_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderOverlays(RegionRenderCacheBuilder p_postRenderOverlays_1_, CompiledChunk p_postRenderOverlays_2_, boolean[] p_postRenderOverlays_3_) {
/* 587 */     postRenderOverlay(BlockRenderLayer.CUTOUT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 588 */     postRenderOverlay(BlockRenderLayer.CUTOUT_MIPPED, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/* 589 */     postRenderOverlay(BlockRenderLayer.TRANSLUCENT, p_postRenderOverlays_1_, p_postRenderOverlays_2_, p_postRenderOverlays_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderOverlay(BlockRenderLayer p_postRenderOverlay_1_, RegionRenderCacheBuilder p_postRenderOverlay_2_, CompiledChunk p_postRenderOverlay_3_, boolean[] p_postRenderOverlay_4_) {
/* 594 */     BufferBuilder bufferbuilder = p_postRenderOverlay_2_.getWorldRendererByLayer(p_postRenderOverlay_1_);
/*     */     
/* 596 */     if (bufferbuilder.isDrawing()) {
/*     */       
/* 598 */       p_postRenderOverlay_3_.setLayerStarted(p_postRenderOverlay_1_);
/* 599 */       p_postRenderOverlay_4_[p_postRenderOverlay_1_.ordinal()] = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkCacheOF makeChunkCacheOF() {
/* 605 */     BlockPos blockpos = this.position.add(-1, -1, -1);
/* 606 */     ChunkCache chunkcache = createRegionRenderCache(this.world, blockpos, this.position.add(16, 16, 16), 1);
/*     */     
/* 608 */     if (Reflector.MinecraftForgeClient_onRebuildChunk.exists())
/*     */     {
/* 610 */       Reflector.call(Reflector.MinecraftForgeClient_onRebuildChunk, new Object[] { this.world, this.position, chunkcache });
/*     */     }
/*     */     
/* 613 */     ChunkCacheOF chunkcacheof = new ChunkCacheOF(chunkcache, blockpos, 1);
/* 614 */     return chunkcacheof;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunkOffset16(ViewFrustum p_getRenderChunkOffset16_1_, EnumFacing p_getRenderChunkOffset16_2_) {
/* 619 */     RenderChunk renderchunk = this.renderChunksOfset16[p_getRenderChunkOffset16_2_.ordinal()];
/*     */     
/* 621 */     if (renderchunk == null) {
/*     */       
/* 623 */       BlockPos blockpos = getBlockPosOffset16(p_getRenderChunkOffset16_2_);
/* 624 */       renderchunk = p_getRenderChunkOffset16_1_.getRenderChunk(blockpos);
/* 625 */       this.renderChunksOfset16[p_getRenderChunkOffset16_2_.ordinal()] = renderchunk;
/*     */     } 
/*     */     
/* 628 */     return renderchunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk getChunk(World p_getChunk_1_) {
/* 633 */     if (this.chunk != null && this.chunk.isLoaded())
/*     */     {
/* 635 */       return this.chunk;
/*     */     }
/*     */ 
/*     */     
/* 639 */     this.chunk = p_getChunk_1_.getChunkFromBlockCoords(getPosition());
/* 640 */     return this.chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChunkCache createRegionRenderCache(World p_createRegionRenderCache_1_, BlockPos p_createRegionRenderCache_2_, BlockPos p_createRegionRenderCache_3_, int p_createRegionRenderCache_4_) {
/* 646 */     return new ChunkCache(p_createRegionRenderCache_1_, p_createRegionRenderCache_2_, p_createRegionRenderCache_3_, p_createRegionRenderCache_4_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\RenderChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */