/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDoublePlant
/*     */   extends BlockBush implements IGrowable {
/*  29 */   public static final PropertyEnum<EnumPlantType> VARIANT = PropertyEnum.create("variant", EnumPlantType.class);
/*  30 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*  31 */   public static final PropertyEnum<EnumFacing> FACING = (PropertyEnum<EnumFacing>)BlockHorizontal.FACING;
/*     */ 
/*     */   
/*     */   public BlockDoublePlant() {
/*  35 */     super(Material.VINE);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumPlantType.SUNFLOWER).withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  37 */     setHardness(0.0F);
/*  38 */     setSoundType(SoundType.PLANT);
/*  39 */     setUnlocalizedName("doublePlant");
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  44 */     return FULL_BLOCK_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumPlantType getType(IBlockAccess blockAccess, BlockPos pos, IBlockState state) {
/*  49 */     if (state.getBlock() == this) {
/*     */       
/*  51 */       state = state.getActualState(blockAccess, pos);
/*  52 */       return (EnumPlantType)state.getValue((IProperty)VARIANT);
/*     */     } 
/*     */ 
/*     */     
/*  56 */     return EnumPlantType.FERN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  62 */     return (super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/*  70 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  72 */     if (iblockstate.getBlock() != this)
/*     */     {
/*  74 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  78 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)iblockstate.getActualState(worldIn, pos).getValue((IProperty)VARIANT);
/*  79 */     return !(blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  85 */     if (!canBlockStay(worldIn, pos, state)) {
/*     */       
/*  87 */       boolean flag = (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER);
/*  88 */       BlockPos blockpos = flag ? pos : pos.up();
/*  89 */       BlockPos blockpos1 = flag ? pos.down() : pos;
/*  90 */       Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
/*  91 */       Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;
/*     */       
/*  93 */       if (block == this)
/*     */       {
/*  95 */         worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
/*     */       }
/*     */       
/*  98 */       if (block1 == this) {
/*     */         
/* 100 */         worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
/*     */         
/* 102 */         if (!flag)
/*     */         {
/* 104 */           dropBlockAsItem(worldIn, blockpos1, state, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 112 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER)
/*     */     {
/* 114 */       return (worldIn.getBlockState(pos.down()).getBlock() == this);
/*     */     }
/*     */ 
/*     */     
/* 118 */     IBlockState iblockstate = worldIn.getBlockState(pos.up());
/* 119 */     return (iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 128 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER)
/*     */     {
/* 130 */       return Items.field_190931_a;
/*     */     }
/*     */ 
/*     */     
/* 134 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/*     */     
/* 136 */     if (blockdoubleplant$enumplanttype == EnumPlantType.FERN)
/*     */     {
/* 138 */       return Items.field_190931_a;
/*     */     }
/* 140 */     if (blockdoubleplant$enumplanttype == EnumPlantType.GRASS)
/*     */     {
/* 142 */       return (rand.nextInt(8) == 0) ? Items.WHEAT_SEEDS : Items.field_190931_a;
/*     */     }
/*     */ 
/*     */     
/* 146 */     return super.getItemDropped(state, rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 157 */     return (state.getValue((IProperty)HALF) != EnumBlockHalf.UPPER && state.getValue((IProperty)VARIANT) != EnumPlantType.GRASS) ? ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void placeAt(World worldIn, BlockPos lowerPos, EnumPlantType variant, int flags) {
/* 162 */     worldIn.setBlockState(lowerPos, getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, variant), flags);
/* 163 */     worldIn.setBlockState(lowerPos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 171 */     worldIn.setBlockState(pos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 176 */     if (worldIn.isRemote || stack.getItem() != Items.SHEARS || state.getValue((IProperty)HALF) != EnumBlockHalf.LOWER || !onHarvest(worldIn, pos, state, player))
/*     */     {
/* 178 */       super.harvestBlock(worldIn, player, pos, state, te, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 184 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*     */       
/* 186 */       if (worldIn.getBlockState(pos.down()).getBlock() == this)
/*     */       {
/* 188 */         if (player.capabilities.isCreativeMode) {
/*     */           
/* 190 */           worldIn.setBlockToAir(pos.down());
/*     */         }
/*     */         else {
/*     */           
/* 194 */           IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 195 */           EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */           
/* 197 */           if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS)
/*     */           {
/* 199 */             worldIn.destroyBlock(pos.down(), true);
/*     */           }
/* 201 */           else if (worldIn.isRemote)
/*     */           {
/* 203 */             worldIn.setBlockToAir(pos.down());
/*     */           }
/* 205 */           else if (!player.getHeldItemMainhand().func_190926_b() && player.getHeldItemMainhand().getItem() == Items.SHEARS)
/*     */           {
/* 207 */             onHarvest(worldIn, pos, iblockstate, player);
/* 208 */             worldIn.setBlockToAir(pos.down());
/*     */           }
/*     */           else
/*     */           {
/* 212 */             worldIn.destroyBlock(pos.down(), true);
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 217 */     } else if (worldIn.getBlockState(pos.up()).getBlock() == this) {
/*     */       
/* 219 */       worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
/*     */     } 
/*     */     
/* 222 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onHarvest(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 227 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/*     */     
/* 229 */     if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS)
/*     */     {
/* 231 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 235 */     player.addStat(StatList.getBlockStats(this));
/* 236 */     int i = ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).getMeta();
/* 237 */     spawnAsEntity(worldIn, pos, new ItemStack(Blocks.TALLGRASS, 2, i));
/* 238 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumPlantType[] arrayOfEnumPlantType;
/* 247 */     for (i = (arrayOfEnumPlantType = EnumPlantType.values()).length, b = 0; b < i; ) { EnumPlantType blockdoubleplant$enumplanttype = arrayOfEnumPlantType[b];
/*     */       
/* 249 */       tab.add(new ItemStack(this, 1, blockdoubleplant$enumplanttype.getMeta()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 255 */     return new ItemStack(this, 1, getType((IBlockAccess)worldIn, pos, state).getMeta());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 263 */     EnumPlantType blockdoubleplant$enumplanttype = getType((IBlockAccess)worldIn, pos, state);
/* 264 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 274 */     spawnAsEntity(worldIn, pos, new ItemStack(this, 1, getType((IBlockAccess)worldIn, pos, state).getMeta()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 282 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER) : getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, EnumPlantType.byMetadata(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 291 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*     */       
/* 293 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */       
/* 295 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 297 */         state = state.withProperty((IProperty)VARIANT, iblockstate.getValue((IProperty)VARIANT));
/*     */       }
/*     */     } 
/*     */     
/* 301 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 309 */     return (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) ? (0x8 | ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex()) : ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 314 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT, (IProperty)FACING });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block.EnumOffsetType getOffsetType() {
/* 322 */     return Block.EnumOffsetType.XZ;
/*     */   }
/*     */   
/*     */   public enum EnumBlockHalf
/*     */     implements IStringSerializable {
/* 327 */     UPPER,
/* 328 */     LOWER;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 332 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 337 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumPlantType
/*     */     implements IStringSerializable {
/* 343 */     SUNFLOWER(0, "sunflower"),
/* 344 */     SYRINGA(1, "syringa"),
/* 345 */     GRASS(2, "double_grass", "grass"),
/* 346 */     FERN(3, "double_fern", "fern"),
/* 347 */     ROSE(4, "double_rose", "rose"),
/* 348 */     PAEONIA(5, "paeonia");
/*     */     
/* 350 */     private static final EnumPlantType[] META_LOOKUP = new EnumPlantType[(values()).length];
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
/*     */     private final int meta;
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
/*     */     private final String name;
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
/*     */     private final String unlocalizedName;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumPlantType[] arrayOfEnumPlantType;
/* 398 */       for (i = (arrayOfEnumPlantType = values()).length, b = 0; b < i; ) { EnumPlantType blockdoubleplant$enumplanttype = arrayOfEnumPlantType[b];
/*     */         
/* 400 */         META_LOOKUP[blockdoubleplant$enumplanttype.getMeta()] = blockdoubleplant$enumplanttype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumPlantType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMeta() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumPlantType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDoublePlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */