/*     */ package net.minecraftforge.client.model.pipeline;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.apache.commons.lang3.tuple.Pair;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LightUtil
/*     */ {
/*     */   public static float diffuseLight(float x, float y, float z) {
/*  40 */     return Math.min(x * x * 0.6F + y * y * (3.0F + y) / 4.0F + z * z * 0.8F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float diffuseLight(EnumFacing side) {
/*  45 */     switch (side) {
/*     */       
/*     */       case null:
/*  48 */         return 0.5F;
/*     */       case UP:
/*  50 */         return 1.0F;
/*     */       case NORTH:
/*     */       case SOUTH:
/*  53 */         return 0.8F;
/*     */     } 
/*  55 */     return 0.6F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFacing toSide(float x, float y, float z) {
/*  61 */     if (Math.abs(x) > Math.abs(y)) {
/*     */       
/*  63 */       if (Math.abs(x) > Math.abs(z)) {
/*     */         
/*  65 */         if (x < 0.0F) return EnumFacing.WEST; 
/*  66 */         return EnumFacing.EAST;
/*     */       } 
/*     */ 
/*     */       
/*  70 */       if (z < 0.0F) return EnumFacing.NORTH; 
/*  71 */       return EnumFacing.SOUTH;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  76 */     if (Math.abs(y) > Math.abs(z)) {
/*     */       
/*  78 */       if (y < 0.0F) return EnumFacing.DOWN; 
/*  79 */       return EnumFacing.UP;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     if (z < 0.0F) return EnumFacing.NORTH; 
/*  84 */     return EnumFacing.SOUTH;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  89 */   private static final ConcurrentMap<Pair<VertexFormat, VertexFormat>, int[]> formatMaps = (ConcurrentMap)new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putBakedQuad(IVertexConsumer consumer, BakedQuad quad) {
/*  94 */     consumer.setQuadOrientation(quad.getFace());
/*  95 */     if (quad.hasTintIndex())
/*     */     {
/*  97 */       consumer.setQuadTint(quad.getTintIndex());
/*     */     }
/*     */     
/* 100 */     float[] data = new float[4];
/* 101 */     VertexFormat formatFrom = consumer.getVertexFormat();
/* 102 */     VertexFormat formatTo = quad.getFormat();
/* 103 */     int countFrom = formatFrom.getElementCount();
/* 104 */     int countTo = formatTo.getElementCount();
/* 105 */     int[] eMap = mapFormats(formatFrom, formatTo);
/* 106 */     for (int v = 0; v < 4; v++) {
/*     */       
/* 108 */       for (int e = 0; e < countFrom; e++) {
/*     */         
/* 110 */         if (eMap[e] != countTo) {
/*     */           
/* 112 */           unpack(quad.getVertexData(), data, formatTo, v, eMap[e]);
/* 113 */           consumer.put(e, data);
/*     */         }
/*     */         else {
/*     */           
/* 117 */           consumer.put(e, new float[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 123 */   private static final VertexFormat DEFAULT_FROM = VertexLighterFlat.withNormal(DefaultVertexFormats.BLOCK);
/* 124 */   private static final VertexFormat DEFAULT_TO = DefaultVertexFormats.ITEM;
/* 125 */   private static final int[] DEFAULT_MAPPING = generateMapping(DEFAULT_FROM, DEFAULT_TO);
/*     */ 
/*     */   
/*     */   public static int[] mapFormats(VertexFormat from, VertexFormat to) {
/* 129 */     if (from.equals(DEFAULT_FROM) && to.equals(DEFAULT_TO))
/* 130 */       return DEFAULT_MAPPING; 
/* 131 */     return formatMaps.computeIfAbsent(Pair.of(from, to), pair -> generateMapping((VertexFormat)pair.getLeft(), (VertexFormat)pair.getRight()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] generateMapping(VertexFormat from, VertexFormat to) {
/* 136 */     int fromCount = from.getElementCount();
/* 137 */     int toCount = to.getElementCount();
/* 138 */     int[] eMap = new int[fromCount];
/*     */     
/* 140 */     for (int e = 0; e < fromCount; e++) {
/*     */       
/* 142 */       VertexFormatElement expected = from.getElement(e);
/*     */       int e2;
/* 144 */       for (e2 = 0; e2 < toCount; e2++) {
/*     */         
/* 146 */         VertexFormatElement current = to.getElement(e2);
/* 147 */         if (expected.getUsage() == current.getUsage() && expected.getIndex() == current.getIndex()) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 152 */       eMap[e] = e2;
/*     */     } 
/* 154 */     return eMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unpack(int[] from, float[] to, VertexFormat formatFrom, int v, int e) {
/* 159 */     int length = (4 < to.length) ? 4 : to.length;
/* 160 */     VertexFormatElement element = formatFrom.getElement(e);
/* 161 */     int vertexStart = v * formatFrom.getNextOffset() + formatFrom.getOffset(e);
/* 162 */     int count = element.getElementCount();
/* 163 */     VertexFormatElement.EnumType type = element.getType();
/* 164 */     int size = type.getSize();
/* 165 */     int mask = (256 << 8 * (size - 1)) - 1;
/* 166 */     for (int i = 0; i < length; i++) {
/*     */       
/* 168 */       if (i < count) {
/*     */         
/* 170 */         int pos = vertexStart + size * i;
/* 171 */         int index = pos >> 2;
/* 172 */         int offset = pos & 0x3;
/* 173 */         int bits = from[index];
/* 174 */         bits >>>= offset * 8;
/* 175 */         if ((pos + size - 1) / 4 != index)
/*     */         {
/* 177 */           bits |= from[index + 1] << (4 - offset) * 8;
/*     */         }
/* 179 */         bits &= mask;
/* 180 */         if (type == VertexFormatElement.EnumType.FLOAT)
/*     */         {
/* 182 */           to[i] = Float.intBitsToFloat(bits);
/*     */         }
/* 184 */         else if (type == VertexFormatElement.EnumType.UBYTE || type == VertexFormatElement.EnumType.USHORT)
/*     */         {
/* 186 */           to[i] = bits / mask;
/*     */         }
/* 188 */         else if (type == VertexFormatElement.EnumType.UINT)
/*     */         {
/* 190 */           to[i] = (float)((bits & 0xFFFFFFFFL) / 4.294967295E9D);
/*     */         }
/* 192 */         else if (type == VertexFormatElement.EnumType.BYTE)
/*     */         {
/* 194 */           to[i] = (byte)bits / (mask >> 1);
/*     */         }
/* 196 */         else if (type == VertexFormatElement.EnumType.SHORT)
/*     */         {
/* 198 */           to[i] = (short)bits / (mask >> 1);
/*     */         }
/* 200 */         else if (type == VertexFormatElement.EnumType.INT)
/*     */         {
/* 202 */           to[i] = (float)((bits & 0xFFFFFFFFL) / 2.147483647E9D);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 207 */         to[i] = 0.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void pack(float[] from, int[] to, VertexFormat formatTo, int v, int e) {
/* 214 */     VertexFormatElement element = formatTo.getElement(e);
/* 215 */     int vertexStart = v * formatTo.getNextOffset() + formatTo.getOffset(e);
/* 216 */     int count = element.getElementCount();
/* 217 */     VertexFormatElement.EnumType type = element.getType();
/* 218 */     int size = type.getSize();
/* 219 */     int mask = (256 << 8 * (size - 1)) - 1;
/* 220 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 222 */       if (i < count) {
/*     */         
/* 224 */         int pos = vertexStart + size * i;
/* 225 */         int index = pos >> 2;
/* 226 */         int offset = pos & 0x3;
/* 227 */         int bits = 0;
/* 228 */         float f = (i < from.length) ? from[i] : 0.0F;
/* 229 */         if (type == VertexFormatElement.EnumType.FLOAT) {
/*     */           
/* 231 */           bits = Float.floatToRawIntBits(f);
/*     */         
/*     */         }
/* 234 */         else if (type == VertexFormatElement.EnumType.UBYTE || 
/* 235 */           type == VertexFormatElement.EnumType.USHORT || 
/* 236 */           type == VertexFormatElement.EnumType.UINT) {
/*     */ 
/*     */           
/* 239 */           bits = Math.round(f * mask);
/*     */         }
/*     */         else {
/*     */           
/* 243 */           bits = Math.round(f * (mask >> 1));
/*     */         } 
/* 245 */         to[index] = to[index] & (mask << offset * 8 ^ 0xFFFFFFFF);
/* 246 */         to[index] = to[index] | (bits & mask) << offset * 8;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 252 */   private static IVertexConsumer tessellator = null;
/*     */   
/*     */   @Deprecated
/*     */   public static IVertexConsumer getTessellator() {
/* 256 */     if (tessellator == null) {
/*     */       
/* 258 */       Tessellator tes = Tessellator.getInstance();
/* 259 */       BufferBuilder wr = tes.getBuffer();
/* 260 */       tessellator = new VertexBufferConsumer(wr);
/*     */     } 
/* 262 */     return tessellator;
/*     */   }
/*     */   
/* 265 */   private static ItemConsumer itemConsumer = null;
/*     */   
/*     */   @Deprecated
/*     */   public static ItemConsumer getItemConsumer() {
/* 269 */     if (itemConsumer == null)
/*     */     {
/* 271 */       itemConsumer = new ItemConsumer(getTessellator());
/*     */     }
/* 273 */     return itemConsumer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ItemPipeline
/*     */   {
/* 283 */     final VertexBufferConsumer bufferConsumer = new VertexBufferConsumer();
/* 284 */     final LightUtil.ItemConsumer itemConsumer = new LightUtil.ItemConsumer(this.bufferConsumer);
/*     */   }
/*     */ 
/*     */   
/* 288 */   private static final ThreadLocal<ItemPipeline> itemPipeline = ThreadLocal.withInitial(ItemPipeline::new);
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderQuadColorSlow(BufferBuilder buffer, BakedQuad quad, int auxColor) {
/* 293 */     ItemPipeline pipeline = itemPipeline.get();
/* 294 */     pipeline.bufferConsumer.setBuffer(buffer);
/* 295 */     ItemConsumer cons = pipeline.itemConsumer;
/*     */     
/* 297 */     float b = (auxColor & 0xFF) / 255.0F;
/* 298 */     float g = (auxColor >>> 8 & 0xFF) / 255.0F;
/* 299 */     float r = (auxColor >>> 16 & 0xFF) / 255.0F;
/* 300 */     float a = (auxColor >>> 24 & 0xFF) / 255.0F;
/*     */     
/* 302 */     cons.setAuxColor(new float[] { r, g, b, a });
/* 303 */     quad.pipe(cons);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderQuadColor(BufferBuilder buffer, BakedQuad quad, int auxColor) {
/* 308 */     if (quad.getFormat().equals(buffer.getVertexFormat())) {
/*     */       
/* 310 */       buffer.addVertexData(quad.getVertexData());
/* 311 */       buffer.getVertexFormat().hasColor();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 318 */       renderQuadColorSlow(buffer, quad, auxColor);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class ItemConsumer
/*     */     extends VertexTransformer {
/* 324 */     private int vertices = 0;
/*     */     
/* 326 */     private float[] auxColor = new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
/* 327 */     private float[] buf = new float[4];
/*     */ 
/*     */     
/*     */     public ItemConsumer(IVertexConsumer parent) {
/* 331 */       super(parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setAuxColor(float... auxColor) {
/* 336 */       System.arraycopy(auxColor, 0, this.auxColor, 0, this.auxColor.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(int element, float... data) {
/* 342 */       if (getVertexFormat().getElement(element).getUsage() == VertexFormatElement.EnumUsage.COLOR) {
/*     */         
/* 344 */         System.arraycopy(this.auxColor, 0, this.buf, 0, this.buf.length);
/* 345 */         int n = Math.min(4, data.length);
/* 346 */         for (int i = 0; i < n; i++)
/*     */         {
/* 348 */           this.buf[i] = this.buf[i] * data[i];
/*     */         }
/* 350 */         super.put(element, this.buf);
/*     */       }
/*     */       else {
/*     */         
/* 354 */         super.put(element, data);
/*     */       } 
/* 356 */       if (element == getVertexFormat().getElementCount() - 1) {
/*     */         
/* 358 */         this.vertices++;
/* 359 */         if (this.vertices == 4)
/*     */         {
/* 361 */           this.vertices = 0;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\LightUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */