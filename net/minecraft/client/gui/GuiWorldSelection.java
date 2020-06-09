/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiWorldSelection
/*     */   extends GuiScreen {
/*  13 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   protected GuiScreen prevScreen;
/*     */   
/*  17 */   protected String title = "Select world";
/*     */   
/*     */   private String worldVersTooltip;
/*     */   
/*     */   private GuiButton deleteButton;
/*     */   
/*     */   private GuiButton selectButton;
/*     */   
/*     */   private GuiButton renameButton;
/*     */   
/*     */   private GuiButton copyButton;
/*     */   private GuiListWorldSelection selectionList;
/*     */   
/*     */   public GuiWorldSelection(GuiScreen screenIn) {
/*  31 */     this.prevScreen = screenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  40 */     this.title = I18n.format("selectWorld.title", new Object[0]);
/*  41 */     this.selectionList = new GuiListWorldSelection(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
/*  42 */     postInit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  50 */     super.handleMouseInput();
/*  51 */     this.selectionList.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public void postInit() {
/*  56 */     this.selectButton = addButton(new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
/*  57 */     addButton(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  58 */     this.renameButton = addButton(new GuiButton(4, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.edit", new Object[0])));
/*  59 */     this.deleteButton = addButton(new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
/*  60 */     this.copyButton = addButton(new GuiButton(5, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
/*  61 */     addButton(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
/*  62 */     this.selectButton.enabled = false;
/*  63 */     this.deleteButton.enabled = false;
/*  64 */     this.renameButton.enabled = false;
/*  65 */     this.copyButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  73 */     if (button.enabled) {
/*     */       
/*  75 */       GuiListWorldSelectionEntry guilistworldselectionentry = this.selectionList.getSelectedWorld();
/*     */       
/*  77 */       if (button.id == 2) {
/*     */         
/*  79 */         if (guilistworldselectionentry != null)
/*     */         {
/*  81 */           guilistworldselectionentry.deleteWorld();
/*     */         }
/*     */       }
/*  84 */       else if (button.id == 1) {
/*     */         
/*  86 */         if (guilistworldselectionentry != null)
/*     */         {
/*  88 */           guilistworldselectionentry.joinWorld();
/*     */         }
/*     */       }
/*  91 */       else if (button.id == 3) {
/*     */         
/*  93 */         this.mc.displayGuiScreen(new GuiCreateWorld(this));
/*     */       }
/*  95 */       else if (button.id == 4) {
/*     */         
/*  97 */         if (guilistworldselectionentry != null)
/*     */         {
/*  99 */           guilistworldselectionentry.editWorld();
/*     */         }
/*     */       }
/* 102 */       else if (button.id == 0) {
/*     */         
/* 104 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       }
/* 106 */       else if (button.id == 5 && guilistworldselectionentry != null) {
/*     */         
/* 108 */         guilistworldselectionentry.recreateWorld();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 118 */     this.worldVersTooltip = null;
/*     */     
/* 120 */     drawDefaultBackground();
/*     */     
/* 122 */     this.selectionList.drawScreen(mouseX, mouseY, partialTicks);
/* 123 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/* 124 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 126 */     if (this.worldVersTooltip != null)
/*     */     {
/* 128 */       drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.worldVersTooltip)), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 137 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 138 */     this.selectionList.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 146 */     super.mouseReleased(mouseX, mouseY, state);
/* 147 */     this.selectionList.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersionTooltip(String p_184861_1_) {
/* 155 */     this.worldVersTooltip = p_184861_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectWorld(@Nullable GuiListWorldSelectionEntry entry) {
/* 160 */     boolean flag = (entry != null);
/* 161 */     this.selectButton.enabled = flag;
/* 162 */     this.deleteButton.enabled = flag;
/* 163 */     this.renameButton.enabled = flag;
/* 164 */     this.copyButton.enabled = flag;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiWorldSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */