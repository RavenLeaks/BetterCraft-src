/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ public class SPacketSpawnObject
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public int entityId;
/*     */   public UUID uniqueId;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public int speedX;
/*     */   public int speedY;
/*     */   public int speedZ;
/*     */   public int pitch;
/*     */   public int yaw;
/*     */   public int type;
/*     */   public int data;
/*     */   
/*     */   public SPacketSpawnObject() {}
/*     */   
/*     */   public SPacketSpawnObject(Entity entityIn, int typeIn) {
/*  33 */     this(entityIn, typeIn, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketSpawnObject(Entity entityIn, int typeIn, int dataIn) {
/*  38 */     this.entityId = entityIn.getEntityId();
/*  39 */     this.uniqueId = entityIn.getUniqueID();
/*  40 */     this.x = entityIn.posX;
/*  41 */     this.y = entityIn.posY;
/*  42 */     this.z = entityIn.posZ;
/*  43 */     this.pitch = MathHelper.floor(entityIn.rotationPitch * 256.0F / 360.0F);
/*  44 */     this.yaw = MathHelper.floor(entityIn.rotationYaw * 256.0F / 360.0F);
/*  45 */     this.type = typeIn;
/*  46 */     this.data = dataIn;
/*  47 */     double d0 = 3.9D;
/*  48 */     this.speedX = (int)(MathHelper.clamp(entityIn.motionX, -3.9D, 3.9D) * 8000.0D);
/*  49 */     this.speedY = (int)(MathHelper.clamp(entityIn.motionY, -3.9D, 3.9D) * 8000.0D);
/*  50 */     this.speedZ = (int)(MathHelper.clamp(entityIn.motionZ, -3.9D, 3.9D) * 8000.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketSpawnObject(Entity entityIn, int typeIn, int dataIn, BlockPos pos) {
/*  55 */     this(entityIn, typeIn, dataIn);
/*  56 */     this.x = pos.getX();
/*  57 */     this.y = pos.getY();
/*  58 */     this.z = pos.getZ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  66 */     this.entityId = buf.readVarIntFromBuffer();
/*  67 */     this.uniqueId = buf.readUuid();
/*  68 */     this.type = buf.readByte();
/*  69 */     this.x = buf.readDouble();
/*  70 */     this.y = buf.readDouble();
/*  71 */     this.z = buf.readDouble();
/*  72 */     this.pitch = buf.readByte();
/*  73 */     this.yaw = buf.readByte();
/*  74 */     this.data = buf.readInt();
/*  75 */     this.speedX = buf.readShort();
/*  76 */     this.speedY = buf.readShort();
/*  77 */     this.speedZ = buf.readShort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  85 */     buf.writeVarIntToBuffer(this.entityId);
/*  86 */     buf.writeUuid(this.uniqueId);
/*  87 */     buf.writeByte(this.type);
/*  88 */     buf.writeDouble(this.x);
/*  89 */     buf.writeDouble(this.y);
/*  90 */     buf.writeDouble(this.z);
/*  91 */     buf.writeByte(this.pitch);
/*  92 */     buf.writeByte(this.yaw);
/*  93 */     buf.writeInt(this.data);
/*  94 */     buf.writeShort(this.speedX);
/*  95 */     buf.writeShort(this.speedY);
/*  96 */     buf.writeShort(this.speedZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 104 */     handler.handleSpawnObject(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/* 109 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 114 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/* 119 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/* 124 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 129 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeedX() {
/* 134 */     return this.speedX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeedY() {
/* 139 */     return this.speedY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSpeedZ() {
/* 144 */     return this.speedZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPitch() {
/* 149 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYaw() {
/* 154 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/* 159 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getData() {
/* 164 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeedX(int newSpeedX) {
/* 169 */     this.speedX = newSpeedX;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeedY(int newSpeedY) {
/* 174 */     this.speedY = newSpeedY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpeedZ(int newSpeedZ) {
/* 179 */     this.speedZ = newSpeedZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(int dataIn) {
/* 184 */     this.data = dataIn;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */