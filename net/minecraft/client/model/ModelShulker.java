/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.monster.EntityShulker;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelShulker
/*    */   extends ModelBase
/*    */ {
/*    */   public final ModelRenderer base;
/*    */   public final ModelRenderer lid;
/*    */   public ModelRenderer head;
/*    */   
/*    */   public ModelShulker() {
/* 15 */     this.textureHeight = 64;
/* 16 */     this.textureWidth = 64;
/* 17 */     this.lid = new ModelRenderer(this);
/* 18 */     this.base = new ModelRenderer(this);
/* 19 */     this.head = new ModelRenderer(this);
/* 20 */     this.lid.setTextureOffset(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16, 12, 16);
/* 21 */     this.lid.setRotationPoint(0.0F, 24.0F, 0.0F);
/* 22 */     this.base.setTextureOffset(0, 28).addBox(-8.0F, -8.0F, -8.0F, 16, 8, 16);
/* 23 */     this.base.setRotationPoint(0.0F, 24.0F, 0.0F);
/* 24 */     this.head.setTextureOffset(0, 52).addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6);
/* 25 */     this.head.setRotationPoint(0.0F, 12.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 35 */     EntityShulker entityshulker = (EntityShulker)entityIn;
/* 36 */     float f = ageInTicks - entityshulker.ticksExisted;
/* 37 */     float f1 = (0.5F + entityshulker.getClientPeekAmount(f)) * 3.1415927F;
/* 38 */     float f2 = -1.0F + MathHelper.sin(f1);
/* 39 */     float f3 = 0.0F;
/*    */     
/* 41 */     if (f1 > 3.1415927F)
/*    */     {
/* 43 */       f3 = MathHelper.sin(ageInTicks * 0.1F) * 0.7F;
/*    */     }
/*    */     
/* 46 */     this.lid.setRotationPoint(0.0F, 16.0F + MathHelper.sin(f1) * 8.0F + f3, 0.0F);
/*    */     
/* 48 */     if (entityshulker.getClientPeekAmount(f) > 0.3F) {
/*    */       
/* 50 */       this.lid.rotateAngleY = f2 * f2 * f2 * f2 * 3.1415927F * 0.125F;
/*    */     }
/*    */     else {
/*    */       
/* 54 */       this.lid.rotateAngleY = 0.0F;
/*    */     } 
/*    */     
/* 57 */     this.head.rotateAngleX = headPitch * 0.017453292F;
/* 58 */     this.head.rotateAngleY = netHeadYaw * 0.017453292F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 66 */     this.base.render(scale);
/* 67 */     this.lid.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelShulker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */