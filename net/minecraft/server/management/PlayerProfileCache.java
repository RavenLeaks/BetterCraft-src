/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.GameProfileRepository;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerProfileCache
/*     */ {
/*  46 */   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*     */   private static boolean onlineMode;
/*  48 */   private final Map<String, ProfileEntry> usernameToProfileEntryMap = Maps.newHashMap();
/*  49 */   private final Map<UUID, ProfileEntry> uuidToProfileEntryMap = Maps.newHashMap();
/*  50 */   private final Deque<GameProfile> gameProfiles = Lists.newLinkedList();
/*     */   
/*     */   private final GameProfileRepository profileRepo;
/*     */   
/*  54 */   private static final ParameterizedType TYPE = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  58 */         return new Type[] { PlayerProfileCache.ProfileEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  62 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  66 */         return null;
/*     */       }
/*     */     };
/*     */   protected final Gson gson; private final File usercacheFile;
/*     */   
/*     */   public PlayerProfileCache(GameProfileRepository profileRepoIn, File usercacheFileIn) {
/*  72 */     this.profileRepo = profileRepoIn;
/*  73 */     this.usercacheFile = usercacheFileIn;
/*  74 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*  75 */     gsonbuilder.registerTypeHierarchyAdapter(ProfileEntry.class, new Serializer(null));
/*  76 */     this.gson = gsonbuilder.create();
/*  77 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   private static GameProfile lookupProfile(GameProfileRepository profileRepoIn, String name) {
/*  82 */     final GameProfile[] agameprofile = new GameProfile[1];
/*  83 */     ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_)
/*     */         {
/*  87 */           agameprofile[0] = p_onProfileLookupSucceeded_1_;
/*     */         }
/*     */         
/*     */         public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/*  91 */           agameprofile[0] = null;
/*     */         }
/*     */       };
/*  94 */     profileRepoIn.findProfilesByNames(new String[] { name }, Agent.MINECRAFT, profilelookupcallback);
/*     */     
/*  96 */     if (!isOnlineMode() && agameprofile[0] == null) {
/*     */       
/*  98 */       UUID uuid = EntityPlayer.getUUID(new GameProfile(null, name));
/*  99 */       GameProfile gameprofile = new GameProfile(uuid, name);
/* 100 */       profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */     } 
/*     */     
/* 103 */     return agameprofile[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setOnlineMode(boolean onlineModeIn) {
/* 108 */     onlineMode = onlineModeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isOnlineMode() {
/* 113 */     return onlineMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(GameProfile gameProfile) {
/* 121 */     addEntry(gameProfile, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(GameProfile gameProfile, Date expirationDate) {
/* 129 */     UUID uuid = gameProfile.getId();
/*     */     
/* 131 */     if (expirationDate == null) {
/*     */       
/* 133 */       Calendar calendar = Calendar.getInstance();
/* 134 */       calendar.setTime(new Date());
/* 135 */       calendar.add(2, 1);
/* 136 */       expirationDate = calendar.getTime();
/*     */     } 
/*     */     
/* 139 */     String s = gameProfile.getName().toLowerCase(Locale.ROOT);
/* 140 */     ProfileEntry playerprofilecache$profileentry = new ProfileEntry(gameProfile, expirationDate, null);
/*     */     
/* 142 */     if (this.uuidToProfileEntryMap.containsKey(uuid)) {
/*     */       
/* 144 */       ProfileEntry playerprofilecache$profileentry1 = this.uuidToProfileEntryMap.get(uuid);
/* 145 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry1.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 146 */       this.gameProfiles.remove(gameProfile);
/*     */     } 
/*     */     
/* 149 */     this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), playerprofilecache$profileentry);
/* 150 */     this.uuidToProfileEntryMap.put(uuid, playerprofilecache$profileentry);
/* 151 */     this.gameProfiles.addFirst(gameProfile);
/* 152 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GameProfile getGameProfileForUsername(String username) {
/* 163 */     String s = username.toLowerCase(Locale.ROOT);
/* 164 */     ProfileEntry playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */     
/* 166 */     if (playerprofilecache$profileentry != null && (new Date()).getTime() >= playerprofilecache$profileentry.expirationDate.getTime()) {
/*     */       
/* 168 */       this.uuidToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getId());
/* 169 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 170 */       this.gameProfiles.remove(playerprofilecache$profileentry.getGameProfile());
/* 171 */       playerprofilecache$profileentry = null;
/*     */     } 
/*     */     
/* 174 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 176 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 177 */       this.gameProfiles.remove(gameprofile);
/* 178 */       this.gameProfiles.addFirst(gameprofile);
/*     */     }
/*     */     else {
/*     */       
/* 182 */       GameProfile gameprofile1 = lookupProfile(this.profileRepo, s);
/*     */       
/* 184 */       if (gameprofile1 != null) {
/*     */         
/* 186 */         addEntry(gameprofile1);
/* 187 */         playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     save();
/* 192 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getUsernames() {
/* 200 */     List<String> list = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
/* 201 */     return list.<String>toArray(new String[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GameProfile getProfileByUUID(UUID uuid) {
/* 211 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/* 212 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProfileEntry getByUUID(UUID uuid) {
/* 220 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/*     */     
/* 222 */     if (playerprofilecache$profileentry != null) {
/*     */       
/* 224 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 225 */       this.gameProfiles.remove(gameprofile);
/* 226 */       this.gameProfiles.addFirst(gameprofile);
/*     */     } 
/*     */     
/* 229 */     return playerprofilecache$profileentry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 237 */     BufferedReader bufferedreader = null;
/*     */ 
/*     */     
/*     */     try {
/* 241 */       bufferedreader = Files.newReader(this.usercacheFile, StandardCharsets.UTF_8);
/* 242 */       List<ProfileEntry> list = (List<ProfileEntry>)JsonUtils.func_193841_a(this.gson, bufferedreader, TYPE);
/* 243 */       this.usernameToProfileEntryMap.clear();
/* 244 */       this.uuidToProfileEntryMap.clear();
/* 245 */       this.gameProfiles.clear();
/*     */       
/* 247 */       if (list != null)
/*     */       {
/* 249 */         for (ProfileEntry playerprofilecache$profileentry : Lists.reverse(list))
/*     */         {
/* 251 */           if (playerprofilecache$profileentry != null)
/*     */           {
/* 253 */             addEntry(playerprofilecache$profileentry.getGameProfile(), playerprofilecache$profileentry.getExpirationDate());
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 258 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 262 */     catch (JsonParseException jsonParseException) {
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 268 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/* 277 */     String s = this.gson.toJson(getEntriesWithLimit(1000));
/* 278 */     BufferedWriter bufferedwriter = null;
/*     */ 
/*     */     
/*     */     try {
/* 282 */       bufferedwriter = Files.newWriter(this.usercacheFile, StandardCharsets.UTF_8);
/* 283 */       bufferedwriter.write(s);
/*     */       
/*     */       return;
/* 286 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */ 
/*     */     
/*     */     }
/* 290 */     catch (IOException var9) {
/*     */       
/*     */       return;
/*     */     }
/*     */     finally {
/*     */       
/* 296 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ProfileEntry> getEntriesWithLimit(int limitSize) {
/* 302 */     List<ProfileEntry> list = Lists.newArrayList();
/*     */     
/* 304 */     for (GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), limitSize))) {
/*     */       
/* 306 */       ProfileEntry playerprofilecache$profileentry = getByUUID(gameprofile.getId());
/*     */       
/* 308 */       if (playerprofilecache$profileentry != null)
/*     */       {
/* 310 */         list.add(playerprofilecache$profileentry);
/*     */       }
/*     */     } 
/*     */     
/* 314 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   class ProfileEntry
/*     */   {
/*     */     private final GameProfile gameProfile;
/*     */     private final Date expirationDate;
/*     */     
/*     */     private ProfileEntry(GameProfile gameProfileIn, Date expirationDateIn) {
/* 324 */       this.gameProfile = gameProfileIn;
/* 325 */       this.expirationDate = expirationDateIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getGameProfile() {
/* 330 */       return this.gameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date getExpirationDate() {
/* 335 */       return this.expirationDate;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<ProfileEntry>, JsonSerializer<ProfileEntry>
/*     */   {
/*     */     private Serializer() {}
/*     */ 
/*     */     
/*     */     public JsonElement serialize(PlayerProfileCache.ProfileEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 347 */       JsonObject jsonobject = new JsonObject();
/* 348 */       jsonobject.addProperty("name", p_serialize_1_.getGameProfile().getName());
/* 349 */       UUID uuid = p_serialize_1_.getGameProfile().getId();
/* 350 */       jsonobject.addProperty("uuid", (uuid == null) ? "" : uuid.toString());
/* 351 */       jsonobject.addProperty("expiresOn", PlayerProfileCache.DATE_FORMAT.format(p_serialize_1_.getExpirationDate()));
/* 352 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public PlayerProfileCache.ProfileEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 357 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 359 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 360 */         JsonElement jsonelement = jsonobject.get("name");
/* 361 */         JsonElement jsonelement1 = jsonobject.get("uuid");
/* 362 */         JsonElement jsonelement2 = jsonobject.get("expiresOn");
/*     */         
/* 364 */         if (jsonelement != null && jsonelement1 != null) {
/*     */           
/* 366 */           String s = jsonelement1.getAsString();
/* 367 */           String s1 = jsonelement.getAsString();
/* 368 */           Date date = null;
/*     */           
/* 370 */           if (jsonelement2 != null) {
/*     */             
/*     */             try {
/*     */               
/* 374 */               date = PlayerProfileCache.DATE_FORMAT.parse(jsonelement2.getAsString());
/*     */             }
/* 376 */             catch (ParseException var14) {
/*     */               
/* 378 */               date = null;
/*     */             } 
/*     */           }
/*     */           
/* 382 */           if (s1 != null && s != null) {
/*     */             UUID uuid;
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 388 */               uuid = UUID.fromString(s);
/*     */             }
/* 390 */             catch (Throwable var13) {
/*     */               
/* 392 */               return null;
/*     */             } 
/*     */             
/* 395 */             PlayerProfileCache.this.getClass(); return new PlayerProfileCache.ProfileEntry(new GameProfile(uuid, s1), date, null);
/*     */           } 
/*     */ 
/*     */           
/* 399 */           return null;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 404 */         return null;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 409 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\PlayerProfileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */