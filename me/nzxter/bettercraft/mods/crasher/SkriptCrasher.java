/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ 
/*    */ public class SkriptCrasher
/*    */ {
/*    */   public static void start() {
/*  9 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try {
/*    */             while (true) {
/* 15 */               if ((Minecraft.getMinecraft()).objectMouseOver.entityHit != null) {
/* 16 */                 (Minecraft.getMinecraft().getConnection().getNetworkManager()).channel.writeAndFlush(new CPacketUseEntity((Minecraft.getMinecraft()).objectMouseOver.entityHit));
/*    */               }
/* 18 */               Thread.sleep(5L);
/*    */             }
/*    */           
/* 21 */           } catch (Throwable throwable) {
/*    */             return;
/*    */           } 
/*    */         }
/* 25 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\SkriptCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */