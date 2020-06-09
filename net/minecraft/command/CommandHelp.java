/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.util.text.event.ClickEvent;
/*     */ 
/*     */ public class CommandHelp
/*     */   extends CommandBase {
/*  21 */   private static final String[] seargeSays = new String[] { "Yolo", "Ask for help on twitter", "/deop @p", "Scoreboard deleted, commands blocked", "Contact helpdesk for help", "/testfornoob @p", "/trigger warning", "Oh my god, it's full of stats", "/kill @p[name=!Searge]", "Have you tried turning it off and on again?", "Sorry, no help today" };
/*  22 */   private final Random rand = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  29 */     return "help";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  37 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  45 */     return "commands.help.usage";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  50 */     return Arrays.asList(new String[] { "?" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  58 */     if (sender instanceof net.minecraft.tileentity.CommandBlockBaseLogic) {
/*     */       
/*  60 */       sender.addChatMessage((new TextComponentString("Searge says: ")).appendText(seargeSays[this.rand.nextInt(seargeSays.length) % seargeSays.length]));
/*     */     }
/*     */     else {
/*     */       
/*  64 */       List<ICommand> list = getSortedPossibleCommands(sender, server);
/*  65 */       int i = 7;
/*  66 */       int j = (list.size() - 1) / 7;
/*  67 */       int k = 0;
/*     */ 
/*     */       
/*     */       try {
/*  71 */         k = (args.length == 0) ? 0 : (parseInt(args[0], 1, j + 1) - 1);
/*     */       }
/*  73 */       catch (NumberInvalidException numberinvalidexception) {
/*     */         
/*  75 */         Map<String, ICommand> map = getCommandMap(server);
/*  76 */         ICommand icommand = map.get(args[0]);
/*     */         
/*  78 */         if (icommand != null)
/*     */         {
/*  80 */           throw new WrongUsageException(icommand.getCommandUsage(sender), new Object[0]);
/*     */         }
/*     */         
/*  83 */         if (MathHelper.getInt(args[0], -1) == -1 && MathHelper.getInt(args[0], -2) == -2)
/*     */         {
/*  85 */           throw new CommandNotFoundException();
/*     */         }
/*     */         
/*  88 */         throw numberinvalidexception;
/*     */       } 
/*     */       
/*  91 */       int l = Math.min((k + 1) * 7, list.size());
/*  92 */       TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.help.header", new Object[] { Integer.valueOf(k + 1), Integer.valueOf(j + 1) });
/*  93 */       textcomponenttranslation1.getStyle().setColor(TextFormatting.DARK_GREEN);
/*  94 */       sender.addChatMessage((ITextComponent)textcomponenttranslation1);
/*     */       
/*  96 */       for (int i1 = k * 7; i1 < l; i1++) {
/*     */         
/*  98 */         ICommand icommand1 = list.get(i1);
/*  99 */         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(icommand1.getCommandUsage(sender), new Object[0]);
/* 100 */         textcomponenttranslation.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + icommand1.getCommandName() + " "));
/* 101 */         sender.addChatMessage((ITextComponent)textcomponenttranslation);
/*     */       } 
/*     */       
/* 104 */       if (k == 0) {
/*     */         
/* 106 */         TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.help.footer", new Object[0]);
/* 107 */         textcomponenttranslation2.getStyle().setColor(TextFormatting.GREEN);
/* 108 */         sender.addChatMessage((ITextComponent)textcomponenttranslation2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ICommand> getSortedPossibleCommands(ICommandSender sender, MinecraftServer server) {
/* 115 */     List<ICommand> list = server.getCommandManager().getPossibleCommands(sender);
/* 116 */     Collections.sort(list);
/* 117 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, ICommand> getCommandMap(MinecraftServer server) {
/* 122 */     return server.getCommandManager().getCommands();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 127 */     if (args.length == 1) {
/*     */       
/* 129 */       Set<String> set = getCommandMap(server).keySet();
/* 130 */       return getListOfStringsMatchingLastWord(args, set.<String>toArray(new String[set.size()]));
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */