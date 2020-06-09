/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBat;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityBat;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterBat
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBat() {
/* 16 */     super(EntityBat.class, "bat", 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelBat();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelBat))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelBat modelbat = (ModelBat)model;
/*    */     
/* 34 */     if (modelPart.equals("head"))
/*    */     {
/* 36 */       return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 0);
/*    */     }
/* 38 */     if (modelPart.equals("body"))
/*    */     {
/* 40 */       return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 1);
/*    */     }
/* 42 */     if (modelPart.equals("right_wing"))
/*    */     {
/* 44 */       return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 2);
/*    */     }
/* 46 */     if (modelPart.equals("left_wing"))
/*    */     {
/* 48 */       return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 3);
/*    */     }
/* 50 */     if (modelPart.equals("outer_right_wing"))
/*    */     {
/* 52 */       return (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 4);
/*    */     }
/*    */ 
/*    */     
/* 56 */     return modelPart.equals("outer_left_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 5) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 63 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 64 */     RenderBat renderbat = new RenderBat(rendermanager);
/* 65 */     renderbat.mainModel = modelBase;
/* 66 */     renderbat.shadowSize = shadowSize;
/* 67 */     return (IEntityRenderer)renderbat;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */