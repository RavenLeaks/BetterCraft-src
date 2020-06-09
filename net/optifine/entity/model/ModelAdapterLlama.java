/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelLlama;
/*    */ import net.minecraft.client.renderer.entity.RenderLlama;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityLlama;
/*    */ 
/*    */ public class ModelAdapterLlama
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterLlama() {
/* 14 */     super(EntityLlama.class, "llama", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelLlama(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderLlama renderllama = new RenderLlama(rendermanager);
/* 26 */     renderllama.mainModel = modelBase;
/* 27 */     renderllama.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderllama;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterLlama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */