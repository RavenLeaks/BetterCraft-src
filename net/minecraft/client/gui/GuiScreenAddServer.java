/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.io.IOException;
/*     */ import java.net.IDN;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenAddServer extends GuiScreen {
/*     */   private final GuiScreen parentScreen;
/*     */   private final ServerData serverData;
/*     */   private GuiTextField serverIPField;
/*     */   private GuiTextField serverNameField;
/*     */   private GuiButton serverResourcePacks;
/*     */   
/*  19 */   private final Predicate<String> addressFilter = new Predicate<String>()
/*     */     {
/*     */       public boolean apply(@Nullable String p_apply_1_)
/*     */       {
/*  23 */         if (StringUtils.isNullOrEmpty(p_apply_1_))
/*     */         {
/*  25 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  29 */         String[] astring = p_apply_1_.split(":");
/*     */         
/*  31 */         if (astring.length == 0)
/*     */         {
/*  33 */           return true;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  39 */           String s = IDN.toASCII(astring[0]);
/*  40 */           return true;
/*     */         }
/*  42 */         catch (IllegalArgumentException var4) {
/*     */           
/*  44 */           return false;
/*     */         } 
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiScreenAddServer(GuiScreen p_i1033_1_, ServerData p_i1033_2_) {
/*  53 */     this.parentScreen = p_i1033_1_;
/*  54 */     this.serverData = p_i1033_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  62 */     this.serverNameField.updateCursorCounter();
/*  63 */     this.serverIPField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  72 */     Keyboard.enableRepeatEvents(true);
/*  73 */     this.buttonList.clear();
/*  74 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, I18n.format("addServer.add", new Object[0])));
/*  75 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, I18n.format("gui.cancel", new Object[0])));
/*  76 */     this.serverResourcePacks = addButton(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText()));
/*  77 */     this.serverNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
/*  78 */     this.serverNameField.setFocused(true);
/*  79 */     this.serverNameField.setText(this.serverData.serverName);
/*  80 */     this.serverIPField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
/*  81 */     this.serverIPField.setMaxStringLength(128);
/*  82 */     this.serverIPField.setText(this.serverData.serverIP);
/*  83 */     this.serverIPField.setValidator(this.addressFilter);
/*  84 */     ((GuiButton)this.buttonList.get(0)).enabled = (!this.serverIPField.getText().isEmpty() && (this.serverIPField.getText().split(":")).length > 0 && !this.serverNameField.getText().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  92 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 100 */     if (button.enabled)
/*     */     {
/* 102 */       if (button.id == 2) {
/*     */         
/* 104 */         this.serverData.setResourceMode(ServerData.ServerResourceMode.values()[(this.serverData.getResourceMode().ordinal() + 1) % (ServerData.ServerResourceMode.values()).length]);
/* 105 */         this.serverResourcePacks.displayString = String.valueOf(I18n.format("addServer.resourcePack", new Object[0])) + ": " + this.serverData.getResourceMode().getMotd().getFormattedText();
/*     */       }
/* 107 */       else if (button.id == 1) {
/*     */         
/* 109 */         this.parentScreen.confirmClicked(false, 0);
/*     */       }
/* 111 */       else if (button.id == 0) {
/*     */         
/* 113 */         this.serverData.serverName = this.serverNameField.getText();
/* 114 */         this.serverData.serverIP = this.serverIPField.getText();
/* 115 */         this.parentScreen.confirmClicked(true, 0);
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
/* 126 */     this.serverNameField.textboxKeyTyped(typedChar, keyCode);
/* 127 */     this.serverIPField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 129 */     if (keyCode == 15) {
/*     */       
/* 131 */       this.serverNameField.setFocused(!this.serverNameField.isFocused());
/* 132 */       this.serverIPField.setFocused(!this.serverIPField.isFocused());
/*     */     } 
/*     */     
/* 135 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 137 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 140 */     ((GuiButton)this.buttonList.get(0)).enabled = (!this.serverIPField.getText().isEmpty() && (this.serverIPField.getText().split(":")).length > 0 && !this.serverNameField.getText().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 148 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 149 */     this.serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
/* 150 */     this.serverNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 158 */     drawDefaultBackground();
/* 159 */     drawCenteredString(this.fontRendererObj, I18n.format("addServer.title", new Object[0]), this.width / 2, 17, 16777215);
/* 160 */     drawString(this.fontRendererObj, I18n.format("addServer.enterName", new Object[0]), this.width / 2 - 100, 53, 10526880);
/* 161 */     drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 94, 10526880);
/* 162 */     this.serverNameField.drawTextBox();
/* 163 */     this.serverIPField.drawTextBox();
/* 164 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiScreenAddServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */