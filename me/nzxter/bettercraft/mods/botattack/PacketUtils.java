/*     */ package me.nzxter.bettercraft.mods.botattack;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ public class PacketUtils {
/*     */   public static byte[] readByteArray(DataInputStream in) {
/*  10 */     int length = readVarInt(in);
/*  11 */     byte[] data = new byte[length];
/*     */ 
/*     */     
/*     */     try {
/*  15 */       in.readFully(data);
/*  16 */     } catch (IOException ex) {
/*     */       
/*  18 */       throw new RuntimeException(ex);
/*     */     } 
/*     */     
/*  21 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int readVarInt(DataInputStream in) {
/*  26 */     int numRead = 0;
/*  27 */     int result = 0;
/*     */ 
/*     */     
/*     */     try {
/*     */       byte read;
/*     */       
/*     */       do {
/*  34 */         read = in.readByte();
/*     */         
/*  36 */         int value = read & Byte.MAX_VALUE;
/*     */         
/*  38 */         result |= value << 7 * numRead;
/*  39 */         numRead++;
/*     */         
/*  41 */         if (numRead > 5) throw new RuntimeException("VarInt is too big"); 
/*  42 */       } while ((read & 0x80) != 0);
/*  43 */     } catch (Exception ex) {
/*     */       
/*  45 */       throw new RuntimeException(ex);
/*     */     } 
/*     */     
/*  48 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeVarInt(DataOutputStream out, int paramInt) {
/*     */     try {
/*     */       while (true) {
/*  57 */         if ((paramInt & 0xFFFFFF80) == 0) {
/*     */           
/*  59 */           out.writeByte(paramInt);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/*  64 */         out.writeByte(paramInt & 0x7F | 0x80);
/*  65 */         paramInt >>>= 7;
/*     */       } 
/*  67 */     } catch (IOException ex) {
/*     */       
/*  69 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeVarIntException(DataOutputStream out, int paramInt) throws IOException {
/*     */     while (true) {
/*  78 */       if ((paramInt & 0xFFFFFF80) == 0) {
/*     */         
/*  80 */         out.writeByte(paramInt);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  85 */       out.writeByte(paramInt & 0x7F | 0x80);
/*  86 */       paramInt >>>= 7;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeVarInt(ByteArrayOutputStream out, int paramInt) {
/*     */     while (true) {
/*  94 */       if ((paramInt & 0xFFFFFF80) == 0) {
/*     */         
/*  96 */         out.write(paramInt);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 101 */       out.write(paramInt & 0x7F | 0x80);
/* 102 */       paramInt >>>= 7;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeByteArray(DataOutputStream out, byte[] data) {
/*     */     try {
/* 110 */       writeVarInt(out, data.length);
/* 111 */       out.write(data, 0, data.length);
/* 112 */     } catch (IOException ex) {
/*     */       
/* 114 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writePacket(DataOutputStream out, byte[] packet) {
/*     */     try {
/* 122 */       writeVarInt(out, packet.length);
/* 123 */       out.write(packet);
/* 124 */     } catch (IOException ex) {
/*     */       
/* 126 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writePacketException(DataOutputStream out, byte[] packet) throws IOException {
/* 133 */     writeVarIntException(out, packet.length);
/* 134 */     out.write(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] serialize(Object obj) {
/*     */     try {
/* 141 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 142 */       ObjectOutputStream os = new ObjectOutputStream(out);
/* 143 */       os.writeObject(obj);
/*     */       
/* 145 */       return out.toByteArray();
/* 146 */     } catch (IOException ex) {
/*     */       
/* 148 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object deserialize(byte[] data) {
/*     */     try {
/* 156 */       ByteArrayInputStream in = new ByteArrayInputStream(data);
/* 157 */       ObjectInputStream is = new ObjectInputStream(in);
/*     */       
/* 159 */       return is.readObject();
/* 160 */     } catch (IOException|ClassNotFoundException ex) {
/*     */       
/* 162 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] createEncryptionResponsePacket(byte[] encryptedKey, byte[] encryptedVerifyToken) {
/* 168 */     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
/* 169 */     DataOutputStream out = new DataOutputStream(bytes);
/* 170 */     writeVarInt(out, 1);
/* 171 */     writeByteArray(out, encryptedKey);
/* 172 */     writeByteArray(out, encryptedVerifyToken);
/* 173 */     byte[] data = bytes.toByteArray();
/*     */ 
/*     */     
/*     */     try {
/* 177 */       bytes.close();
/* 178 */     } catch (IOException ex) {
/*     */       
/* 180 */       throw new RuntimeException(ex);
/*     */     } 
/*     */     
/* 183 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] createHandshakeMessage(String host, int port, int state) {
/*     */     try {
/* 190 */       ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */       
/* 192 */       DataOutputStream handshake = new DataOutputStream(buffer);
/* 193 */       handshake.writeByte(0);
/* 194 */       writeVarInt(handshake, 578);
/* 195 */       writeString(handshake, host, StandardCharsets.UTF_8);
/* 196 */       handshake.writeShort(port);
/* 197 */       writeVarInt(handshake, state);
/*     */       
/* 199 */       return buffer.toByteArray();
/* 200 */     } catch (IOException ex) {
/*     */       
/* 202 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] createLogin(String username) {
/*     */     try {
/* 210 */       ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 211 */       DataOutputStream login = new DataOutputStream(buffer);
/* 212 */       login.writeByte(0);
/* 213 */       writeString(login, username, StandardCharsets.UTF_8);
/*     */       
/* 215 */       return buffer.toByteArray();
/* 216 */     } catch (IOException ex) {
/*     */       
/* 218 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeString(DataOutputStream out, String string, Charset charset) {
/*     */     try {
/* 226 */       byte[] bytes = string.getBytes(charset);
/* 227 */       writeVarInt(out, bytes.length);
/* 228 */       out.write(bytes);
/* 229 */     } catch (IOException ex) {
/*     */       
/* 231 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeString(ByteArrayOutputStream out, String string, Charset charset) {
/*     */     try {
/* 239 */       byte[] bytes = string.getBytes(charset);
/* 240 */       writeVarInt(out, bytes.length);
/* 241 */       out.write(bytes);
/* 242 */     } catch (IOException ex) {
/*     */       
/* 244 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendPacket(byte[] packet, DataOutputStream out) {
/* 250 */     writePacket(out, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendPacketException(byte[] packet, DataOutputStream out) throws IOException {
/* 256 */     writePacketException(out, packet);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\botattack\PacketUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */