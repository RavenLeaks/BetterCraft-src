/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockLilyPad extends BlockBush {
/* 18 */   protected static final AxisAlignedBB LILY_PAD_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.09375D, 0.9375D);
/*    */ 
/*    */   
/*    */   protected BlockLilyPad() {
/* 22 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/* 27 */     if (!(entityIn instanceof net.minecraft.entity.item.EntityBoat))
/*    */     {
/* 29 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, LILY_PAD_AABB);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 38 */     super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
/*    */     
/* 40 */     if (entityIn instanceof net.minecraft.entity.item.EntityBoat)
/*    */     {
/* 42 */       worldIn.destroyBlock(new BlockPos((Vec3i)pos), true);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 48 */     return LILY_PAD_AABB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canSustainBush(IBlockState state) {
/* 56 */     return !(state.getBlock() != Blocks.WATER && state.getMaterial() != Material.ICE);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 61 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*    */       
/* 63 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 64 */       Material material = iblockstate.getMaterial();
/* 65 */       return !((material != Material.WATER || ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() != 0) && material != Material.ICE);
/*    */     } 
/*    */ 
/*    */     
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 78 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockLilyPad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */