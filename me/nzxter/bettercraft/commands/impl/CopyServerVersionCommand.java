/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import me.nzxter.bettercraft.utils.ClipboardUtils;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CopyServerVersionCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 13 */     if (args.length == 0) {
/* 14 */       msg("The Server Version was saved in your §eclipboard!", true);
/* 15 */       String toCopy = "vanilla";
/* 16 */       if (!Minecraft.getMinecraft().isIntegratedServerRunning()) {
/* 17 */         toCopy = (Minecraft.getMinecraft()).player.getServerBrand().toString();
/*    */       }
/* 19 */       ClipboardUtils.setClipboard(toCopy);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 25 */     return "copyversion";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\CopyServerVersionCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */