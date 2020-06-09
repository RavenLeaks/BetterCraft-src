/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ 
/*    */ public class ReflectorConstructor
/*    */ {
/*  7 */   private ReflectorClass reflectorClass = null;
/*  8 */   private Class[] parameterTypes = null;
/*    */   private boolean checked = false;
/* 10 */   private Constructor targetConstructor = null;
/*    */ 
/*    */   
/*    */   public ReflectorConstructor(ReflectorClass p_i84_1_, Class[] p_i84_2_) {
/* 14 */     this.reflectorClass = p_i84_1_;
/* 15 */     this.parameterTypes = p_i84_2_;
/* 16 */     Constructor constructor = getTargetConstructor();
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor getTargetConstructor() {
/* 21 */     if (this.checked)
/*    */     {
/* 23 */       return this.targetConstructor;
/*    */     }
/*    */ 
/*    */     
/* 27 */     this.checked = true;
/* 28 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 30 */     if (oclass == null)
/*    */     {
/* 32 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 38 */       this.targetConstructor = findConstructor(oclass, this.parameterTypes);
/*    */       
/* 40 */       if (this.targetConstructor == null)
/*    */       {
/* 42 */         Config.dbg("(Reflector) Constructor not present: " + oclass.getName() + ", params: " + Config.arrayToString((Object[])this.parameterTypes));
/*    */       }
/*    */       
/* 45 */       if (this.targetConstructor != null)
/*    */       {
/* 47 */         this.targetConstructor.setAccessible(true);
/*    */       }
/*    */     }
/* 50 */     catch (Throwable throwable) {
/*    */       
/* 52 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 55 */     return this.targetConstructor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Constructor findConstructor(Class p_findConstructor_0_, Class[] p_findConstructor_1_) {
/* 62 */     Constructor[] aconstructor = (Constructor[])p_findConstructor_0_.getDeclaredConstructors();
/*    */     
/* 64 */     for (int i = 0; i < aconstructor.length; i++) {
/*    */       
/* 66 */       Constructor constructor = aconstructor[i];
/* 67 */       Class[] aclass = constructor.getParameterTypes();
/*    */       
/* 69 */       if (Reflector.matchesTypes(p_findConstructor_1_, aclass))
/*    */       {
/* 71 */         return constructor;
/*    */       }
/*    */     } 
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 80 */     if (this.checked)
/*    */     {
/* 82 */       return (this.targetConstructor != null);
/*    */     }
/*    */ 
/*    */     
/* 86 */     return (getTargetConstructor() != null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void deactivate() {
/* 92 */     this.checked = true;
/* 93 */     this.targetConstructor = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */