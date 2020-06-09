/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.PublicKey;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.crypto.SecretKey;
/*     */ import me.nzxter.bettercraft.mods.mcleaks.McLeaksAPI;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenRealmsProxy;
/*     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.INetHandlerLoginClient;
/*     */ import net.minecraft.network.login.client.CPacketEncryptionResponse;
/*     */ import net.minecraft.network.login.server.SPacketDisconnect;
/*     */ import net.minecraft.network.login.server.SPacketEnableCompression;
/*     */ import net.minecraft.network.login.server.SPacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.SPacketLoginSuccess;
/*     */ import net.minecraft.realms.DisconnectedRealmsScreen;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class NetHandlerLoginClient
/*     */   implements INetHandlerLoginClient
/*     */ {
/*  54 */   private static final Logger LOGGER = LogManager.getLogger(); private final Minecraft mc; @Nullable
/*     */   private final GuiScreen previousGuiScreen;
/*     */   
/*     */   public NetHandlerLoginClient(NetworkManager networkManagerIn, Minecraft mcIn, @Nullable GuiScreen previousScreenIn) {
/*  58 */     this.networkManager = networkManagerIn;
/*  59 */     this.mc = mcIn;
/*  60 */     this.previousGuiScreen = previousScreenIn;
/*     */   }
/*     */   private final NetworkManager networkManager; private GameProfile gameProfile;
/*     */   
/*     */   public void handleEncryptionRequest(SPacketEncryptionRequest packetIn) {
/*  65 */     final SecretKey secretkey = CryptManager.createNewSharedKey();
/*  66 */     String s = packetIn.getServerId();
/*  67 */     PublicKey publickey = packetIn.getPublicKey();
/*  68 */     String s2 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);
/*     */     
/*  70 */     GuiConnecting.verifyingsession = true;
/*     */ 
/*     */ 
/*     */     
/*  74 */     if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().isOnLAN()) {
/*  75 */       if (McLeaksAPI.sessions_mcLeaksSession == null) {
/*     */         try {
/*  77 */           getSessionService().joinServer(Minecraft.getSession().getProfile(), Minecraft.getSession().getToken(), s2);
/*     */         }
/*  79 */         catch (AuthenticationException var10) {
/*  80 */           LOGGER.warn("Couldn't connect to auth servers but will continue to join LAN");
/*     */         } 
/*     */       } else {
/*     */         
/*     */         try {
/*  85 */           McLeaksAPI.joinServer(McLeaksAPI.sessions_mcLeaksSession, s2, (InetSocketAddress)this.networkManager.getRemoteAddress());
/*     */         }
/*  87 */         catch (IOException e) {
/*  88 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/*     */       try {
/*  94 */         if (McLeaksAPI.sessions_mcLeaksSession == null) {
/*  95 */           getSessionService().joinServer(Minecraft.getSession().getProfile(), Minecraft.getSession().getToken(), s2);
/*     */         } else {
/*     */           
/*     */           try {
/*  99 */             McLeaksAPI.joinServer(McLeaksAPI.sessions_mcLeaksSession, s2, (InetSocketAddress)this.networkManager.getRemoteAddress());
/*     */           }
/* 101 */           catch (IOException e) {
/* 102 */             e.printStackTrace();
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 108 */       catch (AuthenticationUnavailableException var11) {
/* 109 */         this.networkManager.closeChannel((ITextComponent)new TextComponentTranslation("disconnect.loginFailedInfo", new Object[] { new TextComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0]) }));
/*     */         
/*     */         return;
/* 112 */       } catch (InvalidCredentialsException var12) {
/* 113 */         this.networkManager.closeChannel((ITextComponent)new TextComponentTranslation("disconnect.loginFailedInfo", new Object[] { new TextComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
/*     */         
/*     */         return;
/* 116 */       } catch (AuthenticationException authenticationexception) {
/* 117 */         this.networkManager.closeChannel((ITextComponent)new TextComponentTranslation("disconnect.loginFailedInfo", new Object[] { authenticationexception.getMessage() }));
/*     */         return;
/*     */       } 
/*     */     } 
/* 121 */     this.networkManager.sendPacket((Packet)new CPacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()), new GenericFutureListener<Future<? super Void>>()
/*     */         {
/*     */           public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
/* 124 */             NetHandlerLoginClient.this.networkManager.enableEncryption(secretkey);
/*     */           }
/* 126 */         },  new GenericFutureListener[0]);
/*     */   }
/*     */   
/*     */   private MinecraftSessionService getSessionService() {
/* 130 */     return this.mc.getSessionService();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleLoginSuccess(SPacketLoginSuccess packetIn) {
/* 136 */     GuiConnecting.sucess = true;
/*     */ 
/*     */     
/* 139 */     this.gameProfile = packetIn.getProfile();
/* 140 */     this.networkManager.setConnectionState(EnumConnectionState.PLAY);
/* 141 */     this.networkManager.setNetHandler((INetHandler)new NetHandlerPlayClient(this.mc, this.previousGuiScreen, this.networkManager, this.gameProfile));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisconnect(ITextComponent reason) {
/* 146 */     if (this.previousGuiScreen != null && this.previousGuiScreen instanceof GuiScreenRealmsProxy) {
/* 147 */       this.mc.displayGuiScreen((GuiScreen)(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.previousGuiScreen).getProxy(), "connect.failed", reason)).getProxy());
/*     */     } else {
/*     */       
/* 150 */       this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(this.previousGuiScreen, "connect.failed", reason));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleDisconnect(SPacketDisconnect packetIn) {
/* 156 */     this.networkManager.closeChannel(packetIn.getReason());
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEnableCompression(SPacketEnableCompression packetIn) {
/* 161 */     if (!this.networkManager.isLocalChannel())
/* 162 */       this.networkManager.setCompressionThreshold(packetIn.getCompressionThreshold()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\network\NetHandlerLoginClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */