/*     */ package net.minecraft.client.main;
/*     */ 
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.io.File;
/*     */ import java.net.Proxy;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.resources.ResourceIndex;
/*     */ import net.minecraft.client.resources.ResourceIndexFolder;
/*     */ import net.minecraft.util.Session;
/*     */ 
/*     */ 
/*     */ public class GameConfiguration
/*     */ {
/*     */   public final UserInformation userInfo;
/*     */   public final DisplayInformation displayInfo;
/*     */   public final FolderInformation folderInfo;
/*     */   public final GameInformation gameInfo;
/*     */   public final ServerInformation serverInfo;
/*     */   
/*     */   public GameConfiguration(UserInformation userInfoIn, DisplayInformation displayInfoIn, FolderInformation folderInfoIn, GameInformation gameInfoIn, ServerInformation serverInfoIn) {
/*  21 */     this.userInfo = userInfoIn;
/*  22 */     this.displayInfo = displayInfoIn;
/*  23 */     this.folderInfo = folderInfoIn;
/*  24 */     this.gameInfo = gameInfoIn;
/*  25 */     this.serverInfo = serverInfoIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DisplayInformation
/*     */   {
/*     */     public final int width;
/*     */     public final int height;
/*     */     public final boolean fullscreen;
/*     */     public final boolean checkGlErrors;
/*     */     
/*     */     public DisplayInformation(int widthIn, int heightIn, boolean fullscreenIn, boolean checkGlErrorsIn) {
/*  37 */       this.width = widthIn;
/*  38 */       this.height = heightIn;
/*  39 */       this.fullscreen = fullscreenIn;
/*  40 */       this.checkGlErrors = checkGlErrorsIn;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FolderInformation
/*     */   {
/*     */     public final File mcDataDir;
/*     */     public final File resourcePacksDir;
/*     */     public final File assetsDir;
/*     */     public final String assetIndex;
/*     */     
/*     */     public FolderInformation(File mcDataDirIn, File resourcePacksDirIn, File assetsDirIn, @Nullable String assetIndexIn) {
/*  53 */       this.mcDataDir = mcDataDirIn;
/*  54 */       this.resourcePacksDir = resourcePacksDirIn;
/*  55 */       this.assetsDir = assetsDirIn;
/*  56 */       this.assetIndex = assetIndexIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceIndex getAssetsIndex() {
/*  61 */       return (this.assetIndex == null) ? (ResourceIndex)new ResourceIndexFolder(this.assetsDir) : new ResourceIndex(this.assetsDir, this.assetIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GameInformation
/*     */   {
/*     */     public final boolean isDemo;
/*     */     public final String version;
/*     */     public final String versionType;
/*     */     
/*     */     public GameInformation(boolean demo, String versionIn, String versionTypeIn) {
/*  73 */       this.isDemo = demo;
/*  74 */       this.version = versionIn;
/*  75 */       this.versionType = versionTypeIn;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ServerInformation
/*     */   {
/*     */     public final String serverName;
/*     */     public final int serverPort;
/*     */     
/*     */     public ServerInformation(String serverNameIn, int serverPortIn) {
/*  86 */       this.serverName = serverNameIn;
/*  87 */       this.serverPort = serverPortIn;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class UserInformation
/*     */   {
/*     */     public final Session session;
/*     */     public final PropertyMap userProperties;
/*     */     public final PropertyMap profileProperties;
/*     */     public final Proxy proxy;
/*     */     
/*     */     public UserInformation(Session sessionIn, PropertyMap userPropertiesIn, PropertyMap profilePropertiesIn, Proxy proxyIn) {
/* 100 */       this.session = sessionIn;
/* 101 */       this.userProperties = userPropertiesIn;
/* 102 */       this.profileProperties = profilePropertiesIn;
/* 103 */       this.proxy = proxyIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\main\GameConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */