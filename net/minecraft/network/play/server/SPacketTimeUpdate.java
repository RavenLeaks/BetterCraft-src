/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.AbstractPacket;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class SPacketTimeUpdate
/*    */   extends AbstractPacket<INetHandlerPlayClient>
/*    */ {
/*    */   private long totalWorldTime;
/*    */   private long worldTime;
/*    */   
/*    */   public SPacketTimeUpdate() {}
/*    */   
/*    */   public SPacketTimeUpdate(long totalWorldTimeIn, long totalTimeIn, boolean doDayLightCycle) {
/* 18 */     this.totalWorldTime = totalWorldTimeIn;
/* 19 */     this.worldTime = totalTimeIn;
/*    */     
/* 21 */     if (!doDayLightCycle) {
/* 22 */       this.worldTime = -this.worldTime;
/*    */       
/* 24 */       if (this.worldTime == 0L) {
/* 25 */         this.worldTime = -1L;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.totalWorldTime = buf.readLong();
/* 35 */     this.worldTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeLong(this.totalWorldTime);
/* 43 */     buf.writeLong(this.worldTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 50 */     super.processPacket((INetHandler)handler);
/* 51 */     if (this.cancelled) {
/*    */       return;
/*    */     }
/* 54 */     handler.handleTimeUpdate(this);
/*    */   }
/*    */   
/*    */   public long getTotalWorldTime() {
/* 58 */     return this.totalWorldTime;
/*    */   }
/*    */   
/*    */   public long getWorldTime() {
/* 62 */     return this.worldTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketTimeUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */