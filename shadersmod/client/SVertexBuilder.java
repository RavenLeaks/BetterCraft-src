/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public class SVertexBuilder
/*     */ {
/*     */   int vertexSize;
/*     */   int offsetNormal;
/*     */   int offsetUV;
/*     */   int offsetUVCenter;
/*     */   boolean hasNormal;
/*     */   boolean hasTangent;
/*     */   boolean hasUV;
/*     */   boolean hasUVCenter;
/*  26 */   long[] entityData = new long[10];
/*  27 */   int entityDataIndex = 0;
/*     */ 
/*     */   
/*     */   public SVertexBuilder() {
/*  31 */     this.entityData[this.entityDataIndex] = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initVertexBuilder(BufferBuilder wrr) {
/*  36 */     wrr.sVertexBuilder = new SVertexBuilder();
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushEntity(long data) {
/*  41 */     this.entityDataIndex++;
/*  42 */     this.entityData[this.entityDataIndex] = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void popEntity() {
/*  47 */     this.entityData[this.entityDataIndex] = 0L;
/*  48 */     this.entityDataIndex--;
/*     */   }
/*     */   
/*     */   public static void pushEntity(IBlockState blockState, BlockPos blockPos, IBlockAccess blockAccess, BufferBuilder wrr) {
/*     */     int j;
/*  53 */     Block block = blockState.getBlock();
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (blockState instanceof BlockStateBase) {
/*     */       
/*  59 */       BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  60 */       i = blockstatebase.getBlockId();
/*  61 */       j = blockstatebase.getMetadata();
/*     */     }
/*     */     else {
/*     */       
/*  65 */       i = Block.getIdFromBlock(block);
/*  66 */       j = block.getMetaFromState(blockState);
/*     */     } 
/*     */     
/*  69 */     int i = BlockAliases.getMappedBlockId(i, j);
/*  70 */     int i1 = block.getRenderType(blockState).ordinal();
/*  71 */     int k = ((i1 & 0xFFFF) << 16) + (i & 0xFFFF);
/*  72 */     int l = j & 0xFFFF;
/*  73 */     wrr.sVertexBuilder.pushEntity((l << 32L) + k);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void popEntity(BufferBuilder wrr) {
/*  78 */     wrr.sVertexBuilder.popEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean popEntity(boolean value, BufferBuilder wrr) {
/*  83 */     wrr.sVertexBuilder.popEntity();
/*  84 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endSetVertexFormat(BufferBuilder wrr) {
/*  89 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*  90 */     VertexFormat vertexformat = wrr.getVertexFormat();
/*  91 */     svertexbuilder.vertexSize = vertexformat.getNextOffset() / 4;
/*  92 */     svertexbuilder.hasNormal = vertexformat.hasNormal();
/*  93 */     svertexbuilder.hasTangent = svertexbuilder.hasNormal;
/*  94 */     svertexbuilder.hasUV = vertexformat.hasUvOffset(0);
/*  95 */     svertexbuilder.offsetNormal = svertexbuilder.hasNormal ? (vertexformat.getNormalOffset() / 4) : 0;
/*  96 */     svertexbuilder.offsetUV = svertexbuilder.hasUV ? (vertexformat.getUvOffsetById(0) / 4) : 0;
/*  97 */     svertexbuilder.offsetUVCenter = 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertex(BufferBuilder wrr) {
/* 102 */     if (wrr.vertexCount == 0)
/*     */     {
/* 104 */       endSetVertexFormat(wrr);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endAddVertex(BufferBuilder wrr) {
/* 110 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 112 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 114 */       if (wrr.drawMode == 7 && wrr.vertexCount % 4 == 0)
/*     */       {
/* 116 */         svertexbuilder.calcNormal(wrr, wrr.getBufferSize() - 4 * svertexbuilder.vertexSize);
/*     */       }
/*     */       
/* 119 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/* 120 */       int j = wrr.getBufferSize() - 14 + 12;
/* 121 */       wrr.rawIntBuffer.put(j, (int)i);
/* 122 */       wrr.rawIntBuffer.put(j + 1, (int)(i >> 32L));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beginAddVertexData(BufferBuilder wrr, int[] data) {
/* 128 */     if (wrr.vertexCount == 0)
/*     */     {
/* 130 */       endSetVertexFormat(wrr);
/*     */     }
/*     */     
/* 133 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 135 */     if (svertexbuilder.vertexSize == 14) {
/*     */       
/* 137 */       long i = svertexbuilder.entityData[svertexbuilder.entityDataIndex];
/*     */       
/* 139 */       for (int j = 12; j + 1 < data.length; j += 14) {
/*     */         
/* 141 */         data[j] = (int)i;
/* 142 */         data[j + 1] = (int)(i >> 32L);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void endAddVertexData(BufferBuilder wrr) {
/* 149 */     SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/*     */     
/* 151 */     if (svertexbuilder.vertexSize == 14 && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0)
/*     */     {
/* 153 */       svertexbuilder.calcNormal(wrr, wrr.getBufferSize() - 4 * svertexbuilder.vertexSize);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcNormal(BufferBuilder wrr, int baseIndex) {
/* 159 */     FloatBuffer floatbuffer = wrr.rawFloatBuffer;
/* 160 */     IntBuffer intbuffer = wrr.rawIntBuffer;
/* 161 */     int i = wrr.getBufferSize();
/* 162 */     float f = floatbuffer.get(baseIndex + 0 * this.vertexSize);
/* 163 */     float f1 = floatbuffer.get(baseIndex + 0 * this.vertexSize + 1);
/* 164 */     float f2 = floatbuffer.get(baseIndex + 0 * this.vertexSize + 2);
/* 165 */     float f3 = floatbuffer.get(baseIndex + 0 * this.vertexSize + this.offsetUV);
/* 166 */     float f4 = floatbuffer.get(baseIndex + 0 * this.vertexSize + this.offsetUV + 1);
/* 167 */     float f5 = floatbuffer.get(baseIndex + 1 * this.vertexSize);
/* 168 */     float f6 = floatbuffer.get(baseIndex + 1 * this.vertexSize + 1);
/* 169 */     float f7 = floatbuffer.get(baseIndex + 1 * this.vertexSize + 2);
/* 170 */     float f8 = floatbuffer.get(baseIndex + 1 * this.vertexSize + this.offsetUV);
/* 171 */     float f9 = floatbuffer.get(baseIndex + 1 * this.vertexSize + this.offsetUV + 1);
/* 172 */     float f10 = floatbuffer.get(baseIndex + 2 * this.vertexSize);
/* 173 */     float f11 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 1);
/* 174 */     float f12 = floatbuffer.get(baseIndex + 2 * this.vertexSize + 2);
/* 175 */     float f13 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV);
/* 176 */     float f14 = floatbuffer.get(baseIndex + 2 * this.vertexSize + this.offsetUV + 1);
/* 177 */     float f15 = floatbuffer.get(baseIndex + 3 * this.vertexSize);
/* 178 */     float f16 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 1);
/* 179 */     float f17 = floatbuffer.get(baseIndex + 3 * this.vertexSize + 2);
/* 180 */     float f18 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV);
/* 181 */     float f19 = floatbuffer.get(baseIndex + 3 * this.vertexSize + this.offsetUV + 1);
/* 182 */     float f20 = f10 - f;
/* 183 */     float f21 = f11 - f1;
/* 184 */     float f22 = f12 - f2;
/* 185 */     float f23 = f15 - f5;
/* 186 */     float f24 = f16 - f6;
/* 187 */     float f25 = f17 - f7;
/* 188 */     float f30 = f21 * f25 - f24 * f22;
/* 189 */     float f31 = f22 * f23 - f25 * f20;
/* 190 */     float f32 = f20 * f24 - f23 * f21;
/* 191 */     float f33 = f30 * f30 + f31 * f31 + f32 * f32;
/* 192 */     float f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 193 */     f30 *= f34;
/* 194 */     f31 *= f34;
/* 195 */     f32 *= f34;
/* 196 */     f20 = f5 - f;
/* 197 */     f21 = f6 - f1;
/* 198 */     f22 = f7 - f2;
/* 199 */     float f26 = f8 - f3;
/* 200 */     float f27 = f9 - f4;
/* 201 */     f23 = f10 - f;
/* 202 */     f24 = f11 - f1;
/* 203 */     f25 = f12 - f2;
/* 204 */     float f28 = f13 - f3;
/* 205 */     float f29 = f14 - f4;
/* 206 */     float f35 = f26 * f29 - f28 * f27;
/* 207 */     float f36 = (f35 != 0.0F) ? (1.0F / f35) : 1.0F;
/* 208 */     float f37 = (f29 * f20 - f27 * f23) * f36;
/* 209 */     float f38 = (f29 * f21 - f27 * f24) * f36;
/* 210 */     float f39 = (f29 * f22 - f27 * f25) * f36;
/* 211 */     float f40 = (f26 * f23 - f28 * f20) * f36;
/* 212 */     float f41 = (f26 * f24 - f28 * f21) * f36;
/* 213 */     float f42 = (f26 * f25 - f28 * f22) * f36;
/* 214 */     f33 = f37 * f37 + f38 * f38 + f39 * f39;
/* 215 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 216 */     f37 *= f34;
/* 217 */     f38 *= f34;
/* 218 */     f39 *= f34;
/* 219 */     f33 = f40 * f40 + f41 * f41 + f42 * f42;
/* 220 */     f34 = (f33 != 0.0D) ? (float)(1.0D / Math.sqrt(f33)) : 1.0F;
/* 221 */     f40 *= f34;
/* 222 */     f41 *= f34;
/* 223 */     f42 *= f34;
/* 224 */     float f43 = f32 * f38 - f31 * f39;
/* 225 */     float f44 = f30 * f39 - f32 * f37;
/* 226 */     float f45 = f31 * f37 - f30 * f38;
/* 227 */     float f46 = (f40 * f43 + f41 * f44 + f42 * f45 < 0.0F) ? -1.0F : 1.0F;
/* 228 */     int j = (int)(f30 * 127.0F) & 0xFF;
/* 229 */     int k = (int)(f31 * 127.0F) & 0xFF;
/* 230 */     int l = (int)(f32 * 127.0F) & 0xFF;
/* 231 */     int i1 = (l << 16) + (k << 8) + j;
/* 232 */     intbuffer.put(baseIndex + 0 * this.vertexSize + this.offsetNormal, i1);
/* 233 */     intbuffer.put(baseIndex + 1 * this.vertexSize + this.offsetNormal, i1);
/* 234 */     intbuffer.put(baseIndex + 2 * this.vertexSize + this.offsetNormal, i1);
/* 235 */     intbuffer.put(baseIndex + 3 * this.vertexSize + this.offsetNormal, i1);
/* 236 */     int j1 = ((int)(f37 * 32767.0F) & 0xFFFF) + (((int)(f38 * 32767.0F) & 0xFFFF) << 16);
/* 237 */     int k1 = ((int)(f39 * 32767.0F) & 0xFFFF) + (((int)(f46 * 32767.0F) & 0xFFFF) << 16);
/* 238 */     intbuffer.put(baseIndex + 0 * this.vertexSize + 10, j1);
/* 239 */     intbuffer.put(baseIndex + 0 * this.vertexSize + 10 + 1, k1);
/* 240 */     intbuffer.put(baseIndex + 1 * this.vertexSize + 10, j1);
/* 241 */     intbuffer.put(baseIndex + 1 * this.vertexSize + 10 + 1, k1);
/* 242 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10, j1);
/* 243 */     intbuffer.put(baseIndex + 2 * this.vertexSize + 10 + 1, k1);
/* 244 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10, j1);
/* 245 */     intbuffer.put(baseIndex + 3 * this.vertexSize + 10 + 1, k1);
/* 246 */     float f47 = (f3 + f8 + f13 + f18) / 4.0F;
/* 247 */     float f48 = (f4 + f9 + f14 + f19) / 4.0F;
/* 248 */     floatbuffer.put(baseIndex + 0 * this.vertexSize + 8, f47);
/* 249 */     floatbuffer.put(baseIndex + 0 * this.vertexSize + 8 + 1, f48);
/* 250 */     floatbuffer.put(baseIndex + 1 * this.vertexSize + 8, f47);
/* 251 */     floatbuffer.put(baseIndex + 1 * this.vertexSize + 8 + 1, f48);
/* 252 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8, f47);
/* 253 */     floatbuffer.put(baseIndex + 2 * this.vertexSize + 8 + 1, f48);
/* 254 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8, f47);
/* 255 */     floatbuffer.put(baseIndex + 3 * this.vertexSize + 8 + 1, f48);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void calcNormalChunkLayer(BufferBuilder wrr) {
/* 260 */     if (wrr.getVertexFormat().hasNormal() && wrr.drawMode == 7 && wrr.vertexCount % 4 == 0) {
/*     */       
/* 262 */       SVertexBuilder svertexbuilder = wrr.sVertexBuilder;
/* 263 */       endSetVertexFormat(wrr);
/* 264 */       int i = wrr.vertexCount * svertexbuilder.vertexSize;
/*     */       
/* 266 */       for (int j = 0; j < i; j += svertexbuilder.vertexSize * 4)
/*     */       {
/* 268 */         svertexbuilder.calcNormal(wrr, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawArrays(int drawMode, int first, int count, BufferBuilder wrr) {
/* 275 */     if (count != 0) {
/*     */       
/* 277 */       VertexFormat vertexformat = wrr.getVertexFormat();
/* 278 */       int i = vertexformat.getNextOffset();
/*     */       
/* 280 */       if (i == 56) {
/*     */         
/* 282 */         ByteBuffer bytebuffer = wrr.getByteBuffer();
/* 283 */         bytebuffer.position(32);
/* 284 */         GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, i, bytebuffer);
/* 285 */         bytebuffer.position(40);
/* 286 */         GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, i, bytebuffer);
/* 287 */         bytebuffer.position(48);
/* 288 */         GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, i, bytebuffer);
/* 289 */         bytebuffer.position(0);
/* 290 */         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 291 */         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 292 */         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/* 293 */         GL11.glDrawArrays(drawMode, first, count);
/* 294 */         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 295 */         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 296 */         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */       }
/*     */       else {
/*     */         
/* 300 */         GL11.glDrawArrays(drawMode, first, count);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\SVertexBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */