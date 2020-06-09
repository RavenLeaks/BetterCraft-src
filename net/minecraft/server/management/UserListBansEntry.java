/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UserListBansEntry
/*    */   extends UserListEntryBan<GameProfile>
/*    */ {
/*    */   public UserListBansEntry(GameProfile profile) {
/* 12 */     this(profile, null, null, null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListBansEntry(GameProfile profile, Date startDate, String banner, Date endDate, String banReason) {
/* 17 */     super(profile, endDate, banner, endDate, banReason);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListBansEntry(JsonObject json) {
/* 22 */     super(toGameProfile(json), json);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 27 */     if (getValue() != null) {
/*    */       
/* 29 */       data.addProperty("uuid", (getValue().getId() == null) ? "" : getValue().getId().toString());
/* 30 */       data.addProperty("name", getValue().getName());
/* 31 */       super.onSerialization(data);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static GameProfile toGameProfile(JsonObject json) {
/* 41 */     if (json.has("uuid") && json.has("name")) {
/*    */       UUID uuid;
/* 43 */       String s = json.get("uuid").getAsString();
/*    */ 
/*    */ 
/*    */       
/*    */       try {
/* 48 */         uuid = UUID.fromString(s);
/*    */       }
/* 50 */       catch (Throwable var4) {
/*    */         
/* 52 */         return null;
/*    */       } 
/*    */       
/* 55 */       return new GameProfile(uuid, json.get("name").getAsString());
/*    */     } 
/*    */ 
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListBansEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */