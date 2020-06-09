/*     */ package me.nzxter.bettercraft.commands.impl;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import me.nzxter.bettercraft.commands.Command;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
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
/*     */ public class GetServerGeoCommand
/*     */   extends Command
/*     */ {
/*     */   public void execute(String[] args) {
/*  28 */     if (args.length == 0) {
/*     */       try {
/*  30 */         if (Minecraft.getMinecraft().isIntegratedServerRunning())
/*     */         {
/*  32 */           JSONObject jsonObject = getObjectFromWebsite("http://ip-api.com/json/");
/*     */           
/*  34 */           String organisation = jsonObject.getString("org");
/*  35 */           String country = jsonObject.getString("country");
/*  36 */           String city = jsonObject.getString("city");
/*  37 */           String region = jsonObject.getString("regionName");
/*  38 */           String as = jsonObject.getString("as");
/*  39 */           String isp = jsonObject.getString("isp");
/*  40 */           String timeZone = jsonObject.getString("timezone");
/*  41 */           String ip = jsonObject.getString("query");
/*  42 */           String cc = jsonObject.getString("countryCode");
/*     */           
/*  44 */           msg("", false);
/*  45 */           msg("§eOrganisation: §7" + organisation, true);
/*  46 */           msg("§eCountry: §7" + country, true);
/*  47 */           msg("§eCity: §7" + city, true);
/*  48 */           msg("§eRegion: §7" + region, true);
/*  49 */           msg("§eAS: §7" + as, true);
/*  50 */           msg("§eISP: §7" + isp, true);
/*  51 */           msg("§eTimezone: §7" + timeZone, true);
/*  52 */           msg("§eIP: §7" + ip, true);
/*  53 */           msg("§eCountry Code: §7" + cc, true);
/*  54 */           msg("", false);
/*     */         }
/*     */         else
/*     */         {
/*  58 */           JSONObject jsonObject = getObjectFromWebsite("http://ip-api.com/json/" + (Minecraft.getMinecraft().getCurrentServerData()).serverIP.toLowerCase());
/*     */           
/*  60 */           String organisation = jsonObject.getString("org");
/*  61 */           String country = jsonObject.getString("country");
/*  62 */           String city = jsonObject.getString("city");
/*  63 */           String region = jsonObject.getString("regionName");
/*  64 */           String as = jsonObject.getString("as");
/*  65 */           String isp = jsonObject.getString("isp");
/*  66 */           String timeZone = jsonObject.getString("timezone");
/*  67 */           String ip = jsonObject.getString("query");
/*  68 */           String cc = jsonObject.getString("countryCode");
/*     */           
/*  70 */           msg("", false);
/*  71 */           msg("§eOrganisation: §7" + organisation, true);
/*  72 */           msg("§eCountry: §7" + country, true);
/*  73 */           msg("§eCity: §7" + city, true);
/*  74 */           msg("§eRegion: §7" + region, true);
/*  75 */           msg("§eAS: §7" + as, true);
/*  76 */           msg("§eISP: §7" + isp, true);
/*  77 */           msg("§eTimezone: §7" + timeZone, true);
/*  78 */           msg("§eIP: §7" + ip, true);
/*  79 */           msg("§eCountry Code: §7" + cc, true);
/*  80 */           msg("", false);
/*     */         }
/*     */       
/*  83 */       } catch (IOException|JSONException iOException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject getObjectFromWebsite(String url) throws IOException, JSONException {
/*  91 */     InputStream inputStream = (new URL(url)).openStream();
/*     */     
/*     */     try {
/*  94 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
/*  95 */       String rawJsonText = read(bufferedReader);
/*     */       
/*  97 */       JSONObject jsonObject = new JSONObject(rawJsonText);
/*     */       
/*  99 */       return jsonObject;
/*     */     } finally {
/* 101 */       inputStream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String read(Reader reader) throws IOException {
/* 106 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     int counter;
/* 108 */     while ((counter = reader.read()) != -1) {
/* 109 */       stringBuilder.append((char)counter);
/*     */     }
/*     */     
/* 112 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return "getgeo";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\GetServerGeoCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */