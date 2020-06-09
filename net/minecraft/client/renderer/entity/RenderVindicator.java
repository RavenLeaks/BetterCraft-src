/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIllager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityMob;
/*    */ import net.minecraft.entity.monster.EntityVindicator;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderVindicator extends RenderLiving<EntityMob> {
/* 14 */   private static final ResourceLocation field_191357_a = new ResourceLocation("textures/entity/illager/vindicator.png");
/*    */ 
/*    */   
/*    */   public RenderVindicator(RenderManager p_i47189_1_) {
/* 18 */     super(p_i47189_1_, (ModelBase)new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
/* 19 */     addLayer(new LayerHeldItem(this)
/*    */         {
/*    */           public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
/*    */           {
/* 23 */             if (((EntityVindicator)entitylivingbaseIn).func_190639_o())
/*    */             {
/* 25 */               super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*    */             }
/*    */           }
/*    */           
/*    */           protected void func_191361_a(EnumHandSide p_191361_1_) {
/* 30 */             ((ModelIllager)this.livingEntityRenderer.getMainModel()).func_191216_a(p_191361_1_).postRender(0.0625F);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityMob entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 40 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityMob entity) {
/* 48 */     return field_191357_a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime) {
/* 56 */     float f = 0.9375F;
/* 57 */     GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderVindicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */