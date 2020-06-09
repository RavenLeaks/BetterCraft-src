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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandServerKick
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 19 */     return "kick";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 27 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 35 */     return "commands.kick.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 43 */     if (args.length > 0 && args[0].length() > 1) {
/*    */       
/* 45 */       EntityPlayerMP entityplayermp = server.getPlayerList().getPlayerByUsername(args[0]);
/*    */       
/* 47 */       if (entityplayermp == null)
/*    */       {
/* 49 */         throw new PlayerNotFoundException("commands.generic.player.notFound", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 53 */       if (args.length >= 2)
/*    */       {
/* 55 */         ITextComponent itextcomponent = getChatComponentFromNthArg(sender, args, 1);
/* 56 */         entityplayermp.connection.func_194028_b(itextcomponent);
/* 57 */         notifyCommandListener(sender, this, "commands.kick.success.reason", new Object[] { entityplayermp.getName(), itextcomponent.getUnformattedText() });
/*    */       }
/*    */       else
/*    */       {
/* 61 */         entityplayermp.connection.func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.kicked", new Object[0]));
/* 62 */         notifyCommandListener(sender, this, "commands.kick.success", new Object[] { entityplayermp.getName() });
/*    */       }
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 68 */       throw new WrongUsageException("commands.kick.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 74 */     return (args.length >= 1) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandServerKick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */