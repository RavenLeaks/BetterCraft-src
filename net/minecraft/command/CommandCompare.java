/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandCompare
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  22 */     return "testforblocks";
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
/*  38 */     return "commands.compare.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
/*  46 */     if (args.length < 9)
/*     */     {
/*  48 */       throw new WrongUsageException("commands.compare.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  52 */     sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
/*  53 */     BlockPos blockpos = parseBlockPos(sender, args, 0, false);
/*  54 */     BlockPos blockpos1 = parseBlockPos(sender, args, 3, false);
/*  55 */     BlockPos blockpos2 = parseBlockPos(sender, args, 6, false);
/*  56 */     StructureBoundingBox structureboundingbox = new StructureBoundingBox((Vec3i)blockpos, (Vec3i)blockpos1);
/*  57 */     StructureBoundingBox structureboundingbox1 = new StructureBoundingBox((Vec3i)blockpos2, (Vec3i)blockpos2.add(structureboundingbox.getLength()));
/*  58 */     int i = structureboundingbox.getXSize() * structureboundingbox.getYSize() * structureboundingbox.getZSize();
/*     */     
/*  60 */     if (i > 524288)
/*     */     {
/*  62 */       throw new CommandException("commands.compare.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(524288) });
/*     */     }
/*  64 */     if (structureboundingbox.minY >= 0 && structureboundingbox.maxY < 256 && structureboundingbox1.minY >= 0 && structureboundingbox1.maxY < 256) {
/*     */       
/*  66 */       World world = sender.getEntityWorld();
/*     */       
/*  68 */       if (world.isAreaLoaded(structureboundingbox) && world.isAreaLoaded(structureboundingbox1))
/*     */       {
/*  70 */         boolean flag = false;
/*     */         
/*  72 */         if (args.length > 9 && "masked".equals(args[9]))
/*     */         {
/*  74 */           flag = true;
/*     */         }
/*     */         
/*  77 */         i = 0;
/*  78 */         BlockPos blockpos3 = new BlockPos(structureboundingbox1.minX - structureboundingbox.minX, structureboundingbox1.minY - structureboundingbox.minY, structureboundingbox1.minZ - structureboundingbox.minZ);
/*  79 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*  80 */         BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */         
/*  82 */         for (int j = structureboundingbox.minZ; j <= structureboundingbox.maxZ; j++) {
/*     */           
/*  84 */           for (int k = structureboundingbox.minY; k <= structureboundingbox.maxY; k++) {
/*     */             
/*  86 */             for (int l = structureboundingbox.minX; l <= structureboundingbox.maxX; l++) {
/*     */               
/*  88 */               blockpos$mutableblockpos.setPos(l, k, j);
/*  89 */               blockpos$mutableblockpos1.setPos(l + blockpos3.getX(), k + blockpos3.getY(), j + blockpos3.getZ());
/*  90 */               boolean flag1 = false;
/*  91 */               IBlockState iblockstate = world.getBlockState((BlockPos)blockpos$mutableblockpos);
/*     */               
/*  93 */               if (!flag || iblockstate.getBlock() != Blocks.AIR) {
/*     */                 
/*  95 */                 if (iblockstate == world.getBlockState((BlockPos)blockpos$mutableblockpos1)) {
/*     */                   
/*  97 */                   TileEntity tileentity = world.getTileEntity((BlockPos)blockpos$mutableblockpos);
/*  98 */                   TileEntity tileentity1 = world.getTileEntity((BlockPos)blockpos$mutableblockpos1);
/*     */                   
/* 100 */                   if (tileentity != null && tileentity1 != null)
/*     */                   {
/* 102 */                     NBTTagCompound nbttagcompound = tileentity.writeToNBT(new NBTTagCompound());
/* 103 */                     nbttagcompound.removeTag("x");
/* 104 */                     nbttagcompound.removeTag("y");
/* 105 */                     nbttagcompound.removeTag("z");
/* 106 */                     NBTTagCompound nbttagcompound1 = tileentity1.writeToNBT(new NBTTagCompound());
/* 107 */                     nbttagcompound1.removeTag("x");
/* 108 */                     nbttagcompound1.removeTag("y");
/* 109 */                     nbttagcompound1.removeTag("z");
/*     */                     
/* 111 */                     if (!nbttagcompound.equals(nbttagcompound1))
/*     */                     {
/* 113 */                       flag1 = true;
/*     */                     }
/*     */                   }
/* 116 */                   else if (tileentity != null)
/*     */                   {
/* 118 */                     flag1 = true;
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 123 */                   flag1 = true;
/*     */                 } 
/*     */                 
/* 126 */                 i++;
/*     */                 
/* 128 */                 if (flag1)
/*     */                 {
/* 130 */                   throw new CommandException("commands.compare.failed", new Object[0]);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 137 */         sender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, i);
/* 138 */         notifyCommandListener(sender, this, "commands.compare.success", new Object[] { Integer.valueOf(i) });
/*     */       }
/*     */       else
/*     */       {
/* 142 */         throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 147 */       throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
/* 154 */     if (args.length > 0 && args.length <= 3)
/*     */     {
/* 156 */       return getTabCompletionCoordinate(args, 0, pos);
/*     */     }
/* 158 */     if (args.length > 3 && args.length <= 6)
/*     */     {
/* 160 */       return getTabCompletionCoordinate(args, 3, pos);
/*     */     }
/* 162 */     if (args.length > 6 && args.length <= 9)
/*     */     {
/* 164 */       return getTabCompletionCoordinate(args, 6, pos);
/*     */     }
/*     */ 
/*     */     
/* 168 */     return (args.length == 10) ? getListOfStringsMatchingLastWord(args, new String[] { "masked", "all" }) : Collections.<String>emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandCompare.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */