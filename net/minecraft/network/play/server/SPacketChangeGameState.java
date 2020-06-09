/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class SPacketChangeGameState implements Packet<INetHandlerPlayClient> {
/* 10 */   public static final String[] MESSAGE_NAMES = new String[] { "tile.bed.notValid" };
/*    */   
/*    */   private int state;
/*    */   
/*    */   private float value;
/*    */ 
/*    */   
/*    */   public SPacketChangeGameState() {}
/*    */   
/*    */   public SPacketChangeGameState(int stateIn, float valueIn) {
/* 20 */     this.state = stateIn;
/* 21 */     this.value = valueIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.state = buf.readUnsignedByte();
/* 30 */     this.value = buf.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 38 */     buf.writeByte(this.state);
/* 39 */     buf.writeFloat(this.value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleChangeGameState(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGameState() {
/* 52 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue() {
/* 57 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketChangeGameState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */