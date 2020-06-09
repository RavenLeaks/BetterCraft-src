/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import optifine.Config;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon> {
/*  16 */   public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");
/*     */ 
/*     */   
/*     */   public void func_192841_a(TileEntityBeacon p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/*  20 */     renderBeacon(p_192841_2_, p_192841_4_, p_192841_6_, p_192841_8_, p_192841_1_.shouldBeamRender(), p_192841_1_.getBeamSegments(), p_192841_1_.getWorld().getTotalWorldTime());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBeacon(double p_188206_1_, double p_188206_3_, double p_188206_5_, double p_188206_7_, double p_188206_9_, List<TileEntityBeacon.BeamSegment> p_188206_11_, double p_188206_12_) {
/*  25 */     if (p_188206_9_ > 0.0D && p_188206_11_.size() > 0) {
/*     */       
/*  27 */       if (Config.isShaders())
/*     */       {
/*  29 */         Shaders.beginBeacon();
/*     */       }
/*     */       
/*  32 */       GlStateManager.alphaFunc(516, 0.1F);
/*  33 */       bindTexture(TEXTURE_BEACON_BEAM);
/*     */       
/*  35 */       if (p_188206_9_ > 0.0D) {
/*     */         
/*  37 */         GlStateManager.disableFog();
/*  38 */         int i = 0;
/*     */         
/*  40 */         for (int j = 0; j < p_188206_11_.size(); j++) {
/*     */           
/*  42 */           TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = p_188206_11_.get(j);
/*  43 */           renderBeamSegment(p_188206_1_, p_188206_3_, p_188206_5_, p_188206_7_, p_188206_9_, p_188206_12_, i, tileentitybeacon$beamsegment.getHeight(), tileentitybeacon$beamsegment.getColors());
/*  44 */           i += tileentitybeacon$beamsegment.getHeight();
/*     */         } 
/*     */         
/*  47 */         GlStateManager.enableFog();
/*     */       } 
/*     */       
/*  50 */       if (Config.isShaders())
/*     */       {
/*  52 */         Shaders.endBeacon();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors) {
/*  59 */     renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, 0.2D, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius) {
/*  64 */     int i = yOffset + height;
/*  65 */     GlStateManager.glTexParameteri(3553, 10242, 10497);
/*  66 */     GlStateManager.glTexParameteri(3553, 10243, 10497);
/*  67 */     GlStateManager.disableLighting();
/*  68 */     GlStateManager.disableCull();
/*  69 */     GlStateManager.disableBlend();
/*  70 */     GlStateManager.depthMask(true);
/*  71 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  72 */     Tessellator tessellator = Tessellator.getInstance();
/*  73 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  74 */     double d0 = totalWorldTime + partialTicks;
/*  75 */     double d1 = (height < 0) ? d0 : -d0;
/*  76 */     double d2 = MathHelper.frac(d1 * 0.2D - MathHelper.floor(d1 * 0.1D));
/*  77 */     float f = colors[0];
/*  78 */     float f1 = colors[1];
/*  79 */     float f2 = colors[2];
/*  80 */     double d3 = d0 * 0.025D * -1.5D;
/*  81 */     double d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * beamRadius;
/*  82 */     double d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * beamRadius;
/*  83 */     double d6 = 0.5D + Math.cos(d3 + 0.7853981633974483D) * beamRadius;
/*  84 */     double d7 = 0.5D + Math.sin(d3 + 0.7853981633974483D) * beamRadius;
/*  85 */     double d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * beamRadius;
/*  86 */     double d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * beamRadius;
/*  87 */     double d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * beamRadius;
/*  88 */     double d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * beamRadius;
/*  89 */     double d12 = 0.0D;
/*  90 */     double d13 = 1.0D;
/*  91 */     double d14 = -1.0D + d2;
/*  92 */     double d15 = height * textureScale * 0.5D / beamRadius + d14;
/*  93 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*  94 */     bufferbuilder.pos(x + d4, y + i, z + d5).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/*  95 */     bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/*  96 */     bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/*  97 */     bufferbuilder.pos(x + d6, y + i, z + d7).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/*  98 */     bufferbuilder.pos(x + d10, y + i, z + d11).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/*  99 */     bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/* 100 */     bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/* 101 */     bufferbuilder.pos(x + d8, y + i, z + d9).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/* 102 */     bufferbuilder.pos(x + d6, y + i, z + d7).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/* 103 */     bufferbuilder.pos(x + d6, y + yOffset, z + d7).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/* 104 */     bufferbuilder.pos(x + d10, y + yOffset, z + d11).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/* 105 */     bufferbuilder.pos(x + d10, y + i, z + d11).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/* 106 */     bufferbuilder.pos(x + d8, y + i, z + d9).tex(1.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/* 107 */     bufferbuilder.pos(x + d8, y + yOffset, z + d9).tex(1.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/* 108 */     bufferbuilder.pos(x + d4, y + yOffset, z + d5).tex(0.0D, d14).color(f, f1, f2, 1.0F).endVertex();
/* 109 */     bufferbuilder.pos(x + d4, y + i, z + d5).tex(0.0D, d15).color(f, f1, f2, 1.0F).endVertex();
/* 110 */     tessellator.draw();
/* 111 */     GlStateManager.enableBlend();
/* 112 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 113 */     GlStateManager.depthMask(false);
/* 114 */     d3 = 0.5D - glowRadius;
/* 115 */     d4 = 0.5D - glowRadius;
/* 116 */     d5 = 0.5D + glowRadius;
/* 117 */     d6 = 0.5D - glowRadius;
/* 118 */     d7 = 0.5D - glowRadius;
/* 119 */     d8 = 0.5D + glowRadius;
/* 120 */     d9 = 0.5D + glowRadius;
/* 121 */     d10 = 0.5D + glowRadius;
/* 122 */     d11 = 0.0D;
/* 123 */     d12 = 1.0D;
/* 124 */     d13 = -1.0D + d2;
/* 125 */     d14 = height * textureScale + d13;
/* 126 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 127 */     bufferbuilder.pos(x + d3, y + i, z + d4).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 128 */     bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 129 */     bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 130 */     bufferbuilder.pos(x + d5, y + i, z + d6).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 131 */     bufferbuilder.pos(x + d9, y + i, z + d10).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 132 */     bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 133 */     bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 134 */     bufferbuilder.pos(x + d7, y + i, z + d8).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 135 */     bufferbuilder.pos(x + d5, y + i, z + d6).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 136 */     bufferbuilder.pos(x + d5, y + yOffset, z + d6).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 137 */     bufferbuilder.pos(x + d9, y + yOffset, z + d10).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 138 */     bufferbuilder.pos(x + d9, y + i, z + d10).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 139 */     bufferbuilder.pos(x + d7, y + i, z + d8).tex(1.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 140 */     bufferbuilder.pos(x + d7, y + yOffset, z + d8).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 141 */     bufferbuilder.pos(x + d3, y + yOffset, z + d4).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
/* 142 */     bufferbuilder.pos(x + d3, y + i, z + d4).tex(0.0D, d14).color(f, f1, f2, 0.125F).endVertex();
/* 143 */     tessellator.draw();
/* 144 */     GlStateManager.enableLighting();
/* 145 */     GlStateManager.enableTexture2D();
/* 146 */     GlStateManager.depthMask(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGlobalRenderer(TileEntityBeacon te) {
/* 151 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityBeaconRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */