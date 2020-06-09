/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import java.net.IDN;
/*     */ import java.util.Hashtable;
/*     */ import javax.naming.directory.Attributes;
/*     */ import javax.naming.directory.DirContext;
/*     */ import javax.naming.directory.InitialDirContext;
/*     */ 
/*     */ 
/*     */ public class ServerAddress
/*     */ {
/*     */   private final String ipAddress;
/*     */   private final int serverPort;
/*     */   
/*     */   private ServerAddress(String address, int port) {
/*  16 */     this.ipAddress = address;
/*  17 */     this.serverPort = port;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIP() {
/*     */     try {
/*  24 */       return IDN.toASCII(this.ipAddress);
/*     */     }
/*  26 */     catch (IllegalArgumentException var2) {
/*     */       
/*  28 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/*  34 */     return this.serverPort;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ServerAddress fromString(String addrString) {
/*  39 */     if (addrString == null)
/*     */     {
/*  41 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  45 */     String[] astring = addrString.split(":");
/*     */     
/*  47 */     if (addrString.startsWith("[")) {
/*     */       
/*  49 */       int i = addrString.indexOf("]");
/*     */       
/*  51 */       if (i > 0) {
/*     */         
/*  53 */         String s = addrString.substring(1, i);
/*  54 */         String s1 = addrString.substring(i + 1).trim();
/*     */         
/*  56 */         if (s1.startsWith(":") && !s1.isEmpty()) {
/*     */           
/*  58 */           s1 = s1.substring(1);
/*  59 */           astring = new String[] { s, s1 };
/*     */         }
/*     */         else {
/*     */           
/*  63 */           astring = new String[] { s };
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     if (astring.length > 2)
/*     */     {
/*  70 */       astring = new String[] { addrString };
/*     */     }
/*     */     
/*  73 */     String s2 = astring[0];
/*  74 */     int j = (astring.length > 1) ? getInt(astring[1], 25565) : 25565;
/*     */     
/*  76 */     if (j == 25565) {
/*     */       
/*  78 */       String[] astring1 = getServerAddress(s2);
/*  79 */       s2 = astring1[0];
/*  80 */       j = getInt(astring1[1], 25565);
/*     */     } 
/*     */     
/*  83 */     return new ServerAddress(s2, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getServerAddress(String p_78863_0_) {
/*     */     try {
/*  94 */       String s = "com.sun.jndi.dns.DnsContextFactory";
/*  95 */       Class.forName("com.sun.jndi.dns.DnsContextFactory");
/*  96 */       Hashtable<String, String> hashtable = new Hashtable<>();
/*  97 */       hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
/*  98 */       hashtable.put("java.naming.provider.url", "dns:");
/*  99 */       hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
/* 100 */       DirContext dircontext = new InitialDirContext(hashtable);
/* 101 */       Attributes attributes = dircontext.getAttributes("_minecraft._tcp." + p_78863_0_, new String[] { "SRV" });
/* 102 */       String[] astring = attributes.get("srv").get().toString().split(" ", 4);
/* 103 */       return new String[] { astring[3], astring[2] };
/*     */     }
/* 105 */     catch (Throwable var6) {
/*     */       
/* 107 */       return new String[] { p_78863_0_, Integer.toString(25565) };
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getInt(String value, int defaultValue) {
/*     */     try {
/* 115 */       return Integer.parseInt(value.trim());
/*     */     }
/* 117 */     catch (Exception var3) {
/*     */       
/* 119 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerAddress resolveAddress(String serverIP) {
/* 127 */     if (serverIP == null) {
/* 128 */       return null;
/*     */     }
/* 130 */     String[] astring = serverIP.split(":"); int i;
/* 131 */     if (serverIP.startsWith("[") && (i = serverIP.indexOf("]")) > 0) {
/* 132 */       String s = serverIP.substring(1, i);
/* 133 */       String s1 = serverIP.substring(i + 1).trim();
/* 134 */       if (s1.startsWith(":") && s1.length() > 0) {
/* 135 */         s1 = s1.substring(1);
/* 136 */         astring = new String[] { s, s1 };
/*     */       } else {
/* 138 */         astring = new String[] { s };
/*     */       } 
/*     */     } 
/* 141 */     if (astring.length > 2) {
/* 142 */       astring = new String[] { serverIP };
/*     */     }
/* 144 */     String s2 = astring[0];
/* 145 */     int j = (astring.length > 1) ? getInt(astring[1], 25565) : 25565, n = j;
/* 146 */     if (j == 25565) {
/* 147 */       String[] astring1 = getServerAddress(s2);
/* 148 */       s2 = astring1[0];
/* 149 */       j = getInt(astring1[1], 25565);
/*     */     } 
/* 151 */     return new ServerAddress(s2, j);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\ServerAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */