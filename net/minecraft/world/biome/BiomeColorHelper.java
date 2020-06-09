/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BiomeColorHelper
/*    */ {
/*  8 */   private static final ColorResolver GRASS_COLOR = new ColorResolver()
/*    */     {
/*    */       public int getColorAtPos(Biome biome, BlockPos blockPosition)
/*    */       {
/* 12 */         return biome.getGrassColorAtPos(blockPosition);
/*    */       }
/*    */     };
/* 15 */   private static final ColorResolver FOLIAGE_COLOR = new ColorResolver()
/*    */     {
/*    */       public int getColorAtPos(Biome biome, BlockPos blockPosition)
/*    */       {
/* 19 */         return biome.getFoliageColorAtPos(blockPosition);
/*    */       }
/*    */     };
/* 22 */   private static final ColorResolver WATER_COLOR = new ColorResolver()
/*    */     {
/*    */       public int getColorAtPos(Biome biome, BlockPos blockPosition)
/*    */       {
/* 26 */         return biome.getWaterColor();
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private static int getColorAtPos(IBlockAccess blockAccess, BlockPos pos, ColorResolver colorResolver) {
/* 32 */     int i = 0;
/* 33 */     int j = 0;
/* 34 */     int k = 0;
/*    */     
/* 36 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
/*    */       
/* 38 */       int l = colorResolver.getColorAtPos(blockAccess.getBiome((BlockPos)blockpos$mutableblockpos), (BlockPos)blockpos$mutableblockpos);
/* 39 */       i += (l & 0xFF0000) >> 16;
/* 40 */       j += (l & 0xFF00) >> 8;
/* 41 */       k += l & 0xFF;
/*    */     } 
/*    */     
/* 44 */     return (i / 9 & 0xFF) << 16 | (j / 9 & 0xFF) << 8 | k / 9 & 0xFF;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getGrassColorAtPos(IBlockAccess blockAccess, BlockPos pos) {
/* 49 */     return getColorAtPos(blockAccess, pos, GRASS_COLOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getFoliageColorAtPos(IBlockAccess blockAccess, BlockPos pos) {
/* 54 */     return getColorAtPos(blockAccess, pos, FOLIAGE_COLOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getWaterColorAtPos(IBlockAccess blockAccess, BlockPos pos) {
/* 59 */     return getColorAtPos(blockAccess, pos, WATER_COLOR);
/*    */   }
/*    */   
/*    */   static interface ColorResolver {
/*    */     int getColorAtPos(Biome param1Biome, BlockPos param1BlockPos);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeColorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */