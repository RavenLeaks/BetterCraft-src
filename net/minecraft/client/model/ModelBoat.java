/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class ModelBoat
/*     */   extends ModelBase implements IMultipassModel {
/*  11 */   public ModelRenderer[] boatSides = new ModelRenderer[5];
/*  12 */   public ModelRenderer[] paddles = new ModelRenderer[2];
/*     */ 
/*     */   
/*     */   public ModelRenderer noWater;
/*     */ 
/*     */   
/*  18 */   private final int patchList = GLAllocation.generateDisplayLists(1);
/*     */ 
/*     */   
/*     */   public ModelBoat() {
/*  22 */     this.boatSides[0] = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
/*  23 */     this.boatSides[1] = (new ModelRenderer(this, 0, 19)).setTextureSize(128, 64);
/*  24 */     this.boatSides[2] = (new ModelRenderer(this, 0, 27)).setTextureSize(128, 64);
/*  25 */     this.boatSides[3] = (new ModelRenderer(this, 0, 35)).setTextureSize(128, 64);
/*  26 */     this.boatSides[4] = (new ModelRenderer(this, 0, 43)).setTextureSize(128, 64);
/*  27 */     int i = 32;
/*  28 */     int j = 6;
/*  29 */     int k = 20;
/*  30 */     int l = 4;
/*  31 */     int i1 = 28;
/*  32 */     this.boatSides[0].addBox(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
/*  33 */     this.boatSides[0].setRotationPoint(0.0F, 3.0F, 1.0F);
/*  34 */     this.boatSides[1].addBox(-13.0F, -7.0F, -1.0F, 18, 6, 2, 0.0F);
/*  35 */     this.boatSides[1].setRotationPoint(-15.0F, 4.0F, 4.0F);
/*  36 */     this.boatSides[2].addBox(-8.0F, -7.0F, -1.0F, 16, 6, 2, 0.0F);
/*  37 */     this.boatSides[2].setRotationPoint(15.0F, 4.0F, 0.0F);
/*  38 */     this.boatSides[3].addBox(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
/*  39 */     this.boatSides[3].setRotationPoint(0.0F, 4.0F, -9.0F);
/*  40 */     this.boatSides[4].addBox(-14.0F, -7.0F, -1.0F, 28, 6, 2, 0.0F);
/*  41 */     this.boatSides[4].setRotationPoint(0.0F, 4.0F, 9.0F);
/*  42 */     (this.boatSides[0]).rotateAngleX = 1.5707964F;
/*  43 */     (this.boatSides[1]).rotateAngleY = 4.712389F;
/*  44 */     (this.boatSides[2]).rotateAngleY = 1.5707964F;
/*  45 */     (this.boatSides[3]).rotateAngleY = 3.1415927F;
/*  46 */     this.paddles[0] = makePaddle(true);
/*  47 */     this.paddles[0].setRotationPoint(3.0F, -5.0F, 9.0F);
/*  48 */     this.paddles[1] = makePaddle(false);
/*  49 */     this.paddles[1].setRotationPoint(3.0F, -5.0F, -9.0F);
/*  50 */     (this.paddles[1]).rotateAngleY = 3.1415927F;
/*  51 */     (this.paddles[0]).rotateAngleZ = 0.19634955F;
/*  52 */     (this.paddles[1]).rotateAngleZ = 0.19634955F;
/*  53 */     this.noWater = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
/*  54 */     this.noWater.addBox(-14.0F, -9.0F, -3.0F, 28, 16, 3, 0.0F);
/*  55 */     this.noWater.setRotationPoint(0.0F, -3.0F, 1.0F);
/*  56 */     this.noWater.rotateAngleX = 1.5707964F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  64 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*  65 */     EntityBoat entityboat = (EntityBoat)entityIn;
/*  66 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*     */     
/*  68 */     for (int i = 0; i < 5; i++)
/*     */     {
/*  70 */       this.boatSides[i].render(scale);
/*     */     }
/*     */     
/*  73 */     renderPaddle(entityboat, 0, scale, limbSwing);
/*  74 */     renderPaddle(entityboat, 1, scale, limbSwing);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMultipass(Entity p_187054_1_, float p_187054_2_, float p_187054_3_, float p_187054_4_, float p_187054_5_, float p_187054_6_, float scale) {
/*  79 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*  80 */     GlStateManager.colorMask(false, false, false, false);
/*  81 */     this.noWater.render(scale);
/*  82 */     GlStateManager.colorMask(true, true, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelRenderer makePaddle(boolean p_187056_1_) {
/*  87 */     ModelRenderer modelrenderer = (new ModelRenderer(this, 62, p_187056_1_ ? 0 : 20)).setTextureSize(128, 64);
/*  88 */     int i = 20;
/*  89 */     int j = 7;
/*  90 */     int k = 6;
/*  91 */     float f = -5.0F;
/*  92 */     modelrenderer.addBox(-1.0F, 0.0F, -5.0F, 2, 2, 18);
/*  93 */     modelrenderer.addBox(p_187056_1_ ? -1.001F : 0.001F, -3.0F, 8.0F, 1, 6, 7);
/*  94 */     return modelrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderPaddle(EntityBoat boat, int paddle, float scale, float limbSwing) {
/*  99 */     float f = boat.getRowingTime(paddle, limbSwing);
/* 100 */     ModelRenderer modelrenderer = this.paddles[paddle];
/* 101 */     modelrenderer.rotateAngleX = (float)MathHelper.clampedLerp(-1.0471975803375244D, -0.2617993950843811D, ((MathHelper.sin(-f) + 1.0F) / 2.0F));
/* 102 */     modelrenderer.rotateAngleY = (float)MathHelper.clampedLerp(-0.7853981633974483D, 0.7853981633974483D, ((MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F));
/*     */     
/* 104 */     if (paddle == 1)
/*     */     {
/* 106 */       modelrenderer.rotateAngleY = 3.1415927F - modelrenderer.rotateAngleY;
/*     */     }
/*     */     
/* 109 */     modelrenderer.render(scale);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */