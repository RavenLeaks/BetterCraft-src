/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderCreeper extends RenderLiving<EntityCreeper> {
/* 12 */   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
/*    */ 
/*    */   
/*    */   public RenderCreeper(RenderManager renderManagerIn) {
/* 16 */     super(renderManagerIn, (ModelBase)new ModelCreeper(), 0.5F);
/* 17 */     addLayer(new LayerCreeperCharge(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityCreeper entitylivingbaseIn, float partialTickTime) {
/* 25 */     float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
/* 26 */     float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
/* 27 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 28 */     f *= f;
/* 29 */     f *= f;
/* 30 */     float f2 = (1.0F + f * 0.4F) * f1;
/* 31 */     float f3 = (1.0F + f * 0.1F) / f1;
/* 32 */     GlStateManager.scale(f2, f3, f2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getColorMultiplier(EntityCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
/* 40 */     float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
/*    */     
/* 42 */     if ((int)(f * 10.0F) % 2 == 0)
/*    */     {
/* 44 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 48 */     int i = (int)(f * 0.2F * 255.0F);
/* 49 */     i = MathHelper.clamp(i, 0, 255);
/* 50 */     return i << 24 | 0x30FFFFFF;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityCreeper entity) {
/* 59 */     return CREEPER_TEXTURES;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */