/*    */ package me.nzxter.bettercraft.mods.altmanager;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import java.io.FileReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import me.nzxter.bettercraft.utils.FileManager;
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
/*    */ public class AltManager
/*    */ {
/* 23 */   public static String loggedInName = null;
/*    */   
/*    */   private static AltManager altManager;
/* 26 */   private static ArrayList<Alt> alts = new ArrayList<>();
/*    */   
/*    */   public static ArrayList<Alt> getAlts() {
/* 29 */     return alts;
/*    */   }
/*    */   
/*    */   public static void addAlt(Alt alt) {
/* 33 */     alts.add(alt);
/*    */   }
/*    */   
/*    */   public static void saveAlts() {
/* 37 */     Alt[] alts = AltManager.alts.<Alt>toArray(new Alt[0]);
/* 38 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/* 39 */     String json = gson.toJson(alts); try {
/* 40 */       Exception exception2, exception1 = null;
/*    */ 
/*    */     
/*    */     }
/* 44 */     catch (Exception exception) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static void loadAlts() {
/*    */     try {
/* 50 */       Alt[] alts = (Alt[])(new Gson()).fromJson(new FileReader(FileManager.altsDir), Alt[].class);
/* 51 */       AltManager.alts.addAll(Arrays.asList(alts));
/*    */     }
/* 53 */     catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\altmanager\AltManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */