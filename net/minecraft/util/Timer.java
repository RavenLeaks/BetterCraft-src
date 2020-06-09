/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   public int elapsedTicks;
/*    */   public float field_194147_b;
/*    */   public float field_194148_c;
/*    */   private long lastSyncSysClock;
/*    */   private float field_194149_e;
/*    */   
/*    */   public Timer(float tps) {
/* 22 */     this.field_194149_e = 1000.0F / tps;
/* 23 */     this.lastSyncSysClock = Minecraft.getSystemTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTimer() {
/* 31 */     long i = Minecraft.getSystemTime();
/* 32 */     this.field_194148_c = (float)(i - this.lastSyncSysClock) / this.field_194149_e;
/* 33 */     this.lastSyncSysClock = i;
/* 34 */     this.field_194147_b += this.field_194148_c;
/* 35 */     this.elapsedTicks = (int)this.field_194147_b;
/* 36 */     this.field_194147_b -= this.elapsedTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */