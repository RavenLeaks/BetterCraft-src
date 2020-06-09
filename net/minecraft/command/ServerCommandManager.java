/*     */ package net.minecraft.command;
/*     */ 
/*     */ import net.minecraft.command.server.CommandBanIp;
/*     */ import net.minecraft.command.server.CommandBanPlayer;
/*     */ import net.minecraft.command.server.CommandBroadcast;
/*     */ import net.minecraft.command.server.CommandDeOp;
/*     */ import net.minecraft.command.server.CommandEmote;
/*     */ import net.minecraft.command.server.CommandListBans;
/*     */ import net.minecraft.command.server.CommandListPlayers;
/*     */ import net.minecraft.command.server.CommandMessage;
/*     */ import net.minecraft.command.server.CommandMessageRaw;
/*     */ import net.minecraft.command.server.CommandOp;
/*     */ import net.minecraft.command.server.CommandPardonIp;
/*     */ import net.minecraft.command.server.CommandPardonPlayer;
/*     */ import net.minecraft.command.server.CommandPublishLocalServer;
/*     */ import net.minecraft.command.server.CommandSaveAll;
/*     */ import net.minecraft.command.server.CommandSaveOff;
/*     */ import net.minecraft.command.server.CommandSaveOn;
/*     */ import net.minecraft.command.server.CommandScoreboard;
/*     */ import net.minecraft.command.server.CommandSetBlock;
/*     */ import net.minecraft.command.server.CommandSetDefaultSpawnpoint;
/*     */ import net.minecraft.command.server.CommandStop;
/*     */ import net.minecraft.command.server.CommandSummon;
/*     */ import net.minecraft.command.server.CommandTeleport;
/*     */ import net.minecraft.command.server.CommandTestFor;
/*     */ import net.minecraft.command.server.CommandTestForBlock;
/*     */ import net.minecraft.command.server.CommandWhitelist;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ public class ServerCommandManager
/*     */   extends CommandHandler
/*     */   implements ICommandListener
/*     */ {
/*     */   private final MinecraftServer server;
/*     */   
/*     */   public ServerCommandManager(MinecraftServer serverIn) {
/*  42 */     this.server = serverIn;
/*  43 */     registerCommand(new CommandTime());
/*  44 */     registerCommand(new CommandGameMode());
/*  45 */     registerCommand(new CommandDifficulty());
/*  46 */     registerCommand(new CommandDefaultGameMode());
/*  47 */     registerCommand(new CommandKill());
/*  48 */     registerCommand(new CommandToggleDownfall());
/*  49 */     registerCommand(new CommandWeather());
/*  50 */     registerCommand(new CommandXP());
/*  51 */     registerCommand(new CommandTP());
/*  52 */     registerCommand((ICommand)new CommandTeleport());
/*  53 */     registerCommand(new CommandGive());
/*  54 */     registerCommand(new CommandReplaceItem());
/*  55 */     registerCommand(new CommandStats());
/*  56 */     registerCommand(new CommandEffect());
/*  57 */     registerCommand(new CommandEnchant());
/*  58 */     registerCommand(new CommandParticle());
/*  59 */     registerCommand((ICommand)new CommandEmote());
/*  60 */     registerCommand(new CommandShowSeed());
/*  61 */     registerCommand(new CommandHelp());
/*  62 */     registerCommand(new CommandDebug());
/*  63 */     registerCommand((ICommand)new CommandMessage());
/*  64 */     registerCommand((ICommand)new CommandBroadcast());
/*  65 */     registerCommand(new CommandSetSpawnpoint());
/*  66 */     registerCommand((ICommand)new CommandSetDefaultSpawnpoint());
/*  67 */     registerCommand(new CommandGameRule());
/*  68 */     registerCommand(new CommandClearInventory());
/*  69 */     registerCommand((ICommand)new CommandTestFor());
/*  70 */     registerCommand(new CommandSpreadPlayers());
/*  71 */     registerCommand(new CommandPlaySound());
/*  72 */     registerCommand((ICommand)new CommandScoreboard());
/*  73 */     registerCommand(new CommandExecuteAt());
/*  74 */     registerCommand(new CommandTrigger());
/*  75 */     registerCommand(new AdvancementCommand());
/*  76 */     registerCommand(new RecipeCommand());
/*  77 */     registerCommand((ICommand)new CommandSummon());
/*  78 */     registerCommand((ICommand)new CommandSetBlock());
/*  79 */     registerCommand(new CommandFill());
/*  80 */     registerCommand(new CommandClone());
/*  81 */     registerCommand(new CommandCompare());
/*  82 */     registerCommand(new CommandBlockData());
/*  83 */     registerCommand((ICommand)new CommandTestForBlock());
/*  84 */     registerCommand((ICommand)new CommandMessageRaw());
/*  85 */     registerCommand(new CommandWorldBorder());
/*  86 */     registerCommand(new CommandTitle());
/*  87 */     registerCommand(new CommandEntityData());
/*  88 */     registerCommand(new CommandStopSound());
/*  89 */     registerCommand(new CommandLocate());
/*  90 */     registerCommand(new CommandReload());
/*  91 */     registerCommand(new CommandFunction());
/*     */     
/*  93 */     if (serverIn.isDedicatedServer()) {
/*     */       
/*  95 */       registerCommand((ICommand)new CommandOp());
/*  96 */       registerCommand((ICommand)new CommandDeOp());
/*  97 */       registerCommand((ICommand)new CommandStop());
/*  98 */       registerCommand((ICommand)new CommandSaveAll());
/*  99 */       registerCommand((ICommand)new CommandSaveOff());
/* 100 */       registerCommand((ICommand)new CommandSaveOn());
/* 101 */       registerCommand((ICommand)new CommandBanIp());
/* 102 */       registerCommand((ICommand)new CommandPardonIp());
/* 103 */       registerCommand((ICommand)new CommandBanPlayer());
/* 104 */       registerCommand((ICommand)new CommandListBans());
/* 105 */       registerCommand((ICommand)new CommandPardonPlayer());
/* 106 */       registerCommand(new CommandServerKick());
/* 107 */       registerCommand((ICommand)new CommandListPlayers());
/* 108 */       registerCommand((ICommand)new CommandWhitelist());
/* 109 */       registerCommand(new CommandSetPlayerTimeout());
/*     */     }
/*     */     else {
/*     */       
/* 113 */       registerCommand((ICommand)new CommandPublishLocalServer());
/*     */     } 
/*     */     
/* 116 */     CommandBase.setCommandListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyListener(ICommandSender sender, ICommand command, int flags, String translationKey, Object... translationArgs) {
/* 124 */     boolean flag = true;
/* 125 */     MinecraftServer minecraftserver = this.server;
/*     */     
/* 127 */     if (!sender.sendCommandFeedback())
/*     */     {
/* 129 */       flag = false;
/*     */     }
/*     */     
/* 132 */     TextComponentTranslation textComponentTranslation = new TextComponentTranslation("chat.type.admin", new Object[] { sender.getName(), new TextComponentTranslation(translationKey, translationArgs) });
/* 133 */     textComponentTranslation.getStyle().setColor(TextFormatting.GRAY);
/* 134 */     textComponentTranslation.getStyle().setItalic(Boolean.valueOf(true));
/*     */     
/* 136 */     if (flag)
/*     */     {
/* 138 */       for (EntityPlayer entityplayer : minecraftserver.getPlayerList().getPlayerList()) {
/*     */         
/* 140 */         if (entityplayer != sender && minecraftserver.getPlayerList().canSendCommands(entityplayer.getGameProfile()) && command.checkPermission(this.server, sender)) {
/*     */           
/* 142 */           boolean flag1 = (sender instanceof MinecraftServer && this.server.shouldBroadcastConsoleToOps());
/* 143 */           boolean flag2 = (sender instanceof net.minecraft.network.rcon.RConConsoleSource && this.server.shouldBroadcastRconToOps());
/*     */           
/* 145 */           if (flag1 || flag2 || (!(sender instanceof net.minecraft.network.rcon.RConConsoleSource) && !(sender instanceof MinecraftServer)))
/*     */           {
/* 147 */             entityplayer.addChatMessage((ITextComponent)textComponentTranslation);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 153 */     if (sender != minecraftserver && minecraftserver.worldServers[0].getGameRules().getBoolean("logAdminCommands"))
/*     */     {
/* 155 */       minecraftserver.addChatMessage((ITextComponent)textComponentTranslation);
/*     */     }
/*     */     
/* 158 */     boolean flag3 = minecraftserver.worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*     */     
/* 160 */     if (sender instanceof CommandBlockBaseLogic)
/*     */     {
/* 162 */       flag3 = ((CommandBlockBaseLogic)sender).shouldTrackOutput();
/*     */     }
/*     */     
/* 165 */     if (((flags & 0x1) != 1 && flag3) || sender instanceof MinecraftServer)
/*     */     {
/* 167 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation(translationKey, translationArgs));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected MinecraftServer getServer() {
/* 173 */     return this.server;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\ServerCommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */