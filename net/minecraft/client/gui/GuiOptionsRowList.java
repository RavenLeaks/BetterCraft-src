/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ public class GuiOptionsRowList
/*     */   extends GuiListExtended {
/*  10 */   private final List<Row> options = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public GuiOptionsRowList(Minecraft mcIn, int p_i45015_2_, int p_i45015_3_, int p_i45015_4_, int p_i45015_5_, int p_i45015_6_, GameSettings.Options... p_i45015_7_) {
/*  14 */     super(mcIn, p_i45015_2_, p_i45015_3_, p_i45015_4_, p_i45015_5_, p_i45015_6_);
/*  15 */     this.centerListVertically = false;
/*     */     
/*  17 */     for (int i = 0; i < p_i45015_7_.length; i += 2) {
/*     */       
/*  19 */       GameSettings.Options gamesettings$options = p_i45015_7_[i];
/*  20 */       GameSettings.Options gamesettings$options1 = (i < p_i45015_7_.length - 1) ? p_i45015_7_[i + 1] : null;
/*  21 */       GuiButton guibutton = createButton(mcIn, p_i45015_2_ / 2 - 155, 0, gamesettings$options);
/*  22 */       GuiButton guibutton1 = createButton(mcIn, p_i45015_2_ / 2 - 155 + 160, 0, gamesettings$options1);
/*  23 */       this.options.add(new Row(guibutton, guibutton1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private GuiButton createButton(Minecraft mcIn, int p_148182_2_, int p_148182_3_, GameSettings.Options options) {
/*  29 */     if (options == null)
/*     */     {
/*  31 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  35 */     int i = options.returnEnumOrdinal();
/*  36 */     return options.getEnumFloat() ? new GuiOptionSlider(i, p_148182_2_, p_148182_3_, options) : new GuiOptionButton(i, p_148182_2_, p_148182_3_, options, mcIn.gameSettings.getKeyBinding(options));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Row getListEntry(int index) {
/*  45 */     return this.options.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  50 */     return this.options.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  58 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  63 */     return super.getScrollBarX() + 32;
/*     */   }
/*     */   
/*     */   public static class Row
/*     */     implements GuiListExtended.IGuiListEntry {
/*  68 */     private final Minecraft client = Minecraft.getMinecraft();
/*     */     
/*     */     private final GuiButton buttonA;
/*     */     private final GuiButton buttonB;
/*     */     
/*     */     public Row(GuiButton buttonAIn, GuiButton buttonBIn) {
/*  74 */       this.buttonA = buttonAIn;
/*  75 */       this.buttonB = buttonBIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/*  80 */       if (this.buttonA != null) {
/*     */         
/*  82 */         this.buttonA.yPosition = p_192634_3_;
/*  83 */         this.buttonA.func_191745_a(this.client, p_192634_6_, p_192634_7_, p_192634_9_);
/*     */       } 
/*     */       
/*  86 */       if (this.buttonB != null) {
/*     */         
/*  88 */         this.buttonB.yPosition = p_192634_3_;
/*  89 */         this.buttonB.func_191745_a(this.client, p_192634_6_, p_192634_7_, p_192634_9_);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/*  95 */       if (this.buttonA.mousePressed(this.client, mouseX, mouseY)) {
/*     */         
/*  97 */         if (this.buttonA instanceof GuiOptionButton) {
/*     */           
/*  99 */           this.client.gameSettings.setOptionValue(((GuiOptionButton)this.buttonA).returnEnumOptions(), 1);
/* 100 */           this.buttonA.displayString = this.client.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.buttonA.id));
/*     */         } 
/*     */         
/* 103 */         return true;
/*     */       } 
/* 105 */       if (this.buttonB != null && this.buttonB.mousePressed(this.client, mouseX, mouseY)) {
/*     */         
/* 107 */         if (this.buttonB instanceof GuiOptionButton) {
/*     */           
/* 109 */           this.client.gameSettings.setOptionValue(((GuiOptionButton)this.buttonB).returnEnumOptions(), 1);
/* 110 */           this.buttonB.displayString = this.client.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.buttonB.id));
/*     */         } 
/*     */         
/* 113 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
/* 123 */       if (this.buttonA != null)
/*     */       {
/* 125 */         this.buttonA.mouseReleased(x, y);
/*     */       }
/*     */       
/* 128 */       if (this.buttonB != null)
/*     */       {
/* 130 */         this.buttonB.mouseReleased(x, y);
/*     */       }
/*     */     }
/*     */     
/*     */     public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiOptionsRowList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */