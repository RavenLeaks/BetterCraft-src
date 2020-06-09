/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTorch
/*     */   extends Block {
/*  27 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
/*     */       {
/*     */         public boolean apply(@Nullable EnumFacing p_apply_1_)
/*     */         {
/*  31 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*  34 */   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);
/*  35 */   protected static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.3499999940395355D, 0.20000000298023224D, 0.699999988079071D, 0.6499999761581421D, 0.800000011920929D, 1.0D);
/*  36 */   protected static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.3499999940395355D, 0.20000000298023224D, 0.0D, 0.6499999761581421D, 0.800000011920929D, 0.30000001192092896D);
/*  37 */   protected static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.699999988079071D, 0.20000000298023224D, 0.3499999940395355D, 1.0D, 0.800000011920929D, 0.6499999761581421D);
/*  38 */   protected static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0D, 0.20000000298023224D, 0.3499999940395355D, 0.30000001192092896D, 0.800000011920929D, 0.6499999761581421D);
/*     */ 
/*     */   
/*     */   protected BlockTorch() {
/*  42 */     super(Material.CIRCUITS);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  44 */     setTickRandomly(true);
/*  45 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  50 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/*  53 */         return TORCH_EAST_AABB;
/*     */       
/*     */       case WEST:
/*  56 */         return TORCH_WEST_AABB;
/*     */       
/*     */       case SOUTH:
/*  59 */         return TORCH_SOUTH_AABB;
/*     */       
/*     */       case NORTH:
/*  62 */         return TORCH_NORTH_AABB;
/*     */     } 
/*     */     
/*  65 */     return STANDING_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  72 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(World worldIn, BlockPos pos) {
/*  90 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  91 */     boolean flag = !(block != Blocks.END_GATEWAY && block != Blocks.LIT_PUMPKIN);
/*     */     
/*  93 */     if (worldIn.getBlockState(pos).isFullyOpaque())
/*     */     {
/*  95 */       return !flag;
/*     */     }
/*     */ 
/*     */     
/*  99 */     boolean flag1 = !(!(block instanceof BlockFence) && block != Blocks.GLASS && block != Blocks.COBBLESTONE_WALL && block != Blocks.STAINED_GLASS);
/* 100 */     return (flag1 && !flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 106 */     for (EnumFacing enumfacing : FACING.getAllowedValues()) {
/*     */       
/* 108 */       if (canPlaceAt(worldIn, pos, enumfacing))
/*     */       {
/* 110 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
/* 119 */     BlockPos blockpos = pos.offset(facing.getOpposite());
/* 120 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 121 */     Block block = iblockstate.getBlock();
/* 122 */     BlockFaceShape blockfaceshape = iblockstate.func_193401_d((IBlockAccess)worldIn, blockpos, facing);
/*     */     
/* 124 */     if (facing.equals(EnumFacing.UP) && canPlaceOn(worldIn, blockpos))
/*     */     {
/* 126 */       return true;
/*     */     }
/* 128 */     if (facing != EnumFacing.UP && facing != EnumFacing.DOWN)
/*     */     {
/* 130 */       return (!func_193382_c(block) && blockfaceshape == BlockFaceShape.SOLID);
/*     */     }
/*     */ 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 144 */     if (canPlaceAt(worldIn, pos, facing))
/*     */     {
/* 146 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */ 
/*     */     
/* 150 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 152 */       if (canPlaceAt(worldIn, pos, enumfacing))
/*     */       {
/* 154 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 167 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 177 */     onNeighborChangeInternal(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
/* 182 */     if (!checkForDrop(worldIn, pos, state))
/*     */     {
/* 184 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 188 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 189 */     EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
/* 190 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 191 */     BlockPos blockpos = pos.offset(enumfacing1);
/* 192 */     boolean flag = false;
/*     */     
/* 194 */     if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).func_193401_d((IBlockAccess)worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
/*     */       
/* 196 */       flag = true;
/*     */     }
/* 198 */     else if (enumfacing$axis.isVertical() && !canPlaceOn(worldIn, blockpos)) {
/*     */       
/* 200 */       flag = true;
/*     */     } 
/*     */     
/* 203 */     if (flag) {
/*     */       
/* 205 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 206 */       worldIn.setBlockToAir(pos);
/* 207 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 218 */     if (state.getBlock() == this && canPlaceAt(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING)))
/*     */     {
/* 220 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 224 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 226 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 227 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 230 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 236 */     EnumFacing enumfacing = (EnumFacing)stateIn.getValue((IProperty)FACING);
/* 237 */     double d0 = pos.getX() + 0.5D;
/* 238 */     double d1 = pos.getY() + 0.7D;
/* 239 */     double d2 = pos.getZ() + 0.5D;
/* 240 */     double d3 = 0.22D;
/* 241 */     double d4 = 0.27D;
/*     */     
/* 243 */     if (enumfacing.getAxis().isHorizontal()) {
/*     */       
/* 245 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 246 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D * enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/* 247 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.27D * enumfacing1.getFrontOffsetX(), d1 + 0.22D, d2 + 0.27D * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     else {
/*     */       
/* 251 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/* 252 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 258 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 266 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 268 */     switch (meta)
/*     */     
/*     */     { case 1:
/* 271 */         iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.EAST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 291 */         return iblockstate;case 2: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.WEST); return iblockstate;case 3: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH); return iblockstate;case 4: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH); return iblockstate; }  iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP); return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 299 */     int i = 0;
/*     */     
/* 301 */     switch ((EnumFacing)state.getValue((IProperty)FACING))
/*     */     
/*     */     { case EAST:
/* 304 */         i |= 0x1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 325 */         return i;case WEST: i |= 0x2; return i;case SOUTH: i |= 0x3; return i;case NORTH: i |= 0x4; return i; }  i |= 0x5; return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 334 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 343 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 348 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 353 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */