/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIllager;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityMob;
/*    */ import net.minecraft.entity.monster.EntitySpellcasterIllager;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderEvoker extends RenderLiving<EntityMob> {
/* 14 */   private static final ResourceLocation field_191338_a = new ResourceLocation("textures/entity/illager/evoker.png");
/*    */ 
/*    */   
/*    */   public RenderEvoker(RenderManager p_i47207_1_) {
/* 18 */     super(p_i47207_1_, (ModelBase)new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
/* 19 */     addLayer(new LayerHeldItem(this)
/*    */         {
/*    */           public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
/*    */           {
/* 23 */             if (((EntitySpellcasterIllager)entitylivingbaseIn).func_193082_dl())
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
/*    */   protected ResourceLocation getEntityTexture(EntityMob entity) {
/* 40 */     return field_191338_a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime) {
/* 48 */     float f = 0.9375F;
/* 49 */     GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderEvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */