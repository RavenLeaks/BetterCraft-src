/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.local.LocalAddress;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.local.LocalServerChannel;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.network.NetHandlerHandshakeMemory;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.network.play.server.SPacketDisconnect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.network.NetHandlerHandshakeTCP;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetworkSystem {
/*  45 */   private static final Logger LOGGER = LogManager.getLogger();
/*  46 */   public static final LazyLoadBase<NioEventLoopGroup> SERVER_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>()
/*     */     {
/*     */       protected NioEventLoopGroup load()
/*     */       {
/*  50 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  53 */   public static final LazyLoadBase<EpollEventLoopGroup> SERVER_EPOLL_EVENTLOOP = new LazyLoadBase<EpollEventLoopGroup>()
/*     */     {
/*     */       protected EpollEventLoopGroup load()
/*     */       {
/*  57 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  60 */   public static final LazyLoadBase<LocalEventLoopGroup> SERVER_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>()
/*     */     {
/*     */       protected LocalEventLoopGroup load()
/*     */       {
/*  64 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */   
/*     */   public volatile boolean isAlive;
/*     */   
/*  73 */   private final List<ChannelFuture> endpoints = Collections.synchronizedList(Lists.newArrayList());
/*  74 */   private final List<NetworkManager> networkManagers = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public NetworkSystem(MinecraftServer server) {
/*  78 */     this.mcServer = server;
/*  79 */     this.isAlive = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLanEndpoint(InetAddress address, int port) throws IOException {
/*  87 */     synchronized (this.endpoints) {
/*     */       Class<NioServerSocketChannel> clazz;
/*     */       
/*     */       LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/*     */       
/*  92 */       if (Epoll.isAvailable() && this.mcServer.shouldUseNativeTransport()) {
/*     */         
/*  94 */         Class<EpollServerSocketChannel> clazz1 = EpollServerSocketChannel.class;
/*  95 */         LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = SERVER_EPOLL_EVENTLOOP;
/*  96 */         LOGGER.info("Using epoll channel type");
/*     */       }
/*     */       else {
/*     */         
/* 100 */         clazz = NioServerSocketChannel.class;
/* 101 */         lazyLoadBase = SERVER_NIO_EVENTLOOP;
/* 102 */         LOGGER.info("Using default channel type");
/*     */       } 
/*     */       
/* 105 */       this.endpoints.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(clazz)).childHandler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */             {
/*     */               
/*     */               protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */               {
/*     */                 try {
/* 111 */                   p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */                 }
/* 113 */                 catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 118 */                 p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new LegacyPingHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new NettyVarint21FrameDecoder()).addLast("decoder", (ChannelHandler)new NettyPacketDecoder(EnumPacketDirection.SERVERBOUND)).addLast("prepender", (ChannelHandler)new NettyVarint21FrameEncoder()).addLast("encoder", (ChannelHandler)new NettyPacketEncoder(EnumPacketDirection.CLIENTBOUND));
/* 119 */                 NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
/* 120 */                 NetworkSystem.this.networkManagers.add(networkmanager);
/* 121 */                 p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/* 122 */                 networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, networkmanager));
/*     */               }
/* 124 */             }).group((EventLoopGroup)lazyLoadBase.getValue()).localAddress(address, port)).bind().syncUninterruptibly());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress addLocalEndpoint() {
/*     */     ChannelFuture channelfuture;
/* 135 */     synchronized (this.endpoints) {
/*     */       
/* 137 */       channelfuture = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */           {
/*     */             protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */             {
/* 141 */               NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
/* 142 */               networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, networkmanager));
/* 143 */               NetworkSystem.this.networkManagers.add(networkmanager);
/* 144 */               p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */             }
/* 146 */           }).group((EventLoopGroup)SERVER_NIO_EVENTLOOP.getValue()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
/* 147 */       this.endpoints.add(channelfuture);
/*     */     } 
/*     */     
/* 150 */     return channelfuture.channel().localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminateEndpoints() {
/* 158 */     this.isAlive = false;
/*     */     
/* 160 */     for (ChannelFuture channelfuture : this.endpoints) {
/*     */ 
/*     */       
/*     */       try {
/* 164 */         channelfuture.channel().close().sync();
/*     */       }
/* 166 */       catch (InterruptedException var4) {
/*     */         
/* 168 */         LOGGER.error("Interrupted whilst closing channel");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void networkTick() {
/* 179 */     synchronized (this.networkManagers) {
/*     */       
/* 181 */       Iterator<NetworkManager> iterator = this.networkManagers.iterator();
/*     */       
/* 183 */       while (iterator.hasNext()) {
/*     */         
/* 185 */         final NetworkManager networkmanager = iterator.next();
/*     */         
/* 187 */         if (!networkmanager.hasNoChannel()) {
/*     */           
/* 189 */           if (networkmanager.isChannelOpen()) {
/*     */ 
/*     */             
/*     */             try {
/* 193 */               networkmanager.processReceivedPackets();
/*     */             }
/* 195 */             catch (Exception exception) {
/*     */               
/* 197 */               if (networkmanager.isLocalChannel()) {
/*     */                 
/* 199 */                 CrashReport crashreport = CrashReport.makeCrashReport(exception, "Ticking memory connection");
/* 200 */                 CrashReportCategory crashreportcategory = crashreport.makeCategory("Ticking connection");
/* 201 */                 crashreportcategory.setDetail("Connection", new ICrashReportDetail<String>()
/*     */                     {
/*     */                       public String call() throws Exception
/*     */                       {
/* 205 */                         return networkmanager.toString();
/*     */                       }
/*     */                     });
/* 208 */                 throw new ReportedException(crashreport);
/*     */               } 
/*     */               
/* 211 */               LOGGER.warn("Failed to handle packet for {}", networkmanager.getRemoteAddress(), exception);
/* 212 */               final TextComponentString textcomponentstring = new TextComponentString("Internal server error");
/* 213 */               networkmanager.sendPacket((Packet<?>)new SPacketDisconnect((ITextComponent)textcomponentstring), new GenericFutureListener<Future<? super Void>>()
/*     */                   {
/*     */                     public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception
/*     */                     {
/* 217 */                       networkmanager.closeChannel((ITextComponent)textcomponentstring);
/*     */                     }
/*     */                   },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/* 220 */               networkmanager.disableAutoRead();
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/* 225 */           iterator.remove();
/* 226 */           networkmanager.checkDisconnected();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MinecraftServer getServer() {
/* 235 */     return this.mcServer;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NetworkSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */