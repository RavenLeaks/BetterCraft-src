/*    */ package javax.vecmath;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TexCoord3f
/*    */   extends Tuple3f
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = -3517736544731446513L;
/*    */   
/*    */   public TexCoord3f(float x, float y, float z) {
/* 49 */     super(x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(float[] v) {
/* 59 */     super(v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(TexCoord3f v1) {
/* 69 */     super(v1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(Tuple3f t1) {
/* 79 */     super(t1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(Tuple3d t1) {
/* 89 */     super(t1);
/*    */   }
/*    */   
/*    */   public TexCoord3f() {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\TexCoord3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */