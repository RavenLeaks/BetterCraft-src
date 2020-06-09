/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandOp
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 22 */     return "op";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 30 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 38 */     return "commands.op.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 46 */     if (args.length == 1 && args[0].length() > 0) {
/*    */       
/* 48 */       GameProfile gameprofile = server.getPlayerProfileCache().getGameProfileForUsername(args[0]);
/*    */       
/* 50 */       if (gameprofile == null)
/*    */       {
/* 52 */         throw new CommandException("commands.op.failed", new Object[] { args[0] });
/*    */       }
/*    */ 
/*    */       
/* 56 */       server.getPlayerList().addOp(gameprofile);
/* 57 */       notifyCommandListener(sender, (ICommand)this, "commands.op.success", new Object[] { args[0] });
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 62 */       throw new WrongUsageException("commands.op.usage", new Object[0]);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 68 */     if (args.length == 1) {
/*    */       
/* 70 */       String s = args[args.length - 1];
/* 71 */       List<String> list = Lists.newArrayList(); byte b; int i;
/*    */       GameProfile[] arrayOfGameProfile;
/* 73 */       for (i = (arrayOfGameProfile = server.getGameProfiles()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/*    */         
/* 75 */         if (!server.getPlayerList().canSendCommands(gameprofile) && doesStringStartWith(s, gameprofile.getName()))
/*    */         {
/* 77 */           list.add(gameprofile.getName());
/*    */         }
/*    */         b++; }
/*    */       
/* 81 */       return list;
/*    */     } 
/*    */ 
/*    */     
/* 85 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandOp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */