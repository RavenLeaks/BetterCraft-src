/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import optifine.Config;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Property
/*     */ {
/*  11 */   private int[] values = null;
/*  12 */   private int defaultValue = 0;
/*  13 */   private String propertyName = null;
/*  14 */   private String[] propertyValues = null;
/*  15 */   private String userName = null;
/*  16 */   private String[] userValues = null;
/*  17 */   private int value = 0;
/*     */ 
/*     */   
/*     */   public Property(String propertyName, String[] propertyValues, String userName, String[] userValues, int defaultValue) {
/*  21 */     this.propertyName = propertyName;
/*  22 */     this.propertyValues = propertyValues;
/*  23 */     this.userName = userName;
/*  24 */     this.userValues = userValues;
/*  25 */     this.defaultValue = defaultValue;
/*     */     
/*  27 */     if (propertyValues.length != userValues.length)
/*     */     {
/*  29 */       throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length);
/*     */     }
/*  31 */     if (defaultValue >= 0 && defaultValue < propertyValues.length) {
/*     */       
/*  33 */       this.value = defaultValue;
/*     */     }
/*     */     else {
/*     */       
/*  37 */       throw new IllegalArgumentException("Invalid default value: " + defaultValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setPropertyValue(String propVal) {
/*  43 */     if (propVal == null) {
/*     */       
/*  45 */       this.value = this.defaultValue;
/*  46 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  50 */     this.value = ArrayUtils.indexOf((Object[])this.propertyValues, propVal);
/*     */     
/*  52 */     if (this.value >= 0 && this.value < this.propertyValues.length)
/*     */     {
/*  54 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  58 */     this.value = this.defaultValue;
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  66 */     this.value++;
/*     */     
/*  68 */     if (this.value < 0 || this.value >= this.propertyValues.length)
/*     */     {
/*  70 */       this.value = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(int val) {
/*  76 */     this.value = val;
/*     */     
/*  78 */     if (this.value < 0 || this.value >= this.propertyValues.length)
/*     */     {
/*  80 */       this.value = this.defaultValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getValue() {
/*  86 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserValue() {
/*  91 */     return this.userValues[this.value];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyValue() {
/*  96 */     return this.propertyValues[this.value];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserName() {
/* 101 */     return this.userName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/* 106 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetValue() {
/* 111 */     this.value = this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadFrom(Properties props) {
/* 116 */     resetValue();
/*     */     
/* 118 */     if (props == null)
/*     */     {
/* 120 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 124 */     String s = props.getProperty(this.propertyName);
/* 125 */     return (s == null) ? false : setPropertyValue(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveTo(Properties props) {
/* 131 */     if (props != null)
/*     */     {
/* 133 */       props.setProperty(getPropertyName(), getPropertyValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     return this.propertyName + "=" + getPropertyValue() + " [" + Config.arrayToString((Object[])this.propertyValues) + "], value: " + this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\Property.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */