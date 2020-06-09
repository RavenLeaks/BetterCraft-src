/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.monster.EntityVex;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelVex
/*    */   extends ModelBiped
/*    */ {
/*    */   protected ModelRenderer field_191229_a;
/*    */   protected ModelRenderer field_191230_b;
/*    */   
/*    */   public ModelVex() {
/* 15 */     this(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelVex(float p_i47224_1_) {
/* 20 */     super(p_i47224_1_, 0.0F, 64, 64);
/* 21 */     this.bipedLeftLeg.showModel = false;
/* 22 */     this.bipedHeadwear.showModel = false;
/* 23 */     this.bipedRightLeg = new ModelRenderer(this, 32, 0);
/* 24 */     this.bipedRightLeg.addBox(-1.0F, -1.0F, -2.0F, 6, 10, 4, 0.0F);
/* 25 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
/* 26 */     this.field_191230_b = new ModelRenderer(this, 0, 32);
/* 27 */     this.field_191230_b.addBox(-20.0F, 0.0F, 0.0F, 20, 12, 1);
/* 28 */     this.field_191229_a = new ModelRenderer(this, 0, 32);
/* 29 */     this.field_191229_a.mirror = true;
/* 30 */     this.field_191229_a.addBox(0.0F, 0.0F, 0.0F, 20, 12, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 38 */     super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 39 */     this.field_191230_b.render(scale);
/* 40 */     this.field_191229_a.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 50 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 51 */     EntityVex entityvex = (EntityVex)entityIn;
/*    */     
/* 53 */     if (entityvex.func_190647_dj())
/*    */     {
/* 55 */       if (entityvex.getPrimaryHand() == EnumHandSide.RIGHT) {
/*    */         
/* 57 */         this.bipedRightArm.rotateAngleX = 3.7699115F;
/*    */       }
/*    */       else {
/*    */         
/* 61 */         this.bipedLeftArm.rotateAngleX = 3.7699115F;
/*    */       } 
/*    */     }
/*    */     
/* 65 */     this.bipedRightLeg.rotateAngleX += 0.62831855F;
/* 66 */     this.field_191230_b.rotationPointZ = 2.0F;
/* 67 */     this.field_191229_a.rotationPointZ = 2.0F;
/* 68 */     this.field_191230_b.rotationPointY = 1.0F;
/* 69 */     this.field_191229_a.rotationPointY = 1.0F;
/* 70 */     this.field_191230_b.rotateAngleY = 0.47123894F + MathHelper.cos(ageInTicks * 0.8F) * 3.1415927F * 0.05F;
/* 71 */     this.field_191229_a.rotateAngleY = -this.field_191230_b.rotateAngleY;
/* 72 */     this.field_191229_a.rotateAngleZ = -0.47123894F;
/* 73 */     this.field_191229_a.rotateAngleX = 0.47123894F;
/* 74 */     this.field_191230_b.rotateAngleX = 0.47123894F;
/* 75 */     this.field_191230_b.rotateAngleZ = 0.47123894F;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_191228_a() {
/* 80 */     return 23;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelVex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */