/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.minecraft.world.storage.MapDecoration;
/*     */ 
/*     */ public class MapItemRenderer
/*     */ {
/*  19 */   private static final ResourceLocation TEXTURE_MAP_ICONS = new ResourceLocation("textures/map/map_icons.png");
/*     */   private final TextureManager textureManager;
/*  21 */   private final Map<String, Instance> loadedMaps = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public MapItemRenderer(TextureManager textureManagerIn) {
/*  25 */     this.textureManager = textureManagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMapTexture(MapData mapdataIn) {
/*  33 */     getMapRendererInstance(mapdataIn).updateMapTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMap(MapData mapdataIn, boolean p_148250_2_) {
/*  38 */     getMapRendererInstance(mapdataIn).render(p_148250_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Instance getMapRendererInstance(MapData mapdataIn) {
/*  46 */     Instance mapitemrenderer$instance = this.loadedMaps.get(mapdataIn.mapName);
/*     */     
/*  48 */     if (mapitemrenderer$instance == null) {
/*     */       
/*  50 */       mapitemrenderer$instance = new Instance(mapdataIn, null);
/*  51 */       this.loadedMaps.put(mapdataIn.mapName, mapitemrenderer$instance);
/*     */     } 
/*     */     
/*  54 */     return mapitemrenderer$instance;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Instance func_191205_a(String p_191205_1_) {
/*  60 */     return this.loadedMaps.get(p_191205_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearLoadedMaps() {
/*  68 */     for (Instance mapitemrenderer$instance : this.loadedMaps.values())
/*     */     {
/*  70 */       this.textureManager.deleteTexture(mapitemrenderer$instance.location);
/*     */     }
/*     */     
/*  73 */     this.loadedMaps.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MapData func_191207_a(@Nullable Instance p_191207_1_) {
/*  79 */     return (p_191207_1_ != null) ? p_191207_1_.mapData : null;
/*     */   }
/*     */ 
/*     */   
/*     */   class Instance
/*     */   {
/*     */     private final MapData mapData;
/*     */     private final DynamicTexture mapTexture;
/*     */     private final ResourceLocation location;
/*     */     private final int[] mapTextureData;
/*     */     
/*     */     private Instance(MapData mapdataIn) {
/*  91 */       this.mapData = mapdataIn;
/*  92 */       this.mapTexture = new DynamicTexture(128, 128);
/*  93 */       this.mapTextureData = this.mapTexture.getTextureData();
/*  94 */       this.location = MapItemRenderer.this.textureManager.getDynamicTextureLocation("map/" + mapdataIn.mapName, this.mapTexture);
/*     */       
/*  96 */       for (int i = 0; i < this.mapTextureData.length; i++)
/*     */       {
/*  98 */         this.mapTextureData[i] = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void updateMapTexture() {
/* 104 */       for (int i = 0; i < 16384; i++) {
/*     */         
/* 106 */         int j = this.mapData.colors[i] & 0xFF;
/*     */         
/* 108 */         if (j / 4 == 0) {
/*     */           
/* 110 */           this.mapTextureData[i] = (i + i / 128 & 0x1) * 8 + 16 << 24;
/*     */         }
/*     */         else {
/*     */           
/* 114 */           this.mapTextureData[i] = MapColor.COLORS[j / 4].getMapColor(j & 0x3);
/*     */         } 
/*     */       } 
/*     */       
/* 118 */       this.mapTexture.updateDynamicTexture();
/*     */     }
/*     */ 
/*     */     
/*     */     private void render(boolean noOverlayRendering) {
/* 123 */       int i = 0;
/* 124 */       int j = 0;
/* 125 */       Tessellator tessellator = Tessellator.getInstance();
/* 126 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 127 */       float f = 0.0F;
/* 128 */       MapItemRenderer.this.textureManager.bindTexture(this.location);
/* 129 */       GlStateManager.enableBlend();
/* 130 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
/* 131 */       GlStateManager.disableAlpha();
/* 132 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 133 */       bufferbuilder.pos(0.0D, 128.0D, -0.009999999776482582D).tex(0.0D, 1.0D).endVertex();
/* 134 */       bufferbuilder.pos(128.0D, 128.0D, -0.009999999776482582D).tex(1.0D, 1.0D).endVertex();
/* 135 */       bufferbuilder.pos(128.0D, 0.0D, -0.009999999776482582D).tex(1.0D, 0.0D).endVertex();
/* 136 */       bufferbuilder.pos(0.0D, 0.0D, -0.009999999776482582D).tex(0.0D, 0.0D).endVertex();
/* 137 */       tessellator.draw();
/* 138 */       GlStateManager.enableAlpha();
/* 139 */       GlStateManager.disableBlend();
/* 140 */       MapItemRenderer.this.textureManager.bindTexture(MapItemRenderer.TEXTURE_MAP_ICONS);
/* 141 */       int k = 0;
/*     */       
/* 143 */       for (MapDecoration mapdecoration : this.mapData.mapDecorations.values()) {
/*     */         
/* 145 */         if (!noOverlayRendering || mapdecoration.func_191180_f()) {
/*     */           
/* 147 */           GlStateManager.pushMatrix();
/* 148 */           GlStateManager.translate(0.0F + mapdecoration.getX() / 2.0F + 64.0F, 0.0F + mapdecoration.getY() / 2.0F + 64.0F, -0.02F);
/* 149 */           GlStateManager.rotate((mapdecoration.getRotation() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
/* 150 */           GlStateManager.scale(4.0F, 4.0F, 3.0F);
/* 151 */           GlStateManager.translate(-0.125F, 0.125F, 0.0F);
/* 152 */           byte b0 = mapdecoration.getType();
/* 153 */           float f1 = (b0 % 4 + 0) / 4.0F;
/* 154 */           float f2 = (b0 / 4 + 0) / 4.0F;
/* 155 */           float f3 = (b0 % 4 + 1) / 4.0F;
/* 156 */           float f4 = (b0 / 4 + 1) / 4.0F;
/* 157 */           bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 158 */           float f5 = -0.001F;
/* 159 */           bufferbuilder.pos(-1.0D, 1.0D, (k * -0.001F)).tex(f1, f2).endVertex();
/* 160 */           bufferbuilder.pos(1.0D, 1.0D, (k * -0.001F)).tex(f3, f2).endVertex();
/* 161 */           bufferbuilder.pos(1.0D, -1.0D, (k * -0.001F)).tex(f3, f4).endVertex();
/* 162 */           bufferbuilder.pos(-1.0D, -1.0D, (k * -0.001F)).tex(f1, f4).endVertex();
/* 163 */           tessellator.draw();
/* 164 */           GlStateManager.popMatrix();
/* 165 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 169 */       GlStateManager.pushMatrix();
/* 170 */       GlStateManager.translate(0.0F, 0.0F, -0.04F);
/* 171 */       GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 172 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\MapItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */