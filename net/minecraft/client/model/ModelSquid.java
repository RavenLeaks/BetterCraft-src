/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelSquid
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer squidBody;
/* 11 */   ModelRenderer[] squidTentacles = new ModelRenderer[8];
/*    */ 
/*    */   
/*    */   public ModelSquid() {
/* 15 */     int i = -16;
/* 16 */     this.squidBody = new ModelRenderer(this, 0, 0);
/* 17 */     this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
/* 18 */     this.squidBody.rotationPointY += 8.0F;
/*    */     
/* 20 */     for (int j = 0; j < this.squidTentacles.length; j++) {
/*    */       
/* 22 */       this.squidTentacles[j] = new ModelRenderer(this, 48, 0);
/* 23 */       double d0 = j * Math.PI * 2.0D / this.squidTentacles.length;
/* 24 */       float f = (float)Math.cos(d0) * 5.0F;
/* 25 */       float f1 = (float)Math.sin(d0) * 5.0F;
/* 26 */       this.squidTentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
/* 27 */       (this.squidTentacles[j]).rotationPointX = f;
/* 28 */       (this.squidTentacles[j]).rotationPointZ = f1;
/* 29 */       (this.squidTentacles[j]).rotationPointY = 15.0F;
/* 30 */       d0 = j * Math.PI * -2.0D / this.squidTentacles.length + 1.5707963267948966D;
/* 31 */       (this.squidTentacles[j]).rotateAngleY = (float)d0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*    */     byte b;
/*    */     int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 42 */     for (i = (arrayOfModelRenderer = this.squidTentacles).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/*    */       
/* 44 */       modelrenderer.rotateAngleX = ageInTicks;
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 53 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 54 */     this.squidBody.render(scale); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 56 */     for (i = (arrayOfModelRenderer = this.squidTentacles).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/*    */       
/* 58 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */