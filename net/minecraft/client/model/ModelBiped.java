/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBiped
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer bipedHead;
/*     */   public ModelRenderer bipedHeadwear;
/*     */   public ModelRenderer bipedBody;
/*     */   public ModelRenderer bipedRightArm;
/*     */   public ModelRenderer bipedLeftArm;
/*     */   public ModelRenderer bipedRightLeg;
/*     */   public ModelRenderer bipedLeftLeg;
/*     */   public ArmPose leftArmPose;
/*     */   public ArmPose rightArmPose;
/*     */   public boolean isSneak;
/*     */   
/*     */   public ModelBiped() {
/*  35 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBiped(float modelSize) {
/*  40 */     this(modelSize, 0.0F, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
/*  45 */     this.leftArmPose = ArmPose.EMPTY;
/*  46 */     this.rightArmPose = ArmPose.EMPTY;
/*  47 */     this.textureWidth = textureWidthIn;
/*  48 */     this.textureHeight = textureHeightIn;
/*  49 */     this.bipedHead = new ModelRenderer(this, 0, 0);
/*  50 */     this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
/*  51 */     this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  52 */     this.bipedHeadwear = new ModelRenderer(this, 32, 0);
/*  53 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
/*  54 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  55 */     this.bipedBody = new ModelRenderer(this, 16, 16);
/*  56 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
/*  57 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  58 */     this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  59 */     this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  60 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  61 */     this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/*  62 */     this.bipedLeftArm.mirror = true;
/*  63 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  64 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  65 */     this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/*  66 */     this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  67 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
/*  68 */     this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/*  69 */     this.bipedLeftLeg.mirror = true;
/*  70 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  71 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  79 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*  80 */     GlStateManager.pushMatrix();
/*     */     
/*  82 */     if (this.isChild) {
/*     */       
/*  84 */       float f = 2.0F;
/*  85 */       GlStateManager.scale(0.75F, 0.75F, 0.75F);
/*  86 */       GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*  87 */       this.bipedHead.render(scale);
/*  88 */       GlStateManager.popMatrix();
/*  89 */       GlStateManager.pushMatrix();
/*  90 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  91 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  92 */       this.bipedBody.render(scale);
/*  93 */       this.bipedRightArm.render(scale);
/*  94 */       this.bipedLeftArm.render(scale);
/*  95 */       this.bipedRightLeg.render(scale);
/*  96 */       this.bipedLeftLeg.render(scale);
/*  97 */       this.bipedHeadwear.render(scale);
/*     */     }
/*     */     else {
/*     */       
/* 101 */       if (entityIn.isSneaking())
/*     */       {
/* 103 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/* 106 */       this.bipedHead.render(scale);
/* 107 */       this.bipedBody.render(scale);
/* 108 */       this.bipedRightArm.render(scale);
/* 109 */       this.bipedLeftArm.render(scale);
/* 110 */       this.bipedRightLeg.render(scale);
/* 111 */       this.bipedLeftLeg.render(scale);
/* 112 */       this.bipedHeadwear.render(scale);
/*     */     } 
/*     */     
/* 115 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 127 */     boolean flag = (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4);
/* 128 */     this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
/*     */     
/* 130 */     if (flag) {
/*     */       
/* 132 */       this.bipedHead.rotateAngleX = -0.7853982F;
/*     */     }
/*     */     else {
/*     */       
/* 136 */       this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
/*     */     } 
/*     */     
/* 139 */     this.bipedBody.rotateAngleY = 0.0F;
/* 140 */     this.bipedRightArm.rotationPointZ = 0.0F;
/* 141 */     this.bipedRightArm.rotationPointX = -5.0F;
/* 142 */     this.bipedLeftArm.rotationPointZ = 0.0F;
/* 143 */     this.bipedLeftArm.rotationPointX = 5.0F;
/* 144 */     float f = 1.0F;
/*     */     
/* 146 */     if (flag) {
/*     */       
/* 148 */       f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
/* 149 */       f /= 0.2F;
/* 150 */       f = f * f * f;
/*     */     } 
/*     */     
/* 153 */     if (f < 1.0F)
/*     */     {
/* 155 */       f = 1.0F;
/*     */     }
/*     */     
/* 158 */     this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F / f;
/* 159 */     this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
/* 160 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 161 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 162 */     this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
/* 163 */     this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount / f;
/* 164 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/* 165 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/* 166 */     this.bipedRightLeg.rotateAngleZ = 0.0F;
/* 167 */     this.bipedLeftLeg.rotateAngleZ = 0.0F;
/*     */     
/* 169 */     if (this.isRiding) {
/*     */       
/* 171 */       this.bipedRightArm.rotateAngleX += -0.62831855F;
/* 172 */       this.bipedLeftArm.rotateAngleX += -0.62831855F;
/* 173 */       this.bipedRightLeg.rotateAngleX = -1.4137167F;
/* 174 */       this.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 175 */       this.bipedRightLeg.rotateAngleZ = 0.07853982F;
/* 176 */       this.bipedLeftLeg.rotateAngleX = -1.4137167F;
/* 177 */       this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/* 178 */       this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
/*     */     } 
/*     */     
/* 181 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 182 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/*     */     
/* 184 */     switch (this.leftArmPose) {
/*     */       
/*     */       case EMPTY:
/* 187 */         this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */       
/*     */       case null:
/* 191 */         this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.9424779F;
/* 192 */         this.bipedLeftArm.rotateAngleY = 0.5235988F;
/*     */         break;
/*     */       
/*     */       case ITEM:
/* 196 */         this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F;
/* 197 */         this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */     } 
/* 200 */     switch (this.rightArmPose) {
/*     */       
/*     */       case EMPTY:
/* 203 */         this.bipedRightArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */       
/*     */       case null:
/* 207 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.9424779F;
/* 208 */         this.bipedRightArm.rotateAngleY = -0.5235988F;
/*     */         break;
/*     */       
/*     */       case ITEM:
/* 212 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F;
/* 213 */         this.bipedRightArm.rotateAngleY = 0.0F;
/*     */         break;
/*     */     } 
/* 216 */     if (this.swingProgress > 0.0F) {
/*     */       
/* 218 */       EnumHandSide enumhandside = getMainHand(entityIn);
/* 219 */       ModelRenderer modelrenderer = getArmForSide(enumhandside);
/* 220 */       float f1 = this.swingProgress;
/* 221 */       this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * 6.2831855F) * 0.2F;
/*     */       
/* 223 */       if (enumhandside == EnumHandSide.LEFT)
/*     */       {
/* 225 */         this.bipedBody.rotateAngleY *= -1.0F;
/*     */       }
/*     */       
/* 228 */       this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 229 */       this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 230 */       this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 231 */       this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 232 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 233 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 234 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 235 */       f1 = 1.0F - this.swingProgress;
/* 236 */       f1 *= f1;
/* 237 */       f1 *= f1;
/* 238 */       f1 = 1.0F - f1;
/* 239 */       float f2 = MathHelper.sin(f1 * 3.1415927F);
/* 240 */       float f3 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 241 */       modelrenderer.rotateAngleX = (float)(modelrenderer.rotateAngleX - f2 * 1.2D + f3);
/* 242 */       modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 243 */       modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
/*     */     } 
/*     */     
/* 246 */     if (this.isSneak) {
/*     */       
/* 248 */       this.bipedBody.rotateAngleX = 0.5F;
/* 249 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 250 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/* 251 */       this.bipedRightLeg.rotationPointZ = 4.0F;
/* 252 */       this.bipedLeftLeg.rotationPointZ = 4.0F;
/* 253 */       this.bipedRightLeg.rotationPointY = 9.0F;
/* 254 */       this.bipedLeftLeg.rotationPointY = 9.0F;
/* 255 */       this.bipedHead.rotationPointY = 1.0F;
/*     */     }
/*     */     else {
/*     */       
/* 259 */       this.bipedBody.rotateAngleX = 0.0F;
/* 260 */       this.bipedRightLeg.rotationPointZ = 0.1F;
/* 261 */       this.bipedLeftLeg.rotationPointZ = 0.1F;
/* 262 */       this.bipedRightLeg.rotationPointY = 12.0F;
/* 263 */       this.bipedLeftLeg.rotationPointY = 12.0F;
/* 264 */       this.bipedHead.rotationPointY = 0.0F;
/*     */     } 
/*     */     
/* 267 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 268 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 269 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 270 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     
/* 272 */     if (this.rightArmPose == ArmPose.BOW_AND_ARROW) {
/*     */       
/* 274 */       this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
/* 275 */       this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
/* 276 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 277 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/*     */     }
/* 279 */     else if (this.leftArmPose == ArmPose.BOW_AND_ARROW) {
/*     */       
/* 281 */       this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY - 0.4F;
/* 282 */       this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY;
/* 283 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 284 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/*     */     } 
/*     */     
/* 287 */     copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModelAttributes(ModelBase model) {
/* 292 */     super.setModelAttributes(model);
/*     */     
/* 294 */     if (model instanceof ModelBiped) {
/*     */       
/* 296 */       ModelBiped modelbiped = (ModelBiped)model;
/* 297 */       this.leftArmPose = modelbiped.leftArmPose;
/* 298 */       this.rightArmPose = modelbiped.rightArmPose;
/* 299 */       this.isSneak = modelbiped.isSneak;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 305 */     this.bipedHead.showModel = invisible;
/* 306 */     this.bipedHeadwear.showModel = invisible;
/* 307 */     this.bipedBody.showModel = invisible;
/* 308 */     this.bipedRightArm.showModel = invisible;
/* 309 */     this.bipedLeftArm.showModel = invisible;
/* 310 */     this.bipedRightLeg.showModel = invisible;
/* 311 */     this.bipedLeftLeg.showModel = invisible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale, EnumHandSide side) {
/* 316 */     getArmForSide(side).postRender(scale);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelRenderer getArmForSide(EnumHandSide side) {
/* 321 */     return (side == EnumHandSide.LEFT) ? this.bipedLeftArm : this.bipedRightArm;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumHandSide getMainHand(Entity entityIn) {
/* 326 */     if (entityIn instanceof EntityLivingBase) {
/*     */       
/* 328 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
/* 329 */       EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
/* 330 */       return (entitylivingbase.swingingHand == EnumHand.MAIN_HAND) ? enumhandside : enumhandside.opposite();
/*     */     } 
/*     */ 
/*     */     
/* 334 */     return EnumHandSide.RIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   public enum ArmPose
/*     */   {
/* 340 */     EMPTY,
/* 341 */     ITEM,
/* 342 */     BLOCK,
/* 343 */     BOW_AND_ARROW;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */