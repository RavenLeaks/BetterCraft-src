/*     */ package net.minecraft.command.server;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
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
/*     */ public class CommandSetBlock
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  30 */     return "setblock";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  38 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  46 */     return "commands.setblock.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     IBlockState iblockstate;
/*  54 */     if (args.length < 4)
/*     */     {
/*  56 */       throw new WrongUsageException("commands.setblock.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  60 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  61 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  62 */     Block block = CommandBase.getBlockByText(sender, args[3]);
/*     */ 
/*     */     
/*  65 */     if (args.length >= 5) {
/*     */       
/*  67 */       iblockstate = func_190794_a(block, args[4]);
/*     */     }
/*     */     else {
/*     */       
/*  71 */       iblockstate = block.getDefaultState();
/*     */     } 
/*     */     
/*  74 */     World world = sender.getEntityWorld();
/*     */     
/*  76 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  78 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  82 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  83 */     boolean flag = false;
/*     */     
/*  85 */     if (args.length >= 7 && block.hasTileEntity()) {
/*     */       
/*  87 */       String s = buildString(args, 6);
/*     */ 
/*     */       
/*     */       try {
/*  91 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  92 */         flag = true;
/*     */       }
/*  94 */       catch (NBTException nbtexception) {
/*     */         
/*  96 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     if (args.length >= 6)
/*     */     {
/* 102 */       if ("destroy".equals(args[5])) {
/*     */         
/* 104 */         world.destroyBlock(blockpos, true);
/*     */         
/* 106 */         if (block == Blocks.AIR) {
/*     */           
/* 108 */           notifyCommandListener(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */           
/*     */           return;
/*     */         } 
/* 112 */       } else if ("keep".equals(args[5]) && !world.isAirBlock(blockpos)) {
/*     */         
/* 114 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/* 118 */     TileEntity tileentity1 = world.getTileEntity(blockpos);
/*     */     
/* 120 */     if (tileentity1 != null && tileentity1 instanceof IInventory)
/*     */     {
/* 122 */       ((IInventory)tileentity1).clear();
/*     */     }
/*     */     
/* 125 */     if (!world.setBlockState(blockpos, iblockstate, 2))
/*     */     {
/* 127 */       throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 131 */     if (flag) {
/*     */       
/* 133 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 135 */       if (tileentity != null) {
/*     */         
/* 137 */         nbttagcompound.setInteger("x", blockpos.getX());
/* 138 */         nbttagcompound.setInteger("y", blockpos.getY());
/* 139 */         nbttagcompound.setInteger("z", blockpos.getZ());
/* 140 */         tileentity.readFromNBT(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     world.notifyNeighborsRespectDebug(blockpos, iblockstate.getBlock(), false);
/* 145 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 146 */     notifyCommandListener(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 154 */     if (args.length > 0 && args.length <= 3)
/*     */     {
/* 156 */       return getTabCompletionCoordinate(args, 0, pos);
/*     */     }
/* 158 */     if (args.length == 4)
/*     */     {
/* 160 */       return getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
/*     */     }
/*     */ 
/*     */     
/* 164 */     return (args.length == 6) ? getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep" }) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandSetBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */