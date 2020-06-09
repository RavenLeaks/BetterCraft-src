/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.BlockFaceShape;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockAir
/*    */   extends Block {
/* 18 */   private static Map mapOriginalOpacity = new IdentityHashMap<>();
/*    */ 
/*    */   
/*    */   protected BlockAir() {
/* 22 */     super(Material.AIR);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 31 */     return EnumBlockRenderType.INVISIBLE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/* 37 */     return NULL_AABB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube(IBlockState state) {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/* 65 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFullCube(IBlockState state) {
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setLightOpacity(Block p_setLightOpacity_0_, int p_setLightOpacity_1_) {
/* 75 */     if (!mapOriginalOpacity.containsKey(p_setLightOpacity_0_))
/*    */     {
/* 77 */       mapOriginalOpacity.put(p_setLightOpacity_0_, Integer.valueOf(p_setLightOpacity_0_.lightOpacity));
/*    */     }
/*    */     
/* 80 */     p_setLightOpacity_0_.lightOpacity = p_setLightOpacity_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void restoreLightOpacity(Block p_restoreLightOpacity_0_) {
/* 85 */     if (mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
/*    */       
/* 87 */       int i = ((Integer)mapOriginalOpacity.get(p_restoreLightOpacity_0_)).intValue();
/* 88 */       setLightOpacity(p_restoreLightOpacity_0_, i);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 94 */     return BlockFaceShape.UNDEFINED;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockAir.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */