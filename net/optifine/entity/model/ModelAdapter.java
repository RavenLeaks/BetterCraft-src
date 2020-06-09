/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ public abstract class ModelAdapter
/*    */ {
/*    */   private Class entityClass;
/*    */   private String name;
/*    */   private float shadowSize;
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize) {
/* 14 */     this.entityClass = entityClass;
/* 15 */     this.name = name;
/* 16 */     this.shadowSize = shadowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getEntityClass() {
/* 21 */     return this.entityClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 26 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadowSize() {
/* 31 */     return this.shadowSize;
/*    */   }
/*    */   
/*    */   public abstract ModelBase makeModel();
/*    */   
/*    */   public abstract ModelRenderer getModelRenderer(ModelBase paramModelBase, String paramString);
/*    */   
/*    */   public abstract IEntityRenderer makeEntityRender(ModelBase paramModelBase, float paramFloat);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */