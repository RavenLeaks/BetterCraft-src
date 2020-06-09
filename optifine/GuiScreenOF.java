/*    */ package optifine;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiVideoSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiScreenOF
/*    */   extends GuiScreen
/*    */ {
/*    */   protected void actionPerformedRightClick(GuiButton p_actionPerformedRightClick_1_) throws IOException {}
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 20 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */     
/* 22 */     if (mouseButton == 1) {
/*    */       
/* 24 */       GuiButton guibutton = getSelectedButton(this.buttonList, mouseX, mouseY);
/*    */       
/* 26 */       if (guibutton != null && guibutton.enabled) {
/*    */         
/* 28 */         guibutton.playPressSound(this.mc.getSoundHandler());
/* 29 */         actionPerformedRightClick(guibutton);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static GuiButton getSelectedButton(List<GuiButton> p_getSelectedButton_0_, int p_getSelectedButton_1_, int p_getSelectedButton_2_) {
/* 36 */     for (int i = 0; i < p_getSelectedButton_0_.size(); i++) {
/*    */       
/* 38 */       GuiButton guibutton = p_getSelectedButton_0_.get(i);
/*    */       
/* 40 */       if (guibutton.visible) {
/*    */         
/* 42 */         int j = GuiVideoSettings.getButtonWidth(guibutton);
/* 43 */         int k = GuiVideoSettings.getButtonHeight(guibutton);
/*    */         
/* 45 */         if (p_getSelectedButton_1_ >= guibutton.xPosition && p_getSelectedButton_2_ >= guibutton.yPosition && p_getSelectedButton_1_ < guibutton.xPosition + j && p_getSelectedButton_2_ < guibutton.yPosition + k)
/*    */         {
/* 47 */           return guibutton;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GuiScreenOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */