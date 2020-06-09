/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIllager;
/*    */ import net.minecraft.client.renderer.entity.RenderEvoker;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityEvoker;
/*    */ 
/*    */ public class ModelAdapterEvoker
/*    */   extends ModelAdapterIllager
/*    */ {
/*    */   public ModelAdapterEvoker() {
/* 14 */     super(EntityEvoker.class, "evocation_illager", 0.5F);
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
/* 25 */     RenderEvoker renderevoker = new RenderEvoker(rendermanager);
/* 26 */     renderevoker.mainModel = modelBase;
/* 27 */     renderevoker.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderevoker;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterEvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */