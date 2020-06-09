/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWitherSkeleton;
/*    */ import net.minecraft.entity.monster.EntityWitherSkeleton;
/*    */ 
/*    */ public class ModelAdapterWitherSkeleton
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterWitherSkeleton() {
/* 14 */     super(EntityWitherSkeleton.class, "wither_skeleton", 0.7F);
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
/* 25 */     RenderWitherSkeleton renderwitherskeleton = new RenderWitherSkeleton(rendermanager);
/* 26 */     renderwitherskeleton.mainModel = modelBase;
/* 27 */     renderwitherskeleton.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderwitherskeleton;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterWitherSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */