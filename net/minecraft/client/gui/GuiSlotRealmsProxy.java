/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.realms.RealmsScrolledSelectionList;
/*    */ 
/*    */ public class GuiSlotRealmsProxy
/*    */   extends GuiSlot
/*    */ {
/*    */   private final RealmsScrolledSelectionList selectionList;
/*    */   
/*    */   public GuiSlotRealmsProxy(RealmsScrolledSelectionList selectionListIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/* 12 */     super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/* 13 */     this.selectionList = selectionListIn;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSize() {
/* 18 */     return this.selectionList.getItemCount();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 26 */     this.selectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 34 */     return this.selectionList.isSelectedItem(slotIndex);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawBackground() {
/* 39 */     this.selectionList.renderBackground();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_, int p_192637_6_, float p_192637_7_) {
/* 44 */     this.selectionList.renderItem(p_192637_1_, p_192637_2_, p_192637_3_, p_192637_4_, p_192637_5_, p_192637_6_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 49 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMouseY() {
/* 54 */     return this.mouseY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMouseX() {
/* 59 */     return this.mouseX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getContentHeight() {
/* 67 */     return this.selectionList.getMaxPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getScrollBarX() {
/* 72 */     return this.selectionList.getScrollbarPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleMouseInput() {
/* 77 */     super.handleMouseInput();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSlotRealmsProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */