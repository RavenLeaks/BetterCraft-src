/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCow;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMooshroom;
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ 
/*    */ public class ModelAdapterMooshroom
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterMooshroom() {
/* 14 */     super(EntityMooshroom.class, "mooshroom", 0.7F);
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
/* 25 */     RenderMooshroom rendermooshroom = new RenderMooshroom(rendermanager);
/* 26 */     rendermooshroom.mainModel = modelBase;
/* 27 */     rendermooshroom.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)rendermooshroom;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterMooshroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */