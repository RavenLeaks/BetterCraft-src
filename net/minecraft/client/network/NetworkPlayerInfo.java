/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.client.resources.SkinManager;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkPlayerInfo
/*     */ {
/*     */   private final GameProfile gameProfile;
/*  25 */   Map<MinecraftProfileTexture.Type, ResourceLocation> playerTextures = Maps.newEnumMap(MinecraftProfileTexture.Type.class);
/*     */   
/*     */   private GameType gameType;
/*     */   
/*     */   private int responseTime;
/*     */   
/*     */   private boolean playerTexturesLoaded;
/*     */   
/*     */   private String skinType;
/*     */   
/*     */   private ITextComponent displayName;
/*     */   
/*     */   private int lastHealth;
/*     */   
/*     */   private int displayHealth;
/*     */   private long lastHealthTime;
/*     */   private long healthBlinkTime;
/*     */   private long renderVisibilityId;
/*     */   
/*     */   public NetworkPlayerInfo(GameProfile profile) {
/*  45 */     this.gameProfile = profile;
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkPlayerInfo(SPacketPlayerListItem.AddPlayerData entry) {
/*  50 */     this.gameProfile = entry.getProfile();
/*  51 */     this.gameType = entry.getGameMode();
/*  52 */     this.responseTime = entry.getPing();
/*  53 */     this.displayName = entry.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getGameProfile() {
/*  61 */     return this.gameProfile;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/*  66 */     return this.gameType;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setGameType(GameType gameMode) {
/*  71 */     this.gameType = gameMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getResponseTime() {
/*  76 */     return this.responseTime;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setResponseTime(int latency) {
/*  81 */     this.responseTime = latency;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasLocationSkin() {
/*  86 */     return (getLocationSkin() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSkinType() {
/*  91 */     return (this.skinType == null) ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  96 */     loadPlayerTextures();
/*  97 */     return (ResourceLocation)MoreObjects.firstNonNull(this.playerTextures.get(MinecraftProfileTexture.Type.SKIN), DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLocationCape() {
/* 103 */     loadPlayerTextures();
/* 104 */     return this.playerTextures.get(MinecraftProfileTexture.Type.CAPE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLocationElytra() {
/* 114 */     loadPlayerTextures();
/* 115 */     return this.playerTextures.get(MinecraftProfileTexture.Type.ELYTRA);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ScorePlayerTeam getPlayerTeam() {
/* 121 */     return (Minecraft.getMinecraft()).world.getScoreboard().getPlayersTeam(getGameProfile().getName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadPlayerTextures() {
/* 126 */     synchronized (this) {
/*     */       
/* 128 */       if (!this.playerTexturesLoaded) {
/*     */         
/* 130 */         this.playerTexturesLoaded = true;
/* 131 */         Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.gameProfile, new SkinManager.SkinAvailableCallback()
/*     */             {
/*     */               public void skinAvailable(MinecraftProfileTexture.Type typeIn, ResourceLocation location, MinecraftProfileTexture profileTexture)
/*     */               {
/* 135 */                 switch (typeIn) {
/*     */                   
/*     */                   case SKIN:
/* 138 */                     NetworkPlayerInfo.this.playerTextures.put(MinecraftProfileTexture.Type.SKIN, location);
/* 139 */                     NetworkPlayerInfo.this.skinType = profileTexture.getMetadata("model");
/*     */                     
/* 141 */                     if (NetworkPlayerInfo.this.skinType == null)
/*     */                     {
/* 143 */                       NetworkPlayerInfo.this.skinType = "default";
/*     */                     }
/*     */                     break;
/*     */ 
/*     */                   
/*     */                   case null:
/* 149 */                     NetworkPlayerInfo.this.playerTextures.put(MinecraftProfileTexture.Type.CAPE, location);
/*     */                     break;
/*     */                   
/*     */                   case ELYTRA:
/* 153 */                     NetworkPlayerInfo.this.playerTextures.put(MinecraftProfileTexture.Type.ELYTRA, location);
/*     */                     break;
/*     */                 }  }
/* 156 */             }true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisplayName(@Nullable ITextComponent displayNameIn) {
/* 163 */     this.displayName = displayNameIn;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ITextComponent getDisplayName() {
/* 169 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastHealth() {
/* 174 */     return this.lastHealth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastHealth(int p_178836_1_) {
/* 179 */     this.lastHealth = p_178836_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayHealth() {
/* 184 */     return this.displayHealth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisplayHealth(int p_178857_1_) {
/* 189 */     this.displayHealth = p_178857_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLastHealthTime() {
/* 194 */     return this.lastHealthTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastHealthTime(long p_178846_1_) {
/* 199 */     this.lastHealthTime = p_178846_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getHealthBlinkTime() {
/* 204 */     return this.healthBlinkTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHealthBlinkTime(long p_178844_1_) {
/* 209 */     this.healthBlinkTime = p_178844_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getRenderVisibilityId() {
/* 214 */     return this.renderVisibilityId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderVisibilityId(long p_178843_1_) {
/* 219 */     this.renderVisibilityId = p_178843_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\network\NetworkPlayerInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */