/*    */ package shadersmod.common;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public abstract class SMCLog
/*    */ {
/*  8 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private static final String PREFIX = "[Shaders] ";
/*    */   
/*    */   public static void severe(String message) {
/* 13 */     LOGGER.error("[Shaders] " + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void warning(String message) {
/* 18 */     LOGGER.warn("[Shaders] " + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void info(String message) {
/* 23 */     LOGGER.info("[Shaders] " + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void fine(String message) {
/* 28 */     LOGGER.debug("[Shaders] " + message);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void severe(String format, Object... args) {
/* 33 */     String s = String.format(format, args);
/* 34 */     LOGGER.error("[Shaders] " + s);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void warning(String format, Object... args) {
/* 39 */     String s = String.format(format, args);
/* 40 */     LOGGER.warn("[Shaders] " + s);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void info(String format, Object... args) {
/* 45 */     String s = String.format(format, args);
/* 46 */     LOGGER.info("[Shaders] " + s);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void fine(String format, Object... args) {
/* 51 */     String s = String.format(format, args);
/* 52 */     LOGGER.debug("[Shaders] " + s);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\common\SMCLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */