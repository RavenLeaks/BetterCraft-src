/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ public class CPacketUpdateSign
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private BlockPos pos;
/*    */   private String[] lines;
/*    */   
/*    */   public CPacketUpdateSign() {}
/*    */   
/*    */   public CPacketUpdateSign(BlockPos posIn, ITextComponent[] linesIn) {
/* 21 */     this.pos = posIn;
/* 22 */     this.lines = new String[] { linesIn[0].getUnformattedText(), linesIn[1].getUnformattedText(), linesIn[2].getUnformattedText(), linesIn[3].getUnformattedText() };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.pos = buf.readBlockPos();
/* 31 */     this.lines = new String[4];
/*    */     
/* 33 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 35 */       this.lines[i] = buf.readStringFromBuffer(384);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 44 */     buf.writeBlockPos(this.pos);
/*    */     
/* 46 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 48 */       buf.writeString(this.lines[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 57 */     handler.processUpdateSign(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 62 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getLines() {
/* 67 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */