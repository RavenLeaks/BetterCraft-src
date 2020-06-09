/*     */ package optifine;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class TextureAnimations
/*     */ {
/*  21 */   private static TextureAnimation[] textureAnimations = null;
/*     */ 
/*     */   
/*     */   public static void reset() {
/*  25 */     textureAnimations = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  30 */     textureAnimations = null;
/*  31 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*  32 */     textureAnimations = getTextureAnimations(airesourcepack);
/*     */     
/*  34 */     if (Config.isAnimatedTextures())
/*     */     {
/*  36 */       updateAnimations();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateCustomAnimations() {
/*  42 */     if (textureAnimations != null)
/*     */     {
/*  44 */       if (Config.isAnimatedTextures())
/*     */       {
/*  46 */         updateAnimations();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateAnimations() {
/*  53 */     if (textureAnimations != null)
/*     */     {
/*  55 */       for (int i = 0; i < textureAnimations.length; i++) {
/*     */         
/*  57 */         TextureAnimation textureanimation = textureAnimations[i];
/*  58 */         textureanimation.updateTexture();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureAnimation[] getTextureAnimations(IResourcePack[] p_getTextureAnimations_0_) {
/*  65 */     List list = new ArrayList();
/*     */     
/*  67 */     for (int i = 0; i < p_getTextureAnimations_0_.length; i++) {
/*     */       
/*  69 */       IResourcePack iresourcepack = p_getTextureAnimations_0_[i];
/*  70 */       TextureAnimation[] atextureanimation = getTextureAnimations(iresourcepack);
/*     */       
/*  72 */       if (atextureanimation != null)
/*     */       {
/*  74 */         list.addAll(Arrays.asList(atextureanimation));
/*     */       }
/*     */     } 
/*     */     
/*  78 */     TextureAnimation[] atextureanimation1 = (TextureAnimation[])list.toArray((Object[])new TextureAnimation[list.size()]);
/*  79 */     return atextureanimation1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureAnimation[] getTextureAnimations(IResourcePack p_getTextureAnimations_0_) {
/*  84 */     String[] astring = ResUtils.collectFiles(p_getTextureAnimations_0_, "mcpatcher/anim/", ".properties", (String[])null);
/*     */     
/*  86 */     if (astring.length <= 0)
/*     */     {
/*  88 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  92 */     List<TextureAnimation> list = new ArrayList();
/*     */     
/*  94 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/*  96 */       String s = astring[i];
/*  97 */       Config.dbg("Texture animation: " + s);
/*     */ 
/*     */       
/*     */       try {
/* 101 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 102 */         InputStream inputstream = p_getTextureAnimations_0_.getInputStream(resourcelocation);
/* 103 */         Properties properties = new Properties();
/* 104 */         properties.load(inputstream);
/* 105 */         TextureAnimation textureanimation = makeTextureAnimation(properties, resourcelocation);
/*     */         
/* 107 */         if (textureanimation != null) {
/*     */           
/* 109 */           ResourceLocation resourcelocation1 = new ResourceLocation(textureanimation.getDstTex());
/*     */           
/* 111 */           if (Config.getDefiningResourcePack(resourcelocation1) != p_getTextureAnimations_0_)
/*     */           {
/* 113 */             Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
/*     */           }
/*     */           else
/*     */           {
/* 117 */             list.add(textureanimation);
/*     */           }
/*     */         
/*     */         } 
/* 121 */       } catch (FileNotFoundException filenotfoundexception) {
/*     */         
/* 123 */         Config.warn("File not found: " + filenotfoundexception.getMessage());
/*     */       }
/* 125 */       catch (IOException ioexception) {
/*     */         
/* 127 */         ioexception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     TextureAnimation[] atextureanimation = list.<TextureAnimation>toArray(new TextureAnimation[list.size()]);
/* 132 */     return atextureanimation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextureAnimation makeTextureAnimation(Properties p_makeTextureAnimation_0_, ResourceLocation p_makeTextureAnimation_1_) {
/* 138 */     String s = p_makeTextureAnimation_0_.getProperty("from");
/* 139 */     String s1 = p_makeTextureAnimation_0_.getProperty("to");
/* 140 */     int i = Config.parseInt(p_makeTextureAnimation_0_.getProperty("x"), -1);
/* 141 */     int j = Config.parseInt(p_makeTextureAnimation_0_.getProperty("y"), -1);
/* 142 */     int k = Config.parseInt(p_makeTextureAnimation_0_.getProperty("w"), -1);
/* 143 */     int l = Config.parseInt(p_makeTextureAnimation_0_.getProperty("h"), -1);
/*     */     
/* 145 */     if (s != null && s1 != null) {
/*     */       
/* 147 */       if (i >= 0 && j >= 0 && k >= 0 && l >= 0) {
/*     */         
/* 149 */         s = s.trim();
/* 150 */         s1 = s1.trim();
/* 151 */         String s2 = TextureUtils.getBasePath(p_makeTextureAnimation_1_.getResourcePath());
/* 152 */         s = TextureUtils.fixResourcePath(s, s2);
/* 153 */         s1 = TextureUtils.fixResourcePath(s1, s2);
/* 154 */         byte[] abyte = getCustomTextureData(s, k);
/*     */         
/* 156 */         if (abyte == null) {
/*     */           
/* 158 */           Config.warn("TextureAnimation: Source texture not found: " + s1);
/* 159 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 163 */         int i1 = abyte.length / 4;
/* 164 */         int j1 = i1 / k * l;
/* 165 */         int k1 = j1 * k * l;
/*     */         
/* 167 */         if (i1 != k1) {
/*     */           
/* 169 */           Config.warn("TextureAnimation: Source texture has invalid number of frames: " + s + ", frames: " + (i1 / (k * l)));
/* 170 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 174 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */ 
/*     */         
/*     */         try {
/* 178 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/* 180 */           if (inputstream == null) {
/*     */             
/* 182 */             Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 183 */             return null;
/*     */           } 
/*     */ 
/*     */           
/* 187 */           BufferedImage bufferedimage = readTextureImage(inputstream);
/*     */           
/* 189 */           if (i + k <= bufferedimage.getWidth() && j + l <= bufferedimage.getHeight()) {
/*     */             
/* 191 */             TextureAnimation textureanimation = new TextureAnimation(s, abyte, s1, resourcelocation, i, j, k, l, p_makeTextureAnimation_0_, 1);
/* 192 */             return textureanimation;
/*     */           } 
/*     */ 
/*     */           
/* 196 */           Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + s1);
/* 197 */           return null;
/*     */ 
/*     */         
/*     */         }
/* 201 */         catch (IOException var17) {
/*     */           
/* 203 */           Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 204 */           return null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       Config.warn("TextureAnimation: Invalid coordinates");
/* 212 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 217 */     Config.warn("TextureAnimation: Source or target texture not specified");
/* 218 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getCustomTextureData(String p_getCustomTextureData_0_, int p_getCustomTextureData_1_) {
/* 224 */     byte[] abyte = loadImage(p_getCustomTextureData_0_, p_getCustomTextureData_1_);
/*     */     
/* 226 */     if (abyte == null)
/*     */     {
/* 228 */       abyte = loadImage("/anim" + p_getCustomTextureData_0_, p_getCustomTextureData_1_);
/*     */     }
/*     */     
/* 231 */     return abyte;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] loadImage(String p_loadImage_0_, int p_loadImage_1_) {
/* 236 */     GameSettings gamesettings = Config.getGameSettings();
/*     */ 
/*     */     
/*     */     try {
/* 240 */       ResourceLocation resourcelocation = new ResourceLocation(p_loadImage_0_);
/* 241 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 243 */       if (inputstream == null)
/*     */       {
/* 245 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 249 */       BufferedImage bufferedimage = readTextureImage(inputstream);
/* 250 */       inputstream.close();
/*     */       
/* 252 */       if (bufferedimage == null)
/*     */       {
/* 254 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 258 */       if (p_loadImage_1_ > 0 && bufferedimage.getWidth() != p_loadImage_1_) {
/*     */         
/* 260 */         double d0 = (bufferedimage.getHeight() / bufferedimage.getWidth());
/* 261 */         int j = (int)(p_loadImage_1_ * d0);
/* 262 */         bufferedimage = scaleBufferedImage(bufferedimage, p_loadImage_1_, j);
/*     */       } 
/*     */       
/* 265 */       int k2 = bufferedimage.getWidth();
/* 266 */       int i = bufferedimage.getHeight();
/* 267 */       int[] aint = new int[k2 * i];
/* 268 */       byte[] abyte = new byte[k2 * i * 4];
/* 269 */       bufferedimage.getRGB(0, 0, k2, i, aint, 0, k2);
/*     */       
/* 271 */       for (int k = 0; k < aint.length; k++) {
/*     */         
/* 273 */         int l = aint[k] >> 24 & 0xFF;
/* 274 */         int i1 = aint[k] >> 16 & 0xFF;
/* 275 */         int j1 = aint[k] >> 8 & 0xFF;
/* 276 */         int k1 = aint[k] & 0xFF;
/*     */         
/* 278 */         if (gamesettings != null && gamesettings.anaglyph) {
/*     */           
/* 280 */           int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
/* 281 */           int i2 = (i1 * 30 + j1 * 70) / 100;
/* 282 */           int j2 = (i1 * 30 + k1 * 70) / 100;
/* 283 */           i1 = l1;
/* 284 */           j1 = i2;
/* 285 */           k1 = j2;
/*     */         } 
/*     */         
/* 288 */         abyte[k * 4 + 0] = (byte)i1;
/* 289 */         abyte[k * 4 + 1] = (byte)j1;
/* 290 */         abyte[k * 4 + 2] = (byte)k1;
/* 291 */         abyte[k * 4 + 3] = (byte)l;
/*     */       } 
/*     */       
/* 294 */       return abyte;
/*     */ 
/*     */     
/*     */     }
/* 298 */     catch (FileNotFoundException var18) {
/*     */       
/* 300 */       return null;
/*     */     }
/* 302 */     catch (Exception exception) {
/*     */       
/* 304 */       exception.printStackTrace();
/* 305 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static BufferedImage readTextureImage(InputStream p_readTextureImage_0_) throws IOException {
/* 311 */     BufferedImage bufferedimage = ImageIO.read(p_readTextureImage_0_);
/* 312 */     p_readTextureImage_0_.close();
/* 313 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleBufferedImage(BufferedImage p_scaleBufferedImage_0_, int p_scaleBufferedImage_1_, int p_scaleBufferedImage_2_) {
/* 318 */     BufferedImage bufferedimage = new BufferedImage(p_scaleBufferedImage_1_, p_scaleBufferedImage_2_, 2);
/* 319 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 320 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 321 */     graphics2d.drawImage(p_scaleBufferedImage_0_, 0, 0, p_scaleBufferedImage_1_, p_scaleBufferedImage_2_, null);
/* 322 */     return bufferedimage;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\TextureAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */