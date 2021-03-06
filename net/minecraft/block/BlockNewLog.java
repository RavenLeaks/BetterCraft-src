/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ public class BlockNewLog
/*     */   extends BlockLog {
/*  19 */   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>()
/*     */       {
/*     */         public boolean apply(@Nullable BlockPlanks.EnumType p_apply_1_)
/*     */         {
/*  23 */           return (p_apply_1_.getMetadata() >= 4);
/*     */         }
/*     */       });
/*     */ 
/*     */   
/*     */   public BlockNewLog() {
/*  29 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.ACACIA).withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.Y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/*  37 */     BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)state.getValue((IProperty)VARIANT);
/*     */     
/*  39 */     switch ((BlockLog.EnumAxis)state.getValue((IProperty)LOG_AXIS)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/*  45 */         switch (blockplanks$enumtype) {
/*     */ 
/*     */           
/*     */           default:
/*  49 */             return MapColor.STONE;
/*     */           case DARK_OAK:
/*     */             break;
/*  52 */         }  return BlockPlanks.EnumType.DARK_OAK.getMapColor();
/*     */       case Y:
/*     */         break;
/*     */     } 
/*  56 */     return blockplanks$enumtype.getMapColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*  65 */     tab.add(new ItemStack(this, 1, BlockPlanks.EnumType.ACACIA.getMetadata() - 4));
/*  66 */     tab.add(new ItemStack(this, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  74 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.byMetadata((meta & 0x3) + 4));
/*     */     
/*  76 */     switch (meta & 0xC)
/*     */     
/*     */     { case 0:
/*  79 */         iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.Y);
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
/*  94 */         return iblockstate;case 4: iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.X); return iblockstate;case 8: iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.Z); return iblockstate; }  iblockstate = iblockstate.withProperty((IProperty)LOG_AXIS, BlockLog.EnumAxis.NONE); return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 104 */     int i = 0;
/* 105 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata() - 4;
/*     */     
/* 107 */     switch ((BlockLog.EnumAxis)state.getValue((IProperty)LOG_AXIS)) {
/*     */       
/*     */       case X:
/* 110 */         i |= 0x4;
/*     */         break;
/*     */       
/*     */       case Z:
/* 114 */         i |= 0x8;
/*     */         break;
/*     */       
/*     */       case null:
/* 118 */         i |= 0xC;
/*     */         break;
/*     */     } 
/* 121 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 126 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)VARIANT, (IProperty)LOG_AXIS });
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/* 131 */     return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata() - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 140 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata() - 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockNewLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */