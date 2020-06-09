/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public abstract class CommandHandler
/*     */   implements ICommandManager {
/*  22 */   private static final Logger LOGGER = LogManager.getLogger();
/*  23 */   private final Map<String, ICommand> commandMap = Maps.newHashMap();
/*  24 */   private final Set<ICommand> commandSet = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeCommand(ICommandSender sender, String rawCommand) {
/*  33 */     rawCommand = rawCommand.trim();
/*     */     
/*  35 */     if (rawCommand.startsWith("/"))
/*     */     {
/*  37 */       rawCommand = rawCommand.substring(1);
/*     */     }
/*     */     
/*  40 */     String[] astring = rawCommand.split(" ");
/*  41 */     String s = astring[0];
/*  42 */     astring = dropFirstString(astring);
/*  43 */     ICommand icommand = this.commandMap.get(s);
/*  44 */     int i = 0;
/*     */ 
/*     */     
/*     */     try {
/*  48 */       int j = getUsernameIndex(icommand, astring);
/*     */       
/*  50 */       if (icommand == null) {
/*     */         
/*  52 */         TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.generic.notFound", new Object[0]);
/*  53 */         textcomponenttranslation1.getStyle().setColor(TextFormatting.RED);
/*  54 */         sender.addChatMessage((ITextComponent)textcomponenttranslation1);
/*     */       }
/*  56 */       else if (icommand.checkPermission(getServer(), sender)) {
/*     */         
/*  58 */         if (j > -1)
/*     */         {
/*  60 */           List<Entity> list = EntitySelector.matchEntities(sender, astring[j], Entity.class);
/*  61 */           String s1 = astring[j];
/*  62 */           sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*     */           
/*  64 */           if (list.isEmpty())
/*     */           {
/*  66 */             throw new PlayerNotFoundException("commands.generic.selector.notFound", new Object[] { astring[j] });
/*     */           }
/*     */           
/*  69 */           for (Entity entity : list) {
/*     */             
/*  71 */             astring[j] = entity.getCachedUniqueIdString();
/*     */             
/*  73 */             if (tryExecute(sender, astring, icommand, rawCommand))
/*     */             {
/*  75 */               i++;
/*     */             }
/*     */           } 
/*     */           
/*  79 */           astring[j] = s1;
/*     */         }
/*     */         else
/*     */         {
/*  83 */           sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
/*     */           
/*  85 */           if (tryExecute(sender, astring, icommand, rawCommand))
/*     */           {
/*  87 */             i++;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  93 */         TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.generic.permission", new Object[0]);
/*  94 */         textcomponenttranslation2.getStyle().setColor(TextFormatting.RED);
/*  95 */         sender.addChatMessage((ITextComponent)textcomponenttranslation2);
/*     */       }
/*     */     
/*  98 */     } catch (CommandException commandexception) {
/*     */       
/* 100 */       TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/* 101 */       textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
/* 102 */       sender.addChatMessage((ITextComponent)textcomponenttranslation);
/*     */     } 
/*     */     
/* 105 */     sender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, i);
/* 106 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean tryExecute(ICommandSender sender, String[] args, ICommand command, String input) {
/*     */     try {
/* 113 */       command.execute(getServer(), sender, args);
/* 114 */       return true;
/*     */     }
/* 116 */     catch (WrongUsageException wrongusageexception) {
/*     */       
/* 118 */       TextComponentTranslation textcomponenttranslation2 = new TextComponentTranslation("commands.generic.usage", new Object[] { new TextComponentTranslation(wrongusageexception.getMessage(), wrongusageexception.getErrorObjects()) });
/* 119 */       textcomponenttranslation2.getStyle().setColor(TextFormatting.RED);
/* 120 */       sender.addChatMessage((ITextComponent)textcomponenttranslation2);
/*     */     }
/* 122 */     catch (CommandException commandexception) {
/*     */       
/* 124 */       TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/* 125 */       textcomponenttranslation1.getStyle().setColor(TextFormatting.RED);
/* 126 */       sender.addChatMessage((ITextComponent)textcomponenttranslation1);
/*     */     }
/* 128 */     catch (Throwable throwable) {
/*     */       
/* 130 */       TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.generic.exception", new Object[0]);
/* 131 */       textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
/* 132 */       sender.addChatMessage((ITextComponent)textcomponenttranslation);
/* 133 */       LOGGER.warn("Couldn't process command: " + input, throwable);
/*     */     } 
/*     */     
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract MinecraftServer getServer();
/*     */ 
/*     */ 
/*     */   
/*     */   public ICommand registerCommand(ICommand command) {
/* 146 */     this.commandMap.put(command.getCommandName(), command);
/* 147 */     this.commandSet.add(command);
/*     */     
/* 149 */     for (String s : command.getCommandAliases()) {
/*     */       
/* 151 */       ICommand icommand = this.commandMap.get(s);
/*     */       
/* 153 */       if (icommand == null || !icommand.getCommandName().equals(s))
/*     */       {
/* 155 */         this.commandMap.put(s, command);
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] dropFirstString(String[] input) {
/* 167 */     String[] astring = new String[input.length - 1];
/* 168 */     System.arraycopy(input, 1, astring, 0, input.length - 1);
/* 169 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(ICommandSender sender, String input, @Nullable BlockPos pos) {
/* 174 */     String[] astring = input.split(" ", -1);
/* 175 */     String s = astring[0];
/*     */     
/* 177 */     if (astring.length == 1) {
/*     */       
/* 179 */       List<String> list = Lists.newArrayList();
/*     */       
/* 181 */       for (Map.Entry<String, ICommand> entry : this.commandMap.entrySet()) {
/*     */         
/* 183 */         if (CommandBase.doesStringStartWith(s, entry.getKey()) && ((ICommand)entry.getValue()).checkPermission(getServer(), sender))
/*     */         {
/* 185 */           list.add(entry.getKey());
/*     */         }
/*     */       } 
/*     */       
/* 189 */       return list;
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (astring.length > 1) {
/*     */       
/* 195 */       ICommand icommand = this.commandMap.get(s);
/*     */       
/* 197 */       if (icommand != null && icommand.checkPermission(getServer(), sender))
/*     */       {
/* 199 */         return icommand.getTabCompletionOptions(getServer(), sender, dropFirstString(astring), pos);
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ICommand> getPossibleCommands(ICommandSender sender) {
/* 209 */     List<ICommand> list = Lists.newArrayList();
/*     */     
/* 211 */     for (ICommand icommand : this.commandSet) {
/*     */       
/* 213 */       if (icommand.checkPermission(getServer(), sender))
/*     */       {
/* 215 */         list.add(icommand);
/*     */       }
/*     */     } 
/*     */     
/* 219 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, ICommand> getCommands() {
/* 224 */     return this.commandMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getUsernameIndex(ICommand command, String[] args) throws CommandException {
/* 232 */     if (command == null)
/*     */     {
/* 234 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 238 */     for (int i = 0; i < args.length; i++) {
/*     */       
/* 240 */       if (command.isUsernameIndex(args, i) && EntitySelector.matchesMultiplePlayers(args[i]))
/*     */       {
/* 242 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 246 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */