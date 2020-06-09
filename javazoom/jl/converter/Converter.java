/*     */ package javazoom.jl.converter;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import javazoom.jl.decoder.Bitstream;
/*     */ import javazoom.jl.decoder.Decoder;
/*     */ import javazoom.jl.decoder.Header;
/*     */ import javazoom.jl.decoder.JavaLayerException;
/*     */ import javazoom.jl.decoder.Obuffer;
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
/*     */ 
/*     */ 
/*     */ public class Converter
/*     */ {
/*     */   public synchronized void convert(String sourceName, String destName) throws JavaLayerException {
/*  59 */     convert(sourceName, destName, (ProgressListener)null, (Decoder.Params)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void convert(String sourceName, String destName, ProgressListener progressListener) throws JavaLayerException {
/*  66 */     convert(sourceName, destName, progressListener, (Decoder.Params)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convert(String sourceName, String destName, ProgressListener progressListener, Decoder.Params decoderParams) throws JavaLayerException {
/*  74 */     if (destName.length() == 0)
/*  75 */       destName = null; 
/*     */     try {
/*  77 */       InputStream in = openInput(sourceName);
/*  78 */       convert(in, destName, progressListener, decoderParams);
/*  79 */       in.close();
/*  80 */     } catch (IOException ioe) {
/*  81 */       throw new JavaLayerException(ioe.getLocalizedMessage(), ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void convert(InputStream sourceStream, String destName, ProgressListener progressListener, Decoder.Params decoderParams) throws JavaLayerException {
/*  89 */     if (progressListener == null)
/*  90 */       progressListener = PrintWriterProgressListener.newStdOut(
/*  91 */           0); 
/*     */     try {
/*  93 */       if (!(sourceStream instanceof BufferedInputStream))
/*  94 */         sourceStream = new BufferedInputStream(sourceStream); 
/*  95 */       int frameCount = -1;
/*  96 */       if (sourceStream.markSupported()) {
/*  97 */         sourceStream.mark(-1);
/*  98 */         frameCount = countFrames(sourceStream);
/*  99 */         sourceStream.reset();
/*     */       } 
/* 101 */       progressListener.converterUpdate(1, frameCount, 0);
/*     */ 
/*     */       
/* 104 */       Obuffer output = null;
/* 105 */       Decoder decoder = new Decoder(decoderParams);
/* 106 */       Bitstream stream = new Bitstream(sourceStream);
/*     */       
/* 108 */       if (frameCount == -1) {
/* 109 */         frameCount = Integer.MAX_VALUE;
/*     */       }
/* 111 */       int frame = 0;
/* 112 */       long startTime = System.currentTimeMillis();
/*     */ 
/*     */       
/*     */       try {
/* 116 */         for (; frame < frameCount; frame++)
/*     */         {
/*     */           try
/*     */           {
/* 120 */             Header header = stream.readFrame();
/* 121 */             if (header == null) {
/*     */               break;
/*     */             }
/* 124 */             progressListener.readFrame(frame, header);
/*     */             
/* 126 */             if (output == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 132 */               int channels = (header.mode() == 3) ? 1 : 2;
/* 133 */               int freq = header.frequency();
/* 134 */               output = new WaveFileObuffer(channels, freq, destName);
/* 135 */               decoder.setOutputBuffer(output);
/*     */             } 
/*     */             
/* 138 */             Obuffer decoderOutput = decoder.decodeFrame(header, stream);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 143 */             if (decoderOutput != output) {
/* 144 */               throw new InternalError("Output buffers are different.");
/*     */             }
/*     */             
/* 147 */             progressListener.decodedFrame(frame, header, output);
/*     */             
/* 149 */             stream.closeFrame();
/*     */           
/*     */           }
/* 152 */           catch (Exception ex)
/*     */           {
/* 154 */             boolean stop = !progressListener.converterException(ex);
/*     */             
/* 156 */             if (stop)
/*     */             {
/* 158 */               throw new JavaLayerException(ex.getLocalizedMessage(), ex);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 167 */         if (output != null) {
/* 168 */           output.close();
/*     */         }
/*     */       } 
/* 171 */       int time = (int)(System.currentTimeMillis() - startTime);
/* 172 */       progressListener.converterUpdate(2, 
/* 173 */           time, frame);
/*     */     }
/* 175 */     catch (IOException ex) {
/*     */       
/* 177 */       throw new JavaLayerException(ex.getLocalizedMessage(), ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int countFrames(InputStream in) {
/* 184 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InputStream openInput(String fileName) throws IOException {
/* 192 */     File file = new File(fileName);
/* 193 */     InputStream fileIn = new FileInputStream(file);
/* 194 */     BufferedInputStream bufIn = new BufferedInputStream(fileIn);
/*     */     
/* 196 */     return bufIn;
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
/*     */   
/*     */   public static class PrintWriterProgressListener
/*     */     implements ProgressListener
/*     */   {
/*     */     public static final int NO_DETAIL = 0;
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
/*     */     public static final int EXPERT_DETAIL = 1;
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
/*     */     public static final int VERBOSE_DETAIL = 2;
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
/*     */     public static final int DEBUG_DETAIL = 7;
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
/*     */     public static final int MAX_DETAIL = 10;
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
/*     */     private PrintWriter pw;
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
/*     */     private int detailLevel;
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
/*     */     public static PrintWriterProgressListener newStdOut(int detail) {
/* 312 */       return new PrintWriterProgressListener(
/* 313 */           new PrintWriter(System.out, true), detail);
/*     */     }
/*     */ 
/*     */     
/*     */     public PrintWriterProgressListener(PrintWriter writer, int detailLevel) {
/* 318 */       this.pw = writer;
/* 319 */       this.detailLevel = detailLevel;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDetail(int detail) {
/* 325 */       return (this.detailLevel >= detail);
/*     */     }
/*     */ 
/*     */     
/*     */     public void converterUpdate(int updateID, int param1, int param2) {
/* 330 */       if (isDetail(2))
/*     */       {
/* 332 */         switch (updateID) {
/*     */ 
/*     */           
/*     */           case 2:
/* 336 */             if (param2 == 0) {
/* 337 */               param2 = 1;
/*     */             }
/* 339 */             this.pw.println();
/* 340 */             this.pw.println("Converted " + param2 + " frames in " + param1 + " ms (" + (
/* 341 */                 param1 / param2) + " ms per frame.)");
/*     */             break;
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public void parsedFrame(int frameNo, Header header) {
/* 348 */       if (frameNo == 0 && isDetail(2)) {
/*     */         
/* 350 */         String headerString = header.toString();
/* 351 */         this.pw.println("File is a " + headerString);
/*     */       }
/* 353 */       else if (isDetail(10)) {
/*     */         
/* 355 */         String headerString = header.toString();
/* 356 */         this.pw.println("Prased frame " + frameNo + ": " + headerString);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFrame(int frameNo, Header header) {
/* 362 */       if (frameNo == 0 && isDetail(2)) {
/*     */         
/* 364 */         String headerString = header.toString();
/* 365 */         this.pw.println("File is a " + headerString);
/*     */       }
/* 367 */       else if (isDetail(10)) {
/*     */         
/* 369 */         String headerString = header.toString();
/* 370 */         this.pw.println("Read frame " + frameNo + ": " + headerString);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void decodedFrame(int frameNo, Header header, Obuffer o) {
/* 376 */       if (isDetail(10)) {
/*     */         
/* 378 */         String headerString = header.toString();
/* 379 */         this.pw.println("Decoded frame " + frameNo + ": " + headerString);
/* 380 */         this.pw.println("Output: " + o);
/*     */       }
/* 382 */       else if (isDetail(2)) {
/*     */         
/* 384 */         if (frameNo == 0) {
/*     */           
/* 386 */           this.pw.print("Converting.");
/* 387 */           this.pw.flush();
/*     */         } 
/*     */         
/* 390 */         if (frameNo % 10 == 0) {
/*     */           
/* 392 */           this.pw.print('.');
/* 393 */           this.pw.flush();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean converterException(Throwable t) {
/* 400 */       if (this.detailLevel > 0) {
/*     */         
/* 402 */         t.printStackTrace(this.pw);
/* 403 */         this.pw.flush();
/*     */       } 
/* 405 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ProgressListener {
/*     */     public static final int UPDATE_FRAME_COUNT = 1;
/*     */     public static final int UPDATE_CONVERT_COMPLETE = 2;
/*     */     
/*     */     void converterUpdate(int param1Int1, int param1Int2, int param1Int3);
/*     */     
/*     */     void parsedFrame(int param1Int, Header param1Header);
/*     */     
/*     */     void readFrame(int param1Int, Header param1Header);
/*     */     
/*     */     void decodedFrame(int param1Int, Header param1Header, Obuffer param1Obuffer);
/*     */     
/*     */     boolean converterException(Throwable param1Throwable);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\converter\Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */