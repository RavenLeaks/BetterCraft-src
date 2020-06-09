/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.IScoreCriteria;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandTrigger
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  23 */     return "trigger";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  39 */     return "commands.trigger.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     EntityPlayerMP entityplayermp;
/*  47 */     if (args.length < 3)
/*     */     {
/*  49 */       throw new WrongUsageException("commands.trigger.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (sender instanceof EntityPlayerMP) {
/*     */       
/*  57 */       entityplayermp = (EntityPlayerMP)sender;
/*     */     }
/*     */     else {
/*     */       
/*  61 */       Entity entity = sender.getCommandSenderEntity();
/*     */       
/*  63 */       if (!(entity instanceof EntityPlayerMP))
/*     */       {
/*  65 */         throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
/*     */       }
/*     */       
/*  68 */       entityplayermp = (EntityPlayerMP)entity;
/*     */     } 
/*     */     
/*  71 */     Scoreboard scoreboard = server.worldServerForDimension(0).getScoreboard();
/*  72 */     ScoreObjective scoreobjective = scoreboard.getObjective(args[0]);
/*     */     
/*  74 */     if (scoreobjective != null && scoreobjective.getCriteria() == IScoreCriteria.TRIGGER) {
/*     */       
/*  76 */       int i = parseInt(args[2]);
/*     */       
/*  78 */       if (!scoreboard.entityHasObjective(entityplayermp.getName(), scoreobjective))
/*     */       {
/*  80 */         throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */       }
/*     */ 
/*     */       
/*  84 */       Score score = scoreboard.getOrCreateScore(entityplayermp.getName(), scoreobjective);
/*     */       
/*  86 */       if (score.isLocked())
/*     */       {
/*  88 */         throw new CommandException("commands.trigger.disabled", new Object[] { args[0] });
/*     */       }
/*     */ 
/*     */       
/*  92 */       if ("set".equals(args[1])) {
/*     */         
/*  94 */         score.setScorePoints(i);
/*     */       }
/*     */       else {
/*     */         
/*  98 */         if (!"add".equals(args[1]))
/*     */         {
/* 100 */           throw new CommandException("commands.trigger.invalidMode", new Object[] { args[1] });
/*     */         }
/*     */         
/* 103 */         score.increaseScore(i);
/*     */       } 
/*     */       
/* 106 */       score.setLocked(true);
/*     */       
/* 108 */       if (entityplayermp.interactionManager.isCreative())
/*     */       {
/* 110 */         notifyCommandListener(sender, this, "commands.trigger.success", new Object[] { args[0], args[1], args[2] });
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 117 */       throw new CommandException("commands.trigger.invalidObjective", new Object[] { args[0] });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 124 */     if (args.length == 1) {
/*     */       
/* 126 */       Scoreboard scoreboard = server.worldServerForDimension(0).getScoreboard();
/* 127 */       List<String> list = Lists.newArrayList();
/*     */       
/* 129 */       for (ScoreObjective scoreobjective : scoreboard.getScoreObjectives()) {
/*     */         
/* 131 */         if (scoreobjective.getCriteria() == IScoreCriteria.TRIGGER)
/*     */         {
/* 133 */           list.add(scoreobjective.getName());
/*     */         }
/*     */       } 
/*     */       
/* 137 */       return getListOfStringsMatchingLastWord(args, list.<String>toArray(new String[list.size()]));
/*     */     } 
/*     */ 
/*     */     
/* 141 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, new String[] { "add", "set" }) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */