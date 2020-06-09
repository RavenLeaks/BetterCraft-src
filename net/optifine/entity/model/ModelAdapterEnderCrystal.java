/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderCrystal
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterEnderCrystal() {
/* 18 */     this("end_crystal");
/*    */   }
/*    */ 
/*    */   
/*    */   protected ModelAdapterEnderCrystal(String name) {
/* 23 */     super(EntityEnderCrystal.class, name, 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 28 */     return (ModelBase)new ModelEnderCrystal(0.0F, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 33 */     if (!(model instanceof ModelEnderCrystal))
/*    */     {
/* 35 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 39 */     ModelEnderCrystal modelendercrystal = (ModelEnderCrystal)model;
/*    */     
/* 41 */     if (modelPart.equals("cube"))
/*    */     {
/* 43 */       return (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 0);
/*    */     }
/* 45 */     if (modelPart.equals("glass"))
/*    */     {
/* 47 */       return (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 1);
/*    */     }
/*    */ 
/*    */     
/* 51 */     return modelPart.equals("base") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 2) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 58 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 59 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntityEnderCrystal.class);
/*    */     
/* 61 */     if (!(render instanceof RenderEnderCrystal)) {
/*    */       
/* 63 */       Config.warn("Not an instance of RenderEnderCrystal: " + render);
/* 64 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 68 */     RenderEnderCrystal renderendercrystal = (RenderEnderCrystal)render;
/*    */     
/* 70 */     if (!Reflector.RenderEnderCrystal_modelEnderCrystal.exists()) {
/*    */       
/* 72 */       Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
/* 73 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 77 */     Reflector.setFieldValue(renderendercrystal, Reflector.RenderEnderCrystal_modelEnderCrystal, modelBase);
/* 78 */     renderendercrystal.shadowSize = shadowSize;
/* 79 */     return (IEntityRenderer)renderendercrystal;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */