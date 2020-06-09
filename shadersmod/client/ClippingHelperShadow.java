/*     */ package shadersmod.client;
/*     */ 
/*     */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class ClippingHelperShadow
/*     */   extends ClippingHelper {
/*   8 */   private static ClippingHelperShadow instance = new ClippingHelperShadow();
/*   9 */   float[] frustumTest = new float[6];
/*  10 */   float[][] shadowClipPlanes = new float[10][4];
/*     */   int shadowClipPlaneCount;
/*  12 */   float[] matInvMP = new float[16];
/*  13 */   float[] vecIntersection = new float[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBoxInFrustum(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  20 */     for (int i = 0; i < this.shadowClipPlaneCount; i++) {
/*     */       
/*  22 */       float[] afloat = this.shadowClipPlanes[i];
/*     */       
/*  24 */       if (dot4(afloat, x1, y1, z1) <= 0.0D && dot4(afloat, x2, y1, z1) <= 0.0D && dot4(afloat, x1, y2, z1) <= 0.0D && dot4(afloat, x2, y2, z1) <= 0.0D && dot4(afloat, x1, y1, z2) <= 0.0D && dot4(afloat, x2, y1, z2) <= 0.0D && dot4(afloat, x1, y2, z2) <= 0.0D && dot4(afloat, x2, y2, z2) <= 0.0D)
/*     */       {
/*  26 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private double dot4(float[] plane, double x, double y, double z) {
/*  35 */     return plane[0] * x + plane[1] * y + plane[2] * z + plane[3];
/*     */   }
/*     */ 
/*     */   
/*     */   private double dot3(float[] vecA, float[] vecB) {
/*  40 */     return vecA[0] * vecB[0] + vecA[1] * vecB[1] + vecA[2] * vecB[2];
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClippingHelper getInstance() {
/*  45 */     instance.init();
/*  46 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private void normalizePlane(float[] plane) {
/*  51 */     float f = MathHelper.sqrt(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
/*  52 */     plane[0] = plane[0] / f;
/*  53 */     plane[1] = plane[1] / f;
/*  54 */     plane[2] = plane[2] / f;
/*  55 */     plane[3] = plane[3] / f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void normalize3(float[] plane) {
/*  60 */     float f = MathHelper.sqrt(plane[0] * plane[0] + plane[1] * plane[1] + plane[2] * plane[2]);
/*     */     
/*  62 */     if (f == 0.0F)
/*     */     {
/*  64 */       f = 1.0F;
/*     */     }
/*     */     
/*  67 */     plane[0] = plane[0] / f;
/*  68 */     plane[1] = plane[1] / f;
/*  69 */     plane[2] = plane[2] / f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void assignPlane(float[] plane, float a, float b, float c, float d) {
/*  74 */     float f = (float)Math.sqrt((a * a + b * b + c * c));
/*  75 */     plane[0] = a / f;
/*  76 */     plane[1] = b / f;
/*  77 */     plane[2] = c / f;
/*  78 */     plane[3] = d / f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyPlane(float[] dst, float[] src) {
/*  83 */     dst[0] = src[0];
/*  84 */     dst[1] = src[1];
/*  85 */     dst[2] = src[2];
/*  86 */     dst[3] = src[3];
/*     */   }
/*     */ 
/*     */   
/*     */   private void cross3(float[] out, float[] a, float[] b) {
/*  91 */     out[0] = a[1] * b[2] - a[2] * b[1];
/*  92 */     out[1] = a[2] * b[0] - a[0] * b[2];
/*  93 */     out[2] = a[0] * b[1] - a[1] * b[0];
/*     */   }
/*     */ 
/*     */   
/*     */   private void addShadowClipPlane(float[] plane) {
/*  98 */     copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], plane);
/*     */   }
/*     */ 
/*     */   
/*     */   private float length(float x, float y, float z) {
/* 103 */     return (float)Math.sqrt((x * x + y * y + z * z));
/*     */   }
/*     */ 
/*     */   
/*     */   private float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
/* 108 */     return length(x1 - x2, y1 - y2, z1 - z2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void makeShadowPlane(float[] shadowPlane, float[] positivePlane, float[] negativePlane, float[] vecSun) {
/* 113 */     cross3(this.vecIntersection, positivePlane, negativePlane);
/* 114 */     cross3(shadowPlane, this.vecIntersection, vecSun);
/* 115 */     normalize3(shadowPlane);
/* 116 */     float f = (float)dot3(positivePlane, negativePlane);
/* 117 */     float f1 = (float)dot3(shadowPlane, negativePlane);
/* 118 */     float f2 = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], negativePlane[0] * f1, negativePlane[1] * f1, negativePlane[2] * f1);
/* 119 */     float f3 = distance(positivePlane[0], positivePlane[1], positivePlane[2], negativePlane[0] * f, negativePlane[1] * f, negativePlane[2] * f);
/* 120 */     float f4 = f2 / f3;
/* 121 */     float f5 = (float)dot3(shadowPlane, positivePlane);
/* 122 */     float f6 = distance(shadowPlane[0], shadowPlane[1], shadowPlane[2], positivePlane[0] * f5, positivePlane[1] * f5, positivePlane[2] * f5);
/* 123 */     float f7 = distance(negativePlane[0], negativePlane[1], negativePlane[2], positivePlane[0] * f, positivePlane[1] * f, positivePlane[2] * f);
/* 124 */     float f8 = f6 / f7;
/* 125 */     shadowPlane[3] = positivePlane[3] * f4 + negativePlane[3] * f8;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 130 */     float[] afloat = this.projectionMatrix;
/* 131 */     float[] afloat1 = this.modelviewMatrix;
/* 132 */     float[] afloat2 = this.clippingMatrix;
/* 133 */     System.arraycopy(Shaders.faProjection, 0, afloat, 0, 16);
/* 134 */     System.arraycopy(Shaders.faModelView, 0, afloat1, 0, 16);
/* 135 */     SMath.multiplyMat4xMat4(afloat2, afloat1, afloat);
/* 136 */     assignPlane(this.frustum[0], afloat2[3] - afloat2[0], afloat2[7] - afloat2[4], afloat2[11] - afloat2[8], afloat2[15] - afloat2[12]);
/* 137 */     assignPlane(this.frustum[1], afloat2[3] + afloat2[0], afloat2[7] + afloat2[4], afloat2[11] + afloat2[8], afloat2[15] + afloat2[12]);
/* 138 */     assignPlane(this.frustum[2], afloat2[3] + afloat2[1], afloat2[7] + afloat2[5], afloat2[11] + afloat2[9], afloat2[15] + afloat2[13]);
/* 139 */     assignPlane(this.frustum[3], afloat2[3] - afloat2[1], afloat2[7] - afloat2[5], afloat2[11] - afloat2[9], afloat2[15] - afloat2[13]);
/* 140 */     assignPlane(this.frustum[4], afloat2[3] - afloat2[2], afloat2[7] - afloat2[6], afloat2[11] - afloat2[10], afloat2[15] - afloat2[14]);
/* 141 */     assignPlane(this.frustum[5], afloat2[3] + afloat2[2], afloat2[7] + afloat2[6], afloat2[11] + afloat2[10], afloat2[15] + afloat2[14]);
/* 142 */     float[] afloat3 = Shaders.shadowLightPositionVector;
/* 143 */     float f = (float)dot3(this.frustum[0], afloat3);
/* 144 */     float f1 = (float)dot3(this.frustum[1], afloat3);
/* 145 */     float f2 = (float)dot3(this.frustum[2], afloat3);
/* 146 */     float f3 = (float)dot3(this.frustum[3], afloat3);
/* 147 */     float f4 = (float)dot3(this.frustum[4], afloat3);
/* 148 */     float f5 = (float)dot3(this.frustum[5], afloat3);
/* 149 */     this.shadowClipPlaneCount = 0;
/*     */     
/* 151 */     if (f >= 0.0F) {
/*     */       
/* 153 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0]);
/*     */       
/* 155 */       if (f > 0.0F) {
/*     */         
/* 157 */         if (f2 < 0.0F)
/*     */         {
/* 159 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 162 */         if (f3 < 0.0F)
/*     */         {
/* 164 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[3], afloat3);
/*     */         }
/*     */         
/* 167 */         if (f4 < 0.0F)
/*     */         {
/* 169 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 172 */         if (f5 < 0.0F)
/*     */         {
/* 174 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[0], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     if (f1 >= 0.0F) {
/*     */       
/* 181 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1]);
/*     */       
/* 183 */       if (f1 > 0.0F) {
/*     */         
/* 185 */         if (f2 < 0.0F)
/*     */         {
/* 187 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 190 */         if (f3 < 0.0F)
/*     */         {
/* 192 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[3], afloat3);
/*     */         }
/*     */         
/* 195 */         if (f4 < 0.0F)
/*     */         {
/* 197 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 200 */         if (f5 < 0.0F)
/*     */         {
/* 202 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[1], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     if (f2 >= 0.0F) {
/*     */       
/* 209 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2]);
/*     */       
/* 211 */       if (f2 > 0.0F) {
/*     */         
/* 213 */         if (f < 0.0F)
/*     */         {
/* 215 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 218 */         if (f1 < 0.0F)
/*     */         {
/* 220 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 223 */         if (f4 < 0.0F)
/*     */         {
/* 225 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 228 */         if (f5 < 0.0F)
/*     */         {
/* 230 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[2], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     if (f3 >= 0.0F) {
/*     */       
/* 237 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3]);
/*     */       
/* 239 */       if (f3 > 0.0F) {
/*     */         
/* 241 */         if (f < 0.0F)
/*     */         {
/* 243 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 246 */         if (f1 < 0.0F)
/*     */         {
/* 248 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 251 */         if (f4 < 0.0F)
/*     */         {
/* 253 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[4], afloat3);
/*     */         }
/*     */         
/* 256 */         if (f5 < 0.0F)
/*     */         {
/* 258 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[3], this.frustum[5], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     if (f4 >= 0.0F) {
/*     */       
/* 265 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4]);
/*     */       
/* 267 */       if (f4 > 0.0F) {
/*     */         
/* 269 */         if (f < 0.0F)
/*     */         {
/* 271 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 274 */         if (f1 < 0.0F)
/*     */         {
/* 276 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 279 */         if (f2 < 0.0F)
/*     */         {
/* 281 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 284 */         if (f3 < 0.0F)
/*     */         {
/* 286 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[4], this.frustum[3], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     if (f5 >= 0.0F) {
/*     */       
/* 293 */       copyPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5]);
/*     */       
/* 295 */       if (f5 > 0.0F) {
/*     */         
/* 297 */         if (f < 0.0F)
/*     */         {
/* 299 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[0], afloat3);
/*     */         }
/*     */         
/* 302 */         if (f1 < 0.0F)
/*     */         {
/* 304 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[1], afloat3);
/*     */         }
/*     */         
/* 307 */         if (f2 < 0.0F)
/*     */         {
/* 309 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[2], afloat3);
/*     */         }
/*     */         
/* 312 */         if (f3 < 0.0F)
/*     */         {
/* 314 */           makeShadowPlane(this.shadowClipPlanes[this.shadowClipPlaneCount++], this.frustum[5], this.frustum[3], afloat3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ClippingHelperShadow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */