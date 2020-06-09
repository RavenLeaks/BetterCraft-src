/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelSlime
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer slimeBodies;
/*    */   ModelRenderer slimeRightEye;
/*    */   ModelRenderer slimeLeftEye;
/*    */   ModelRenderer slimeMouth;
/*    */   
/*    */   public ModelSlime(int p_i1157_1_) {
/* 22 */     if (p_i1157_1_ > 0) {
/*    */       
/* 24 */       this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
/* 25 */       this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
/* 26 */       this.slimeRightEye = new ModelRenderer(this, 32, 0);
/* 27 */       this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
/* 28 */       this.slimeLeftEye = new ModelRenderer(this, 32, 4);
/* 29 */       this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
/* 30 */       this.slimeMouth = new ModelRenderer(this, 32, 8);
/* 31 */       this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
/*    */     }
/*    */     else {
/*    */       
/* 35 */       this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
/* 36 */       this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 45 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 46 */     GlStateManager.translate(0.0F, 0.001F, 0.0F);
/* 47 */     this.slimeBodies.render(scale);
/*    */     
/* 49 */     if (this.slimeRightEye != null) {
/*    */       
/* 51 */       this.slimeRightEye.render(scale);
/* 52 */       this.slimeLeftEye.render(scale);
/* 53 */       this.slimeMouth.render(scale);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelSlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */