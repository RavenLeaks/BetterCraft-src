/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiWorldEdit
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen lastScreen;
/*     */   private GuiTextField nameEdit;
/*     */   private final String worldId;
/*     */   
/*     */   public GuiWorldEdit(GuiScreen p_i46593_1_, String p_i46593_2_) {
/*  19 */     this.lastScreen = p_i46593_1_;
/*  20 */     this.worldId = p_i46593_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  28 */     this.nameEdit.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     Keyboard.enableRepeatEvents(true);
/*  38 */     this.buttonList.clear();
/*  39 */     GuiButton guibutton = addButton(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 24 + 12, I18n.format("selectWorld.edit.resetIcon", new Object[0])));
/*  40 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 48 + 12, I18n.format("selectWorld.edit.openFolder", new Object[0])));
/*  41 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectWorld.edit.save", new Object[0])));
/*  42 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*  43 */     guibutton.enabled = this.mc.getSaveLoader().getFile(this.worldId, "icon.png").isFile();
/*  44 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  45 */     WorldInfo worldinfo = isaveformat.getWorldInfo(this.worldId);
/*  46 */     String s = (worldinfo == null) ? "" : worldinfo.getWorldName();
/*  47 */     this.nameEdit = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  48 */     this.nameEdit.setFocused(true);
/*  49 */     this.nameEdit.setText(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  57 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  65 */     if (button.enabled)
/*     */     {
/*  67 */       if (button.id == 1) {
/*     */         
/*  69 */         this.mc.displayGuiScreen(this.lastScreen);
/*     */       }
/*  71 */       else if (button.id == 0) {
/*     */         
/*  73 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  74 */         isaveformat.renameWorld(this.worldId, this.nameEdit.getText().trim());
/*  75 */         this.mc.displayGuiScreen(this.lastScreen);
/*     */       }
/*  77 */       else if (button.id == 3) {
/*     */         
/*  79 */         ISaveFormat isaveformat1 = this.mc.getSaveLoader();
/*  80 */         FileUtils.deleteQuietly(isaveformat1.getFile(this.worldId, "icon.png"));
/*  81 */         button.enabled = false;
/*     */       }
/*  83 */       else if (button.id == 4) {
/*     */         
/*  85 */         ISaveFormat isaveformat2 = this.mc.getSaveLoader();
/*  86 */         OpenGlHelper.openFile(isaveformat2.getFile(this.worldId, "icon.png").getParentFile());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  97 */     this.nameEdit.textboxKeyTyped(typedChar, keyCode);
/*  98 */     ((GuiButton)this.buttonList.get(2)).enabled = !this.nameEdit.getText().trim().isEmpty();
/*     */     
/* 100 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 102 */       actionPerformed(this.buttonList.get(2));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 111 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 112 */     this.nameEdit.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 120 */     drawDefaultBackground();
/* 121 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.edit.title", new Object[0]), this.width / 2, 20, 16777215);
/* 122 */     drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, 10526880);
/* 123 */     this.nameEdit.drawTextBox();
/* 124 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiWorldEdit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */