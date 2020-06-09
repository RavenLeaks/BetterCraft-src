/*      */ package net.minecraft.block;
/*      */ 
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.material.EnumPushReaction;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockFaceShape;
/*      */ import net.minecraft.block.state.BlockStateContainer;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.util.ITooltipFlag;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Enchantments;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EnumBlockRenderType;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.Mirror;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ObjectIntIdentityMap;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Rotation;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
/*      */ import net.minecraft.util.text.translation.I18n;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ public class Block
/*      */ {
/*   56 */   private static final ResourceLocation AIR_ID = new ResourceLocation("air");
/*   57 */   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> REGISTRY = new RegistryNamespacedDefaultedByKey(AIR_ID);
/*   58 */   public static final ObjectIntIdentityMap<IBlockState> BLOCK_STATE_IDS = new ObjectIntIdentityMap();
/*   59 */   public static final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*      */   @Nullable
/*   61 */   public static final AxisAlignedBB NULL_AABB = null;
/*      */ 
/*      */   
/*      */   private CreativeTabs displayOnCreativeTab;
/*      */ 
/*      */   
/*      */   protected boolean fullBlock;
/*      */ 
/*      */   
/*      */   protected int lightOpacity;
/*      */ 
/*      */   
/*      */   protected boolean translucent;
/*      */ 
/*      */   
/*      */   protected int lightValue;
/*      */ 
/*      */   
/*      */   protected boolean useNeighborBrightness;
/*      */ 
/*      */   
/*      */   protected float blockHardness;
/*      */ 
/*      */   
/*      */   protected float blockResistance;
/*      */   
/*      */   protected boolean enableStats;
/*      */   
/*      */   protected boolean needsRandomTick;
/*      */   
/*      */   protected boolean isBlockContainer;
/*      */   
/*      */   protected SoundType blockSoundType;
/*      */   
/*      */   public float blockParticleGravity;
/*      */   
/*      */   protected final Material blockMaterial;
/*      */   
/*      */   protected final MapColor blockMapColor;
/*      */   
/*      */   public float slipperiness;
/*      */   
/*      */   protected final BlockStateContainer blockState;
/*      */   
/*      */   private IBlockState defaultBlockState;
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */   
/*      */   public static int getIdFromBlock(Block blockIn) {
/*  111 */     return REGISTRY.getIDForObject(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStateId(IBlockState state) {
/*  119 */     Block block = state.getBlock();
/*  120 */     return getIdFromBlock(block) + (block.getMetaFromState(state) << 12);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockById(int id) {
/*  125 */     return (Block)REGISTRY.getObjectById(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IBlockState getStateById(int id) {
/*  133 */     int i = id & 0xFFF;
/*  134 */     int j = id >> 12 & 0xF;
/*  135 */     return getBlockById(i).getStateFromMeta(j);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockFromItem(@Nullable Item itemIn) {
/*  140 */     return (itemIn instanceof ItemBlock) ? ((ItemBlock)itemIn).getBlock() : Blocks.AIR;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAir() {
/*  145 */     return (this == Blocks.AIR);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static Block getBlockFromName(String name) {
/*  152 */     ResourceLocation resourcelocation = new ResourceLocation(name);
/*      */     
/*  154 */     if (REGISTRY.containsKey(resourcelocation))
/*      */     {
/*  156 */       return (Block)REGISTRY.getObject(resourcelocation);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  162 */       return (Block)REGISTRY.getObjectById(Integer.parseInt(name));
/*      */     }
/*  164 */     catch (NumberFormatException var3) {
/*      */       
/*  166 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isFullyOpaque(IBlockState state) {
/*  178 */     return (state.getMaterial().isOpaque() && state.isFullCube());
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isFullBlock(IBlockState state) {
/*  184 */     return this.fullBlock;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
/*  190 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getLightOpacity(IBlockState state) {
/*  196 */     return this.lightOpacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isTranslucent(IBlockState state) {
/*  206 */     return this.translucent;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getLightValue(IBlockState state) {
/*  212 */     return this.lightValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean getUseNeighborBrightness(IBlockState state) {
/*  222 */     return this.useNeighborBrightness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Material getMaterial(IBlockState state) {
/*  232 */     return this.blockMaterial;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/*  242 */     return this.blockMapColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public IBlockState getStateFromMeta(int meta) {
/*  252 */     return getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetaFromState(IBlockState state) {
/*  260 */     if (state.getPropertyNames().isEmpty())
/*      */     {
/*  262 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  266 */     throw new IllegalArgumentException("Don't know how to convert " + state + " back into data...");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  278 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/*  289 */     return state;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/*  300 */     return state;
/*      */   }
/*      */ 
/*      */   
/*      */   public Block(Material blockMaterialIn, MapColor blockMapColorIn) {
/*  305 */     this.enableStats = true;
/*  306 */     this.blockSoundType = SoundType.STONE;
/*  307 */     this.blockParticleGravity = 1.0F;
/*  308 */     this.slipperiness = 0.6F;
/*  309 */     this.blockMaterial = blockMaterialIn;
/*  310 */     this.blockMapColor = blockMapColorIn;
/*  311 */     this.blockState = createBlockState();
/*  312 */     setDefaultState(this.blockState.getBaseState());
/*  313 */     this.fullBlock = getDefaultState().isOpaqueCube();
/*  314 */     this.lightOpacity = this.fullBlock ? 255 : 0;
/*  315 */     this.translucent = !blockMaterialIn.blocksLight();
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block(Material materialIn) {
/*  320 */     this(materialIn, materialIn.getMaterialMapColor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setSoundType(SoundType sound) {
/*  328 */     this.blockSoundType = sound;
/*  329 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightOpacity(int opacity) {
/*  337 */     this.lightOpacity = opacity;
/*  338 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightLevel(float value) {
/*  346 */     this.lightValue = (int)(15.0F * value);
/*  347 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setResistance(float resistance) {
/*  355 */     this.blockResistance = resistance * 3.0F;
/*  356 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static boolean func_193384_b(Block p_193384_0_) {
/*  361 */     return !(!(p_193384_0_ instanceof BlockShulkerBox) && !(p_193384_0_ instanceof BlockLeaves) && !(p_193384_0_ instanceof BlockTrapDoor) && p_193384_0_ != Blocks.BEACON && p_193384_0_ != Blocks.CAULDRON && p_193384_0_ != Blocks.GLASS && p_193384_0_ != Blocks.GLOWSTONE && p_193384_0_ != Blocks.ICE && p_193384_0_ != Blocks.SEA_LANTERN && p_193384_0_ != Blocks.STAINED_GLASS);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static boolean func_193382_c(Block p_193382_0_) {
/*  366 */     return !(!func_193384_b(p_193382_0_) && p_193382_0_ != Blocks.PISTON && p_193382_0_ != Blocks.STICKY_PISTON && p_193382_0_ != Blocks.PISTON_HEAD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isBlockNormalCube(IBlockState state) {
/*  376 */     return (state.getMaterial().blocksMovement() && state.isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isNormalCube(IBlockState state) {
/*  387 */     return (state.getMaterial().isOpaque() && state.isFullCube() && !state.canProvidePower());
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean causesSuffocation(IBlockState p_176214_1_) {
/*  393 */     return (this.blockMaterial.blocksMovement() && getDefaultState().isFullCube());
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isFullCube(IBlockState state) {
/*  399 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean func_190946_v(IBlockState p_190946_1_) {
/*  405 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  410 */     return !this.blockMaterial.blocksMovement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  421 */     return EnumBlockRenderType.MODEL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/*  429 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setHardness(float hardness) {
/*  437 */     this.blockHardness = hardness;
/*      */     
/*  439 */     if (this.blockResistance < hardness * 5.0F)
/*      */     {
/*  441 */       this.blockResistance = hardness * 5.0F;
/*      */     }
/*      */     
/*  444 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block setBlockUnbreakable() {
/*  449 */     setHardness(-1.0F);
/*  450 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
/*  456 */     return this.blockHardness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setTickRandomly(boolean shouldTick) {
/*  464 */     this.needsRandomTick = shouldTick;
/*  465 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTickRandomly() {
/*  474 */     return this.needsRandomTick;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasTileEntity() {
/*  479 */     return this.isBlockContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  485 */     return FULL_BLOCK_AABB;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  491 */     int i = source.getCombinedLight(pos, state.getLightValue());
/*      */     
/*  493 */     if (i == 0 && state.getBlock() instanceof BlockSlab) {
/*      */       
/*  495 */       pos = pos.down();
/*  496 */       state = source.getBlockState(pos);
/*  497 */       return source.getCombinedLight(pos, state.getLightValue());
/*      */     } 
/*      */ 
/*      */     
/*  501 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  508 */     AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);
/*      */     
/*  510 */     switch (side) {
/*      */       
/*      */       case null:
/*  513 */         if (axisalignedbb.minY > 0.0D)
/*      */         {
/*  515 */           return true;
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case UP:
/*  521 */         if (axisalignedbb.maxY < 1.0D)
/*      */         {
/*  523 */           return true;
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case NORTH:
/*  529 */         if (axisalignedbb.minZ > 0.0D)
/*      */         {
/*  531 */           return true;
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case SOUTH:
/*  537 */         if (axisalignedbb.maxZ < 1.0D)
/*      */         {
/*  539 */           return true;
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case WEST:
/*  545 */         if (axisalignedbb.minX > 0.0D)
/*      */         {
/*  547 */           return true;
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case EAST:
/*  553 */         if (axisalignedbb.maxX < 1.0D)
/*      */         {
/*  555 */           return true;
/*      */         }
/*      */         break;
/*      */     } 
/*  559 */     return !blockAccess.getBlockState(pos.offset(side)).isOpaqueCube();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/*  565 */     return BlockFaceShape.SOLID;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
/*  571 */     return state.getBoundingBox((IBlockAccess)worldIn, pos).offset(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  577 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox((IBlockAccess)worldIn, pos));
/*      */   }
/*      */ 
/*      */   
/*      */   protected static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB blockBox) {
/*  582 */     if (blockBox != NULL_AABB) {
/*      */       
/*  584 */       AxisAlignedBB axisalignedbb = blockBox.offset(pos);
/*      */       
/*  586 */       if (entityBox.intersectsWith(axisalignedbb))
/*      */       {
/*  588 */         collidingBoxes.add(axisalignedbb);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Nullable
/*      */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  597 */     return blockState.getBoundingBox(worldIn, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean isOpaqueCube(IBlockState state) {
/*  607 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  612 */     return isCollidable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCollidable() {
/*  621 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
/*  629 */     updateTick(worldIn, pos, state, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int tickRate(World worldIn) {
/*  663 */     return 10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDropped(Random random) {
/*  685 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  693 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
/*  703 */     float f = state.getBlockHardness(worldIn, pos);
/*      */     
/*  705 */     if (f < 0.0F)
/*      */     {
/*  707 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/*  711 */     return !player.canHarvestBlock(state) ? (player.getDigSpeed(state) / f / 100.0F) : (player.getDigSpeed(state) / f / 30.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int fortune) {
/*  720 */     dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, fortune);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  728 */     if (!worldIn.isRemote) {
/*      */       
/*  730 */       int i = quantityDroppedWithBonus(fortune, worldIn.rand);
/*      */       
/*  732 */       for (int j = 0; j < i; j++) {
/*      */         
/*  734 */         if (worldIn.rand.nextFloat() <= chance) {
/*      */           
/*  736 */           Item item = getItemDropped(state, worldIn.rand, fortune);
/*      */           
/*  738 */           if (item != Items.field_190931_a)
/*      */           {
/*  740 */             spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
/*  752 */     if (!worldIn.isRemote && !stack.func_190926_b() && worldIn.getGameRules().getBoolean("doTileDrops")) {
/*      */       
/*  754 */       float f = 0.5F;
/*  755 */       double d0 = (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
/*  756 */       double d1 = (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
/*  757 */       double d2 = (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
/*  758 */       EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
/*  759 */       entityitem.setDefaultPickupDelay();
/*  760 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
/*  769 */     if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops"))
/*      */     {
/*  771 */       while (amount > 0) {
/*      */         
/*  773 */         int i = EntityXPOrb.getXPSplit(amount);
/*  774 */         amount -= i;
/*  775 */         worldIn.spawnEntityInWorld((Entity)new EntityXPOrb(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, i));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int damageDropped(IBlockState state) {
/*  786 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Entity exploder) {
/*  794 */     return this.blockResistance / 5.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Nullable
/*      */   public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
/*  805 */     return rayTrace(pos, start, end, blockState.getBoundingBox((IBlockAccess)worldIn, pos));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox) {
/*  811 */     Vec3d vec3d = start.subtract(pos.getX(), pos.getY(), pos.getZ());
/*  812 */     Vec3d vec3d1 = end.subtract(pos.getX(), pos.getY(), pos.getZ());
/*  813 */     RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
/*  814 */     return (raytraceresult == null) ? null : new RayTraceResult(raytraceresult.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), raytraceresult.sideHit, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockRenderLayer getBlockLayer() {
/*  826 */     return BlockRenderLayer.SOLID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  834 */     return canPlaceBlockAt(worldIn, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  839 */     return (worldIn.getBlockState(pos).getBlock()).blockMaterial.isReplaceable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  844 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  860 */     return getStateFromMeta(meta);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {}
/*      */ 
/*      */   
/*      */   public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
/*  869 */     return motion;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  875 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean canProvidePower(IBlockState state) {
/*  885 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  898 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/*  903 */     player.addStat(StatList.getBlockStats(this));
/*  904 */     player.addExhaustion(0.005F);
/*      */     
/*  906 */     if (canSilkHarvest() && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
/*      */       
/*  908 */       ItemStack itemstack = getSilkTouchDrop(state);
/*  909 */       spawnAsEntity(worldIn, pos, itemstack);
/*      */     }
/*      */     else {
/*      */       
/*  913 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
/*  914 */       dropBlockAsItem(worldIn, pos, state, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canSilkHarvest() {
/*  920 */     return (getDefaultState().isFullCube() && !this.isBlockContainer);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/*  925 */     Item item = Item.getItemFromBlock(this);
/*  926 */     int i = 0;
/*      */     
/*  928 */     if (item.getHasSubtypes())
/*      */     {
/*  930 */       i = getMetaFromState(state);
/*      */     }
/*      */     
/*  933 */     return new ItemStack(item, 1, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  941 */     return quantityDropped(random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSpawnInBlock() {
/*  956 */     return (!this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid());
/*      */   }
/*      */ 
/*      */   
/*      */   public Block setUnlocalizedName(String name) {
/*  961 */     this.unlocalizedName = name;
/*  962 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalizedName() {
/*  970 */     return I18n.translateToLocal(String.valueOf(getUnlocalizedName()) + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  978 */     return "tile." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
/*  991 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnableStats() {
/*  999 */     return this.enableStats;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block disableStats() {
/* 1004 */     this.enableStats = false;
/* 1005 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 1011 */     return this.blockMaterial.getMobilityFlag();
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public float getAmbientOcclusionLightValue(IBlockState state) {
/* 1017 */     return state.isBlockNormalCube() ? 0.2F : 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/* 1025 */     entityIn.fall(fallDistance, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLanded(World worldIn, Entity entityIn) {
/* 1034 */     entityIn.motionY = 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 1039 */     return new ItemStack(Item.getItemFromBlock(this), 1, damageDropped(state));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 1047 */     tab.add(new ItemStack(this));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTabToDisplayOn() {
/* 1055 */     return this.displayOnCreativeTab;
/*      */   }
/*      */ 
/*      */   
/*      */   public Block setCreativeTab(CreativeTabs tab) {
/* 1060 */     this.displayOnCreativeTab = tab;
/* 1061 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillWithRain(World worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requiresUpdates() {
/* 1077 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 1085 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAssociatedBlock(Block other) {
/* 1090 */     return (this == other);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isEqualTo(Block blockIn, Block other) {
/* 1095 */     if (blockIn != null && other != null)
/*      */     {
/* 1097 */       return (blockIn == other) ? true : blockIn.isAssociatedBlock(other);
/*      */     }
/*      */ 
/*      */     
/* 1101 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 1108 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 1114 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BlockStateContainer createBlockState() {
/* 1119 */     return new BlockStateContainer(this, new IProperty[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockStateContainer getBlockState() {
/* 1124 */     return this.blockState;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void setDefaultState(IBlockState state) {
/* 1129 */     this.defaultBlockState = state;
/*      */   }
/*      */ 
/*      */   
/*      */   public final IBlockState getDefaultState() {
/* 1134 */     return this.defaultBlockState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumOffsetType getOffsetType() {
/* 1142 */     return EnumOffsetType.NONE;
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Vec3d func_190949_e(IBlockState p_190949_1_, IBlockAccess p_190949_2_, BlockPos p_190949_3_) {
/* 1148 */     EnumOffsetType block$enumoffsettype = getOffsetType();
/*      */     
/* 1150 */     if (block$enumoffsettype == EnumOffsetType.NONE)
/*      */     {
/* 1152 */       return Vec3d.ZERO;
/*      */     }
/*      */ 
/*      */     
/* 1156 */     long i = MathHelper.getCoordinateRandom(p_190949_3_.getX(), 0, p_190949_3_.getZ());
/* 1157 */     return new Vec3d((((float)(i >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D, (block$enumoffsettype == EnumOffsetType.XYZ) ? ((((float)(i >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D) : 0.0D, (((float)(i >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SoundType getSoundType() {
/* 1163 */     return this.blockSoundType;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1168 */     return "Block{" + REGISTRY.getNameForObject(this) + "}";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_190948_a(ItemStack p_190948_1_, @Nullable World p_190948_2_, List<String> p_190948_3_, ITooltipFlag p_190948_4_) {}
/*      */ 
/*      */   
/*      */   public static void registerBlocks() {
/* 1177 */     registerBlock(0, AIR_ID, (new BlockAir()).setUnlocalizedName("air"));
/* 1178 */     registerBlock(1, "stone", (new BlockStone()).setHardness(1.5F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stone"));
/* 1179 */     registerBlock(2, "grass", (new BlockGrass()).setHardness(0.6F).setSoundType(SoundType.PLANT).setUnlocalizedName("grass"));
/* 1180 */     registerBlock(3, "dirt", (new BlockDirt()).setHardness(0.5F).setSoundType(SoundType.GROUND).setUnlocalizedName("dirt"));
/* 1181 */     Block block = (new Block(Material.ROCK)).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/* 1182 */     registerBlock(4, "cobblestone", block);
/* 1183 */     Block block1 = (new BlockPlanks()).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("wood");
/* 1184 */     registerBlock(5, "planks", block1);
/* 1185 */     registerBlock(6, "sapling", (new BlockSapling()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("sapling"));
/* 1186 */     registerBlock(7, "bedrock", (new BlockEmptyDrops(Material.ROCK)).setBlockUnbreakable().setResistance(6000000.0F).setSoundType(SoundType.STONE).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1187 */     registerBlock(8, "flowing_water", (new BlockDynamicLiquid(Material.WATER)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1188 */     registerBlock(9, "water", (new BlockStaticLiquid(Material.WATER)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1189 */     registerBlock(10, "flowing_lava", (new BlockDynamicLiquid(Material.LAVA)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1190 */     registerBlock(11, "lava", (new BlockStaticLiquid(Material.LAVA)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1191 */     registerBlock(12, "sand", (new BlockSand()).setHardness(0.5F).setSoundType(SoundType.SAND).setUnlocalizedName("sand"));
/* 1192 */     registerBlock(13, "gravel", (new BlockGravel()).setHardness(0.6F).setSoundType(SoundType.GROUND).setUnlocalizedName("gravel"));
/* 1193 */     registerBlock(14, "gold_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreGold"));
/* 1194 */     registerBlock(15, "iron_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreIron"));
/* 1195 */     registerBlock(16, "coal_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreCoal"));
/* 1196 */     registerBlock(17, "log", (new BlockOldLog()).setUnlocalizedName("log"));
/* 1197 */     registerBlock(18, "leaves", (new BlockOldLeaf()).setUnlocalizedName("leaves"));
/* 1198 */     registerBlock(19, "sponge", (new BlockSponge()).setHardness(0.6F).setSoundType(SoundType.PLANT).setUnlocalizedName("sponge"));
/* 1199 */     registerBlock(20, "glass", (new BlockGlass(Material.GLASS, false)).setHardness(0.3F).setSoundType(SoundType.GLASS).setUnlocalizedName("glass"));
/* 1200 */     registerBlock(21, "lapis_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreLapis"));
/* 1201 */     registerBlock(22, "lapis_block", (new Block(Material.IRON, MapColor.LAPIS)).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1202 */     registerBlock(23, "dispenser", (new BlockDispenser()).setHardness(3.5F).setSoundType(SoundType.STONE).setUnlocalizedName("dispenser"));
/* 1203 */     Block block2 = (new BlockSandStone()).setSoundType(SoundType.STONE).setHardness(0.8F).setUnlocalizedName("sandStone");
/* 1204 */     registerBlock(24, "sandstone", block2);
/* 1205 */     registerBlock(25, "noteblock", (new BlockNote()).setSoundType(SoundType.WOOD).setHardness(0.8F).setUnlocalizedName("musicBlock"));
/* 1206 */     registerBlock(26, "bed", (new BlockBed()).setSoundType(SoundType.WOOD).setHardness(0.2F).setUnlocalizedName("bed").disableStats());
/* 1207 */     registerBlock(27, "golden_rail", (new BlockRailPowered()).setHardness(0.7F).setSoundType(SoundType.METAL).setUnlocalizedName("goldenRail"));
/* 1208 */     registerBlock(28, "detector_rail", (new BlockRailDetector()).setHardness(0.7F).setSoundType(SoundType.METAL).setUnlocalizedName("detectorRail"));
/* 1209 */     registerBlock(29, "sticky_piston", (new BlockPistonBase(true)).setUnlocalizedName("pistonStickyBase"));
/* 1210 */     registerBlock(30, "web", (new BlockWeb()).setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
/* 1211 */     registerBlock(31, "tallgrass", (new BlockTallGrass()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("tallgrass"));
/* 1212 */     registerBlock(32, "deadbush", (new BlockDeadBush()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("deadbush"));
/* 1213 */     registerBlock(33, "piston", (new BlockPistonBase(false)).setUnlocalizedName("pistonBase"));
/* 1214 */     registerBlock(34, "piston_head", (new BlockPistonExtension()).setUnlocalizedName("pistonBase"));
/* 1215 */     registerBlock(35, "wool", (new BlockColored(Material.CLOTH)).setHardness(0.8F).setSoundType(SoundType.CLOTH).setUnlocalizedName("cloth"));
/* 1216 */     registerBlock(36, "piston_extension", new BlockPistonMoving());
/* 1217 */     registerBlock(37, "yellow_flower", (new BlockYellowFlower()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("flower1"));
/* 1218 */     registerBlock(38, "red_flower", (new BlockRedFlower()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("flower2"));
/* 1219 */     Block block3 = (new BlockMushroom()).setHardness(0.0F).setSoundType(SoundType.PLANT).setLightLevel(0.125F).setUnlocalizedName("mushroom");
/* 1220 */     registerBlock(39, "brown_mushroom", block3);
/* 1221 */     Block block4 = (new BlockMushroom()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("mushroom");
/* 1222 */     registerBlock(40, "red_mushroom", block4);
/* 1223 */     registerBlock(41, "gold_block", (new Block(Material.IRON, MapColor.GOLD)).setHardness(3.0F).setResistance(10.0F).setSoundType(SoundType.METAL).setUnlocalizedName("blockGold").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1224 */     registerBlock(42, "iron_block", (new Block(Material.IRON, MapColor.IRON)).setHardness(5.0F).setResistance(10.0F).setSoundType(SoundType.METAL).setUnlocalizedName("blockIron").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1225 */     registerBlock(43, "double_stone_slab", (new BlockDoubleStoneSlab()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stoneSlab"));
/* 1226 */     registerBlock(44, "stone_slab", (new BlockHalfStoneSlab()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stoneSlab"));
/* 1227 */     Block block5 = (new Block(Material.ROCK, MapColor.RED)).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/* 1228 */     registerBlock(45, "brick_block", block5);
/* 1229 */     registerBlock(46, "tnt", (new BlockTNT()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("tnt"));
/* 1230 */     registerBlock(47, "bookshelf", (new BlockBookshelf()).setHardness(1.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("bookshelf"));
/* 1231 */     registerBlock(48, "mossy_cobblestone", (new Block(Material.ROCK)).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1232 */     registerBlock(49, "obsidian", (new BlockObsidian()).setHardness(50.0F).setResistance(2000.0F).setSoundType(SoundType.STONE).setUnlocalizedName("obsidian"));
/* 1233 */     registerBlock(50, "torch", (new BlockTorch()).setHardness(0.0F).setLightLevel(0.9375F).setSoundType(SoundType.WOOD).setUnlocalizedName("torch"));
/* 1234 */     registerBlock(51, "fire", (new BlockFire()).setHardness(0.0F).setLightLevel(1.0F).setSoundType(SoundType.CLOTH).setUnlocalizedName("fire").disableStats());
/* 1235 */     registerBlock(52, "mob_spawner", (new BlockMobSpawner()).setHardness(5.0F).setSoundType(SoundType.METAL).setUnlocalizedName("mobSpawner").disableStats());
/* 1236 */     registerBlock(53, "oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK))).setUnlocalizedName("stairsWood"));
/* 1237 */     registerBlock(54, "chest", (new BlockChest(BlockChest.Type.BASIC)).setHardness(2.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("chest"));
/* 1238 */     registerBlock(55, "redstone_wire", (new BlockRedstoneWire()).setHardness(0.0F).setSoundType(SoundType.STONE).setUnlocalizedName("redstoneDust").disableStats());
/* 1239 */     registerBlock(56, "diamond_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreDiamond"));
/* 1240 */     registerBlock(57, "diamond_block", (new Block(Material.IRON, MapColor.DIAMOND)).setHardness(5.0F).setResistance(10.0F).setSoundType(SoundType.METAL).setUnlocalizedName("blockDiamond").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1241 */     registerBlock(58, "crafting_table", (new BlockWorkbench()).setHardness(2.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("workbench"));
/* 1242 */     registerBlock(59, "wheat", (new BlockCrops()).setUnlocalizedName("crops"));
/* 1243 */     Block block6 = (new BlockFarmland()).setHardness(0.6F).setSoundType(SoundType.GROUND).setUnlocalizedName("farmland");
/* 1244 */     registerBlock(60, "farmland", block6);
/* 1245 */     registerBlock(61, "furnace", (new BlockFurnace(false)).setHardness(3.5F).setSoundType(SoundType.STONE).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.DECORATIONS));
/* 1246 */     registerBlock(62, "lit_furnace", (new BlockFurnace(true)).setHardness(3.5F).setSoundType(SoundType.STONE).setLightLevel(0.875F).setUnlocalizedName("furnace"));
/* 1247 */     registerBlock(63, "standing_sign", (new BlockStandingSign()).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("sign").disableStats());
/* 1248 */     registerBlock(64, "wooden_door", (new BlockDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("doorOak").disableStats());
/* 1249 */     registerBlock(65, "ladder", (new BlockLadder()).setHardness(0.4F).setSoundType(SoundType.LADDER).setUnlocalizedName("ladder"));
/* 1250 */     registerBlock(66, "rail", (new BlockRail()).setHardness(0.7F).setSoundType(SoundType.METAL).setUnlocalizedName("rail"));
/* 1251 */     registerBlock(67, "stone_stairs", (new BlockStairs(block.getDefaultState())).setUnlocalizedName("stairsStone"));
/* 1252 */     registerBlock(68, "wall_sign", (new BlockWallSign()).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("sign").disableStats());
/* 1253 */     registerBlock(69, "lever", (new BlockLever()).setHardness(0.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("lever"));
/* 1254 */     registerBlock(70, "stone_pressure_plate", (new BlockPressurePlate(Material.ROCK, BlockPressurePlate.Sensitivity.MOBS)).setHardness(0.5F).setSoundType(SoundType.STONE).setUnlocalizedName("pressurePlateStone"));
/* 1255 */     registerBlock(71, "iron_door", (new BlockDoor(Material.IRON)).setHardness(5.0F).setSoundType(SoundType.METAL).setUnlocalizedName("doorIron").disableStats());
/* 1256 */     registerBlock(72, "wooden_pressure_plate", (new BlockPressurePlate(Material.WOOD, BlockPressurePlate.Sensitivity.EVERYTHING)).setHardness(0.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("pressurePlateWood"));
/* 1257 */     registerBlock(73, "redstone_ore", (new BlockRedstoneOre(false)).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1258 */     registerBlock(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreRedstone"));
/* 1259 */     registerBlock(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).setHardness(0.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("notGate"));
/* 1260 */     registerBlock(76, "redstone_torch", (new BlockRedstoneTorch(true)).setHardness(0.0F).setLightLevel(0.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.REDSTONE));
/* 1261 */     registerBlock(77, "stone_button", (new BlockButtonStone()).setHardness(0.5F).setSoundType(SoundType.STONE).setUnlocalizedName("button"));
/* 1262 */     registerBlock(78, "snow_layer", (new BlockSnow()).setHardness(0.1F).setSoundType(SoundType.SNOW).setUnlocalizedName("snow").setLightOpacity(0));
/* 1263 */     registerBlock(79, "ice", (new BlockIce()).setHardness(0.5F).setLightOpacity(3).setSoundType(SoundType.GLASS).setUnlocalizedName("ice"));
/* 1264 */     registerBlock(80, "snow", (new BlockSnowBlock()).setHardness(0.2F).setSoundType(SoundType.SNOW).setUnlocalizedName("snow"));
/* 1265 */     registerBlock(81, "cactus", (new BlockCactus()).setHardness(0.4F).setSoundType(SoundType.CLOTH).setUnlocalizedName("cactus"));
/* 1266 */     registerBlock(82, "clay", (new BlockClay()).setHardness(0.6F).setSoundType(SoundType.GROUND).setUnlocalizedName("clay"));
/* 1267 */     registerBlock(83, "reeds", (new BlockReed()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("reeds").disableStats());
/* 1268 */     registerBlock(84, "jukebox", (new BlockJukebox()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("jukebox"));
/* 1269 */     registerBlock(85, "fence", (new BlockFence(Material.WOOD, BlockPlanks.EnumType.OAK.getMapColor())).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("fence"));
/* 1270 */     Block block7 = (new BlockPumpkin()).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("pumpkin");
/* 1271 */     registerBlock(86, "pumpkin", block7);
/* 1272 */     registerBlock(87, "netherrack", (new BlockNetherrack()).setHardness(0.4F).setSoundType(SoundType.STONE).setUnlocalizedName("hellrock"));
/* 1273 */     registerBlock(88, "soul_sand", (new BlockSoulSand()).setHardness(0.5F).setSoundType(SoundType.SAND).setUnlocalizedName("hellsand"));
/* 1274 */     registerBlock(89, "glowstone", (new BlockGlowstone(Material.GLASS)).setHardness(0.3F).setSoundType(SoundType.GLASS).setLightLevel(1.0F).setUnlocalizedName("lightgem"));
/* 1275 */     registerBlock(90, "portal", (new BlockPortal()).setHardness(-1.0F).setSoundType(SoundType.GLASS).setLightLevel(0.75F).setUnlocalizedName("portal"));
/* 1276 */     registerBlock(91, "lit_pumpkin", (new BlockPumpkin()).setHardness(1.0F).setSoundType(SoundType.WOOD).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
/* 1277 */     registerBlock(92, "cake", (new BlockCake()).setHardness(0.5F).setSoundType(SoundType.CLOTH).setUnlocalizedName("cake").disableStats());
/* 1278 */     registerBlock(93, "unpowered_repeater", (new BlockRedstoneRepeater(false)).setHardness(0.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("diode").disableStats());
/* 1279 */     registerBlock(94, "powered_repeater", (new BlockRedstoneRepeater(true)).setHardness(0.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("diode").disableStats());
/* 1280 */     registerBlock(95, "stained_glass", (new BlockStainedGlass(Material.GLASS)).setHardness(0.3F).setSoundType(SoundType.GLASS).setUnlocalizedName("stainedGlass"));
/* 1281 */     registerBlock(96, "trapdoor", (new BlockTrapDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("trapdoor").disableStats());
/* 1282 */     registerBlock(97, "monster_egg", (new BlockSilverfish()).setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
/* 1283 */     Block block8 = (new BlockStoneBrick()).setHardness(1.5F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stonebricksmooth");
/* 1284 */     registerBlock(98, "stonebrick", block8);
/* 1285 */     registerBlock(99, "brown_mushroom_block", (new BlockHugeMushroom(Material.WOOD, MapColor.DIRT, block3)).setHardness(0.2F).setSoundType(SoundType.WOOD).setUnlocalizedName("mushroom"));
/* 1286 */     registerBlock(100, "red_mushroom_block", (new BlockHugeMushroom(Material.WOOD, MapColor.RED, block4)).setHardness(0.2F).setSoundType(SoundType.WOOD).setUnlocalizedName("mushroom"));
/* 1287 */     registerBlock(101, "iron_bars", (new BlockPane(Material.IRON, true)).setHardness(5.0F).setResistance(10.0F).setSoundType(SoundType.METAL).setUnlocalizedName("fenceIron"));
/* 1288 */     registerBlock(102, "glass_pane", (new BlockPane(Material.GLASS, false)).setHardness(0.3F).setSoundType(SoundType.GLASS).setUnlocalizedName("thinGlass"));
/* 1289 */     Block block9 = (new BlockMelon()).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("melon");
/* 1290 */     registerBlock(103, "melon_block", block9);
/* 1291 */     registerBlock(104, "pumpkin_stem", (new BlockStem(block7)).setHardness(0.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("pumpkinStem"));
/* 1292 */     registerBlock(105, "melon_stem", (new BlockStem(block9)).setHardness(0.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("pumpkinStem"));
/* 1293 */     registerBlock(106, "vine", (new BlockVine()).setHardness(0.2F).setSoundType(SoundType.PLANT).setUnlocalizedName("vine"));
/* 1294 */     registerBlock(107, "fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.OAK)).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("fenceGate"));
/* 1295 */     registerBlock(108, "brick_stairs", (new BlockStairs(block5.getDefaultState())).setUnlocalizedName("stairsBrick"));
/* 1296 */     registerBlock(109, "stone_brick_stairs", (new BlockStairs(block8.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT))).setUnlocalizedName("stairsStoneBrickSmooth"));
/* 1297 */     registerBlock(110, "mycelium", (new BlockMycelium()).setHardness(0.6F).setSoundType(SoundType.PLANT).setUnlocalizedName("mycel"));
/* 1298 */     registerBlock(111, "waterlily", (new BlockLilyPad()).setHardness(0.0F).setSoundType(SoundType.PLANT).setUnlocalizedName("waterlily"));
/* 1299 */     Block block10 = (new BlockNetherBrick()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/* 1300 */     registerBlock(112, "nether_brick", block10);
/* 1301 */     registerBlock(113, "nether_brick_fence", (new BlockFence(Material.ROCK, MapColor.NETHERRACK)).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("netherFence"));
/* 1302 */     registerBlock(114, "nether_brick_stairs", (new BlockStairs(block10.getDefaultState())).setUnlocalizedName("stairsNetherBrick"));
/* 1303 */     registerBlock(115, "nether_wart", (new BlockNetherWart()).setUnlocalizedName("netherStalk"));
/* 1304 */     registerBlock(116, "enchanting_table", (new BlockEnchantmentTable()).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable"));
/* 1305 */     registerBlock(117, "brewing_stand", (new BlockBrewingStand()).setHardness(0.5F).setLightLevel(0.125F).setUnlocalizedName("brewingStand"));
/* 1306 */     registerBlock(118, "cauldron", (new BlockCauldron()).setHardness(2.0F).setUnlocalizedName("cauldron"));
/* 1307 */     registerBlock(119, "end_portal", (new BlockEndPortal(Material.PORTAL)).setHardness(-1.0F).setResistance(6000000.0F));
/* 1308 */     registerBlock(120, "end_portal_frame", (new BlockEndPortalFrame()).setSoundType(SoundType.GLASS).setLightLevel(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.DECORATIONS));
/* 1309 */     registerBlock(121, "end_stone", (new Block(Material.ROCK, MapColor.SAND)).setHardness(3.0F).setResistance(15.0F).setSoundType(SoundType.STONE).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1310 */     registerBlock(122, "dragon_egg", (new BlockDragonEgg()).setHardness(3.0F).setResistance(15.0F).setSoundType(SoundType.STONE).setLightLevel(0.125F).setUnlocalizedName("dragonEgg"));
/* 1311 */     registerBlock(123, "redstone_lamp", (new BlockRedstoneLight(false)).setHardness(0.3F).setSoundType(SoundType.GLASS).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.REDSTONE));
/* 1312 */     registerBlock(124, "lit_redstone_lamp", (new BlockRedstoneLight(true)).setHardness(0.3F).setSoundType(SoundType.GLASS).setUnlocalizedName("redstoneLight"));
/* 1313 */     registerBlock(125, "double_wooden_slab", (new BlockDoubleWoodSlab()).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("woodSlab"));
/* 1314 */     registerBlock(126, "wooden_slab", (new BlockHalfWoodSlab()).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("woodSlab"));
/* 1315 */     registerBlock(127, "cocoa", (new BlockCocoa()).setHardness(0.2F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("cocoa"));
/* 1316 */     registerBlock(128, "sandstone_stairs", (new BlockStairs(block2.getDefaultState().withProperty((IProperty)BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH))).setUnlocalizedName("stairsSandStone"));
/* 1317 */     registerBlock(129, "emerald_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("oreEmerald"));
/* 1318 */     registerBlock(130, "ender_chest", (new BlockEnderChest()).setHardness(22.5F).setResistance(1000.0F).setSoundType(SoundType.STONE).setUnlocalizedName("enderChest").setLightLevel(0.5F));
/* 1319 */     registerBlock(131, "tripwire_hook", (new BlockTripWireHook()).setUnlocalizedName("tripWireSource"));
/* 1320 */     registerBlock(132, "tripwire", (new BlockTripWire()).setUnlocalizedName("tripWire"));
/* 1321 */     registerBlock(133, "emerald_block", (new Block(Material.IRON, MapColor.EMERALD)).setHardness(5.0F).setResistance(10.0F).setSoundType(SoundType.METAL).setUnlocalizedName("blockEmerald").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1322 */     registerBlock(134, "spruce_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE))).setUnlocalizedName("stairsWoodSpruce"));
/* 1323 */     registerBlock(135, "birch_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH))).setUnlocalizedName("stairsWoodBirch"));
/* 1324 */     registerBlock(136, "jungle_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE))).setUnlocalizedName("stairsWoodJungle"));
/* 1325 */     registerBlock(137, "command_block", (new BlockCommandBlock(MapColor.BROWN)).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("commandBlock"));
/* 1326 */     registerBlock(138, "beacon", (new BlockBeacon()).setUnlocalizedName("beacon").setLightLevel(1.0F));
/* 1327 */     registerBlock(139, "cobblestone_wall", (new BlockWall(block)).setUnlocalizedName("cobbleWall"));
/* 1328 */     registerBlock(140, "flower_pot", (new BlockFlowerPot()).setHardness(0.0F).setSoundType(SoundType.STONE).setUnlocalizedName("flowerPot"));
/* 1329 */     registerBlock(141, "carrots", (new BlockCarrot()).setUnlocalizedName("carrots"));
/* 1330 */     registerBlock(142, "potatoes", (new BlockPotato()).setUnlocalizedName("potatoes"));
/* 1331 */     registerBlock(143, "wooden_button", (new BlockButtonWood()).setHardness(0.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("button"));
/* 1332 */     registerBlock(144, "skull", (new BlockSkull()).setHardness(1.0F).setSoundType(SoundType.STONE).setUnlocalizedName("skull"));
/* 1333 */     registerBlock(145, "anvil", (new BlockAnvil()).setHardness(5.0F).setSoundType(SoundType.ANVIL).setResistance(2000.0F).setUnlocalizedName("anvil"));
/* 1334 */     registerBlock(146, "trapped_chest", (new BlockChest(BlockChest.Type.TRAP)).setHardness(2.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("chestTrap"));
/* 1335 */     registerBlock(147, "light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.IRON, 15, MapColor.GOLD)).setHardness(0.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("weightedPlate_light"));
/* 1336 */     registerBlock(148, "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.IRON, 150)).setHardness(0.5F).setSoundType(SoundType.WOOD).setUnlocalizedName("weightedPlate_heavy"));
/* 1337 */     registerBlock(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).setHardness(0.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("comparator").disableStats());
/* 1338 */     registerBlock(150, "powered_comparator", (new BlockRedstoneComparator(true)).setHardness(0.0F).setLightLevel(0.625F).setSoundType(SoundType.WOOD).setUnlocalizedName("comparator").disableStats());
/* 1339 */     registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
/* 1340 */     registerBlock(152, "redstone_block", (new BlockCompressedPowered(Material.IRON, MapColor.TNT)).setHardness(5.0F).setResistance(10.0F).setSoundType(SoundType.METAL).setUnlocalizedName("blockRedstone").setCreativeTab(CreativeTabs.REDSTONE));
/* 1341 */     registerBlock(153, "quartz_ore", (new BlockOre(MapColor.NETHERRACK)).setHardness(3.0F).setResistance(5.0F).setSoundType(SoundType.STONE).setUnlocalizedName("netherquartz"));
/* 1342 */     registerBlock(154, "hopper", (new BlockHopper()).setHardness(3.0F).setResistance(8.0F).setSoundType(SoundType.METAL).setUnlocalizedName("hopper"));
/* 1343 */     Block block11 = (new BlockQuartz()).setSoundType(SoundType.STONE).setHardness(0.8F).setUnlocalizedName("quartzBlock");
/* 1344 */     registerBlock(155, "quartz_block", block11);
/* 1345 */     registerBlock(156, "quartz_stairs", (new BlockStairs(block11.getDefaultState().withProperty((IProperty)BlockQuartz.VARIANT, BlockQuartz.EnumType.DEFAULT))).setUnlocalizedName("stairsQuartz"));
/* 1346 */     registerBlock(157, "activator_rail", (new BlockRailPowered()).setHardness(0.7F).setSoundType(SoundType.METAL).setUnlocalizedName("activatorRail"));
/* 1347 */     registerBlock(158, "dropper", (new BlockDropper()).setHardness(3.5F).setSoundType(SoundType.STONE).setUnlocalizedName("dropper"));
/* 1348 */     registerBlock(159, "stained_hardened_clay", (new BlockStainedHardenedClay()).setHardness(1.25F).setResistance(7.0F).setSoundType(SoundType.STONE).setUnlocalizedName("clayHardenedStained"));
/* 1349 */     registerBlock(160, "stained_glass_pane", (new BlockStainedGlassPane()).setHardness(0.3F).setSoundType(SoundType.GLASS).setUnlocalizedName("thinStainedGlass"));
/* 1350 */     registerBlock(161, "leaves2", (new BlockNewLeaf()).setUnlocalizedName("leaves"));
/* 1351 */     registerBlock(162, "log2", (new BlockNewLog()).setUnlocalizedName("log"));
/* 1352 */     registerBlock(163, "acacia_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA))).setUnlocalizedName("stairsWoodAcacia"));
/* 1353 */     registerBlock(164, "dark_oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK))).setUnlocalizedName("stairsWoodDarkOak"));
/* 1354 */     registerBlock(165, "slime", (new BlockSlime()).setUnlocalizedName("slime").setSoundType(SoundType.SLIME));
/* 1355 */     registerBlock(166, "barrier", (new BlockBarrier()).setUnlocalizedName("barrier"));
/* 1356 */     registerBlock(167, "iron_trapdoor", (new BlockTrapDoor(Material.IRON)).setHardness(5.0F).setSoundType(SoundType.METAL).setUnlocalizedName("ironTrapdoor").disableStats());
/* 1357 */     registerBlock(168, "prismarine", (new BlockPrismarine()).setHardness(1.5F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("prismarine"));
/* 1358 */     registerBlock(169, "sea_lantern", (new BlockSeaLantern(Material.GLASS)).setHardness(0.3F).setSoundType(SoundType.GLASS).setLightLevel(1.0F).setUnlocalizedName("seaLantern"));
/* 1359 */     registerBlock(170, "hay_block", (new BlockHay()).setHardness(0.5F).setSoundType(SoundType.PLANT).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1360 */     registerBlock(171, "carpet", (new BlockCarpet()).setHardness(0.1F).setSoundType(SoundType.CLOTH).setUnlocalizedName("woolCarpet").setLightOpacity(0));
/* 1361 */     registerBlock(172, "hardened_clay", (new BlockHardenedClay()).setHardness(1.25F).setResistance(7.0F).setSoundType(SoundType.STONE).setUnlocalizedName("clayHardened"));
/* 1362 */     registerBlock(173, "coal_block", (new Block(Material.ROCK, MapColor.BLACK)).setHardness(5.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1363 */     registerBlock(174, "packed_ice", (new BlockPackedIce()).setHardness(0.5F).setSoundType(SoundType.GLASS).setUnlocalizedName("icePacked"));
/* 1364 */     registerBlock(175, "double_plant", new BlockDoublePlant());
/* 1365 */     registerBlock(176, "standing_banner", (new BlockBanner.BlockBannerStanding()).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("banner").disableStats());
/* 1366 */     registerBlock(177, "wall_banner", (new BlockBanner.BlockBannerHanging()).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("banner").disableStats());
/* 1367 */     registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
/* 1368 */     Block block12 = (new BlockRedSandstone()).setSoundType(SoundType.STONE).setHardness(0.8F).setUnlocalizedName("redSandStone");
/* 1369 */     registerBlock(179, "red_sandstone", block12);
/* 1370 */     registerBlock(180, "red_sandstone_stairs", (new BlockStairs(block12.getDefaultState().withProperty((IProperty)BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH))).setUnlocalizedName("stairsRedSandStone"));
/* 1371 */     registerBlock(181, "double_stone_slab2", (new BlockDoubleStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stoneSlab2"));
/* 1372 */     registerBlock(182, "stone_slab2", (new BlockHalfStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("stoneSlab2"));
/* 1373 */     registerBlock(183, "spruce_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.SPRUCE)).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("spruceFenceGate"));
/* 1374 */     registerBlock(184, "birch_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.BIRCH)).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("birchFenceGate"));
/* 1375 */     registerBlock(185, "jungle_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.JUNGLE)).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("jungleFenceGate"));
/* 1376 */     registerBlock(186, "dark_oak_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK)).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("darkOakFenceGate"));
/* 1377 */     registerBlock(187, "acacia_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.ACACIA)).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("acaciaFenceGate"));
/* 1378 */     registerBlock(188, "spruce_fence", (new BlockFence(Material.WOOD, BlockPlanks.EnumType.SPRUCE.getMapColor())).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("spruceFence"));
/* 1379 */     registerBlock(189, "birch_fence", (new BlockFence(Material.WOOD, BlockPlanks.EnumType.BIRCH.getMapColor())).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("birchFence"));
/* 1380 */     registerBlock(190, "jungle_fence", (new BlockFence(Material.WOOD, BlockPlanks.EnumType.JUNGLE.getMapColor())).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("jungleFence"));
/* 1381 */     registerBlock(191, "dark_oak_fence", (new BlockFence(Material.WOOD, BlockPlanks.EnumType.DARK_OAK.getMapColor())).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("darkOakFence"));
/* 1382 */     registerBlock(192, "acacia_fence", (new BlockFence(Material.WOOD, BlockPlanks.EnumType.ACACIA.getMapColor())).setHardness(2.0F).setResistance(5.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("acaciaFence"));
/* 1383 */     registerBlock(193, "spruce_door", (new BlockDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("doorSpruce").disableStats());
/* 1384 */     registerBlock(194, "birch_door", (new BlockDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("doorBirch").disableStats());
/* 1385 */     registerBlock(195, "jungle_door", (new BlockDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("doorJungle").disableStats());
/* 1386 */     registerBlock(196, "acacia_door", (new BlockDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("doorAcacia").disableStats());
/* 1387 */     registerBlock(197, "dark_oak_door", (new BlockDoor(Material.WOOD)).setHardness(3.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("doorDarkOak").disableStats());
/* 1388 */     registerBlock(198, "end_rod", (new BlockEndRod()).setHardness(0.0F).setLightLevel(0.9375F).setSoundType(SoundType.WOOD).setUnlocalizedName("endRod"));
/* 1389 */     registerBlock(199, "chorus_plant", (new BlockChorusPlant()).setHardness(0.4F).setSoundType(SoundType.WOOD).setUnlocalizedName("chorusPlant"));
/* 1390 */     registerBlock(200, "chorus_flower", (new BlockChorusFlower()).setHardness(0.4F).setSoundType(SoundType.WOOD).setUnlocalizedName("chorusFlower"));
/* 1391 */     Block block13 = (new Block(Material.ROCK, MapColor.MAGENTA)).setHardness(1.5F).setResistance(10.0F).setSoundType(SoundType.STONE).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setUnlocalizedName("purpurBlock");
/* 1392 */     registerBlock(201, "purpur_block", block13);
/* 1393 */     registerBlock(202, "purpur_pillar", (new BlockRotatedPillar(Material.ROCK, MapColor.MAGENTA)).setHardness(1.5F).setResistance(10.0F).setSoundType(SoundType.STONE).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setUnlocalizedName("purpurPillar"));
/* 1394 */     registerBlock(203, "purpur_stairs", (new BlockStairs(block13.getDefaultState())).setUnlocalizedName("stairsPurpur"));
/* 1395 */     registerBlock(204, "purpur_double_slab", (new BlockPurpurSlab.Double()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("purpurSlab"));
/* 1396 */     registerBlock(205, "purpur_slab", (new BlockPurpurSlab.Half()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("purpurSlab"));
/* 1397 */     registerBlock(206, "end_bricks", (new Block(Material.ROCK, MapColor.SAND)).setSoundType(SoundType.STONE).setHardness(0.8F).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setUnlocalizedName("endBricks"));
/* 1398 */     registerBlock(207, "beetroots", (new BlockBeetroot()).setUnlocalizedName("beetroots"));
/* 1399 */     Block block14 = (new BlockGrassPath()).setHardness(0.65F).setSoundType(SoundType.PLANT).setUnlocalizedName("grassPath").disableStats();
/* 1400 */     registerBlock(208, "grass_path", block14);
/* 1401 */     registerBlock(209, "end_gateway", (new BlockEndGateway(Material.PORTAL)).setHardness(-1.0F).setResistance(6000000.0F));
/* 1402 */     registerBlock(210, "repeating_command_block", (new BlockCommandBlock(MapColor.PURPLE)).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("repeatingCommandBlock"));
/* 1403 */     registerBlock(211, "chain_command_block", (new BlockCommandBlock(MapColor.GREEN)).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("chainCommandBlock"));
/* 1404 */     registerBlock(212, "frosted_ice", (new BlockFrostedIce()).setHardness(0.5F).setLightOpacity(3).setSoundType(SoundType.GLASS).setUnlocalizedName("frostedIce"));
/* 1405 */     registerBlock(213, "magma", (new BlockMagma()).setHardness(0.5F).setSoundType(SoundType.STONE).setUnlocalizedName("magma"));
/* 1406 */     registerBlock(214, "nether_wart_block", (new Block(Material.GRASS, MapColor.RED)).setCreativeTab(CreativeTabs.BUILDING_BLOCKS).setHardness(1.0F).setSoundType(SoundType.WOOD).setUnlocalizedName("netherWartBlock"));
/* 1407 */     registerBlock(215, "red_nether_brick", (new BlockNetherBrick()).setHardness(2.0F).setResistance(10.0F).setSoundType(SoundType.STONE).setUnlocalizedName("redNetherBrick").setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
/* 1408 */     registerBlock(216, "bone_block", (new BlockBone()).setUnlocalizedName("boneBlock"));
/* 1409 */     registerBlock(217, "structure_void", (new BlockStructureVoid()).setUnlocalizedName("structureVoid"));
/* 1410 */     registerBlock(218, "observer", (new BlockObserver()).setHardness(3.0F).setUnlocalizedName("observer"));
/* 1411 */     registerBlock(219, "white_shulker_box", (new BlockShulkerBox(EnumDyeColor.WHITE)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxWhite"));
/* 1412 */     registerBlock(220, "orange_shulker_box", (new BlockShulkerBox(EnumDyeColor.ORANGE)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxOrange"));
/* 1413 */     registerBlock(221, "magenta_shulker_box", (new BlockShulkerBox(EnumDyeColor.MAGENTA)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxMagenta"));
/* 1414 */     registerBlock(222, "light_blue_shulker_box", (new BlockShulkerBox(EnumDyeColor.LIGHT_BLUE)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxLightBlue"));
/* 1415 */     registerBlock(223, "yellow_shulker_box", (new BlockShulkerBox(EnumDyeColor.YELLOW)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxYellow"));
/* 1416 */     registerBlock(224, "lime_shulker_box", (new BlockShulkerBox(EnumDyeColor.LIME)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxLime"));
/* 1417 */     registerBlock(225, "pink_shulker_box", (new BlockShulkerBox(EnumDyeColor.PINK)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxPink"));
/* 1418 */     registerBlock(226, "gray_shulker_box", (new BlockShulkerBox(EnumDyeColor.GRAY)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxGray"));
/* 1419 */     registerBlock(227, "silver_shulker_box", (new BlockShulkerBox(EnumDyeColor.SILVER)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxSilver"));
/* 1420 */     registerBlock(228, "cyan_shulker_box", (new BlockShulkerBox(EnumDyeColor.CYAN)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxCyan"));
/* 1421 */     registerBlock(229, "purple_shulker_box", (new BlockShulkerBox(EnumDyeColor.PURPLE)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxPurple"));
/* 1422 */     registerBlock(230, "blue_shulker_box", (new BlockShulkerBox(EnumDyeColor.BLUE)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxBlue"));
/* 1423 */     registerBlock(231, "brown_shulker_box", (new BlockShulkerBox(EnumDyeColor.BROWN)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxBrown"));
/* 1424 */     registerBlock(232, "green_shulker_box", (new BlockShulkerBox(EnumDyeColor.GREEN)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxGreen"));
/* 1425 */     registerBlock(233, "red_shulker_box", (new BlockShulkerBox(EnumDyeColor.RED)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxRed"));
/* 1426 */     registerBlock(234, "black_shulker_box", (new BlockShulkerBox(EnumDyeColor.BLACK)).setHardness(2.0F).setSoundType(SoundType.STONE).setUnlocalizedName("shulkerBoxBlack"));
/* 1427 */     registerBlock(235, "white_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.WHITE));
/* 1428 */     registerBlock(236, "orange_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.ORANGE));
/* 1429 */     registerBlock(237, "magenta_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.MAGENTA));
/* 1430 */     registerBlock(238, "light_blue_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.LIGHT_BLUE));
/* 1431 */     registerBlock(239, "yellow_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.YELLOW));
/* 1432 */     registerBlock(240, "lime_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.LIME));
/* 1433 */     registerBlock(241, "pink_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.PINK));
/* 1434 */     registerBlock(242, "gray_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.GRAY));
/* 1435 */     registerBlock(243, "silver_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.SILVER));
/* 1436 */     registerBlock(244, "cyan_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.CYAN));
/* 1437 */     registerBlock(245, "purple_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.PURPLE));
/* 1438 */     registerBlock(246, "blue_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.BLUE));
/* 1439 */     registerBlock(247, "brown_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.BROWN));
/* 1440 */     registerBlock(248, "green_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.GREEN));
/* 1441 */     registerBlock(249, "red_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.RED));
/* 1442 */     registerBlock(250, "black_glazed_terracotta", new BlockGlazedTerracotta(EnumDyeColor.BLACK));
/* 1443 */     registerBlock(251, "concrete", (new BlockColored(Material.ROCK)).setHardness(1.8F).setSoundType(SoundType.STONE).setUnlocalizedName("concrete"));
/* 1444 */     registerBlock(252, "concrete_powder", (new BlockConcretePowder()).setHardness(0.5F).setSoundType(SoundType.SAND).setUnlocalizedName("concretePowder"));
/* 1445 */     registerBlock(255, "structure_block", (new BlockStructure()).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("structureBlock"));
/* 1446 */     REGISTRY.validateKey();
/*      */     
/* 1448 */     for (Block block15 : REGISTRY) {
/*      */       
/* 1450 */       if (block15.blockMaterial == Material.AIR) {
/*      */         
/* 1452 */         block15.useNeighborBrightness = false;
/*      */         
/*      */         continue;
/*      */       } 
/* 1456 */       boolean flag = false;
/* 1457 */       boolean flag1 = block15 instanceof BlockStairs;
/* 1458 */       boolean flag2 = block15 instanceof BlockSlab;
/* 1459 */       boolean flag3 = !(block15 != block6 && block15 != block14);
/* 1460 */       boolean flag4 = block15.translucent;
/* 1461 */       boolean flag5 = (block15.lightOpacity == 0);
/*      */       
/* 1463 */       if (flag1 || flag2 || flag3 || flag4 || flag5)
/*      */       {
/* 1465 */         flag = true;
/*      */       }
/*      */       
/* 1468 */       block15.useNeighborBrightness = flag;
/*      */     } 
/*      */ 
/*      */     
/* 1472 */     Set<Block> set = Sets.newHashSet((Object[])new Block[] { (Block)REGISTRY.getObject(new ResourceLocation("tripwire")) });
/*      */     
/* 1474 */     for (Block block16 : REGISTRY) {
/*      */       
/* 1476 */       if (set.contains(block16)) {
/*      */         
/* 1478 */         for (int i = 0; i < 15; i++) {
/*      */           
/* 1480 */           int j = REGISTRY.getIDForObject(block16) << 4 | i;
/* 1481 */           BLOCK_STATE_IDS.put(block16.getStateFromMeta(i), j);
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/* 1486 */       UnmodifiableIterator unmodifiableiterator = block16.getBlockState().getValidStates().iterator();
/*      */       
/* 1488 */       while (unmodifiableiterator.hasNext()) {
/*      */         
/* 1490 */         IBlockState iblockstate = (IBlockState)unmodifiableiterator.next();
/* 1491 */         int k = REGISTRY.getIDForObject(block16) << 4 | block16.getMetaFromState(iblockstate);
/* 1492 */         BLOCK_STATE_IDS.put(iblockstate, k);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void registerBlock(int id, ResourceLocation textualID, Block block_) {
/* 1500 */     REGISTRY.register(id, textualID, block_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerBlock(int id, String textualID, Block block_) {
/* 1505 */     registerBlock(id, new ResourceLocation(textualID), block_);
/*      */   }
/*      */   
/*      */   public enum EnumOffsetType
/*      */   {
/* 1510 */     NONE,
/* 1511 */     XZ,
/* 1512 */     XYZ;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */