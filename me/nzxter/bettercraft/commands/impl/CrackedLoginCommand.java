/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CrackedLoginCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 16 */     if (args.length == 0) {
/* 17 */       msg("Register in cracked: §ebccrack123", true);
/* 18 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/login bccrack123 bccrack123"));
/* 19 */       (Minecraft.getMinecraft()).player.connection.sendPacket((Packet)new CPacketChatMessage("/register bccrack123 bccrack123"));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     return "cracked";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\CrackedLoginCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */