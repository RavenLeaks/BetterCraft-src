/*     */ package javazoom.jl.player;
/*     */ 
/*     */ import java.applet.Applet;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerApplet
/*     */   extends Applet
/*     */   implements Runnable
/*     */ {
/*     */   public static final String AUDIO_PARAMETER = "audioURL";
/*  45 */   private Player player = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private Thread playerThread = null;
/*     */   
/*  52 */   private String fileName = null;
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
/*     */   protected AudioDevice getAudioDevice() throws JavaLayerException {
/*  64 */     return FactoryRegistry.systemRegistry().createAudioDevice();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getAudioStream() {
/*  76 */     InputStream in = null;
/*     */ 
/*     */     
/*     */     try {
/*  80 */       URL url = getAudioURL();
/*  81 */       if (url != null) {
/*  82 */         in = url.openStream();
/*     */       }
/*  84 */     } catch (IOException ex) {
/*     */       
/*  86 */       System.err.println(ex);
/*     */     } 
/*  88 */     return in;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getAudioFileName() {
/*  93 */     String urlString = this.fileName;
/*  94 */     if (urlString == null)
/*     */     {
/*  96 */       urlString = getParameter("audioURL");
/*     */     }
/*  98 */     return urlString;
/*     */   }
/*     */ 
/*     */   
/*     */   protected URL getAudioURL() {
/* 103 */     String urlString = getAudioFileName();
/* 104 */     URL url = null;
/* 105 */     if (urlString != null) {
/*     */       
/*     */       try {
/*     */         
/* 109 */         url = new URL(getDocumentBase(), urlString);
/*     */       }
/* 111 */       catch (Exception ex) {
/*     */         
/* 113 */         System.err.println(ex);
/*     */       } 
/*     */     }
/* 116 */     return url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFileName(String name) {
/* 124 */     this.fileName = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 129 */     return this.fileName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void stopPlayer() throws JavaLayerException {
/* 138 */     if (this.player != null) {
/*     */       
/* 140 */       this.player.close();
/* 141 */       this.player = null;
/* 142 */       this.playerThread = null;
/*     */     } 
/*     */   }
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
/*     */   protected void play(InputStream in, AudioDevice dev) throws JavaLayerException {
/* 159 */     stopPlayer();
/*     */     
/* 161 */     if (in != null && dev != null) {
/*     */       
/* 163 */       this.player = new Player(in, dev);
/* 164 */       this.playerThread = createPlayerThread();
/* 165 */       this.playerThread.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Thread createPlayerThread() {
/* 175 */     return new Thread(this, "Audio player thread");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 191 */     String name = getAudioFileName();
/*     */     
/*     */     try {
/* 194 */       InputStream in = getAudioStream();
/* 195 */       AudioDevice dev = getAudioDevice();
/* 196 */       play(in, dev);
/*     */     }
/* 198 */     catch (JavaLayerException ex) {
/*     */       
/* 200 */       synchronized (System.err) {
/*     */         
/* 202 */         System.err.println("Unable to play " + name);
/* 203 */         ex.printStackTrace(System.err);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*     */     try {
/* 216 */       stopPlayer();
/*     */     }
/* 218 */     catch (JavaLayerException ex) {
/*     */       
/* 220 */       System.err.println(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 234 */     if (this.player != null)
/*     */       
/*     */       try {
/*     */         
/* 238 */         this.player.play();
/*     */       }
/* 240 */       catch (JavaLayerException ex) {
/*     */         
/* 242 */         System.err.println("Problem playing audio: " + ex);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\PlayerApplet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */