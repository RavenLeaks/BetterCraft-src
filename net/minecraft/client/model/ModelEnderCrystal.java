/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelEnderCrystal
/*    */   extends ModelBase
/*    */ {
/*    */   private final ModelRenderer cube;
/* 12 */   private final ModelRenderer glass = new ModelRenderer(this, "glass");
/*    */ 
/*    */   
/*    */   private ModelRenderer base;
/*    */ 
/*    */   
/*    */   public ModelEnderCrystal(float p_i1170_1_, boolean renderBase) {
/* 19 */     this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 20 */     this.cube = new ModelRenderer(this, "cube");
/* 21 */     this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*    */     
/* 23 */     if (renderBase) {
/*    */       
/* 25 */       this.base = new ModelRenderer(this, "base");
/* 26 */       this.base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 35 */     GlStateManager.pushMatrix();
/* 36 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 37 */     GlStateManager.translate(0.0F, -0.5F, 0.0F);
/*    */     
/* 39 */     if (this.base != null)
/*    */     {
/* 41 */       this.base.render(scale);
/*    */     }
/*    */     
/* 44 */     GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
/* 45 */     GlStateManager.translate(0.0F, 0.8F + ageInTicks, 0.0F);
/* 46 */     GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 47 */     this.glass.render(scale);
/* 48 */     float f = 0.875F;
/* 49 */     GlStateManager.scale(0.875F, 0.875F, 0.875F);
/* 50 */     GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 51 */     GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
/* 52 */     this.glass.render(scale);
/* 53 */     GlStateManager.scale(0.875F, 0.875F, 0.875F);
/* 54 */     GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 55 */     GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
/* 56 */     this.cube.render(scale);
/* 57 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */