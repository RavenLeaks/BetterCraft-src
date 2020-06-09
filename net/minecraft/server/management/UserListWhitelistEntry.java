/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UserListWhitelistEntry
/*    */   extends UserListEntry<GameProfile>
/*    */ {
/*    */   public UserListWhitelistEntry(GameProfile profile) {
/* 11 */     super(profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListWhitelistEntry(JsonObject json) {
/* 16 */     super(gameProfileFromJsonObject(json), json);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 21 */     if (getValue() != null) {
/*    */       
/* 23 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 24 */       data.addProperty("name", getValue().getName());
/* 25 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static GameProfile gameProfileFromJsonObject(JsonObject json) {
/* 31 */     if (json.has("uuid") && json.has("name")) {
/*    */       UUID uuid;
/* 33 */       String s = json.get("uuid").getAsString();
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 38 */         uuid = UUID.fromString(s);
/*    */       }
/* 40 */       catch (Throwable var4) {
/*    */         
/* 42 */         return null;
/*    */       } 
/*    */       
/* 45 */       return new GameProfile(uuid, json.get("name").getAsString());
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListWhitelistEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */