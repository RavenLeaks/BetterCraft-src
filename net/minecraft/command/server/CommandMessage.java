/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerNotFoundException;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public class CommandMessage
/*    */   extends CommandBase
/*    */ {
/*    */   public List<String> getCommandAliases() {
/* 22 */     return Arrays.asList(new String[] { "w", "msg" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandName() {
/* 30 */     return "tell";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 38 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 46 */     return "commands.message.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 54 */     if (args.length < 2)
/*    */     {
/* 56 */       throw new WrongUsageException("commands.message.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 60 */     EntityPlayerMP entityPlayerMP = getPlayer(server, sender, args[0]);
/*    */     
/* 62 */     if (entityPlayerMP == sender)
/*    */     {
/* 64 */       throw new PlayerNotFoundException("commands.message.sameTarget");
/*    */     }
/*    */ 
/*    */     
/* 68 */     ITextComponent itextcomponent = getChatComponentFromNthArg(sender, args, 1, !(sender instanceof net.minecraft.entity.player.EntityPlayer));
/* 69 */     TextComponentTranslation textcomponenttranslation = new TextComponentTranslation("commands.message.display.incoming", new Object[] { sender.getDisplayName(), itextcomponent.createCopy() });
/* 70 */     TextComponentTranslation textcomponenttranslation1 = new TextComponentTranslation("commands.message.display.outgoing", new Object[] { entityPlayerMP.getDisplayName(), itextcomponent.createCopy() });
/* 71 */     textcomponenttranslation.getStyle().setColor(TextFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 72 */     textcomponenttranslation1.getStyle().setColor(TextFormatting.GRAY).setItalic(Boolean.valueOf(true));
/* 73 */     entityPlayerMP.addChatMessage((ITextComponent)textcomponenttranslation);
/* 74 */     sender.addChatMessage((ITextComponent)textcomponenttranslation1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 81 */     return getListOfStringsMatchingLastWord(args, server.getAllUsernames());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 89 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */