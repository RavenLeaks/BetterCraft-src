/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ 
/*    */ 
/*    */ public class UserListEntry<T>
/*    */ {
/*    */   private final T value;
/*    */   
/*    */   public UserListEntry(T valueIn) {
/* 11 */     this.value = valueIn;
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntry(T valueIn, JsonObject json) {
/* 16 */     this.value = valueIn;
/*    */   }
/*    */ 
/*    */   
/*    */   T getValue() {
/* 21 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean hasBanExpired() {
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   protected void onSerialization(JsonObject data) {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */