/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UUIDFetcherUtils
/*    */ {
/*    */   public static final long FEBRUARY_2015 = 1422748800000L;
/* 23 */   private static Gson gson = (new GsonBuilder()).registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
/*    */   private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
/*    */   private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
/* 26 */   private static Map<String, UUID> uuidCache = new HashMap<>();
/* 27 */   private static Map<UUID, String> nameCache = new HashMap<>();
/* 28 */   private static ExecutorService pool = Executors.newCachedThreadPool();
/*    */   private String name;
/*    */   private UUID id;
/*    */   
/*    */   public static void getUUID(String name, Consumer<UUID> action) {
/* 33 */     pool.execute(() -> paramConsumer.accept(getUUID(paramString)));
/*    */   }
/*    */   
/*    */   public static UUID getUUID(String name) {
/* 37 */     return getUUIDAt(name, System.currentTimeMillis());
/*    */   }
/*    */   
/*    */   public static void getUUIDAt(String name, long timestamp, Consumer<UUID> action) {
/* 41 */     pool.execute(() -> paramConsumer.accept(getUUIDAt(paramString, paramLong)));
/*    */   }
/*    */   
/*    */   public static UUID getUUIDAt(String name, long timestamp) {
/* 45 */     if (uuidCache.containsKey(name = name.toLowerCase())) {
/* 46 */       return uuidCache.get(name);
/*    */     }
/*    */     try {
/* 49 */       HttpURLConnection connection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", new Object[] { name, Long.valueOf(timestamp / 1000L) }))).openConnection();
/* 50 */       connection.setReadTimeout(5000);
/* 51 */       UUIDFetcherUtils data = (UUIDFetcherUtils)gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcherUtils.class);
/* 52 */       uuidCache.put(name, data.id);
/* 53 */       nameCache.put(data.id, data.name);
/* 54 */       return data.id;
/*    */     }
/* 56 */     catch (Exception e) {
/* 57 */       e.printStackTrace();
/* 58 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void getName(UUID uuid, Consumer<String> action) {
/* 63 */     pool.execute(() -> paramConsumer.accept(getName(paramUUID)));
/*    */   }
/*    */   
/*    */   public static String getName(UUID uuid) {
/* 67 */     if (nameCache.containsKey(uuid)) {
/* 68 */       return nameCache.get(uuid);
/*    */     }
/*    */     try {
/* 71 */       HttpURLConnection connection = (HttpURLConnection)(new URL(String.format("https://api.mojang.com/user/profiles/%s/names", new Object[] { UUIDTypeAdapter.fromUUID(uuid) }))).openConnection();
/* 72 */       connection.setReadTimeout(5000);
/* 73 */       UUIDFetcherUtils[] nameHistory = (UUIDFetcherUtils[])gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())), UUIDFetcherUtils[].class);
/* 74 */       UUIDFetcherUtils currentNameData = nameHistory[nameHistory.length - 1];
/* 75 */       uuidCache.put(currentNameData.name.toLowerCase(), uuid);
/* 76 */       nameCache.put(uuid, currentNameData.name);
/* 77 */       return currentNameData.name;
/*    */     }
/* 79 */     catch (Exception e) {
/* 80 */       return "Bert";
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\UUIDFetcherUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */