/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelHumanoidHead;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterHeadHumanoid
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterHeadHumanoid() {
/* 17 */     super(TileEntitySkull.class, "head_humanoid", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelHumanoidHead();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelHumanoidHead))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelHumanoidHead modelhumanoidhead = (ModelHumanoidHead)model;
/*    */     
/* 35 */     if (modelPart.equals("head"))
/*    */     {
/* 37 */       return modelhumanoidhead.skeletonHead;
/*    */     }
/* 39 */     if (modelPart.equals("head2"))
/*    */     {
/* 41 */       return !Reflector.ModelHumanoidHead_head.exists() ? null : (ModelRenderer)Reflector.getFieldValue(modelhumanoidhead, Reflector.ModelHumanoidHead_head);
/*    */     }
/*    */ 
/*    */     
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntitySkullRenderer tileEntitySkullRenderer;
/* 52 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 53 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntitySkull.class);
/*    */     
/* 55 */     if (!(tileentityspecialrenderer instanceof TileEntitySkullRenderer))
/*    */     {
/* 57 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 61 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/*    */       
/* 63 */       tileEntitySkullRenderer = new TileEntitySkullRenderer();
/* 64 */       tileEntitySkullRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 67 */     if (!Reflector.TileEntitySkullRenderer_humanoidHead.exists()) {
/*    */       
/* 69 */       Config.warn("Field not found: TileEntitySkullRenderer.humanoidHead");
/* 70 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 74 */     Reflector.setFieldValue(tileEntitySkullRenderer, Reflector.TileEntitySkullRenderer_humanoidHead, modelBase);
/* 75 */     return (IEntityRenderer)tileEntitySkullRenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterHeadHumanoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */