/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandom
/*    */ {
/*    */   public static int getTotalWeight(List<? extends Item> collection) {
/* 13 */     int i = 0;
/* 14 */     int j = 0;
/*    */     
/* 16 */     for (int k = collection.size(); j < k; j++) {
/*    */       
/* 18 */       Item weightedrandom$item = collection.get(j);
/* 19 */       i += weightedrandom$item.itemWeight;
/*    */     } 
/*    */     
/* 22 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Random random, List<T> collection, int totalWeight) {
/* 27 */     if (totalWeight <= 0)
/*    */     {
/* 29 */       throw new IllegalArgumentException();
/*    */     }
/*    */ 
/*    */     
/* 33 */     int i = random.nextInt(totalWeight);
/* 34 */     return getRandomItem(collection, i);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(List<T> collection, int weight) {
/* 40 */     int i = 0;
/*    */     
/* 42 */     for (int j = collection.size(); i < j; i++) {
/*    */       
/* 44 */       Item item = (Item)collection.get(i);
/* 45 */       weight -= item.itemWeight;
/*    */       
/* 47 */       if (weight < 0)
/*    */       {
/* 49 */         return (T)item;
/*    */       }
/*    */     } 
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Random random, List<T> collection) {
/* 58 */     return getRandomItem(random, collection, getTotalWeight(collection));
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Item
/*    */   {
/*    */     protected int itemWeight;
/*    */     
/*    */     public Item(int itemWeightIn) {
/* 67 */       this.itemWeight = itemWeightIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\WeightedRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */