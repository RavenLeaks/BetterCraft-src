/*     */ package net.minecraft.realms;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.client.CPacketLoginStart;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class RealmsConnect {
/*  17 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final RealmsScreen onlineScreen;
/*     */   private volatile boolean aborted;
/*     */   private NetworkManager connection;
/*     */   
/*     */   public RealmsConnect(RealmsScreen onlineScreenIn) {
/*  24 */     this.onlineScreen = onlineScreenIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void connect(final String p_connect_1_, final int p_connect_2_) {
/*  29 */     Realms.setConnectedToRealms(true);
/*  30 */     (new Thread("Realms-connect-task")
/*     */       {
/*     */         public void run()
/*     */         {
/*  34 */           InetAddress inetaddress = null;
/*     */ 
/*     */           
/*     */           try {
/*  38 */             inetaddress = InetAddress.getByName(p_connect_1_);
/*     */             
/*  40 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  45 */             RealmsConnect.this.connection = NetworkManager.createNetworkManagerAndConnect(inetaddress, p_connect_2_, (Minecraft.getMinecraft()).gameSettings.isUsingNativeTransport());
/*     */             
/*  47 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  52 */             RealmsConnect.this.connection.setNetHandler((INetHandler)new NetHandlerLoginClient(RealmsConnect.this.connection, Minecraft.getMinecraft(), (GuiScreen)RealmsConnect.this.onlineScreen.getProxy()));
/*     */             
/*  54 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  59 */             RealmsConnect.this.connection.sendPacket((Packet)new C00Handshake(p_connect_1_, p_connect_2_, EnumConnectionState.LOGIN));
/*     */             
/*  61 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  66 */             Minecraft.getMinecraft(); RealmsConnect.this.connection.sendPacket((Packet)new CPacketLoginStart(Minecraft.getSession().getProfile()));
/*     */           }
/*  68 */           catch (UnknownHostException unknownhostexception) {
/*     */             
/*  70 */             Realms.clearResourcePack();
/*     */             
/*  72 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  77 */             RealmsConnect.LOGGER.error("Couldn't connect to world", unknownhostexception);
/*  78 */             Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (ITextComponent)new TextComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + this.val$p_connect_1_ + "'" })));
/*     */           }
/*  80 */           catch (Exception exception) {
/*     */             
/*  82 */             Realms.clearResourcePack();
/*     */             
/*  84 */             if (RealmsConnect.this.aborted) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  89 */             RealmsConnect.LOGGER.error("Couldn't connect to world", exception);
/*  90 */             String s = exception.toString();
/*     */             
/*  92 */             if (inetaddress != null) {
/*     */               
/*  94 */               String s1 = inetaddress + ":" + p_connect_2_;
/*  95 */               s = s.replaceAll(s1, "");
/*     */             } 
/*     */             
/*  98 */             Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (ITextComponent)new TextComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*     */           } 
/*     */         }
/* 101 */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void abort() {
/* 106 */     this.aborted = true;
/*     */     
/* 108 */     if (this.connection != null && this.connection.isChannelOpen()) {
/*     */       
/* 110 */       this.connection.closeChannel((ITextComponent)new TextComponentTranslation("disconnect.genericReason", new Object[0]));
/* 111 */       this.connection.checkDisconnected();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 117 */     if (this.connection != null)
/*     */     {
/* 119 */       if (this.connection.isChannelOpen()) {
/*     */         
/* 121 */         this.connection.processReceivedPackets();
/*     */       }
/*     */       else {
/*     */         
/* 125 */         this.connection.checkDisconnected();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsConnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */