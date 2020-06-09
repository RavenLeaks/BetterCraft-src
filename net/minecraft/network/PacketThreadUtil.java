/*    */ package net.minecraft.network;
/*    */ 
/*    */ import net.minecraft.util.IThreadListener;
/*    */ 
/*    */ 
/*    */ public class PacketThreadUtil
/*    */ {
/*    */   public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> packetIn, final T processor, IThreadListener scheduler) throws ThreadQuickExitException {
/*  9 */     if (!scheduler.isCallingFromMinecraftThread()) {
/*    */       
/* 11 */       scheduler.addScheduledTask(new Runnable()
/*    */           {
/*    */             public void run()
/*    */             {
/* 15 */               packetIn.processPacket(processor);
/*    */             }
/*    */           });
/* 18 */       throw ThreadQuickExitException.INSTANCE;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\PacketThreadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */