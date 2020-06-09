/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CPacketInput
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private float strafeSpeed;
/*    */   private float field_192621_b;
/*    */   private boolean jumping;
/*    */   private boolean sneaking;
/*    */   
/*    */   public CPacketInput() {}
/*    */   
/*    */   public CPacketInput(float strafeSpeedIn, float forwardSpeedIn, boolean jumpingIn, boolean sneakingIn) {
/* 22 */     this.strafeSpeed = strafeSpeedIn;
/* 23 */     this.field_192621_b = forwardSpeedIn;
/* 24 */     this.jumping = jumpingIn;
/* 25 */     this.sneaking = sneakingIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 33 */     this.strafeSpeed = buf.readFloat();
/* 34 */     this.field_192621_b = buf.readFloat();
/* 35 */     byte b0 = buf.readByte();
/* 36 */     this.jumping = ((b0 & 0x1) > 0);
/* 37 */     this.sneaking = ((b0 & 0x2) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeFloat(this.strafeSpeed);
/* 46 */     buf.writeFloat(this.field_192621_b);
/* 47 */     byte b0 = 0;
/*    */     
/* 49 */     if (this.jumping)
/*    */     {
/* 51 */       b0 = (byte)(b0 | 0x1);
/*    */     }
/*    */     
/* 54 */     if (this.sneaking)
/*    */     {
/* 56 */       b0 = (byte)(b0 | 0x2);
/*    */     }
/*    */     
/* 59 */     buf.writeByte(b0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 67 */     handler.processInput(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getStrafeSpeed() {
/* 72 */     return this.strafeSpeed;
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_192620_b() {
/* 77 */     return this.field_192621_b;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isJumping() {
/* 82 */     return this.jumping;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSneaking() {
/* 87 */     return this.sneaking;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */