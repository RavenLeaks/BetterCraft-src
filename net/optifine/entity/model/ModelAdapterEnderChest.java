/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderChest
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterEnderChest() {
/* 17 */     super(TileEntityEnderChest.class, "ender_chest", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelChest();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelChest))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelChest modelchest = (ModelChest)model;
/*    */     
/* 35 */     if (modelPart.equals("lid"))
/*    */     {
/* 37 */       return modelchest.chestLid;
/*    */     }
/* 39 */     if (modelPart.equals("base"))
/*    */     {
/* 41 */       return modelchest.chestBelow;
/*    */     }
/*    */ 
/*    */     
/* 45 */     return modelPart.equals("knob") ? modelchest.chestKnob : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityEnderChestRenderer tileEntityEnderChestRenderer;
/* 52 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 53 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityEnderChest.class);
/*    */     
/* 55 */     if (!(tileentityspecialrenderer instanceof TileEntityEnderChestRenderer))
/*    */     {
/* 57 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 61 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 63 */       tileEntityEnderChestRenderer = new TileEntityEnderChestRenderer();
/* 64 */       tileEntityEnderChestRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 67 */     if (!Reflector.TileEntityEnderChestRenderer_modelChest.exists()) {
/*    */       
/* 69 */       Config.warn("Field not found: TileEntityEnderChestRenderer.modelChest");
/* 70 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 74 */     Reflector.setFieldValue(tileEntityEnderChestRenderer, Reflector.TileEntityEnderChestRenderer_modelChest, modelBase);
/* 75 */     return (IEntityRenderer)tileEntityEnderChestRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */