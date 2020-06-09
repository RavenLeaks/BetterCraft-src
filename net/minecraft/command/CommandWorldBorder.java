/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandWorldBorder
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  19 */     return "worldborder";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  27 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  35 */     return "commands.worldborder.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  43 */     if (args.length < 1)
/*     */     {
/*  45 */       throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  49 */     WorldBorder worldborder = getWorldBorder(server);
/*     */     
/*  51 */     if ("set".equals(args[0])) {
/*     */       
/*  53 */       if (args.length != 2 && args.length != 3)
/*     */       {
/*  55 */         throw new WrongUsageException("commands.worldborder.set.usage", new Object[0]);
/*     */       }
/*     */       
/*  58 */       double d0 = worldborder.getTargetSize();
/*  59 */       double d2 = parseDouble(args[1], 1.0D, 6.0E7D);
/*  60 */       long i = (args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L;
/*     */       
/*  62 */       if (i > 0L) {
/*     */         
/*  64 */         worldborder.setTransition(d0, d2, i);
/*     */         
/*  66 */         if (d0 > d2)
/*     */         {
/*  68 */           notifyCommandListener(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         }
/*     */         else
/*     */         {
/*  72 */           notifyCommandListener(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }), Long.toString(i / 1000L) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  77 */         worldborder.setTransition(d2);
/*  78 */         notifyCommandListener(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d0) }) });
/*     */       }
/*     */     
/*  81 */     } else if ("add".equals(args[0])) {
/*     */       
/*  83 */       if (args.length != 2 && args.length != 3)
/*     */       {
/*  85 */         throw new WrongUsageException("commands.worldborder.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  88 */       double d4 = worldborder.getDiameter();
/*  89 */       double d8 = d4 + parseDouble(args[1], -d4, 6.0E7D - d4);
/*  90 */       long j1 = worldborder.getTimeUntilTarget() + ((args.length > 2) ? (parseLong(args[2], 0L, 9223372036854775L) * 1000L) : 0L);
/*     */       
/*  92 */       if (j1 > 0L) {
/*     */         
/*  94 */         worldborder.setTransition(d4, d8, j1);
/*     */         
/*  96 */         if (d4 > d8)
/*     */         {
/*  98 */           notifyCommandListener(sender, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(j1 / 1000L) });
/*     */         }
/*     */         else
/*     */         {
/* 102 */           notifyCommandListener(sender, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }), Long.toString(j1 / 1000L) });
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 107 */         worldborder.setTransition(d8);
/* 108 */         notifyCommandListener(sender, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d8) }), String.format("%.1f", new Object[] { Double.valueOf(d4) }) });
/*     */       }
/*     */     
/* 111 */     } else if ("center".equals(args[0])) {
/*     */       
/* 113 */       if (args.length != 3)
/*     */       {
/* 115 */         throw new WrongUsageException("commands.worldborder.center.usage", new Object[0]);
/*     */       }
/*     */       
/* 118 */       BlockPos blockpos = sender.getPosition();
/* 119 */       double d1 = parseDouble(blockpos.getX() + 0.5D, args[1], true);
/* 120 */       double d3 = parseDouble(blockpos.getZ() + 0.5D, args[2], true);
/* 121 */       worldborder.setCenter(d1, d3);
/* 122 */       notifyCommandListener(sender, this, "commands.worldborder.center.success", new Object[] { Double.valueOf(d1), Double.valueOf(d3) });
/*     */     }
/* 124 */     else if ("damage".equals(args[0])) {
/*     */       
/* 126 */       if (args.length < 2)
/*     */       {
/* 128 */         throw new WrongUsageException("commands.worldborder.damage.usage", new Object[0]);
/*     */       }
/*     */       
/* 131 */       if ("buffer".equals(args[1]))
/*     */       {
/* 133 */         if (args.length != 3)
/*     */         {
/* 135 */           throw new WrongUsageException("commands.worldborder.damage.buffer.usage", new Object[0]);
/*     */         }
/*     */         
/* 138 */         double d5 = parseDouble(args[2], 0.0D);
/* 139 */         double d9 = worldborder.getDamageBuffer();
/* 140 */         worldborder.setDamageBuffer(d5);
/* 141 */         notifyCommandListener(sender, this, "commands.worldborder.damage.buffer.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d5) }), String.format("%.1f", new Object[] { Double.valueOf(d9) }) });
/*     */       }
/* 143 */       else if ("amount".equals(args[1]))
/*     */       {
/* 145 */         if (args.length != 3)
/*     */         {
/* 147 */           throw new WrongUsageException("commands.worldborder.damage.amount.usage", new Object[0]);
/*     */         }
/*     */         
/* 150 */         double d6 = parseDouble(args[2], 0.0D);
/* 151 */         double d10 = worldborder.getDamageAmount();
/* 152 */         worldborder.setDamageAmount(d6);
/* 153 */         notifyCommandListener(sender, this, "commands.worldborder.damage.amount.success", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d6) }), String.format("%.2f", new Object[] { Double.valueOf(d10) }) });
/*     */       }
/*     */     
/* 156 */     } else if ("warning".equals(args[0])) {
/*     */       
/* 158 */       if (args.length < 2)
/*     */       {
/* 160 */         throw new WrongUsageException("commands.worldborder.warning.usage", new Object[0]);
/*     */       }
/*     */       
/* 163 */       if ("time".equals(args[1]))
/*     */       {
/* 165 */         if (args.length != 3)
/*     */         {
/* 167 */           throw new WrongUsageException("commands.worldborder.warning.time.usage", new Object[0]);
/*     */         }
/*     */         
/* 170 */         int j = parseInt(args[2], 0);
/* 171 */         int l = worldborder.getWarningTime();
/* 172 */         worldborder.setWarningTime(j);
/* 173 */         notifyCommandListener(sender, this, "commands.worldborder.warning.time.success", new Object[] { Integer.valueOf(j), Integer.valueOf(l) });
/*     */       }
/* 175 */       else if ("distance".equals(args[1]))
/*     */       {
/* 177 */         if (args.length != 3)
/*     */         {
/* 179 */           throw new WrongUsageException("commands.worldborder.warning.distance.usage", new Object[0]);
/*     */         }
/*     */         
/* 182 */         int k = parseInt(args[2], 0);
/* 183 */         int i1 = worldborder.getWarningDistance();
/* 184 */         worldborder.setWarningDistance(k);
/* 185 */         notifyCommandListener(sender, this, "commands.worldborder.warning.distance.success", new Object[] { Integer.valueOf(k), Integer.valueOf(i1) });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 190 */       if (!"get".equals(args[0]))
/*     */       {
/* 192 */         throw new WrongUsageException("commands.worldborder.usage", new Object[0]);
/*     */       }
/*     */       
/* 195 */       double d7 = worldborder.getDiameter();
/* 196 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor(d7 + 0.5D));
/* 197 */       sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.worldborder.get.success", new Object[] { String.format("%.0f", new Object[] { Double.valueOf(d7) }) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldBorder getWorldBorder(MinecraftServer server) {
/* 204 */     return server.worldServers[0].getWorldBorder();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 209 */     if (args.length == 1)
/*     */     {
/* 211 */       return getListOfStringsMatchingLastWord(args, new String[] { "set", "center", "damage", "warning", "add", "get" });
/*     */     }
/* 213 */     if (args.length == 2 && "damage".equals(args[0]))
/*     */     {
/* 215 */       return getListOfStringsMatchingLastWord(args, new String[] { "buffer", "amount" });
/*     */     }
/* 217 */     if (args.length >= 2 && args.length <= 3 && "center".equals(args[0]))
/*     */     {
/* 219 */       return getTabCompletionCoordinateXZ(args, 1, pos);
/*     */     }
/*     */ 
/*     */     
/* 223 */     return (args.length == 2 && "warning".equals(args[0])) ? getListOfStringsMatchingLastWord(args, new String[] { "time", "distance" }) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandWorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */