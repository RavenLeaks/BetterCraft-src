/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.awt.Desktop;
/*    */ import java.net.URI;
/*    */ 
/*    */ public class OpenWebsiteUtils {
/*    */   public static boolean openLink(String url) {
/*    */     try {
/*  9 */       Desktop.getDesktop().browse(new URI(url));
/* 10 */       return true;
/*    */     }
/* 12 */     catch (Exception e) {
/* 13 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\OpenWebsiteUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */