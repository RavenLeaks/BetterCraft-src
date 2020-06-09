/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIllager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIllusionIllager;
/*    */ import net.minecraft.entity.monster.EntityMob;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class RenderIllusionIllager extends RenderLiving<EntityMob> {
/* 16 */   private static final ResourceLocation field_193121_a = new ResourceLocation("textures/entity/illager/illusionist.png");
/*    */ 
/*    */   
/*    */   public RenderIllusionIllager(RenderManager p_i47477_1_) {
/* 20 */     super(p_i47477_1_, (ModelBase)new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
/* 21 */     addLayer(new LayerHeldItem(this)
/*    */         {
/*    */           public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
/*    */           {
/* 25 */             if (((EntityIllusionIllager)entitylivingbaseIn).func_193082_dl() || ((EntityIllusionIllager)entitylivingbaseIn).func_193096_dj())
/*    */             {
/* 27 */               super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*    */             }
/*    */           }
/*    */           
/*    */           protected void func_191361_a(EnumHandSide p_191361_1_) {
/* 32 */             ((ModelIllager)this.livingEntityRenderer.getMainModel()).func_191216_a(p_191361_1_).postRender(0.0625F);
/*    */           }
/*    */         });
/* 35 */     ((ModelIllager)getMainModel()).field_193775_b.showModel = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityMob entity) {
/* 43 */     return field_193121_a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime) {
/* 51 */     float f = 0.9375F;
/* 52 */     GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityMob entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 60 */     if (entity.isInvisible()) {
/*    */       
/* 62 */       Vec3d[] avec3d = ((EntityIllusionIllager)entity).func_193098_a(partialTicks);
/* 63 */       float f = handleRotationFloat(entity, partialTicks);
/*    */       
/* 65 */       for (int i = 0; i < avec3d.length; i++)
/*    */       {
/* 67 */         super.doRender(entity, x + (avec3d[i]).xCoord + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).yCoord + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).zCoord + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 72 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderName(EntityMob entity, double x, double y, double z) {
/* 78 */     super.renderName(entity, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean func_193115_c(EntityMob p_193115_1_) {
/* 83 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderIllusionIllager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */