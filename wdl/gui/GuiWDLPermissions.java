/*     */ package wdl.gui;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLMessageTypes;
/*     */ import wdl.WDLMessages;
/*     */ import wdl.WDLPluginChannels;
/*     */ import wdl.api.IWDLMessageType;
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
/*     */ public class GuiWDLPermissions
/*     */   extends GuiScreen
/*     */ {
/*     */   private static final int TOP_MARGIN = 61;
/*     */   private static final int BOTTOM_MARGIN = 32;
/*     */   private GuiButton reloadButton;
/*  37 */   private int refreshTicks = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final GuiScreen parent;
/*     */ 
/*     */ 
/*     */   
/*     */   private TextList list;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiWDLPermissions(GuiScreen parent) {
/*  52 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  57 */     this.buttonList.clear();
/*     */     
/*  59 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 29, 
/*  60 */           I18n.format("gui.done", new Object[0])));
/*     */     
/*  62 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 155, 39, 100, 20, 
/*  63 */           I18n.format("wdl.gui.permissions.current", new Object[0])));
/*  64 */     if (WDLPluginChannels.canRequestPermissions()) {
/*  65 */       this.buttonList.add(new GuiButton(201, this.width / 2 - 50, 39, 100, 20, 
/*  66 */             I18n.format("wdl.gui.permissions.request", new Object[0])));
/*  67 */       this.buttonList.add(new GuiButton(202, this.width / 2 + 55, 39, 100, 20, 
/*  68 */             I18n.format("wdl.gui.permissions.overrides", new Object[0])));
/*     */     } 
/*     */     
/*  71 */     this.reloadButton = new GuiButton(1, this.width / 2 + 5, 18, 150, 20, 
/*  72 */         "Reload permissions");
/*  73 */     this.buttonList.add(this.reloadButton);
/*     */     
/*  75 */     this.list = new TextList(this.mc, this.width, this.height, 61, 32);
/*     */     
/*  77 */     this.list.addLine("§c§lThis is a work in progress.");
/*     */     
/*  79 */     if (!WDLPluginChannels.hasPermissions()) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     this.list.addBlankLine();
/*  84 */     if (!WDLPluginChannels.canRequestPermissions()) {
/*  85 */       this.list.addLine("§cThe serverside permission plugin is out of date and does support permission requests.  Please go ask a server administrator to update the plugin.");
/*     */ 
/*     */       
/*  88 */       this.list.addBlankLine();
/*     */     } 
/*     */     
/*  91 */     if (WDLPluginChannels.getRequestMessage() != null) {
/*  92 */       this.list.addLine("Note from the server moderators: ");
/*  93 */       this.list.addLine(WDLPluginChannels.getRequestMessage());
/*  94 */       this.list.addBlankLine();
/*     */     } 
/*     */     
/*  97 */     this.list.addLine("These are your current permissions:");
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.list.addLine("Can download: " + 
/* 102 */         WDLPluginChannels.canDownloadInGeneral());
/* 103 */     this.list.addLine("Can save chunks as you move: " + WDLPluginChannels.canCacheChunks());
/* 104 */     if (!WDLPluginChannels.canCacheChunks() && WDLPluginChannels.canDownloadInGeneral()) {
/* 105 */       this.list.addLine("Nearby chunk save radius: " + WDLPluginChannels.getSaveRadius());
/*     */     }
/* 107 */     this.list.addLine("Can save entities: " + 
/* 108 */         WDLPluginChannels.canSaveEntities());
/* 109 */     this.list.addLine("Can save tile entities: " + 
/* 110 */         WDLPluginChannels.canSaveTileEntities());
/* 111 */     this.list.addLine("Can save containers: " + 
/* 112 */         WDLPluginChannels.canSaveContainers());
/* 113 */     this.list.addLine("Received entity ranges: " + 
/* 114 */         WDLPluginChannels.hasServerEntityRange() + " (" + 
/* 115 */         WDLPluginChannels.getEntityRanges().size() + " total)");
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 120 */     if (this.refreshTicks > 0) {
/* 121 */       this.refreshTicks--;
/* 122 */     } else if (this.refreshTicks == 0) {
/* 123 */       initGui();
/* 124 */       this.refreshTicks = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 130 */     WDL.saveProps();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 139 */     this.list.mouseClicked(mouseX, mouseY, mouseButton);
/* 140 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 148 */     super.handleMouseInput();
/* 149 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 154 */     if (this.list.mouseReleased(mouseX, mouseY, state)) {
/*     */       return;
/*     */     }
/* 157 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 162 */     if (button.id == 1) {
/*     */       CPacketCustomPayload initPacket;
/*     */       
/*     */       try {
/* 166 */         initPacket = new CPacketCustomPayload("WDL|INIT", 
/* 167 */             new PacketBuffer(Unpooled.copiedBuffer("1.11a-beta1"
/* 168 */                 .getBytes("UTF-8"))));
/* 169 */       } catch (UnsupportedEncodingException e) {
/* 170 */         WDLMessages.chatMessageTranslated((IWDLMessageType)WDLMessageTypes.ERROR, 
/* 171 */             "wdl.messages.generalError.noUTF8", new Object[0]);
/*     */         
/* 173 */         initPacket = new CPacketCustomPayload("WDL|INIT", 
/* 174 */             new PacketBuffer(Unpooled.buffer()));
/*     */       } 
/* 176 */       WDL.minecraft.getConnection().sendPacket((Packet)initPacket);
/*     */       
/* 178 */       button.enabled = false;
/* 179 */       button.displayString = "Refershing...";
/*     */       
/* 181 */       this.refreshTicks = 50;
/*     */     } 
/* 183 */     if (button.id == 100) {
/* 184 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (button.id == 201) {
/* 190 */       this.mc.displayGuiScreen(new GuiWDLPermissionRequest(this.parent));
/*     */     }
/* 192 */     if (button.id == 202) {
/* 193 */       this.mc.displayGuiScreen(new GuiWDLChunkOverrides(this.parent));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 199 */     if (this.list == null) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 205 */     drawCenteredString(this.fontRendererObj, "Permission info", 
/* 206 */         this.width / 2, 8, 16777215);
/*     */     
/* 208 */     if (!WDLPluginChannels.hasPermissions()) {
/* 209 */       drawCenteredString(this.fontRendererObj, 
/* 210 */           "No permissions received; defaulting to everything enabled.", 
/* 211 */           this.width / 2, (this.height - 32 - 23) / 2 + 23 - 
/* 212 */           this.fontRendererObj.FONT_HEIGHT / 2, 16777215);
/*     */     }
/*     */     
/* 215 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLPermissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */