/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class RenderChicken extends RenderLiving<EntityChicken> {
/* 10 */   private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation("textures/entity/chicken.png");
/*    */ 
/*    */   
/*    */   public RenderChicken(RenderManager p_i47211_1_) {
/* 14 */     super(p_i47211_1_, (ModelBase)new ModelChicken(), 0.3F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityChicken entity) {
/* 22 */     return CHICKEN_TEXTURES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float handleRotationFloat(EntityChicken livingBase, float partialTicks) {
/* 30 */     float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
/* 31 */     float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
/* 32 */     return (MathHelper.sin(f) + 1.0F) * f1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */