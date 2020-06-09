/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelWitch;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWitch;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterWitch
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterWitch() {
/* 16 */     super(EntityWitch.class, "witch", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelWitch(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelWitch))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelWitch modelwitch = (ModelWitch)model;
/*    */     
/* 34 */     if (modelPart.equals("mole"))
/*    */     {
/* 36 */       return (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_mole);
/*    */     }
/* 38 */     if (modelPart.equals("hat"))
/*    */     {
/* 40 */       return (ModelRenderer)Reflector.getFieldValue(modelwitch, Reflector.ModelWitch_hat);
/*    */     }
/* 42 */     if (modelPart.equals("head"))
/*    */     {
/* 44 */       return modelwitch.villagerHead;
/*    */     }
/* 46 */     if (modelPart.equals("body"))
/*    */     {
/* 48 */       return modelwitch.villagerBody;
/*    */     }
/* 50 */     if (modelPart.equals("arms"))
/*    */     {
/* 52 */       return modelwitch.villagerArms;
/*    */     }
/* 54 */     if (modelPart.equals("left_leg"))
/*    */     {
/* 56 */       return modelwitch.leftVillagerLeg;
/*    */     }
/* 58 */     if (modelPart.equals("right_leg"))
/*    */     {
/* 60 */       return modelwitch.rightVillagerLeg;
/*    */     }
/*    */ 
/*    */     
/* 64 */     return modelPart.equals("nose") ? modelwitch.villagerNose : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 71 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 72 */     RenderWitch renderwitch = new RenderWitch(rendermanager);
/* 73 */     renderwitch.mainModel = modelBase;
/* 74 */     renderwitch.shadowSize = shadowSize;
/* 75 */     return (IEntityRenderer)renderwitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */