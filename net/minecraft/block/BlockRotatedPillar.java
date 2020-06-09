/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRotatedPillar
/*     */   extends Block {
/*  19 */   public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);
/*     */ 
/*     */   
/*     */   protected BlockRotatedPillar(Material materialIn) {
/*  23 */     super(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockRotatedPillar(Material materialIn, MapColor color) {
/*  28 */     super(materialIn, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/*  37 */     switch (rot) {
/*     */       
/*     */       case CLOCKWISE_90:
/*     */       case COUNTERCLOCKWISE_90:
/*  41 */         switch ((EnumFacing.Axis)state.getValue((IProperty)AXIS)) {
/*     */           
/*     */           case null:
/*  44 */             return state.withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.Z);
/*     */           
/*     */           case Z:
/*  47 */             return state.withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.X);
/*     */         } 
/*     */         
/*  50 */         return state;
/*     */     } 
/*     */ 
/*     */     
/*  54 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  63 */     EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
/*  64 */     int i = meta & 0xC;
/*     */     
/*  66 */     if (i == 4) {
/*     */       
/*  68 */       enumfacing$axis = EnumFacing.Axis.X;
/*     */     }
/*  70 */     else if (i == 8) {
/*     */       
/*  72 */       enumfacing$axis = EnumFacing.Axis.Z;
/*     */     } 
/*     */     
/*  75 */     return getDefaultState().withProperty((IProperty)AXIS, (Comparable)enumfacing$axis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  83 */     int i = 0;
/*  84 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue((IProperty)AXIS);
/*     */     
/*  86 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/*     */       
/*  88 */       i |= 0x4;
/*     */     }
/*  90 */     else if (enumfacing$axis == EnumFacing.Axis.Z) {
/*     */       
/*  92 */       i |= 0x8;
/*     */     } 
/*     */     
/*  95 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 100 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AXIS });
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/* 105 */     return new ItemStack(Item.getItemFromBlock(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 114 */     return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)AXIS, (Comparable)facing.getAxis());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRotatedPillar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */