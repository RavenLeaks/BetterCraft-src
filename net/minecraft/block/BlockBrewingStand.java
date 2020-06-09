/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockBrewingStand extends BlockContainer {
/*  36 */   public static final PropertyBool[] HAS_BOTTLE = new PropertyBool[] { PropertyBool.create("has_bottle_0"), PropertyBool.create("has_bottle_1"), PropertyBool.create("has_bottle_2") };
/*  37 */   protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
/*  38 */   protected static final AxisAlignedBB STICK_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
/*     */ 
/*     */   
/*     */   public BlockBrewingStand() {
/*  42 */     super(Material.IRON);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)HAS_BOTTLE[0], Boolean.valueOf(false)).withProperty((IProperty)HAS_BOTTLE[1], Boolean.valueOf(false)).withProperty((IProperty)HAS_BOTTLE[2], Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  51 */     return I18n.translateToLocal("item.brewingStand.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  68 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  76 */     return (TileEntity)new TileEntityBrewingStand();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  86 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, STICK_AABB);
/*  87 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  92 */     return BASE_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  97 */     if (worldIn.isRemote)
/*     */     {
/*  99 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 103 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 105 */     if (tileentity instanceof TileEntityBrewingStand) {
/*     */       
/* 107 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 108 */       playerIn.addStat(StatList.BREWINGSTAND_INTERACTION);
/*     */     } 
/*     */     
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 120 */     if (stack.hasDisplayName()) {
/*     */       
/* 122 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 124 */       if (tileentity instanceof TileEntityBrewingStand)
/*     */       {
/* 126 */         ((TileEntityBrewingStand)tileentity).setName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 133 */     double d0 = (pos.getX() + 0.4F + rand.nextFloat() * 0.2F);
/* 134 */     double d1 = (pos.getY() + 0.7F + rand.nextFloat() * 0.3F);
/* 135 */     double d2 = (pos.getZ() + 0.4F + rand.nextFloat() * 0.2F);
/* 136 */     worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 144 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 146 */     if (tileentity instanceof TileEntityBrewingStand)
/*     */     {
/* 148 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/*     */     }
/*     */     
/* 151 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 159 */     return Items.BREWING_STAND;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 164 */     return new ItemStack(Items.BREWING_STAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 174 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 179 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 187 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 189 */     for (int i = 0; i < 3; i++)
/*     */     {
/* 191 */       iblockstate = iblockstate.withProperty((IProperty)HAS_BOTTLE[i], Boolean.valueOf(((meta & 1 << i) > 0)));
/*     */     }
/*     */     
/* 194 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 202 */     int i = 0;
/*     */     
/* 204 */     for (int j = 0; j < 3; j++) {
/*     */       
/* 206 */       if (((Boolean)state.getValue((IProperty)HAS_BOTTLE[j])).booleanValue())
/*     */       {
/* 208 */         i |= 1 << j;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 217 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)HAS_BOTTLE[0], (IProperty)HAS_BOTTLE[1], (IProperty)HAS_BOTTLE[2] });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 222 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */