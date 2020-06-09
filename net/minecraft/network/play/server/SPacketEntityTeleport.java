/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ 
/*     */ public class SPacketEntityTeleport
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   public int entityId;
/*     */   public double posX;
/*     */   public double posY;
/*     */   public double posZ;
/*     */   public byte yaw;
/*     */   public byte pitch;
/*     */   public boolean onGround;
/*     */   
/*     */   public SPacketEntityTeleport() {}
/*     */   
/*     */   public SPacketEntityTeleport(Entity entityIn) {
/*  25 */     this.entityId = entityIn.getEntityId();
/*  26 */     this.posX = entityIn.posX;
/*  27 */     this.posY = entityIn.posY;
/*  28 */     this.posZ = entityIn.posZ;
/*  29 */     this.yaw = (byte)(int)(entityIn.rotationYaw * 256.0F / 360.0F);
/*  30 */     this.pitch = (byte)(int)(entityIn.rotationPitch * 256.0F / 360.0F);
/*  31 */     this.onGround = entityIn.onGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  39 */     this.entityId = buf.readVarIntFromBuffer();
/*  40 */     this.posX = buf.readDouble();
/*  41 */     this.posY = buf.readDouble();
/*  42 */     this.posZ = buf.readDouble();
/*  43 */     this.yaw = buf.readByte();
/*  44 */     this.pitch = buf.readByte();
/*  45 */     this.onGround = buf.readBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  53 */     buf.writeVarIntToBuffer(this.entityId);
/*  54 */     buf.writeDouble(this.posX);
/*  55 */     buf.writeDouble(this.posY);
/*  56 */     buf.writeDouble(this.posZ);
/*  57 */     buf.writeByte(this.yaw);
/*  58 */     buf.writeByte(this.pitch);
/*  59 */     buf.writeBoolean(this.onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  67 */     handler.handleEntityTeleport(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityId() {
/*  72 */     return this.entityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  77 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  82 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  87 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/*  92 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/*  97 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOnGround() {
/* 102 */     return this.onGround;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */