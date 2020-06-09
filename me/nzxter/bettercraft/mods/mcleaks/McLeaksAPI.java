/*     */ package me.nzxter.bettercraft.mods.mcleaks;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Objects;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class McLeaksAPI
/*     */ {
/*     */   public static RedeemedSession sessions_mcLeaksSession;
/*     */   
/*     */   public static RedeemedSession redeemSession(String token) throws IOException {
/*  24 */     JsonObject request = new JsonObject();
/*  25 */     request.addProperty("token", token);
/*  26 */     JsonObject response = requestAndResponse("https://auth.mcleaks.net/v1/redeem", request.toString());
/*  27 */     ensureSuccess(response);
/*  28 */     JsonObject result = response.getAsJsonObject("result");
/*  29 */     return new RedeemedSession(result.get("mcname").getAsString(), result.get("session").getAsString(), null);
/*     */   }
/*     */   
/*     */   public static void joinServer(RedeemedSession redeemedSession, String serverHash, InetSocketAddress address) throws IOException {
/*  33 */     JsonObject request = new JsonObject();
/*  34 */     request.addProperty("mcname", redeemedSession.name);
/*  35 */     request.addProperty("session", redeemedSession.session);
/*  36 */     request.addProperty("serverhash", serverHash);
/*  37 */     request.addProperty("server", String.valueOf(String.valueOf(address.getHostName())) + ":" + address.getPort());
/*  38 */     JsonObject response = requestAndResponse("https://auth.mcleaks.net/v1/joinserver", request.toString());
/*  39 */     ensureSuccess(response);
/*     */   }
/*     */   
/*     */   private static void ensureSuccess(JsonObject jsonObject) {
/*  43 */     if (!jsonObject.get("success").getAsBoolean()) {
/*  44 */       throw new IllegalArgumentException(jsonObject.get("errorMessage").getAsString());
/*     */     }
/*     */   }
/*     */   
/*     */   private static JsonObject requestAndResponse(String url, String request) throws IOException {
/*  49 */     HttpsURLConnection urlConnection = (HttpsURLConnection)(new URL(url)).openConnection();
/*  50 */     urlConnection.setDoOutput(true);
/*  51 */     urlConnection.setDoInput(true);
/*  52 */     urlConnection.setRequestMethod("POST");
/*  53 */     urlConnection.setConnectTimeout(15000);
/*  54 */     urlConnection.setReadTimeout(5000);
/*  55 */     urlConnection.setDefaultUseCaches(false);
/*  56 */     urlConnection.setUseCaches(false);
/*  57 */     Throwable t = null;
/*     */     try {
/*  59 */       OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), StandardCharsets.UTF_8);
/*     */       try {
/*  61 */         writer.write(request);
/*     */       } finally {
/*     */         
/*  64 */         if (writer != null) {
/*  65 */           writer.close();
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/*  70 */       if (t == null) {
/*  71 */         Throwable t2 = null;
/*  72 */         t = t2;
/*     */       } else {
/*     */         
/*  75 */         Throwable t2 = null;
/*  76 */         if (t != t2) {
/*  77 */           t.addSuppressed(t2);
/*     */         }
/*     */       } 
/*     */     } 
/*  81 */     return (new JsonParser()).parse(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8)).getAsJsonObject();
/*     */   }
/*     */   
/*     */   public static final class RedeemedSession
/*     */   {
/*     */     public final String name;
/*     */     public final String session;
/*     */     
/*     */     private RedeemedSession(String name, String session) {
/*  90 */       this.name = name;
/*  91 */       this.session = session;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/*  96 */       if (this == o) {
/*  97 */         return true;
/*     */       }
/*  99 */       if (o == null || getClass() != o.getClass()) {
/* 100 */         return false;
/*     */       }
/* 102 */       RedeemedSession that = (RedeemedSession)o;
/* 103 */       return (Objects.equals(this.name, that.name) && Objects.equals(this.session, that.session));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 108 */       return Objects.hash(new Object[] { this.name, this.session });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 113 */       return "RedeemedSession{name='" + this.name + '\'' + ", session='" + this.session + '\'' + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\mcleaks\McLeaksAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */