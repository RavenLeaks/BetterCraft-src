/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class GuiOptionSlider
/*    */   extends GuiButton
/*    */ {
/*    */   private float sliderValue;
/*    */   public boolean dragging;
/*    */   private final GameSettings.Options options;
/*    */   private final float minValue;
/*    */   private final float maxValue;
/*    */   
/*    */   public GuiOptionSlider(int buttonId, int x, int y, GameSettings.Options optionIn) {
/* 18 */     this(buttonId, x, y, optionIn, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public GuiOptionSlider(int buttonId, int x, int y, GameSettings.Options optionIn, float minValueIn, float maxValue) {
/* 23 */     super(buttonId, x, y, 150, 20, "");
/* 24 */     this.sliderValue = 1.0F;
/* 25 */     this.options = optionIn;
/* 26 */     this.minValue = minValueIn;
/* 27 */     this.maxValue = maxValue;
/* 28 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 29 */     this.sliderValue = optionIn.normalizeValue(minecraft.gameSettings.getOptionFloatValue(optionIn));
/* 30 */     this.displayString = minecraft.gameSettings.getKeyBinding(optionIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getHoverState(boolean mouseOver) {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 47 */     if (this.visible) {
/*    */       
/* 49 */       if (this.dragging) {
/*    */         
/* 51 */         this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 52 */         this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
/* 53 */         float f = this.options.denormalizeValue(this.sliderValue);
/* 54 */         mc.gameSettings.setOptionFloatValue(this.options, f);
/* 55 */         this.sliderValue = this.options.normalizeValue(f);
/* 56 */         this.displayString = mc.gameSettings.getKeyBinding(this.options);
/*    */       } 
/*    */       
/* 59 */       mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
/* 60 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 61 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 62 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 72 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/*    */       
/* 74 */       this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 75 */       this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
/* 76 */       mc.gameSettings.setOptionFloatValue(this.options, this.options.denormalizeValue(this.sliderValue));
/* 77 */       this.displayString = mc.gameSettings.getKeyBinding(this.options);
/* 78 */       this.dragging = true;
/* 79 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY) {
/* 92 */     this.dragging = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiOptionSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */