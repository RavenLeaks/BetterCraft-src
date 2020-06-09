/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelGhast;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderGhast extends RenderLiving<EntityGhast> {
/* 10 */   private static final ResourceLocation GHAST_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast.png");
/* 11 */   private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
/*    */ 
/*    */   
/*    */   public RenderGhast(RenderManager renderManagerIn) {
/* 15 */     super(renderManagerIn, (ModelBase)new ModelGhast(), 0.5F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityGhast entity) {
/* 23 */     return entity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityGhast entitylivingbaseIn, float partialTickTime) {
/* 31 */     float f = 1.0F;
/* 32 */     float f1 = 4.5F;
/* 33 */     float f2 = 4.5F;
/* 34 */     GlStateManager.scale(4.5F, 4.5F, 4.5F);
/* 35 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */