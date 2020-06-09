/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockBasePressurePlate
/*     */   extends Block {
/*  21 */   protected static final AxisAlignedBB PRESSED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.03125D, 0.9375D);
/*  22 */   protected static final AxisAlignedBB UNPRESSED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.0625D, 0.9375D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   protected static final AxisAlignedBB PRESSURE_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D);
/*     */ 
/*     */   
/*     */   protected BlockBasePressurePlate(Material materialIn) {
/*  31 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockBasePressurePlate(Material materialIn, MapColor mapColorIn) {
/*  36 */     super(materialIn, mapColorIn);
/*  37 */     setCreativeTab(CreativeTabs.REDSTONE);
/*  38 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  43 */     boolean flag = (getRedstoneStrength(state) > 0);
/*  44 */     return flag ? PRESSED_AABB : UNPRESSED_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  52 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  58 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnInBlock() {
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  89 */     return canBePlacedOn(worldIn, pos.down());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  99 */     if (!canBePlacedOn(worldIn, pos.down())) {
/*     */       
/* 101 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 102 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBePlacedOn(World worldIn, BlockPos pos) {
/* 108 */     return !(!worldIn.getBlockState(pos).isFullyOpaque() && !(worldIn.getBlockState(pos).getBlock() instanceof BlockFence));
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
/* 120 */     if (!worldIn.isRemote) {
/*     */       
/* 122 */       int i = getRedstoneStrength(state);
/*     */       
/* 124 */       if (i > 0)
/*     */       {
/* 126 */         updateState(worldIn, pos, state, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 136 */     if (!worldIn.isRemote) {
/*     */       
/* 138 */       int i = getRedstoneStrength(state);
/*     */       
/* 140 */       if (i == 0)
/*     */       {
/* 142 */         updateState(worldIn, pos, state, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state, int oldRedstoneStrength) {
/* 152 */     int i = computeRedstoneStrength(worldIn, pos);
/* 153 */     boolean flag = (oldRedstoneStrength > 0);
/* 154 */     boolean flag1 = (i > 0);
/*     */     
/* 156 */     if (oldRedstoneStrength != i) {
/*     */       
/* 158 */       state = setRedstoneStrength(state, i);
/* 159 */       worldIn.setBlockState(pos, state, 2);
/* 160 */       updateNeighbors(worldIn, pos);
/* 161 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 164 */     if (!flag1 && flag) {
/*     */       
/* 166 */       playClickOffSound(worldIn, pos);
/*     */     }
/* 168 */     else if (flag1 && !flag) {
/*     */       
/* 170 */       playClickOnSound(worldIn, pos);
/*     */     } 
/*     */     
/* 173 */     if (flag1)
/*     */     {
/* 175 */       worldIn.scheduleUpdate(new BlockPos((Vec3i)pos), this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void playClickOnSound(World paramWorld, BlockPos paramBlockPos);
/*     */ 
/*     */   
/*     */   protected abstract void playClickOffSound(World paramWorld, BlockPos paramBlockPos);
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 188 */     if (getRedstoneStrength(state) > 0)
/*     */     {
/* 190 */       updateNeighbors(worldIn, pos);
/*     */     }
/*     */     
/* 193 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateNeighbors(World worldIn, BlockPos pos) {
/* 201 */     worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 202 */     worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 207 */     return getRedstoneStrength(blockState);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 212 */     return (side == EnumFacing.UP) ? getRedstoneStrength(blockState) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 220 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 225 */     return EnumPushReaction.DESTROY;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract int computeRedstoneStrength(World paramWorld, BlockPos paramBlockPos);
/*     */   
/*     */   protected abstract int getRedstoneStrength(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState setRedstoneStrength(IBlockState paramIBlockState, int paramInt);
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 236 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBasePressurePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */