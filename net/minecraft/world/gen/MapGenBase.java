/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ 
/*    */ 
/*    */ public class MapGenBase
/*    */ {
/* 10 */   protected int range = 8;
/*    */ 
/*    */   
/* 13 */   protected Random rand = new Random();
/*    */ 
/*    */   
/*    */   protected World worldObj;
/*    */ 
/*    */   
/*    */   public void generate(World worldIn, int x, int z, ChunkPrimer primer) {
/* 20 */     int i = this.range;
/* 21 */     this.worldObj = worldIn;
/* 22 */     this.rand.setSeed(worldIn.getSeed());
/* 23 */     long j = this.rand.nextLong();
/* 24 */     long k = this.rand.nextLong();
/*    */     
/* 26 */     for (int l = x - i; l <= x + i; l++) {
/*    */       
/* 28 */       for (int i1 = z - i; i1 <= z + i; i1++) {
/*    */         
/* 30 */         long j1 = l * j;
/* 31 */         long k1 = i1 * k;
/* 32 */         this.rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
/* 33 */         recursiveGenerate(worldIn, l, i1, x, z, primer);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void func_191068_a(long p_191068_0_, Random p_191068_2_, int p_191068_3_, int p_191068_4_) {
/* 40 */     p_191068_2_.setSeed(p_191068_0_);
/* 41 */     long i = p_191068_2_.nextLong();
/* 42 */     long j = p_191068_2_.nextLong();
/* 43 */     long k = p_191068_3_ * i;
/* 44 */     long l = p_191068_4_ * j;
/* 45 */     p_191068_2_.setSeed(k ^ l ^ p_191068_0_);
/*    */   }
/*    */   
/*    */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\MapGenBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */