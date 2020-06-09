/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonStructureHelper
/*     */ {
/*     */   private final World world;
/*     */   private final BlockPos pistonPos;
/*     */   private final BlockPos blockToMove;
/*     */   private final EnumFacing moveDirection;
/*  20 */   private final List<BlockPos> toMove = Lists.newArrayList();
/*  21 */   private final List<BlockPos> toDestroy = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public BlockPistonStructureHelper(World worldIn, BlockPos posIn, EnumFacing pistonFacing, boolean extending) {
/*  25 */     this.world = worldIn;
/*  26 */     this.pistonPos = posIn;
/*     */     
/*  28 */     if (extending) {
/*     */       
/*  30 */       this.moveDirection = pistonFacing;
/*  31 */       this.blockToMove = posIn.offset(pistonFacing);
/*     */     }
/*     */     else {
/*     */       
/*  35 */       this.moveDirection = pistonFacing.getOpposite();
/*  36 */       this.blockToMove = posIn.offset(pistonFacing, 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMove() {
/*  42 */     this.toMove.clear();
/*  43 */     this.toDestroy.clear();
/*  44 */     IBlockState iblockstate = this.world.getBlockState(this.blockToMove);
/*     */     
/*  46 */     if (!BlockPistonBase.canPush(iblockstate, this.world, this.blockToMove, this.moveDirection, false, this.moveDirection)) {
/*     */       
/*  48 */       if (iblockstate.getMobilityFlag() == EnumPushReaction.DESTROY) {
/*     */         
/*  50 */         this.toDestroy.add(this.blockToMove);
/*  51 */         return true;
/*     */       } 
/*     */ 
/*     */       
/*  55 */       return false;
/*     */     } 
/*     */     
/*  58 */     if (!addBlockLine(this.blockToMove, this.moveDirection))
/*     */     {
/*  60 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  64 */     for (int i = 0; i < this.toMove.size(); i++) {
/*     */       
/*  66 */       BlockPos blockpos = this.toMove.get(i);
/*     */       
/*  68 */       if (this.world.getBlockState(blockpos).getBlock() == Blocks.SLIME_BLOCK && !addBranchingBlocks(blockpos))
/*     */       {
/*  70 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean addBlockLine(BlockPos origin, EnumFacing p_177251_2_) {
/*  80 */     IBlockState iblockstate = this.world.getBlockState(origin);
/*  81 */     Block block = iblockstate.getBlock();
/*     */     
/*  83 */     if (iblockstate.getMaterial() == Material.AIR)
/*     */     {
/*  85 */       return true;
/*     */     }
/*  87 */     if (!BlockPistonBase.canPush(iblockstate, this.world, origin, this.moveDirection, false, p_177251_2_))
/*     */     {
/*  89 */       return true;
/*     */     }
/*  91 */     if (origin.equals(this.pistonPos))
/*     */     {
/*  93 */       return true;
/*     */     }
/*  95 */     if (this.toMove.contains(origin))
/*     */     {
/*  97 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 101 */     int i = 1;
/*     */     
/* 103 */     if (i + this.toMove.size() > 12)
/*     */     {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 109 */     while (block == Blocks.SLIME_BLOCK) {
/*     */       
/* 111 */       BlockPos blockpos = origin.offset(this.moveDirection.getOpposite(), i);
/* 112 */       iblockstate = this.world.getBlockState(blockpos);
/* 113 */       block = iblockstate.getBlock();
/*     */       
/* 115 */       if (iblockstate.getMaterial() == Material.AIR || !BlockPistonBase.canPush(iblockstate, this.world, blockpos, this.moveDirection, false, this.moveDirection.getOpposite()) || blockpos.equals(this.pistonPos)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 120 */       i++;
/*     */       
/* 122 */       if (i + this.toMove.size() > 12)
/*     */       {
/* 124 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     int i1 = 0;
/*     */     
/* 130 */     for (int j = i - 1; j >= 0; j--) {
/*     */       
/* 132 */       this.toMove.add(origin.offset(this.moveDirection.getOpposite(), j));
/* 133 */       i1++;
/*     */     } 
/*     */     
/* 136 */     int j1 = 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 140 */       BlockPos blockpos1 = origin.offset(this.moveDirection, j1);
/* 141 */       int k = this.toMove.indexOf(blockpos1);
/*     */       
/* 143 */       if (k > -1) {
/*     */         
/* 145 */         reorderListAtCollision(i1, k);
/*     */         
/* 147 */         for (int l = 0; l <= k + i1; l++) {
/*     */           
/* 149 */           BlockPos blockpos2 = this.toMove.get(l);
/*     */           
/* 151 */           if (this.world.getBlockState(blockpos2).getBlock() == Blocks.SLIME_BLOCK && !addBranchingBlocks(blockpos2))
/*     */           {
/* 153 */             return false;
/*     */           }
/*     */         } 
/*     */         
/* 157 */         return true;
/*     */       } 
/*     */       
/* 160 */       iblockstate = this.world.getBlockState(blockpos1);
/*     */       
/* 162 */       if (iblockstate.getMaterial() == Material.AIR)
/*     */       {
/* 164 */         return true;
/*     */       }
/*     */       
/* 167 */       if (!BlockPistonBase.canPush(iblockstate, this.world, blockpos1, this.moveDirection, true, this.moveDirection) || blockpos1.equals(this.pistonPos))
/*     */       {
/* 169 */         return false;
/*     */       }
/*     */       
/* 172 */       if (iblockstate.getMobilityFlag() == EnumPushReaction.DESTROY) {
/*     */         
/* 174 */         this.toDestroy.add(blockpos1);
/* 175 */         return true;
/*     */       } 
/*     */       
/* 178 */       if (this.toMove.size() >= 12)
/*     */       {
/* 180 */         return false;
/*     */       }
/*     */       
/* 183 */       this.toMove.add(blockpos1);
/* 184 */       i1++;
/* 185 */       j1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reorderListAtCollision(int p_177255_1_, int p_177255_2_) {
/* 193 */     List<BlockPos> list = Lists.newArrayList();
/* 194 */     List<BlockPos> list1 = Lists.newArrayList();
/* 195 */     List<BlockPos> list2 = Lists.newArrayList();
/* 196 */     list.addAll(this.toMove.subList(0, p_177255_2_));
/* 197 */     list1.addAll(this.toMove.subList(this.toMove.size() - p_177255_1_, this.toMove.size()));
/* 198 */     list2.addAll(this.toMove.subList(p_177255_2_, this.toMove.size() - p_177255_1_));
/* 199 */     this.toMove.clear();
/* 200 */     this.toMove.addAll(list);
/* 201 */     this.toMove.addAll(list1);
/* 202 */     this.toMove.addAll(list2);
/*     */   } private boolean addBranchingBlocks(BlockPos p_177250_1_) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 207 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 209 */       if (enumfacing.getAxis() != this.moveDirection.getAxis() && !addBlockLine(p_177250_1_.offset(enumfacing), enumfacing))
/*     */       {
/* 211 */         return false;
/*     */       }
/*     */       b++; }
/*     */     
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getBlocksToMove() {
/* 220 */     return this.toMove;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BlockPos> getBlocksToDestroy() {
/* 225 */     return this.toDestroy;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\BlockPistonStructureHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */