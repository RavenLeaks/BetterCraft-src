/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ 
/*    */ public class Token
/*    */ {
/*    */   private EnumTokenType type;
/*    */   private String text;
/*    */   
/*    */   public Token(EnumTokenType type, String text) {
/* 10 */     this.type = type;
/* 11 */     this.text = text;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumTokenType getType() {
/* 16 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 21 */     return this.text;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\Token.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */