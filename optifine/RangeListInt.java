/*    */ package optifine;
/*    */ 
/*    */ public class RangeListInt
/*    */ {
/*  5 */   private RangeInt[] ranges = new RangeInt[0];
/*    */ 
/*    */   
/*    */   public void addRange(RangeInt p_addRange_1_) {
/*  9 */     this.ranges = (RangeInt[])Config.addObjectToArray((Object[])this.ranges, p_addRange_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInRange(int p_isInRange_1_) {
/* 14 */     for (int i = 0; i < this.ranges.length; i++) {
/*    */       
/* 16 */       RangeInt rangeint = this.ranges[i];
/*    */       
/* 18 */       if (rangeint.isInRange(p_isInRange_1_))
/*    */       {
/* 20 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCountRanges() {
/* 29 */     return this.ranges.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public RangeInt getRange(int p_getRange_1_) {
/* 34 */     return this.ranges[p_getRange_1_];
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuffer stringbuffer = new StringBuffer();
/* 40 */     stringbuffer.append("[");
/*    */     
/* 42 */     for (int i = 0; i < this.ranges.length; i++) {
/*    */       
/* 44 */       RangeInt rangeint = this.ranges[i];
/*    */       
/* 46 */       if (i > 0)
/*    */       {
/* 48 */         stringbuffer.append(", ");
/*    */       }
/*    */       
/* 51 */       stringbuffer.append(rangeint.toString());
/*    */     } 
/*    */     
/* 54 */     stringbuffer.append("]");
/* 55 */     return stringbuffer.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\RangeListInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */