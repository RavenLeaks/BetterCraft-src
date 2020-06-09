/*      */ package net.minecraft.command.server;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.command.CommandBase;
/*      */ import net.minecraft.command.CommandException;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.EntitySelector;
/*      */ import net.minecraft.command.ICommand;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.SyntaxErrorException;
/*      */ import net.minecraft.command.WrongUsageException;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.nbt.JsonToNBT;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTException;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTUtil;
/*      */ import net.minecraft.scoreboard.IScoreCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ 
/*      */ 
/*      */ public class CommandScoreboard
/*      */   extends CommandBase
/*      */ {
/*      */   public String getCommandName() {
/*   45 */     return "scoreboard";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRequiredPermissionLevel() {
/*   53 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommandUsage(ICommandSender sender) {
/*   61 */     return "commands.scoreboard.usage";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*   69 */     if (!handleUserWildcards(server, sender, args)) {
/*      */       
/*   71 */       if (args.length < 1)
/*      */       {
/*   73 */         throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*   77 */       if ("objectives".equalsIgnoreCase(args[0])) {
/*      */         
/*   79 */         if (args.length == 1)
/*      */         {
/*   81 */           throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*      */         }
/*      */         
/*   84 */         if ("list".equalsIgnoreCase(args[1]))
/*      */         {
/*   86 */           listObjectives(sender, server);
/*      */         }
/*   88 */         else if ("add".equalsIgnoreCase(args[1]))
/*      */         {
/*   90 */           if (args.length < 4)
/*      */           {
/*   92 */             throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*      */           }
/*      */           
/*   95 */           addObjective(sender, args, 2, server);
/*      */         }
/*   97 */         else if ("remove".equalsIgnoreCase(args[1]))
/*      */         {
/*   99 */           if (args.length != 3)
/*      */           {
/*  101 */             throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  104 */           removeObjective(sender, args[2], server);
/*      */         }
/*      */         else
/*      */         {
/*  108 */           if (!"setdisplay".equalsIgnoreCase(args[1]))
/*      */           {
/*  110 */             throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
/*      */           }
/*      */           
/*  113 */           if (args.length != 3 && args.length != 4)
/*      */           {
/*  115 */             throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*      */           }
/*      */           
/*  118 */           setDisplayObjective(sender, args, 2, server);
/*      */         }
/*      */       
/*  121 */       } else if ("players".equalsIgnoreCase(args[0])) {
/*      */         
/*  123 */         if (args.length == 1)
/*      */         {
/*  125 */           throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*      */         }
/*      */         
/*  128 */         if ("list".equalsIgnoreCase(args[1]))
/*      */         {
/*  130 */           if (args.length > 3)
/*      */           {
/*  132 */             throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
/*      */           }
/*      */           
/*  135 */           listPlayers(sender, args, 2, server);
/*      */         }
/*  137 */         else if ("add".equalsIgnoreCase(args[1]))
/*      */         {
/*  139 */           if (args.length < 5)
/*      */           {
/*  141 */             throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
/*      */           }
/*      */           
/*  144 */           addPlayerScore(sender, args, 2, server);
/*      */         }
/*  146 */         else if ("remove".equalsIgnoreCase(args[1]))
/*      */         {
/*  148 */           if (args.length < 5)
/*      */           {
/*  150 */             throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  153 */           addPlayerScore(sender, args, 2, server);
/*      */         }
/*  155 */         else if ("set".equalsIgnoreCase(args[1]))
/*      */         {
/*  157 */           if (args.length < 5)
/*      */           {
/*  159 */             throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
/*      */           }
/*      */           
/*  162 */           addPlayerScore(sender, args, 2, server);
/*      */         }
/*  164 */         else if ("reset".equalsIgnoreCase(args[1]))
/*      */         {
/*  166 */           if (args.length != 3 && args.length != 4)
/*      */           {
/*  168 */             throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
/*      */           }
/*      */           
/*  171 */           resetPlayerScore(sender, args, 2, server);
/*      */         }
/*  173 */         else if ("enable".equalsIgnoreCase(args[1]))
/*      */         {
/*  175 */           if (args.length != 4)
/*      */           {
/*  177 */             throw new WrongUsageException("commands.scoreboard.players.enable.usage", new Object[0]);
/*      */           }
/*      */           
/*  180 */           enablePlayerTrigger(sender, args, 2, server);
/*      */         }
/*  182 */         else if ("test".equalsIgnoreCase(args[1]))
/*      */         {
/*  184 */           if (args.length != 5 && args.length != 6)
/*      */           {
/*  186 */             throw new WrongUsageException("commands.scoreboard.players.test.usage", new Object[0]);
/*      */           }
/*      */           
/*  189 */           testPlayerScore(sender, args, 2, server);
/*      */         }
/*  191 */         else if ("operation".equalsIgnoreCase(args[1]))
/*      */         {
/*  193 */           if (args.length != 7)
/*      */           {
/*  195 */             throw new WrongUsageException("commands.scoreboard.players.operation.usage", new Object[0]);
/*      */           }
/*      */           
/*  198 */           applyPlayerOperation(sender, args, 2, server);
/*      */         }
/*      */         else
/*      */         {
/*  202 */           if (!"tag".equalsIgnoreCase(args[1]))
/*      */           {
/*  204 */             throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
/*      */           }
/*      */           
/*  207 */           if (args.length < 4)
/*      */           {
/*  209 */             throw new WrongUsageException("commands.scoreboard.players.tag.usage", new Object[0]);
/*      */           }
/*      */           
/*  212 */           applyPlayerTag(server, sender, args, 2);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  217 */         if (!"teams".equalsIgnoreCase(args[0]))
/*      */         {
/*  219 */           throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
/*      */         }
/*      */         
/*  222 */         if (args.length == 1)
/*      */         {
/*  224 */           throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*      */         }
/*      */         
/*  227 */         if ("list".equalsIgnoreCase(args[1])) {
/*      */           
/*  229 */           if (args.length > 3)
/*      */           {
/*  231 */             throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
/*      */           }
/*      */           
/*  234 */           listTeams(sender, args, 2, server);
/*      */         }
/*  236 */         else if ("add".equalsIgnoreCase(args[1])) {
/*      */           
/*  238 */           if (args.length < 3)
/*      */           {
/*  240 */             throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*      */           }
/*      */           
/*  243 */           addTeam(sender, args, 2, server);
/*      */         }
/*  245 */         else if ("remove".equalsIgnoreCase(args[1])) {
/*      */           
/*  247 */           if (args.length != 3)
/*      */           {
/*  249 */             throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
/*      */           }
/*      */           
/*  252 */           removeTeam(sender, args, 2, server);
/*      */         }
/*  254 */         else if ("empty".equalsIgnoreCase(args[1])) {
/*      */           
/*  256 */           if (args.length != 3)
/*      */           {
/*  258 */             throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
/*      */           }
/*      */           
/*  261 */           emptyTeam(sender, args, 2, server);
/*      */         }
/*  263 */         else if ("join".equalsIgnoreCase(args[1])) {
/*      */           
/*  265 */           if (args.length < 4 && (args.length != 3 || !(sender instanceof net.minecraft.entity.player.EntityPlayer)))
/*      */           {
/*  267 */             throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
/*      */           }
/*      */           
/*  270 */           joinTeam(sender, args, 2, server);
/*      */         }
/*  272 */         else if ("leave".equalsIgnoreCase(args[1])) {
/*      */           
/*  274 */           if (args.length < 3 && !(sender instanceof net.minecraft.entity.player.EntityPlayer))
/*      */           {
/*  276 */             throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
/*      */           }
/*      */           
/*  279 */           leaveTeam(sender, args, 2, server);
/*      */         }
/*      */         else {
/*      */           
/*  283 */           if (!"option".equalsIgnoreCase(args[1]))
/*      */           {
/*  285 */             throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
/*      */           }
/*      */           
/*  288 */           if (args.length != 4 && args.length != 5)
/*      */           {
/*  290 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */           }
/*      */           
/*  293 */           setTeamOption(sender, args, 2, server);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean handleUserWildcards(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  302 */     int i = -1;
/*      */     
/*  304 */     for (int j = 0; j < args.length; j++) {
/*      */       
/*  306 */       if (isUsernameIndex(args, j) && "*".equals(args[j])) {
/*      */         
/*  308 */         if (i >= 0)
/*      */         {
/*  310 */           throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
/*      */         }
/*      */         
/*  313 */         i = j;
/*      */       } 
/*      */     } 
/*      */     
/*  317 */     if (i < 0)
/*      */     {
/*  319 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  323 */     List<String> list1 = Lists.newArrayList(getScoreboard(server).getObjectiveNames());
/*  324 */     String s = args[i];
/*  325 */     List<String> list = Lists.newArrayList();
/*      */     
/*  327 */     for (String s1 : list1) {
/*      */       
/*  329 */       args[i] = s1;
/*      */ 
/*      */       
/*      */       try {
/*  333 */         execute(server, sender, args);
/*  334 */         list.add(s1);
/*      */       }
/*  336 */       catch (CommandException commandexception) {
/*      */         
/*  338 */         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
/*  339 */         textcomponenttranslation.getStyle().setColor(TextFormatting.RED);
/*  340 */         sender.addChatMessage((ITextComponent)textcomponenttranslation);
/*      */       } 
/*      */     } 
/*      */     
/*  344 */     args[i] = s;
/*  345 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, list.size());
/*      */     
/*  347 */     if (list.isEmpty())
/*      */     {
/*  349 */       throw new WrongUsageException("commands.scoreboard.allMatchesFailed", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  353 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Scoreboard getScoreboard(MinecraftServer server) {
/*  360 */     return server.worldServerForDimension(0).getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   protected ScoreObjective convertToObjective(String name, boolean forWrite, MinecraftServer server) throws CommandException {
/*  365 */     Scoreboard scoreboard = getScoreboard(server);
/*  366 */     ScoreObjective scoreobjective = scoreboard.getObjective(name);
/*      */     
/*  368 */     if (scoreobjective == null)
/*      */     {
/*  370 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { name });
/*      */     }
/*  372 */     if (forWrite && scoreobjective.getCriteria().isReadOnly())
/*      */     {
/*  374 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { name });
/*      */     }
/*      */ 
/*      */     
/*  378 */     return scoreobjective;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ScorePlayerTeam convertToTeam(String p_184915_1_, MinecraftServer server) throws CommandException {
/*  384 */     Scoreboard scoreboard = getScoreboard(server);
/*  385 */     ScorePlayerTeam scoreplayerteam = scoreboard.getTeam(p_184915_1_);
/*      */     
/*  387 */     if (scoreplayerteam == null)
/*      */     {
/*  389 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { p_184915_1_ });
/*      */     }
/*      */ 
/*      */     
/*  393 */     return scoreplayerteam;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addObjective(ICommandSender sender, String[] commandArgs, int argStartIndex, MinecraftServer server) throws CommandException {
/*  399 */     String s = commandArgs[argStartIndex++];
/*  400 */     String s1 = commandArgs[argStartIndex++];
/*  401 */     Scoreboard scoreboard = getScoreboard(server);
/*  402 */     IScoreCriteria iscorecriteria = (IScoreCriteria)IScoreCriteria.INSTANCES.get(s1);
/*      */     
/*  404 */     if (iscorecriteria == null)
/*      */     {
/*  406 */       throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { s1 });
/*      */     }
/*  408 */     if (scoreboard.getObjective(s) != null)
/*      */     {
/*  410 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { s });
/*      */     }
/*  412 */     if (s.length() > 16)
/*      */     {
/*  414 */       throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { s, Integer.valueOf(16) });
/*      */     }
/*  416 */     if (s.isEmpty())
/*      */     {
/*  418 */       throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  422 */     if (commandArgs.length > argStartIndex) {
/*      */       
/*  424 */       String s2 = getChatComponentFromNthArg(sender, commandArgs, argStartIndex).getUnformattedText();
/*      */       
/*  426 */       if (s2.length() > 32)
/*      */       {
/*  428 */         throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { s2, Integer.valueOf(32) });
/*      */       }
/*      */       
/*  431 */       if (s2.isEmpty())
/*      */       {
/*  433 */         scoreboard.addScoreObjective(s, iscorecriteria);
/*      */       }
/*      */       else
/*      */       {
/*  437 */         scoreboard.addScoreObjective(s, iscorecriteria).setDisplayName(s2);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  442 */       scoreboard.addScoreObjective(s, iscorecriteria);
/*      */     } 
/*      */     
/*  445 */     notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.objectives.add.success", new Object[] { s });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addTeam(ICommandSender sender, String[] p_184910_2_, int p_184910_3_, MinecraftServer server) throws CommandException {
/*  451 */     String s = p_184910_2_[p_184910_3_++];
/*  452 */     Scoreboard scoreboard = getScoreboard(server);
/*      */     
/*  454 */     if (scoreboard.getTeam(s) != null)
/*      */     {
/*  456 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { s });
/*      */     }
/*  458 */     if (s.length() > 16)
/*      */     {
/*  460 */       throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { s, Integer.valueOf(16) });
/*      */     }
/*  462 */     if (s.isEmpty())
/*      */     {
/*  464 */       throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  468 */     if (p_184910_2_.length > p_184910_3_) {
/*      */       
/*  470 */       String s1 = getChatComponentFromNthArg(sender, p_184910_2_, p_184910_3_).getUnformattedText();
/*      */       
/*  472 */       if (s1.length() > 32)
/*      */       {
/*  474 */         throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { s1, Integer.valueOf(32) });
/*      */       }
/*      */       
/*  477 */       if (s1.isEmpty())
/*      */       {
/*  479 */         scoreboard.createTeam(s);
/*      */       }
/*      */       else
/*      */       {
/*  483 */         scoreboard.createTeam(s).setTeamName(s1);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  488 */       scoreboard.createTeam(s);
/*      */     } 
/*      */     
/*  491 */     notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.teams.add.success", new Object[] { s });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setTeamOption(ICommandSender sender, String[] p_184923_2_, int p_184923_3_, MinecraftServer server) throws CommandException {
/*  497 */     ScorePlayerTeam scoreplayerteam = convertToTeam(p_184923_2_[p_184923_3_++], server);
/*      */     
/*  499 */     if (scoreplayerteam != null) {
/*      */       
/*  501 */       String s = p_184923_2_[p_184923_3_++].toLowerCase(Locale.ROOT);
/*      */       
/*  503 */       if (!"color".equalsIgnoreCase(s) && !"friendlyfire".equalsIgnoreCase(s) && !"seeFriendlyInvisibles".equalsIgnoreCase(s) && !"nametagVisibility".equalsIgnoreCase(s) && !"deathMessageVisibility".equalsIgnoreCase(s) && !"collisionRule".equalsIgnoreCase(s))
/*      */       {
/*  505 */         throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */       }
/*  507 */       if (p_184923_2_.length == 4) {
/*      */         
/*  509 */         if ("color".equalsIgnoreCase(s))
/*      */         {
/*  511 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(TextFormatting.getValidValues(true, false)) });
/*      */         }
/*  513 */         if (!"friendlyfire".equalsIgnoreCase(s) && !"seeFriendlyInvisibles".equalsIgnoreCase(s)) {
/*      */           
/*  515 */           if (!"nametagVisibility".equalsIgnoreCase(s) && !"deathMessageVisibility".equalsIgnoreCase(s)) {
/*      */             
/*  517 */             if ("collisionRule".equalsIgnoreCase(s))
/*      */             {
/*  519 */               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.CollisionRule.getNames()) });
/*      */             }
/*      */ 
/*      */             
/*  523 */             throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  528 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.getNames()) });
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  533 */         throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  538 */       String s1 = p_184923_2_[p_184923_3_];
/*      */       
/*  540 */       if ("color".equalsIgnoreCase(s)) {
/*      */         
/*  542 */         TextFormatting textformatting = TextFormatting.getValueByName(s1);
/*      */         
/*  544 */         if (textformatting == null || textformatting.isFancyStyling())
/*      */         {
/*  546 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(TextFormatting.getValidValues(true, false)) });
/*      */         }
/*      */         
/*  549 */         scoreplayerteam.setChatFormat(textformatting);
/*  550 */         scoreplayerteam.setNamePrefix(textformatting.toString());
/*  551 */         scoreplayerteam.setNameSuffix(TextFormatting.RESET.toString());
/*      */       }
/*  553 */       else if ("friendlyfire".equalsIgnoreCase(s)) {
/*      */         
/*  555 */         if (!"true".equalsIgnoreCase(s1) && !"false".equalsIgnoreCase(s1))
/*      */         {
/*  557 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */         }
/*      */         
/*  560 */         scoreplayerteam.setAllowFriendlyFire("true".equalsIgnoreCase(s1));
/*      */       }
/*  562 */       else if ("seeFriendlyInvisibles".equalsIgnoreCase(s)) {
/*      */         
/*  564 */         if (!"true".equalsIgnoreCase(s1) && !"false".equalsIgnoreCase(s1))
/*      */         {
/*  566 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceStringFromCollection(Arrays.asList(new String[] { "true", "false" })) });
/*      */         }
/*      */         
/*  569 */         scoreplayerteam.setSeeFriendlyInvisiblesEnabled("true".equalsIgnoreCase(s1));
/*      */       }
/*  571 */       else if ("nametagVisibility".equalsIgnoreCase(s)) {
/*      */         
/*  573 */         Team.EnumVisible team$enumvisible = Team.EnumVisible.getByName(s1);
/*      */         
/*  575 */         if (team$enumvisible == null)
/*      */         {
/*  577 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.getNames()) });
/*      */         }
/*      */         
/*  580 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*  582 */       else if ("deathMessageVisibility".equalsIgnoreCase(s)) {
/*      */         
/*  584 */         Team.EnumVisible team$enumvisible1 = Team.EnumVisible.getByName(s1);
/*      */         
/*  586 */         if (team$enumvisible1 == null)
/*      */         {
/*  588 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.EnumVisible.getNames()) });
/*      */         }
/*      */         
/*  591 */         scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
/*      */       }
/*  593 */       else if ("collisionRule".equalsIgnoreCase(s)) {
/*      */         
/*  595 */         Team.CollisionRule team$collisionrule = Team.CollisionRule.getByName(s1);
/*      */         
/*  597 */         if (team$collisionrule == null)
/*      */         {
/*  599 */           throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { s, joinNiceString(Team.CollisionRule.getNames()) });
/*      */         }
/*      */         
/*  602 */         scoreplayerteam.setCollisionRule(team$collisionrule);
/*      */       } 
/*      */       
/*  605 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.teams.option.success", new Object[] { s, scoreplayerteam.getRegisteredName(), s1 });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeTeam(ICommandSender sender, String[] p_184921_2_, int p_184921_3_, MinecraftServer server) throws CommandException {
/*  612 */     Scoreboard scoreboard = getScoreboard(server);
/*  613 */     ScorePlayerTeam scoreplayerteam = convertToTeam(p_184921_2_[p_184921_3_], server);
/*      */     
/*  615 */     if (scoreplayerteam != null) {
/*      */       
/*  617 */       scoreboard.removeTeam(scoreplayerteam);
/*  618 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.teams.remove.success", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void listTeams(ICommandSender sender, String[] p_184922_2_, int p_184922_3_, MinecraftServer server) throws CommandException {
/*  624 */     Scoreboard scoreboard = getScoreboard(server);
/*      */     
/*  626 */     if (p_184922_2_.length > p_184922_3_) {
/*      */       
/*  628 */       ScorePlayerTeam scoreplayerteam = convertToTeam(p_184922_2_[p_184922_3_], server);
/*      */       
/*  630 */       if (scoreplayerteam == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  635 */       Collection<String> collection = scoreplayerteam.getMembershipCollection();
/*  636 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*      */       
/*  638 */       if (collection.isEmpty())
/*      */       {
/*  640 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */       }
/*      */       
/*  643 */       TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*  644 */       textcomponenttranslation.getStyle().setColor(TextFormatting.DARK_GREEN);
/*  645 */       sender.addChatMessage((ITextComponent)textcomponenttranslation);
/*  646 */       sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString(collection.toArray())));
/*      */     }
/*      */     else {
/*      */       
/*  650 */       Collection<ScorePlayerTeam> collection1 = scoreboard.getTeams();
/*  651 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection1.size());
/*      */       
/*  653 */       if (collection1.isEmpty())
/*      */       {
/*  655 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/*      */       }
/*      */       
/*  658 */       TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(collection1.size()) });
/*  659 */       textcomponenttranslation1.getStyle().setColor(TextFormatting.DARK_GREEN);
/*  660 */       sender.addChatMessage((ITextComponent)textcomponenttranslation1);
/*      */       
/*  662 */       for (ScorePlayerTeam scoreplayerteam1 : collection1) {
/*      */         
/*  664 */         sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.scoreboard.teams.list.entry", new Object[] { scoreplayerteam1.getRegisteredName(), scoreplayerteam1.getTeamName(), Integer.valueOf(scoreplayerteam1.getMembershipCollection().size()) }));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void joinTeam(ICommandSender sender, String[] p_184916_2_, int p_184916_3_, MinecraftServer server) throws CommandException {
/*  671 */     Scoreboard scoreboard = getScoreboard(server);
/*  672 */     String s = p_184916_2_[p_184916_3_++];
/*  673 */     Set<String> set = Sets.newHashSet();
/*  674 */     Set<String> set1 = Sets.newHashSet();
/*      */     
/*  676 */     if (sender instanceof net.minecraft.entity.player.EntityPlayer && p_184916_3_ == p_184916_2_.length) {
/*      */       
/*  678 */       String s4 = getCommandSenderAsPlayer(sender).getName();
/*      */       
/*  680 */       if (scoreboard.addPlayerToTeam(s4, s))
/*      */       {
/*  682 */         set.add(s4);
/*      */       }
/*      */       else
/*      */       {
/*  686 */         set1.add(s4);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  691 */       while (p_184916_3_ < p_184916_2_.length) {
/*      */         
/*  693 */         String s1 = p_184916_2_[p_184916_3_++];
/*      */         
/*  695 */         if (EntitySelector.hasArguments(s1)) {
/*      */           
/*  697 */           for (Entity entity : getEntityList(server, sender, s1)) {
/*      */             
/*  699 */             String s3 = getEntityName(server, sender, entity.getCachedUniqueIdString());
/*      */             
/*  701 */             if (scoreboard.addPlayerToTeam(s3, s)) {
/*      */               
/*  703 */               set.add(s3);
/*      */               
/*      */               continue;
/*      */             } 
/*  707 */             set1.add(s3);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  713 */         String s2 = getEntityName(server, sender, s1);
/*      */         
/*  715 */         if (scoreboard.addPlayerToTeam(s2, s)) {
/*      */           
/*  717 */           set.add(s2);
/*      */           
/*      */           continue;
/*      */         } 
/*  721 */         set1.add(s2);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  727 */     if (!set.isEmpty()) {
/*      */       
/*  729 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/*  730 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(set.size()), s, joinNiceString(set.toArray((Object[])new String[set.size()])) });
/*      */     } 
/*      */     
/*  733 */     if (!set1.isEmpty())
/*      */     {
/*  735 */       throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(set1.size()), s, joinNiceString(set1.toArray(new String[set1.size()])) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void leaveTeam(ICommandSender sender, String[] p_184911_2_, int p_184911_3_, MinecraftServer server) throws CommandException {
/*  741 */     Scoreboard scoreboard = getScoreboard(server);
/*  742 */     Set<String> set = Sets.newHashSet();
/*  743 */     Set<String> set1 = Sets.newHashSet();
/*      */     
/*  745 */     if (sender instanceof net.minecraft.entity.player.EntityPlayer && p_184911_3_ == p_184911_2_.length) {
/*      */       
/*  747 */       String s3 = getCommandSenderAsPlayer(sender).getName();
/*      */       
/*  749 */       if (scoreboard.removePlayerFromTeams(s3))
/*      */       {
/*  751 */         set.add(s3);
/*      */       }
/*      */       else
/*      */       {
/*  755 */         set1.add(s3);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  760 */       while (p_184911_3_ < p_184911_2_.length) {
/*      */         
/*  762 */         String s = p_184911_2_[p_184911_3_++];
/*      */         
/*  764 */         if (EntitySelector.hasArguments(s)) {
/*      */           
/*  766 */           for (Entity entity : getEntityList(server, sender, s)) {
/*      */             
/*  768 */             String s2 = getEntityName(server, sender, entity.getCachedUniqueIdString());
/*      */             
/*  770 */             if (scoreboard.removePlayerFromTeams(s2)) {
/*      */               
/*  772 */               set.add(s2);
/*      */               
/*      */               continue;
/*      */             } 
/*  776 */             set1.add(s2);
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/*  782 */         String s1 = getEntityName(server, sender, s);
/*      */         
/*  784 */         if (scoreboard.removePlayerFromTeams(s1)) {
/*      */           
/*  786 */           set.add(s1);
/*      */           
/*      */           continue;
/*      */         } 
/*  790 */         set1.add(s1);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  796 */     if (!set.isEmpty()) {
/*      */       
/*  798 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, set.size());
/*  799 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(set.size()), joinNiceString(set.toArray((Object[])new String[set.size()])) });
/*      */     } 
/*      */     
/*  802 */     if (!set1.isEmpty())
/*      */     {
/*  804 */       throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(set1.size()), joinNiceString(set1.toArray(new String[set1.size()])) });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void emptyTeam(ICommandSender sender, String[] p_184917_2_, int p_184917_3_, MinecraftServer server) throws CommandException {
/*  810 */     Scoreboard scoreboard = getScoreboard(server);
/*  811 */     ScorePlayerTeam scoreplayerteam = convertToTeam(p_184917_2_[p_184917_3_], server);
/*      */     
/*  813 */     if (scoreplayerteam != null) {
/*      */       
/*  815 */       Collection<String> collection = Lists.newArrayList(scoreplayerteam.getMembershipCollection());
/*  816 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, collection.size());
/*      */       
/*  818 */       if (collection.isEmpty())
/*      */       {
/*  820 */         throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { scoreplayerteam.getRegisteredName() });
/*      */       }
/*      */ 
/*      */       
/*  824 */       for (String s : collection)
/*      */       {
/*  826 */         scoreboard.removePlayerFromTeam(s, scoreplayerteam);
/*      */       }
/*      */       
/*  829 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(collection.size()), scoreplayerteam.getRegisteredName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeObjective(ICommandSender sender, String name, MinecraftServer server) throws CommandException {
/*  836 */     Scoreboard scoreboard = getScoreboard(server);
/*  837 */     ScoreObjective scoreobjective = convertToObjective(name, false, server);
/*  838 */     scoreboard.removeObjective(scoreobjective);
/*  839 */     notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.objectives.remove.success", new Object[] { name });
/*      */   }
/*      */ 
/*      */   
/*      */   protected void listObjectives(ICommandSender sender, MinecraftServer server) throws CommandException {
/*  844 */     Scoreboard scoreboard = getScoreboard(server);
/*  845 */     Collection<ScoreObjective> collection = scoreboard.getScoreObjectives();
/*      */     
/*  847 */     if (collection.isEmpty())
/*      */     {
/*  849 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/*      */     }
/*      */ 
/*      */     
/*  853 */     TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(collection.size()) });
/*  854 */     textcomponenttranslation.getStyle().setColor(TextFormatting.DARK_GREEN);
/*  855 */     sender.addChatMessage((ITextComponent)textcomponenttranslation);
/*      */     
/*  857 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/*  859 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.scoreboard.objectives.list.entry", new Object[] { scoreobjective.getName(), scoreobjective.getDisplayName(), scoreobjective.getCriteria().getName() }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDisplayObjective(ICommandSender sender, String[] p_184919_2_, int p_184919_3_, MinecraftServer server) throws CommandException {
/*  866 */     Scoreboard scoreboard = getScoreboard(server);
/*  867 */     String s = p_184919_2_[p_184919_3_++];
/*  868 */     int i = Scoreboard.getObjectiveDisplaySlotNumber(s);
/*  869 */     ScoreObjective scoreobjective = null;
/*      */     
/*  871 */     if (p_184919_2_.length == 4)
/*      */     {
/*  873 */       scoreobjective = convertToObjective(p_184919_2_[p_184919_3_], false, server);
/*      */     }
/*      */     
/*  876 */     if (i < 0)
/*      */     {
/*  878 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { s });
/*      */     }
/*      */ 
/*      */     
/*  882 */     scoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
/*      */     
/*  884 */     if (scoreobjective != null) {
/*      */       
/*  886 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getObjectiveDisplaySlot(i), scoreobjective.getName() });
/*      */     }
/*      */     else {
/*      */       
/*  890 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getObjectiveDisplaySlot(i) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void listPlayers(ICommandSender sender, String[] p_184920_2_, int p_184920_3_, MinecraftServer server) throws CommandException {
/*  897 */     Scoreboard scoreboard = getScoreboard(server);
/*      */     
/*  899 */     if (p_184920_2_.length > p_184920_3_) {
/*      */       
/*  901 */       String s = getEntityName(server, sender, p_184920_2_[p_184920_3_]);
/*  902 */       Map<ScoreObjective, Score> map = scoreboard.getObjectivesForEntity(s);
/*  903 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, map.size());
/*      */       
/*  905 */       if (map.isEmpty())
/*      */       {
/*  907 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { s });
/*      */       }
/*      */       
/*  910 */       TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(map.size()), s });
/*  911 */       textcomponenttranslation.getStyle().setColor(TextFormatting.DARK_GREEN);
/*  912 */       sender.addChatMessage((ITextComponent)textcomponenttranslation);
/*      */       
/*  914 */       for (Score score : map.values())
/*      */       {
/*  916 */         sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(score.getScorePoints()), score.getObjective().getDisplayName(), score.getObjective().getName() }));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  921 */       Collection<String> collection = scoreboard.getObjectiveNames();
/*  922 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, collection.size());
/*      */       
/*  924 */       if (collection.isEmpty())
/*      */       {
/*  926 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/*      */       }
/*      */       
/*  929 */       TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(collection.size()) });
/*  930 */       textcomponenttranslation1.getStyle().setColor(TextFormatting.DARK_GREEN);
/*  931 */       sender.addChatMessage((ITextComponent)textcomponenttranslation1);
/*  932 */       sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString(collection.toArray())));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addPlayerScore(ICommandSender sender, String[] p_184918_2_, int p_184918_3_, MinecraftServer server) throws CommandException {
/*  938 */     String s = p_184918_2_[p_184918_3_ - 1];
/*  939 */     int i = p_184918_3_;
/*  940 */     String s1 = getEntityName(server, sender, p_184918_2_[p_184918_3_++]);
/*      */     
/*  942 */     if (s1.length() > 40)
/*      */     {
/*  944 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s1, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/*  948 */     ScoreObjective scoreobjective = convertToObjective(p_184918_2_[p_184918_3_++], true, server);
/*  949 */     int j = "set".equalsIgnoreCase(s) ? parseInt(p_184918_2_[p_184918_3_++]) : parseInt(p_184918_2_[p_184918_3_++], 0);
/*      */     
/*  951 */     if (p_184918_2_.length > p_184918_3_) {
/*      */       
/*  953 */       Entity entity = getEntity(server, sender, p_184918_2_[i]);
/*      */ 
/*      */       
/*      */       try {
/*  957 */         NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(buildString(p_184918_2_, p_184918_3_));
/*  958 */         NBTTagCompound nbttagcompound1 = entityToNBT(entity);
/*      */         
/*  960 */         if (!NBTUtil.areNBTEquals((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*      */         {
/*  962 */           throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { s1 });
/*      */         }
/*      */       }
/*  965 */       catch (NBTException nbtexception) {
/*      */         
/*  967 */         throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { nbtexception.getMessage() });
/*      */       } 
/*      */     } 
/*      */     
/*  971 */     Scoreboard scoreboard = getScoreboard(server);
/*  972 */     Score score = scoreboard.getOrCreateScore(s1, scoreobjective);
/*      */     
/*  974 */     if ("set".equalsIgnoreCase(s)) {
/*      */       
/*  976 */       score.setScorePoints(j);
/*      */     }
/*  978 */     else if ("add".equalsIgnoreCase(s)) {
/*      */       
/*  980 */       score.increaseScore(j);
/*      */     }
/*      */     else {
/*      */       
/*  984 */       score.decreaseScore(j);
/*      */     } 
/*      */     
/*  987 */     notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.set.success", new Object[] { scoreobjective.getName(), s1, Integer.valueOf(score.getScorePoints()) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPlayerScore(ICommandSender sender, String[] p_184912_2_, int p_184912_3_, MinecraftServer server) throws CommandException {
/*  993 */     Scoreboard scoreboard = getScoreboard(server);
/*  994 */     String s = getEntityName(server, sender, p_184912_2_[p_184912_3_++]);
/*      */     
/*  996 */     if (p_184912_2_.length > p_184912_3_) {
/*      */       
/*  998 */       ScoreObjective scoreobjective = convertToObjective(p_184912_2_[p_184912_3_++], false, server);
/*  999 */       scoreboard.removeObjectiveFromEntity(s, scoreobjective);
/* 1000 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.resetscore.success", new Object[] { scoreobjective.getName(), s });
/*      */     }
/*      */     else {
/*      */       
/* 1004 */       scoreboard.removeObjectiveFromEntity(s, null);
/* 1005 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.reset.success", new Object[] { s });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void enablePlayerTrigger(ICommandSender sender, String[] p_184914_2_, int p_184914_3_, MinecraftServer server) throws CommandException {
/* 1011 */     Scoreboard scoreboard = getScoreboard(server);
/* 1012 */     String s = getPlayerName(server, sender, p_184914_2_[p_184914_3_++]);
/*      */     
/* 1014 */     if (s.length() > 40)
/*      */     {
/* 1016 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1020 */     ScoreObjective scoreobjective = convertToObjective(p_184914_2_[p_184914_3_], false, server);
/*      */     
/* 1022 */     if (scoreobjective.getCriteria() != IScoreCriteria.TRIGGER)
/*      */     {
/* 1024 */       throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { scoreobjective.getName() });
/*      */     }
/*      */ 
/*      */     
/* 1028 */     Score score = scoreboard.getOrCreateScore(s, scoreobjective);
/* 1029 */     score.setLocked(false);
/* 1030 */     notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.enable.success", new Object[] { scoreobjective.getName(), s });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void testPlayerScore(ICommandSender sender, String[] p_184907_2_, int p_184907_3_, MinecraftServer server) throws CommandException {
/* 1037 */     Scoreboard scoreboard = getScoreboard(server);
/* 1038 */     String s = getEntityName(server, sender, p_184907_2_[p_184907_3_++]);
/*      */     
/* 1040 */     if (s.length() > 40)
/*      */     {
/* 1042 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1046 */     ScoreObjective scoreobjective = convertToObjective(p_184907_2_[p_184907_3_++], false, server);
/*      */     
/* 1048 */     if (!scoreboard.entityHasObjective(s, scoreobjective))
/*      */     {
/* 1050 */       throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { scoreobjective.getName(), s });
/*      */     }
/*      */ 
/*      */     
/* 1054 */     int i = p_184907_2_[p_184907_3_].equals("*") ? Integer.MIN_VALUE : parseInt(p_184907_2_[p_184907_3_]);
/* 1055 */     p_184907_3_++;
/* 1056 */     int j = (p_184907_3_ < p_184907_2_.length && !p_184907_2_[p_184907_3_].equals("*")) ? parseInt(p_184907_2_[p_184907_3_], i) : Integer.MAX_VALUE;
/* 1057 */     Score score = scoreboard.getOrCreateScore(s, scoreobjective);
/*      */     
/* 1059 */     if (score.getScorePoints() >= i && score.getScorePoints() <= j) {
/*      */       
/* 1061 */       notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.test.success", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*      */     }
/*      */     else {
/*      */       
/* 1065 */       throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { Integer.valueOf(score.getScorePoints()), Integer.valueOf(i), Integer.valueOf(j) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyPlayerOperation(ICommandSender sender, String[] p_184906_2_, int p_184906_3_, MinecraftServer server) throws CommandException {
/* 1073 */     Scoreboard scoreboard = getScoreboard(server);
/* 1074 */     String s = getEntityName(server, sender, p_184906_2_[p_184906_3_++]);
/* 1075 */     ScoreObjective scoreobjective = convertToObjective(p_184906_2_[p_184906_3_++], true, server);
/* 1076 */     String s1 = p_184906_2_[p_184906_3_++];
/* 1077 */     String s2 = getEntityName(server, sender, p_184906_2_[p_184906_3_++]);
/* 1078 */     ScoreObjective scoreobjective1 = convertToObjective(p_184906_2_[p_184906_3_], false, server);
/*      */     
/* 1080 */     if (s.length() > 40)
/*      */     {
/* 1082 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s, Integer.valueOf(40) });
/*      */     }
/* 1084 */     if (s2.length() > 40)
/*      */     {
/* 1086 */       throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", new Object[] { s2, Integer.valueOf(40) });
/*      */     }
/*      */ 
/*      */     
/* 1090 */     Score score = scoreboard.getOrCreateScore(s, scoreobjective);
/*      */     
/* 1092 */     if (!scoreboard.entityHasObjective(s2, scoreobjective1))
/*      */     {
/* 1094 */       throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { scoreobjective1.getName(), s2 });
/*      */     }
/*      */ 
/*      */     
/* 1098 */     Score score1 = scoreboard.getOrCreateScore(s2, scoreobjective1);
/*      */     
/* 1100 */     if ("+=".equals(s1)) {
/*      */       
/* 1102 */       score.setScorePoints(score.getScorePoints() + score1.getScorePoints());
/*      */     }
/* 1104 */     else if ("-=".equals(s1)) {
/*      */       
/* 1106 */       score.setScorePoints(score.getScorePoints() - score1.getScorePoints());
/*      */     }
/* 1108 */     else if ("*=".equals(s1)) {
/*      */       
/* 1110 */       score.setScorePoints(score.getScorePoints() * score1.getScorePoints());
/*      */     }
/* 1112 */     else if ("/=".equals(s1)) {
/*      */       
/* 1114 */       if (score1.getScorePoints() != 0)
/*      */       {
/* 1116 */         score.setScorePoints(score.getScorePoints() / score1.getScorePoints());
/*      */       }
/*      */     }
/* 1119 */     else if ("%=".equals(s1)) {
/*      */       
/* 1121 */       if (score1.getScorePoints() != 0)
/*      */       {
/* 1123 */         score.setScorePoints(score.getScorePoints() % score1.getScorePoints());
/*      */       }
/*      */     }
/* 1126 */     else if ("=".equals(s1)) {
/*      */       
/* 1128 */       score.setScorePoints(score1.getScorePoints());
/*      */     }
/* 1130 */     else if ("<".equals(s1)) {
/*      */       
/* 1132 */       score.setScorePoints(Math.min(score.getScorePoints(), score1.getScorePoints()));
/*      */     }
/* 1134 */     else if (">".equals(s1)) {
/*      */       
/* 1136 */       score.setScorePoints(Math.max(score.getScorePoints(), score1.getScorePoints()));
/*      */     }
/*      */     else {
/*      */       
/* 1140 */       if (!"><".equals(s1))
/*      */       {
/* 1142 */         throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { s1 });
/*      */       }
/*      */       
/* 1145 */       int i = score.getScorePoints();
/* 1146 */       score.setScorePoints(score1.getScorePoints());
/* 1147 */       score1.setScorePoints(i);
/*      */     } 
/*      */     
/* 1150 */     notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.operation.success", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyPlayerTag(MinecraftServer server, ICommandSender sender, String[] p_184924_3_, int p_184924_4_) throws CommandException {
/* 1157 */     String s = getEntityName(server, sender, p_184924_3_[p_184924_4_]);
/* 1158 */     Entity entity = getEntity(server, sender, p_184924_3_[p_184924_4_++]);
/* 1159 */     String s1 = p_184924_3_[p_184924_4_++];
/* 1160 */     Set<String> set = entity.getTags();
/*      */     
/* 1162 */     if ("list".equals(s1)) {
/*      */       
/* 1164 */       if (!set.isEmpty()) {
/*      */         
/* 1166 */         TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.scoreboard.players.tag.list", new Object[] { s });
/* 1167 */         textcomponenttranslation.getStyle().setColor(TextFormatting.DARK_GREEN);
/* 1168 */         sender.addChatMessage((ITextComponent)textcomponenttranslation);
/* 1169 */         sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString(set.toArray())));
/*      */       } 
/*      */       
/* 1172 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, set.size());
/*      */     } else {
/* 1174 */       if (p_184924_3_.length < 5)
/*      */       {
/* 1176 */         throw new WrongUsageException("commands.scoreboard.players.tag.usage", new Object[0]);
/*      */       }
/*      */ 
/*      */       
/* 1180 */       String s2 = p_184924_3_[p_184924_4_++];
/*      */       
/* 1182 */       if (p_184924_3_.length > p_184924_4_) {
/*      */         
/*      */         try {
/*      */           
/* 1186 */           NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(buildString(p_184924_3_, p_184924_4_));
/* 1187 */           NBTTagCompound nbttagcompound1 = entityToNBT(entity);
/*      */           
/* 1189 */           if (!NBTUtil.areNBTEquals((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*      */           {
/* 1191 */             throw new CommandException("commands.scoreboard.players.tag.tagMismatch", new Object[] { s });
/*      */           }
/*      */         }
/* 1194 */         catch (NBTException nbtexception) {
/*      */           
/* 1196 */           throw new CommandException("commands.scoreboard.players.tag.tagError", new Object[] { nbtexception.getMessage() });
/*      */         } 
/*      */       }
/*      */       
/* 1200 */       if ("add".equals(s1)) {
/*      */         
/* 1202 */         if (!entity.addTag(s2))
/*      */         {
/* 1204 */           throw new CommandException("commands.scoreboard.players.tag.tooMany", new Object[] { Integer.valueOf(1024) });
/*      */         }
/*      */         
/* 1207 */         notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.tag.success.add", new Object[] { s2 });
/*      */       }
/*      */       else {
/*      */         
/* 1211 */         if (!"remove".equals(s1))
/*      */         {
/* 1213 */           throw new WrongUsageException("commands.scoreboard.players.tag.usage", new Object[0]);
/*      */         }
/*      */         
/* 1216 */         if (!entity.removeTag(s2))
/*      */         {
/* 1218 */           throw new CommandException("commands.scoreboard.players.tag.notFound", new Object[] { s2 });
/*      */         }
/*      */         
/* 1221 */         notifyCommandListener(sender, (ICommand)this, "commands.scoreboard.players.tag.success.remove", new Object[] { s2 });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 1228 */     if (args.length == 1)
/*      */     {
/* 1230 */       return getListOfStringsMatchingLastWord(args, new String[] { "objectives", "players", "teams" });
/*      */     }
/*      */ 
/*      */     
/* 1234 */     if ("objectives".equalsIgnoreCase(args[0])) {
/*      */       
/* 1236 */       if (args.length == 2)
/*      */       {
/* 1238 */         return getListOfStringsMatchingLastWord(args, new String[] { "list", "add", "remove", "setdisplay" });
/*      */       }
/*      */       
/* 1241 */       if ("add".equalsIgnoreCase(args[1])) {
/*      */         
/* 1243 */         if (args.length == 4)
/*      */         {
/* 1245 */           Set<String> set = IScoreCriteria.INSTANCES.keySet();
/* 1246 */           return getListOfStringsMatchingLastWord(args, set);
/*      */         }
/*      */       
/* 1249 */       } else if ("remove".equalsIgnoreCase(args[1])) {
/*      */         
/* 1251 */         if (args.length == 3)
/*      */         {
/* 1253 */           return getListOfStringsMatchingLastWord(args, getObjectiveNames(false, server));
/*      */         }
/*      */       }
/* 1256 */       else if ("setdisplay".equalsIgnoreCase(args[1])) {
/*      */         
/* 1258 */         if (args.length == 3)
/*      */         {
/* 1260 */           return getListOfStringsMatchingLastWord(args, Scoreboard.getDisplaySlotStrings());
/*      */         }
/*      */         
/* 1263 */         if (args.length == 4)
/*      */         {
/* 1265 */           return getListOfStringsMatchingLastWord(args, getObjectiveNames(false, server));
/*      */         }
/*      */       }
/*      */     
/* 1269 */     } else if ("players".equalsIgnoreCase(args[0])) {
/*      */       
/* 1271 */       if (args.length == 2)
/*      */       {
/* 1273 */         return getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "remove", "reset", "list", "enable", "test", "operation", "tag" });
/*      */       }
/*      */       
/* 1276 */       if (!"set".equalsIgnoreCase(args[1]) && !"add".equalsIgnoreCase(args[1]) && !"remove".equalsIgnoreCase(args[1]) && !"reset".equalsIgnoreCase(args[1])) {
/*      */         
/* 1278 */         if ("enable".equalsIgnoreCase(args[1])) {
/*      */           
/* 1280 */           if (args.length == 3)
/*      */           {
/* 1282 */             return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*      */           }
/*      */           
/* 1285 */           if (args.length == 4)
/*      */           {
/* 1287 */             return getListOfStringsMatchingLastWord(args, getTriggerNames(server));
/*      */           }
/*      */         }
/* 1290 */         else if (!"list".equalsIgnoreCase(args[1]) && !"test".equalsIgnoreCase(args[1])) {
/*      */           
/* 1292 */           if ("operation".equalsIgnoreCase(args[1]))
/*      */           {
/* 1294 */             if (args.length == 3)
/*      */             {
/* 1296 */               return getListOfStringsMatchingLastWord(args, getScoreboard(server).getObjectiveNames());
/*      */             }
/*      */             
/* 1299 */             if (args.length == 4)
/*      */             {
/* 1301 */               return getListOfStringsMatchingLastWord(args, getObjectiveNames(true, server));
/*      */             }
/*      */             
/* 1304 */             if (args.length == 5)
/*      */             {
/* 1306 */               return getListOfStringsMatchingLastWord(args, new String[] { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" });
/*      */             }
/*      */             
/* 1309 */             if (args.length == 6)
/*      */             {
/* 1311 */               return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*      */             }
/*      */             
/* 1314 */             if (args.length == 7)
/*      */             {
/* 1316 */               return getListOfStringsMatchingLastWord(args, getObjectiveNames(false, server));
/*      */             }
/*      */           }
/* 1319 */           else if ("tag".equalsIgnoreCase(args[1]))
/*      */           {
/* 1321 */             if (args.length == 3)
/*      */             {
/* 1323 */               return getListOfStringsMatchingLastWord(args, getScoreboard(server).getObjectiveNames());
/*      */             }
/*      */             
/* 1326 */             if (args.length == 4)
/*      */             {
/* 1328 */               return getListOfStringsMatchingLastWord(args, new String[] { "add", "remove", "list" });
/*      */             }
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1334 */           if (args.length == 3)
/*      */           {
/* 1336 */             return getListOfStringsMatchingLastWord(args, getScoreboard(server).getObjectiveNames());
/*      */           }
/*      */           
/* 1339 */           if (args.length == 4 && "test".equalsIgnoreCase(args[1]))
/*      */           {
/* 1341 */             return getListOfStringsMatchingLastWord(args, getObjectiveNames(false, server));
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1347 */         if (args.length == 3)
/*      */         {
/* 1349 */           return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*      */         }
/*      */         
/* 1352 */         if (args.length == 4)
/*      */         {
/* 1354 */           return getListOfStringsMatchingLastWord(args, getObjectiveNames(true, server));
/*      */         }
/*      */       }
/*      */     
/* 1358 */     } else if ("teams".equalsIgnoreCase(args[0])) {
/*      */       
/* 1360 */       if (args.length == 2)
/*      */       {
/* 1362 */         return getListOfStringsMatchingLastWord(args, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/*      */       }
/*      */       
/* 1365 */       if ("join".equalsIgnoreCase(args[1])) {
/*      */         
/* 1367 */         if (args.length == 3)
/*      */         {
/* 1369 */           return getListOfStringsMatchingLastWord(args, getScoreboard(server).getTeamNames());
/*      */         }
/*      */         
/* 1372 */         if (args.length >= 4)
/*      */         {
/* 1374 */           return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1379 */         if ("leave".equalsIgnoreCase(args[1]))
/*      */         {
/* 1381 */           return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*      */         }
/*      */         
/* 1384 */         if (!"empty".equalsIgnoreCase(args[1]) && !"list".equalsIgnoreCase(args[1]) && !"remove".equalsIgnoreCase(args[1])) {
/*      */           
/* 1386 */           if ("option".equalsIgnoreCase(args[1])) {
/*      */             
/* 1388 */             if (args.length == 3)
/*      */             {
/* 1390 */               return getListOfStringsMatchingLastWord(args, getScoreboard(server).getTeamNames());
/*      */             }
/*      */             
/* 1393 */             if (args.length == 4)
/*      */             {
/* 1395 */               return getListOfStringsMatchingLastWord(args, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility", "collisionRule" });
/*      */             }
/*      */             
/* 1398 */             if (args.length == 5)
/*      */             {
/* 1400 */               if ("color".equalsIgnoreCase(args[3]))
/*      */               {
/* 1402 */                 return getListOfStringsMatchingLastWord(args, TextFormatting.getValidValues(true, false));
/*      */               }
/*      */               
/* 1405 */               if ("nametagVisibility".equalsIgnoreCase(args[3]) || "deathMessageVisibility".equalsIgnoreCase(args[3]))
/*      */               {
/* 1407 */                 return getListOfStringsMatchingLastWord(args, Team.EnumVisible.getNames());
/*      */               }
/*      */               
/* 1410 */               if ("collisionRule".equalsIgnoreCase(args[3]))
/*      */               {
/* 1412 */                 return getListOfStringsMatchingLastWord(args, Team.CollisionRule.getNames());
/*      */               }
/*      */               
/* 1415 */               if ("friendlyfire".equalsIgnoreCase(args[3]) || "seeFriendlyInvisibles".equalsIgnoreCase(args[3]))
/*      */               {
/* 1417 */                 return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/* 1422 */         } else if (args.length == 3) {
/*      */           
/* 1424 */           return getListOfStringsMatchingLastWord(args, getScoreboard(server).getTeamNames());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1429 */     return Collections.emptyList();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<String> getObjectiveNames(boolean writableOnly, MinecraftServer server) {
/* 1435 */     Collection<ScoreObjective> collection = getScoreboard(server).getScoreObjectives();
/* 1436 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1438 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/* 1440 */       if (!writableOnly || !scoreobjective.getCriteria().isReadOnly())
/*      */       {
/* 1442 */         list.add(scoreobjective.getName());
/*      */       }
/*      */     } 
/*      */     
/* 1446 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   protected List<String> getTriggerNames(MinecraftServer server) {
/* 1451 */     Collection<ScoreObjective> collection = getScoreboard(server).getScoreObjectives();
/* 1452 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1454 */     for (ScoreObjective scoreobjective : collection) {
/*      */       
/* 1456 */       if (scoreobjective.getCriteria() == IScoreCriteria.TRIGGER)
/*      */       {
/* 1458 */         list.add(scoreobjective.getName());
/*      */       }
/*      */     } 
/*      */     
/* 1462 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsernameIndex(String[] args, int index) {
/* 1470 */     if (!"players".equalsIgnoreCase(args[0])) {
/*      */       
/* 1472 */       if ("teams".equalsIgnoreCase(args[0]))
/*      */       {
/* 1474 */         return (index == 2);
/*      */       }
/*      */ 
/*      */       
/* 1478 */       return false;
/*      */     } 
/*      */     
/* 1481 */     if (args.length > 1 && "operation".equalsIgnoreCase(args[1]))
/*      */     {
/* 1483 */       return !(index != 2 && index != 5);
/*      */     }
/*      */ 
/*      */     
/* 1487 */     return (index == 2);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */