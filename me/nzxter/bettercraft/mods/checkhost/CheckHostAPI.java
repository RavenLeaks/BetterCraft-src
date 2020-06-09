/*     */ package me.nzxter.bettercraft.mods.checkhost;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostDnsResult;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostHttpResult;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostPingResult;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostTcpResult;
/*     */ import me.nzxter.bettercraft.mods.checkhost.results.CheckHostUdpResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CheckHostAPI
/*     */ {
/*     */   public static CheckHostResult<Map<CheckHostServer, CheckHostPingResult>> createPingRequest(String host, int maxNodes) throws IOException {
/*  28 */     Map.Entry<String, List<CheckHostServer>> entry = sendCheckHostRequest(CheckHostType.PING, host, maxNodes);
/*  29 */     return new CheckHostResult<>(CheckHostType.PING, entry.getKey(), entry.getValue());
/*     */   }
/*     */   
/*     */   public static CheckHostResult<Map<CheckHostServer, CheckHostTcpResult>> createTcpRequest(String host, int maxNodes) throws IOException {
/*  33 */     Map.Entry<String, List<CheckHostServer>> entry = sendCheckHostRequest(CheckHostType.TCP, host, maxNodes);
/*  34 */     return new CheckHostResult<>(CheckHostType.TCP, entry.getKey(), entry.getValue());
/*     */   }
/*     */   
/*     */   public static CheckHostResult<Map<CheckHostServer, CheckHostUdpResult>> createUdpRequest(String host, int maxNodes) throws IOException {
/*  38 */     Map.Entry<String, List<CheckHostServer>> entry = sendCheckHostRequest(CheckHostType.UDP, host, maxNodes);
/*  39 */     return new CheckHostResult<>(CheckHostType.UDP, entry.getKey(), entry.getValue());
/*     */   }
/*     */   
/*     */   public static CheckHostResult<Map<CheckHostServer, CheckHostHttpResult>> createHttpRequest(String host, int maxNodes) throws IOException {
/*  43 */     Map.Entry<String, List<CheckHostServer>> entry = sendCheckHostRequest(CheckHostType.HTTP, host, maxNodes);
/*  44 */     return new CheckHostResult<>(CheckHostType.HTTP, entry.getKey(), entry.getValue());
/*     */   }
/*     */   
/*     */   public static CheckHostResult<Map<CheckHostServer, CheckHostDnsResult>> createDnsRequest(String host, int maxNodes) throws IOException {
/*  48 */     Map.Entry<String, List<CheckHostServer>> entry = sendCheckHostRequest(CheckHostType.DNS, host, maxNodes);
/*  49 */     return new CheckHostResult<>(CheckHostType.DNS, entry.getKey(), entry.getValue());
/*     */   }
/*     */   
/*     */   private static JsonObject performGetRequest(String url) throws IOException {
/*  53 */     HttpURLConnection con = (HttpURLConnection)(new URL(url)).openConnection();
/*  54 */     con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0");
/*  55 */     con.setRequestProperty("Accept", "application/json");
/*  56 */     BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
/*  57 */     String json = "";
/*  58 */     String line = null;
/*  59 */     while ((line = br.readLine()) != null) {
/*  60 */       json = String.valueOf(String.valueOf(json)) + line + System.lineSeparator();
/*     */     }
/*  62 */     br.close();
/*  63 */     JsonObject main = (new JsonParser()).parse(json).getAsJsonObject();
/*  64 */     con.disconnect();
/*  65 */     return main;
/*     */   }
/*     */   
/*     */   private static Map.Entry<String, List<CheckHostServer>> sendCheckHostRequest(CheckHostType type, String host, int maxNodes) throws IOException {
/*  69 */     final JsonObject main = performGetRequest("https://check-host.net/check-" + type.getValue() + "?host=" + URLEncoder.encode(host, "UTF-8") + "&max_nodes=" + maxNodes);
/*  70 */     if (!main.has("nodes")) {
/*  71 */       throw new IOException("Invalid response!");
/*     */     }
/*  73 */     final List<CheckHostServer> servers = new ArrayList<>();
/*  74 */     JsonObject nodes = main.get("nodes").getAsJsonObject();
/*  75 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)nodes.entrySet()) {
/*  76 */       JsonArray list = ((JsonElement)entry.getValue()).getAsJsonArray();
/*  77 */       List<String> infos = new ArrayList<>();
/*  78 */       if (list.size() > 3) {
/*  79 */         for (int i = 3; i < list.size(); i++) {
/*  80 */           infos.add(list.get(i).getAsString());
/*     */         }
/*     */       }
/*  83 */       servers.add(new CheckHostServer(entry.getKey(), list.get(1).getAsString(), list.get(0).getAsString(), list.get(2).getAsString(), infos));
/*     */     } 
/*  85 */     return new Map.Entry<String, List<CheckHostServer>>()
/*     */       {
/*     */         public String getKey() {
/*  88 */           return main.get("request_id").getAsString();
/*     */         }
/*     */ 
/*     */         
/*     */         public List<CheckHostServer> getValue() {
/*  93 */           return servers;
/*     */         }
/*     */ 
/*     */         
/*     */         public List<CheckHostServer> setValue(List<CheckHostServer> value) {
/*  98 */           return servers;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static Map<CheckHostServer, CheckHostPingResult> ping(Map.Entry<String, List<CheckHostServer>> input) throws IOException {
/* 104 */     String id = input.getKey();
/* 105 */     List<CheckHostServer> servers = input.getValue();
/* 106 */     JsonObject main = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(id, "UTF-8"));
/* 107 */     Map<CheckHostServer, CheckHostPingResult> result = new HashMap<>();
/* 108 */     for (int i = 0; i < servers.size(); i++) {
/* 109 */       CheckHostServer server = servers.get(i);
/* 110 */       if (main.has(server.getName()) && 
/* 111 */         !main.get(server.getName()).isJsonNull()) {
/* 112 */         JsonArray ja = main.get(server.getName()).getAsJsonArray();
/* 113 */         for (int k = 0; k < ja.size(); k++) {
/* 114 */           JsonElement elmt = ja.get(k);
/* 115 */           if (elmt.isJsonArray()) {
/* 116 */             JsonArray ja2 = elmt.getAsJsonArray();
/* 117 */             List<CheckHostPingResult.PingEntry> pEntries = new ArrayList<>();
/* 118 */             for (int j = 0; j < ja2.size(); j++) {
/* 119 */               if (ja2.get(j).isJsonArray()) {
/* 120 */                 JsonArray ja3 = ja2.get(j).getAsJsonArray();
/* 121 */                 if (ja3.size() != 2 && ja3.size() != 3) {
/* 122 */                   pEntries.add(new CheckHostPingResult.PingEntry("Unable to resolve domain name.", -1.0D, null));
/*     */                 } else {
/*     */                   
/* 125 */                   String status = ja3.get(0).getAsString();
/* 126 */                   double ping = ja3.get(1).getAsDouble();
/* 127 */                   String addr = null;
/* 128 */                   if (ja3.size() > 2) {
/* 129 */                     addr = ja3.get(2).getAsString();
/*     */                   }
/* 131 */                   CheckHostPingResult.PingEntry pEntry = new CheckHostPingResult.PingEntry(status, ping, addr);
/* 132 */                   pEntries.add(pEntry);
/*     */                 } 
/*     */               } 
/*     */             } 
/* 136 */             result.put(server, new CheckHostPingResult(pEntries));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return result;
/*     */   }
/*     */   
/*     */   static Map<CheckHostServer, CheckHostTcpResult> tcp(Map.Entry<String, List<CheckHostServer>> input) throws IOException {
/* 146 */     String id = input.getKey();
/* 147 */     List<CheckHostServer> servers = input.getValue();
/* 148 */     JsonObject main = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(id, "UTF-8"));
/* 149 */     Map<CheckHostServer, CheckHostTcpResult> result = new HashMap<>();
/* 150 */     for (int i = 0; i < servers.size(); i++) {
/* 151 */       CheckHostServer server = servers.get(i);
/* 152 */       JsonArray ja = null;
/* 153 */       if (main.has(server.getName()) && 
/* 154 */         !main.get(server.getName()).isJsonNull()) {
/* 155 */         ja = main.get(server.getName()).getAsJsonArray();
/* 156 */         if (ja.size() == 1) {
/* 157 */           JsonObject obj = ja.get(0).getAsJsonObject();
/* 158 */           String error = null;
/* 159 */           if (obj.has("error")) {
/* 160 */             error = obj.get("error").getAsString();
/*     */           }
/* 162 */           String addr = null;
/* 163 */           if (obj.has("address")) {
/* 164 */             addr = obj.get("address").getAsString();
/*     */           }
/* 166 */           double ping = 0.0D;
/* 167 */           if (obj.has("time")) {
/* 168 */             ping = obj.get("time").getAsDouble();
/*     */           }
/* 170 */           CheckHostTcpResult res = new CheckHostTcpResult(ping, addr, error);
/* 171 */           result.put(server, res);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 176 */     return result;
/*     */   }
/*     */   
/*     */   static Map<CheckHostServer, CheckHostUdpResult> udp(Map.Entry<String, List<CheckHostServer>> input) throws IOException {
/* 180 */     String id = input.getKey();
/* 181 */     List<CheckHostServer> servers = input.getValue();
/* 182 */     JsonObject main = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(id, "UTF-8"));
/* 183 */     Map<CheckHostServer, CheckHostUdpResult> result = new HashMap<>();
/* 184 */     for (int i = 0; i < servers.size(); i++) {
/* 185 */       CheckHostServer server = servers.get(i);
/* 186 */       JsonArray ja = null;
/* 187 */       if (main.has(server.getName()) && 
/* 188 */         !main.get(server.getName()).isJsonNull()) {
/* 189 */         ja = main.get(server.getName()).getAsJsonArray();
/* 190 */         if (ja.size() == 1) {
/* 191 */           JsonObject obj = ja.get(0).getAsJsonObject();
/* 192 */           String error = null;
/* 193 */           if (obj.has("error")) {
/* 194 */             error = obj.get("error").getAsString();
/*     */           }
/* 196 */           String addr = null;
/* 197 */           if (obj.has("address")) {
/* 198 */             addr = obj.get("address").getAsString();
/*     */           }
/* 200 */           double ping = 0.0D;
/* 201 */           if (obj.has("time")) {
/* 202 */             ping = obj.get("time").getAsDouble();
/*     */           }
/* 204 */           double timeout = 0.0D;
/* 205 */           if (obj.has("timeout")) {
/* 206 */             timeout = obj.get("timeout").getAsDouble();
/*     */           }
/* 208 */           CheckHostUdpResult res = new CheckHostUdpResult(timeout, ping, addr, error);
/* 209 */           result.put(server, res);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 214 */     return result;
/*     */   }
/*     */   
/*     */   static Map<CheckHostServer, CheckHostHttpResult> http(Map.Entry<String, List<CheckHostServer>> input) throws IOException {
/* 218 */     String id = input.getKey();
/* 219 */     List<CheckHostServer> servers = input.getValue();
/* 220 */     JsonObject main = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(id, "UTF-8"));
/* 221 */     Map<CheckHostServer, CheckHostHttpResult> result = new HashMap<>();
/* 222 */     for (int i = 0; i < servers.size(); i++) {
/* 223 */       CheckHostServer server = servers.get(i);
/* 224 */       JsonArray ja = null;
/* 225 */       if (main.has(server.getName()) && 
/* 226 */         !main.get(server.getName()).isJsonNull()) {
/* 227 */         ja = main.get(server.getName()).getAsJsonArray();
/* 228 */         if (ja.size() == 1) {
/* 229 */           ja = ja.get(0).getAsJsonArray();
/* 230 */           double ping = ja.get(1).getAsDouble();
/* 231 */           String status = ja.get(2).getAsString();
/* 232 */           int error = (ja.size() > 3 && ja.get(3).isJsonPrimitive()) ? ja.get(3).getAsInt() : -1;
/* 233 */           if (error != -1) {
/* 234 */             String addr = (ja.size() > 4 && ja.get(4).isJsonPrimitive()) ? ja.get(4).getAsString() : null;
/* 235 */             CheckHostHttpResult res = new CheckHostHttpResult(status, ping, addr, error);
/* 236 */             result.put(server, res);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     return result;
/*     */   }
/*     */   
/*     */   static Map<CheckHostServer, CheckHostDnsResult> dns(Map.Entry<String, List<CheckHostServer>> input) throws IOException {
/* 246 */     String id = input.getKey();
/* 247 */     List<CheckHostServer> servers = input.getValue();
/* 248 */     JsonObject main = performGetRequest("https://check-host.net/check-result/" + URLEncoder.encode(id, "UTF-8"));
/* 249 */     Map<CheckHostServer, CheckHostDnsResult> result = new HashMap<>();
/* 250 */     for (int i = 0; i < servers.size(); i++) {
/* 251 */       CheckHostServer server = servers.get(i);
/* 252 */       JsonArray ja = null;
/* 253 */       if (main.has(server.getName()) && 
/* 254 */         !main.get(server.getName()).isJsonNull()) {
/* 255 */         ja = main.get(server.getName()).getAsJsonArray();
/* 256 */         if (ja.size() == 1) {
/* 257 */           JsonObject obj = ja.get(0).getAsJsonObject();
/* 258 */           Map<String, String[]> domainInfos = (Map)new HashMap<>();
/* 259 */           for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)obj.entrySet()) {
/* 260 */             if (((String)entry.getKey()).equals("TTL") || 
/* 261 */               !((JsonElement)entry.getValue()).isJsonArray()) {
/*     */               continue;
/*     */             }
/* 264 */             JsonArray ja2 = ((JsonElement)entry.getValue()).getAsJsonArray();
/* 265 */             String[] values = new String[ja2.size()];
/* 266 */             for (int k = 0; k < ja2.size(); k++) {
/* 267 */               if (ja2.get(k).isJsonPrimitive()) {
/* 268 */                 values[k] = ja2.get(k).getAsString();
/*     */               }
/*     */             } 
/* 271 */             domainInfos.put(entry.getKey(), values);
/*     */           } 
/*     */           
/* 274 */           CheckHostDnsResult res = new CheckHostDnsResult((obj.has("TTL") && obj.get("TTL").isJsonPrimitive()) ? obj.get("TTL").getAsInt() : -1, domainInfos);
/* 275 */           result.put(server, res);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 280 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\CheckHostAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */