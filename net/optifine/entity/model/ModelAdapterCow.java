/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCow;
/*    */ import net.minecraft.client.renderer.entity.RenderCow;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityCow;
/*    */ 
/*    */ public class ModelAdapterCow
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterCow() {
/* 14 */     super(EntityCow.class, "cow", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelCow();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderCow rendercow = new RenderCow(rendermanager);
/* 26 */     rendercow.mainModel = modelBase;
/* 27 */     rendercow.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)rendercow;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */