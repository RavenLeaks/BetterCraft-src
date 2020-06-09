/*     */ package optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ 
/*     */ 
/*     */ public class ConnectedTexturesCompact
/*     */ {
/*     */   private static final int COMPACT_NONE = 0;
/*     */   private static final int COMPACT_ALL = 1;
/*     */   private static final int COMPACT_V = 2;
/*     */   private static final int COMPACT_H = 3;
/*     */   private static final int COMPACT_HV = 4;
/*     */   
/*     */   public static BakedQuad[] getConnectedTextureCtmCompact(int p_getConnectedTextureCtmCompact_0_, ConnectedProperties p_getConnectedTextureCtmCompact_1_, int p_getConnectedTextureCtmCompact_2_, BakedQuad p_getConnectedTextureCtmCompact_3_, RenderEnv p_getConnectedTextureCtmCompact_4_) {
/*  18 */     if (p_getConnectedTextureCtmCompact_1_.ctmTileIndexes != null && p_getConnectedTextureCtmCompact_0_ >= 0 && p_getConnectedTextureCtmCompact_0_ < p_getConnectedTextureCtmCompact_1_.ctmTileIndexes.length) {
/*     */       
/*  20 */       int i = p_getConnectedTextureCtmCompact_1_.ctmTileIndexes[p_getConnectedTextureCtmCompact_0_];
/*     */       
/*  22 */       if (i >= 0 && i <= p_getConnectedTextureCtmCompact_1_.tileIcons.length)
/*     */       {
/*  24 */         return getQuadsCompact(i, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       }
/*     */     } 
/*     */     
/*  28 */     switch (p_getConnectedTextureCtmCompact_0_) {
/*     */       
/*     */       case 1:
/*  31 */         return getQuadsCompactH(0, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 2:
/*  34 */         return getQuadsCompact(3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 3:
/*  37 */         return getQuadsCompactH(3, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 4:
/*  40 */         return getQuadsCompact4(0, 3, 2, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 5:
/*  43 */         return getQuadsCompact4(3, 0, 4, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 6:
/*  46 */         return getQuadsCompact4(2, 4, 2, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 7:
/*  49 */         return getQuadsCompact4(3, 3, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 8:
/*  52 */         return getQuadsCompact4(4, 1, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 9:
/*  55 */         return getQuadsCompact4(4, 4, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 10:
/*  58 */         return getQuadsCompact4(1, 4, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 11:
/*  61 */         return getQuadsCompact4(1, 1, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 12:
/*  64 */         return getQuadsCompactV(0, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 13:
/*  67 */         return getQuadsCompact4(0, 3, 2, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 14:
/*  70 */         return getQuadsCompactV(3, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 15:
/*  73 */         return getQuadsCompact4(3, 0, 1, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 16:
/*  76 */         return getQuadsCompact4(2, 4, 0, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 17:
/*  79 */         return getQuadsCompact4(4, 2, 3, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 18:
/*  82 */         return getQuadsCompact4(4, 4, 3, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 19:
/*  85 */         return getQuadsCompact4(4, 2, 4, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 20:
/*  88 */         return getQuadsCompact4(1, 4, 4, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 21:
/*  91 */         return getQuadsCompact4(4, 4, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 22:
/*  94 */         return getQuadsCompact4(4, 4, 1, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 23:
/*  97 */         return getQuadsCompact4(4, 1, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 24:
/* 100 */         return getQuadsCompact(2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 25:
/* 103 */         return getQuadsCompactH(2, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 26:
/* 106 */         return getQuadsCompact(1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 27:
/* 109 */         return getQuadsCompactH(1, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 28:
/* 112 */         return getQuadsCompact4(2, 4, 2, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 29:
/* 115 */         return getQuadsCompact4(3, 3, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 30:
/* 118 */         return getQuadsCompact4(2, 1, 2, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 31:
/* 121 */         return getQuadsCompact4(3, 3, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 32:
/* 124 */         return getQuadsCompact4(1, 1, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 33:
/* 127 */         return getQuadsCompact4(1, 1, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 34:
/* 130 */         return getQuadsCompact4(4, 1, 1, 4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 35:
/* 133 */         return getQuadsCompact4(1, 4, 4, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 36:
/* 136 */         return getQuadsCompactV(2, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 37:
/* 139 */         return getQuadsCompact4(2, 1, 0, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 38:
/* 142 */         return getQuadsCompactV(1, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 39:
/* 145 */         return getQuadsCompact4(1, 2, 3, 0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 40:
/* 148 */         return getQuadsCompact4(4, 1, 3, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 41:
/* 151 */         return getQuadsCompact4(1, 2, 4, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 42:
/* 154 */         return getQuadsCompact4(1, 4, 3, 3, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 43:
/* 157 */         return getQuadsCompact4(4, 2, 1, 2, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 44:
/* 160 */         return getQuadsCompact4(1, 4, 1, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 45:
/* 163 */         return getQuadsCompact4(4, 1, 1, 1, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */       
/*     */       case 46:
/* 166 */         return getQuadsCompact(4, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */     } 
/*     */     
/* 169 */     return getQuadsCompact(0, p_getConnectedTextureCtmCompact_1_.tileIcons, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactH(int p_getQuadsCompactH_0_, int p_getQuadsCompactH_1_, TextureAtlasSprite[] p_getQuadsCompactH_2_, int p_getQuadsCompactH_3_, BakedQuad p_getQuadsCompactH_4_, RenderEnv p_getQuadsCompactH_5_) {
/* 175 */     return getQuadsCompact(Dir.LEFT, p_getQuadsCompactH_0_, Dir.RIGHT, p_getQuadsCompactH_1_, p_getQuadsCompactH_2_, p_getQuadsCompactH_3_, p_getQuadsCompactH_4_, p_getQuadsCompactH_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompactV(int p_getQuadsCompactV_0_, int p_getQuadsCompactV_1_, TextureAtlasSprite[] p_getQuadsCompactV_2_, int p_getQuadsCompactV_3_, BakedQuad p_getQuadsCompactV_4_, RenderEnv p_getQuadsCompactV_5_) {
/* 180 */     return getQuadsCompact(Dir.UP, p_getQuadsCompactV_0_, Dir.DOWN, p_getQuadsCompactV_1_, p_getQuadsCompactV_2_, p_getQuadsCompactV_3_, p_getQuadsCompactV_4_, p_getQuadsCompactV_5_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact4(int p_getQuadsCompact4_0_, int p_getQuadsCompact4_1_, int p_getQuadsCompact4_2_, int p_getQuadsCompact4_3_, TextureAtlasSprite[] p_getQuadsCompact4_4_, int p_getQuadsCompact4_5_, BakedQuad p_getQuadsCompact4_6_, RenderEnv p_getQuadsCompact4_7_) {
/* 185 */     if (p_getQuadsCompact4_0_ == p_getQuadsCompact4_1_)
/*     */     {
/* 187 */       return (p_getQuadsCompact4_2_ == p_getQuadsCompact4_3_) ? getQuadsCompact(Dir.UP, p_getQuadsCompact4_0_, Dir.DOWN, p_getQuadsCompact4_2_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_) : getQuadsCompact(Dir.UP, p_getQuadsCompact4_0_, Dir.DOWN_LEFT, p_getQuadsCompact4_2_, Dir.DOWN_RIGHT, p_getQuadsCompact4_3_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
/*     */     }
/* 189 */     if (p_getQuadsCompact4_2_ == p_getQuadsCompact4_3_)
/*     */     {
/* 191 */       return getQuadsCompact(Dir.UP_LEFT, p_getQuadsCompact4_0_, Dir.UP_RIGHT, p_getQuadsCompact4_1_, Dir.DOWN, p_getQuadsCompact4_2_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
/*     */     }
/* 193 */     if (p_getQuadsCompact4_0_ == p_getQuadsCompact4_2_)
/*     */     {
/* 195 */       return (p_getQuadsCompact4_1_ == p_getQuadsCompact4_3_) ? getQuadsCompact(Dir.LEFT, p_getQuadsCompact4_0_, Dir.RIGHT, p_getQuadsCompact4_1_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_) : getQuadsCompact(Dir.LEFT, p_getQuadsCompact4_0_, Dir.UP_RIGHT, p_getQuadsCompact4_1_, Dir.DOWN_RIGHT, p_getQuadsCompact4_3_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
/*     */     }
/*     */ 
/*     */     
/* 199 */     return (p_getQuadsCompact4_1_ == p_getQuadsCompact4_3_) ? getQuadsCompact(Dir.UP_LEFT, p_getQuadsCompact4_0_, Dir.DOWN_LEFT, p_getQuadsCompact4_2_, Dir.RIGHT, p_getQuadsCompact4_1_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_) : getQuadsCompact(Dir.UP_LEFT, p_getQuadsCompact4_0_, Dir.UP_RIGHT, p_getQuadsCompact4_1_, Dir.DOWN_LEFT, p_getQuadsCompact4_2_, Dir.DOWN_RIGHT, p_getQuadsCompact4_3_, p_getQuadsCompact4_4_, p_getQuadsCompact4_5_, p_getQuadsCompact4_6_, p_getQuadsCompact4_7_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(int p_getQuadsCompact_0_, TextureAtlasSprite[] p_getQuadsCompact_1_, BakedQuad p_getQuadsCompact_2_, RenderEnv p_getQuadsCompact_3_) {
/* 205 */     TextureAtlasSprite textureatlassprite = p_getQuadsCompact_1_[p_getQuadsCompact_0_];
/* 206 */     return ConnectedTextures.getQuads(textureatlassprite, p_getQuadsCompact_2_, p_getQuadsCompact_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir p_getQuadsCompact_0_, int p_getQuadsCompact_1_, Dir p_getQuadsCompact_2_, int p_getQuadsCompact_3_, TextureAtlasSprite[] p_getQuadsCompact_4_, int p_getQuadsCompact_5_, BakedQuad p_getQuadsCompact_6_, RenderEnv p_getQuadsCompact_7_) {
/* 211 */     BakedQuad bakedquad = getQuadCompact(p_getQuadsCompact_4_[p_getQuadsCompact_1_], p_getQuadsCompact_0_, p_getQuadsCompact_5_, p_getQuadsCompact_6_, p_getQuadsCompact_7_);
/* 212 */     BakedQuad bakedquad1 = getQuadCompact(p_getQuadsCompact_4_[p_getQuadsCompact_3_], p_getQuadsCompact_2_, p_getQuadsCompact_5_, p_getQuadsCompact_6_, p_getQuadsCompact_7_);
/* 213 */     return p_getQuadsCompact_7_.getArrayQuadsCtm(bakedquad, bakedquad1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir p_getQuadsCompact_0_, int p_getQuadsCompact_1_, Dir p_getQuadsCompact_2_, int p_getQuadsCompact_3_, Dir p_getQuadsCompact_4_, int p_getQuadsCompact_5_, TextureAtlasSprite[] p_getQuadsCompact_6_, int p_getQuadsCompact_7_, BakedQuad p_getQuadsCompact_8_, RenderEnv p_getQuadsCompact_9_) {
/* 218 */     BakedQuad bakedquad = getQuadCompact(p_getQuadsCompact_6_[p_getQuadsCompact_1_], p_getQuadsCompact_0_, p_getQuadsCompact_7_, p_getQuadsCompact_8_, p_getQuadsCompact_9_);
/* 219 */     BakedQuad bakedquad1 = getQuadCompact(p_getQuadsCompact_6_[p_getQuadsCompact_3_], p_getQuadsCompact_2_, p_getQuadsCompact_7_, p_getQuadsCompact_8_, p_getQuadsCompact_9_);
/* 220 */     BakedQuad bakedquad2 = getQuadCompact(p_getQuadsCompact_6_[p_getQuadsCompact_5_], p_getQuadsCompact_4_, p_getQuadsCompact_7_, p_getQuadsCompact_8_, p_getQuadsCompact_9_);
/* 221 */     return p_getQuadsCompact_9_.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad[] getQuadsCompact(Dir p_getQuadsCompact_0_, int p_getQuadsCompact_1_, Dir p_getQuadsCompact_2_, int p_getQuadsCompact_3_, Dir p_getQuadsCompact_4_, int p_getQuadsCompact_5_, Dir p_getQuadsCompact_6_, int p_getQuadsCompact_7_, TextureAtlasSprite[] p_getQuadsCompact_8_, int p_getQuadsCompact_9_, BakedQuad p_getQuadsCompact_10_, RenderEnv p_getQuadsCompact_11_) {
/* 226 */     BakedQuad bakedquad = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_1_], p_getQuadsCompact_0_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
/* 227 */     BakedQuad bakedquad1 = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_3_], p_getQuadsCompact_2_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
/* 228 */     BakedQuad bakedquad2 = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_5_], p_getQuadsCompact_4_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
/* 229 */     BakedQuad bakedquad3 = getQuadCompact(p_getQuadsCompact_8_[p_getQuadsCompact_7_], p_getQuadsCompact_6_, p_getQuadsCompact_9_, p_getQuadsCompact_10_, p_getQuadsCompact_11_);
/* 230 */     return p_getQuadsCompact_11_.getArrayQuadsCtm(bakedquad, bakedquad1, bakedquad2, bakedquad3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite p_getQuadCompact_0_, Dir p_getQuadCompact_1_, int p_getQuadCompact_2_, BakedQuad p_getQuadCompact_3_, RenderEnv p_getQuadCompact_4_) {
/* 235 */     switch (p_getQuadCompact_1_) {
/*     */       
/*     */       case UP:
/* 238 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 0, 16, 8, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case UP_RIGHT:
/* 241 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 8, 0, 16, 8, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case RIGHT:
/* 244 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 8, 0, 16, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case DOWN_RIGHT:
/* 247 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 8, 8, 16, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case null:
/* 250 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 8, 16, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case DOWN_LEFT:
/* 253 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 8, 8, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case LEFT:
/* 256 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 0, 8, 16, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */       
/*     */       case UP_LEFT:
/* 259 */         return getQuadCompact(p_getQuadCompact_0_, p_getQuadCompact_1_, 0, 0, 8, 8, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_);
/*     */     } 
/*     */     
/* 262 */     return p_getQuadCompact_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad getQuadCompact(TextureAtlasSprite p_getQuadCompact_0_, Dir p_getQuadCompact_1_, int p_getQuadCompact_2_, int p_getQuadCompact_3_, int p_getQuadCompact_4_, int p_getQuadCompact_5_, int p_getQuadCompact_6_, BakedQuad p_getQuadCompact_7_, RenderEnv p_getQuadCompact_8_) {
/* 268 */     Map[][] amap = ConnectedTextures.getSpriteQuadCompactMaps();
/*     */     
/* 270 */     if (amap == null)
/*     */     {
/* 272 */       return p_getQuadCompact_7_;
/*     */     }
/*     */ 
/*     */     
/* 276 */     int i = p_getQuadCompact_0_.getIndexInMap();
/*     */     
/* 278 */     if (i >= 0 && i < amap.length) {
/*     */       
/* 280 */       Map[] amap1 = amap[i];
/*     */       
/* 282 */       if (amap1 == null) {
/*     */         
/* 284 */         amap1 = new Map[Dir.VALUES.length];
/* 285 */         amap[i] = amap1;
/*     */       } 
/*     */       
/* 288 */       Map<BakedQuad, BakedQuad> map = amap1[p_getQuadCompact_1_.ordinal()];
/*     */       
/* 290 */       if (map == null) {
/*     */         
/* 292 */         map = new IdentityHashMap<>(1);
/* 293 */         amap1[p_getQuadCompact_1_.ordinal()] = map;
/*     */       } 
/*     */       
/* 296 */       BakedQuad bakedquad = map.get(p_getQuadCompact_7_);
/*     */       
/* 298 */       if (bakedquad == null) {
/*     */         
/* 300 */         bakedquad = makeSpriteQuadCompact(p_getQuadCompact_7_, p_getQuadCompact_0_, p_getQuadCompact_6_, p_getQuadCompact_2_, p_getQuadCompact_3_, p_getQuadCompact_4_, p_getQuadCompact_5_);
/* 301 */         map.put(p_getQuadCompact_7_, bakedquad);
/*     */       } 
/*     */       
/* 304 */       return bakedquad;
/*     */     } 
/*     */ 
/*     */     
/* 308 */     return p_getQuadCompact_7_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BakedQuad makeSpriteQuadCompact(BakedQuad p_makeSpriteQuadCompact_0_, TextureAtlasSprite p_makeSpriteQuadCompact_1_, int p_makeSpriteQuadCompact_2_, int p_makeSpriteQuadCompact_3_, int p_makeSpriteQuadCompact_4_, int p_makeSpriteQuadCompact_5_, int p_makeSpriteQuadCompact_6_) {
/* 315 */     int[] aint = (int[])p_makeSpriteQuadCompact_0_.getVertexData().clone();
/* 316 */     TextureAtlasSprite textureatlassprite = p_makeSpriteQuadCompact_0_.getSprite();
/*     */     
/* 318 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 320 */       fixVertexCompact(aint, i, textureatlassprite, p_makeSpriteQuadCompact_1_, p_makeSpriteQuadCompact_2_, p_makeSpriteQuadCompact_3_, p_makeSpriteQuadCompact_4_, p_makeSpriteQuadCompact_5_, p_makeSpriteQuadCompact_6_);
/*     */     }
/*     */     
/* 323 */     BakedQuad bakedquad = new BakedQuad(aint, p_makeSpriteQuadCompact_0_.getTintIndex(), p_makeSpriteQuadCompact_0_.getFace(), p_makeSpriteQuadCompact_1_);
/* 324 */     return bakedquad;
/*     */   }
/*     */   
/*     */   private static void fixVertexCompact(int[] p_fixVertexCompact_0_, int p_fixVertexCompact_1_, TextureAtlasSprite p_fixVertexCompact_2_, TextureAtlasSprite p_fixVertexCompact_3_, int p_fixVertexCompact_4_, int p_fixVertexCompact_5_, int p_fixVertexCompact_6_, int p_fixVertexCompact_7_, int p_fixVertexCompact_8_) {
/*     */     float f5, f6;
/* 329 */     int i = p_fixVertexCompact_0_.length / 4;
/* 330 */     int j = i * p_fixVertexCompact_1_;
/* 331 */     float f = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 4]);
/* 332 */     float f1 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 4 + 1]);
/* 333 */     double d0 = p_fixVertexCompact_2_.getSpriteU16(f);
/* 334 */     double d1 = p_fixVertexCompact_2_.getSpriteV16(f1);
/* 335 */     float f2 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 0]);
/* 336 */     float f3 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 1]);
/* 337 */     float f4 = Float.intBitsToFloat(p_fixVertexCompact_0_[j + 2]);
/*     */ 
/*     */ 
/*     */     
/* 341 */     switch (p_fixVertexCompact_4_) {
/*     */       
/*     */       case 0:
/* 344 */         f5 = f2;
/* 345 */         f6 = 1.0F - f4;
/*     */         break;
/*     */       
/*     */       case 1:
/* 349 */         f5 = f2;
/* 350 */         f6 = f4;
/*     */         break;
/*     */       
/*     */       case 2:
/* 354 */         f5 = 1.0F - f2;
/* 355 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 3:
/* 359 */         f5 = f2;
/* 360 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 4:
/* 364 */         f5 = f4;
/* 365 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       case 5:
/* 369 */         f5 = 1.0F - f4;
/* 370 */         f6 = 1.0F - f3;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 377 */     float f7 = 15.968F;
/* 378 */     float f8 = 15.968F;
/*     */     
/* 380 */     if (d0 < p_fixVertexCompact_5_) {
/*     */       
/* 382 */       f5 = (float)(f5 + (p_fixVertexCompact_5_ - d0) / f7);
/* 383 */       d0 = p_fixVertexCompact_5_;
/*     */     } 
/*     */     
/* 386 */     if (d0 > p_fixVertexCompact_7_) {
/*     */       
/* 388 */       f5 = (float)(f5 - (d0 - p_fixVertexCompact_7_) / f7);
/* 389 */       d0 = p_fixVertexCompact_7_;
/*     */     } 
/*     */     
/* 392 */     if (d1 < p_fixVertexCompact_6_) {
/*     */       
/* 394 */       f6 = (float)(f6 + (p_fixVertexCompact_6_ - d1) / f8);
/* 395 */       d1 = p_fixVertexCompact_6_;
/*     */     } 
/*     */     
/* 398 */     if (d1 > p_fixVertexCompact_8_) {
/*     */       
/* 400 */       f6 = (float)(f6 - (d1 - p_fixVertexCompact_8_) / f8);
/* 401 */       d1 = p_fixVertexCompact_8_;
/*     */     } 
/*     */     
/* 404 */     switch (p_fixVertexCompact_4_) {
/*     */       
/*     */       case 0:
/* 407 */         f2 = f5;
/* 408 */         f4 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 1:
/* 412 */         f2 = f5;
/* 413 */         f4 = f6;
/*     */         break;
/*     */       
/*     */       case 2:
/* 417 */         f2 = 1.0F - f5;
/* 418 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 3:
/* 422 */         f2 = f5;
/* 423 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 4:
/* 427 */         f4 = f5;
/* 428 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       case 5:
/* 432 */         f4 = 1.0F - f5;
/* 433 */         f3 = 1.0F - f6;
/*     */         break;
/*     */       
/*     */       default:
/*     */         return;
/*     */     } 
/*     */     
/* 440 */     p_fixVertexCompact_0_[j + 4] = Float.floatToRawIntBits(p_fixVertexCompact_3_.getInterpolatedU(d0));
/* 441 */     p_fixVertexCompact_0_[j + 4 + 1] = Float.floatToRawIntBits(p_fixVertexCompact_3_.getInterpolatedV(d1));
/* 442 */     p_fixVertexCompact_0_[j + 0] = Float.floatToRawIntBits(f2);
/* 443 */     p_fixVertexCompact_0_[j + 1] = Float.floatToRawIntBits(f3);
/* 444 */     p_fixVertexCompact_0_[j + 2] = Float.floatToRawIntBits(f4);
/*     */   }
/*     */   
/*     */   private enum Dir
/*     */   {
/* 449 */     UP,
/* 450 */     UP_RIGHT,
/* 451 */     RIGHT,
/* 452 */     DOWN_RIGHT,
/* 453 */     DOWN,
/* 454 */     DOWN_LEFT,
/* 455 */     LEFT,
/* 456 */     UP_LEFT;
/*     */     
/* 458 */     public static final Dir[] VALUES = values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ConnectedTexturesCompact.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */