/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Framebuffer
/*     */ {
/*     */   public int framebufferTextureWidth;
/*     */   public int framebufferTextureHeight;
/*     */   public int framebufferWidth;
/*     */   public int framebufferHeight;
/*     */   public boolean useDepth;
/*     */   public int framebufferObject;
/*     */   public int framebufferTexture;
/*     */   public int depthBuffer;
/*     */   public float[] framebufferColor;
/*     */   public int framebufferFilter;
/*     */   
/*     */   public Framebuffer(int width, int height, boolean useDepthIn) {
/*  26 */     this.useDepth = useDepthIn;
/*  27 */     this.framebufferObject = -1;
/*  28 */     this.framebufferTexture = -1;
/*  29 */     this.depthBuffer = -1;
/*  30 */     this.framebufferColor = new float[4];
/*  31 */     this.framebufferColor[0] = 1.0F;
/*  32 */     this.framebufferColor[1] = 1.0F;
/*  33 */     this.framebufferColor[2] = 1.0F;
/*  34 */     this.framebufferColor[3] = 0.0F;
/*  35 */     createBindFramebuffer(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBindFramebuffer(int width, int height) {
/*  40 */     if (!OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/*  42 */       this.framebufferWidth = width;
/*  43 */       this.framebufferHeight = height;
/*     */     }
/*     */     else {
/*     */       
/*  47 */       GlStateManager.enableDepth();
/*     */       
/*  49 */       if (this.framebufferObject >= 0)
/*     */       {
/*  51 */         deleteFramebuffer();
/*     */       }
/*     */       
/*  54 */       createFramebuffer(width, height);
/*  55 */       checkFramebufferComplete();
/*  56 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteFramebuffer() {
/*  62 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/*  64 */       unbindFramebufferTexture();
/*  65 */       unbindFramebuffer();
/*     */       
/*  67 */       if (this.depthBuffer > -1) {
/*     */         
/*  69 */         OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
/*  70 */         this.depthBuffer = -1;
/*     */       } 
/*     */       
/*  73 */       if (this.framebufferTexture > -1) {
/*     */         
/*  75 */         TextureUtil.deleteTexture(this.framebufferTexture);
/*  76 */         this.framebufferTexture = -1;
/*     */       } 
/*     */       
/*  79 */       if (this.framebufferObject > -1) {
/*     */         
/*  81 */         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*  82 */         OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
/*  83 */         this.framebufferObject = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createFramebuffer(int width, int height) {
/*  90 */     this.framebufferWidth = width;
/*  91 */     this.framebufferHeight = height;
/*  92 */     this.framebufferTextureWidth = width;
/*  93 */     this.framebufferTextureHeight = height;
/*     */     
/*  95 */     if (!OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/*  97 */       framebufferClear();
/*     */     }
/*     */     else {
/*     */       
/* 101 */       this.framebufferObject = OpenGlHelper.glGenFramebuffers();
/* 102 */       this.framebufferTexture = TextureUtil.glGenTextures();
/*     */       
/* 104 */       if (this.useDepth)
/*     */       {
/* 106 */         this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
/*     */       }
/*     */       
/* 109 */       setFramebufferFilter(9728);
/* 110 */       GlStateManager.bindTexture(this.framebufferTexture);
/* 111 */       GlStateManager.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, null);
/* 112 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
/* 113 */       OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);
/*     */       
/* 115 */       if (this.useDepth) {
/*     */         
/* 117 */         OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
/* 118 */         OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
/* 119 */         OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
/*     */       } 
/*     */       
/* 122 */       framebufferClear();
/* 123 */       unbindFramebufferTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramebufferFilter(int framebufferFilterIn) {
/* 129 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 131 */       this.framebufferFilter = framebufferFilterIn;
/* 132 */       GlStateManager.bindTexture(this.framebufferTexture);
/* 133 */       GlStateManager.glTexParameteri(3553, 10241, framebufferFilterIn);
/* 134 */       GlStateManager.glTexParameteri(3553, 10240, framebufferFilterIn);
/* 135 */       GlStateManager.glTexParameteri(3553, 10242, 10496);
/* 136 */       GlStateManager.glTexParameteri(3553, 10243, 10496);
/* 137 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkFramebufferComplete() {
/* 143 */     int i = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
/*     */     
/* 145 */     if (i != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
/*     */       
/* 147 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT)
/*     */       {
/* 149 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
/*     */       }
/* 151 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH)
/*     */       {
/* 153 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
/*     */       }
/* 155 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER)
/*     */       {
/* 157 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
/*     */       }
/* 159 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER)
/*     */       {
/* 161 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
/*     */       }
/*     */ 
/*     */       
/* 165 */       throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindFramebufferTexture() {
/* 172 */     if (OpenGlHelper.isFramebufferEnabled())
/*     */     {
/* 174 */       GlStateManager.bindTexture(this.framebufferTexture);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindFramebufferTexture() {
/* 180 */     if (OpenGlHelper.isFramebufferEnabled())
/*     */     {
/* 182 */       GlStateManager.bindTexture(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindFramebuffer(boolean p_147610_1_) {
/* 188 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 190 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
/*     */       
/* 192 */       if (p_147610_1_)
/*     */       {
/* 194 */         GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unbindFramebuffer() {
/* 201 */     if (OpenGlHelper.isFramebufferEnabled())
/*     */     {
/* 203 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramebufferColor(float red, float green, float blue, float alpha) {
/* 209 */     this.framebufferColor[0] = red;
/* 210 */     this.framebufferColor[1] = green;
/* 211 */     this.framebufferColor[2] = blue;
/* 212 */     this.framebufferColor[3] = alpha;
/*     */   }
/*     */ 
/*     */   
/*     */   public void framebufferRender(int width, int height) {
/* 217 */     framebufferRenderExt(width, height, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void framebufferRenderExt(int width, int height, boolean p_178038_3_) {
/* 222 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*     */       
/* 224 */       GlStateManager.colorMask(true, true, true, false);
/* 225 */       GlStateManager.disableDepth();
/* 226 */       GlStateManager.depthMask(false);
/* 227 */       GlStateManager.matrixMode(5889);
/* 228 */       GlStateManager.loadIdentity();
/* 229 */       GlStateManager.ortho(0.0D, width, height, 0.0D, 1000.0D, 3000.0D);
/* 230 */       GlStateManager.matrixMode(5888);
/* 231 */       GlStateManager.loadIdentity();
/* 232 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 233 */       GlStateManager.viewport(0, 0, width, height);
/* 234 */       GlStateManager.enableTexture2D();
/* 235 */       GlStateManager.disableLighting();
/* 236 */       GlStateManager.disableAlpha();
/*     */       
/* 238 */       if (p_178038_3_) {
/*     */         
/* 240 */         GlStateManager.disableBlend();
/* 241 */         GlStateManager.enableColorMaterial();
/*     */       } 
/*     */       
/* 244 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 245 */       bindFramebufferTexture();
/* 246 */       float f = width;
/* 247 */       float f1 = height;
/* 248 */       float f2 = this.framebufferWidth / this.framebufferTextureWidth;
/* 249 */       float f3 = this.framebufferHeight / this.framebufferTextureHeight;
/* 250 */       Tessellator tessellator = Tessellator.getInstance();
/* 251 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 252 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 253 */       bufferbuilder.pos(0.0D, f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 254 */       bufferbuilder.pos(f, f1, 0.0D).tex(f2, 0.0D).color(255, 255, 255, 255).endVertex();
/* 255 */       bufferbuilder.pos(f, 0.0D, 0.0D).tex(f2, f3).color(255, 255, 255, 255).endVertex();
/* 256 */       bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, f3).color(255, 255, 255, 255).endVertex();
/* 257 */       tessellator.draw();
/* 258 */       unbindFramebufferTexture();
/* 259 */       GlStateManager.depthMask(true);
/* 260 */       GlStateManager.colorMask(true, true, true, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void framebufferClear() {
/* 266 */     bindFramebuffer(true);
/* 267 */     GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
/* 268 */     int i = 16384;
/*     */     
/* 270 */     if (this.useDepth) {
/*     */       
/* 272 */       GlStateManager.clearDepth(1.0D);
/* 273 */       i |= 0x100;
/*     */     } 
/*     */     
/* 276 */     GlStateManager.clear(i);
/* 277 */     unbindFramebuffer();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\Framebuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */