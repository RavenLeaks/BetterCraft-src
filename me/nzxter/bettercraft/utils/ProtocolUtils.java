/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ public class ProtocolUtils {
/*    */   public static ProtocolUtils getInstance() {
/*  5 */     return new ProtocolUtils();
/*    */   }
/*    */   
/*    */   public String getKnownAs(int protocolVersion) {
/*  9 */     if (protocolVersion == 0 || protocolVersion < 0) {
/* 10 */       return "Old Protocol";
/*    */     }
/* 12 */     if (protocolVersion < 6 && protocolVersion != 0) {
/* 13 */       return "1.7.X";
/*    */     }
/* 15 */     if (protocolVersion > 6 && protocolVersion < 48) {
/* 16 */       return "1.8.X";
/*    */     }
/* 18 */     if (protocolVersion == 48 || (protocolVersion > 48 && protocolVersion < 201)) {
/* 19 */       return "1.9.X";
/*    */     }
/* 21 */     if (protocolVersion == 201 || (protocolVersion > 201 && protocolVersion < 301)) {
/* 22 */       return "1.10.X";
/*    */     }
/* 24 */     if (protocolVersion == 301 || (protocolVersion > 301 && protocolVersion < 317)) {
/* 25 */       return "1.11.X";
/*    */     }
/* 27 */     if (protocolVersion == 317 || (protocolVersion > 317 && protocolVersion < 341)) {
/* 28 */       return "1.12.X";
/*    */     }
/* 30 */     if (protocolVersion == 341 || (protocolVersion > 341 && protocolVersion < 441)) {
/* 31 */       return "1.13.X";
/*    */     }
/* 33 */     if (protocolVersion == 441 || (protocolVersion > 441 && protocolVersion < 550)) {
/* 34 */       return "1.14.X";
/*    */     }
/* 36 */     if (protocolVersion == 550 || protocolVersion > 550) {
/* 37 */       return "1.15.X";
/*    */     }
/* 39 */     return "Wrong Protocol";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ProtocolUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */