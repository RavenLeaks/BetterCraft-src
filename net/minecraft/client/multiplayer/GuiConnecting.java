/*     */ package net.minecraft.client.multiplayer;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.login.client.CPacketLoginStart;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiConnecting extends GuiScreen {
/*  27 */   private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
/*  28 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private NetworkManager networkManager;
/*     */   
/*     */   private boolean cancel;
/*     */   private final GuiScreen previousGuiScreen;
/*     */   public static boolean SRV = true;
/*     */   public static boolean Netty = true;
/*     */   public static boolean Resolving = true;
/*     */   public static boolean Connecting = true;
/*     */   public static boolean sendingloginpackets;
/*     */   public static boolean waitingforresponse;
/*     */   public static boolean verifyingsession;
/*     */   public static boolean encrypting;
/*     */   public static boolean sucess;
/*  43 */   public static String kickedMessage = "";
/*     */   
/*     */   public static boolean kicked = false;
/*     */ 
/*     */   
/*     */   public GuiConnecting(GuiScreen parent, Minecraft mcIn, ServerData serverDataIn) {
/*  49 */     this.mc = mcIn;
/*  50 */     this.previousGuiScreen = parent;
/*  51 */     ServerAddress serveraddress = ServerAddress.fromString(serverDataIn.serverIP);
/*  52 */     mcIn.loadWorld(null);
/*  53 */     mcIn.setServerData(serverDataIn);
/*  54 */     connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiConnecting(GuiScreen parent, Minecraft mcIn, String hostName, int port) {
/*  59 */     this.mc = mcIn;
/*  60 */     this.previousGuiScreen = parent;
/*  61 */     mcIn.loadWorld(null);
/*  62 */     connect(hostName, port);
/*     */   }
/*     */ 
/*     */   
/*     */   private void connect(final String ip, final int port) {
/*  67 */     LOGGER.info("Connecting to {}, {}", ip, Integer.valueOf(port));
/*  68 */     (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
/*     */       {
/*     */         
/*     */         public void run()
/*     */         {
/*  73 */           InetAddress inetaddress = null;
/*     */ 
/*     */           
/*     */           try {
/*  77 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  82 */             GuiConnecting.SRV = true;
/*  83 */             inetaddress = InetAddress.getByName(ip);
/*  84 */             GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.isUsingNativeTransport());
/*  85 */             GuiConnecting.Resolving = true;
/*  86 */             GuiConnecting.this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
/*  87 */             GuiConnecting.Netty = true;
/*  88 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00Handshake(ip, port, EnumConnectionState.LOGIN));
/*  89 */             GuiConnecting.Connecting = true;
/*  90 */             GuiConnecting.this.networkManager.sendPacket((Packet)new CPacketLoginStart(Minecraft.getSession().getProfile()));
/*  91 */             GuiConnecting.sendingloginpackets = true;
/*     */           
/*     */           }
/*  94 */           catch (UnknownHostException unknownhostexception) {
/*     */             
/*  96 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/* 101 */             GuiConnecting.LOGGER.error("Couldn't connect to server", unknownhostexception);
/* 102 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (ITextComponent)new TextComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
/*     */           }
/* 104 */           catch (Exception exception) {
/*     */             
/* 106 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/* 111 */             GuiConnecting.LOGGER.error("Couldn't connect to server", exception);
/* 112 */             String s = exception.toString();
/*     */             
/* 114 */             if (inetaddress != null) {
/*     */               
/* 116 */               String s1 = inetaddress + ":" + port;
/* 117 */               s = s.replaceAll(s1, "");
/*     */             } 
/*     */             
/* 120 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (ITextComponent)new TextComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*     */           } 
/*     */         }
/* 123 */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 128 */     SRV = true;
/* 129 */     Netty = true;
/* 130 */     Resolving = true;
/* 131 */     Connecting = true;
/* 132 */     sendingloginpackets = false;
/* 133 */     waitingforresponse = false;
/* 134 */     encrypting = false;
/* 135 */     sucess = false;
/* 136 */     kicked = false;
/* 137 */     kickedMessage = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 146 */     if (this.networkManager != null)
/*     */     {
/* 148 */       if (this.networkManager.isChannelOpen()) {
/*     */ 
/*     */         
/* 151 */         waitingforresponse = true;
/*     */         
/* 153 */         this.networkManager.processReceivedPackets();
/*     */       }
/*     */       else {
/*     */         
/* 157 */         this.networkManager.checkDisconnected();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 176 */     this.buttonList.clear();
/* 177 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 185 */     if (button.id == 0) {
/*     */       
/* 187 */       this.cancel = true;
/*     */       
/* 189 */       if (this.networkManager != null)
/*     */       {
/* 191 */         this.networkManager.closeChannel((ITextComponent)new TextComponentString("Aborted"));
/*     */       }
/*     */       
/* 194 */       this.mc.displayGuiScreen(this.previousGuiScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*     */     String s2;
/* 205 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*     */     
/* 207 */     drawDefaultBackground();
/* 208 */     int var4 = this.height / 4 + 120 + 12;
/* 209 */     Gui.drawRect(this.width / 2 - 100, var4 - 130, this.width / 2 + 100, var4 - 20, -16777216);
/* 210 */     switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
/*     */       default:
/* 212 */         s2 = "§7_";
/*     */         break;
/*     */       
/*     */       case 1:
/*     */       case 3:
/* 217 */         s2 = "";
/*     */         break;
/*     */       
/*     */       case 2:
/* 221 */         s2 = "§7_";
/*     */         break;
/*     */     } 
/* 224 */     int yPos = var4 - 86;
/* 225 */     drawString(this.fontRendererObj, "§c" + kickedMessage, 5, 5, Color.RED.darker().getRGB());
/* 226 */     if (SRV) {
/* 227 */       drawString(this.fontRendererObj, "Resolving SRV...", this.width / 2 - 95 - 1, var4 - 126, Color.GREEN.darker().getRGB());
/* 228 */       if (Netty) {
/* 229 */         drawString(this.fontRendererObj, "Starting Netty Connection...", this.width / 2 - 95 - 1, var4 - 116, Color.GREEN.darker().getRGB());
/* 230 */         if (Resolving) {
/* 231 */           drawString(this.fontRendererObj, "Resolving IP...", this.width / 2 - 95 - 1, var4 - 106, Color.GREEN.darker().getRGB());
/* 232 */           if (Connecting) {
/* 233 */             drawString(this.fontRendererObj, "Connecting...", this.width / 2 - 95 - 1, var4 - 96, Color.GREEN.darker().getRGB());
/* 234 */             if (sendingloginpackets) {
/* 235 */               drawString(this.fontRendererObj, "Sending Login Packets...", this.width / 2 - 95 - 1, var4 - 86, Color.GREEN.darker().getRGB());
/* 236 */               yPos = var4 - 76;
/* 237 */               if (waitingforresponse) {
/* 238 */                 drawString(this.fontRendererObj, "Waiting for response...", this.width / 2 - 95 - 1, var4 - 76, Color.GREEN.darker().getRGB());
/* 239 */                 yPos = var4 - 66;
/* 240 */                 if (verifyingsession) {
/* 241 */                   drawString(this.fontRendererObj, "Verifying Session...", this.width / 2 - 95 - 1, var4 - 66, Color.GREEN.darker().getRGB());
/* 242 */                   drawString(this.fontRendererObj, "Encrypting...", this.width / 2 - 95 - 1, var4 - 56, Color.GREEN.darker().getRGB());
/* 243 */                   yPos = var4 - 46;
/* 244 */                   if (sucess) {
/* 245 */                     drawString(this.fontRendererObj, "Success!", this.width / 2 - 95 - 1, var4 - 46, Color.GREEN.darker().getRGB());
/* 246 */                     yPos = var4 - 36;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 254 */       drawString(this.fontRendererObj, s2, this.width / 2 - 95 - 1, yPos, Color.GREEN.darker().getRGB());
/* 255 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\GuiConnecting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */