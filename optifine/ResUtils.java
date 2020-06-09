/*     */ package optifine;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.client.resources.AbstractResourcePack;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResUtils
/*     */ {
/*     */   public static String[] collectFiles(String p_collectFiles_0_, String p_collectFiles_1_) {
/*  24 */     return collectFiles(new String[] { p_collectFiles_0_ }, new String[] { p_collectFiles_1_ });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(String[] p_collectFiles_0_, String[] p_collectFiles_1_) {
/*  29 */     Set<String> set = new LinkedHashSet<>();
/*  30 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  32 */     for (int i = 0; i < airesourcepack.length; i++) {
/*     */       
/*  34 */       IResourcePack iresourcepack = airesourcepack[i];
/*  35 */       String[] astring = collectFiles(iresourcepack, p_collectFiles_0_, p_collectFiles_1_, (String[])null);
/*  36 */       set.addAll(Arrays.asList(astring));
/*     */     } 
/*     */     
/*  39 */     String[] astring1 = set.<String>toArray(new String[set.size()]);
/*  40 */     return astring1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(IResourcePack p_collectFiles_0_, String p_collectFiles_1_, String p_collectFiles_2_, String[] p_collectFiles_3_) {
/*  45 */     return collectFiles(p_collectFiles_0_, new String[] { p_collectFiles_1_ }, new String[] { p_collectFiles_2_ }, p_collectFiles_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(IResourcePack p_collectFiles_0_, String[] p_collectFiles_1_, String[] p_collectFiles_2_) {
/*  50 */     return collectFiles(p_collectFiles_0_, p_collectFiles_1_, p_collectFiles_2_, (String[])null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] collectFiles(IResourcePack p_collectFiles_0_, String[] p_collectFiles_1_, String[] p_collectFiles_2_, String[] p_collectFiles_3_) {
/*  55 */     if (p_collectFiles_0_ instanceof net.minecraft.client.resources.DefaultResourcePack)
/*     */     {
/*  57 */       return collectFilesFixed(p_collectFiles_0_, p_collectFiles_3_);
/*     */     }
/*     */ 
/*     */     
/*  61 */     if (p_collectFiles_0_ instanceof net.minecraft.client.resources.LegacyV2Adapter) {
/*     */       
/*  63 */       IResourcePack iresourcepack = (IResourcePack)Reflector.getFieldValue(p_collectFiles_0_, Reflector.LegacyV2Adapter_pack);
/*     */       
/*  65 */       if (iresourcepack == null) {
/*     */         
/*  67 */         Config.warn("LegacyV2Adapter base resource pack not found: " + p_collectFiles_0_);
/*  68 */         return new String[0];
/*     */       } 
/*     */       
/*  71 */       p_collectFiles_0_ = iresourcepack;
/*     */     } 
/*     */     
/*  74 */     if (!(p_collectFiles_0_ instanceof AbstractResourcePack)) {
/*     */       
/*  76 */       Config.warn("Unknown resource pack type: " + p_collectFiles_0_);
/*  77 */       return new String[0];
/*     */     } 
/*     */ 
/*     */     
/*  81 */     AbstractResourcePack abstractresourcepack = (AbstractResourcePack)p_collectFiles_0_;
/*  82 */     File file1 = abstractresourcepack.resourcePackFile;
/*     */     
/*  84 */     if (file1 == null)
/*     */     {
/*  86 */       return new String[0];
/*     */     }
/*  88 */     if (file1.isDirectory())
/*     */     {
/*  90 */       return collectFilesFolder(file1, "", p_collectFiles_1_, p_collectFiles_2_);
/*     */     }
/*  92 */     if (file1.isFile())
/*     */     {
/*  94 */       return collectFilesZIP(file1, p_collectFiles_1_, p_collectFiles_2_);
/*     */     }
/*     */ 
/*     */     
/*  98 */     Config.warn("Unknown resource pack file: " + file1);
/*  99 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFixed(IResourcePack p_collectFilesFixed_0_, String[] p_collectFilesFixed_1_) {
/* 107 */     if (p_collectFilesFixed_1_ == null)
/*     */     {
/* 109 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 113 */     List<String> list = new ArrayList();
/*     */     
/* 115 */     for (int i = 0; i < p_collectFilesFixed_1_.length; i++) {
/*     */       
/* 117 */       String s = p_collectFilesFixed_1_[i];
/*     */       
/* 119 */       if (!isLowercase(s)) {
/*     */         
/* 121 */         Config.warn("Skipping non-lowercase path: " + s);
/*     */       }
/*     */       else {
/*     */         
/* 125 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/* 127 */         if (p_collectFilesFixed_0_.resourceExists(resourcelocation))
/*     */         {
/* 129 */           list.add(s);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 135 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFolder(File p_collectFilesFolder_0_, String p_collectFilesFolder_1_, String[] p_collectFilesFolder_2_, String[] p_collectFilesFolder_3_) {
/* 141 */     List<String> list = new ArrayList();
/* 142 */     String s = "assets/minecraft/";
/* 143 */     File[] afile = p_collectFilesFolder_0_.listFiles();
/*     */     
/* 145 */     if (afile == null)
/*     */     {
/* 147 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 151 */     for (int i = 0; i < afile.length; i++) {
/*     */       
/* 153 */       File file1 = afile[i];
/*     */       
/* 155 */       if (file1.isFile()) {
/*     */         
/* 157 */         String s3 = String.valueOf(p_collectFilesFolder_1_) + file1.getName();
/*     */         
/* 159 */         if (s3.startsWith(s)) {
/*     */           
/* 161 */           s3 = s3.substring(s.length());
/*     */           
/* 163 */           if (StrUtils.startsWith(s3, p_collectFilesFolder_2_) && StrUtils.endsWith(s3, p_collectFilesFolder_3_))
/*     */           {
/* 165 */             if (!isLowercase(s3))
/*     */             {
/* 167 */               Config.warn("Skipping non-lowercase path: " + s3);
/*     */             }
/*     */             else
/*     */             {
/* 171 */               list.add(s3);
/*     */             }
/*     */           
/*     */           }
/*     */         } 
/* 176 */       } else if (file1.isDirectory()) {
/*     */         
/* 178 */         String s1 = String.valueOf(p_collectFilesFolder_1_) + file1.getName() + "/";
/* 179 */         String[] astring = collectFilesFolder(file1, s1, p_collectFilesFolder_2_, p_collectFilesFolder_3_);
/*     */         
/* 181 */         for (int j = 0; j < astring.length; j++) {
/*     */           
/* 183 */           String s2 = astring[j];
/* 184 */           list.add(s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     String[] astring1 = list.<String>toArray(new String[list.size()]);
/* 190 */     return astring1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesZIP(File p_collectFilesZIP_0_, String[] p_collectFilesZIP_1_, String[] p_collectFilesZIP_2_) {
/* 196 */     List<String> list = new ArrayList();
/* 197 */     String s = "assets/minecraft/";
/*     */ 
/*     */     
/*     */     try {
/* 201 */       ZipFile zipfile = new ZipFile(p_collectFilesZIP_0_);
/* 202 */       Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
/*     */       
/* 204 */       while (enumeration.hasMoreElements()) {
/*     */         
/* 206 */         ZipEntry zipentry = enumeration.nextElement();
/* 207 */         String s1 = zipentry.getName();
/*     */         
/* 209 */         if (s1.startsWith(s)) {
/*     */           
/* 211 */           s1 = s1.substring(s.length());
/*     */           
/* 213 */           if (StrUtils.startsWith(s1, p_collectFilesZIP_1_) && StrUtils.endsWith(s1, p_collectFilesZIP_2_)) {
/*     */             
/* 215 */             if (!isLowercase(s1)) {
/*     */               
/* 217 */               Config.warn("Skipping non-lowercase path: " + s1);
/*     */               
/*     */               continue;
/*     */             } 
/* 221 */             list.add(s1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 227 */       zipfile.close();
/* 228 */       String[] astring = list.<String>toArray(new String[list.size()]);
/* 229 */       return astring;
/*     */     }
/* 231 */     catch (IOException ioexception) {
/*     */       
/* 233 */       ioexception.printStackTrace();
/* 234 */       return new String[0];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isLowercase(String p_isLowercase_0_) {
/* 240 */     return p_isLowercase_0_.equals(p_isLowercase_0_.toLowerCase(Locale.ROOT));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ResUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */