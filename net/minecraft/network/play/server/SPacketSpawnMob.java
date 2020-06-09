/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class SPacketSpawnMob
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public int entityId;
/*     */   public UUID uniqueId;
/*     */   public int type;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public int velocityX;
/*     */   public int velocityY;
/*     */   public int velocityZ;
/*     */   public byte yaw;
/*     */   public byte pitch;
/*     */   public byte headPitch;
/*     */   private EntityDataManager dataManager;
/*     */   public List<EntityDataManager.DataEntry<?>> dataManagerEntries;
/*     */   
/*     */   public SPacketSpawnMob() {}
/*     */   
/*     */   public SPacketSpawnMob(EntityLivingBase entityIn) {
/*  37 */     this.entityId = entityIn.getEntityId();
/*  38 */     this.uniqueId = entityIn.getUniqueID();
/*  39 */     this.type = EntityList.field_191308_b.getIDForObject(entityIn.getClass());
/*  40 */     this.x = entityIn.posX;
/*  41 */     this.y = entityIn.posY;
/*  42 */     this.z = entityIn.posZ;
/*  43 */     this.yaw = (byte)(int)(entityIn.rotationYaw * 256.0F / 360.0F);
/*  44 */     this.pitch = (byte)(int)(entityIn.rotationPitch * 256.0F / 360.0F);
/*  45 */     this.headPitch = (byte)(int)(entityIn.rotationYawHead * 256.0F / 360.0F);
/*  46 */     double d0 = 3.9D;
/*  47 */     double d1 = entityIn.motionX;
/*  48 */     double d2 = entityIn.motionY;
/*  49 */     double d3 = entityIn.motionZ;
/*     */     
/*  51 */     if (d1 < -3.9D)
/*     */     {
/*  53 */       d1 = -3.9D;
/*     */     }
/*     */     
/*  56 */     if (d2 < -3.9D)
/*     */     {
/*  58 */       d2 = -3.9D;
/*     */     }
/*     */     
/*  61 */     if (d3 < -3.9D)
/*     */     {
/*  63 */       d3 = -3.9D;
/*     */     }
/*     */     
/*  66 */     if (d1 > 3.9D)
/*     */     {
/*  68 */       d1 = 3.9D;
/*     */     }
/*     */     
/*  71 */     if (d2 > 3.9D)
/*     */     {
/*  73 */       d2 = 3.9D;
/*     */     }
/*     */     
/*  76 */     if (d3 > 3.9D)
/*     */     {
/*  78 */       d3 = 3.9D;
/*     */     }
/*     */     
/*  81 */     this.velocityX = (int)(d1 * 8000.0D);
/*  82 */     this.velocityY = (int)(d2 * 8000.0D);
/*  83 */     this.velocityZ = (int)(d3 * 8000.0D);
/*  84 */     this.dataManager = entityIn.getDataManager();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  92 */     this.entityId = buf.readVarIntFromBuffer();
/*  93 */     this.uniqueId = buf.readUuid();
/*  94 */     this.type = buf.readVarIntFromBuffer();
/*  95 */     this.x = buf.readDouble();
/*  96 */     this.y = buf.readDouble();
/*  97 */     this.z = buf.readDouble();
/*  98 */     this.yaw = buf.readByte();
/*  99 */     this.pitch = buf.readByte();
/* 100 */     this.headPitch = buf.readByte();
/* 101 */     this.velocityX = buf.readShort();
/* 102 */     this.velocityY = buf.readShort();
/* 103 */     this.velocityZ = buf.readShort();
/* 104 */     this.dataManagerEntries = EntityDataManager.readEntries(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 112 */     buf.writeVarIntToBuffer(this.entityId);
/* 113 */     buf.writeUuid(this.uniqueId);
/* 114 */     buf.writeVarIntToBuffer(this.type);
/* 115 */     buf.writeDouble(this.x);
/* 116 */     buf.writeDouble(this.y);
/* 117 */     buf.writeDouble(this.z);
/* 118 */     buf.writeByte(this.yaw);
/* 119 */     buf.writeByte(this.pitch);
/* 120 */     buf.writeByte(this.headPitch);
/* 121 */     buf.writeShort(this.velocityX);
/* 122 */     buf.writeShort(this.velocityY);
/* 123 */     buf.writeShort(this.velocityZ);
/* 124 */     this.dataManager.writeEntries(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 132 */     handler.handleSpawnMob(this);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<EntityDataManager.DataEntry<?>> getDataManagerEntries() {
/* 138 */     return this.dataManagerEntries;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/* 143 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 148 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityType() {
/* 153 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 158 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 163 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 168 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVelocityX() {
/* 173 */     return this.velocityX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVelocityY() {
/* 178 */     return this.velocityY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVelocityZ() {
/* 183 */     return this.velocityZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/* 188 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/* 193 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getHeadPitch() {
/* 198 */     return this.headPitch;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */