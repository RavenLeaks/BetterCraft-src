/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import me.nzxter.bettercraft.utils.ClipboardUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CopyServerIPCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 13 */     if (args.length == 0) {
/* 14 */       msg("The Server IP was saved in your §eclipboard!", true);
/* 15 */       String toCopy = "localhost";
/* 16 */       if (!Minecraft.getMinecraft().isIntegratedServerRunning()) {
/* 17 */         toCopy = Minecraft.getMinecraft().getConnection().getNetworkManager().getRemoteAddress().toString();
/*    */       }
/* 19 */       ClipboardUtils.setClipboard(toCopy);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 25 */     return "copyip";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\CopyServerIPCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */