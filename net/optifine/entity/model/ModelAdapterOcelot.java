/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelOcelot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderOcelot;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterOcelot
/*    */   extends ModelAdapter {
/* 16 */   private static Map<String, Integer> mapPartFields = null;
/*    */ 
/*    */   
/*    */   public ModelAdapterOcelot() {
/* 20 */     super(EntityOcelot.class, "ocelot", 0.4F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 25 */     return (ModelBase)new ModelOcelot();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 30 */     if (!(model instanceof ModelOcelot))
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 36 */     ModelOcelot modelocelot = (ModelOcelot)model;
/* 37 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 39 */     if (map.containsKey(modelPart)) {
/*    */       
/* 41 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 42 */       return (ModelRenderer)Reflector.getFieldValue(modelocelot, Reflector.ModelOcelot_ModelRenderers, i);
/*    */     } 
/*    */ 
/*    */     
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Map<String, Integer> getMapPartFields() {
/* 53 */     if (mapPartFields != null)
/*    */     {
/* 55 */       return mapPartFields;
/*    */     }
/*    */ 
/*    */     
/* 59 */     mapPartFields = new HashMap<>();
/* 60 */     mapPartFields.put("back_left_leg", Integer.valueOf(0));
/* 61 */     mapPartFields.put("back_right_leg", Integer.valueOf(1));
/* 62 */     mapPartFields.put("front_left_leg", Integer.valueOf(2));
/* 63 */     mapPartFields.put("front_right_leg", Integer.valueOf(3));
/* 64 */     mapPartFields.put("tail", Integer.valueOf(4));
/* 65 */     mapPartFields.put("tail2", Integer.valueOf(5));
/* 66 */     mapPartFields.put("head", Integer.valueOf(6));
/* 67 */     mapPartFields.put("body", Integer.valueOf(7));
/* 68 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 74 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 75 */     RenderOcelot renderocelot = new RenderOcelot(rendermanager);
/* 76 */     renderocelot.mainModel = modelBase;
/* 77 */     renderocelot.shadowSize = shadowSize;
/* 78 */     return (IEntityRenderer)renderocelot;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */