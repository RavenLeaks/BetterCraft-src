/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.UserListBansEntry;
/*    */ import net.minecraft.server.management.UserListEntry;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ 
/*    */ public class CommandBanPlayer
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 25 */     return "ban";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 33 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 41 */     return "commands.ban.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
/* 49 */     return (server.getPlayerList().getBannedPlayers().isLanServer() && super.checkPermission(server, sender));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 57 */     if (args.length >= 1 && args[0].length() > 0) {
/*    */       
/* 59 */       GameProfile gameprofile = server.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 61 */       if (gameprofile == null)
/*    */       {
/* 63 */         throw new CommandException("commands.ban.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 67 */       String s = null;
/*    */       
/* 69 */       if (args.length >= 2)
/*    */       {
/* 71 */         s = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
/*    */       }
/*    */       
/* 74 */       UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, null, sender.getName(), null, s);
/* 75 */       server.getPlayerList().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/* 76 */       EntityPlayerMP entityplayermp = server.getPlayerList().getPlayerByUsername(args[0]);
/*    */       
/* 78 */       if (entityplayermp != null)
/*    */       {
/* 80 */         entityplayermp.connection.func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.banned", new Object[0]));
/*    */       }
/*    */       
/* 83 */       notifyCommandListener(sender, (ICommand)this, "commands.ban.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 88 */       throw new WrongUsageException("commands.ban.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 94 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandBanPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */