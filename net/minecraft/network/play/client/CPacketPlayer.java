/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ public class CPacketPlayer
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double z;
/*     */   protected float yaw;
/*     */   protected float pitch;
/*     */   protected boolean onGround;
/*     */   protected boolean moving;
/*     */   protected boolean rotating;
/*     */   
/*     */   public CPacketPlayer() {}
/*     */   
/*     */   public CPacketPlayer(boolean onGroundIn) {
/*  25 */     this.onGround = onGroundIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  33 */     handler.processPlayer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  41 */     this.onGround = (buf.readUnsignedByte() != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  49 */     buf.writeByte(this.onGround ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX(double defaultValue) {
/*  54 */     return this.moving ? this.x : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY(double defaultValue) {
/*  59 */     return this.moving ? this.y : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ(double defaultValue) {
/*  64 */     return this.moving ? this.z : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYaw(float defaultValue) {
/*  69 */     return this.rotating ? this.yaw : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch(float defaultValue) {
/*  74 */     return this.rotating ? this.pitch : defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnGround() {
/*  79 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public static class Position
/*     */     extends CPacketPlayer
/*     */   {
/*     */     public Position() {
/*  86 */       this.moving = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Position(double xIn, double yIn, double zIn, boolean onGroundIn) {
/*  91 */       this.x = xIn;
/*  92 */       this.y = yIn;
/*  93 */       this.z = zIn;
/*  94 */       this.onGround = onGroundIn;
/*  95 */       this.moving = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 100 */       this.x = buf.readDouble();
/* 101 */       this.y = buf.readDouble();
/* 102 */       this.z = buf.readDouble();
/* 103 */       super.readPacketData(buf);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 108 */       buf.writeDouble(this.x);
/* 109 */       buf.writeDouble(this.y);
/* 110 */       buf.writeDouble(this.z);
/* 111 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PositionRotation
/*     */     extends CPacketPlayer
/*     */   {
/*     */     public PositionRotation() {
/* 119 */       this.moving = true;
/* 120 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionRotation(double xIn, double yIn, double zIn, float yawIn, float pitchIn, boolean onGroundIn) {
/* 125 */       this.x = xIn;
/* 126 */       this.y = yIn;
/* 127 */       this.z = zIn;
/* 128 */       this.yaw = yawIn;
/* 129 */       this.pitch = pitchIn;
/* 130 */       this.onGround = onGroundIn;
/* 131 */       this.rotating = true;
/* 132 */       this.moving = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 137 */       this.x = buf.readDouble();
/* 138 */       this.y = buf.readDouble();
/* 139 */       this.z = buf.readDouble();
/* 140 */       this.yaw = buf.readFloat();
/* 141 */       this.pitch = buf.readFloat();
/* 142 */       super.readPacketData(buf);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 147 */       buf.writeDouble(this.x);
/* 148 */       buf.writeDouble(this.y);
/* 149 */       buf.writeDouble(this.z);
/* 150 */       buf.writeFloat(this.yaw);
/* 151 */       buf.writeFloat(this.pitch);
/* 152 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Rotation
/*     */     extends CPacketPlayer
/*     */   {
/*     */     public Rotation() {
/* 160 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Rotation(float yawIn, float pitchIn, boolean onGroundIn) {
/* 165 */       this.yaw = yawIn;
/* 166 */       this.pitch = pitchIn;
/* 167 */       this.onGround = onGroundIn;
/* 168 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 173 */       this.yaw = buf.readFloat();
/* 174 */       this.pitch = buf.readFloat();
/* 175 */       super.readPacketData(buf);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 180 */       buf.writeFloat(this.yaw);
/* 181 */       buf.writeFloat(this.pitch);
/* 182 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */