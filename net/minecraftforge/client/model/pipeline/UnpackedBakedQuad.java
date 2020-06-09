/*     */ package net.minecraftforge.client.model.pipeline;
/*     */ 
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.util.EnumFacing;
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
/*     */ public class UnpackedBakedQuad
/*     */   extends BakedQuad
/*     */ {
/*     */   protected final float[][][] unpackedData;
/*     */   protected final VertexFormat format;
/*     */   protected boolean packed = false;
/*     */   
/*     */   public UnpackedBakedQuad(float[][][] unpackedData, int tint, EnumFacing orientation, TextureAtlasSprite texture, boolean applyDiffuseLighting, VertexFormat format) {
/*  38 */     super(new int[format.getNextOffset()], tint, orientation, texture, applyDiffuseLighting, format);
/*  39 */     this.unpackedData = unpackedData;
/*  40 */     this.format = format;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getVertexData() {
/*  46 */     if (!this.packed) {
/*     */       
/*  48 */       this.packed = true;
/*  49 */       for (int v = 0; v < 4; v++) {
/*     */         
/*  51 */         for (int e = 0; e < this.format.getElementCount(); e++)
/*     */         {
/*  53 */           LightUtil.pack(this.unpackedData[v][e], this.vertexData, this.format, v, e);
/*     */         }
/*     */       } 
/*     */     } 
/*  57 */     return this.vertexData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pipe(IVertexConsumer consumer) {
/*  63 */     int[] eMap = LightUtil.mapFormats(consumer.getVertexFormat(), this.format);
/*     */     
/*  65 */     if (hasTintIndex())
/*     */     {
/*  67 */       consumer.setQuadTint(getTintIndex());
/*     */     }
/*     */ 
/*     */     
/*  71 */     consumer.setQuadOrientation(getFace());
/*  72 */     for (int v = 0; v < 4; v++) {
/*     */       
/*  74 */       for (int e = 0; e < consumer.getVertexFormat().getElementCount(); e++) {
/*     */         
/*  76 */         if (eMap[e] != this.format.getElementCount()) {
/*     */           
/*  78 */           consumer.put(e, this.unpackedData[v][eMap[e]]);
/*     */         }
/*     */         else {
/*     */           
/*  82 */           consumer.put(e, new float[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */     implements IVertexConsumer {
/*     */     private final VertexFormat format;
/*     */     private final float[][][] unpackedData;
/*  92 */     private int tint = -1;
/*     */     
/*     */     private EnumFacing orientation;
/*     */     private TextureAtlasSprite texture;
/*     */     private boolean applyDiffuseLighting = true;
/*  97 */     private int vertices = 0;
/*  98 */     private int elements = 0;
/*     */ 
/*     */     
/*     */     private boolean full = false;
/*     */ 
/*     */     
/*     */     private boolean contractUVs = false;
/*     */ 
/*     */     
/*     */     private final float eps = 0.00390625F;
/*     */ 
/*     */     
/*     */     public VertexFormat getVertexFormat() {
/* 111 */       return this.format;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setContractUVs(boolean value) {
/* 116 */       this.contractUVs = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setQuadTint(int tint) {
/* 121 */       this.tint = tint;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setQuadOrientation(EnumFacing orientation) {
/* 127 */       this.orientation = orientation;
/*     */     }
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
/*     */     public void put(int element, float... data) {
/* 146 */       for (int i = 0; i < 4; i++) {
/*     */         
/* 148 */         if (i < data.length) {
/*     */           
/* 150 */           this.unpackedData[this.vertices][element][i] = data[i];
/*     */         }
/*     */         else {
/*     */           
/* 154 */           this.unpackedData[this.vertices][element][i] = 0.0F;
/*     */         } 
/*     */       } 
/* 157 */       this.elements++;
/* 158 */       if (this.elements == this.format.getElementCount()) {
/*     */         
/* 160 */         this.vertices++;
/* 161 */         this.elements = 0;
/*     */       } 
/* 163 */       if (this.vertices == 4)
/*     */       {
/* 165 */         this.full = true; } 
/*     */     }
/*     */     
/*     */     public Builder(VertexFormat format) {
/* 169 */       this.eps = 0.00390625F;
/*     */       this.format = format;
/*     */       this.unpackedData = new float[4][format.getElementCount()][4];
/*     */     } public UnpackedBakedQuad build() {
/* 173 */       if (!this.full)
/*     */       {
/* 175 */         throw new IllegalStateException("not enough data");
/*     */       }
/* 177 */       if (this.texture == null)
/*     */       {
/* 179 */         throw new IllegalStateException("texture not set");
/*     */       }
/* 181 */       if (this.contractUVs) {
/*     */         
/* 183 */         float tX = this.texture.getIconWidth() / (this.texture.getMaxU() - this.texture.getMinU());
/* 184 */         float tY = this.texture.getIconHeight() / (this.texture.getMaxV() - this.texture.getMinV());
/* 185 */         float tS = (tX > tY) ? tX : tY;
/* 186 */         float ep = 1.0F / tS * 256.0F;
/* 187 */         int uve = 0;
/* 188 */         while (uve < this.format.getElementCount()) {
/*     */           
/* 190 */           VertexFormatElement e = this.format.getElement(uve);
/* 191 */           if (e.getUsage() == VertexFormatElement.EnumUsage.UV && e.getIndex() == 0) {
/*     */             break;
/*     */           }
/*     */           
/* 195 */           uve++;
/*     */         } 
/* 197 */         if (uve == this.format.getElementCount())
/*     */         {
/* 199 */           throw new IllegalStateException("Can't contract UVs: format doesn't contain UVs");
/*     */         }
/* 201 */         float[] uvc = new float[4]; int v;
/* 202 */         for (v = 0; v < 4; v++) {
/*     */           
/* 204 */           for (int i = 0; i < 4; i++)
/*     */           {
/* 206 */             uvc[i] = uvc[i] + this.unpackedData[v][uve][i] / 4.0F;
/*     */           }
/*     */         } 
/* 209 */         for (v = 0; v < 4; v++) {
/*     */           
/* 211 */           for (int i = 0; i < 4; i++) {
/*     */             
/* 213 */             float uo = this.unpackedData[v][uve][i];
/* 214 */             float un = uo * 0.99609375F + uvc[i] * 0.00390625F;
/* 215 */             float ud = uo - un;
/* 216 */             float aud = ud;
/* 217 */             if (aud < 0.0F) aud = -aud; 
/* 218 */             if (aud < ep) {
/*     */               
/* 220 */               float udc = uo - uvc[i];
/* 221 */               if (udc < 0.0F) udc = -udc; 
/* 222 */               if (udc < 2.0F * ep) {
/*     */                 
/* 224 */                 un = (uo + uvc[i]) / 2.0F;
/*     */               }
/*     */               else {
/*     */                 
/* 228 */                 un = uo + ((ud < 0.0F) ? ep : -ep);
/*     */               } 
/*     */             } 
/* 231 */             this.unpackedData[v][uve][i] = un;
/*     */           } 
/*     */         } 
/*     */       } 
/* 235 */       return new UnpackedBakedQuad(this.unpackedData, this.tint, this.orientation, this.texture, this.applyDiffuseLighting, this.format);
/*     */     }
/*     */     
/*     */     public void setQuadColored() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraftforge\client\model\pipeline\UnpackedBakedQuad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */