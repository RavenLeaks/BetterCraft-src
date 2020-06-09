/*     */ package me.nzxter.bettercraft.mods.rcon;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class RconClient
/*     */   implements Closeable
/*     */ {
/*     */   private static final int AUTHENTICATION_FAILURE_ID = -1;
/*  37 */   private static final Charset PAYLOAD_CHARSET = StandardCharsets.US_ASCII;
/*     */   
/*     */   private static final int TYPE_COMMAND = 2;
/*     */   
/*     */   private static final int TYPE_AUTH = 3;
/*     */   private final SocketChannel socketChannel;
/*     */   private final AtomicInteger currentRequestId;
/*     */   
/*     */   private RconClient(SocketChannel socketChannel) {
/*  46 */     this.socketChannel = Objects.<SocketChannel>requireNonNull(socketChannel, "socketChannel");
/*  47 */     this.currentRequestId = new AtomicInteger(1);
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
/*     */   public static RconClient open(String host, int port, String password) {
/*     */     SocketChannel socketChannel;
/*     */     try {
/*  66 */       socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
/*  67 */     } catch (IOException e) {
/*  68 */       throw new RconClientException("Failed to open socket to " + host + ":" + port, e);
/*     */     } 
/*     */     
/*  71 */     RconClient rconClient = new RconClient(socketChannel);
/*     */     try {
/*  73 */       rconClient.authenticate(password);
/*  74 */     } catch (Exception authException) {
/*     */       try {
/*  76 */         rconClient.close();
/*  77 */       } catch (Exception closingException) {
/*  78 */         authException.addSuppressed(closingException);
/*     */       } 
/*  80 */       throw authException;
/*     */     } 
/*  82 */     return rconClient;
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
/*     */   
/*     */   public String sendCommand(String command) {
/* 100 */     return send(2, command);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 106 */       this.socketChannel.close();
/* 107 */     } catch (IOException e) {
/* 108 */       throw new RconClientException("Failed to close socket channel", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void authenticate(String password) {
/* 113 */     send(3, password);
/*     */   }
/*     */   
/*     */   private String send(int type, String payload) {
/* 117 */     int requestId = this.currentRequestId.getAndIncrement();
/*     */     
/* 119 */     ByteBuffer buffer = toByteBuffer(requestId, type, payload);
/*     */     try {
/* 121 */       this.socketChannel.write(buffer);
/* 122 */     } catch (IOException e) {
/* 123 */       throw new RconClientException("Failed to write " + buffer.capacity() + " bytes", e);
/*     */     } 
/*     */     
/* 126 */     ByteBuffer responseBuffer = readResponse();
/* 127 */     int responseId = responseBuffer.getInt();
/*     */     
/* 129 */     if (responseId == -1) {
/* 130 */       throw new AuthFailureException();
/*     */     }
/*     */     
/* 133 */     if (responseId != requestId) {
/* 134 */       throw new RconClientException("Sent request id " + requestId + " but received " + responseId);
/*     */     }
/*     */ 
/*     */     
/* 138 */     int responseType = responseBuffer.getInt();
/*     */     
/* 140 */     byte[] bodyBytes = new byte[responseBuffer.remaining()];
/* 141 */     responseBuffer.get(bodyBytes);
/* 142 */     return new String(bodyBytes, PAYLOAD_CHARSET);
/*     */   }
/*     */   
/*     */   private ByteBuffer readResponse() {
/* 146 */     int size = readData(4).getInt();
/* 147 */     ByteBuffer dataBuffer = readData(size - 2);
/* 148 */     ByteBuffer nullsBuffer = readData(2);
/*     */     
/* 150 */     byte null1 = nullsBuffer.get(0);
/* 151 */     byte null2 = nullsBuffer.get(1);
/*     */     
/* 153 */     if (null1 != 0 || null2 != 0) {
/* 154 */       throw new RconClientException("Expected 2 null bytes but received " + null1 + " and " + null2);
/*     */     }
/*     */     
/* 157 */     return dataBuffer;
/*     */   }
/*     */   private ByteBuffer readData(int size) {
/*     */     int readCount;
/* 161 */     ByteBuffer buffer = ByteBuffer.allocate(size);
/*     */     
/*     */     try {
/* 164 */       readCount = this.socketChannel.read(buffer);
/* 165 */     } catch (IOException e) {
/* 166 */       throw new RconClientException("Failed to read " + size + " bytes", e);
/*     */     } 
/*     */     
/* 169 */     if (readCount != size) {
/* 170 */       throw new RconClientException("Expected " + size + " bytes but received " + readCount);
/*     */     }
/*     */     
/* 173 */     buffer.position(0);
/* 174 */     buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 175 */     return buffer;
/*     */   }
/*     */   
/*     */   private static ByteBuffer toByteBuffer(int requestId, int type, String payload) {
/* 179 */     ByteBuffer buffer = ByteBuffer.allocate(12 + payload.length() + 2);
/* 180 */     buffer.order(ByteOrder.LITTLE_ENDIAN);
/*     */     
/* 182 */     buffer.putInt(8 + payload.length() + 2);
/* 183 */     buffer.putInt(requestId);
/* 184 */     buffer.putInt(type);
/* 185 */     buffer.put(payload.getBytes(PAYLOAD_CHARSET));
/* 186 */     buffer.put((byte)0);
/* 187 */     buffer.put((byte)0);
/*     */     
/* 189 */     buffer.position(0);
/* 190 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\rcon\RconClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */