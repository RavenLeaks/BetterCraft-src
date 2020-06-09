/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockAir;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ 
/*    */ 
/*    */ public class ClearWater
/*    */ {
/*    */   public static void updateWaterOpacity(GameSettings p_updateWaterOpacity_0_, World p_updateWaterOpacity_1_) {
/* 19 */     if (p_updateWaterOpacity_0_ != null) {
/*    */       
/* 21 */       int i = 3;
/*    */       
/* 23 */       if (p_updateWaterOpacity_0_.ofClearWater)
/*    */       {
/* 25 */         i = 1;
/*    */       }
/*    */       
/* 28 */       BlockAir.setLightOpacity((Block)Blocks.WATER, i);
/* 29 */       BlockAir.setLightOpacity((Block)Blocks.FLOWING_WATER, i);
/*    */     } 
/*    */     
/* 32 */     if (p_updateWaterOpacity_1_ != null) {
/*    */       
/* 34 */       IChunkProvider ichunkprovider = p_updateWaterOpacity_1_.getChunkProvider();
/*    */       
/* 36 */       if (ichunkprovider != null) {
/*    */         
/* 38 */         Entity entity = Config.getMinecraft().getRenderViewEntity();
/*    */         
/* 40 */         if (entity != null) {
/*    */           
/* 42 */           int j = (int)entity.posX / 16;
/* 43 */           int k = (int)entity.posZ / 16;
/* 44 */           int l = j - 512;
/* 45 */           int i1 = j + 512;
/* 46 */           int j1 = k - 512;
/* 47 */           int k1 = k + 512;
/* 48 */           int l1 = 0;
/*    */           
/* 50 */           for (int i2 = l; i2 < i1; i2++) {
/*    */             
/* 52 */             for (int j2 = j1; j2 < k1; j2++) {
/*    */               
/* 54 */               Chunk chunk = ichunkprovider.getLoadedChunk(i2, j2);
/*    */               
/* 56 */               if (chunk != null && !(chunk instanceof net.minecraft.world.chunk.EmptyChunk)) {
/*    */                 
/* 58 */                 int k2 = i2 << 4;
/* 59 */                 int l2 = j2 << 4;
/* 60 */                 int i3 = k2 + 16;
/* 61 */                 int j3 = l2 + 16;
/* 62 */                 BlockPosM blockposm = new BlockPosM(0, 0, 0);
/* 63 */                 BlockPosM blockposm1 = new BlockPosM(0, 0, 0);
/*    */                 
/* 65 */                 for (int k3 = k2; k3 < i3; k3++) {
/*    */                   
/* 67 */                   for (int l3 = l2; l3 < j3; l3++) {
/*    */                     
/* 69 */                     blockposm.setXyz(k3, 0, l3);
/* 70 */                     BlockPos blockpos = p_updateWaterOpacity_1_.getPrecipitationHeight(blockposm);
/*    */                     
/* 72 */                     for (int i4 = 0; i4 < blockpos.getY(); i4++) {
/*    */                       
/* 74 */                       blockposm1.setXyz(k3, i4, l3);
/* 75 */                       IBlockState iblockstate = p_updateWaterOpacity_1_.getBlockState(blockposm1);
/*    */                       
/* 77 */                       if (iblockstate.getMaterial() == Material.WATER) {
/*    */                         
/* 79 */                         p_updateWaterOpacity_1_.markBlocksDirtyVertical(k3, l3, blockposm1.getY(), blockpos.getY());
/* 80 */                         l1++;
/*    */                         
/*    */                         break;
/*    */                       } 
/*    */                     } 
/*    */                   } 
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */           } 
/* 90 */           if (l1 > 0) {
/*    */             
/* 92 */             String s = "server";
/*    */             
/* 94 */             if (Config.isMinecraftThread())
/*    */             {
/* 96 */               s = "client";
/*    */             }
/*    */             
/* 99 */             Config.dbg("ClearWater (" + s + ") relighted " + l1 + " chunks");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ClearWater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */