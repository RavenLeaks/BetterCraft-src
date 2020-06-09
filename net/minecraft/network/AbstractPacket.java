/*    */ package net.minecraft.network;
/*    */ 
/*    */ import me.nzxter.bettercraft.utils.TimeHelperUtils;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractPacket<T extends INetHandler>
/*    */   implements Packet<T>
/*    */ {
/*    */   protected boolean cancelled = false;
/*    */   public boolean crit = false;
/*    */   
/*    */   public void processPacket(T handler) {
/* 13 */     TimeHelperUtils.onPacketRecieved(this);
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 17 */     this.cancelled = true;
/*    */   }
/*    */   
/*    */   public AbstractPacket setCrit(boolean crit) {
/* 21 */     this.crit = crit;
/* 22 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\AbstractPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */