/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelLeashKnot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLeashKnot;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterLeadKnot
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterLeadKnot() {
/* 17 */     super(EntityLeashKnot.class, "lead_knot", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelLeashKnot();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelLeashKnot))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelLeashKnot modelleashknot = (ModelLeashKnot)model;
/* 34 */     return modelPart.equals("knot") ? modelleashknot.knotRenderer : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 40 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 41 */     RenderLeashKnot renderleashknot = new RenderLeashKnot(rendermanager);
/*    */     
/* 43 */     if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
/*    */       
/* 45 */       Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
/* 46 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 50 */     Reflector.setFieldValue(renderleashknot, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
/* 51 */     renderleashknot.shadowSize = shadowSize;
/* 52 */     return (IEntityRenderer)renderleashknot;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterLeadKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */