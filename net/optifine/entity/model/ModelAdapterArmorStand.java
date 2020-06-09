/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelArmorStand;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderArmorStand;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ 
/*    */ public class ModelAdapterArmorStand
/*    */   extends ModelAdapter
/*    */ {
/*    */   public ModelAdapterArmorStand() {
/* 15 */     super(EntityArmorStand.class, "armor_stand", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 20 */     return (ModelBase)new ModelArmorStand();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 25 */     if (!(model instanceof ModelArmorStand))
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     ModelArmorStand modelarmorstand = (ModelArmorStand)model;
/*    */     
/* 33 */     if (modelPart.equals("right"))
/*    */     {
/* 35 */       return modelarmorstand.standRightSide;
/*    */     }
/* 37 */     if (modelPart.equals("left"))
/*    */     {
/* 39 */       return modelarmorstand.standLeftSide;
/*    */     }
/* 41 */     if (modelPart.equals("waist"))
/*    */     {
/* 43 */       return modelarmorstand.standWaist;
/*    */     }
/*    */ 
/*    */     
/* 47 */     return modelPart.equals("base") ? modelarmorstand.standBase : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 54 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 55 */     RenderArmorStand renderarmorstand = new RenderArmorStand(rendermanager);
/* 56 */     renderarmorstand.mainModel = modelBase;
/* 57 */     renderarmorstand.shadowSize = shadowSize;
/* 58 */     return (IEntityRenderer)renderarmorstand;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */