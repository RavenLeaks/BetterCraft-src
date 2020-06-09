/*     */ package wdl;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CapeHandler
/*     */ {
/*  25 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final Map<UUID, ResourceLocation> capes = new HashMap<>();
/*     */   
/*  35 */   private static final Set<EntityPlayer> handledPlayers = new HashSet<>();
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final Map<EntityPlayer, Integer> playerFailures = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static int totalFailures = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_PLAYER_FAILURES = 40;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_TOTAL_FAILURES = 40;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  59 */     capes.put(UUID.fromString("6c8976e3-99a9-4d8b-a98e-d4c0c09b305b"), 
/*  60 */         new ResourceLocation("wdl", "textures/cape_dev.png"));
/*     */     
/*  62 */     capes.put(UUID.fromString("f6c068f1-0738-4b41-bdb2-69d81d2b0f1c"), 
/*  63 */         new ResourceLocation("wdl", "textures/cape_dev.png"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onWorldTick(List<EntityPlayer> players) {
/*  68 */     if (totalFailures > 40) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  73 */       handledPlayers.retainAll(players);
/*     */       
/*  75 */       for (EntityPlayer player : players) {
/*  76 */         if (handledPlayers.contains(player)) {
/*     */           continue;
/*     */         }
/*     */         
/*  80 */         if (player instanceof AbstractClientPlayer) {
/*  81 */           setupPlayer((AbstractClientPlayer)player);
/*     */         }
/*     */       } 
/*  84 */     } catch (Exception e) {
/*  85 */       logger.warn("[WDL] Failed to tick cape setup", e);
/*  86 */       totalFailures++;
/*     */       
/*  88 */       if (totalFailures > 40) {
/*  89 */         logger.warn("[WDL] Disabling cape system (too many failures)");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setupPlayer(AbstractClientPlayer player) {
/*     */     try {
/*  96 */       NetworkPlayerInfo info = 
/*  97 */         ReflectionUtils.<NetworkPlayerInfo>stealAndGetField(player, 
/*  98 */           AbstractClientPlayer.class, 
/*  99 */           NetworkPlayerInfo.class);
/*     */       
/* 101 */       if (info == null) {
/* 102 */         incrementFailure((EntityPlayer)player);
/*     */         
/*     */         return;
/*     */       } 
/* 106 */       GameProfile profile = info.getGameProfile();
/*     */       
/* 108 */       if (capes.containsKey(profile.getId())) {
/* 109 */         setPlayerCape(info, capes.get(profile.getId()));
/*     */       }
/*     */       
/* 112 */       handledPlayers.add(player);
/* 113 */     } catch (Exception e) {
/* 114 */       logger.warn("[WDL] Failed to perform cape set up for " + player, e);
/* 115 */       incrementFailure((EntityPlayer)player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setPlayerCape(NetworkPlayerInfo info, ResourceLocation cape) throws Exception {
/* 122 */     Map<MinecraftProfileTexture.Type, ResourceLocation> map = 
/* 123 */       ReflectionUtils.<Map<MinecraftProfileTexture.Type, ResourceLocation>>stealAndGetField(info, (Class)Map.class);
/* 124 */     if (!map.containsKey(MinecraftProfileTexture.Type.CAPE)) {
/* 125 */       map.put(MinecraftProfileTexture.Type.CAPE, cape);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void incrementFailure(EntityPlayer player) {
/* 133 */     if (playerFailures.containsKey(player)) {
/* 134 */       int numFailures = ((Integer)playerFailures.get(player)).intValue() + 1;
/* 135 */       playerFailures.put(player, Integer.valueOf(numFailures));
/*     */       
/* 137 */       if (numFailures > 40) {
/* 138 */         handledPlayers.add(player);
/* 139 */         playerFailures.remove(player);
/* 140 */         logger.warn("[WDL] Failed to set up cape for " + player + 
/* 141 */             " too many times (" + numFailures + "); skipping them");
/*     */       } 
/*     */     } else {
/* 144 */       playerFailures.put(player, Integer.valueOf(1));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\CapeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */