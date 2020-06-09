/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ 
/*    */ public class ModelAdapterSheep
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterSheep() {
/* 14 */     super(EntitySheep.class, "sheep", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSheep2();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderSheep rendersheep = new RenderSheep(rendermanager);
/* 26 */     rendersheep.mainModel = modelBase;
/* 27 */     rendersheep.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)rendersheep;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterSheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */