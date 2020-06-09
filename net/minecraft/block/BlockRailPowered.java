/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRailPowered
/*     */   extends BlockRailBase {
/*  17 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class, new Predicate<BlockRailBase.EnumRailDirection>()
/*     */       {
/*     */         public boolean apply(@Nullable BlockRailBase.EnumRailDirection p_apply_1_)
/*     */         {
/*  21 */           return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         }
/*     */       });
/*  24 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   protected BlockRailPowered() {
/*  28 */     super(true);
/*  29 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean findPoweredRailSignal(World worldIn, BlockPos pos, IBlockState state, boolean p_176566_4_, int p_176566_5_) {
/*  35 */     if (p_176566_5_ >= 8)
/*     */     {
/*  37 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  41 */     int i = pos.getX();
/*  42 */     int j = pos.getY();
/*  43 */     int k = pos.getZ();
/*  44 */     boolean flag = true;
/*  45 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE);
/*     */     
/*  47 */     switch (blockrailbase$enumraildirection) {
/*     */       
/*     */       case NORTH_SOUTH:
/*  50 */         if (p_176566_4_) {
/*     */           
/*  52 */           k++;
/*     */           
/*     */           break;
/*     */         } 
/*  56 */         k--;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EAST_WEST:
/*  62 */         if (p_176566_4_) {
/*     */           
/*  64 */           i--;
/*     */           
/*     */           break;
/*     */         } 
/*  68 */         i++;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/*  74 */         if (p_176566_4_) {
/*     */           
/*  76 */           i--;
/*     */         }
/*     */         else {
/*     */           
/*  80 */           i++;
/*  81 */           j++;
/*  82 */           flag = false;
/*     */         } 
/*     */         
/*  85 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         break;
/*     */       
/*     */       case ASCENDING_WEST:
/*  89 */         if (p_176566_4_) {
/*     */           
/*  91 */           i--;
/*  92 */           j++;
/*  93 */           flag = false;
/*     */         }
/*     */         else {
/*     */           
/*  97 */           i++;
/*     */         } 
/*     */         
/* 100 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         break;
/*     */       
/*     */       case ASCENDING_NORTH:
/* 104 */         if (p_176566_4_) {
/*     */           
/* 106 */           k++;
/*     */         }
/*     */         else {
/*     */           
/* 110 */           k--;
/* 111 */           j++;
/* 112 */           flag = false;
/*     */         } 
/*     */         
/* 115 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         break;
/*     */       
/*     */       case ASCENDING_SOUTH:
/* 119 */         if (p_176566_4_) {
/*     */           
/* 121 */           k++;
/* 122 */           j++;
/* 123 */           flag = false;
/*     */         }
/*     */         else {
/*     */           
/* 127 */           k--;
/*     */         } 
/*     */         
/* 130 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         break;
/*     */     } 
/* 133 */     if (isSameRailWithPower(worldIn, new BlockPos(i, j, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection))
/*     */     {
/* 135 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 139 */     return (flag && isSameRailWithPower(worldIn, new BlockPos(i, j - 1, k), p_176566_4_, p_176566_5_, blockrailbase$enumraildirection));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSameRailWithPower(World worldIn, BlockPos pos, boolean p_176567_3_, int distance, BlockRailBase.EnumRailDirection p_176567_5_) {
/* 146 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 148 */     if (iblockstate.getBlock() != this)
/*     */     {
/* 150 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 154 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue((IProperty)SHAPE);
/*     */     
/* 156 */     if (p_176567_5_ != BlockRailBase.EnumRailDirection.EAST_WEST || (blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.NORTH_SOUTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_NORTH && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_SOUTH)) {
/*     */       
/* 158 */       if (p_176567_5_ != BlockRailBase.EnumRailDirection.NORTH_SOUTH || (blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.EAST_WEST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_EAST && blockrailbase$enumraildirection != BlockRailBase.EnumRailDirection.ASCENDING_WEST)) {
/*     */         
/* 160 */         if (((Boolean)iblockstate.getValue((IProperty)POWERED)).booleanValue())
/*     */         {
/* 162 */           return worldIn.isBlockPowered(pos) ? true : findPoweredRailSignal(worldIn, pos, iblockstate, p_176567_3_, distance + 1);
/*     */         }
/*     */ 
/*     */         
/* 166 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 171 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(IBlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_) {
/* 183 */     boolean flag = ((Boolean)p_189541_1_.getValue((IProperty)POWERED)).booleanValue();
/* 184 */     boolean flag1 = !(!p_189541_2_.isBlockPowered(p_189541_3_) && !findPoweredRailSignal(p_189541_2_, p_189541_3_, p_189541_1_, true, 0) && !findPoweredRailSignal(p_189541_2_, p_189541_3_, p_189541_1_, false, 0));
/*     */     
/* 186 */     if (flag1 != flag) {
/*     */       
/* 188 */       p_189541_2_.setBlockState(p_189541_3_, p_189541_1_.withProperty((IProperty)POWERED, Boolean.valueOf(flag1)), 3);
/* 189 */       p_189541_2_.notifyNeighborsOfStateChange(p_189541_3_.down(), this, false);
/*     */       
/* 191 */       if (((BlockRailBase.EnumRailDirection)p_189541_1_.getValue((IProperty)SHAPE)).isAscending())
/*     */       {
/* 193 */         p_189541_2_.notifyNeighborsOfStateChange(p_189541_3_.up(), this, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/* 200 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 208 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 216 */     int i = 0;
/* 217 */     i |= ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
/*     */     
/* 219 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 221 */       i |= 0x8;
/*     */     }
/*     */     
/* 224 */     return i;
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
/* 235 */     switch (rot) {
/*     */       
/*     */       case null:
/* 238 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/* 241 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_WEST:
/* 244 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 247 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 250 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/* 253 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 256 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 259 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_EAST:
/* 262 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         } 
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 266 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case NORTH_SOUTH:
/* 269 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
/*     */           
/*     */           case EAST_WEST:
/* 272 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */           
/*     */           case null:
/* 275 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case ASCENDING_WEST:
/* 278 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 281 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 284 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case SOUTH_EAST:
/* 287 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case SOUTH_WEST:
/* 290 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 293 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 296 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */         } 
/*     */       
/*     */       case CLOCKWISE_90:
/* 300 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case NORTH_SOUTH:
/* 303 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
/*     */           
/*     */           case EAST_WEST:
/* 306 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */           
/*     */           case null:
/* 309 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_WEST:
/* 312 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 315 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 318 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case SOUTH_EAST:
/* 321 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 324 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_WEST:
/* 327 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case NORTH_EAST:
/* 330 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */         } 
/*     */         break;
/*     */     } 
/* 334 */     return state;
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
/* 346 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE);
/*     */     
/* 348 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 351 */         switch (blockrailbase$enumraildirection) {
/*     */           
/*     */           case ASCENDING_NORTH:
/* 354 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 357 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/* 360 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case SOUTH_WEST:
/* 363 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_WEST:
/* 366 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 369 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */         } 
/*     */         
/* 372 */         return super.withMirror(state, mirrorIn);
/*     */ 
/*     */       
/*     */       case null:
/* 376 */         switch (blockrailbase$enumraildirection) {
/*     */           
/*     */           case null:
/* 379 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_WEST:
/* 382 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */ 
/*     */           
/*     */           default:
/*     */             break;
/*     */ 
/*     */           
/*     */           case SOUTH_EAST:
/* 390 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 393 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 396 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           case NORTH_EAST:
/*     */             break;
/* 399 */         }  return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */     } 
/*     */ 
/*     */     
/* 403 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 408 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)SHAPE, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRailPowered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */