/*    */ package net.minecraft.util.text;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextComponentSelector
/*    */   extends TextComponentBase
/*    */ {
/*    */   private final String selector;
/*    */   
/*    */   public TextComponentSelector(String selectorIn) {
/* 12 */     this.selector = selectorIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSelector() {
/* 20 */     return this.selector;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnformattedComponentText() {
/* 29 */     return this.selector;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TextComponentSelector createCopy() {
/* 37 */     TextComponentSelector textcomponentselector = new TextComponentSelector(this.selector);
/* 38 */     textcomponentselector.setStyle(getStyle().createShallowCopy());
/*    */     
/* 40 */     for (ITextComponent itextcomponent : getSiblings())
/*    */     {
/* 42 */       textcomponentselector.appendSibling(itextcomponent.createCopy());
/*    */     }
/*    */     
/* 45 */     return textcomponentselector;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 50 */     if (this == p_equals_1_)
/*    */     {
/* 52 */       return true;
/*    */     }
/* 54 */     if (!(p_equals_1_ instanceof TextComponentSelector))
/*    */     {
/* 56 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 60 */     TextComponentSelector textcomponentselector = (TextComponentSelector)p_equals_1_;
/* 61 */     return (this.selector.equals(textcomponentselector.selector) && super.equals(p_equals_1_));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return "SelectorComponent{pattern='" + this.selector + '\'' + ", siblings=" + this.siblings + ", style=" + getStyle() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */