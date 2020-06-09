/*    */ package shadersmod.client;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniformInt
/*    */   extends ShaderUniformBase {
/*  7 */   private int value = -1;
/*    */ 
/*    */   
/*    */   public ShaderUniformInt(String name) {
/* 11 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramChanged() {
/* 16 */     this.value = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(int value) {
/* 21 */     if (getLocation() >= 0)
/*    */     {
/* 23 */       if (this.value != value) {
/*    */         
/* 25 */         ARBShaderObjects.glUniform1iARB(getLocation(), value);
/* 26 */         Shaders.checkGLError(getName());
/* 27 */         this.value = value;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 34 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderUniformInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */