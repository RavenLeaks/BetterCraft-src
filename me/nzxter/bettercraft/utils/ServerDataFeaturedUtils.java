/*    */ package me.nzxter.bettercraft.utils;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ServerDataFeaturedUtils
/*    */   extends ServerData {
/*  8 */   public static final ResourceLocation STAR_ICON = new ResourceLocation("textures/misc/star.png");
/*    */   
/*    */   public ServerDataFeaturedUtils(String serverName, String serverIP) {
/* 11 */     super(serverName, serverIP, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraf\\utils\ServerDataFeaturedUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */