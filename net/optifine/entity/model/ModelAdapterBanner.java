/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBanner;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterBanner
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBanner() {
/* 17 */     super(TileEntityBanner.class, "banner", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBanner();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBanner))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBanner modelbanner = (ModelBanner)model;
/*    */     
/* 35 */     if (modelPart.equals("slate"))
/*    */     {
/* 37 */       return modelbanner.bannerSlate;
/*    */     }
/* 39 */     if (modelPart.equals("stand"))
/*    */     {
/* 41 */       return modelbanner.bannerStand;
/*    */     }
/*    */ 
/*    */     
/* 45 */     return modelPart.equals("top") ? modelbanner.bannerTop : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityBannerRenderer tileEntityBannerRenderer;
/* 52 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 53 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityBanner.class);
/*    */     
/* 55 */     if (!(tileentityspecialrenderer instanceof TileEntityBannerRenderer))
/*    */     {
/* 57 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 61 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 63 */       tileEntityBannerRenderer = new TileEntityBannerRenderer();
/* 64 */       tileEntityBannerRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 67 */     if (!Reflector.TileEntityBannerRenderer_bannerModel.exists()) {
/*    */       
/* 69 */       Config.warn("Field not found: TileEntityBannerRenderer.bannerModel");
/* 70 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 74 */     Reflector.setFieldValue(tileEntityBannerRenderer, Reflector.TileEntityBannerRenderer_bannerModel, modelBase);
/* 75 */     return (IEntityRenderer)tileEntityBannerRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */