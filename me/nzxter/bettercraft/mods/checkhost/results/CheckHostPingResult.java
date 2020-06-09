/*    */ package me.nzxter.bettercraft.mods.checkhost.results;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class CheckHostPingResult {
/*    */   private final List<PingEntry> pingEntries;
/*    */   
/*    */   public CheckHostPingResult(List<PingEntry> pingEntries) {
/*  9 */     this.pingEntries = pingEntries;
/*    */   }
/*    */   
/*    */   public List<PingEntry> getPingEntries() {
/* 13 */     return this.pingEntries;
/*    */   }
/*    */   
/*    */   public boolean isSuccessful() {
/* 17 */     return (this.pingEntries != null && !this.pingEntries.isEmpty());
/*    */   }
/*    */   
/*    */   public static class PingEntry {
/*    */     private final String status;
/*    */     private final double ping;
/*    */     private final String address;
/*    */     
/*    */     public PingEntry(String status, double ping, String address) {
/* 26 */       this.status = status;
/* 27 */       this.ping = ping;
/* 28 */       this.address = address;
/*    */     }
/*    */     
/*    */     public String getAddress() {
/* 32 */       return this.address;
/*    */     }
/*    */     
/*    */     public double getPing() {
/* 36 */       return this.ping;
/*    */     }
/*    */     
/*    */     public String getStatus() {
/* 40 */       return this.status;
/*    */     }
/*    */     
/*    */     public boolean isSuccessful() {
/* 44 */       return (this.status != null && this.status.equalsIgnoreCase("OK") && this.ping >= 0.0D);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\results\CheckHostPingResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */