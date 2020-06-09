/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSign;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterSign
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSign() {
/* 17 */     super(TileEntitySign.class, "sign", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelSign();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelSign))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelSign modelsign = (ModelSign)model;
/*    */     
/* 35 */     if (modelPart.equals("board"))
/*    */     {
/* 37 */       return modelsign.signBoard;
/*    */     }
/*    */ 
/*    */     
/* 41 */     return modelPart.equals("stick") ? modelsign.signStick : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntitySignRenderer tileEntitySignRenderer;
/* 48 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 49 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntitySign.class);
/*    */     
/* 51 */     if (!(tileentityspecialrenderer instanceof TileEntitySignRenderer))
/*    */     {
/* 53 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 57 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 59 */       tileEntitySignRenderer = new TileEntitySignRenderer();
/* 60 */       tileEntitySignRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 63 */     if (!Reflector.TileEntitySignRenderer_model.exists()) {
/*    */       
/* 65 */       Config.warn("Field not found: TileEntitySignRenderer.model");
/* 66 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 70 */     Reflector.setFieldValue(tileEntitySignRenderer, Reflector.TileEntitySignRenderer_model, modelBase);
/* 71 */     return (IEntityRenderer)tileEntitySignRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */