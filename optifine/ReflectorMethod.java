/*     */ package optifine;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class ReflectorMethod
/*     */ {
/*     */   private ReflectorClass reflectorClass;
/*     */   private String targetMethodName;
/*     */   private Class[] targetMethodParameterTypes;
/*     */   private boolean checked;
/*     */   private Method targetMethod;
/*     */   
/*     */   public ReflectorMethod(ReflectorClass p_i93_1_, String p_i93_2_) {
/*  17 */     this(p_i93_1_, p_i93_2_, null, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass p_i94_1_, String p_i94_2_, Class[] p_i94_3_) {
/*  22 */     this(p_i94_1_, p_i94_2_, p_i94_3_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReflectorMethod(ReflectorClass p_i95_1_, String p_i95_2_, Class[] p_i95_3_, boolean p_i95_4_) {
/*  27 */     this.reflectorClass = null;
/*  28 */     this.targetMethodName = null;
/*  29 */     this.targetMethodParameterTypes = null;
/*  30 */     this.checked = false;
/*  31 */     this.targetMethod = null;
/*  32 */     this.reflectorClass = p_i95_1_;
/*  33 */     this.targetMethodName = p_i95_2_;
/*  34 */     this.targetMethodParameterTypes = p_i95_3_;
/*     */     
/*  36 */     if (!p_i95_4_)
/*     */     {
/*  38 */       Method method = getTargetMethod();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Method getTargetMethod() {
/*  44 */     if (this.checked)
/*     */     {
/*  46 */       return this.targetMethod;
/*     */     }
/*     */ 
/*     */     
/*  50 */     this.checked = true;
/*  51 */     Class oclass = this.reflectorClass.getTargetClass();
/*     */     
/*  53 */     if (oclass == null)
/*     */     {
/*  55 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  61 */       if (this.targetMethodParameterTypes == null) {
/*     */         
/*  63 */         Method[] amethod = getMethods(oclass, this.targetMethodName);
/*     */         
/*  65 */         if (amethod.length <= 0) {
/*     */           
/*  67 */           Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  68 */           return null;
/*     */         } 
/*     */         
/*  71 */         if (amethod.length > 1) {
/*     */           
/*  73 */           Config.warn("(Reflector) More than one method found: " + oclass.getName() + "." + this.targetMethodName);
/*     */           
/*  75 */           for (int i = 0; i < amethod.length; i++) {
/*     */             
/*  77 */             Method method = amethod[i];
/*  78 */             Config.warn("(Reflector)  - " + method);
/*     */           } 
/*     */           
/*  81 */           return null;
/*     */         } 
/*     */         
/*  84 */         this.targetMethod = amethod[0];
/*     */       }
/*     */       else {
/*     */         
/*  88 */         this.targetMethod = getMethod(oclass, this.targetMethodName, this.targetMethodParameterTypes);
/*     */       } 
/*     */       
/*  91 */       if (this.targetMethod == null) {
/*     */         
/*  93 */         Config.log("(Reflector) Method not present: " + oclass.getName() + "." + this.targetMethodName);
/*  94 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*  98 */       this.targetMethod.setAccessible(true);
/*  99 */       return this.targetMethod;
/*     */     
/*     */     }
/* 102 */     catch (Throwable throwable) {
/*     */       
/* 104 */       throwable.printStackTrace();
/* 105 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exists() {
/* 113 */     if (this.checked)
/*     */     {
/* 115 */       return (this.targetMethod != null);
/*     */     }
/*     */ 
/*     */     
/* 119 */     return (getTargetMethod() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getReturnType() {
/* 125 */     Method method = getTargetMethod();
/* 126 */     return (method == null) ? null : method.getReturnType();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deactivate() {
/* 131 */     this.checked = true;
/* 132 */     this.targetMethod = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method getMethod(Class p_getMethod_0_, String p_getMethod_1_, Class[] p_getMethod_2_) {
/* 137 */     Method[] amethod = p_getMethod_0_.getDeclaredMethods();
/*     */     
/* 139 */     for (int i = 0; i < amethod.length; i++) {
/*     */       
/* 141 */       Method method = amethod[i];
/*     */       
/* 143 */       if (method.getName().equals(p_getMethod_1_)) {
/*     */         
/* 145 */         Class[] aclass = method.getParameterTypes();
/*     */         
/* 147 */         if (Reflector.matchesTypes(p_getMethod_2_, aclass))
/*     */         {
/* 149 */           return method;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Method[] getMethods(Class p_getMethods_0_, String p_getMethods_1_) {
/* 159 */     List<Method> list = new ArrayList();
/* 160 */     Method[] amethod = p_getMethods_0_.getDeclaredMethods();
/*     */     
/* 162 */     for (int i = 0; i < amethod.length; i++) {
/*     */       
/* 164 */       Method method = amethod[i];
/*     */       
/* 166 */       if (method.getName().equals(p_getMethods_1_))
/*     */       {
/* 168 */         list.add(method);
/*     */       }
/*     */     } 
/*     */     
/* 172 */     Method[] amethod1 = list.<Method>toArray(new Method[list.size()]);
/* 173 */     return amethod1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */