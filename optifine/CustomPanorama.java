/*     */ package optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class CustomPanorama
/*     */ {
/*  13 */   private static CustomPanoramaProperties customPanoramaProperties = null;
/*  14 */   private static final Random random = new Random();
/*     */ 
/*     */   
/*     */   public static CustomPanoramaProperties getCustomPanoramaProperties() {
/*  18 */     return customPanoramaProperties;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  23 */     customPanoramaProperties = null;
/*  24 */     String[] astring = getPanoramaFolders();
/*     */     
/*  26 */     if (astring.length > 1) {
/*     */       
/*  28 */       Properties[] aproperties = getPanoramaProperties(astring);
/*  29 */       int[] aint = getWeights(aproperties);
/*  30 */       int i = getRandomIndex(aint);
/*  31 */       String s = astring[i];
/*  32 */       Properties properties = aproperties[i];
/*     */       
/*  34 */       if (properties == null)
/*     */       {
/*  36 */         properties = aproperties[0];
/*     */       }
/*     */       
/*  39 */       if (properties == null)
/*     */       {
/*  41 */         properties = new Properties();
/*     */       }
/*     */       
/*  44 */       CustomPanoramaProperties custompanoramaproperties = new CustomPanoramaProperties(s, properties);
/*  45 */       customPanoramaProperties = custompanoramaproperties;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getPanoramaFolders() {
/*  51 */     List<String> list = new ArrayList<>();
/*  52 */     list.add("textures/gui/title/background");
/*     */     
/*  54 */     for (int i = 0; i < 100; i++) {
/*     */       
/*  56 */       String s = "optifine/gui/background" + i;
/*  57 */       String s1 = String.valueOf(s) + "/panorama_0.png";
/*  58 */       ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */       
/*  60 */       if (Config.hasResource(resourcelocation))
/*     */       {
/*  62 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/*  66 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  67 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Properties[] getPanoramaProperties(String[] p_getPanoramaProperties_0_) {
/*  72 */     Properties[] aproperties = new Properties[p_getPanoramaProperties_0_.length];
/*     */     
/*  74 */     for (int i = 0; i < p_getPanoramaProperties_0_.length; i++) {
/*     */       
/*  76 */       String s = p_getPanoramaProperties_0_[i];
/*     */       
/*  78 */       if (i == 0) {
/*     */         
/*  80 */         s = "optifine/gui";
/*     */       }
/*     */       else {
/*     */         
/*  84 */         Config.dbg("CustomPanorama: " + s);
/*     */       } 
/*     */       
/*  87 */       ResourceLocation resourcelocation = new ResourceLocation(String.valueOf(s) + "/background.properties");
/*     */ 
/*     */       
/*     */       try {
/*  91 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/*  93 */         if (inputstream != null)
/*     */         {
/*  95 */           Properties properties = new Properties();
/*  96 */           properties.load(inputstream);
/*  97 */           Config.dbg("CustomPanorama: " + resourcelocation.getResourcePath());
/*  98 */           aproperties[i] = properties;
/*  99 */           inputstream.close();
/*     */         }
/*     */       
/* 102 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     return aproperties;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[] getWeights(Properties[] p_getWeights_0_) {
/* 113 */     int[] aint = new int[p_getWeights_0_.length];
/*     */     
/* 115 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 117 */       Properties properties = p_getWeights_0_[i];
/*     */       
/* 119 */       if (properties == null)
/*     */       {
/* 121 */         properties = p_getWeights_0_[0];
/*     */       }
/*     */       
/* 124 */       if (properties == null) {
/*     */         
/* 126 */         aint[i] = 1;
/*     */       }
/*     */       else {
/*     */         
/* 130 */         String s = properties.getProperty("weight", null);
/* 131 */         aint[i] = Config.parseInt(s, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getRandomIndex(int[] p_getRandomIndex_0_) {
/* 140 */     int i = MathUtils.getSum(p_getRandomIndex_0_);
/* 141 */     int j = random.nextInt(i);
/* 142 */     int k = 0;
/*     */     
/* 144 */     for (int l = 0; l < p_getRandomIndex_0_.length; l++) {
/*     */       
/* 146 */       k += p_getRandomIndex_0_[l];
/*     */       
/* 148 */       if (k > j)
/*     */       {
/* 150 */         return l;
/*     */       }
/*     */     } 
/*     */     
/* 154 */     return p_getRandomIndex_0_.length - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomPanorama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */