/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelBlaze
/*    */   extends ModelBase
/*    */ {
/*  9 */   private final ModelRenderer[] blazeSticks = new ModelRenderer[12];
/*    */   
/*    */   private final ModelRenderer blazeHead;
/*    */   
/*    */   public ModelBlaze() {
/* 14 */     for (int i = 0; i < this.blazeSticks.length; i++) {
/*    */       
/* 16 */       this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
/* 17 */       this.blazeSticks[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
/*    */     } 
/*    */     
/* 20 */     this.blazeHead = new ModelRenderer(this, 0, 0);
/* 21 */     this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 29 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 30 */     this.blazeHead.render(scale); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 32 */     for (i = (arrayOfModelRenderer = this.blazeSticks).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/*    */       
/* 34 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 45 */     float f = ageInTicks * 3.1415927F * -0.1F;
/*    */     
/* 47 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 49 */       (this.blazeSticks[i]).rotationPointY = -2.0F + MathHelper.cos(((i * 2) + ageInTicks) * 0.25F);
/* 50 */       (this.blazeSticks[i]).rotationPointX = MathHelper.cos(f) * 9.0F;
/* 51 */       (this.blazeSticks[i]).rotationPointZ = MathHelper.sin(f) * 9.0F;
/* 52 */       f++;
/*    */     } 
/*    */     
/* 55 */     f = 0.7853982F + ageInTicks * 3.1415927F * 0.03F;
/*    */     
/* 57 */     for (int j = 4; j < 8; j++) {
/*    */       
/* 59 */       (this.blazeSticks[j]).rotationPointY = 2.0F + MathHelper.cos(((j * 2) + ageInTicks) * 0.25F);
/* 60 */       (this.blazeSticks[j]).rotationPointX = MathHelper.cos(f) * 7.0F;
/* 61 */       (this.blazeSticks[j]).rotationPointZ = MathHelper.sin(f) * 7.0F;
/* 62 */       f++;
/*    */     } 
/*    */     
/* 65 */     f = 0.47123894F + ageInTicks * 3.1415927F * -0.05F;
/*    */     
/* 67 */     for (int k = 8; k < 12; k++) {
/*    */       
/* 69 */       (this.blazeSticks[k]).rotationPointY = 11.0F + MathHelper.cos((k * 1.5F + ageInTicks) * 0.5F);
/* 70 */       (this.blazeSticks[k]).rotationPointX = MathHelper.cos(f) * 5.0F;
/* 71 */       (this.blazeSticks[k]).rotationPointZ = MathHelper.sin(f) * 5.0F;
/* 72 */       f++;
/*    */     } 
/*    */     
/* 75 */     this.blazeHead.rotateAngleY = netHeadYaw * 0.017453292F;
/* 76 */     this.blazeHead.rotateAngleX = headPitch * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBlaze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */