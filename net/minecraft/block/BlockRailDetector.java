/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartCommandBlock;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRailDetector extends BlockRailBase {
/*  28 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
/*     */       {
/*     */         public boolean apply(@Nullable BlockRailBase.EnumRailDirection p_apply_1_)
/*     */         {
/*  32 */           return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         }
/*     */       });
/*  35 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   public BlockRailDetector() {
/*  39 */     super(true);
/*  40 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
/*  41 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  49 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/*  57 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/*  65 */     if (!worldIn.isRemote)
/*     */     {
/*  67 */       if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/*  69 */         updatePoweredState(worldIn, pos, state);
/*     */       }
/*     */     }
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
/*  83 */     if (!worldIn.isRemote && ((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/*  85 */       updatePoweredState(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  91 */     return ((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  96 */     if (!((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/*  98 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 102 */     return (side == EnumFacing.UP) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updatePoweredState(World worldIn, BlockPos pos, IBlockState state) {
/* 108 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 109 */     boolean flag1 = false;
/* 110 */     List<EntityMinecart> list = findMinecarts(worldIn, pos, EntityMinecart.class, (Predicate<Entity>[])new Predicate[0]);
/*     */     
/* 112 */     if (!list.isEmpty())
/*     */     {
/* 114 */       flag1 = true;
/*     */     }
/*     */     
/* 117 */     if (flag1 && !flag) {
/*     */       
/* 119 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/* 120 */       updateConnectedRails(worldIn, pos, state, true);
/* 121 */       worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 122 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
/* 123 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 126 */     if (!flag1 && flag) {
/*     */       
/* 128 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)), 3);
/* 129 */       updateConnectedRails(worldIn, pos, state, false);
/* 130 */       worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 131 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
/* 132 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 135 */     if (flag1)
/*     */     {
/* 137 */       worldIn.scheduleUpdate(new BlockPos((Vec3i)pos), this, tickRate(worldIn));
/*     */     }
/*     */     
/* 140 */     worldIn.updateComparatorOutputLevel(pos, this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateConnectedRails(World worldIn, BlockPos pos, IBlockState state, boolean p_185592_4_) {
/* 145 */     BlockRailBase.Rail blockrailbase$rail = new BlockRailBase.Rail(this, worldIn, pos, state);
/*     */     
/* 147 */     for (BlockPos blockpos : blockrailbase$rail.getConnectedRails()) {
/*     */       
/* 149 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 151 */       if (iblockstate != null)
/*     */       {
/* 153 */         iblockstate.neighborChanged(worldIn, blockpos, iblockstate.getBlock(), pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 163 */     super.onBlockAdded(worldIn, pos, state);
/* 164 */     updatePoweredState(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/* 169 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 179 */     if (((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue()) {
/*     */       
/* 181 */       List<EntityMinecartCommandBlock> list = findMinecarts(worldIn, pos, EntityMinecartCommandBlock.class, (Predicate<Entity>[])new Predicate[0]);
/*     */       
/* 183 */       if (!list.isEmpty())
/*     */       {
/* 185 */         return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlockLogic().getSuccessCount();
/*     */       }
/*     */       
/* 188 */       List<EntityMinecart> list1 = findMinecarts(worldIn, pos, EntityMinecart.class, (Predicate<Entity>[])new Predicate[] { EntitySelectors.HAS_INVENTORY });
/*     */       
/* 190 */       if (!list1.isEmpty())
/*     */       {
/* 192 */         return Container.calcRedstoneFromInventory((IInventory)list1.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 196 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends EntityMinecart> List<T> findMinecarts(World worldIn, BlockPos pos, Class<T> clazz, Predicate... filter) {
/* 201 */     AxisAlignedBB axisalignedbb = getDectectionBox(pos);
/* 202 */     return (filter.length != 1) ? worldIn.getEntitiesWithinAABB(clazz, axisalignedbb) : worldIn.getEntitiesWithinAABB(clazz, axisalignedbb, filter[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB getDectectionBox(BlockPos pos) {
/* 207 */     float f = 0.2F;
/* 208 */     return new AxisAlignedBB((pos.getX() + 0.2F), pos.getY(), (pos.getZ() + 0.2F), ((pos.getX() + 1) - 0.2F), ((pos.getY() + 1) - 0.2F), ((pos.getZ() + 1) - 0.2F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 216 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 224 */     int i = 0;
/* 225 */     i |= ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
/*     */     
/* 227 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 229 */       i |= 0x8;
/*     */     }
/*     */     
/* 232 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 243 */     switch (rot) {
/*     */       
/*     */       case null:
/* 246 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/* 249 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_WEST:
/* 252 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 255 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 258 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/* 261 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 264 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 267 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_EAST:
/* 270 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         } 
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 274 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/* 277 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case ASCENDING_WEST:
/* 280 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 283 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 286 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case SOUTH_EAST:
/* 289 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case SOUTH_WEST:
/* 292 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 295 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 298 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_SOUTH:
/* 301 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
/*     */           
/*     */           case EAST_WEST:
/* 304 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */         } 
/*     */       
/*     */       case CLOCKWISE_90:
/* 308 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/* 311 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_WEST:
/* 314 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 317 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 320 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case SOUTH_EAST:
/* 323 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 326 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_WEST:
/* 329 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case NORTH_EAST:
/* 332 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_SOUTH:
/* 335 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
/*     */           
/*     */           case EAST_WEST:
/* 338 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */         } 
/*     */         break;
/*     */     } 
/* 342 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 354 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE);
/*     */     
/* 356 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 359 */         switch (blockrailbase$enumraildirection) {
/*     */           
/*     */           case ASCENDING_NORTH:
/* 362 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 365 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/* 368 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case SOUTH_WEST:
/* 371 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_WEST:
/* 374 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 377 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */         } 
/*     */         
/* 380 */         return super.withMirror(state, mirrorIn);
/*     */ 
/*     */       
/*     */       case null:
/* 384 */         switch (blockrailbase$enumraildirection) {
/*     */           
/*     */           case null:
/* 387 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_WEST:
/* 390 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */ 
/*     */           
/*     */           default:
/*     */             break;
/*     */ 
/*     */           
/*     */           case SOUTH_EAST:
/* 398 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 401 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 404 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           case NORTH_EAST:
/*     */             break;
/* 407 */         }  return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */     } 
/*     */ 
/*     */     
/* 411 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 416 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)SHAPE, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRailDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */