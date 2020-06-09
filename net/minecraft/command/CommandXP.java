/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandXP
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "xp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  25 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  33 */     return "commands.xp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  41 */     if (args.length <= 0)
/*     */     {
/*  43 */       throw new WrongUsageException("commands.xp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  47 */     String s = args[0];
/*  48 */     boolean flag = !(!s.endsWith("l") && !s.endsWith("L"));
/*     */     
/*  50 */     if (flag && s.length() > 1)
/*     */     {
/*  52 */       s = s.substring(0, s.length() - 1);
/*     */     }
/*     */     
/*  55 */     int i = parseInt(s);
/*  56 */     boolean flag1 = (i < 0);
/*     */     
/*  58 */     if (flag1)
/*     */     {
/*  60 */       i *= -1;
/*     */     }
/*     */     
/*  63 */     EntityPlayerMP entityPlayerMP = (args.length > 1) ? getPlayer(server, sender, args[1]) : getCommandSenderAsPlayer(sender);
/*     */     
/*  65 */     if (flag) {
/*     */       
/*  67 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceLevel);
/*     */       
/*  69 */       if (flag1)
/*     */       {
/*  71 */         entityPlayerMP.addExperienceLevel(-i);
/*  72 */         notifyCommandListener(sender, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */       }
/*     */       else
/*     */       {
/*  76 */         entityPlayerMP.addExperienceLevel(i);
/*  77 */         notifyCommandListener(sender, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  82 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceTotal);
/*     */       
/*  84 */       if (flag1)
/*     */       {
/*  86 */         throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
/*     */       }
/*     */       
/*  89 */       entityPlayerMP.addExperience(i);
/*  90 */       notifyCommandListener(sender, this, "commands.xp.success", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/*  97 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 105 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandXP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */