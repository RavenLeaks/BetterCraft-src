/*     */ package javazoom.jl.player.advanced;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javazoom.jl.decoder.JavaLayerException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class jlap
/*     */ {
/*     */   public static void main(String[] args) {
/*  38 */     jlap test = new jlap();
/*  39 */     if (args.length != 1) {
/*     */       
/*  41 */       test.showUsage();
/*  42 */       System.exit(0);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/*  48 */         test.play(args[0]);
/*     */       }
/*  50 */       catch (Exception ex) {
/*     */         
/*  52 */         System.err.println(ex.getMessage());
/*  53 */         System.exit(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void play(String filename) throws JavaLayerException, IOException {
/*  60 */     InfoListener lst = new InfoListener();
/*  61 */     playMp3(new File(filename), lst);
/*     */   }
/*     */ 
/*     */   
/*     */   public void showUsage() {
/*  66 */     System.out.println("Usage: jla <filename>");
/*  67 */     System.out.println("");
/*  68 */     System.out.println(" e.g. : java javazoom.jl.player.advanced.jlap localfile.mp3");
/*     */   }
/*     */ 
/*     */   
/*     */   public static AdvancedPlayer playMp3(File mp3, PlaybackListener listener) throws IOException, JavaLayerException {
/*  73 */     return playMp3(mp3, 0, 2147483647, listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AdvancedPlayer playMp3(File mp3, int start, int end, PlaybackListener listener) throws IOException, JavaLayerException {
/*  78 */     return playMp3(new BufferedInputStream(new FileInputStream(mp3)), start, end, listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public static AdvancedPlayer playMp3(InputStream is, final int start, final int end, PlaybackListener listener) throws JavaLayerException {
/*  83 */     final AdvancedPlayer player = new AdvancedPlayer(is);
/*  84 */     player.setPlayBackListener(listener);
/*     */     
/*  86 */     (new Thread()
/*     */       {
/*     */         
/*     */         public void run()
/*     */         {
/*     */           try {
/*  92 */             player.play(start, end);
/*     */           }
/*  94 */           catch (Exception e) {
/*     */             
/*  96 */             throw new RuntimeException(e.getMessage());
/*     */           } 
/*     */         }
/*  99 */       }).start();
/* 100 */     return player;
/*     */   }
/*     */   
/*     */   public class InfoListener
/*     */     extends PlaybackListener
/*     */   {
/*     */     public void playbackStarted(PlaybackEvent evt) {
/* 107 */       System.out.println("Play started from frame " + evt.getFrame());
/*     */     }
/*     */ 
/*     */     
/*     */     public void playbackFinished(PlaybackEvent evt) {
/* 112 */       System.out.println("Play completed at frame " + evt.getFrame());
/* 113 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\advanced\jlap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */