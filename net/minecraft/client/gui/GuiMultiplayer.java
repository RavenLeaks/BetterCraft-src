/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.util.List;
/*     */ import me.nzxter.bettercraft.BetterCraft;
/*     */ import me.nzxter.bettercraft.utils.GeoUtils;
/*     */ import me.nzxter.bettercraft.utils.ProtocolUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*     */ import net.minecraft.client.multiplayer.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.network.LanServerDetector;
/*     */ import net.minecraft.client.network.LanServerInfo;
/*     */ import net.minecraft.client.network.ServerPinger;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiMultiplayer
/*     */   extends GuiScreen
/*     */ {
/*  34 */   private static final Logger LOGGER = LogManager.getLogger();
/*  35 */   private final ServerPinger oldServerPinger = new ServerPinger();
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   
/*     */   private ServerSelectionList serverListSelector;
/*     */   
/*     */   private ServerList savedServerList;
/*     */   
/*     */   private GuiButton btnEditServer;
/*     */   
/*     */   private GuiButton btnSelectServer;
/*     */   
/*     */   private GuiButton btnDeleteServer;
/*     */   
/*     */   private boolean deletingServer;
/*     */   
/*     */   private boolean addingServer;
/*     */   
/*     */   private boolean editingServer;
/*     */   
/*     */   private boolean directConnect;
/*     */   
/*     */   private String hoveringText;
/*     */   
/*     */   private ServerData selectedServer;
/*     */   
/*     */   private LanServerDetector.LanServerList lanServerList;
/*     */   private LanServerDetector.ThreadLanServerFind lanServerDetector;
/*     */   private boolean initialized;
/*     */   String lastAddress;
/*     */   private volatile String addressPort;
/*     */   
/*     */   public void initGui() {
/*  68 */     Keyboard.enableRepeatEvents(true);
/*  69 */     this.buttonList.clear();
/*  70 */     this.buttonList.clear();
/*     */ 
/*     */     
/*  73 */     this.buttonList.add(new GuiButton(7777, 8, 5, 20, 20, "§c«"));
/*  74 */     this.buttonList.add(new GuiButton(7778, 88, 5, 20, 20, "§c»"));
/*  75 */     GuiButton versionSwitchBut = new GuiButton(789, 28, 5, 60, 20, "§a" + BetterCraft.INSTANCE.getCurrentMinecraftVersion().getName());
/*  76 */     versionSwitchBut.enabled = false;
/*  77 */     this.buttonList.add(versionSwitchBut);
/*     */ 
/*     */     
/*  80 */     if (this.initialized) {
/*     */       
/*  82 */       this.serverListSelector.setDimensions(this.width, this.height, 32, this.height - 64);
/*     */     }
/*     */     else {
/*     */       
/*  86 */       this.initialized = true;
/*  87 */       this.savedServerList = new ServerList(this.mc);
/*  88 */       this.savedServerList.loadServerList();
/*  89 */       this.lanServerList = new LanServerDetector.LanServerList();
/*     */ 
/*     */       
/*     */       try {
/*  93 */         this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList);
/*  94 */         this.lanServerDetector.start();
/*     */       }
/*  96 */       catch (Exception exception) {
/*     */         
/*  98 */         LOGGER.warn("Unable to start LAN server detection: {}", exception.getMessage());
/*     */       } 
/*     */       
/* 101 */       this.serverListSelector = new ServerSelectionList(this, this.mc, this.width, this.height, 32, this.height - 64, 36);
/* 102 */       this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */     } 
/*     */     
/* 105 */     createButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 113 */     super.handleMouseInput();
/* 114 */     this.serverListSelector.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public void createButtons() {
/* 119 */     this.btnEditServer = addButton(new GuiButton(7, this.width / 2 - 154, this.height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
/* 120 */     this.btnDeleteServer = addButton(new GuiButton(2, this.width / 2 - 74, this.height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
/* 121 */     this.btnSelectServer = addButton(new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
/* 122 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
/* 123 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
/* 124 */     this.buttonList.add(new GuiButton(8, this.width / 2 + 4, this.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
/* 125 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 76, this.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
/*     */ 
/*     */     
/* 128 */     this.buttonList.add(new GuiButton(8000, this.width - 108, 5, 100, 20, "Crash"));
/*     */ 
/*     */     
/* 131 */     selectServer(this.serverListSelector.getSelected());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 140 */     ((GuiButton)this.buttonList.get(2)).displayString = "§a" + BetterCraft.INSTANCE.getCurrentMinecraftVersion().getName();
/*     */ 
/*     */     
/* 143 */     super.updateScreen();
/*     */     
/* 145 */     if (this.lanServerList.getWasUpdated()) {
/*     */       
/* 147 */       List<LanServerInfo> list = this.lanServerList.getLanServers();
/* 148 */       this.lanServerList.setWasNotUpdated();
/* 149 */       this.serverListSelector.updateNetworkServers(list);
/*     */     } 
/*     */     
/* 152 */     this.oldServerPinger.pingPendingNetworks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 160 */     Keyboard.enableRepeatEvents(false);
/*     */     
/* 162 */     if (this.lanServerDetector != null) {
/*     */       
/* 164 */       this.lanServerDetector.interrupt();
/* 165 */       this.lanServerDetector = null;
/*     */     } 
/*     */     
/* 168 */     this.oldServerPinger.clearPendingNetworks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 176 */     if (button.enabled) {
/*     */       
/* 178 */       GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
/*     */       
/* 180 */       if (button.id == 2 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 182 */         String s4 = (((ServerListEntryNormal)guilistextended$iguilistentry).getServerData()).serverName;
/*     */         
/* 184 */         if (s4 != null)
/*     */         {
/* 186 */           this.deletingServer = true;
/* 187 */           String s = I18n.format("selectServer.deleteQuestion", new Object[0]);
/* 188 */           String s1 = "'" + s4 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
/* 189 */           String s2 = I18n.format("selectServer.deleteButton", new Object[0]);
/* 190 */           String s3 = I18n.format("gui.cancel", new Object[0]);
/* 191 */           GuiYesNo guiyesno = new GuiYesNo(this, s, s1, s2, s3, this.serverListSelector.getSelected());
/* 192 */           this.mc.displayGuiScreen(guiyesno);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 197 */       else if (button.id == 8000) {
/*     */         
/* 199 */         GuiListExtended.IGuiListEntry listEntry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
/* 200 */         if (listEntry == null) {
/*     */           return;
/*     */         }
/* 203 */         ServerData serverData = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/*     */         
/* 205 */         if (serverData == null) {
/*     */           return;
/*     */         }
/* 208 */         ServerAddress serveradress = ServerAddress.resolveAddress(serverData.serverIP);
/* 209 */         String address = this.addressPort = String.valueOf(InetAddress.getByName(serveradress.getIP()).getHostAddress()) + " " + serveradress.getPort();
/* 210 */         Runtime.getRuntime().exec("BetterCraft/instantcrasher.exe " + address);
/*     */ 
/*     */       
/*     */       }
/* 214 */       else if (button.id == 1) {
/*     */         
/* 216 */         connectToSelected();
/*     */       }
/* 218 */       else if (button.id == 4) {
/*     */         
/* 220 */         this.directConnect = true;
/* 221 */         this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false);
/* 222 */         this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer));
/*     */       }
/* 224 */       else if (button.id == 3) {
/*     */         
/* 226 */         this.addingServer = true;
/* 227 */         this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false);
/* 228 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
/*     */       }
/* 230 */       else if (button.id == 7 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 232 */         this.editingServer = true;
/* 233 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 234 */         this.selectedServer = new ServerData(serverdata.serverName, serverdata.serverIP, false);
/* 235 */         this.selectedServer.copyFrom(serverdata);
/* 236 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
/*     */       }
/* 238 */       else if (button.id == 0) {
/*     */         
/* 240 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 242 */       else if (button.id == 8) {
/*     */         
/* 244 */         refreshServerList();
/*     */ 
/*     */       
/*     */       }
/* 248 */       else if (button.id == 7777) {
/* 249 */         BetterCraft.INSTANCE.moveVersionBackward();
/*     */       }
/* 251 */       else if (button.id == 7778) {
/* 252 */         BetterCraft.INSTANCE.moveVersionForward();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshServerList() {
/* 260 */     this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 265 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
/*     */     
/* 267 */     if (this.deletingServer) {
/*     */       
/* 269 */       this.deletingServer = false;
/*     */       
/* 271 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 273 */         this.savedServerList.removeServerData(this.serverListSelector.getSelected());
/* 274 */         this.savedServerList.saveServerList();
/* 275 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 276 */         this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */       } 
/*     */       
/* 279 */       this.mc.displayGuiScreen(this);
/*     */     }
/* 281 */     else if (this.directConnect) {
/*     */       
/* 283 */       this.directConnect = false;
/*     */       
/* 285 */       if (result)
/*     */       {
/* 287 */         connectToServer(this.selectedServer);
/*     */       }
/*     */       else
/*     */       {
/* 291 */         this.mc.displayGuiScreen(this);
/*     */       }
/*     */     
/* 294 */     } else if (this.addingServer) {
/*     */       
/* 296 */       this.addingServer = false;
/*     */       
/* 298 */       if (result) {
/*     */         
/* 300 */         this.savedServerList.addServerData(this.selectedServer);
/* 301 */         this.savedServerList.saveServerList();
/* 302 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 303 */         this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */       } 
/*     */       
/* 306 */       this.mc.displayGuiScreen(this);
/*     */     }
/* 308 */     else if (this.editingServer) {
/*     */       
/* 310 */       this.editingServer = false;
/*     */       
/* 312 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 314 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 315 */         serverdata.serverName = this.selectedServer.serverName;
/* 316 */         serverdata.serverIP = this.selectedServer.serverIP;
/* 317 */         serverdata.copyFrom(this.selectedServer);
/* 318 */         this.savedServerList.saveServerList();
/* 319 */         this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */       } 
/*     */       
/* 322 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 332 */     int i = this.serverListSelector.getSelected();
/* 333 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (i < 0) ? null : this.serverListSelector.getListEntry(i);
/*     */     
/* 335 */     if (keyCode == 63) {
/*     */       
/* 337 */       refreshServerList();
/*     */ 
/*     */     
/*     */     }
/* 341 */     else if (i >= 0) {
/*     */       
/* 343 */       if (keyCode == 200) {
/*     */         
/* 345 */         if (isShiftKeyDown()) {
/*     */           
/* 347 */           if (i > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal)
/*     */           {
/* 349 */             this.savedServerList.swapServers(i, i - 1);
/* 350 */             selectServer(this.serverListSelector.getSelected() - 1);
/* 351 */             this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/* 352 */             this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */           }
/*     */         
/* 355 */         } else if (i > 0) {
/*     */           
/* 357 */           selectServer(this.serverListSelector.getSelected() - 1);
/* 358 */           this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */           
/* 360 */           if (this.serverListSelector.getListEntry(this.serverListSelector.getSelected()) instanceof ServerListEntryLanScan)
/*     */           {
/* 362 */             if (this.serverListSelector.getSelected() > 0)
/*     */             {
/* 364 */               selectServer(this.serverListSelector.getSize() - 1);
/* 365 */               this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */             }
/*     */             else
/*     */             {
/* 369 */               selectServer(-1);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 375 */           selectServer(-1);
/*     */         }
/*     */       
/* 378 */       } else if (keyCode == 208) {
/*     */         
/* 380 */         if (isShiftKeyDown()) {
/*     */           
/* 382 */           if (i < this.savedServerList.countServers() - 1)
/*     */           {
/* 384 */             this.savedServerList.swapServers(i, i + 1);
/* 385 */             selectServer(i + 1);
/* 386 */             this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/* 387 */             this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */           }
/*     */         
/* 390 */         } else if (i < this.serverListSelector.getSize()) {
/*     */           
/* 392 */           selectServer(this.serverListSelector.getSelected() + 1);
/* 393 */           this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */           
/* 395 */           if (this.serverListSelector.getListEntry(this.serverListSelector.getSelected()) instanceof ServerListEntryLanScan)
/*     */           {
/* 397 */             if (this.serverListSelector.getSelected() < this.serverListSelector.getSize() - 1)
/*     */             {
/* 399 */               selectServer(this.serverListSelector.getSize() + 1);
/* 400 */               this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */             }
/*     */             else
/*     */             {
/* 404 */               selectServer(-1);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 410 */           selectServer(-1);
/*     */         }
/*     */       
/* 413 */       } else if (keyCode != 28 && keyCode != 156) {
/*     */         
/* 415 */         super.keyTyped(typedChar, keyCode);
/*     */       }
/*     */       else {
/*     */         
/* 419 */         actionPerformed(this.buttonList.get(2));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 424 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiMultiplayer(GuiScreen parentScreen) {
/* 435 */     this.lastAddress = "Pinging...";
/*     */     this.parentScreen = parentScreen;
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 440 */     this.hoveringText = null;
/* 441 */     drawDefaultBackground();
/* 442 */     this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
/* 443 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
/*     */ 
/*     */     
/*     */     try {
/* 447 */       GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
/*     */       
/* 449 */       ServerData sd = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 450 */       String version = sd.gameVersion;
/* 451 */       int i = sd.version;
/* 452 */       long l = sd.pingToServer;
/*     */ 
/*     */ 
/*     */       
/* 456 */       (new Thread(() -> {
/*     */             try {
/*     */               ServerAddress serveradress = ServerAddress.resolveAddress(paramServerData.serverIP);
/*     */               
/*     */               String adress = InetAddress.getByName(serveradress.getIP()).getHostAddress();
/*     */               
/*     */               if (!this.lastAddress.equals(adress)) {
/*     */                 this.lastAddress = adress;
/*     */                 this.addressPort = String.valueOf(InetAddress.getByName(serveradress.getIP()).getHostAddress()) + " " + serveradress.getPort();
/*     */                 return;
/*     */               } 
/* 467 */             } catch (Exception exception) {}
/* 468 */           }"PingThread-")).start();
/*     */ 
/*     */ 
/*     */       
/* 472 */       if (sd.pingToServer < 0L) {
/* 473 */         drawString(this.fontRendererObj, "§6Remote: §7Pinging...", this.width / 4 + 620, (Minecraft.getMinecraft()).displayHeight / 2 - 50, -1);
/*     */       } else {
/* 475 */         drawString(this.fontRendererObj, "§6Remote: §7" + this.addressPort.replace("null", ""), this.width / 4 + 575, (Minecraft.getMinecraft()).displayHeight / 2 - 50, -1);
/*     */       } 
/*     */       
/* 478 */       if (version.equalsIgnoreCase("1.12.2") && sd.pingToServer < 0L) {
/* 479 */         drawString(this.fontRendererObj, "§6Brand: §7Pinging...", this.width / 4 + 620, (Minecraft.getMinecraft()).displayHeight / 2 - 40, -1);
/*     */       } else {
/* 481 */         drawString(this.fontRendererObj, "§6Brand: §7" + version.replaceAll("1.8.x, 1.9.x, 1.10.x, 1.11.x, 1.12.x, 1.13.x, 1.14.x, 1.15.x", "1.8.x-1.15.x").replaceAll("1.7.x, ", "").replaceAll("PE-1.8.x, PE-1.9.x, PE-1.10.x, PE-1.11.x, PE-1.12.x, PE-1.13.x, PE-1.14.x, PE-1.15.x", "PE-1.8.x - PE-1.15.x"), this.width / 4 + 575, (Minecraft.getMinecraft()).displayHeight / 2 - 40, -1);
/*     */       } 
/*     */       
/* 484 */       if (sd.pingToServer < 0L) {
/* 485 */         drawString(this.fontRendererObj, "§6Protocol: §7Pinging...", this.width / 4 + 620, (Minecraft.getMinecraft()).displayHeight / 2 - 30, -1);
/*     */       } else {
/* 487 */         drawString(this.fontRendererObj, "§6Protocol: §7" + i + " -> " + ProtocolUtils.getInstance().getKnownAs(sd.version), this.width / 4 + 575, (Minecraft.getMinecraft()).displayHeight / 2 - 30, -1);
/*     */       } 
/*     */       
/* 490 */       if (sd.pingToServer < 0L) {
/* 491 */         drawString(this.fontRendererObj, "§6Last Ping: §7Pinging...", this.width / 4 + 620, (Minecraft.getMinecraft()).displayHeight / 2 - 20, -1);
/*     */       } else {
/* 493 */         drawString(this.fontRendererObj, "§6Last Ping: §7" + l + "ms", this.width / 4 + 575, (Minecraft.getMinecraft()).displayHeight / 2 - 20, -1);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 498 */       if (sd.pingToServer < 0L) {
/* 499 */         drawString(this.fontRendererObj, "§6AS: §7Pinging...", this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 50, -1);
/*     */       } else {
/* 501 */         drawString(this.fontRendererObj, "§6AS: §7" + GeoUtils.getInstance().getAS().replaceAll("Center ", "").replaceAll("oration", "").replaceAll("Waldecker trading as LUMASERV Systems", "").replaceAll("GmbH", "").replaceAll(". Inc.", "").replaceAll(", LLC", "").replaceAll("Corp.", "").replaceAll("TeleHost", "Tele").replaceAll("Services & Consulting", ""), this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 50, -1);
/*     */       } 
/*     */       
/* 504 */       if (sd.pingToServer < 0L) {
/* 505 */         drawString(this.fontRendererObj, "§6City: §7Pinging...", this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 40, -1);
/*     */       } else {
/* 507 */         drawString(this.fontRendererObj, "§6City: §7" + GeoUtils.getInstance().getCITY(), this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 40, -1);
/*     */       } 
/*     */       
/* 510 */       if (sd.pingToServer < 0L) {
/* 511 */         drawString(this.fontRendererObj, "§6ORG: §7Pinging...", this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 30, -1);
/*     */       } else {
/* 513 */         drawString(this.fontRendererObj, "§6ORG: §7" + GeoUtils.getInstance().getORG().replaceAll("- Connecting your World!", "").replaceAll("Cloud ", "").replaceAll("- DDoS-Protected Gameservers and more", "").replaceAll("www.", "").replaceAll("Corp.", "").replaceAll("sgesellschaft mbH", ""), this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 30, -1);
/*     */       } 
/*     */       
/* 516 */       if (sd.pingToServer < 0L) {
/* 517 */         drawString(this.fontRendererObj, "§6Country: §7Pinging...", this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 20, -1);
/*     */       } else {
/* 519 */         drawString(this.fontRendererObj, "§6Country: §7" + GeoUtils.getInstance().getCOUNTRY(), this.width / 4 - 230, (Minecraft.getMinecraft()).displayHeight / 2 - 20, -1);
/*     */       }
/*     */     
/*     */     }
/* 523 */     catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 530 */     if (this.hoveringText != null)
/*     */     {
/* 532 */       drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void connectToSelected() {
/* 538 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
/*     */     
/* 540 */     if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */       
/* 542 */       connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
/*     */     }
/* 544 */     else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
/*     */       
/* 546 */       LanServerInfo lanserverinfo = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getServerData();
/* 547 */       connectToServer(new ServerData(lanserverinfo.getServerMotd(), lanserverinfo.getServerIpPort(), true));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void connectToServer(ServerData server) {
/* 553 */     this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this, this.mc, server));
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectServer(int index) {
/* 558 */     this.serverListSelector.setSelectedSlotIndex(index);
/* 559 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (index < 0) ? null : this.serverListSelector.getListEntry(index);
/* 560 */     this.btnSelectServer.enabled = false;
/* 561 */     this.btnEditServer.enabled = false;
/* 562 */     this.btnDeleteServer.enabled = false;
/*     */     
/* 564 */     if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
/*     */       
/* 566 */       this.btnSelectServer.enabled = true;
/*     */       
/* 568 */       if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/*     */         
/* 570 */         this.btnEditServer.enabled = true;
/* 571 */         this.btnDeleteServer.enabled = true;
/*     */       } 
/*     */ 
/*     */       
/* 575 */       if (this.savedServerList.getServerData(index) instanceof me.nzxter.bettercraft.utils.ServerDataFeaturedUtils) {
/* 576 */         this.btnEditServer.enabled = false;
/* 577 */         this.btnDeleteServer.enabled = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerPinger getOldServerPinger() {
/* 585 */     return this.oldServerPinger;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHoveringText(String p_146793_1_) {
/* 590 */     this.hoveringText = p_146793_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 598 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 599 */     this.serverListSelector.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 607 */     super.mouseReleased(mouseX, mouseY, state);
/* 608 */     this.serverListSelector.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerList getServerList() {
/* 613 */     return this.savedServerList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMoveUp(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
/* 619 */     return (p_175392_2_ > this.savedServerList.getFeaturedServerCount());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMoveDown(ServerListEntryNormal p_175394_1_, int p_175394_2_) {
/* 625 */     return (p_175394_2_ < this.savedServerList.countServers() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveServerUp(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
/* 630 */     int i = p_175391_3_ ? 0 : (p_175391_2_ - 1);
/* 631 */     this.savedServerList.swapServers(p_175391_2_, i);
/*     */     
/* 633 */     if (this.serverListSelector.getSelected() == p_175391_2_)
/*     */     {
/* 635 */       selectServer(i);
/*     */     }
/*     */     
/* 638 */     this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveServerDown(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
/* 643 */     int i = p_175393_3_ ? (this.savedServerList.countServers() - 1) : (p_175393_2_ + 1);
/* 644 */     this.savedServerList.swapServers(p_175393_2_, i);
/*     */     
/* 646 */     if (this.serverListSelector.getSelected() == p_175393_2_)
/*     */     {
/* 648 */       selectServer(i);
/*     */     }
/*     */     
/* 651 */     this.serverListSelector.updateOnlineServers(this.savedServerList);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiMultiplayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */