/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIllager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderVindicator;
/*    */ import net.minecraft.entity.monster.EntityVindicator;
/*    */ 
/*    */ public class ModelAdapterVindicator
/*    */   extends ModelAdapterIllager
/*    */ {
/*    */   public ModelAdapterVindicator() {
/* 14 */     super(EntityVindicator.class, "vindication_illager", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelIllager(0.0F, 0.0F, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderVindicator rendervindicator = new RenderVindicator(rendermanager);
/* 26 */     rendervindicator.mainModel = modelBase;
/* 27 */     rendervindicator.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)rendervindicator;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterVindicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */