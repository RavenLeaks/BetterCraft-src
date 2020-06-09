/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderHorse;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ public class ModelAdapterHorse
/*     */   extends ModelAdapter {
/*  16 */   private static Map<String, Integer> mapPartFields = null;
/*     */ 
/*     */   
/*     */   public ModelAdapterHorse() {
/*  20 */     super(EntityHorse.class, "horse", 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelAdapterHorse(Class entityClass, String name, float shadowSize) {
/*  25 */     super(entityClass, name, shadowSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBase makeModel() {
/*  30 */     return (ModelBase)new ModelHorse();
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/*  35 */     if (!(model instanceof ModelHorse))
/*     */     {
/*  37 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  41 */     ModelHorse modelhorse = (ModelHorse)model;
/*  42 */     Map<String, Integer> map = getMapPartFields();
/*     */     
/*  44 */     if (map.containsKey(modelPart)) {
/*     */       
/*  46 */       int i = ((Integer)map.get(modelPart)).intValue();
/*  47 */       return (ModelRenderer)Reflector.getFieldValue(modelhorse, Reflector.ModelHorse_ModelRenderers, i);
/*     */     } 
/*     */ 
/*     */     
/*  51 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<String, Integer> getMapPartFields() {
/*  58 */     if (mapPartFields != null)
/*     */     {
/*  60 */       return mapPartFields;
/*     */     }
/*     */ 
/*     */     
/*  64 */     mapPartFields = new HashMap<>();
/*  65 */     mapPartFields.put("head", Integer.valueOf(0));
/*  66 */     mapPartFields.put("upper_mouth", Integer.valueOf(1));
/*  67 */     mapPartFields.put("lower_mouth", Integer.valueOf(2));
/*  68 */     mapPartFields.put("horse_left_ear", Integer.valueOf(3));
/*  69 */     mapPartFields.put("horse_right_ear", Integer.valueOf(4));
/*  70 */     mapPartFields.put("mule_left_ear", Integer.valueOf(5));
/*  71 */     mapPartFields.put("mule_right_ear", Integer.valueOf(6));
/*  72 */     mapPartFields.put("neck", Integer.valueOf(7));
/*  73 */     mapPartFields.put("horse_face_ropes", Integer.valueOf(8));
/*  74 */     mapPartFields.put("mane", Integer.valueOf(9));
/*  75 */     mapPartFields.put("body", Integer.valueOf(10));
/*  76 */     mapPartFields.put("tail_base", Integer.valueOf(11));
/*  77 */     mapPartFields.put("tail_middle", Integer.valueOf(12));
/*  78 */     mapPartFields.put("tail_tip", Integer.valueOf(13));
/*  79 */     mapPartFields.put("back_left_leg", Integer.valueOf(14));
/*  80 */     mapPartFields.put("back_left_shin", Integer.valueOf(15));
/*  81 */     mapPartFields.put("back_left_hoof", Integer.valueOf(16));
/*  82 */     mapPartFields.put("back_right_leg", Integer.valueOf(17));
/*  83 */     mapPartFields.put("back_right_shin", Integer.valueOf(18));
/*  84 */     mapPartFields.put("back_right_hoof", Integer.valueOf(19));
/*  85 */     mapPartFields.put("front_left_leg", Integer.valueOf(20));
/*  86 */     mapPartFields.put("front_left_shin", Integer.valueOf(21));
/*  87 */     mapPartFields.put("front_left_hoof", Integer.valueOf(22));
/*  88 */     mapPartFields.put("front_right_leg", Integer.valueOf(23));
/*  89 */     mapPartFields.put("front_right_shin", Integer.valueOf(24));
/*  90 */     mapPartFields.put("front_right_hoof", Integer.valueOf(25));
/*  91 */     mapPartFields.put("mule_left_chest", Integer.valueOf(26));
/*  92 */     mapPartFields.put("mule_right_chest", Integer.valueOf(27));
/*  93 */     mapPartFields.put("horse_saddle_bottom", Integer.valueOf(28));
/*  94 */     mapPartFields.put("horse_saddle_front", Integer.valueOf(29));
/*  95 */     mapPartFields.put("horse_saddle_back", Integer.valueOf(30));
/*  96 */     mapPartFields.put("horse_left_saddle_rope", Integer.valueOf(31));
/*  97 */     mapPartFields.put("horse_left_saddle_metal", Integer.valueOf(32));
/*  98 */     mapPartFields.put("horse_right_saddle_rope", Integer.valueOf(33));
/*  99 */     mapPartFields.put("horse_right_saddle_metal", Integer.valueOf(34));
/* 100 */     mapPartFields.put("horse_left_face_metal", Integer.valueOf(35));
/* 101 */     mapPartFields.put("horse_right_face_metal", Integer.valueOf(36));
/* 102 */     mapPartFields.put("horse_left_rein", Integer.valueOf(37));
/* 103 */     mapPartFields.put("horse_right_rein", Integer.valueOf(38));
/* 104 */     return mapPartFields;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 110 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 111 */     RenderHorse renderhorse = new RenderHorse(rendermanager);
/* 112 */     renderhorse.mainModel = modelBase;
/* 113 */     renderhorse.shadowSize = shadowSize;
/* 114 */     return (IEntityRenderer)renderhorse;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */