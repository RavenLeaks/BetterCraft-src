/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ public class MinMaxBounds
/*    */ {
/* 10 */   public static final MinMaxBounds field_192516_a = new MinMaxBounds(null, null);
/*    */   
/*    */   private final Float field_192517_b;
/*    */   private final Float field_192518_c;
/*    */   
/*    */   public MinMaxBounds(@Nullable Float p_i47431_1_, @Nullable Float p_i47431_2_) {
/* 16 */     this.field_192517_b = p_i47431_1_;
/* 17 */     this.field_192518_c = p_i47431_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_192514_a(float p_192514_1_) {
/* 22 */     if (this.field_192517_b != null && this.field_192517_b.floatValue() > p_192514_1_)
/*    */     {
/* 24 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 28 */     return !(this.field_192518_c != null && this.field_192518_c.floatValue() < p_192514_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_192513_a(double p_192513_1_) {
/* 34 */     if (this.field_192517_b != null && (this.field_192517_b.floatValue() * this.field_192517_b.floatValue()) > p_192513_1_)
/*    */     {
/* 36 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 40 */     return !(this.field_192518_c != null && (this.field_192518_c.floatValue() * this.field_192518_c.floatValue()) < p_192513_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static MinMaxBounds func_192515_a(@Nullable JsonElement p_192515_0_) {
/* 46 */     if (p_192515_0_ != null && !p_192515_0_.isJsonNull()) {
/*    */       
/* 48 */       if (JsonUtils.isNumber(p_192515_0_)) {
/*    */         
/* 50 */         float f2 = JsonUtils.getFloat(p_192515_0_, "value");
/* 51 */         return new MinMaxBounds(Float.valueOf(f2), Float.valueOf(f2));
/*    */       } 
/*    */ 
/*    */       
/* 55 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_192515_0_, "value");
/* 56 */       Float f = jsonobject.has("min") ? Float.valueOf(JsonUtils.getFloat(jsonobject, "min")) : null;
/* 57 */       Float f1 = jsonobject.has("max") ? Float.valueOf(JsonUtils.getFloat(jsonobject, "max")) : null;
/* 58 */       return new MinMaxBounds(f, f1);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 63 */     return field_192516_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\MinMaxBounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */