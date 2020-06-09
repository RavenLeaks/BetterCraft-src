/*    */ package me.nzxter.bettercraft.mods.checkhost.results;
/*    */ 
/*    */ public class CheckHostTcpResult {
/*    */   private final double ping;
/*    */   private final String address;
/*    */   private final String error;
/*    */   
/*    */   public CheckHostTcpResult(double ping, String address, String error) {
/*  9 */     this.ping = ping;
/* 10 */     this.address = address;
/* 11 */     this.error = error;
/*    */   }
/*    */   
/*    */   public String getAddress() {
/* 15 */     return this.address;
/*    */   }
/*    */   
/*    */   public double getPing() {
/* 19 */     return this.ping;
/*    */   }
/*    */   
/*    */   public String getError() {
/* 23 */     return this.error;
/*    */   }
/*    */   
/*    */   public boolean isSuccessful() {
/* 27 */     return (this.error == null);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\results\CheckHostTcpResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */