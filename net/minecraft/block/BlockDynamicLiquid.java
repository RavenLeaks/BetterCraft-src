/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDynamicLiquid
/*     */   extends BlockLiquid {
/*     */   int adjacentSourceBlocks;
/*     */   
/*     */   protected BlockDynamicLiquid(Material materialIn) {
/*  19 */     super(materialIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private void placeStaticBlock(World worldIn, BlockPos pos, IBlockState currentState) {
/*  24 */     worldIn.setBlockState(pos, getStaticBlock(this.blockMaterial).getDefaultState().withProperty((IProperty)LEVEL, currentState.getValue((IProperty)LEVEL)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  29 */     int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*  30 */     int j = 1;
/*     */     
/*  32 */     if (this.blockMaterial == Material.LAVA && !worldIn.provider.doesWaterVaporize())
/*     */     {
/*  34 */       j = 2;
/*     */     }
/*     */     
/*  37 */     int k = tickRate(worldIn);
/*     */     
/*  39 */     if (i > 0) {
/*     */       
/*  41 */       int l = -100;
/*  42 */       this.adjacentSourceBlocks = 0;
/*     */       
/*  44 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/*  46 */         l = checkAdjacentBlock(worldIn, pos.offset(enumfacing), l);
/*     */       }
/*     */       
/*  49 */       int i1 = l + j;
/*     */       
/*  51 */       if (i1 >= 8 || l < 0)
/*     */       {
/*  53 */         i1 = -1;
/*     */       }
/*     */       
/*  56 */       int j1 = getDepth(worldIn.getBlockState(pos.up()));
/*     */       
/*  58 */       if (j1 >= 0)
/*     */       {
/*  60 */         if (j1 >= 8) {
/*     */           
/*  62 */           i1 = j1;
/*     */         }
/*     */         else {
/*     */           
/*  66 */           i1 = j1 + 8;
/*     */         } 
/*     */       }
/*     */       
/*  70 */       if (this.adjacentSourceBlocks >= 2 && this.blockMaterial == Material.WATER) {
/*     */         
/*  72 */         IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */         
/*  74 */         if (iblockstate.getMaterial().isSolid()) {
/*     */           
/*  76 */           i1 = 0;
/*     */         }
/*  78 */         else if (iblockstate.getMaterial() == this.blockMaterial && ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() == 0) {
/*     */           
/*  80 */           i1 = 0;
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       if (this.blockMaterial == Material.LAVA && i < 8 && i1 < 8 && i1 > i && rand.nextInt(4) != 0)
/*     */       {
/*  86 */         k *= 4;
/*     */       }
/*     */       
/*  89 */       if (i1 == i) {
/*     */         
/*  91 */         placeStaticBlock(worldIn, pos, state);
/*     */       }
/*     */       else {
/*     */         
/*  95 */         i = i1;
/*     */         
/*  97 */         if (i1 < 0)
/*     */         {
/*  99 */           worldIn.setBlockToAir(pos);
/*     */         }
/*     */         else
/*     */         {
/* 103 */           state = state.withProperty((IProperty)LEVEL, Integer.valueOf(i1));
/* 104 */           worldIn.setBlockState(pos, state, 2);
/* 105 */           worldIn.scheduleUpdate(pos, this, k);
/* 106 */           worldIn.notifyNeighborsOfStateChange(pos, this, false);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 112 */       placeStaticBlock(worldIn, pos, state);
/*     */     } 
/*     */     
/* 115 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */     
/* 117 */     if (canFlowInto(worldIn, pos.down(), iblockstate1)) {
/*     */       
/* 119 */       if (this.blockMaterial == Material.LAVA && worldIn.getBlockState(pos.down()).getMaterial() == Material.WATER) {
/*     */         
/* 121 */         worldIn.setBlockState(pos.down(), Blocks.STONE.getDefaultState());
/* 122 */         triggerMixEffects(worldIn, pos.down());
/*     */         
/*     */         return;
/*     */       } 
/* 126 */       if (i >= 8)
/*     */       {
/* 128 */         tryFlowInto(worldIn, pos.down(), iblockstate1, i);
/*     */       }
/*     */       else
/*     */       {
/* 132 */         tryFlowInto(worldIn, pos.down(), iblockstate1, i + 8);
/*     */       }
/*     */     
/* 135 */     } else if (i >= 0 && (i == 0 || isBlocked(worldIn, pos.down(), iblockstate1))) {
/*     */       
/* 137 */       Set<EnumFacing> set = getPossibleFlowDirections(worldIn, pos);
/* 138 */       int k1 = i + j;
/*     */       
/* 140 */       if (i >= 8)
/*     */       {
/* 142 */         k1 = 1;
/*     */       }
/*     */       
/* 145 */       if (k1 >= 8) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 150 */       for (EnumFacing enumfacing1 : set)
/*     */       {
/* 152 */         tryFlowInto(worldIn, pos.offset(enumfacing1), worldIn.getBlockState(pos.offset(enumfacing1)), k1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryFlowInto(World worldIn, BlockPos pos, IBlockState state, int level) {
/* 159 */     if (canFlowInto(worldIn, pos, state)) {
/*     */       
/* 161 */       if (state.getMaterial() != Material.AIR)
/*     */       {
/* 163 */         if (this.blockMaterial == Material.LAVA) {
/*     */           
/* 165 */           triggerMixEffects(worldIn, pos);
/*     */         }
/*     */         else {
/*     */           
/* 169 */           state.getBlock().dropBlockAsItem(worldIn, pos, state, 0);
/*     */         } 
/*     */       }
/*     */       
/* 173 */       worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(level)), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSlopeDistance(World worldIn, BlockPos pos, int distance, EnumFacing calculateFlowCost) {
/* 179 */     int i = 1000;
/*     */     
/* 181 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 183 */       if (enumfacing != calculateFlowCost) {
/*     */         
/* 185 */         BlockPos blockpos = pos.offset(enumfacing);
/* 186 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/* 188 */         if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */           
/* 190 */           if (!isBlocked(worldIn, blockpos.down(), iblockstate))
/*     */           {
/* 192 */             return distance;
/*     */           }
/*     */           
/* 195 */           if (distance < getSlopeFindDistance(worldIn)) {
/*     */             
/* 197 */             int j = getSlopeDistance(worldIn, blockpos, distance + 1, enumfacing.getOpposite());
/*     */             
/* 199 */             if (j < i)
/*     */             {
/* 201 */               i = j;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 208 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSlopeFindDistance(World worldIn) {
/* 213 */     return (this.blockMaterial == Material.LAVA && !worldIn.provider.doesWaterVaporize()) ? 2 : 4;
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<EnumFacing> getPossibleFlowDirections(World worldIn, BlockPos pos) {
/* 218 */     int i = 1000;
/* 219 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 221 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 223 */       BlockPos blockpos = pos.offset(enumfacing);
/* 224 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 226 */       if (!isBlocked(worldIn, blockpos, iblockstate) && (iblockstate.getMaterial() != this.blockMaterial || ((Integer)iblockstate.getValue((IProperty)LEVEL)).intValue() > 0)) {
/*     */         int j;
/*     */ 
/*     */         
/* 230 */         if (isBlocked(worldIn, blockpos.down(), worldIn.getBlockState(blockpos.down()))) {
/*     */           
/* 232 */           j = getSlopeDistance(worldIn, blockpos, 1, enumfacing.getOpposite());
/*     */         }
/*     */         else {
/*     */           
/* 236 */           j = 0;
/*     */         } 
/*     */         
/* 239 */         if (j < i)
/*     */         {
/* 241 */           set.clear();
/*     */         }
/*     */         
/* 244 */         if (j <= i) {
/*     */           
/* 246 */           set.add(enumfacing);
/* 247 */           i = j;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos, IBlockState state) {
/* 257 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 259 */     if (!(block instanceof BlockDoor) && block != Blocks.STANDING_SIGN && block != Blocks.LADDER && block != Blocks.REEDS)
/*     */     {
/* 261 */       return (block.blockMaterial != Material.PORTAL && block.blockMaterial != Material.STRUCTURE_VOID) ? block.blockMaterial.blocksMovement() : true;
/*     */     }
/*     */ 
/*     */     
/* 265 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int checkAdjacentBlock(World worldIn, BlockPos pos, int currentMinLevel) {
/* 271 */     int i = getDepth(worldIn.getBlockState(pos));
/*     */     
/* 273 */     if (i < 0)
/*     */     {
/* 275 */       return currentMinLevel;
/*     */     }
/*     */ 
/*     */     
/* 279 */     if (i == 0)
/*     */     {
/* 281 */       this.adjacentSourceBlocks++;
/*     */     }
/*     */     
/* 284 */     if (i >= 8)
/*     */     {
/* 286 */       i = 0;
/*     */     }
/*     */     
/* 289 */     return (currentMinLevel >= 0 && i >= currentMinLevel) ? currentMinLevel : i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canFlowInto(World worldIn, BlockPos pos, IBlockState state) {
/* 295 */     Material material = state.getMaterial();
/* 296 */     return (material != this.blockMaterial && material != Material.LAVA && !isBlocked(worldIn, pos, state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 304 */     if (!checkForMixing(worldIn, pos, state))
/*     */     {
/* 306 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDynamicLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */