/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderCreeper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ 
/*    */ public class ModelAdapterCreeper
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterCreeper() {
/* 15 */     super(EntityCreeper.class, "creeper", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelCreeper();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelCreeper))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelCreeper modelcreeper = (ModelCreeper)model;
/*    */     
/* 33 */     if (modelPart.equals("head"))
/*    */     {
/* 35 */       return modelcreeper.head;
/*    */     }
/* 37 */     if (modelPart.equals("armor"))
/*    */     {
/* 39 */       return modelcreeper.creeperArmor;
/*    */     }
/* 41 */     if (modelPart.equals("body"))
/*    */     {
/* 43 */       return modelcreeper.body;
/*    */     }
/* 45 */     if (modelPart.equals("leg1"))
/*    */     {
/* 47 */       return modelcreeper.leg1;
/*    */     }
/* 49 */     if (modelPart.equals("leg2"))
/*    */     {
/* 51 */       return modelcreeper.leg2;
/*    */     }
/* 53 */     if (modelPart.equals("leg3"))
/*    */     {
/* 55 */       return modelcreeper.leg3;
/*    */     }
/*    */ 
/*    */     
/* 59 */     return modelPart.equals("leg4") ? modelcreeper.leg4 : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 66 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 67 */     RenderCreeper rendercreeper = new RenderCreeper(rendermanager);
/* 68 */     rendercreeper.mainModel = modelBase;
/* 69 */     rendercreeper.shadowSize = shadowSize;
/* 70 */     return (IEntityRenderer)rendercreeper;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */