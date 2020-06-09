/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyInteger;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockBeetroot
/*    */   extends BlockCrops {
/* 17 */   public static final PropertyInteger BEETROOT_AGE = PropertyInteger.create("age", 0, 3);
/* 18 */   private static final AxisAlignedBB[] BEETROOT_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D) };
/*    */ 
/*    */   
/*    */   protected PropertyInteger getAgeProperty() {
/* 22 */     return BEETROOT_AGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxAge() {
/* 27 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getSeed() {
/* 32 */     return Items.BEETROOT_SEEDS;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getCrop() {
/* 37 */     return Items.BEETROOT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 42 */     if (rand.nextInt(3) == 0) {
/*    */       
/* 44 */       checkAndDropBlock(worldIn, pos, state);
/*    */     }
/*    */     else {
/*    */       
/* 48 */       super.updateTick(worldIn, pos, state, rand);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBonemealAgeIncrease(World worldIn) {
/* 54 */     return super.getBonemealAgeIncrease(worldIn) / 3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateContainer createBlockState() {
/* 59 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)BEETROOT_AGE });
/*    */   }
/*    */ 
/*    */   
/*    */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 64 */     return BEETROOT_AABB[((Integer)state.getValue((IProperty)getAgeProperty())).intValue()];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBeetroot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */