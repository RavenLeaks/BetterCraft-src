/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.SyntaxErrorException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPardonIp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 22 */     return "pardon-ip";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 30 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
/* 38 */     return (server.getPlayerList().getBannedIPs().isLanServer() && super.checkPermission(server, sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 46 */     return "commands.unbanip.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 54 */     if (args.length == 1 && args[0].length() > 1) {
/*    */       
/* 56 */       Matcher matcher = CommandBanIp.IP_PATTERN.matcher(args[0]);
/*    */       
/* 58 */       if (matcher.matches())
/*    */       {
/* 60 */         server.getPlayerList().getBannedIPs().removeEntry(args[0]);
/* 61 */         notifyCommandListener(sender, (ICommand)this, "commands.unbanip.success", new Object[] { args[0] });
/*    */       }
/*    */       else
/*    */       {
/* 65 */         throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 70 */       throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 76 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, server.getPlayerList().getBannedIPs().getKeys()) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandPardonIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */