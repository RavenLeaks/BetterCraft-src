/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ public class ChunkUtils
/*    */ {
/* 10 */   private static ReflectorField fieldHasEntities = null;
/*    */ 
/*    */   
/*    */   public static boolean hasEntities(Chunk p_hasEntities_0_) {
/* 14 */     if (fieldHasEntities == null)
/*    */     {
/* 16 */       fieldHasEntities = findFieldHasEntities(p_hasEntities_0_);
/*    */     }
/*    */     
/* 19 */     if (!fieldHasEntities.exists())
/*    */     {
/* 21 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 25 */     Boolean obool = (Boolean)Reflector.getFieldValue(p_hasEntities_0_, fieldHasEntities);
/* 26 */     return (obool == null) ? true : obool.booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static ReflectorField findFieldHasEntities(Chunk p_findFieldHasEntities_0_) {
/*    */     try {
/* 34 */       List<Field> list = new ArrayList();
/* 35 */       List<Object> list1 = new ArrayList();
/* 36 */       Field[] afield = Chunk.class.getDeclaredFields();
/*    */       
/* 38 */       for (int i = 0; i < afield.length; i++) {
/*    */         
/* 40 */         Field field = afield[i];
/*    */         
/* 42 */         if (field.getType() == boolean.class) {
/*    */           
/* 44 */           field.setAccessible(true);
/* 45 */           list.add(field);
/* 46 */           list1.add(field.get(p_findFieldHasEntities_0_));
/*    */         } 
/*    */       } 
/*    */       
/* 50 */       p_findFieldHasEntities_0_.setHasEntities(false);
/* 51 */       List<Object> list2 = new ArrayList();
/*    */       
/* 53 */       for (Field field1 : list)
/*    */       {
/* 55 */         list2.add(((Field)field1).get(p_findFieldHasEntities_0_));
/*    */       }
/*    */       
/* 58 */       p_findFieldHasEntities_0_.setHasEntities(true);
/* 59 */       List<Object> list3 = new ArrayList();
/*    */       
/* 61 */       for (Field field2 : list)
/*    */       {
/* 63 */         list3.add(((Field)field2).get(p_findFieldHasEntities_0_));
/*    */       }
/*    */       
/* 66 */       List<Field> list4 = new ArrayList();
/*    */       
/* 68 */       for (int j = 0; j < list.size(); j++) {
/*    */         
/* 70 */         Field field3 = list.get(j);
/* 71 */         Boolean obool = (Boolean)list2.get(j);
/* 72 */         Boolean obool1 = (Boolean)list3.get(j);
/*    */         
/* 74 */         if (!obool.booleanValue() && obool1.booleanValue()) {
/*    */           
/* 76 */           list4.add(field3);
/* 77 */           Boolean obool2 = (Boolean)list1.get(j);
/* 78 */           field3.set(p_findFieldHasEntities_0_, obool2);
/*    */         } 
/*    */       } 
/*    */       
/* 82 */       if (list4.size() == 1)
/*    */       {
/* 84 */         Field field4 = list4.get(0);
/* 85 */         return new ReflectorField(field4);
/*    */       }
/*    */     
/* 88 */     } catch (Exception exception) {
/*    */       
/* 90 */       Config.warn(String.valueOf(exception.getClass().getName()) + " " + exception.getMessage());
/*    */     } 
/*    */     
/* 93 */     Config.warn("Error finding Chunk.hasEntities");
/* 94 */     return new ReflectorField(new ReflectorClass(Chunk.class), "hasEntities");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ChunkUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */