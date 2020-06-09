/*    */ package net.minecraft.util.text.translation;
/*    */ 
/*    */ @Deprecated
/*    */ public class I18n
/*    */ {
/*  6 */   private static final LanguageMap localizedName = LanguageMap.getInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 12 */   private static final LanguageMap fallbackTranslator = new LanguageMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String translateToLocal(String key) {
/* 21 */     return localizedName.translateKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String translateToLocalFormatted(String key, Object... format) {
/* 31 */     return localizedName.translateKeyFormat(key, format);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static String translateToFallback(String key) {
/* 42 */     return fallbackTranslator.translateKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static boolean canTranslate(String key) {
/* 52 */     return localizedName.isKeyTranslated(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long getLastTranslationUpdateTimeInMilliseconds() {
/* 60 */     return localizedName.getLastUpdateTimeInMilliseconds();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\translation\I18n.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */