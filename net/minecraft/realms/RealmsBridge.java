/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiScreenRealmsProxy;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RealmsBridge
/*    */   extends RealmsScreen {
/* 12 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private GuiScreen previousScreen;
/*    */   
/*    */   public void switchToRealms(GuiScreen p_switchToRealms_1_) {
/* 17 */     this.previousScreen = p_switchToRealms_1_;
/*    */ 
/*    */     
/*    */     try {
/* 21 */       Class<?> oclass = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
/* 22 */       Constructor<?> constructor = oclass.getDeclaredConstructor(new Class[] { RealmsScreen.class });
/* 23 */       constructor.setAccessible(true);
/* 24 */       Object object = constructor.newInstance(new Object[] { this });
/* 25 */       Minecraft.getMinecraft().displayGuiScreen((GuiScreen)((RealmsScreen)object).getProxy());
/*    */     }
/* 27 */     catch (ClassNotFoundException var5) {
/*    */       
/* 29 */       LOGGER.error("Realms module missing");
/*    */     }
/* 31 */     catch (Exception exception) {
/*    */       
/* 33 */       LOGGER.error("Failed to load Realms module", exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiScreenRealmsProxy getNotificationScreen(GuiScreen p_getNotificationScreen_1_) {
/*    */     try {
/* 41 */       this.previousScreen = p_getNotificationScreen_1_;
/* 42 */       Class<?> oclass = Class.forName("com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen");
/* 43 */       Constructor<?> constructor = oclass.getDeclaredConstructor(new Class[] { RealmsScreen.class });
/* 44 */       constructor.setAccessible(true);
/* 45 */       Object object = constructor.newInstance(new Object[] { this });
/* 46 */       return ((RealmsScreen)object).getProxy();
/*    */     }
/* 48 */     catch (ClassNotFoundException var5) {
/*    */       
/* 50 */       LOGGER.error("Realms module missing");
/*    */     }
/* 52 */     catch (Exception exception) {
/*    */       
/* 54 */       LOGGER.error("Failed to load Realms module", exception);
/*    */     } 
/*    */     
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 62 */     Minecraft.getMinecraft().displayGuiScreen(this.previousScreen);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */