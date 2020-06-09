/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandExecuteAt
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "execute";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  29 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  37 */     return "commands.execute.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 5)
/*     */     {
/*  47 */       throw new WrongUsageException("commands.execute.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  51 */     Entity entity = getEntity(server, sender, args[0], Entity.class);
/*  52 */     double d0 = parseDouble(entity.posX, args[1], false);
/*  53 */     double d1 = parseDouble(entity.posY, args[2], false);
/*  54 */     double d2 = parseDouble(entity.posZ, args[3], false);
/*     */     
/*  56 */     int i = 4;
/*     */     
/*  58 */     if ("detect".equals(args[4]) && args.length > 10) {
/*     */       
/*  60 */       World world = entity.getEntityWorld();
/*  61 */       double d3 = parseDouble(d0, args[5], false);
/*  62 */       double d4 = parseDouble(d1, args[6], false);
/*  63 */       double d5 = parseDouble(d2, args[7], false);
/*  64 */       Block block = getBlockByText(sender, args[8]);
/*  65 */       BlockPos blockpos = new BlockPos(d3, d4, d5);
/*     */       
/*  67 */       if (!world.isBlockLoaded(blockpos))
/*     */       {
/*  69 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
/*     */       }
/*     */       
/*  72 */       IBlockState iblockstate = world.getBlockState(blockpos);
/*     */       
/*  74 */       if (iblockstate.getBlock() != block)
/*     */       {
/*  76 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
/*     */       }
/*     */       
/*  79 */       if (!CommandBase.func_190791_b(block, args[9]).apply(iblockstate))
/*     */       {
/*  81 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
/*     */       }
/*     */       
/*  84 */       i = 10;
/*     */     } 
/*     */     
/*  87 */     String s = buildString(args, i);
/*  88 */     ICommandSender icommandsender = CommandSenderWrapper.func_193998_a(sender).func_193997_a(entity, new Vec3d(d0, d1, d2)).func_194001_a(server.worldServers[0].getGameRules().getBoolean("commandBlockOutput"));
/*  89 */     ICommandManager icommandmanager = server.getCommandManager();
/*     */ 
/*     */     
/*     */     try {
/*  93 */       int j = icommandmanager.executeCommand(icommandsender, s);
/*     */       
/*  95 */       if (j < 1)
/*     */       {
/*  97 */         throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { s });
/*     */       }
/*     */     }
/* 100 */     catch (Throwable var23) {
/*     */       
/* 102 */       throw new CommandException("commands.execute.failed", new Object[] { s, entity.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 109 */     if (args.length == 1)
/*     */     {
/* 111 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/* 113 */     if (args.length > 1 && args.length <= 4)
/*     */     {
/* 115 */       return getTabCompletionCoordinate(args, 1, pos);
/*     */     }
/* 117 */     if (args.length > 5 && args.length <= 8 && "detect".equals(args[4]))
/*     */     {
/* 119 */       return getTabCompletionCoordinate(args, 5, pos);
/*     */     }
/*     */ 
/*     */     
/* 123 */     return (args.length == 9 && "detect".equals(args[4])) ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 132 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandExecuteAt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */