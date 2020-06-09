/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
/*    */ import net.minecraft.entity.item.EntityMinecartMobSpawner;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecartMobSpawner
/*    */   extends ModelAdapterMinecart
/*    */ {
/*    */   public ModelAdapterMinecartMobSpawner() {
/* 15 */     super(EntityMinecartMobSpawner.class, "spawner_minecart", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 20 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 21 */     RenderMinecartMobSpawner renderminecartmobspawner = new RenderMinecartMobSpawner(rendermanager);
/*    */     
/* 23 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/*    */       
/* 25 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 26 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 30 */     Reflector.setFieldValue(renderminecartmobspawner, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 31 */     renderminecartmobspawner.shadowSize = shadowSize;
/* 32 */     return (IEntityRenderer)renderminecartmobspawner;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterMinecartMobSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */