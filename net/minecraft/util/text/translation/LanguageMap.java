/*     */ package net.minecraft.util.text.translation;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LanguageMap
/*     */ {
/*  19 */   private static final Pattern NUMERIC_VARIABLE_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   private static final Splitter EQUAL_SIGN_SPLITTER = Splitter.on('=').limit(2);
/*     */ 
/*     */   
/*  27 */   private static final LanguageMap instance = new LanguageMap();
/*  28 */   private final Map<String, String> languageList = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastUpdateTimeInMilliseconds;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LanguageMap() {
/*     */     try {
/*  39 */       InputStream inputstream = LanguageMap.class.getResourceAsStream("/assets/minecraft/lang/en_us.lang");
/*     */       
/*  41 */       for (String s : IOUtils.readLines(inputstream, StandardCharsets.UTF_8)) {
/*     */         
/*  43 */         if (!s.isEmpty() && s.charAt(0) != '#') {
/*     */           
/*  45 */           String[] astring = (String[])Iterables.toArray(EQUAL_SIGN_SPLITTER.split(s), String.class);
/*     */           
/*  47 */           if (astring != null && astring.length == 2) {
/*     */             
/*  49 */             String s1 = astring[0];
/*  50 */             String s2 = NUMERIC_VARIABLE_PATTERN.matcher(astring[1]).replaceAll("%$1s");
/*  51 */             this.languageList.put(s1, s2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  56 */       this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*     */     }
/*  58 */     catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static LanguageMap getInstance() {
/*  69 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void replaceWith(Map<String, String> p_135063_0_) {
/*  77 */     instance.languageList.clear();
/*  78 */     instance.languageList.putAll(p_135063_0_);
/*  79 */     instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String translateKey(String key) {
/*  87 */     return tryTranslateKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String translateKeyFormat(String key, Object... format) {
/*  95 */     String s = tryTranslateKey(key);
/*     */ 
/*     */     
/*     */     try {
/*  99 */       return String.format(s, format);
/*     */     }
/* 101 */     catch (IllegalFormatException var5) {
/*     */       
/* 103 */       return "Format error: " + s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String tryTranslateKey(String key) {
/* 112 */     String s = this.languageList.get(key);
/* 113 */     return (s == null) ? key : s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isKeyTranslated(String key) {
/* 121 */     return this.languageList.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastUpdateTimeInMilliseconds() {
/* 129 */     return this.lastUpdateTimeInMilliseconds;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\translation\LanguageMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */