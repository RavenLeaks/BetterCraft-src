/*    */ package shadersmod.client;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class GuiSliderShaderOption
/*    */   extends GuiButtonShaderOption {
/*  9 */   private float sliderValue = 1.0F;
/*    */   public boolean dragging;
/* 11 */   private ShaderOption shaderOption = null;
/*    */ 
/*    */   
/*    */   public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
/* 15 */     super(buttonId, x, y, w, h, shaderOption, text);
/* 16 */     this.shaderOption = shaderOption;
/* 17 */     this.sliderValue = shaderOption.getIndexNormalized();
/* 18 */     this.displayString = GuiShaderOptions.getButtonText(shaderOption, this.width);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getHoverState(boolean mouseOver) {
/* 27 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 35 */     if (this.visible) {
/*    */       
/* 37 */       if (this.dragging) {
/*    */         
/* 39 */         this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 40 */         this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
/* 41 */         this.shaderOption.setIndexNormalized(this.sliderValue);
/* 42 */         this.sliderValue = this.shaderOption.getIndexNormalized();
/* 43 */         this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
/*    */       } 
/*    */       
/* 46 */       mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
/* 47 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 48 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 49 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 59 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/*    */       
/* 61 */       this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 62 */       this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
/* 63 */       this.shaderOption.setIndexNormalized(this.sliderValue);
/* 64 */       this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
/* 65 */       this.dragging = true;
/* 66 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY) {
/* 79 */     this.dragging = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void valueChanged() {
/* 84 */     this.sliderValue = this.shaderOption.getIndexNormalized();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\GuiSliderShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */