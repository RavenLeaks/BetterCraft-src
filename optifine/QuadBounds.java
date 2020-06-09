/*     */ package optifine;
/*     */ 
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class QuadBounds
/*     */ {
/*   7 */   private float minX = Float.MAX_VALUE;
/*   8 */   private float minY = Float.MAX_VALUE;
/*   9 */   private float minZ = Float.MAX_VALUE;
/*  10 */   private float maxX = -3.4028235E38F;
/*  11 */   private float maxY = -3.4028235E38F;
/*  12 */   private float maxZ = -3.4028235E38F;
/*     */ 
/*     */   
/*     */   public QuadBounds(int[] p_i76_1_) {
/*  16 */     int i = p_i76_1_.length / 4;
/*     */     
/*  18 */     for (int j = 0; j < 4; j++) {
/*     */       
/*  20 */       int k = j * i;
/*  21 */       float f = Float.intBitsToFloat(p_i76_1_[k + 0]);
/*  22 */       float f1 = Float.intBitsToFloat(p_i76_1_[k + 1]);
/*  23 */       float f2 = Float.intBitsToFloat(p_i76_1_[k + 2]);
/*     */       
/*  25 */       if (this.minX > f)
/*     */       {
/*  27 */         this.minX = f;
/*     */       }
/*     */       
/*  30 */       if (this.minY > f1)
/*     */       {
/*  32 */         this.minY = f1;
/*     */       }
/*     */       
/*  35 */       if (this.minZ > f2)
/*     */       {
/*  37 */         this.minZ = f2;
/*     */       }
/*     */       
/*  40 */       if (this.maxX < f)
/*     */       {
/*  42 */         this.maxX = f;
/*     */       }
/*     */       
/*  45 */       if (this.maxY < f1)
/*     */       {
/*  47 */         this.maxY = f1;
/*     */       }
/*     */       
/*  50 */       if (this.maxZ < f2)
/*     */       {
/*  52 */         this.maxZ = f2;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinX() {
/*  59 */     return this.minX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinY() {
/*  64 */     return this.minY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMinZ() {
/*  69 */     return this.minZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxX() {
/*  74 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxY() {
/*  79 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxZ() {
/*  84 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFaceQuad(EnumFacing p_isFaceQuad_1_) {
/*     */     float f;
/*     */     float f1;
/*     */     float f2;
/*  93 */     switch (p_isFaceQuad_1_) {
/*     */       
/*     */       case null:
/*  96 */         f = getMinY();
/*  97 */         f1 = getMaxY();
/*  98 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case UP:
/* 102 */         f = getMinY();
/* 103 */         f1 = getMaxY();
/* 104 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 108 */         f = getMinZ();
/* 109 */         f1 = getMaxZ();
/* 110 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 114 */         f = getMinZ();
/* 115 */         f1 = getMaxZ();
/* 116 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 120 */         f = getMinX();
/* 121 */         f1 = getMaxX();
/* 122 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case EAST:
/* 126 */         f = getMinX();
/* 127 */         f1 = getMaxX();
/* 128 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       default:
/* 132 */         return false;
/*     */     } 
/*     */     
/* 135 */     return (f == f2 && f1 == f2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullQuad(EnumFacing p_isFullQuad_1_) {
/*     */     float f;
/*     */     float f1;
/*     */     float f2;
/*     */     float f3;
/* 145 */     switch (p_isFullQuad_1_) {
/*     */       
/*     */       case null:
/*     */       case UP:
/* 149 */         f = getMinX();
/* 150 */         f1 = getMaxX();
/* 151 */         f2 = getMinZ();
/* 152 */         f3 = getMaxZ();
/*     */         break;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 157 */         f = getMinX();
/* 158 */         f1 = getMaxX();
/* 159 */         f2 = getMinY();
/* 160 */         f3 = getMaxY();
/*     */         break;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 165 */         f = getMinY();
/* 166 */         f1 = getMaxY();
/* 167 */         f2 = getMinZ();
/* 168 */         f3 = getMaxZ();
/*     */         break;
/*     */       
/*     */       default:
/* 172 */         return false;
/*     */     } 
/*     */     
/* 175 */     return (f == 0.0F && f1 == 1.0F && f2 == 0.0F && f3 == 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\QuadBounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */