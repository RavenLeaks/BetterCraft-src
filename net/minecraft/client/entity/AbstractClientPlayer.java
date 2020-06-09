/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.World;
/*     */ import optifine.CapeUtils;
/*     */ import optifine.Config;
/*     */ import optifine.PlayerConfigurations;
/*     */ import optifine.Reflector;
/*     */ 
/*     */ public abstract class AbstractClientPlayer
/*     */   extends EntityPlayer {
/*     */   private NetworkPlayerInfo playerInfo;
/*     */   public float rotateElytraX;
/*     */   public float rotateElytraY;
/*     */   public float rotateElytraZ;
/*  32 */   private ResourceLocation locationOfCape = null;
/*  33 */   private String nameClear = null;
/*  34 */   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
/*     */ 
/*     */   
/*     */   public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
/*  38 */     super(worldIn, playerProfile);
/*  39 */     this.nameClear = playerProfile.getName();
/*     */     
/*  41 */     if (this.nameClear != null && !this.nameClear.isEmpty())
/*     */     {
/*  43 */       this.nameClear = StringUtils.stripControlCodes(this.nameClear);
/*     */     }
/*     */     
/*  46 */     CapeUtils.downloadCape(this);
/*  47 */     PlayerConfigurations.getPlayerConfiguration(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  55 */     NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(getGameProfile().getId());
/*  56 */     return (networkplayerinfo != null && networkplayerinfo.getGameType() == GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCreative() {
/*  61 */     NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(getGameProfile().getId());
/*  62 */     return (networkplayerinfo != null && networkplayerinfo.getGameType() == GameType.CREATIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInfo() {
/*  70 */     return (getPlayerInfo() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected NetworkPlayerInfo getPlayerInfo() {
/*  76 */     if (this.playerInfo == null)
/*     */     {
/*  78 */       this.playerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(getUniqueID());
/*     */     }
/*     */     
/*  81 */     return this.playerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSkin() {
/*  89 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  90 */     return (networkplayerinfo != null && networkplayerinfo.hasLocationSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  98 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  99 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(getUniqueID()) : networkplayerinfo.getLocationSkin();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLocationCape() {
/* 105 */     if (!Config.isShowCapes())
/*     */     {
/* 107 */       return null;
/*     */     }
/* 109 */     if (this.locationOfCape != null)
/*     */     {
/* 111 */       return this.locationOfCape;
/*     */     }
/*     */ 
/*     */     
/* 115 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 116 */     return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationCape();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlayerInfoSet() {
/* 122 */     return (getPlayerInfo() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLocationElytra() {
/* 132 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 133 */     return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationElytra();
/*     */   }
/*     */   
/*     */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
/*     */     ThreadDownloadImageData threadDownloadImageData;
/* 138 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 139 */     ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);
/*     */     
/* 141 */     if (itextureobject == null) {
/*     */       
/* 143 */       threadDownloadImageData = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(username) }), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), (IImageBuffer)new ImageBufferDownload());
/* 144 */       texturemanager.loadTexture(resourceLocationIn, (ITextureObject)threadDownloadImageData);
/*     */     } 
/*     */     
/* 147 */     return threadDownloadImageData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getLocationSkin(String username) {
/* 155 */     return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSkinType() {
/* 160 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 161 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(getUniqueID()) : networkplayerinfo.getSkinType();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFovModifier() {
/* 166 */     float f = 1.0F;
/*     */     
/* 168 */     if (this.capabilities.isFlying)
/*     */     {
/* 170 */       f *= 1.1F;
/*     */     }
/*     */     
/* 173 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/* 174 */     f = (float)(f * (iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
/*     */     
/* 176 */     if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
/*     */     {
/* 178 */       f = 1.0F;
/*     */     }
/*     */     
/* 181 */     if (isHandActive() && getActiveItemStack().getItem() == Items.BOW) {
/*     */       
/* 183 */       int i = getItemInUseMaxCount();
/* 184 */       float f1 = i / 20.0F;
/*     */       
/* 186 */       if (f1 > 1.0F) {
/*     */         
/* 188 */         f1 = 1.0F;
/*     */       }
/*     */       else {
/*     */         
/* 192 */         f1 *= f1;
/*     */       } 
/*     */       
/* 195 */       f *= 1.0F - f1 * 0.15F;
/*     */     } 
/*     */     
/* 198 */     return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, new Object[] { this, Float.valueOf(f) }) : f;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameClear() {
/* 203 */     return this.nameClear;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationOfCape() {
/* 208 */     return this.locationOfCape;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocationOfCape(ResourceLocation p_setLocationOfCape_1_) {
/* 213 */     this.locationOfCape = p_setLocationOfCape_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasElytraCape() {
/* 218 */     ResourceLocation resourcelocation = getLocationCape();
/*     */     
/* 220 */     if (resourcelocation == null)
/*     */     {
/* 222 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 226 */     return (resourcelocation != this.locationOfCape);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\entity\AbstractClientPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */