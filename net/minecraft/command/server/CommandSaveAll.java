/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 23 */     return "save-all";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 31 */     return "commands.save.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 39 */     sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.save.start", new Object[0]));
/*    */     
/* 41 */     if (server.getPlayerList() != null)
/*    */     {
/* 43 */       server.getPlayerList().saveAllPlayerData();
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 48 */       for (int i = 0; i < server.worldServers.length; i++) {
/*    */         
/* 50 */         if (server.worldServers[i] != null) {
/*    */           
/* 52 */           WorldServer worldserver = server.worldServers[i];
/* 53 */           boolean flag = worldserver.disableLevelSaving;
/* 54 */           worldserver.disableLevelSaving = false;
/* 55 */           worldserver.saveAllChunks(true, null);
/* 56 */           worldserver.disableLevelSaving = flag;
/*    */         } 
/*    */       } 
/*    */       
/* 60 */       if (args.length > 0 && "flush".equals(args[0]))
/*    */       {
/* 62 */         sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.save.flushStart", new Object[0]));
/*    */         
/* 64 */         for (int j = 0; j < server.worldServers.length; j++) {
/*    */           
/* 66 */           if (server.worldServers[j] != null) {
/*    */             
/* 68 */             WorldServer worldserver1 = server.worldServers[j];
/* 69 */             boolean flag1 = worldserver1.disableLevelSaving;
/* 70 */             worldserver1.disableLevelSaving = false;
/* 71 */             worldserver1.saveChunkData();
/* 72 */             worldserver1.disableLevelSaving = flag1;
/*    */           } 
/*    */         } 
/*    */         
/* 76 */         sender.addChatMessage((ITextComponent)new TextComponentTranslation("commands.save.flushEnd", new Object[0]));
/*    */       }
/*    */     
/* 79 */     } catch (MinecraftException minecraftexception) {
/*    */       
/* 81 */       notifyCommandListener(sender, (ICommand)this, "commands.save.failed", new Object[] { minecraftexception.getMessage() });
/*    */       
/*    */       return;
/*    */     } 
/* 85 */     notifyCommandListener(sender, (ICommand)this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 90 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, new String[] { "flush" }) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandSaveAll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */