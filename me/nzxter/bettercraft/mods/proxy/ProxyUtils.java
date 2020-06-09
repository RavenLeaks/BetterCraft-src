/*    */ package me.nzxter.bettercraft.mods.proxy;
/*    */ 
/*    */ import io.netty.bootstrap.ChannelFactory;
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.socket.oio.OioSocketChannel;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.Proxy;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class ProxyUtils
/*    */   implements ChannelFactory<OioSocketChannel> {
/*    */   private Proxy proxy;
/*    */   
/*    */   public ProxyUtils(Proxy proxy) {
/* 15 */     this.proxy = proxy;
/*    */   }
/*    */   
/*    */   public Proxy getProxy() {
/* 19 */     return this.proxy;
/*    */   }
/*    */   
/*    */   public void setProxy(Proxy proxy) {
/* 23 */     this.proxy = proxy;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public OioSocketChannel newChannel() {
/* 29 */     if (this.proxy == null || this.proxy == Proxy.NO_PROXY) {
/* 30 */       return new OioSocketChannel(new Socket(Proxy.NO_PROXY));
/*    */     }
/* 32 */     Socket sock = new Socket(this.proxy);
/*    */     try {
/* 34 */       Method m = sock.getClass().getDeclaredMethod("getImpl", new Class[0]);
/* 35 */       m.setAccessible(true);
/* 36 */       Object sd = m.invoke(sock, new Object[0]);
/* 37 */       m = sd.getClass().getDeclaredMethod("setV4", new Class[0]);
/* 38 */       m.setAccessible(true);
/* 39 */       m.invoke(sd, new Object[0]);
/* 40 */       return new OioSocketChannel(sock);
/*    */     }
/* 42 */     catch (Exception ignored) {
/* 43 */       ignored.printStackTrace();
/* 44 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\proxy\ProxyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */