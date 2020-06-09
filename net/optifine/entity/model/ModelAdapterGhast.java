/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelGhast;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderGhast;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterGhast
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterGhast() {
/* 17 */     super(EntityGhast.class, "ghast", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelGhast();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelGhast))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelGhast modelghast = (ModelGhast)model;
/*    */     
/* 35 */     if (modelPart.equals("body"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modelghast, Reflector.ModelGhast_body);
/*    */     }
/*    */ 
/*    */     
/* 41 */     String s = "tentacle";
/*    */     
/* 43 */     if (modelPart.startsWith(s)) {
/*    */       
/* 45 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelghast, Reflector.ModelGhast_tentacles);
/*    */       
/* 47 */       if (amodelrenderer == null)
/*    */       {
/* 49 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 53 */       String s1 = modelPart.substring(s.length());
/* 54 */       int i = Config.parseInt(s1, -1);
/* 55 */       i--; return (
/* 56 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 69 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 70 */     RenderGhast renderghast = new RenderGhast(rendermanager);
/* 71 */     renderghast.mainModel = modelBase;
/* 72 */     renderghast.shadowSize = shadowSize;
/* 73 */     return (IEntityRenderer)renderghast;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */