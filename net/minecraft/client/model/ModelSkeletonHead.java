/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelSkeletonHead
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer skeletonHead;
/*    */   
/*    */   public ModelSkeletonHead() {
/* 11 */     this(0, 35, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelSkeletonHead(int p_i1155_1_, int p_i1155_2_, int p_i1155_3_, int p_i1155_4_) {
/* 16 */     this.textureWidth = p_i1155_3_;
/* 17 */     this.textureHeight = p_i1155_4_;
/* 18 */     this.skeletonHead = new ModelRenderer(this, p_i1155_1_, p_i1155_2_);
/* 19 */     this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
/* 20 */     this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 28 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 29 */     this.skeletonHead.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 39 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 40 */     this.skeletonHead.rotateAngleY = netHeadYaw * 0.017453292F;
/* 41 */     this.skeletonHead.rotateAngleX = headPitch * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelSkeletonHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */