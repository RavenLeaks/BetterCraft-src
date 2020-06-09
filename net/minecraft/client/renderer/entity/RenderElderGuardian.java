/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityGuardian;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderElderGuardian extends RenderGuardian {
/*  9 */   private static final ResourceLocation GUARDIAN_ELDER_TEXTURE = new ResourceLocation("textures/entity/guardian_elder.png");
/*    */ 
/*    */   
/*    */   public RenderElderGuardian(RenderManager p_i47209_1_) {
/* 13 */     super(p_i47209_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityGuardian entitylivingbaseIn, float partialTickTime) {
/* 21 */     GlStateManager.scale(2.35F, 2.35F, 2.35F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityGuardian entity) {
/* 29 */     return GUARDIAN_ELDER_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderElderGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */