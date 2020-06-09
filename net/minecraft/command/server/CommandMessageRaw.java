/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandMessageRaw
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 24 */     return "tellraw";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 32 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 40 */     return "commands.tellraw.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 48 */     if (args.length < 2)
/*    */     {
/* 50 */       throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 54 */     EntityPlayerMP entityPlayerMP = getPlayer(server, sender, args[0]);
/* 55 */     String s = buildString(args, 1);
/*    */ 
/*    */     
/*    */     try {
/* 59 */       ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
/* 60 */       entityPlayerMP.addChatMessage(TextComponentUtils.processComponent(sender, itextcomponent, (Entity)entityPlayerMP));
/*    */     }
/* 62 */     catch (JsonParseException jsonparseexception) {
/*    */       
/* 64 */       throw toSyntaxException(jsonparseexception);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 71 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 79 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandMessageRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */