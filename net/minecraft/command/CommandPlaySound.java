/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketCustomSound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandPlaySound
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  21 */     return "playsound";
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
/*  37 */     return "commands.playsound.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  45 */     if (args.length < 2)
/*     */     {
/*  47 */       throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  51 */     int i = 0;
/*  52 */     String s = args[i++];
/*  53 */     String s1 = args[i++];
/*  54 */     SoundCategory soundcategory = SoundCategory.getByName(s1);
/*     */     
/*  56 */     if (soundcategory == null)
/*     */     {
/*  58 */       throw new CommandException("commands.playsound.unknownSoundSource", new Object[] { s1 });
/*     */     }
/*     */ 
/*     */     
/*  62 */     EntityPlayerMP entityplayermp = getPlayer(server, sender, args[i++]);
/*  63 */     Vec3d vec3d = sender.getPositionVector();
/*  64 */     double d0 = (args.length > i) ? parseDouble(vec3d.xCoord, args[i++], true) : vec3d.xCoord;
/*  65 */     double d1 = (args.length > i) ? parseDouble(vec3d.yCoord, args[i++], 0, 0, false) : vec3d.yCoord;
/*  66 */     double d2 = (args.length > i) ? parseDouble(vec3d.zCoord, args[i++], true) : vec3d.zCoord;
/*  67 */     double d3 = (args.length > i) ? parseDouble(args[i++], 0.0D, 3.4028234663852886E38D) : 1.0D;
/*  68 */     double d4 = (args.length > i) ? parseDouble(args[i++], 0.0D, 2.0D) : 1.0D;
/*  69 */     double d5 = (args.length > i) ? parseDouble(args[i], 0.0D, 1.0D) : 0.0D;
/*  70 */     double d6 = (d3 > 1.0D) ? (d3 * 16.0D) : 16.0D;
/*  71 */     double d7 = entityplayermp.getDistance(d0, d1, d2);
/*     */     
/*  73 */     if (d7 > d6) {
/*     */       
/*  75 */       if (d5 <= 0.0D)
/*     */       {
/*  77 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { entityplayermp.getName() });
/*     */       }
/*     */       
/*  80 */       double d8 = d0 - entityplayermp.posX;
/*  81 */       double d9 = d1 - entityplayermp.posY;
/*  82 */       double d10 = d2 - entityplayermp.posZ;
/*  83 */       double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
/*     */       
/*  85 */       if (d11 > 0.0D) {
/*     */         
/*  87 */         d0 = entityplayermp.posX + d8 / d11 * 2.0D;
/*  88 */         d1 = entityplayermp.posY + d9 / d11 * 2.0D;
/*  89 */         d2 = entityplayermp.posZ + d10 / d11 * 2.0D;
/*     */       } 
/*     */       
/*  92 */       d3 = d5;
/*     */     } 
/*     */     
/*  95 */     entityplayermp.connection.sendPacket((Packet)new SPacketCustomSound(s, soundcategory, d0, d1, d2, (float)d3, (float)d4));
/*  96 */     notifyCommandListener(sender, this, "commands.playsound.success", new Object[] { s, entityplayermp.getName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 103 */     if (args.length == 1)
/*     */     {
/* 105 */       return getListOfStringsMatchingLastWord(args, SoundEvent.REGISTRY.getKeys());
/*     */     }
/* 107 */     if (args.length == 2)
/*     */     {
/* 109 */       return getListOfStringsMatchingLastWord(args, SoundCategory.getSoundCategoryNames());
/*     */     }
/* 111 */     if (args.length == 3)
/*     */     {
/* 113 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 117 */     return (args.length > 3 && args.length <= 6) ? getTabCompletionCoordinate(args, 3, pos) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 126 */     return (index == 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandPlaySound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */