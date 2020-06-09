/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.PlayerNotFoundException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.UserListEntry;
/*     */ import net.minecraft.server.management.UserListIPBansEntry;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class CommandBanIp
/*     */   extends CommandBase {
/*  24 */   public static final Pattern IP_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  31 */     return "ban-ip";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  39 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
/*  47 */     return (server.getPlayerList().getBannedIPs().isLanServer() && super.checkPermission(server, sender));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  55 */     return "commands.banip.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  63 */     if (args.length >= 1 && args[0].length() > 1) {
/*     */       
/*  65 */       ITextComponent itextcomponent = (args.length >= 2) ? getChatComponentFromNthArg(sender, args, 1) : null;
/*  66 */       Matcher matcher = IP_PATTERN.matcher(args[0]);
/*     */       
/*  68 */       if (matcher.matches())
/*     */       {
/*  70 */         banIp(server, sender, args[0], (itextcomponent == null) ? null : itextcomponent.getUnformattedText());
/*     */       }
/*     */       else
/*     */       {
/*  74 */         EntityPlayerMP entityplayermp = server.getPlayerList().getPlayerByUsername(args[0]);
/*     */         
/*  76 */         if (entityplayermp == null)
/*     */         {
/*  78 */           throw new PlayerNotFoundException("commands.banip.invalid");
/*     */         }
/*     */         
/*  81 */         banIp(server, sender, entityplayermp.getPlayerIP(), (itextcomponent == null) ? null : itextcomponent.getUnformattedText());
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  86 */       throw new WrongUsageException("commands.banip.usage", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/*  92 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void banIp(MinecraftServer server, ICommandSender sender, String ipAddress, @Nullable String banReason) {
/*  97 */     UserListIPBansEntry userlistipbansentry = new UserListIPBansEntry(ipAddress, null, sender.getName(), null, banReason);
/*  98 */     server.getPlayerList().getBannedIPs().addEntry((UserListEntry)userlistipbansentry);
/*  99 */     List<EntityPlayerMP> list = server.getPlayerList().getPlayersMatchingAddress(ipAddress);
/* 100 */     String[] astring = new String[list.size()];
/* 101 */     int i = 0;
/*     */     
/* 103 */     for (EntityPlayerMP entityplayermp : list) {
/*     */       
/* 105 */       entityplayermp.connection.func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.ip_banned", new Object[0]));
/* 106 */       astring[i++] = entityplayermp.getName();
/*     */     } 
/*     */     
/* 109 */     if (list.isEmpty()) {
/*     */       
/* 111 */       notifyCommandListener(sender, (ICommand)this, "commands.banip.success", new Object[] { ipAddress });
/*     */     }
/*     */     else {
/*     */       
/* 115 */       notifyCommandListener(sender, (ICommand)this, "commands.banip.success.players", new Object[] { ipAddress, joinNiceString((Object[])astring) });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandBanIp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */