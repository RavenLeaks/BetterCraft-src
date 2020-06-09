/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandListBans
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 21 */     return "banlist";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
/* 37 */     return ((server.getPlayerList().getBannedIPs().isLanServer() || server.getPlayerList().getBannedPlayers().isLanServer()) && super.checkPermission(server, sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 45 */     return "commands.banlist.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 53 */     if (args.length >= 1 && "ips".equalsIgnoreCase(args[0])) {
/*    */       
/* 55 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.banlist.ips", new Object[] { Integer.valueOf((server.getPlayerList().getBannedIPs().getKeys()).length) }));
/* 56 */       sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString((Object[])server.getPlayerList().getBannedIPs().getKeys())));
/*    */     }
/*    */     else {
/*    */       
/* 60 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.banlist.players", new Object[] { Integer.valueOf((server.getPlayerList().getBannedPlayers().getKeys()).length) }));
/* 61 */       sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString((Object[])server.getPlayerList().getBannedPlayers().getKeys())));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 67 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "players", "ips" }) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandListBans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */