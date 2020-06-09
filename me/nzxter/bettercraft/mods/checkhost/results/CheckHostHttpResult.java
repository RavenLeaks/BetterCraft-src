/*    */ package me.nzxter.bettercraft.mods.checkhost.results;
/*    */ 
/*    */ public class CheckHostHttpResult {
/*    */   private final String status;
/*    */   private final String address;
/*    */   private final double ping;
/*    */   private final int errorCode;
/*    */   
/*    */   public CheckHostHttpResult(String status, double ping, String address, int errorCode) {
/* 10 */     this.status = status;
/* 11 */     this.ping = ping;
/* 12 */     this.address = address;
/* 13 */     this.errorCode = errorCode;
/*    */   }
/*    */   
/*    */   public String getAddress() {
/* 17 */     return this.address;
/*    */   }
/*    */   
/*    */   public double getPing() {
/* 21 */     return this.ping;
/*    */   }
/*    */   
/*    */   public String getStatus() {
/* 25 */     return this.status;
/*    */   }
/*    */   
/*    */   public int getErrorCode() {
/* 29 */     return this.errorCode;
/*    */   }
/*    */   
/*    */   public boolean isSuccessful() {
/* 33 */     return (this.status != null && this.status.equalsIgnoreCase("OK"));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\results\CheckHostHttpResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */