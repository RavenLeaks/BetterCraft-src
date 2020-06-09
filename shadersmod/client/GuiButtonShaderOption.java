/*    */ package shadersmod.client;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public class GuiButtonShaderOption
/*    */   extends GuiButton {
/*  7 */   private ShaderOption shaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiButtonShaderOption(int buttonId, int x, int y, int widthIn, int heightIn, ShaderOption shaderOption, String text) {
/* 11 */     super(buttonId, x, y, widthIn, heightIn, text);
/* 12 */     this.shaderOption = shaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   public ShaderOption getShaderOption() {
/* 17 */     return this.shaderOption;
/*    */   }
/*    */   
/*    */   public void valueChanged() {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\GuiButtonShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */