/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.bootstrap.ChannelFactory;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollSocketChannel;
/*     */ import io.netty.channel.local.LocalChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.oio.OioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.SecretKey;
/*     */ import me.nzxter.bettercraft.mods.proxy.ProxyManager;
/*     */ import me.nzxter.bettercraft.mods.proxy.ProxyUtils;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ 
/*     */ 
/*     */ public class NetworkManager
/*     */   extends SimpleChannelInboundHandler<Packet<?>>
/*     */ {
/*  53 */   private static final Logger LOGGER = LogManager.getLogger();
/*  54 */   public static final Marker NETWORK_MARKER = MarkerManager.getMarker("NETWORK");
/*  55 */   public static final Marker NETWORK_PACKETS_MARKER = MarkerManager.getMarker("NETWORK_PACKETS", NETWORK_MARKER);
/*  56 */   public static final AttributeKey<EnumConnectionState> PROTOCOL_ATTRIBUTE_KEY = AttributeKey.valueOf("protocol");
/*  57 */   public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>()
/*     */     {
/*     */       protected NioEventLoopGroup load()
/*     */       {
/*  61 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  64 */   public static final LazyLoadBase<EpollEventLoopGroup> CLIENT_EPOLL_EVENTLOOP = new LazyLoadBase<EpollEventLoopGroup>()
/*     */     {
/*     */       protected EpollEventLoopGroup load()
/*     */       {
/*  68 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  71 */   public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>()
/*     */     {
/*     */       protected LocalEventLoopGroup load()
/*     */       {
/*  75 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */   private final EnumPacketDirection direction;
/*  79 */   private final Queue<InboundHandlerTuplePacketListener> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  80 */   private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
/*     */ 
/*     */   
/*     */   public Channel channel;
/*     */ 
/*     */   
/*     */   private SocketAddress socketAddress;
/*     */   
/*     */   private INetHandler packetListener;
/*     */   
/*     */   private ITextComponent terminationReason;
/*     */   
/*     */   private boolean isEncrypted;
/*     */   
/*     */   private boolean disconnected;
/*     */ 
/*     */   
/*     */   public NetworkManager(EnumPacketDirection packetDirection) {
/*  98 */     this.direction = packetDirection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
/* 103 */     super.channelActive(p_channelActive_1_);
/* 104 */     this.channel = p_channelActive_1_.channel();
/* 105 */     this.socketAddress = this.channel.remoteAddress();
/*     */ 
/*     */     
/*     */     try {
/* 109 */       setConnectionState(EnumConnectionState.HANDSHAKING);
/*     */     }
/* 111 */     catch (Throwable throwable) {
/*     */       
/* 113 */       LOGGER.fatal(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionState(EnumConnectionState newState) {
/* 122 */     this.channel.attr(PROTOCOL_ATTRIBUTE_KEY).set(newState);
/* 123 */     this.channel.config().setAutoRead(true);
/* 124 */     LOGGER.debug("Enabled auto read");
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) throws Exception {
/* 129 */     closeChannel((ITextComponent)new TextComponentTranslation("disconnect.endOfStream", new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/*     */     TextComponentTranslation textcomponenttranslation;
/* 136 */     if (p_exceptionCaught_2_ instanceof io.netty.handler.timeout.TimeoutException) {
/*     */       
/* 138 */       textcomponenttranslation = new TextComponentTranslation("disconnect.timeout", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 142 */       textcomponenttranslation = new TextComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ });
/*     */     } 
/*     */     
/* 145 */     LOGGER.debug(textcomponenttranslation.getUnformattedText(), p_exceptionCaught_2_);
/* 146 */     closeChannel((ITextComponent)textcomponenttranslation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_) throws Exception {
/* 151 */     if (this.channel.isOpen()) {
/*     */       
/*     */       try {
/*     */         
/* 155 */         p_channelRead0_2_.processPacket(this.packetListener);
/*     */       }
/* 157 */       catch (ThreadQuickExitException threadQuickExitException) {}
/*     */     }
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
/*     */   public void setNetHandler(INetHandler handler) {
/* 170 */     Validate.notNull(handler, "packetListener", new Object[0]);
/* 171 */     LOGGER.debug("Set listener of {} to {}", this, handler);
/* 172 */     this.packetListener = handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet<?> packetIn) {
/* 177 */     if (isChannelOpen()) {
/*     */       
/* 179 */       flushOutboundQueue();
/* 180 */       dispatchPacket(packetIn, null);
/*     */     }
/*     */     else {
/*     */       
/* 184 */       this.readWriteLock.writeLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 188 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]));
/*     */       }
/*     */       finally {
/*     */         
/* 192 */         this.readWriteLock.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet<?> packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener... listeners) {
/* 199 */     if (isChannelOpen()) {
/*     */       
/* 201 */       flushOutboundQueue();
/* 202 */       dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener));
/*     */     }
/*     */     else {
/*     */       
/* 206 */       this.readWriteLock.writeLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 210 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener)));
/*     */       }
/*     */       finally {
/*     */         
/* 214 */         this.readWriteLock.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dispatchPacket(final Packet<?> inPacket, @Nullable final GenericFutureListener[] futureListeners) {
/* 225 */     final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
/* 226 */     final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(PROTOCOL_ATTRIBUTE_KEY).get();
/*     */     
/* 228 */     if (enumconnectionstate1 != enumconnectionstate) {
/*     */       
/* 230 */       LOGGER.debug("Disabled auto read");
/* 231 */       this.channel.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 234 */     if (this.channel.eventLoop().inEventLoop()) {
/*     */       
/* 236 */       if (enumconnectionstate != enumconnectionstate1)
/*     */       {
/* 238 */         setConnectionState(enumconnectionstate);
/*     */       }
/*     */       
/* 241 */       ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
/*     */       
/* 243 */       if (futureListeners != null)
/*     */       {
/* 245 */         channelfuture.addListeners(futureListeners);
/*     */       }
/*     */       
/* 248 */       channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     }
/*     */     else {
/*     */       
/* 252 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 256 */               if (enumconnectionstate != enumconnectionstate1)
/*     */               {
/* 258 */                 NetworkManager.this.setConnectionState(enumconnectionstate);
/*     */               }
/*     */               
/* 261 */               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(inPacket);
/*     */               
/* 263 */               if (futureListeners != null)
/*     */               {
/* 265 */                 channelfuture1.addListeners(futureListeners);
/*     */               }
/*     */               
/* 268 */               channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushOutboundQueue() {
/* 279 */     if (this.channel != null && this.channel.isOpen()) {
/*     */       
/* 281 */       this.readWriteLock.readLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 285 */         while (!this.outboundPacketsQueue.isEmpty())
/*     */         {
/* 287 */           InboundHandlerTuplePacketListener networkmanager$inboundhandlertuplepacketlistener = this.outboundPacketsQueue.poll();
/* 288 */           dispatchPacket(networkmanager$inboundhandlertuplepacketlistener.packet, networkmanager$inboundhandlertuplepacketlistener.futureListeners);
/*     */         }
/*     */       
/*     */       } finally {
/*     */         
/* 293 */         this.readWriteLock.readLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processReceivedPackets() {
/* 303 */     flushOutboundQueue();
/*     */     
/* 305 */     if (this.packetListener instanceof ITickable)
/*     */     {
/* 307 */       ((ITickable)this.packetListener).update();
/*     */     }
/*     */     
/* 310 */     if (this.channel != null)
/*     */     {
/* 312 */       this.channel.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 321 */     return this.socketAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeChannel(ITextComponent message) {
/* 329 */     if (this.channel.isOpen()) {
/*     */       
/* 331 */       this.channel.close().awaitUninterruptibly();
/* 332 */       this.terminationReason = message;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalChannel() {
/* 342 */     return !(!(this.channel instanceof LocalChannel) && !(this.channel instanceof io.netty.channel.local.LocalServerChannel));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager createNetworkManagerAndConnect(InetAddress address, int serverPort, boolean useNativeTransport) {
/*     */     Class<NioSocketChannel> clazz;
/*     */     LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/* 350 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/*     */ 
/*     */ 
/*     */     
/* 354 */     if (Epoll.isAvailable() && useNativeTransport) {
/*     */       
/* 356 */       Class<EpollSocketChannel> clazz1 = EpollSocketChannel.class;
/* 357 */       LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = CLIENT_EPOLL_EVENTLOOP;
/*     */     }
/*     */     else {
/*     */       
/* 361 */       clazz = NioSocketChannel.class;
/* 362 */       lazyLoadBase = CLIENT_NIO_EVENTLOOP;
/*     */     } 
/*     */ 
/*     */     
/* 366 */     Bootstrap bootstrap = new Bootstrap();
/*     */     
/* 368 */     if (ProxyManager.getProxy() != null) {
/* 369 */       bootstrap.group((EventLoopGroup)new OioEventLoopGroup());
/* 370 */       bootstrap.channelFactory((ChannelFactory)new ProxyUtils(ProxyManager.getProxy()));
/*     */     } else {
/* 372 */       bootstrap.group((EventLoopGroup)lazyLoadBase.getValue());
/* 373 */       bootstrap.channel(clazz);
/*     */     } 
/*     */ 
/*     */     
/* 377 */     ((Bootstrap)bootstrap.handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */ 
/*     */           
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/*     */             try {
/* 384 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */             }
/* 386 */             catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 392 */             p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new NettyVarint21FrameDecoder()).addLast("decoder", (ChannelHandler)new NettyPacketDecoder(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new NettyVarint21FrameEncoder()).addLast("encoder", (ChannelHandler)new NettyPacketEncoder(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 395 */         })).connect(address, serverPort).syncUninterruptibly();
/* 396 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager provideLocalClient(SocketAddress address) {
/* 405 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/* 406 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/* 410 */             p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 412 */         })).channel(LocalChannel.class)).connect(address).syncUninterruptibly();
/* 413 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEncryption(SecretKey key) {
/* 421 */     this.isEncrypted = true;
/* 422 */     this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
/* 423 */     this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEncrypted() {
/* 428 */     return this.isEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChannelOpen() {
/* 436 */     return (this.channel != null && this.channel.isOpen());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoChannel() {
/* 441 */     return (this.channel == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INetHandler getNetHandler() {
/* 449 */     return this.packetListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getExitMessage() {
/* 457 */     return this.terminationReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAutoRead() {
/* 465 */     this.channel.config().setAutoRead(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompressionThreshold(int threshold) {
/* 470 */     if (threshold >= 0) {
/*     */       
/* 472 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/*     */         
/* 474 */         ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionThreshold(threshold);
/*     */       }
/*     */       else {
/*     */         
/* 478 */         this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(threshold));
/*     */       } 
/*     */       
/* 481 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
/*     */       {
/* 483 */         ((NettyCompressionEncoder)this.channel.pipeline().get("compress")).setCompressionThreshold(threshold);
/*     */       }
/*     */       else
/*     */       {
/* 487 */         this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(threshold));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 492 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder)
/*     */       {
/* 494 */         this.channel.pipeline().remove("decompress");
/*     */       }
/*     */       
/* 497 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
/*     */       {
/* 499 */         this.channel.pipeline().remove("compress");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkDisconnected() {
/* 506 */     if (this.channel != null && !this.channel.isOpen())
/*     */     {
/* 508 */       if (this.disconnected) {
/*     */         
/* 510 */         LOGGER.warn("handleDisconnection() called twice");
/*     */       }
/*     */       else {
/*     */         
/* 514 */         this.disconnected = true;
/*     */         
/* 516 */         if (getExitMessage() != null) {
/*     */           
/* 518 */           getNetHandler().onDisconnect(getExitMessage());
/*     */         }
/* 520 */         else if (getNetHandler() != null) {
/*     */           
/* 522 */           getNetHandler().onDisconnect((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.generic", new Object[0]));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class InboundHandlerTuplePacketListener
/*     */   {
/*     */     private final Packet<?> packet;
/*     */     private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
/*     */     
/*     */     public InboundHandlerTuplePacketListener(Packet<?> inPacket, GenericFutureListener... inFutureListeners) {
/* 535 */       this.packet = inPacket;
/* 536 */       this.futureListeners = (GenericFutureListener<? extends Future<? super Void>>[])inFutureListeners;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */