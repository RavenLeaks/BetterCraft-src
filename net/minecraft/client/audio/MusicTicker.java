/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.util.SoundEvent;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class MusicTicker
/*    */   implements ITickable {
/* 12 */   private final Random rand = new Random();
/*    */   private final Minecraft mc;
/*    */   private ISound currentMusic;
/* 15 */   private int timeUntilNextMusic = 100;
/*    */ 
/*    */   
/*    */   public MusicTicker(Minecraft mcIn) {
/* 19 */     this.mc = mcIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 27 */     MusicType musicticker$musictype = this.mc.getAmbientMusicType();
/*    */     
/* 29 */     if (this.currentMusic != null) {
/*    */       
/* 31 */       if (!musicticker$musictype.getMusicLocation().getSoundName().equals(this.currentMusic.getSoundLocation())) {
/*    */         
/* 33 */         this.mc.getSoundHandler().stopSound(this.currentMusic);
/* 34 */         this.timeUntilNextMusic = MathHelper.getInt(this.rand, 0, musicticker$musictype.getMinDelay() / 2);
/*    */       } 
/*    */       
/* 37 */       if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
/*    */         
/* 39 */         this.currentMusic = null;
/* 40 */         this.timeUntilNextMusic = Math.min(MathHelper.getInt(this.rand, musicticker$musictype.getMinDelay(), musicticker$musictype.getMaxDelay()), this.timeUntilNextMusic);
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, musicticker$musictype.getMaxDelay());
/*    */     
/* 46 */     if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
/*    */     {
/* 48 */       playMusic(musicticker$musictype);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void playMusic(MusicType requestedMusicType) {
/* 57 */     this.currentMusic = PositionedSoundRecord.getMusicRecord(requestedMusicType.getMusicLocation());
/* 58 */     this.mc.getSoundHandler().playSound(this.currentMusic);
/* 59 */     this.timeUntilNextMusic = Integer.MAX_VALUE;
/*    */   }
/*    */   
/*    */   public enum MusicType
/*    */   {
/* 64 */     MENU((String)SoundEvents.MUSIC_MENU, 20, 600),
/* 65 */     GAME((String)SoundEvents.MUSIC_GAME, 12000, 24000),
/* 66 */     CREATIVE((String)SoundEvents.MUSIC_CREATIVE, 1200, 3600),
/* 67 */     CREDITS((String)SoundEvents.MUSIC_CREDITS, 0, 0),
/* 68 */     NETHER((String)SoundEvents.MUSIC_NETHER, 1200, 3600),
/* 69 */     END_BOSS((String)SoundEvents.MUSIC_DRAGON, 0, 0),
/* 70 */     END((String)SoundEvents.MUSIC_END, 6000, 24000);
/*    */     
/*    */     private final SoundEvent musicLocation;
/*    */     
/*    */     private final int minDelay;
/*    */     private final int maxDelay;
/*    */     
/*    */     MusicType(SoundEvent musicLocationIn, int minDelayIn, int maxDelayIn) {
/* 78 */       this.musicLocation = musicLocationIn;
/* 79 */       this.minDelay = minDelayIn;
/* 80 */       this.maxDelay = maxDelayIn;
/*    */     }
/*    */ 
/*    */     
/*    */     public SoundEvent getMusicLocation() {
/* 85 */       return this.musicLocation;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getMinDelay() {
/* 90 */       return this.minDelay;
/*    */     }
/*    */ 
/*    */     
/*    */     public int getMaxDelay() {
/* 95 */       return this.maxDelay;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\MusicTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */