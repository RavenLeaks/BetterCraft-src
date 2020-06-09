/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ public enum EnumTokenType
/*    */ {
/*  5 */   IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "0123456789_:."),
/*  6 */   CONSTANT("0123456789", "."),
/*  7 */   OPERATOR("+-*/%", 1),
/*  8 */   COMMA(",", 1),
/*  9 */   BRACKET_OPEN("(", 1),
/* 10 */   BRACKET_CLOSE(")", 1);
/*    */   private String charsFirst;
/*    */   private String charsExt;
/*    */   
/*    */   static {
/* 15 */     VALUES = values();
/*    */   }
/*    */   private int maxLen; public static final EnumTokenType[] VALUES;
/*    */   EnumTokenType(String charsFirst) {
/* 19 */     this.charsFirst = charsFirst;
/* 20 */     this.charsExt = "";
/*    */   }
/*    */ 
/*    */   
/*    */   EnumTokenType(String charsFirst, int maxLen) {
/* 25 */     this.charsFirst = charsFirst;
/* 26 */     this.charsExt = "";
/* 27 */     this.maxLen = maxLen;
/*    */   }
/*    */ 
/*    */   
/*    */   EnumTokenType(String charsFirst, String charsExt) {
/* 32 */     this.charsFirst = charsFirst;
/* 33 */     this.charsExt = charsExt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCharsFirst() {
/* 38 */     return this.charsFirst;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCharsExt() {
/* 43 */     return this.charsExt;
/*    */   }
/*    */ 
/*    */   
/*    */   public static EnumTokenType getTypeByFirstChar(char ch) {
/* 48 */     for (int i = 0; i < VALUES.length; i++) {
/*    */       
/* 50 */       EnumTokenType enumtokentype = VALUES[i];
/*    */       
/* 52 */       if (enumtokentype.getCharsFirst().indexOf(ch) >= 0)
/*    */       {
/* 54 */         return enumtokentype;
/*    */       }
/*    */     } 
/*    */     
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasChar(char ch) {
/* 63 */     if (getCharsFirst().indexOf(ch) >= 0)
/*    */     {
/* 65 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 69 */     return (getCharsExt().indexOf(ch) >= 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLen() {
/* 75 */     return this.maxLen;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\EnumTokenType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */