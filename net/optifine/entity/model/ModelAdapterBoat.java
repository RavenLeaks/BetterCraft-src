/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBoat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterBoat
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterBoat() {
/* 17 */     super(EntityBoat.class, "boat", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelBoat();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelBoat))
/*    */     {
/* 29 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 33 */     ModelBoat modelboat = (ModelBoat)model;
/*    */     
/* 35 */     if (modelPart.equals("bottom"))
/*    */     {
/* 37 */       return modelboat.boatSides[0];
/*    */     }
/* 39 */     if (modelPart.equals("back"))
/*    */     {
/* 41 */       return modelboat.boatSides[1];
/*    */     }
/* 43 */     if (modelPart.equals("front"))
/*    */     {
/* 45 */       return modelboat.boatSides[2];
/*    */     }
/* 47 */     if (modelPart.equals("right"))
/*    */     {
/* 49 */       return modelboat.boatSides[3];
/*    */     }
/* 51 */     if (modelPart.equals("left"))
/*    */     {
/* 53 */       return modelboat.boatSides[4];
/*    */     }
/* 55 */     if (modelPart.equals("paddle_left"))
/*    */     {
/* 57 */       return modelboat.paddles[0];
/*    */     }
/* 59 */     if (modelPart.equals("paddle_right"))
/*    */     {
/* 61 */       return modelboat.paddles[1];
/*    */     }
/*    */ 
/*    */     
/* 65 */     return modelPart.equals("bottom_no_water") ? modelboat.noWater : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 72 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 73 */     RenderBoat renderboat = new RenderBoat(rendermanager);
/*    */     
/* 75 */     if (!Reflector.RenderBoat_modelBoat.exists()) {
/*    */       
/* 77 */       Config.warn("Field not found: RenderBoat.modelBoat");
/* 78 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 82 */     Reflector.setFieldValue(renderboat, Reflector.RenderBoat_modelBoat, modelBase);
/* 83 */     renderboat.shadowSize = shadowSize;
/* 84 */     return (IEntityRenderer)renderboat;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */