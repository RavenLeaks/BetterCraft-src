/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.network.AbstractPacket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TimeHelperUtils
/*    */ {
/* 12 */   public long time = System.nanoTime() / 1000000L;
/*    */   public static long currentCalcLag;
/*    */   
/*    */   public boolean hasTimeElapsed(long time, boolean reset) {
/* 16 */     if (getTime() >= time) {
/* 17 */       if (reset) {
/* 18 */         reset();
/*    */       }
/* 20 */       return true;
/*    */     } 
/* 22 */     return false;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 26 */     return System.nanoTime() / 1000000L - this.time;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 30 */     this.time = System.nanoTime() / 1000000L;
/*    */   }
/*    */   
/*    */   public static long bytesToMb(long l) {
/* 34 */     return l / 1024L / 1024L;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static long getLag() {
/* 40 */     return System.currentTimeMillis() - currentCalcLag;
/*    */   }
/*    */   
/*    */   public static long getFormattedLag() {
/* 44 */     long currentLag = getLag();
/* 45 */     if (currentLag >= 2500L && currentLag < 5000000L) {
/* 46 */       return currentLag;
/*    */     }
/* 48 */     return 0L;
/*    */   }
/*    */   
/* 51 */   private static TimeHelperUtils tpsTimer = new TimeHelperUtils();
/* 52 */   public static double lastTps = 20.0D;
/*    */   
/* 54 */   private static ArrayList<Long> times = new ArrayList<>();
/*    */   
/*    */   public static void onPacketRecieved(AbstractPacket modPacket) {
/* 57 */     if (modPacket instanceof net.minecraft.network.play.server.SPacketTimeUpdate) {
/*    */       
/* 59 */       currentCalcLag = System.currentTimeMillis();
/*    */       
/* 61 */       times.add(Long.valueOf(Math.max(1000L, tpsTimer.getTime())));
/*    */       
/* 63 */       long timesAdded = 0L;
/* 64 */       if (times.size() > 5) {
/* 65 */         times.remove(0);
/*    */       }
/* 67 */       for (Iterator<Long> iterator = times.iterator(); iterator.hasNext(); ) { long l = ((Long)iterator.next()).longValue();
/* 68 */         timesAdded += l; }
/*    */       
/* 70 */       long roundedTps = timesAdded / times.size();
/* 71 */       lastTps = 20.0D / roundedTps * 1000.0D;
/* 72 */       tpsTimer.reset();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\TimeHelperUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */