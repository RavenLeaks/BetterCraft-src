/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChicken;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderChicken;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ 
/*    */ public class ModelAdapterChicken
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterChicken() {
/* 15 */     super(EntityChicken.class, "chicken", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelChicken();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelChicken))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelChicken modelchicken = (ModelChicken)model;
/*    */     
/* 33 */     if (modelPart.equals("head"))
/*    */     {
/* 35 */       return modelchicken.head;
/*    */     }
/* 37 */     if (modelPart.equals("body"))
/*    */     {
/* 39 */       return modelchicken.body;
/*    */     }
/* 41 */     if (modelPart.equals("right_leg"))
/*    */     {
/* 43 */       return modelchicken.rightLeg;
/*    */     }
/* 45 */     if (modelPart.equals("left_leg"))
/*    */     {
/* 47 */       return modelchicken.leftLeg;
/*    */     }
/* 49 */     if (modelPart.equals("right_wing"))
/*    */     {
/* 51 */       return modelchicken.rightWing;
/*    */     }
/* 53 */     if (modelPart.equals("left_wing"))
/*    */     {
/* 55 */       return modelchicken.leftWing;
/*    */     }
/* 57 */     if (modelPart.equals("bill"))
/*    */     {
/* 59 */       return modelchicken.bill;
/*    */     }
/*    */ 
/*    */     
/* 63 */     return modelPart.equals("chin") ? modelchicken.chin : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 70 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 71 */     RenderChicken renderchicken = new RenderChicken(rendermanager);
/* 72 */     renderchicken.mainModel = modelBase;
/* 73 */     renderchicken.shadowSize = shadowSize;
/* 74 */     return (IEntityRenderer)renderchicken;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */