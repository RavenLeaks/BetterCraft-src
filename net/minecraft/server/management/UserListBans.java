/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListBans
/*    */   extends UserList<GameProfile, UserListBansEntry>
/*    */ {
/*    */   public UserListBans(File bansFile) {
/* 11 */     super(bansFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 16 */     return new UserListBansEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBanned(GameProfile profile) {
/* 21 */     return hasEntry(profile);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 26 */     String[] astring = new String[getValues().size()];
/* 27 */     int i = 0;
/*    */     
/* 29 */     for (UserListBansEntry userlistbansentry : getValues().values())
/*    */     {
/* 31 */       astring[i++] = userlistbansentry.getValue().getName();
/*    */     }
/*    */     
/* 34 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 42 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getBannedProfile(String username) {
/* 51 */     for (UserListBansEntry userlistbansentry : getValues().values()) {
/*    */       
/* 53 */       if (username.equalsIgnoreCase(userlistbansentry.getValue().getName()))
/*    */       {
/* 55 */         return userlistbansentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListBans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */