/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonBlendingMode;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ShaderManager
/*     */ {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*  31 */   private static final ShaderDefault DEFAULT_SHADER_UNIFORM = new ShaderDefault();
/*     */   private static ShaderManager staticShaderManager;
/*  33 */   private static int currentProgram = -1;
/*     */   private static boolean lastCull = true;
/*  35 */   private final Map<String, Object> shaderSamplers = Maps.newHashMap();
/*  36 */   private final List<String> samplerNames = Lists.newArrayList();
/*  37 */   private final List<Integer> shaderSamplerLocations = Lists.newArrayList();
/*  38 */   private final List<ShaderUniform> shaderUniforms = Lists.newArrayList();
/*  39 */   private final List<Integer> shaderUniformLocations = Lists.newArrayList();
/*  40 */   private final Map<String, ShaderUniform> mappedShaderUniforms = Maps.newHashMap();
/*     */   
/*     */   private final int program;
/*     */   private final String programFilename;
/*     */   private final boolean useFaceCulling;
/*     */   private boolean isDirty;
/*     */   private final JsonBlendingMode blendingMode;
/*     */   private final List<Integer> attribLocations;
/*     */   private final List<String> attributes;
/*     */   private final ShaderLoader vertexShaderLoader;
/*     */   private final ShaderLoader fragmentShaderLoader;
/*     */   
/*     */   public ShaderManager(IResourceManager resourceManager, String programName) throws JsonException, IOException {
/*  53 */     JsonParser jsonparser = new JsonParser();
/*  54 */     ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + programName + ".json");
/*  55 */     this.programFilename = programName;
/*  56 */     IResource iresource = null;
/*     */ 
/*     */     
/*     */     try {
/*  60 */       iresource = resourceManager.getResource(resourcelocation);
/*  61 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(iresource.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
/*  62 */       String s = JsonUtils.getString(jsonobject, "vertex");
/*  63 */       String s1 = JsonUtils.getString(jsonobject, "fragment");
/*  64 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "samplers", null);
/*     */       
/*  66 */       if (jsonarray != null) {
/*     */         
/*  68 */         int i = 0;
/*     */         
/*  70 */         for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */           
/*     */           try {
/*  74 */             parseSampler(jsonelement);
/*     */           }
/*  76 */           catch (Exception exception2) {
/*     */             
/*  78 */             JsonException jsonexception1 = JsonException.forException(exception2);
/*  79 */             jsonexception1.prependJsonKey("samplers[" + i + "]");
/*  80 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  83 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "attributes", null);
/*     */       
/*  89 */       if (jsonarray1 != null) {
/*     */         
/*  91 */         int j = 0;
/*  92 */         this.attribLocations = Lists.newArrayListWithCapacity(jsonarray1.size());
/*  93 */         this.attributes = Lists.newArrayListWithCapacity(jsonarray1.size());
/*     */         
/*  95 */         for (JsonElement jsonelement1 : jsonarray1)
/*     */         {
/*     */           
/*     */           try {
/*  99 */             this.attributes.add(JsonUtils.getString(jsonelement1, "attribute"));
/*     */           }
/* 101 */           catch (Exception exception1) {
/*     */             
/* 103 */             JsonException jsonexception2 = JsonException.forException(exception1);
/* 104 */             jsonexception2.prependJsonKey("attributes[" + j + "]");
/* 105 */             throw jsonexception2;
/*     */           } 
/*     */           
/* 108 */           j++;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 113 */         this.attribLocations = null;
/* 114 */         this.attributes = null;
/*     */       } 
/*     */       
/* 117 */       JsonArray jsonarray2 = JsonUtils.getJsonArray(jsonobject, "uniforms", null);
/*     */       
/* 119 */       if (jsonarray2 != null) {
/*     */         
/* 121 */         int k = 0;
/*     */         
/* 123 */         for (JsonElement jsonelement2 : jsonarray2) {
/*     */ 
/*     */           
/*     */           try {
/* 127 */             parseUniform(jsonelement2);
/*     */           }
/* 129 */           catch (Exception exception) {
/*     */             
/* 131 */             JsonException jsonexception3 = JsonException.forException(exception);
/* 132 */             jsonexception3.prependJsonKey("uniforms[" + k + "]");
/* 133 */             throw jsonexception3;
/*     */           } 
/*     */           
/* 136 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       this.blendingMode = JsonBlendingMode.parseBlendNode(JsonUtils.getJsonObject(jsonobject, "blend", null));
/* 141 */       this.useFaceCulling = JsonUtils.getBoolean(jsonobject, "cull", true);
/* 142 */       this.vertexShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.VERTEX, s);
/* 143 */       this.fragmentShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.FRAGMENT, s1);
/* 144 */       this.program = ShaderLinkHelper.getStaticShaderLinkHelper().createProgram();
/* 145 */       ShaderLinkHelper.getStaticShaderLinkHelper().linkProgram(this);
/* 146 */       setupUniforms();
/*     */       
/* 148 */       if (this.attributes != null)
/*     */       {
/* 150 */         for (String s2 : this.attributes)
/*     */         {
/* 152 */           int l = OpenGlHelper.glGetAttribLocation(this.program, s2);
/* 153 */           this.attribLocations.add(Integer.valueOf(l));
/*     */         }
/*     */       
/*     */       }
/* 157 */     } catch (Exception exception3) {
/*     */       
/* 159 */       JsonException jsonexception = JsonException.forException(exception3);
/* 160 */       jsonexception.setFilenameAndFlush(resourcelocation.getResourcePath());
/* 161 */       throw jsonexception;
/*     */     }
/*     */     finally {
/*     */       
/* 165 */       IOUtils.closeQuietly((Closeable)iresource);
/*     */     } 
/*     */     
/* 168 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShader() {
/* 173 */     ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endShader() {
/* 178 */     OpenGlHelper.glUseProgram(0);
/* 179 */     currentProgram = -1;
/* 180 */     staticShaderManager = null;
/* 181 */     lastCull = true;
/*     */     
/* 183 */     for (int i = 0; i < this.shaderSamplerLocations.size(); i++) {
/*     */       
/* 185 */       if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
/*     */         
/* 187 */         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 188 */         GlStateManager.bindTexture(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void useShader() {
/* 195 */     this.isDirty = false;
/* 196 */     staticShaderManager = this;
/* 197 */     this.blendingMode.apply();
/*     */     
/* 199 */     if (this.program != currentProgram) {
/*     */       
/* 201 */       OpenGlHelper.glUseProgram(this.program);
/* 202 */       currentProgram = this.program;
/*     */     } 
/*     */     
/* 205 */     if (this.useFaceCulling) {
/*     */       
/* 207 */       GlStateManager.enableCull();
/*     */     }
/*     */     else {
/*     */       
/* 211 */       GlStateManager.disableCull();
/*     */     } 
/*     */     
/* 214 */     for (int i = 0; i < this.shaderSamplerLocations.size(); i++) {
/*     */       
/* 216 */       if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
/*     */         
/* 218 */         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 219 */         GlStateManager.enableTexture2D();
/* 220 */         Object object = this.shaderSamplers.get(this.samplerNames.get(i));
/* 221 */         int j = -1;
/*     */         
/* 223 */         if (object instanceof Framebuffer) {
/*     */           
/* 225 */           j = ((Framebuffer)object).framebufferTexture;
/*     */         }
/* 227 */         else if (object instanceof ITextureObject) {
/*     */           
/* 229 */           j = ((ITextureObject)object).getGlTextureId();
/*     */         }
/* 231 */         else if (object instanceof Integer) {
/*     */           
/* 233 */           j = ((Integer)object).intValue();
/*     */         } 
/*     */         
/* 236 */         if (j != -1) {
/*     */           
/* 238 */           GlStateManager.bindTexture(j);
/* 239 */           OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(this.program, this.samplerNames.get(i)), i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     for (ShaderUniform shaderuniform : this.shaderUniforms)
/*     */     {
/* 246 */       shaderuniform.upload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 252 */     this.isDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ShaderUniform getShaderUniform(String name) {
/* 262 */     return this.mappedShaderUniforms.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderUniform getShaderUniformOrDefault(String name) {
/* 270 */     ShaderUniform shaderuniform = getShaderUniform(name);
/* 271 */     return (shaderuniform == null) ? DEFAULT_SHADER_UNIFORM : shaderuniform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupUniforms() {
/* 279 */     int i = 0;
/*     */     
/* 281 */     for (int j = 0; i < this.samplerNames.size(); j++) {
/*     */       
/* 283 */       String s = this.samplerNames.get(i);
/* 284 */       int k = OpenGlHelper.glGetUniformLocation(this.program, s);
/*     */       
/* 286 */       if (k == -1) {
/*     */         
/* 288 */         LOGGER.warn("Shader {}could not find sampler named {} in the specified shader program.", this.programFilename, s);
/* 289 */         this.shaderSamplers.remove(s);
/* 290 */         this.samplerNames.remove(j);
/* 291 */         j--;
/*     */       }
/*     */       else {
/*     */         
/* 295 */         this.shaderSamplerLocations.add(Integer.valueOf(k));
/*     */       } 
/*     */       
/* 298 */       i++;
/*     */     } 
/*     */     
/* 301 */     for (ShaderUniform shaderuniform : this.shaderUniforms) {
/*     */       
/* 303 */       String s1 = shaderuniform.getShaderName();
/* 304 */       int l = OpenGlHelper.glGetUniformLocation(this.program, s1);
/*     */       
/* 306 */       if (l == -1) {
/*     */         
/* 308 */         LOGGER.warn("Could not find uniform named {} in the specified shader program.", s1);
/*     */         
/*     */         continue;
/*     */       } 
/* 312 */       this.shaderUniformLocations.add(Integer.valueOf(l));
/* 313 */       shaderuniform.setUniformLocation(l);
/* 314 */       this.mappedShaderUniforms.put(s1, shaderuniform);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseSampler(JsonElement element) throws JsonException {
/* 321 */     JsonObject jsonobject = JsonUtils.getJsonObject(element, "sampler");
/* 322 */     String s = JsonUtils.getString(jsonobject, "name");
/*     */     
/* 324 */     if (!JsonUtils.isString(jsonobject, "file")) {
/*     */       
/* 326 */       this.shaderSamplers.put(s, null);
/* 327 */       this.samplerNames.add(s);
/*     */     }
/*     */     else {
/*     */       
/* 331 */       this.samplerNames.add(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSamplerTexture(String name, Object samplerTexture) {
/* 340 */     if (this.shaderSamplers.containsKey(name))
/*     */     {
/* 342 */       this.shaderSamplers.remove(name);
/*     */     }
/*     */     
/* 345 */     this.shaderSamplers.put(name, samplerTexture);
/* 346 */     markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseUniform(JsonElement element) throws JsonException {
/* 351 */     JsonObject jsonobject = JsonUtils.getJsonObject(element, "uniform");
/* 352 */     String s = JsonUtils.getString(jsonobject, "name");
/* 353 */     int i = ShaderUniform.parseType(JsonUtils.getString(jsonobject, "type"));
/* 354 */     int j = JsonUtils.getInt(jsonobject, "count");
/* 355 */     float[] afloat = new float[Math.max(j, 16)];
/* 356 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "values");
/*     */     
/* 358 */     if (jsonarray.size() != j && jsonarray.size() > 1)
/*     */     {
/* 360 */       throw new JsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
/*     */     }
/*     */ 
/*     */     
/* 364 */     int k = 0;
/*     */     
/* 366 */     for (JsonElement jsonelement : jsonarray) {
/*     */ 
/*     */       
/*     */       try {
/* 370 */         afloat[k] = JsonUtils.getFloat(jsonelement, "value");
/*     */       }
/* 372 */       catch (Exception exception) {
/*     */         
/* 374 */         JsonException jsonexception = JsonException.forException(exception);
/* 375 */         jsonexception.prependJsonKey("values[" + k + "]");
/* 376 */         throw jsonexception;
/*     */       } 
/*     */       
/* 379 */       k++;
/*     */     } 
/*     */     
/* 382 */     if (j > 1 && jsonarray.size() == 1)
/*     */     {
/* 384 */       while (k < j) {
/*     */         
/* 386 */         afloat[k] = afloat[0];
/* 387 */         k++;
/*     */       } 
/*     */     }
/*     */     
/* 391 */     int l = (j > 1 && j <= 4 && i < 8) ? (j - 1) : 0;
/* 392 */     ShaderUniform shaderuniform = new ShaderUniform(s, i + l, j, this);
/*     */     
/* 394 */     if (i <= 3) {
/*     */       
/* 396 */       shaderuniform.set((int)afloat[0], (int)afloat[1], (int)afloat[2], (int)afloat[3]);
/*     */     }
/* 398 */     else if (i <= 7) {
/*     */       
/* 400 */       shaderuniform.setSafe(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */     }
/*     */     else {
/*     */       
/* 404 */       shaderuniform.set(afloat);
/*     */     } 
/*     */     
/* 407 */     this.shaderUniforms.add(shaderuniform);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderLoader getVertexShaderLoader() {
/* 413 */     return this.vertexShaderLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShaderLoader getFragmentShaderLoader() {
/* 418 */     return this.fragmentShaderLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProgram() {
/* 423 */     return this.program;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\ShaderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */