/*     */ package net.minecraft.realms;
/*     */ 
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.net.Proxy;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.Session;
/*     */ import net.minecraft.world.GameType;
/*     */ 
/*     */ 
/*     */ public class Realms
/*     */ {
/*     */   public static boolean isTouchScreen() {
/*  22 */     return (Minecraft.getMinecraft()).gameSettings.touchscreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Proxy getProxy() {
/*  27 */     return Minecraft.getMinecraft().getProxy();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sessionId() {
/*  32 */     Minecraft.getMinecraft(); Session session = Minecraft.getSession();
/*  33 */     return (session == null) ? null : session.getSessionID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String userName() {
/*  38 */     Minecraft.getMinecraft(); Session session = Minecraft.getSession();
/*  39 */     return (session == null) ? null : session.getUsername();
/*     */   }
/*     */ 
/*     */   
/*     */   public static long currentTimeMillis() {
/*  44 */     return Minecraft.getSystemTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSessionId() {
/*  49 */     Minecraft.getMinecraft(); return Minecraft.getSession().getSessionID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getUUID() {
/*  54 */     Minecraft.getMinecraft(); return Minecraft.getSession().getPlayerID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getName() {
/*  59 */     Minecraft.getMinecraft(); return Minecraft.getSession().getUsername();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String uuidToName(String p_uuidToName_0_) {
/*  64 */     return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), null), false).getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setScreen(RealmsScreen p_setScreen_0_) {
/*  69 */     Minecraft.getMinecraft().displayGuiScreen((GuiScreen)p_setScreen_0_.getProxy());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getGameDirectoryPath() {
/*  74 */     return (Minecraft.getMinecraft()).mcDataDir.getAbsolutePath();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int survivalId() {
/*  79 */     return GameType.SURVIVAL.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int creativeId() {
/*  84 */     return GameType.CREATIVE.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int adventureId() {
/*  89 */     return GameType.ADVENTURE.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int spectatorId() {
/*  94 */     return GameType.SPECTATOR.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setConnectedToRealms(boolean p_setConnectedToRealms_0_) {
/*  99 */     Minecraft.getMinecraft().setConnectedToRealms(p_setConnectedToRealms_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListenableFuture<Object> downloadResourcePack(String p_downloadResourcePack_0_, String p_downloadResourcePack_1_) {
/* 104 */     return Minecraft.getMinecraft().getResourcePackRepository().downloadResourcePack(p_downloadResourcePack_0_, p_downloadResourcePack_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearResourcePack() {
/* 109 */     Minecraft.getMinecraft().getResourcePackRepository().clearResourcePack();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getRealmsNotificationsEnabled() {
/* 114 */     return (Minecraft.getMinecraft()).gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean inTitleScreen() {
/* 119 */     return ((Minecraft.getMinecraft()).currentScreen != null && (Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiMainMenu);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deletePlayerTag(File p_deletePlayerTag_0_) {
/* 124 */     if (p_deletePlayerTag_0_.exists())
/*     */       
/*     */       try {
/*     */         
/* 128 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(p_deletePlayerTag_0_));
/* 129 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 130 */         nbttagcompound1.removeTag("Player");
/* 131 */         CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(p_deletePlayerTag_0_));
/*     */       }
/* 133 */       catch (Exception exception) {
/*     */         
/* 135 */         exception.printStackTrace();
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\Realms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */