/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.AppGameContainer;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Color;
/*    */ import org.newdawn.slick.Font;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.SpriteSheet;
/*    */ import org.newdawn.slick.SpriteSheetFont;
/*    */ import org.newdawn.slick.util.Log;
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
/*    */ public class SpriteSheetFontTest
/*    */   extends BasicGame
/*    */ {
/*    */   private Font font;
/*    */   private static AppGameContainer container;
/*    */   
/*    */   public SpriteSheetFontTest() {
/* 31 */     super("SpriteSheetFont Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 38 */     SpriteSheet sheet = new SpriteSheet("testdata/spriteSheetFont.png", 32, 32);
/* 39 */     this.font = (Font)new SpriteSheetFont(sheet, ' ');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) {
/* 46 */     g.setBackground(Color.gray);
/* 47 */     this.font.drawString(80.0F, 5.0F, "A FONT EXAMPLE", Color.red);
/* 48 */     this.font.drawString(100.0F, 50.0F, "A MORE COMPLETE LINE");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyPressed(int key, char c) {
/* 61 */     if (key == 1) {
/* 62 */       System.exit(0);
/*    */     }
/* 64 */     if (key == 57) {
/*    */       try {
/* 66 */         container.setDisplayMode(640, 480, false);
/* 67 */       } catch (SlickException e) {
/* 68 */         Log.error((Throwable)e);
/*    */       } 
/*    */     }
/*    */   }
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
/*    */   public static void main(String[] argv) {
/*    */     try {
/* 85 */       container = new AppGameContainer((Game)new SpriteSheetFontTest());
/* 86 */       container.setDisplayMode(800, 600, false);
/* 87 */       container.start();
/* 88 */     } catch (SlickException e) {
/* 89 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\tests\SpriteSheetFontTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */