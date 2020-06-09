/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSetSpawnpoint
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 17 */     return "spawnpoint";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 25 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 33 */     return "commands.spawnpoint.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 41 */     if (args.length > 1 && args.length < 4)
/*    */     {
/* 43 */       throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 47 */     EntityPlayerMP entityplayermp = (args.length > 0) ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
/* 48 */     BlockPos blockpos = (args.length > 3) ? parseBlockPos(sender, args, 1, true) : entityplayermp.getPosition();
/*    */     
/* 50 */     if (entityplayermp.world != null) {
/*    */       
/* 52 */       entityplayermp.setSpawnPoint(blockpos, true);
/* 53 */       notifyCommandListener(sender, this, "commands.spawnpoint.success", new Object[] { entityplayermp.getName(), Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 60 */     if (args.length == 1)
/*    */     {
/* 62 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*    */     }
/*    */ 
/*    */     
/* 66 */     return (args.length > 1 && args.length <= 4) ? getTabCompletionCoordinate(args, 1, pos) : Collections.<String>emptyList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 75 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandSetSpawnpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */