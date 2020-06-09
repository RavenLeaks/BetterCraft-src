/*    */ package me.nzxter.bettercraft.mods.cosmetics;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ public abstract class CosmeticBase implements LayerRenderer<AbstractClientPlayer> {
/*    */   protected final RenderPlayer playerRenderer;
/*    */   
/*    */   public CosmeticBase(RenderPlayer playerRenderer) {
/* 12 */     this.playerRenderer = playerRenderer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 18 */     if (player.hasPlayerInfo() && !player.isInvisible()) {
/* 19 */       render(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {}
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\cosmetics\CosmeticBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */