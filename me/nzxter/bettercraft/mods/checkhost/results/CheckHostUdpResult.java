/*    */ package me.nzxter.bettercraft.mods.checkhost.results;
/*    */ 
/*    */ public class CheckHostUdpResult {
/*    */   private final double timeout;
/*    */   private final double ping;
/*    */   private final String address;
/*    */   private final String error;
/*    */   
/*    */   public CheckHostUdpResult(double timeout, double ping, String address, String error) {
/* 10 */     this.timeout = timeout;
/* 11 */     this.ping = ping;
/* 12 */     this.address = address;
/* 13 */     this.error = error;
/*    */   }
/*    */   
/*    */   public String getAddress() {
/* 17 */     return this.address;
/*    */   }
/*    */   
/*    */   public double getTimeout() {
/* 21 */     return this.timeout;
/*    */   }
/*    */   
/*    */   public double getPing() {
/* 25 */     return this.ping;
/*    */   }
/*    */   
/*    */   public String getError() {
/* 29 */     return this.error;
/*    */   }
/*    */   
/*    */   public boolean isSuccessful() {
/* 33 */     return (this.error == null);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\results\CheckHostUdpResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */