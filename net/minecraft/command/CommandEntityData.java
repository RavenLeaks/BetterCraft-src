/*    */ package net.minecraft.command;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandEntityData
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 18 */     return "entitydata";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 26 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 34 */     return "commands.entitydata.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*    */     NBTTagCompound nbttagcompound2;
/* 42 */     if (args.length < 2)
/*    */     {
/* 44 */       throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 48 */     Entity entity = getEntity(server, sender, args[0]);
/*    */     
/* 50 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer)
/*    */     {
/* 52 */       throw new CommandException("commands.entitydata.noPlayers", new Object[] { entity.getDisplayName() });
/*    */     }
/*    */ 
/*    */     
/* 56 */     NBTTagCompound nbttagcompound = entityToNBT(entity);
/* 57 */     NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 62 */       nbttagcompound2 = JsonToNBT.getTagFromJson(buildString(args, 1));
/*    */     }
/* 64 */     catch (NBTException nbtexception) {
/*    */       
/* 66 */       throw new CommandException("commands.entitydata.tagError", new Object[] { nbtexception.getMessage() });
/*    */     } 
/*    */     
/* 69 */     UUID uuid = entity.getUniqueID();
/* 70 */     nbttagcompound.merge(nbttagcompound2);
/* 71 */     entity.setUniqueId(uuid);
/*    */     
/* 73 */     if (nbttagcompound.equals(nbttagcompound1))
/*    */     {
/* 75 */       throw new CommandException("commands.entitydata.failed", new Object[] { nbttagcompound.toString() });
/*    */     }
/*    */ 
/*    */     
/* 79 */     entity.readFromNBT(nbttagcompound);
/* 80 */     notifyCommandListener(sender, this, "commands.entitydata.success", new Object[] { nbttagcompound.toString() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 91 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandEntityData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */