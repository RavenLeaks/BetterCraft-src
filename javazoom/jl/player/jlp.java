/*     */ package javazoom.jl.player;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileInputStream;
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
/*     */ public class jlp
/*     */ {
/*  42 */   private String fFilename = null;
/*     */   
/*     */   private boolean remote = false;
/*     */   
/*     */   public static void main(String[] args) {
/*  47 */     int retval = 0;
/*     */     
/*     */     try {
/*  50 */       jlp player = createInstance(args);
/*  51 */       if (player != null) {
/*  52 */         player.play();
/*     */       }
/*  54 */     } catch (Exception ex) {
/*     */       
/*  56 */       System.err.println(ex);
/*  57 */       ex.printStackTrace(System.err);
/*  58 */       retval = 1;
/*     */     } 
/*  60 */     System.exit(retval);
/*     */   }
/*     */ 
/*     */   
/*     */   public static jlp createInstance(String[] args) {
/*  65 */     jlp player = new jlp();
/*  66 */     if (!player.parseArgs(args))
/*  67 */       player = null; 
/*  68 */     return player;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private jlp() {}
/*     */ 
/*     */   
/*     */   public jlp(String filename) {
/*  77 */     init(filename);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init(String filename) {
/*  82 */     this.fFilename = filename;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean parseArgs(String[] args) {
/*  87 */     boolean parsed = false;
/*  88 */     if (args.length == 1) {
/*     */       
/*  90 */       init(args[0]);
/*  91 */       parsed = true;
/*  92 */       this.remote = false;
/*     */     }
/*  94 */     else if (args.length == 2) {
/*     */       
/*  96 */       if (!args[0].equals("-url"))
/*     */       {
/*  98 */         showUsage();
/*     */       }
/*     */       else
/*     */       {
/* 102 */         init(args[1]);
/* 103 */         parsed = true;
/* 104 */         this.remote = true;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 109 */       showUsage();
/*     */     } 
/* 111 */     return parsed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void showUsage() {
/* 116 */     System.out.println("Usage: jlp [-url] <filename>");
/* 117 */     System.out.println("");
/* 118 */     System.out.println(" e.g. : java javazoom.jl.player.jlp localfile.mp3");
/* 119 */     System.out.println("        java javazoom.jl.player.jlp -url http://www.server.com/remotefile.mp3");
/* 120 */     System.out.println("        java javazoom.jl.player.jlp -url http://www.shoutcastserver.com:8000");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() throws JavaLayerException {
/*     */     try {
/* 128 */       System.out.println("playing " + this.fFilename + "...");
/* 129 */       InputStream in = null;
/* 130 */       if (this.remote) { in = getURLInputStream(); }
/* 131 */       else { in = getInputStream(); }
/* 132 */        AudioDevice dev = getAudioDevice();
/* 133 */       Player player = new Player(in, dev);
/* 134 */       player.play();
/*     */     }
/* 136 */     catch (IOException ex) {
/*     */       
/* 138 */       throw new JavaLayerException("Problem playing file " + this.fFilename, ex);
/*     */     }
/* 140 */     catch (Exception ex) {
/*     */       
/* 142 */       throw new JavaLayerException("Problem playing file " + this.fFilename, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getURLInputStream() throws Exception {
/* 153 */     URL url = new URL(this.fFilename);
/* 154 */     InputStream fin = url.openStream();
/* 155 */     BufferedInputStream bin = new BufferedInputStream(fin);
/* 156 */     return bin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream getInputStream() throws IOException {
/* 165 */     FileInputStream fin = new FileInputStream(this.fFilename);
/* 166 */     BufferedInputStream bin = new BufferedInputStream(fin);
/* 167 */     return bin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AudioDevice getAudioDevice() throws JavaLayerException {
/* 173 */     return FactoryRegistry.systemRegistry().createAudioDevice();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\jlp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */