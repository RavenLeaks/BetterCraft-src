/*    */ package org.newdawn.slick.tests.xml;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameData
/*    */ {
/* 12 */   private ArrayList entities = new ArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(Entity entity) {
/* 20 */     this.entities.add(entity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dump(String prefix) {
/* 29 */     System.out.println(String.valueOf(prefix) + "GameData");
/* 30 */     for (int i = 0; i < this.entities.size(); i++)
/* 31 */       ((Entity)this.entities.get(i)).dump(String.valueOf(prefix) + "\t"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\tests\xml\GameData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */