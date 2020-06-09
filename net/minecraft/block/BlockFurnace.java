/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFurnace extends BlockContainer {
/*  32 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*     */   
/*     */   private final boolean isBurning;
/*     */   private static boolean keepInventory;
/*     */   
/*     */   protected BlockFurnace(boolean isBurning) {
/*  38 */     super(Material.ROCK);
/*  39 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  40 */     this.isBurning = isBurning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  48 */     return Item.getItemFromBlock(Blocks.FURNACE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  56 */     setDefaultFacing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
/*  61 */     if (!worldIn.isRemote) {
/*     */       
/*  63 */       IBlockState iblockstate = worldIn.getBlockState(pos.north());
/*  64 */       IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
/*  65 */       IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
/*  66 */       IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
/*  67 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  69 */       if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
/*     */         
/*  71 */         enumfacing = EnumFacing.SOUTH;
/*     */       }
/*  73 */       else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
/*     */         
/*  75 */         enumfacing = EnumFacing.NORTH;
/*     */       }
/*  77 */       else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
/*     */         
/*  79 */         enumfacing = EnumFacing.EAST;
/*     */       }
/*  81 */       else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
/*     */         
/*  83 */         enumfacing = EnumFacing.WEST;
/*     */       } 
/*     */       
/*  86 */       worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/*  93 */     if (this.isBurning) {
/*     */       
/*  95 */       EnumFacing enumfacing = (EnumFacing)stateIn.getValue((IProperty)FACING);
/*  96 */       double d0 = pos.getX() + 0.5D;
/*  97 */       double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
/*  98 */       double d2 = pos.getZ() + 0.5D;
/*  99 */       double d3 = 0.52D;
/* 100 */       double d4 = rand.nextDouble() * 0.6D - 0.3D;
/*     */       
/* 102 */       if (rand.nextDouble() < 0.1D)
/*     */       {
/* 104 */         worldIn.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*     */       }
/*     */       
/* 107 */       switch (enumfacing) {
/*     */         
/*     */         case WEST:
/* 110 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 111 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 115 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 116 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 120 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
/* 121 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 125 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
/* 126 */           worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 133 */     if (worldIn.isRemote)
/*     */     {
/* 135 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 139 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 141 */     if (tileentity instanceof TileEntityFurnace) {
/*     */       
/* 143 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 144 */       playerIn.addStat(StatList.FURNACE_INTERACTION);
/*     */     } 
/*     */     
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setState(boolean active, World worldIn, BlockPos pos) {
/* 153 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 154 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 155 */     keepInventory = true;
/*     */     
/* 157 */     if (active) {
/*     */       
/* 159 */       worldIn.setBlockState(pos, Blocks.LIT_FURNACE.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 160 */       worldIn.setBlockState(pos, Blocks.LIT_FURNACE.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     }
/*     */     else {
/*     */       
/* 164 */       worldIn.setBlockState(pos, Blocks.FURNACE.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/* 165 */       worldIn.setBlockState(pos, Blocks.FURNACE.getDefaultState().withProperty((IProperty)FACING, iblockstate.getValue((IProperty)FACING)), 3);
/*     */     } 
/*     */     
/* 168 */     keepInventory = false;
/*     */     
/* 170 */     if (tileentity != null) {
/*     */       
/* 172 */       tileentity.validate();
/* 173 */       worldIn.setTileEntity(pos, tileentity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 182 */     return (TileEntity)new TileEntityFurnace();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 191 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 199 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()), 2);
/*     */     
/* 201 */     if (stack.hasDisplayName()) {
/*     */       
/* 203 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 205 */       if (tileentity instanceof TileEntityFurnace)
/*     */       {
/* 207 */         ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 217 */     if (!keepInventory) {
/*     */       
/* 219 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 221 */       if (tileentity instanceof TileEntityFurnace) {
/*     */         
/* 223 */         InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 224 */         worldIn.updateComparatorOutputLevel(pos, this);
/*     */       } 
/*     */     } 
/*     */     
/* 228 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 238 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 243 */     return new ItemStack(Blocks.FURNACE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 252 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 260 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 262 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 264 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 267 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 275 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 284 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 293 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 298 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */