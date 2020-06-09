/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRabbit;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderRabbit;
/*    */ import net.minecraft.entity.passive.EntityRabbit;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterRabbit
/*    */   extends ModelAdapter {
/* 16 */   private static Map<String, Integer> mapPartFields = null;
/*    */ 
/*    */   
/*    */   public ModelAdapterRabbit() {
/* 20 */     super(EntityRabbit.class, "rabbit", 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 25 */     return (ModelBase)new ModelRabbit();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 30 */     if (!(model instanceof ModelRabbit))
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 36 */     ModelRabbit modelrabbit = (ModelRabbit)model;
/* 37 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 39 */     if (map.containsKey(modelPart)) {
/*    */       
/* 41 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 42 */       return (ModelRenderer)Reflector.getFieldValue(modelrabbit, Reflector.ModelRabbit_renderers, i);
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
/* 60 */     mapPartFields.put("left_foot", Integer.valueOf(0));
/* 61 */     mapPartFields.put("right_foot", Integer.valueOf(1));
/* 62 */     mapPartFields.put("left_thigh", Integer.valueOf(2));
/* 63 */     mapPartFields.put("right_thigh", Integer.valueOf(3));
/* 64 */     mapPartFields.put("body", Integer.valueOf(4));
/* 65 */     mapPartFields.put("left_arm", Integer.valueOf(5));
/* 66 */     mapPartFields.put("right_arm", Integer.valueOf(6));
/* 67 */     mapPartFields.put("head", Integer.valueOf(7));
/* 68 */     mapPartFields.put("right_ear", Integer.valueOf(8));
/* 69 */     mapPartFields.put("left_ear", Integer.valueOf(9));
/* 70 */     mapPartFields.put("tail", Integer.valueOf(10));
/* 71 */     mapPartFields.put("nose", Integer.valueOf(11));
/* 72 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 78 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 79 */     RenderRabbit renderrabbit = new RenderRabbit(rendermanager);
/* 80 */     renderrabbit.mainModel = modelBase;
/* 81 */     renderrabbit.shadowSize = shadowSize;
/* 82 */     return (IEntityRenderer)renderrabbit;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */