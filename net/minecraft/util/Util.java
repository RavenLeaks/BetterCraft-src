/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static EnumOS getOSType() {
/* 14 */     String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
/*    */     
/* 16 */     if (s.contains("win"))
/*    */     {
/* 18 */       return EnumOS.WINDOWS;
/*    */     }
/* 20 */     if (s.contains("mac"))
/*    */     {
/* 22 */       return EnumOS.OSX;
/*    */     }
/* 24 */     if (s.contains("solaris"))
/*    */     {
/* 26 */       return EnumOS.SOLARIS;
/*    */     }
/* 28 */     if (s.contains("sunos"))
/*    */     {
/* 30 */       return EnumOS.SOLARIS;
/*    */     }
/* 32 */     if (s.contains("linux"))
/*    */     {
/* 34 */       return EnumOS.LINUX;
/*    */     }
/*    */ 
/*    */     
/* 38 */     return s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static <V> V runTask(FutureTask<V> task, Logger logger) {
/*    */     try {
/* 47 */       task.run();
/* 48 */       return task.get();
/*    */     }
/* 50 */     catch (ExecutionException executionexception) {
/*    */       
/* 52 */       logger.fatal("Error executing task", executionexception);
/*    */     }
/* 54 */     catch (InterruptedException interruptedexception) {
/*    */       
/* 56 */       logger.fatal("Error executing task", interruptedexception);
/*    */     } 
/*    */     
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T> T getLastElement(List<T> list) {
/* 64 */     return list.get(list.size() - 1);
/*    */   }
/*    */   
/*    */   public enum EnumOS
/*    */   {
/* 69 */     LINUX,
/* 70 */     SOLARIS,
/* 71 */     WINDOWS,
/* 72 */     OSX,
/* 73 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */