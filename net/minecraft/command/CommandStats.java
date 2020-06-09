/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandStats
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  24 */     return "stats";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  32 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  40 */     return "commands.stats.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     boolean flag;
/*     */     int i;
/*     */     CommandResultStats commandresultstats;
/*  48 */     if (args.length < 1)
/*     */     {
/*  50 */       throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     if ("entity".equals(args[0])) {
/*     */       
/*  58 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/*  62 */       if (!"block".equals(args[0]))
/*     */       {
/*  64 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/*  67 */       flag = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  72 */     if (flag) {
/*     */       
/*  74 */       if (args.length < 5)
/*     */       {
/*  76 */         throw new WrongUsageException("commands.stats.block.usage", new Object[0]);
/*     */       }
/*     */       
/*  79 */       i = 4;
/*     */     }
/*     */     else {
/*     */       
/*  83 */       if (args.length < 3)
/*     */       {
/*  85 */         throw new WrongUsageException("commands.stats.entity.usage", new Object[0]);
/*     */       }
/*     */       
/*  88 */       i = 2;
/*     */     } 
/*     */     
/*  91 */     String s = args[i++];
/*     */     
/*  93 */     if ("set".equals(s)) {
/*     */       
/*  95 */       if (args.length < i + 3)
/*     */       {
/*  97 */         if (i == 5)
/*     */         {
/*  99 */           throw new WrongUsageException("commands.stats.block.set.usage", new Object[0]);
/*     */         }
/*     */         
/* 102 */         throw new WrongUsageException("commands.stats.entity.set.usage", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 107 */       if (!"clear".equals(s))
/*     */       {
/* 109 */         throw new WrongUsageException("commands.stats.usage", new Object[0]);
/*     */       }
/*     */       
/* 112 */       if (args.length < i + 1) {
/*     */         
/* 114 */         if (i == 5)
/*     */         {
/* 116 */           throw new WrongUsageException("commands.stats.block.clear.usage", new Object[0]);
/*     */         }
/*     */         
/* 119 */         throw new WrongUsageException("commands.stats.entity.clear.usage", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     CommandResultStats.Type commandresultstats$type = CommandResultStats.Type.getTypeByName(args[i++]);
/*     */     
/* 125 */     if (commandresultstats$type == null)
/*     */     {
/* 127 */       throw new CommandException("commands.stats.failed", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 131 */     World world = sender.getEntityWorld();
/*     */ 
/*     */     
/* 134 */     if (flag) {
/*     */       
/* 136 */       BlockPos blockpos = parseBlockPos(sender, args, 1, false);
/* 137 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 139 */       if (tileentity == null)
/*     */       {
/* 141 */         throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 144 */       if (tileentity instanceof TileEntityCommandBlock)
/*     */       {
/* 146 */         commandresultstats = ((TileEntityCommandBlock)tileentity).getCommandResultStats();
/*     */       }
/*     */       else
/*     */       {
/* 150 */         if (!(tileentity instanceof TileEntitySign))
/*     */         {
/* 152 */           throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */         }
/*     */         
/* 155 */         commandresultstats = ((TileEntitySign)tileentity).getStats();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 160 */       Entity entity = getEntity(server, sender, args[1]);
/* 161 */       commandresultstats = entity.getCommandStats();
/*     */     } 
/*     */     
/* 164 */     if ("set".equals(s)) {
/*     */       
/* 166 */       String s1 = args[i++];
/* 167 */       String s2 = args[i];
/*     */       
/* 169 */       if (s1.isEmpty() || s2.isEmpty())
/*     */       {
/* 171 */         throw new CommandException("commands.stats.failed", new Object[0]);
/*     */       }
/*     */       
/* 174 */       CommandResultStats.setScoreBoardStat(commandresultstats, commandresultstats$type, s1, s2);
/* 175 */       notifyCommandListener(sender, this, "commands.stats.success", new Object[] { commandresultstats$type.getTypeName(), s2, s1 });
/*     */     }
/* 177 */     else if ("clear".equals(s)) {
/*     */       
/* 179 */       CommandResultStats.setScoreBoardStat(commandresultstats, commandresultstats$type, null, null);
/* 180 */       notifyCommandListener(sender, this, "commands.stats.cleared", new Object[] { commandresultstats$type.getTypeName() });
/*     */     } 
/*     */     
/* 183 */     if (flag) {
/*     */       
/* 185 */       BlockPos blockpos1 = parseBlockPos(sender, args, 1, false);
/* 186 */       TileEntity tileentity1 = world.getTileEntity(blockpos1);
/* 187 */       tileentity1.markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 195 */     if (args.length == 1)
/*     */     {
/* 197 */       return getListOfStringsMatchingLastWord(args, new String[] { "entity", "block" });
/*     */     }
/* 199 */     if (args.length == 2 && "entity".equals(args[0]))
/*     */     {
/* 201 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/* 203 */     if (args.length >= 2 && args.length <= 4 && "block".equals(args[0]))
/*     */     {
/* 205 */       return getTabCompletionCoordinate(args, 1, pos);
/*     */     }
/* 207 */     if ((args.length != 3 || !"entity".equals(args[0])) && (args.length != 5 || !"block".equals(args[0]))) {
/*     */       
/* 209 */       if ((args.length != 4 || !"entity".equals(args[0])) && (args.length != 6 || !"block".equals(args[0])))
/*     */       {
/* 211 */         return ((args.length != 6 || !"entity".equals(args[0])) && (args.length != 8 || !"block".equals(args[0]))) ? Collections.<String>emptyList() : getListOfStringsMatchingLastWord(args, getObjectiveNames(server));
/*     */       }
/*     */ 
/*     */       
/* 215 */       return getListOfStringsMatchingLastWord(args, CommandResultStats.Type.getTypeNames());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 220 */     return getListOfStringsMatchingLastWord(args, new String[] { "set", "clear" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> getObjectiveNames(MinecraftServer server) {
/* 226 */     Collection<ScoreObjective> collection = server.worldServerForDimension(0).getScoreboard().getScoreObjectives();
/* 227 */     List<String> list = Lists.newArrayList();
/*     */     
/* 229 */     for (ScoreObjective scoreobjective : collection) {
/*     */       
/* 231 */       if (!scoreobjective.getCriteria().isReadOnly())
/*     */       {
/* 233 */         list.add(scoreobjective.getName());
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 245 */     return (args.length > 0 && "entity".equals(args[0]) && index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */