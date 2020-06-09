/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveOff
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "save-off";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 24 */     return "commands.save-off.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 32 */     boolean flag = false;
/*    */     
/* 34 */     for (int i = 0; i < server.worldServers.length; i++) {
/*    */       
/* 36 */       if (server.worldServers[i] != null) {
/*    */         
/* 38 */         WorldServer worldserver = server.worldServers[i];
/*    */         
/* 40 */         if (!worldserver.disableLevelSaving) {
/*    */           
/* 42 */           worldserver.disableLevelSaving = true;
/* 43 */           flag = true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     if (flag) {
/*    */       
/* 50 */       notifyCommandListener(sender, (ICommand)this, "commands.save.disabled", new Object[0]);
/*    */     }
/*    */     else {
/*    */       
/* 54 */       throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandSaveOff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */