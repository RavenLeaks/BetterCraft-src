/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandDifficulty
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 18 */     return "difficulty";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 26 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 34 */     return "commands.difficulty.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 42 */     if (args.length <= 0)
/*    */     {
/* 44 */       throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 48 */     EnumDifficulty enumdifficulty = getDifficultyFromCommand(args[0]);
/* 49 */     server.setDifficultyForAllWorlds(enumdifficulty);
/* 50 */     notifyCommandListener(sender, this, "commands.difficulty.success", new Object[] { new TextComponentTranslation(enumdifficulty.getDifficultyResourceKey(), new Object[0]) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected EnumDifficulty getDifficultyFromCommand(String difficultyString) throws CommandException, NumberInvalidException {
/* 56 */     if (!"peaceful".equalsIgnoreCase(difficultyString) && !"p".equalsIgnoreCase(difficultyString)) {
/*    */       
/* 58 */       if (!"easy".equalsIgnoreCase(difficultyString) && !"e".equalsIgnoreCase(difficultyString)) {
/*    */         
/* 60 */         if (!"normal".equalsIgnoreCase(difficultyString) && !"n".equalsIgnoreCase(difficultyString))
/*    */         {
/* 62 */           return (!"hard".equalsIgnoreCase(difficultyString) && !"h".equalsIgnoreCase(difficultyString)) ? EnumDifficulty.getDifficultyEnum(parseInt(difficultyString, 0, 3)) : EnumDifficulty.HARD;
/*    */         }
/*    */ 
/*    */         
/* 66 */         return EnumDifficulty.NORMAL;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 71 */       return EnumDifficulty.EASY;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 76 */     return EnumDifficulty.PEACEFUL;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 82 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "peaceful", "easy", "normal", "hard" }) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandDifficulty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */