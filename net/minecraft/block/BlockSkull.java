/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockMaterialMatcher;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.BlockStateMatcher;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSkull extends BlockContainer {
/*  44 */   public static final PropertyDirection FACING = BlockDirectional.FACING;
/*  45 */   public static final PropertyBool NODROP = PropertyBool.create("nodrop");
/*  46 */   private static final Predicate<BlockWorldState> IS_WITHER_SKELETON = new Predicate<BlockWorldState>()
/*     */     {
/*     */       public boolean apply(@Nullable BlockWorldState p_apply_1_)
/*     */       {
/*  50 */         return (p_apply_1_.getBlockState() != null && p_apply_1_.getBlockState().getBlock() == Blocks.SKULL && p_apply_1_.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)p_apply_1_.getTileEntity()).getSkullType() == 1);
/*     */       }
/*     */     };
/*  53 */   protected static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);
/*  54 */   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.5D, 0.75D, 0.75D, 1.0D);
/*  55 */   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 0.5D);
/*  56 */   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.5D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
/*  57 */   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 0.5D, 0.75D, 0.75D);
/*     */   
/*     */   private BlockPattern witherBasePattern;
/*     */   private BlockPattern witherPattern;
/*     */   
/*     */   protected BlockSkull() {
/*  63 */     super(Material.CIRCUITS);
/*  64 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)NODROP, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  72 */     return I18n.translateToLocal("tile.skull.skeleton.name");
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
/*     */   public boolean func_190946_v(IBlockState p_190946_1_) {
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  95 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/*  99 */         return DEFAULT_AABB;
/*     */       
/*     */       case NORTH:
/* 102 */         return NORTH_AABB;
/*     */       
/*     */       case SOUTH:
/* 105 */         return SOUTH_AABB;
/*     */       
/*     */       case WEST:
/* 108 */         return WEST_AABB;
/*     */       case EAST:
/*     */         break;
/* 111 */     }  return EAST_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 121 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)NODROP, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 129 */     return (TileEntity)new TileEntitySkull();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 134 */     int i = 0;
/* 135 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 137 */     if (tileentity instanceof TileEntitySkull)
/*     */     {
/* 139 */       i = ((TileEntitySkull)tileentity).getSkullType();
/*     */     }
/*     */     
/* 142 */     return new ItemStack(Items.SKULL, 1, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 154 */     if (player.capabilities.isCreativeMode) {
/*     */       
/* 156 */       state = state.withProperty((IProperty)NODROP, Boolean.valueOf(true));
/* 157 */       worldIn.setBlockState(pos, state, 4);
/*     */     } 
/*     */     
/* 160 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 168 */     if (!worldIn.isRemote) {
/*     */       
/* 170 */       if (!((Boolean)state.getValue((IProperty)NODROP)).booleanValue()) {
/*     */         
/* 172 */         TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */         
/* 174 */         if (tileentity instanceof TileEntitySkull) {
/*     */           
/* 176 */           TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/* 177 */           ItemStack itemstack = getItem(worldIn, pos, state);
/*     */           
/* 179 */           if (tileentityskull.getSkullType() == 3 && tileentityskull.getPlayerProfile() != null) {
/*     */             
/* 181 */             itemstack.setTagCompound(new NBTTagCompound());
/* 182 */             NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 183 */             NBTUtil.writeGameProfile(nbttagcompound, tileentityskull.getPlayerProfile());
/* 184 */             itemstack.getTagCompound().setTag("SkullOwner", (NBTBase)nbttagcompound);
/*     */           } 
/*     */           
/* 187 */           spawnAsEntity(worldIn, pos, itemstack);
/*     */         } 
/*     */       } 
/*     */       
/* 191 */       super.breakBlock(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 200 */     return Items.SKULL;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDispenserPlace(World worldIn, BlockPos pos, ItemStack stack) {
/* 205 */     if (stack.getMetadata() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote)
/*     */     {
/* 207 */       return (getWitherBasePattern().match(worldIn, pos) != null);
/*     */     }
/*     */ 
/*     */     
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkWitherSpawn(World worldIn, BlockPos pos, TileEntitySkull te) {
/* 217 */     if (te.getSkullType() == 1 && pos.getY() >= 2 && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL && !worldIn.isRemote) {
/*     */       
/* 219 */       BlockPattern blockpattern = getWitherPattern();
/* 220 */       BlockPattern.PatternHelper blockpattern$patternhelper = blockpattern.match(worldIn, pos);
/*     */       
/* 222 */       if (blockpattern$patternhelper != null) {
/*     */         
/* 224 */         for (int i = 0; i < 3; i++) {
/*     */           
/* 226 */           BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, 0, 0);
/* 227 */           worldIn.setBlockState(blockworldstate.getPos(), blockworldstate.getBlockState().withProperty((IProperty)NODROP, Boolean.valueOf(true)), 2);
/*     */         } 
/*     */         
/* 230 */         for (int j = 0; j < blockpattern.getPalmLength(); j++) {
/*     */           
/* 232 */           for (int k = 0; k < blockpattern.getThumbLength(); k++) {
/*     */             
/* 234 */             BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(j, k, 0);
/* 235 */             worldIn.setBlockState(blockworldstate1.getPos(), Blocks.AIR.getDefaultState(), 2);
/*     */           } 
/*     */         } 
/*     */         
/* 239 */         BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 0, 0).getPos();
/* 240 */         EntityWither entitywither = new EntityWither(worldIn);
/* 241 */         BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
/* 242 */         entitywither.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.55D, blockpos1.getZ() + 0.5D, (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) ? 0.0F : 90.0F, 0.0F);
/* 243 */         entitywither.renderYawOffset = (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) ? 0.0F : 90.0F;
/* 244 */         entitywither.ignite();
/*     */         
/* 246 */         for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entitywither.getEntityBoundingBox().expandXyz(50.0D)))
/*     */         {
/* 248 */           CriteriaTriggers.field_192133_m.func_192229_a(entityplayermp, (Entity)entitywither);
/*     */         }
/*     */         
/* 251 */         worldIn.spawnEntityInWorld((Entity)entitywither);
/*     */         
/* 253 */         for (int l = 0; l < 120; l++)
/*     */         {
/* 255 */           worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, blockpos.getX() + worldIn.rand.nextDouble(), (blockpos.getY() - 2) + worldIn.rand.nextDouble() * 3.9D, blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 258 */         for (int i1 = 0; i1 < blockpattern.getPalmLength(); i1++) {
/*     */           
/* 260 */           for (int j1 = 0; j1 < blockpattern.getThumbLength(); j1++) {
/*     */             
/* 262 */             BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(i1, j1, 0);
/* 263 */             worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR, false);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 275 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta & 0x7)).withProperty((IProperty)NODROP, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 283 */     int i = 0;
/* 284 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 286 */     if (((Boolean)state.getValue((IProperty)NODROP)).booleanValue())
/*     */     {
/* 288 */       i |= 0x8;
/*     */     }
/*     */     
/* 291 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 300 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 309 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 314 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)NODROP });
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getWitherBasePattern() {
/* 319 */     if (this.witherBasePattern == null)
/*     */     {
/* 321 */       this.witherBasePattern = FactoryBlockPattern.start().aisle(new String[] { "   ", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.SOUL_SAND))).where('~', BlockWorldState.hasState((Predicate)BlockMaterialMatcher.forMaterial(Material.AIR))).build();
/*     */     }
/*     */     
/* 324 */     return this.witherBasePattern;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockPattern getWitherPattern() {
/* 329 */     if (this.witherPattern == null)
/*     */     {
/* 331 */       this.witherPattern = FactoryBlockPattern.start().aisle(new String[] { "^^^", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.SOUL_SAND))).where('^', IS_WITHER_SKELETON).where('~', BlockWorldState.hasState((Predicate)BlockMaterialMatcher.forMaterial(Material.AIR))).build();
/*     */     }
/*     */     
/* 334 */     return this.witherPattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 339 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */