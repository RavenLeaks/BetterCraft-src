/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NameUtils
/*    */ {
/*    */   protected static final String UUIDAPI = "https://api.mojang.com/users/profiles/minecraft/";
/*    */   protected static final String PROFILEAPI = "https://api.mojang.com/user/profiles/";
/*    */   
/*    */   public static String getUUID(String playername) {
/* 20 */     JsonObject getUUidObject = (new JsonParser()).parse(infosFromWebsite("https://api.mojang.com/users/profiles/minecraft/" + playername)).getAsJsonObject();
/* 21 */     return getUUidObject.get("id").getAsString();
/*    */   }
/*    */   
/*    */   public static String[] getNameHistory(String uuid) {
/* 25 */     String json = infosFromWebsite("https://api.mojang.com/user/profiles/" + uuid + "/names");
/* 26 */     ArrayList<String> names = new ArrayList<>();
/* 27 */     Gson gson = new Gson();
/* 28 */     NameArrayHookUtils[] nameClass = (NameArrayHookUtils[])gson.fromJson(json, NameArrayHookUtils[].class);
/* 29 */     for (int i = 0; i < nameClass.length; i++) {
/* 30 */       names.add(nameClass[i].getName());
/*    */     }
/* 32 */     return names.<String>toArray(new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String infosFromWebsite(String website) {
/*    */     try {
/* 38 */       StringBuilder stringBuilder = new StringBuilder("");
/* 39 */       URL url = new URL(website);
/* 40 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream())); String line;
/* 41 */       while ((line = bufferedReader.readLine()) != null) {
/* 42 */         stringBuilder.append(line);
/*    */       }
/* 44 */       bufferedReader.close();
/* 45 */       return stringBuilder.toString();
/*    */     }
/* 47 */     catch (Exception e) {
/* 48 */       String line; return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\NameUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */