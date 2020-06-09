/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class Gui
/*     */ {
/*  12 */   public static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("textures/gui/options_background.png");
/*  13 */   public static final ResourceLocation STAT_ICONS = new ResourceLocation("textures/gui/container/stats_icons.png");
/*  14 */   public static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
/*     */ 
/*     */   
/*     */   protected float zLevel;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHorizontalLine(int startX, int endX, int y, int color) {
/*  22 */     if (endX < startX) {
/*     */       
/*  24 */       int i = startX;
/*  25 */       startX = endX;
/*  26 */       endX = i;
/*     */     } 
/*     */     
/*  29 */     drawRect(startX, y, endX + 1, y + 1, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalLine(int x, int startY, int endY, int color) {
/*  37 */     if (endY < startY) {
/*     */       
/*  39 */       int i = startY;
/*  40 */       startY = endY;
/*  41 */       endY = i;
/*     */     } 
/*     */     
/*  44 */     drawRect(x, startY + 1, x + 1, endY, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, int color) {
/*  52 */     if (left < right) {
/*     */       
/*  54 */       int i = left;
/*  55 */       left = right;
/*  56 */       right = i;
/*     */     } 
/*     */     
/*  59 */     if (top < bottom) {
/*     */       
/*  61 */       int j = top;
/*  62 */       top = bottom;
/*  63 */       bottom = j;
/*     */     } 
/*     */     
/*  66 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  67 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  68 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  69 */     float f2 = (color & 0xFF) / 255.0F;
/*  70 */     Tessellator tessellator = Tessellator.getInstance();
/*  71 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  72 */     GlStateManager.enableBlend();
/*  73 */     GlStateManager.disableTexture2D();
/*  74 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  75 */     GlStateManager.color(f, f1, f2, f3);
/*  76 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
/*  77 */     bufferbuilder.pos(left, bottom, 0.0D).endVertex();
/*  78 */     bufferbuilder.pos(right, bottom, 0.0D).endVertex();
/*  79 */     bufferbuilder.pos(right, top, 0.0D).endVertex();
/*  80 */     bufferbuilder.pos(left, top, 0.0D).endVertex();
/*  81 */     tessellator.draw();
/*  82 */     GlStateManager.enableTexture2D();
/*  83 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  92 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/*  93 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/*  94 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/*  95 */     float f3 = (startColor & 0xFF) / 255.0F;
/*  96 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/*  97 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/*  98 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/*  99 */     float f7 = (endColor & 0xFF) / 255.0F;
/* 100 */     GlStateManager.disableTexture2D();
/* 101 */     GlStateManager.enableBlend();
/* 102 */     GlStateManager.disableAlpha();
/* 103 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 104 */     GlStateManager.shadeModel(7425);
/* 105 */     Tessellator tessellator = Tessellator.getInstance();
/* 106 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 107 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 108 */     bufferbuilder.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 109 */     bufferbuilder.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 110 */     bufferbuilder.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 111 */     bufferbuilder.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 112 */     tessellator.draw();
/* 113 */     GlStateManager.shadeModel(7424);
/* 114 */     GlStateManager.disableBlend();
/* 115 */     GlStateManager.enableAlpha();
/* 116 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 124 */     fontRendererIn.drawStringWithShadow(text, (x - fontRendererIn.getStringWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 132 */     fontRendererIn.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(double x, double y, double textureX, double textureY, double width, double height) {
/* 138 */     float f = 0.00390625F;
/* 139 */     float f1 = 0.00390625F;
/* 140 */     Tessellator tessellator = Tessellator.getInstance();
/* 141 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 142 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 143 */     bufferbuilder.pos(x + 0.0D, y + height, this.zLevel).tex(((float)(textureX + 0.0D) * 0.00390625F), ((float)(textureY + height) * 0.00390625F)).endVertex();
/* 144 */     bufferbuilder.pos(x + width, y + height, this.zLevel).tex(((float)(textureX + width) * 0.00390625F), ((float)(textureY + height) * 0.00390625F)).endVertex();
/* 145 */     bufferbuilder.pos(x + width, y + 0.0D, this.zLevel).tex(((float)(textureX + width) * 0.00390625F), ((float)(textureY + 0.0D) * 0.00390625F)).endVertex();
/* 146 */     bufferbuilder.pos(x + 0.0D, y + 0.0D, this.zLevel).tex(((float)(textureX + 0.0D) * 0.00390625F), ((float)(textureY + 0.0D) * 0.00390625F)).endVertex();
/* 147 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 155 */     float f = 0.00390625F;
/* 156 */     float f1 = 0.00390625F;
/* 157 */     Tessellator tessellator = Tessellator.getInstance();
/* 158 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 159 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 160 */     bufferbuilder.pos((x + 0), (y + height), this.zLevel).tex(((textureX + 0) * 0.00390625F), ((textureY + height) * 0.00390625F)).endVertex();
/* 161 */     bufferbuilder.pos((x + width), (y + height), this.zLevel).tex(((textureX + width) * 0.00390625F), ((textureY + height) * 0.00390625F)).endVertex();
/* 162 */     bufferbuilder.pos((x + width), (y + 0), this.zLevel).tex(((textureX + width) * 0.00390625F), ((textureY + 0) * 0.00390625F)).endVertex();
/* 163 */     bufferbuilder.pos((x + 0), (y + 0), this.zLevel).tex(((textureX + 0) * 0.00390625F), ((textureY + 0) * 0.00390625F)).endVertex();
/* 164 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
/* 172 */     float f = 0.00390625F;
/* 173 */     float f1 = 0.00390625F;
/* 174 */     Tessellator tessellator = Tessellator.getInstance();
/* 175 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 176 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 177 */     bufferbuilder.pos((xCoord + 0.0F), (yCoord + maxV), this.zLevel).tex(((minU + 0) * 0.00390625F), ((minV + maxV) * 0.00390625F)).endVertex();
/* 178 */     bufferbuilder.pos((xCoord + maxU), (yCoord + maxV), this.zLevel).tex(((minU + maxU) * 0.00390625F), ((minV + maxV) * 0.00390625F)).endVertex();
/* 179 */     bufferbuilder.pos((xCoord + maxU), (yCoord + 0.0F), this.zLevel).tex(((minU + maxU) * 0.00390625F), ((minV + 0) * 0.00390625F)).endVertex();
/* 180 */     bufferbuilder.pos((xCoord + 0.0F), (yCoord + 0.0F), this.zLevel).tex(((minU + 0) * 0.00390625F), ((minV + 0) * 0.00390625F)).endVertex();
/* 181 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
/* 189 */     Tessellator tessellator = Tessellator.getInstance();
/* 190 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 191 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 192 */     bufferbuilder.pos((xCoord + 0), (yCoord + heightIn), this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
/* 193 */     bufferbuilder.pos((xCoord + widthIn), (yCoord + heightIn), this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
/* 194 */     bufferbuilder.pos((xCoord + widthIn), (yCoord + 0), this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
/* 195 */     bufferbuilder.pos((xCoord + 0), (yCoord + 0), this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
/* 196 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
/* 204 */     float f = 1.0F / textureWidth;
/* 205 */     float f1 = 1.0F / textureHeight;
/* 206 */     Tessellator tessellator = Tessellator.getInstance();
/* 207 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 208 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 209 */     bufferbuilder.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
/* 210 */     bufferbuilder.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
/* 211 */     bufferbuilder.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
/* 212 */     bufferbuilder.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 213 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 221 */     float f = 1.0F / tileWidth;
/* 222 */     float f1 = 1.0F / tileHeight;
/* 223 */     Tessellator tessellator = Tessellator.getInstance();
/* 224 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 225 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 226 */     bufferbuilder.pos(x, (y + height), 0.0D).tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 227 */     bufferbuilder.pos((x + width), (y + height), 0.0D).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 228 */     bufferbuilder.pos((x + width), y, 0.0D).tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 229 */     bufferbuilder.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 230 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */