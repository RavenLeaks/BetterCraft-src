/*     */ package me.nzxter.bettercraft.mods.botattack;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketImpl;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.ServerData;
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
/*     */ public class BotConnector
/*     */ {
/*     */   private static final int threadAmount = 30;
/*     */   public static ExecutorService executor;
/*  37 */   private static final ProxyManager proxyManager = new ProxyManager();
/*  38 */   private Random random = new Random();
/*     */   
/*     */   public static void start() {
/*  41 */     executor = Executors.newFixedThreadPool(30);
/*     */ 
/*     */     
/*     */     try {
/*  45 */       ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
/*     */       
/*  47 */       if (serverData == null) {
/*     */         return;
/*     */       }
/*  50 */       ServerAddress serveradress = ServerAddress.resolveAddress(serverData.serverIP);
/*  51 */       String[] addressPort = (String.valueOf(InetAddress.getByName(serveradress.getIP()).getHostAddress()) + " " + serveradress.getPort()).split(" ");
/*     */       
/*  53 */       Random random = new Random();
/*     */       
/*  55 */       (new Thread(() -> {
/*     */             while (!executor.isShutdown()) {
/*     */               Proxy proxy = proxyManager.nextProxy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/*     */                 Socket socket = new Socket(proxy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 if (proxyManager.getSocksType(proxy) == ProxyManager.SocksType.SOCKS4) {
/*     */                   Class<? extends Socket> clazzSocks = (Class)socket.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   Field sockImplField = clazzSocks.getDeclaredField("impl");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   sockImplField.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   SocketImpl socksimpl = (SocketImpl)sockImplField.get(socket);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   Class<? extends SocketImpl> clazzSocksImpl = (Class)socksimpl.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   Method setSockVersion = clazzSocksImpl.getDeclaredMethod("setV4", new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   setSockVersion.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   setSockVersion.invoke(socksimpl, new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   sockImplField.set(socket, socksimpl);
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 executor.execute(());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 Thread.sleep(100L);
/* 161 */               } catch (Exception exception) {}
/*     */             } 
/* 163 */           })).start();
/* 164 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\botattack\BotConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */