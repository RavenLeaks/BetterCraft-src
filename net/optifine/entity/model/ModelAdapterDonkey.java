/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderAbstractHorse;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityDonkey;
/*    */ 
/*    */ public class ModelAdapterDonkey
/*    */   extends ModelAdapterHorse
/*    */ {
/*    */   public ModelAdapterDonkey() {
/* 13 */     super(EntityDonkey.class, "donkey", 0.75F);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 18 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 19 */     RenderAbstractHorse renderabstracthorse = new RenderAbstractHorse(rendermanager);
/* 20 */     renderabstracthorse.mainModel = modelBase;
/* 21 */     renderabstracthorse.shadowSize = shadowSize;
/* 22 */     return (IEntityRenderer)renderabstracthorse;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterDonkey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */