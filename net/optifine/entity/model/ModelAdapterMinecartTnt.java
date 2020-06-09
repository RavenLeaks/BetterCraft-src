/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderTntMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecartTNT;
/*    */ import optifine.Config;
/*    */ import optifine.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecartTnt
/*    */   extends ModelAdapterMinecart
/*    */ {
/*    */   public ModelAdapterMinecartTnt() {
/* 15 */     super(EntityMinecartTNT.class, "tnt_minecart", 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 20 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 21 */     RenderTntMinecart rendertntminecart = new RenderTntMinecart(rendermanager);
/*    */     
/* 23 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/*    */       
/* 25 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 26 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 30 */     Reflector.setFieldValue(rendertntminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 31 */     rendertntminecart.shadowSize = shadowSize;
/* 32 */     return (IEntityRenderer)rendertntminecart;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\ModelAdapterMinecartTnt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */