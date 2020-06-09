/*    */ package net.minecraft.network.datasync;
/*    */ 
/*    */ 
/*    */ public class DataParameter<T>
/*    */ {
/*    */   private int id;
/*    */   private final DataSerializer<T> serializer;
/*    */   
/*    */   public DataParameter(int idIn, DataSerializer<T> serializerIn) {
/* 10 */     this.id = idIn;
/* 11 */     this.serializer = serializerIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 16 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public DataSerializer<T> getSerializer() {
/* 21 */     return this.serializer;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 26 */     if (this == p_equals_1_)
/*    */     {
/* 28 */       return true;
/*    */     }
/* 30 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*    */       
/* 32 */       DataParameter<?> dataparameter = (DataParameter)p_equals_1_;
/* 33 */       return (this.id == dataparameter.id);
/*    */     } 
/*    */ 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 43 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int i) {
/* 47 */     this.id = i;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\datasync\DataParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */