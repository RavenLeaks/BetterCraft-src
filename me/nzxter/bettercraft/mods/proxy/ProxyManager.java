/*    */ package me.nzxter.bettercraft.mods.proxy;
/*    */ 
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Proxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProxyManager
/*    */ {
/*    */   public static volatile Proxy proxy;
/*    */   
/*    */   public static Proxy getProxyFromString(String proxy) {
/* 13 */     return new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxy.split(":")[0], Integer.valueOf(proxy.split(":")[1]).intValue()));
/*    */   }
/*    */   
/*    */   public static void setProxy(Proxy proxy) {
/* 17 */     ProxyManager.proxy = proxy;
/*    */   }
/*    */   
/*    */   public static Proxy getProxy() {
/* 21 */     return proxy;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\proxy\ProxyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */