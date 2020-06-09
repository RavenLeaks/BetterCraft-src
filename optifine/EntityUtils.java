/*    */ package optifine;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EntityUtils
/*    */ {
/* 11 */   private static final Map<Class, Integer> mapIdByClass = (Map)new HashMap<>();
/* 12 */   private static final Map<String, Integer> mapIdByLocation = new HashMap<>();
/* 13 */   private static final Map<String, Integer> mapIdByName = new HashMap<>();
/*    */ 
/*    */   
/*    */   public static int getEntityIdByClass(Entity p_getEntityIdByClass_0_) {
/* 17 */     return (p_getEntityIdByClass_0_ == null) ? -1 : getEntityIdByClass(p_getEntityIdByClass_0_.getClass());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByClass(Class p_getEntityIdByClass_0_) {
/* 22 */     Integer integer = mapIdByClass.get(p_getEntityIdByClass_0_);
/* 23 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByLocation(String p_getEntityIdByLocation_0_) {
/* 28 */     Integer integer = mapIdByLocation.get(p_getEntityIdByLocation_0_);
/* 29 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getEntityIdByName(String p_getEntityIdByName_0_) {
/* 34 */     Integer integer = mapIdByName.get(p_getEntityIdByName_0_);
/* 35 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 40 */     for (int i = 0; i < 1000; i++) {
/*    */       
/* 42 */       Class oclass = EntityList.getClassFromID(i);
/*    */       
/* 44 */       if (oclass != null) {
/*    */         
/* 46 */         ResourceLocation resourcelocation = EntityList.func_191306_a(oclass);
/*    */         
/* 48 */         if (resourcelocation != null) {
/*    */           
/* 50 */           String s = resourcelocation.toString();
/* 51 */           String s1 = EntityList.func_191302_a(resourcelocation);
/*    */           
/* 53 */           if (s1 != null) {
/*    */             
/* 55 */             if (mapIdByClass.containsKey(oclass))
/*    */             {
/* 57 */               Config.warn("Duplicate entity class: " + oclass + ", id1: " + mapIdByClass.get(oclass) + ", id2: " + i);
/*    */             }
/*    */             
/* 60 */             if (mapIdByLocation.containsKey(s))
/*    */             {
/* 62 */               Config.warn("Duplicate entity location: " + s + ", id1: " + mapIdByLocation.get(s) + ", id2: " + i);
/*    */             }
/*    */             
/* 65 */             if (mapIdByName.containsKey(s))
/*    */             {
/* 67 */               Config.warn("Duplicate entity name: " + s1 + ", id1: " + mapIdByName.get(s1) + ", id2: " + i);
/*    */             }
/*    */             
/* 70 */             mapIdByClass.put(oclass, Integer.valueOf(i));
/* 71 */             mapIdByLocation.put(s, Integer.valueOf(i));
/* 72 */             mapIdByName.put(s1, Integer.valueOf(i));
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */