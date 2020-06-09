/*    */ package net.minecraft.world.gen.feature;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockTorch;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenEndPodium extends WorldGenerator {
/* 12 */   public static final BlockPos END_PODIUM_LOCATION = BlockPos.ORIGIN;
/* 13 */   public static final BlockPos END_PODIUM_CHUNK_POS = new BlockPos(END_PODIUM_LOCATION.getX() - 4 & 0xFFFFFFF0, 0, END_PODIUM_LOCATION.getZ() - 4 & 0xFFFFFFF0);
/*    */   
/*    */   private final boolean activePortal;
/*    */   
/*    */   public WorldGenEndPodium(boolean activePortalIn) {
/* 18 */     this.activePortal = activePortalIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 23 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(new BlockPos(position.getX() - 4, position.getY() - 1, position.getZ() - 4), new BlockPos(position.getX() + 4, position.getY() + 32, position.getZ() + 4))) {
/*    */       
/* 25 */       double d0 = blockpos$mutableblockpos.getDistance(position.getX(), blockpos$mutableblockpos.getY(), position.getZ());
/*    */       
/* 27 */       if (d0 <= 3.5D) {
/*    */         
/* 29 */         if (blockpos$mutableblockpos.getY() < position.getY()) {
/*    */           
/* 31 */           if (d0 <= 2.5D) {
/*    */             
/* 33 */             setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState()); continue;
/*    */           } 
/* 35 */           if (blockpos$mutableblockpos.getY() < position.getY())
/*    */           {
/* 37 */             setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.END_STONE.getDefaultState()); } 
/*    */           continue;
/*    */         } 
/* 40 */         if (blockpos$mutableblockpos.getY() > position.getY()) {
/*    */           
/* 42 */           setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.AIR.getDefaultState()); continue;
/*    */         } 
/* 44 */         if (d0 > 2.5D) {
/*    */           
/* 46 */           setBlockAndNotifyAdequately(worldIn, (BlockPos)blockpos$mutableblockpos, Blocks.BEDROCK.getDefaultState()); continue;
/*    */         } 
/* 48 */         if (this.activePortal) {
/*    */           
/* 50 */           setBlockAndNotifyAdequately(worldIn, new BlockPos((Vec3i)blockpos$mutableblockpos), Blocks.END_PORTAL.getDefaultState());
/*    */           
/*    */           continue;
/*    */         } 
/* 54 */         setBlockAndNotifyAdequately(worldIn, new BlockPos((Vec3i)blockpos$mutableblockpos), Blocks.AIR.getDefaultState());
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 59 */     for (int i = 0; i < 4; i++)
/*    */     {
/* 61 */       setBlockAndNotifyAdequately(worldIn, position.up(i), Blocks.BEDROCK.getDefaultState());
/*    */     }
/*    */     
/* 64 */     BlockPos blockpos = position.up(2);
/*    */     
/* 66 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*    */     {
/* 68 */       setBlockAndNotifyAdequately(worldIn, blockpos.offset(enumfacing), Blocks.TORCH.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)enumfacing));
/*    */     }
/*    */     
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenEndPodium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */