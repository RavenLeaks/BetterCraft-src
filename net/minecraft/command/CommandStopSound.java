/*     */ package net.minecraft.command;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandStopSound
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "stopsound";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  30 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  38 */     return "commands.stopsound.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  46 */     if (args.length >= 1 && args.length <= 3) {
/*     */       
/*  48 */       int i = 0;
/*  49 */       EntityPlayerMP entityplayermp = getPlayer(server, sender, args[i++]);
/*  50 */       String s = "";
/*  51 */       String s1 = "";
/*     */       
/*  53 */       if (args.length >= 2) {
/*     */         
/*  55 */         String s2 = args[i++];
/*  56 */         SoundCategory soundcategory = SoundCategory.getByName(s2);
/*     */         
/*  58 */         if (soundcategory == null)
/*     */         {
/*  60 */           throw new CommandException("commands.stopsound.unknownSoundSource", new Object[] { s2 });
/*     */         }
/*     */         
/*  63 */         s = soundcategory.getName();
/*     */       } 
/*     */       
/*  66 */       if (args.length == 3)
/*     */       {
/*  68 */         s1 = args[i++];
/*     */       }
/*     */       
/*  71 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  72 */       packetbuffer.writeString(s);
/*  73 */       packetbuffer.writeString(s1);
/*  74 */       entityplayermp.connection.sendPacket((Packet)new SPacketCustomPayload("MC|StopSound", packetbuffer));
/*     */       
/*  76 */       if (s.isEmpty() && s1.isEmpty())
/*     */       {
/*  78 */         notifyCommandListener(sender, this, "commands.stopsound.success.all", new Object[] { entityplayermp.getName() });
/*     */       }
/*  80 */       else if (s1.isEmpty())
/*     */       {
/*  82 */         notifyCommandListener(sender, this, "commands.stopsound.success.soundSource", new Object[] { s, entityplayermp.getName() });
/*     */       }
/*     */       else
/*     */       {
/*  86 */         notifyCommandListener(sender, this, "commands.stopsound.success.individualSound", new Object[] { s1, s, entityplayermp.getName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  91 */       throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/*  97 */     if (args.length == 1)
/*     */     {
/*  99 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/* 101 */     if (args.length == 2)
/*     */     {
/* 103 */       return getListOfStringsMatchingLastWord(args, SoundCategory.getSoundCategoryNames());
/*     */     }
/*     */ 
/*     */     
/* 107 */     return (args.length == 3) ? getListOfStringsMatchingLastWord(args, SoundEvent.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 116 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandStopSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */