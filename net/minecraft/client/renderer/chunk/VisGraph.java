/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.BitSet;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class VisGraph
/*     */ {
/*  13 */   private static final int DX = (int)Math.pow(16.0D, 0.0D);
/*  14 */   private static final int DZ = (int)Math.pow(16.0D, 1.0D);
/*  15 */   private static final int DY = (int)Math.pow(16.0D, 2.0D);
/*  16 */   private final BitSet bitSet = new BitSet(4096);
/*  17 */   private static final int[] INDEX_OF_EDGES = new int[1352];
/*  18 */   private int empty = 4096;
/*     */ 
/*     */   
/*     */   public void setOpaqueCube(BlockPos pos) {
/*  22 */     this.bitSet.set(getIndex(pos), true);
/*  23 */     this.empty--;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(BlockPos pos) {
/*  28 */     return getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(int x, int y, int z) {
/*  33 */     return x << 0 | y << 8 | z << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public SetVisibility computeVisibility() {
/*  38 */     SetVisibility setvisibility = new SetVisibility();
/*     */     
/*  40 */     if (4096 - this.empty < 256) {
/*     */       
/*  42 */       setvisibility.setAllVisible(true);
/*     */     }
/*  44 */     else if (this.empty == 0) {
/*     */       
/*  46 */       setvisibility.setAllVisible(false);
/*     */     } else {
/*     */       byte b; int i;
/*     */       int[] arrayOfInt;
/*  50 */       for (i = (arrayOfInt = INDEX_OF_EDGES).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*     */         
/*  52 */         if (!this.bitSet.get(j))
/*     */         {
/*  54 */           setvisibility.setManyVisible(floodFill(j));
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/*  59 */     return setvisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EnumFacing> getVisibleFacings(BlockPos pos) {
/*  64 */     return floodFill(getIndex(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<EnumFacing> floodFill(int p_178604_1_) {
/*  69 */     Set<EnumFacing> set = EnumSet.noneOf(EnumFacing.class);
/*  70 */     ArrayDeque<Integer> arraydeque = new ArrayDeque(384);
/*  71 */     arraydeque.add(IntegerCache.getInteger(p_178604_1_));
/*  72 */     this.bitSet.set(p_178604_1_, true);
/*     */     
/*  74 */     while (!arraydeque.isEmpty()) {
/*     */       
/*  76 */       int i = ((Integer)arraydeque.poll()).intValue();
/*  77 */       addEdges(i, set); byte b; int j;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  79 */       for (j = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/*  81 */         int k = getNeighborIndexAtFace(i, enumfacing);
/*     */         
/*  83 */         if (k >= 0 && !this.bitSet.get(k)) {
/*     */           
/*  85 */           this.bitSet.set(k, true);
/*  86 */           arraydeque.add(IntegerCache.getInteger(k));
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*  91 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addEdges(int p_178610_1_, Set<EnumFacing> p_178610_2_) {
/*  96 */     int i = p_178610_1_ >> 0 & 0xF;
/*     */     
/*  98 */     if (i == 0) {
/*     */       
/* 100 */       p_178610_2_.add(EnumFacing.WEST);
/*     */     }
/* 102 */     else if (i == 15) {
/*     */       
/* 104 */       p_178610_2_.add(EnumFacing.EAST);
/*     */     } 
/*     */     
/* 107 */     int j = p_178610_1_ >> 8 & 0xF;
/*     */     
/* 109 */     if (j == 0) {
/*     */       
/* 111 */       p_178610_2_.add(EnumFacing.DOWN);
/*     */     }
/* 113 */     else if (j == 15) {
/*     */       
/* 115 */       p_178610_2_.add(EnumFacing.UP);
/*     */     } 
/*     */     
/* 118 */     int k = p_178610_1_ >> 4 & 0xF;
/*     */     
/* 120 */     if (k == 0) {
/*     */       
/* 122 */       p_178610_2_.add(EnumFacing.NORTH);
/*     */     }
/* 124 */     else if (k == 15) {
/*     */       
/* 126 */       p_178610_2_.add(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNeighborIndexAtFace(int p_178603_1_, EnumFacing p_178603_2_) {
/* 132 */     switch (p_178603_2_) {
/*     */       
/*     */       case null:
/* 135 */         if ((p_178603_1_ >> 8 & 0xF) == 0)
/*     */         {
/* 137 */           return -1;
/*     */         }
/*     */         
/* 140 */         return p_178603_1_ - DY;
/*     */       
/*     */       case UP:
/* 143 */         if ((p_178603_1_ >> 8 & 0xF) == 15)
/*     */         {
/* 145 */           return -1;
/*     */         }
/*     */         
/* 148 */         return p_178603_1_ + DY;
/*     */       
/*     */       case NORTH:
/* 151 */         if ((p_178603_1_ >> 4 & 0xF) == 0)
/*     */         {
/* 153 */           return -1;
/*     */         }
/*     */         
/* 156 */         return p_178603_1_ - DZ;
/*     */       
/*     */       case SOUTH:
/* 159 */         if ((p_178603_1_ >> 4 & 0xF) == 15)
/*     */         {
/* 161 */           return -1;
/*     */         }
/*     */         
/* 164 */         return p_178603_1_ + DZ;
/*     */       
/*     */       case WEST:
/* 167 */         if ((p_178603_1_ >> 0 & 0xF) == 0)
/*     */         {
/* 169 */           return -1;
/*     */         }
/*     */         
/* 172 */         return p_178603_1_ - DX;
/*     */       
/*     */       case EAST:
/* 175 */         if ((p_178603_1_ >> 0 & 0xF) == 15)
/*     */         {
/* 177 */           return -1;
/*     */         }
/*     */         
/* 180 */         return p_178603_1_ + DX;
/*     */     } 
/*     */     
/* 183 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 189 */     int i = 0;
/* 190 */     int j = 15;
/* 191 */     int k = 0;
/*     */     
/* 193 */     for (int l = 0; l < 16; l++) {
/*     */       
/* 195 */       for (int i1 = 0; i1 < 16; i1++) {
/*     */         
/* 197 */         for (int j1 = 0; j1 < 16; j1++) {
/*     */           
/* 199 */           if (l == 0 || l == 15 || i1 == 0 || i1 == 15 || j1 == 0 || j1 == 15)
/*     */           {
/* 201 */             INDEX_OF_EDGES[k++] = getIndex(l, i1, j1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\chunk\VisGraph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */