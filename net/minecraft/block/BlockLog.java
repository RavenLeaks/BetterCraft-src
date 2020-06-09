/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockLog extends BlockRotatedPillar {
/*  16 */   public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.create("axis", EnumAxis.class);
/*     */ 
/*     */   
/*     */   public BlockLog() {
/*  20 */     super(Material.WOOD);
/*  21 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*  22 */     setHardness(2.0F);
/*  23 */     setSoundType(SoundType.WOOD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  31 */     int i = 4;
/*  32 */     int j = 5;
/*     */     
/*  34 */     if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
/*     */     {
/*  36 */       for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
/*     */         
/*  38 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */         
/*  40 */         if (iblockstate.getMaterial() == Material.LEAVES && !((Boolean)iblockstate.getValue((IProperty)BlockLeaves.CHECK_DECAY)).booleanValue())
/*     */         {
/*  42 */           worldIn.setBlockState(blockpos, iblockstate.withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(true)), 4);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  54 */     return getStateFromMeta(meta).withProperty((IProperty)LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/*  63 */     switch (rot) {
/*     */       
/*     */       case CLOCKWISE_90:
/*     */       case COUNTERCLOCKWISE_90:
/*  67 */         switch ((EnumAxis)state.getValue((IProperty)LOG_AXIS)) {
/*     */           
/*     */           case X:
/*  70 */             return state.withProperty((IProperty)LOG_AXIS, EnumAxis.Z);
/*     */           
/*     */           case Z:
/*  73 */             return state.withProperty((IProperty)LOG_AXIS, EnumAxis.X);
/*     */         } 
/*     */         
/*  76 */         return state;
/*     */     } 
/*     */ 
/*     */     
/*  80 */     return state;
/*     */   }
/*     */   
/*     */   public enum EnumAxis
/*     */     implements IStringSerializable
/*     */   {
/*  86 */     X("x"),
/*  87 */     Y("y"),
/*  88 */     Z("z"),
/*  89 */     NONE("none");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumAxis(String name) {
/*  95 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 100 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumAxis fromFacingAxis(EnumFacing.Axis axis) {
/* 105 */       switch (axis) {
/*     */         
/*     */         case null:
/* 108 */           return X;
/*     */         
/*     */         case Y:
/* 111 */           return Y;
/*     */         
/*     */         case Z:
/* 114 */           return Z;
/*     */       } 
/*     */       
/* 117 */       return NONE;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 123 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */