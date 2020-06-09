/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ThreadLanServerPing
/*     */   extends Thread {
/*  14 */   private static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
/*  15 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final String motd;
/*     */   
/*     */   private final DatagramSocket socket;
/*     */   
/*     */   private boolean isStopping = true;
/*     */   private final String address;
/*     */   
/*     */   public ThreadLanServerPing(String p_i1321_1_, String p_i1321_2_) throws IOException {
/*  25 */     super("LanServerPinger #" + UNIQUE_THREAD_ID.incrementAndGet());
/*  26 */     this.motd = p_i1321_1_;
/*  27 */     this.address = p_i1321_2_;
/*  28 */     setDaemon(true);
/*  29 */     this.socket = new DatagramSocket();
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  34 */     String s = getPingResponse(this.motd, this.address);
/*  35 */     byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
/*     */     
/*  37 */     while (!isInterrupted() && this.isStopping) {
/*     */ 
/*     */       
/*     */       try {
/*  41 */         InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
/*  42 */         DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length, inetaddress, 4445);
/*  43 */         this.socket.send(datagrampacket);
/*     */       }
/*  45 */       catch (IOException ioexception) {
/*     */         
/*  47 */         LOGGER.warn("LanServerPinger: {}", ioexception.getMessage());
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/*     */       try {
/*  53 */         sleep(1500L);
/*     */       }
/*  55 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interrupt() {
/*  64 */     super.interrupt();
/*  65 */     this.isStopping = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPingResponse(String p_77525_0_, String p_77525_1_) {
/*  70 */     return "[MOTD]" + p_77525_0_ + "[/MOTD][AD]" + p_77525_1_ + "[/AD]";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getMotdFromPingResponse(String p_77524_0_) {
/*  75 */     int i = p_77524_0_.indexOf("[MOTD]");
/*     */     
/*  77 */     if (i < 0)
/*     */     {
/*  79 */       return "missing no";
/*     */     }
/*     */ 
/*     */     
/*  83 */     int j = p_77524_0_.indexOf("[/MOTD]", i + "[MOTD]".length());
/*  84 */     return (j < i) ? "missing no" : p_77524_0_.substring(i + "[MOTD]".length(), j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAdFromPingResponse(String p_77523_0_) {
/*  90 */     int i = p_77523_0_.indexOf("[/MOTD]");
/*     */     
/*  92 */     if (i < 0)
/*     */     {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  98 */     int j = p_77523_0_.indexOf("[/MOTD]", i + "[/MOTD]".length());
/*     */     
/* 100 */     if (j >= 0)
/*     */     {
/* 102 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 106 */     int k = p_77523_0_.indexOf("[AD]", i + "[/MOTD]".length());
/*     */     
/* 108 */     if (k < 0)
/*     */     {
/* 110 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 114 */     int l = p_77523_0_.indexOf("[/AD]", k + "[AD]".length());
/* 115 */     return (l < k) ? null : p_77523_0_.substring(k + "[AD]".length(), l);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\ThreadLanServerPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */