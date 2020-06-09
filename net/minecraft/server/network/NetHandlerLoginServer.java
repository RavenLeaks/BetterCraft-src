/*     */ package net.minecraft.server.network;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.INetHandlerLoginServer;
/*     */ import net.minecraft.network.login.client.CPacketEncryptionResponse;
/*     */ import net.minecraft.network.login.client.CPacketLoginStart;
/*     */ import net.minecraft.network.login.server.SPacketDisconnect;
/*     */ import net.minecraft.network.login.server.SPacketEnableCompression;
/*     */ import net.minecraft.network.login.server.SPacketLoginSuccess;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetHandlerLoginServer implements INetHandlerLoginServer, ITickable {
/*  39 */   private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
/*  40 */   private static final Logger LOGGER = LogManager.getLogger();
/*  41 */   private static final Random RANDOM = new Random();
/*  42 */   private final byte[] verifyToken = new byte[4];
/*     */   private final MinecraftServer server;
/*     */   public final NetworkManager networkManager;
/*  45 */   private LoginState currentLoginState = LoginState.HELLO;
/*     */   
/*     */   private int connectionTimer;
/*     */   
/*     */   private GameProfile loginGameProfile;
/*  50 */   private final String serverId = "";
/*     */   
/*     */   private SecretKey secretKey;
/*     */   private EntityPlayerMP player;
/*     */   
/*     */   public NetHandlerLoginServer(MinecraftServer serverIn, NetworkManager networkManagerIn) {
/*  56 */     this.server = serverIn;
/*  57 */     this.networkManager = networkManagerIn;
/*  58 */     RANDOM.nextBytes(this.verifyToken);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  66 */     if (this.currentLoginState == LoginState.READY_TO_ACCEPT) {
/*     */       
/*  68 */       tryAcceptPlayer();
/*     */     }
/*  70 */     else if (this.currentLoginState == LoginState.DELAY_ACCEPT) {
/*     */       
/*  72 */       EntityPlayerMP entityplayermp = this.server.getPlayerList().getPlayerByUUID(this.loginGameProfile.getId());
/*     */       
/*  74 */       if (entityplayermp == null) {
/*     */         
/*  76 */         this.currentLoginState = LoginState.READY_TO_ACCEPT;
/*  77 */         this.server.getPlayerList().initializeConnectionToPlayer(this.networkManager, this.player);
/*  78 */         this.player = null;
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     if (this.connectionTimer++ == 600)
/*     */     {
/*  84 */       func_194026_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.slow_login", new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_194026_b(ITextComponent p_194026_1_) {
/*     */     try {
/*  92 */       LOGGER.info("Disconnecting {}: {}", getConnectionInfo(), p_194026_1_.getUnformattedText());
/*  93 */       this.networkManager.sendPacket((Packet)new SPacketDisconnect(p_194026_1_));
/*  94 */       this.networkManager.closeChannel(p_194026_1_);
/*     */     }
/*  96 */     catch (Exception exception) {
/*     */       
/*  98 */       LOGGER.error("Error whilst disconnecting player", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tryAcceptPlayer() {
/* 104 */     if (!this.loginGameProfile.isComplete())
/*     */     {
/* 106 */       this.loginGameProfile = getOfflineProfile(this.loginGameProfile);
/*     */     }
/*     */     
/* 109 */     String s = this.server.getPlayerList().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
/*     */     
/* 111 */     if (s != null) {
/*     */       
/* 113 */       func_194026_b((ITextComponent)new TextComponentTranslation(s, new Object[0]));
/*     */     }
/*     */     else {
/*     */       
/* 117 */       this.currentLoginState = LoginState.ACCEPTED;
/*     */       
/* 119 */       if (this.server.getNetworkCompressionThreshold() >= 0 && !this.networkManager.isLocalChannel())
/*     */       {
/* 121 */         this.networkManager.sendPacket((Packet)new SPacketEnableCompression(this.server.getNetworkCompressionThreshold()), (GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture p_operationComplete_1_) throws Exception
/*     */               {
/* 125 */                 NetHandlerLoginServer.this.networkManager.setCompressionThreshold(NetHandlerLoginServer.this.server.getNetworkCompressionThreshold());
/*     */               }
/*     */             },  new GenericFutureListener[0]);
/*     */       }
/*     */       
/* 130 */       this.networkManager.sendPacket((Packet)new SPacketLoginSuccess(this.loginGameProfile));
/* 131 */       EntityPlayerMP entityplayermp = this.server.getPlayerList().getPlayerByUUID(this.loginGameProfile.getId());
/*     */       
/* 133 */       if (entityplayermp != null) {
/*     */         
/* 135 */         this.currentLoginState = LoginState.DELAY_ACCEPT;
/* 136 */         this.player = this.server.getPlayerList().createPlayerForUser(this.loginGameProfile);
/*     */       }
/*     */       else {
/*     */         
/* 140 */         this.server.getPlayerList().initializeConnectionToPlayer(this.networkManager, this.server.getPlayerList().createPlayerForUser(this.loginGameProfile));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnect(ITextComponent reason) {
/* 150 */     LOGGER.info("{} lost connection: {}", getConnectionInfo(), reason.getUnformattedText());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getConnectionInfo() {
/* 155 */     return (this.loginGameProfile != null) ? (this.loginGameProfile + " (" + this.networkManager.getRemoteAddress() + ")") : String.valueOf(this.networkManager.getRemoteAddress());
/*     */   }
/*     */ 
/*     */   
/*     */   public void processLoginStart(CPacketLoginStart packetIn) {
/* 160 */     Validate.validState((this.currentLoginState == LoginState.HELLO), "Unexpected hello packet", new Object[0]);
/* 161 */     this.loginGameProfile = packetIn.getProfile();
/*     */     
/* 163 */     if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
/*     */       
/* 165 */       this.currentLoginState = LoginState.KEY;
/* 166 */       this.networkManager.sendPacket((Packet)new SPacketEncryptionRequest("", this.server.getKeyPair().getPublic(), this.verifyToken));
/*     */     }
/*     */     else {
/*     */       
/* 170 */       this.currentLoginState = LoginState.READY_TO_ACCEPT;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEncryptionResponse(CPacketEncryptionResponse packetIn) {
/* 176 */     Validate.validState((this.currentLoginState == LoginState.KEY), "Unexpected key packet", new Object[0]);
/* 177 */     PrivateKey privatekey = this.server.getKeyPair().getPrivate();
/*     */     
/* 179 */     if (!Arrays.equals(this.verifyToken, packetIn.getVerifyToken(privatekey)))
/*     */     {
/* 181 */       throw new IllegalStateException("Invalid nonce!");
/*     */     }
/*     */ 
/*     */     
/* 185 */     this.secretKey = packetIn.getSecretKey(privatekey);
/* 186 */     this.currentLoginState = LoginState.AUTHENTICATING;
/* 187 */     this.networkManager.enableEncryption(this.secretKey);
/* 188 */     (new Thread("User Authenticator #" + AUTHENTICATOR_THREAD_ID.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/* 192 */           GameProfile gameprofile = NetHandlerLoginServer.this.loginGameProfile;
/*     */ 
/*     */           
/*     */           try {
/* 196 */             String s = (new BigInteger(CryptManager.getServerIdHash("", NetHandlerLoginServer.this.server.getKeyPair().getPublic(), NetHandlerLoginServer.this.secretKey))).toString(16);
/* 197 */             NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.server.getMinecraftSessionService().hasJoinedServer(new GameProfile(null, gameprofile.getName()), s, func_191235_a());
/*     */             
/* 199 */             if (NetHandlerLoginServer.this.loginGameProfile != null)
/*     */             {
/* 201 */               NetHandlerLoginServer.LOGGER.info("UUID of player {} is {}", NetHandlerLoginServer.this.loginGameProfile.getName(), NetHandlerLoginServer.this.loginGameProfile.getId());
/* 202 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             }
/* 204 */             else if (NetHandlerLoginServer.this.server.isSinglePlayer())
/*     */             {
/* 206 */               NetHandlerLoginServer.LOGGER.warn("Failed to verify username but will let them in anyway!");
/* 207 */               NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.getOfflineProfile(gameprofile);
/* 208 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             }
/*     */             else
/*     */             {
/* 212 */               NetHandlerLoginServer.this.func_194026_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.unverified_username", new Object[0]));
/* 213 */               NetHandlerLoginServer.LOGGER.error("Username '{}' tried to join with an invalid session", gameprofile.getName());
/*     */             }
/*     */           
/* 216 */           } catch (AuthenticationUnavailableException var3) {
/*     */             
/* 218 */             if (NetHandlerLoginServer.this.server.isSinglePlayer()) {
/*     */               
/* 220 */               NetHandlerLoginServer.LOGGER.warn("Authentication servers are down but will let them in anyway!");
/* 221 */               NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.getOfflineProfile(gameprofile);
/* 222 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             }
/*     */             else {
/*     */               
/* 226 */               NetHandlerLoginServer.this.func_194026_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.authservers_down", new Object[0]));
/* 227 */               NetHandlerLoginServer.LOGGER.error("Couldn't verify username because servers are unavailable");
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         @Nullable
/*     */         private InetAddress func_191235_a() {
/* 234 */           SocketAddress socketaddress = NetHandlerLoginServer.this.networkManager.getRemoteAddress();
/* 235 */           return (NetHandlerLoginServer.this.server.func_190518_ac() && socketaddress instanceof InetSocketAddress) ? ((InetSocketAddress)socketaddress).getAddress() : null;
/*     */         }
/* 237 */       }).start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected GameProfile getOfflineProfile(GameProfile original) {
/* 243 */     UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + original.getName()).getBytes(StandardCharsets.UTF_8));
/* 244 */     return new GameProfile(uuid, original.getName());
/*     */   }
/*     */   
/*     */   enum LoginState
/*     */   {
/* 249 */     HELLO,
/* 250 */     KEY,
/* 251 */     AUTHENTICATING,
/* 252 */     READY_TO_ACCEPT,
/* 253 */     DELAY_ACCEPT,
/* 254 */     ACCEPTED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\network\NetHandlerLoginServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */