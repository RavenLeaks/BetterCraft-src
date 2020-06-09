/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLMultiworld
/*     */   extends GuiScreen
/*     */ {
/*     */   private final MultiworldCallback callback;
/*     */   private GuiButton multiworldEnabledBtn;
/*     */   private boolean enableMultiworld = false;
/*     */   private int infoBoxWidth;
/*     */   private int infoBoxHeight;
/*     */   private int infoBoxX;
/*     */   private int infoBoxY;
/*     */   private List<String> infoBoxLines;
/*     */   
/*     */   public GuiWDLMultiworld(MultiworldCallback callback) {
/*  29 */     this.callback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  37 */     this.buttonList.clear();
/*     */     
/*  39 */     String multiworldMessage = 
/*  40 */       String.valueOf(I18n.format("wdl.gui.multiworld.descirption.requiredWhen", new Object[0])) + 
/*  41 */       "\n\n" + 
/*  42 */       I18n.format("wdl.gui.multiworld.descirption.whatIs", new Object[0]);
/*     */     
/*  44 */     this.infoBoxWidth = 320;
/*  45 */     this.infoBoxLines = Utils.wordWrap(multiworldMessage, this.infoBoxWidth - 20);
/*  46 */     this.infoBoxHeight = this.fontRendererObj.FONT_HEIGHT * (this.infoBoxLines.size() + 1) + 40;
/*     */     
/*  48 */     this.infoBoxX = this.width / 2 - this.infoBoxWidth / 2;
/*  49 */     this.infoBoxY = this.height / 2 - this.infoBoxHeight / 2;
/*     */     
/*  51 */     this.multiworldEnabledBtn = new GuiButton(1, this.width / 2 - 100, 
/*  52 */         this.infoBoxY + this.infoBoxHeight - 30, 
/*  53 */         getMultiworldEnabledText());
/*  54 */     this.buttonList.add(this.multiworldEnabledBtn);
/*     */     
/*  56 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 155, 
/*  57 */           this.height - 29, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     
/*  59 */     this.buttonList.add(new GuiButton(101, this.width / 2 + 5, 
/*  60 */           this.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  69 */     if (button.id == 1) {
/*  70 */       toggleMultiworldEnabled();
/*  71 */     } else if (button.id == 100) {
/*  72 */       this.callback.onCancel();
/*  73 */     } else if (button.id == 101) {
/*  74 */       this.callback.onSelect(this.enableMultiworld);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  84 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  93 */     super.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 101 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 109 */     drawDefaultBackground();
/* 110 */     Utils.drawBorder(32, 32, 0, 0, this.height, this.width);
/*     */     
/* 112 */     drawCenteredString(this.fontRendererObj, 
/* 113 */         I18n.format("wdl.gui.multiworld.title", new Object[0]), 
/* 114 */         this.width / 2, 8, 16777215);
/*     */     
/* 116 */     drawRect(this.infoBoxX, this.infoBoxY, this.infoBoxX + this.infoBoxWidth, this.infoBoxY + 
/* 117 */         this.infoBoxHeight, -1342177280);
/*     */     
/* 119 */     int x = this.infoBoxX + 10;
/* 120 */     int y = this.infoBoxY + 10;
/*     */     
/* 122 */     for (String s : this.infoBoxLines) {
/* 123 */       drawString(this.fontRendererObj, s, x, y, 16777215);
/* 124 */       y += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */ 
/*     */     
/* 128 */     drawRect(
/* 129 */         this.multiworldEnabledBtn.xPosition - 2, 
/* 130 */         this.multiworldEnabledBtn.yPosition - 2, 
/* 131 */         this.multiworldEnabledBtn.xPosition + 
/* 132 */         this.multiworldEnabledBtn.getButtonWidth() + 2, 
/* 133 */         this.multiworldEnabledBtn.yPosition + 20 + 2, -65536);
/*     */     
/* 135 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggleMultiworldEnabled() {
/* 142 */     if (this.enableMultiworld) {
/* 143 */       this.enableMultiworld = false;
/*     */     } else {
/* 145 */       this.enableMultiworld = true;
/*     */     } 
/*     */     
/* 148 */     this.multiworldEnabledBtn.displayString = getMultiworldEnabledText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getMultiworldEnabledText() {
/* 155 */     return I18n.format("wdl.gui.multiworld." + this.enableMultiworld, new Object[0]);
/*     */   }
/*     */   
/*     */   public static interface MultiworldCallback {
/*     */     void onCancel();
/*     */     
/*     */     void onSelect(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLMultiworld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */