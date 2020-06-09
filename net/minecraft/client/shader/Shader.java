/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public class Shader
/*     */ {
/*     */   private final ShaderManager manager;
/*     */   public final Framebuffer framebufferIn;
/*     */   public final Framebuffer framebufferOut;
/*  20 */   private final List<Object> listAuxFramebuffers = Lists.newArrayList();
/*  21 */   private final List<String> listAuxNames = Lists.newArrayList();
/*  22 */   private final List<Integer> listAuxWidths = Lists.newArrayList();
/*  23 */   private final List<Integer> listAuxHeights = Lists.newArrayList();
/*     */   
/*     */   private Matrix4f projectionMatrix;
/*     */   
/*     */   public Shader(IResourceManager resourceManager, String programName, Framebuffer framebufferInIn, Framebuffer framebufferOutIn) throws JsonException, IOException {
/*  28 */     this.manager = new ShaderManager(resourceManager, programName);
/*  29 */     this.framebufferIn = framebufferInIn;
/*  30 */     this.framebufferOut = framebufferOutIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShader() {
/*  35 */     this.manager.deleteShader();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAuxFramebuffer(String auxName, Object auxFramebufferIn, int width, int height) {
/*  40 */     this.listAuxNames.add(this.listAuxNames.size(), auxName);
/*  41 */     this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), auxFramebufferIn);
/*  42 */     this.listAuxWidths.add(this.listAuxWidths.size(), Integer.valueOf(width));
/*  43 */     this.listAuxHeights.add(this.listAuxHeights.size(), Integer.valueOf(height));
/*     */   }
/*     */ 
/*     */   
/*     */   private void preLoadShader() {
/*  48 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  49 */     GlStateManager.disableBlend();
/*  50 */     GlStateManager.disableDepth();
/*  51 */     GlStateManager.disableAlpha();
/*  52 */     GlStateManager.disableFog();
/*  53 */     GlStateManager.disableLighting();
/*  54 */     GlStateManager.disableColorMaterial();
/*  55 */     GlStateManager.enableTexture2D();
/*  56 */     GlStateManager.bindTexture(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProjectionMatrix(Matrix4f projectionMatrixIn) {
/*  61 */     this.projectionMatrix = projectionMatrixIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadShader(float p_148042_1_) {
/*  66 */     preLoadShader();
/*  67 */     this.framebufferIn.unbindFramebuffer();
/*  68 */     float f = this.framebufferOut.framebufferTextureWidth;
/*  69 */     float f1 = this.framebufferOut.framebufferTextureHeight;
/*  70 */     GlStateManager.viewport(0, 0, (int)f, (int)f1);
/*  71 */     this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);
/*     */     
/*  73 */     for (int i = 0; i < this.listAuxFramebuffers.size(); i++) {
/*     */       
/*  75 */       this.manager.addSamplerTexture(this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
/*  76 */       this.manager.getShaderUniformOrDefault("AuxSize" + i).set(((Integer)this.listAuxWidths.get(i)).intValue(), ((Integer)this.listAuxHeights.get(i)).intValue());
/*     */     } 
/*     */     
/*  79 */     this.manager.getShaderUniformOrDefault("ProjMat").set(this.projectionMatrix);
/*  80 */     this.manager.getShaderUniformOrDefault("InSize").set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
/*  81 */     this.manager.getShaderUniformOrDefault("OutSize").set(f, f1);
/*  82 */     this.manager.getShaderUniformOrDefault("Time").set(p_148042_1_);
/*  83 */     Minecraft minecraft = Minecraft.getMinecraft();
/*  84 */     this.manager.getShaderUniformOrDefault("ScreenSize").set(minecraft.displayWidth, minecraft.displayHeight);
/*  85 */     this.manager.useShader();
/*  86 */     this.framebufferOut.framebufferClear();
/*  87 */     this.framebufferOut.bindFramebuffer(false);
/*  88 */     GlStateManager.depthMask(false);
/*  89 */     GlStateManager.colorMask(true, true, true, true);
/*  90 */     Tessellator tessellator = Tessellator.getInstance();
/*  91 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  92 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  93 */     bufferbuilder.pos(0.0D, f1, 500.0D).color(255, 255, 255, 255).endVertex();
/*  94 */     bufferbuilder.pos(f, f1, 500.0D).color(255, 255, 255, 255).endVertex();
/*  95 */     bufferbuilder.pos(f, 0.0D, 500.0D).color(255, 255, 255, 255).endVertex();
/*  96 */     bufferbuilder.pos(0.0D, 0.0D, 500.0D).color(255, 255, 255, 255).endVertex();
/*  97 */     tessellator.draw();
/*  98 */     GlStateManager.depthMask(true);
/*  99 */     GlStateManager.colorMask(true, true, true, true);
/* 100 */     this.manager.endShader();
/* 101 */     this.framebufferOut.unbindFramebuffer();
/* 102 */     this.framebufferIn.unbindFramebufferTexture();
/*     */     
/* 104 */     for (Object object : this.listAuxFramebuffers) {
/*     */       
/* 106 */       if (object instanceof Framebuffer)
/*     */       {
/* 108 */         ((Framebuffer)object).unbindFramebufferTexture();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ShaderManager getShaderManager() {
/* 115 */     return this.manager;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\shader\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */