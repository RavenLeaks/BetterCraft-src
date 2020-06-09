/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSnowMan;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSnowMan;
/*    */ import net.minecraft.entity.monster.EntitySnowman;
/*    */ 
/*    */ public class ModelAdapterSnowman
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterSnowman() {
/* 15 */     super(EntitySnowman.class, "snow_golem", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelSnowMan();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelSnowMan))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelSnowMan modelsnowman = (ModelSnowMan)model;
/*    */     
/* 33 */     if (modelPart.equals("body"))
/*    */     {
/* 35 */       return modelsnowman.body;
/*    */     }
/* 37 */     if (modelPart.equals("body_bottom"))
/*    */     {
/* 39 */       return modelsnowman.bottomBody;
/*    */     }
/* 41 */     if (modelPart.equals("head"))
/*    */     {
/* 43 */       return modelsnowman.head;
/*    */     }
/* 45 */     if (modelPart.equals("left_hand"))
/*    */     {
/* 47 */       return modelsnowman.leftHand;
/*    */     }
/*    */ 
/*    */     
/* 51 */     return modelPart.equals("right_hand") ? modelsnowman.rightHand : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 58 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 59 */     RenderSnowMan rendersnowman = new RenderSnowMan(rendermanager);
/* 60 */     rendersnowman.mainModel = modelBase;
/* 61 */     rendersnowman.shadowSize = shadowSize;
/* 62 */     return (IEntityRenderer)rendersnowman;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSnowman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */