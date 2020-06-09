/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelGuardian;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderGuardian;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityGuardian;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterGuardian
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterGuardian() {
/* 17 */     super(EntityGuardian.class, "guardian", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelGuardian();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelGuardian))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelGuardian modelguardian = (ModelGuardian)model;
/*    */     
/* 35 */     if (modelPart.equals("body"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_body);
/*    */     }
/* 39 */     if (modelPart.equals("eye"))
/*    */     {
/* 41 */       return (ModelRenderer)Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_eye);
/*    */     }
/*    */ 
/*    */     
/* 45 */     String s = "spine";
/*    */     
/* 47 */     if (modelPart.startsWith(s)) {
/*    */       
/* 49 */       ModelRenderer[] amodelrenderer1 = (ModelRenderer[])Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_spines);
/*    */       
/* 51 */       if (amodelrenderer1 == null)
/*    */       {
/* 53 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 57 */       String s3 = modelPart.substring(s.length());
/* 58 */       int j = Config.parseInt(s3, -1);
/* 59 */       j--; return (
/* 60 */         j >= 0 && j < amodelrenderer1.length) ? amodelrenderer1[j] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 65 */     String s1 = "tail";
/*    */     
/* 67 */     if (modelPart.startsWith(s1)) {
/*    */       
/* 69 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelguardian, Reflector.ModelGuardian_tail);
/*    */       
/* 71 */       if (amodelrenderer == null)
/*    */       {
/* 73 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 77 */       String s2 = modelPart.substring(s1.length());
/* 78 */       int i = Config.parseInt(s2, -1);
/* 79 */       i--; return (
/* 80 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 85 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 94 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 95 */     RenderGuardian renderguardian = new RenderGuardian(rendermanager);
/* 96 */     renderguardian.mainModel = modelBase;
/* 97 */     renderguardian.shadowSize = shadowSize;
/* 98 */     return (IEntityRenderer)renderguardian;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */