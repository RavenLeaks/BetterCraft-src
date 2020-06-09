/*    */ package shadersmod.client;
/*    */ 
/*    */ import optifine.Lang;
/*    */ 
/*    */ public class PropertyDefaultTrueFalse
/*    */   extends Property {
/*  7 */   public static final String[] PROPERTY_VALUES = new String[] { "default", "true", "false" };
/*  8 */   public static final String[] USER_VALUES = new String[] { "Default", "ON", "OFF" };
/*    */ 
/*    */   
/*    */   public PropertyDefaultTrueFalse(String propertyName, String userName, int defaultValue) {
/* 12 */     super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUserValue() {
/* 17 */     if (isDefault())
/*    */     {
/* 19 */       return Lang.getDefault();
/*    */     }
/* 21 */     if (isTrue())
/*    */     {
/* 23 */       return Lang.getOn();
/*    */     }
/*    */ 
/*    */     
/* 27 */     return isFalse() ? Lang.getOff() : super.getUserValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDefault() {
/* 33 */     return (getValue() == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isTrue() {
/* 38 */     return (getValue() == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFalse() {
/* 43 */     return (getValue() == 2);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\PropertyDefaultTrueFalse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */