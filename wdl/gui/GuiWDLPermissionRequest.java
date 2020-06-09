/*     */ package wdl.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import wdl.WDLPluginChannels;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLPermissionRequest
/*     */   extends GuiScreen
/*     */ {
/*     */   private static final int TOP_MARGIN = 61;
/*     */   private static final int BOTTOM_MARGIN = 32;
/*     */   private TextList list;
/*     */   private final GuiScreen parent;
/*     */   private GuiTextField requestField;
/*     */   private GuiButton submitButton;
/*     */   
/*     */   public GuiWDLPermissionRequest(GuiScreen parent) {
/*  36 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  41 */     this.list = new TextList(this.mc, this.width, this.height, 61, 32);
/*     */     
/*  43 */     this.list.addLine("§c§lThis is a work in progress.");
/*  44 */     this.list.addLine("You can request permissions in this GUI, although it currently requires manually specifying the names.");
/*     */     
/*  46 */     this.list.addBlankLine();
/*  47 */     this.list.addLine("Boolean fields: " + WDLPluginChannels.BOOLEAN_REQUEST_FIELDS);
/*  48 */     this.list.addLine("Integer fields: " + WDLPluginChannels.INTEGER_REQUEST_FIELDS);
/*  49 */     this.list.addBlankLine();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     Iterator<Map.Entry<String, String>> iterator = WDLPluginChannels.getRequests().entrySet().iterator(); while (iterator.hasNext()) { Map.Entry<String, String> request = iterator.next();
/*  55 */       this.list.addLine("Requesting '" + (String)request.getKey() + "' to be '" + 
/*  56 */           (String)request.getValue() + "'."); }
/*     */ 
/*     */     
/*  59 */     this.requestField = new GuiTextField(0, this.fontRendererObj, 
/*  60 */         this.width / 2 - 155, 18, 150, 20);
/*     */     
/*  62 */     this.submitButton = new GuiButton(1, this.width / 2 + 5, 18, 150, 
/*  63 */         20, "Submit request");
/*  64 */     this.submitButton.enabled = !WDLPluginChannels.getRequests().isEmpty();
/*  65 */     this.buttonList.add(this.submitButton);
/*     */     
/*  67 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 29, 
/*  68 */           I18n.format("gui.done", new Object[0])));
/*     */     
/*  70 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 155, 39, 100, 20, 
/*  71 */           I18n.format("wdl.gui.permissions.current", new Object[0])));
/*  72 */     this.buttonList.add(new GuiButton(201, this.width / 2 - 50, 39, 100, 20, 
/*  73 */           I18n.format("wdl.gui.permissions.request", new Object[0])));
/*  74 */     this.buttonList.add(new GuiButton(202, this.width / 2 + 55, 39, 100, 20, 
/*  75 */           I18n.format("wdl.gui.permissions.overrides", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  80 */     if (button.id == 1) {
/*  81 */       WDLPluginChannels.sendRequests();
/*  82 */       button.displayString = "Submitted!";
/*     */     } 
/*     */     
/*  85 */     if (button.id == 100) {
/*  86 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */     
/*  89 */     if (button.id == 200) {
/*  90 */       this.mc.displayGuiScreen(new GuiWDLPermissions(this.parent));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (button.id == 202) {
/*  96 */       this.mc.displayGuiScreen(new GuiWDLChunkOverrides(this.parent));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 102 */     this.requestField.updateCursorCounter();
/* 103 */     super.updateScreen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 112 */     this.requestField.mouseClicked(mouseX, mouseY, mouseButton);
/* 113 */     this.list.mouseClicked(mouseX, mouseY, mouseButton);
/* 114 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 119 */     super.keyTyped(typedChar, keyCode);
/* 120 */     this.requestField.textboxKeyTyped(typedChar, keyCode);
/*     */     
/* 122 */     if (this.requestField.isFocused()) {
/* 123 */       String request = this.requestField.getText();
/*     */       
/* 125 */       boolean isValid = false;
/*     */       
/* 127 */       if (request.contains("=")) {
/* 128 */         String[] requestData = request.split("=", 2);
/* 129 */         if (requestData.length == 2) {
/* 130 */           String key = requestData[0];
/* 131 */           String value = requestData[1];
/*     */           
/* 133 */           isValid = WDLPluginChannels.isValidRequest(key, value);
/*     */           
/* 135 */           if (isValid && keyCode == 28) {
/* 136 */             this.requestField.setText("");
/* 137 */             isValid = false;
/*     */             
/* 139 */             WDLPluginChannels.addRequest(key, value);
/* 140 */             this.list.addLine("Requesting '" + key + "' to be '" + 
/* 141 */                 value + "'.");
/* 142 */             this.submitButton.enabled = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       this.requestField.setTextColor(isValid ? 4251712 : 14696512);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 156 */     super.handleMouseInput();
/* 157 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 162 */     if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 165 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 170 */     if (this.list == null) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 176 */     this.requestField.drawTextBox();
/*     */     
/* 178 */     drawCenteredString(this.fontRendererObj, "Permission request", 
/* 179 */         this.width / 2, 8, 16777215);
/*     */     
/* 181 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLPermissionRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */