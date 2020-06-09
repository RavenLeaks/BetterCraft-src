/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSquid;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSquid;
/*    */ import net.minecraft.entity.passive.EntitySquid;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterSquid
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSquid() {
/* 17 */     super(EntitySquid.class, "squid", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSquid();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelSquid))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelSquid modelsquid = (ModelSquid)model;
/*    */     
/* 35 */     if (modelPart.equals("body"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_body);
/*    */     }
/*    */ 
/*    */     
/* 41 */     String s = "tentacle";
/*    */     
/* 43 */     if (modelPart.startsWith(s)) {
/*    */       
/* 45 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_tentacles);
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
/* 70 */     RenderSquid rendersquid = new RenderSquid(rendermanager);
/* 71 */     rendersquid.mainModel = modelBase;
/* 72 */     rendersquid.shadowSize = shadowSize;
/* 73 */     return (IEntityRenderer)rendersquid;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */