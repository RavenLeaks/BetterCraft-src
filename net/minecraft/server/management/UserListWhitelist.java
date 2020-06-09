/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.File;
/*    */ 
/*    */ public class UserListWhitelist
/*    */   extends UserList<GameProfile, UserListWhitelistEntry>
/*    */ {
/*    */   public UserListWhitelist(File p_i1132_1_) {
/* 11 */     super(p_i1132_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<GameProfile> createEntry(JsonObject entryData) {
/* 16 */     return new UserListWhitelistEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getKeys() {
/* 21 */     String[] astring = new String[getValues().size()];
/* 22 */     int i = 0;
/*    */     
/* 24 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values())
/*    */     {
/* 26 */       astring[i++] = userlistwhitelistentry.getValue().getName();
/*    */     }
/*    */     
/* 29 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getObjectKey(GameProfile obj) {
/* 37 */     return obj.getId().toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getByName(String profileName) {
/* 45 */     for (UserListWhitelistEntry userlistwhitelistentry : getValues().values()) {
/*    */       
/* 47 */       if (profileName.equalsIgnoreCase(userlistwhitelistentry.getValue().getName()))
/*    */       {
/* 49 */         return userlistwhitelistentry.getValue();
/*    */       }
/*    */     } 
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */