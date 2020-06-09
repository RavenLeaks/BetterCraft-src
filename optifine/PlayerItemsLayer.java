/*    */ package optifine;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public class PlayerItemsLayer implements LayerRenderer {
/* 14 */   private RenderPlayer renderPlayer = null;
/*    */ 
/*    */   
/*    */   public PlayerItemsLayer(RenderPlayer p_i75_1_) {
/* 18 */     this.renderPlayer = p_i75_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 23 */     renderEquippedItems(entitylivingbaseIn, scale, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderEquippedItems(EntityLivingBase p_renderEquippedItems_1_, float p_renderEquippedItems_2_, float p_renderEquippedItems_3_) {
/* 28 */     if (Config.isShowCapes())
/*    */     {
/* 30 */       if (p_renderEquippedItems_1_ instanceof AbstractClientPlayer) {
/*    */         
/* 32 */         AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_renderEquippedItems_1_;
/* 33 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 34 */         GlStateManager.disableRescaleNormal();
/* 35 */         GlStateManager.enableCull();
/* 36 */         ModelPlayer modelPlayer = this.renderPlayer.getMainModel();
/* 37 */         PlayerConfigurations.renderPlayerItems((ModelBiped)modelPlayer, abstractclientplayer, p_renderEquippedItems_2_, p_renderEquippedItems_3_);
/* 38 */         GlStateManager.disableCull();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void register(Map p_register_0_) {
/* 50 */     Set set = p_register_0_.keySet();
/* 51 */     boolean flag = false;
/*    */     
/* 53 */     for (Object object : set) {
/*    */       
/* 55 */       Object object1 = p_register_0_.get(object);
/*    */       
/* 57 */       if (object1 instanceof RenderPlayer) {
/*    */         
/* 59 */         RenderPlayer renderplayer = (RenderPlayer)object1;
/* 60 */         renderplayer.addLayer(new PlayerItemsLayer(renderplayer));
/* 61 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     if (!flag)
/*    */     {
/* 67 */       Config.warn("PlayerItemsLayer not registered");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\PlayerItemsLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */