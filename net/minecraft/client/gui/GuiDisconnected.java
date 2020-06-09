/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ public class GuiDisconnected
/*    */   extends GuiScreen
/*    */ {
/*    */   private final String reason;
/*    */   private final ITextComponent message;
/*    */   private List<String> multilineMessage;
/*    */   private final GuiScreen parentScreen;
/*    */   private int textHeight;
/*    */   
/*    */   public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, ITextComponent chatComp) {
/* 18 */     this.parentScreen = screen;
/* 19 */     this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
/* 20 */     this.message = chatComp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 37 */     this.buttonList.clear();
/* 38 */     this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
/* 39 */     this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
/* 40 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, this.height - 30), I18n.format("gui.toMenu", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 48 */     if (button.id == 0)
/*    */     {
/* 50 */       this.mc.displayGuiScreen(this.parentScreen);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 59 */     drawDefaultBackground();
/* 60 */     drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
/* 61 */     int i = this.height / 2 - this.textHeight / 2;
/*    */     
/* 63 */     if (this.multilineMessage != null)
/*    */     {
/* 65 */       for (String s : this.multilineMessage) {
/*    */         
/* 67 */         drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
/* 68 */         i += this.fontRendererObj.FONT_HEIGHT;
/*    */       } 
/*    */     }
/*    */     
/* 72 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiDisconnected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */