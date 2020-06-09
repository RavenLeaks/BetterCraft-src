/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class ModelResourceLocation
/*    */   extends ResourceLocation
/*    */ {
/*    */   private final String variant;
/*    */   
/*    */   protected ModelResourceLocation(int unused, String... resourceName) {
/* 13 */     super(0, new String[] { resourceName[0], resourceName[1] });
/* 14 */     this.variant = StringUtils.isEmpty(resourceName[2]) ? "normal" : resourceName[2].toLowerCase(Locale.ROOT);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelResourceLocation(String pathIn) {
/* 19 */     this(0, parsePathString(pathIn));
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelResourceLocation(ResourceLocation location, String variantIn) {
/* 24 */     this(location.toString(), variantIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelResourceLocation(String location, String variantIn) {
/* 29 */     this(0, parsePathString(String.valueOf(location) + '#' + ((variantIn == null) ? "normal" : variantIn)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected static String[] parsePathString(String pathIn) {
/* 34 */     String[] astring = { null, pathIn };
/* 35 */     int i = pathIn.indexOf('#');
/* 36 */     String s = pathIn;
/*    */     
/* 38 */     if (i >= 0) {
/*    */       
/* 40 */       astring[2] = pathIn.substring(i + 1, pathIn.length());
/*    */       
/* 42 */       if (i > 1)
/*    */       {
/* 44 */         s = pathIn.substring(0, i);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     System.arraycopy(ResourceLocation.splitObjectName(s), 0, astring, 0, 2);
/* 49 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVariant() {
/* 54 */     return this.variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 59 */     if (this == p_equals_1_)
/*    */     {
/* 61 */       return true;
/*    */     }
/* 63 */     if (p_equals_1_ instanceof ModelResourceLocation && super.equals(p_equals_1_)) {
/*    */       
/* 65 */       ModelResourceLocation modelresourcelocation = (ModelResourceLocation)p_equals_1_;
/* 66 */       return this.variant.equals(modelresourcelocation.variant);
/*    */     } 
/*    */ 
/*    */     
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     return 31 * super.hashCode() + this.variant.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return String.valueOf(super.toString()) + '#' + this.variant;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\ModelResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */