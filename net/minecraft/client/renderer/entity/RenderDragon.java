/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class RenderDragon extends RenderLiving<EntityDragon> {
/*  17 */   public static final ResourceLocation ENDERCRYSTAL_BEAM_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
/*  18 */   private static final ResourceLocation DRAGON_EXPLODING_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
/*  19 */   private static final ResourceLocation DRAGON_TEXTURES = new ResourceLocation("textures/entity/enderdragon/dragon.png");
/*     */ 
/*     */   
/*     */   public RenderDragon(RenderManager renderManagerIn) {
/*  23 */     super(renderManagerIn, (ModelBase)new ModelDragon(0.0F), 0.5F);
/*  24 */     addLayer(new LayerEnderDragonEyes(this));
/*  25 */     addLayer(new LayerEnderDragonDeath());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(EntityDragon entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
/*  30 */     float f = (float)entityLiving.getMovementOffsets(7, partialTicks)[0];
/*  31 */     float f1 = (float)(entityLiving.getMovementOffsets(5, partialTicks)[1] - entityLiving.getMovementOffsets(10, partialTicks)[1]);
/*  32 */     GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
/*  33 */     GlStateManager.rotate(f1 * 10.0F, 1.0F, 0.0F, 0.0F);
/*  34 */     GlStateManager.translate(0.0F, 0.0F, 1.0F);
/*     */     
/*  36 */     if (entityLiving.deathTime > 0) {
/*     */       
/*  38 */       float f2 = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/*  39 */       f2 = MathHelper.sqrt(f2);
/*     */       
/*  41 */       if (f2 > 1.0F)
/*     */       {
/*  43 */         f2 = 1.0F;
/*     */       }
/*     */       
/*  46 */       GlStateManager.rotate(f2 * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderModel(EntityDragon entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
/*  55 */     if (entitylivingbaseIn.deathTicks > 0) {
/*     */       
/*  57 */       float f = entitylivingbaseIn.deathTicks / 200.0F;
/*  58 */       GlStateManager.depthFunc(515);
/*  59 */       GlStateManager.enableAlpha();
/*  60 */       GlStateManager.alphaFunc(516, f);
/*  61 */       bindTexture(DRAGON_EXPLODING_TEXTURES);
/*  62 */       this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/*  63 */       GlStateManager.alphaFunc(516, 0.1F);
/*  64 */       GlStateManager.depthFunc(514);
/*     */     } 
/*     */     
/*  67 */     bindEntityTexture(entitylivingbaseIn);
/*  68 */     this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/*     */     
/*  70 */     if (entitylivingbaseIn.hurtTime > 0) {
/*     */       
/*  72 */       GlStateManager.depthFunc(514);
/*  73 */       GlStateManager.disableTexture2D();
/*  74 */       GlStateManager.enableBlend();
/*  75 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*  76 */       GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
/*  77 */       this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/*  78 */       GlStateManager.enableTexture2D();
/*  79 */       GlStateManager.disableBlend();
/*  80 */       GlStateManager.depthFunc(515);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityDragon entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  89 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     
/*  91 */     if (entity.healingEnderCrystal != null) {
/*     */       
/*  93 */       bindTexture(ENDERCRYSTAL_BEAM_TEXTURES);
/*  94 */       float f = MathHelper.sin((entity.healingEnderCrystal.ticksExisted + partialTicks) * 0.2F) / 2.0F + 0.5F;
/*  95 */       f = (f * f + f) * 0.2F;
/*  96 */       renderCrystalBeams(x, y, z, partialTicks, entity.posX + (entity.prevPosX - entity.posX) * (1.0F - partialTicks), entity.posY + (entity.prevPosY - entity.posY) * (1.0F - partialTicks), entity.posZ + (entity.prevPosZ - entity.posZ) * (1.0F - partialTicks), entity.ticksExisted, entity.healingEnderCrystal.posX, f + entity.healingEnderCrystal.posY, entity.healingEnderCrystal.posZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderCrystalBeams(double p_188325_0_, double p_188325_2_, double p_188325_4_, float p_188325_6_, double p_188325_7_, double p_188325_9_, double p_188325_11_, int p_188325_13_, double p_188325_14_, double p_188325_16_, double p_188325_18_) {
/* 102 */     float f = (float)(p_188325_14_ - p_188325_7_);
/* 103 */     float f1 = (float)(p_188325_16_ - 1.0D - p_188325_9_);
/* 104 */     float f2 = (float)(p_188325_18_ - p_188325_11_);
/* 105 */     float f3 = MathHelper.sqrt(f * f + f2 * f2);
/* 106 */     float f4 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
/* 107 */     GlStateManager.pushMatrix();
/* 108 */     GlStateManager.translate((float)p_188325_0_, (float)p_188325_2_ + 2.0F, (float)p_188325_4_);
/* 109 */     GlStateManager.rotate((float)-Math.atan2(f2, f) * 57.295776F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 110 */     GlStateManager.rotate((float)-Math.atan2(f3, f1) * 57.295776F - 90.0F, 1.0F, 0.0F, 0.0F);
/* 111 */     Tessellator tessellator = Tessellator.getInstance();
/* 112 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 113 */     RenderHelper.disableStandardItemLighting();
/* 114 */     GlStateManager.disableCull();
/* 115 */     GlStateManager.shadeModel(7425);
/* 116 */     float f5 = 0.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
/* 117 */     float f6 = MathHelper.sqrt(f * f + f1 * f1 + f2 * f2) / 32.0F - (p_188325_13_ + p_188325_6_) * 0.01F;
/* 118 */     bufferbuilder.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 119 */     int i = 8;
/*     */     
/* 121 */     for (int j = 0; j <= 8; j++) {
/*     */       
/* 123 */       float f7 = MathHelper.sin((j % 8) * 6.2831855F / 8.0F) * 0.75F;
/* 124 */       float f8 = MathHelper.cos((j % 8) * 6.2831855F / 8.0F) * 0.75F;
/* 125 */       float f9 = (j % 8) / 8.0F;
/* 126 */       bufferbuilder.pos((f7 * 0.2F), (f8 * 0.2F), 0.0D).tex(f9, f5).color(0, 0, 0, 255).endVertex();
/* 127 */       bufferbuilder.pos(f7, f8, f4).tex(f9, f6).color(255, 255, 255, 255).endVertex();
/*     */     } 
/*     */     
/* 130 */     tessellator.draw();
/* 131 */     GlStateManager.enableCull();
/* 132 */     GlStateManager.shadeModel(7424);
/* 133 */     RenderHelper.enableStandardItemLighting();
/* 134 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityDragon entity) {
/* 142 */     return DRAGON_TEXTURES;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */