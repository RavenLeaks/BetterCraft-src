/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandClone
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  28 */     return "clone";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  36 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  44 */     return "commands.clone.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  52 */     if (args.length < 9)
/*     */     {
/*  54 */       throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  58 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  59 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  60 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  61 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  62 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  63 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.getLength()));
/*  64 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  66 */     if (i > 32768)
/*     */     {
/*  68 */       throw new CommandException("commands.clone.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(32768) });
/*     */     }
/*     */ 
/*     */     
/*  72 */     boolean flag = false;
/*  73 */     Block block = null;
/*  74 */     Predicate<IBlockState> predicate = null;
/*     */     
/*  76 */     if ((args.length < 11 || (!"force".equals(args[10]) && !"move".equals(args[10]))) && structureboundingbox.intersectsWith(structureboundingbox1))
/*     */     {
/*  78 */       throw new CommandException("commands.clone.noOverlap", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (args.length >= 11 && "move".equals(args[10]))
/*     */     {
/*  84 */       flag = true;
/*     */     }
/*     */     
/*  87 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*     */       
/*  89 */       World world = sender.getEntityWorld();
/*     */       
/*  91 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1))
/*     */       {
/*  93 */         boolean flag1 = false;
/*     */         
/*  95 */         if (args.length >= 10)
/*     */         {
/*  97 */           if ("masked".equals(args[9])) {
/*     */             
/*  99 */             flag1 = true;
/*     */           }
/* 101 */           else if ("filtered".equals(args[9])) {
/*     */             
/* 103 */             if (args.length < 12)
/*     */             {
/* 105 */               throw new WrongUsageException("commands.clone.usage", new Object[0]);
/*     */             }
/*     */             
/* 108 */             block = getBlockByText(sender, args[11]);
/*     */             
/* 110 */             if (args.length >= 13)
/*     */             {
/* 112 */               predicate = func_190791_b(block, args[12]);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 117 */         List<StaticCloneData> list = Lists.newArrayList();
/* 118 */         List<StaticCloneData> list1 = Lists.newArrayList();
/* 119 */         List<StaticCloneData> list2 = Lists.newArrayList();
/* 120 */         Deque<BlockPos> deque = Lists.newLinkedList();
/* 121 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*     */         
/* 123 */         for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; j++) {
/*     */           
/* 125 */           for (int k = structureboundingbox.minY; k <= structureboundingbox.maxY; k++) {
/*     */             
/* 127 */             for (int l = structureboundingbox.minX; l <= structureboundingbox.maxX; l++) {
/*     */               
/* 129 */               BlockPos blockpos4 = new BlockPos(l, k, j);
/* 130 */               BlockPos blockpos5 = blockpos4.add((Vec3i)blockpos3);
/* 131 */               IBlockState iblockstate = world.getBlockState(blockpos4);
/*     */               
/* 133 */               if ((!flag1 || iblockstate.getBlock() != Blocks.AIR) && (block == null || (iblockstate.getBlock() == block && (predicate == null || predicate.apply(iblockstate))))) {
/*     */                 
/* 135 */                 TileEntity tileentity = world.getTileEntity(blockpos4);
/*     */                 
/* 137 */                 if (tileentity != null) {
/*     */                   
/* 139 */                   NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
/* 140 */                   list1.add(new StaticCloneData(blockpos5, iblockstate, nbttagcompound));
/* 141 */                   deque.addLast(blockpos4);
/*     */                 }
/* 143 */                 else if (!iblockstate.isFullBlock() && !iblockstate.isFullCube()) {
/*     */                   
/* 145 */                   list2.add(new StaticCloneData(blockpos5, iblockstate, null));
/* 146 */                   deque.addFirst(blockpos4);
/*     */                 }
/*     */                 else {
/*     */                   
/* 150 */                   list.add(new StaticCloneData(blockpos5, iblockstate, null));
/* 151 */                   deque.addLast(blockpos4);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 158 */         if (flag) {
/*     */           
/* 160 */           for (BlockPos blockpos6 : deque) {
/*     */             
/* 162 */             TileEntity tileentity1 = world.getTileEntity(blockpos6);
/*     */             
/* 164 */             if (tileentity1 instanceof IInventory)
/*     */             {
/* 166 */               ((IInventory)tileentity1).clear();
/*     */             }
/*     */             
/* 169 */             world.setBlockState(blockpos6, Blocks.BARRIER.getDefaultState(), 2);
/*     */           } 
/*     */           
/* 172 */           for (BlockPos blockpos7 : deque)
/*     */           {
/* 174 */             world.setBlockState(blockpos7, Blocks.AIR.getDefaultState(), 3);
/*     */           }
/*     */         } 
/*     */         
/* 178 */         List<StaticCloneData> list3 = Lists.newArrayList();
/* 179 */         list3.addAll(list);
/* 180 */         list3.addAll(list1);
/* 181 */         list3.addAll(list2);
/* 182 */         List<StaticCloneData> list4 = Lists.reverse(list3);
/*     */         
/* 184 */         for (StaticCloneData commandclone$staticclonedata : list4) {
/*     */           
/* 186 */           TileEntity tileentity2 = world.getTileEntity(commandclone$staticclonedata.pos);
/*     */           
/* 188 */           if (tileentity2 instanceof IInventory)
/*     */           {
/* 190 */             ((IInventory)tileentity2).clear();
/*     */           }
/*     */           
/* 193 */           world.setBlockState(commandclone$staticclonedata.pos, Blocks.BARRIER.getDefaultState(), 2);
/*     */         } 
/*     */         
/* 196 */         i = 0;
/*     */         
/* 198 */         for (StaticCloneData commandclone$staticclonedata1 : list3) {
/*     */           
/* 200 */           if (world.setBlockState(commandclone$staticclonedata1.pos, commandclone$staticclonedata1.blockState, 2))
/*     */           {
/* 202 */             i++;
/*     */           }
/*     */         } 
/*     */         
/* 206 */         for (StaticCloneData commandclone$staticclonedata2 : list1) {
/*     */           
/* 208 */           TileEntity tileentity3 = world.getTileEntity(commandclone$staticclonedata2.pos);
/*     */           
/* 210 */           if (commandclone$staticclonedata2.nbt != null && tileentity3 != null) {
/*     */             
/* 212 */             commandclone$staticclonedata2.nbt.setInteger("x", commandclone$staticclonedata2.pos.getX());
/* 213 */             commandclone$staticclonedata2.nbt.setInteger("y", commandclone$staticclonedata2.pos.getY());
/* 214 */             commandclone$staticclonedata2.nbt.setInteger("z", commandclone$staticclonedata2.pos.getZ());
/* 215 */             tileentity3.readFromNBT(commandclone$staticclonedata2.nbt);
/* 216 */             tileentity3.markDirty();
/*     */           } 
/*     */           
/* 219 */           world.setBlockState(commandclone$staticclonedata2.pos, commandclone$staticclonedata2.blockState, 2);
/*     */         } 
/*     */         
/* 222 */         for (StaticCloneData commandclone$staticclonedata3 : list4)
/*     */         {
/* 224 */           world.notifyNeighborsRespectDebug(commandclone$staticclonedata3.pos, commandclone$staticclonedata3.blockState.getBlock(), false);
/*     */         }
/*     */         
/* 227 */         List<NextTickListEntry> list5 = world.getPendingBlockUpdates(structureboundingbox, false);
/*     */         
/* 229 */         if (list5 != null)
/*     */         {
/* 231 */           for (NextTickListEntry nextticklistentry : list5) {
/*     */             
/* 233 */             if (structureboundingbox.isVecInside((Vec3i)nextticklistentry.position)) {
/*     */               
/* 235 */               BlockPos blockpos8 = nextticklistentry.position.add((Vec3i)blockpos3);
/* 236 */               world.scheduleBlockUpdate(blockpos8, nextticklistentry.getBlock(), (int)(nextticklistentry.scheduledTime - world.getWorldInfo().getWorldTotalTime()), nextticklistentry.priority);
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 241 */         if (i <= 0)
/*     */         {
/* 243 */           throw new CommandException("commands.clone.failed", new Object[0]);
/*     */         }
/*     */ 
/*     */         
/* 247 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 248 */         notifyCommandListener(sender, this, "commands.clone.success", new Object[] { Integer.valueOf(i) });
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 253 */         throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 258 */       throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 267 */     if (args.length > 0 && args.length <= 3)
/*     */     {
/* 269 */       return getTabCompletionCoordinate(args, 0, pos);
/*     */     }
/* 271 */     if (args.length > 3 && args.length <= 6)
/*     */     {
/* 273 */       return getTabCompletionCoordinate(args, 3, pos);
/*     */     }
/* 275 */     if (args.length > 6 && args.length <= 9)
/*     */     {
/* 277 */       return getTabCompletionCoordinate(args, 6, pos);
/*     */     }
/* 279 */     if (args.length == 10)
/*     */     {
/* 281 */       return getListOfStringsMatchingLastWord(args, new String[] { "replace", "masked", "filtered" });
/*     */     }
/* 283 */     if (args.length == 11)
/*     */     {
/* 285 */       return getListOfStringsMatchingLastWord(args, new String[] { "normal", "force", "move" });
/*     */     }
/*     */ 
/*     */     
/* 289 */     return (args.length == 12 && "filtered".equals(args[9])) ? getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys()) : Collections.<String>emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   static class StaticCloneData
/*     */   {
/*     */     public final BlockPos pos;
/*     */     
/*     */     public final IBlockState blockState;
/*     */     public final NBTTagCompound nbt;
/*     */     
/*     */     public StaticCloneData(BlockPos posIn, IBlockState stateIn, NBTTagCompound compoundIn) {
/* 301 */       this.pos = posIn;
/* 302 */       this.blockState = stateIn;
/* 303 */       this.nbt = compoundIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandClone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */