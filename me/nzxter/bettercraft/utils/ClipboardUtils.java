/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.DataFlavor;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import java.awt.datatransfer.Transferable;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class ClipboardUtils
/*    */ {
/*    */   private static String getClipboardString() {
/*    */     try {
/* 14 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/* 15 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
/* 16 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*    */       }
/*    */     }
/* 19 */     catch (Exception var1) {
/* 20 */       var1.printStackTrace();
/*    */     } 
/* 22 */     return "";
/*    */   }
/*    */   
/*    */   public static String getClipboard() {
/* 26 */     String toReturn = null;
/*    */     try {
/* 28 */       toReturn = getClipboardString();
/*    */     }
/* 30 */     catch (Exception e) {
/* 31 */       e.printStackTrace();
/*    */     } 
/* 33 */     return toReturn;
/*    */   }
/*    */   
/*    */   private static void setClipboardString(String text) {
/* 37 */     if (!StringUtils.isEmpty(text)) {
/*    */       try {
/* 39 */         StringSelection stringselection = new StringSelection(text);
/* 40 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
/*    */       }
/* 42 */       catch (Exception var2) {
/* 43 */         var2.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static void setClipboard(String text) {
/*    */     try {
/* 50 */       setClipboardString(text);
/*    */     }
/* 52 */     catch (Exception e) {
/* 53 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ClipboardUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */