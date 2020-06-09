/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderCrystalNoBase
/*    */   extends ModelAdapterEnderCrystal
/*    */ {
/*    */   public ModelAdapterEnderCrystalNoBase() {
/* 17 */     super("end_crystal_no_base");
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 22 */     return (ModelBase)new ModelEnderCrystal(0.0F, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 27 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 28 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntityEnderCrystal.class);
/*    */     
/* 30 */     if (!(render instanceof RenderEnderCrystal)) {
/*    */       
/* 32 */       Config.warn("Not an instance of RenderEnderCrystal: " + render);
/* 33 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 37 */     RenderEnderCrystal renderendercrystal = (RenderEnderCrystal)render;
/*    */     
/* 39 */     if (!Reflector.RenderEnderCrystal_modelEnderCrystalNoBase.exists()) {
/*    */       
/* 41 */       Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystalNoBase");
/* 42 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 46 */     Reflector.setFieldValue(renderendercrystal, Reflector.RenderEnderCrystal_modelEnderCrystalNoBase, modelBase);
/* 47 */     renderendercrystal.shadowSize = shadowSize;
/* 48 */     return (IEntityRenderer)renderendercrystal;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterEnderCrystalNoBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */