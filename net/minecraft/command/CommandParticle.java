/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandParticle
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "particle";
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
/*  37 */     return "commands.particle.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     EntityPlayerMP entityplayermp;
/*  45 */     if (args.length < 8)
/*     */     {
/*  47 */       throw new WrongUsageException("commands.particle.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  51 */     boolean flag = false;
/*  52 */     EnumParticleTypes enumparticletypes = EnumParticleTypes.getByName(args[0]);
/*     */     
/*  54 */     if (enumparticletypes == null)
/*     */     {
/*  56 */       throw new CommandException("commands.particle.notFound", new Object[] { args[0] });
/*     */     }
/*     */ 
/*     */     
/*  60 */     String s = args[0];
/*  61 */     Vec3d vec3d = sender.getPositionVector();
/*  62 */     double d0 = (float)parseDouble(vec3d.xCoord, args[1], true);
/*  63 */     double d1 = (float)parseDouble(vec3d.yCoord, args[2], true);
/*  64 */     double d2 = (float)parseDouble(vec3d.zCoord, args[3], true);
/*  65 */     double d3 = (float)parseDouble(args[4]);
/*  66 */     double d4 = (float)parseDouble(args[5]);
/*  67 */     double d5 = (float)parseDouble(args[6]);
/*  68 */     double d6 = (float)parseDouble(args[7]);
/*  69 */     int i = 0;
/*     */     
/*  71 */     if (args.length > 8)
/*     */     {
/*  73 */       i = parseInt(args[8], 0);
/*     */     }
/*     */     
/*  76 */     boolean flag1 = false;
/*     */     
/*  78 */     if (args.length > 9 && "force".equals(args[9]))
/*     */     {
/*  80 */       flag1 = true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  85 */     if (args.length > 10) {
/*     */       
/*  87 */       entityplayermp = getPlayer(server, sender, args[10]);
/*     */     }
/*     */     else {
/*     */       
/*  91 */       entityplayermp = null;
/*     */     } 
/*     */     
/*  94 */     int[] aint = new int[enumparticletypes.getArgumentCount()];
/*     */     
/*  96 */     for (int j = 0; j < aint.length; j++) {
/*     */       
/*  98 */       if (args.length > 11 + j) {
/*     */         
/*     */         try {
/*     */           
/* 102 */           aint[j] = Integer.parseInt(args[11 + j]);
/*     */         }
/* 104 */         catch (NumberFormatException var28) {
/*     */           
/* 106 */           throw new CommandException("commands.particle.invalidParam", new Object[] { args[11 + j] });
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 111 */     World world = sender.getEntityWorld();
/*     */     
/* 113 */     if (world instanceof WorldServer) {
/*     */       
/* 115 */       WorldServer worldserver = (WorldServer)world;
/*     */       
/* 117 */       if (entityplayermp == null) {
/*     */         
/* 119 */         worldserver.spawnParticle(enumparticletypes, flag1, d0, d1, d2, i, d3, d4, d5, d6, aint);
/*     */       }
/*     */       else {
/*     */         
/* 123 */         worldserver.spawnParticle(entityplayermp, enumparticletypes, flag1, d0, d1, d2, i, d3, d4, d5, d6, aint);
/*     */       } 
/*     */       
/* 126 */       notifyCommandListener(sender, this, "commands.particle.success", new Object[] { s, Integer.valueOf(Math.max(i, 1)) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 134 */     if (args.length == 1)
/*     */     {
/* 136 */       return getListOfStringsMatchingLastWord(args, EnumParticleTypes.getParticleNames());
/*     */     }
/* 138 */     if (args.length > 1 && args.length <= 4)
/*     */     {
/* 140 */       return getTabCompletionCoordinate(args, 1, pos);
/*     */     }
/* 142 */     if (args.length == 10)
/*     */     {
/* 144 */       return getListOfStringsMatchingLastWord(args, new String[] { "normal", "force" });
/*     */     }
/*     */ 
/*     */     
/* 148 */     return (args.length == 11) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 157 */     return (index == 10);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */