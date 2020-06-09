/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelVex;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderVex;
/*    */ import net.minecraft.entity.monster.EntityVex;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterVex
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterVex() {
/* 16 */     super(EntityVex.class, "vex", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 21 */     if (!(model instanceof ModelVex))
/*    */     {
/* 23 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 27 */     ModelRenderer modelrenderer = super.getModelRenderer(model, modelPart);
/*    */     
/* 29 */     if (modelrenderer != null)
/*    */     {
/* 31 */       return modelrenderer;
/*    */     }
/*    */ 
/*    */     
/* 35 */     ModelVex modelvex = (ModelVex)model;
/*    */     
/* 37 */     if (modelPart.equals("left_wing"))
/*    */     {
/* 39 */       return (ModelRenderer)Reflector.getFieldValue(modelvex, Reflector.ModelVex_leftWing);
/*    */     }
/*    */ 
/*    */     
/* 43 */     return modelPart.equals("right_wing") ? (ModelRenderer)Reflector.getFieldValue(modelvex, Reflector.ModelVex_rightWing) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 51 */     return (ModelBase)new ModelVex();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 56 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 57 */     RenderVex rendervex = new RenderVex(rendermanager);
/* 58 */     rendervex.mainModel = modelBase;
/* 59 */     rendervex.shadowSize = shadowSize;
/* 60 */     return (IEntityRenderer)rendervex;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterVex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */