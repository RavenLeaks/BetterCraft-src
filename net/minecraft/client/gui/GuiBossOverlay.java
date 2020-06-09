/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.network.play.server.SPacketUpdateBossInfo;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.BossInfo;
/*     */ 
/*     */ public class GuiBossOverlay
/*     */   extends Gui {
/*  14 */   private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
/*     */   private final Minecraft client;
/*  16 */   private final Map<UUID, BossInfoClient> mapBossInfos = Maps.newLinkedHashMap();
/*     */ 
/*     */   
/*     */   public GuiBossOverlay(Minecraft clientIn) {
/*  20 */     this.client = clientIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBossHealth() {
/*  25 */     if (!this.mapBossInfos.isEmpty()) {
/*     */       
/*  27 */       ScaledResolution scaledresolution = new ScaledResolution(this.client);
/*  28 */       int i = ScaledResolution.getScaledWidth();
/*  29 */       int j = 12;
/*     */       
/*  31 */       for (BossInfoClient bossinfoclient : this.mapBossInfos.values()) {
/*     */         
/*  33 */         int k = i / 2 - 91;
/*  34 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  35 */         this.client.getTextureManager().bindTexture(GUI_BARS_TEXTURES);
/*  36 */         render(k, j, bossinfoclient);
/*  37 */         String s = bossinfoclient.getName().getFormattedText();
/*  38 */         this.client.fontRendererObj.drawStringWithShadow(s, (i / 2 - this.client.fontRendererObj.getStringWidth(s) / 2), (j - 9), 16777215);
/*  39 */         j += 10 + this.client.fontRendererObj.FONT_HEIGHT;
/*     */         
/*  41 */         if (j >= ScaledResolution.getScaledHeight() / 3) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void render(int x, int y, BossInfo info) {
/*  51 */     drawTexturedModalRect(x, y, 0, info.getColor().ordinal() * 5 * 2, 182, 5);
/*     */     
/*  53 */     if (info.getOverlay() != BossInfo.Overlay.PROGRESS)
/*     */     {
/*  55 */       drawTexturedModalRect(x, y, 0, 80 + (info.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
/*     */     }
/*     */     
/*  58 */     int i = (int)(info.getPercent() * 183.0F);
/*     */     
/*  60 */     if (i > 0) {
/*     */       
/*  62 */       drawTexturedModalRect(x, y, 0, info.getColor().ordinal() * 5 * 2 + 5, i, 5);
/*     */       
/*  64 */       if (info.getOverlay() != BossInfo.Overlay.PROGRESS)
/*     */       {
/*  66 */         drawTexturedModalRect(x, y, 0, 80 + (info.getOverlay().ordinal() - 1) * 5 * 2 + 5, i, 5);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(SPacketUpdateBossInfo packetIn) {
/*  73 */     if (packetIn.getOperation() == SPacketUpdateBossInfo.Operation.ADD) {
/*     */       
/*  75 */       this.mapBossInfos.put(packetIn.getUniqueId(), new BossInfoClient(packetIn));
/*     */     }
/*  77 */     else if (packetIn.getOperation() == SPacketUpdateBossInfo.Operation.REMOVE) {
/*     */       
/*  79 */       this.mapBossInfos.remove(packetIn.getUniqueId());
/*     */     }
/*     */     else {
/*     */       
/*  83 */       ((BossInfoClient)this.mapBossInfos.get(packetIn.getUniqueId())).updateFromPacket(packetIn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearBossInfos() {
/*  89 */     this.mapBossInfos.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldPlayEndBossMusic() {
/*  94 */     if (!this.mapBossInfos.isEmpty())
/*     */     {
/*  96 */       for (BossInfo bossinfo : this.mapBossInfos.values()) {
/*     */         
/*  98 */         if (bossinfo.shouldPlayEndBossMusic())
/*     */         {
/* 100 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDarkenSky() {
/* 110 */     if (!this.mapBossInfos.isEmpty())
/*     */     {
/* 112 */       for (BossInfo bossinfo : this.mapBossInfos.values()) {
/*     */         
/* 114 */         if (bossinfo.shouldDarkenSky())
/*     */         {
/* 116 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCreateFog() {
/* 126 */     if (!this.mapBossInfos.isEmpty())
/*     */     {
/* 128 */       for (BossInfo bossinfo : this.mapBossInfos.values()) {
/*     */         
/* 130 */         if (bossinfo.shouldCreateFog())
/*     */         {
/* 132 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 137 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\GuiBossOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */