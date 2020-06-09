/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBox
/*     */ {
/*     */   private final PositionTextureVertex[] vertexPositions;
/*     */   private final TexturedQuad[] quadList;
/*     */   public final float posX1;
/*     */   public final float posY1;
/*     */   public final float posZ1;
/*     */   public final float posX2;
/*     */   public final float posY2;
/*     */   public final float posZ2;
/*     */   public String boxName;
/*     */   
/*     */   public ModelBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta) {
/*  36 */     this(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBox(ModelRenderer p_i0_1_, int[][] p_i0_2_, float p_i0_3_, float p_i0_4_, float p_i0_5_, float p_i0_6_, float p_i0_7_, float p_i0_8_, float p_i0_9_, boolean p_i0_10_) {
/*  41 */     this.posX1 = p_i0_3_;
/*  42 */     this.posY1 = p_i0_4_;
/*  43 */     this.posZ1 = p_i0_5_;
/*  44 */     this.posX2 = p_i0_3_ + p_i0_6_;
/*  45 */     this.posY2 = p_i0_4_ + p_i0_7_;
/*  46 */     this.posZ2 = p_i0_5_ + p_i0_8_;
/*  47 */     this.vertexPositions = new PositionTextureVertex[8];
/*  48 */     this.quadList = new TexturedQuad[6];
/*  49 */     float f = p_i0_3_ + p_i0_6_;
/*  50 */     float f1 = p_i0_4_ + p_i0_7_;
/*  51 */     float f2 = p_i0_5_ + p_i0_8_;
/*  52 */     p_i0_3_ -= p_i0_9_;
/*  53 */     p_i0_4_ -= p_i0_9_;
/*  54 */     p_i0_5_ -= p_i0_9_;
/*  55 */     f += p_i0_9_;
/*  56 */     f1 += p_i0_9_;
/*  57 */     f2 += p_i0_9_;
/*     */     
/*  59 */     if (p_i0_10_) {
/*     */       
/*  61 */       float f3 = f;
/*  62 */       f = p_i0_3_;
/*  63 */       p_i0_3_ = f3;
/*     */     } 
/*     */     
/*  66 */     PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(p_i0_3_, p_i0_4_, p_i0_5_, 0.0F, 0.0F);
/*  67 */     PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, p_i0_4_, p_i0_5_, 0.0F, 8.0F);
/*  68 */     PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, p_i0_5_, 8.0F, 8.0F);
/*  69 */     PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(p_i0_3_, f1, p_i0_5_, 8.0F, 0.0F);
/*  70 */     PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(p_i0_3_, p_i0_4_, f2, 0.0F, 0.0F);
/*  71 */     PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, p_i0_4_, f2, 0.0F, 8.0F);
/*  72 */     PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
/*  73 */     PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(p_i0_3_, f1, f2, 8.0F, 0.0F);
/*  74 */     this.vertexPositions[0] = positiontexturevertex7;
/*  75 */     this.vertexPositions[1] = positiontexturevertex;
/*  76 */     this.vertexPositions[2] = positiontexturevertex1;
/*  77 */     this.vertexPositions[3] = positiontexturevertex2;
/*  78 */     this.vertexPositions[4] = positiontexturevertex3;
/*  79 */     this.vertexPositions[5] = positiontexturevertex4;
/*  80 */     this.vertexPositions[6] = positiontexturevertex5;
/*  81 */     this.vertexPositions[7] = positiontexturevertex6;
/*  82 */     this.quadList[0] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 }, p_i0_2_[4], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  83 */     this.quadList[1] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 }, p_i0_2_[5], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  84 */     this.quadList[2] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex }, p_i0_2_[1], true, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  85 */     this.quadList[3] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 }, p_i0_2_[0], true, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  86 */     this.quadList[4] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, p_i0_2_[2], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*  87 */     this.quadList[5] = makeTexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 }, p_i0_2_[3], false, p_i0_1_.textureWidth, p_i0_1_.textureHeight);
/*     */     
/*  89 */     if (p_i0_10_) {
/*     */       byte b; int i; TexturedQuad[] arrayOfTexturedQuad;
/*  91 */       for (i = (arrayOfTexturedQuad = this.quadList).length, b = 0; b < i; ) { TexturedQuad texturedquad = arrayOfTexturedQuad[b];
/*     */         
/*  93 */         texturedquad.flipFace();
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   private TexturedQuad makeTexturedQuad(PositionTextureVertex[] p_makeTexturedQuad_1_, int[] p_makeTexturedQuad_2_, boolean p_makeTexturedQuad_3_, float p_makeTexturedQuad_4_, float p_makeTexturedQuad_5_) {
/* 100 */     if (p_makeTexturedQuad_2_ == null)
/*     */     {
/* 102 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 106 */     return p_makeTexturedQuad_3_ ? new TexturedQuad(p_makeTexturedQuad_1_, p_makeTexturedQuad_2_[2], p_makeTexturedQuad_2_[3], p_makeTexturedQuad_2_[0], p_makeTexturedQuad_2_[1], p_makeTexturedQuad_4_, p_makeTexturedQuad_5_) : new TexturedQuad(p_makeTexturedQuad_1_, p_makeTexturedQuad_2_[0], p_makeTexturedQuad_2_[1], p_makeTexturedQuad_2_[2], p_makeTexturedQuad_2_[3], p_makeTexturedQuad_4_, p_makeTexturedQuad_5_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror) {
/* 112 */     this.posX1 = x;
/* 113 */     this.posY1 = y;
/* 114 */     this.posZ1 = z;
/* 115 */     this.posX2 = x + dx;
/* 116 */     this.posY2 = y + dy;
/* 117 */     this.posZ2 = z + dz;
/* 118 */     this.vertexPositions = new PositionTextureVertex[8];
/* 119 */     this.quadList = new TexturedQuad[6];
/* 120 */     float f = x + dx;
/* 121 */     float f1 = y + dy;
/* 122 */     float f2 = z + dz;
/* 123 */     x -= delta;
/* 124 */     y -= delta;
/* 125 */     z -= delta;
/* 126 */     f += delta;
/* 127 */     f1 += delta;
/* 128 */     f2 += delta;
/*     */     
/* 130 */     if (mirror) {
/*     */       
/* 132 */       float f3 = f;
/* 133 */       f = x;
/* 134 */       x = f3;
/*     */     } 
/*     */     
/* 137 */     PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
/* 138 */     PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
/* 139 */     PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
/* 140 */     PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
/* 141 */     PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
/* 142 */     PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
/* 143 */     PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
/* 144 */     PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
/* 145 */     this.vertexPositions[0] = positiontexturevertex7;
/* 146 */     this.vertexPositions[1] = positiontexturevertex;
/* 147 */     this.vertexPositions[2] = positiontexturevertex1;
/* 148 */     this.vertexPositions[3] = positiontexturevertex2;
/* 149 */     this.vertexPositions[4] = positiontexturevertex3;
/* 150 */     this.vertexPositions[5] = positiontexturevertex4;
/* 151 */     this.vertexPositions[6] = positiontexturevertex5;
/* 152 */     this.vertexPositions[7] = positiontexturevertex6;
/* 153 */     this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 }, texU + dz + dx, texV + dz, texU + dz + dx + dz, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
/* 154 */     this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 }, texU, texV + dz, texU + dz, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
/* 155 */     this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex }, texU + dz, texV, texU + dz + dx, texV + dz, renderer.textureWidth, renderer.textureHeight);
/* 156 */     this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 }, texU + dz + dx, texV + dz, texU + dz + dx + dx, texV, renderer.textureWidth, renderer.textureHeight);
/* 157 */     this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 }, texU + dz, texV + dz, texU + dz + dx, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
/* 158 */     this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 }, texU + dz + dx + dz, texV + dz, texU + dz + dx + dz + dx, texV + dz + dy, renderer.textureWidth, renderer.textureHeight);
/*     */     
/* 160 */     if (mirror) {
/*     */       byte b; int i; TexturedQuad[] arrayOfTexturedQuad;
/* 162 */       for (i = (arrayOfTexturedQuad = this.quadList).length, b = 0; b < i; ) { TexturedQuad texturedquad = arrayOfTexturedQuad[b];
/*     */         
/* 164 */         texturedquad.flipFace();
/*     */         b++; }
/*     */     
/*     */     }  } public void render(BufferBuilder renderer, float scale) {
/*     */     byte b;
/*     */     int i;
/*     */     TexturedQuad[] arrayOfTexturedQuad;
/* 171 */     for (i = (arrayOfTexturedQuad = this.quadList).length, b = 0; b < i; ) { TexturedQuad texturedquad = arrayOfTexturedQuad[b];
/*     */       
/* 173 */       if (texturedquad != null)
/*     */       {
/* 175 */         texturedquad.draw(renderer, scale);
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public ModelBox setBoxName(String name) {
/* 182 */     this.boxName = name;
/* 183 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */