/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexConsumer;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexProducer;
/*     */ import optifine.Config;
/*     */ import optifine.QuadBounds;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BakedQuad
/*     */   implements IVertexProducer
/*     */ {
/*     */   protected int[] vertexData;
/*     */   protected final int tintIndex;
/*     */   protected EnumFacing face;
/*     */   protected TextureAtlasSprite sprite;
/*  24 */   private int[] vertexDataSingle = null;
/*  25 */   protected boolean applyDiffuseLighting = Reflector.ForgeHooksClient_fillNormal.exists();
/*  26 */   protected VertexFormat format = DefaultVertexFormats.ITEM;
/*     */   
/*     */   private QuadBounds quadBounds;
/*     */   
/*     */   public BakedQuad(int[] p_i6_1_, int p_i6_2_, EnumFacing p_i6_3_, TextureAtlasSprite p_i6_4_, boolean p_i6_5_, VertexFormat p_i6_6_) {
/*  31 */     this.vertexData = p_i6_1_;
/*  32 */     this.tintIndex = p_i6_2_;
/*  33 */     this.face = p_i6_3_;
/*  34 */     this.sprite = p_i6_4_;
/*  35 */     this.applyDiffuseLighting = p_i6_5_;
/*  36 */     this.format = p_i6_6_;
/*  37 */     fixVertexData();
/*     */   }
/*     */ 
/*     */   
/*     */   public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn, TextureAtlasSprite spriteIn) {
/*  42 */     this.vertexData = vertexDataIn;
/*  43 */     this.tintIndex = tintIndexIn;
/*  44 */     this.face = faceIn;
/*  45 */     this.sprite = spriteIn;
/*  46 */     fixVertexData();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getSprite() {
/*  51 */     if (this.sprite == null)
/*     */     {
/*  53 */       this.sprite = getSpriteByUv(getVertexData());
/*     */     }
/*     */     
/*  56 */     return this.sprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getVertexData() {
/*  61 */     fixVertexData();
/*  62 */     return this.vertexData;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTintIndex() {
/*  67 */     return (this.tintIndex != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTintIndex() {
/*  72 */     return this.tintIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumFacing getFace() {
/*  77 */     if (this.face == null)
/*     */     {
/*  79 */       this.face = FaceBakery.getFacingFromVertexData(getVertexData());
/*     */     }
/*     */     
/*  82 */     return this.face;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getVertexDataSingle() {
/*  87 */     if (this.vertexDataSingle == null)
/*     */     {
/*  89 */       this.vertexDataSingle = makeVertexDataSingle(getVertexData(), getSprite());
/*     */     }
/*     */     
/*  92 */     return this.vertexDataSingle;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] makeVertexDataSingle(int[] p_makeVertexDataSingle_0_, TextureAtlasSprite p_makeVertexDataSingle_1_) {
/*  97 */     int[] aint = (int[])p_makeVertexDataSingle_0_.clone();
/*  98 */     int i = p_makeVertexDataSingle_1_.sheetWidth / p_makeVertexDataSingle_1_.getIconWidth();
/*  99 */     int j = p_makeVertexDataSingle_1_.sheetHeight / p_makeVertexDataSingle_1_.getIconHeight();
/* 100 */     int k = aint.length / 4;
/*     */     
/* 102 */     for (int l = 0; l < 4; l++) {
/*     */       
/* 104 */       int i1 = l * k;
/* 105 */       float f = Float.intBitsToFloat(aint[i1 + 4]);
/* 106 */       float f1 = Float.intBitsToFloat(aint[i1 + 4 + 1]);
/* 107 */       float f2 = p_makeVertexDataSingle_1_.toSingleU(f);
/* 108 */       float f3 = p_makeVertexDataSingle_1_.toSingleV(f1);
/* 109 */       aint[i1 + 4] = Float.floatToRawIntBits(f2);
/* 110 */       aint[i1 + 4 + 1] = Float.floatToRawIntBits(f3);
/*     */     } 
/*     */     
/* 113 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pipe(IVertexConsumer p_pipe_1_) {
/* 118 */     Reflector.callVoid(Reflector.LightUtil_putBakedQuad, new Object[] { p_pipe_1_, this });
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormat getFormat() {
/* 123 */     return this.format;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldApplyDiffuseLighting() {
/* 128 */     return this.applyDiffuseLighting;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite getSpriteByUv(int[] p_getSpriteByUv_0_) {
/* 133 */     float f = 1.0F;
/* 134 */     float f1 = 1.0F;
/* 135 */     float f2 = 0.0F;
/* 136 */     float f3 = 0.0F;
/* 137 */     int i = p_getSpriteByUv_0_.length / 4;
/*     */     
/* 139 */     for (int j = 0; j < 4; j++) {
/*     */       
/* 141 */       int k = j * i;
/* 142 */       float f4 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4]);
/* 143 */       float f5 = Float.intBitsToFloat(p_getSpriteByUv_0_[k + 4 + 1]);
/* 144 */       f = Math.min(f, f4);
/* 145 */       f1 = Math.min(f1, f5);
/* 146 */       f2 = Math.max(f2, f4);
/* 147 */       f3 = Math.max(f3, f5);
/*     */     } 
/*     */     
/* 150 */     float f6 = (f + f2) / 2.0F;
/* 151 */     float f7 = (f1 + f3) / 2.0F;
/* 152 */     TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
/* 153 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fixVertexData() {
/* 158 */     if (Config.isShaders()) {
/*     */       
/* 160 */       if (this.vertexData.length == 28)
/*     */       {
/* 162 */         this.vertexData = expandVertexData(this.vertexData);
/*     */       }
/*     */     }
/* 165 */     else if (this.vertexData.length == 56) {
/*     */       
/* 167 */       this.vertexData = compactVertexData(this.vertexData);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] expandVertexData(int[] p_expandVertexData_0_) {
/* 173 */     int i = p_expandVertexData_0_.length / 4;
/* 174 */     int j = i * 2;
/* 175 */     int[] aint = new int[j * 4];
/*     */     
/* 177 */     for (int k = 0; k < 4; k++)
/*     */     {
/* 179 */       System.arraycopy(p_expandVertexData_0_, k * i, aint, k * j, i);
/*     */     }
/*     */     
/* 182 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] compactVertexData(int[] p_compactVertexData_0_) {
/* 187 */     int i = p_compactVertexData_0_.length / 4;
/* 188 */     int j = i / 2;
/* 189 */     int[] aint = new int[j * 4];
/*     */     
/* 191 */     for (int k = 0; k < 4; k++)
/*     */     {
/* 193 */       System.arraycopy(p_compactVertexData_0_, k * i, aint, k * j, j);
/*     */     }
/*     */     
/* 196 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public QuadBounds getQuadBounds() {
/* 201 */     if (this.quadBounds == null)
/*     */     {
/* 203 */       this.quadBounds = new QuadBounds(getVertexData());
/*     */     }
/*     */     
/* 206 */     return this.quadBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMidX() {
/* 211 */     QuadBounds quadbounds = getQuadBounds();
/* 212 */     return (quadbounds.getMaxX() + quadbounds.getMinX()) / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMidY() {
/* 217 */     QuadBounds quadbounds = getQuadBounds();
/* 218 */     return ((quadbounds.getMaxY() + quadbounds.getMinY()) / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMidZ() {
/* 223 */     QuadBounds quadbounds = getQuadBounds();
/* 224 */     return ((quadbounds.getMaxZ() + quadbounds.getMinZ()) / 2.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFaceQuad() {
/* 229 */     QuadBounds quadbounds = getQuadBounds();
/* 230 */     return quadbounds.isFaceQuad(this.face);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullQuad() {
/* 235 */     QuadBounds quadbounds = getQuadBounds();
/* 236 */     return quadbounds.isFullQuad(this.face);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullFaceQuad() {
/* 241 */     return (isFullQuad() && isFaceQuad());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 246 */     return "vertex: " + (this.vertexData.length / 7) + ", tint: " + this.tintIndex + ", facing: " + this.face + ", sprite: " + this.sprite;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\BakedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */