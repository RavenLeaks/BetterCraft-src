/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBanner
/*     */   extends BlockContainer {
/*  29 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  30 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*  31 */   protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
/*     */ 
/*     */   
/*     */   protected BlockBanner() {
/*  35 */     super(Material.WOOD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  43 */     return I18n.translateToLocal("item.banner.white.name");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  49 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnInBlock() {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  83 */     return (TileEntity)new TileEntityBanner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  91 */     return Items.BANNER;
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack getTileDataItemStack(World worldIn, BlockPos pos) {
/*  96 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*  97 */     return (tileentity instanceof TileEntityBanner) ? ((TileEntityBanner)tileentity).func_190615_l() : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 102 */     ItemStack itemstack = getTileDataItemStack(worldIn, pos);
/* 103 */     return itemstack.func_190926_b() ? new ItemStack(Items.BANNER) : itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 111 */     ItemStack itemstack = getTileDataItemStack(worldIn, pos);
/*     */     
/* 113 */     if (itemstack.func_190926_b()) {
/*     */       
/* 115 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     }
/*     */     else {
/*     */       
/* 119 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 125 */     return (!hasInvalidNeighbor(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 130 */     if (te instanceof TileEntityBanner) {
/*     */       
/* 132 */       TileEntityBanner tileentitybanner = (TileEntityBanner)te;
/* 133 */       ItemStack itemstack = tileentitybanner.func_190615_l();
/* 134 */       spawnAsEntity(worldIn, pos, itemstack);
/*     */     }
/*     */     else {
/*     */       
/* 138 */       super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 144 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public static class BlockBannerHanging
/*     */     extends BlockBanner {
/* 149 */     protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 0.78125D, 1.0D);
/* 150 */     protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.78125D, 0.125D);
/* 151 */     protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 0.78125D, 1.0D);
/* 152 */     protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 0.78125D, 1.0D);
/*     */ 
/*     */     
/*     */     public BlockBannerHanging() {
/* 156 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 161 */       return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 166 */       return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */     }
/*     */ 
/*     */     
/*     */     public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 171 */       switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */ 
/*     */         
/*     */         default:
/* 175 */           return NORTH_AABB;
/*     */         
/*     */         case SOUTH:
/* 178 */           return SOUTH_AABB;
/*     */         
/*     */         case WEST:
/* 181 */           return WEST_AABB;
/*     */         case EAST:
/*     */           break;
/* 184 */       }  return EAST_AABB;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 190 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 192 */       if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getMaterial().isSolid()) {
/*     */         
/* 194 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 195 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 198 */       super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 203 */       EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */       
/* 205 */       if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */       {
/* 207 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*     */       
/* 210 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 215 */       return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     }
/*     */ 
/*     */     
/*     */     protected BlockStateContainer createBlockState() {
/* 220 */       return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockBannerStanding
/*     */     extends BlockBanner
/*     */   {
/*     */     public BlockBannerStanding() {
/* 228 */       setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 233 */       return STANDING_AABB;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 238 */       return state.withProperty((IProperty)ROTATION, Integer.valueOf(rot.rotate(((Integer)state.getValue((IProperty)ROTATION)).intValue(), 16)));
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 243 */       return state.withProperty((IProperty)ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(((Integer)state.getValue((IProperty)ROTATION)).intValue(), 16)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 248 */       if (!worldIn.getBlockState(pos.down()).getMaterial().isSolid()) {
/*     */         
/* 250 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 251 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 254 */       super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getStateFromMeta(int meta) {
/* 259 */       return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetaFromState(IBlockState state) {
/* 264 */       return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     protected BlockStateContainer createBlockState() {
/* 269 */       return new BlockStateContainer(this, new IProperty[] { (IProperty)ROTATION });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */