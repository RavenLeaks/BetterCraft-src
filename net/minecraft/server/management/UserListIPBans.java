/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.io.File;
/*    */ import java.net.SocketAddress;
/*    */ 
/*    */ public class UserListIPBans
/*    */   extends UserList<String, UserListIPBansEntry>
/*    */ {
/*    */   public UserListIPBans(File bansFile) {
/* 11 */     super(bansFile);
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry<String> createEntry(JsonObject entryData) {
/* 16 */     return new UserListIPBansEntry(entryData);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBanned(SocketAddress address) {
/* 21 */     String s = addressToString(address);
/* 22 */     return hasEntry(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public UserListIPBansEntry getBanEntry(SocketAddress address) {
/* 27 */     String s = addressToString(address);
/* 28 */     return getEntry(s);
/*    */   }
/*    */ 
/*    */   
/*    */   private String addressToString(SocketAddress address) {
/* 33 */     String s = address.toString();
/*    */     
/* 35 */     if (s.contains("/"))
/*    */     {
/* 37 */       s = s.substring(s.indexOf('/') + 1);
/*    */     }
/*    */     
/* 40 */     if (s.contains(":"))
/*    */     {
/* 42 */       s = s.substring(0, s.indexOf(':'));
/*    */     }
/*    */     
/* 45 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListIPBans.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */