/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandLocate
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 17 */     return "locate";
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
/* 33 */     return "commands.locate.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 41 */     if (args.length != 1)
/*    */     {
/* 43 */       throw new WrongUsageException("commands.locate.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 47 */     String s = args[0];
/* 48 */     BlockPos blockpos = sender.getEntityWorld().func_190528_a(s, sender.getPosition(), false);
/*    */     
/* 50 */     if (blockpos != null) {
/*    */       
/* 52 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.locate.success", new Object[] { s, Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getZ()) }));
/*    */     }
/*    */     else {
/*    */       
/* 56 */       throw new CommandException("commands.locate.failure", new Object[] { s });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 63 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "Stronghold", "Monument", "Village", "Mansion", "EndCity", "Fortress", "Temple", "Mineshaft" }) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandLocate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */