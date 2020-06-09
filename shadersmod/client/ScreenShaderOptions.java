/*    */ package shadersmod.client;
/*    */ 
/*    */ 
/*    */ public class ScreenShaderOptions
/*    */ {
/*    */   private String name;
/*    */   private ShaderOption[] shaderOptions;
/*    */   private int columns;
/*    */   
/*    */   public ScreenShaderOptions(String name, ShaderOption[] shaderOptions, int columns) {
/* 11 */     this.name = name;
/* 12 */     this.shaderOptions = shaderOptions;
/* 13 */     this.columns = columns;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderOption[] getShaderOptions() {
/* 23 */     return this.shaderOptions;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColumns() {
/* 28 */     return this.columns;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ScreenShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */