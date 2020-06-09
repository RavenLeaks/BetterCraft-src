/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderZombieVillager;
/*    */ import net.minecraft.entity.monster.EntityZombieVillager;
/*    */ 
/*    */ public class ModelAdapterZombieVillager
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterZombieVillager() {
/* 14 */     super(EntityZombieVillager.class, "zombie_villager", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelZombieVillager();
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 24 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 25 */     RenderZombieVillager renderzombievillager = new RenderZombieVillager(rendermanager);
/* 26 */     renderzombievillager.mainModel = modelBase;
/* 27 */     renderzombievillager.shadowSize = shadowSize;
/* 28 */     return (IEntityRenderer)renderzombievillager;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterZombieVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */