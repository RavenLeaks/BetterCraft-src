/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ 
/*     */ 
/*     */ public class ServerStatusResponse
/*     */ {
/*     */   private ITextComponent description;
/*     */   private Players players;
/*     */   private Version version;
/*     */   private String favicon;
/*     */   
/*     */   public ITextComponent getServerDescription() {
/*  26 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setServerDescription(ITextComponent descriptionIn) {
/*  31 */     this.description = descriptionIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Players getPlayers() {
/*  36 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayers(Players playersIn) {
/*  41 */     this.players = playersIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Version getVersion() {
/*  46 */     return this.version;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVersion(Version versionIn) {
/*  51 */     this.version = versionIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFavicon(String faviconBlob) {
/*  56 */     this.favicon = faviconBlob;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFavicon() {
/*  61 */     return this.favicon;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Players
/*     */   {
/*     */     private final int maxPlayers;
/*     */     private final int onlinePlayerCount;
/*     */     private GameProfile[] players;
/*     */     
/*     */     public Players(int maxOnlinePlayers, int onlinePlayers) {
/*  72 */       this.maxPlayers = maxOnlinePlayers;
/*  73 */       this.onlinePlayerCount = onlinePlayers;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPlayers() {
/*  78 */       return this.maxPlayers;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOnlinePlayerCount() {
/*  83 */       return this.onlinePlayerCount;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile[] getPlayers() {
/*  88 */       return this.players;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setPlayers(GameProfile[] playersIn) {
/*  93 */       this.players = playersIn;
/*     */     }
/*     */     
/*     */     public static class Serializer
/*     */       implements JsonDeserializer<Players>, JsonSerializer<Players>
/*     */     {
/*     */       public ServerStatusResponse.Players deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 100 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "players");
/* 101 */         ServerStatusResponse.Players serverstatusresponse$players = new ServerStatusResponse.Players(JsonUtils.getInt(jsonobject, "max"), JsonUtils.getInt(jsonobject, "online"));
/*     */         
/* 103 */         if (JsonUtils.isJsonArray(jsonobject, "sample")) {
/*     */           
/* 105 */           JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sample");
/*     */           
/* 107 */           if (jsonarray.size() > 0) {
/*     */             
/* 109 */             GameProfile[] agameprofile = new GameProfile[jsonarray.size()];
/*     */             
/* 111 */             for (int i = 0; i < agameprofile.length; i++) {
/*     */               
/* 113 */               JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonarray.get(i), "player[" + i + "]");
/* 114 */               String s = JsonUtils.getString(jsonobject1, "id");
/* 115 */               agameprofile[i] = new GameProfile(UUID.fromString(s), JsonUtils.getString(jsonobject1, "name"));
/*     */             } 
/*     */             
/* 118 */             serverstatusresponse$players.setPlayers(agameprofile);
/*     */           } 
/*     */         } 
/*     */         
/* 122 */         return serverstatusresponse$players;
/*     */       }
/*     */ 
/*     */       
/*     */       public JsonElement serialize(ServerStatusResponse.Players p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 127 */         JsonObject jsonobject = new JsonObject();
/* 128 */         jsonobject.addProperty("max", Integer.valueOf(p_serialize_1_.getMaxPlayers()));
/* 129 */         jsonobject.addProperty("online", Integer.valueOf(p_serialize_1_.getOnlinePlayerCount()));
/*     */         
/* 131 */         if (p_serialize_1_.getPlayers() != null && (p_serialize_1_.getPlayers()).length > 0) {
/*     */           
/* 133 */           JsonArray jsonarray = new JsonArray();
/*     */           
/* 135 */           for (int i = 0; i < (p_serialize_1_.getPlayers()).length; i++) {
/*     */             
/* 137 */             JsonObject jsonobject1 = new JsonObject();
/* 138 */             UUID uuid = p_serialize_1_.getPlayers()[i].getId();
/* 139 */             jsonobject1.addProperty("id", (uuid == null) ? "" : uuid.toString());
/* 140 */             jsonobject1.addProperty("name", p_serialize_1_.getPlayers()[i].getName());
/* 141 */             jsonarray.add((JsonElement)jsonobject1);
/*     */           } 
/*     */           
/* 144 */           jsonobject.add("sample", (JsonElement)jsonarray);
/*     */         } 
/*     */         
/* 147 */         return (JsonElement)jsonobject;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse>
/*     */   {
/*     */     public ServerStatusResponse deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 156 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "status");
/* 157 */       ServerStatusResponse serverstatusresponse = new ServerStatusResponse();
/*     */       
/* 159 */       if (jsonobject.has("description"))
/*     */       {
/* 161 */         serverstatusresponse.setServerDescription((ITextComponent)p_deserialize_3_.deserialize(jsonobject.get("description"), ITextComponent.class));
/*     */       }
/*     */       
/* 164 */       if (jsonobject.has("players"))
/*     */       {
/* 166 */         serverstatusresponse.setPlayers((ServerStatusResponse.Players)p_deserialize_3_.deserialize(jsonobject.get("players"), ServerStatusResponse.Players.class));
/*     */       }
/*     */       
/* 169 */       if (jsonobject.has("version"))
/*     */       {
/* 171 */         serverstatusresponse.setVersion((ServerStatusResponse.Version)p_deserialize_3_.deserialize(jsonobject.get("version"), ServerStatusResponse.Version.class));
/*     */       }
/*     */       
/* 174 */       if (jsonobject.has("favicon"))
/*     */       {
/* 176 */         serverstatusresponse.setFavicon(JsonUtils.getString(jsonobject, "favicon"));
/*     */       }
/*     */       
/* 179 */       return serverstatusresponse;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ServerStatusResponse p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 184 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 186 */       if (p_serialize_1_.getServerDescription() != null)
/*     */       {
/* 188 */         jsonobject.add("description", p_serialize_3_.serialize(p_serialize_1_.getServerDescription()));
/*     */       }
/*     */       
/* 191 */       if (p_serialize_1_.getPlayers() != null)
/*     */       {
/* 193 */         jsonobject.add("players", p_serialize_3_.serialize(p_serialize_1_.getPlayers()));
/*     */       }
/*     */       
/* 196 */       if (p_serialize_1_.getVersion() != null)
/*     */       {
/* 198 */         jsonobject.add("version", p_serialize_3_.serialize(p_serialize_1_.getVersion()));
/*     */       }
/*     */       
/* 201 */       if (p_serialize_1_.getFavicon() != null)
/*     */       {
/* 203 */         jsonobject.addProperty("favicon", p_serialize_1_.getFavicon());
/*     */       }
/*     */       
/* 206 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Version
/*     */   {
/*     */     private final String name;
/*     */     private final int protocol;
/*     */     
/*     */     public Version(String nameIn, int protocolIn) {
/* 217 */       this.name = nameIn;
/* 218 */       this.protocol = protocolIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 223 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getProtocol() {
/* 228 */       return this.protocol;
/*     */     }
/*     */     
/*     */     public static class Serializer
/*     */       implements JsonDeserializer<Version>, JsonSerializer<Version>
/*     */     {
/*     */       public ServerStatusResponse.Version deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 235 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "version");
/* 236 */         return new ServerStatusResponse.Version(JsonUtils.getString(jsonobject, "name"), JsonUtils.getInt(jsonobject, "protocol"));
/*     */       }
/*     */ 
/*     */       
/*     */       public JsonElement serialize(ServerStatusResponse.Version p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 241 */         JsonObject jsonobject = new JsonObject();
/* 242 */         jsonobject.addProperty("name", p_serialize_1_.getName());
/* 243 */         jsonobject.addProperty("protocol", Integer.valueOf(p_serialize_1_.getProtocol()));
/* 244 */         return (JsonElement)jsonobject;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\ServerStatusResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */