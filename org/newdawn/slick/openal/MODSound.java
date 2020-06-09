/*    */ package org.newdawn.slick.openal;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.IntBuffer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.openal.AL10;
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
/*    */ public class MODSound
/*    */   extends AudioImpl
/*    */ {
/*    */   private SoundStore store;
/*    */   
/*    */   public MODSound(SoundStore store, InputStream in) throws IOException {
/* 31 */     this.store = store;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int playAsMusic(float pitch, float gain, boolean loop) {
/* 39 */     cleanUpSource();
/*    */     
/* 41 */     this.store.setCurrentMusicVolume(gain);
/*    */     
/* 43 */     this.store.setMOD(this);
/*    */     
/* 45 */     return this.store.getSource(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void cleanUpSource() {
/* 52 */     AL10.alSourceStop(this.store.getSource(0));
/* 53 */     IntBuffer buffer = BufferUtils.createIntBuffer(1);
/* 54 */     int queued = AL10.alGetSourcei(this.store.getSource(0), 4117);
/*    */     
/* 56 */     while (queued > 0) {
/*    */       
/* 58 */       AL10.alSourceUnqueueBuffers(this.store.getSource(0), buffer);
/* 59 */       queued--;
/*    */     } 
/*    */     
/* 62 */     AL10.alSourcei(this.store.getSource(0), 4105, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void poll() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int playAsSoundEffect(float pitch, float gain, boolean loop) {
/* 76 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {
/* 83 */     this.store.setMOD(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPosition() {
/* 90 */     throw new RuntimeException("Positioning on modules is not currently supported");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean setPosition(float position) {
/* 97 */     throw new RuntimeException("Positioning on modules is not currently supported");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\openal\MODSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */