/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public abstract class GuiListExtended
/*    */   extends GuiSlot
/*    */ {
/*    */   public GuiListExtended(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  9 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawBackground() {}
/*    */ 
/*    */   
/*    */   protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 33 */     getListEntry(p_192637_1_).func_192634_a(p_192637_1_, p_192637_2_, p_192637_3_, getListWidth(), p_192637_4_, p_192637_5_, p_192637_6_, (isMouseYWithinSlotBounds(p_192637_6_) && getSlotIndexFromScreenCoords(p_192637_5_, p_192637_6_) == p_192637_1_), p_192637_7_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_192639_a(int p_192639_1_, int p_192639_2_, int p_192639_3_, float p_192639_4_) {
/* 38 */     getListEntry(p_192639_1_).func_192633_a(p_192639_1_, p_192639_2_, p_192639_3_, p_192639_4_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
/* 43 */     if (isMouseYWithinSlotBounds(mouseY)) {
/*    */       
/* 45 */       int i = getSlotIndexFromScreenCoords(mouseX, mouseY);
/*    */       
/* 47 */       if (i >= 0) {
/*    */         
/* 49 */         int j = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 50 */         int k = this.top + 4 - getAmountScrolled() + i * this.slotHeight + this.headerPadding;
/* 51 */         int l = mouseX - j;
/* 52 */         int i1 = mouseY - k;
/*    */         
/* 54 */         if (getListEntry(i).mousePressed(i, mouseX, mouseY, mouseEvent, l, i1)) {
/*    */           
/* 56 */           setEnabled(false);
/* 57 */           return true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseReleased(int p_148181_1_, int p_148181_2_, int p_148181_3_) {
/* 67 */     for (int i = 0; i < getSize(); i++) {
/*    */       
/* 69 */       int j = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 70 */       int k = this.top + 4 - getAmountScrolled() + i * this.slotHeight + this.headerPadding;
/* 71 */       int l = p_148181_1_ - j;
/* 72 */       int i1 = p_148181_2_ - k;
/* 73 */       getListEntry(i).mouseReleased(i, p_148181_1_, p_148181_2_, p_148181_3_, l, i1);
/*    */     } 
/*    */     
/* 76 */     setEnabled(true);
/* 77 */     return false;
/*    */   }
/*    */   
/*    */   public abstract IGuiListEntry getListEntry(int paramInt);
/*    */   
/*    */   public static interface IGuiListEntry {
/*    */     void func_192633_a(int param1Int1, int param1Int2, int param1Int3, float param1Float);
/*    */     
/*    */     void func_192634_a(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, boolean param1Boolean, float param1Float);
/*    */     
/*    */     boolean mousePressed(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*    */     
/*    */     void mouseReleased(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiListExtended.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */