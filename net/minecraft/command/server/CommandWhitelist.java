/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ 
/*     */ public class CommandWhitelist
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "whitelist";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  39 */     return "commands.whitelist.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  47 */     if (args.length < 1)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  53 */     if ("on".equals(args[0])) {
/*     */       
/*  55 */       server.getPlayerList().setWhiteListEnabled(true);
/*  56 */       notifyCommandListener(sender, (ICommand)this, "commands.whitelist.enabled", new Object[0]);
/*     */     }
/*  58 */     else if ("off".equals(args[0])) {
/*     */       
/*  60 */       server.getPlayerList().setWhiteListEnabled(false);
/*  61 */       notifyCommandListener(sender, (ICommand)this, "commands.whitelist.disabled", new Object[0]);
/*     */     }
/*  63 */     else if ("list".equals(args[0])) {
/*     */       
/*  65 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.whitelist.list", new Object[] { Integer.valueOf((server.getPlayerList().getWhitelistedPlayerNames()).length), Integer.valueOf((server.getPlayerList().getAvailablePlayerDat()).length) }));
/*  66 */       String[] astring = server.getPlayerList().getWhitelistedPlayerNames();
/*  67 */       sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString((Object[])astring)));
/*     */     }
/*  69 */     else if ("add".equals(args[0])) {
/*     */       
/*  71 */       if (args.length < 2)
/*     */       {
/*  73 */         throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  76 */       GameProfile gameprofile = server.getPlayerProfileCache().getGameProfileForUsername(args[1]);
/*     */       
/*  78 */       if (gameprofile == null)
/*     */       {
/*  80 */         throw new CommandException("commands.whitelist.add.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/*  83 */       server.getPlayerList().addWhitelistedPlayer(gameprofile);
/*  84 */       notifyCommandListener(sender, (ICommand)this, "commands.whitelist.add.success", new Object[] { args[1] });
/*     */     }
/*  86 */     else if ("remove".equals(args[0])) {
/*     */       
/*  88 */       if (args.length < 2)
/*     */       {
/*  90 */         throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
/*     */       }
/*     */       
/*  93 */       GameProfile gameprofile1 = server.getPlayerList().getWhitelistedPlayers().getByName(args[1]);
/*     */       
/*  95 */       if (gameprofile1 == null)
/*     */       {
/*  97 */         throw new CommandException("commands.whitelist.remove.failed", new Object[] { args[1] });
/*     */       }
/*     */       
/* 100 */       server.getPlayerList().removePlayerFromWhitelist(gameprofile1);
/* 101 */       notifyCommandListener(sender, (ICommand)this, "commands.whitelist.remove.success", new Object[] { args[1] });
/*     */     }
/* 103 */     else if ("reload".equals(args[0])) {
/*     */       
/* 105 */       server.getPlayerList().reloadWhitelist();
/* 106 */       notifyCommandListener(sender, (ICommand)this, "commands.whitelist.reloaded", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 113 */     if (args.length == 1)
/*     */     {
/* 115 */       return getListOfStringsMatchingLastWord(args, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*     */     }
/*     */ 
/*     */     
/* 119 */     if (args.length == 2) {
/*     */       
/* 121 */       if ("remove".equals(args[0]))
/*     */       {
/* 123 */         return getListOfStringsMatchingLastWord(args, server.getPlayerList().getWhitelistedPlayerNames());
/*     */       }
/*     */       
/* 126 */       if ("add".equals(args[0]))
/*     */       {
/* 128 */         return getListOfStringsMatchingLastWord(args, server.getPlayerProfileCache().getUsernames());
/*     */       }
/*     */     } 
/*     */     
/* 132 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */