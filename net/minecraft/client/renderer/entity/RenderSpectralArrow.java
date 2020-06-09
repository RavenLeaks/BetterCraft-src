/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSpectralArrow extends RenderArrow<EntitySpectralArrow> {
/*  8 */   public static final ResourceLocation RES_SPECTRAL_ARROW = new ResourceLocation("textures/entity/projectiles/spectral_arrow.png");
/*    */ 
/*    */   
/*    */   public RenderSpectralArrow(RenderManager manager) {
/* 12 */     super(manager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySpectralArrow entity) {
/* 20 */     return RES_SPECTRAL_ARROW;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderSpectralArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */