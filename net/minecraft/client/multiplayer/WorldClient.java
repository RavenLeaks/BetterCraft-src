/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecart;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleFirework;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.DimensionType;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.MapStorage;
/*     */ import net.minecraft.world.storage.SaveDataMemoryStorage;
/*     */ import net.minecraft.world.storage.SaveHandlerMP;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import optifine.Config;
/*     */ import optifine.CustomGuis;
/*     */ import optifine.DynamicLights;
/*     */ import optifine.PlayerControllerOF;
/*     */ import optifine.Reflector;
/*     */ import wdl.WDLHooks;
/*     */ 
/*     */ public class WorldClient extends World {
/*     */   private final NetHandlerPlayClient connection;
/*  62 */   private final Set<Entity> entityList = Sets.newHashSet(); private ChunkProviderClient clientChunkProvider;
/*  63 */   private final Set<Entity> entitySpawnQueue = Sets.newHashSet();
/*  64 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  65 */   private final Set<ChunkPos> previousActiveChunkSet = Sets.newHashSet();
/*     */   private int ambienceTicks;
/*     */   protected Set<ChunkPos> viewableChunks;
/*  68 */   private int playerChunkX = Integer.MIN_VALUE;
/*  69 */   private int playerChunkY = Integer.MIN_VALUE;
/*     */   
/*     */   private boolean playerUpdate = false;
/*     */   
/*     */   public WorldClient(NetHandlerPlayClient netHandler, WorldSettings settings, int dimension, EnumDifficulty difficulty, Profiler profilerIn) {
/*  74 */     super((ISaveHandler)new SaveHandlerMP(), new WorldInfo(settings, "MpServer"), makeWorldProvider(dimension), profilerIn, true);
/*  75 */     this.ambienceTicks = this.rand.nextInt(12000);
/*  76 */     this.viewableChunks = Sets.newHashSet();
/*  77 */     this.connection = netHandler;
/*  78 */     getWorldInfo().setDifficulty(difficulty);
/*  79 */     this.provider.registerWorld(this);
/*  80 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*  81 */     this.chunkProvider = createChunkProvider();
/*  82 */     this.mapStorage = (MapStorage)new SaveDataMemoryStorage();
/*  83 */     calculateInitialSkylight();
/*  84 */     calculateInitialWeather();
/*  85 */     Reflector.call(this, Reflector.ForgeWorld_initCapabilities, new Object[0]);
/*  86 */     Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { this });
/*     */     
/*  88 */     if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerControllerMP.class) {
/*     */       
/*  90 */       this.mc.playerController = (PlayerControllerMP)new PlayerControllerOF(this.mc, netHandler);
/*  91 */       CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.mc.playerController);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static WorldProvider makeWorldProvider(int p_makeWorldProvider_0_) {
/*  97 */     return Reflector.DimensionManager_createProviderFor.exists() ? (WorldProvider)Reflector.call(Reflector.DimensionManager_createProviderFor, new Object[] { Integer.valueOf(p_makeWorldProvider_0_) }) : DimensionType.getById(p_makeWorldProvider_0_).createDimension();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 105 */     super.tick();
/* 106 */     setTotalWorldTime(getTotalWorldTime() + 1L);
/*     */     
/* 108 */     if (getGameRules().getBoolean("doDaylightCycle"))
/*     */     {
/* 110 */       setWorldTime(getWorldTime() + 1L);
/*     */     }
/*     */     
/* 113 */     this.theProfiler.startSection("reEntryProcessing");
/*     */     
/* 115 */     for (int i = 0; i < 10 && !this.entitySpawnQueue.isEmpty(); i++) {
/*     */       
/* 117 */       Entity entity = this.entitySpawnQueue.iterator().next();
/* 118 */       this.entitySpawnQueue.remove(entity);
/*     */       
/* 120 */       if (!this.loadedEntityList.contains(entity))
/*     */       {
/* 122 */         spawnEntityInWorld(entity);
/*     */       }
/*     */     } 
/*     */     
/* 126 */     this.theProfiler.endStartSection("chunkCache");
/* 127 */     this.clientChunkProvider.unloadQueuedChunks();
/* 128 */     this.theProfiler.endStartSection("blocks");
/* 129 */     updateBlocks();
/* 130 */     this.theProfiler.endSection();
/*     */ 
/*     */     
/* 133 */     WDLHooks.onWorldClientTick(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateBlockReceiveRegion(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IChunkProvider createChunkProvider() {
/* 150 */     this.clientChunkProvider = new ChunkProviderClient(this);
/* 151 */     return this.clientChunkProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
/* 156 */     return !(!allowEmpty && getChunkProvider().provideChunk(x, z).isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildChunkCoordList() {
/* 161 */     int i = MathHelper.floor(this.mc.player.posX / 16.0D);
/* 162 */     int j = MathHelper.floor(this.mc.player.posZ / 16.0D);
/*     */     
/* 164 */     if (i != this.playerChunkX || j != this.playerChunkY) {
/*     */       
/* 166 */       this.playerChunkX = i;
/* 167 */       this.playerChunkY = j;
/* 168 */       this.viewableChunks.clear();
/* 169 */       int k = this.mc.gameSettings.renderDistanceChunks;
/* 170 */       this.theProfiler.startSection("buildList");
/* 171 */       int l = MathHelper.floor(this.mc.player.posX / 16.0D);
/* 172 */       int i1 = MathHelper.floor(this.mc.player.posZ / 16.0D);
/*     */       
/* 174 */       for (int j1 = -k; j1 <= k; j1++) {
/*     */         
/* 176 */         for (int k1 = -k; k1 <= k; k1++)
/*     */         {
/* 178 */           this.viewableChunks.add(new ChunkPos(j1 + l, k1 + i1));
/*     */         }
/*     */       } 
/*     */       
/* 182 */       this.theProfiler.endSection();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateBlocks() {
/* 188 */     buildChunkCoordList();
/*     */     
/* 190 */     if (this.ambienceTicks > 0)
/*     */     {
/* 192 */       this.ambienceTicks--;
/*     */     }
/*     */     
/* 195 */     this.previousActiveChunkSet.retainAll(this.viewableChunks);
/*     */     
/* 197 */     if (this.previousActiveChunkSet.size() == this.viewableChunks.size())
/*     */     {
/* 199 */       this.previousActiveChunkSet.clear();
/*     */     }
/*     */     
/* 202 */     int i = 0;
/*     */     
/* 204 */     for (ChunkPos chunkpos : this.viewableChunks) {
/*     */       
/* 206 */       if (!this.previousActiveChunkSet.contains(chunkpos)) {
/*     */         
/* 208 */         int j = chunkpos.chunkXPos * 16;
/* 209 */         int k = chunkpos.chunkZPos * 16;
/* 210 */         this.theProfiler.startSection("getChunk");
/* 211 */         Chunk chunk = getChunkFromChunkCoords(chunkpos.chunkXPos, chunkpos.chunkZPos);
/* 212 */         playMoodSoundAndCheckLight(j, k, chunk);
/* 213 */         this.theProfiler.endSection();
/* 214 */         this.previousActiveChunkSet.add(chunkpos);
/* 215 */         i++;
/*     */         
/* 217 */         if (i >= 10) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doPreChunk(int chunkX, int chunkZ, boolean loadChunk) {
/* 229 */     WDLHooks.onWorldClientDoPreChunk(this, chunkX, chunkZ, loadChunk);
/*     */ 
/*     */     
/* 232 */     if (loadChunk) {
/*     */       
/* 234 */       this.clientChunkProvider.loadChunk(chunkX, chunkZ);
/*     */     }
/*     */     else {
/*     */       
/* 238 */       this.clientChunkProvider.unloadChunk(chunkX, chunkZ);
/* 239 */       markBlockRangeForRenderUpdate(chunkX * 16, 0, chunkZ * 16, chunkX * 16 + 15, 256, chunkZ * 16 + 15);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 248 */     boolean flag = super.spawnEntityInWorld(entityIn);
/* 249 */     this.entityList.add(entityIn);
/*     */     
/* 251 */     if (flag) {
/*     */       
/* 253 */       if (entityIn instanceof EntityMinecart)
/*     */       {
/* 255 */         this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecart((EntityMinecart)entityIn));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 260 */       this.entitySpawnQueue.add(entityIn);
/*     */     } 
/*     */     
/* 263 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entityIn) {
/* 271 */     super.removeEntity(entityIn);
/* 272 */     this.entityList.remove(entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityAdded(Entity entityIn) {
/* 277 */     super.onEntityAdded(entityIn);
/*     */     
/* 279 */     if (this.entitySpawnQueue.contains(entityIn))
/*     */     {
/* 281 */       this.entitySpawnQueue.remove(entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityRemoved(Entity entityIn) {
/* 287 */     super.onEntityRemoved(entityIn);
/*     */     
/* 289 */     if (this.entityList.contains(entityIn))
/*     */     {
/* 291 */       if (entityIn.isEntityAlive()) {
/*     */         
/* 293 */         this.entitySpawnQueue.add(entityIn);
/*     */       }
/*     */       else {
/*     */         
/* 297 */         this.entityList.remove(entityIn);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToWorld(int entityID, Entity entityToSpawn) {
/* 307 */     Entity entity = getEntityByID(entityID);
/*     */     
/* 309 */     if (entity != null)
/*     */     {
/* 311 */       removeEntity(entity);
/*     */     }
/*     */     
/* 314 */     this.entityList.add(entityToSpawn);
/* 315 */     entityToSpawn.setEntityId(entityID);
/*     */     
/* 317 */     if (!spawnEntityInWorld(entityToSpawn))
/*     */     {
/* 319 */       this.entitySpawnQueue.add(entityToSpawn);
/*     */     }
/*     */     
/* 322 */     this.entitiesById.addKey(entityID, entityToSpawn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntityByID(int id) {
/* 332 */     return (id == this.mc.player.getEntityId()) ? (Entity)this.mc.player : super.getEntityByID(id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity removeEntityFromWorld(int entityID) {
/* 338 */     WDLHooks.onWorldClientRemoveEntityFromWorld(this, entityID);
/*     */ 
/*     */     
/* 341 */     Entity entity = (Entity)this.entitiesById.removeObject(entityID);
/*     */     
/* 343 */     if (entity != null) {
/*     */       
/* 345 */       this.entityList.remove(entity);
/* 346 */       removeEntity(entity);
/*     */     } 
/*     */     
/* 349 */     return entity;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean invalidateRegionAndSetBlock(BlockPos pos, IBlockState state) {
/* 355 */     int i = pos.getX();
/* 356 */     int j = pos.getY();
/* 357 */     int k = pos.getZ();
/* 358 */     invalidateBlockReceiveRegion(i, j, k, i, j, k);
/* 359 */     return super.setBlockState(pos, state, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendQuittingDisconnectingPacket() {
/* 367 */     this.connection.getNetworkManager().closeChannel((ITextComponent)new TextComponentString("Quitting"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateWeather() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk chunkIn) {
/* 379 */     super.playMoodSoundAndCheckLight(p_147467_1_, p_147467_2_, chunkIn);
/*     */     
/* 381 */     if (this.ambienceTicks == 0) {
/*     */       
/* 383 */       EntityPlayerSP entityplayersp = this.mc.player;
/*     */       
/* 385 */       if (entityplayersp == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 390 */       if (Math.abs(entityplayersp.chunkCoordX - chunkIn.xPosition) > 1 || Math.abs(entityplayersp.chunkCoordZ - chunkIn.zPosition) > 1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 395 */       this.updateLCG = this.updateLCG * 3 + 1013904223;
/* 396 */       int i = this.updateLCG >> 2;
/* 397 */       int j = i & 0xF;
/* 398 */       int k = i >> 8 & 0xF;
/* 399 */       int l = i >> 16 & 0xFF;
/* 400 */       l /= 2;
/*     */       
/* 402 */       if (entityplayersp.posY > 160.0D) {
/*     */         
/* 404 */         l += 128;
/*     */       }
/* 406 */       else if (entityplayersp.posY > 96.0D) {
/*     */         
/* 408 */         l += 64;
/*     */       } 
/*     */       
/* 411 */       BlockPos blockpos = new BlockPos(j + p_147467_1_, l, k + p_147467_2_);
/* 412 */       IBlockState iblockstate = chunkIn.getBlockState(blockpos);
/* 413 */       j += p_147467_1_;
/* 414 */       k += p_147467_2_;
/* 415 */       double d0 = this.mc.player.getDistanceSq(j + 0.5D, l + 0.5D, k + 0.5D);
/*     */       
/* 417 */       if (d0 < 4.0D) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 422 */       if (d0 > 255.0D) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 427 */       if (iblockstate.getMaterial() == Material.AIR && getLight(blockpos) <= this.rand.nextInt(8) && getLightFor(EnumSkyBlock.SKY, blockpos) <= 0) {
/*     */         
/* 429 */         playSound(j + 0.5D, l + 0.5D, k + 0.5D, SoundEvents.AMBIENT_CAVE, SoundCategory.AMBIENT, 0.7F, 0.8F + this.rand.nextFloat() * 0.2F, false);
/* 430 */         this.ambienceTicks = this.rand.nextInt(12000) + 6000;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doVoidFogParticles(int posX, int posY, int posZ) {
/* 437 */     int i = 32;
/* 438 */     Random random = new Random();
/* 439 */     ItemStack itemstack = this.mc.player.getHeldItemMainhand();
/*     */     
/* 441 */     if (itemstack == null || Block.getBlockFromItem(itemstack.getItem()) != Blocks.BARRIER)
/*     */     {
/* 443 */       itemstack = this.mc.player.getHeldItemOffhand();
/*     */     }
/*     */     
/* 446 */     boolean flag = (this.mc.playerController.getCurrentGameType() == GameType.CREATIVE && !itemstack.func_190926_b() && itemstack.getItem() == Item.getItemFromBlock(Blocks.BARRIER));
/* 447 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 449 */     for (int j = 0; j < 667; j++) {
/*     */       
/* 451 */       showBarrierParticles(posX, posY, posZ, 16, random, flag, blockpos$mutableblockpos);
/* 452 */       showBarrierParticles(posX, posY, posZ, 32, random, flag, blockpos$mutableblockpos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void showBarrierParticles(int p_184153_1_, int p_184153_2_, int p_184153_3_, int p_184153_4_, Random random, boolean p_184153_6_, BlockPos.MutableBlockPos pos) {
/* 458 */     int i = p_184153_1_ + this.rand.nextInt(p_184153_4_) - this.rand.nextInt(p_184153_4_);
/* 459 */     int j = p_184153_2_ + this.rand.nextInt(p_184153_4_) - this.rand.nextInt(p_184153_4_);
/* 460 */     int k = p_184153_3_ + this.rand.nextInt(p_184153_4_) - this.rand.nextInt(p_184153_4_);
/* 461 */     pos.setPos(i, j, k);
/* 462 */     IBlockState iblockstate = getBlockState((BlockPos)pos);
/* 463 */     iblockstate.getBlock().randomDisplayTick(iblockstate, this, (BlockPos)pos, random);
/*     */     
/* 465 */     if (p_184153_6_ && iblockstate.getBlock() == Blocks.BARRIER)
/*     */     {
/* 467 */       spawnParticle(EnumParticleTypes.BARRIER, (i + 0.5F), (j + 0.5F), (k + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllEntities() {
/* 476 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*     */     
/* 478 */     for (int i = 0; i < this.unloadedEntityList.size(); i++) {
/*     */       
/* 480 */       Entity entity = this.unloadedEntityList.get(i);
/* 481 */       int j = entity.chunkCoordX;
/* 482 */       int k = entity.chunkCoordZ;
/*     */       
/* 484 */       if (entity.addedToChunk && isChunkLoaded(j, k, true))
/*     */       {
/* 486 */         getChunkFromChunkCoords(j, k).removeEntity(entity);
/*     */       }
/*     */     } 
/*     */     
/* 490 */     for (int i1 = 0; i1 < this.unloadedEntityList.size(); i1++)
/*     */     {
/* 492 */       onEntityRemoved(this.unloadedEntityList.get(i1));
/*     */     }
/*     */     
/* 495 */     this.unloadedEntityList.clear();
/*     */     
/* 497 */     for (int j1 = 0; j1 < this.loadedEntityList.size(); j1++) {
/*     */       
/* 499 */       Entity entity1 = this.loadedEntityList.get(j1);
/* 500 */       Entity entity2 = entity1.getRidingEntity();
/*     */       
/* 502 */       if (entity2 != null) {
/*     */         
/* 504 */         if (!entity2.isDead && entity2.isPassenger(entity1)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 509 */         entity1.dismountRidingEntity();
/*     */       } 
/*     */       
/* 512 */       if (entity1.isDead) {
/*     */         
/* 514 */         int k1 = entity1.chunkCoordX;
/* 515 */         int l = entity1.chunkCoordZ;
/*     */         
/* 517 */         if (entity1.addedToChunk && isChunkLoaded(k1, l, true))
/*     */         {
/* 519 */           getChunkFromChunkCoords(k1, l).removeEntity(entity1);
/*     */         }
/*     */         
/* 522 */         this.loadedEntityList.remove(j1--);
/* 523 */         onEntityRemoved(entity1);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 533 */     CrashReportCategory crashreportcategory = super.addWorldInfoToCrashReport(report);
/* 534 */     crashreportcategory.setDetail("Forced entities", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 538 */             return String.valueOf(WorldClient.this.entityList.size()) + " total; " + WorldClient.this.entityList;
/*     */           }
/*     */         });
/* 541 */     crashreportcategory.setDetail("Retry entities", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 545 */             return String.valueOf(WorldClient.this.entitySpawnQueue.size()) + " total; " + WorldClient.this.entitySpawnQueue;
/*     */           }
/*     */         });
/* 548 */     crashreportcategory.setDetail("Server brand", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 552 */             return WorldClient.this.mc.player.getServerBrand();
/*     */           }
/*     */         });
/* 555 */     crashreportcategory.setDetail("Server type", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 559 */             return (WorldClient.this.mc.getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
/*     */           }
/*     */         });
/* 562 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(@Nullable EntityPlayer player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
/* 567 */     if (player == this.mc.player)
/*     */     {
/* 569 */       playSound(x, y, z, soundIn, category, volume, pitch, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay) {
/* 575 */     playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, soundIn, category, volume, pitch, distanceDelay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay) {
/* 580 */     double d0 = this.mc.getRenderViewEntity().getDistanceSq(x, y, z);
/* 581 */     PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(soundIn, category, volume, pitch, (float)x, (float)y, (float)z);
/*     */     
/* 583 */     if (distanceDelay && d0 > 100.0D) {
/*     */       
/* 585 */       double d1 = Math.sqrt(d0) / 40.0D;
/* 586 */       this.mc.getSoundHandler().playDelayedSound((ISound)positionedsoundrecord, (int)(d1 * 20.0D));
/*     */     }
/*     */     else {
/*     */       
/* 590 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, @Nullable NBTTagCompound compund) {
/* 596 */     this.mc.effectRenderer.addEffect((Particle)new ParticleFirework.Starter(this, x, y, z, motionX, motionY, motionZ, this.mc.effectRenderer, compund));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacketToServer(Packet<?> packetIn) {
/* 601 */     this.connection.sendPacket(packetIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWorldScoreboard(Scoreboard scoreboardIn) {
/* 606 */     this.worldScoreboard = scoreboardIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {
/* 614 */     if (time < 0L) {
/*     */       
/* 616 */       time = -time;
/* 617 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*     */     }
/*     */     else {
/*     */       
/* 621 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
/*     */     } 
/*     */     
/* 624 */     super.setWorldTime(time);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkProviderClient getChunkProvider() {
/* 632 */     return (ChunkProviderClient)super.getChunkProvider();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/* 637 */     int i = super.getCombinedLight(pos, lightValue);
/*     */     
/* 639 */     if (Config.isDynamicLights())
/*     */     {
/* 641 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/* 644 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/* 654 */     this.playerUpdate = isPlayerActing();
/* 655 */     boolean flag = super.setBlockState(pos, newState, flags);
/* 656 */     this.playerUpdate = false;
/* 657 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPlayerActing() {
/* 662 */     if (this.mc.playerController instanceof PlayerControllerOF) {
/*     */       
/* 664 */       PlayerControllerOF playercontrollerof = (PlayerControllerOF)this.mc.playerController;
/* 665 */       return playercontrollerof.isActing();
/*     */     } 
/*     */ 
/*     */     
/* 669 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 675 */     return this.playerUpdate;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\WorldClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */