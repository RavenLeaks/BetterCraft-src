/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEvokerFangs;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderEvokerFangs;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.projectile.EntityEvokerFangs;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterEvokerFangs
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterEvokerFangs() {
/* 17 */     super(EntityEvokerFangs.class, "evocation_fangs", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelEvokerFangs();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelEvokerFangs))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelEvokerFangs modelevokerfangs = (ModelEvokerFangs)model;
/*    */     
/* 35 */     if (modelPart.equals("base"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modelevokerfangs, Reflector.ModelEvokerFangs_ModelRenderers, 0);
/*    */     }
/* 39 */     if (modelPart.equals("upper_jaw"))
/*    */     {
/* 41 */       return (ModelRenderer)Reflector.getFieldValue(modelevokerfangs, Reflector.ModelEvokerFangs_ModelRenderers, 1);
/*    */     }
/*    */ 
/*    */     
/* 45 */     return modelPart.equals("lower_jaw") ? (ModelRenderer)Reflector.getFieldValue(modelevokerfangs, Reflector.ModelEvokerFangs_ModelRenderers, 2) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 52 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 53 */     RenderEvokerFangs renderevokerfangs = new RenderEvokerFangs(rendermanager);
/*    */     
/* 55 */     if (!Reflector.RenderEvokerFangs_model.exists()) {
/*    */       
/* 57 */       Config.warn("Field not found: RenderEvokerFangs.model");
/* 58 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 62 */     Reflector.setFieldValue(renderevokerfangs, Reflector.RenderEvokerFangs_model, modelBase);
/* 63 */     renderevokerfangs.shadowSize = shadowSize;
/* 64 */     return (IEntityRenderer)renderevokerfangs;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterEvokerFangs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */