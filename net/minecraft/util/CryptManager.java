/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CryptManager
/*     */ {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey createNewSharedKey() {
/*     */     try {
/*  39 */       KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
/*  40 */       keygenerator.init(128);
/*  41 */       return keygenerator.generateKey();
/*     */     }
/*  43 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  45 */       throw new Error(nosuchalgorithmexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair() {
/*     */     try {
/*  56 */       KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
/*  57 */       keypairgenerator.initialize(1024);
/*  58 */       return keypairgenerator.generateKeyPair();
/*     */     }
/*  60 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  62 */       nosuchalgorithmexception.printStackTrace();
/*  63 */       LOGGER.error("Key pair generation failed!");
/*  64 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) {
/*     */     try {
/*  75 */       return digestOperation("SHA-1", new byte[][] { serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
/*     */     }
/*  77 */     catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */       
/*  79 */       unsupportedencodingexception.printStackTrace();
/*  80 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] digestOperation(String algorithm, byte[]... data) {
/*     */     try {
/*  91 */       MessageDigest messagedigest = MessageDigest.getInstance(algorithm); byte b; int i;
/*     */       byte[][] arrayOfByte;
/*  93 */       for (i = (arrayOfByte = data).length, b = 0; b < i; ) { byte[] abyte = arrayOfByte[b];
/*     */         
/*  95 */         messagedigest.update(abyte);
/*     */         b++; }
/*     */       
/*  98 */       return messagedigest.digest();
/*     */     }
/* 100 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 102 */       nosuchalgorithmexception.printStackTrace();
/* 103 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKey decodePublicKey(byte[] encodedKey) {
/*     */     try {
/* 114 */       EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
/* 115 */       KeyFactory keyfactory = KeyFactory.getInstance("RSA");
/* 116 */       return keyfactory.generatePublic(encodedkeyspec);
/*     */     }
/* 118 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */ 
/*     */     
/*     */     }
/* 122 */     catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     LOGGER.error("Public key reconstitute failed!");
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted) {
/* 136 */     return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encryptData(Key key, byte[] data) {
/* 144 */     return cipherOperation(1, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptData(Key key, byte[] data) {
/* 152 */     return cipherOperation(2, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] cipherOperation(int opMode, Key key, byte[] data) {
/*     */     try {
/* 162 */       return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
/*     */     }
/* 164 */     catch (IllegalBlockSizeException illegalblocksizeexception) {
/*     */       
/* 166 */       illegalblocksizeexception.printStackTrace();
/*     */     }
/* 168 */     catch (BadPaddingException badpaddingexception) {
/*     */       
/* 170 */       badpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 173 */     LOGGER.error("Cipher data failed!");
/* 174 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Cipher createTheCipherInstance(int opMode, String transformation, Key key) {
/*     */     try {
/* 184 */       Cipher cipher = Cipher.getInstance(transformation);
/* 185 */       cipher.init(opMode, key);
/* 186 */       return cipher;
/*     */     }
/* 188 */     catch (InvalidKeyException invalidkeyexception) {
/*     */       
/* 190 */       invalidkeyexception.printStackTrace();
/*     */     }
/* 192 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 194 */       nosuchalgorithmexception.printStackTrace();
/*     */     }
/* 196 */     catch (NoSuchPaddingException nosuchpaddingexception) {
/*     */       
/* 198 */       nosuchpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 201 */     LOGGER.error("Cipher creation failed!");
/* 202 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cipher createNetCipherInstance(int opMode, Key key) {
/*     */     try {
/* 212 */       Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 213 */       cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
/* 214 */       return cipher;
/*     */     }
/* 216 */     catch (GeneralSecurityException generalsecurityexception) {
/*     */       
/* 218 */       throw new RuntimeException(generalsecurityexception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\CryptManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */