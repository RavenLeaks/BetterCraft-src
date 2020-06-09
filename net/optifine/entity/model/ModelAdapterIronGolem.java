/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderIronGolem;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ 
/*    */ public class ModelAdapterIronGolem
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterIronGolem() {
/* 15 */     super(EntityIronGolem.class, "iron_golem", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelIronGolem();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelIronGolem))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelIronGolem modelirongolem = (ModelIronGolem)model;
/*    */     
/* 33 */     if (modelPart.equals("head"))
/*    */     {
/* 35 */       return modelirongolem.ironGolemHead;
/*    */     }
/* 37 */     if (modelPart.equals("body"))
/*    */     {
/* 39 */       return modelirongolem.ironGolemBody;
/*    */     }
/* 41 */     if (modelPart.equals("left_arm"))
/*    */     {
/* 43 */       return modelirongolem.ironGolemLeftArm;
/*    */     }
/* 45 */     if (modelPart.equals("right_arm"))
/*    */     {
/* 47 */       return modelirongolem.ironGolemRightArm;
/*    */     }
/* 49 */     if (modelPart.equals("left_leg"))
/*    */     {
/* 51 */       return modelirongolem.ironGolemLeftLeg;
/*    */     }
/*    */ 
/*    */     
/* 55 */     return modelPart.equals("right_leg") ? modelirongolem.ironGolemRightLeg : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 62 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 63 */     RenderIronGolem renderirongolem = new RenderIronGolem(rendermanager);
/* 64 */     renderirongolem.mainModel = modelBase;
/* 65 */     renderirongolem.shadowSize = shadowSize;
/* 66 */     return (IEntityRenderer)renderirongolem;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */