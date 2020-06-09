/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class FlyCrasher {
/*    */   public static void start() {
/*  9 */     double x = (Minecraft.getMinecraft()).player.posX;
/* 10 */     double y = (Minecraft.getMinecraft()).player.posY;
/* 11 */     double z = (Minecraft.getMinecraft()).player.posZ;
/* 12 */     double d1 = 0.0D;
/* 13 */     double d2 = 0.0D;
/* 14 */     for (int i2 = 0; i2 < 200; i2++) {
/* 15 */       d1 = (i2 * 9);
/* 16 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + d1, z, false));
/*    */     } 
/* 18 */     for (int i3 = 0; i3 < 10000; i3++) {
/* 19 */       d2 = (i3 * 9);
/* 20 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayer.Position(x, y + d1, z + d2, false));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\FlyCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */