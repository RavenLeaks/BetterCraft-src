/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Ordering;
/*      */ import java.text.DateFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import me.nzxter.bettercraft.mods.gui.ui.CPS;
/*      */ import me.nzxter.bettercraft.utils.ColorUtils;
/*      */ import me.nzxter.bettercraft.utils.UIRendererUtils;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.chat.IChatListener;
/*      */ import net.minecraft.client.gui.chat.NarratorChatListener;
/*      */ import net.minecraft.client.gui.chat.NormalChatListener;
/*      */ import net.minecraft.client.gui.chat.OverlayChatListener;
/*      */ import net.minecraft.client.gui.inventory.GuiContainer;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.RenderItem;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.text.ChatType;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.CustomItems;
/*      */ import optifine.Reflector;
/*      */ import optifine.ReflectorForge;
/*      */ import optifine.TextureAnimations;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiIngame
/*      */   extends Gui
/*      */ {
/*   79 */   private static final ResourceLocation VIGNETTE_TEX_PATH = new ResourceLocation("textures/misc/vignette.png");
/*   80 */   private static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");
/*   81 */   private static final ResourceLocation PUMPKIN_BLUR_TEX_PATH = new ResourceLocation("textures/misc/pumpkinblur.png");
/*   82 */   private final Random rand = new Random();
/*      */   
/*      */   private final Minecraft mc;
/*      */   
/*      */   private final RenderItem itemRenderer;
/*      */   
/*      */   private final GuiNewChat persistantChatGUI;
/*      */   
/*      */   private int updateCounter;
/*   91 */   private String recordPlaying = "";
/*      */ 
/*      */   
/*      */   private int recordPlayingUpFor;
/*      */   
/*      */   private boolean recordIsPlaying;
/*      */   
/*   98 */   public float prevVignetteBrightness = 1.0F;
/*      */ 
/*      */   
/*      */   private int remainingHighlightTicks;
/*      */ 
/*      */   
/*  104 */   private ItemStack highlightingItemStack = ItemStack.field_190927_a;
/*      */   
/*      */   private final GuiOverlayDebug overlayDebug;
/*      */   
/*      */   private final GuiSubtitleOverlay overlaySubtitle;
/*      */   
/*      */   private final GuiSpectator spectatorGui;
/*      */   
/*      */   private final GuiPlayerTabOverlay overlayPlayerList;
/*      */   
/*      */   private final GuiBossOverlay overlayBoss;
/*      */   
/*      */   private int titlesTimer;
/*  117 */   private String displayedTitle = "";
/*      */ 
/*      */   
/*  120 */   private String displayedSubTitle = "";
/*      */ 
/*      */   
/*      */   private int titleFadeIn;
/*      */ 
/*      */   
/*      */   private int titleDisplayTime;
/*      */   
/*      */   private int titleFadeOut;
/*      */   
/*      */   private int playerHealth;
/*      */   
/*      */   private int lastPlayerHealth;
/*      */   
/*      */   private long lastSystemTime;
/*      */   
/*      */   private long healthUpdateCounter;
/*      */   
/*  138 */   private final Map<ChatType, List<IChatListener>> field_191743_I = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   public GuiIngame(Minecraft mcIn) {
/*  142 */     this.mc = mcIn;
/*  143 */     this.itemRenderer = mcIn.getRenderItem();
/*  144 */     this.overlayDebug = new GuiOverlayDebug(mcIn);
/*  145 */     this.spectatorGui = new GuiSpectator(mcIn);
/*  146 */     this.persistantChatGUI = new GuiNewChat(mcIn);
/*  147 */     this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
/*  148 */     this.overlayBoss = new GuiBossOverlay(mcIn);
/*  149 */     this.overlaySubtitle = new GuiSubtitleOverlay(mcIn); byte b; int i;
/*      */     ChatType[] arrayOfChatType;
/*  151 */     for (i = (arrayOfChatType = ChatType.values()).length, b = 0; b < i; ) { ChatType chattype = arrayOfChatType[b];
/*      */       
/*  153 */       this.field_191743_I.put(chattype, Lists.newArrayList());
/*      */       b++; }
/*      */     
/*  156 */     NarratorChatListener narratorChatListener = NarratorChatListener.field_193643_a;
/*  157 */     ((List<NormalChatListener>)this.field_191743_I.get(ChatType.CHAT)).add(new NormalChatListener(mcIn));
/*  158 */     ((List<NarratorChatListener>)this.field_191743_I.get(ChatType.CHAT)).add(narratorChatListener);
/*  159 */     ((List<NormalChatListener>)this.field_191743_I.get(ChatType.SYSTEM)).add(new NormalChatListener(mcIn));
/*  160 */     ((List<NarratorChatListener>)this.field_191743_I.get(ChatType.SYSTEM)).add(narratorChatListener);
/*  161 */     ((List<OverlayChatListener>)this.field_191743_I.get(ChatType.GAME_INFO)).add(new OverlayChatListener(mcIn));
/*  162 */     setDefaultTitlesTimes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultTitlesTimes() {
/*  170 */     this.titleFadeIn = 10;
/*  171 */     this.titleDisplayTime = 70;
/*  172 */     this.titleFadeOut = 20;
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderGameOverlay(float partialTicks) {
/*  177 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  178 */     int i = ScaledResolution.getScaledWidth();
/*  179 */     int j = ScaledResolution.getScaledHeight();
/*  180 */     FontRenderer fontrenderer = getFontRenderer();
/*  181 */     GlStateManager.enableBlend();
/*      */     
/*  183 */     if (Config.isVignetteEnabled()) {
/*      */       
/*  185 */       renderVignette(this.mc.player.getBrightness(), scaledresolution);
/*      */     }
/*      */     else {
/*      */       
/*  189 */       GlStateManager.enableDepth();
/*  190 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */     } 
/*      */     
/*  193 */     ItemStack itemstack = this.mc.player.inventory.armorItemInSlot(3);
/*      */     
/*  195 */     if (this.mc.gameSettings.thirdPersonView == 0 && itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN))
/*      */     {
/*  197 */       renderPumpkinOverlay(scaledresolution);
/*      */     }
/*      */     
/*  200 */     if (!this.mc.player.isPotionActive(MobEffects.NAUSEA)) {
/*      */       
/*  202 */       float f = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * partialTicks;
/*      */       
/*  204 */       if (f > 0.0F)
/*      */       {
/*  206 */         renderPortal(f, scaledresolution);
/*      */       }
/*      */     } 
/*      */     
/*  210 */     if (this.mc.playerController.isSpectator()) {
/*      */       
/*  212 */       this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
/*      */     }
/*      */     else {
/*      */       
/*  216 */       renderHotbar(scaledresolution, partialTicks);
/*      */     } 
/*      */     
/*  219 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  220 */     this.mc.getTextureManager().bindTexture(ICONS);
/*  221 */     GlStateManager.enableBlend();
/*  222 */     renderAttackIndicator(partialTicks, scaledresolution);
/*  223 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  224 */     this.mc.mcProfiler.startSection("bossHealth");
/*  225 */     this.overlayBoss.renderBossHealth();
/*  226 */     this.mc.mcProfiler.endSection();
/*  227 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  228 */     this.mc.getTextureManager().bindTexture(ICONS);
/*      */     
/*  230 */     if (this.mc.playerController.shouldDrawHUD())
/*      */     {
/*  232 */       renderPlayerStats(scaledresolution);
/*      */     }
/*      */     
/*  235 */     renderMountHealth(scaledresolution);
/*  236 */     GlStateManager.disableBlend();
/*      */     
/*  238 */     if (this.mc.player.getSleepTimer() > 0) {
/*      */       
/*  240 */       this.mc.mcProfiler.startSection("sleep");
/*  241 */       GlStateManager.disableDepth();
/*  242 */       GlStateManager.disableAlpha();
/*  243 */       int j1 = this.mc.player.getSleepTimer();
/*  244 */       float f1 = j1 / 100.0F;
/*      */       
/*  246 */       if (f1 > 1.0F)
/*      */       {
/*  248 */         f1 = 1.0F - (j1 - 100) / 10.0F;
/*      */       }
/*      */       
/*  251 */       int k = (int)(220.0F * f1) << 24 | 0x101020;
/*  252 */       drawRect(0, 0, i, j, k);
/*  253 */       GlStateManager.enableAlpha();
/*  254 */       GlStateManager.enableDepth();
/*  255 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  258 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  259 */     int k1 = i / 2 - 91;
/*      */     
/*  261 */     if (this.mc.player.isRidingHorse()) {
/*      */       
/*  263 */       renderHorseJumpBar(scaledresolution, k1);
/*      */     }
/*  265 */     else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
/*      */       
/*  267 */       renderExpBar(scaledresolution, k1);
/*      */     } 
/*      */     
/*  270 */     if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
/*      */       
/*  272 */       renderSelectedItem(scaledresolution);
/*      */     }
/*  274 */     else if (this.mc.player.isSpectator()) {
/*      */       
/*  276 */       this.spectatorGui.renderSelectedItem(scaledresolution);
/*      */     } 
/*      */     
/*  279 */     if (this.mc.isDemo())
/*      */     {
/*  281 */       renderDemo(scaledresolution);
/*      */     }
/*      */     
/*  284 */     renderPotionEffects(scaledresolution);
/*      */     
/*  286 */     if (this.mc.gameSettings.showDebugInfo)
/*      */     {
/*  288 */       this.overlayDebug.renderDebugInfo(scaledresolution);
/*      */     }
/*      */     
/*  291 */     if (this.recordPlayingUpFor > 0) {
/*      */       
/*  293 */       this.mc.mcProfiler.startSection("overlayMessage");
/*  294 */       float f2 = this.recordPlayingUpFor - partialTicks;
/*  295 */       int l1 = (int)(f2 * 255.0F / 20.0F);
/*      */       
/*  297 */       if (l1 > 255)
/*      */       {
/*  299 */         l1 = 255;
/*      */       }
/*      */       
/*  302 */       if (l1 > 8) {
/*      */         
/*  304 */         GlStateManager.pushMatrix();
/*  305 */         GlStateManager.translate((i / 2), (j - 68), 0.0F);
/*  306 */         GlStateManager.enableBlend();
/*  307 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  308 */         int l = 16777215;
/*      */         
/*  310 */         if (this.recordIsPlaying)
/*      */         {
/*  312 */           l = MathHelper.hsvToRGB(f2 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
/*      */         }
/*      */         
/*  315 */         fontrenderer.drawString(this.recordPlaying, -fontrenderer.getStringWidth(this.recordPlaying) / 2, -4, l + (l1 << 24 & 0xFF000000));
/*  316 */         GlStateManager.disableBlend();
/*  317 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  320 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  323 */     this.overlaySubtitle.renderSubtitles(scaledresolution);
/*      */     
/*  325 */     if (this.titlesTimer > 0) {
/*      */       
/*  327 */       this.mc.mcProfiler.startSection("titleAndSubtitle");
/*  328 */       float f3 = this.titlesTimer - partialTicks;
/*  329 */       int i2 = 255;
/*      */       
/*  331 */       if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
/*      */         
/*  333 */         float f4 = (this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f3;
/*  334 */         i2 = (int)(f4 * 255.0F / this.titleFadeIn);
/*      */       } 
/*      */       
/*  337 */       if (this.titlesTimer <= this.titleFadeOut)
/*      */       {
/*  339 */         i2 = (int)(f3 * 255.0F / this.titleFadeOut);
/*      */       }
/*      */       
/*  342 */       i2 = MathHelper.clamp(i2, 0, 255);
/*      */       
/*  344 */       if (i2 > 8) {
/*      */         
/*  346 */         GlStateManager.pushMatrix();
/*  347 */         GlStateManager.translate((i / 2), (j / 2), 0.0F);
/*  348 */         GlStateManager.enableBlend();
/*  349 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  350 */         GlStateManager.pushMatrix();
/*  351 */         GlStateManager.scale(4.0F, 4.0F, 4.0F);
/*  352 */         int j2 = i2 << 24 & 0xFF000000;
/*  353 */         fontrenderer.drawString(this.displayedTitle, (-fontrenderer.getStringWidth(this.displayedTitle) / 2), -10.0F, 0xFFFFFF | j2, true);
/*  354 */         GlStateManager.popMatrix();
/*  355 */         GlStateManager.pushMatrix();
/*  356 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*  357 */         fontrenderer.drawString(this.displayedSubTitle, (-fontrenderer.getStringWidth(this.displayedSubTitle) / 2), 5.0F, 0xFFFFFF | j2, true);
/*  358 */         GlStateManager.popMatrix();
/*  359 */         GlStateManager.disableBlend();
/*  360 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  363 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  366 */     Scoreboard scoreboard = this.mc.world.getScoreboard();
/*  367 */     ScoreObjective scoreobjective = null;
/*  368 */     ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.player.getName());
/*      */     
/*  370 */     if (scoreplayerteam != null) {
/*      */       
/*  372 */       int i1 = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  374 */       if (i1 >= 0)
/*      */       {
/*  376 */         scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
/*      */       }
/*      */     } 
/*      */     
/*  380 */     ScoreObjective scoreobjective1 = (scoreobjective != null) ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
/*      */     
/*  382 */     if (scoreobjective1 != null)
/*      */     {
/*  384 */       renderScoreboard(scoreobjective1, scaledresolution);
/*      */     }
/*      */     
/*  387 */     GlStateManager.enableBlend();
/*  388 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  389 */     GlStateManager.disableAlpha();
/*  390 */     GlStateManager.pushMatrix();
/*      */     
/*  392 */     GlStateManager.translate(0.0F, (j - 75), 0.0F);
/*      */     
/*  394 */     this.mc.mcProfiler.startSection("chat");
/*  395 */     this.persistantChatGUI.drawChat(this.updateCounter);
/*  396 */     this.mc.mcProfiler.endSection();
/*  397 */     GlStateManager.popMatrix();
/*  398 */     scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
/*      */ 
/*      */     
/*  401 */     if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.player.connection.getPlayerInfoMap().size() > 1 || scoreobjective1 != null)) {
/*      */       
/*  403 */       this.overlayPlayerList.updatePlayerList(true);
/*  404 */       this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
/*      */     }
/*      */     else {
/*      */       
/*  408 */       this.overlayPlayerList.updatePlayerList(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  413 */     UIRendererUtils.draw();
/*      */ 
/*      */     
/*  416 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  417 */     GlStateManager.disableLighting();
/*  418 */     GlStateManager.enableAlpha();
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderAttackIndicator(float p_184045_1_, ScaledResolution p_184045_2_) {
/*  423 */     GameSettings gamesettings = this.mc.gameSettings;
/*      */     
/*  425 */     if (gamesettings.thirdPersonView == 0) {
/*      */       
/*  427 */       if (this.mc.playerController.isSpectator() && this.mc.pointedEntity == null) {
/*      */         
/*  429 */         RayTraceResult raytraceresult = this.mc.objectMouseOver;
/*      */         
/*  431 */         if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  436 */         BlockPos blockpos = raytraceresult.getBlockPos();
/*  437 */         IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
/*      */         
/*  439 */         if (!ReflectorForge.blockHasTileEntity(iblockstate) || !(this.mc.world.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  445 */       int l = ScaledResolution.getScaledWidth();
/*  446 */       int i1 = ScaledResolution.getScaledHeight();
/*      */       
/*  448 */       if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !this.mc.player.hasReducedDebug() && !gamesettings.reducedDebugInfo) {
/*      */         
/*  450 */         GlStateManager.pushMatrix();
/*  451 */         GlStateManager.translate((l / 2), (i1 / 2), this.zLevel);
/*  452 */         Entity entity = this.mc.getRenderViewEntity();
/*  453 */         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_184045_1_, -1.0F, 0.0F, 0.0F);
/*  454 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_184045_1_, 0.0F, 1.0F, 0.0F);
/*  455 */         GlStateManager.scale(-1.0F, -1.0F, -1.0F);
/*  456 */         OpenGlHelper.renderDirections(10);
/*  457 */         GlStateManager.popMatrix();
/*      */       }
/*      */       else {
/*      */         
/*  461 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  462 */         GlStateManager.enableAlpha();
/*  463 */         drawTexturedModalRect(l / 2 - 7, i1 / 2 - 7, 0, 0, 16, 16);
/*      */         
/*  465 */         if (this.mc.gameSettings.attackIndicator == 1) {
/*      */           
/*  467 */           float f = this.mc.player.getCooledAttackStrength(0.0F);
/*  468 */           boolean flag = false;
/*      */           
/*  470 */           if (this.mc.pointedEntity != null && this.mc.pointedEntity instanceof EntityLivingBase && f >= 1.0F) {
/*      */             
/*  472 */             flag = (this.mc.player.getCooldownPeriod() > 5.0F);
/*  473 */             flag &= ((EntityLivingBase)this.mc.pointedEntity).isEntityAlive();
/*      */           } 
/*      */           
/*  476 */           int i = i1 / 2 - 7 + 16;
/*  477 */           int j = l / 2 - 8;
/*      */           
/*  479 */           if (flag) {
/*      */             
/*  481 */             drawTexturedModalRect(j, i, 68, 94, 16, 16);
/*      */           }
/*  483 */           else if (f < 1.0F) {
/*      */             
/*  485 */             int k = (int)(f * 17.0F);
/*  486 */             drawTexturedModalRect(j, i, 36, 94, 16, 4);
/*  487 */             drawTexturedModalRect(j, i, 52, 94, k, 4);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderPotionEffects(ScaledResolution resolution) {
/*  496 */     Collection<PotionEffect> collection = this.mc.player.getActivePotionEffects();
/*      */     
/*  498 */     if (!collection.isEmpty()) {
/*      */       
/*  500 */       this.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
/*  501 */       GlStateManager.enableBlend();
/*  502 */       int i = 0;
/*  503 */       int j = 0;
/*  504 */       Iterator<PotionEffect> iterator = Ordering.natural().reverse().sortedCopy(collection).iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       while (true) {
/*  514 */         if (!iterator.hasNext()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  519 */         PotionEffect potioneffect = iterator.next();
/*  520 */         Potion potion = potioneffect.getPotion();
/*  521 */         boolean flag = potion.hasStatusIcon();
/*      */         
/*  523 */         if (Reflector.ForgePotion_shouldRenderHUD.exists())
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  528 */           if (Reflector.callBoolean(potion, Reflector.ForgePotion_shouldRenderHUD, new Object[] { potioneffect })) {
/*      */             
/*  530 */             this.mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
/*  531 */             flag = true;
/*      */           } else {
/*      */             continue;
/*      */           } 
/*      */         }
/*  536 */         if (flag && potioneffect.doesShowParticles()) {
/*      */           
/*  538 */           int k = ScaledResolution.getScaledWidth();
/*  539 */           int l = 1;
/*      */           
/*  541 */           if (this.mc.isDemo())
/*      */           {
/*  543 */             l += 15;
/*      */           }
/*      */           
/*  546 */           int i1 = potion.getStatusIconIndex();
/*      */           
/*  548 */           if (potion.isBeneficial()) {
/*      */             
/*  550 */             i++;
/*  551 */             k -= 25 * i;
/*      */           }
/*      */           else {
/*      */             
/*  555 */             j++;
/*  556 */             k -= 25 * j;
/*  557 */             l += 26;
/*      */           } 
/*      */           
/*  560 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  561 */           float f = 1.0F;
/*      */           
/*  563 */           if (potioneffect.getIsAmbient()) {
/*      */             
/*  565 */             drawTexturedModalRect(k, l, 165, 166, 24, 24);
/*      */           }
/*      */           else {
/*      */             
/*  569 */             drawTexturedModalRect(k, l, 141, 166, 24, 24);
/*      */             
/*  571 */             if (potioneffect.getDuration() <= 200) {
/*      */               
/*  573 */               int j1 = 10 - potioneffect.getDuration() / 20;
/*  574 */               f = MathHelper.clamp(potioneffect.getDuration() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F) + MathHelper.cos(potioneffect.getDuration() * 3.1415927F / 5.0F) * MathHelper.clamp(j1 / 10.0F * 0.25F, 0.0F, 0.25F);
/*      */             } 
/*      */           } 
/*      */           
/*  578 */           GlStateManager.color(1.0F, 1.0F, 1.0F, f);
/*      */           
/*  580 */           if (Reflector.ForgePotion_renderHUDEffect.exists()) {
/*      */             
/*  582 */             if (potion.hasStatusIcon())
/*      */             {
/*  584 */               drawTexturedModalRect(k + 3, l + 3, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
/*      */             }
/*  586 */             Reflector.call(potion, Reflector.ForgePotion_renderHUDEffect, new Object[] { Integer.valueOf(k), Integer.valueOf(l), potioneffect, this.mc, Float.valueOf(f) });
/*      */             
/*      */             continue;
/*      */           } 
/*  590 */           drawTexturedModalRect(k + 3, l + 3, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderHotbar(ScaledResolution sr, float partialTicks) {
/*  601 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       int ping;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  607 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  608 */       this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
/*  609 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  610 */       ItemStack itemstack = entityplayer.getHeldItemOffhand();
/*  611 */       EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
/*  612 */       int i = ScaledResolution.getScaledWidth() / 2;
/*  613 */       float f = this.zLevel;
/*  614 */       int j = 182;
/*  615 */       int k = 91;
/*  616 */       this.zLevel = -90.0F;
/*      */       
/*  618 */       drawRect(0, ScaledResolution.getScaledHeight() - 25, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -2147483648);
/*  619 */       if (this.mc.player.inventory.currentItem == 0) {
/*  620 */         drawRect(ScaledResolution.getScaledWidth() / 2 - 91 + this.mc.player.inventory.currentItem * 20, ScaledResolution.getScaledHeight() - 25, ScaledResolution.getScaledWidth() / 2 + 91 - 160, ScaledResolution.getScaledHeight(), 2147483647);
/*      */       } else {
/*  622 */         drawRect(ScaledResolution.getScaledWidth() / 2 - 91 + this.mc.player.inventory.currentItem * 20, ScaledResolution.getScaledHeight() - 25, ScaledResolution.getScaledWidth() / 2 + 91 - 20 * (8 - this.mc.player.inventory.currentItem), ScaledResolution.getScaledHeight(), 2147483647);
/*      */       } 
/*      */       
/*  625 */       drawString(this.mc.fontRendererObj, "FPS: ", 5, ScaledResolution.getScaledHeight() - 22, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  626 */       drawString(this.mc.fontRendererObj, " " + Minecraft.debugFPS, 25, ScaledResolution.getScaledHeight() - 22, 16777215);
/*      */       
/*  628 */       if (this.mc.isSingleplayer()) {
/*  629 */         ping = 0;
/*      */       } else {
/*  631 */         ping = (int)(this.mc.getCurrentServerData()).pingToServer;
/*      */       } 
/*      */       
/*  634 */       drawString(this.mc.fontRendererObj, "PING: ", 5, ScaledResolution.getScaledHeight() - 12, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  635 */       drawString(this.mc.fontRendererObj, " " + ping, 30, ScaledResolution.getScaledHeight() - 12, 16777215);
/*      */       
/*  637 */       drawString(this.mc.fontRendererObj, "X: ", 55, ScaledResolution.getScaledHeight() - 22, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  638 */       drawString(this.mc.fontRendererObj, " " + Math.round(this.mc.player.posX), 65, ScaledResolution.getScaledHeight() - 22, 16777215);
/*      */       
/*  640 */       drawString(this.mc.fontRendererObj, "Y: ", 55, ScaledResolution.getScaledHeight() - 12, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  641 */       drawString(this.mc.fontRendererObj, " " + Math.round(this.mc.player.posY), 65, ScaledResolution.getScaledHeight() - 12, 16777215);
/*      */       
/*  643 */       drawString(this.mc.fontRendererObj, "Z: ", 95, ScaledResolution.getScaledHeight() - 22, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  644 */       drawString(this.mc.fontRendererObj, " " + Math.round(this.mc.player.posZ), 105, ScaledResolution.getScaledHeight() - 22, 16777215);
/*      */       
/*  646 */       drawString(this.mc.fontRendererObj, "CPS: ", 95, ScaledResolution.getScaledHeight() - 12, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  647 */       drawString(this.mc.fontRendererObj, " " + CPS.getCPS(), 115, ScaledResolution.getScaledHeight() - 12, 16777215);
/*      */       
/*  649 */       DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
/*  650 */       Date today = Calendar.getInstance().getTime();
/*  651 */       String renderDate = df.format(today);
/*      */       
/*  653 */       DateFormat dff = new SimpleDateFormat("HH:mm:ss");
/*  654 */       Date todayy = Calendar.getInstance().getTime();
/*  655 */       String renderTime = dff.format(todayy);
/*      */       
/*  657 */       drawString(this.mc.fontRendererObj, "DATE: ", ScaledResolution.getScaledWidth() - 95, ScaledResolution.getScaledHeight() - 12, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  658 */       drawString(this.mc.fontRendererObj, renderDate, ScaledResolution.getScaledWidth() - 65, ScaledResolution.getScaledHeight() - 12, 16777215);
/*      */       
/*  660 */       drawString(this.mc.fontRendererObj, "TIME: ", ScaledResolution.getScaledWidth() - 95, ScaledResolution.getScaledHeight() - 22, ColorUtils.rainbowEffect(0L, 1.0F).getRGB());
/*  661 */       drawString(this.mc.fontRendererObj, renderTime, ScaledResolution.getScaledWidth() - 67, ScaledResolution.getScaledHeight() - 22, 16777215);
/*      */ 
/*      */       
/*  664 */       if (!itemstack.func_190926_b())
/*      */       {
/*  666 */         if (enumhandside == EnumHandSide.LEFT) {
/*      */           
/*  668 */           drawTexturedModalRect(i - 91 - 29, ScaledResolution.getScaledHeight() - 23, 24, 22, 29, 24);
/*      */         }
/*      */         else {
/*      */           
/*  672 */           drawTexturedModalRect(i + 91, ScaledResolution.getScaledHeight() - 23, 53, 22, 29, 24);
/*      */         } 
/*      */       }
/*      */       
/*  676 */       this.zLevel = f;
/*  677 */       GlStateManager.enableRescaleNormal();
/*  678 */       GlStateManager.enableBlend();
/*  679 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  680 */       RenderHelper.enableGUIStandardItemLighting();
/*  681 */       CustomItems.setRenderOffHand(false);
/*      */       
/*  683 */       for (int l = 0; l < 9; l++) {
/*      */         
/*  685 */         int i1 = i - 90 + l * 20 + 2;
/*  686 */         int j1 = ScaledResolution.getScaledHeight() - 16 - 3;
/*  687 */         renderHotbarItem(i1, j1, partialTicks, entityplayer, (ItemStack)entityplayer.inventory.mainInventory.get(l));
/*      */       } 
/*      */       
/*  690 */       if (!itemstack.func_190926_b()) {
/*      */         
/*  692 */         CustomItems.setRenderOffHand(true);
/*  693 */         int l1 = ScaledResolution.getScaledHeight() - 16 - 3;
/*      */         
/*  695 */         if (enumhandside == EnumHandSide.LEFT) {
/*      */           
/*  697 */           renderHotbarItem(i - 91 - 26, l1, partialTicks, entityplayer, itemstack);
/*      */         }
/*      */         else {
/*      */           
/*  701 */           renderHotbarItem(i + 91 + 10, l1, partialTicks, entityplayer, itemstack);
/*      */         } 
/*      */         
/*  704 */         CustomItems.setRenderOffHand(false);
/*      */       } 
/*      */       
/*  707 */       if (this.mc.gameSettings.attackIndicator == 2) {
/*      */         
/*  709 */         float f1 = this.mc.player.getCooledAttackStrength(0.0F);
/*      */         
/*  711 */         if (f1 < 1.0F) {
/*      */           
/*  713 */           int i2 = ScaledResolution.getScaledHeight() - 20;
/*  714 */           int j2 = i + 91 + 6;
/*      */           
/*  716 */           if (enumhandside == EnumHandSide.RIGHT)
/*      */           {
/*  718 */             j2 = i - 91 - 22;
/*      */           }
/*      */           
/*  721 */           this.mc.getTextureManager().bindTexture(Gui.ICONS);
/*  722 */           int k1 = (int)(f1 * 19.0F);
/*  723 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  724 */           drawTexturedModalRect(j2, i2, 0, 94, 18, 18);
/*  725 */           drawTexturedModalRect(j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
/*      */         } 
/*      */       } 
/*      */       
/*  729 */       RenderHelper.disableStandardItemLighting();
/*  730 */       GlStateManager.disableRescaleNormal();
/*  731 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderHorseJumpBar(ScaledResolution scaledRes, int x) {
/*  737 */     this.mc.mcProfiler.startSection("jumpBar");
/*  738 */     this.mc.getTextureManager().bindTexture(Gui.ICONS);
/*  739 */     float f = this.mc.player.getHorseJumpPower();
/*  740 */     int i = 182;
/*  741 */     int j = (int)(f * 183.0F);
/*  742 */     int k = ScaledResolution.getScaledHeight() - 32 + 3;
/*  743 */     drawTexturedModalRect(x, k, 0, 84, 182, 5);
/*      */     
/*  745 */     if (j > 0)
/*      */     {
/*  747 */       drawTexturedModalRect(x, k, 0, 89, j, 5);
/*      */     }
/*      */     
/*  750 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderExpBar(ScaledResolution scaledRes, int x) {
/*  755 */     this.mc.mcProfiler.startSection("expBar");
/*  756 */     this.mc.getTextureManager().bindTexture(Gui.ICONS);
/*  757 */     int i = this.mc.player.xpBarCap();
/*      */     
/*  759 */     if (i > 0) {
/*      */       
/*  761 */       int j = 182;
/*  762 */       int k = (int)(this.mc.player.experience * 183.0F);
/*  763 */       int l = ScaledResolution.getScaledHeight() - 32 + 3;
/*  764 */       drawTexturedModalRect(x, l, 0, 64, 182, 5);
/*      */       
/*  766 */       if (k > 0)
/*      */       {
/*  768 */         drawTexturedModalRect(x, l, 0, 69, k, 5);
/*      */       }
/*      */     } 
/*      */     
/*  772 */     this.mc.mcProfiler.endSection();
/*      */     
/*  774 */     if (this.mc.player.experienceLevel > 0) {
/*      */       
/*  776 */       this.mc.mcProfiler.startSection("expLevel");
/*  777 */       int j1 = 8453920;
/*      */       
/*  779 */       if (Config.isCustomColors())
/*      */       {
/*  781 */         j1 = CustomColors.getExpBarTextColor(j1);
/*      */       }
/*      */       
/*  784 */       int j = this.mc.player.experienceLevel;
/*  785 */       int k1 = (ScaledResolution.getScaledWidth() - getFontRenderer().getStringWidth(j)) / 2;
/*  786 */       int i1 = ScaledResolution.getScaledHeight() - 31 - 4;
/*  787 */       getFontRenderer().drawString(j, k1 + 1, i1, 0);
/*  788 */       getFontRenderer().drawString(j, k1 - 1, i1, 0);
/*  789 */       getFontRenderer().drawString(j, k1, i1 + 1, 0);
/*  790 */       getFontRenderer().drawString(j, k1, i1 - 1, 0);
/*  791 */       getFontRenderer().drawString(j, k1, i1, j1);
/*  792 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderSelectedItem(ScaledResolution scaledRes) {
/*  798 */     this.mc.mcProfiler.startSection("selectedItemName");
/*      */     
/*  800 */     if (this.remainingHighlightTicks > 0 && !this.highlightingItemStack.func_190926_b()) {
/*      */       
/*  802 */       String s = this.highlightingItemStack.getDisplayName();
/*      */       
/*  804 */       if (this.highlightingItemStack.hasDisplayName())
/*      */       {
/*  806 */         s = TextFormatting.ITALIC + s;
/*      */       }
/*      */       
/*  809 */       int i = (ScaledResolution.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/*  810 */       int j = ScaledResolution.getScaledHeight() - 59;
/*      */       
/*  812 */       if (!this.mc.playerController.shouldDrawHUD())
/*      */       {
/*  814 */         j += 14;
/*      */       }
/*      */       
/*  817 */       int k = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
/*      */       
/*  819 */       if (k > 255)
/*      */       {
/*  821 */         k = 255;
/*      */       }
/*      */       
/*  824 */       if (k > 0) {
/*      */         
/*  826 */         GlStateManager.pushMatrix();
/*  827 */         GlStateManager.enableBlend();
/*  828 */         GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  829 */         getFontRenderer().drawStringWithShadow(s, i, j, 16777215 + (k << 24));
/*  830 */         GlStateManager.disableBlend();
/*  831 */         GlStateManager.popMatrix();
/*      */       } 
/*      */     } 
/*      */     
/*  835 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void renderDemo(ScaledResolution scaledRes) {
/*      */     String s;
/*  840 */     this.mc.mcProfiler.startSection("demo");
/*      */ 
/*      */     
/*  843 */     if (this.mc.world.getTotalWorldTime() >= 120500L) {
/*      */       
/*  845 */       s = I18n.format("demo.demoExpired", new Object[0]);
/*      */     }
/*      */     else {
/*      */       
/*  849 */       s = I18n.format("demo.remainingTime", new Object[] { StringUtils.ticksToElapsedTime((int)(120500L - this.mc.world.getTotalWorldTime())) });
/*      */     } 
/*      */     
/*  852 */     int i = getFontRenderer().getStringWidth(s);
/*  853 */     getFontRenderer().drawStringWithShadow(s, (ScaledResolution.getScaledWidth() - i - 10), 5.0F, 16777215);
/*  854 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderScoreboard(ScoreObjective objective, ScaledResolution scaledRes) {
/*  859 */     Scoreboard scoreboard = objective.getScoreboard();
/*  860 */     Collection<Score> collection = scoreboard.getSortedScores(objective);
/*  861 */     List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>()
/*      */           {
/*      */             public boolean apply(@Nullable Score p_apply_1_)
/*      */             {
/*  865 */               return (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"));
/*      */             }
/*      */           }));
/*      */     
/*  869 */     if (list.size() > 15) {
/*      */       
/*  871 */       collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
/*      */     }
/*      */     else {
/*      */       
/*  875 */       collection = list;
/*      */     } 
/*      */     
/*  878 */     int i = getFontRenderer().getStringWidth(objective.getDisplayName());
/*      */     
/*  880 */     for (Score score : collection) {
/*      */       
/*  882 */       ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/*  883 */       String s = String.valueOf(ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName())) + ": " + TextFormatting.RED + score.getScorePoints();
/*  884 */       i = Math.max(i, getFontRenderer().getStringWidth(s));
/*      */     } 
/*      */     
/*  887 */     int i1 = collection.size() * (getFontRenderer()).FONT_HEIGHT;
/*  888 */     int j1 = ScaledResolution.getScaledHeight() / 2 + i1 / 3;
/*  889 */     int k1 = 3;
/*  890 */     int l1 = ScaledResolution.getScaledWidth() - i - 3;
/*  891 */     int j = 0;
/*      */     
/*  893 */     for (Score score1 : collection) {
/*      */       
/*  895 */       j++;
/*  896 */       ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
/*  897 */       String s1 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam1, score1.getPlayerName());
/*  898 */       String s2 = TextFormatting.RED + score1.getScorePoints();
/*  899 */       int k = j1 - j * (getFontRenderer()).FONT_HEIGHT;
/*  900 */       int l = ScaledResolution.getScaledWidth() - 3 + 2;
/*  901 */       drawRect(l1 - 2, k, l, k + (getFontRenderer()).FONT_HEIGHT, 1342177280);
/*  902 */       getFontRenderer().drawString(s1, l1, k, 553648127);
/*  903 */       getFontRenderer().drawString(s2, l - getFontRenderer().getStringWidth(s2), k, 553648127);
/*      */       
/*  905 */       if (j == collection.size()) {
/*      */         
/*  907 */         String s3 = objective.getDisplayName();
/*  908 */         drawRect(l1 - 2, k - (getFontRenderer()).FONT_HEIGHT - 1, l, k - 1, 1610612736);
/*  909 */         drawRect(l1 - 2, k - 1, l, k, 1342177280);
/*  910 */         getFontRenderer().drawString(s3, l1 + i / 2 - getFontRenderer().getStringWidth(s3) / 2, k - (getFontRenderer()).FONT_HEIGHT, 553648127);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderPlayerStats(ScaledResolution scaledRes) {
/*  917 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/*  919 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  920 */       int i = MathHelper.ceil(entityplayer.getHealth());
/*  921 */       boolean flag = (this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L);
/*      */       
/*  923 */       if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*      */         
/*  925 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  926 */         this.healthUpdateCounter = (this.updateCounter + 20);
/*      */       }
/*  928 */       else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*      */         
/*  930 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  931 */         this.healthUpdateCounter = (this.updateCounter + 10);
/*      */       } 
/*      */       
/*  934 */       if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
/*      */         
/*  936 */         this.playerHealth = i;
/*  937 */         this.lastPlayerHealth = i;
/*  938 */         this.lastSystemTime = Minecraft.getSystemTime();
/*      */       } 
/*      */       
/*  941 */       this.playerHealth = i;
/*  942 */       int j = this.lastPlayerHealth;
/*  943 */       this.rand.setSeed((this.updateCounter * 312871));
/*  944 */       FoodStats foodstats = entityplayer.getFoodStats();
/*  945 */       int k = foodstats.getFoodLevel();
/*  946 */       IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
/*  947 */       int l = ScaledResolution.getScaledWidth() / 2 - 91;
/*  948 */       int i1 = ScaledResolution.getScaledWidth() / 2 + 91;
/*  949 */       int j1 = ScaledResolution.getScaledHeight() - 39;
/*  950 */       float f = (float)iattributeinstance.getAttributeValue();
/*  951 */       int k1 = MathHelper.ceil(entityplayer.getAbsorptionAmount());
/*  952 */       int l1 = MathHelper.ceil((f + k1) / 2.0F / 10.0F);
/*  953 */       int i2 = Math.max(10 - l1 - 2, 3);
/*  954 */       int j2 = j1 - (l1 - 1) * i2 - 10;
/*  955 */       int k2 = j1 - 10;
/*  956 */       int l2 = k1;
/*  957 */       int i3 = entityplayer.getTotalArmorValue();
/*  958 */       int j3 = -1;
/*      */       
/*  960 */       if (entityplayer.isPotionActive(MobEffects.REGENERATION))
/*      */       {
/*  962 */         j3 = this.updateCounter % MathHelper.ceil(f + 5.0F);
/*      */       }
/*      */       
/*  965 */       this.mc.mcProfiler.startSection("armor");
/*      */       
/*  967 */       for (int k3 = 0; k3 < 10; k3++) {
/*      */         
/*  969 */         if (i3 > 0) {
/*      */           
/*  971 */           int l3 = l + k3 * 8;
/*      */           
/*  973 */           if (k3 * 2 + 1 < i3)
/*      */           {
/*  975 */             drawTexturedModalRect(l3, j2, 34, 9, 9, 9);
/*      */           }
/*      */           
/*  978 */           if (k3 * 2 + 1 == i3)
/*      */           {
/*  980 */             drawTexturedModalRect(l3, j2, 25, 9, 9, 9);
/*      */           }
/*      */           
/*  983 */           if (k3 * 2 + 1 > i3)
/*      */           {
/*  985 */             drawTexturedModalRect(l3, j2, 16, 9, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  990 */       this.mc.mcProfiler.endStartSection("health");
/*      */       
/*  992 */       for (int j5 = MathHelper.ceil((f + k1) / 2.0F) - 1; j5 >= 0; j5--) {
/*      */         
/*  994 */         int k5 = 16;
/*      */         
/*  996 */         if (entityplayer.isPotionActive(MobEffects.POISON)) {
/*      */           
/*  998 */           k5 += 36;
/*      */         }
/* 1000 */         else if (entityplayer.isPotionActive(MobEffects.WITHER)) {
/*      */           
/* 1002 */           k5 += 72;
/*      */         } 
/*      */         
/* 1005 */         int i4 = 0;
/*      */         
/* 1007 */         if (flag)
/*      */         {
/* 1009 */           i4 = 1;
/*      */         }
/*      */         
/* 1012 */         int j4 = MathHelper.ceil((j5 + 1) / 10.0F) - 1;
/* 1013 */         int k4 = l + j5 % 10 * 8;
/* 1014 */         int l4 = j1 - j4 * i2;
/*      */         
/* 1016 */         if (i <= 4)
/*      */         {
/* 1018 */           l4 += this.rand.nextInt(2);
/*      */         }
/*      */         
/* 1021 */         if (l2 <= 0 && j5 == j3)
/*      */         {
/* 1023 */           l4 -= 2;
/*      */         }
/*      */         
/* 1026 */         int i5 = 0;
/*      */         
/* 1028 */         if (entityplayer.world.getWorldInfo().isHardcoreModeEnabled())
/*      */         {
/* 1030 */           i5 = 5;
/*      */         }
/*      */         
/* 1033 */         drawTexturedModalRect(k4, l4, 16 + i4 * 9, 9 * i5, 9, 9);
/*      */         
/* 1035 */         if (flag) {
/*      */           
/* 1037 */           if (j5 * 2 + 1 < j)
/*      */           {
/* 1039 */             drawTexturedModalRect(k4, l4, k5 + 54, 9 * i5, 9, 9);
/*      */           }
/*      */           
/* 1042 */           if (j5 * 2 + 1 == j)
/*      */           {
/* 1044 */             drawTexturedModalRect(k4, l4, k5 + 63, 9 * i5, 9, 9);
/*      */           }
/*      */         } 
/*      */         
/* 1048 */         if (l2 > 0) {
/*      */           
/* 1050 */           if (l2 == k1 && k1 % 2 == 1)
/*      */           {
/* 1052 */             drawTexturedModalRect(k4, l4, k5 + 153, 9 * i5, 9, 9);
/* 1053 */             l2--;
/*      */           }
/*      */           else
/*      */           {
/* 1057 */             drawTexturedModalRect(k4, l4, k5 + 144, 9 * i5, 9, 9);
/* 1058 */             l2 -= 2;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1063 */           if (j5 * 2 + 1 < i)
/*      */           {
/* 1065 */             drawTexturedModalRect(k4, l4, k5 + 36, 9 * i5, 9, 9);
/*      */           }
/*      */           
/* 1068 */           if (j5 * 2 + 1 == i)
/*      */           {
/* 1070 */             drawTexturedModalRect(k4, l4, k5 + 45, 9 * i5, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1075 */       Entity entity = entityplayer.getRidingEntity();
/*      */       
/* 1077 */       if (entity == null || !(entity instanceof EntityLivingBase)) {
/*      */         
/* 1079 */         this.mc.mcProfiler.endStartSection("food");
/*      */         
/* 1081 */         for (int l5 = 0; l5 < 10; l5++) {
/*      */           
/* 1083 */           int j6 = j1;
/* 1084 */           int l6 = 16;
/* 1085 */           int j7 = 0;
/*      */           
/* 1087 */           if (entityplayer.isPotionActive(MobEffects.HUNGER)) {
/*      */             
/* 1089 */             l6 += 36;
/* 1090 */             j7 = 13;
/*      */           } 
/*      */           
/* 1093 */           if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0)
/*      */           {
/* 1095 */             j6 = j1 + this.rand.nextInt(3) - 1;
/*      */           }
/*      */           
/* 1098 */           int l7 = i1 - l5 * 8 - 9;
/* 1099 */           drawTexturedModalRect(l7, j6, 16 + j7 * 9, 27, 9, 9);
/*      */           
/* 1101 */           if (l5 * 2 + 1 < k)
/*      */           {
/* 1103 */             drawTexturedModalRect(l7, j6, l6 + 36, 27, 9, 9);
/*      */           }
/*      */           
/* 1106 */           if (l5 * 2 + 1 == k)
/*      */           {
/* 1108 */             drawTexturedModalRect(l7, j6, l6 + 45, 27, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1113 */       this.mc.mcProfiler.endStartSection("air");
/*      */       
/* 1115 */       if (entityplayer.isInsideOfMaterial(Material.WATER)) {
/*      */         
/* 1117 */         int i6 = this.mc.player.getAir();
/* 1118 */         int k6 = MathHelper.ceil((i6 - 2) * 10.0D / 300.0D);
/* 1119 */         int i7 = MathHelper.ceil(i6 * 10.0D / 300.0D) - k6;
/*      */         
/* 1121 */         for (int k7 = 0; k7 < k6 + i7; k7++) {
/*      */           
/* 1123 */           if (k7 < k6) {
/*      */             
/* 1125 */             drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 16, 18, 9, 9);
/*      */           }
/*      */           else {
/*      */             
/* 1129 */             drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 25, 18, 9, 9);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1134 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderMountHealth(ScaledResolution p_184047_1_) {
/* 1140 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/* 1142 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/* 1143 */       Entity entity = entityplayer.getRidingEntity();
/*      */       
/* 1145 */       if (entity instanceof EntityLivingBase) {
/*      */         
/* 1147 */         this.mc.mcProfiler.endStartSection("mountHealth");
/* 1148 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 1149 */         int i = (int)Math.ceil(entitylivingbase.getHealth());
/* 1150 */         float f = entitylivingbase.getMaxHealth();
/* 1151 */         int j = (int)(f + 0.5F) / 2;
/*      */         
/* 1153 */         if (j > 30)
/*      */         {
/* 1155 */           j = 30;
/*      */         }
/*      */         
/* 1158 */         int k = ScaledResolution.getScaledHeight() - 39;
/* 1159 */         int l = ScaledResolution.getScaledWidth() / 2 + 91;
/* 1160 */         int i1 = k;
/* 1161 */         int j1 = 0;
/*      */         
/* 1163 */         for (boolean flag = false; j > 0; j1 += 20) {
/*      */           
/* 1165 */           int k1 = Math.min(j, 10);
/* 1166 */           j -= k1;
/*      */           
/* 1168 */           for (int l1 = 0; l1 < k1; l1++) {
/*      */             
/* 1170 */             int i2 = 52;
/* 1171 */             int j2 = 0;
/* 1172 */             int k2 = l - l1 * 8 - 9;
/* 1173 */             drawTexturedModalRect(k2, i1, 52 + j2 * 9, 9, 9, 9);
/*      */             
/* 1175 */             if (l1 * 2 + 1 + j1 < i)
/*      */             {
/* 1177 */               drawTexturedModalRect(k2, i1, 88, 9, 9, 9);
/*      */             }
/*      */             
/* 1180 */             if (l1 * 2 + 1 + j1 == i)
/*      */             {
/* 1182 */               drawTexturedModalRect(k2, i1, 97, 9, 9, 9);
/*      */             }
/*      */           } 
/*      */           
/* 1186 */           i1 -= 10;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderPumpkinOverlay(ScaledResolution scaledRes) {
/* 1194 */     GlStateManager.disableDepth();
/* 1195 */     GlStateManager.depthMask(false);
/* 1196 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1197 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1198 */     GlStateManager.disableAlpha();
/* 1199 */     this.mc.getTextureManager().bindTexture(PUMPKIN_BLUR_TEX_PATH);
/* 1200 */     Tessellator tessellator = Tessellator.getInstance();
/* 1201 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1202 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1203 */     bufferbuilder.pos(0.0D, ScaledResolution.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1204 */     bufferbuilder.pos(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1205 */     bufferbuilder.pos(ScaledResolution.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1206 */     bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1207 */     tessellator.draw();
/* 1208 */     GlStateManager.depthMask(true);
/* 1209 */     GlStateManager.enableDepth();
/* 1210 */     GlStateManager.enableAlpha();
/* 1211 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderVignette(float lightLevel, ScaledResolution scaledRes) {
/* 1219 */     if (!Config.isVignetteEnabled()) {
/*      */       
/* 1221 */       GlStateManager.enableDepth();
/* 1222 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */     }
/*      */     else {
/*      */       
/* 1226 */       lightLevel = 1.0F - lightLevel;
/* 1227 */       lightLevel = MathHelper.clamp(lightLevel, 0.0F, 1.0F);
/* 1228 */       WorldBorder worldborder = this.mc.world.getWorldBorder();
/* 1229 */       float f = (float)worldborder.getClosestDistance((Entity)this.mc.player);
/* 1230 */       double d0 = Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000.0D, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
/* 1231 */       double d1 = Math.max(worldborder.getWarningDistance(), d0);
/*      */       
/* 1233 */       if (f < d1) {
/*      */         
/* 1235 */         f = 1.0F - (float)(f / d1);
/*      */       }
/*      */       else {
/*      */         
/* 1239 */         f = 0.0F;
/*      */       } 
/*      */       
/* 1242 */       this.prevVignetteBrightness = (float)(this.prevVignetteBrightness + (lightLevel - this.prevVignetteBrightness) * 0.01D);
/* 1243 */       GlStateManager.disableDepth();
/* 1244 */       GlStateManager.depthMask(false);
/* 1245 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */       
/* 1247 */       if (f > 0.0F) {
/*      */         
/* 1249 */         GlStateManager.color(0.0F, f, f, 1.0F);
/*      */       }
/*      */       else {
/*      */         
/* 1253 */         GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
/*      */       } 
/*      */       
/* 1256 */       this.mc.getTextureManager().bindTexture(VIGNETTE_TEX_PATH);
/* 1257 */       Tessellator tessellator = Tessellator.getInstance();
/* 1258 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1259 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1260 */       bufferbuilder.pos(0.0D, ScaledResolution.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1261 */       bufferbuilder.pos(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1262 */       bufferbuilder.pos(ScaledResolution.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1263 */       bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1264 */       tessellator.draw();
/* 1265 */       GlStateManager.depthMask(true);
/* 1266 */       GlStateManager.enableDepth();
/* 1267 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1268 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderPortal(float timeInPortal, ScaledResolution scaledRes) {
/* 1274 */     if (timeInPortal < 1.0F) {
/*      */       
/* 1276 */       timeInPortal *= timeInPortal;
/* 1277 */       timeInPortal *= timeInPortal;
/* 1278 */       timeInPortal = timeInPortal * 0.8F + 0.2F;
/*      */     } 
/*      */     
/* 1281 */     GlStateManager.disableAlpha();
/* 1282 */     GlStateManager.disableDepth();
/* 1283 */     GlStateManager.depthMask(false);
/* 1284 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1285 */     GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
/* 1286 */     this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 1287 */     TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.PORTAL.getDefaultState());
/* 1288 */     float f = textureatlassprite.getMinU();
/* 1289 */     float f1 = textureatlassprite.getMinV();
/* 1290 */     float f2 = textureatlassprite.getMaxU();
/* 1291 */     float f3 = textureatlassprite.getMaxV();
/* 1292 */     Tessellator tessellator = Tessellator.getInstance();
/* 1293 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1294 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1295 */     bufferbuilder.pos(0.0D, ScaledResolution.getScaledHeight(), -90.0D).tex(f, f3).endVertex();
/* 1296 */     bufferbuilder.pos(ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), -90.0D).tex(f2, f3).endVertex();
/* 1297 */     bufferbuilder.pos(ScaledResolution.getScaledWidth(), 0.0D, -90.0D).tex(f2, f1).endVertex();
/* 1298 */     bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
/* 1299 */     tessellator.draw();
/* 1300 */     GlStateManager.depthMask(true);
/* 1301 */     GlStateManager.enableDepth();
/* 1302 */     GlStateManager.enableAlpha();
/* 1303 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderHotbarItem(int p_184044_1_, int p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack) {
/* 1308 */     if (!stack.func_190926_b()) {
/*      */       
/* 1310 */       float f = stack.func_190921_D() - p_184044_3_;
/*      */       
/* 1312 */       if (f > 0.0F) {
/*      */         
/* 1314 */         GlStateManager.pushMatrix();
/* 1315 */         float f1 = 1.0F + f / 5.0F;
/* 1316 */         GlStateManager.translate((p_184044_1_ + 8), (p_184044_2_ + 12), 0.0F);
/* 1317 */         GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
/* 1318 */         GlStateManager.translate(-(p_184044_1_ + 8), -(p_184044_2_ + 12), 0.0F);
/*      */       } 
/*      */       
/* 1321 */       this.itemRenderer.renderItemAndEffectIntoGUI((EntityLivingBase)player, stack, p_184044_1_, p_184044_2_);
/*      */       
/* 1323 */       if (f > 0.0F)
/*      */       {
/* 1325 */         GlStateManager.popMatrix();
/*      */       }
/*      */       
/* 1328 */       this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, stack, p_184044_1_, p_184044_2_);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick() {
/* 1337 */     if (this.mc.world == null)
/*      */     {
/* 1339 */       TextureAnimations.updateAnimations();
/*      */     }
/*      */     
/* 1342 */     if (this.recordPlayingUpFor > 0)
/*      */     {
/* 1344 */       this.recordPlayingUpFor--;
/*      */     }
/*      */     
/* 1347 */     if (this.titlesTimer > 0) {
/*      */       
/* 1349 */       this.titlesTimer--;
/*      */       
/* 1351 */       if (this.titlesTimer <= 0) {
/*      */         
/* 1353 */         this.displayedTitle = "";
/* 1354 */         this.displayedSubTitle = "";
/*      */       } 
/*      */     } 
/*      */     
/* 1358 */     this.updateCounter++;
/*      */     
/* 1360 */     if (this.mc.player != null) {
/*      */       
/* 1362 */       ItemStack itemstack = this.mc.player.inventory.getCurrentItem();
/*      */       
/* 1364 */       if (itemstack.func_190926_b()) {
/*      */         
/* 1366 */         this.remainingHighlightTicks = 0;
/*      */       }
/* 1368 */       else if (!this.highlightingItemStack.func_190926_b() && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
/*      */         
/* 1370 */         if (this.remainingHighlightTicks > 0)
/*      */         {
/* 1372 */           this.remainingHighlightTicks--;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1377 */         this.remainingHighlightTicks = 40;
/*      */       } 
/*      */       
/* 1380 */       this.highlightingItemStack = itemstack;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlayingMessage(String recordName) {
/* 1386 */     setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { recordName }), true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlaying(String message, boolean isPlaying) {
/* 1391 */     this.recordPlaying = message;
/* 1392 */     this.recordPlayingUpFor = 60;
/* 1393 */     this.recordIsPlaying = isPlaying;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayTitle(String title, String subTitle, int timeFadeIn, int displayTime, int timeFadeOut) {
/* 1398 */     if (title == null && subTitle == null && timeFadeIn < 0 && displayTime < 0 && timeFadeOut < 0) {
/*      */       
/* 1400 */       this.displayedTitle = "";
/* 1401 */       this.displayedSubTitle = "";
/* 1402 */       this.titlesTimer = 0;
/*      */     }
/* 1404 */     else if (title != null) {
/*      */       
/* 1406 */       this.displayedTitle = title;
/* 1407 */       this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
/*      */     }
/* 1409 */     else if (subTitle != null) {
/*      */       
/* 1411 */       this.displayedSubTitle = subTitle;
/*      */     }
/*      */     else {
/*      */       
/* 1415 */       if (timeFadeIn >= 0)
/*      */       {
/* 1417 */         this.titleFadeIn = timeFadeIn;
/*      */       }
/*      */       
/* 1420 */       if (displayTime >= 0)
/*      */       {
/* 1422 */         this.titleDisplayTime = displayTime;
/*      */       }
/*      */       
/* 1425 */       if (timeFadeOut >= 0)
/*      */       {
/* 1427 */         this.titleFadeOut = timeFadeOut;
/*      */       }
/*      */       
/* 1430 */       if (this.titlesTimer > 0)
/*      */       {
/* 1432 */         this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlaying(ITextComponent component, boolean isPlaying) {
/* 1439 */     setRecordPlaying(component.getUnformattedText(), isPlaying);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191742_a(ChatType p_191742_1_, ITextComponent p_191742_2_) {
/* 1444 */     for (IChatListener ichatlistener : this.field_191743_I.get(p_191742_1_))
/*      */     {
/* 1446 */       ichatlistener.func_192576_a(p_191742_1_, p_191742_2_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiNewChat getChatGUI() {
/* 1455 */     return this.persistantChatGUI;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUpdateCounter() {
/* 1460 */     return this.updateCounter;
/*      */   }
/*      */ 
/*      */   
/*      */   public FontRenderer getFontRenderer() {
/* 1465 */     return this.mc.fontRendererObj;
/*      */   }
/*      */ 
/*      */   
/*      */   public GuiSpectator getSpectatorGui() {
/* 1470 */     return this.spectatorGui;
/*      */   }
/*      */ 
/*      */   
/*      */   public GuiPlayerTabOverlay getTabList() {
/* 1475 */     return this.overlayPlayerList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetPlayersOverlayFooterHeader() {
/* 1483 */     this.overlayPlayerList.resetFooterHeader();
/* 1484 */     this.overlayBoss.clearBossInfos();
/* 1485 */     this.mc.func_193033_an().func_191788_b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiBossOverlay getBossOverlay() {
/* 1493 */     return this.overlayBoss;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */