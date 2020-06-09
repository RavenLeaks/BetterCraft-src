/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatLine
/*    */ {
/*    */   private final int updateCounterCreated;
/*    */   private final ITextComponent lineString;
/* 13 */   public int slide = -100;
/*    */   
/*    */   private final int chatLineID;
/*    */ 
/*    */   
/*    */   public ChatLine(int p_i45000_1_, ITextComponent p_i45000_2_, int p_i45000_3_) {
/* 19 */     this.lineString = p_i45000_2_;
/* 20 */     this.updateCounterCreated = p_i45000_1_;
/* 21 */     this.chatLineID = p_i45000_3_;
/*    */   }
/*    */   
/*    */   public ITextComponent getChatComponent() {
/* 25 */     return this.lineString;
/*    */   }
/*    */   
/*    */   public int getUpdatedCounter() {
/* 29 */     return this.updateCounterCreated;
/*    */   }
/*    */   
/*    */   public int getChatLineID() {
/* 33 */     return this.chatLineID;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\ChatLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */