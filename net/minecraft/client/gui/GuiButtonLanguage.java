/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class GuiButtonLanguage
/*    */   extends GuiButton
/*    */ {
/*    */   public GuiButtonLanguage(int buttonID, int xPos, int yPos) {
/* 10 */     super(buttonID, xPos, yPos, 20, 20, "");
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
/* 15 */     if (this.visible) {
/*    */       
/* 17 */       p_191745_1_.getTextureManager().bindTexture(GuiButton.BUTTON_TEXTURES);
/* 18 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 19 */       boolean flag = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
/* 20 */       int i = 106;
/*    */       
/* 22 */       if (flag)
/*    */       {
/* 24 */         i += this.height;
/*    */       }
/*    */       
/* 27 */       drawTexturedModalRect(this.xPosition, this.yPosition, 0, i, this.width, this.height);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiButtonLanguage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */