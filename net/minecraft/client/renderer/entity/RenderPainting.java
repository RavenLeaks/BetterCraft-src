/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class RenderPainting extends Render<EntityPainting> {
/*  16 */   private static final ResourceLocation KRISTOFFER_PAINTING_TEXTURE = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");
/*     */ 
/*     */   
/*     */   public RenderPainting(RenderManager renderManagerIn) {
/*  20 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityPainting entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  28 */     GlStateManager.pushMatrix();
/*  29 */     GlStateManager.translate(x, y, z);
/*  30 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  31 */     GlStateManager.enableRescaleNormal();
/*  32 */     bindEntityTexture(entity);
/*  33 */     EntityPainting.EnumArt entitypainting$enumart = entity.art;
/*  34 */     float f = 0.0625F;
/*  35 */     GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
/*     */     
/*  37 */     if (this.renderOutlines) {
/*     */       
/*  39 */       GlStateManager.enableColorMaterial();
/*  40 */       GlStateManager.enableOutlineMode(getTeamColor(entity));
/*     */     } 
/*     */     
/*  43 */     renderPainting(entity, entitypainting$enumart.sizeX, entitypainting$enumart.sizeY, entitypainting$enumart.offsetX, entitypainting$enumart.offsetY);
/*     */     
/*  45 */     if (this.renderOutlines) {
/*     */       
/*  47 */       GlStateManager.disableOutlineMode();
/*  48 */       GlStateManager.disableColorMaterial();
/*     */     } 
/*     */     
/*  51 */     GlStateManager.disableRescaleNormal();
/*  52 */     GlStateManager.popMatrix();
/*  53 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityPainting entity) {
/*  61 */     return KRISTOFFER_PAINTING_TEXTURE;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderPainting(EntityPainting painting, int width, int height, int textureU, int textureV) {
/*  66 */     float f = -width / 2.0F;
/*  67 */     float f1 = -height / 2.0F;
/*  68 */     float f2 = 0.5F;
/*  69 */     float f3 = 0.75F;
/*  70 */     float f4 = 0.8125F;
/*  71 */     float f5 = 0.0F;
/*  72 */     float f6 = 0.0625F;
/*  73 */     float f7 = 0.75F;
/*  74 */     float f8 = 0.8125F;
/*  75 */     float f9 = 0.001953125F;
/*  76 */     float f10 = 0.001953125F;
/*  77 */     float f11 = 0.7519531F;
/*  78 */     float f12 = 0.7519531F;
/*  79 */     float f13 = 0.0F;
/*  80 */     float f14 = 0.0625F;
/*     */     
/*  82 */     for (int i = 0; i < width / 16; i++) {
/*     */       
/*  84 */       for (int j = 0; j < height / 16; j++) {
/*     */         
/*  86 */         float f15 = f + ((i + 1) * 16);
/*  87 */         float f16 = f + (i * 16);
/*  88 */         float f17 = f1 + ((j + 1) * 16);
/*  89 */         float f18 = f1 + (j * 16);
/*  90 */         setLightmap(painting, (f15 + f16) / 2.0F, (f17 + f18) / 2.0F);
/*  91 */         float f19 = (textureU + width - i * 16) / 256.0F;
/*  92 */         float f20 = (textureU + width - (i + 1) * 16) / 256.0F;
/*  93 */         float f21 = (textureV + height - j * 16) / 256.0F;
/*  94 */         float f22 = (textureV + height - (j + 1) * 16) / 256.0F;
/*  95 */         Tessellator tessellator = Tessellator.getInstance();
/*  96 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  97 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/*  98 */         bufferbuilder.pos(f15, f18, -0.5D).tex(f20, f21).normal(0.0F, 0.0F, -1.0F).endVertex();
/*  99 */         bufferbuilder.pos(f16, f18, -0.5D).tex(f19, f21).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 100 */         bufferbuilder.pos(f16, f17, -0.5D).tex(f19, f22).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 101 */         bufferbuilder.pos(f15, f17, -0.5D).tex(f20, f22).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 102 */         bufferbuilder.pos(f15, f17, 0.5D).tex(0.75D, 0.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 103 */         bufferbuilder.pos(f16, f17, 0.5D).tex(0.8125D, 0.0D).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 104 */         bufferbuilder.pos(f16, f18, 0.5D).tex(0.8125D, 0.0625D).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 105 */         bufferbuilder.pos(f15, f18, 0.5D).tex(0.75D, 0.0625D).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 106 */         bufferbuilder.pos(f15, f17, -0.5D).tex(0.75D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 107 */         bufferbuilder.pos(f16, f17, -0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 108 */         bufferbuilder.pos(f16, f17, 0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 109 */         bufferbuilder.pos(f15, f17, 0.5D).tex(0.75D, 0.001953125D).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 110 */         bufferbuilder.pos(f15, f18, 0.5D).tex(0.75D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 111 */         bufferbuilder.pos(f16, f18, 0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 112 */         bufferbuilder.pos(f16, f18, -0.5D).tex(0.8125D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 113 */         bufferbuilder.pos(f15, f18, -0.5D).tex(0.75D, 0.001953125D).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 114 */         bufferbuilder.pos(f15, f17, 0.5D).tex(0.751953125D, 0.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 115 */         bufferbuilder.pos(f15, f18, 0.5D).tex(0.751953125D, 0.0625D).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 116 */         bufferbuilder.pos(f15, f18, -0.5D).tex(0.751953125D, 0.0625D).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 117 */         bufferbuilder.pos(f15, f17, -0.5D).tex(0.751953125D, 0.0D).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 118 */         bufferbuilder.pos(f16, f17, -0.5D).tex(0.751953125D, 0.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 119 */         bufferbuilder.pos(f16, f18, -0.5D).tex(0.751953125D, 0.0625D).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 120 */         bufferbuilder.pos(f16, f18, 0.5D).tex(0.751953125D, 0.0625D).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 121 */         bufferbuilder.pos(f16, f17, 0.5D).tex(0.751953125D, 0.0D).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 122 */         tessellator.draw();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setLightmap(EntityPainting painting, float p_77008_2_, float p_77008_3_) {
/* 129 */     int i = MathHelper.floor(painting.posX);
/* 130 */     int j = MathHelper.floor(painting.posY + (p_77008_3_ / 16.0F));
/* 131 */     int k = MathHelper.floor(painting.posZ);
/* 132 */     EnumFacing enumfacing = painting.facingDirection;
/*     */     
/* 134 */     if (enumfacing == EnumFacing.NORTH)
/*     */     {
/* 136 */       i = MathHelper.floor(painting.posX + (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 139 */     if (enumfacing == EnumFacing.WEST)
/*     */     {
/* 141 */       k = MathHelper.floor(painting.posZ - (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 144 */     if (enumfacing == EnumFacing.SOUTH)
/*     */     {
/* 146 */       i = MathHelper.floor(painting.posX - (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 149 */     if (enumfacing == EnumFacing.EAST)
/*     */     {
/* 151 */       k = MathHelper.floor(painting.posZ + (p_77008_2_ / 16.0F));
/*     */     }
/*     */     
/* 154 */     int l = this.renderManager.worldObj.getCombinedLight(new BlockPos(i, j, k), 0);
/* 155 */     int i1 = l % 65536;
/* 156 */     int j1 = l / 65536;
/* 157 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i1, j1);
/* 158 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderPainting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */