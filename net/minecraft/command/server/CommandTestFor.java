/*    */ package net.minecraft.command.server;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.command.CommandBase;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.ICommand;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.WrongUsageException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class CommandTestFor
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 25 */     return "testfor";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 33 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 41 */     return "commands.testfor.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/* 49 */     if (args.length < 1)
/*    */     {
/* 51 */       throw new WrongUsageException("commands.testfor.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 55 */     Entity entity = getEntity(server, sender, args[0]);
/* 56 */     NBTTagCompound nbttagcompound = null;
/*    */     
/* 58 */     if (args.length >= 2) {
/*    */       
/*    */       try {
/*    */         
/* 62 */         nbttagcompound = JsonToNBT.getTagFromJson(buildString(args, 1));
/*    */       }
/* 64 */       catch (NBTException nbtexception) {
/*    */         
/* 66 */         throw new CommandException("commands.testfor.tagError", new Object[] { nbtexception.getMessage() });
/*    */       } 
/*    */     }
/*    */     
/* 70 */     if (nbttagcompound != null) {
/*    */       
/* 72 */       NBTTagCompound nbttagcompound1 = entityToNBT(entity);
/*    */       
/* 74 */       if (!NBTUtil.areNBTEquals((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*    */       {
/* 76 */         throw new CommandException("commands.testfor.failure", new Object[] { entity.getName() });
/*    */       }
/*    */     } 
/*    */     
/* 80 */     notifyCommandListener(sender, (ICommand)this, "commands.testfor.success", new Object[] { entity.getName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 89 */     return (index == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 94 */     return (args.length == 1) ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandTestFor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */