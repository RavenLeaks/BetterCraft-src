/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.GameRules;
/*     */ 
/*     */ public class CommandGameRule
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  20 */     return "gamerule";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  28 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  36 */     return "commands.gamerule.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     String s2;
/*  44 */     GameRules gamerules = getOverWorldGameRules(server);
/*  45 */     String s = (args.length > 0) ? args[0] : "";
/*  46 */     String s1 = (args.length > 1) ? buildString(args, 1) : "";
/*     */     
/*  48 */     switch (args.length) {
/*     */       
/*     */       case 0:
/*  51 */         sender.addChatMessage((ITextComponent)new TextComponentString(joinNiceString((Object[])gamerules.getRules())));
/*     */         return;
/*     */       
/*     */       case 1:
/*  55 */         if (!gamerules.hasRule(s))
/*     */         {
/*  57 */           throw new CommandException("commands.gamerule.norule", new Object[] { s });
/*     */         }
/*     */         
/*  60 */         s2 = gamerules.getString(s);
/*  61 */         sender.addChatMessage((new TextComponentString(s)).appendText(" = ").appendText(s2));
/*  62 */         sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, gamerules.getInt(s));
/*     */         return;
/*     */     } 
/*     */     
/*  66 */     if (gamerules.areSameType(s, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(s1) && !"false".equals(s1))
/*     */     {
/*  68 */       throw new CommandException("commands.generic.boolean.invalid", new Object[] { s1 });
/*     */     }
/*     */     
/*  71 */     gamerules.setOrCreateGameRule(s, s1);
/*  72 */     notifyGameRuleChange(gamerules, s, server);
/*  73 */     notifyCommandListener(sender, this, "commands.gamerule.success", new Object[] { s, s1 });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void notifyGameRuleChange(GameRules rules, String p_184898_1_, MinecraftServer server) {
/*  79 */     if ("reducedDebugInfo".equals(p_184898_1_)) {
/*     */       
/*  81 */       byte b0 = (byte)(rules.getBoolean(p_184898_1_) ? 22 : 23);
/*     */       
/*  83 */       for (EntityPlayerMP entityplayermp : server.getPlayerList().getPlayerList())
/*     */       {
/*  85 */         entityplayermp.connection.sendPacket((Packet)new SPacketEntityStatus((Entity)entityplayermp, b0));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/*  92 */     if (args.length == 1)
/*     */     {
/*  94 */       return getListOfStringsMatchingLastWord(args, getOverWorldGameRules(server).getRules());
/*     */     }
/*     */ 
/*     */     
/*  98 */     if (args.length == 2) {
/*     */       
/* 100 */       GameRules gamerules = getOverWorldGameRules(server);
/*     */       
/* 102 */       if (gamerules.areSameType(args[0], GameRules.ValueType.BOOLEAN_VALUE))
/*     */       {
/* 104 */         return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
/*     */       }
/*     */       
/* 107 */       if (gamerules.areSameType(args[0], GameRules.ValueType.FUNCTION))
/*     */       {
/* 109 */         return getListOfStringsMatchingLastWord(args, server.func_193030_aL().func_193066_d().keySet());
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GameRules getOverWorldGameRules(MinecraftServer server) {
/* 122 */     return server.worldServerForDimension(0).getGameRules();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandGameRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */