/*    */ package me.nzxter.bettercraft.mods.fritzbox;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.net.InetAddress;
/*    */ import java.net.Socket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FritzboxReconnector
/*    */ {
/*    */   public static void reconnectFritzBox() {
/*    */     try {
/* 19 */       String xmldata = "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>";
/* 20 */       String hostname = "fritz.box";
/* 21 */       int port = 49000;
/* 22 */       InetAddress addr = InetAddress.getByName("fritz.box");
/* 23 */       Socket sock = new Socket(addr, 49000);
/* 24 */       BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
/* 25 */       wr.write("POST /igdupnp/control/WANIPConn1 HTTP/1.1");
/* 26 */       wr.write("Host: fritz.box:49000\r\n");
/* 27 */       wr.write("SOAPACTION: \"urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination\"\r\n");
/* 28 */       wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
/* 29 */       wr.write("Content-Length: " + "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>".length() + "\r\n");
/* 30 */       wr.write("\r\n");
/* 31 */       wr.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>");
/* 32 */       wr.flush();
/* 33 */       sock.close();
/*    */     }
/* 35 */     catch (Exception e) {
/* 36 */       e.printStackTrace();
/*    */     } 
/*    */     try {
/* 39 */       String xmldata = "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>";
/* 40 */       String hostname = "192.168.178.1";
/* 41 */       int port = 49000;
/* 42 */       InetAddress addr = InetAddress.getByName("192.168.178.1");
/* 43 */       Socket sock = new Socket(addr, 49000);
/* 44 */       BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
/* 45 */       wr.write("POST /igdupnp/control/WANIPConn1 HTTP/1.1");
/* 46 */       wr.write("Host: 192.168.178.1:49000\r\n");
/* 47 */       wr.write("SOAPACTION: \"urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination\"\r\n");
/* 48 */       wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
/* 49 */       wr.write("Content-Length: " + "<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>".length() + "\r\n");
/* 50 */       wr.write("\r\n");
/* 51 */       wr.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"><s:Body><u:ForceTermination xmlns:u=\"urn:schemas-upnp-org:service:WANIPConnection:1\" /></s:Body></s:Envelope>");
/* 52 */       wr.flush();
/* 53 */       sock.close();
/*    */     }
/* 55 */     catch (Exception e) {
/* 56 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\fritzbox\FritzboxReconnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */