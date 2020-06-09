/*    */ package optifine;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class GuiMessage
/*    */   extends GuiScreen {
/*    */   private GuiScreen parentScreen;
/*    */   private String messageLine1;
/*    */   private String messageLine2;
/* 16 */   private final List listLines2 = Lists.newArrayList();
/*    */   
/*    */   protected String confirmButtonText;
/*    */   private int ticksUntilEnable;
/*    */   
/*    */   public GuiMessage(GuiScreen p_i48_1_, String p_i48_2_, String p_i48_3_) {
/* 22 */     this.parentScreen = p_i48_1_;
/* 23 */     this.messageLine1 = p_i48_2_;
/* 24 */     this.messageLine2 = p_i48_3_;
/* 25 */     this.confirmButtonText = I18n.format("gui.done", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 34 */     this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
/* 35 */     this.listLines2.clear();
/* 36 */     this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 44 */     Config.getMinecraft().displayGuiScreen(this.parentScreen);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 52 */     drawDefaultBackground();
/* 53 */     drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
/* 54 */     int i = 90;
/*    */     
/* 56 */     for (Object s : this.listLines2) {
/*    */       
/* 58 */       drawCenteredString(this.fontRendererObj, (String)s, this.width / 2, i, 16777215);
/* 59 */       i += this.fontRendererObj.FONT_HEIGHT;
/*    */     } 
/*    */     
/* 62 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setButtonDelay(int p_setButtonDelay_1_) {
/* 67 */     this.ticksUntilEnable = p_setButtonDelay_1_;
/*    */     
/* 69 */     for (GuiButton guibutton : this.buttonList)
/*    */     {
/* 71 */       guibutton.enabled = false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 80 */     super.updateScreen();
/*    */     
/* 82 */     if (--this.ticksUntilEnable == 0)
/*    */     {
/* 84 */       for (GuiButton guibutton : this.buttonList)
/*    */       {
/* 86 */         guibutton.enabled = true;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\GuiMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */