/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelLlamaSpit
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelLlamaSpit() {
/* 11 */     this(0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 16 */   private final ModelRenderer field_191225_a = new ModelRenderer(this); public ModelLlamaSpit(float p_i47225_1_) {
/* 17 */     int i = 2;
/* 18 */     this.field_191225_a.setTextureOffset(0, 0).addBox(-4.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
/* 19 */     this.field_191225_a.setTextureOffset(0, 0).addBox(0.0F, -4.0F, 0.0F, 2, 2, 2, p_i47225_1_);
/* 20 */     this.field_191225_a.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -4.0F, 2, 2, 2, p_i47225_1_);
/* 21 */     this.field_191225_a.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
/* 22 */     this.field_191225_a.setTextureOffset(0, 0).addBox(2.0F, 0.0F, 0.0F, 2, 2, 2, p_i47225_1_);
/* 23 */     this.field_191225_a.setTextureOffset(0, 0).addBox(0.0F, 2.0F, 0.0F, 2, 2, 2, p_i47225_1_);
/* 24 */     this.field_191225_a.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 2.0F, 2, 2, 2, p_i47225_1_);
/* 25 */     this.field_191225_a.setRotationPoint(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 33 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 34 */     this.field_191225_a.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelLlamaSpit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */