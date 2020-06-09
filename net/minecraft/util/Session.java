/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class Session
/*    */ {
/*    */   public String username;
/*    */   private final String playerID;
/*    */   public String token;
/*    */   private final Type sessionType;
/*    */   
/*    */   public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
/* 20 */     this.username = usernameIn;
/* 21 */     this.playerID = playerIDIn;
/* 22 */     this.token = tokenIn;
/* 23 */     this.sessionType = Type.setSessionType(sessionTypeIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSessionID() {
/* 28 */     return "token:" + this.token + ":" + this.playerID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPlayerID() {
/* 33 */     return this.playerID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUsername() {
/* 38 */     return this.username;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getToken() {
/* 43 */     return this.token;
/*    */   }
/*    */   
/*    */   public Type getSessionType() {
/* 47 */     return this.sessionType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GameProfile getProfile() {
/*    */     try {
/* 54 */       UUID uuid = UUIDTypeAdapter.fromString(getPlayerID());
/* 55 */       return new GameProfile(uuid, getUsername());
/*    */     }
/* 57 */     catch (IllegalArgumentException var2) {
/*    */       
/* 59 */       return new GameProfile(null, getUsername());
/*    */     } 
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 65 */     LEGACY("legacy"),
/* 66 */     MOJANG("mojang");
/*    */     
/* 68 */     private static final Map<String, Type> SESSION_TYPES = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final String sessionType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/*    */       byte b;
/*    */       int i;
/*    */       Type[] arrayOfType;
/* 83 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type session$type = arrayOfType[b];
/*    */         
/* 85 */         SESSION_TYPES.put(session$type.sessionType, session$type);
/*    */         b++; }
/*    */     
/*    */     }
/*    */     
/*    */     Type(String sessionTypeIn) {
/*    */       this.sessionType = sessionTypeIn;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public static Type setSessionType(String sessionTypeIn) {
/*    */       return SESSION_TYPES.get(sessionTypeIn.toLowerCase(Locale.ROOT));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\Session.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */