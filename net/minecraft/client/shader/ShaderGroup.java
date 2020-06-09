/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.Closeable;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public class ShaderGroup
/*     */ {
/*     */   private final Framebuffer mainFramebuffer;
/*     */   private final IResourceManager resourceManager;
/*     */   private final String shaderGroupName;
/*  32 */   private final List<Shader> listShaders = Lists.newArrayList();
/*  33 */   private final Map<String, Framebuffer> mapFramebuffers = Maps.newHashMap();
/*  34 */   private final List<Framebuffer> listFramebuffers = Lists.newArrayList();
/*     */   
/*     */   private Matrix4f projectionMatrix;
/*     */   private int mainFramebufferWidth;
/*     */   private int mainFramebufferHeight;
/*     */   private float time;
/*     */   private float lastStamp;
/*     */   
/*     */   public ShaderGroup(TextureManager p_i1050_1_, IResourceManager resourceManagerIn, Framebuffer mainFramebufferIn, ResourceLocation p_i1050_4_) throws JsonException, IOException, JsonSyntaxException {
/*  43 */     this.resourceManager = resourceManagerIn;
/*  44 */     this.mainFramebuffer = mainFramebufferIn;
/*  45 */     this.time = 0.0F;
/*  46 */     this.lastStamp = 0.0F;
/*  47 */     this.mainFramebufferWidth = mainFramebufferIn.framebufferWidth;
/*  48 */     this.mainFramebufferHeight = mainFramebufferIn.framebufferHeight;
/*  49 */     this.shaderGroupName = p_i1050_4_.toString();
/*  50 */     resetProjectionMatrix();
/*  51 */     parseGroup(p_i1050_1_, p_i1050_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void parseGroup(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws JsonException, IOException, JsonSyntaxException {
/*  56 */     JsonParser jsonparser = new JsonParser();
/*  57 */     IResource iresource = null;
/*     */ 
/*     */     
/*     */     try {
/*  61 */       iresource = this.resourceManager.getResource(p_152765_2_);
/*  62 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(iresource.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
/*     */       
/*  64 */       if (JsonUtils.isJsonArray(jsonobject, "targets")) {
/*     */         
/*  66 */         JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
/*  67 */         int i = 0;
/*     */         
/*  69 */         for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */           
/*     */           try {
/*  73 */             initTarget(jsonelement);
/*     */           }
/*  75 */           catch (Exception exception1) {
/*     */             
/*  77 */             JsonException jsonexception1 = JsonException.forException(exception1);
/*  78 */             jsonexception1.prependJsonKey("targets[" + i + "]");
/*  79 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  82 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       if (JsonUtils.isJsonArray(jsonobject, "passes")) {
/*     */         
/*  88 */         JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
/*  89 */         int j = 0;
/*     */         
/*  91 */         for (JsonElement jsonelement1 : jsonarray1)
/*     */         {
/*     */           
/*     */           try {
/*  95 */             parsePass(p_152765_1_, jsonelement1);
/*     */           }
/*  97 */           catch (Exception exception) {
/*     */             
/*  99 */             JsonException jsonexception2 = JsonException.forException(exception);
/* 100 */             jsonexception2.prependJsonKey("passes[" + j + "]");
/* 101 */             throw jsonexception2;
/*     */           } 
/*     */           
/* 104 */           j++;
/*     */         }
/*     */       
/*     */       } 
/* 108 */     } catch (Exception exception2) {
/*     */       
/* 110 */       JsonException jsonexception = JsonException.forException(exception2);
/* 111 */       jsonexception.setFilenameAndFlush(p_152765_2_.getResourcePath());
/* 112 */       throw jsonexception;
/*     */     }
/*     */     finally {
/*     */       
/* 116 */       IOUtils.closeQuietly((Closeable)iresource);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initTarget(JsonElement p_148027_1_) throws JsonException {
/* 122 */     if (JsonUtils.isString(p_148027_1_)) {
/*     */       
/* 124 */       addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
/*     */     }
/*     */     else {
/*     */       
/* 128 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_148027_1_, "target");
/* 129 */       String s = JsonUtils.getString(jsonobject, "name");
/* 130 */       int i = JsonUtils.getInt(jsonobject, "width", this.mainFramebufferWidth);
/* 131 */       int j = JsonUtils.getInt(jsonobject, "height", this.mainFramebufferHeight);
/*     */       
/* 133 */       if (this.mapFramebuffers.containsKey(s))
/*     */       {
/* 135 */         throw new JsonException(String.valueOf(s) + " is already defined");
/*     */       }
/*     */       
/* 138 */       addFramebuffer(s, i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parsePass(TextureManager p_152764_1_, JsonElement p_152764_2_) throws JsonException, IOException {
/* 144 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_152764_2_, "pass");
/* 145 */     String s = JsonUtils.getString(jsonobject, "name");
/* 146 */     String s1 = JsonUtils.getString(jsonobject, "intarget");
/* 147 */     String s2 = JsonUtils.getString(jsonobject, "outtarget");
/* 148 */     Framebuffer framebuffer = getFramebuffer(s1);
/* 149 */     Framebuffer framebuffer1 = getFramebuffer(s2);
/*     */     
/* 151 */     if (framebuffer == null)
/*     */     {
/* 153 */       throw new JsonException("Input target '" + s1 + "' does not exist");
/*     */     }
/* 155 */     if (framebuffer1 == null)
/*     */     {
/* 157 */       throw new JsonException("Output target '" + s2 + "' does not exist");
/*     */     }
/*     */ 
/*     */     
/* 161 */     Shader shader = addShader(s, framebuffer, framebuffer1);
/* 162 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "auxtargets", null);
/*     */     
/* 164 */     if (jsonarray != null) {
/*     */       
/* 166 */       int i = 0;
/*     */       
/* 168 */       for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */         
/*     */         try {
/* 172 */           JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "auxtarget");
/* 173 */           String s4 = JsonUtils.getString(jsonobject1, "name");
/* 174 */           String s3 = JsonUtils.getString(jsonobject1, "id");
/* 175 */           Framebuffer framebuffer2 = getFramebuffer(s3);
/*     */           
/* 177 */           if (framebuffer2 == null)
/*     */           {
/* 179 */             ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + s3 + ".png");
/* 180 */             IResource iresource = null;
/*     */ 
/*     */             
/*     */             try {
/* 184 */               iresource = this.resourceManager.getResource(resourcelocation);
/*     */             }
/* 186 */             catch (FileNotFoundException var29) {
/*     */               
/* 188 */               throw new JsonException("Render target or texture '" + s3 + "' does not exist");
/*     */             }
/*     */             finally {
/*     */               
/* 192 */               IOUtils.closeQuietly((Closeable)iresource);
/*     */             } 
/*     */             
/* 195 */             p_152764_1_.bindTexture(resourcelocation);
/* 196 */             ITextureObject lvt_20_2_ = p_152764_1_.getTexture(resourcelocation);
/* 197 */             int lvt_21_1_ = JsonUtils.getInt(jsonobject1, "width");
/* 198 */             int lvt_22_1_ = JsonUtils.getInt(jsonobject1, "height");
/* 199 */             boolean lvt_23_1_ = JsonUtils.getBoolean(jsonobject1, "bilinear");
/*     */             
/* 201 */             if (lvt_23_1_) {
/*     */               
/* 203 */               GlStateManager.glTexParameteri(3553, 10241, 9729);
/* 204 */               GlStateManager.glTexParameteri(3553, 10240, 9729);
/*     */             }
/*     */             else {
/*     */               
/* 208 */               GlStateManager.glTexParameteri(3553, 10241, 9728);
/* 209 */               GlStateManager.glTexParameteri(3553, 10240, 9728);
/*     */             } 
/*     */             
/* 212 */             shader.addAuxFramebuffer(s4, Integer.valueOf(lvt_20_2_.getGlTextureId()), lvt_21_1_, lvt_22_1_);
/*     */           }
/*     */           else
/*     */           {
/* 216 */             shader.addAuxFramebuffer(s4, framebuffer2, framebuffer2.framebufferTextureWidth, framebuffer2.framebufferTextureHeight);
/*     */           }
/*     */         
/* 219 */         } catch (Exception exception1) {
/*     */           
/* 221 */           JsonException jsonexception = JsonException.forException(exception1);
/* 222 */           jsonexception.prependJsonKey("auxtargets[" + i + "]");
/* 223 */           throw jsonexception;
/*     */         } 
/*     */         
/* 226 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "uniforms", null);
/*     */     
/* 232 */     if (jsonarray1 != null) {
/*     */       
/* 234 */       int l = 0;
/*     */       
/* 236 */       for (JsonElement jsonelement1 : jsonarray1) {
/*     */ 
/*     */         
/*     */         try {
/* 240 */           initUniform(jsonelement1);
/*     */         }
/* 242 */         catch (Exception exception) {
/*     */           
/* 244 */           JsonException jsonexception1 = JsonException.forException(exception);
/* 245 */           jsonexception1.prependJsonKey("uniforms[" + l + "]");
/* 246 */           throw jsonexception1;
/*     */         } 
/*     */         
/* 249 */         l++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initUniform(JsonElement p_148028_1_) throws JsonException {
/* 257 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_148028_1_, "uniform");
/* 258 */     String s = JsonUtils.getString(jsonobject, "name");
/* 259 */     ShaderUniform shaderuniform = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(s);
/*     */     
/* 261 */     if (shaderuniform == null)
/*     */     {
/* 263 */       throw new JsonException("Uniform '" + s + "' does not exist");
/*     */     }
/*     */ 
/*     */     
/* 267 */     float[] afloat = new float[4];
/* 268 */     int i = 0;
/*     */     
/* 270 */     for (JsonElement jsonelement : JsonUtils.getJsonArray(jsonobject, "values")) {
/*     */ 
/*     */       
/*     */       try {
/* 274 */         afloat[i] = JsonUtils.getFloat(jsonelement, "value");
/*     */       }
/* 276 */       catch (Exception exception) {
/*     */         
/* 278 */         JsonException jsonexception = JsonException.forException(exception);
/* 279 */         jsonexception.prependJsonKey("values[" + i + "]");
/* 280 */         throw jsonexception;
/*     */       } 
/*     */       
/* 283 */       i++;
/*     */     } 
/*     */     
/* 286 */     switch (i) {
/*     */       default:
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 293 */         shaderuniform.set(afloat[0]);
/*     */ 
/*     */       
/*     */       case 2:
/* 297 */         shaderuniform.set(afloat[0], afloat[1]);
/*     */ 
/*     */       
/*     */       case 3:
/* 301 */         shaderuniform.set(afloat[0], afloat[1], afloat[2]);
/*     */       case 4:
/*     */         break;
/*     */     } 
/* 305 */     shaderuniform.set(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Framebuffer getFramebufferRaw(String p_177066_1_) {
/* 312 */     return this.mapFramebuffers.get(p_177066_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
/* 317 */     Framebuffer framebuffer = new Framebuffer(p_148020_2_, p_148020_3_, true);
/* 318 */     framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 319 */     this.mapFramebuffers.put(p_148020_1_, framebuffer);
/*     */     
/* 321 */     if (p_148020_2_ == this.mainFramebufferWidth && p_148020_3_ == this.mainFramebufferHeight)
/*     */     {
/* 323 */       this.listFramebuffers.add(framebuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShaderGroup() {
/* 329 */     for (Framebuffer framebuffer : this.mapFramebuffers.values())
/*     */     {
/* 331 */       framebuffer.deleteFramebuffer();
/*     */     }
/*     */     
/* 334 */     for (Shader shader : this.listShaders)
/*     */     {
/* 336 */       shader.deleteShader();
/*     */     }
/*     */     
/* 339 */     this.listShaders.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws JsonException, IOException {
/* 344 */     Shader shader = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
/* 345 */     this.listShaders.add(this.listShaders.size(), shader);
/* 346 */     return shader;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetProjectionMatrix() {
/* 351 */     this.projectionMatrix = new Matrix4f();
/* 352 */     this.projectionMatrix.setIdentity();
/* 353 */     this.projectionMatrix.m00 = 2.0F / this.mainFramebuffer.framebufferTextureWidth;
/* 354 */     this.projectionMatrix.m11 = 2.0F / -this.mainFramebuffer.framebufferTextureHeight;
/* 355 */     this.projectionMatrix.m22 = -0.0020001999F;
/* 356 */     this.projectionMatrix.m33 = 1.0F;
/* 357 */     this.projectionMatrix.m03 = -1.0F;
/* 358 */     this.projectionMatrix.m13 = 1.0F;
/* 359 */     this.projectionMatrix.m23 = -1.0001999F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createBindFramebuffers(int width, int height) {
/* 364 */     this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
/* 365 */     this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
/* 366 */     resetProjectionMatrix();
/*     */     
/* 368 */     for (Shader shader : this.listShaders)
/*     */     {
/* 370 */       shader.setProjectionMatrix(this.projectionMatrix);
/*     */     }
/*     */     
/* 373 */     for (Framebuffer framebuffer : this.listFramebuffers)
/*     */     {
/* 375 */       framebuffer.createBindFramebuffer(width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadShaderGroup(float partialTicks) {
/* 381 */     if (partialTicks < this.lastStamp) {
/*     */       
/* 383 */       this.time += 1.0F - this.lastStamp;
/* 384 */       this.time += partialTicks;
/*     */     }
/*     */     else {
/*     */       
/* 388 */       this.time += partialTicks - this.lastStamp;
/*     */     } 
/*     */     
/* 391 */     for (this.lastStamp = partialTicks; this.time > 20.0F; this.time -= 20.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     for (Shader shader : this.listShaders)
/*     */     {
/* 398 */       shader.loadShader(this.time / 20.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getShaderGroupName() {
/* 404 */     return this.shaderGroupName;
/*     */   }
/*     */ 
/*     */   
/*     */   private Framebuffer getFramebuffer(String p_148017_1_) {
/* 409 */     if (p_148017_1_ == null)
/*     */     {
/* 411 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 415 */     return p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(p_148017_1_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\ShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */