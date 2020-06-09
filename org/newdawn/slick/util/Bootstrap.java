/*    */ package org.newdawn.slick.util;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.Game;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Bootstrap
/*    */ {
/*    */   public static void runAsApplication(Game game, int width, int height, boolean fullscreen) {
/*    */     try {
/* 23 */       AppGameContainer container = new AppGameContainer(game, width, height, fullscreen);
/* 24 */       container.start();
/* 25 */     } catch (Exception e) {
/* 26 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slic\\util\Bootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */