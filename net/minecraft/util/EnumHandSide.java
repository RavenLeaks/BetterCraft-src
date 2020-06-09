/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.TextComponentTranslation;
/*    */ 
/*    */ public enum EnumHandSide
/*    */ {
/*  8 */   LEFT((ITextComponent)new TextComponentTranslation("options.mainHand.left", new Object[0])),
/*  9 */   RIGHT((ITextComponent)new TextComponentTranslation("options.mainHand.right", new Object[0]));
/*    */   
/*    */   private final ITextComponent handName;
/*    */ 
/*    */   
/*    */   EnumHandSide(ITextComponent nameIn) {
/* 15 */     this.handName = nameIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumHandSide opposite() {
/* 20 */     return (this == LEFT) ? RIGHT : LEFT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 25 */     return this.handName.getUnformattedText();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\EnumHandSide.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */