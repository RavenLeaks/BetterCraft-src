/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point4d
/*     */   extends Tuple4d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 1733471895962736949L;
/*     */   
/*     */   public Point4d(double x, double y, double z, double w) {
/*  50 */     super(x, y, z, w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(double[] p) {
/*  60 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Point4d p1) {
/*  70 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Point4f p1) {
/*  80 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Tuple4f t1) {
/*  90 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Tuple4d t1) {
/* 100 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d(Tuple3d t1) {
/* 114 */     super(t1.x, t1.y, t1.z, 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 136 */     this.x = t1.x;
/* 137 */     this.y = t1.y;
/* 138 */     this.z = t1.z;
/* 139 */     this.w = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceSquared(Point4d p1) {
/* 152 */     double dx = this.x - p1.x;
/* 153 */     double dy = this.y - p1.y;
/* 154 */     double dz = this.z - p1.z;
/* 155 */     double dw = this.w - p1.w;
/* 156 */     return dx * dx + dy * dy + dz * dz + dw * dw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distance(Point4d p1) {
/* 169 */     double dx = this.x - p1.x;
/* 170 */     double dy = this.y - p1.y;
/* 171 */     double dz = this.z - p1.z;
/* 172 */     double dw = this.w - p1.w;
/* 173 */     return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceL1(Point4d p1) {
/* 185 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + 
/* 186 */       Math.abs(this.z - p1.z) + Math.abs(this.w - p1.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceLinf(Point4d p1) {
/* 198 */     double t1 = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/* 199 */     double t2 = Math.max(Math.abs(this.z - p1.z), Math.abs(this.w - p1.w));
/*     */     
/* 201 */     return Math.max(t1, t2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void project(Point4d p1) {
/* 214 */     double oneOw = 1.0D / p1.w;
/* 215 */     p1.x *= oneOw;
/* 216 */     p1.y *= oneOw;
/* 217 */     p1.z *= oneOw;
/* 218 */     this.w = 1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Point4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */