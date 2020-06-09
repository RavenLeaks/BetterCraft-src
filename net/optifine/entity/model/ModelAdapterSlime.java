/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSlime;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSlime;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterSlime
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSlime() {
/* 16 */     super(EntitySlime.class, "slime", 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelSlime(16);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelSlime))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelSlime modelslime = (ModelSlime)model;
/*    */     
/* 34 */     if (modelPart.equals("body"))
/*    */     {
/* 36 */       return (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 0);
/*    */     }
/* 38 */     if (modelPart.equals("left_eye"))
/*    */     {
/* 40 */       return (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 1);
/*    */     }
/* 42 */     if (modelPart.equals("right_eye"))
/*    */     {
/* 44 */       return (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 2);
/*    */     }
/*    */ 
/*    */     
/* 48 */     return modelPart.equals("mouth") ? (ModelRenderer)Reflector.getFieldValue(modelslime, Reflector.ModelSlime_ModelRenderers, 3) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 55 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 56 */     RenderSlime renderslime = new RenderSlime(rendermanager);
/* 57 */     renderslime.mainModel = modelBase;
/* 58 */     renderslime.shadowSize = shadowSize;
/* 59 */     return (IEntityRenderer)renderslime;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */