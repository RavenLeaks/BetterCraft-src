/*     */ package wdl.update;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class Release
/*     */ {
/*  28 */   private static final Pattern HIDDEN_JSON_REGEX = Pattern.compile(
/*  29 */       "^\\[\\]\\(# '(.+?)'\\)");
/*  30 */   private static final JsonParser PARSER = new JsonParser();
/*     */   
/*     */   public final JsonObject object;
/*     */   
/*     */   public final String URL;
/*     */   
/*     */   public final String tag;
/*     */   
/*     */   public final String title;
/*     */   
/*     */   public final String date;
/*     */   
/*     */   public final boolean prerelease;
/*     */   
/*     */   public final String markdownBody;
/*     */   
/*     */   public final String textOnlyBody;
/*     */   
/*     */   public final HiddenInfo hiddenInfo;
/*     */ 
/*     */   
/*     */   public class HiddenInfo
/*     */   {
/*     */     public final String mainMinecraftVersion;
/*     */     public final String[] supportedMinecraftVersions;
/*     */     public final String loader;
/*     */     public final String post;
/*     */     public final Release.HashData[] hashes;
/*     */     
/*     */     private HiddenInfo(JsonObject object) {
/*  60 */       this.mainMinecraftVersion = object.get("Minecraft").getAsString();
/*  61 */       JsonArray compatibleVersions = object.get("MinecraftCompatible")
/*  62 */         .getAsJsonArray();
/*  63 */       this.supportedMinecraftVersions = 
/*  64 */         new String[compatibleVersions.size()];
/*  65 */       for (int i = 0; i < compatibleVersions.size(); i++) {
/*  66 */         this.supportedMinecraftVersions[i] = compatibleVersions.get(i)
/*  67 */           .getAsString();
/*     */       }
/*     */       
/*  70 */       this.loader = object.get("Loader").getAsString();
/*  71 */       JsonElement post = object.get("Post");
/*  72 */       if (post.isJsonNull()) {
/*  73 */         this.post = null;
/*     */       } else {
/*  75 */         this.post = post.getAsString();
/*     */       } 
/*     */       
/*  78 */       JsonArray hashes = object.get("Hashes").getAsJsonArray();
/*  79 */       this.hashes = new Release.HashData[hashes.size()];
/*  80 */       for (int j = 0; j < hashes.size(); j++) {
/*  81 */         this.hashes[j] = new Release.HashData(hashes.get(j).getAsJsonObject());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  87 */       return "HiddenInfo [mainMinecraftVersion=" + this.mainMinecraftVersion + 
/*  88 */         ", supportedMinecraftVersions=" + 
/*  89 */         Arrays.toString((Object[])this.supportedMinecraftVersions) + ", loader=" + 
/*  90 */         this.loader + ", post=" + this.post + ", hashes=" + 
/*  91 */         Arrays.toString((Object[])this.hashes) + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class HashData
/*     */   {
/*     */     public final String relativeTo;
/*     */ 
/*     */ 
/*     */     
/*     */     public final String file;
/*     */ 
/*     */ 
/*     */     
/*     */     public final String[] validHashes;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HashData(JsonObject object) {
/* 115 */       this.relativeTo = object.get("RelativeTo").getAsString();
/* 116 */       this.file = object.get("File").getAsString();
/* 117 */       JsonArray hashes = object.get("Hash").getAsJsonArray();
/* 118 */       this.validHashes = new String[hashes.size()];
/* 119 */       for (int i = 0; i < this.validHashes.length; i++) {
/* 120 */         this.validHashes[i] = hashes.get(i).getAsString();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 126 */       return "HashData [relativeTo=" + this.relativeTo + ", file=" + this.file + 
/* 127 */         ", validHashes=" + Arrays.toString((Object[])this.validHashes) + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 132 */       int prime = 31;
/* 133 */       int result = 1;
/* 134 */       result = 31 * result + getOuterType().hashCode();
/* 135 */       result = 31 * result + ((this.file == null) ? 0 : this.file.hashCode());
/* 136 */       result = 31 * result + (
/* 137 */         (this.relativeTo == null) ? 0 : this.relativeTo.hashCode());
/* 138 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 143 */       if (this == obj) {
/* 144 */         return true;
/*     */       }
/* 146 */       if (obj == null) {
/* 147 */         return false;
/*     */       }
/* 149 */       if (!(obj instanceof HashData)) {
/* 150 */         return false;
/*     */       }
/* 152 */       HashData other = (HashData)obj;
/* 153 */       if (!getOuterType().equals(other.getOuterType())) {
/* 154 */         return false;
/*     */       }
/* 156 */       if (this.file == null) {
/* 157 */         if (other.file != null) {
/* 158 */           return false;
/*     */         }
/* 160 */       } else if (!this.file.equals(other.file)) {
/* 161 */         return false;
/*     */       } 
/* 163 */       if (this.relativeTo == null) {
/* 164 */         if (other.relativeTo != null) {
/* 165 */           return false;
/*     */         }
/* 167 */       } else if (!this.relativeTo.equals(other.relativeTo)) {
/* 168 */         return false;
/*     */       } 
/* 170 */       return true;
/*     */     }
/*     */     
/*     */     private Release getOuterType() {
/* 174 */       return Release.this;
/*     */     }
/*     */   }
/*     */   
/*     */   public Release(JsonObject object) {
/* 179 */     this.object = object;
/*     */     
/* 181 */     this.markdownBody = object.get("body").getAsString();
/* 182 */     Matcher hiddenJSONMatcher = HIDDEN_JSON_REGEX.matcher(this.markdownBody);
/* 183 */     if (hiddenJSONMatcher.find()) {
/*     */       
/* 185 */       String hiddenJSONStr = this.markdownBody.substring(hiddenJSONMatcher.start(1), 
/* 186 */           hiddenJSONMatcher.end(1));
/* 187 */       JsonObject hiddenJSON = PARSER.parse(hiddenJSONStr)
/* 188 */         .getAsJsonObject();
/* 189 */       this.hiddenInfo = new HiddenInfo(hiddenJSON, null);
/*     */     } else {
/*     */       
/* 192 */       this.hiddenInfo = null;
/*     */     } 
/*     */     
/* 195 */     this.URL = object.get("html_url").getAsString();
/* 196 */     this.textOnlyBody = object.get("body_text").getAsString();
/* 197 */     this.tag = object.get("tag_name").getAsString();
/* 198 */     this.title = object.get("name").getAsString();
/* 199 */     this.date = object.get("published_at").getAsString();
/* 200 */     this.prerelease = object.get("prerelease").getAsBoolean();
/*     */   }
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
/*     */ 
/*     */   
/*     */   public String toString() {
/* 241 */     return "Release [URL=" + this.URL + ", tag=" + this.tag + ", title=" + this.title + 
/* 242 */       ", date=" + this.date + ", prerelease=" + this.prerelease + 
/* 243 */       ", markdownBody=" + this.markdownBody + ", textOnlyBody=" + 
/* 244 */       this.textOnlyBody + ", hiddenInfo=" + this.hiddenInfo + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wd\\update\Release.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */