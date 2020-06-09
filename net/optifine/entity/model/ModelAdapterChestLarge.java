/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelLargeChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterChestLarge
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterChestLarge() {
/* 18 */     super(TileEntityChest.class, "chest_large", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 23 */     return (ModelBase)new ModelLargeChest();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 28 */     if (!(model instanceof ModelChest))
/*    */     {
/* 30 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 34 */     ModelChest modelchest = (ModelChest)model;
/*    */     
/* 36 */     if (modelPart.equals("lid"))
/*    */     {
/* 38 */       return modelchest.chestLid;
/*    */     }
/* 40 */     if (modelPart.equals("base"))
/*    */     {
/* 42 */       return modelchest.chestBelow;
/*    */     }
/*    */ 
/*    */     
/* 46 */     return modelPart.equals("knob") ? modelchest.chestKnob : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityChestRenderer tileEntityChestRenderer;
/* 53 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 54 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityChest.class);
/*    */     
/* 56 */     if (!(tileentityspecialrenderer instanceof TileEntityChestRenderer))
/*    */     {
/* 58 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 62 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 64 */       tileEntityChestRenderer = new TileEntityChestRenderer();
/* 65 */       tileEntityChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 68 */     if (!Reflector.TileEntityChestRenderer_largeChest.exists()) {
/*    */       
/* 70 */       Config.warn("Field not found: TileEntityChestRenderer.largeChest");
/* 71 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 75 */     Reflector.setFieldValue(tileEntityChestRenderer, Reflector.TileEntityChestRenderer_largeChest, modelBase);
/* 76 */     return (IEntityRenderer)tileEntityChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterChestLarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */