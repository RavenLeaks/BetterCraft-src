/*    */ package net.minecraft.world;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ 
/*    */ public enum DimensionType
/*    */ {
/*  8 */   OVERWORLD(0, "overworld", "", (Class)WorldProviderSurface.class),
/*  9 */   NETHER(-1, "the_nether", "_nether", (Class)WorldProviderHell.class),
/* 10 */   THE_END(1, "the_end", "_end", (Class)WorldProviderEnd.class);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   private final String name;
/*    */   private final String suffix;
/*    */   private final Class<? extends WorldProvider> clazz;
/*    */   
/*    */   DimensionType(int idIn, String nameIn, String suffixIn, Class<? extends WorldProvider> clazzIn) {
/* 19 */     this.id = idIn;
/* 20 */     this.name = nameIn;
/* 21 */     this.suffix = suffixIn;
/* 22 */     this.clazz = clazzIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 27 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSuffix() {
/* 37 */     return this.suffix;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldProvider createDimension() {
/*    */     try {
/* 44 */       Constructor<? extends WorldProvider> constructor = this.clazz.getConstructor(new Class[0]);
/* 45 */       return constructor.newInstance(new Object[0]);
/*    */     }
/* 47 */     catch (NoSuchMethodException nosuchmethodexception) {
/*    */       
/* 49 */       throw new Error("Could not create new dimension", nosuchmethodexception);
/*    */     }
/* 51 */     catch (InvocationTargetException invocationtargetexception) {
/*    */       
/* 53 */       throw new Error("Could not create new dimension", invocationtargetexception);
/*    */     }
/* 55 */     catch (InstantiationException instantiationexception) {
/*    */       
/* 57 */       throw new Error("Could not create new dimension", instantiationexception);
/*    */     }
/* 59 */     catch (IllegalAccessException illegalaccessexception) {
/*    */       
/* 61 */       throw new Error("Could not create new dimension", illegalaccessexception);
/*    */     } 
/*    */   } public static DimensionType getById(int id) {
/*    */     byte b;
/*    */     int i;
/*    */     DimensionType[] arrayOfDimensionType;
/* 67 */     for (i = (arrayOfDimensionType = values()).length, b = 0; b < i; ) { DimensionType dimensiontype = arrayOfDimensionType[b];
/*    */       
/* 69 */       if (dimensiontype.getId() == id)
/*    */       {
/* 71 */         return dimensiontype;
/*    */       }
/*    */       b++; }
/*    */     
/* 75 */     throw new IllegalArgumentException("Invalid dimension id " + id);
/*    */   } public static DimensionType func_193417_a(String p_193417_0_) {
/*    */     byte b;
/*    */     int i;
/*    */     DimensionType[] arrayOfDimensionType;
/* 80 */     for (i = (arrayOfDimensionType = values()).length, b = 0; b < i; ) { DimensionType dimensiontype = arrayOfDimensionType[b];
/*    */       
/* 82 */       if (dimensiontype.getName().equals(p_193417_0_))
/*    */       {
/* 84 */         return dimensiontype;
/*    */       }
/*    */       b++; }
/*    */     
/* 88 */     throw new IllegalArgumentException("Invalid dimension " + p_193417_0_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\DimensionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */