/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class BlockFlowerPot
/*     */   extends BlockContainer
/*     */ {
/*  36 */   public static final PropertyInteger LEGACY_DATA = PropertyInteger.create("legacy_data", 0, 15);
/*  37 */   public static final PropertyEnum<EnumFlowerType> CONTENTS = PropertyEnum.create("contents", EnumFlowerType.class);
/*  38 */   protected static final AxisAlignedBB FLOWER_POT_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);
/*     */ 
/*     */   
/*     */   public BlockFlowerPot() {
/*  42 */     super(Material.CIRCUITS);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)CONTENTS, EnumFlowerType.EMPTY).withProperty((IProperty)LEGACY_DATA, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  51 */     return I18n.translateToLocal("item.flowerPot.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  56 */     return FLOWER_POT_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  73 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  83 */     ItemStack itemstack = playerIn.getHeldItem(hand);
/*  84 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */     
/*  86 */     if (tileentityflowerpot == null)
/*     */     {
/*  88 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  92 */     ItemStack itemstack1 = tileentityflowerpot.getFlowerItemStack();
/*     */     
/*  94 */     if (itemstack1.func_190926_b()) {
/*     */       
/*  96 */       if (!func_190951_a(itemstack))
/*     */       {
/*  98 */         return false;
/*     */       }
/*     */       
/* 101 */       tileentityflowerpot.func_190614_a(itemstack);
/* 102 */       playerIn.addStat(StatList.FLOWER_POTTED);
/*     */       
/* 104 */       if (!playerIn.capabilities.isCreativeMode)
/*     */       {
/* 106 */         itemstack.func_190918_g(1);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 111 */       if (itemstack.func_190926_b()) {
/*     */         
/* 113 */         playerIn.setHeldItem(hand, itemstack1);
/*     */       }
/* 115 */       else if (!playerIn.func_191521_c(itemstack1)) {
/*     */         
/* 117 */         playerIn.dropItem(itemstack1, false);
/*     */       } 
/*     */       
/* 120 */       tileentityflowerpot.func_190614_a(ItemStack.field_190927_a);
/*     */     } 
/*     */     
/* 123 */     tileentityflowerpot.markDirty();
/* 124 */     worldIn.notifyBlockUpdate(pos, state, state, 3);
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_190951_a(ItemStack p_190951_1_) {
/* 131 */     Block block = Block.getBlockFromItem(p_190951_1_.getItem());
/*     */     
/* 133 */     if (block != Blocks.YELLOW_FLOWER && block != Blocks.RED_FLOWER && block != Blocks.CACTUS && block != Blocks.BROWN_MUSHROOM && block != Blocks.RED_MUSHROOM && block != Blocks.SAPLING && block != Blocks.DEADBUSH) {
/*     */       
/* 135 */       int i = p_190951_1_.getMetadata();
/* 136 */       return (block == Blocks.TALLGRASS && i == BlockTallGrass.EnumType.FERN.getMeta());
/*     */     } 
/*     */ 
/*     */     
/* 140 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 146 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */     
/* 148 */     if (tileentityflowerpot != null) {
/*     */       
/* 150 */       ItemStack itemstack = tileentityflowerpot.getFlowerItemStack();
/*     */       
/* 152 */       if (!itemstack.func_190926_b())
/*     */       {
/* 154 */         return itemstack;
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return new ItemStack(Items.FLOWER_POT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 163 */     return (super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isFullyOpaque());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 173 */     if (!worldIn.getBlockState(pos.down()).isFullyOpaque()) {
/*     */       
/* 175 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 176 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 185 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */     
/* 187 */     if (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null)
/*     */     {
/* 189 */       spawnAsEntity(worldIn, pos, new ItemStack(tileentityflowerpot.getFlowerPotItem(), 1, tileentityflowerpot.getFlowerPotData()));
/*     */     }
/*     */     
/* 192 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 197 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */     
/* 199 */     if (player.capabilities.isCreativeMode) {
/*     */       
/* 201 */       TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */       
/* 203 */       if (tileentityflowerpot != null)
/*     */       {
/* 205 */         tileentityflowerpot.func_190614_a(ItemStack.field_190927_a);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 215 */     return Items.FLOWER_POT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private TileEntityFlowerPot getTileEntity(World worldIn, BlockPos pos) {
/* 221 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 222 */     return (tileentity instanceof TileEntityFlowerPot) ? (TileEntityFlowerPot)tileentity : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 230 */     Block block = null;
/* 231 */     int i = 0;
/*     */     
/* 233 */     switch (meta) {
/*     */       
/*     */       case 1:
/* 236 */         block = Blocks.RED_FLOWER;
/* 237 */         i = BlockFlower.EnumFlowerType.POPPY.getMeta();
/*     */         break;
/*     */       
/*     */       case 2:
/* 241 */         block = Blocks.YELLOW_FLOWER;
/*     */         break;
/*     */       
/*     */       case 3:
/* 245 */         block = Blocks.SAPLING;
/* 246 */         i = BlockPlanks.EnumType.OAK.getMetadata();
/*     */         break;
/*     */       
/*     */       case 4:
/* 250 */         block = Blocks.SAPLING;
/* 251 */         i = BlockPlanks.EnumType.SPRUCE.getMetadata();
/*     */         break;
/*     */       
/*     */       case 5:
/* 255 */         block = Blocks.SAPLING;
/* 256 */         i = BlockPlanks.EnumType.BIRCH.getMetadata();
/*     */         break;
/*     */       
/*     */       case 6:
/* 260 */         block = Blocks.SAPLING;
/* 261 */         i = BlockPlanks.EnumType.JUNGLE.getMetadata();
/*     */         break;
/*     */       
/*     */       case 7:
/* 265 */         block = Blocks.RED_MUSHROOM;
/*     */         break;
/*     */       
/*     */       case 8:
/* 269 */         block = Blocks.BROWN_MUSHROOM;
/*     */         break;
/*     */       
/*     */       case 9:
/* 273 */         block = Blocks.CACTUS;
/*     */         break;
/*     */       
/*     */       case 10:
/* 277 */         block = Blocks.DEADBUSH;
/*     */         break;
/*     */       
/*     */       case 11:
/* 281 */         block = Blocks.TALLGRASS;
/* 282 */         i = BlockTallGrass.EnumType.FERN.getMeta();
/*     */         break;
/*     */       
/*     */       case 12:
/* 286 */         block = Blocks.SAPLING;
/* 287 */         i = BlockPlanks.EnumType.ACACIA.getMetadata();
/*     */         break;
/*     */       
/*     */       case 13:
/* 291 */         block = Blocks.SAPLING;
/* 292 */         i = BlockPlanks.EnumType.DARK_OAK.getMetadata();
/*     */         break;
/*     */     } 
/* 295 */     return (TileEntity)new TileEntityFlowerPot(Item.getItemFromBlock(block), i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 300 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)CONTENTS, (IProperty)LEGACY_DATA });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 308 */     return ((Integer)state.getValue((IProperty)LEGACY_DATA)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 317 */     EnumFlowerType blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
/* 318 */     TileEntity tileentity = (worldIn instanceof ChunkCache) ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
/*     */     
/* 320 */     if (tileentity instanceof TileEntityFlowerPot)
/*     */     
/* 322 */     { TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
/* 323 */       Item item = tileentityflowerpot.getFlowerPotItem();
/*     */       
/* 325 */       if (item instanceof net.minecraft.item.ItemBlock)
/*     */       
/* 327 */       { int i = tileentityflowerpot.getFlowerPotData();
/* 328 */         Block block = Block.getBlockFromItem(item);
/*     */         
/* 330 */         if (block == Blocks.SAPLING)
/*     */         
/* 332 */         { switch (BlockPlanks.EnumType.byMetadata(i))
/*     */           
/*     */           { case OAK:
/* 335 */               blockflowerpot$enumflowertype = EnumFlowerType.OAK_SAPLING;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 445 */               return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case SPRUCE: blockflowerpot$enumflowertype = EnumFlowerType.SPRUCE_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case BIRCH: blockflowerpot$enumflowertype = EnumFlowerType.BIRCH_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case JUNGLE: blockflowerpot$enumflowertype = EnumFlowerType.JUNGLE_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case null: blockflowerpot$enumflowertype = EnumFlowerType.ACACIA_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case DARK_OAK: blockflowerpot$enumflowertype = EnumFlowerType.DARK_OAK_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.TALLGRASS) { switch (i) { case 0: blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case 2: blockflowerpot$enumflowertype = EnumFlowerType.FERN; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.YELLOW_FLOWER) { blockflowerpot$enumflowertype = EnumFlowerType.DANDELION; } else if (block == Blocks.RED_FLOWER) { switch (BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, i)) { case POPPY: blockflowerpot$enumflowertype = EnumFlowerType.POPPY; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case BLUE_ORCHID: blockflowerpot$enumflowertype = EnumFlowerType.BLUE_ORCHID; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case null: blockflowerpot$enumflowertype = EnumFlowerType.ALLIUM; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case HOUSTONIA: blockflowerpot$enumflowertype = EnumFlowerType.HOUSTONIA; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case RED_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.RED_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case ORANGE_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.ORANGE_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case WHITE_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.WHITE_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case PINK_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.PINK_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case OXEYE_DAISY: blockflowerpot$enumflowertype = EnumFlowerType.OXEYE_DAISY; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.RED_MUSHROOM) { blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_RED; } else if (block == Blocks.BROWN_MUSHROOM) { blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_BROWN; } else if (block == Blocks.DEADBUSH) { blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH; } else if (block == Blocks.CACTUS) { blockflowerpot$enumflowertype = EnumFlowerType.CACTUS; }  }  }  return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 450 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 455 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum EnumFlowerType
/*     */     implements IStringSerializable {
/* 460 */     EMPTY("empty"),
/* 461 */     POPPY("rose"),
/* 462 */     BLUE_ORCHID("blue_orchid"),
/* 463 */     ALLIUM("allium"),
/* 464 */     HOUSTONIA("houstonia"),
/* 465 */     RED_TULIP("red_tulip"),
/* 466 */     ORANGE_TULIP("orange_tulip"),
/* 467 */     WHITE_TULIP("white_tulip"),
/* 468 */     PINK_TULIP("pink_tulip"),
/* 469 */     OXEYE_DAISY("oxeye_daisy"),
/* 470 */     DANDELION("dandelion"),
/* 471 */     OAK_SAPLING("oak_sapling"),
/* 472 */     SPRUCE_SAPLING("spruce_sapling"),
/* 473 */     BIRCH_SAPLING("birch_sapling"),
/* 474 */     JUNGLE_SAPLING("jungle_sapling"),
/* 475 */     ACACIA_SAPLING("acacia_sapling"),
/* 476 */     DARK_OAK_SAPLING("dark_oak_sapling"),
/* 477 */     MUSHROOM_RED("mushroom_red"),
/* 478 */     MUSHROOM_BROWN("mushroom_brown"),
/* 479 */     DEAD_BUSH("dead_bush"),
/* 480 */     FERN("fern"),
/* 481 */     CACTUS("cactus");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumFlowerType(String name) {
/* 487 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 492 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 497 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFlowerPot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */