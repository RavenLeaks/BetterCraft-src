/*    */ package me.nzxter.bettercraft.mods.checkhost;
/*    */ 
/*    */ public enum CheckHostType {
/*  4 */   PING("ping"),
/*  5 */   TCP("tcp"),
/*  6 */   UDP("udp"),
/*  7 */   HTTP("http"),
/*  8 */   DNS("dns");
/*    */   
/*    */   private final String value;
/*    */   
/*    */   CheckHostType(String value2) {
/* 13 */     this.value = value2;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 17 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\checkhost\CheckHostType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */