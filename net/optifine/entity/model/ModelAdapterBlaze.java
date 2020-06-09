/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBlaze;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBlaze;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityBlaze;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterBlaze
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBlaze() {
/* 17 */     super(EntityBlaze.class, "blaze", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBlaze();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBlaze))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBlaze modelblaze = (ModelBlaze)model;
/*    */     
/* 35 */     if (modelPart.equals("head"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modelblaze, Reflector.ModelBlaze_blazeHead);
/*    */     }
/*    */ 
/*    */     
/* 41 */     String s = "stick";
/*    */     
/* 43 */     if (modelPart.startsWith(s)) {
/*    */       
/* 45 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelblaze, Reflector.ModelBlaze_blazeSticks);
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
/* 70 */     RenderBlaze renderblaze = new RenderBlaze(rendermanager);
/* 71 */     renderblaze.mainModel = modelBase;
/* 72 */     renderblaze.shadowSize = shadowSize;
/* 73 */     return (IEntityRenderer)renderblaze;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterBlaze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */