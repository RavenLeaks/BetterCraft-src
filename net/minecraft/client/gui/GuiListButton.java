/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiListButton
/*    */   extends GuiButton
/*    */ {
/*    */   private boolean value;
/*    */   private final String localizationStr;
/*    */   private final GuiPageButtonList.GuiResponder guiResponder;
/*    */   
/*    */   public GuiListButton(GuiPageButtonList.GuiResponder responder, int p_i45539_2_, int p_i45539_3_, int p_i45539_4_, String p_i45539_5_, boolean p_i45539_6_) {
/* 18 */     super(p_i45539_2_, p_i45539_3_, p_i45539_4_, 150, 20, "");
/* 19 */     this.localizationStr = p_i45539_5_;
/* 20 */     this.value = p_i45539_6_;
/* 21 */     this.displayString = buildDisplayString();
/* 22 */     this.guiResponder = responder;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String buildDisplayString() {
/* 30 */     return String.valueOf(I18n.format(this.localizationStr, new Object[0])) + ": " + I18n.format(this.value ? "gui.yes" : "gui.no", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(boolean p_175212_1_) {
/* 35 */     this.value = p_175212_1_;
/* 36 */     this.displayString = buildDisplayString();
/* 37 */     this.guiResponder.setEntryValue(this.id, p_175212_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 46 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/*    */       
/* 48 */       this.value = !this.value;
/* 49 */       this.displayString = buildDisplayString();
/* 50 */       this.guiResponder.setEntryValue(this.id, this.value);
/* 51 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiListButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */