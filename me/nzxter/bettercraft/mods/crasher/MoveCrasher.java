/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MoveCrasher
/*    */ {
/*    */   public static void start() {
/* 15 */     double x = (Minecraft.getMinecraft()).player.posX;
/* 16 */     double y = (Minecraft.getMinecraft()).player.posY;
/* 17 */     double z = (Minecraft.getMinecraft()).player.posZ;
/* 18 */     double d1 = 0.0D;
/* 19 */     for (int i2 = 0; i2 < 10; i2++) {
/* 20 */       for (int i3 = 0; i3 < 10000; i3++) {
/* 21 */         d1 = (i3 * 9);
/* 22 */         (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y, z + d1, false));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\MoveCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */