/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelVillager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderVillager;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ 
/*    */ public class ModelAdapterVillager
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterVillager() {
/* 15 */     super(EntityVillager.class, "villager", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelVillager(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelVillager))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelVillager modelvillager = (ModelVillager)model;
/*    */     
/* 33 */     if (modelPart.equals("head"))
/*    */     {
/* 35 */       return modelvillager.villagerHead;
/*    */     }
/* 37 */     if (modelPart.equals("body"))
/*    */     {
/* 39 */       return modelvillager.villagerBody;
/*    */     }
/* 41 */     if (modelPart.equals("arms"))
/*    */     {
/* 43 */       return modelvillager.villagerArms;
/*    */     }
/* 45 */     if (modelPart.equals("left_leg"))
/*    */     {
/* 47 */       return modelvillager.leftVillagerLeg;
/*    */     }
/* 49 */     if (modelPart.equals("right_leg"))
/*    */     {
/* 51 */       return modelvillager.rightVillagerLeg;
/*    */     }
/*    */ 
/*    */     
/* 55 */     return modelPart.equals("nose") ? modelvillager.villagerNose : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 62 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 63 */     RenderVillager rendervillager = new RenderVillager(rendermanager);
/* 64 */     rendervillager.mainModel = modelBase;
/* 65 */     rendervillager.shadowSize = shadowSize;
/* 66 */     return (IEntityRenderer)rendervillager;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */