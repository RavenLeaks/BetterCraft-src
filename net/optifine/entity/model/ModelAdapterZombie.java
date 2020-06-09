/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderZombie;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ 
/*    */ public class ModelAdapterZombie
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterZombie() {
/* 14 */     super(EntityZombie.class, "zombie", 0.5F);
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
/* 25 */     RenderZombie renderzombie = new RenderZombie(rendermanager);
/* 26 */     renderzombie.mainModel = modelBase;
/* 27 */     renderzombie.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderzombie;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */