/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandFunction
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  17 */     return "function";
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
/*  33 */     return "commands.function.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  41 */     if (args.length != 1 && args.length != 3)
/*     */     {
/*  43 */       throw new WrongUsageException("commands.function.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  47 */     ResourceLocation resourcelocation = new ResourceLocation(args[0]);
/*  48 */     FunctionObject functionobject = server.func_193030_aL().func_193058_a(resourcelocation);
/*     */     
/*  50 */     if (functionobject == null)
/*     */     {
/*  52 */       throw new CommandException("commands.function.unknown", new Object[] { resourcelocation });
/*     */     }
/*     */ 
/*     */     
/*  56 */     if (args.length == 3) {
/*     */       boolean flag;
/*  58 */       String s = args[1];
/*     */ 
/*     */       
/*  61 */       if ("if".equals(s)) {
/*     */         
/*  63 */         flag = true;
/*     */       }
/*     */       else {
/*     */         
/*  67 */         if (!"unless".equals(s))
/*     */         {
/*  69 */           throw new WrongUsageException("commands.function.usage", new Object[0]);
/*     */         }
/*     */         
/*  72 */         flag = false;
/*     */       } 
/*     */       
/*  75 */       boolean flag1 = false;
/*     */ 
/*     */       
/*     */       try {
/*  79 */         flag1 = !getEntityList(server, sender, args[2]).isEmpty();
/*     */       }
/*  81 */       catch (EntityNotFoundException entityNotFoundException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       if (flag != flag1)
/*     */       {
/*  88 */         throw new CommandException("commands.function.skipped", new Object[] { resourcelocation });
/*     */       }
/*     */     } 
/*     */     
/*  92 */     int i = server.func_193030_aL().func_194019_a(functionobject, CommandSenderWrapper.func_193998_a(sender).func_194000_i().func_193999_a(2).func_194001_a(false));
/*  93 */     notifyCommandListener(sender, this, "commands.function.success", new Object[] { resourcelocation, Integer.valueOf(i) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 100 */     if (args.length == 1)
/*     */     {
/* 102 */       return getListOfStringsMatchingLastWord(args, server.func_193030_aL().func_193066_d().keySet());
/*     */     }
/* 104 */     if (args.length == 2)
/*     */     {
/* 106 */       return getListOfStringsMatchingLastWord(args, new String[] { "if", "unless" });
/*     */     }
/*     */ 
/*     */     
/* 110 */     return (args.length == 3) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */