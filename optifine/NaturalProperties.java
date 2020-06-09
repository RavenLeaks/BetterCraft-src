/*     */ package optifine;
/*     */ 
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class NaturalProperties
/*     */ {
/*  12 */   public int rotation = 1;
/*     */   public boolean flip = false;
/*  14 */   private Map[] quadMaps = new Map[8];
/*     */ 
/*     */   
/*     */   public NaturalProperties(String p_i68_1_) {
/*  18 */     if (p_i68_1_.equals("4")) {
/*     */       
/*  20 */       this.rotation = 4;
/*     */     }
/*  22 */     else if (p_i68_1_.equals("2")) {
/*     */       
/*  24 */       this.rotation = 2;
/*     */     }
/*  26 */     else if (p_i68_1_.equals("F")) {
/*     */       
/*  28 */       this.flip = true;
/*     */     }
/*  30 */     else if (p_i68_1_.equals("4F")) {
/*     */       
/*  32 */       this.rotation = 4;
/*  33 */       this.flip = true;
/*     */     }
/*  35 */     else if (p_i68_1_.equals("2F")) {
/*     */       
/*  37 */       this.rotation = 2;
/*  38 */       this.flip = true;
/*     */     }
/*     */     else {
/*     */       
/*  42 */       Config.warn("NaturalTextures: Unknown type: " + p_i68_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  48 */     if (this.rotation != 2 && this.rotation != 4)
/*     */     {
/*  50 */       return this.flip;
/*     */     }
/*     */ 
/*     */     
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized BakedQuad getQuad(BakedQuad p_getQuad_1_, int p_getQuad_2_, boolean p_getQuad_3_) {
/*  60 */     int i = p_getQuad_2_;
/*     */     
/*  62 */     if (p_getQuad_3_)
/*     */     {
/*  64 */       i = p_getQuad_2_ | 0x4;
/*     */     }
/*     */     
/*  67 */     if (i > 0 && i < this.quadMaps.length) {
/*     */       
/*  69 */       Map<Object, Object> map = this.quadMaps[i];
/*     */       
/*  71 */       if (map == null) {
/*     */         
/*  73 */         map = new IdentityHashMap<>(1);
/*  74 */         this.quadMaps[i] = map;
/*     */       } 
/*     */       
/*  77 */       BakedQuad bakedquad = (BakedQuad)map.get(p_getQuad_1_);
/*     */       
/*  79 */       if (bakedquad == null) {
/*     */         
/*  81 */         bakedquad = makeQuad(p_getQuad_1_, p_getQuad_2_, p_getQuad_3_);
/*  82 */         map.put(p_getQuad_1_, bakedquad);
/*     */       } 
/*     */       
/*  85 */       return bakedquad;
/*     */     } 
/*     */ 
/*     */     
/*  89 */     return p_getQuad_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BakedQuad makeQuad(BakedQuad p_makeQuad_1_, int p_makeQuad_2_, boolean p_makeQuad_3_) {
/*  95 */     int[] aint = p_makeQuad_1_.getVertexData();
/*  96 */     int i = p_makeQuad_1_.getTintIndex();
/*  97 */     EnumFacing enumfacing = p_makeQuad_1_.getFace();
/*  98 */     TextureAtlasSprite textureatlassprite = p_makeQuad_1_.getSprite();
/*     */     
/* 100 */     if (!isFullSprite(p_makeQuad_1_))
/*     */     {
/* 102 */       p_makeQuad_2_ = 0;
/*     */     }
/*     */     
/* 105 */     aint = transformVertexData(aint, p_makeQuad_2_, p_makeQuad_3_);
/* 106 */     BakedQuad bakedquad = new BakedQuad(aint, i, enumfacing, textureatlassprite);
/* 107 */     return bakedquad;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] transformVertexData(int[] p_transformVertexData_1_, int p_transformVertexData_2_, boolean p_transformVertexData_3_) {
/* 112 */     int[] aint = (int[])p_transformVertexData_1_.clone();
/* 113 */     int i = 4 - p_transformVertexData_2_;
/*     */     
/* 115 */     if (p_transformVertexData_3_)
/*     */     {
/* 117 */       i += 3;
/*     */     }
/*     */     
/* 120 */     i %= 4;
/* 121 */     int j = aint.length / 4;
/*     */     
/* 123 */     for (int k = 0; k < 4; k++) {
/*     */       
/* 125 */       int l = k * j;
/* 126 */       int i1 = i * j;
/* 127 */       aint[i1 + 4] = p_transformVertexData_1_[l + 4];
/* 128 */       aint[i1 + 4 + 1] = p_transformVertexData_1_[l + 4 + 1];
/*     */       
/* 130 */       if (p_transformVertexData_3_) {
/*     */         
/* 132 */         i--;
/*     */         
/* 134 */         if (i < 0)
/*     */         {
/* 136 */           i = 3;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 141 */         i++;
/*     */         
/* 143 */         if (i > 3)
/*     */         {
/* 145 */           i = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFullSprite(BakedQuad p_isFullSprite_1_) {
/* 155 */     TextureAtlasSprite textureatlassprite = p_isFullSprite_1_.getSprite();
/* 156 */     float f = textureatlassprite.getMinU();
/* 157 */     float f1 = textureatlassprite.getMaxU();
/* 158 */     float f2 = f1 - f;
/* 159 */     float f3 = f2 / 256.0F;
/* 160 */     float f4 = textureatlassprite.getMinV();
/* 161 */     float f5 = textureatlassprite.getMaxV();
/* 162 */     float f6 = f5 - f4;
/* 163 */     float f7 = f6 / 256.0F;
/* 164 */     int[] aint = p_isFullSprite_1_.getVertexData();
/* 165 */     int i = aint.length / 4;
/*     */     
/* 167 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 169 */       int k = j * i;
/* 170 */       float f8 = Float.intBitsToFloat(aint[k + 4]);
/* 171 */       float f9 = Float.intBitsToFloat(aint[k + 4 + 1]);
/*     */       
/* 173 */       if (!equalsDelta(f8, f, f3) && !equalsDelta(f8, f1, f3))
/*     */       {
/* 175 */         return false;
/*     */       }
/*     */       
/* 178 */       if (!equalsDelta(f9, f4, f7) && !equalsDelta(f9, f5, f7))
/*     */       {
/* 180 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean equalsDelta(float p_equalsDelta_1_, float p_equalsDelta_2_, float p_equalsDelta_3_) {
/* 189 */     float f = MathHelper.abs(p_equalsDelta_1_ - p_equalsDelta_2_);
/* 190 */     return (f < p_equalsDelta_3_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\NaturalProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */