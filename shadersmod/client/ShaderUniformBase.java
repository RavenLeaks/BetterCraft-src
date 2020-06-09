/*    */ package shadersmod.client;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public abstract class ShaderUniformBase
/*    */ {
/*    */   private String name;
/*  8 */   private int program = -1;
/*  9 */   private int location = -1;
/*    */ 
/*    */   
/*    */   public ShaderUniformBase(String name) {
/* 13 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgram(int program) {
/* 18 */     if (this.program != program) {
/*    */       
/* 20 */       this.program = program;
/* 21 */       this.location = ARBShaderObjects.glGetUniformLocationARB(program, this.name);
/* 22 */       onProgramChanged();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void onProgramChanged();
/*    */   
/*    */   public String getName() {
/* 30 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProgram() {
/* 35 */     return this.program;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLocation() {
/* 40 */     return this.location;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefined() {
/* 45 */     return (this.location >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderUniformBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */