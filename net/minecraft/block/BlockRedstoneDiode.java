/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockRedstoneDiode extends BlockHorizontal {
/*  19 */   protected static final AxisAlignedBB REDSTONE_DIODE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
/*     */ 
/*     */   
/*     */   protected final boolean isRepeaterPowered;
/*     */ 
/*     */   
/*     */   protected BlockRedstoneDiode(boolean powered) {
/*  26 */     super(Material.CIRCUITS);
/*  27 */     this.isRepeaterPowered = powered;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  32 */     return REDSTONE_DIODE_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  42 */     return worldIn.getBlockState(pos.down()).isFullyOpaque() ? super.canPlaceBlockAt(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/*  47 */     return worldIn.getBlockState(pos.down()).isFullyOpaque();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  59 */     if (!isLocked((IBlockAccess)worldIn, pos, state)) {
/*     */       
/*  61 */       boolean flag = shouldBePowered(worldIn, pos, state);
/*     */       
/*  63 */       if (this.isRepeaterPowered && !flag) {
/*     */         
/*  65 */         worldIn.setBlockState(pos, getUnpoweredState(state), 2);
/*     */       }
/*  67 */       else if (!this.isRepeaterPowered) {
/*     */         
/*  69 */         worldIn.setBlockState(pos, getPoweredState(state), 2);
/*     */         
/*  71 */         if (!flag)
/*     */         {
/*  73 */           worldIn.updateBlockTick(pos, getPoweredState(state).getBlock(), getTickDelay(state), -1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  81 */     return (side.getAxis() != EnumFacing.Axis.Y);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  86 */     return this.isRepeaterPowered;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  91 */     return blockState.getWeakPower(blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  96 */     if (!isPowered(blockState))
/*     */     {
/*  98 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 102 */     return (blockState.getValue((IProperty)FACING) == side) ? getActiveSignal(blockAccess, pos, blockState) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 113 */     if (canBlockStay(worldIn, pos)) {
/*     */       
/* 115 */       updateState(worldIn, pos, state);
/*     */     }
/*     */     else {
/*     */       
/* 119 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 120 */       worldIn.setBlockToAir(pos); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 122 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 124 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 131 */     if (!isLocked((IBlockAccess)worldIn, pos, state)) {
/*     */       
/* 133 */       boolean flag = shouldBePowered(worldIn, pos, state);
/*     */       
/* 135 */       if (this.isRepeaterPowered != flag && !worldIn.isBlockTickPending(pos, this)) {
/*     */         
/* 137 */         int i = -1;
/*     */         
/* 139 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/*     */           
/* 141 */           i = -3;
/*     */         }
/* 143 */         else if (this.isRepeaterPowered) {
/*     */           
/* 145 */           i = -2;
/*     */         } 
/*     */         
/* 148 */         worldIn.updateBlockTick(pos, this, getDelay(state), i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 155 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/* 160 */     return (calculateInputStrength(worldIn, pos, state) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 165 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 166 */     BlockPos blockpos = pos.offset(enumfacing);
/* 167 */     int i = worldIn.getRedstonePower(blockpos, enumfacing);
/*     */     
/* 169 */     if (i >= 15)
/*     */     {
/* 171 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 175 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 176 */     return Math.max(i, (iblockstate.getBlock() == Blocks.REDSTONE_WIRE) ? ((Integer)iblockstate.getValue((IProperty)BlockRedstoneWire.POWER)).intValue() : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 182 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 183 */     EnumFacing enumfacing1 = enumfacing.rotateY();
/* 184 */     EnumFacing enumfacing2 = enumfacing.rotateYCCW();
/* 185 */     return Math.max(getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1), getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 190 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 191 */     Block block = iblockstate.getBlock();
/*     */     
/* 193 */     if (isAlternateInput(iblockstate)) {
/*     */       
/* 195 */       if (block == Blocks.REDSTONE_BLOCK)
/*     */       {
/* 197 */         return 15;
/*     */       }
/*     */ 
/*     */       
/* 201 */       return (block == Blocks.REDSTONE_WIRE) ? ((Integer)iblockstate.getValue((IProperty)BlockRedstoneWire.POWER)).intValue() : worldIn.getStrongPower(pos, side);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 206 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 224 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 232 */     if (shouldBePowered(worldIn, pos, state))
/*     */     {
/* 234 */       worldIn.scheduleUpdate(pos, this, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 243 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
/* 248 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 249 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 250 */     worldIn.func_190524_a(blockpos, this, pos);
/* 251 */     worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/* 259 */     if (this.isRepeaterPowered) {
/*     */       byte b; int i; EnumFacing[] arrayOfEnumFacing;
/* 261 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 263 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */         b++; }
/*     */     
/*     */     } 
/* 267 */     super.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 275 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAlternateInput(IBlockState state) {
/* 280 */     return state.canProvidePower();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 285 */     return 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isDiode(IBlockState state) {
/* 290 */     return !(!Blocks.UNPOWERED_REPEATER.isSameDiode(state) && !Blocks.UNPOWERED_COMPARATOR.isSameDiode(state));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSameDiode(IBlockState state) {
/* 295 */     Block block = state.getBlock();
/* 296 */     return !(block != getPoweredState(getDefaultState()).getBlock() && block != getUnpoweredState(getDefaultState()).getBlock());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state) {
/* 301 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/* 302 */     BlockPos blockpos = pos.offset(enumfacing);
/*     */     
/* 304 */     if (isDiode(worldIn.getBlockState(blockpos)))
/*     */     {
/* 306 */       return (worldIn.getBlockState(blockpos).getValue((IProperty)FACING) != enumfacing);
/*     */     }
/*     */ 
/*     */     
/* 310 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getTickDelay(IBlockState state) {
/* 316 */     return getDelay(state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int getDelay(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState getPoweredState(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState getUnpoweredState(IBlockState paramIBlockState);
/*     */   
/*     */   public boolean isAssociatedBlock(Block other) {
/* 327 */     return isSameDiode(other.getDefaultState());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 332 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 337 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRedstoneDiode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */