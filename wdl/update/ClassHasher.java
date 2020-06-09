/*    */ package wdl.update;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.InputStream;
/*    */ import java.security.DigestInputStream;
/*    */ import java.security.MessageDigest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClassHasher
/*    */ {
/* 13 */   private static final char[] hexArray = "0123456789ABCDEF".toCharArray();
/*    */   
/*    */   public static String bytesToHex(byte[] bytes) {
/* 16 */     char[] hexChars = new char[bytes.length * 2];
/* 17 */     for (int j = 0; j < bytes.length; j++) {
/* 18 */       int v = bytes[j] & 0xFF;
/* 19 */       hexChars[j * 2] = hexArray[v >>> 4];
/* 20 */       hexChars[j * 2 + 1] = hexArray[v & 0xF];
/*    */     } 
/* 22 */     return new String(hexChars);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String hash(String relativeTo, String file) throws ClassNotFoundException, FileNotFoundException, Exception {
/* 44 */     Class<?> clazz = Class.forName(relativeTo);
/* 45 */     MessageDigest digest = MessageDigest.getInstance("MD5");
/*    */     
/* 47 */     InputStream stream = null;
/*    */     try {
/* 49 */       stream = clazz.getResourceAsStream(file);
/* 50 */       if (stream == null) {
/* 51 */         throw new FileNotFoundException(String.valueOf(file) + " relative to " + 
/* 52 */             relativeTo);
/*    */       }
/* 54 */       DigestInputStream digestStream = null;
/*    */       try {
/* 56 */         digestStream = new DigestInputStream(stream, digest); do {
/*    */         
/* 58 */         } while (digestStream.read() != -1);
/*    */       } finally {
/* 60 */         if (digestStream != null) {
/* 61 */           digestStream.close();
/*    */         }
/*    */       } 
/*    */     } finally {
/* 65 */       if (stream != null) {
/* 66 */         stream.close();
/*    */       }
/*    */     } 
/*    */     
/* 70 */     return bytesToHex(digest.digest());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wd\\update\ClassHasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */