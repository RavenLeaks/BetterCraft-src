/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIllager;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public abstract class ModelAdapterIllager
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterIllager(Class entityClass, String name, float shadowSize) {
/* 11 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 16 */     if (!(model instanceof ModelIllager))
/*    */     {
/* 18 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 22 */     ModelIllager modelillager = (ModelIllager)model;
/*    */     
/* 24 */     if (modelPart.equals("head"))
/*    */     {
/* 26 */       return modelillager.field_191217_a;
/*    */     }
/* 28 */     if (modelPart.equals("body"))
/*    */     {
/* 30 */       return modelillager.field_191218_b;
/*    */     }
/* 32 */     if (modelPart.equals("arms"))
/*    */     {
/* 34 */       return modelillager.field_191219_c;
/*    */     }
/* 36 */     if (modelPart.equals("left_leg"))
/*    */     {
/* 38 */       return modelillager.field_191221_e;
/*    */     }
/* 40 */     if (modelPart.equals("right_leg"))
/*    */     {
/* 42 */       return modelillager.field_191220_d;
/*    */     }
/* 44 */     if (modelPart.equals("nose"))
/*    */     {
/* 46 */       return modelillager.field_191222_f;
/*    */     }
/* 48 */     if (modelPart.equals("left_arm"))
/*    */     {
/* 50 */       return modelillager.field_191224_h;
/*    */     }
/*    */ 
/*    */     
/* 54 */     return modelPart.equals("right_arm") ? modelillager.field_191223_g : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterIllager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */