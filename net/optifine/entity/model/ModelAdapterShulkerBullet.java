/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelShulkerBullet;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderShulkerBullet;
/*    */ import net.minecraft.entity.projectile.EntityShulkerBullet;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterShulkerBullet
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterShulkerBullet() {
/* 17 */     super(EntityShulkerBullet.class, "shulker_bullet", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelShulkerBullet();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelShulkerBullet))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelShulkerBullet modelshulkerbullet = (ModelShulkerBullet)model;
/* 34 */     return modelPart.equals("bullet") ? modelshulkerbullet.renderer : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 40 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 41 */     RenderShulkerBullet rendershulkerbullet = new RenderShulkerBullet(rendermanager);
/*    */     
/* 43 */     if (!Reflector.RenderShulkerBullet_model.exists()) {
/*    */       
/* 45 */       Config.warn("Field not found: RenderShulkerBullet.model");
/* 46 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 50 */     Reflector.setFieldValue(rendershulkerbullet, Reflector.RenderShulkerBullet_model, modelBase);
/* 51 */     rendershulkerbullet.shadowSize = shadowSize;
/* 52 */     return (IEntityRenderer)rendershulkerbullet;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterShulkerBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */