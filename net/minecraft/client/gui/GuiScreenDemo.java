/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class GuiScreenDemo
/*    */   extends GuiScreen {
/* 14 */   private static final Logger LOGGER = LogManager.getLogger();
/* 15 */   private static final ResourceLocation DEMO_BACKGROUND_LOCATION = new ResourceLocation("textures/gui/demo_background.png");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 23 */     this.buttonList.clear();
/* 24 */     int i = -16;
/* 25 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, I18n.format("demo.help.buy", new Object[0])));
/* 26 */     this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, I18n.format("demo.help.later", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 34 */     switch (button.id) {
/*    */       
/*    */       case 1:
/* 37 */         button.enabled = false;
/*    */ 
/*    */         
/*    */         try {
/* 41 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 42 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 43 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://www.minecraft.net/store?source=demo") });
/*    */         }
/* 45 */         catch (Throwable throwable) {
/*    */           
/* 47 */           LOGGER.error("Couldn't open link", throwable);
/*    */         } 
/*    */         break;
/*    */ 
/*    */       
/*    */       case 2:
/* 53 */         this.mc.displayGuiScreen(null);
/* 54 */         this.mc.setIngameFocus();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawDefaultBackground() {
/* 63 */     super.drawDefaultBackground();
/* 64 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 65 */     this.mc.getTextureManager().bindTexture(DEMO_BACKGROUND_LOCATION);
/* 66 */     int i = (this.width - 248) / 2;
/* 67 */     int j = (this.height - 166) / 2;
/* 68 */     drawTexturedModalRect(i, j, 0, 0, 248, 166);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 76 */     drawDefaultBackground();
/* 77 */     int i = (this.width - 248) / 2 + 10;
/* 78 */     int j = (this.height - 166) / 2 + 8;
/* 79 */     this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), i, j, 2039583);
/* 80 */     j += 12;
/* 81 */     GameSettings gamesettings = this.mc.gameSettings;
/* 82 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()) }), i, j, 5197647);
/* 83 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), i, j + 12, 5197647);
/* 84 */     this.fontRendererObj.drawString(I18n.format("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }), i, j + 24, 5197647);
/* 85 */     this.fontRendererObj.drawString(I18n.format("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindInventory.getKeyCode()) }), i, j + 36, 5197647);
/* 86 */     this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), i, j + 68, 218, 2039583);
/* 87 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenDemo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */