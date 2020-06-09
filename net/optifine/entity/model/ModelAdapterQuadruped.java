/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelQuadruped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public abstract class ModelAdapterQuadruped
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterQuadruped(Class entityClass, String name, float shadowSize) {
/* 11 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 16 */     if (!(model instanceof ModelQuadruped))
/*    */     {
/* 18 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 22 */     ModelQuadruped modelquadruped = (ModelQuadruped)model;
/*    */     
/* 24 */     if (modelPart.equals("head"))
/*    */     {
/* 26 */       return modelquadruped.head;
/*    */     }
/* 28 */     if (modelPart.equals("body"))
/*    */     {
/* 30 */       return modelquadruped.body;
/*    */     }
/* 32 */     if (modelPart.equals("leg1"))
/*    */     {
/* 34 */       return modelquadruped.leg1;
/*    */     }
/* 36 */     if (modelPart.equals("leg2"))
/*    */     {
/* 38 */       return modelquadruped.leg2;
/*    */     }
/* 40 */     if (modelPart.equals("leg3"))
/*    */     {
/* 42 */       return modelquadruped.leg3;
/*    */     }
/*    */ 
/*    */     
/* 46 */     return modelPart.equals("leg4") ? modelquadruped.leg4 : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterQuadruped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */