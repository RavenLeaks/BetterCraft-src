/*    */ package shadersmod.client;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniformFloat4
/*    */   extends ShaderUniformBase {
/*  7 */   private float[] values = new float[4];
/*    */ 
/*    */   
/*    */   public ShaderUniformFloat4(String name) {
/* 11 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramChanged() {
/* 16 */     this.values[0] = 0.0F;
/* 17 */     this.values[1] = 0.0F;
/* 18 */     this.values[2] = 0.0F;
/* 19 */     this.values[3] = 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(float f0, float f1, float f2, float f3) {
/* 24 */     if (getLocation() >= 0)
/*    */     {
/* 26 */       if (this.values[0] != f0 || this.values[1] != f1 || this.values[2] != f2 || this.values[3] != f3) {
/*    */         
/* 28 */         ARBShaderObjects.glUniform4fARB(getLocation(), f0, f1, f2, f3);
/* 29 */         Shaders.checkGLError(getName());
/* 30 */         this.values[0] = f0;
/* 31 */         this.values[1] = f1;
/* 32 */         this.values[2] = f2;
/* 33 */         this.values[3] = f3;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public float[] getValues() {
/* 40 */     return this.values;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderUniformFloat4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */