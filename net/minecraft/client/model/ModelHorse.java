/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.AbstractChestHorse;
/*     */ import net.minecraft.entity.passive.AbstractHorse;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelHorse
/*     */   extends ModelBase
/*     */ {
/*     */   private final ModelRenderer head;
/*     */   private final ModelRenderer upperMouth;
/*     */   private final ModelRenderer lowerMouth;
/*     */   private final ModelRenderer horseLeftEar;
/*     */   private final ModelRenderer horseRightEar;
/*     */   private final ModelRenderer muleLeftEar;
/*     */   private final ModelRenderer muleRightEar;
/*     */   private final ModelRenderer neck;
/*     */   private final ModelRenderer horseFaceRopes;
/*     */   private final ModelRenderer mane;
/*     */   private final ModelRenderer body;
/*     */   private final ModelRenderer tailBase;
/*     */   private final ModelRenderer tailMiddle;
/*     */   private final ModelRenderer tailTip;
/*     */   private final ModelRenderer backLeftLeg;
/*     */   private final ModelRenderer backLeftShin;
/*     */   private final ModelRenderer backLeftHoof;
/*     */   private final ModelRenderer backRightLeg;
/*     */   private final ModelRenderer backRightShin;
/*     */   private final ModelRenderer backRightHoof;
/*     */   private final ModelRenderer frontLeftLeg;
/*     */   private final ModelRenderer frontLeftShin;
/*     */   private final ModelRenderer frontLeftHoof;
/*     */   private final ModelRenderer frontRightLeg;
/*     */   private final ModelRenderer frontRightShin;
/*     */   private final ModelRenderer frontRightHoof;
/*     */   private final ModelRenderer muleLeftChest;
/*     */   private final ModelRenderer muleRightChest;
/*     */   private final ModelRenderer horseSaddleBottom;
/*     */   private final ModelRenderer horseSaddleFront;
/*     */   private final ModelRenderer horseSaddleBack;
/*     */   private final ModelRenderer horseLeftSaddleRope;
/*     */   private final ModelRenderer horseLeftSaddleMetal;
/*     */   private final ModelRenderer horseRightSaddleRope;
/*     */   private final ModelRenderer horseRightSaddleMetal;
/*     */   private final ModelRenderer horseLeftFaceMetal;
/*     */   private final ModelRenderer horseRightFaceMetal;
/*     */   private final ModelRenderer horseLeftRein;
/*     */   private final ModelRenderer horseRightRein;
/*     */   
/*     */   public ModelHorse() {
/*  68 */     this.textureWidth = 128;
/*  69 */     this.textureHeight = 128;
/*  70 */     this.body = new ModelRenderer(this, 0, 34);
/*  71 */     this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
/*  72 */     this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
/*  73 */     this.tailBase = new ModelRenderer(this, 44, 0);
/*  74 */     this.tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
/*  75 */     this.tailBase.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  76 */     this.tailBase.rotateAngleX = -1.134464F;
/*  77 */     this.tailMiddle = new ModelRenderer(this, 38, 7);
/*  78 */     this.tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
/*  79 */     this.tailMiddle.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  80 */     this.tailMiddle.rotateAngleX = -1.134464F;
/*  81 */     this.tailTip = new ModelRenderer(this, 24, 3);
/*  82 */     this.tailTip.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
/*  83 */     this.tailTip.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  84 */     this.tailTip.rotateAngleX = -1.3962634F;
/*  85 */     this.backLeftLeg = new ModelRenderer(this, 78, 29);
/*  86 */     this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
/*  87 */     this.backLeftLeg.setRotationPoint(4.0F, 9.0F, 11.0F);
/*  88 */     this.backLeftShin = new ModelRenderer(this, 78, 43);
/*  89 */     this.backLeftShin.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
/*  90 */     this.backLeftShin.setRotationPoint(4.0F, 16.0F, 11.0F);
/*  91 */     this.backLeftHoof = new ModelRenderer(this, 78, 51);
/*  92 */     this.backLeftHoof.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
/*  93 */     this.backLeftHoof.setRotationPoint(4.0F, 16.0F, 11.0F);
/*  94 */     this.backRightLeg = new ModelRenderer(this, 96, 29);
/*  95 */     this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
/*  96 */     this.backRightLeg.setRotationPoint(-4.0F, 9.0F, 11.0F);
/*  97 */     this.backRightShin = new ModelRenderer(this, 96, 43);
/*  98 */     this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
/*  99 */     this.backRightShin.setRotationPoint(-4.0F, 16.0F, 11.0F);
/* 100 */     this.backRightHoof = new ModelRenderer(this, 96, 51);
/* 101 */     this.backRightHoof.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
/* 102 */     this.backRightHoof.setRotationPoint(-4.0F, 16.0F, 11.0F);
/* 103 */     this.frontLeftLeg = new ModelRenderer(this, 44, 29);
/* 104 */     this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
/* 105 */     this.frontLeftLeg.setRotationPoint(4.0F, 9.0F, -8.0F);
/* 106 */     this.frontLeftShin = new ModelRenderer(this, 44, 41);
/* 107 */     this.frontLeftShin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
/* 108 */     this.frontLeftShin.setRotationPoint(4.0F, 16.0F, -8.0F);
/* 109 */     this.frontLeftHoof = new ModelRenderer(this, 44, 51);
/* 110 */     this.frontLeftHoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
/* 111 */     this.frontLeftHoof.setRotationPoint(4.0F, 16.0F, -8.0F);
/* 112 */     this.frontRightLeg = new ModelRenderer(this, 60, 29);
/* 113 */     this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
/* 114 */     this.frontRightLeg.setRotationPoint(-4.0F, 9.0F, -8.0F);
/* 115 */     this.frontRightShin = new ModelRenderer(this, 60, 41);
/* 116 */     this.frontRightShin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
/* 117 */     this.frontRightShin.setRotationPoint(-4.0F, 16.0F, -8.0F);
/* 118 */     this.frontRightHoof = new ModelRenderer(this, 60, 51);
/* 119 */     this.frontRightHoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
/* 120 */     this.frontRightHoof.setRotationPoint(-4.0F, 16.0F, -8.0F);
/* 121 */     this.head = new ModelRenderer(this, 0, 0);
/* 122 */     this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
/* 123 */     this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 124 */     this.head.rotateAngleX = 0.5235988F;
/* 125 */     this.upperMouth = new ModelRenderer(this, 24, 18);
/* 126 */     this.upperMouth.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
/* 127 */     this.upperMouth.setRotationPoint(0.0F, 3.95F, -10.0F);
/* 128 */     this.upperMouth.rotateAngleX = 0.5235988F;
/* 129 */     this.lowerMouth = new ModelRenderer(this, 24, 27);
/* 130 */     this.lowerMouth.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5);
/* 131 */     this.lowerMouth.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 132 */     this.lowerMouth.rotateAngleX = 0.5235988F;
/* 133 */     this.head.addChild(this.upperMouth);
/* 134 */     this.head.addChild(this.lowerMouth);
/* 135 */     this.horseLeftEar = new ModelRenderer(this, 0, 0);
/* 136 */     this.horseLeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1);
/* 137 */     this.horseLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 138 */     this.horseLeftEar.rotateAngleX = 0.5235988F;
/* 139 */     this.horseRightEar = new ModelRenderer(this, 0, 0);
/* 140 */     this.horseRightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1);
/* 141 */     this.horseRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 142 */     this.horseRightEar.rotateAngleX = 0.5235988F;
/* 143 */     this.muleLeftEar = new ModelRenderer(this, 0, 12);
/* 144 */     this.muleLeftEar.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1);
/* 145 */     this.muleLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 146 */     this.muleLeftEar.rotateAngleX = 0.5235988F;
/* 147 */     this.muleLeftEar.rotateAngleZ = 0.2617994F;
/* 148 */     this.muleRightEar = new ModelRenderer(this, 0, 12);
/* 149 */     this.muleRightEar.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1);
/* 150 */     this.muleRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 151 */     this.muleRightEar.rotateAngleX = 0.5235988F;
/* 152 */     this.muleRightEar.rotateAngleZ = -0.2617994F;
/* 153 */     this.neck = new ModelRenderer(this, 0, 12);
/* 154 */     this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
/* 155 */     this.neck.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 156 */     this.neck.rotateAngleX = 0.5235988F;
/* 157 */     this.muleLeftChest = new ModelRenderer(this, 0, 34);
/* 158 */     this.muleLeftChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
/* 159 */     this.muleLeftChest.setRotationPoint(-7.5F, 3.0F, 10.0F);
/* 160 */     this.muleLeftChest.rotateAngleY = 1.5707964F;
/* 161 */     this.muleRightChest = new ModelRenderer(this, 0, 47);
/* 162 */     this.muleRightChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
/* 163 */     this.muleRightChest.setRotationPoint(4.5F, 3.0F, 10.0F);
/* 164 */     this.muleRightChest.rotateAngleY = 1.5707964F;
/* 165 */     this.horseSaddleBottom = new ModelRenderer(this, 80, 0);
/* 166 */     this.horseSaddleBottom.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8);
/* 167 */     this.horseSaddleBottom.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 168 */     this.horseSaddleFront = new ModelRenderer(this, 106, 9);
/* 169 */     this.horseSaddleFront.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2);
/* 170 */     this.horseSaddleFront.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 171 */     this.horseSaddleBack = new ModelRenderer(this, 80, 9);
/* 172 */     this.horseSaddleBack.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2);
/* 173 */     this.horseSaddleBack.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 174 */     this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0);
/* 175 */     this.horseLeftSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
/* 176 */     this.horseLeftSaddleMetal.setRotationPoint(5.0F, 3.0F, 2.0F);
/* 177 */     this.horseLeftSaddleRope = new ModelRenderer(this, 70, 0);
/* 178 */     this.horseLeftSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
/* 179 */     this.horseLeftSaddleRope.setRotationPoint(5.0F, 3.0F, 2.0F);
/* 180 */     this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4);
/* 181 */     this.horseRightSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
/* 182 */     this.horseRightSaddleMetal.setRotationPoint(-5.0F, 3.0F, 2.0F);
/* 183 */     this.horseRightSaddleRope = new ModelRenderer(this, 80, 0);
/* 184 */     this.horseRightSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
/* 185 */     this.horseRightSaddleRope.setRotationPoint(-5.0F, 3.0F, 2.0F);
/* 186 */     this.horseLeftFaceMetal = new ModelRenderer(this, 74, 13);
/* 187 */     this.horseLeftFaceMetal.addBox(1.5F, -8.0F, -4.0F, 1, 2, 2);
/* 188 */     this.horseLeftFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 189 */     this.horseLeftFaceMetal.rotateAngleX = 0.5235988F;
/* 190 */     this.horseRightFaceMetal = new ModelRenderer(this, 74, 13);
/* 191 */     this.horseRightFaceMetal.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2);
/* 192 */     this.horseRightFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 193 */     this.horseRightFaceMetal.rotateAngleX = 0.5235988F;
/* 194 */     this.horseLeftRein = new ModelRenderer(this, 44, 10);
/* 195 */     this.horseLeftRein.addBox(2.6F, -6.0F, -6.0F, 0, 3, 16);
/* 196 */     this.horseLeftRein.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 197 */     this.horseRightRein = new ModelRenderer(this, 44, 5);
/* 198 */     this.horseRightRein.addBox(-2.6F, -6.0F, -6.0F, 0, 3, 16);
/* 199 */     this.horseRightRein.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 200 */     this.mane = new ModelRenderer(this, 58, 0);
/* 201 */     this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4);
/* 202 */     this.mane.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 203 */     this.mane.rotateAngleX = 0.5235988F;
/* 204 */     this.horseFaceRopes = new ModelRenderer(this, 80, 12);
/* 205 */     this.horseFaceRopes.addBox(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
/* 206 */     this.horseFaceRopes.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 207 */     this.horseFaceRopes.rotateAngleX = 0.5235988F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 215 */     AbstractHorse abstracthorse = (AbstractHorse)entityIn;
/* 216 */     float f = abstracthorse.getGrassEatingAmount(0.0F);
/* 217 */     boolean flag = abstracthorse.isChild();
/* 218 */     boolean flag1 = (!flag && abstracthorse.isHorseSaddled());
/* 219 */     boolean flag2 = abstracthorse instanceof AbstractChestHorse;
/* 220 */     boolean flag3 = (!flag && flag2 && ((AbstractChestHorse)abstracthorse).func_190695_dh());
/* 221 */     float f1 = abstracthorse.getHorseSize();
/* 222 */     boolean flag4 = abstracthorse.isBeingRidden();
/*     */     
/* 224 */     if (flag1) {
/*     */       
/* 226 */       this.horseFaceRopes.render(scale);
/* 227 */       this.horseSaddleBottom.render(scale);
/* 228 */       this.horseSaddleFront.render(scale);
/* 229 */       this.horseSaddleBack.render(scale);
/* 230 */       this.horseLeftSaddleRope.render(scale);
/* 231 */       this.horseLeftSaddleMetal.render(scale);
/* 232 */       this.horseRightSaddleRope.render(scale);
/* 233 */       this.horseRightSaddleMetal.render(scale);
/* 234 */       this.horseLeftFaceMetal.render(scale);
/* 235 */       this.horseRightFaceMetal.render(scale);
/*     */       
/* 237 */       if (flag4) {
/*     */         
/* 239 */         this.horseLeftRein.render(scale);
/* 240 */         this.horseRightRein.render(scale);
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     if (flag) {
/*     */       
/* 246 */       GlStateManager.pushMatrix();
/* 247 */       GlStateManager.scale(f1, 0.5F + f1 * 0.5F, f1);
/* 248 */       GlStateManager.translate(0.0F, 0.95F * (1.0F - f1), 0.0F);
/*     */     } 
/*     */     
/* 251 */     this.backLeftLeg.render(scale);
/* 252 */     this.backLeftShin.render(scale);
/* 253 */     this.backLeftHoof.render(scale);
/* 254 */     this.backRightLeg.render(scale);
/* 255 */     this.backRightShin.render(scale);
/* 256 */     this.backRightHoof.render(scale);
/* 257 */     this.frontLeftLeg.render(scale);
/* 258 */     this.frontLeftShin.render(scale);
/* 259 */     this.frontLeftHoof.render(scale);
/* 260 */     this.frontRightLeg.render(scale);
/* 261 */     this.frontRightShin.render(scale);
/* 262 */     this.frontRightHoof.render(scale);
/*     */     
/* 264 */     if (flag) {
/*     */       
/* 266 */       GlStateManager.popMatrix();
/* 267 */       GlStateManager.pushMatrix();
/* 268 */       GlStateManager.scale(f1, f1, f1);
/* 269 */       GlStateManager.translate(0.0F, 1.35F * (1.0F - f1), 0.0F);
/*     */     } 
/*     */     
/* 272 */     this.body.render(scale);
/* 273 */     this.tailBase.render(scale);
/* 274 */     this.tailMiddle.render(scale);
/* 275 */     this.tailTip.render(scale);
/* 276 */     this.neck.render(scale);
/* 277 */     this.mane.render(scale);
/*     */     
/* 279 */     if (flag) {
/*     */       
/* 281 */       GlStateManager.popMatrix();
/* 282 */       GlStateManager.pushMatrix();
/* 283 */       float f2 = 0.5F + f1 * f1 * 0.5F;
/* 284 */       GlStateManager.scale(f2, f2, f2);
/*     */       
/* 286 */       if (f <= 0.0F) {
/*     */         
/* 288 */         GlStateManager.translate(0.0F, 1.35F * (1.0F - f1), 0.0F);
/*     */       }
/*     */       else {
/*     */         
/* 292 */         GlStateManager.translate(0.0F, 0.9F * (1.0F - f1) * f + 1.35F * (1.0F - f1) * (1.0F - f), 0.15F * (1.0F - f1) * f);
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (flag2) {
/*     */       
/* 298 */       this.muleLeftEar.render(scale);
/* 299 */       this.muleRightEar.render(scale);
/*     */     }
/*     */     else {
/*     */       
/* 303 */       this.horseLeftEar.render(scale);
/* 304 */       this.horseRightEar.render(scale);
/*     */     } 
/*     */     
/* 307 */     this.head.render(scale);
/*     */     
/* 309 */     if (flag)
/*     */     {
/* 311 */       GlStateManager.popMatrix();
/*     */     }
/*     */     
/* 314 */     if (flag3) {
/*     */       
/* 316 */       this.muleLeftChest.render(scale);
/* 317 */       this.muleRightChest.render(scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float updateHorseRotation(float p_110683_1_, float p_110683_2_, float p_110683_3_) {
/*     */     float f;
/* 328 */     for (f = p_110683_2_ - p_110683_1_; f < -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     while (f >= 180.0F)
/*     */     {
/* 335 */       f -= 360.0F;
/*     */     }
/*     */     
/* 338 */     return p_110683_1_ + p_110683_3_ * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 347 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/* 348 */     float f = updateHorseRotation(entitylivingbaseIn.prevRenderYawOffset, entitylivingbaseIn.renderYawOffset, partialTickTime);
/* 349 */     float f1 = updateHorseRotation(entitylivingbaseIn.prevRotationYawHead, entitylivingbaseIn.rotationYawHead, partialTickTime);
/* 350 */     float f2 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTickTime;
/* 351 */     float f3 = f1 - f;
/* 352 */     float f4 = f2 * 0.017453292F;
/*     */     
/* 354 */     if (f3 > 20.0F)
/*     */     {
/* 356 */       f3 = 20.0F;
/*     */     }
/*     */     
/* 359 */     if (f3 < -20.0F)
/*     */     {
/* 361 */       f3 = -20.0F;
/*     */     }
/*     */     
/* 364 */     if (p_78086_3_ > 0.2F)
/*     */     {
/* 366 */       f4 += MathHelper.cos(p_78086_2_ * 0.4F) * 0.15F * p_78086_3_;
/*     */     }
/*     */     
/* 369 */     AbstractHorse abstracthorse = (AbstractHorse)entitylivingbaseIn;
/* 370 */     float f5 = abstracthorse.getGrassEatingAmount(partialTickTime);
/* 371 */     float f6 = abstracthorse.getRearingAmount(partialTickTime);
/* 372 */     float f7 = 1.0F - f6;
/* 373 */     float f8 = abstracthorse.getMouthOpennessAngle(partialTickTime);
/* 374 */     boolean flag = (abstracthorse.tailCounter != 0);
/* 375 */     boolean flag1 = abstracthorse.isHorseSaddled();
/* 376 */     boolean flag2 = abstracthorse.isBeingRidden();
/* 377 */     float f9 = entitylivingbaseIn.ticksExisted + partialTickTime;
/* 378 */     float f10 = MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F);
/* 379 */     float f11 = f10 * 0.8F * p_78086_3_;
/* 380 */     this.head.rotationPointY = 4.0F;
/* 381 */     this.head.rotationPointZ = -10.0F;
/* 382 */     this.tailBase.rotationPointY = 3.0F;
/* 383 */     this.tailMiddle.rotationPointZ = 14.0F;
/* 384 */     this.muleRightChest.rotationPointY = 3.0F;
/* 385 */     this.muleRightChest.rotationPointZ = 10.0F;
/* 386 */     this.body.rotateAngleX = 0.0F;
/* 387 */     this.head.rotateAngleX = 0.5235988F + f4;
/* 388 */     this.head.rotateAngleY = f3 * 0.017453292F;
/* 389 */     this.head.rotateAngleX = f6 * (0.2617994F + f4) + f5 * 2.1816616F + (1.0F - Math.max(f6, f5)) * this.head.rotateAngleX;
/* 390 */     this.head.rotateAngleY = f6 * f3 * 0.017453292F + (1.0F - Math.max(f6, f5)) * this.head.rotateAngleY;
/* 391 */     this.head.rotationPointY = f6 * -6.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.head.rotationPointY;
/* 392 */     this.head.rotationPointZ = f6 * -1.0F + f5 * -10.0F + (1.0F - Math.max(f6, f5)) * this.head.rotationPointZ;
/* 393 */     this.tailBase.rotationPointY = f6 * 9.0F + f7 * this.tailBase.rotationPointY;
/* 394 */     this.tailMiddle.rotationPointZ = f6 * 18.0F + f7 * this.tailMiddle.rotationPointZ;
/* 395 */     this.muleRightChest.rotationPointY = f6 * 5.5F + f7 * this.muleRightChest.rotationPointY;
/* 396 */     this.muleRightChest.rotationPointZ = f6 * 15.0F + f7 * this.muleRightChest.rotationPointZ;
/* 397 */     this.body.rotateAngleX = f6 * -0.7853982F + f7 * this.body.rotateAngleX;
/* 398 */     this.horseLeftEar.rotationPointY = this.head.rotationPointY;
/* 399 */     this.horseRightEar.rotationPointY = this.head.rotationPointY;
/* 400 */     this.muleLeftEar.rotationPointY = this.head.rotationPointY;
/* 401 */     this.muleRightEar.rotationPointY = this.head.rotationPointY;
/* 402 */     this.neck.rotationPointY = this.head.rotationPointY;
/* 403 */     this.upperMouth.rotationPointY = 0.02F;
/* 404 */     this.lowerMouth.rotationPointY = 0.0F;
/* 405 */     this.mane.rotationPointY = this.head.rotationPointY;
/* 406 */     this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
/* 407 */     this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
/* 408 */     this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
/* 409 */     this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
/* 410 */     this.neck.rotationPointZ = this.head.rotationPointZ;
/* 411 */     this.upperMouth.rotationPointZ = 0.02F - f8;
/* 412 */     this.lowerMouth.rotationPointZ = f8;
/* 413 */     this.mane.rotationPointZ = this.head.rotationPointZ;
/* 414 */     this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
/* 415 */     this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
/* 416 */     this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
/* 417 */     this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
/* 418 */     this.neck.rotateAngleX = this.head.rotateAngleX;
/* 419 */     this.upperMouth.rotateAngleX = -0.09424778F * f8;
/* 420 */     this.lowerMouth.rotateAngleX = 0.15707964F * f8;
/* 421 */     this.mane.rotateAngleX = this.head.rotateAngleX;
/* 422 */     this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
/* 423 */     this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
/* 424 */     this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
/* 425 */     this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
/* 426 */     this.neck.rotateAngleY = this.head.rotateAngleY;
/* 427 */     this.upperMouth.rotateAngleY = 0.0F;
/* 428 */     this.lowerMouth.rotateAngleY = 0.0F;
/* 429 */     this.mane.rotateAngleY = this.head.rotateAngleY;
/* 430 */     this.muleLeftChest.rotateAngleX = f11 / 5.0F;
/* 431 */     this.muleRightChest.rotateAngleX = -f11 / 5.0F;
/* 432 */     float f12 = 0.2617994F * f6;
/* 433 */     float f13 = MathHelper.cos(f9 * 0.6F + 3.1415927F);
/* 434 */     this.frontLeftLeg.rotationPointY = -2.0F * f6 + 9.0F * f7;
/* 435 */     this.frontLeftLeg.rotationPointZ = -2.0F * f6 + -8.0F * f7;
/* 436 */     this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
/* 437 */     this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
/* 438 */     this.backLeftLeg.rotationPointY += MathHelper.sin(1.5707964F + f12 + f7 * -f10 * 0.5F * p_78086_3_) * 7.0F;
/* 439 */     this.backLeftLeg.rotationPointZ += MathHelper.cos(-1.5707964F + f12 + f7 * -f10 * 0.5F * p_78086_3_) * 7.0F;
/* 440 */     this.backRightLeg.rotationPointY += MathHelper.sin(1.5707964F + f12 + f7 * f10 * 0.5F * p_78086_3_) * 7.0F;
/* 441 */     this.backRightLeg.rotationPointZ += MathHelper.cos(-1.5707964F + f12 + f7 * f10 * 0.5F * p_78086_3_) * 7.0F;
/* 442 */     float f14 = (-1.0471976F + f13) * f6 + f11 * f7;
/* 443 */     float f15 = (-1.0471976F - f13) * f6 + -f11 * f7;
/* 444 */     this.frontLeftLeg.rotationPointY += MathHelper.sin(1.5707964F + f14) * 7.0F;
/* 445 */     this.frontLeftLeg.rotationPointZ += MathHelper.cos(-1.5707964F + f14) * 7.0F;
/* 446 */     this.frontRightLeg.rotationPointY += MathHelper.sin(1.5707964F + f15) * 7.0F;
/* 447 */     this.frontRightLeg.rotationPointZ += MathHelper.cos(-1.5707964F + f15) * 7.0F;
/* 448 */     this.backLeftLeg.rotateAngleX = f12 + -f10 * 0.5F * p_78086_3_ * f7;
/* 449 */     this.backLeftShin.rotateAngleX = -0.08726646F * f6 + (-f10 * 0.5F * p_78086_3_ - Math.max(0.0F, f10 * 0.5F * p_78086_3_)) * f7;
/* 450 */     this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
/* 451 */     this.backRightLeg.rotateAngleX = f12 + f10 * 0.5F * p_78086_3_ * f7;
/* 452 */     this.backRightShin.rotateAngleX = -0.08726646F * f6 + (f10 * 0.5F * p_78086_3_ - Math.max(0.0F, -f10 * 0.5F * p_78086_3_)) * f7;
/* 453 */     this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
/* 454 */     this.frontLeftLeg.rotateAngleX = f14;
/* 455 */     this.frontLeftShin.rotateAngleX = (this.frontLeftLeg.rotateAngleX + 3.1415927F * Math.max(0.0F, 0.2F + f13 * 0.2F)) * f6 + (f11 + Math.max(0.0F, f10 * 0.5F * p_78086_3_)) * f7;
/* 456 */     this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
/* 457 */     this.frontRightLeg.rotateAngleX = f15;
/* 458 */     this.frontRightShin.rotateAngleX = (this.frontRightLeg.rotateAngleX + 3.1415927F * Math.max(0.0F, 0.2F - f13 * 0.2F)) * f6 + (-f11 + Math.max(0.0F, -f10 * 0.5F * p_78086_3_)) * f7;
/* 459 */     this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
/* 460 */     this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
/* 461 */     this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
/* 462 */     this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
/* 463 */     this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
/* 464 */     this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
/* 465 */     this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
/* 466 */     this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
/* 467 */     this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
/*     */     
/* 469 */     if (flag1) {
/*     */       
/* 471 */       this.horseSaddleBottom.rotationPointY = f6 * 0.5F + f7 * 2.0F;
/* 472 */       this.horseSaddleBottom.rotationPointZ = f6 * 11.0F + f7 * 2.0F;
/* 473 */       this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 474 */       this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 475 */       this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 476 */       this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 477 */       this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 478 */       this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 479 */       this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
/* 480 */       this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 481 */       this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 482 */       this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 483 */       this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 484 */       this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 485 */       this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 486 */       this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
/* 487 */       this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
/* 488 */       this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
/* 489 */       this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
/* 490 */       this.horseLeftRein.rotationPointY = this.head.rotationPointY;
/* 491 */       this.horseRightRein.rotationPointY = this.head.rotationPointY;
/* 492 */       this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
/* 493 */       this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
/* 494 */       this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
/* 495 */       this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
/* 496 */       this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
/* 497 */       this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
/* 498 */       this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
/* 499 */       this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
/* 500 */       this.horseLeftRein.rotateAngleX = f4;
/* 501 */       this.horseRightRein.rotateAngleX = f4;
/* 502 */       this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
/* 503 */       this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
/* 504 */       this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
/* 505 */       this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
/* 506 */       this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
/* 507 */       this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
/* 508 */       this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
/* 509 */       this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
/*     */       
/* 511 */       if (flag2) {
/*     */         
/* 513 */         this.horseLeftSaddleRope.rotateAngleX = -1.0471976F;
/* 514 */         this.horseLeftSaddleMetal.rotateAngleX = -1.0471976F;
/* 515 */         this.horseRightSaddleRope.rotateAngleX = -1.0471976F;
/* 516 */         this.horseRightSaddleMetal.rotateAngleX = -1.0471976F;
/* 517 */         this.horseLeftSaddleRope.rotateAngleZ = 0.0F;
/* 518 */         this.horseLeftSaddleMetal.rotateAngleZ = 0.0F;
/* 519 */         this.horseRightSaddleRope.rotateAngleZ = 0.0F;
/* 520 */         this.horseRightSaddleMetal.rotateAngleZ = 0.0F;
/*     */       }
/*     */       else {
/*     */         
/* 524 */         this.horseLeftSaddleRope.rotateAngleX = f11 / 3.0F;
/* 525 */         this.horseLeftSaddleMetal.rotateAngleX = f11 / 3.0F;
/* 526 */         this.horseRightSaddleRope.rotateAngleX = f11 / 3.0F;
/* 527 */         this.horseRightSaddleMetal.rotateAngleX = f11 / 3.0F;
/* 528 */         this.horseLeftSaddleRope.rotateAngleZ = f11 / 5.0F;
/* 529 */         this.horseLeftSaddleMetal.rotateAngleZ = f11 / 5.0F;
/* 530 */         this.horseRightSaddleRope.rotateAngleZ = -f11 / 5.0F;
/* 531 */         this.horseRightSaddleMetal.rotateAngleZ = -f11 / 5.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 535 */     f12 = -1.3089969F + p_78086_3_ * 1.5F;
/*     */     
/* 537 */     if (f12 > 0.0F)
/*     */     {
/* 539 */       f12 = 0.0F;
/*     */     }
/*     */     
/* 542 */     if (flag) {
/*     */       
/* 544 */       this.tailBase.rotateAngleY = MathHelper.cos(f9 * 0.7F);
/* 545 */       f12 = 0.0F;
/*     */     }
/*     */     else {
/*     */       
/* 549 */       this.tailBase.rotateAngleY = 0.0F;
/*     */     } 
/*     */     
/* 552 */     this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
/* 553 */     this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
/* 554 */     this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
/* 555 */     this.tailTip.rotationPointY = this.tailBase.rotationPointY;
/* 556 */     this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
/* 557 */     this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
/* 558 */     this.tailBase.rotateAngleX = f12;
/* 559 */     this.tailMiddle.rotateAngleX = f12;
/* 560 */     this.tailTip.rotateAngleX = -0.2617994F + f12;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */