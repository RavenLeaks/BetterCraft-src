/*    */ package me.nzxter.bettercraft.commands.impl;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.Reader;
/*    */ import java.net.URL;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import me.nzxter.bettercraft.commands.Command;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.json.JSONException;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetApiCommand
/*    */   extends Command
/*    */ {
/*    */   public void execute(String[] args) {
/* 28 */     if (args.length == 0) {
/*    */       try {
/* 30 */         if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
/*    */           
/* 32 */           msg("§7Use Muliplayer Only", true);
/*    */         } else {
/*    */           
/* 35 */           JSONObject jsonObject = getObjectFromWebsite("https://api.mcsrvstat.us/2/" + (Minecraft.getMinecraft().getCurrentServerData()).serverIP);
/*    */           
/* 37 */           String ip = jsonObject.getString("ip");
/* 38 */           int port = jsonObject.getInt("port");
/* 39 */           String hostname = jsonObject.getString("hostname");
/* 40 */           String software = jsonObject.getString("software");
/* 41 */           String version = jsonObject.getString("version");
/*    */           
/* 43 */           msg("", false);
/* 44 */           msg("§eIP: §7" + ip, true);
/* 45 */           msg("§ePort: §7" + port, true);
/* 46 */           msg("§eHostname: §7" + hostname, true);
/* 47 */           msg("§eSoftware: §7" + software, true);
/* 48 */           msg("§eVersion: §7" + version, true);
/* 49 */           msg("", false);
/*    */         }
/*    */       
/* 52 */       } catch (IOException|JSONException e) {
/* 53 */         e.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static JSONObject getObjectFromWebsite(String url) throws IOException, JSONException {
/* 61 */     InputStream inputStream = (new URL(url)).openStream();
/*    */     
/*    */     try {
/* 64 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
/* 65 */       String rawJsonText = read(bufferedReader);
/*    */       
/* 67 */       JSONObject jsonObject = new JSONObject(rawJsonText);
/*    */       
/* 69 */       return jsonObject;
/*    */     } finally {
/* 71 */       inputStream.close();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String read(Reader reader) throws IOException {
/* 76 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     int counter;
/* 78 */     while ((counter = reader.read()) != -1) {
/* 79 */       stringBuilder.append((char)counter);
/*    */     }
/*    */     
/* 82 */     return stringBuilder.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 88 */     return "getapi";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\commands\impl\GetApiCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */