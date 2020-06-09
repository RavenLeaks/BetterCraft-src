/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public abstract class UserListEntryBan<T>
/*    */   extends UserListEntry<T> {
/* 10 */   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*    */   
/*    */   protected final Date banStartDate;
/*    */   protected final String bannedBy;
/*    */   protected final Date banEndDate;
/*    */   protected final String reason;
/*    */   
/*    */   public UserListEntryBan(T valueIn, Date startDate, String banner, Date endDate, String banReason) {
/* 18 */     super(valueIn);
/* 19 */     this.banStartDate = (startDate == null) ? new Date() : startDate;
/* 20 */     this.bannedBy = (banner == null) ? "(Unknown)" : banner;
/* 21 */     this.banEndDate = endDate;
/* 22 */     this.reason = (banReason == null) ? "Banned by an operator." : banReason;
/*    */   }
/*    */ 
/*    */   
/*    */   protected UserListEntryBan(T valueIn, JsonObject json) {
/* 27 */     super(valueIn, json);
/*    */     
/*    */     Date date, date1;
/*    */     
/*    */     try {
/* 32 */       date = json.has("created") ? DATE_FORMAT.parse(json.get("created").getAsString()) : new Date();
/*    */     }
/* 34 */     catch (ParseException var7) {
/*    */       
/* 36 */       date = new Date();
/*    */     } 
/*    */     
/* 39 */     this.banStartDate = date;
/* 40 */     this.bannedBy = json.has("source") ? json.get("source").getAsString() : "(Unknown)";
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 45 */       date1 = json.has("expires") ? DATE_FORMAT.parse(json.get("expires").getAsString()) : null;
/*    */     }
/* 47 */     catch (ParseException var6) {
/*    */       
/* 49 */       date1 = null;
/*    */     } 
/*    */     
/* 52 */     this.banEndDate = date1;
/* 53 */     this.reason = json.has("reason") ? json.get("reason").getAsString() : "Banned by an operator.";
/*    */   }
/*    */ 
/*    */   
/*    */   public Date getBanEndDate() {
/* 58 */     return this.banEndDate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBanReason() {
/* 63 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   boolean hasBanExpired() {
/* 68 */     return (this.banEndDate == null) ? false : this.banEndDate.before(new Date());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onSerialization(JsonObject data) {
/* 73 */     data.addProperty("created", DATE_FORMAT.format(this.banStartDate));
/* 74 */     data.addProperty("source", this.bannedBy);
/* 75 */     data.addProperty("expires", (this.banEndDate == null) ? "forever" : DATE_FORMAT.format(this.banEndDate));
/* 76 */     data.addProperty("reason", this.reason);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\UserListEntryBan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */