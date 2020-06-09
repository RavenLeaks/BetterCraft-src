/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.minecraft.world.storage.WorldSummary;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiListWorldSelectionEntry implements GuiListExtended.IGuiListEntry {
/*  29 */   private static final Logger LOGGER = LogManager.getLogger();
/*  30 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
/*  31 */   private static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
/*  32 */   private static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/world_selection.png");
/*     */   
/*     */   private final Minecraft client;
/*     */   private final GuiWorldSelection worldSelScreen;
/*     */   private final WorldSummary worldSummary;
/*     */   private final ResourceLocation iconLocation;
/*     */   private final GuiListWorldSelection containingListSel;
/*     */   private File iconFile;
/*     */   private DynamicTexture icon;
/*     */   private long lastClickTime;
/*     */   
/*     */   public GuiListWorldSelectionEntry(GuiListWorldSelection listWorldSelIn, WorldSummary p_i46591_2_, ISaveFormat p_i46591_3_) {
/*  44 */     this.containingListSel = listWorldSelIn;
/*  45 */     this.worldSelScreen = listWorldSelIn.getGuiWorldSelection();
/*  46 */     this.worldSummary = p_i46591_2_;
/*  47 */     this.client = Minecraft.getMinecraft();
/*  48 */     this.iconLocation = new ResourceLocation("worlds/" + p_i46591_2_.getFileName() + "/icon");
/*  49 */     this.iconFile = p_i46591_3_.getFile(p_i46591_2_.getFileName(), "icon.png");
/*     */     
/*  51 */     if (!this.iconFile.isFile())
/*     */     {
/*  53 */       this.iconFile = null;
/*     */     }
/*     */     
/*  56 */     loadServerIcon();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_192634_a(int p_192634_1_, int p_192634_2_, int p_192634_3_, int p_192634_4_, int p_192634_5_, int p_192634_6_, int p_192634_7_, boolean p_192634_8_, float p_192634_9_) {
/*  61 */     String s = this.worldSummary.getDisplayName();
/*  62 */     String s1 = String.valueOf(this.worldSummary.getFileName()) + " (" + DATE_FORMAT.format(new Date(this.worldSummary.getLastTimePlayed())) + ")";
/*  63 */     String s2 = "";
/*     */     
/*  65 */     if (StringUtils.isEmpty(s))
/*     */     {
/*  67 */       s = String.valueOf(I18n.format("selectWorld.world", new Object[0])) + " " + (p_192634_1_ + 1);
/*     */     }
/*     */     
/*  70 */     if (this.worldSummary.requiresConversion()) {
/*     */       
/*  72 */       s2 = String.valueOf(I18n.format("selectWorld.conversion", new Object[0])) + " " + s2;
/*     */     }
/*     */     else {
/*     */       
/*  76 */       s2 = I18n.format("gameMode." + this.worldSummary.getEnumGameType().getName(), new Object[0]);
/*     */       
/*  78 */       if (this.worldSummary.isHardcoreModeEnabled())
/*     */       {
/*  80 */         s2 = TextFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + TextFormatting.RESET;
/*     */       }
/*     */       
/*  83 */       if (this.worldSummary.getCheatsEnabled())
/*     */       {
/*  85 */         s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.cheats", new Object[0]);
/*     */       }
/*     */       
/*  88 */       String s3 = this.worldSummary.getVersionName();
/*     */       
/*  90 */       if (this.worldSummary.markVersionInList()) {
/*     */         
/*  92 */         if (this.worldSummary.askToOpenWorld())
/*     */         {
/*  94 */           s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.version", new Object[0]) + " " + TextFormatting.RED + s3 + TextFormatting.RESET;
/*     */         }
/*     */         else
/*     */         {
/*  98 */           s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.version", new Object[0]) + " " + TextFormatting.ITALIC + s3 + TextFormatting.RESET;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 103 */         s2 = String.valueOf(s2) + ", " + I18n.format("selectWorld.version", new Object[0]) + " " + s3;
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     this.client.fontRendererObj.drawString(s, p_192634_2_ + 32 + 3, p_192634_3_ + 1, 16777215);
/* 108 */     this.client.fontRendererObj.drawString(s1, p_192634_2_ + 32 + 3, p_192634_3_ + this.client.fontRendererObj.FONT_HEIGHT + 3, 8421504);
/* 109 */     this.client.fontRendererObj.drawString(s2, p_192634_2_ + 32 + 3, p_192634_3_ + this.client.fontRendererObj.FONT_HEIGHT + this.client.fontRendererObj.FONT_HEIGHT + 3, 8421504);
/* 110 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 111 */     this.client.getTextureManager().bindTexture((this.icon != null) ? this.iconLocation : ICON_MISSING);
/* 112 */     GlStateManager.enableBlend();
/* 113 */     Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/* 114 */     GlStateManager.disableBlend();
/*     */     
/* 116 */     if (this.client.gameSettings.touchscreen || p_192634_8_) {
/*     */       
/* 118 */       this.client.getTextureManager().bindTexture(ICON_OVERLAY_LOCATION);
/* 119 */       Gui.drawRect(p_192634_2_, p_192634_3_, p_192634_2_ + 32, p_192634_3_ + 32, -1601138544);
/* 120 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 121 */       int j = p_192634_6_ - p_192634_2_;
/* 122 */       int i = (j < 32) ? 32 : 0;
/*     */       
/* 124 */       if (this.worldSummary.markVersionInList()) {
/*     */         
/* 126 */         Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 32.0F, i, 32, 32, 256.0F, 256.0F);
/*     */         
/* 128 */         if (this.worldSummary.askToOpenWorld())
/*     */         {
/* 130 */           Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 96.0F, i, 32, 32, 256.0F, 256.0F);
/*     */           
/* 132 */           if (j < 32)
/*     */           {
/* 134 */             this.worldSelScreen.setVersionTooltip(TextFormatting.RED + I18n.format("selectWorld.tooltip.fromNewerVersion1", new Object[0]) + "\n" + TextFormatting.RED + I18n.format("selectWorld.tooltip.fromNewerVersion2", new Object[0]));
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 139 */           Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 64.0F, i, 32, 32, 256.0F, 256.0F);
/*     */           
/* 141 */           if (j < 32)
/*     */           {
/* 143 */             this.worldSelScreen.setVersionTooltip(TextFormatting.GOLD + I18n.format("selectWorld.tooltip.snapshot1", new Object[0]) + "\n" + TextFormatting.GOLD + I18n.format("selectWorld.tooltip.snapshot2", new Object[0]));
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 149 */         Gui.drawModalRectWithCustomSizedTexture(p_192634_2_, p_192634_3_, 0.0F, i, 32, 32, 256.0F, 256.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseEvent, int relativeX, int relativeY) {
/* 160 */     this.containingListSel.selectWorld(slotIndex);
/*     */     
/* 162 */     if (relativeX <= 32 && relativeX < 32) {
/*     */       
/* 164 */       joinWorld();
/* 165 */       return true;
/*     */     } 
/* 167 */     if (Minecraft.getSystemTime() - this.lastClickTime < 250L) {
/*     */       
/* 169 */       joinWorld();
/* 170 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     this.lastClickTime = Minecraft.getSystemTime();
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void joinWorld() {
/* 181 */     if (this.worldSummary.askToOpenWorld()) {
/*     */       
/* 183 */       this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
/*     */             {
/*     */               public void confirmClicked(boolean result, int id)
/*     */               {
/* 187 */                 if (result) {
/*     */                   
/* 189 */                   GuiListWorldSelectionEntry.this.loadWorld();
/*     */                 }
/*     */                 else {
/*     */                   
/* 193 */                   GuiListWorldSelectionEntry.this.client.displayGuiScreen(GuiListWorldSelectionEntry.this.worldSelScreen);
/*     */                 } 
/*     */               }
/* 196 */             },  I18n.format("selectWorld.versionQuestion", new Object[0]), I18n.format("selectWorld.versionWarning", new Object[] { this.worldSummary.getVersionName() }), I18n.format("selectWorld.versionJoinButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 0));
/*     */     }
/*     */     else {
/*     */       
/* 200 */       loadWorld();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteWorld() {
/* 206 */     this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
/*     */           {
/*     */             public void confirmClicked(boolean result, int id)
/*     */             {
/* 210 */               if (result) {
/*     */                 
/* 212 */                 GuiListWorldSelectionEntry.this.client.displayGuiScreen(new GuiScreenWorking());
/* 213 */                 ISaveFormat isaveformat = GuiListWorldSelectionEntry.this.client.getSaveLoader();
/* 214 */                 isaveformat.flushCache();
/* 215 */                 isaveformat.deleteWorldDirectory(GuiListWorldSelectionEntry.this.worldSummary.getFileName());
/* 216 */                 GuiListWorldSelectionEntry.this.containingListSel.refreshList();
/*     */               } 
/*     */               
/* 219 */               GuiListWorldSelectionEntry.this.client.displayGuiScreen(GuiListWorldSelectionEntry.this.worldSelScreen);
/*     */             }
/* 221 */           },  I18n.format("selectWorld.deleteQuestion", new Object[0]), "'" + this.worldSummary.getDisplayName() + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]), I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void editWorld() {
/* 226 */     this.client.displayGuiScreen(new GuiWorldEdit(this.worldSelScreen, this.worldSummary.getFileName()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateWorld() {
/* 231 */     this.client.displayGuiScreen(new GuiScreenWorking());
/* 232 */     GuiCreateWorld guicreateworld = new GuiCreateWorld(this.worldSelScreen);
/* 233 */     ISaveHandler isavehandler = this.client.getSaveLoader().getSaveLoader(this.worldSummary.getFileName(), false);
/* 234 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/* 235 */     isavehandler.flush();
/*     */     
/* 237 */     if (worldinfo != null) {
/*     */       
/* 239 */       guicreateworld.recreateFromExistingWorld(worldinfo);
/* 240 */       this.client.displayGuiScreen(guicreateworld);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadWorld() {
/* 246 */     this.client.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*     */     
/* 248 */     if (this.client.getSaveLoader().canLoadWorld(this.worldSummary.getFileName()))
/*     */     {
/* 250 */       this.client.launchIntegratedServer(this.worldSummary.getFileName(), this.worldSummary.getDisplayName(), null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadServerIcon() {
/* 256 */     boolean flag = (this.iconFile != null && this.iconFile.isFile());
/*     */     
/* 258 */     if (flag) {
/*     */       BufferedImage bufferedimage;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 264 */         bufferedimage = ImageIO.read(this.iconFile);
/* 265 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/* 266 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*     */       }
/* 268 */       catch (Throwable throwable) {
/*     */         
/* 270 */         LOGGER.error("Invalid icon for world {}", this.worldSummary.getFileName(), throwable);
/* 271 */         this.iconFile = null;
/*     */         
/*     */         return;
/*     */       } 
/* 275 */       if (this.icon == null) {
/*     */         
/* 277 */         this.icon = new DynamicTexture(bufferedimage.getWidth(), bufferedimage.getHeight());
/* 278 */         this.client.getTextureManager().loadTexture(this.iconLocation, (ITextureObject)this.icon);
/*     */       } 
/*     */       
/* 281 */       bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), this.icon.getTextureData(), 0, bufferedimage.getWidth());
/* 282 */       this.icon.updateDynamicTexture();
/*     */     }
/* 284 */     else if (!flag) {
/*     */       
/* 286 */       this.client.getTextureManager().deleteTexture(this.iconLocation);
/* 287 */       this.icon = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */   
/*     */   public void func_192633_a(int p_192633_1_, int p_192633_2_, int p_192633_3_, float p_192633_4_) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiListWorldSelectionEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */