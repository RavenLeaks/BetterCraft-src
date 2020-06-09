/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelShulker;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderShulker;
/*    */ import net.minecraft.entity.monster.EntityShulker;
/*    */ 
/*    */ public class ModelAdapterShulker
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterShulker() {
/* 15 */     super(EntityShulker.class, "shulker", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelShulker();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelShulker))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelShulker modelshulker = (ModelShulker)model;
/*    */     
/* 33 */     if (modelPart.equals("head"))
/*    */     {
/* 35 */       return modelshulker.head;
/*    */     }
/* 37 */     if (modelPart.equals("base"))
/*    */     {
/* 39 */       return modelshulker.base;
/*    */     }
/*    */ 
/*    */     
/* 43 */     return modelPart.equals("lid") ? modelshulker.lid : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 50 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 51 */     RenderShulker rendershulker = new RenderShulker(rendermanager);
/* 52 */     rendershulker.mainModel = modelBase;
/* 53 */     rendershulker.shadowSize = shadowSize;
/* 54 */     return (IEntityRenderer)rendershulker;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterShulker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */