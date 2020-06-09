/*     */ package optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   public static Properties readFontProperties(ResourceLocation p_readFontProperties_0_) {
/*  13 */     String s = p_readFontProperties_0_.getResourcePath();
/*  14 */     Properties properties = new Properties();
/*  15 */     String s1 = ".png";
/*     */     
/*  17 */     if (!s.endsWith(s1))
/*     */     {
/*  19 */       return properties;
/*     */     }
/*     */ 
/*     */     
/*  23 */     String s2 = String.valueOf(s.substring(0, s.length() - s1.length())) + ".properties";
/*     */ 
/*     */     
/*     */     try {
/*  27 */       ResourceLocation resourcelocation = new ResourceLocation(p_readFontProperties_0_.getResourceDomain(), s2);
/*  28 */       InputStream inputstream = Config.getResourceStream(Config.getResourceManager(), resourcelocation);
/*     */       
/*  30 */       if (inputstream == null)
/*     */       {
/*  32 */         return properties;
/*     */       }
/*     */       
/*  35 */       Config.log("Loading " + s2);
/*  36 */       properties.load(inputstream);
/*     */     }
/*  38 */     catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/*  42 */     catch (IOException ioexception) {
/*     */       
/*  44 */       ioexception.printStackTrace();
/*     */     } 
/*     */     
/*  47 */     return properties;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void readCustomCharWidths(Properties p_readCustomCharWidths_0_, float[] p_readCustomCharWidths_1_) {
/*  53 */     for (Object s : p_readCustomCharWidths_0_.keySet()) {
/*     */       
/*  55 */       String s1 = "width.";
/*     */       
/*  57 */       if (((String)s).startsWith(s1)) {
/*     */         
/*  59 */         String s2 = ((String)s).substring(s1.length());
/*  60 */         int i = Config.parseInt(s2, -1);
/*     */         
/*  62 */         if (i >= 0 && i < p_readCustomCharWidths_1_.length) {
/*     */           
/*  64 */           String s3 = p_readCustomCharWidths_0_.getProperty((String)s);
/*  65 */           float f = Config.parseFloat(s3, -1.0F);
/*     */           
/*  67 */           if (f >= 0.0F)
/*     */           {
/*  69 */             p_readCustomCharWidths_1_[i] = f;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float readFloat(Properties p_readFloat_0_, String p_readFloat_1_, float p_readFloat_2_) {
/*  78 */     String s = p_readFloat_0_.getProperty(p_readFloat_1_);
/*     */     
/*  80 */     if (s == null)
/*     */     {
/*  82 */       return p_readFloat_2_;
/*     */     }
/*     */ 
/*     */     
/*  86 */     float f = Config.parseFloat(s, Float.MIN_VALUE);
/*     */     
/*  88 */     if (f == Float.MIN_VALUE) {
/*     */       
/*  90 */       Config.warn("Invalid value for " + p_readFloat_1_ + ": " + s);
/*  91 */       return p_readFloat_2_;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean readBoolean(Properties p_readBoolean_0_, String p_readBoolean_1_, boolean p_readBoolean_2_) {
/* 102 */     String s = p_readBoolean_0_.getProperty(p_readBoolean_1_);
/*     */     
/* 104 */     if (s == null)
/*     */     {
/* 106 */       return p_readBoolean_2_;
/*     */     }
/*     */ 
/*     */     
/* 110 */     String s1 = s.toLowerCase().trim();
/*     */     
/* 112 */     if (!s1.equals("true") && !s1.equals("on")) {
/*     */       
/* 114 */       if (!s1.equals("false") && !s1.equals("off")) {
/*     */         
/* 116 */         Config.warn("Invalid value for " + p_readBoolean_1_ + ": " + s);
/* 117 */         return p_readBoolean_2_;
/*     */       } 
/*     */ 
/*     */       
/* 121 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getHdFontLocation(ResourceLocation p_getHdFontLocation_0_) {
/* 133 */     if (!Config.isCustomFonts())
/*     */     {
/* 135 */       return p_getHdFontLocation_0_;
/*     */     }
/* 137 */     if (p_getHdFontLocation_0_ == null)
/*     */     {
/* 139 */       return p_getHdFontLocation_0_;
/*     */     }
/* 141 */     if (!Config.isMinecraftThread())
/*     */     {
/* 143 */       return p_getHdFontLocation_0_;
/*     */     }
/*     */ 
/*     */     
/* 147 */     String s = p_getHdFontLocation_0_.getResourcePath();
/* 148 */     String s1 = "textures/";
/* 149 */     String s2 = "mcpatcher/";
/*     */     
/* 151 */     if (!s.startsWith(s1))
/*     */     {
/* 153 */       return p_getHdFontLocation_0_;
/*     */     }
/*     */ 
/*     */     
/* 157 */     s = s.substring(s1.length());
/* 158 */     s = String.valueOf(s2) + s;
/* 159 */     ResourceLocation resourcelocation = new ResourceLocation(p_getHdFontLocation_0_.getResourceDomain(), s);
/* 160 */     return Config.hasResource(Config.getResourceManager(), resourcelocation) ? resourcelocation : p_getHdFontLocation_0_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\FontUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */