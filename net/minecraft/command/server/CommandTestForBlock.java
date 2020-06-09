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
/*     */ import net.minecraft.command.NumberInvalidException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class CommandTestForBlock
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  30 */     return "testforblock";
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
/*  46 */     return "commands.testforblock.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  54 */     if (args.length < 4)
/*     */     {
/*  56 */       throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  60 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  61 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  62 */     Block block = getBlockByText(sender, args[3]);
/*     */     
/*  64 */     if (block == null)
/*     */     {
/*  66 */       throw new NumberInvalidException("commands.setblock.notFound", new Object[] { args[3] });
/*     */     }
/*     */ 
/*     */     
/*  70 */     World world = sender.getEntityWorld();
/*     */     
/*  72 */     if (!world.isBlockLoaded(blockpos))
/*     */     {
/*  74 */       throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  78 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  79 */     boolean flag = false;
/*     */     
/*  81 */     if (args.length >= 6 && block.hasTileEntity()) {
/*     */       
/*  83 */       String s = buildString(args, 5);
/*     */ 
/*     */       
/*     */       try {
/*  87 */         nbttagcompound = JsonToNBT.getTagFromJson(s);
/*  88 */         flag = true;
/*     */       }
/*  90 */       catch (NBTException nbtexception) {
/*     */         
/*  92 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception.getMessage() });
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     IBlockState iblockstate = world.getBlockState(blockpos);
/*  97 */     Block block1 = iblockstate.getBlock();
/*     */     
/*  99 */     if (block1 != block)
/*     */     {
/* 101 */       throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), block1.getLocalizedName(), block.getLocalizedName() });
/*     */     }
/* 103 */     if (args.length >= 5 && !CommandBase.func_190791_b(block, args[4]).apply(iblockstate)) {
/*     */       
/*     */       try {
/*     */         
/* 107 */         int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 108 */         throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), Integer.valueOf(i), Integer.valueOf(Integer.parseInt(args[4])) });
/*     */       }
/* 110 */       catch (NumberFormatException var13) {
/*     */         
/* 112 */         throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()), iblockstate.toString(), args[4] });
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 117 */     if (flag) {
/*     */       
/* 119 */       TileEntity tileentity = world.getTileEntity(blockpos);
/*     */       
/* 121 */       if (tileentity == null)
/*     */       {
/* 123 */         throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */       
/* 126 */       NBTTagCompound nbttagcompound1 = tileentity.writeToNBT(new NBTTagCompound());
/*     */       
/* 128 */       if (!NBTUtil.areNBTEquals((NBTBase)nbttagcompound, (NBTBase)nbttagcompound1, true))
/*     */       {
/* 130 */         throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */       }
/*     */     } 
/*     */     
/* 134 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
/* 135 */     notifyCommandListener(sender, (ICommand)this, "commands.testforblock.success", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 144 */     if (args.length > 0 && args.length <= 3)
/*     */     {
/* 146 */       return getTabCompletionCoordinate(args, 0, pos);
/*     */     }
/*     */ 
/*     */     
/* 150 */     return (args.length == 4) ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\server\CommandTestForBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */