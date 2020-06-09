/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandBlockData
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "blockdata";
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
/*  38 */     return "commands.blockdata.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     NBTTagCompound nbttagcompound2;
/*  46 */     if (args.length < 4)
/*     */     {
/*  48 */       throw new WrongUsageException("commands.blockdata.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  52 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  53 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  54 */     World world = sender.getEntityWorld();
/*     */     
/*  56 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  58 */       throw new CommandException("commands.blockdata.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  62 */     IBlockState iblockstate = world.getBlockState(blockpos);
/*  63 */     TileEntity tileentity = world.getTileEntity(blockpos);
/*     */     
/*  65 */     if (tileentity == null)
/*     */     {
/*  67 */       throw new CommandException("commands.blockdata.notValid", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  71 */     NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
/*  72 */     NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  77 */       nbttagcompound2 = JsonToNBT.getTagFromJson(buildString(args, 3));
/*     */     }
/*  79 */     catch (NBTException nbtexception) {
/*     */       
/*  81 */       throw new CommandException("commands.blockdata.tagError", new Object[] { nbtexception.getMessage() });
/*     */     } 
/*     */     
/*  84 */     nbttagcompound.merge(nbttagcompound2);
/*  85 */     nbttagcompound.setInteger("x", blockpos.getX());
/*  86 */     nbttagcompound.setInteger("y", blockpos.getY());
/*  87 */     nbttagcompound.setInteger("z", blockpos.getZ());
/*     */     
/*  89 */     if (nbttagcompound.equals(nbttagcompound1))
/*     */     {
/*  91 */       throw new CommandException("commands.blockdata.failed", new Object[] { nbttagcompound.toString() });
/*     */     }
/*     */ 
/*     */     
/*  95 */     tileentity.readFromNBT(nbttagcompound);
/*  96 */     tileentity.markDirty();
/*  97 */     world.notifyBlockUpdate(blockpos, iblockstate, iblockstate, 3);
/*  98 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/*  99 */     notifyCommandListener(sender, this, "commands.blockdata.success", new Object[] { nbttagcompound.toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 108 */     return (args.length > 0 && args.length <= 3) ? getTabCompletionCoordinate(args, 0, pos) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandBlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */