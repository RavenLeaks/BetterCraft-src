/*     */ package javazoom.jl.converter;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import javazoom.jl.decoder.Crc16;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class jlc
/*     */ {
/*     */   public static void main(String[] args) {
/*  49 */     long start = System.currentTimeMillis();
/*  50 */     int argc = args.length + 1;
/*  51 */     String[] argv = new String[argc];
/*  52 */     argv[0] = "jlc";
/*  53 */     for (int i = 0; i < args.length; i++) {
/*  54 */       argv[i + 1] = args[i];
/*     */     }
/*  56 */     jlcArgs ma = new jlcArgs();
/*  57 */     if (!ma.processArgs(argv)) {
/*  58 */       System.exit(1);
/*     */     }
/*  60 */     Converter conv = new Converter();
/*     */     
/*  62 */     int detail = ma.verbose_mode ? 
/*  63 */       ma.verbose_level : 
/*  64 */       0;
/*     */     
/*  66 */     Converter.ProgressListener listener = 
/*  67 */       new Converter.PrintWriterProgressListener(
/*  68 */         new PrintWriter(System.out, true), detail);
/*     */ 
/*     */     
/*     */     try {
/*  72 */       conv.convert(ma.filename, ma.output_filename, listener);
/*     */     }
/*  74 */     catch (JavaLayerException ex) {
/*     */       
/*  76 */       System.err.println("Convertion failure: " + ex);
/*     */     } 
/*     */     
/*  79 */     System.exit(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class jlcArgs
/*     */   {
/*     */     public int which_c;
/*     */ 
/*     */     
/*     */     public int output_mode;
/*     */ 
/*     */     
/*     */     public boolean use_own_scalefactor;
/*     */     
/*     */     public float scalefactor;
/*     */     
/*     */     public String output_filename;
/*     */     
/*     */     public String filename;
/*     */     
/*     */     public boolean verbose_mode;
/*     */     
/* 102 */     public int verbose_level = 3;
/*     */ 
/*     */     
/*     */     public jlcArgs() {
/* 106 */       this.which_c = 0;
/* 107 */       this.use_own_scalefactor = false;
/* 108 */       this.scalefactor = 32768.0F;
/*     */       
/* 110 */       this.verbose_mode = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean processArgs(String[] argv) {
/* 120 */       this.filename = null;
/*     */       
/* 122 */       Crc16[] crc = new Crc16[1];
/*     */       
/* 124 */       int argc = argv.length;
/*     */ 
/*     */       
/* 127 */       this.verbose_mode = false;
/* 128 */       this.output_mode = 0;
/* 129 */       this.output_filename = "";
/* 130 */       if (argc < 2 || argv[1].equals("-h")) {
/* 131 */         return Usage();
/*     */       }
/* 133 */       int i = 1;
/* 134 */       while (i < argc) {
/*     */ 
/*     */         
/* 137 */         if (argv[i].charAt(0) == '-') {
/*     */           
/* 139 */           if (argv[i].startsWith("-v")) {
/*     */             
/* 141 */             this.verbose_mode = true;
/* 142 */             if (argv[i].length() > 2) {
/*     */               
/*     */               try {
/*     */                 
/* 146 */                 String level = argv[i].substring(2);
/* 147 */                 this.verbose_level = Integer.parseInt(level);
/*     */               }
/* 149 */               catch (NumberFormatException ex) {
/*     */                 
/* 151 */                 System.err.println("Invalid verbose level. Using default.");
/*     */               } 
/*     */             }
/* 154 */             System.out.println("Verbose Activated (level " + this.verbose_level + ")");
/*     */ 
/*     */           
/*     */           }
/* 158 */           else if (argv[i].equals("-p")) {
/*     */             
/* 160 */             if (++i == argc) {
/*     */               
/* 162 */               System.out.println("Please specify an output filename after the -p option!");
/* 163 */               System.exit(1);
/*     */             } 
/*     */             
/* 166 */             this.output_filename = argv[i];
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 178 */             return Usage();
/*     */           } 
/*     */         } else {
/*     */           
/* 182 */           this.filename = argv[i];
/* 183 */           System.out.println("FileName = " + argv[i]);
/* 184 */           if (this.filename == null) return Usage(); 
/*     */         } 
/* 186 */         i++;
/*     */       } 
/* 188 */       if (this.filename == null) {
/* 189 */         return Usage();
/*     */       }
/* 191 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean Usage() {
/* 200 */       System.out.println("JavaLayer Converter :");
/* 201 */       System.out.println("  -v[x]         verbose mode. ");
/* 202 */       System.out.println("                default = 2");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       System.out.println("  -p name    output as a PCM wave file");
/* 210 */       System.out.println("");
/* 211 */       System.out.println("  More info on http://www.javazoom.net");
/*     */       
/* 213 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\converter\jlc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */