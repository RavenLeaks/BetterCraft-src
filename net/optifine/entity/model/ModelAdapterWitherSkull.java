/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWitherSkull;
/*    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterWitherSkull
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterWitherSkull() {
/* 17 */     super(EntityWitherSkull.class, "wither_skull", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSkeletonHead();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelSkeletonHead))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelSkeletonHead modelskeletonhead = (ModelSkeletonHead)model;
/* 34 */     return modelPart.equals("head") ? modelskeletonhead.skeletonHead : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 40 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 41 */     RenderWitherSkull renderwitherskull = new RenderWitherSkull(rendermanager);
/*    */     
/* 43 */     if (!Reflector.RenderWitherSkull_model.exists()) {
/*    */       
/* 45 */       Config.warn("Field not found: RenderWitherSkull_model");
/* 46 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 50 */     Reflector.setFieldValue(renderwitherskull, Reflector.RenderWitherSkull_model, modelBase);
/* 51 */     renderwitherskull.shadowSize = shadowSize;
/* 52 */     return (IEntityRenderer)renderwitherskull;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */