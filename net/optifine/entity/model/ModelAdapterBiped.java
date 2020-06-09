/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public abstract class ModelAdapterBiped
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBiped(Class entityClass, String name, float shadowSize) {
/* 11 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 16 */     if (!(model instanceof ModelBiped))
/*    */     {
/* 18 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 22 */     ModelBiped modelbiped = (ModelBiped)model;
/*    */     
/* 24 */     if (modelPart.equals("head"))
/*    */     {
/* 26 */       return modelbiped.bipedHead;
/*    */     }
/* 28 */     if (modelPart.equals("headwear"))
/*    */     {
/* 30 */       return modelbiped.bipedHeadwear;
/*    */     }
/* 32 */     if (modelPart.equals("body"))
/*    */     {
/* 34 */       return modelbiped.bipedBody;
/*    */     }
/* 36 */     if (modelPart.equals("left_arm"))
/*    */     {
/* 38 */       return modelbiped.bipedLeftArm;
/*    */     }
/* 40 */     if (modelPart.equals("right_arm"))
/*    */     {
/* 42 */       return modelbiped.bipedRightArm;
/*    */     }
/* 44 */     if (modelPart.equals("left_leg"))
/*    */     {
/* 46 */       return modelbiped.bipedLeftLeg;
/*    */     }
/*    */ 
/*    */     
/* 50 */     return modelPart.equals("right_leg") ? modelbiped.bipedRightLeg : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */