/*    */ package org.newdawn.slick.tests.xml;
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
/*    */ public class Entity
/*    */ {
/*    */   private float x;
/*    */   private float y;
/*    */   private Inventory invent;
/*    */   private Stats stats;
/*    */   
/*    */   private void add(Inventory inventory) {
/* 24 */     this.invent = inventory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void add(Stats stats) {
/* 33 */     this.stats = stats;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dump(String prefix) {
/* 42 */     System.out.println(String.valueOf(prefix) + "Entity " + this.x + "," + this.y);
/* 43 */     this.invent.dump(String.valueOf(prefix) + "\t");
/* 44 */     this.stats.dump(String.valueOf(prefix) + "\t");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\tests\xml\Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */