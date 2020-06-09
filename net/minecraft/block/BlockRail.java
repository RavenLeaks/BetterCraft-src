/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRail
/*     */   extends BlockRailBase {
/*  14 */   public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.create("shape", BlockRailBase.EnumRailDirection.class);
/*     */ 
/*     */   
/*     */   protected BlockRail() {
/*  18 */     super(false);
/*  19 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateState(IBlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_) {
/*  24 */     if (p_189541_4_.getDefaultState().canProvidePower() && (new BlockRailBase.Rail(this, p_189541_2_, p_189541_3_, p_189541_1_)).countAdjacentRails() == 3)
/*     */     {
/*  26 */       updateDir(p_189541_2_, p_189541_3_, p_189541_1_, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
/*  32 */     return (IProperty<BlockRailBase.EnumRailDirection>)SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  40 */     return getDefaultState().withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  48 */     return ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)).getMetadata();
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
/*  59 */     switch (rot) {
/*     */       
/*     */       case null:
/*  62 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/*  65 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_WEST:
/*  68 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case ASCENDING_NORTH:
/*  71 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_SOUTH:
/*  74 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/*  77 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/*  80 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/*  83 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_EAST:
/*  86 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */         } 
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/*  90 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/*  93 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case ASCENDING_WEST:
/*  96 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_NORTH:
/*  99 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 102 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case SOUTH_EAST:
/* 105 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case SOUTH_WEST:
/* 108 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 111 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 114 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_SOUTH:
/* 117 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
/*     */           
/*     */           case EAST_WEST:
/* 120 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */         } 
/*     */       
/*     */       case CLOCKWISE_90:
/* 124 */         switch ((BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE)) {
/*     */           
/*     */           case null:
/* 127 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_WEST:
/* 130 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case ASCENDING_NORTH:
/* 133 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 136 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case SOUTH_EAST:
/* 139 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 142 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_WEST:
/* 145 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case NORTH_EAST:
/* 148 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_SOUTH:
/* 151 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
/*     */           
/*     */           case EAST_WEST:
/* 154 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
/*     */         } 
/*     */         break;
/*     */     } 
/* 158 */     return state;
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
/* 170 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue((IProperty)SHAPE);
/*     */     
/* 172 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 175 */         switch (blockrailbase$enumraildirection) {
/*     */           
/*     */           case ASCENDING_NORTH:
/* 178 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
/*     */           
/*     */           case ASCENDING_SOUTH:
/* 181 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
/*     */           
/*     */           case SOUTH_EAST:
/* 184 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           
/*     */           case SOUTH_WEST:
/* 187 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */           
/*     */           case NORTH_WEST:
/* 190 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case NORTH_EAST:
/* 193 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */         } 
/*     */         
/* 196 */         return super.withMirror(state, mirrorIn);
/*     */ 
/*     */       
/*     */       case null:
/* 200 */         switch (blockrailbase$enumraildirection) {
/*     */           
/*     */           case null:
/* 203 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
/*     */           
/*     */           case ASCENDING_WEST:
/* 206 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
/*     */ 
/*     */           
/*     */           default:
/*     */             break;
/*     */ 
/*     */           
/*     */           case SOUTH_EAST:
/* 214 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
/*     */           
/*     */           case SOUTH_WEST:
/* 217 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);
/*     */           
/*     */           case NORTH_WEST:
/* 220 */             return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);
/*     */           case NORTH_EAST:
/*     */             break;
/* 223 */         }  return state.withProperty((IProperty)SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
/*     */     } 
/*     */ 
/*     */     
/* 227 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 232 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)SHAPE });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRail.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */