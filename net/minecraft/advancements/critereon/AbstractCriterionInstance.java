/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import net.minecraft.advancements.ICriterionInstance;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class AbstractCriterionInstance
/*    */   implements ICriterionInstance
/*    */ {
/*    */   private final ResourceLocation field_192245_a;
/*    */   
/*    */   public AbstractCriterionInstance(ResourceLocation p_i47465_1_) {
/* 12 */     this.field_192245_a = p_i47465_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation func_192244_a() {
/* 17 */     return this.field_192245_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "AbstractCriterionInstance{criterion=" + this.field_192245_a + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\advancements\critereon\AbstractCriterionInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */