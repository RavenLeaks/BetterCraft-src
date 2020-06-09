/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.GameType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandDefaultGameMode
/*    */   extends CommandGameMode
/*    */ {
/*    */   public String getCommandName() {
/* 15 */     return "defaultgamemode";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 23 */     return "commands.defaultgamemode.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 31 */     if (args.length <= 0)
/*    */     {
/* 33 */       throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 37 */     GameType gametype = getGameModeFromCommand(sender, args[0]);
/* 38 */     setDefaultGameType(gametype, server);
/* 39 */     notifyCommandListener(sender, this, "commands.defaultgamemode.success", new Object[] { new TextComponentTranslation("gameMode." + gametype.getName(), new Object[0]) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setDefaultGameType(GameType gameType, MinecraftServer server) {
/* 49 */     server.setGameType(gameType);
/*    */     
/* 51 */     if (server.getForceGamemode())
/*    */     {
/* 53 */       for (EntityPlayerMP entityplayermp : server.getPlayerList().getPlayerList())
/*    */       {
/* 55 */         entityplayermp.setGameType(gameType);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandDefaultGameMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */