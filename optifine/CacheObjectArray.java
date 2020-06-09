/*     */ package optifine;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayDeque;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ 
/*     */ public class CacheObjectArray
/*     */ {
/*   9 */   private static ArrayDeque<int[]> arrays = (ArrayDeque)new ArrayDeque<>();
/*  10 */   private static int maxCacheSize = 10;
/*     */ 
/*     */   
/*     */   private static synchronized int[] allocateArray(int p_allocateArray_0_) {
/*  14 */     int[] aint = arrays.pollLast();
/*     */     
/*  16 */     if (aint == null || aint.length < p_allocateArray_0_)
/*     */     {
/*  18 */       aint = new int[p_allocateArray_0_];
/*     */     }
/*     */     
/*  21 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized void freeArray(int[] p_freeArray_0_) {
/*  26 */     if (arrays.size() < maxCacheSize)
/*     */     {
/*  28 */       arrays.add(p_freeArray_0_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] p_main_0_) throws Exception {
/*  34 */     int i = 4096;
/*  35 */     int j = 500000;
/*  36 */     testNew(i, j);
/*  37 */     testClone(i, j);
/*  38 */     testNewObj(i, j);
/*  39 */     testCloneObj(i, j);
/*  40 */     testNewObjDyn(IBlockState.class, i, j);
/*  41 */     long k = testNew(i, j);
/*  42 */     long l = testClone(i, j);
/*  43 */     long i1 = testNewObj(i, j);
/*  44 */     long j1 = testCloneObj(i, j);
/*  45 */     long k1 = testNewObjDyn(IBlockState.class, i, j);
/*  46 */     Config.dbg("New: " + k);
/*  47 */     Config.dbg("Clone: " + l);
/*  48 */     Config.dbg("NewObj: " + i1);
/*  49 */     Config.dbg("CloneObj: " + j1);
/*  50 */     Config.dbg("NewObjDyn: " + k1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testClone(int p_testClone_0_, int p_testClone_1_) {
/*  55 */     long i = System.currentTimeMillis();
/*  56 */     int[] aint = new int[p_testClone_0_];
/*     */     
/*  58 */     for (int j = 0; j < p_testClone_1_; j++)
/*     */     {
/*  60 */       int[] arrayOfInt = (int[])aint.clone();
/*     */     }
/*     */     
/*  63 */     long k = System.currentTimeMillis();
/*  64 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testNew(int p_testNew_0_, int p_testNew_1_) {
/*  69 */     long i = System.currentTimeMillis();
/*     */     
/*  71 */     for (int j = 0; j < p_testNew_1_; j++)
/*     */     {
/*  73 */       int[] arrayOfInt = (int[])Array.newInstance(int.class, p_testNew_0_);
/*     */     }
/*     */     
/*  76 */     long k = System.currentTimeMillis();
/*  77 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testCloneObj(int p_testCloneObj_0_, int p_testCloneObj_1_) {
/*  82 */     long i = System.currentTimeMillis();
/*  83 */     IBlockState[] aiblockstate = new IBlockState[p_testCloneObj_0_];
/*     */     
/*  85 */     for (int j = 0; j < p_testCloneObj_1_; j++)
/*     */     {
/*  87 */       IBlockState[] arrayOfIBlockState = (IBlockState[])aiblockstate.clone();
/*     */     }
/*     */     
/*  90 */     long k = System.currentTimeMillis();
/*  91 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testNewObj(int p_testNewObj_0_, int p_testNewObj_1_) {
/*  96 */     long i = System.currentTimeMillis();
/*     */     
/*  98 */     for (int j = 0; j < p_testNewObj_1_; j++)
/*     */     {
/* 100 */       IBlockState[] arrayOfIBlockState = new IBlockState[p_testNewObj_0_];
/*     */     }
/*     */     
/* 103 */     long k = System.currentTimeMillis();
/* 104 */     return k - i;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long testNewObjDyn(Class<?> p_testNewObjDyn_0_, int p_testNewObjDyn_1_, int p_testNewObjDyn_2_) {
/* 109 */     long i = System.currentTimeMillis();
/*     */     
/* 111 */     for (int j = 0; j < p_testNewObjDyn_2_; j++)
/*     */     {
/* 113 */       Object[] arrayOfObject = (Object[])Array.newInstance(p_testNewObjDyn_0_, p_testNewObjDyn_1_);
/*     */     }
/*     */     
/* 116 */     long k = System.currentTimeMillis();
/* 117 */     return k - i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CacheObjectArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */