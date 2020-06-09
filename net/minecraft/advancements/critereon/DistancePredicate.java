/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class DistancePredicate
/*    */ {
/* 11 */   public static final DistancePredicate field_193423_a = new DistancePredicate(MinMaxBounds.field_192516_a, MinMaxBounds.field_192516_a, MinMaxBounds.field_192516_a, MinMaxBounds.field_192516_a, MinMaxBounds.field_192516_a);
/*    */   
/*    */   private final MinMaxBounds field_193424_b;
/*    */   private final MinMaxBounds field_193425_c;
/*    */   private final MinMaxBounds field_193426_d;
/*    */   private final MinMaxBounds field_193427_e;
/*    */   private final MinMaxBounds field_193428_f;
/*    */   
/*    */   public DistancePredicate(MinMaxBounds p_i47542_1_, MinMaxBounds p_i47542_2_, MinMaxBounds p_i47542_3_, MinMaxBounds p_i47542_4_, MinMaxBounds p_i47542_5_) {
/* 20 */     this.field_193424_b = p_i47542_1_;
/* 21 */     this.field_193425_c = p_i47542_2_;
/* 22 */     this.field_193426_d = p_i47542_3_;
/* 23 */     this.field_193427_e = p_i47542_4_;
/* 24 */     this.field_193428_f = p_i47542_5_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_193422_a(double p_193422_1_, double p_193422_3_, double p_193422_5_, double p_193422_7_, double p_193422_9_, double p_193422_11_) {
/* 29 */     float f = (float)(p_193422_1_ - p_193422_7_);
/* 30 */     float f1 = (float)(p_193422_3_ - p_193422_9_);
/* 31 */     float f2 = (float)(p_193422_5_ - p_193422_11_);
/*    */     
/* 33 */     if (this.field_193424_b.func_192514_a(MathHelper.abs(f)) && this.field_193425_c.func_192514_a(MathHelper.abs(f1)) && this.field_193426_d.func_192514_a(MathHelper.abs(f2))) {
/*    */       
/* 35 */       if (!this.field_193427_e.func_192513_a((f * f + f2 * f2)))
/*    */       {
/* 37 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 41 */       return this.field_193428_f.func_192513_a((f * f + f1 * f1 + f2 * f2));
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static DistancePredicate func_193421_a(@Nullable JsonElement p_193421_0_) {
/* 52 */     if (p_193421_0_ != null && !p_193421_0_.isJsonNull()) {
/*    */       
/* 54 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_193421_0_, "distance");
/* 55 */       MinMaxBounds minmaxbounds = MinMaxBounds.func_192515_a(jsonobject.get("x"));
/* 56 */       MinMaxBounds minmaxbounds1 = MinMaxBounds.func_192515_a(jsonobject.get("y"));
/* 57 */       MinMaxBounds minmaxbounds2 = MinMaxBounds.func_192515_a(jsonobject.get("z"));
/* 58 */       MinMaxBounds minmaxbounds3 = MinMaxBounds.func_192515_a(jsonobject.get("horizontal"));
/* 59 */       MinMaxBounds minmaxbounds4 = MinMaxBounds.func_192515_a(jsonobject.get("absolute"));
/* 60 */       return new DistancePredicate(minmaxbounds, minmaxbounds1, minmaxbounds2, minmaxbounds3, minmaxbounds4);
/*    */     } 
/*    */ 
/*    */     
/* 64 */     return field_193423_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\DistancePredicate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */