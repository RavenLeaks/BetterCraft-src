/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelDragon;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterDragon
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterDragon() {
/* 16 */     super(EntityDragon.class, "dragon", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelDragon(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelDragon))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelDragon modeldragon = (ModelDragon)model;
/*    */     
/* 34 */     if (modelPart.equals("head"))
/*    */     {
/* 36 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 0);
/*    */     }
/* 38 */     if (modelPart.equals("spine"))
/*    */     {
/* 40 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 1);
/*    */     }
/* 42 */     if (modelPart.equals("jaw"))
/*    */     {
/* 44 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 2);
/*    */     }
/* 46 */     if (modelPart.equals("body"))
/*    */     {
/* 48 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 3);
/*    */     }
/* 50 */     if (modelPart.equals("rear_leg"))
/*    */     {
/* 52 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 4);
/*    */     }
/* 54 */     if (modelPart.equals("front_leg"))
/*    */     {
/* 56 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 5);
/*    */     }
/* 58 */     if (modelPart.equals("rear_leg_tip"))
/*    */     {
/* 60 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 6);
/*    */     }
/* 62 */     if (modelPart.equals("front_leg_tip"))
/*    */     {
/* 64 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 7);
/*    */     }
/* 66 */     if (modelPart.equals("rear_foot"))
/*    */     {
/* 68 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 8);
/*    */     }
/* 70 */     if (modelPart.equals("front_foot"))
/*    */     {
/* 72 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 9);
/*    */     }
/* 74 */     if (modelPart.equals("wing"))
/*    */     {
/* 76 */       return (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 10);
/*    */     }
/*    */ 
/*    */     
/* 80 */     return modelPart.equals("wing_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 11) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 87 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 88 */     RenderDragon renderdragon = new RenderDragon(rendermanager);
/* 89 */     renderdragon.mainModel = modelBase;
/* 90 */     renderdragon.shadowSize = shadowSize;
/* 91 */     return (IEntityRenderer)renderdragon;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */