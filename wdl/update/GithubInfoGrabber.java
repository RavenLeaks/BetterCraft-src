/*     */ package wdl.update;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLMessageTypes;
/*     */ import wdl.WDLMessages;
/*     */ import wdl.api.IWDLMessageType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GithubInfoGrabber
/*     */ {
/*     */   private static final String USER_AGENT;
/*  29 */   private static final JsonParser PARSER = new JsonParser();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String RELEASE_LIST_LOCATION = "https://api.github.com/repos/Pokechu22/WorldDownloader/releases?per_page=100";
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final File CACHED_RELEASES_FILE = new File(
/*  38 */       (Minecraft.getMinecraft()).mcDataDir, 
/*  39 */       "WorldDownloader_Update_Cache.json");
/*     */   
/*     */   static {
/*  42 */     String mcVersion = WDL.getMinecraftVersionInfo();
/*  43 */     String wdlVersion = "1.11a-beta1";
/*     */     
/*  45 */     USER_AGENT = String.format("WorldDownloader mod by Pokechu22 (Minecraft %s; WDL %s) ", new Object[] {
/*  46 */           mcVersion, wdlVersion
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Release> getReleases() throws Exception {
/*  55 */     JsonArray array = query("https://api.github.com/repos/Pokechu22/WorldDownloader/releases?per_page=100").getAsJsonArray();
/*  56 */     List<Release> returned = new ArrayList<>();
/*  57 */     for (JsonElement element : array) {
/*  58 */       returned.add(new Release(element.getAsJsonObject()));
/*     */     }
/*  60 */     return returned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonElement query(String path) throws Exception {
/*  70 */     InputStream stream = null;
/*     */     try {
/*  72 */       HttpsURLConnection connection = (HttpsURLConnection)(new URL(path))
/*  73 */         .openConnection();
/*     */       
/*  75 */       connection.setRequestProperty("User-Agent", USER_AGENT);
/*  76 */       connection.setRequestProperty("Accept", 
/*  77 */           "application/vnd.github.v3.full+json");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       if (WDL.globalProps.getProperty("UpdateETag") != null) {
/*  83 */         String etag = WDL.globalProps.getProperty("UpdateETag");
/*  84 */         if (!etag.isEmpty()) {
/*  85 */           connection.setRequestProperty("If-None-Match", etag);
/*     */         }
/*     */       } 
/*     */       
/*  89 */       connection.connect();
/*     */       
/*  91 */       if (connection.getResponseCode() == 304) {
/*     */         
/*  93 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/*  94 */             "wdl.messages.updates.usingCachedUpdates", new Object[0]);
/*     */         
/*  96 */         stream = new FileInputStream(CACHED_RELEASES_FILE);
/*  97 */       } else if (connection.getResponseCode() == 200) {
/*     */         
/*  99 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.UPDATE_DEBUG, 
/* 100 */             "wdl.messages.updates.grabingUpdatesFromGithub", new Object[0]);
/*     */         
/* 102 */         stream = connection.getInputStream();
/*     */       } else {
/* 104 */         throw new Exception("Unexpected response while getting " + path + 
/* 105 */             ": " + connection.getResponseCode() + " " + 
/* 106 */             connection.getResponseMessage());
/*     */       } 
/*     */       
/* 109 */       InputStreamReader reader = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       if (stream != null)
/* 149 */         stream.close(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wd\\update\GithubInfoGrabber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */