/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelMinecart;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecart
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterMinecart() {
/* 17 */     super(EntityMinecart.class, "minecart", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ModelAdapterMinecart(Class entityClass, String name, float shadow) {
/* 22 */     super(entityClass, name, shadow);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 27 */     return (ModelBase)new ModelMinecart();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 32 */     if (!(model instanceof ModelMinecart))
/*    */     {
/* 34 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 38 */     ModelMinecart modelminecart = (ModelMinecart)model;
/*    */     
/* 40 */     if (modelPart.equals("bottom"))
/*    */     {
/* 42 */       return modelminecart.sideModels[0];
/*    */     }
/* 44 */     if (modelPart.equals("back"))
/*    */     {
/* 46 */       return modelminecart.sideModels[1];
/*    */     }
/* 48 */     if (modelPart.equals("front"))
/*    */     {
/* 50 */       return modelminecart.sideModels[2];
/*    */     }
/* 52 */     if (modelPart.equals("right"))
/*    */     {
/* 54 */       return modelminecart.sideModels[3];
/*    */     }
/* 56 */     if (modelPart.equals("left"))
/*    */     {
/* 58 */       return modelminecart.sideModels[4];
/*    */     }
/*    */ 
/*    */     
/* 62 */     return modelPart.equals("dirt") ? modelminecart.sideModels[5] : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 69 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 70 */     RenderMinecart renderminecart = new RenderMinecart(rendermanager);
/*    */     
/* 72 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/*    */       
/* 74 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 75 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 79 */     Reflector.setFieldValue(renderminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 80 */     renderminecart.shadowSize = shadowSize;
/* 81 */     return (IEntityRenderer)renderminecart;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */