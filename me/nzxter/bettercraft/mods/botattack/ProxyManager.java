/*    */ package me.nzxter.bettercraft.mods.botattack;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Proxy;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ public class ProxyManager {
/* 12 */   private final List<Proxy> proxys = new ArrayList<>();
/* 13 */   private final Map<Proxy, SocksType> sockTypes = new HashMap<>();
/* 14 */   private volatile AtomicInteger currentPosition = new AtomicInteger(0);
/*    */ 
/*    */   
/*    */   public ProxyManager() {
/* 18 */     this.proxys.add(Proxy.NO_PROXY);
/*    */     
/* 20 */     InputStream in = getClass().getResourceAsStream("/me/nzxter/bettercraft/mods/botattack/socks4.txt");
/* 21 */     BufferedReader reader = new BufferedReader(new InputStreamReader(in));
/*    */     
/* 23 */     reader.lines().forEach(str -> {
/*    */           String[] arr = str.split(":");
/*    */           String host = arr[0];
/*    */           int port = Integer.parseInt(arr[1]);
/*    */           Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port));
/*    */           this.proxys.add(proxy);
/*    */           this.sockTypes.put(proxy, SocksType.SOCKS4);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Proxy> getProxys() {
/* 39 */     return this.proxys;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized int getCurrentPosition() {
/* 44 */     return this.currentPosition.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Proxy nextProxy() {
/* 49 */     Proxy proxy = this.proxys.get(this.currentPosition.getAndIncrement());
/*    */     
/* 51 */     if (this.currentPosition.get() >= this.proxys.size()) this.currentPosition.set(0);
/*    */     
/* 53 */     return proxy;
/*    */   }
/*    */ 
/*    */   
/*    */   public SocksType getSocksType(Proxy proxy) {
/* 58 */     return this.sockTypes.get(proxy);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String proxyToString(Proxy proxy) {
/* 63 */     return "Proxy[host=" + ((InetSocketAddress)proxy.address()).getAddress().getHostAddress() + ";port=" + ((InetSocketAddress)proxy.address()).getPort() + "]";
/*    */   }
/*    */   
/*    */   public enum SocksType
/*    */   {
/* 68 */     SOCKS4,
/* 69 */     SOCKS5;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\botattack\ProxyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */