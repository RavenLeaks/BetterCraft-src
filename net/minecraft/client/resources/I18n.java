/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class I18n
/*    */ {
/*    */   private static Locale i18nLocale;
/*    */   
/*    */   static void setLocale(Locale i18nLocaleIn) {
/* 11 */     i18nLocale = i18nLocaleIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String format(String translateKey, Object... parameters) {
/* 19 */     return i18nLocale.formatMessage(translateKey, parameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasKey(String key) {
/* 24 */     return i18nLocale.hasKey(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Map getLocaleProperties() {
/* 29 */     return i18nLocale.properties;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\I18n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */