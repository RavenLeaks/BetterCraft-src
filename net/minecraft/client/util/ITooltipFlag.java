/*    */ package net.minecraft.client.util;
/*    */ 
/*    */ public interface ITooltipFlag
/*    */ {
/*    */   boolean func_194127_a();
/*    */   
/*    */   public enum TooltipFlags
/*    */     implements ITooltipFlag {
/*  9 */     NORMAL(false),
/* 10 */     ADVANCED(true);
/*    */     
/*    */     final boolean field_194131_c;
/*    */ 
/*    */     
/*    */     TooltipFlags(boolean p_i47611_3_) {
/* 16 */       this.field_194131_c = p_i47611_3_;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean func_194127_a() {
/* 21 */       return this.field_194131_c;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\ITooltipFlag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */