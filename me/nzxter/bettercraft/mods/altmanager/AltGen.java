/*    */ package me.nzxter.bettercraft.mods.altmanager;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import javax.net.ssl.HttpsURLConnection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AltGen
/*    */ {
/*    */   public static String username;
/*    */   public static String password;
/*    */   
/*    */   public static void generate() {
/*    */     try {
/* 18 */       URL url = new URL("https://bettercraft.net/api/alt/alt.php");
/* 19 */       HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
/* 20 */       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/*    */       String line;
/* 22 */       while ((line = reader.readLine()) != null) {
/*    */         
/* 24 */         String[] split = line.split(":");
/* 25 */         username = split[0];
/* 26 */         password = split[1];
/*    */       } 
/* 28 */       reader.close();
/*    */     }
/* 30 */     catch (Exception e) {
/*    */       
/* 32 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\altmanager\AltGen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */