/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
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
/*     */ 
/*     */ public class CommandFill
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  26 */     return "fill";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  34 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  42 */     return "commands.fill.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*     */     IBlockState iblockstate;
/*  50 */     if (args.length < 7)
/*     */     {
/*  52 */       throw new WrongUsageException("commands.fill.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  56 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  57 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  58 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  59 */     Block block = CommandBase.getBlockByText(sender, args[6]);
/*     */ 
/*     */     
/*  62 */     if (args.length >= 8) {
/*     */       
/*  64 */       iblockstate = func_190794_a(block, args[7]);
/*     */     }
/*     */     else {
/*     */       
/*  68 */       iblockstate = block.getDefaultState();
/*     */     } 
/*     */     
/*  71 */     BlockPos blockpos2 = new BlockPos(Math.min(blockpos.getX(), blockpos1.getX()), Math.min(blockpos.getY(), blockpos1.getY()), Math.min(blockpos.getZ(), blockpos1.getZ()));
/*  72 */     BlockPos blockpos3 = new BlockPos(Math.max(blockpos.getX(), blockpos1.getX()), Math.max(blockpos.getY(), blockpos1.getY()), Math.max(blockpos.getZ(), blockpos1.getZ()));
/*  73 */     int i = (blockpos3.getX() - blockpos2.getX() + 1) * (blockpos3.getY() - blockpos2.getY() + 1) * (blockpos3.getZ() - blockpos2.getZ() + 1);
/*     */     
/*  75 */     if (i > 32768)
/*     */     {
/*  77 */       throw new CommandException("commands.fill.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(32768) });
/*     */     }
/*  79 */     if (blockpos2.getY() >= 0 && blockpos3.getY() < 256) {
/*     */       
/*  81 */       World world = sender.getEntityWorld();
/*     */       
/*  83 */       for (int j = blockpos2.getZ(); j <= blockpos3.getZ(); j += 16) {
/*     */         
/*  85 */         for (int k = blockpos2.getX(); k <= blockpos3.getX(); k += 16) {
/*     */           
/*  87 */           if (!world.isBlockLoaded(new BlockPos(k, blockpos3.getY() - blockpos2.getY(), j)))
/*     */           {
/*  89 */             throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  94 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  95 */       boolean flag = false;
/*     */       
/*  97 */       if (args.length >= 10 && block.hasTileEntity()) {
/*     */         
/*  99 */         String s = buildString(args, 9);
/*     */ 
/*     */         
/*     */         try {
/* 103 */           nbttagcompound = JsonToNBT.getTagFromJson(s);
/* 104 */           flag = true;
/*     */         }
/* 106 */         catch (NBTException nbtexception) {
/*     */           
/* 108 */           throw new CommandException("commands.fill.tagError", new Object[] { nbtexception.getMessage() });
/*     */         } 
/*     */       } 
/*     */       
/* 112 */       List<BlockPos> list = Lists.newArrayList();
/* 113 */       i = 0;
/*     */       
/* 115 */       for (int l = blockpos2.getZ(); l <= blockpos3.getZ(); l++) {
/*     */         
/* 117 */         for (int i1 = blockpos2.getY(); i1 <= blockpos3.getY(); i1++) {
/*     */           
/* 119 */           for (int j1 = blockpos2.getX(); j1 <= blockpos3.getX(); j1++) {
/*     */             
/* 121 */             BlockPos blockpos4 = new BlockPos(j1, i1, l);
/*     */             
/* 123 */             if (args.length >= 9)
/*     */             {
/* 125 */               if (!"outline".equals(args[8]) && !"hollow".equals(args[8])) {
/*     */                 
/* 127 */                 if ("destroy".equals(args[8]))
/*     */                 {
/* 129 */                   world.destroyBlock(blockpos4, true);
/*     */                 }
/* 131 */                 else if ("keep".equals(args[8]))
/*     */                 {
/* 133 */                   if (!world.isAirBlock(blockpos4))
/*     */                   {
/*     */                     continue;
/*     */                   }
/*     */                 }
/* 138 */                 else if ("replace".equals(args[8]) && !block.hasTileEntity() && args.length > 9)
/*     */                 {
/* 140 */                   Block block1 = CommandBase.getBlockByText(sender, args[9]);
/*     */                   
/* 142 */                   if (world.getBlockState(blockpos4).getBlock() != block1 || (args.length > 10 && !"-1".equals(args[10]) && !"*".equals(args[10]) && !CommandBase.func_190791_b(block1, args[10]).apply(world.getBlockState(blockpos4)))) {
/*     */                     continue;
/*     */                   }
/*     */                 }
/*     */               
/*     */               }
/* 148 */               else if (j1 != blockpos2.getX() && j1 != blockpos3.getX() && i1 != blockpos2.getY() && i1 != blockpos3.getY() && l != blockpos2.getZ() && l != blockpos3.getZ()) {
/*     */                 
/* 150 */                 if ("hollow".equals(args[8])) {
/*     */                   
/* 152 */                   world.setBlockState(blockpos4, Blocks.AIR.getDefaultState(), 2);
/* 153 */                   list.add(blockpos4);
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */             }
/*     */             
/* 160 */             TileEntity tileentity1 = world.getTileEntity(blockpos4);
/*     */             
/* 162 */             if (tileentity1 != null && tileentity1 instanceof IInventory)
/*     */             {
/* 164 */               ((IInventory)tileentity1).clear();
/*     */             }
/*     */             
/* 167 */             if (world.setBlockState(blockpos4, iblockstate, 2)) {
/*     */               
/* 169 */               list.add(blockpos4);
/* 170 */               i++;
/*     */               
/* 172 */               if (flag) {
/*     */                 
/* 174 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 176 */                 if (tileentity != null) {
/*     */                   
/* 178 */                   nbttagcompound.setInteger("x", blockpos4.getX());
/* 179 */                   nbttagcompound.setInteger("y", blockpos4.getY());
/* 180 */                   nbttagcompound.setInteger("z", blockpos4.getZ());
/* 181 */                   tileentity.readFromNBT(nbttagcompound);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 189 */       for (BlockPos blockpos5 : list) {
/*     */         
/* 191 */         Block block2 = world.getBlockState(blockpos5).getBlock();
/* 192 */         world.notifyNeighborsRespectDebug(blockpos5, block2, false);
/*     */       } 
/*     */       
/* 195 */       if (i <= 0)
/*     */       {
/* 197 */         throw new CommandException("commands.fill.failed", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 201 */       sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 202 */       notifyCommandListener(sender, this, "commands.fill.success", new Object[] { Integer.valueOf(i) });
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 207 */       throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 214 */     if (args.length > 0 && args.length <= 3)
/*     */     {
/* 216 */       return getTabCompletionCoordinate(args, 0, pos);
/*     */     }
/* 218 */     if (args.length > 3 && args.length <= 6)
/*     */     {
/* 220 */       return getTabCompletionCoordinate(args, 3, pos);
/*     */     }
/* 222 */     if (args.length == 7)
/*     */     {
/* 224 */       return getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
/*     */     }
/* 226 */     if (args.length == 9)
/*     */     {
/* 228 */       return getListOfStringsMatchingLastWord(args, new String[] { "replace", "destroy", "keep", "hollow", "outline" });
/*     */     }
/*     */ 
/*     */     
/* 232 */     return (args.length == 10 && "replace".equals(args[8])) ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */