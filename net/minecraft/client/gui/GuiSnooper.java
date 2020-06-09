/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ 
/*     */ public class GuiSnooper
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen lastScreen;
/*     */   private final GameSettings game_settings_2;
/*  16 */   private final java.util.List<String> keys = Lists.newArrayList();
/*  17 */   private final java.util.List<String> values = Lists.newArrayList();
/*     */   
/*     */   private String title;
/*     */   private String[] desc;
/*     */   private List list;
/*     */   private GuiButton toggleButton;
/*     */   
/*     */   public GuiSnooper(GuiScreen p_i1061_1_, GameSettings p_i1061_2_) {
/*  25 */     this.lastScreen = p_i1061_1_;
/*  26 */     this.game_settings_2 = p_i1061_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     this.title = I18n.format("options.snooper.title", new Object[0]);
/*  36 */     String s = I18n.format("options.snooper.desc", new Object[0]);
/*  37 */     java.util.List<String> list = Lists.newArrayList();
/*     */     
/*  39 */     for (String s1 : this.fontRendererObj.listFormattedStringToWidth(s, this.width - 30))
/*     */     {
/*  41 */       list.add(s1);
/*     */     }
/*     */     
/*  44 */     this.desc = list.<String>toArray(new String[list.size()]);
/*  45 */     this.keys.clear();
/*  46 */     this.values.clear();
/*  47 */     this.toggleButton = addButton(new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
/*  48 */     this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
/*  49 */     boolean flag = (this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null);
/*     */     
/*  51 */     for (Map.Entry<String, String> entry : (new TreeMap<>(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
/*     */       
/*  53 */       this.keys.add(String.valueOf(flag ? "C " : "") + (String)entry.getKey());
/*  54 */       this.values.add(this.fontRendererObj.trimStringToWidth(entry.getValue(), this.width - 220));
/*     */     } 
/*     */     
/*  57 */     if (flag)
/*     */     {
/*  59 */       for (Map.Entry<String, String> entry1 : (new TreeMap<>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
/*     */         
/*  61 */         this.keys.add("S " + (String)entry1.getKey());
/*  62 */         this.values.add(this.fontRendererObj.trimStringToWidth(entry1.getValue(), this.width - 220));
/*     */       } 
/*     */     }
/*     */     
/*  66 */     this.list = new List();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  74 */     super.handleMouseInput();
/*  75 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  83 */     if (button.enabled) {
/*     */       
/*  85 */       if (button.id == 2) {
/*     */         
/*  87 */         this.game_settings_2.saveOptions();
/*  88 */         this.game_settings_2.saveOptions();
/*  89 */         this.mc.displayGuiScreen(this.lastScreen);
/*     */       } 
/*     */       
/*  92 */       if (button.id == 1) {
/*     */         
/*  94 */         this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
/*  95 */         this.toggleButton.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 105 */     drawDefaultBackground();
/* 106 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 107 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 8, 16777215);
/* 108 */     int i = 22; byte b; int j;
/*     */     String[] arrayOfString;
/* 110 */     for (j = (arrayOfString = this.desc).length, b = 0; b < j; ) { String s = arrayOfString[b];
/*     */       
/* 112 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 8421504);
/* 113 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */       b++; }
/*     */     
/* 116 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot
/*     */   {
/*     */     public List() {
/* 123 */       super(GuiSnooper.this.mc, GuiSnooper.this.width, GuiSnooper.this.height, 80, GuiSnooper.this.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 128 */       return GuiSnooper.this.keys.size();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 137 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */ 
/*     */     
/*     */     protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 146 */       GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.keys.get(p_192637_1_), 10, p_192637_3_, 16777215);
/* 147 */       GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.values.get(p_192637_1_), 230, p_192637_3_, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollBarX() {
/* 152 */       return this.width - 10;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSnooper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */