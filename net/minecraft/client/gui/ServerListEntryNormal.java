/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.InputStream;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import me.nzxter.bettercraft.utils.ServerDataFeaturedUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*  31 */   private static final ThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());
/*  32 */   private static final ResourceLocation UNKNOWN_SERVER = new ResourceLocation("textures/misc/unknown_server.png");
/*  33 */   private static final ResourceLocation SERVER_SELECTION_BUTTONS = new ResourceLocation("textures/gui/server_selection.png");
/*     */   
/*     */   private final GuiMultiplayer owner;
/*     */   private final Minecraft mc;
/*     */   private final ServerData server;
/*     */   private final ResourceLocation serverIcon;
/*     */   private String lastIconB64;
/*     */   private DynamicTexture icon;
/*     */   private long lastClickTime;
/*     */   
/*     */   protected ServerListEntryNormal(GuiMultiplayer p_i45048_1_, ServerData serverIn) {
/*  44 */     this.owner = p_i45048_1_;
/*  45 */     this.server = serverIn;
/*  46 */     this.mc = Minecraft.getMinecraft();
/*  47 */     this.serverIcon = new ResourceLocation("servers/" + serverIn.serverIP + "/icon");
/*  48 */     this.icon = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
/*     */   }
/*     */   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/*     */     int l;
/*     */     String s1;
/*  53 */     if (!this.server.pinged) {
/*     */       
/*  55 */       this.server.pinged = true;
/*  56 */       this.server.pingToServer = -2L;
/*  57 */       this.server.serverMOTD = "";
/*  58 */       this.server.populationInfo = "";
/*  59 */       EXECUTOR.submit(new Runnable()
/*     */           {
/*     */             
/*     */             public void run()
/*     */             {
/*     */               try {
/*  65 */                 ServerListEntryNormal.this.owner.getOldServerPinger().ping(ServerListEntryNormal.this.server);
/*     */               }
/*  67 */               catch (UnknownHostException var2) {
/*     */                 
/*  69 */                 ServerListEntryNormal.this.server.pingToServer = -1L;
/*  70 */                 ServerListEntryNormal.this.server.serverMOTD = TextFormatting.DARK_RED + I18n.format("multiplayer.status.cannot_resolve", new Object[0]);
/*     */               }
/*  72 */               catch (Exception var3) {
/*     */                 
/*  74 */                 ServerListEntryNormal.this.server.pingToServer = -1L;
/*  75 */                 ServerListEntryNormal.this.server.serverMOTD = TextFormatting.DARK_RED + I18n.format("multiplayer.status.cannot_connect", new Object[0]);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/*  82 */     boolean isFeaturedServer = this.server instanceof ServerDataFeaturedUtils;
/*  83 */     if (isFeaturedServer) {
/*  84 */       drawImg(p_192634_2_ - 5, p_192634_3_, false, ServerDataFeaturedUtils.STAR_ICON);
/*     */     }
/*     */ 
/*     */     
/*  88 */     boolean flag = (this.server.version > 340);
/*  89 */     boolean flag1 = (this.server.version < 340);
/*  90 */     boolean flag2 = !(!flag && !flag1);
/*  91 */     this.mc.fontRendererObj.drawString(this.server.serverName, p_192634_2_ + 32 + 3, p_192634_3_ + 1, 16777215);
/*  92 */     List<String> list = this.mc.fontRendererObj.listFormattedStringToWidth(this.server.serverMOTD, p_192634_4_ - 32 - 2);
/*     */     
/*  94 */     for (int i = 0; i < Math.min(list.size(), 2); i++)
/*     */     {
/*  96 */       this.mc.fontRendererObj.drawString(list.get(i), p_192634_2_ + 32 + 3, p_192634_3_ + 12 + this.mc.fontRendererObj.FONT_HEIGHT * i, 8421504);
/*     */     }
/*     */     
/*  99 */     String s2 = flag2 ? (TextFormatting.DARK_RED + this.server.gameVersion) : this.server.populationInfo;
/* 100 */     int j = this.mc.fontRendererObj.getStringWidth(s2);
/* 101 */     this.mc.fontRendererObj.drawString(s2, p_192634_2_ + p_192634_4_ - j - 15 - 2, p_192634_3_ + 1, 8421504);
/* 102 */     int k = 0;
/* 103 */     String s = null;
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (flag2) {
/*     */       
/* 109 */       l = 5;
/* 110 */       s1 = I18n.format(flag ? "multiplayer.status.client_out_of_date" : "multiplayer.status.server_out_of_date", new Object[0]);
/* 111 */       s = this.server.playerList;
/*     */     }
/* 113 */     else if (this.server.pinged && this.server.pingToServer != -2L) {
/*     */       
/* 115 */       if (this.server.pingToServer < 0L) {
/*     */         
/* 117 */         l = 5;
/*     */       }
/* 119 */       else if (this.server.pingToServer < 150L) {
/*     */         
/* 121 */         l = 0;
/*     */       }
/* 123 */       else if (this.server.pingToServer < 300L) {
/*     */         
/* 125 */         l = 1;
/*     */       }
/* 127 */       else if (this.server.pingToServer < 600L) {
/*     */         
/* 129 */         l = 2;
/*     */       }
/* 131 */       else if (this.server.pingToServer < 1000L) {
/*     */         
/* 133 */         l = 3;
/*     */       }
/*     */       else {
/*     */         
/* 137 */         l = 4;
/*     */       } 
/*     */       
/* 140 */       if (this.server.pingToServer < 0L)
/*     */       {
/* 142 */         s1 = I18n.format("multiplayer.status.no_connection", new Object[0]);
/*     */       }
/*     */       else
/*     */       {
/* 146 */         s1 = String.valueOf(this.server.pingToServer) + "ms";
/* 147 */         s = this.server.playerList;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 152 */       k = 1;
/* 153 */       l = (int)(Minecraft.getSystemTime() / 100L + (p_192634_1_ * 2) & 0x7L);
/*     */       
/* 155 */       if (l > 4)
/*     */       {
/* 157 */         l = 8 - l;
/*     */       }
/*     */       
/* 160 */       s1 = I18n.format("multiplayer.status.pinging", new Object[0]);
/*     */     } 
/*     */     
/* 163 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 164 */     this.mc.getTextureManager().bindTexture(Gui.ICONS);
/* 165 */     Gui.drawModalRectWithCustomSizedTexture(p_192634_2_ + p_192634_4_ - 15, p_192634_3_, (k * 10), (176 + l * 8), 10, 8, 256.0F, 256.0F);
/*     */     
/* 167 */     if (this.server.getBase64EncodedIconData() != null && !this.server.getBase64EncodedIconData().equals(this.lastIconB64)) {
/*     */       
/* 169 */       this.lastIconB64 = this.server.getBase64EncodedIconData();
/* 170 */       prepareServerIcon();
/* 171 */       this.owner.getServerList().saveServerList();
/*     */     } 
/*     */     
/* 174 */     if (this.icon != null) {
/*     */       
/* 176 */       drawTextureAt(p_192634_2_, p_192634_3_, this.serverIcon);
/*     */     }
/*     */     else {
/*     */       
/* 180 */       drawTextureAt(p_192634_2_, p_192634_3_, UNKNOWN_SERVER);
/*     */     } 
/*     */     
/* 183 */     int i1 = p_192634_6_ - p_192634_2_;
/* 184 */     int j1 = p_192634_7_ - p_192634_3_;
/*     */     
/* 186 */     if (i1 >= p_192634_4_ - 15 && i1 <= p_192634_4_ - 5 && j1 >= 0 && j1 <= 8) {
/*     */       
/* 188 */       this.owner.setHoveringText(s1);
/*     */     }
/* 190 */     else if (i1 >= p_192634_4_ - j - 15 - 2 && i1 <= p_192634_4_ - 15 - 2 && j1 >= 0 && j1 <= 8) {
/*     */       
/* 192 */       this.owner.setHoveringText(s);
/*     */     } 
/*     */     
/* 195 */     if (this.mc.gameSettings.touchscreen || p_192634_8_) {
/*     */       
/* 197 */       this.mc.getTextureManager().bindTexture(SERVER_SELECTION_BUTTONS);
/* 198 */       Gui.drawRect(p_192634_2_, p_192634_3_, p_192634_2_ + 32, p_192634_3_ + 32, -1601138544);
/* 199 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 200 */       int k1 = p_192634_6_ - p_192634_2_;
/* 201 */       int l1 = p_192634_7_ - p_192634_3_;
/*     */       
/* 203 */       if (canJoin())
/*     */       {
/* 205 */         if (k1 < 32 && k1 > 16) {
/*     */           
/* 207 */           Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else {
/*     */           
/* 211 */           Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 216 */       if (!isFeaturedServer) {
/*     */         
/* 218 */         if (this.owner.canMoveUp(this, p_192634_1_))
/*     */         {
/* 220 */           if (k1 < 16 && l1 < 16) {
/*     */             
/* 222 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/* 226 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/* 230 */         if (this.owner.canMoveDown(this, p_192634_1_))
/*     */         {
/* 232 */           if (k1 < 16 && l1 > 16) {
/*     */             
/* 234 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/* 238 */             Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawImg(int p_192634_2_, int p_192634_3_, boolean lower, ResourceLocation texture) {
/* 248 */     this.mc.getTextureManager().bindTexture(texture);
/* 249 */     Gui.drawModalRectWithCustomSizedTexture(p_192634_2_ - 16, lower ? (p_192634_3_ + 16) : p_192634_3_, 0.0F, 0.0F, 16, 16, 16.0F, 16.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawTextureAt(int p_178012_1_, int p_178012_2_, ResourceLocation p_178012_3_) {
/* 255 */     this.mc.getTextureManager().bindTexture(p_178012_3_);
/* 256 */     GlStateManager.enableBlend();
/* 257 */     Gui.drawModalRectWithCustomSizedTexture(p_178012_1_, p_178012_2_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/* 258 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canJoin() {
/* 263 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepareServerIcon() {
/* 268 */     if (this.server.getBase64EncodedIconData() == null) {
/*     */       
/* 270 */       this.mc.getTextureManager().deleteTexture(this.serverIcon);
/* 271 */       this.icon = null;
/*     */     } else {
/*     */       BufferedImage bufferedimage;
/*     */       
/* 275 */       ByteBuf bytebuf = Unpooled.copiedBuffer(this.server.getBase64EncodedIconData(), StandardCharsets.UTF_8);
/* 276 */       ByteBuf bytebuf1 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 282 */         bytebuf1 = Base64.decode(bytebuf);
/* 283 */         bufferedimage = TextureUtil.readBufferedImage((InputStream)new ByteBufInputStream(bytebuf1));
/* 284 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/* 285 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*     */       
/*     */       }
/* 288 */       catch (Throwable throwable) {
/*     */         
/* 290 */         LOGGER.error("Invalid icon for server {} ({})", this.server.serverName, this.server.serverIP, throwable);
/* 291 */         this.server.setBase64EncodedIconData(null);
/*     */       }
/*     */       finally {
/*     */         
/* 295 */         bytebuf.release();
/*     */         
/* 297 */         if (bytebuf1 != null)
/*     */         {
/* 299 */           bytebuf1.release();
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (this.icon == null) {
/*     */         
/* 308 */         this.icon = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
/* 309 */         this.mc.getTextureManager().loadTexture(this.serverIcon, (ITextureObject)this.icon);
/*     */       } 
/*     */       
/* 312 */       bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.icon.getTextureData(), 0, bufferedimage.getWidth());
/* 313 */       this.icon.updateDynamicTexture();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/* 323 */     if (relativeX <= 32) {
/*     */       
/* 325 */       if (relativeX < 32 && relativeX > 16 && canJoin()) {
/*     */         
/* 327 */         this.owner.selectServer(slotIndex);
/* 328 */         this.owner.connectToSelected();
/* 329 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 333 */       if (!(this.owner.getServerList().getServerData(slotIndex) instanceof ServerDataFeaturedUtils)) {
/*     */         
/* 335 */         if (relativeX < 16 && relativeY < 16 && this.owner.canMoveUp(this, slotIndex)) {
/*     */           
/* 337 */           this.owner.moveServerUp(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 338 */           return true;
/*     */         } 
/*     */         
/* 341 */         if (relativeX < 16 && relativeY > 16 && this.owner.canMoveDown(this, slotIndex)) {
/*     */           
/* 343 */           this.owner.moveServerDown(this, slotIndex, GuiScreen.isShiftKeyDown());
/* 344 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 350 */     this.owner.selectServer(slotIndex);
/*     */     
/* 352 */     if (Minecraft.getSystemTime() - this.lastClickTime < 250L)
/*     */     {
/* 354 */       this.owner.connectToSelected();
/*     */     }
/*     */     
/* 357 */     this.lastClickTime = Minecraft.getSystemTime();
/* 358 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerData getServerData() {
/* 374 */     return this.server;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\ServerListEntryNormal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */