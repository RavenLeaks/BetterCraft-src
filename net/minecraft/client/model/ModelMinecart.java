/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelMinecart
/*    */   extends ModelBase {
/*  7 */   public ModelRenderer[] sideModels = new ModelRenderer[7];
/*    */ 
/*    */   
/*    */   public ModelMinecart() {
/* 11 */     this.sideModels[0] = new ModelRenderer(this, 0, 10);
/* 12 */     this.sideModels[1] = new ModelRenderer(this, 0, 0);
/* 13 */     this.sideModels[2] = new ModelRenderer(this, 0, 0);
/* 14 */     this.sideModels[3] = new ModelRenderer(this, 0, 0);
/* 15 */     this.sideModels[4] = new ModelRenderer(this, 0, 0);
/* 16 */     this.sideModels[5] = new ModelRenderer(this, 44, 10);
/* 17 */     int i = 20;
/* 18 */     int j = 8;
/* 19 */     int k = 16;
/* 20 */     int l = 4;
/* 21 */     this.sideModels[0].addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
/* 22 */     this.sideModels[0].setRotationPoint(0.0F, 4.0F, 0.0F);
/* 23 */     this.sideModels[5].addBox(-9.0F, -7.0F, -1.0F, 18, 14, 1, 0.0F);
/* 24 */     this.sideModels[5].setRotationPoint(0.0F, 4.0F, 0.0F);
/* 25 */     this.sideModels[1].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
/* 26 */     this.sideModels[1].setRotationPoint(-9.0F, 4.0F, 0.0F);
/* 27 */     this.sideModels[2].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
/* 28 */     this.sideModels[2].setRotationPoint(9.0F, 4.0F, 0.0F);
/* 29 */     this.sideModels[3].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
/* 30 */     this.sideModels[3].setRotationPoint(0.0F, 4.0F, -7.0F);
/* 31 */     this.sideModels[4].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
/* 32 */     this.sideModels[4].setRotationPoint(0.0F, 4.0F, 7.0F);
/* 33 */     (this.sideModels[0]).rotateAngleX = 1.5707964F;
/* 34 */     (this.sideModels[1]).rotateAngleY = 4.712389F;
/* 35 */     (this.sideModels[2]).rotateAngleY = 1.5707964F;
/* 36 */     (this.sideModels[3]).rotateAngleY = 3.1415927F;
/* 37 */     (this.sideModels[5]).rotateAngleX = -1.5707964F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 45 */     (this.sideModels[5]).rotationPointY = 4.0F - ageInTicks;
/*    */     
/* 47 */     for (int i = 0; i < 6; i++)
/*    */     {
/* 49 */       this.sideModels[i].render(scale);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */