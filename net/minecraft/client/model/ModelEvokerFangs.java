/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelEvokerFangs
/*    */   extends ModelBase {
/*  8 */   private final ModelRenderer field_191213_a = new ModelRenderer(this, 0, 0);
/*    */   
/*    */   private final ModelRenderer field_191214_b;
/*    */   private final ModelRenderer field_191215_c;
/*    */   
/*    */   public ModelEvokerFangs() {
/* 14 */     this.field_191213_a.setRotationPoint(-5.0F, 22.0F, -5.0F);
/* 15 */     this.field_191213_a.addBox(0.0F, 0.0F, 0.0F, 10, 12, 10);
/* 16 */     this.field_191214_b = new ModelRenderer(this, 40, 0);
/* 17 */     this.field_191214_b.setRotationPoint(1.5F, 22.0F, -4.0F);
/* 18 */     this.field_191214_b.addBox(0.0F, 0.0F, 0.0F, 4, 14, 8);
/* 19 */     this.field_191215_c = new ModelRenderer(this, 40, 0);
/* 20 */     this.field_191215_c.setRotationPoint(-1.5F, 22.0F, 4.0F);
/* 21 */     this.field_191215_c.addBox(0.0F, 0.0F, 0.0F, 4, 14, 8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 29 */     float f = limbSwing * 2.0F;
/*    */     
/* 31 */     if (f > 1.0F)
/*    */     {
/* 33 */       f = 1.0F;
/*    */     }
/*    */     
/* 36 */     f = 1.0F - f * f * f;
/* 37 */     this.field_191214_b.rotateAngleZ = 3.1415927F - f * 0.35F * 3.1415927F;
/* 38 */     this.field_191215_c.rotateAngleZ = 3.1415927F + f * 0.35F * 3.1415927F;
/* 39 */     this.field_191215_c.rotateAngleY = 3.1415927F;
/* 40 */     float f1 = (limbSwing + MathHelper.sin(limbSwing * 2.7F)) * 0.6F * 12.0F;
/* 41 */     this.field_191214_b.rotationPointY = 24.0F - f1;
/* 42 */     this.field_191215_c.rotationPointY = this.field_191214_b.rotationPointY;
/* 43 */     this.field_191213_a.rotationPointY = this.field_191214_b.rotationPointY;
/* 44 */     this.field_191213_a.render(scale);
/* 45 */     this.field_191214_b.render(scale);
/* 46 */     this.field_191215_c.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelEvokerFangs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */