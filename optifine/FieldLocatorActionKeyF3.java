/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class FieldLocatorActionKeyF3
/*    */   implements IFieldLocator
/*    */ {
/*    */   public Field getField() {
/* 13 */     Class<Minecraft> oclass = Minecraft.class;
/* 14 */     Field field = getFieldRenderChunksMany();
/*    */     
/* 16 */     if (field == null) {
/*    */       
/* 18 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3 (field renderChunksMany not found)");
/* 19 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 23 */     Field field1 = ReflectorRaw.getFieldAfter(Minecraft.class, field, boolean.class, 0);
/*    */     
/* 25 */     if (field1 == null) {
/*    */       
/* 27 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3");
/* 28 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 32 */     return field1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getFieldRenderChunksMany() {
/* 39 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 40 */     boolean flag = minecraft.renderChunksMany;
/* 41 */     Field[] afield = Minecraft.class.getDeclaredFields();
/* 42 */     minecraft.renderChunksMany = true;
/* 43 */     Field[] afield1 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.TRUE);
/* 44 */     minecraft.renderChunksMany = false;
/* 45 */     Field[] afield2 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.FALSE);
/* 46 */     minecraft.renderChunksMany = flag;
/* 47 */     Set<Field> set = new HashSet<>(Arrays.asList(afield1));
/* 48 */     Set<Field> set1 = new HashSet<>(Arrays.asList(afield2));
/* 49 */     Set<Field> set2 = new HashSet<>(set);
/* 50 */     set2.retainAll(set1);
/* 51 */     Field[] afield3 = set2.<Field>toArray(new Field[set2.size()]);
/* 52 */     return (afield3.length != 1) ? null : afield3[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\FieldLocatorActionKeyF3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */