/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSpider;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSpider;
/*    */ import net.minecraft.entity.monster.EntitySpider;
/*    */ 
/*    */ public class ModelAdapterSpider
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSpider() {
/* 15 */     super(EntitySpider.class, "spider", 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ModelAdapterSpider(Class entityClass, String name, float shadowSize) {
/* 20 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 25 */     return (ModelBase)new ModelSpider();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 30 */     if (!(model instanceof ModelSpider))
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 36 */     ModelSpider modelspider = (ModelSpider)model;
/*    */     
/* 38 */     if (modelPart.equals("head"))
/*    */     {
/* 40 */       return modelspider.spiderHead;
/*    */     }
/* 42 */     if (modelPart.equals("neck"))
/*    */     {
/* 44 */       return modelspider.spiderNeck;
/*    */     }
/* 46 */     if (modelPart.equals("body"))
/*    */     {
/* 48 */       return modelspider.spiderBody;
/*    */     }
/* 50 */     if (modelPart.equals("leg1"))
/*    */     {
/* 52 */       return modelspider.spiderLeg1;
/*    */     }
/* 54 */     if (modelPart.equals("leg2"))
/*    */     {
/* 56 */       return modelspider.spiderLeg2;
/*    */     }
/* 58 */     if (modelPart.equals("leg3"))
/*    */     {
/* 60 */       return modelspider.spiderLeg3;
/*    */     }
/* 62 */     if (modelPart.equals("leg4"))
/*    */     {
/* 64 */       return modelspider.spiderLeg4;
/*    */     }
/* 66 */     if (modelPart.equals("leg5"))
/*    */     {
/* 68 */       return modelspider.spiderLeg5;
/*    */     }
/* 70 */     if (modelPart.equals("leg6"))
/*    */     {
/* 72 */       return modelspider.spiderLeg6;
/*    */     }
/* 74 */     if (modelPart.equals("leg7"))
/*    */     {
/* 76 */       return modelspider.spiderLeg7;
/*    */     }
/*    */ 
/*    */     
/* 80 */     return modelPart.equals("leg8") ? modelspider.spiderLeg8 : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 87 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 88 */     RenderSpider renderspider = new RenderSpider(rendermanager);
/* 89 */     renderspider.mainModel = modelBase;
/* 90 */     renderspider.shadowSize = shadowSize;
/* 91 */     return (IEntityRenderer)renderspider;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */