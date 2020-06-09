/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelGhast
/*    */   extends ModelBase {
/*    */   ModelRenderer body;
/* 11 */   ModelRenderer[] tentacles = new ModelRenderer[9];
/*    */ 
/*    */   
/*    */   public ModelGhast() {
/* 15 */     int i = -16;
/* 16 */     this.body = new ModelRenderer(this, 0, 0);
/* 17 */     this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
/* 18 */     this.body.rotationPointY += 8.0F;
/* 19 */     Random random = new Random(1660L);
/*    */     
/* 21 */     for (int j = 0; j < this.tentacles.length; j++) {
/*    */       
/* 23 */       this.tentacles[j] = new ModelRenderer(this, 0, 0);
/* 24 */       float f = (((j % 3) - (j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 25 */       float f1 = ((j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 26 */       int k = random.nextInt(7) + 8;
/* 27 */       this.tentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, k, 2);
/* 28 */       (this.tentacles[j]).rotationPointX = f;
/* 29 */       (this.tentacles[j]).rotationPointZ = f1;
/* 30 */       (this.tentacles[j]).rotationPointY = 15.0F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 41 */     for (int i = 0; i < this.tentacles.length; i++)
/*    */     {
/* 43 */       (this.tentacles[i]).rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + i) + 0.4F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 52 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 53 */     GlStateManager.pushMatrix();
/* 54 */     GlStateManager.translate(0.0F, 0.6F, 0.0F);
/* 55 */     this.body.render(scale); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 57 */     for (i = (arrayOfModelRenderer = this.tentacles).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/*    */       
/* 59 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */     
/* 62 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */