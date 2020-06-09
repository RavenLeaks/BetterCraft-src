/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelDragonHead;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterHeadDragon
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterHeadDragon() {
/* 17 */     super(TileEntitySkull.class, "head_dragon", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelDragonHead(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelDragonHead))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelDragonHead modeldragonhead = (ModelDragonHead)model;
/*    */     
/* 35 */     if (modelPart.equals("head"))
/*    */     {
/* 37 */       return (ModelRenderer)Reflector.getFieldValue(modeldragonhead, Reflector.ModelDragonHead_head);
/*    */     }
/*    */ 
/*    */     
/* 41 */     return modelPart.equals("jaw") ? (ModelRenderer)Reflector.getFieldValue(modeldragonhead, Reflector.ModelDragonHead_jaw) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntitySkullRenderer tileEntitySkullRenderer;
/* 48 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 49 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntitySkull.class);
/*    */     
/* 51 */     if (!(tileentityspecialrenderer instanceof TileEntitySkullRenderer))
/*    */     {
/* 53 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 57 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 59 */       tileEntitySkullRenderer = new TileEntitySkullRenderer();
/* 60 */       tileEntitySkullRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 63 */     if (!Reflector.TileEntitySkullRenderer_dragonHead.exists()) {
/*    */       
/* 65 */       Config.warn("Field not found: TileEntitySkullRenderer.dragonHead");
/* 66 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 70 */     Reflector.setFieldValue(tileEntitySkullRenderer, Reflector.TileEntitySkullRenderer_dragonHead, modelBase);
/* 71 */     return (IEntityRenderer)tileEntitySkullRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterHeadDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */