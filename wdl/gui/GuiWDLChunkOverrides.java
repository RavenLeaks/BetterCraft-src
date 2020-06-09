/*     */ package wdl.gui;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import wdl.WDL;
/*     */ import wdl.WDLPluginChannels;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiWDLChunkOverrides
/*     */   extends GuiScreen
/*     */ {
/*     */   private static final int TOP_MARGIN = 61;
/*     */   private static final int BOTTOM_MARGIN = 32;
/*     */   private final GuiScreen parent;
/*     */   private GuiButton startDownloadButton;
/*     */   private float scrollX;
/*     */   private float scrollZ;
/*  31 */   private static final ResourceLocation WIDGET_TEXTURES = new ResourceLocation(
/*  32 */       "wdl:textures/permission_widgets.png");
/*     */   private static final int SCALE = 8;
/*     */   
/*  35 */   private enum Mode { PANNING(0, 128),
/*  36 */     REQUESTING(16, 128),
/*  37 */     ERASING(32, 128),
/*  38 */     MOVING(48, 128); public final int overlayU;
/*     */     
/*     */     Mode(int overlayU, int overlayV) {
/*  41 */       this.overlayU = overlayU;
/*  42 */       this.overlayV = overlayV;
/*     */     }
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
/*     */     public final int overlayV; }
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
/*  71 */   private Mode mode = Mode.PANNING;
/*     */   
/*     */   private boolean partiallyRequested;
/*     */   
/*     */   private int requestStartX;
/*     */   
/*     */   private int requestStartZ;
/*     */   
/*     */   private int requestEndX;
/*     */   
/*     */   private int requestEndZ;
/*     */   
/*     */   private boolean dragging;
/*     */   
/*     */   private int lastTickX;
/*     */   
/*     */   private int lastTickY;
/*     */   
/*     */   private static final int RNG_SEED = 769532;
/*     */   
/*     */   public GuiWDLChunkOverrides(GuiScreen parent) {
/*  92 */     this.parent = parent;
/*     */     
/*  94 */     if (WDL.thePlayer != null) {
/*  95 */       this.scrollX = WDL.thePlayer.chunkCoordX;
/*  96 */       this.scrollZ = WDL.thePlayer.chunkCoordZ;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 102 */     this.buttonList.add(new RequestModeButton(0, this.width / 2 - 155, 18, Mode.PANNING));
/* 103 */     this.buttonList.add(new RequestModeButton(1, this.width / 2 - 130, 18, Mode.REQUESTING));
/* 104 */     this.buttonList.add(new RequestModeButton(this, 2, this.width / 2 - 105, 18, Mode.ERASING) {  });
/* 105 */     this.buttonList.add(new RequestModeButton(this, 3, this.width / 2 - 80, 18, Mode.MOVING) {  }
/*     */       );
/* 107 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 80, 18, 80, 20, 
/* 108 */           "Send request"));
/*     */     
/* 110 */     this.startDownloadButton = new GuiButton(6, this.width / 2 + 5, 18, 150, 20, 
/* 111 */         "Start download in these ranges");
/* 112 */     this.startDownloadButton.enabled = WDLPluginChannels.canDownloadAtAll();
/* 113 */     this.buttonList.add(this.startDownloadButton);
/*     */     
/* 115 */     this.buttonList.add(new GuiButton(100, this.width / 2 - 100, this.height - 29, 
/* 116 */           I18n.format("gui.done", new Object[0])));
/*     */     
/* 118 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 155, 39, 100, 20, 
/* 119 */           I18n.format("wdl.gui.permissions.current", new Object[0])));
/* 120 */     this.buttonList.add(new GuiButton(201, this.width / 2 - 50, 39, 100, 20, 
/* 121 */           I18n.format("wdl.gui.permissions.request", new Object[0])));
/* 122 */     this.buttonList.add(new GuiButton(202, this.width / 2 + 55, 39, 100, 20, 
/* 123 */           I18n.format("wdl.gui.permissions.overrides", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 128 */     if (button.id == 0) {
/* 129 */       this.mode = Mode.PANNING;
/*     */     }
/* 131 */     if (button.id == 1) {
/* 132 */       this.mode = Mode.REQUESTING;
/* 133 */       this.partiallyRequested = false;
/*     */     } 
/* 135 */     if (button.id == 4) {
/* 136 */       WDLPluginChannels.sendRequests();
/*     */     }
/* 138 */     if (button.id == 6) {
/* 139 */       if (!WDLPluginChannels.canDownloadAtAll()) {
/* 140 */         button.enabled = false;
/*     */         return;
/*     */       } 
/* 143 */       WDL.startDownload();
/*     */     } 
/*     */     
/* 146 */     if (button.id == 100) {
/* 147 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */     
/* 150 */     if (button.id == 200) {
/* 151 */       this.mc.displayGuiScreen(new GuiWDLPermissions(this.parent));
/*     */     }
/* 153 */     if (button.id == 201) {
/* 154 */       this.mc.displayGuiScreen(new GuiWDLPermissionRequest(this.parent));
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
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 167 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 169 */     if (mouseY > 61 && mouseY < this.height - 32 && mouseButton == 0) {
/* 170 */       switch (this.mode) {
/*     */         case PANNING:
/* 172 */           this.dragging = true;
/* 173 */           this.lastTickX = mouseX;
/* 174 */           this.lastTickY = mouseY;
/*     */           break;
/*     */         case REQUESTING:
/* 177 */           if (this.partiallyRequested) {
/* 178 */             this.requestEndX = displayXToChunkX(mouseX);
/* 179 */             this.requestEndZ = displayZToChunkZ(mouseY);
/*     */             
/* 181 */             WDLPluginChannels.ChunkRange requestRange = new WDLPluginChannels.ChunkRange("", this.requestStartX, 
/* 182 */                 this.requestStartZ, this.requestEndX, this.requestEndZ);
/* 183 */             WDLPluginChannels.addChunkOverrideRequest(requestRange);
/*     */             
/* 185 */             this.partiallyRequested = false;
/*     */           } else {
/* 187 */             this.requestStartX = displayXToChunkX(mouseX);
/* 188 */             this.requestStartZ = displayZToChunkZ(mouseY);
/*     */             
/* 190 */             this.partiallyRequested = true;
/*     */           } 
/*     */           
/* 193 */           this.mc.getSoundHandler().playSound(
/* 194 */               (ISound)PositionedSoundRecord.getMasterRecord(
/* 195 */                 SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 203 */     super.mouseReleased(mouseX, mouseY, state);
/* 204 */     if (state == 0) {
/* 205 */       this.dragging = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 212 */     if (this.dragging) {
/* 213 */       int deltaX = this.lastTickX - mouseX;
/* 214 */       int deltaY = this.lastTickY - mouseY;
/*     */       
/* 216 */       this.lastTickX = mouseX;
/* 217 */       this.lastTickY = mouseY;
/*     */       
/* 219 */       this.scrollX += deltaX / 8.0F;
/* 220 */       this.scrollZ += deltaY / 8.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 226 */     Utils.drawDarkBackground(0, 0, this.height, this.width);
/*     */ 
/*     */     
/* 229 */     if (this.mode == Mode.REQUESTING) {
/* 230 */       int x1 = this.partiallyRequested ? this.requestStartX : displayXToChunkX(mouseX);
/* 231 */       int z1 = this.partiallyRequested ? this.requestStartZ : displayZToChunkZ(mouseY);
/* 232 */       int x2 = displayXToChunkX(mouseX);
/* 233 */       int z2 = displayZToChunkZ(mouseY);
/*     */ 
/*     */       
/* 236 */       WDLPluginChannels.ChunkRange requestRange = new WDLPluginChannels.ChunkRange("", x1, z1, x2, z2);
/*     */ 
/*     */       
/* 239 */       int alpha = 127 + (int)(Math.sin(Minecraft.getSystemTime() * Math.PI / 5000.0D) * 64.0D);
/* 240 */       drawRange(requestRange, 16777215, alpha);
/*     */     } 
/*     */ 
/*     */     
/* 244 */     for (Multimap<String, WDLPluginChannels.ChunkRange> group : (Iterable<Multimap<String, WDLPluginChannels.ChunkRange>>)WDLPluginChannels.getChunkOverrides().values()) {
/* 245 */       for (WDLPluginChannels.ChunkRange range : group.values()) {
/* 246 */         drawRange(range, 769532, 255);
/*     */       }
/*     */     } 
/* 249 */     for (WDLPluginChannels.ChunkRange range : WDLPluginChannels.getChunkOverrideRequests()) {
/*     */       
/* 251 */       int alpha = 127 + (int)(Math.sin(Minecraft.getSystemTime() * Math.PI / 5000.0D) * 64.0D);
/* 252 */       drawRange(range, 8421504, alpha);
/*     */     } 
/*     */ 
/*     */     
/* 256 */     int playerPosX = (int)((WDL.thePlayer.posX / 16.0D - this.scrollX) * 8.0D + (this.width / 2));
/* 257 */     int playerPosZ = (int)((WDL.thePlayer.posZ / 16.0D - this.scrollZ) * 8.0D + (this.height / 2));
/*     */     
/* 259 */     drawHorizontalLine(playerPosX - 3, playerPosX + 3, playerPosZ, -1);
/*     */     
/* 261 */     drawVerticalLine(playerPosX, playerPosZ - 4, playerPosZ + 4, -1);
/*     */ 
/*     */     
/* 264 */     Utils.drawBorder(61, 32, 0, 0, this.height, this.width);
/*     */     
/* 266 */     drawCenteredString(this.fontRendererObj, "Chunk overrides", 
/* 267 */         this.width / 2, 8, 16777215);
/*     */     
/* 269 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 271 */     drawCenteredString(this.fontRendererObj, "§c§lThis is a work in progress.", 
/* 272 */         this.width / 2, this.height / 2, 16777215);
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
/*     */   private void drawRange(WDLPluginChannels.ChunkRange range, int seed, int alpha) {
/* 291 */     int color = (range.tag.hashCode() ^ seed) & 0xFFFFFF;
/*     */     
/* 293 */     int x1 = chunkXToDisplayX(range.x1);
/* 294 */     int z1 = chunkZToDisplayZ(range.z1);
/* 295 */     int x2 = chunkXToDisplayX(range.x2) + 8 - 1;
/* 296 */     int z2 = chunkZToDisplayZ(range.z2) + 8 - 1;
/*     */     
/* 298 */     drawRect(x1, z1, x2, z2, color + (alpha << 24));
/*     */     
/* 300 */     int colorDark = darken(color);
/*     */     
/* 302 */     drawVerticalLine(x1, z1, z2, colorDark + (alpha << 24));
/* 303 */     drawVerticalLine(x2, z1, z2, colorDark + (alpha << 24));
/* 304 */     drawHorizontalLine(x1, x2, z1, colorDark + (alpha << 24));
/* 305 */     drawHorizontalLine(x1, x2, z2, colorDark + (alpha << 24));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int chunkXToDisplayX(int chunkX) {
/* 316 */     return (int)((chunkX - this.scrollX) * 8.0F + (this.width / 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int chunkZToDisplayZ(int chunkZ) {
/* 327 */     return (int)((chunkZ - this.scrollZ) * 8.0F + (this.height / 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int displayXToChunkX(int displayX) {
/* 338 */     return MathHelper.floor((displayX - (this.width / 2)) / 8.0F + this.scrollX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int displayZToChunkZ(int displayZ) {
/* 349 */     return MathHelper.floor((displayZ - (this.height / 2)) / 8.0F + this.scrollZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int darken(int color) {
/* 356 */     int r = color >> 16 & 0xFF;
/* 357 */     int g = color >> 8 & 0xFF;
/* 358 */     int b = color & 0xFF;
/*     */     
/* 360 */     r /= 2;
/* 361 */     g /= 2;
/* 362 */     b /= 2;
/*     */     
/* 364 */     return (r << 16) + (g << 8) + b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class RequestModeButton
/*     */     extends GuiButton
/*     */   {
/*     */     public final GuiWDLChunkOverrides.Mode mode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RequestModeButton(int buttonId, int x, int y, GuiWDLChunkOverrides.Mode mode) {
/* 384 */       super(buttonId, x, y, 20, 20, "");
/* 385 */       this.mode = mode;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float p_191745_4_) {
/* 390 */       if (GuiWDLChunkOverrides.this.mode == this.mode)
/*     */       {
/* 392 */         drawRect(this.xPosition - 2, this.yPosition - 2, 
/* 393 */             this.xPosition + this.width + 2, this.yPosition + this.height + 2, 
/* 394 */             -16744704);
/*     */       }
/*     */       
/* 397 */       super.func_191745_a(mc, mouseX, mouseY, p_191745_4_);
/*     */ 
/*     */       
/* 400 */       GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 401 */       mc.getTextureManager().bindTexture(GuiWDLChunkOverrides.WIDGET_TEXTURES);
/*     */       
/* 403 */       drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, 
/* 404 */           this.mode.overlayU, this.mode.overlayV, 16, 16);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\gui\GuiWDLChunkOverrides.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */