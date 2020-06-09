/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class SPacketSpawnPlayer
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public int entityId;
/*     */   public UUID uniqueId;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public byte yaw;
/*     */   public byte pitch;
/*     */   private EntityDataManager watcher;
/*     */   public List<EntityDataManager.DataEntry<?>> dataManagerEntries;
/*     */   
/*     */   public SPacketSpawnPlayer() {}
/*     */   
/*     */   public SPacketSpawnPlayer(EntityPlayer player) {
/*  31 */     this.entityId = player.getEntityId();
/*  32 */     this.uniqueId = player.getGameProfile().getId();
/*  33 */     this.x = player.posX;
/*  34 */     this.y = player.posY;
/*  35 */     this.z = player.posZ;
/*  36 */     this.yaw = (byte)(int)(player.rotationYaw * 256.0F / 360.0F);
/*  37 */     this.pitch = (byte)(int)(player.rotationPitch * 256.0F / 360.0F);
/*  38 */     this.watcher = player.getDataManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  46 */     this.entityId = buf.readVarIntFromBuffer();
/*  47 */     this.uniqueId = buf.readUuid();
/*  48 */     this.x = buf.readDouble();
/*  49 */     this.y = buf.readDouble();
/*  50 */     this.z = buf.readDouble();
/*  51 */     this.yaw = buf.readByte();
/*  52 */     this.pitch = buf.readByte();
/*  53 */     this.dataManagerEntries = EntityDataManager.readEntries(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  61 */     buf.writeVarIntToBuffer(this.entityId);
/*  62 */     buf.writeUuid(this.uniqueId);
/*  63 */     buf.writeDouble(this.x);
/*  64 */     buf.writeDouble(this.y);
/*  65 */     buf.writeDouble(this.z);
/*  66 */     buf.writeByte(this.yaw);
/*  67 */     buf.writeByte(this.pitch);
/*  68 */     this.watcher.writeEntries(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  76 */     handler.handleSpawnPlayer(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<EntityDataManager.DataEntry<?>> getDataManagerEntries() {
/*  82 */     return this.dataManagerEntries;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/*  87 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/*  92 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  97 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 102 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 107 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/* 112 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/* 117 */     return this.pitch;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */