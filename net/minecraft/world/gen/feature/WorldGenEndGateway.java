/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenEndGateway
/*    */   extends WorldGenerator {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 12 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(position.add(-1, -2, -1), position.add(1, 2, 1))) {
/*    */       
/* 14 */       boolean flag = (blockpos$mutableblockpos.getX() == position.getX());
/* 15 */       boolean flag1 = (blockpos$mutableblockpos.getY() == position.getY());
/* 16 */       boolean flag2 = (blockpos$mutableblockpos.getZ() == position.getZ());
/* 17 */       boolean flag3 = (Math.abs(blockpos$mutableblockpos.getY() - position.getY()) == 2);
/*    */       
/* 19 */       if (flag && flag1 && flag2) {
/*    */         
/* 21 */         setBlockAndNotifyAdequately(worldIn, new BlockPos((Vec3i)blockpos$mutableblockpos), Blocks.END_GATEWAY.getDefaultState()); continue;
/*    */       } 
/* 23 */       if (flag1) {
/*    */         
/* 25 */         setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.AIR.getDefaultState()); continue;
/*    */       } 
/* 27 */       if (flag3 && flag && flag2) {
/*    */         
/* 29 */         setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState()); continue;
/*    */       } 
/* 31 */       if ((flag || flag2) && !flag3) {
/*    */         
/* 33 */         setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState());
/*    */         
/*    */         continue;
/*    */       } 
/* 37 */       setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.AIR.getDefaultState());
/*    */     } 
/*    */ 
/*    */     
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenEndGateway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */