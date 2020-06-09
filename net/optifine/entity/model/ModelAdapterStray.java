/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderStray;
/*    */ import net.minecraft.entity.monster.EntityStray;
/*    */ 
/*    */ public class ModelAdapterStray
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterStray() {
/* 14 */     super(EntityStray.class, "stray", 0.7F);
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
/* 25 */     RenderStray renderstray = new RenderStray(rendermanager);
/* 26 */     renderstray.mainModel = modelBase;
/* 27 */     renderstray.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderstray;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterStray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */