/*    */ package me.nzxter.bettercraft.mods.altmanager;
/*    */ 
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.UserAuthentication;
/*    */ import com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*    */ import java.net.Proxy;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Login
/*    */ {
/*    */   public static boolean login(String email, String password) throws AuthenticationException {
/* 18 */     login(email, password, Proxy.NO_PROXY);
/* 19 */     AltManager.loggedInName = null;
/* 20 */     return true;
/*    */   }
/*    */   
/*    */   public static void login(String email, String password, Proxy proxy) throws AuthenticationException {
/* 24 */     YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(proxy, "");
/* 25 */     UserAuthentication auth = authService.createUserAuthentication(Agent.MINECRAFT);
/*    */     
/* 27 */     auth.setUsername(email);
/* 28 */     auth.setPassword(password);
/* 29 */     auth.logIn();
/* 30 */     Minecraft.getMinecraft(); Minecraft.session = new Session(auth.getSelectedProfile().getName(), 
/* 31 */         auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
/*    */   }
/*    */   
/*    */   public static void changeName(String newName) {
/* 35 */     if (newName.equals("MHF_TikTok") || newName.equals("NzxterMC")) {
/* 36 */       newName = "succ";
/*    */     }
/* 38 */     Minecraft.getMinecraft(); Minecraft.session = new Session(newName, "", "", "mojang");
/* 39 */     AltManager.loggedInName = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\altmanager\Login.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */