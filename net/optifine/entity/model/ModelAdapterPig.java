/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelPig;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPig;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ 
/*    */ public class ModelAdapterPig
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterPig() {
/* 14 */     super(EntityPig.class, "pig", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelPig();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderPig renderpig = new RenderPig(rendermanager);
/* 26 */     renderpig.mainModel = modelBase;
/* 27 */     renderpig.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderpig;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */