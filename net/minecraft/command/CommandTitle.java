/*     */ package net.minecraft.command;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketTitle;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CommandTitle extends CommandBase {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandName() {
/*  25 */     return "title";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  33 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  41 */     return "commands.title.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  49 */     if (args.length < 2)
/*     */     {
/*  51 */       throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (args.length < 3) {
/*     */       
/*  57 */       if ("title".equals(args[1]) || "subtitle".equals(args[1]) || "actionbar".equals(args[1]))
/*     */       {
/*  59 */         throw new WrongUsageException("commands.title.usage.title", new Object[0]);
/*     */       }
/*     */       
/*  62 */       if ("times".equals(args[1]))
/*     */       {
/*  64 */         throw new WrongUsageException("commands.title.usage.times", new Object[0]);
/*     */       }
/*     */     } 
/*     */     
/*  68 */     EntityPlayerMP entityplayermp = getPlayer(server, sender, args[0]);
/*  69 */     SPacketTitle.Type spackettitle$type = SPacketTitle.Type.byName(args[1]);
/*     */     
/*  71 */     if (spackettitle$type != SPacketTitle.Type.CLEAR && spackettitle$type != SPacketTitle.Type.RESET) {
/*     */       
/*  73 */       if (spackettitle$type == SPacketTitle.Type.TIMES) {
/*     */         
/*  75 */         if (args.length != 5)
/*     */         {
/*  77 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/*  81 */         int i = parseInt(args[2]);
/*  82 */         int j = parseInt(args[3]);
/*  83 */         int k = parseInt(args[4]);
/*  84 */         SPacketTitle spackettitle2 = new SPacketTitle(i, j, k);
/*  85 */         entityplayermp.connection.sendPacket((Packet)spackettitle2);
/*  86 */         notifyCommandListener(sender, this, "commands.title.success", new Object[0]);
/*     */       } else {
/*     */         ITextComponent itextcomponent;
/*  89 */         if (args.length < 3)
/*     */         {
/*  91 */           throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/*  95 */         String s = buildString(args, 2);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 100 */           itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
/*     */         }
/* 102 */         catch (JsonParseException jsonparseexception) {
/*     */           
/* 104 */           throw toSyntaxException(jsonparseexception);
/*     */         } 
/*     */         
/* 107 */         SPacketTitle spackettitle1 = new SPacketTitle(spackettitle$type, TextComponentUtils.processComponent(sender, itextcomponent, (Entity)entityplayermp));
/* 108 */         entityplayermp.connection.sendPacket((Packet)spackettitle1);
/* 109 */         notifyCommandListener(sender, this, "commands.title.success", new Object[0]);
/*     */       } 
/*     */     } else {
/* 112 */       if (args.length != 2)
/*     */       {
/* 114 */         throw new WrongUsageException("commands.title.usage", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 118 */       SPacketTitle spackettitle = new SPacketTitle(spackettitle$type, null);
/* 119 */       entityplayermp.connection.sendPacket((Packet)spackettitle);
/* 120 */       notifyCommandListener(sender, this, "commands.title.success", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 127 */     if (args.length == 1)
/*     */     {
/* 129 */       return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*     */     }
/*     */ 
/*     */     
/* 133 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, SPacketTitle.Type.getNames()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 142 */     return (index == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */