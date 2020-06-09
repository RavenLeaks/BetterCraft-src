/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.ISoundEventListener;
/*     */ import net.minecraft.client.audio.SoundEventAccessor;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class GuiSubtitleOverlay
/*     */   extends Gui implements ISoundEventListener {
/*     */   private final Minecraft client;
/*  17 */   private final List<Subtitle> subtitles = Lists.newArrayList();
/*     */   
/*     */   private boolean enabled;
/*     */   
/*     */   public GuiSubtitleOverlay(Minecraft clientIn) {
/*  22 */     this.client = clientIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSubtitles(ScaledResolution resolution) {
/*  27 */     if (!this.enabled && this.client.gameSettings.showSubtitles) {
/*     */       
/*  29 */       this.client.getSoundHandler().addListener(this);
/*  30 */       this.enabled = true;
/*     */     }
/*  32 */     else if (this.enabled && !this.client.gameSettings.showSubtitles) {
/*     */       
/*  34 */       this.client.getSoundHandler().removeListener(this);
/*  35 */       this.enabled = false;
/*     */     } 
/*     */     
/*  38 */     if (this.enabled && !this.subtitles.isEmpty()) {
/*     */       
/*  40 */       GlStateManager.pushMatrix();
/*  41 */       GlStateManager.enableBlend();
/*  42 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  43 */       Vec3d vec3d = new Vec3d(this.client.player.posX, this.client.player.posY + this.client.player.getEyeHeight(), this.client.player.posZ);
/*  44 */       Vec3d vec3d1 = (new Vec3d(0.0D, 0.0D, -1.0D)).rotatePitch(-this.client.player.rotationPitch * 0.017453292F).rotateYaw(-this.client.player.rotationYaw * 0.017453292F);
/*  45 */       Vec3d vec3d2 = (new Vec3d(0.0D, 1.0D, 0.0D)).rotatePitch(-this.client.player.rotationPitch * 0.017453292F).rotateYaw(-this.client.player.rotationYaw * 0.017453292F);
/*  46 */       Vec3d vec3d3 = vec3d1.crossProduct(vec3d2);
/*  47 */       int i = 0;
/*  48 */       int j = 0;
/*  49 */       Iterator<Subtitle> iterator = this.subtitles.iterator();
/*     */       
/*  51 */       while (iterator.hasNext()) {
/*     */         
/*  53 */         Subtitle guisubtitleoverlay$subtitle = iterator.next();
/*     */         
/*  55 */         if (guisubtitleoverlay$subtitle.getStartTime() + 3000L <= Minecraft.getSystemTime()) {
/*     */           
/*  57 */           iterator.remove();
/*     */           
/*     */           continue;
/*     */         } 
/*  61 */         j = Math.max(j, this.client.fontRendererObj.getStringWidth(guisubtitleoverlay$subtitle.getString()));
/*     */       } 
/*     */ 
/*     */       
/*  65 */       j = j + this.client.fontRendererObj.getStringWidth("<") + this.client.fontRendererObj.getStringWidth(" ") + this.client.fontRendererObj.getStringWidth(">") + this.client.fontRendererObj.getStringWidth(" ");
/*     */       
/*  67 */       for (Subtitle guisubtitleoverlay$subtitle1 : this.subtitles) {
/*     */         
/*  69 */         int k = 255;
/*  70 */         String s = guisubtitleoverlay$subtitle1.getString();
/*  71 */         Vec3d vec3d4 = guisubtitleoverlay$subtitle1.getLocation().subtract(vec3d).normalize();
/*  72 */         double d0 = -vec3d3.dotProduct(vec3d4);
/*  73 */         double d1 = -vec3d1.dotProduct(vec3d4);
/*  74 */         boolean flag = (d1 > 0.5D);
/*  75 */         int l = j / 2;
/*  76 */         int i1 = this.client.fontRendererObj.FONT_HEIGHT;
/*  77 */         int j1 = i1 / 2;
/*  78 */         float f = 1.0F;
/*  79 */         int k1 = this.client.fontRendererObj.getStringWidth(s);
/*  80 */         int l1 = MathHelper.floor(MathHelper.clampedLerp(255.0D, 75.0D, ((float)(Minecraft.getSystemTime() - guisubtitleoverlay$subtitle1.getStartTime()) / 3000.0F)));
/*  81 */         int i2 = l1 << 16 | l1 << 8 | l1;
/*  82 */         GlStateManager.pushMatrix();
/*  83 */         GlStateManager.translate(ScaledResolution.getScaledWidth() - l * 1.0F - 2.0F, (ScaledResolution.getScaledHeight() - 30) - (i * (i1 + 1)) * 1.0F, 0.0F);
/*  84 */         GlStateManager.scale(1.0F, 1.0F, 1.0F);
/*  85 */         drawRect(-l - 1, -j1 - 1, l + 1, j1 + 1, -872415232);
/*  86 */         GlStateManager.enableBlend();
/*     */         
/*  88 */         if (!flag)
/*     */         {
/*  90 */           if (d0 > 0.0D) {
/*     */             
/*  92 */             this.client.fontRendererObj.drawString(">", l - this.client.fontRendererObj.getStringWidth(">"), -j1, i2 + -16777216);
/*     */           }
/*  94 */           else if (d0 < 0.0D) {
/*     */             
/*  96 */             this.client.fontRendererObj.drawString("<", -l, -j1, i2 + -16777216);
/*     */           } 
/*     */         }
/*     */         
/* 100 */         this.client.fontRendererObj.drawString(s, -k1 / 2, -j1, i2 + -16777216);
/* 101 */         GlStateManager.popMatrix();
/* 102 */         i++;
/*     */       } 
/*     */       
/* 105 */       GlStateManager.disableBlend();
/* 106 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void soundPlay(ISound soundIn, SoundEventAccessor accessor) {
/* 112 */     if (accessor.getSubtitle() != null) {
/*     */       
/* 114 */       String s = accessor.getSubtitle().getFormattedText();
/*     */       
/* 116 */       if (!this.subtitles.isEmpty())
/*     */       {
/* 118 */         for (Subtitle guisubtitleoverlay$subtitle : this.subtitles) {
/*     */           
/* 120 */           if (guisubtitleoverlay$subtitle.getString().equals(s)) {
/*     */             
/* 122 */             guisubtitleoverlay$subtitle.refresh(new Vec3d(soundIn.getXPosF(), soundIn.getYPosF(), soundIn.getZPosF()));
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       }
/* 128 */       this.subtitles.add(new Subtitle(s, new Vec3d(soundIn.getXPosF(), soundIn.getYPosF(), soundIn.getZPosF())));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public class Subtitle
/*     */   {
/*     */     private final String subtitle;
/*     */     private long startTime;
/*     */     private Vec3d location;
/*     */     
/*     */     public Subtitle(String subtitleIn, Vec3d locationIn) {
/* 140 */       this.subtitle = subtitleIn;
/* 141 */       this.location = locationIn;
/* 142 */       this.startTime = Minecraft.getSystemTime();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getString() {
/* 147 */       return this.subtitle;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getStartTime() {
/* 152 */       return this.startTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public Vec3d getLocation() {
/* 157 */       return this.location;
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh(Vec3d locationIn) {
/* 162 */       this.location = locationIn;
/* 163 */       this.startTime = Minecraft.getSystemTime();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiSubtitleOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */