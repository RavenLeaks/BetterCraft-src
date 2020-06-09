/*    */ package me.nzxter.bettercraft.mods.checkhost.results;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CheckHostDnsResult {
/*    */   private final int ttl;
/*    */   private final Map<String, String[]> result;
/*    */   
/*    */   public CheckHostDnsResult(int ttl, Map<String, String[]> result2) {
/* 10 */     this.ttl = ttl;
/* 11 */     this.result = result2;
/*    */   }
/*    */   
/*    */   public Map<String, String[]> getResult() {
/* 15 */     return this.result;
/*    */   }
/*    */   
/*    */   public int getTTL() {
/* 19 */     return this.ttl;
/*    */   }
/*    */   
/*    */   public boolean isSuccessful() {
/* 23 */     return (this.ttl >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\results\CheckHostDnsResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */