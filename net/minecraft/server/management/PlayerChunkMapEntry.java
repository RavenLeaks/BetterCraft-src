/*     */ package net.minecraft.server.management;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketChunkData;
/*     */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketUnloadChunk;
/*     */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PlayerChunkMapEntry {
/*  24 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final PlayerChunkMap playerChunkMap;
/*  26 */   private final List<EntityPlayerMP> players = Lists.newArrayList();
/*     */   private final ChunkPos pos;
/*  28 */   private final short[] changedBlocks = new short[64];
/*     */   
/*     */   @Nullable
/*     */   private Chunk chunk;
/*     */   private int changes;
/*     */   private int changedSectionFilter;
/*     */   private long lastUpdateInhabitedTime;
/*     */   private boolean sentToPlayers;
/*     */   
/*     */   public PlayerChunkMapEntry(PlayerChunkMap mapIn, int chunkX, int chunkZ) {
/*  38 */     this.playerChunkMap = mapIn;
/*  39 */     this.pos = new ChunkPos(chunkX, chunkZ);
/*  40 */     this.chunk = mapIn.getWorldServer().getChunkProvider().loadChunk(chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkPos getPos() {
/*  45 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPlayer(EntityPlayerMP player) {
/*  50 */     if (this.players.contains(player)) {
/*     */       
/*  52 */       LOGGER.debug("Failed to add player. {} already is in chunk {}, {}", player, Integer.valueOf(this.pos.chunkXPos), Integer.valueOf(this.pos.chunkZPos));
/*     */     }
/*     */     else {
/*     */       
/*  56 */       if (this.players.isEmpty())
/*     */       {
/*  58 */         this.lastUpdateInhabitedTime = this.playerChunkMap.getWorldServer().getTotalWorldTime();
/*     */       }
/*     */       
/*  61 */       this.players.add(player);
/*     */       
/*  63 */       if (this.sentToPlayers)
/*     */       {
/*  65 */         sendNearbySpecialEntities(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayer(EntityPlayerMP player) {
/*  72 */     if (this.players.contains(player)) {
/*     */       
/*  74 */       if (this.sentToPlayers)
/*     */       {
/*  76 */         player.connection.sendPacket((Packet)new SPacketUnloadChunk(this.pos.chunkXPos, this.pos.chunkZPos));
/*     */       }
/*     */       
/*  79 */       this.players.remove(player);
/*     */       
/*  81 */       if (this.players.isEmpty())
/*     */       {
/*  83 */         this.playerChunkMap.removeEntry(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean providePlayerChunk(boolean canGenerate) {
/*  94 */     if (this.chunk != null)
/*     */     {
/*  96 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 100 */     if (canGenerate) {
/*     */       
/* 102 */       this.chunk = this.playerChunkMap.getWorldServer().getChunkProvider().provideChunk(this.pos.chunkXPos, this.pos.chunkZPos);
/*     */     }
/*     */     else {
/*     */       
/* 106 */       this.chunk = this.playerChunkMap.getWorldServer().getChunkProvider().loadChunk(this.pos.chunkXPos, this.pos.chunkZPos);
/*     */     } 
/*     */     
/* 109 */     return (this.chunk != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendToPlayers() {
/* 115 */     if (this.sentToPlayers)
/*     */     {
/* 117 */       return true;
/*     */     }
/* 119 */     if (this.chunk == null)
/*     */     {
/* 121 */       return false;
/*     */     }
/* 123 */     if (!this.chunk.isPopulated())
/*     */     {
/* 125 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 129 */     this.changes = 0;
/* 130 */     this.changedSectionFilter = 0;
/* 131 */     this.sentToPlayers = true;
/* 132 */     SPacketChunkData sPacketChunkData = new SPacketChunkData(this.chunk, 65535);
/*     */     
/* 134 */     for (EntityPlayerMP entityplayermp : this.players) {
/*     */       
/* 136 */       entityplayermp.connection.sendPacket((Packet)sPacketChunkData);
/* 137 */       this.playerChunkMap.getWorldServer().getEntityTracker().sendLeashedEntitiesInChunk(entityplayermp, this.chunk);
/*     */     } 
/*     */     
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendNearbySpecialEntities(EntityPlayerMP player) {
/* 152 */     if (this.sentToPlayers) {
/*     */       
/* 154 */       player.connection.sendPacket((Packet)new SPacketChunkData(this.chunk, 65535));
/* 155 */       this.playerChunkMap.getWorldServer().getEntityTracker().sendLeashedEntitiesInChunk(player, this.chunk);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChunkInhabitedTime() {
/* 161 */     long i = this.playerChunkMap.getWorldServer().getTotalWorldTime();
/*     */     
/* 163 */     if (this.chunk != null)
/*     */     {
/* 165 */       this.chunk.setInhabitedTime(this.chunk.getInhabitedTime() + i - this.lastUpdateInhabitedTime);
/*     */     }
/*     */     
/* 168 */     this.lastUpdateInhabitedTime = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void blockChanged(int x, int y, int z) {
/* 173 */     if (this.sentToPlayers) {
/*     */       
/* 175 */       if (this.changes == 0)
/*     */       {
/* 177 */         this.playerChunkMap.addEntry(this);
/*     */       }
/*     */       
/* 180 */       this.changedSectionFilter |= 1 << y >> 4;
/*     */       
/* 182 */       if (this.changes < 64) {
/*     */         
/* 184 */         short short1 = (short)(x << 12 | z << 8 | y);
/*     */         
/* 186 */         for (int i = 0; i < this.changes; i++) {
/*     */           
/* 188 */           if (this.changedBlocks[i] == short1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 194 */         this.changedBlocks[this.changes++] = short1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet<?> packetIn) {
/* 201 */     if (this.sentToPlayers)
/*     */     {
/* 203 */       for (int i = 0; i < this.players.size(); i++)
/*     */       {
/* 205 */         ((EntityPlayerMP)this.players.get(i)).connection.sendPacket(packetIn);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 212 */     if (this.sentToPlayers && this.chunk != null)
/*     */     {
/* 214 */       if (this.changes != 0) {
/*     */         
/* 216 */         if (this.changes == 1) {
/*     */           
/* 218 */           int i = (this.changedBlocks[0] >> 12 & 0xF) + this.pos.chunkXPos * 16;
/* 219 */           int j = this.changedBlocks[0] & 0xFF;
/* 220 */           int k = (this.changedBlocks[0] >> 8 & 0xF) + this.pos.chunkZPos * 16;
/* 221 */           BlockPos blockpos = new BlockPos(i, j, k);
/* 222 */           sendPacket((Packet<?>)new SPacketBlockChange((World)this.playerChunkMap.getWorldServer(), blockpos));
/*     */           
/* 224 */           if (this.playerChunkMap.getWorldServer().getBlockState(blockpos).getBlock().hasTileEntity())
/*     */           {
/* 226 */             sendBlockEntity(this.playerChunkMap.getWorldServer().getTileEntity(blockpos));
/*     */           }
/*     */         }
/* 229 */         else if (this.changes == 64) {
/*     */           
/* 231 */           sendPacket((Packet<?>)new SPacketChunkData(this.chunk, this.changedSectionFilter));
/*     */         }
/*     */         else {
/*     */           
/* 235 */           sendPacket((Packet<?>)new SPacketMultiBlockChange(this.changes, this.changedBlocks, this.chunk));
/*     */           
/* 237 */           for (int l = 0; l < this.changes; l++) {
/*     */             
/* 239 */             int i1 = (this.changedBlocks[l] >> 12 & 0xF) + this.pos.chunkXPos * 16;
/* 240 */             int j1 = this.changedBlocks[l] & 0xFF;
/* 241 */             int k1 = (this.changedBlocks[l] >> 8 & 0xF) + this.pos.chunkZPos * 16;
/* 242 */             BlockPos blockpos1 = new BlockPos(i1, j1, k1);
/*     */             
/* 244 */             if (this.playerChunkMap.getWorldServer().getBlockState(blockpos1).getBlock().hasTileEntity())
/*     */             {
/* 246 */               sendBlockEntity(this.playerChunkMap.getWorldServer().getTileEntity(blockpos1));
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 251 */         this.changes = 0;
/* 252 */         this.changedSectionFilter = 0;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendBlockEntity(@Nullable TileEntity be) {
/* 259 */     if (be != null) {
/*     */       
/* 261 */       SPacketUpdateTileEntity spacketupdatetileentity = be.getUpdatePacket();
/*     */       
/* 263 */       if (spacketupdatetileentity != null)
/*     */       {
/* 265 */         sendPacket((Packet<?>)spacketupdatetileentity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsPlayer(EntityPlayerMP player) {
/* 272 */     return this.players.contains(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayerMatching(Predicate<EntityPlayerMP> predicate) {
/* 277 */     return Iterables.tryFind(this.players, predicate).isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlayerMatchingInRange(double range, Predicate<EntityPlayerMP> predicate) {
/* 282 */     int i = 0;
/*     */     
/* 284 */     for (int j = this.players.size(); i < j; i++) {
/*     */       
/* 286 */       EntityPlayerMP entityplayermp = this.players.get(i);
/*     */       
/* 288 */       if (predicate.apply(entityplayermp) && this.pos.getDistanceSq((Entity)entityplayermp) < range * range)
/*     */       {
/* 290 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 294 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSentToPlayers() {
/* 299 */     return this.sentToPlayers;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Chunk getChunk() {
/* 305 */     return this.chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getClosestPlayerDistance() {
/* 310 */     double d0 = Double.MAX_VALUE;
/*     */     
/* 312 */     for (EntityPlayerMP entityplayermp : this.players) {
/*     */       
/* 314 */       double d1 = this.pos.getDistanceSq((Entity)entityplayermp);
/*     */       
/* 316 */       if (d1 < d0)
/*     */       {
/* 318 */         d0 = d1;
/*     */       }
/*     */     } 
/*     */     
/* 322 */     return d0;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\PlayerChunkMapEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */