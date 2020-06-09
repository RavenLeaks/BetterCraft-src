/*     */ package me.nzxter.bettercraft.utils;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeoUtils
/*     */ {
/*     */   private static GeoUtils instance;
/*     */   public JsonObject object;
/*  18 */   public String server = "";
/*     */   
/*     */   public GeoUtils(String ip) {
/*  21 */     instance = this;
/*  22 */     this.server = "http://ip-api.com/json/" + ip + "?fields=status,message,continent,continentCode,country,countryCode,region,regionName,city,district,zip,lat,lon,timezone,currency,isp,org,as,asname,reverse,mobile,proxy,query";
/*  23 */     this.object = (new JsonParser()).parse(websitedata(this.server)).getAsJsonObject();
/*     */   }
/*     */   
/*     */   public String getAS() {
/*  27 */     return getObjectString("as");
/*     */   }
/*     */   
/*     */   public String getASNAME() {
/*  31 */     return getObjectString("asname");
/*     */   }
/*     */   
/*     */   public String getCITY() {
/*  35 */     return getObjectString("city");
/*     */   }
/*     */   
/*     */   public String getCONTINENT() {
/*  39 */     return getObjectString("continent");
/*     */   }
/*     */   
/*     */   public String getCONTINENTCODE() {
/*  43 */     return getObjectString("continentCode");
/*     */   }
/*     */   
/*     */   public String getCOUNTRY() {
/*  47 */     return getObjectString("country");
/*     */   }
/*     */   
/*     */   public String getCOUNTRYCODE() {
/*  51 */     return getObjectString("countryCode");
/*     */   }
/*     */   
/*     */   public String getDISTRICT() {
/*  55 */     return getObjectString("district");
/*     */   }
/*     */   
/*     */   public static GeoUtils getInstance() {
/*  59 */     return instance;
/*     */   }
/*     */   
/*     */   public String getISP() {
/*  63 */     return getObjectString("isp");
/*     */   }
/*     */   
/*     */   public String getObjectString(String obj) {
/*     */     try {
/*  68 */       return this.object.get(obj).getAsString();
/*  69 */     } catch (Exception e) {
/*  70 */       return "Pinging...";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getORG() {
/*  75 */     return getObjectString("org");
/*     */   }
/*     */   
/*     */   public String getQUERY() {
/*  79 */     return getObjectString("query");
/*     */   }
/*     */   
/*     */   public String getREGIONNAME() {
/*  83 */     return getObjectString("regionName");
/*     */   }
/*     */   
/*     */   public String getREVERSE() {
/*  87 */     return getObjectString("reverse");
/*     */   }
/*     */   
/*     */   public String getTIMEZONE() {
/*  91 */     return getObjectString("timezone");
/*     */   }
/*     */   
/*     */   public boolean isRight(String obj) {
/*  95 */     return (!obj.isEmpty() && !obj.contains("Unknown"));
/*     */   }
/*     */ 
/*     */   
/*     */   public String websitedata(String website) {
/*     */     try {
/* 101 */       URL url = new URL(website);
/* 102 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
/* 103 */       String content = format(bufferedReader.lines().collect((Collector)Collectors.toList()));
/* 104 */       bufferedReader.close();
/* 105 */       return content;
/* 106 */     } catch (Exception e) {
/* 107 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String format(List<String> arrayList) {
/* 112 */     String out = "";
/* 113 */     for (String entry : arrayList) {
/* 114 */       out = String.valueOf(out) + entry + "\n";
/*     */     }
/* 116 */     return out;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\GeoUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */