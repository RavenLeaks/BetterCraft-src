/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class ActionResult<T>
/*    */ {
/*    */   private final EnumActionResult type;
/*    */   private final T result;
/*    */   
/*    */   public ActionResult(EnumActionResult typeIn, T resultIn) {
/* 10 */     this.type = typeIn;
/* 11 */     this.result = resultIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumActionResult getType() {
/* 16 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public T getResult() {
/* 21 */     return this.result;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ActionResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */