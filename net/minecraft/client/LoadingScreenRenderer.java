/*     */ package net.minecraft.client;
/*     */ 
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MinecraftError;
/*     */ 
/*     */ public class LoadingScreenRenderer
/*     */   implements IProgressUpdate {
/*  16 */   private String message = "";
/*     */ 
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */ 
/*     */ 
/*     */   
/*  24 */   private String currentlyDisplayedText = "";
/*     */ 
/*     */   
/*  27 */   private long systemTime = Minecraft.getSystemTime();
/*     */   
/*     */   private boolean loadingSuccess;
/*     */   
/*     */   private final ScaledResolution scaledResolution;
/*     */   
/*     */   private final Framebuffer framebuffer;
/*     */   
/*     */   public LoadingScreenRenderer(Minecraft mcIn) {
/*  36 */     this.mc = mcIn;
/*  37 */     this.scaledResolution = new ScaledResolution(mcIn);
/*  38 */     this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
/*  39 */     this.framebuffer.setFramebufferFilter(9728);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetProgressAndMessage(String message) {
/*  48 */     this.loadingSuccess = false;
/*  49 */     displayString(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displaySavingString(String message) {
/*  57 */     this.loadingSuccess = true;
/*  58 */     displayString(message);
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayString(String message) {
/*  63 */     this.currentlyDisplayedText = message;
/*     */     
/*  65 */     if (!this.mc.running) {
/*     */       
/*  67 */       if (!this.loadingSuccess)
/*     */       {
/*  69 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  74 */       GlStateManager.clear(256);
/*  75 */       GlStateManager.matrixMode(5889);
/*  76 */       GlStateManager.loadIdentity();
/*     */       
/*  78 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/*  80 */         int i = this.scaledResolution.getScaleFactor();
/*  81 */         GlStateManager.ortho(0.0D, (ScaledResolution.getScaledWidth() * i), (ScaledResolution.getScaledHeight() * i), 0.0D, 100.0D, 300.0D);
/*     */       }
/*     */       else {
/*     */         
/*  85 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  86 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/*     */       } 
/*     */       
/*  89 */       GlStateManager.matrixMode(5888);
/*  90 */       GlStateManager.loadIdentity();
/*  91 */       GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayLoadingString(String message) {
/* 100 */     if (!this.mc.running) {
/*     */       
/* 102 */       if (!this.loadingSuccess)
/*     */       {
/* 104 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 109 */       this.systemTime = 0L;
/* 110 */       this.message = message;
/* 111 */       setLoadingProgress(-1);
/* 112 */       this.systemTime = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadingProgress(int progress) {
/* 121 */     if (!this.mc.running) {
/*     */       
/* 123 */       if (!this.loadingSuccess)
/*     */       {
/* 125 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 130 */       long i = Minecraft.getSystemTime();
/*     */       
/* 132 */       if (i - this.systemTime >= 100L) {
/*     */         
/* 134 */         this.systemTime = i;
/* 135 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 136 */         int j = scaledresolution.getScaleFactor();
/* 137 */         int k = ScaledResolution.getScaledWidth();
/* 138 */         int l = ScaledResolution.getScaledHeight();
/*     */         
/* 140 */         if (OpenGlHelper.isFramebufferEnabled()) {
/*     */           
/* 142 */           this.framebuffer.framebufferClear();
/*     */         }
/*     */         else {
/*     */           
/* 146 */           GlStateManager.clear(256);
/*     */         } 
/*     */         
/* 149 */         this.framebuffer.bindFramebuffer(false);
/* 150 */         GlStateManager.matrixMode(5889);
/* 151 */         GlStateManager.loadIdentity();
/* 152 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/* 153 */         GlStateManager.matrixMode(5888);
/* 154 */         GlStateManager.loadIdentity();
/* 155 */         GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */         
/* 157 */         if (!OpenGlHelper.isFramebufferEnabled())
/*     */         {
/* 159 */           GlStateManager.clear(16640);
/*     */         }
/*     */         
/* 162 */         Tessellator tessellator = Tessellator.getInstance();
/* 163 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 164 */         this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
/* 165 */         float f = 32.0F;
/* 166 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 167 */         bufferbuilder.pos(0.0D, l, 0.0D).tex(0.0D, (l / 32.0F)).color(64, 64, 64, 255).endVertex();
/* 168 */         bufferbuilder.pos(k, l, 0.0D).tex((k / 32.0F), (l / 32.0F)).color(64, 64, 64, 255).endVertex();
/* 169 */         bufferbuilder.pos(k, 0.0D, 0.0D).tex((k / 32.0F), 0.0D).color(64, 64, 64, 255).endVertex();
/* 170 */         bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 255).endVertex();
/* 171 */         tessellator.draw();
/*     */         
/* 173 */         if (progress >= 0) {
/*     */           
/* 175 */           int i1 = 100;
/* 176 */           int j1 = 2;
/* 177 */           int k1 = k / 2 - 50;
/* 178 */           int l1 = l / 2 + 16;
/* 179 */           GlStateManager.disableTexture2D();
/* 180 */           bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 181 */           bufferbuilder.pos(k1, l1, 0.0D).color(128, 128, 128, 255).endVertex();
/* 182 */           bufferbuilder.pos(k1, (l1 + 2), 0.0D).color(128, 128, 128, 255).endVertex();
/* 183 */           bufferbuilder.pos((k1 + 100), (l1 + 2), 0.0D).color(128, 128, 128, 255).endVertex();
/* 184 */           bufferbuilder.pos((k1 + 100), l1, 0.0D).color(128, 128, 128, 255).endVertex();
/* 185 */           bufferbuilder.pos(k1, l1, 0.0D).color(128, 255, 128, 255).endVertex();
/* 186 */           bufferbuilder.pos(k1, (l1 + 2), 0.0D).color(128, 255, 128, 255).endVertex();
/* 187 */           bufferbuilder.pos((k1 + progress), (l1 + 2), 0.0D).color(128, 255, 128, 255).endVertex();
/* 188 */           bufferbuilder.pos((k1 + progress), l1, 0.0D).color(128, 255, 128, 255).endVertex();
/* 189 */           tessellator.draw();
/* 190 */           GlStateManager.enableTexture2D();
/*     */         } 
/*     */         
/* 193 */         GlStateManager.enableBlend();
/* 194 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 195 */         this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText, ((k - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2), (l / 2 - 4 - 16), 16777215);
/* 196 */         this.mc.fontRendererObj.drawStringWithShadow(this.message, ((k - this.mc.fontRendererObj.getStringWidth(this.message)) / 2), (l / 2 - 4 + 8), 16777215);
/* 197 */         this.framebuffer.unbindFramebuffer();
/*     */         
/* 199 */         if (OpenGlHelper.isFramebufferEnabled())
/*     */         {
/* 201 */           this.framebuffer.framebufferRender(k * j, l * j);
/*     */         }
/*     */         
/* 204 */         this.mc.updateDisplay();
/*     */ 
/*     */         
/*     */         try {
/* 208 */           Thread.yield();
/*     */         }
/* 210 */         catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDoneWorking() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\LoadingScreenRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */