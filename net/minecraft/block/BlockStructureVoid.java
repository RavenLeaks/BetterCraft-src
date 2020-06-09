/*    */ package net.minecraft.block;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.EnumPushReaction;
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
/*    */ public class BlockStructureVoid
/*    */   extends Block {
/* 17 */   private static final AxisAlignedBB STRUCTURE_VOID_AABB = new AxisAlignedBB(0.3D, 0.3D, 0.3D, 0.7D, 0.7D, 0.7D);
/*    */ 
/*    */   
/*    */   protected BlockStructureVoid() {
/* 21 */     super(Material.STRUCTURE_VOID);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 30 */     return EnumBlockRenderType.INVISIBLE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/* 36 */     return NULL_AABB;
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 41 */     return STRUCTURE_VOID_AABB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube(IBlockState state) {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFullCube(IBlockState state) {
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAmbientOcclusionLightValue(IBlockState state) {
/* 59 */     return 1.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 71 */     return EnumPushReaction.DESTROY;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 76 */     return BlockFaceShape.UNDEFINED;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockStructureVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */