/*    */ package optifine;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class CustomItemsComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object p_compare_1_, Object p_compare_2_) {
/*  9 */     CustomItemProperties customitemproperties = (CustomItemProperties)p_compare_1_;
/* 10 */     CustomItemProperties customitemproperties1 = (CustomItemProperties)p_compare_2_;
/*    */     
/* 12 */     if (customitemproperties.weight != customitemproperties1.weight)
/*    */     {
/* 14 */       return customitemproperties1.weight - customitemproperties.weight;
/*    */     }
/*    */ 
/*    */     
/* 18 */     return !Config.equals(customitemproperties.basePath, customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomItemsComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */