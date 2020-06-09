/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class PlayerChunkMap
/*     */ {
/*  26 */   private static final Predicate<EntityPlayerMP> NOT_SPECTATOR = new Predicate<EntityPlayerMP>()
/*     */     {
/*     */       public boolean apply(@Nullable EntityPlayerMP p_apply_1_)
/*     */       {
/*  30 */         return (p_apply_1_ != null && !p_apply_1_.isSpectator());
/*     */       }
/*     */     };
/*  33 */   private static final Predicate<EntityPlayerMP> CAN_GENERATE_CHUNKS = new Predicate<EntityPlayerMP>()
/*     */     {
/*     */       public boolean apply(@Nullable EntityPlayerMP p_apply_1_)
/*     */       {
/*  37 */         return (p_apply_1_ != null && (!p_apply_1_.isSpectator() || p_apply_1_.getServerWorld().getGameRules().getBoolean("spectatorsGenerateChunks")));
/*     */       }
/*     */     };
/*     */   private final WorldServer theWorldServer;
/*  41 */   private final List<EntityPlayerMP> players = Lists.newArrayList();
/*  42 */   private final Long2ObjectMap<PlayerChunkMapEntry> playerInstances = (Long2ObjectMap<PlayerChunkMapEntry>)new Long2ObjectOpenHashMap(4096);
/*  43 */   private final Set<PlayerChunkMapEntry> playerInstancesToUpdate = Sets.newHashSet();
/*  44 */   private final List<PlayerChunkMapEntry> pendingSendToPlayers = Lists.newLinkedList();
/*  45 */   private final List<PlayerChunkMapEntry> playersNeedingChunks = Lists.newLinkedList();
/*  46 */   private final List<PlayerChunkMapEntry> playerInstanceList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private int playerViewRadius;
/*     */ 
/*     */   
/*     */   private long previousTotalWorldTime;
/*     */   
/*     */   private boolean sortMissingChunks = true;
/*     */   
/*     */   private boolean sortSendToPlayers = true;
/*     */ 
/*     */   
/*     */   public PlayerChunkMap(WorldServer serverWorld) {
/*  60 */     this.theWorldServer = serverWorld;
/*  61 */     setPlayerViewRadius(serverWorld.getMinecraftServer().getPlayerList().getViewDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldServer getWorldServer() {
/*  69 */     return this.theWorldServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Chunk> getChunkIterator() {
/*  74 */     final Iterator<PlayerChunkMapEntry> iterator = this.playerInstanceList.iterator();
/*  75 */     return (Iterator<Chunk>)new AbstractIterator<Chunk>()
/*     */       {
/*     */ 
/*     */         
/*     */         protected Chunk computeNext()
/*     */         {
/*  81 */           while (iterator.hasNext()) {
/*     */             
/*  83 */             PlayerChunkMapEntry playerchunkmapentry = iterator.next();
/*  84 */             Chunk chunk = playerchunkmapentry.getChunk();
/*     */             
/*  86 */             if (chunk == null) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/*  91 */             if (!chunk.isLightPopulated() && chunk.isTerrainPopulated())
/*     */             {
/*  93 */               return chunk;
/*     */             }
/*     */             
/*  96 */             if (!chunk.isChunkTicked())
/*     */             {
/*  98 */               return chunk;
/*     */             }
/*     */             
/* 101 */             if (!playerchunkmapentry.hasPlayerMatchingInRange(128.0D, PlayerChunkMap.NOT_SPECTATOR)) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/* 106 */             return chunk;
/*     */           } 
/*     */           
/* 109 */           return (Chunk)endOfData();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 120 */     long i = this.theWorldServer.getTotalWorldTime();
/*     */     
/* 122 */     if (i - this.previousTotalWorldTime > 8000L) {
/*     */       
/* 124 */       this.previousTotalWorldTime = i;
/*     */       
/* 126 */       for (int j = 0; j < this.playerInstanceList.size(); j++) {
/*     */         
/* 128 */         PlayerChunkMapEntry playerchunkmapentry = this.playerInstanceList.get(j);
/* 129 */         playerchunkmapentry.update();
/* 130 */         playerchunkmapentry.updateChunkInhabitedTime();
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     if (!this.playerInstancesToUpdate.isEmpty()) {
/*     */       
/* 136 */       for (PlayerChunkMapEntry playerchunkmapentry2 : this.playerInstancesToUpdate)
/*     */       {
/* 138 */         playerchunkmapentry2.update();
/*     */       }
/*     */       
/* 141 */       this.playerInstancesToUpdate.clear();
/*     */     } 
/*     */     
/* 144 */     if (this.sortMissingChunks && i % 4L == 0L) {
/*     */       
/* 146 */       this.sortMissingChunks = false;
/* 147 */       Collections.sort(this.playersNeedingChunks, new Comparator<PlayerChunkMapEntry>()
/*     */           {
/*     */             public int compare(PlayerChunkMapEntry p_compare_1_, PlayerChunkMapEntry p_compare_2_)
/*     */             {
/* 151 */               return ComparisonChain.start().compare(p_compare_1_.getClosestPlayerDistance(), p_compare_2_.getClosestPlayerDistance()).result();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 156 */     if (this.sortSendToPlayers && i % 4L == 2L) {
/*     */       
/* 158 */       this.sortSendToPlayers = false;
/* 159 */       Collections.sort(this.pendingSendToPlayers, new Comparator<PlayerChunkMapEntry>()
/*     */           {
/*     */             public int compare(PlayerChunkMapEntry p_compare_1_, PlayerChunkMapEntry p_compare_2_)
/*     */             {
/* 163 */               return ComparisonChain.start().compare(p_compare_1_.getClosestPlayerDistance(), p_compare_2_.getClosestPlayerDistance()).result();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 168 */     if (!this.playersNeedingChunks.isEmpty()) {
/*     */       
/* 170 */       long l = System.nanoTime() + 50000000L;
/* 171 */       int k = 49;
/* 172 */       Iterator<PlayerChunkMapEntry> iterator = this.playersNeedingChunks.iterator();
/*     */       
/* 174 */       while (iterator.hasNext()) {
/*     */         
/* 176 */         PlayerChunkMapEntry playerchunkmapentry1 = iterator.next();
/*     */         
/* 178 */         if (playerchunkmapentry1.getChunk() == null) {
/*     */           
/* 180 */           boolean flag = playerchunkmapentry1.hasPlayerMatching(CAN_GENERATE_CHUNKS);
/*     */           
/* 182 */           if (playerchunkmapentry1.providePlayerChunk(flag)) {
/*     */             
/* 184 */             iterator.remove();
/*     */             
/* 186 */             if (playerchunkmapentry1.sendToPlayers())
/*     */             {
/* 188 */               this.pendingSendToPlayers.remove(playerchunkmapentry1);
/*     */             }
/*     */             
/* 191 */             k--;
/*     */             
/* 193 */             if (k < 0 || System.nanoTime() > l) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 202 */     if (!this.pendingSendToPlayers.isEmpty()) {
/*     */       
/* 204 */       int i1 = 81;
/* 205 */       Iterator<PlayerChunkMapEntry> iterator1 = this.pendingSendToPlayers.iterator();
/*     */       
/* 207 */       while (iterator1.hasNext()) {
/*     */         
/* 209 */         PlayerChunkMapEntry playerchunkmapentry3 = iterator1.next();
/*     */         
/* 211 */         if (playerchunkmapentry3.sendToPlayers()) {
/*     */           
/* 213 */           iterator1.remove();
/* 214 */           i1--;
/*     */           
/* 216 */           if (i1 < 0) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if (this.players.isEmpty()) {
/*     */       
/* 226 */       WorldProvider worldprovider = this.theWorldServer.provider;
/*     */       
/* 228 */       if (!worldprovider.canRespawnHere())
/*     */       {
/* 230 */         this.theWorldServer.getChunkProvider().unloadAllChunks();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int chunkX, int chunkZ) {
/* 237 */     long i = getIndex(chunkX, chunkZ);
/* 238 */     return (this.playerInstances.get(i) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PlayerChunkMapEntry getEntry(int x, int z) {
/* 244 */     return (PlayerChunkMapEntry)this.playerInstances.get(getIndex(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   private PlayerChunkMapEntry getOrCreateEntry(int chunkX, int chunkZ) {
/* 249 */     long i = getIndex(chunkX, chunkZ);
/* 250 */     PlayerChunkMapEntry playerchunkmapentry = (PlayerChunkMapEntry)this.playerInstances.get(i);
/*     */     
/* 252 */     if (playerchunkmapentry == null) {
/*     */       
/* 254 */       playerchunkmapentry = new PlayerChunkMapEntry(this, chunkX, chunkZ);
/* 255 */       this.playerInstances.put(i, playerchunkmapentry);
/* 256 */       this.playerInstanceList.add(playerchunkmapentry);
/*     */       
/* 258 */       if (playerchunkmapentry.getChunk() == null)
/*     */       {
/* 260 */         this.playersNeedingChunks.add(playerchunkmapentry);
/*     */       }
/*     */       
/* 263 */       if (!playerchunkmapentry.sendToPlayers())
/*     */       {
/* 265 */         this.pendingSendToPlayers.add(playerchunkmapentry);
/*     */       }
/*     */     } 
/*     */     
/* 269 */     return playerchunkmapentry;
/*     */   }
/*     */ 
/*     */   
/*     */   public void markBlockForUpdate(BlockPos pos) {
/* 274 */     int i = pos.getX() >> 4;
/* 275 */     int j = pos.getZ() >> 4;
/* 276 */     PlayerChunkMapEntry playerchunkmapentry = getEntry(i, j);
/*     */     
/* 278 */     if (playerchunkmapentry != null)
/*     */     {
/* 280 */       playerchunkmapentry.blockChanged(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(EntityPlayerMP player) {
/* 289 */     int i = (int)player.posX >> 4;
/* 290 */     int j = (int)player.posZ >> 4;
/* 291 */     player.managedPosX = player.posX;
/* 292 */     player.managedPosZ = player.posZ;
/*     */     
/* 294 */     for (int k = i - this.playerViewRadius; k <= i + this.playerViewRadius; k++) {
/*     */       
/* 296 */       for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++)
/*     */       {
/* 298 */         getOrCreateEntry(k, l).addPlayer(player);
/*     */       }
/*     */     } 
/*     */     
/* 302 */     this.players.add(player);
/* 303 */     markSortPending();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(EntityPlayerMP player) {
/* 311 */     int i = (int)player.managedPosX >> 4;
/* 312 */     int j = (int)player.managedPosZ >> 4;
/*     */     
/* 314 */     for (int k = i - this.playerViewRadius; k <= i + this.playerViewRadius; k++) {
/*     */       
/* 316 */       for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/*     */         
/* 318 */         PlayerChunkMapEntry playerchunkmapentry = getEntry(k, l);
/*     */         
/* 320 */         if (playerchunkmapentry != null)
/*     */         {
/* 322 */           playerchunkmapentry.removePlayer(player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     this.players.remove(player);
/* 328 */     markSortPending();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean overlaps(int x1, int z1, int x2, int z2, int radius) {
/* 337 */     int i = x1 - x2;
/* 338 */     int j = z1 - z2;
/*     */     
/* 340 */     if (i >= -radius && i <= radius)
/*     */     {
/* 342 */       return (j >= -radius && j <= radius);
/*     */     }
/*     */ 
/*     */     
/* 346 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMovingPlayer(EntityPlayerMP player) {
/* 355 */     int i = (int)player.posX >> 4;
/* 356 */     int j = (int)player.posZ >> 4;
/* 357 */     double d0 = player.managedPosX - player.posX;
/* 358 */     double d1 = player.managedPosZ - player.posZ;
/* 359 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 361 */     if (d2 >= 64.0D) {
/*     */       
/* 363 */       int k = (int)player.managedPosX >> 4;
/* 364 */       int l = (int)player.managedPosZ >> 4;
/* 365 */       int i1 = this.playerViewRadius;
/* 366 */       int j1 = i - k;
/* 367 */       int k1 = j - l;
/*     */       
/* 369 */       if (j1 != 0 || k1 != 0) {
/*     */         
/* 371 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/*     */           
/* 373 */           for (int i2 = j - i1; i2 <= j + i1; i2++) {
/*     */             
/* 375 */             if (!overlaps(l1, i2, k, l, i1))
/*     */             {
/* 377 */               getOrCreateEntry(l1, i2).addPlayer(player);
/*     */             }
/*     */             
/* 380 */             if (!overlaps(l1 - j1, i2 - k1, i, j, i1)) {
/*     */               
/* 382 */               PlayerChunkMapEntry playerchunkmapentry = getEntry(l1 - j1, i2 - k1);
/*     */               
/* 384 */               if (playerchunkmapentry != null)
/*     */               {
/* 386 */                 playerchunkmapentry.removePlayer(player);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 392 */         player.managedPosX = player.posX;
/* 393 */         player.managedPosZ = player.posZ;
/* 394 */         markSortPending();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerWatchingChunk(EntityPlayerMP player, int chunkX, int chunkZ) {
/* 401 */     PlayerChunkMapEntry playerchunkmapentry = getEntry(chunkX, chunkZ);
/* 402 */     return (playerchunkmapentry != null && playerchunkmapentry.containsPlayer(player) && playerchunkmapentry.isSentToPlayers());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerViewRadius(int radius) {
/* 407 */     radius = MathHelper.clamp(radius, 3, 32);
/*     */     
/* 409 */     if (radius != this.playerViewRadius) {
/*     */       
/* 411 */       int i = radius - this.playerViewRadius;
/*     */       
/* 413 */       for (EntityPlayerMP entityplayermp : Lists.newArrayList(this.players)) {
/*     */         
/* 415 */         int j = (int)entityplayermp.posX >> 4;
/* 416 */         int k = (int)entityplayermp.posZ >> 4;
/*     */         
/* 418 */         if (i > 0) {
/*     */           
/* 420 */           for (int j1 = j - radius; j1 <= j + radius; j1++) {
/*     */             
/* 422 */             for (int k1 = k - radius; k1 <= k + radius; k1++) {
/*     */               
/* 424 */               PlayerChunkMapEntry playerchunkmapentry = getOrCreateEntry(j1, k1);
/*     */               
/* 426 */               if (!playerchunkmapentry.containsPlayer(entityplayermp))
/*     */               {
/* 428 */                 playerchunkmapentry.addPlayer(entityplayermp);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 435 */         for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/*     */           
/* 437 */           for (int i1 = k - this.playerViewRadius; i1 <= k + this.playerViewRadius; i1++) {
/*     */             
/* 439 */             if (!overlaps(l, i1, j, k, radius))
/*     */             {
/* 441 */               getOrCreateEntry(l, i1).removePlayer(entityplayermp);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 448 */       this.playerViewRadius = radius;
/* 449 */       markSortPending();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void markSortPending() {
/* 455 */     this.sortMissingChunks = true;
/* 456 */     this.sortSendToPlayers = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFurthestViewableBlock(int distance) {
/* 464 */     return distance * 16 - 16;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long getIndex(int p_187307_0_, int p_187307_1_) {
/* 469 */     return p_187307_0_ + 2147483647L | p_187307_1_ + 2147483647L << 32L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEntry(PlayerChunkMapEntry entry) {
/* 474 */     this.playerInstancesToUpdate.add(entry);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntry(PlayerChunkMapEntry entry) {
/* 479 */     ChunkPos chunkpos = entry.getPos();
/* 480 */     long i = getIndex(chunkpos.chunkXPos, chunkpos.chunkZPos);
/* 481 */     entry.updateChunkInhabitedTime();
/* 482 */     this.playerInstances.remove(i);
/* 483 */     this.playerInstanceList.remove(entry);
/* 484 */     this.playerInstancesToUpdate.remove(entry);
/* 485 */     this.pendingSendToPlayers.remove(entry);
/* 486 */     this.playersNeedingChunks.remove(entry);
/* 487 */     Chunk chunk = entry.getChunk();
/*     */     
/* 489 */     if (chunk != null)
/*     */     {
/* 491 */       getWorldServer().getChunkProvider().unload(chunk);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\PlayerChunkMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */