/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.realms.RealmsButton;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ 
/*     */ public class GuiScreenRealmsProxy
/*     */   extends GuiScreen
/*     */ {
/*     */   private final RealmsScreen proxy;
/*     */   
/*     */   public GuiScreenRealmsProxy(RealmsScreen proxyIn) {
/*  17 */     this.proxy = proxyIn;
/*  18 */     this.buttonList = Collections.synchronizedList(Lists.newArrayList());
/*     */   }
/*     */ 
/*     */   
/*     */   public RealmsScreen getProxy() {
/*  23 */     return this.proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  32 */     this.proxy.init();
/*  33 */     super.initGui();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String p_154325_1_, int p_154325_2_, int p_154325_3_, int p_154325_4_) {
/*  38 */     GuiScreen.drawCenteredString(this.fontRendererObj, p_154325_1_, p_154325_2_, p_154325_3_, p_154325_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(String p_154322_1_, int p_154322_2_, int p_154322_3_, int p_154322_4_, boolean p_154322_5_) {
/*  43 */     if (p_154322_5_) {
/*     */       
/*  45 */       GuiScreen.drawString(this.fontRendererObj, p_154322_1_, p_154322_2_, p_154322_3_, p_154322_4_);
/*     */     }
/*     */     else {
/*     */       
/*  49 */       this.fontRendererObj.drawString(p_154322_1_, p_154322_2_, p_154322_3_, p_154322_4_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/*  58 */     this.proxy.blit(x, y, textureX, textureY, width, height);
/*  59 */     super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  68 */     super.drawGradientRect(left, top, right, bottom, startColor, endColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/*  76 */     super.drawDefaultBackground();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  84 */     return super.doesGuiPauseGame();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/*  89 */     super.drawWorldBackground(tint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  97 */     this.proxy.render(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderToolTip(ItemStack stack, int x, int y) {
/* 102 */     super.renderToolTip(stack, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 111 */     super.drawCreativeTabHoveringText(tabName, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawHoveringText(List<String> textLines, int x, int y) {
/* 119 */     super.drawHoveringText(textLines, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 127 */     this.proxy.tick();
/* 128 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFontHeight() {
/* 133 */     return this.fontRendererObj.FONT_HEIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(String p_154326_1_) {
/* 138 */     return this.fontRendererObj.getStringWidth(p_154326_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fontDrawShadow(String p_154319_1_, int p_154319_2_, int p_154319_3_, int p_154319_4_) {
/* 143 */     this.fontRendererObj.drawStringWithShadow(p_154319_1_, p_154319_2_, p_154319_3_, p_154319_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> fontSplit(String p_154323_1_, int p_154323_2_) {
/* 148 */     return this.fontRendererObj.listFormattedStringToWidth(p_154323_1_, p_154323_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void actionPerformed(GuiButton button) throws IOException {
/* 156 */     this.proxy.buttonClicked(((GuiButtonRealmsProxy)button).getRealmsButton());
/*     */   }
/*     */ 
/*     */   
/*     */   public void buttonsClear() {
/* 161 */     this.buttonList.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void buttonsAdd(RealmsButton button) {
/* 166 */     this.buttonList.add(button.getProxy());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<RealmsButton> buttons() {
/* 171 */     List<RealmsButton> list = Lists.newArrayListWithExpectedSize(this.buttonList.size());
/*     */     
/* 173 */     for (GuiButton guibutton : this.buttonList)
/*     */     {
/* 175 */       list.add(((GuiButtonRealmsProxy)guibutton).getRealmsButton());
/*     */     }
/*     */     
/* 178 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void buttonsRemove(RealmsButton button) {
/* 183 */     this.buttonList.remove(button.getProxy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 191 */     this.proxy.mouseClicked(mouseX, mouseY, mouseButton);
/* 192 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 200 */     this.proxy.mouseEvent();
/* 201 */     super.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 209 */     this.proxy.keyboardEvent();
/* 210 */     super.handleKeyboardInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 218 */     this.proxy.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 227 */     this.proxy.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) throws IOException {
/* 236 */     this.proxy.keyPressed(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 241 */     this.proxy.confirmResult(result, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 249 */     this.proxy.removed();
/* 250 */     super.onGuiClosed();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenRealmsProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */