/*    */ package shadersmod.client;
/*    */ 
/*    */ public class ShaderOptionScreen
/*    */   extends ShaderOption
/*    */ {
/*    */   public ShaderOptionScreen(String name) {
/*  7 */     super(name, null, null, new String[0], null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getNameText() {
/* 12 */     return Shaders.translate("screen." + getName(), getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionText() {
/* 17 */     return Shaders.translate("screen." + getName() + ".comment", null);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShaderOptionScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */