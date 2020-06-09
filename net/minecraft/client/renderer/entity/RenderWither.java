/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelWither;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityWither;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderWither extends RenderLiving<EntityWither> {
/* 11 */   private static final ResourceLocation INVULNERABLE_WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 12 */   private static final ResourceLocation WITHER_TEXTURES = new ResourceLocation("textures/entity/wither/wither.png");
/*    */ 
/*    */   
/*    */   public RenderWither(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn, (ModelBase)new ModelWither(0.0F), 1.0F);
/* 17 */     addLayer(new LayerWitherAura(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityWither entity) {
/* 25 */     int i = entity.getInvulTime();
/* 26 */     return (i > 0 && (i > 80 || i / 5 % 2 != 1)) ? INVULNERABLE_WITHER_TEXTURES : WITHER_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityWither entitylivingbaseIn, float partialTickTime) {
/* 34 */     float f = 2.0F;
/* 35 */     int i = entitylivingbaseIn.getInvulTime();
/*    */     
/* 37 */     if (i > 0)
/*    */     {
/* 39 */       f -= (i - partialTickTime) / 220.0F * 0.5F;
/*    */     }
/*    */     
/* 42 */     GlStateManager.scale(f, f, f);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */