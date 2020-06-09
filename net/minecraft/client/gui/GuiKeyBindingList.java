/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ public class GuiKeyBindingList
/*     */   extends GuiListExtended
/*     */ {
/*     */   private final GuiControls controlsScreen;
/*     */   private final Minecraft mc;
/*     */   private final GuiListExtended.IGuiListEntry[] listEntries;
/*     */   private int maxListLabelWidth;
/*     */   
/*     */   public GuiKeyBindingList(GuiControls controls, Minecraft mcIn) {
/*  20 */     super(mcIn, controls.width + 45, controls.height, 63, controls.height - 32, 20);
/*  21 */     this.controlsScreen = controls;
/*  22 */     this.mc = mcIn;
/*  23 */     KeyBinding[] akeybinding = (KeyBinding[])ArrayUtils.clone((Object[])mcIn.gameSettings.keyBindings);
/*  24 */     this.listEntries = new GuiListExtended.IGuiListEntry[akeybinding.length + KeyBinding.getKeybinds().size()];
/*  25 */     Arrays.sort((Object[])akeybinding);
/*  26 */     int i = 0;
/*  27 */     String s = null; byte b; int j;
/*     */     KeyBinding[] arrayOfKeyBinding1;
/*  29 */     for (j = (arrayOfKeyBinding1 = akeybinding).length, b = 0; b < j; ) { KeyBinding keybinding = arrayOfKeyBinding1[b];
/*     */       
/*  31 */       String s1 = keybinding.getKeyCategory();
/*     */       
/*  33 */       if (!s1.equals(s)) {
/*     */         
/*  35 */         s = s1;
/*  36 */         this.listEntries[i++] = new CategoryEntry(s1);
/*     */       } 
/*     */       
/*  39 */       int k = mcIn.fontRendererObj.getStringWidth(I18n.format(keybinding.getKeyDescription(), new Object[0]));
/*     */       
/*  41 */       if (k > this.maxListLabelWidth)
/*     */       {
/*  43 */         this.maxListLabelWidth = k;
/*     */       }
/*     */       
/*  46 */       this.listEntries[i++] = new KeyEntry(keybinding, null);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   protected int getSize() {
/*  52 */     return this.listEntries.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiListExtended.IGuiListEntry getListEntry(int index) {
/*  60 */     return this.listEntries[index];
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  65 */     return super.getScrollBarX() + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  73 */     return super.getListWidth() + 32;
/*     */   }
/*     */   
/*     */   public class CategoryEntry
/*     */     implements GuiListExtended.IGuiListEntry
/*     */   {
/*     */     private final String labelText;
/*     */     private final int labelWidth;
/*     */     
/*     */     public CategoryEntry(String name) {
/*  83 */       this.labelText = I18n.format(name, new Object[0]);
/*  84 */       this.labelWidth = GuiKeyBindingList.this.mc.fontRendererObj.getStringWidth(this.labelText);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/*  89 */       GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.labelText, GuiKeyBindingList.this.mc.currentScreen.width / 2 - this.labelWidth / 2, p_192634_3_ + p_192634_5_ - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT - 1, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/*  94 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */     
/*     */     public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public class KeyEntry
/*     */     implements GuiListExtended.IGuiListEntry
/*     */   {
/*     */     private final KeyBinding keybinding;
/*     */     
/*     */     private final String keyDesc;
/*     */     private final GuiButton btnChangeKeyBinding;
/*     */     private final GuiButton btnReset;
/*     */     
/*     */     private KeyEntry(KeyBinding name) {
/* 115 */       this.keybinding = name;
/* 116 */       this.keyDesc = I18n.format(name.getKeyDescription(), new Object[0]);
/* 117 */       this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(name.getKeyDescription(), new Object[0]));
/* 118 */       this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset", new Object[0]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/* 123 */       boolean flag = (GuiKeyBindingList.this.controlsScreen.buttonId == this.keybinding);
/* 124 */       GuiKeyBindingList.this.mc.fontRendererObj.drawString(this.keyDesc, p_192634_2_ + 90 - GuiKeyBindingList.this.maxListLabelWidth, p_192634_3_ + p_192634_5_ / 2 - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT / 2, 16777215);
/* 125 */       this.btnReset.xPosition = p_192634_2_ + 190;
/* 126 */       this.btnReset.yPosition = p_192634_3_;
/* 127 */       this.btnReset.enabled = (this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault());
/* 128 */       this.btnReset.func_191745_a(GuiKeyBindingList.this.mc, p_192634_6_, p_192634_7_, p_192634_9_);
/* 129 */       this.btnChangeKeyBinding.xPosition = p_192634_2_ + 105;
/* 130 */       this.btnChangeKeyBinding.yPosition = p_192634_3_;
/* 131 */       this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
/* 132 */       boolean flag1 = false;
/*     */       
/* 134 */       if (this.keybinding.getKeyCode() != 0) {
/*     */         byte b; int i; KeyBinding[] arrayOfKeyBinding;
/* 136 */         for (i = (arrayOfKeyBinding = GuiKeyBindingList.this.mc.gameSettings.keyBindings).length, b = 0; b < i; ) { KeyBinding keybinding = arrayOfKeyBinding[b];
/*     */           
/* 138 */           if (keybinding != this.keybinding && keybinding.getKeyCode() == this.keybinding.getKeyCode()) {
/*     */             
/* 140 */             flag1 = true;
/*     */             break;
/*     */           } 
/*     */           b++; }
/*     */       
/*     */       } 
/* 146 */       if (flag) {
/*     */         
/* 148 */         this.btnChangeKeyBinding.displayString = TextFormatting.WHITE + "> " + TextFormatting.YELLOW + this.btnChangeKeyBinding.displayString + TextFormatting.WHITE + " <";
/*     */       }
/* 150 */       else if (flag1) {
/*     */         
/* 152 */         this.btnChangeKeyBinding.displayString = TextFormatting.RED + this.btnChangeKeyBinding.displayString;
/*     */       } 
/*     */       
/* 155 */       this.btnChangeKeyBinding.func_191745_a(GuiKeyBindingList.this.mc, p_192634_6_, p_192634_7_, p_192634_9_);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/* 160 */       if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, mouseX, mouseY)) {
/*     */         
/* 162 */         GuiKeyBindingList.this.controlsScreen.buttonId = this.keybinding;
/* 163 */         return true;
/*     */       } 
/* 165 */       if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, mouseX, mouseY)) {
/*     */         
/* 167 */         GuiKeyBindingList.this.mc.gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
/* 168 */         KeyBinding.resetKeyBindingArrayAndHash();
/* 169 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 173 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 179 */       this.btnChangeKeyBinding.mouseReleased(x, y);
/* 180 */       this.btnReset.mouseReleased(x, y);
/*     */     }
/*     */     
/*     */     public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiKeyBindingList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */