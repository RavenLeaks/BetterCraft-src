/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.primitives.Floats;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.nio.ShortBuffer;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Comparator;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import optifine.Config;
/*      */ import optifine.RenderEnv;
/*      */ import optifine.TextureUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import shadersmod.client.SVertexBuilder;
/*      */ 
/*      */ 
/*      */ public class BufferBuilder
/*      */ {
/*   31 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */   
/*      */   private ByteBuffer byteBuffer;
/*      */   
/*      */   public IntBuffer rawIntBuffer;
/*      */   private ShortBuffer rawShortBuffer;
/*      */   public FloatBuffer rawFloatBuffer;
/*      */   public int vertexCount;
/*      */   private VertexFormatElement vertexFormatElement;
/*      */   private int vertexFormatIndex;
/*      */   private boolean noColor;
/*      */   public int drawMode;
/*      */   private double xOffset;
/*      */   private double yOffset;
/*      */   private double zOffset;
/*      */   private VertexFormat vertexFormat;
/*      */   private boolean isDrawing;
/*   48 */   private BlockRenderLayer blockLayer = null;
/*   49 */   private boolean[] drawnIcons = new boolean[256];
/*   50 */   private TextureAtlasSprite[] quadSprites = null;
/*   51 */   private TextureAtlasSprite[] quadSpritesPrev = null;
/*   52 */   private TextureAtlasSprite quadSprite = null;
/*      */   public SVertexBuilder sVertexBuilder;
/*   54 */   public RenderEnv renderEnv = null;
/*      */ 
/*      */   
/*      */   public BufferBuilder(int bufferSizeIn) {
/*   58 */     if (Config.isShaders())
/*      */     {
/*   60 */       bufferSizeIn *= 2;
/*      */     }
/*      */     
/*   63 */     this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
/*   64 */     this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*   65 */     this.rawShortBuffer = this.byteBuffer.asShortBuffer();
/*   66 */     this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*   67 */     SVertexBuilder.initVertexBuilder(this);
/*      */   }
/*      */ 
/*      */   
/*      */   private void growBuffer(int p_181670_1_) {
/*   72 */     if (Config.isShaders())
/*      */     {
/*   74 */       p_181670_1_ *= 2;
/*      */     }
/*      */     
/*   77 */     if (MathHelper.roundUp(p_181670_1_, 4) / 4 > this.rawIntBuffer.remaining() || this.vertexCount * this.vertexFormat.getNextOffset() + p_181670_1_ > this.byteBuffer.capacity()) {
/*      */       
/*   79 */       int i = this.byteBuffer.capacity();
/*   80 */       int j = i + MathHelper.roundUp(p_181670_1_, 2097152);
/*   81 */       LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", Integer.valueOf(i), Integer.valueOf(j));
/*   82 */       int k = this.rawIntBuffer.position();
/*   83 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(j);
/*   84 */       this.byteBuffer.position(0);
/*   85 */       bytebuffer.put(this.byteBuffer);
/*   86 */       bytebuffer.rewind();
/*   87 */       this.byteBuffer = bytebuffer;
/*   88 */       this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
/*   89 */       this.rawIntBuffer = this.byteBuffer.asIntBuffer();
/*   90 */       this.rawIntBuffer.position(k);
/*   91 */       this.rawShortBuffer = this.byteBuffer.asShortBuffer();
/*   92 */       this.rawShortBuffer.position(k << 1);
/*      */       
/*   94 */       if (this.quadSprites != null) {
/*      */         
/*   96 */         TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
/*   97 */         int l = getBufferQuadSize();
/*   98 */         this.quadSprites = new TextureAtlasSprite[l];
/*   99 */         System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
/*  100 */         this.quadSpritesPrev = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sortVertexData(float p_181674_1_, float p_181674_2_, float p_181674_3_) {
/*  107 */     int i = this.vertexCount / 4;
/*  108 */     final float[] afloat = new float[i];
/*      */     
/*  110 */     for (int j = 0; j < i; j++)
/*      */     {
/*  112 */       afloat[j] = getDistanceSq(this.rawFloatBuffer, (float)(p_181674_1_ + this.xOffset), (float)(p_181674_2_ + this.yOffset), (float)(p_181674_3_ + this.zOffset), this.vertexFormat.getIntegerSize(), j * this.vertexFormat.getNextOffset());
/*      */     }
/*      */     
/*  115 */     Integer[] ainteger = new Integer[i];
/*      */     
/*  117 */     for (int k = 0; k < ainteger.length; k++)
/*      */     {
/*  119 */       ainteger[k] = Integer.valueOf(k);
/*      */     }
/*      */     
/*  122 */     Arrays.sort(ainteger, new Comparator<Integer>()
/*      */         {
/*      */           public int compare(Integer p_compare_1_, Integer p_compare_2_)
/*      */           {
/*  126 */             return Floats.compare(afloat[p_compare_2_.intValue()], afloat[p_compare_1_.intValue()]);
/*      */           }
/*      */         });
/*  129 */     BitSet bitset = new BitSet();
/*  130 */     int l = this.vertexFormat.getNextOffset();
/*  131 */     int[] aint = new int[l];
/*      */     
/*  133 */     for (int i1 = bitset.nextClearBit(0); i1 < ainteger.length; i1 = bitset.nextClearBit(i1 + 1)) {
/*      */       
/*  135 */       int j1 = ainteger[i1].intValue();
/*      */       
/*  137 */       if (j1 != i1) {
/*      */         
/*  139 */         this.rawIntBuffer.limit(j1 * l + l);
/*  140 */         this.rawIntBuffer.position(j1 * l);
/*  141 */         this.rawIntBuffer.get(aint);
/*  142 */         int k1 = j1;
/*      */         
/*  144 */         for (int l1 = ainteger[j1].intValue(); k1 != i1; l1 = ainteger[l1].intValue()) {
/*      */           
/*  146 */           this.rawIntBuffer.limit(l1 * l + l);
/*  147 */           this.rawIntBuffer.position(l1 * l);
/*  148 */           IntBuffer intbuffer = this.rawIntBuffer.slice();
/*  149 */           this.rawIntBuffer.limit(k1 * l + l);
/*  150 */           this.rawIntBuffer.position(k1 * l);
/*  151 */           this.rawIntBuffer.put(intbuffer);
/*  152 */           bitset.set(k1);
/*  153 */           k1 = l1;
/*      */         } 
/*      */         
/*  156 */         this.rawIntBuffer.limit(i1 * l + l);
/*  157 */         this.rawIntBuffer.position(i1 * l);
/*  158 */         this.rawIntBuffer.put(aint);
/*      */       } 
/*      */       
/*  161 */       bitset.set(i1);
/*      */     } 
/*      */     
/*  164 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/*  165 */     this.rawIntBuffer.position(getBufferSize());
/*      */     
/*  167 */     if (this.quadSprites != null) {
/*      */       
/*  169 */       TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];
/*  170 */       int i2 = this.vertexFormat.getNextOffset() / 4 * 4;
/*      */       
/*  172 */       for (int j2 = 0; j2 < ainteger.length; j2++) {
/*      */         
/*  174 */         int k2 = ainteger[j2].intValue();
/*  175 */         atextureatlassprite[j2] = this.quadSprites[k2];
/*      */       } 
/*      */       
/*  178 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public State getVertexState() {
/*  184 */     this.rawIntBuffer.rewind();
/*  185 */     int i = getBufferSize();
/*  186 */     this.rawIntBuffer.limit(i);
/*  187 */     int[] aint = new int[i];
/*  188 */     this.rawIntBuffer.get(aint);
/*  189 */     this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
/*  190 */     this.rawIntBuffer.position(i);
/*  191 */     TextureAtlasSprite[] atextureatlassprite = null;
/*      */     
/*  193 */     if (this.quadSprites != null) {
/*      */       
/*  195 */       int j = this.vertexCount / 4;
/*  196 */       atextureatlassprite = new TextureAtlasSprite[j];
/*  197 */       System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
/*      */     } 
/*      */     
/*  200 */     return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBufferSize() {
/*  205 */     return this.vertexCount * this.vertexFormat.getIntegerSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private static float getDistanceSq(FloatBuffer p_181665_0_, float p_181665_1_, float p_181665_2_, float p_181665_3_, int p_181665_4_, int p_181665_5_) {
/*  210 */     float f = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 0);
/*  211 */     float f1 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 1);
/*  212 */     float f2 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 0 + 2);
/*  213 */     float f3 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 0);
/*  214 */     float f4 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 1);
/*  215 */     float f5 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 1 + 2);
/*  216 */     float f6 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 0);
/*  217 */     float f7 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 1);
/*  218 */     float f8 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 2 + 2);
/*  219 */     float f9 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 0);
/*  220 */     float f10 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 1);
/*  221 */     float f11 = p_181665_0_.get(p_181665_5_ + p_181665_4_ * 3 + 2);
/*  222 */     float f12 = (f + f3 + f6 + f9) * 0.25F - p_181665_1_;
/*  223 */     float f13 = (f1 + f4 + f7 + f10) * 0.25F - p_181665_2_;
/*  224 */     float f14 = (f2 + f5 + f8 + f11) * 0.25F - p_181665_3_;
/*  225 */     return f12 * f12 + f13 * f13 + f14 * f14;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVertexState(State state) {
/*  230 */     this.rawIntBuffer.clear();
/*  231 */     growBuffer((state.getRawBuffer()).length * 4);
/*  232 */     this.rawIntBuffer.put(state.getRawBuffer());
/*  233 */     this.vertexCount = state.getVertexCount();
/*  234 */     this.vertexFormat = new VertexFormat(state.getVertexFormat());
/*      */     
/*  236 */     if (state.stateQuadSprites != null) {
/*      */       
/*  238 */       if (this.quadSprites == null)
/*      */       {
/*  240 */         this.quadSprites = this.quadSpritesPrev;
/*      */       }
/*      */       
/*  243 */       if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize())
/*      */       {
/*  245 */         this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*      */       }
/*      */       
/*  248 */       TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
/*  249 */       System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
/*      */     }
/*      */     else {
/*      */       
/*  253 */       if (this.quadSprites != null)
/*      */       {
/*  255 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  258 */       this.quadSprites = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/*  264 */     this.vertexCount = 0;
/*  265 */     this.vertexFormatElement = null;
/*  266 */     this.vertexFormatIndex = 0;
/*  267 */     this.quadSprite = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void begin(int glMode, VertexFormat format) {
/*  272 */     if (this.isDrawing)
/*      */     {
/*  274 */       throw new IllegalStateException("Already building!");
/*      */     }
/*      */ 
/*      */     
/*  278 */     this.isDrawing = true;
/*  279 */     reset();
/*  280 */     this.drawMode = glMode;
/*  281 */     this.vertexFormat = format;
/*  282 */     this.vertexFormatElement = format.getElement(this.vertexFormatIndex);
/*  283 */     this.noColor = false;
/*  284 */     this.byteBuffer.limit(this.byteBuffer.capacity());
/*      */     
/*  286 */     if (Config.isShaders())
/*      */     {
/*  288 */       SVertexBuilder.endSetVertexFormat(this);
/*      */     }
/*      */     
/*  291 */     if (Config.isMultiTexture()) {
/*      */       
/*  293 */       if (this.blockLayer != null)
/*      */       {
/*  295 */         if (this.quadSprites == null)
/*      */         {
/*  297 */           this.quadSprites = this.quadSpritesPrev;
/*      */         }
/*      */         
/*  300 */         if (this.quadSprites == null || this.quadSprites.length < getBufferQuadSize())
/*      */         {
/*  302 */           this.quadSprites = new TextureAtlasSprite[getBufferQuadSize()];
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  308 */       if (this.quadSprites != null)
/*      */       {
/*  310 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  313 */       this.quadSprites = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BufferBuilder tex(double u, double v) {
/*  320 */     if (this.quadSprite != null && this.quadSprites != null) {
/*      */       
/*  322 */       u = this.quadSprite.toSingleU((float)u);
/*  323 */       v = this.quadSprite.toSingleV((float)v);
/*  324 */       this.quadSprites[this.vertexCount / 4] = this.quadSprite;
/*      */     } 
/*      */     
/*  327 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  329 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  332 */         this.byteBuffer.putFloat(i, (float)u);
/*  333 */         this.byteBuffer.putFloat(i + 4, (float)v);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  338 */         this.byteBuffer.putInt(i, (int)u);
/*  339 */         this.byteBuffer.putInt(i + 4, (int)v);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  344 */         this.byteBuffer.putShort(i, (short)(int)v);
/*  345 */         this.byteBuffer.putShort(i + 2, (short)(int)u);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case null:
/*  350 */         this.byteBuffer.put(i, (byte)(int)v);
/*  351 */         this.byteBuffer.put(i + 1, (byte)(int)u);
/*      */         break;
/*      */     } 
/*  354 */     nextVertexFormatIndex();
/*  355 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferBuilder lightmap(int p_187314_1_, int p_187314_2_) {
/*  360 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  362 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  365 */         this.byteBuffer.putFloat(i, p_187314_1_);
/*  366 */         this.byteBuffer.putFloat(i + 4, p_187314_2_);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  371 */         this.byteBuffer.putInt(i, p_187314_1_);
/*  372 */         this.byteBuffer.putInt(i + 4, p_187314_2_);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  377 */         this.byteBuffer.putShort(i, (short)p_187314_2_);
/*  378 */         this.byteBuffer.putShort(i + 2, (short)p_187314_1_);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case null:
/*  383 */         this.byteBuffer.put(i, (byte)p_187314_2_);
/*  384 */         this.byteBuffer.put(i + 1, (byte)p_187314_1_);
/*      */         break;
/*      */     } 
/*  387 */     nextVertexFormatIndex();
/*  388 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
/*  393 */     int i = (this.vertexCount - 4) * this.vertexFormat.getIntegerSize() + this.vertexFormat.getUvOffsetById(1) / 4;
/*  394 */     int j = this.vertexFormat.getNextOffset() >> 2;
/*  395 */     this.rawIntBuffer.put(i, p_178962_1_);
/*  396 */     this.rawIntBuffer.put(i + j, p_178962_2_);
/*  397 */     this.rawIntBuffer.put(i + j * 2, p_178962_3_);
/*  398 */     this.rawIntBuffer.put(i + j * 3, p_178962_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putPosition(double x, double y, double z) {
/*  403 */     int i = this.vertexFormat.getIntegerSize();
/*  404 */     int j = (this.vertexCount - 4) * i;
/*      */     
/*  406 */     for (int k = 0; k < 4; k++) {
/*      */       
/*  408 */       int l = j + k * i;
/*  409 */       int i1 = l + 1;
/*  410 */       int j1 = i1 + 1;
/*  411 */       this.rawIntBuffer.put(l, Float.floatToRawIntBits((float)(x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
/*  412 */       this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float)(y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
/*  413 */       this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float)(z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColorIndex(int vertexIndex) {
/*  425 */     return ((this.vertexCount - vertexIndex) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putColorMultiplier(float red, float green, float blue, int vertexIndex) {
/*  435 */     int i = getColorIndex(vertexIndex);
/*  436 */     int j = -1;
/*      */     
/*  438 */     if (!this.noColor) {
/*      */       
/*  440 */       j = this.rawIntBuffer.get(i);
/*      */       
/*  442 */       if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */         
/*  444 */         int k = (int)((j & 0xFF) * red);
/*  445 */         int l = (int)((j >> 8 & 0xFF) * green);
/*  446 */         int i1 = (int)((j >> 16 & 0xFF) * blue);
/*  447 */         j &= 0xFF000000;
/*  448 */         j = j | i1 << 16 | l << 8 | k;
/*      */       }
/*      */       else {
/*      */         
/*  452 */         int j1 = (int)((j >> 24 & 0xFF) * red);
/*  453 */         int k1 = (int)((j >> 16 & 0xFF) * green);
/*  454 */         int l1 = (int)((j >> 8 & 0xFF) * blue);
/*  455 */         j &= 0xFF;
/*  456 */         j = j | j1 << 24 | k1 << 16 | l1 << 8;
/*      */       } 
/*      */     } 
/*      */     
/*  460 */     this.rawIntBuffer.put(i, j);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_192836_a(int p_192836_1_, int p_192836_2_) {
/*  465 */     int i = getColorIndex(p_192836_2_);
/*  466 */     int j = p_192836_1_ >> 16 & 0xFF;
/*  467 */     int k = p_192836_1_ >> 8 & 0xFF;
/*  468 */     int l = p_192836_1_ & 0xFF;
/*  469 */     putColorRGBA(i, j, k, l);
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGB_F(float red, float green, float blue, int vertexIndex) {
/*  474 */     int i = getColorIndex(vertexIndex);
/*  475 */     int j = MathHelper.clamp((int)(red * 255.0F), 0, 255);
/*  476 */     int k = MathHelper.clamp((int)(green * 255.0F), 0, 255);
/*  477 */     int l = MathHelper.clamp((int)(blue * 255.0F), 0, 255);
/*  478 */     putColorRGBA(i, j, k, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void putColorRGBA(int index, int red, int green, int blue) {
/*  489 */     if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */       
/*  491 */       this.rawIntBuffer.put(index, 0xFF000000 | blue << 16 | green << 8 | red);
/*      */     }
/*      */     else {
/*      */       
/*  495 */       this.rawIntBuffer.put(index, red << 24 | green << 16 | blue << 8 | 0xFF);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void noColor() {
/*  504 */     this.noColor = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferBuilder color(float red, float green, float blue, float alpha) {
/*  509 */     return color((int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), (int)(alpha * 255.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferBuilder color(int red, int green, int blue, int alpha) {
/*  514 */     if (this.noColor)
/*      */     {
/*  516 */       return this;
/*      */     }
/*      */ 
/*      */     
/*  520 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  522 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  525 */         this.byteBuffer.putFloat(i, red / 255.0F);
/*  526 */         this.byteBuffer.putFloat(i + 4, green / 255.0F);
/*  527 */         this.byteBuffer.putFloat(i + 8, blue / 255.0F);
/*  528 */         this.byteBuffer.putFloat(i + 12, alpha / 255.0F);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  533 */         this.byteBuffer.putFloat(i, red);
/*  534 */         this.byteBuffer.putFloat(i + 4, green);
/*  535 */         this.byteBuffer.putFloat(i + 8, blue);
/*  536 */         this.byteBuffer.putFloat(i + 12, alpha);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  541 */         this.byteBuffer.putShort(i, (short)red);
/*  542 */         this.byteBuffer.putShort(i + 2, (short)green);
/*  543 */         this.byteBuffer.putShort(i + 4, (short)blue);
/*  544 */         this.byteBuffer.putShort(i + 6, (short)alpha);
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case null:
/*  549 */         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */           
/*  551 */           this.byteBuffer.put(i, (byte)red);
/*  552 */           this.byteBuffer.put(i + 1, (byte)green);
/*  553 */           this.byteBuffer.put(i + 2, (byte)blue);
/*  554 */           this.byteBuffer.put(i + 3, (byte)alpha);
/*      */           
/*      */           break;
/*      */         } 
/*  558 */         this.byteBuffer.put(i, (byte)alpha);
/*  559 */         this.byteBuffer.put(i + 1, (byte)blue);
/*  560 */         this.byteBuffer.put(i + 2, (byte)green);
/*  561 */         this.byteBuffer.put(i + 3, (byte)red);
/*      */         break;
/*      */     } 
/*      */     
/*  565 */     nextVertexFormatIndex();
/*  566 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVertexData(int[] vertexData) {
/*  572 */     if (Config.isShaders())
/*      */     {
/*  574 */       SVertexBuilder.beginAddVertexData(this, vertexData);
/*      */     }
/*      */     
/*  577 */     growBuffer(vertexData.length * 4);
/*  578 */     this.rawIntBuffer.position(getBufferSize());
/*  579 */     this.rawIntBuffer.put(vertexData);
/*  580 */     this.vertexCount += vertexData.length / this.vertexFormat.getIntegerSize();
/*      */     
/*  582 */     if (Config.isShaders())
/*      */     {
/*  584 */       SVertexBuilder.endAddVertexData(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void endVertex() {
/*  590 */     this.vertexCount++;
/*  591 */     growBuffer(this.vertexFormat.getNextOffset());
/*  592 */     this.vertexFormatIndex = 0;
/*  593 */     this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
/*      */     
/*  595 */     if (Config.isShaders())
/*      */     {
/*  597 */       SVertexBuilder.endAddVertex(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferBuilder pos(double x, double y, double z) {
/*  603 */     if (Config.isShaders())
/*      */     {
/*  605 */       SVertexBuilder.beginAddVertex(this);
/*      */     }
/*      */     
/*  608 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  610 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  613 */         this.byteBuffer.putFloat(i, (float)(x + this.xOffset));
/*  614 */         this.byteBuffer.putFloat(i + 4, (float)(y + this.yOffset));
/*  615 */         this.byteBuffer.putFloat(i + 8, (float)(z + this.zOffset));
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  620 */         this.byteBuffer.putInt(i, Float.floatToRawIntBits((float)(x + this.xOffset)));
/*  621 */         this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float)(y + this.yOffset)));
/*  622 */         this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float)(z + this.zOffset)));
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  627 */         this.byteBuffer.putShort(i, (short)(int)(x + this.xOffset));
/*  628 */         this.byteBuffer.putShort(i + 2, (short)(int)(y + this.yOffset));
/*  629 */         this.byteBuffer.putShort(i + 4, (short)(int)(z + this.zOffset));
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case null:
/*  634 */         this.byteBuffer.put(i, (byte)(int)(x + this.xOffset));
/*  635 */         this.byteBuffer.put(i + 1, (byte)(int)(y + this.yOffset));
/*  636 */         this.byteBuffer.put(i + 2, (byte)(int)(z + this.zOffset));
/*      */         break;
/*      */     } 
/*  639 */     nextVertexFormatIndex();
/*  640 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putNormal(float x, float y, float z) {
/*  645 */     int i = (byte)(int)(x * 127.0F) & 0xFF;
/*  646 */     int j = (byte)(int)(y * 127.0F) & 0xFF;
/*  647 */     int k = (byte)(int)(z * 127.0F) & 0xFF;
/*  648 */     int l = i | j << 8 | k << 16;
/*  649 */     int i1 = this.vertexFormat.getNextOffset() >> 2;
/*  650 */     int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
/*  651 */     this.rawIntBuffer.put(j1, l);
/*  652 */     this.rawIntBuffer.put(j1 + i1, l);
/*  653 */     this.rawIntBuffer.put(j1 + i1 * 2, l);
/*  654 */     this.rawIntBuffer.put(j1 + i1 * 3, l);
/*      */   }
/*      */ 
/*      */   
/*      */   private void nextVertexFormatIndex() {
/*  659 */     this.vertexFormatIndex++;
/*  660 */     this.vertexFormatIndex %= this.vertexFormat.getElementCount();
/*  661 */     this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);
/*      */     
/*  663 */     if (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING)
/*      */     {
/*  665 */       nextVertexFormatIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BufferBuilder normal(float x, float y, float z) {
/*  671 */     int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.getOffset(this.vertexFormatIndex);
/*      */     
/*  673 */     switch (this.vertexFormatElement.getType()) {
/*      */       
/*      */       case FLOAT:
/*  676 */         this.byteBuffer.putFloat(i, x);
/*  677 */         this.byteBuffer.putFloat(i + 4, y);
/*  678 */         this.byteBuffer.putFloat(i + 8, z);
/*      */         break;
/*      */       
/*      */       case UINT:
/*      */       case INT:
/*  683 */         this.byteBuffer.putInt(i, (int)x);
/*  684 */         this.byteBuffer.putInt(i + 4, (int)y);
/*  685 */         this.byteBuffer.putInt(i + 8, (int)z);
/*      */         break;
/*      */       
/*      */       case USHORT:
/*      */       case SHORT:
/*  690 */         this.byteBuffer.putShort(i, (short)((int)(x * 32767.0F) & 0xFFFF));
/*  691 */         this.byteBuffer.putShort(i + 2, (short)((int)(y * 32767.0F) & 0xFFFF));
/*  692 */         this.byteBuffer.putShort(i + 4, (short)((int)(z * 32767.0F) & 0xFFFF));
/*      */         break;
/*      */       
/*      */       case UBYTE:
/*      */       case null:
/*  697 */         this.byteBuffer.put(i, (byte)((int)(x * 127.0F) & 0xFF));
/*  698 */         this.byteBuffer.put(i + 1, (byte)((int)(y * 127.0F) & 0xFF));
/*  699 */         this.byteBuffer.put(i + 2, (byte)((int)(z * 127.0F) & 0xFF));
/*      */         break;
/*      */     } 
/*  702 */     nextVertexFormatIndex();
/*  703 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTranslation(double x, double y, double z) {
/*  708 */     this.xOffset = x;
/*  709 */     this.yOffset = y;
/*  710 */     this.zOffset = z;
/*      */   }
/*      */ 
/*      */   
/*      */   public void finishDrawing() {
/*  715 */     if (!this.isDrawing)
/*      */     {
/*  717 */       throw new IllegalStateException("Not building!");
/*      */     }
/*      */ 
/*      */     
/*  721 */     this.isDrawing = false;
/*  722 */     this.byteBuffer.position(0);
/*  723 */     this.byteBuffer.limit(getBufferSize() * 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuffer getByteBuffer() {
/*  729 */     return this.byteBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public VertexFormat getVertexFormat() {
/*  734 */     return this.vertexFormat;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVertexCount() {
/*  739 */     return this.vertexCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDrawMode() {
/*  744 */     return this.drawMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColor4(int argb) {
/*  749 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  751 */       func_192836_a(argb, i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGB_F4(float red, float green, float blue) {
/*  757 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  759 */       putColorRGB_F(red, green, blue, i + 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void putSprite(TextureAtlasSprite p_putSprite_1_) {
/*  765 */     if (this.quadSprites != null) {
/*      */       
/*  767 */       int i = this.vertexCount / 4;
/*  768 */       this.quadSprites[i - 1] = p_putSprite_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSprite(TextureAtlasSprite p_setSprite_1_) {
/*  774 */     if (this.quadSprites != null)
/*      */     {
/*  776 */       this.quadSprite = p_setSprite_1_;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMultiTexture() {
/*  782 */     return (this.quadSprites != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawMultiTexture() {
/*  787 */     if (this.quadSprites != null) {
/*      */       
/*  789 */       int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
/*      */       
/*  791 */       if (this.drawnIcons.length <= i)
/*      */       {
/*  793 */         this.drawnIcons = new boolean[i + 1];
/*      */       }
/*      */       
/*  796 */       Arrays.fill(this.drawnIcons, false);
/*  797 */       int j = 0;
/*  798 */       int k = -1;
/*  799 */       int l = this.vertexCount / 4;
/*      */       
/*  801 */       for (int i1 = 0; i1 < l; i1++) {
/*      */         
/*  803 */         TextureAtlasSprite textureatlassprite = this.quadSprites[i1];
/*      */         
/*  805 */         if (textureatlassprite != null) {
/*      */           
/*  807 */           int j1 = textureatlassprite.getIndexInMap();
/*      */           
/*  809 */           if (!this.drawnIcons[j1])
/*      */           {
/*  811 */             if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
/*      */               
/*  813 */               if (k < 0)
/*      */               {
/*  815 */                 k = i1;
/*      */               }
/*      */             }
/*      */             else {
/*      */               
/*  820 */               i1 = drawForIcon(textureatlassprite, i1) - 1;
/*  821 */               j++;
/*      */               
/*  823 */               if (this.blockLayer != BlockRenderLayer.TRANSLUCENT)
/*      */               {
/*  825 */                 this.drawnIcons[j1] = true;
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  832 */       if (k >= 0) {
/*      */         
/*  834 */         drawForIcon(TextureUtils.iconGrassSideOverlay, k);
/*  835 */         j++;
/*      */       } 
/*      */       
/*  838 */       if (j > 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
/*  847 */     GL11.glBindTexture(3553, p_drawForIcon_1_.glSpriteTextureId);
/*  848 */     int i = -1;
/*  849 */     int j = -1;
/*  850 */     int k = this.vertexCount / 4;
/*      */     
/*  852 */     for (int l = p_drawForIcon_2_; l < k; l++) {
/*      */       
/*  854 */       TextureAtlasSprite textureatlassprite = this.quadSprites[l];
/*      */       
/*  856 */       if (textureatlassprite == p_drawForIcon_1_) {
/*      */         
/*  858 */         if (j < 0)
/*      */         {
/*  860 */           j = l;
/*      */         }
/*      */       }
/*  863 */       else if (j >= 0) {
/*      */         
/*  865 */         draw(j, l);
/*      */         
/*  867 */         if (this.blockLayer == BlockRenderLayer.TRANSLUCENT)
/*      */         {
/*  869 */           return l;
/*      */         }
/*      */         
/*  872 */         j = -1;
/*      */         
/*  874 */         if (i < 0)
/*      */         {
/*  876 */           i = l;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  881 */     if (j >= 0)
/*      */     {
/*  883 */       draw(j, k);
/*      */     }
/*      */     
/*  886 */     if (i < 0)
/*      */     {
/*  888 */       i = k;
/*      */     }
/*      */     
/*  891 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private void draw(int p_draw_1_, int p_draw_2_) {
/*  896 */     int i = p_draw_2_ - p_draw_1_;
/*      */     
/*  898 */     if (i > 0) {
/*      */       
/*  900 */       int j = p_draw_1_ * 4;
/*  901 */       int k = i * 4;
/*  902 */       GL11.glDrawArrays(this.drawMode, j, k);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBlockLayer(BlockRenderLayer p_setBlockLayer_1_) {
/*  908 */     this.blockLayer = p_setBlockLayer_1_;
/*      */     
/*  910 */     if (p_setBlockLayer_1_ == null) {
/*      */       
/*  912 */       if (this.quadSprites != null)
/*      */       {
/*  914 */         this.quadSpritesPrev = this.quadSprites;
/*      */       }
/*      */       
/*  917 */       this.quadSprites = null;
/*  918 */       this.quadSprite = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBufferQuadSize() {
/*  924 */     int i = this.rawIntBuffer.capacity() * 4 / this.vertexFormat.getIntegerSize() * 4;
/*  925 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderEnv getRenderEnv(IBlockAccess p_getRenderEnv_1_, IBlockState p_getRenderEnv_2_, BlockPos p_getRenderEnv_3_) {
/*  930 */     if (this.renderEnv == null) {
/*      */       
/*  932 */       this.renderEnv = new RenderEnv(p_getRenderEnv_1_, p_getRenderEnv_2_, p_getRenderEnv_3_);
/*  933 */       return this.renderEnv;
/*      */     } 
/*      */ 
/*      */     
/*  937 */     this.renderEnv.reset(p_getRenderEnv_1_, p_getRenderEnv_2_, p_getRenderEnv_3_);
/*  938 */     return this.renderEnv;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDrawing() {
/*  944 */     return this.isDrawing;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getXOffset() {
/*  949 */     return this.xOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/*  954 */     return this.yOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getZOffset() {
/*  959 */     return this.zOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putColorRGBA(int p_putColorRGBA_1_, int p_putColorRGBA_2_, int p_putColorRGBA_3_, int p_putColorRGBA_4_, int p_putColorRGBA_5_) {
/*  964 */     if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
/*      */       
/*  966 */       this.rawIntBuffer.put(p_putColorRGBA_1_, p_putColorRGBA_5_ << 24 | p_putColorRGBA_4_ << 16 | p_putColorRGBA_3_ << 8 | p_putColorRGBA_2_);
/*      */     }
/*      */     else {
/*      */       
/*  970 */       this.rawIntBuffer.put(p_putColorRGBA_1_, p_putColorRGBA_2_ << 24 | p_putColorRGBA_3_ << 16 | p_putColorRGBA_4_ << 8 | p_putColorRGBA_5_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isColorDisabled() {
/*  976 */     return this.noColor;
/*      */   }
/*      */ 
/*      */   
/*      */   public class State
/*      */   {
/*      */     private final int[] stateRawBuffer;
/*      */     private final VertexFormat stateVertexFormat;
/*      */     private TextureAtlasSprite[] stateQuadSprites;
/*      */     
/*      */     public State(int[] p_i5_2_, VertexFormat p_i5_3_, TextureAtlasSprite[] p_i5_4_) {
/*  987 */       this.stateRawBuffer = p_i5_2_;
/*  988 */       this.stateVertexFormat = p_i5_3_;
/*  989 */       this.stateQuadSprites = p_i5_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public State(int[] buffer, VertexFormat format) {
/*  994 */       this.stateRawBuffer = buffer;
/*  995 */       this.stateVertexFormat = format;
/*      */     }
/*      */ 
/*      */     
/*      */     public int[] getRawBuffer() {
/* 1000 */       return this.stateRawBuffer;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getVertexCount() {
/* 1005 */       return this.stateRawBuffer.length / this.stateVertexFormat.getIntegerSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public VertexFormat getVertexFormat() {
/* 1010 */       return this.stateVertexFormat;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\BufferBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */