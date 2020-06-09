/*    */ package net.minecraft.util.text;
/*    */ 
/*    */ public class TextComponentString
/*    */   extends TextComponentBase
/*    */ {
/*    */   private final String text;
/*    */   
/*    */   public TextComponentString(String msg) {
/*  9 */     this.text = msg;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getText() {
/* 18 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnformattedComponentText() {
/* 27 */     return this.text;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TextComponentString createCopy() {
/* 35 */     TextComponentString textcomponentstring = new TextComponentString(this.text);
/* 36 */     textcomponentstring.setStyle(getStyle().createShallowCopy());
/*    */     
/* 38 */     for (ITextComponent itextcomponent : getSiblings())
/*    */     {
/* 40 */       textcomponentstring.appendSibling(itextcomponent.createCopy());
/*    */     }
/*    */     
/* 43 */     return textcomponentstring;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 48 */     if (this == p_equals_1_)
/*    */     {
/* 50 */       return true;
/*    */     }
/* 52 */     if (!(p_equals_1_ instanceof TextComponentString))
/*    */     {
/* 54 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 58 */     TextComponentString textcomponentstring = (TextComponentString)p_equals_1_;
/* 59 */     return (this.text.equals(textcomponentstring.getText()) && super.equals(p_equals_1_));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + getStyle() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */