/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiWinGame extends GuiScreen {
/*  25 */   private static final Logger LOGGER = LogManager.getLogger();
/*  26 */   private static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
/*  27 */   private static final ResourceLocation field_194401_g = new ResourceLocation("textures/gui/title/edition.png");
/*  28 */   private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
/*     */   private final boolean field_193980_h;
/*     */   private final Runnable field_193981_i;
/*     */   private float time;
/*     */   private List<String> lines;
/*     */   private int totalScrollLength;
/*  34 */   private float scrollSpeed = 0.5F;
/*     */ 
/*     */   
/*     */   public GuiWinGame(boolean p_i47590_1_, Runnable p_i47590_2_) {
/*  38 */     this.field_193980_h = p_i47590_1_;
/*  39 */     this.field_193981_i = p_i47590_2_;
/*     */     
/*  41 */     if (!p_i47590_1_)
/*     */     {
/*  43 */       this.scrollSpeed = 0.75F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  52 */     this.mc.getMusicTicker().update();
/*  53 */     this.mc.getSoundHandler().update();
/*  54 */     float f = (this.totalScrollLength + this.height + this.height + 24) / this.scrollSpeed;
/*     */     
/*  56 */     if (this.time > f)
/*     */     {
/*  58 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  68 */     if (keyCode == 1)
/*     */     {
/*  70 */       sendRespawnPacket();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendRespawnPacket() {
/*  76 */     this.field_193981_i.run();
/*  77 */     this.mc.displayGuiScreen(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  94 */     if (this.lines == null) {
/*     */       
/*  96 */       this.lines = Lists.newArrayList();
/*  97 */       IResource iresource = null;
/*     */ 
/*     */       
/*     */       try {
/* 101 */         String s = TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
/* 102 */         int i = 274;
/*     */         
/* 104 */         if (this.field_193980_h) {
/*     */           
/* 106 */           iresource = this.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt"));
/* 107 */           InputStream inputstream = iresource.getInputStream();
/* 108 */           BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
/* 109 */           Random random = new Random(8124371L);
/*     */           
/*     */           String s1;
/* 112 */           while ((s1 = bufferedreader.readLine()) != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 117 */             for (s1 = s1.replaceAll("PLAYERNAME", Minecraft.getSession().getUsername()); s1.contains(s); s1 = String.valueOf(s2) + TextFormatting.WHITE + TextFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3) {
/*     */               
/* 119 */               int j = s1.indexOf(s);
/* 120 */               String s2 = s1.substring(0, j);
/* 121 */               String s3 = s1.substring(j + s.length());
/*     */             } 
/*     */             
/* 124 */             this.lines.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s1, 274));
/* 125 */             this.lines.add("");
/*     */           } 
/*     */           
/* 128 */           inputstream.close();
/*     */           
/* 130 */           for (int k = 0; k < 8; k++)
/*     */           {
/* 132 */             this.lines.add("");
/*     */           }
/*     */         } 
/*     */         
/* 136 */         InputStream inputstream1 = this.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream();
/* 137 */         BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(inputstream1, StandardCharsets.UTF_8));
/*     */         
/*     */         String s4;
/* 140 */         while ((s4 = bufferedreader1.readLine()) != null) {
/*     */           
/* 142 */           s4 = s4.replaceAll("PLAYERNAME", Minecraft.getSession().getUsername());
/* 143 */           s4 = s4.replaceAll("\t", "    ");
/* 144 */           this.lines.addAll(this.mc.fontRendererObj.listFormattedStringToWidth(s4, 274));
/* 145 */           this.lines.add("");
/*     */         } 
/*     */         
/* 148 */         inputstream1.close();
/* 149 */         this.totalScrollLength = this.lines.size() * 12;
/*     */       }
/* 151 */       catch (Exception exception) {
/*     */         
/* 153 */         LOGGER.error("Couldn't load credits", exception);
/*     */       }
/*     */       finally {
/*     */         
/* 157 */         IOUtils.closeQuietly((Closeable)iresource);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawWinGameScreen(int p_146575_1_, int p_146575_2_, float p_146575_3_) {
/* 164 */     Tessellator tessellator = Tessellator.getInstance();
/* 165 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 166 */     this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
/* 167 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 168 */     int i = this.width;
/* 169 */     float f = -this.time * 0.5F * this.scrollSpeed;
/* 170 */     float f1 = this.height - this.time * 0.5F * this.scrollSpeed;
/* 171 */     float f2 = 0.015625F;
/* 172 */     float f3 = this.time * 0.02F;
/* 173 */     float f4 = (this.totalScrollLength + this.height + this.height + 24) / this.scrollSpeed;
/* 174 */     float f5 = (f4 - 20.0F - this.time) * 0.005F;
/*     */     
/* 176 */     if (f5 < f3)
/*     */     {
/* 178 */       f3 = f5;
/*     */     }
/*     */     
/* 181 */     if (f3 > 1.0F)
/*     */     {
/* 183 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 186 */     f3 *= f3;
/* 187 */     f3 = f3 * 96.0F / 255.0F;
/* 188 */     bufferbuilder.pos(0.0D, this.height, this.zLevel).tex(0.0D, (f * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
/* 189 */     bufferbuilder.pos(i, this.height, this.zLevel).tex((i * 0.015625F), (f * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
/* 190 */     bufferbuilder.pos(i, 0.0D, this.zLevel).tex((i * 0.015625F), (f1 * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
/* 191 */     bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, (f1 * 0.015625F)).color(f3, f3, f3, 1.0F).endVertex();
/* 192 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 200 */     drawWinGameScreen(mouseX, mouseY, partialTicks);
/* 201 */     Tessellator tessellator = Tessellator.getInstance();
/* 202 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 203 */     int i = 274;
/* 204 */     int j = this.width / 2 - 137;
/* 205 */     int k = this.height + 50;
/* 206 */     this.time += partialTicks;
/* 207 */     float f = -this.time * this.scrollSpeed;
/* 208 */     GlStateManager.pushMatrix();
/* 209 */     GlStateManager.translate(0.0F, f, 0.0F);
/* 210 */     this.mc.getTextureManager().bindTexture(MINECRAFT_LOGO);
/* 211 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 212 */     GlStateManager.enableAlpha();
/* 213 */     drawTexturedModalRect(j, k, 0, 0, 155, 44);
/* 214 */     drawTexturedModalRect(j + 155, k, 0, 45, 155, 44);
/* 215 */     this.mc.getTextureManager().bindTexture(field_194401_g);
/* 216 */     drawModalRectWithCustomSizedTexture(j + 88, k + 37, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);
/* 217 */     GlStateManager.disableAlpha();
/* 218 */     int l = k + 100;
/*     */     
/* 220 */     for (int i1 = 0; i1 < this.lines.size(); i1++) {
/*     */       
/* 222 */       if (i1 == this.lines.size() - 1) {
/*     */         
/* 224 */         float f1 = l + f - (this.height / 2 - 6);
/*     */         
/* 226 */         if (f1 < 0.0F)
/*     */         {
/* 228 */           GlStateManager.translate(0.0F, -f1, 0.0F);
/*     */         }
/*     */       } 
/*     */       
/* 232 */       if (l + f + 12.0F + 8.0F > 0.0F && l + f < this.height) {
/*     */         
/* 234 */         String s = this.lines.get(i1);
/*     */         
/* 236 */         if (s.startsWith("[C]")) {
/*     */           
/* 238 */           this.fontRendererObj.drawStringWithShadow(s.substring(3), (j + (274 - this.fontRendererObj.getStringWidth(s.substring(3))) / 2), l, 16777215);
/*     */         }
/*     */         else {
/*     */           
/* 242 */           this.fontRendererObj.fontRandom.setSeed((long)((float)(i1 * 4238972211L) + this.time / 4.0F));
/* 243 */           this.fontRendererObj.drawStringWithShadow(s, j, l, 16777215);
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       l += 12;
/*     */     } 
/*     */     
/* 250 */     GlStateManager.popMatrix();
/* 251 */     this.mc.getTextureManager().bindTexture(VIGNETTE_TEXTURE);
/* 252 */     GlStateManager.enableBlend();
/* 253 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
/* 254 */     int j1 = this.width;
/* 255 */     int k1 = this.height;
/* 256 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 257 */     bufferbuilder.pos(0.0D, k1, this.zLevel).tex(0.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 258 */     bufferbuilder.pos(j1, k1, this.zLevel).tex(1.0D, 1.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 259 */     bufferbuilder.pos(j1, 0.0D, this.zLevel).tex(1.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 260 */     bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex(0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 261 */     tessellator.draw();
/* 262 */     GlStateManager.disableBlend();
/* 263 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiWinGame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */