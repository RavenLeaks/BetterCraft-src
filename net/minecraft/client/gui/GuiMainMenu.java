/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.Runnables;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import me.nzxter.bettercraft.mods.altmanager.GuiAltManager;
/*     */ import me.nzxter.bettercraft.mods.gui.GuiClient;
/*     */ import me.nzxter.bettercraft.mods.gui.GuiCredits;
/*     */ import me.nzxter.bettercraft.utils.ParticleUtils;
/*     */ import me.nzxter.bettercraft.utils.RenderUtils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.realms.RealmsBridge;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Session;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.WorldServerDemo;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import optifine.CustomPanorama;
/*     */ import optifine.CustomPanoramaProperties;
/*     */ import optifine.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.util.glu.Project;
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
/*     */ public class GuiMainMenu
/*     */   extends GuiScreen
/*     */ {
/*  67 */   private static final Logger LOGGER = LogManager.getLogger();
/*  68 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */ 
/*     */   
/*     */   private final float updateCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   private String splashText;
/*     */ 
/*     */ 
/*     */   
/*     */   private GuiButton buttonResetDemo;
/*     */ 
/*     */ 
/*     */   
/*     */   private float panoramaTimer;
/*     */ 
/*     */   
/*     */   private DynamicTexture viewportTexture;
/*     */ 
/*     */   
/*  90 */   private final Object threadLock = new Object();
/*  91 */   public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + 
/*  92 */     TextFormatting.RESET + " for more information.";
/*     */ 
/*     */   
/*     */   private int openGLWarning2Width;
/*     */ 
/*     */   
/*     */   private int openGLWarning1Width;
/*     */ 
/*     */   
/*     */   private int openGLWarningX1;
/*     */ 
/*     */   
/*     */   private int openGLWarningY1;
/*     */ 
/*     */   
/*     */   private int openGLWarningX2;
/*     */ 
/*     */   
/*     */   private int openGLWarningY2;
/*     */ 
/*     */   
/*     */   private String openGLWarning1;
/*     */ 
/*     */   
/*     */   private String openGLWarning2;
/*     */   
/*     */   private String openGLWarningLink;
/*     */   
/* 120 */   private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
/* 121 */   private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation(
/* 122 */       "textures/gui/title/minecraft.png");
/* 123 */   private static final ResourceLocation field_194400_H = new ResourceLocation("textures/gui/title/edition.png");
/*     */ 
/*     */   
/* 126 */   private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[] {
/* 127 */       new ResourceLocation("textures/gui/title/background/panorama_0.png"), 
/* 128 */       new ResourceLocation("textures/gui/title/background/panorama_1.png"), 
/* 129 */       new ResourceLocation("textures/gui/title/background/panorama_2.png"), 
/* 130 */       new ResourceLocation("textures/gui/title/background/panorama_3.png"), 
/* 131 */       new ResourceLocation("textures/gui/title/background/panorama_4.png"), 
/* 132 */       new ResourceLocation("textures/gui/title/background/panorama_5.png")
/*     */     };
/*     */   
/*     */   private ResourceLocation backgroundTexture;
/*     */   
/*     */   private GuiButton realmsButton;
/*     */   
/*     */   private boolean hasCheckedForRealmsNotification;
/*     */   
/*     */   private GuiScreen realmsNotification;
/*     */   
/*     */   private int field_193978_M;
/*     */   private int field_193979_N;
/*     */   private GuiButton modButton;
/*     */   private GuiScreen modUpdateNotification;
/*     */   private ParticleUtils particles;
/*     */   private boolean accountInfo;
/*     */   private boolean accountInfoOverride;
/*     */   
/*     */   public GuiMainMenu() {
/* 152 */     this.openGLWarning2 = MORE_INFO_TEXT;
/* 153 */     this.splashText = "missingno";
/* 154 */     IResource iresource = null;
/*     */     
/*     */     try {
/* 157 */       List<String> list = Lists.newArrayList();
/* 158 */       iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
/* 159 */       BufferedReader bufferedreader = new BufferedReader(
/* 160 */           new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
/*     */       
/*     */       String s;
/* 163 */       while ((s = bufferedreader.readLine()) != null) {
/* 164 */         s = s.trim();
/*     */         
/* 166 */         if (!s.isEmpty()) {
/* 167 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 171 */       if (!list.isEmpty()) {
/*     */         do {
/* 173 */           this.splashText = list.get(RANDOM.nextInt(list.size()));
/*     */         }
/* 175 */         while (this.splashText.hashCode() == 125780783);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 180 */     catch (IOException iOException) {
/*     */     
/*     */     } finally {
/* 183 */       IOUtils.closeQuietly((Closeable)iresource);
/*     */     } 
/*     */     
/* 186 */     this.updateCounter = RANDOM.nextFloat();
/* 187 */     this.openGLWarning1 = "";
/*     */     
/* 189 */     if (!(GLContext.getCapabilities()).OpenGL20 && !OpenGlHelper.areShadersSupported()) {
/* 190 */       this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
/* 191 */       this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
/* 192 */       this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean areRealmsNotificationsEnabled() {
/* 201 */     return ((Minecraft.getMinecraft()).gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && 
/* 202 */       this.realmsNotification != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 209 */     if (areRealmsNotificationsEnabled()) {
/* 210 */       this.realmsNotification.updateScreen();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 219 */     return false;
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
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 242 */     RenderUtils.downloadSkin();
/*     */ 
/*     */     
/* 245 */     this.particles = new ParticleUtils(this.width, this.height);
/*     */ 
/*     */     
/* 248 */     this.viewportTexture = new DynamicTexture(256, 256);
/* 249 */     this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", 
/* 250 */         this.viewportTexture);
/* 251 */     this.field_193978_M = this.fontRendererObj.getStringWidth("Copyright Mojang AB. Do not distribute!");
/* 252 */     this.field_193979_N = this.width - this.field_193978_M - 2;
/* 253 */     Calendar calendar = Calendar.getInstance();
/* 254 */     calendar.setTime(new Date());
/*     */     
/* 256 */     if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
/* 257 */       this.splashText = "Merry X-mas!";
/* 258 */     } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
/* 259 */       this.splashText = "Happy new year!";
/* 260 */     } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
/* 261 */       this.splashText = "OOoooOOOoooo! Spooky!";
/*     */     } 
/*     */     
/* 264 */     int i = 24;
/* 265 */     int j = this.height / 4 + 48;
/*     */     
/* 267 */     if (this.mc.isDemo()) {
/* 268 */       addDemoButtons(j, 24);
/*     */     } else {
/* 270 */       addSingleplayerMultiplayerButtons(j, 24);
/*     */     } 
/*     */     
/* 273 */     this.buttonList
/* 274 */       .add(new GuiButton(0, 215, this.height - 25, 98, 20, I18n.format("menu.options", new Object[0])));
/* 275 */     this.buttonList.add(new GuiButton(100, this.width - 104, this.height - 25, 98, 20, "Tools"));
/* 276 */     this.buttonList.add(new GuiButton(101, this.width - 105, 5, 100, 20, "Credits"));
/*     */ 
/*     */     
/* 279 */     synchronized (this.threadLock) {
/* 280 */       this.openGLWarning1Width = this.fontRendererObj.getStringWidth(this.openGLWarning1);
/* 281 */       this.openGLWarning2Width = this.fontRendererObj.getStringWidth(this.openGLWarning2);
/* 282 */       int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
/* 283 */       this.openGLWarningX1 = (this.width - k) / 2;
/* 284 */       this.openGLWarningY1 = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
/* 285 */       this.openGLWarningX2 = this.openGLWarningX1 + k;
/* 286 */       this.openGLWarningY2 = this.openGLWarningY1 + 24;
/*     */     } 
/*     */     
/* 289 */     this.mc.setConnectedToRealms(false);
/*     */     
/* 291 */     if ((Minecraft.getMinecraft()).gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && 
/* 292 */       !this.hasCheckedForRealmsNotification) {
/* 293 */       RealmsBridge realmsbridge = new RealmsBridge();
/* 294 */       this.realmsNotification = realmsbridge.getNotificationScreen(this);
/* 295 */       this.hasCheckedForRealmsNotification = true;
/*     */     } 
/*     */     
/* 298 */     if (areRealmsNotificationsEnabled()) {
/* 299 */       this.realmsNotification.setGuiSize(this.width, this.height);
/* 300 */       this.realmsNotification.initGui();
/*     */     } 
/*     */     
/* 303 */     if (Reflector.NotificationModUpdateScreen_init.exists()) {
/* 304 */       this.modUpdateNotification = (GuiScreen)Reflector.call(Reflector.NotificationModUpdateScreen_init, new Object[] { this, 
/* 305 */             this.modButton });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
/* 315 */     this.buttonList
/* 316 */       .add(new GuiButton(1, 5, this.height - 25, 98, 20, I18n.format("menu.singleplayer", new Object[0])));
/* 317 */     this.buttonList
/* 318 */       .add(new GuiButton(2, 110, this.height - 25, 98, 20, I18n.format("menu.multiplayer", new Object[0])));
/*     */ 
/*     */     
/* 321 */     if (Reflector.GuiModList_Constructor.exists()) {
/* 322 */       this.buttonList.add(this.modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, 98, 
/* 323 */             20, I18n.format("fml.menu.mods", new Object[0])));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
/* 331 */     this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
/* 332 */     this.buttonResetDemo = addButton(
/* 333 */         new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
/* 334 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 335 */     WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
/*     */     
/* 337 */     if (worldinfo == null) {
/* 338 */       this.buttonResetDemo.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 347 */     if (button.id == 0) {
/* 348 */       this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */     }
/*     */ 
/*     */     
/* 352 */     if (button.id == 100) {
/* 353 */       this.mc.displayGuiScreen((GuiScreen)new GuiClient(null));
/*     */     }
/*     */     
/* 356 */     if (button.id == 101) {
/* 357 */       this.mc.displayGuiScreen((GuiScreen)new GuiCredits(null));
/*     */     }
/*     */ 
/*     */     
/* 361 */     if (button.id == 1) {
/* 362 */       this.mc.displayGuiScreen(new GuiWorldSelection(this));
/*     */     }
/*     */     
/* 365 */     if (button.id == 2) {
/* 366 */       this.mc.displayGuiScreen(new GuiMultiplayer(this));
/*     */     }
/*     */     
/* 369 */     if (button.id == 14 && this.realmsButton.visible) {
/* 370 */       switchToRealms();
/*     */     }
/*     */     
/* 373 */     if (button.id == 4) {
/* 374 */       this.mc.shutdown();
/*     */     }
/*     */     
/* 377 */     if (button.id == 6 && Reflector.GuiModList_Constructor.exists()) {
/* 378 */       this.mc.displayGuiScreen((GuiScreen)Reflector.newInstance(Reflector.GuiModList_Constructor, new Object[] { this }));
/*     */     }
/*     */     
/* 381 */     if (button.id == 11) {
/* 382 */       this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
/*     */     }
/*     */     
/* 385 */     if (button.id == 12) {
/* 386 */       ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 387 */       WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
/*     */       
/* 389 */       if (worldinfo != null) {
/* 390 */         this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion", new Object[0]), 
/* 391 */               "'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]), 
/* 392 */               I18n.format("selectWorld.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 12));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void switchToRealms() {
/* 398 */     RealmsBridge realmsbridge = new RealmsBridge();
/* 399 */     realmsbridge.switchToRealms(this);
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 403 */     if (result && id == 12) {
/* 404 */       ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 405 */       isaveformat.flushCache();
/* 406 */       isaveformat.deleteWorldDirectory("Demo_World");
/* 407 */       this.mc.displayGuiScreen(this);
/* 408 */     } else if (id == 12) {
/* 409 */       this.mc.displayGuiScreen(this);
/* 410 */     } else if (id == 13) {
/* 411 */       if (result) {
/*     */         try {
/* 413 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 414 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 415 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(this.openGLWarningLink) });
/* 416 */         } catch (Throwable throwable1) {
/* 417 */           LOGGER.error("Couldn't open link", throwable1);
/*     */         } 
/*     */       }
/*     */       
/* 421 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawPanorama(int mouseX, int mouseY, float partialTicks) {
/* 429 */     Tessellator tessellator = Tessellator.getInstance();
/* 430 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 431 */     GlStateManager.matrixMode(5889);
/* 432 */     GlStateManager.pushMatrix();
/* 433 */     GlStateManager.loadIdentity();
/* 434 */     Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
/* 435 */     GlStateManager.matrixMode(5888);
/* 436 */     GlStateManager.pushMatrix();
/* 437 */     GlStateManager.loadIdentity();
/* 438 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 439 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 440 */     GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 441 */     GlStateManager.enableBlend();
/* 442 */     GlStateManager.disableAlpha();
/* 443 */     GlStateManager.disableCull();
/* 444 */     GlStateManager.depthMask(false);
/* 445 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, 
/* 446 */         GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, 
/* 447 */         GlStateManager.DestFactor.ZERO);
/* 448 */     int i = 8;
/* 449 */     int j = 64;
/* 450 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 452 */     if (custompanoramaproperties != null) {
/* 453 */       j = custompanoramaproperties.getBlur1();
/*     */     }
/*     */     
/* 456 */     for (int k = 0; k < j; k++) {
/* 457 */       GlStateManager.pushMatrix();
/* 458 */       float f = ((k % 8) / 8.0F - 0.5F) / 64.0F;
/* 459 */       float f1 = ((k / 8) / 8.0F - 0.5F) / 64.0F;
/* 460 */       float f2 = 0.0F;
/* 461 */       GlStateManager.translate(f, f1, 0.0F);
/* 462 */       GlStateManager.rotate(MathHelper.sin(this.panoramaTimer / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
/* 463 */       GlStateManager.rotate(-this.panoramaTimer * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */       
/* 465 */       for (int l = 0; l < 6; l++) {
/* 466 */         GlStateManager.pushMatrix();
/*     */         
/* 468 */         if (l == 1) {
/* 469 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 472 */         if (l == 2) {
/* 473 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 476 */         if (l == 3) {
/* 477 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 480 */         if (l == 4) {
/* 481 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 484 */         if (l == 5) {
/* 485 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 488 */         ResourceLocation[] aresourcelocation = TITLE_PANORAMA_PATHS;
/*     */         
/* 490 */         if (custompanoramaproperties != null) {
/* 491 */           aresourcelocation = custompanoramaproperties.getPanoramaLocations();
/*     */         }
/*     */         
/* 494 */         this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
/* 495 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 496 */         int i1 = 255 / (k + 1);
/* 497 */         float f3 = 0.0F;
/* 498 */         bufferbuilder.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, i1).endVertex();
/* 499 */         bufferbuilder.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, i1).endVertex();
/* 500 */         bufferbuilder.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, i1).endVertex();
/* 501 */         bufferbuilder.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, i1).endVertex();
/* 502 */         tessellator.draw();
/* 503 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 506 */       GlStateManager.popMatrix();
/* 507 */       GlStateManager.colorMask(true, true, true, false);
/*     */     } 
/*     */     
/* 510 */     bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
/* 511 */     GlStateManager.colorMask(true, true, true, true);
/* 512 */     GlStateManager.matrixMode(5889);
/* 513 */     GlStateManager.popMatrix();
/* 514 */     GlStateManager.matrixMode(5888);
/* 515 */     GlStateManager.popMatrix();
/* 516 */     GlStateManager.depthMask(true);
/* 517 */     GlStateManager.enableCull();
/* 518 */     GlStateManager.enableDepth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateAndBlurSkybox() {
/* 525 */     this.mc.getTextureManager().bindTexture(this.backgroundTexture);
/* 526 */     GlStateManager.glTexParameteri(3553, 10241, 9729);
/* 527 */     GlStateManager.glTexParameteri(3553, 10240, 9729);
/* 528 */     GlStateManager.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
/* 529 */     GlStateManager.enableBlend();
/* 530 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, 
/* 531 */         GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, 
/* 532 */         GlStateManager.DestFactor.ZERO);
/* 533 */     GlStateManager.colorMask(true, true, true, false);
/* 534 */     Tessellator tessellator = Tessellator.getInstance();
/* 535 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 536 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 537 */     GlStateManager.disableAlpha();
/* 538 */     int i = 3;
/* 539 */     int j = 3;
/* 540 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 542 */     if (custompanoramaproperties != null) {
/* 543 */       j = custompanoramaproperties.getBlur2();
/*     */     }
/*     */     
/* 546 */     for (int k = 0; k < j; k++) {
/* 547 */       float f = 1.0F / (k + 1);
/* 548 */       int l = this.width;
/* 549 */       int i1 = this.height;
/* 550 */       float f1 = (k - 1) / 256.0F;
/* 551 */       bufferbuilder.pos(l, i1, this.zLevel).tex((0.0F + f1), 1.0D)
/* 552 */         .color(1.0F, 1.0F, 1.0F, f).endVertex();
/* 553 */       bufferbuilder.pos(l, 0.0D, this.zLevel).tex((1.0F + f1), 1.0D)
/* 554 */         .color(1.0F, 1.0F, 1.0F, f).endVertex();
/* 555 */       bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex((1.0F + f1), 0.0D)
/* 556 */         .color(1.0F, 1.0F, 1.0F, f).endVertex();
/* 557 */       bufferbuilder.pos(0.0D, i1, this.zLevel).tex((0.0F + f1), 0.0D)
/* 558 */         .color(1.0F, 1.0F, 1.0F, f).endVertex();
/*     */     } 
/*     */     
/* 561 */     tessellator.draw();
/* 562 */     GlStateManager.enableAlpha();
/* 563 */     GlStateManager.colorMask(true, true, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
/* 570 */     this.mc.getFramebuffer().unbindFramebuffer();
/* 571 */     GlStateManager.viewport(0, 0, 256, 256);
/* 572 */     drawPanorama(mouseX, mouseY, partialTicks);
/* 573 */     rotateAndBlurSkybox();
/* 574 */     int i = 3;
/* 575 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 577 */     if (custompanoramaproperties != null) {
/* 578 */       i = custompanoramaproperties.getBlur3();
/*     */     }
/*     */     
/* 581 */     for (int j = 0; j < i; j++) {
/* 582 */       rotateAndBlurSkybox();
/* 583 */       rotateAndBlurSkybox();
/*     */     } 
/*     */     
/* 586 */     this.mc.getFramebuffer().bindFramebuffer(true);
/* 587 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 588 */     float f2 = 120.0F / ((this.width > this.height) ? this.width : this.height);
/* 589 */     float f = this.height * f2 / 256.0F;
/* 590 */     float f1 = this.width * f2 / 256.0F;
/* 591 */     int k = this.width;
/* 592 */     int l = this.height;
/* 593 */     Tessellator tessellator = Tessellator.getInstance();
/* 594 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 595 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 596 */     bufferbuilder.pos(0.0D, l, this.zLevel).tex((0.5F - f), (0.5F + f1))
/* 597 */       .color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 598 */     bufferbuilder.pos(k, l, this.zLevel).tex((0.5F - f), (0.5F - f1))
/* 599 */       .color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 600 */     bufferbuilder.pos(k, 0.0D, this.zLevel).tex((0.5F + f), (0.5F - f1))
/* 601 */       .color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 602 */     bufferbuilder.pos(0.0D, 0.0D, this.zLevel).tex((0.5F + f), (0.5F + f1))
/* 603 */       .color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 604 */     tessellator.draw();
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
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 617 */     drawDefaultBackground();
/*     */ 
/*     */     
/* 620 */     this.particles.drawParticles();
/*     */ 
/*     */ 
/*     */     
/* 624 */     drawString(this.fontRendererObj, Minecraft.session.getUsername(), 5, 59, -1);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 629 */       int x = 2;
/* 630 */       int y = 2;
/*     */       
/* 632 */       int scale = 55;
/*     */       
/* 634 */       String name = Minecraft.session.getUsername();
/*     */       
/* 636 */       if (RenderUtils.dynamicTexture != null) {
/* 637 */         GlStateManager.bindTexture(RenderUtils.dynamicTexture.getGlTextureId());
/* 638 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 639 */         Gui.drawScaledCustomSizeModalRect(x, y, 0.0F, 0.0F, scale, scale, scale, scale, scale, scale);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 644 */       if (mouseX > 4 && mouseX < 56 && mouseY > 5 && mouseY < 57) {
/* 645 */         drawRect(5, 5, 55, 55, Mouse.isButtonDown(0) ? 1342177280 : 1073741824);
/* 646 */         this.accountInfo = true;
/* 647 */         if (Mouse.isButtonDown(0)) {
/* 648 */           this.mc.displayGuiScreen((GuiScreen)new GuiAltManager(null));
/* 649 */           Mouse.destroy();
/* 650 */           Mouse.create();
/*     */         } 
/*     */       } else {
/* 653 */         this.accountInfo = false;
/*     */       } 
/* 655 */       GL11.glDisable(3042);
/* 656 */     } catch (Exception e) {
/* 657 */       e.printStackTrace();
/*     */     } 
/* 659 */     if (this.accountInfo || this.accountInfoOverride) {
/* 660 */       if (Minecraft.session.getSessionType() == Session.Type.LEGACY || Minecraft.session.getUsername().isEmpty()) {
/* 661 */         drawString(this.fontRendererObj, "§cNot Migrated", 5, 70, -1);
/*     */       } else {
/* 663 */         drawString(this.fontRendererObj, "§6Migrated (Mojang Account)", 5, 70, -1);
/*     */       } 
/*     */     }
/* 666 */     GlStateManager.pushMatrix();
/* 667 */     GlStateManager.scale(1.5D, 1.5D, 0.0D);
/* 668 */     drawString(this.fontRendererObj, " Alt Manager", 38, 3, -1);
/* 669 */     GlStateManager.popMatrix();
/* 670 */     drawString(this.fontRendererObj, "§7 (Click the head)", 58, 19, -1);
/*     */ 
/*     */     
/* 673 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 683 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 685 */     synchronized (this.threadLock) {
/* 686 */       if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && 
/* 687 */         mouseX >= this.openGLWarningX1 && mouseX <= this.openGLWarningX2 && 
/* 688 */         mouseY >= this.openGLWarningY1 && mouseY <= this.openGLWarningY2) {
/* 689 */         GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
/* 690 */         guiconfirmopenlink.disableSecurityWarning();
/* 691 */         this.mc.displayGuiScreen(guiconfirmopenlink);
/*     */       } 
/*     */     } 
/*     */     
/* 695 */     if (areRealmsNotificationsEnabled()) {
/* 696 */       this.realmsNotification.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     
/* 699 */     if (mouseX > this.field_193979_N && mouseX < this.field_193979_N + this.field_193978_M && 
/* 700 */       mouseY > this.height - 10 && mouseY < this.height) {
/* 701 */       this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 709 */     if (this.realmsNotification != null)
/* 710 */       this.realmsNotification.onGuiClosed(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiMainMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */