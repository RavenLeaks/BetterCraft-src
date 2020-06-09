/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBook;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterBook
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBook() {
/* 17 */     super(TileEntityEnchantmentTable.class, "book", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBook();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBook))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBook modelbook = (ModelBook)model;
/*    */     
/* 35 */     if (modelPart.equals("cover_right"))
/*    */     {
/* 37 */       return modelbook.coverRight;
/*    */     }
/* 39 */     if (modelPart.equals("cover_left"))
/*    */     {
/* 41 */       return modelbook.coverLeft;
/*    */     }
/* 43 */     if (modelPart.equals("pages_right"))
/*    */     {
/* 45 */       return modelbook.pagesRight;
/*    */     }
/* 47 */     if (modelPart.equals("pages_left"))
/*    */     {
/* 49 */       return modelbook.pagesLeft;
/*    */     }
/* 51 */     if (modelPart.equals("flipping_page_right"))
/*    */     {
/* 53 */       return modelbook.flippingPageRight;
/*    */     }
/* 55 */     if (modelPart.equals("flipping_page_left"))
/*    */     {
/* 57 */       return modelbook.flippingPageLeft;
/*    */     }
/*    */ 
/*    */     
/* 61 */     return modelPart.equals("book_spine") ? modelbook.bookSpine : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityEnchantmentTableRenderer tileEntityEnchantmentTableRenderer;
/* 68 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 69 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnchantmentTable.class);
/*    */     
/* 71 */     if (!(tileentityspecialrenderer instanceof TileEntityEnchantmentTableRenderer))
/*    */     {
/* 73 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 77 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 79 */       tileEntityEnchantmentTableRenderer = new TileEntityEnchantmentTableRenderer();
/* 80 */       tileEntityEnchantmentTableRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 83 */     if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
/*    */       
/* 85 */       Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
/* 86 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 90 */     Reflector.setFieldValue(tileEntityEnchantmentTableRenderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
/* 91 */     return (IEntityRenderer)tileEntityEnchantmentTableRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */