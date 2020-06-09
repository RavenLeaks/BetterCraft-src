/*     */ package wdl;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiIngameMenu;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.network.play.server.SPacketBlockAction;
/*     */ import net.minecraft.network.play.server.SPacketChat;
/*     */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*     */ import net.minecraft.network.play.server.SPacketMaps;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import wdl.api.IBlockEventListener;
/*     */ import wdl.api.IChatMessageListener;
/*     */ import wdl.api.IGuiHooksListener;
/*     */ import wdl.api.WDLApi;
/*     */ import wdl.gui.GuiWDL;
/*     */ import wdl.gui.GuiWDLAbout;
/*     */ import wdl.gui.GuiWDLChunkOverrides;
/*     */ import wdl.gui.GuiWDLPermissions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLHooks
/*     */ {
/*  44 */   private static final Profiler profiler = (Minecraft.getMinecraft()).mcProfiler;
/*     */   
/*     */   private static final int WDLs = 1464093811;
/*     */   
/*     */   private static final int WDLo = 1464093807;
/*     */ 
/*     */   
/*     */   public static void onWorldClientTick(WorldClient sender) {
/*     */     try {
/*  53 */       profiler.startSection("wdl");
/*     */       
/*  55 */       ImmutableList immutableList = ImmutableList.copyOf(sender.playerEntities);
/*     */       
/*  57 */       if (sender != WDL.worldClient) {
/*  58 */         profiler.startSection("onWorldLoad");
/*  59 */         if (WDL.worldLoadingDeferred) {
/*     */           return;
/*     */         }
/*     */         
/*  63 */         WDLEvents.onWorldLoad(sender);
/*  64 */         profiler.endSection();
/*     */       } else {
/*  66 */         profiler.startSection("inventoryCheck");
/*  67 */         if (WDL.downloading && WDL.thePlayer != null && 
/*  68 */           WDL.thePlayer.openContainer != WDL.windowContainer) {
/*  69 */           if (WDL.thePlayer.openContainer == WDL.thePlayer.inventoryContainer) {
/*     */ 
/*     */             
/*  72 */             profiler.startSection("onItemGuiClosed");
/*  73 */             profiler.startSection("Core");
/*  74 */             boolean handled = WDLEvents.onItemGuiClosed();
/*  75 */             profiler.endSection();
/*     */             
/*  77 */             Container container = WDL.thePlayer.openContainer;
/*  78 */             if (WDL.lastEntity != null) {
/*  79 */               Entity entity = WDL.lastEntity;
/*     */ 
/*     */               
/*  82 */               Iterator<WDLApi.ModInfo<IGuiHooksListener>> iterator = WDLApi.getImplementingExtensions(IGuiHooksListener.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IGuiHooksListener> info = iterator.next();
/*  83 */                 if (handled) {
/*     */                   break;
/*     */                 }
/*     */                 
/*  87 */                 profiler.startSection(info.id);
/*  88 */                 handled = ((IGuiHooksListener)info.mod).onEntityGuiClosed(
/*  89 */                     sender, entity, container);
/*  90 */                 profiler.endSection(); }
/*     */ 
/*     */               
/*  93 */               if (!handled)
/*  94 */                 WDLMessages.chatMessageTranslated(
/*  95 */                     WDLMessageTypes.ON_GUI_CLOSED_WARNING, 
/*  96 */                     "wdl.messages.onGuiClosedWarning.unhandledEntity", new Object[] {
/*  97 */                       entity
/*     */                     }); 
/*     */             } else {
/* 100 */               BlockPos pos = WDL.lastClickedBlock;
/*     */               
/* 102 */               Iterator<WDLApi.ModInfo<IGuiHooksListener>> iterator = WDLApi.getImplementingExtensions(IGuiHooksListener.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IGuiHooksListener> info = iterator.next();
/* 103 */                 if (handled) {
/*     */                   break;
/*     */                 }
/*     */                 
/* 107 */                 profiler.startSection(info.id);
/* 108 */                 handled = ((IGuiHooksListener)info.mod).onBlockGuiClosed(
/* 109 */                     sender, pos, container);
/* 110 */                 profiler.endSection(); }
/*     */ 
/*     */               
/* 113 */               if (!handled) {
/* 114 */                 WDLMessages.chatMessageTranslated(
/* 115 */                     WDLMessageTypes.ON_GUI_CLOSED_WARNING, 
/* 116 */                     "wdl.messages.onGuiClosedWarning.unhandledTileEntity", new Object[] {
/* 117 */                       pos, sender.getTileEntity(pos)
/*     */                     });
/*     */               }
/*     */             } 
/* 121 */             profiler.endSection();
/*     */           } else {
/* 123 */             profiler.startSection("onItemGuiOpened");
/* 124 */             profiler.startSection("Core");
/* 125 */             WDLEvents.onItemGuiOpened();
/* 126 */             profiler.endSection();
/* 127 */             profiler.endSection();
/*     */           } 
/*     */           
/* 130 */           WDL.windowContainer = WDL.thePlayer.openContainer;
/*     */         } 
/*     */         
/* 133 */         profiler.endSection();
/*     */       } 
/*     */       
/* 136 */       profiler.startSection("capes");
/* 137 */       CapeHandler.onWorldTick((List<EntityPlayer>)immutableList);
/* 138 */       profiler.endSection();
/* 139 */       profiler.endSection();
/* 140 */     } catch (Throwable e) {
/* 141 */       WDL.crashed(e, "WDL mod: exception in onWorldClientTick event");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onWorldClientDoPreChunk(WorldClient sender, int x, int z, boolean loading) {
/*     */     try {
/* 153 */       if (!WDL.downloading)
/*     */         return; 
/* 155 */       profiler.startSection("wdl");
/*     */       
/* 157 */       if (!loading) {
/* 158 */         profiler.startSection("onChunkNoLongerNeeded");
/* 159 */         Chunk c = sender.getChunkFromChunkCoords(x, z);
/*     */         
/* 161 */         profiler.startSection("Core");
/* 162 */         WDLEvents.onChunkNoLongerNeeded(c);
/* 163 */         profiler.endSection();
/*     */         
/* 165 */         profiler.endSection();
/*     */       } 
/*     */       
/* 168 */       profiler.endSection();
/* 169 */     } catch (Throwable e) {
/* 170 */       WDL.crashed(e, "WDL mod: exception in onWorldDoPreChunk event");
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
/*     */   
/*     */   public static void onWorldClientRemoveEntityFromWorld(WorldClient sender, int eid) {
/*     */     try {
/* 185 */       if (!WDL.downloading)
/*     */         return; 
/* 187 */       profiler.startSection("wdl.onRemoveEntityFromWorld");
/*     */       
/* 189 */       Entity entity = sender.getEntityByID(eid);
/*     */       
/* 191 */       profiler.startSection("Core");
/* 192 */       WDLEvents.onRemoveEntityFromWorld(entity);
/* 193 */       profiler.endSection();
/*     */       
/* 195 */       profiler.endSection();
/* 196 */     } catch (Throwable e) {
/* 197 */       WDL.crashed(e, 
/* 198 */           "WDL mod: exception in onWorldRemoveEntityFromWorld event");
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
/*     */   public static void onNHPCHandleChat(NetHandlerPlayClient sender, SPacketChat packet) {
/*     */     try {
/* 211 */       if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */         return;
/*     */       }
/*     */       
/* 215 */       if (!WDL.downloading)
/*     */         return; 
/* 217 */       profiler.startSection("wdl.onChatMessage");
/*     */ 
/*     */       
/* 220 */       String chatMessage = packet.getChatComponent().getUnformattedText();
/*     */       
/* 222 */       profiler.startSection("Core");
/* 223 */       WDLEvents.onChatMessage(chatMessage);
/* 224 */       profiler.endSection();
/*     */ 
/*     */       
/* 227 */       Iterator<WDLApi.ModInfo<IChatMessageListener>> iterator = WDLApi.getImplementingExtensions(IChatMessageListener.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IChatMessageListener> info = iterator.next();
/* 228 */         profiler.startSection(info.id);
/* 229 */         ((IChatMessageListener)info.mod).onChat(WDL.worldClient, chatMessage);
/* 230 */         profiler.endSection(); }
/*     */ 
/*     */       
/* 233 */       profiler.endSection();
/* 234 */     } catch (Throwable e) {
/* 235 */       WDL.crashed(e, "WDL mod: exception in onNHPCHandleChat event");
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
/*     */   public static void onNHPCHandleMaps(NetHandlerPlayClient sender, SPacketMaps packet) {
/*     */     try {
/* 248 */       if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */         return;
/*     */       }
/*     */       
/* 252 */       if (!WDL.downloading)
/*     */         return; 
/* 254 */       profiler.startSection("wdl.onMapDataLoaded");
/*     */       
/* 256 */       int id = packet.getMapId();
/* 257 */       MapData mapData = ItemMap.loadMapData(packet.getMapId(), 
/* 258 */           (World)WDL.worldClient);
/*     */       
/* 260 */       profiler.startSection("Core");
/* 261 */       WDLEvents.onMapDataLoaded(id, mapData);
/* 262 */       profiler.endSection();
/*     */       
/* 264 */       profiler.endSection();
/* 265 */     } catch (Throwable e) {
/* 266 */       WDL.crashed(e, "WDL mod: exception in onNHPCHandleMaps event");
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
/*     */   
/*     */   public static void onNHPCHandleCustomPayload(NetHandlerPlayClient sender, SPacketCustomPayload packet) {}
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
/*     */   public static void onNHPCHandleBlockAction(NetHandlerPlayClient sender, SPacketBlockAction packet) {
/*     */     try {
/* 292 */       if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */         return;
/*     */       }
/*     */       
/* 296 */       if (!WDL.downloading)
/*     */         return; 
/* 298 */       profiler.startSection("wdl.onBlockEvent");
/*     */       
/* 300 */       BlockPos pos = packet.getBlockPosition();
/* 301 */       Block block = packet.getBlockType();
/* 302 */       int data1 = packet.getData1();
/* 303 */       int data2 = packet.getData2();
/*     */       
/* 305 */       profiler.startSection("Core");
/* 306 */       WDLEvents.onBlockEvent(pos, block, data1, data2);
/* 307 */       profiler.endSection();
/*     */ 
/*     */       
/* 310 */       Iterator<WDLApi.ModInfo<IBlockEventListener>> iterator = WDLApi.getImplementingExtensions(IBlockEventListener.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IBlockEventListener> info = iterator.next();
/* 311 */         profiler.startSection(info.id);
/* 312 */         ((IBlockEventListener)info.mod).onBlockEvent(WDL.worldClient, pos, block, 
/* 313 */             data1, data2);
/* 314 */         profiler.endSection(); }
/*     */ 
/*     */       
/* 317 */       profiler.endSection();
/* 318 */     } catch (Throwable e) {
/* 319 */       WDL.crashed(e, 
/* 320 */           "WDL mod: exception in onNHPCHandleBlockAction event");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onCrashReportPopulateEnvironment(CrashReport report) {
/* 331 */     report.makeCategory("World Downloader Mod").setDetail("Info", 
/* 332 */         new ICrashReportDetail<String>() {
/*     */           public String call() {
/* 334 */             return WDL.getDebugInfo();
/*     */           }
/*     */         });
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
/*     */   public static void injectWDLButtons(GuiIngameMenu gui, List<GuiButton> buttonList) {
/* 358 */     int insertAtYPos = 0;
/*     */     
/* 360 */     for (Object obj : buttonList) {
/* 361 */       GuiButton btn = (GuiButton)obj;
/*     */       
/* 363 */       if (btn.id == 5) {
/* 364 */         insertAtYPos = btn.yPosition + 24;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     for (Object obj : buttonList) {
/* 371 */       GuiButton btn = (GuiButton)obj;
/*     */       
/* 373 */       if (btn.yPosition >= insertAtYPos) {
/* 374 */         btn.yPosition += 24;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 379 */     GuiButton wdlDownload = new GuiButton(1464093811, gui.width / 2 - 100, 
/* 380 */         insertAtYPos, 170, 20, null);
/*     */     
/* 382 */     GuiButton wdlOptions = new GuiButton(1464093807, gui.width / 2 + 71, 
/* 383 */         insertAtYPos, 28, 20, 
/* 384 */         I18n.format("wdl.gui.ingameMenu.settings", new Object[0]));
/*     */     
/* 386 */     if (WDL.minecraft.isIntegratedServerRunning()) {
/* 387 */       wdlDownload.displayString = 
/* 388 */         I18n.format("wdl.gui.ingameMenu.downloadStatus.singlePlayer", new Object[0]);
/* 389 */       wdlDownload.enabled = false;
/* 390 */     } else if (!WDLPluginChannels.canDownloadAtAll()) {
/* 391 */       if (WDLPluginChannels.canRequestPermissions()) {
/*     */         
/* 393 */         wdlDownload.displayString = 
/* 394 */           I18n.format("wdl.gui.ingameMenu.downloadStatus.request", new Object[0]);
/*     */       } else {
/*     */         
/* 397 */         wdlDownload.displayString = 
/* 398 */           I18n.format("wdl.gui.ingameMenu.downloadStatus.disabled", new Object[0]);
/* 399 */         wdlDownload.enabled = false;
/*     */       } 
/* 401 */     } else if (WDL.saving) {
/* 402 */       wdlDownload.displayString = 
/* 403 */         I18n.format("wdl.gui.ingameMenu.downloadStatus.saving", new Object[0]);
/* 404 */       wdlDownload.enabled = false;
/* 405 */       wdlOptions.enabled = false;
/* 406 */     } else if (WDL.downloading) {
/* 407 */       wdlDownload.displayString = 
/* 408 */         I18n.format("wdl.gui.ingameMenu.downloadStatus.stop", new Object[0]);
/*     */     } else {
/* 410 */       wdlDownload.displayString = 
/* 411 */         I18n.format("wdl.gui.ingameMenu.downloadStatus.start", new Object[0]);
/*     */     } 
/* 413 */     buttonList.add(wdlDownload);
/* 414 */     buttonList.add(wdlOptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void handleWDLButtonClick(GuiIngameMenu gui, GuiButton button) {
/* 424 */     if (!button.enabled) {
/*     */       return;
/*     */     }
/*     */     
/* 428 */     if (button.id == 1464093811) {
/* 429 */       if (WDL.minecraft.isIntegratedServerRunning()) {
/*     */         return;
/*     */       }
/*     */       
/* 433 */       if (WDL.downloading) {
/* 434 */         WDL.stopDownload();
/*     */       } else {
/* 436 */         if (!WDLPluginChannels.canDownloadAtAll()) {
/*     */ 
/*     */           
/* 439 */           if (WDLPluginChannels.canRequestPermissions()) {
/* 440 */             WDL.minecraft.displayGuiScreen((GuiScreen)new GuiWDLPermissions((GuiScreen)gui));
/*     */           } else {
/* 442 */             button.enabled = false;
/*     */           } 
/*     */           return;
/*     */         } 
/* 446 */         if (WDLPluginChannels.hasChunkOverrides() && 
/* 447 */           !WDLPluginChannels.canDownloadInGeneral()) {
/*     */ 
/*     */           
/* 450 */           WDL.minecraft.displayGuiScreen((GuiScreen)new GuiWDLChunkOverrides((GuiScreen)gui));
/*     */         } else {
/* 452 */           WDL.startDownload();
/*     */         } 
/*     */       } 
/* 455 */     } else if (button.id == 1464093807) {
/* 456 */       if (WDL.minecraft.isIntegratedServerRunning()) {
/* 457 */         WDL.minecraft.displayGuiScreen((GuiScreen)new GuiWDLAbout((GuiScreen)gui));
/*     */       } else {
/* 459 */         WDL.minecraft.displayGuiScreen((GuiScreen)new GuiWDL((GuiScreen)gui));
/*     */       } 
/* 461 */     } else if (button.id == 1) {
/* 462 */       WDL.stopDownload();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDLHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */