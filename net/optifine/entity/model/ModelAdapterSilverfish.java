/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSilverfish;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSilverfish;
/*    */ import net.minecraft.entity.monster.EntitySilverfish;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterSilverfish
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSilverfish() {
/* 17 */     super(EntitySilverfish.class, "silverfish", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSilverfish();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelSilverfish))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelSilverfish modelsilverfish = (ModelSilverfish)model;
/* 34 */     String s = "body";
/*    */     
/* 36 */     if (modelPart.startsWith(s)) {
/*    */       
/* 38 */       ModelRenderer[] amodelrenderer1 = (ModelRenderer[])Reflector.getFieldValue(modelsilverfish, Reflector.ModelSilverfish_bodyParts);
/*    */       
/* 40 */       if (amodelrenderer1 == null)
/*    */       {
/* 42 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 46 */       String s3 = modelPart.substring(s.length());
/* 47 */       int j = Config.parseInt(s3, -1);
/* 48 */       j--; return (
/* 49 */         j >= 0 && j < amodelrenderer1.length) ? amodelrenderer1[j] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 54 */     String s1 = "wing";
/*    */     
/* 56 */     if (modelPart.startsWith(s1)) {
/*    */       
/* 58 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsilverfish, Reflector.ModelSilverfish_wingParts);
/*    */       
/* 60 */       if (amodelrenderer == null)
/*    */       {
/* 62 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 66 */       String s2 = modelPart.substring(s1.length());
/* 67 */       int i = Config.parseInt(s2, -1);
/* 68 */       i--; return (
/* 69 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 82 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 83 */     RenderSilverfish rendersilverfish = new RenderSilverfish(rendermanager);
/* 84 */     rendersilverfish.mainModel = modelBase;
/* 85 */     rendersilverfish.shadowSize = shadowSize;
/* 86 */     return (IEntityRenderer)rendersilverfish;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */