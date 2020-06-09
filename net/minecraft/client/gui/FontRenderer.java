/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.ibm.icu.text.ArabicShaping;
/*      */ import com.ibm.icu.text.ArabicShapingException;
/*      */ import com.ibm.icu.text.Bidi;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.FontUtils;
/*      */ import optifine.GlBlendState;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FontRenderer
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*   37 */   private static final ResourceLocation[] UNICODE_PAGE_LOCATIONS = new ResourceLocation[256];
/*      */ 
/*      */   
/*   40 */   private final int[] charWidth = new int[256];
/*      */ 
/*      */   
/*   43 */   public int FONT_HEIGHT = 9;
/*   44 */   public Random fontRandom = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   49 */   private final byte[] glyphWidth = new byte[65536];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   55 */   public final int[] colorCode = new int[32];
/*      */ 
/*      */   
/*      */   private ResourceLocation locationFontTexture;
/*      */ 
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */ 
/*      */   
/*      */   private float posX;
/*      */ 
/*      */   
/*      */   private float posY;
/*      */ 
/*      */   
/*      */   private boolean unicodeFlag;
/*      */ 
/*      */   
/*      */   private boolean bidiFlag;
/*      */ 
/*      */   
/*      */   private float red;
/*      */ 
/*      */   
/*      */   private float blue;
/*      */ 
/*      */   
/*      */   private float green;
/*      */ 
/*      */   
/*      */   private float alpha;
/*      */ 
/*      */   
/*      */   private int textColor;
/*      */ 
/*      */   
/*      */   private boolean randomStyle;
/*      */ 
/*      */   
/*      */   private boolean boldStyle;
/*      */ 
/*      */   
/*      */   private boolean italicStyle;
/*      */ 
/*      */   
/*      */   private boolean underlineStyle;
/*      */ 
/*      */   
/*      */   private boolean strikethroughStyle;
/*      */ 
/*      */   
/*      */   public GameSettings gameSettings;
/*      */ 
/*      */   
/*      */   public ResourceLocation locationFontTextureBase;
/*      */   
/*      */   public boolean enabled = true;
/*      */   
/*  113 */   public float offsetBold = 1.0F;
/*  114 */   private float[] charWidthFloat = new float[256];
/*      */   private boolean blend = false;
/*  116 */   private GlBlendState oldBlendState = new GlBlendState();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
/*  122 */     this.gameSettings = gameSettingsIn;
/*  123 */     this.locationFontTextureBase = location;
/*  124 */     this.locationFontTexture = location;
/*  125 */     this.renderEngine = textureManagerIn;
/*  126 */     this.unicodeFlag = unicode;
/*  127 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*  128 */     bindTexture(this.locationFontTexture);
/*      */     
/*  130 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  132 */       int j = (i >> 3 & 0x1) * 85;
/*  133 */       int k = (i >> 2 & 0x1) * 170 + j;
/*  134 */       int l = (i >> 1 & 0x1) * 170 + j;
/*  135 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*      */       
/*  137 */       if (i == 6)
/*      */       {
/*  139 */         k += 85;
/*      */       }
/*      */       
/*  142 */       if (gameSettingsIn.anaglyph) {
/*      */         
/*  144 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/*  145 */         int k1 = (k * 30 + l * 70) / 100;
/*  146 */         int l1 = (k * 30 + i1 * 70) / 100;
/*  147 */         k = j1;
/*  148 */         l = k1;
/*  149 */         i1 = l1;
/*      */       } 
/*      */       
/*  152 */       if (i >= 16) {
/*      */         
/*  154 */         k /= 4;
/*  155 */         l /= 4;
/*  156 */         i1 /= 4;
/*      */       } 
/*      */       
/*  159 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*      */     } 
/*      */     
/*  162 */     readGlyphSizes();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  167 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*      */     
/*  169 */     for (int i = 0; i < UNICODE_PAGE_LOCATIONS.length; i++)
/*      */     {
/*  171 */       UNICODE_PAGE_LOCATIONS[i] = null;
/*      */     }
/*      */     
/*  174 */     readFontTexture();
/*  175 */     readGlyphSizes();
/*      */   }
/*      */   
/*      */   private void readFontTexture() {
/*      */     BufferedImage bufferedimage;
/*  180 */     IResource iresource = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  185 */       iresource = getResource(this.locationFontTexture);
/*  186 */       bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
/*      */     }
/*  188 */     catch (IOException ioexception) {
/*      */       
/*  190 */       throw new RuntimeException(ioexception);
/*      */     }
/*      */     finally {
/*      */       
/*  194 */       IOUtils.closeQuietly((Closeable)iresource);
/*      */     } 
/*      */     
/*  197 */     Properties props = FontUtils.readFontProperties(this.locationFontTexture);
/*  198 */     this.blend = FontUtils.readBoolean(props, "blend", false);
/*  199 */     int imgWidth = bufferedimage.getWidth();
/*  200 */     int imgHeight = bufferedimage.getHeight();
/*  201 */     int charW = imgWidth / 16;
/*  202 */     int charH = imgHeight / 16;
/*  203 */     float kx = imgWidth / 128.0F;
/*  204 */     float boldScaleFactor = Config.limit(kx, 1.0F, 2.0F);
/*  205 */     this.offsetBold = 1.0F / boldScaleFactor;
/*  206 */     float offsetBoldConfig = FontUtils.readFloat(props, "offsetBold", -1.0F);
/*      */     
/*  208 */     if (offsetBoldConfig >= 0.0F)
/*      */     {
/*  210 */       this.offsetBold = offsetBoldConfig;
/*      */     }
/*      */     
/*  213 */     int[] aint = new int[imgWidth * imgHeight];
/*  214 */     bufferedimage.getRGB(0, 0, imgWidth, imgHeight, aint, 0, imgWidth);
/*      */     
/*  216 */     for (int i1 = 0; i1 < 256; i1++) {
/*      */       
/*  218 */       int j1 = i1 % 16;
/*  219 */       int k1 = i1 / 16;
/*  220 */       int l1 = 0;
/*      */       
/*  222 */       for (l1 = charW - 1; l1 >= 0; l1--) {
/*      */         
/*  224 */         int i2 = j1 * charW + l1;
/*  225 */         boolean flag = true;
/*      */         
/*  227 */         for (int j2 = 0; j2 < charH && flag; j2++) {
/*      */           
/*  229 */           int k2 = (k1 * charH + j2) * imgWidth;
/*  230 */           int l2 = aint[i2 + k2];
/*  231 */           int i3 = l2 >> 24 & 0xFF;
/*      */           
/*  233 */           if (i3 > 16)
/*      */           {
/*  235 */             flag = false;
/*      */           }
/*      */         } 
/*      */         
/*  239 */         if (!flag) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  245 */       if (i1 == 65)
/*      */       {
/*  247 */         i1 = i1;
/*      */       }
/*      */       
/*  250 */       if (i1 == 32)
/*      */       {
/*  252 */         if (charW <= 8) {
/*      */           
/*  254 */           l1 = (int)(2.0F * kx);
/*      */         }
/*      */         else {
/*      */           
/*  258 */           l1 = (int)(1.5F * kx);
/*      */         } 
/*      */       }
/*      */       
/*  262 */       this.charWidthFloat[i1] = (l1 + 1) / kx + 1.0F;
/*      */     } 
/*      */     
/*  265 */     FontUtils.readCustomCharWidths(props, this.charWidthFloat);
/*      */     
/*  267 */     for (int j3 = 0; j3 < this.charWidth.length; j3++)
/*      */     {
/*  269 */       this.charWidth[j3] = Math.round(this.charWidthFloat[j3]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void readGlyphSizes() {
/*  275 */     IResource iresource = null;
/*      */ 
/*      */     
/*      */     try {
/*  279 */       iresource = getResource(new ResourceLocation("font/glyph_sizes.bin"));
/*  280 */       iresource.getInputStream().read(this.glyphWidth);
/*      */     }
/*  282 */     catch (IOException ioexception) {
/*      */       
/*  284 */       throw new RuntimeException(ioexception);
/*      */     }
/*      */     finally {
/*      */       
/*  288 */       IOUtils.closeQuietly((Closeable)iresource);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderChar(char ch, boolean italic) {
/*  297 */     if (ch != ' ' && ch != ' ') {
/*      */       
/*  299 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(ch);
/*  300 */       return (i != -1 && !this.unicodeFlag) ? renderDefaultChar(i, italic) : renderUnicodeChar(ch, italic);
/*      */     } 
/*      */ 
/*      */     
/*  304 */     return !this.unicodeFlag ? this.charWidthFloat[ch] : 4.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderDefaultChar(int ch, boolean italic) {
/*  313 */     int i = ch % 16 * 8;
/*  314 */     int j = ch / 16 * 8;
/*  315 */     int k = italic ? 1 : 0;
/*  316 */     bindTexture(this.locationFontTexture);
/*  317 */     float f = this.charWidthFloat[ch];
/*  318 */     float f1 = 7.99F;
/*  319 */     GlStateManager.glBegin(5);
/*  320 */     GlStateManager.glTexCoord2f(i / 128.0F, j / 128.0F);
/*  321 */     GlStateManager.glVertex3f(this.posX + k, this.posY, 0.0F);
/*  322 */     GlStateManager.glTexCoord2f(i / 128.0F, (j + 7.99F) / 128.0F);
/*  323 */     GlStateManager.glVertex3f(this.posX - k, this.posY + 7.99F, 0.0F);
/*  324 */     GlStateManager.glTexCoord2f((i + f1 - 1.0F) / 128.0F, j / 128.0F);
/*  325 */     GlStateManager.glVertex3f(this.posX + f1 - 1.0F + k, this.posY, 0.0F);
/*  326 */     GlStateManager.glTexCoord2f((i + f1 - 1.0F) / 128.0F, (j + 7.99F) / 128.0F);
/*  327 */     GlStateManager.glVertex3f(this.posX + f1 - 1.0F - k, this.posY + 7.99F, 0.0F);
/*  328 */     GlStateManager.glEnd();
/*  329 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   private ResourceLocation getUnicodePageLocation(int page) {
/*  334 */     if (UNICODE_PAGE_LOCATIONS[page] == null) {
/*      */       
/*  336 */       UNICODE_PAGE_LOCATIONS[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(page) }));
/*  337 */       UNICODE_PAGE_LOCATIONS[page] = FontUtils.getHdFontLocation(UNICODE_PAGE_LOCATIONS[page]);
/*      */     } 
/*      */     
/*  340 */     return UNICODE_PAGE_LOCATIONS[page];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadGlyphTexture(int page) {
/*  348 */     bindTexture(getUnicodePageLocation(page));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float renderUnicodeChar(char ch, boolean italic) {
/*  356 */     int i = this.glyphWidth[ch] & 0xFF;
/*      */     
/*  358 */     if (i == 0)
/*      */     {
/*  360 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/*  364 */     int j = ch / 256;
/*  365 */     loadGlyphTexture(j);
/*  366 */     int k = i >>> 4;
/*  367 */     int l = i & 0xF;
/*  368 */     float f = k;
/*  369 */     float f1 = (l + 1);
/*  370 */     float f2 = (ch % 16 * 16) + f;
/*  371 */     float f3 = ((ch & 0xFF) / 16 * 16);
/*  372 */     float f4 = f1 - f - 0.02F;
/*  373 */     float f5 = italic ? 1.0F : 0.0F;
/*      */ 
/*      */     
/*  376 */     GlStateManager.glBegin(5);
/*  377 */     GlStateManager.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/*  378 */     GlStateManager.glVertex3f(this.posX + f5, this.posY, 0.0F);
/*  379 */     GlStateManager.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/*  380 */     GlStateManager.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/*  381 */     GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/*  382 */     GlStateManager.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/*  383 */     GlStateManager.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/*  384 */     GlStateManager.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/*  385 */     GlStateManager.glEnd();
/*      */     
/*  387 */     return (f1 - f) / 2.0F + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawStringWithShadow(String text, float x, float y, int color) {
/*  396 */     return drawString(text, x, y, color, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, double e, double d, int color) {
/*  404 */     return !this.enabled ? 0 : drawString(text, (float)e, (float)d, color, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, float x, float y, int color, boolean dropShadow) {
/*      */     int i;
/*  412 */     enableAlpha();
/*      */     
/*  414 */     if (this.blend) {
/*      */       
/*  416 */       GlStateManager.getBlendState(this.oldBlendState);
/*  417 */       GlStateManager.enableBlend();
/*  418 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*      */     } 
/*      */     
/*  421 */     resetStyles();
/*      */ 
/*      */     
/*  424 */     if (dropShadow) {
/*      */       
/*  426 */       i = renderString(text, x + 1.0F, y + 1.0F, color, true);
/*  427 */       i = Math.max(i, renderString(text, x, y, color, false));
/*      */     }
/*      */     else {
/*      */       
/*  431 */       i = renderString(text, x, y, color, false);
/*      */     } 
/*      */     
/*  434 */     if (this.blend)
/*      */     {
/*  436 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */     
/*  439 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String bidiReorder(String text) {
/*      */     try {
/*  449 */       Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
/*  450 */       bidi.setReorderingMode(0);
/*  451 */       return bidi.writeReordered(2);
/*      */     }
/*  453 */     catch (ArabicShapingException var31) {
/*      */       
/*  455 */       return text;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetStyles() {
/*  464 */     this.randomStyle = false;
/*  465 */     this.boldStyle = false;
/*  466 */     this.italicStyle = false;
/*  467 */     this.underlineStyle = false;
/*  468 */     this.strikethroughStyle = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderStringAtPos(String text, boolean shadow) {
/*  476 */     for (int i = 0; i < text.length(); i++) {
/*      */       
/*  478 */       char c0 = text.charAt(i);
/*      */       
/*  480 */       if (c0 == '§' && i + 1 < text.length()) {
/*      */         
/*  482 */         int l = "0123456789abcdefklmnor".indexOf(String.valueOf(text.charAt(i + 1)).toLowerCase(Locale.ROOT).charAt(0));
/*      */         
/*  484 */         if (l < 16) {
/*      */           
/*  486 */           this.randomStyle = false;
/*  487 */           this.boldStyle = false;
/*  488 */           this.strikethroughStyle = false;
/*  489 */           this.underlineStyle = false;
/*  490 */           this.italicStyle = false;
/*      */           
/*  492 */           if (l < 0 || l > 15)
/*      */           {
/*  494 */             l = 15;
/*      */           }
/*      */           
/*  497 */           if (shadow)
/*      */           {
/*  499 */             l += 16;
/*      */           }
/*      */           
/*  502 */           int i1 = this.colorCode[l];
/*      */           
/*  504 */           if (Config.isCustomColors())
/*      */           {
/*  506 */             i1 = CustomColors.getTextColor(l, i1);
/*      */           }
/*      */           
/*  509 */           this.textColor = i1;
/*  510 */           setColor((i1 >> 16) / 255.0F, (i1 >> 8 & 0xFF) / 255.0F, (i1 & 0xFF) / 255.0F, this.alpha);
/*      */         }
/*  512 */         else if (l == 16) {
/*      */           
/*  514 */           this.randomStyle = true;
/*      */         }
/*  516 */         else if (l == 17) {
/*      */           
/*  518 */           this.boldStyle = true;
/*      */         }
/*  520 */         else if (l == 18) {
/*      */           
/*  522 */           this.strikethroughStyle = true;
/*      */         }
/*  524 */         else if (l == 19) {
/*      */           
/*  526 */           this.underlineStyle = true;
/*      */         }
/*  528 */         else if (l == 20) {
/*      */           
/*  530 */           this.italicStyle = true;
/*      */         }
/*  532 */         else if (l == 21) {
/*      */           
/*  534 */           this.randomStyle = false;
/*  535 */           this.boldStyle = false;
/*  536 */           this.strikethroughStyle = false;
/*  537 */           this.underlineStyle = false;
/*  538 */           this.italicStyle = false;
/*  539 */           setColor(this.red, this.blue, this.green, this.alpha);
/*      */         } 
/*      */         
/*  542 */         i++;
/*      */       }
/*      */       else {
/*      */         
/*  546 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*      */         
/*  548 */         if (this.randomStyle && j != -1) {
/*      */           char c1;
/*  550 */           int k = getCharWidth(c0);
/*      */ 
/*      */ 
/*      */           
/*      */           do {
/*  555 */             j = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/*  556 */             c1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(j);
/*      */           }
/*  558 */           while (k != getCharWidth(c1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  564 */           c0 = c1;
/*      */         } 
/*      */         
/*  567 */         float f1 = (j != -1 && !this.unicodeFlag) ? this.offsetBold : 0.5F;
/*  568 */         boolean flag = ((c0 == '\000' || j == -1 || this.unicodeFlag) && shadow);
/*      */         
/*  570 */         if (flag) {
/*      */           
/*  572 */           this.posX -= f1;
/*  573 */           this.posY -= f1;
/*      */         } 
/*      */         
/*  576 */         float f = renderChar(c0, this.italicStyle);
/*      */         
/*  578 */         if (flag) {
/*      */           
/*  580 */           this.posX += f1;
/*  581 */           this.posY += f1;
/*      */         } 
/*      */         
/*  584 */         if (this.boldStyle) {
/*      */           
/*  586 */           this.posX += f1;
/*      */           
/*  588 */           if (flag) {
/*      */             
/*  590 */             this.posX -= f1;
/*  591 */             this.posY -= f1;
/*      */           } 
/*      */           
/*  594 */           renderChar(c0, this.italicStyle);
/*  595 */           this.posX -= f1;
/*      */           
/*  597 */           if (flag) {
/*      */             
/*  599 */             this.posX += f1;
/*  600 */             this.posY += f1;
/*      */           } 
/*      */           
/*  603 */           f += f1;
/*      */         } 
/*      */         
/*  606 */         doDraw(f);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doDraw(float p_doDraw_1_) {
/*  613 */     if (this.strikethroughStyle) {
/*      */       
/*  615 */       Tessellator tessellator = Tessellator.getInstance();
/*  616 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  617 */       GlStateManager.disableTexture2D();
/*  618 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
/*  619 */       bufferbuilder.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  620 */       bufferbuilder.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/*  621 */       bufferbuilder.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  622 */       bufferbuilder.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/*  623 */       tessellator.draw();
/*  624 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  627 */     if (this.underlineStyle) {
/*      */       
/*  629 */       Tessellator tessellator1 = Tessellator.getInstance();
/*  630 */       BufferBuilder bufferbuilder1 = tessellator1.getBuffer();
/*  631 */       GlStateManager.disableTexture2D();
/*  632 */       bufferbuilder1.begin(7, DefaultVertexFormats.POSITION);
/*  633 */       int i = this.underlineStyle ? -1 : 0;
/*  634 */       bufferbuilder1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  635 */       bufferbuilder1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/*  636 */       bufferbuilder1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  637 */       bufferbuilder1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/*  638 */       tessellator1.draw();
/*  639 */       GlStateManager.enableTexture2D();
/*      */     } 
/*      */     
/*  642 */     this.posX += p_doDraw_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderStringAligned(String text, int x, int y, int width, int color, boolean dropShadow) {
/*  650 */     if (this.bidiFlag) {
/*      */       
/*  652 */       int i = getStringWidth(bidiReorder(text));
/*  653 */       x = x + width - i;
/*      */     } 
/*      */     
/*  656 */     return renderString(text, x, y, color, dropShadow);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int renderString(String text, float x, float y, int color, boolean dropShadow) {
/*  664 */     if (text == null)
/*      */     {
/*  666 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  670 */     if (this.bidiFlag)
/*      */     {
/*  672 */       text = bidiReorder(text);
/*      */     }
/*      */     
/*  675 */     if ((color & 0xFC000000) == 0)
/*      */     {
/*  677 */       color |= 0xFF000000;
/*      */     }
/*      */     
/*  680 */     if (dropShadow)
/*      */     {
/*  682 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*      */     }
/*      */     
/*  685 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/*  686 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/*  687 */     this.green = (color & 0xFF) / 255.0F;
/*  688 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/*  689 */     setColor(this.red, this.blue, this.green, this.alpha);
/*  690 */     this.posX = x;
/*  691 */     this.posY = y;
/*  692 */     renderStringAtPos(text, dropShadow);
/*  693 */     setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*  694 */     return (int)this.posX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStringWidth(String text) {
/*  703 */     if (text == null)
/*      */     {
/*  705 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  709 */     float f = 0.0F;
/*  710 */     boolean flag = false;
/*      */     
/*  712 */     for (int i = 0; i < text.length(); i++) {
/*      */       
/*  714 */       char c0 = text.charAt(i);
/*  715 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  717 */       if (f1 < 0.0F && i < text.length() - 1) {
/*      */         
/*  719 */         i++;
/*  720 */         c0 = text.charAt(i);
/*      */         
/*  722 */         if (c0 != 'l' && c0 != 'L') {
/*      */           
/*  724 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/*  726 */             flag = false;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  731 */           flag = true;
/*      */         } 
/*      */         
/*  734 */         f1 = 0.0F;
/*      */       } 
/*      */       
/*  737 */       f += f1;
/*      */       
/*  739 */       if (flag && f1 > 0.0F)
/*      */       {
/*  741 */         f += this.unicodeFlag ? 1.0F : this.offsetBold;
/*      */       }
/*      */     } 
/*      */     
/*  745 */     return Math.round(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharWidth(char character) {
/*  754 */     return Math.round(getCharWidthFloat(character));
/*      */   }
/*      */ 
/*      */   
/*      */   private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
/*  759 */     if (p_getCharWidthFloat_1_ == '§')
/*      */     {
/*  761 */       return -1.0F;
/*      */     }
/*  763 */     if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != ' ') {
/*      */       
/*  765 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_getCharWidthFloat_1_);
/*      */       
/*  767 */       if (p_getCharWidthFloat_1_ > '\000' && i != -1 && !this.unicodeFlag)
/*      */       {
/*  769 */         return this.charWidthFloat[i];
/*      */       }
/*  771 */       if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
/*      */         
/*  773 */         int j = this.glyphWidth[p_getCharWidthFloat_1_] & 0xFF;
/*  774 */         int k = j >>> 4;
/*  775 */         int l = j & 0xF;
/*  776 */         l++;
/*  777 */         return ((l - k) / 2 + 1);
/*      */       } 
/*      */ 
/*      */       
/*  781 */       return 0.0F;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  786 */     return this.charWidthFloat[32];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width) {
/*  795 */     return trimStringToWidth(text, width, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String trimStringToWidth(String text, int width, boolean reverse) {
/*  803 */     StringBuilder stringbuilder = new StringBuilder();
/*  804 */     float f = 0.0F;
/*  805 */     int i = reverse ? (text.length() - 1) : 0;
/*  806 */     int j = reverse ? -1 : 1;
/*  807 */     boolean flag = false;
/*  808 */     boolean flag1 = false;
/*      */     
/*  810 */     for (int k = i; k >= 0 && k < text.length() && f < width; k += j) {
/*      */       
/*  812 */       char c0 = text.charAt(k);
/*  813 */       float f1 = getCharWidthFloat(c0);
/*      */       
/*  815 */       if (flag) {
/*      */         
/*  817 */         flag = false;
/*      */         
/*  819 */         if (c0 != 'l' && c0 != 'L')
/*      */         {
/*  821 */           if (c0 == 'r' || c0 == 'R')
/*      */           {
/*  823 */             flag1 = false;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  828 */           flag1 = true;
/*      */         }
/*      */       
/*  831 */       } else if (f1 < 0.0F) {
/*      */         
/*  833 */         flag = true;
/*      */       }
/*      */       else {
/*      */         
/*  837 */         f += f1;
/*      */         
/*  839 */         if (flag1)
/*      */         {
/*  841 */           f++;
/*      */         }
/*      */       } 
/*      */       
/*  845 */       if (f > width) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  850 */       if (reverse) {
/*      */         
/*  852 */         stringbuilder.insert(0, c0);
/*      */       }
/*      */       else {
/*      */         
/*  856 */         stringbuilder.append(c0);
/*      */       } 
/*      */     } 
/*      */     
/*  860 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String trimStringNewline(String text) {
/*  868 */     while (text != null && text.endsWith("\n"))
/*      */     {
/*  870 */       text = text.substring(0, text.length() - 1);
/*      */     }
/*      */     
/*  873 */     return text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
/*  881 */     if (this.blend) {
/*      */       
/*  883 */       GlStateManager.getBlendState(this.oldBlendState);
/*  884 */       GlStateManager.enableBlend();
/*  885 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*      */     } 
/*      */     
/*  888 */     resetStyles();
/*  889 */     this.textColor = textColor;
/*  890 */     str = trimStringNewline(str);
/*  891 */     renderSplitString(str, x, y, wrapWidth, false);
/*      */     
/*  893 */     if (this.blend)
/*      */     {
/*  895 */       GlStateManager.setBlendState(this.oldBlendState);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
/*  905 */     for (String s : listFormattedStringToWidth(str, wrapWidth)) {
/*      */       
/*  907 */       renderStringAligned(s, x, y, wrapWidth, this.textColor, addShadow);
/*  908 */       y += this.FONT_HEIGHT;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int splitStringWidth(String str, int maxLength) {
/*  917 */     return this.FONT_HEIGHT * listFormattedStringToWidth(str, maxLength).size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUnicodeFlag(boolean unicodeFlagIn) {
/*  926 */     this.unicodeFlag = unicodeFlagIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUnicodeFlag() {
/*  935 */     return this.unicodeFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBidiFlag(boolean bidiFlagIn) {
/*  943 */     this.bidiFlag = bidiFlagIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
/*  948 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String wrapFormattedStringToWidth(String str, int wrapWidth) {
/*  956 */     if (str.length() <= 1)
/*      */     {
/*  958 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  962 */     int i = sizeStringToWidth(str, wrapWidth);
/*      */     
/*  964 */     if (str.length() <= i)
/*      */     {
/*  966 */       return str;
/*      */     }
/*      */ 
/*      */     
/*  970 */     String s = str.substring(0, i);
/*  971 */     char c0 = str.charAt(i);
/*  972 */     boolean flag = !(c0 != ' ' && c0 != '\n');
/*  973 */     String s1 = String.valueOf(getFormatFromString(s)) + str.substring(i + (flag ? 1 : 0));
/*  974 */     return String.valueOf(s) + "\n" + wrapFormattedStringToWidth(s1, wrapWidth);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int sizeStringToWidth(String str, int wrapWidth) {
/*  984 */     int i = str.length();
/*  985 */     float f = 0.0F;
/*  986 */     int j = 0;
/*  987 */     int k = -1;
/*      */     
/*  989 */     for (boolean flag = false; j < i; j++) {
/*      */       
/*  991 */       char c0 = str.charAt(j);
/*      */       
/*  993 */       switch (c0) {
/*      */         
/*      */         case '\n':
/*  996 */           j--;
/*      */           break;
/*      */         
/*      */         case ' ':
/* 1000 */           k = j;
/*      */         
/*      */         default:
/* 1003 */           f += getCharWidthFloat(c0);
/*      */           
/* 1005 */           if (flag)
/*      */           {
/* 1007 */             f++;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case '§':
/* 1013 */           if (j < i - 1) {
/*      */             
/* 1015 */             j++;
/* 1016 */             char c1 = str.charAt(j);
/*      */             
/* 1018 */             if (c1 != 'l' && c1 != 'L') {
/*      */               
/* 1020 */               if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
/*      */               {
/* 1022 */                 flag = false;
/*      */               }
/*      */               
/*      */               break;
/*      */             } 
/* 1027 */             flag = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */       
/* 1032 */       if (c0 == '\n') {
/*      */ 
/*      */         
/* 1035 */         k = ++j;
/*      */         
/*      */         break;
/*      */       } 
/* 1039 */       if (Math.round(f) > wrapWidth) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1045 */     return (j != i && k != -1 && k < j) ? k : j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatColor(char colorChar) {
/* 1053 */     return !((colorChar < '0' || colorChar > '9') && (colorChar < 'a' || colorChar > 'f') && (colorChar < 'A' || colorChar > 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFormatSpecial(char formatChar) {
/* 1061 */     return !((formatChar < 'k' || formatChar > 'o') && (formatChar < 'K' || formatChar > 'O') && formatChar != 'r' && formatChar != 'R');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getFormatFromString(String text) {
/* 1069 */     String s = "";
/* 1070 */     int i = -1;
/* 1071 */     int j = text.length();
/*      */     
/* 1073 */     while ((i = text.indexOf('§', i + 1)) != -1) {
/*      */       
/* 1075 */       if (i < j - 1) {
/*      */         
/* 1077 */         char c0 = text.charAt(i + 1);
/*      */         
/* 1079 */         if (isFormatColor(c0)) {
/*      */           
/* 1081 */           s = "§" + c0; continue;
/*      */         } 
/* 1083 */         if (isFormatSpecial(c0))
/*      */         {
/* 1085 */           s = String.valueOf(s) + "§" + c0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1090 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBidiFlag() {
/* 1098 */     return this.bidiFlag;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColorCode(char character) {
/* 1103 */     int i = "0123456789abcdef".indexOf(character);
/*      */     
/* 1105 */     if (i >= 0 && i < this.colorCode.length) {
/*      */       
/* 1107 */       int j = this.colorCode[i];
/*      */       
/* 1109 */       if (Config.isCustomColors())
/*      */       {
/* 1111 */         j = CustomColors.getTextColor(i, j);
/*      */       }
/*      */       
/* 1114 */       return j;
/*      */     } 
/*      */ 
/*      */     
/* 1118 */     return 16777215;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setColor(float p_setColor_1_, float p_setColor_2_, float p_setColor_3_, float p_setColor_4_) {
/* 1124 */     GlStateManager.color(p_setColor_1_, p_setColor_2_, p_setColor_3_, p_setColor_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void enableAlpha() {
/* 1129 */     GlStateManager.enableAlpha();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void bindTexture(ResourceLocation p_bindTexture_1_) {
/* 1134 */     this.renderEngine.bindTexture(p_bindTexture_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   protected IResource getResource(ResourceLocation p_getResource_1_) throws IOException {
/* 1139 */     return Minecraft.getMinecraft().getResourceManager().getResource(p_getResource_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int drawString(String text, int x, int y, int color) {
/* 1145 */     return !this.enabled ? 0 : drawString(text, x, y, color, false);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\FontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */