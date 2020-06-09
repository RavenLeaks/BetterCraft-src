/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelShulker;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityShulkerBoxRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntityShulkerBox;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterShulkerBox
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterShulkerBox() {
/* 17 */     super(TileEntityShulkerBox.class, "shulker_box", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelShulker();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelShulker))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelShulker modelshulker = (ModelShulker)model;
/*    */     
/* 35 */     if (modelPart.equals("head"))
/*    */     {
/* 37 */       return modelshulker.head;
/*    */     }
/* 39 */     if (modelPart.equals("base"))
/*    */     {
/* 41 */       return modelshulker.base;
/*    */     }
/*    */ 
/*    */     
/* 45 */     return modelPart.equals("lid") ? modelshulker.lid : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityShulkerBoxRenderer tileEntityShulkerBoxRenderer;
/* 52 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 53 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityShulkerBox.class);
/*    */     
/* 55 */     if (!(tileentityspecialrenderer instanceof TileEntityShulkerBoxRenderer))
/*    */     {
/* 57 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 61 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 63 */       tileEntityShulkerBoxRenderer = new TileEntityShulkerBoxRenderer((ModelShulker)modelBase);
/* 64 */       tileEntityShulkerBoxRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 67 */     if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
/*    */       
/* 69 */       Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
/* 70 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 74 */     Reflector.setFieldValue(tileEntityShulkerBoxRenderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
/* 75 */     return (IEntityRenderer)tileEntityShulkerBoxRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */