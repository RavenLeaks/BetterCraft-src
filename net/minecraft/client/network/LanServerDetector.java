/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LanServerDetector
/*     */ {
/*  19 */   private static final AtomicInteger ATOMIC_COUNTER = new AtomicInteger(0);
/*  20 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   public static class LanServerList
/*     */   {
/*  24 */     private final List<LanServerInfo> listOfLanServers = Lists.newArrayList();
/*     */     
/*     */     boolean wasUpdated;
/*     */     
/*     */     public synchronized boolean getWasUpdated() {
/*  29 */       return this.wasUpdated;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void setWasNotUpdated() {
/*  34 */       this.wasUpdated = false;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized List<LanServerInfo> getLanServers() {
/*  39 */       return Collections.unmodifiableList(this.listOfLanServers);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void addServer(String pingResponse, InetAddress ipAddress) {
/*  44 */       String s = ThreadLanServerPing.getMotdFromPingResponse(pingResponse);
/*  45 */       String s1 = ThreadLanServerPing.getAdFromPingResponse(pingResponse);
/*     */       
/*  47 */       if (s1 != null) {
/*     */         
/*  49 */         s1 = String.valueOf(ipAddress.getHostAddress()) + ":" + s1;
/*  50 */         boolean flag = false;
/*     */         
/*  52 */         for (LanServerInfo lanserverinfo : this.listOfLanServers) {
/*     */           
/*  54 */           if (lanserverinfo.getServerIpPort().equals(s1)) {
/*     */             
/*  56 */             lanserverinfo.updateLastSeen();
/*  57 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  62 */         if (!flag) {
/*     */           
/*  64 */           this.listOfLanServers.add(new LanServerInfo(s, s1));
/*  65 */           this.wasUpdated = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ThreadLanServerFind
/*     */     extends Thread
/*     */   {
/*     */     private final LanServerDetector.LanServerList localServerList;
/*     */     private final InetAddress broadcastAddress;
/*     */     private final MulticastSocket socket;
/*     */     
/*     */     public ThreadLanServerFind(LanServerDetector.LanServerList list) throws IOException {
/*  79 */       super("LanServerDetector #" + LanServerDetector.ATOMIC_COUNTER.incrementAndGet());
/*  80 */       this.localServerList = list;
/*  81 */       setDaemon(true);
/*  82 */       this.socket = new MulticastSocket(4445);
/*  83 */       this.broadcastAddress = InetAddress.getByName("224.0.2.60");
/*  84 */       this.socket.setSoTimeout(5000);
/*  85 */       this.socket.joinGroup(this.broadcastAddress);
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*  90 */       byte[] abyte = new byte[1024];
/*     */       
/*  92 */       while (!isInterrupted()) {
/*     */         
/*  94 */         DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);
/*     */ 
/*     */         
/*     */         try {
/*  98 */           this.socket.receive(datagrampacket);
/*     */         }
/* 100 */         catch (SocketTimeoutException var5) {
/*     */           
/*     */           continue;
/*     */         }
/* 104 */         catch (IOException ioexception) {
/*     */           
/* 106 */           LanServerDetector.LOGGER.error("Couldn't ping server", ioexception);
/*     */           
/*     */           break;
/*     */         } 
/* 110 */         String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength(), StandardCharsets.UTF_8);
/* 111 */         LanServerDetector.LOGGER.debug("{}: {}", datagrampacket.getAddress(), s);
/* 112 */         this.localServerList.addServer(s, datagrampacket.getAddress());
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 117 */         this.socket.leaveGroup(this.broadcastAddress);
/*     */       }
/* 119 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       this.socket.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\network\LanServerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */