/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public interface ICriterionTrigger<T extends ICriterionInstance>
/*    */ {
/*    */   ResourceLocation func_192163_a();
/*    */   
/*    */   void func_192165_a(PlayerAdvancements paramPlayerAdvancements, Listener<T> paramListener);
/*    */   
/*    */   void func_192164_b(PlayerAdvancements paramPlayerAdvancements, Listener<T> paramListener);
/*    */   
/*    */   void func_192167_a(PlayerAdvancements paramPlayerAdvancements);
/*    */   
/*    */   T func_192166_a(JsonObject paramJsonObject, JsonDeserializationContext paramJsonDeserializationContext);
/*    */   
/*    */   public static class Listener<T extends ICriterionInstance>
/*    */   {
/*    */     private final T field_192160_a;
/*    */     private final Advancement field_192161_b;
/*    */     private final String field_192162_c;
/*    */     
/*    */     public Listener(T p_i47405_1_, Advancement p_i47405_2_, String p_i47405_3_) {
/* 27 */       this.field_192160_a = p_i47405_1_;
/* 28 */       this.field_192161_b = p_i47405_2_;
/* 29 */       this.field_192162_c = p_i47405_3_;
/*    */     }
/*    */ 
/*    */     
/*    */     public T func_192158_a() {
/* 34 */       return this.field_192160_a;
/*    */     }
/*    */ 
/*    */     
/*    */     public void func_192159_a(PlayerAdvancements p_192159_1_) {
/* 39 */       p_192159_1_.func_192750_a(this.field_192161_b, this.field_192162_c);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object p_equals_1_) {
/* 44 */       if (this == p_equals_1_)
/*    */       {
/* 46 */         return true;
/*    */       }
/* 48 */       if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*    */         
/* 50 */         Listener<?> listener = (Listener)p_equals_1_;
/*    */         
/* 52 */         if (!this.field_192160_a.equals(listener.field_192160_a))
/*    */         {
/* 54 */           return false;
/*    */         }
/*    */ 
/*    */         
/* 58 */         return !this.field_192161_b.equals(listener.field_192161_b) ? false : this.field_192162_c.equals(listener.field_192162_c);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 63 */       return false;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 69 */       int i = this.field_192160_a.hashCode();
/* 70 */       i = 31 * i + this.field_192161_b.hashCode();
/* 71 */       i = 31 * i + this.field_192162_c.hashCode();
/* 72 */       return i;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\ICriterionTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */