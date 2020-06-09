/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelDragonHead
/*    */   extends ModelBase
/*    */ {
/*    */   private final ModelRenderer head;
/*    */   private final ModelRenderer jaw;
/*    */   
/*    */   public ModelDragonHead(float p_i46588_1_) {
/* 13 */     this.textureWidth = 256;
/* 14 */     this.textureHeight = 256;
/* 15 */     setTextureOffset("body.body", 0, 0);
/* 16 */     setTextureOffset("wing.skin", -56, 88);
/* 17 */     setTextureOffset("wingtip.skin", -56, 144);
/* 18 */     setTextureOffset("rearleg.main", 0, 0);
/* 19 */     setTextureOffset("rearfoot.main", 112, 0);
/* 20 */     setTextureOffset("rearlegtip.main", 196, 0);
/* 21 */     setTextureOffset("head.upperhead", 112, 30);
/* 22 */     setTextureOffset("wing.bone", 112, 88);
/* 23 */     setTextureOffset("head.upperlip", 176, 44);
/* 24 */     setTextureOffset("jaw.jaw", 176, 65);
/* 25 */     setTextureOffset("frontleg.main", 112, 104);
/* 26 */     setTextureOffset("wingtip.bone", 112, 136);
/* 27 */     setTextureOffset("frontfoot.main", 144, 104);
/* 28 */     setTextureOffset("neck.box", 192, 104);
/* 29 */     setTextureOffset("frontlegtip.main", 226, 138);
/* 30 */     setTextureOffset("body.scale", 220, 53);
/* 31 */     setTextureOffset("head.scale", 0, 0);
/* 32 */     setTextureOffset("neck.scale", 48, 0);
/* 33 */     setTextureOffset("head.nostril", 112, 0);
/* 34 */     float f = -16.0F;
/* 35 */     this.head = new ModelRenderer(this, "head");
/* 36 */     this.head.addBox("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16);
/* 37 */     this.head.addBox("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16);
/* 38 */     this.head.mirror = true;
/* 39 */     this.head.addBox("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6);
/* 40 */     this.head.addBox("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4);
/* 41 */     this.head.mirror = false;
/* 42 */     this.head.addBox("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6);
/* 43 */     this.head.addBox("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4);
/* 44 */     this.jaw = new ModelRenderer(this, "jaw");
/* 45 */     this.jaw.setRotationPoint(0.0F, 4.0F, -8.0F);
/* 46 */     this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
/* 47 */     this.head.addChild(this.jaw);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 55 */     this.jaw.rotateAngleX = (float)(Math.sin((limbSwing * 3.1415927F * 0.2F)) + 1.0D) * 0.2F;
/* 56 */     this.head.rotateAngleY = netHeadYaw * 0.017453292F;
/* 57 */     this.head.rotateAngleX = headPitch * 0.017453292F;
/* 58 */     GlStateManager.translate(0.0F, -0.374375F, 0.0F);
/* 59 */     GlStateManager.scale(0.75F, 0.75F, 0.75F);
/* 60 */     this.head.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelDragonHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */