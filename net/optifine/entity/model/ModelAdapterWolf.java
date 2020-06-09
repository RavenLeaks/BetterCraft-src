/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelWolf;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWolf;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterWolf
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterWolf() {
/* 16 */     super(EntityWolf.class, "wolf", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelWolf();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelWolf))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelWolf modelwolf = (ModelWolf)model;
/*    */     
/* 34 */     if (modelPart.equals("head"))
/*    */     {
/* 36 */       return modelwolf.wolfHeadMain;
/*    */     }
/* 38 */     if (modelPart.equals("body"))
/*    */     {
/* 40 */       return modelwolf.wolfBody;
/*    */     }
/* 42 */     if (modelPart.equals("leg1"))
/*    */     {
/* 44 */       return modelwolf.wolfLeg1;
/*    */     }
/* 46 */     if (modelPart.equals("leg2"))
/*    */     {
/* 48 */       return modelwolf.wolfLeg2;
/*    */     }
/* 50 */     if (modelPart.equals("leg3"))
/*    */     {
/* 52 */       return modelwolf.wolfLeg3;
/*    */     }
/* 54 */     if (modelPart.equals("leg4"))
/*    */     {
/* 56 */       return modelwolf.wolfLeg4;
/*    */     }
/* 58 */     if (modelPart.equals("tail"))
/*    */     {
/* 60 */       return (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_tail);
/*    */     }
/*    */ 
/*    */     
/* 64 */     return modelPart.equals("mane") ? (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_mane) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 71 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 72 */     RenderWolf renderwolf = new RenderWolf(rendermanager);
/* 73 */     renderwolf.mainModel = modelBase;
/* 74 */     renderwolf.shadowSize = shadowSize;
/* 75 */     return (IEntityRenderer)renderwolf;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */