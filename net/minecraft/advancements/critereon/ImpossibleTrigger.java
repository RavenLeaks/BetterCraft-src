/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonObject;
/*    */ import net.minecraft.advancements.ICriterionInstance;
/*    */ import net.minecraft.advancements.ICriterionTrigger;
/*    */ import net.minecraft.advancements.PlayerAdvancements;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ImpossibleTrigger implements ICriterionTrigger<ImpossibleTrigger.Instance> {
/* 11 */   private static final ResourceLocation field_192205_a = new ResourceLocation("impossible");
/*    */ 
/*    */   
/*    */   public ResourceLocation func_192163_a() {
/* 15 */     return field_192205_a;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener<Instance> p_192165_2_) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener<Instance> p_192164_2_) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_192167_a(PlayerAdvancements p_192167_1_) {}
/*    */ 
/*    */   
/*    */   public Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
/* 32 */     return new Instance();
/*    */   }
/*    */   
/*    */   public static class Instance
/*    */     extends AbstractCriterionInstance
/*    */   {
/*    */     public Instance() {
/* 39 */       super(ImpossibleTrigger.field_192205_a);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\ImpossibleTrigger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */