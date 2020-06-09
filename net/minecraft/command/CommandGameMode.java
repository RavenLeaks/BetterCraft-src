/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.GameType;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandGameMode
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 21 */     return "gm";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 29 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 37 */     return "commands.gamemode.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 45 */     if (args.length <= 0)
/*    */     {
/* 47 */       throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 51 */     GameType gametype = getGameModeFromCommand(sender, args[0]);
/* 52 */     EntityPlayerMP entityPlayerMP = (args.length >= 2) ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
/* 53 */     entityPlayerMP.setGameType(gametype);
/* 54 */     TextComponentTranslation textComponentTranslation = new TextComponentTranslation("gameMode." + gametype.getName(), new Object[0]);
/*    */     
/* 56 */     if (sender.getEntityWorld().getGameRules().getBoolean("sendCommandFeedback"))
/*    */     {
/* 58 */       entityPlayerMP.addChatMessage((ITextComponent)new TextComponentTranslation("gameMode.changed", new Object[] { textComponentTranslation }));
/*    */     }
/*    */     
/* 61 */     if (entityPlayerMP == sender) {
/*    */       
/* 63 */       notifyCommandListener(sender, this, 1, "commands.gamemode.success.self", new Object[] { textComponentTranslation });
/*    */     }
/*    */     else {
/*    */       
/* 67 */       notifyCommandListener(sender, this, 1, "commands.gamemode.success.other", new Object[] { entityPlayerMP.getName(), textComponentTranslation });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected GameType getGameModeFromCommand(ICommandSender sender, String gameModeString) throws CommandException, NumberInvalidException {
/* 77 */     GameType gametype = GameType.parseGameTypeWithDefault(gameModeString, GameType.NOT_SET);
/* 78 */     return (gametype == GameType.NOT_SET) ? WorldSettings.getGameTypeById(parseInt(gameModeString, 0, (GameType.values()).length - 2)) : gametype;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 83 */     if (args.length == 1)
/*    */     {
/* 85 */       return getListOfStringsMatchingLastWord(args, new String[] { "survival", "creative", "adventure", "spectator" });
/*    */     }
/*    */ 
/*    */     
/* 89 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 98 */     return (index == 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandGameMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */