/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class SPacketEntity
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   protected int entityId;
/*     */   protected int posX;
/*     */   protected int posY;
/*     */   protected int posZ;
/*     */   protected byte yaw;
/*     */   protected byte pitch;
/*     */   protected boolean onGround;
/*     */   protected boolean rotating;
/*     */   
/*     */   public SPacketEntity() {}
/*     */   
/*     */   public SPacketEntity(int entityIdIn) {
/*  27 */     this.entityId = entityIdIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  35 */     this.entityId = buf.readVarIntFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  43 */     buf.writeVarIntToBuffer(this.entityId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  51 */     handler.handleEntityMovement(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  56 */     return "Entity_" + super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getEntity(World worldIn) {
/*  61 */     return worldIn.getEntityByID(this.entityId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/*  66 */     return this.posX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/*  71 */     return this.posY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  76 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getYaw() {
/*  81 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getPitch() {
/*  86 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRotating() {
/*  91 */     return this.rotating;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOnGround() {
/*  96 */     return this.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class S15PacketEntityRelMove
/*     */     extends SPacketEntity
/*     */   {
/*     */     public S15PacketEntityRelMove() {}
/*     */ 
/*     */     
/*     */     public S15PacketEntityRelMove(int entityIdIn, long xIn, long yIn, long zIn, boolean onGroundIn) {
/* 107 */       super(entityIdIn);
/* 108 */       this.posX = (int)xIn;
/* 109 */       this.posY = (int)yIn;
/* 110 */       this.posZ = (int)zIn;
/* 111 */       this.onGround = onGroundIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 116 */       super.readPacketData(buf);
/* 117 */       this.posX = buf.readShort();
/* 118 */       this.posY = buf.readShort();
/* 119 */       this.posZ = buf.readShort();
/* 120 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 125 */       super.writePacketData(buf);
/* 126 */       buf.writeShort(this.posX);
/* 127 */       buf.writeShort(this.posY);
/* 128 */       buf.writeShort(this.posZ);
/* 129 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class S16PacketEntityLook
/*     */     extends SPacketEntity
/*     */   {
/*     */     public S16PacketEntityLook() {
/* 137 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public S16PacketEntityLook(int entityIdIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
/* 142 */       super(entityIdIn);
/* 143 */       this.yaw = yawIn;
/* 144 */       this.pitch = pitchIn;
/* 145 */       this.rotating = true;
/* 146 */       this.onGround = onGroundIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 151 */       super.readPacketData(buf);
/* 152 */       this.yaw = buf.readByte();
/* 153 */       this.pitch = buf.readByte();
/* 154 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 159 */       super.writePacketData(buf);
/* 160 */       buf.writeByte(this.yaw);
/* 161 */       buf.writeByte(this.pitch);
/* 162 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class S17PacketEntityLookMove
/*     */     extends SPacketEntity
/*     */   {
/*     */     public S17PacketEntityLookMove() {
/* 170 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public S17PacketEntityLookMove(int entityIdIn, long xIn, long yIn, long zIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
/* 175 */       super(entityIdIn);
/* 176 */       this.posX = (int)xIn;
/* 177 */       this.posY = (int)yIn;
/* 178 */       this.posZ = (int)zIn;
/* 179 */       this.yaw = yawIn;
/* 180 */       this.pitch = pitchIn;
/* 181 */       this.onGround = onGroundIn;
/* 182 */       this.rotating = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 187 */       super.readPacketData(buf);
/* 188 */       this.posX = buf.readShort();
/* 189 */       this.posY = buf.readShort();
/* 190 */       this.posZ = buf.readShort();
/* 191 */       this.yaw = buf.readByte();
/* 192 */       this.pitch = buf.readByte();
/* 193 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 198 */       super.writePacketData(buf);
/* 199 */       buf.writeShort(this.posX);
/* 200 */       buf.writeShort(this.posY);
/* 201 */       buf.writeShort(this.posZ);
/* 202 */       buf.writeByte(this.yaw);
/* 203 */       buf.writeByte(this.pitch);
/* 204 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */