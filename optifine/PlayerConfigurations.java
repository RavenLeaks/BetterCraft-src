/*    */ package optifine;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ 
/*    */ public class PlayerConfigurations {
/* 11 */   private static Map mapConfigurations = null;
/* 12 */   private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
/* 13 */   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public static void renderPlayerItems(ModelBiped p_renderPlayerItems_0_, AbstractClientPlayer p_renderPlayerItems_1_, float p_renderPlayerItems_2_, float p_renderPlayerItems_3_) {
/* 17 */     PlayerConfiguration playerconfiguration = getPlayerConfiguration(p_renderPlayerItems_1_);
/*    */     
/* 19 */     if (playerconfiguration != null)
/*    */     {
/* 21 */       playerconfiguration.renderPlayerItems(p_renderPlayerItems_0_, p_renderPlayerItems_1_, p_renderPlayerItems_2_, p_renderPlayerItems_3_);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer p_getPlayerConfiguration_0_) {
/* 27 */     if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
/*    */       
/* 29 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).player;
/*    */       
/* 31 */       if (entityPlayerSP != null) {
/*    */         
/* 33 */         setPlayerConfiguration(entityPlayerSP.getNameClear(), null);
/* 34 */         timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */       } 
/*    */     } 
/*    */     
/* 38 */     String s1 = p_getPlayerConfiguration_0_.getNameClear();
/*    */     
/* 40 */     if (s1 == null)
/*    */     {
/* 42 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 46 */     PlayerConfiguration playerconfiguration = (PlayerConfiguration)getMapConfigurations().get(s1);
/*    */     
/* 48 */     if (playerconfiguration == null) {
/*    */       
/* 50 */       playerconfiguration = new PlayerConfiguration();
/* 51 */       getMapConfigurations().put(s1, playerconfiguration);
/* 52 */       PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s1);
/* 53 */       String s = String.valueOf(HttpUtils.getPlayerItemsUrl()) + "/users/" + s1 + ".cfg";
/* 54 */       FileDownloadThread filedownloadthread = new FileDownloadThread(s, playerconfigurationreceiver);
/* 55 */       filedownloadthread.start();
/*    */     } 
/*    */     
/* 58 */     return playerconfiguration;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void setPlayerConfiguration(String p_setPlayerConfiguration_0_, PlayerConfiguration p_setPlayerConfiguration_1_) {
/* 64 */     getMapConfigurations().put(p_setPlayerConfiguration_0_, p_setPlayerConfiguration_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   private static Map getMapConfigurations() {
/* 69 */     if (mapConfigurations == null)
/*    */     {
/* 71 */       mapConfigurations = new HashMap<>();
/*    */     }
/*    */     
/* 74 */     return mapConfigurations;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\PlayerConfigurations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */