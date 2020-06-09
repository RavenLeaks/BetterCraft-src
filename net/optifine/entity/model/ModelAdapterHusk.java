/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.RenderHusk;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityHusk;
/*    */ 
/*    */ public class ModelAdapterHusk
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterHusk() {
/* 14 */     super(EntityHusk.class, "husk", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelZombie();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderHusk renderhusk = new RenderHusk(rendermanager);
/* 26 */     renderhusk.mainModel = modelBase;
/* 27 */     renderhusk.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderhusk;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterHusk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */