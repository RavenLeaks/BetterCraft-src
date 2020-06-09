/*     */ package me.nzxter.bettercraft.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.nzxter.bettercraft.BetterCraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.UnicodeFont;
/*     */ import org.newdawn.slick.font.effects.ColorEffect;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnicodeFontRendererUtils
/*     */ {
/*     */   public static UnicodeFontRendererUtils getFontOnPC(String name, int size) {
/*  24 */     return getFontOnPC(name, size, 0);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontOnPC(String name, int size, int fontType) {
/*  28 */     return getFontOnPC(name, size, fontType, 0.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontOnPC(String name, int size, int fontType, float kerning) {
/*  32 */     return getFontOnPC(name, size, fontType, kerning, 3.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontOnPC(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
/*  36 */     return new UnicodeFontRendererUtils(new Font(name, fontType, size), kerning, antiAliasingFactor);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontFromAssets(String name, int size) {
/*  40 */     return getFontOnPC(name, size, 0);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontFromAssets(String name, int size, int fontType) {
/*  44 */     return getFontOnPC(name, fontType, size, 0.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontFromAssets(String name, int size, float kerning, int fontType) {
/*  48 */     return getFontFromAssets(name, size, fontType, kerning, 3.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRendererUtils getFontFromAssets(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
/*  52 */     return new UnicodeFontRendererUtils(name, fontType, size, kerning, antiAliasingFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public final int FONT_HEIGHT = 9;
/*  59 */   private final int[] colorCodes = new int[32];
/*     */   private final float kerning;
/*  61 */   private final Map<String, Float> cachedStringWidth = new HashMap<>();
/*     */   private float antiAliasingFactor;
/*     */   private UnicodeFont unicodeFont;
/*     */   
/*     */   public UnicodeFontRendererUtils(String fontName, int fontType, float fontSize, float kerning, float antiAliasingFactor) {
/*  66 */     this.antiAliasingFactor = antiAliasingFactor;
/*     */     try {
/*  68 */       this.unicodeFont = new UnicodeFont(getFontByName(fontName).deriveFont(fontSize * this.antiAliasingFactor));
/*  69 */     } catch (FontFormatException|IOException e) {
/*  70 */       e.printStackTrace();
/*     */     } 
/*  72 */     this.kerning = kerning;
/*     */     
/*  74 */     this.unicodeFont.addAsciiGlyphs();
/*  75 */     this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
/*     */     
/*     */     try {
/*  78 */       this.unicodeFont.loadGlyphs();
/*  79 */     } catch (Exception e) {
/*  80 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  83 */     for (int i = 0; i < 32; i++) {
/*  84 */       int shadow = (i >> 3 & 0x1) * 85;
/*  85 */       int red = (i >> 2 & 0x1) * 170 + shadow;
/*  86 */       int green = (i >> 1 & 0x1) * 170 + shadow;
/*  87 */       int blue = (i & 0x1) * 170 + shadow;
/*     */       
/*  89 */       if (i == 6) {
/*  90 */         red += 85;
/*     */       }
/*     */       
/*  93 */       if (i >= 16) {
/*  94 */         red /= 4;
/*  95 */         green /= 4;
/*  96 */         blue /= 4;
/*     */       } 
/*     */       
/*  99 */       this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   private UnicodeFontRendererUtils(Font font, float kerning, float antiAliasingFactor) {
/* 104 */     this.antiAliasingFactor = antiAliasingFactor;
/* 105 */     this.unicodeFont = new UnicodeFont(new Font(font.getName(), font.getStyle(), (int)(font.getSize() * antiAliasingFactor)));
/* 106 */     this.kerning = kerning;
/*     */     
/* 108 */     this.unicodeFont.addAsciiGlyphs();
/* 109 */     this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
/*     */     
/*     */     try {
/* 112 */       this.unicodeFont.loadGlyphs();
/* 113 */     } catch (Exception e) {
/* 114 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 117 */     for (int i = 0; i < 32; i++) {
/* 118 */       int shadow = (i >> 3 & 0x1) * 85;
/* 119 */       int red = (i >> 2 & 0x1) * 170 + shadow;
/* 120 */       int green = (i >> 1 & 0x1) * 170 + shadow;
/* 121 */       int blue = (i & 0x1) * 170 + shadow;
/*     */       
/* 123 */       if (i == 6) {
/* 124 */         red += 85;
/*     */       }
/*     */       
/* 127 */       if (i >= 16) {
/* 128 */         red /= 4;
/* 129 */         green /= 4;
/* 130 */         blue /= 4;
/*     */       } 
/*     */       
/* 133 */       this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Font getFontByName(String name) throws IOException, FontFormatException {
/* 138 */     return getFontFromInput("textures/font/" + name + ".ttf");
/*     */   }
/*     */   
/*     */   private Font getFontFromInput(String path) throws IOException, FontFormatException {
/* 142 */     return Font.createFont(0, BetterCraft.class.getResourceAsStream(path));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStringScaled(String text, int givenX, int givenY, int color, double givenScale) {
/* 147 */     GL11.glPushMatrix();
/* 148 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 149 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 150 */     drawString(text, 0.0F, 0.0F, color);
/* 151 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawString(String text, float x, float y, int color) {
/* 156 */     if (text == null) {
/* 157 */       return 0;
/*     */     }
/* 159 */     x *= 2.0F;
/* 160 */     y *= 2.0F;
/*     */     
/* 162 */     float originalX = x;
/*     */     
/* 164 */     GL11.glPushMatrix();
/* 165 */     GlStateManager.scale(1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor);
/* 166 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 167 */     x *= this.antiAliasingFactor;
/* 168 */     y *= this.antiAliasingFactor;
/* 169 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 170 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 171 */     float blue = (color & 0xFF) / 255.0F;
/* 172 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 173 */     GlStateManager.color(red, green, blue, alpha);
/*     */     
/* 175 */     boolean blend = GL11.glIsEnabled(3042);
/* 176 */     boolean lighting = GL11.glIsEnabled(2896);
/* 177 */     boolean texture = GL11.glIsEnabled(3553);
/* 178 */     if (!blend)
/* 179 */       GL11.glEnable(3042); 
/* 180 */     if (lighting)
/* 181 */       GL11.glDisable(2896); 
/* 182 */     if (texture) {
/* 183 */       GL11.glDisable(3553);
/*     */     }
/* 185 */     int currentColor = color;
/* 186 */     char[] characters = text.toCharArray();
/*     */     
/* 188 */     int index = 0; byte b; int i; char[] arrayOfChar1;
/* 189 */     for (i = (arrayOfChar1 = characters).length, b = 0; b < i; ) { char c = arrayOfChar1[b];
/* 190 */       if (c == '\r') {
/* 191 */         x = originalX;
/*     */       }
/* 193 */       if (c == '\n') {
/* 194 */         y += getHeight(Character.toString(c)) * 2.0F;
/*     */       }
/* 196 */       if (c != '§' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '§')) {
/*     */         
/* 198 */         this.unicodeFont.drawString(x, y, Character.toString(c), new Color(currentColor));
/* 199 */         x += getWidth(Character.toString(c)) * 2.0F * this.antiAliasingFactor;
/* 200 */       } else if (c == ' ') {
/* 201 */         x += this.unicodeFont.getSpaceWidth();
/* 202 */       } else if (c == '§' && index != characters.length - 1) {
/* 203 */         int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
/* 204 */         if (codeIndex < 0)
/*     */           continue; 
/* 206 */         currentColor = this.colorCodes[codeIndex];
/*     */       } 
/*     */       
/* 209 */       index++; continue;
/*     */       b++; }
/*     */     
/* 212 */     GL11.glScaled(2.0D, 2.0D, 2.0D);
/* 213 */     if (texture)
/* 214 */       GL11.glEnable(3553); 
/* 215 */     if (lighting)
/* 216 */       GL11.glEnable(2896); 
/* 217 */     if (!blend)
/* 218 */       GL11.glDisable(3042); 
/* 219 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 220 */     GL11.glPopMatrix();
/* 221 */     return (int)x / 2;
/*     */   }
/*     */   
/*     */   public int drawStringWithShadow(String text, float x, float y, int color) {
/* 225 */     drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0);
/* 226 */     return drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String text, float x, float y, int color) {
/* 230 */     drawString(text, x - ((int)getWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
/* 235 */     GL11.glPushMatrix();
/* 236 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 237 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 238 */     drawCenteredString(text, 0.0F, 0.0F, color);
/* 239 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
/* 244 */     drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, color);
/* 245 */     drawCenteredString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public float getWidth(String s) {
/* 249 */     if (this.cachedStringWidth.size() > 1000)
/* 250 */       this.cachedStringWidth.clear(); 
/* 251 */     return ((Float)this.cachedStringWidth.computeIfAbsent(s, e -> { float width = 0.0F; String str = StringUtils.stripControlCodes(paramString1); char[] arrayOfChar; int i = (arrayOfChar = str.toCharArray()).length; for (byte b = 0; b < i; b++) { char c = arrayOfChar[b]; width += this.unicodeFont.getWidth(Character.toString(c)) + this.kerning; }  return Float.valueOf(width / 2.0F / this.antiAliasingFactor); })).floatValue();
/*     */   }
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
/*     */   public int getStringWidth(String text) {
/* 264 */     if (text == null) {
/* 265 */       return 0;
/*     */     }
/* 267 */     int i = 0;
/* 268 */     boolean flag = false;
/*     */     
/* 270 */     for (int j = 0; j < text.length(); j++) {
/* 271 */       char c0 = text.charAt(j);
/* 272 */       float k = getWidth(String.valueOf(c0));
/*     */       
/* 274 */       if (k < 0.0F && j < text.length() - 1) {
/* 275 */         j++;
/* 276 */         c0 = text.charAt(j);
/*     */         
/* 278 */         if (c0 != 'l' && c0 != 'L') {
/* 279 */           if (c0 == 'r' || c0 == 'R') {
/* 280 */             flag = false;
/*     */           }
/*     */         } else {
/* 283 */           flag = true;
/*     */         } 
/*     */         
/* 286 */         k = 0.0F;
/*     */       } 
/*     */       
/* 289 */       i = (int)(i + k);
/*     */       
/* 291 */       if (flag && k > 0.0F) {
/* 292 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 296 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCharWidth(char c) {
/* 301 */     return this.unicodeFont.getWidth(String.valueOf(c));
/*     */   }
/*     */   
/*     */   public float getHeight(String s) {
/* 305 */     return this.unicodeFont.getHeight(s) / 2.0F;
/*     */   }
/*     */   
/*     */   public UnicodeFont getFont() {
/* 309 */     return this.unicodeFont;
/*     */   }
/*     */   
/*     */   public String trimStringToWidth(String par1Str, int par2) {
/* 313 */     StringBuilder var4 = new StringBuilder();
/* 314 */     float var5 = 0.0F;
/* 315 */     int var6 = 0;
/* 316 */     int var7 = 1;
/* 317 */     boolean var8 = false;
/* 318 */     boolean var9 = false;
/*     */     
/* 320 */     for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
/* 321 */       char var11 = par1Str.charAt(var10);
/* 322 */       float var12 = getCharWidth(var11);
/*     */       
/* 324 */       if (var8) {
/* 325 */         var8 = false;
/*     */         
/* 327 */         if (var11 != 'l' && var11 != 'L') {
/* 328 */           if (var11 == 'r' || var11 == 'R') {
/* 329 */             var9 = false;
/*     */           }
/*     */         } else {
/* 332 */           var9 = true;
/*     */         } 
/* 334 */       } else if (var12 < 0.0F) {
/* 335 */         var8 = true;
/*     */       } else {
/* 337 */         var5 += var12;
/*     */         
/* 339 */         if (var9) {
/* 340 */           var5++;
/*     */         }
/*     */       } 
/*     */       
/* 344 */       if (var5 > par2) {
/*     */         break;
/*     */       }
/* 347 */       var4.append(var11);
/*     */     } 
/*     */ 
/*     */     
/* 351 */     return var4.toString();
/*     */   }
/*     */   
/*     */   public void drawSplitString(ArrayList<String> lines, int x, int y, int color) {
/* 355 */     drawString(
/* 356 */         String.join("\n\r", (Iterable)lines), 
/* 357 */         x, 
/* 358 */         y, 
/* 359 */         color);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> splitString(String text, int wrapWidth) {
/* 364 */     List<String> lines = new ArrayList<>();
/*     */     
/* 366 */     String[] splitText = text.split(" ");
/* 367 */     StringBuilder currentString = new StringBuilder(); byte b; int i;
/*     */     String[] arrayOfString1;
/* 369 */     for (i = (arrayOfString1 = splitText).length, b = 0; b < i; ) { String word = arrayOfString1[b];
/* 370 */       String potential = currentString + " " + word;
/*     */       
/* 372 */       if (getWidth(potential) >= wrapWidth) {
/* 373 */         lines.add(currentString.toString());
/* 374 */         currentString = new StringBuilder();
/*     */       } 
/* 376 */       currentString.append(word).append(" "); b++; }
/*     */     
/* 378 */     lines.add(currentString.toString());
/* 379 */     return lines;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\UnicodeFontRendererUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */