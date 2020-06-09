/*     */ package net.minecraft.client.network;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.ServerStatusResponse;
/*     */ import net.minecraft.network.status.INetHandlerStatusClient;
/*     */ import net.minecraft.network.status.client.CPacketPing;
/*     */ import net.minecraft.network.status.client.CPacketServerQuery;
/*     */ import net.minecraft.network.status.server.SPacketPong;
/*     */ import net.minecraft.network.status.server.SPacketServerInfo;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerPinger {
/*  48 */   private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on(false).limit(6);
/*  49 */   private static final Logger LOGGER = LogManager.getLogger();
/*  50 */   private final List<NetworkManager> pingDestinations = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public void ping(final ServerData server) throws UnknownHostException {
/*  54 */     ServerAddress serveraddress = ServerAddress.fromString(server.serverIP);
/*  55 */     final NetworkManager networkmanager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP()), serveraddress.getPort(), false);
/*  56 */     this.pingDestinations.add(networkmanager);
/*  57 */     server.serverMOTD = I18n.format("multiplayer.status.pinging", new Object[0]);
/*  58 */     server.pingToServer = -1L;
/*  59 */     server.playerList = null;
/*  60 */     networkmanager.setNetHandler((INetHandler)new INetHandlerStatusClient()
/*     */         {
/*     */           private boolean successful;
/*     */           private boolean receivedStatus;
/*     */           private long pingSentAt;
/*     */           
/*     */           public void handleServerInfo(SPacketServerInfo packetIn) {
/*  67 */             if (this.receivedStatus) {
/*     */               
/*  69 */               networkmanager.closeChannel((ITextComponent)new TextComponentTranslation("multiplayer.status.unrequested", new Object[0]));
/*     */             }
/*     */             else {
/*     */               
/*  73 */               this.receivedStatus = true;
/*  74 */               ServerStatusResponse serverstatusresponse = packetIn.getResponse();
/*     */               
/*  76 */               if (serverstatusresponse.getServerDescription() != null) {
/*     */                 
/*  78 */                 server.serverMOTD = serverstatusresponse.getServerDescription().getFormattedText();
/*     */               }
/*     */               else {
/*     */                 
/*  82 */                 server.serverMOTD = "";
/*     */               } 
/*     */               
/*  85 */               if (serverstatusresponse.getVersion() != null) {
/*     */                 
/*  87 */                 server.gameVersion = serverstatusresponse.getVersion().getName();
/*  88 */                 server.version = serverstatusresponse.getVersion().getProtocol();
/*     */               }
/*     */               else {
/*     */                 
/*  92 */                 server.gameVersion = I18n.format("multiplayer.status.old", new Object[0]);
/*  93 */                 server.version = 0;
/*     */               } 
/*     */               
/*  96 */               if (serverstatusresponse.getPlayers() != null) {
/*     */                 
/*  98 */                 server.populationInfo = TextFormatting.GRAY + serverstatusresponse.getPlayers().getOnlinePlayerCount() + TextFormatting.DARK_GRAY + "/" + TextFormatting.GRAY + serverstatusresponse.getPlayers().getMaxPlayers();
/*     */                 
/* 100 */                 if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.getPlayers().getPlayers()))
/*     */                 {
/* 102 */                   StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*     */                   GameProfile[] arrayOfGameProfile;
/* 104 */                   for (i = (arrayOfGameProfile = serverstatusresponse.getPlayers().getPlayers()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/*     */                     
/* 106 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 108 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 111 */                     stringbuilder.append(gameprofile.getName());
/*     */                     b++; }
/*     */                   
/* 114 */                   if ((serverstatusresponse.getPlayers().getPlayers()).length < serverstatusresponse.getPlayers().getOnlinePlayerCount()) {
/*     */                     
/* 116 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 118 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 121 */                     stringbuilder.append(I18n.format("multiplayer.status.and_more", new Object[] { Integer.valueOf(serverstatusresponse.getPlayers().getOnlinePlayerCount() - (serverstatusresponse.getPlayers().getPlayers()).length) }));
/*     */                   } 
/*     */                   
/* 124 */                   server.playerList = stringbuilder.toString();
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 129 */                 server.populationInfo = TextFormatting.DARK_GRAY + I18n.format("multiplayer.status.unknown", new Object[0]);
/*     */               } 
/*     */               
/* 132 */               if (serverstatusresponse.getFavicon() != null) {
/*     */                 
/* 134 */                 String s = serverstatusresponse.getFavicon();
/*     */                 
/* 136 */                 if (s.startsWith("data:image/png;base64,"))
/*     */                 {
/* 138 */                   server.setBase64EncodedIconData(s.substring("data:image/png;base64,".length()));
/*     */                 }
/*     */                 else
/*     */                 {
/* 142 */                   ServerPinger.LOGGER.error("Invalid server icon (unknown format)");
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 147 */                 server.setBase64EncodedIconData(null);
/*     */               } 
/*     */               
/* 150 */               this.pingSentAt = Minecraft.getSystemTime();
/* 151 */               networkmanager.sendPacket((Packet)new CPacketPing(this.pingSentAt));
/* 152 */               this.successful = true;
/*     */             } 
/*     */           }
/*     */           
/*     */           public void handlePong(SPacketPong packetIn) {
/* 157 */             long i = this.pingSentAt;
/* 158 */             long j = Minecraft.getSystemTime();
/* 159 */             server.pingToServer = j - i;
/* 160 */             networkmanager.closeChannel((ITextComponent)new TextComponentString("Finished"));
/*     */           }
/*     */           
/*     */           public void onDisconnect(ITextComponent reason) {
/* 164 */             if (!this.successful) {
/*     */               
/* 166 */               ServerPinger.LOGGER.error("Can't ping {}: {}", server.serverIP, reason.getUnformattedText());
/* 167 */               server.serverMOTD = TextFormatting.DARK_RED + I18n.format("multiplayer.status.cannot_connect", new Object[0]);
/* 168 */               server.populationInfo = "";
/* 169 */               ServerPinger.this.tryCompatibilityPing(server);
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*     */     try {
/* 176 */       networkmanager.sendPacket((Packet)new C00Handshake(serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.STATUS));
/* 177 */       networkmanager.sendPacket((Packet)new CPacketServerQuery());
/*     */     }
/* 179 */     catch (Throwable throwable) {
/*     */       
/* 181 */       LOGGER.error(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryCompatibilityPing(final ServerData server) {
/* 187 */     final ServerAddress serveraddress = ServerAddress.fromString(server.serverIP);
/* 188 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/*     */             try {
/* 194 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */             }
/* 196 */             catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 201 */             p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>()
/*     */                   {
/*     */                     public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception
/*     */                     {
/* 205 */                       super.channelActive(p_channelActive_1_);
/* 206 */                       ByteBuf bytebuf = Unpooled.buffer();
/*     */ 
/*     */                       
/*     */                       try {
/* 210 */                         bytebuf.writeByte(254);
/* 211 */                         bytebuf.writeByte(1);
/* 212 */                         bytebuf.writeByte(250);
/* 213 */                         char[] achar = "MC|PingHost".toCharArray();
/* 214 */                         bytebuf.writeShort(achar.length); byte b; int i;
/*     */                         char[] arrayOfChar1;
/* 216 */                         for (i = (arrayOfChar1 = achar).length, b = 0; b < i; ) { char c0 = arrayOfChar1[b];
/*     */                           
/* 218 */                           bytebuf.writeChar(c0);
/*     */                           b++; }
/*     */                         
/* 221 */                         bytebuf.writeShort(7 + 2 * serveraddress.getIP().length());
/* 222 */                         bytebuf.writeByte(127);
/* 223 */                         achar = serveraddress.getIP().toCharArray();
/* 224 */                         bytebuf.writeShort(achar.length);
/*     */                         
/* 226 */                         for (i = (arrayOfChar1 = achar).length, b = 0; b < i; ) { char c1 = arrayOfChar1[b];
/*     */                           
/* 228 */                           bytebuf.writeChar(c1);
/*     */                           b++; }
/*     */                         
/* 231 */                         bytebuf.writeInt(serveraddress.getPort());
/* 232 */                         p_channelActive_1_.channel().writeAndFlush(bytebuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */                       }
/*     */                       finally {
/*     */                         
/* 236 */                         bytebuf.release();
/*     */                       } 
/*     */                     }
/*     */                     
/*     */                     protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, ByteBuf p_channelRead0_2_) throws Exception {
/* 241 */                       short short1 = p_channelRead0_2_.readUnsignedByte();
/*     */                       
/* 243 */                       if (short1 == 255) {
/*     */                         
/* 245 */                         String s = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), StandardCharsets.UTF_16BE);
/* 246 */                         String[] astring = (String[])Iterables.toArray(ServerPinger.PING_RESPONSE_SPLITTER.split(s), String.class);
/*     */                         
/* 248 */                         if ("§1".equals(astring[0])) {
/*     */                           
/* 250 */                           int i = MathHelper.getInt(astring[1], 0);
/* 251 */                           String s1 = astring[2];
/* 252 */                           String s2 = astring[3];
/* 253 */                           int j = MathHelper.getInt(astring[4], -1);
/* 254 */                           int k = MathHelper.getInt(astring[5], -1);
/* 255 */                           server.version = -1;
/* 256 */                           server.gameVersion = s1;
/* 257 */                           server.serverMOTD = s2;
/* 258 */                           server.populationInfo = TextFormatting.GRAY + j + TextFormatting.DARK_GRAY + "/" + TextFormatting.GRAY + k;
/*     */                         } 
/*     */                       } 
/*     */                       
/* 262 */                       p_channelRead0_1_.close();
/*     */                     }
/*     */                     
/*     */                     public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/* 266 */                       p_exceptionCaught_1_.close();
/*     */                     }
/*     */                   } });
/*     */           }
/* 270 */         })).channel(NioSocketChannel.class)).connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public void pingPendingNetworks() {
/* 275 */     synchronized (this.pingDestinations) {
/*     */       
/* 277 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 279 */       while (iterator.hasNext()) {
/*     */         
/* 281 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 283 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 285 */           networkmanager.processReceivedPackets();
/*     */           
/*     */           continue;
/*     */         } 
/* 289 */         iterator.remove();
/* 290 */         networkmanager.checkDisconnected();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPendingNetworks() {
/* 298 */     synchronized (this.pingDestinations) {
/*     */       
/* 300 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 302 */       while (iterator.hasNext()) {
/*     */         
/* 304 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 306 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 308 */           iterator.remove();
/* 309 */           networkmanager.closeChannel((ITextComponent)new TextComponentTranslation("multiplayer.status.cancelled", new Object[0]));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\network\ServerPinger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */