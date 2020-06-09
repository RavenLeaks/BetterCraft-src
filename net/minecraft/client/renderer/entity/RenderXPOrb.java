/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import optifine.Config;
/*     */ import optifine.CustomColors;
/*     */ 
/*     */ public class RenderXPOrb extends Render<EntityXPOrb> {
/*  17 */   private static final ResourceLocation EXPERIENCE_ORB_TEXTURES = new ResourceLocation("textures/entity/experience_orb.png");
/*     */ 
/*     */   
/*     */   public RenderXPOrb(RenderManager renderManagerIn) {
/*  21 */     super(renderManagerIn);
/*  22 */     this.shadowSize = 0.15F;
/*  23 */     this.shadowOpaque = 0.75F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  31 */     if (!this.renderOutlines) {
/*     */       
/*  33 */       GlStateManager.pushMatrix();
/*  34 */       GlStateManager.translate((float)x, (float)y, (float)z);
/*  35 */       bindEntityTexture(entity);
/*  36 */       RenderHelper.enableStandardItemLighting();
/*  37 */       int i = entity.getTextureByXP();
/*  38 */       float f = (i % 4 * 16 + 0) / 64.0F;
/*  39 */       float f1 = (i % 4 * 16 + 16) / 64.0F;
/*  40 */       float f2 = (i / 4 * 16 + 0) / 64.0F;
/*  41 */       float f3 = (i / 4 * 16 + 16) / 64.0F;
/*  42 */       float f4 = 1.0F;
/*  43 */       float f5 = 0.5F;
/*  44 */       float f6 = 0.25F;
/*  45 */       int j = entity.getBrightnessForRender();
/*  46 */       int k = j % 65536;
/*  47 */       int l = j / 65536;
/*  48 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k, l);
/*  49 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  50 */       float f7 = 255.0F;
/*  51 */       float f8 = (entity.xpColor + partialTicks) / 2.0F;
/*     */       
/*  53 */       if (Config.isCustomColors())
/*     */       {
/*  55 */         f8 = CustomColors.getXpOrbTimer(f8);
/*     */       }
/*     */       
/*  58 */       l = (int)((MathHelper.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
/*  59 */       int i1 = 255;
/*  60 */       int j1 = (int)((MathHelper.sin(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
/*  61 */       GlStateManager.translate(0.0F, 0.1F, 0.0F);
/*  62 */       GlStateManager.rotate(180.0F - RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  63 */       GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1 : true) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/*  64 */       float f9 = 0.3F;
/*  65 */       GlStateManager.scale(0.3F, 0.3F, 0.3F);
/*  66 */       Tessellator tessellator = Tessellator.getInstance();
/*  67 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  68 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/*  69 */       int k1 = l;
/*  70 */       int l1 = 255;
/*  71 */       int i2 = j1;
/*     */       
/*  73 */       if (Config.isCustomColors()) {
/*     */         
/*  75 */         int j2 = CustomColors.getXpOrbColor(f8);
/*     */         
/*  77 */         if (j2 >= 0) {
/*     */           
/*  79 */           k1 = j2 >> 16 & 0xFF;
/*  80 */           l1 = j2 >> 8 & 0xFF;
/*  81 */           i2 = j2 >> 0 & 0xFF;
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(f, f3).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  86 */       bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(f1, f3).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  87 */       bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(f1, f2).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  88 */       bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(f, f2).color(k1, l1, i2, 128).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  89 */       tessellator.draw();
/*  90 */       GlStateManager.disableBlend();
/*  91 */       GlStateManager.disableRescaleNormal();
/*  92 */       GlStateManager.popMatrix();
/*  93 */       super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityXPOrb entity) {
/* 102 */     return EXPERIENCE_ORB_TEXTURES;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderXPOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */