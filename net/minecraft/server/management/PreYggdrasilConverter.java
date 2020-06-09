/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.ProfileLookupCallback;
/*    */ import java.io.File;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.StringUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class PreYggdrasilConverter {
/* 22 */   private static final Logger LOGGER = LogManager.getLogger();
/* 23 */   public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
/* 24 */   public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
/* 25 */   public static final File OLD_OPS_FILE = new File("ops.txt");
/* 26 */   public static final File OLD_WHITELIST_FILE = new File("white-list.txt");
/*    */ 
/*    */   
/*    */   private static void lookupNames(MinecraftServer server, Collection<String> names, ProfileLookupCallback callback) {
/* 30 */     String[] astring = (String[])Iterators.toArray((Iterator)Iterators.filter(names.iterator(), new Predicate<String>()
/*    */           {
/*    */             public boolean apply(@Nullable String p_apply_1_)
/*    */             {
/* 34 */               return !StringUtils.isNullOrEmpty(p_apply_1_);
/*    */             }
/* 36 */           }), String.class);
/*    */     
/* 38 */     if (server.isServerInOnlineMode()) {
/*    */       
/* 40 */       server.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, callback);
/*    */     } else {
/*    */       byte b; int i;
/*    */       String[] arrayOfString;
/* 44 */       for (i = (arrayOfString = astring).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*    */         
/* 46 */         UUID uuid = EntityPlayer.getUUID(new GameProfile(null, s));
/* 47 */         GameProfile gameprofile = new GameProfile(uuid, s);
/* 48 */         callback.onProfileLookupSucceeded(gameprofile);
/*    */         b++; }
/*    */     
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String convertMobOwnerIfNeeded(final MinecraftServer server, String username) {
/* 55 */     if (!StringUtils.isNullOrEmpty(username) && username.length() <= 16) {
/*    */       
/* 57 */       GameProfile gameprofile = server.getPlayerProfileCache().getGameProfileForUsername(username);
/*    */       
/* 59 */       if (gameprofile != null && gameprofile.getId() != null)
/*    */       {
/* 61 */         return gameprofile.getId().toString();
/*    */       }
/* 63 */       if (!server.isSinglePlayer() && server.isServerInOnlineMode()) {
/*    */         
/* 65 */         final List<GameProfile> list = Lists.newArrayList();
/* 66 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback()
/*    */           {
/*    */             public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_)
/*    */             {
/* 70 */               server.getPlayerProfileCache().addEntry(p_onProfileLookupSucceeded_1_);
/* 71 */               list.add(p_onProfileLookupSucceeded_1_);
/*    */             }
/*    */             
/*    */             public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/* 75 */               PreYggdrasilConverter.LOGGER.warn("Could not lookup user whitelist entry for {}", p_onProfileLookupFailed_1_.getName(), p_onProfileLookupFailed_2_);
/*    */             }
/*    */           };
/* 78 */         lookupNames(server, Lists.newArrayList((Object[])new String[] { username }, ), profilelookupcallback);
/* 79 */         return (!list.isEmpty() && ((GameProfile)list.get(0)).getId() != null) ? ((GameProfile)list.get(0)).getId().toString() : "";
/*    */       } 
/*    */ 
/*    */       
/* 83 */       return EntityPlayer.getUUID(new GameProfile(null, username)).toString();
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 88 */     return username;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\PreYggdrasilConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */