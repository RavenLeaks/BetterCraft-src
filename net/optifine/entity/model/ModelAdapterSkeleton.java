/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSkeleton;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ 
/*    */ public class ModelAdapterSkeleton
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterSkeleton() {
/* 14 */     super(EntitySkeleton.class, "skeleton", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSkeleton();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderSkeleton renderskeleton = new RenderSkeleton(rendermanager);
/* 26 */     renderskeleton.mainModel = modelBase;
/* 27 */     renderskeleton.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderskeleton;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */