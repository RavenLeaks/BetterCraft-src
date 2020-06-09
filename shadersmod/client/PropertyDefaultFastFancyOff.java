/*    */ package shadersmod.client;
/*    */ 
/*    */ import optifine.Config;
/*    */ 
/*    */ public class PropertyDefaultFastFancyOff
/*    */   extends Property {
/*  7 */   public static final String[] PROPERTY_VALUES = new String[] { "default", "fast", "fancy", "off" };
/*  8 */   public static final String[] USER_VALUES = new String[] { "Default", "Fast", "Fancy", "OFF" };
/*    */ 
/*    */   
/*    */   public PropertyDefaultFastFancyOff(String propertyName, String userName, int defaultValue) {
/* 12 */     super(propertyName, PROPERTY_VALUES, userName, USER_VALUES, defaultValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDefault() {
/* 17 */     return (getValue() == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFast() {
/* 22 */     return (getValue() == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFancy() {
/* 27 */     return (getValue() == 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOff() {
/* 32 */     return (getValue() == 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean setPropertyValue(String propVal) {
/* 37 */     if (Config.equals(propVal, "none"))
/*    */     {
/* 39 */       propVal = "off";
/*    */     }
/*    */     
/* 42 */     return super.setPropertyValue(propVal);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\PropertyDefaultFastFancyOff.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */