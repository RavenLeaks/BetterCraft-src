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
/*     */ public class SPacketEntityVelocity
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int entityID;
/*     */   private int motionX;
/*     */   private int motionY;
/*     */   private int motionZ;
/*     */   
/*     */   public SPacketEntityVelocity() {}
/*     */   
/*     */   public SPacketEntityVelocity(Entity entityIn) {
/*  22 */     this(entityIn.getEntityId(), entityIn.motionX, entityIn.motionY, entityIn.motionZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketEntityVelocity(int entityIdIn, double motionXIn, double motionYIn, double motionZIn) {
/*  27 */     this.entityID = entityIdIn;
/*  28 */     double d0 = 3.9D;
/*     */     
/*  30 */     if (motionXIn < -3.9D)
/*     */     {
/*  32 */       motionXIn = -3.9D;
/*     */     }
/*     */     
/*  35 */     if (motionYIn < -3.9D)
/*     */     {
/*  37 */       motionYIn = -3.9D;
/*     */     }
/*     */     
/*  40 */     if (motionZIn < -3.9D)
/*     */     {
/*  42 */       motionZIn = -3.9D;
/*     */     }
/*     */     
/*  45 */     if (motionXIn > 3.9D)
/*     */     {
/*  47 */       motionXIn = 3.9D;
/*     */     }
/*     */     
/*  50 */     if (motionYIn > 3.9D)
/*     */     {
/*  52 */       motionYIn = 3.9D;
/*     */     }
/*     */     
/*  55 */     if (motionZIn > 3.9D)
/*     */     {
/*  57 */       motionZIn = 3.9D;
/*     */     }
/*     */     
/*  60 */     this.motionX = (int)(motionXIn * 8000.0D);
/*  61 */     this.motionY = (int)(motionYIn * 8000.0D);
/*  62 */     this.motionZ = (int)(motionZIn * 8000.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  70 */     this.entityID = buf.readVarIntFromBuffer();
/*  71 */     this.motionX = buf.readShort();
/*  72 */     this.motionY = buf.readShort();
/*  73 */     this.motionZ = buf.readShort();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  81 */     buf.writeVarIntToBuffer(this.entityID);
/*  82 */     buf.writeShort(this.motionX);
/*  83 */     buf.writeShort(this.motionY);
/*  84 */     buf.writeShort(this.motionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  92 */     handler.handleEntityVelocity(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEntityID() {
/*  97 */     return this.entityID;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMotionX() {
/* 102 */     return this.motionX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMotionY() {
/* 107 */     return this.motionY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMotionZ() {
/* 112 */     return this.motionZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityVelocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */