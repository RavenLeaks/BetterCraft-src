/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelPolarBear;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPolarBear;
/*    */ import net.minecraft.entity.monster.EntityPolarBear;
/*    */ 
/*    */ public class ModelAdapterPolarBear
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterPolarBear() {
/* 14 */     super(EntityPolarBear.class, "polar_bear", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelPolarBear();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderPolarBear renderpolarbear = new RenderPolarBear(rendermanager);
/* 26 */     renderpolarbear.mainModel = modelBase;
/* 27 */     renderpolarbear.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderpolarbear;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterPolarBear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */