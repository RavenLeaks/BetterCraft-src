/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderHusk extends RenderZombie {
/*  9 */   private static final ResourceLocation HUSK_ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/husk.png");
/*    */ 
/*    */   
/*    */   public RenderHusk(RenderManager p_i47204_1_) {
/* 13 */     super(p_i47204_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityZombie entitylivingbaseIn, float partialTickTime) {
/* 21 */     float f = 1.0625F;
/* 22 */     GlStateManager.scale(1.0625F, 1.0625F, 1.0625F);
/* 23 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityZombie entity) {
/* 31 */     return HUSK_ZOMBIE_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderHusk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */