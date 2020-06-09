/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelSilverfish
/*    */   extends ModelBase
/*    */ {
/*  9 */   private final ModelRenderer[] silverfishBodyParts = new ModelRenderer[7];
/*    */   
/*    */   private final ModelRenderer[] silverfishWings;
/*    */   
/* 13 */   private final float[] zPlacement = new float[7];
/*    */ 
/*    */   
/* 16 */   private static final int[][] SILVERFISH_BOX_LENGTH = new int[][] { { 3, 2, 2 }, { 4, 3, 2 }, { 6, 4, 3 }, { 3, 3, 3 }, { 2, 2, 3 }, { 2, 1, 2 }, { 1, 1, 2 } };
/*    */ 
/*    */   
/* 19 */   private static final int[][] SILVERFISH_TEXTURE_POSITIONS = new int[][] { new int[2], { 0, 4 }, { 0, 9 }, { 0, 16 }, { 0, 22 }, { 11 }, { 13, 4 } };
/*    */ 
/*    */   
/*    */   public ModelSilverfish() {
/* 23 */     float f = -3.5F;
/*    */     
/* 25 */     for (int i = 0; i < this.silverfishBodyParts.length; i++) {
/*    */       
/* 27 */       this.silverfishBodyParts[i] = new ModelRenderer(this, SILVERFISH_TEXTURE_POSITIONS[i][0], SILVERFISH_TEXTURE_POSITIONS[i][1]);
/* 28 */       this.silverfishBodyParts[i].addBox(SILVERFISH_BOX_LENGTH[i][0] * -0.5F, 0.0F, SILVERFISH_BOX_LENGTH[i][2] * -0.5F, SILVERFISH_BOX_LENGTH[i][0], SILVERFISH_BOX_LENGTH[i][1], SILVERFISH_BOX_LENGTH[i][2]);
/* 29 */       this.silverfishBodyParts[i].setRotationPoint(0.0F, (24 - SILVERFISH_BOX_LENGTH[i][1]), f);
/* 30 */       this.zPlacement[i] = f;
/*    */       
/* 32 */       if (i < this.silverfishBodyParts.length - 1)
/*    */       {
/* 34 */         f += (SILVERFISH_BOX_LENGTH[i][2] + SILVERFISH_BOX_LENGTH[i + 1][2]) * 0.5F;
/*    */       }
/*    */     } 
/*    */     
/* 38 */     this.silverfishWings = new ModelRenderer[3];
/* 39 */     this.silverfishWings[0] = new ModelRenderer(this, 20, 0);
/* 40 */     this.silverfishWings[0].addBox(-5.0F, 0.0F, SILVERFISH_BOX_LENGTH[2][2] * -0.5F, 10, 8, SILVERFISH_BOX_LENGTH[2][2]);
/* 41 */     this.silverfishWings[0].setRotationPoint(0.0F, 16.0F, this.zPlacement[2]);
/* 42 */     this.silverfishWings[1] = new ModelRenderer(this, 20, 11);
/* 43 */     this.silverfishWings[1].addBox(-3.0F, 0.0F, SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6, 4, SILVERFISH_BOX_LENGTH[4][2]);
/* 44 */     this.silverfishWings[1].setRotationPoint(0.0F, 20.0F, this.zPlacement[4]);
/* 45 */     this.silverfishWings[2] = new ModelRenderer(this, 20, 18);
/* 46 */     this.silverfishWings[2].addBox(-3.0F, 0.0F, SILVERFISH_BOX_LENGTH[4][2] * -0.5F, 6, 5, SILVERFISH_BOX_LENGTH[1][2]);
/* 47 */     this.silverfishWings[2].setRotationPoint(0.0F, 19.0F, this.zPlacement[1]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 55 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 57 */     for (i = (arrayOfModelRenderer = this.silverfishBodyParts).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/*    */       
/* 59 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */     
/* 62 */     for (i = (arrayOfModelRenderer = this.silverfishWings).length, b = 0; b < i; ) { ModelRenderer modelrenderer1 = arrayOfModelRenderer[b];
/*    */       
/* 64 */       modelrenderer1.render(scale);
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
/* 75 */     for (int i = 0; i < this.silverfishBodyParts.length; i++) {
/*    */       
/* 77 */       (this.silverfishBodyParts[i]).rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * 0.05F * (1 + Math.abs(i - 2));
/* 78 */       (this.silverfishBodyParts[i]).rotationPointX = MathHelper.sin(ageInTicks * 0.9F + i * 0.15F * 3.1415927F) * 3.1415927F * 0.2F * Math.abs(i - 2);
/*    */     } 
/*    */     
/* 81 */     (this.silverfishWings[0]).rotateAngleY = (this.silverfishBodyParts[2]).rotateAngleY;
/* 82 */     (this.silverfishWings[1]).rotateAngleY = (this.silverfishBodyParts[4]).rotateAngleY;
/* 83 */     (this.silverfishWings[1]).rotationPointX = (this.silverfishBodyParts[4]).rotationPointX;
/* 84 */     (this.silverfishWings[2]).rotateAngleY = (this.silverfishBodyParts[1]).rotateAngleY;
/* 85 */     (this.silverfishWings[2]).rotationPointX = (this.silverfishBodyParts[1]).rotationPointX;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelSilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */