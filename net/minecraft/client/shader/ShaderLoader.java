/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ public class ShaderLoader
/*     */ {
/*     */   private final ShaderType shaderType;
/*     */   private final String shaderFilename;
/*     */   private final int shader;
/*     */   private int shaderAttachCount;
/*     */   
/*     */   private ShaderLoader(ShaderType type, int shaderId, String filename) {
/*  27 */     this.shaderType = type;
/*  28 */     this.shader = shaderId;
/*  29 */     this.shaderFilename = filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public void attachShader(ShaderManager manager) {
/*  34 */     this.shaderAttachCount++;
/*  35 */     OpenGlHelper.glAttachShader(manager.getProgram(), this.shader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShader(ShaderManager manager) {
/*  40 */     this.shaderAttachCount--;
/*     */     
/*  42 */     if (this.shaderAttachCount <= 0) {
/*     */       
/*  44 */       OpenGlHelper.glDeleteShader(this.shader);
/*  45 */       this.shaderType.getLoadedShaders().remove(this.shaderFilename);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getShaderFilename() {
/*  51 */     return this.shaderFilename;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderLoader loadShader(IResourceManager resourceManager, ShaderType type, String filename) throws IOException {
/*  56 */     ShaderLoader shaderloader = (ShaderLoader)type.getLoadedShaders().get(filename);
/*     */     
/*  58 */     if (shaderloader == null) {
/*     */       
/*  60 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + filename + type.getShaderExtension());
/*  61 */       IResource iresource = resourceManager.getResource(resourcelocation);
/*     */ 
/*     */       
/*     */       try {
/*  65 */         byte[] abyte = IOUtils.toByteArray(new BufferedInputStream(iresource.getInputStream()));
/*  66 */         ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
/*  67 */         bytebuffer.put(abyte);
/*  68 */         bytebuffer.position(0);
/*  69 */         int i = OpenGlHelper.glCreateShader(type.getShaderMode());
/*  70 */         OpenGlHelper.glShaderSource(i, bytebuffer);
/*  71 */         OpenGlHelper.glCompileShader(i);
/*     */         
/*  73 */         if (OpenGlHelper.glGetShaderi(i, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
/*     */           
/*  75 */           String s = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(i, 32768));
/*  76 */           JsonException jsonexception = new JsonException("Couldn't compile " + type.getShaderName() + " program: " + s);
/*  77 */           jsonexception.setFilenameAndFlush(resourcelocation.getResourcePath());
/*  78 */           throw jsonexception;
/*     */         } 
/*     */         
/*  81 */         shaderloader = new ShaderLoader(type, i, filename);
/*  82 */         type.getLoadedShaders().put(filename, shaderloader);
/*     */       }
/*     */       finally {
/*     */         
/*  86 */         IOUtils.closeQuietly((Closeable)iresource);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return shaderloader;
/*     */   }
/*     */   
/*     */   public enum ShaderType
/*     */   {
/*  95 */     VERTEX("vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
/*  96 */     FRAGMENT("fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     private final Map<String, ShaderLoader> loadedShaders = Maps.newHashMap();
/*     */     private final String shaderName;
/*     */     
/*     */     ShaderType(String shaderNameIn, String shaderExtensionIn, int shaderModeIn) {
/* 105 */       this.shaderName = shaderNameIn;
/* 106 */       this.shaderExtension = shaderExtensionIn;
/* 107 */       this.shaderMode = shaderModeIn;
/*     */     }
/*     */     private final String shaderExtension; private final int shaderMode;
/*     */     
/*     */     public String getShaderName() {
/* 112 */       return this.shaderName;
/*     */     }
/*     */ 
/*     */     
/*     */     private String getShaderExtension() {
/* 117 */       return this.shaderExtension;
/*     */     }
/*     */ 
/*     */     
/*     */     private int getShaderMode() {
/* 122 */       return this.shaderMode;
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<String, ShaderLoader> getLoadedShaders() {
/* 127 */       return this.loadedShaders;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\ShaderLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */