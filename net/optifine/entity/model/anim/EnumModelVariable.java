/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import optifine.Config;
/*     */ 
/*     */ public enum EnumModelVariable
/*     */ {
/*   8 */   POS_X("tx"),
/*   9 */   POS_Y("ty"),
/*  10 */   POS_Z("tz"),
/*  11 */   ANGLE_X("rx"),
/*  12 */   ANGLE_Y("ry"),
/*  13 */   ANGLE_Z("rz"),
/*  14 */   OFFSET_X("ox"),
/*  15 */   OFFSET_Y("oy"),
/*  16 */   OFFSET_Z("oz"),
/*  17 */   SCALE_X("sx"),
/*  18 */   SCALE_Y("sy"),
/*  19 */   SCALE_Z("sz"); private String name;
/*     */   
/*     */   static {
/*  22 */     VALUES = values();
/*     */   }
/*     */   public static EnumModelVariable[] VALUES;
/*     */   EnumModelVariable(String name) {
/*  26 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  31 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(ModelRenderer mr) {
/*  36 */     switch (this) {
/*     */       
/*     */       case POS_X:
/*  39 */         return mr.rotationPointX;
/*     */       
/*     */       case POS_Y:
/*  42 */         return mr.rotationPointY;
/*     */       
/*     */       case POS_Z:
/*  45 */         return mr.rotationPointZ;
/*     */       
/*     */       case null:
/*  48 */         return mr.rotateAngleX;
/*     */       
/*     */       case ANGLE_Y:
/*  51 */         return mr.rotateAngleY;
/*     */       
/*     */       case ANGLE_Z:
/*  54 */         return mr.rotateAngleZ;
/*     */       
/*     */       case OFFSET_X:
/*  57 */         return mr.offsetX;
/*     */       
/*     */       case OFFSET_Y:
/*  60 */         return mr.offsetY;
/*     */       
/*     */       case OFFSET_Z:
/*  63 */         return mr.offsetZ;
/*     */       
/*     */       case SCALE_X:
/*  66 */         return mr.scaleX;
/*     */       
/*     */       case SCALE_Y:
/*  69 */         return mr.scaleY;
/*     */       
/*     */       case SCALE_Z:
/*  72 */         return mr.scaleZ;
/*     */     } 
/*     */     
/*  75 */     Config.warn("GetFloat not supported for: " + this);
/*  76 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(ModelRenderer mr, float val) {
/*  82 */     switch (this) {
/*     */       
/*     */       case POS_X:
/*  85 */         mr.rotationPointX = val;
/*     */         return;
/*     */       
/*     */       case POS_Y:
/*  89 */         mr.rotationPointY = val;
/*     */         return;
/*     */       
/*     */       case POS_Z:
/*  93 */         mr.rotationPointZ = val;
/*     */         return;
/*     */       
/*     */       case null:
/*  97 */         mr.rotateAngleX = val;
/*     */         return;
/*     */       
/*     */       case ANGLE_Y:
/* 101 */         mr.rotateAngleY = val;
/*     */         return;
/*     */       
/*     */       case ANGLE_Z:
/* 105 */         mr.rotateAngleZ = val;
/*     */         return;
/*     */       
/*     */       case OFFSET_X:
/* 109 */         mr.offsetX = val;
/*     */         return;
/*     */       
/*     */       case OFFSET_Y:
/* 113 */         mr.offsetY = val;
/*     */         return;
/*     */       
/*     */       case OFFSET_Z:
/* 117 */         mr.offsetZ = val;
/*     */         return;
/*     */       
/*     */       case SCALE_X:
/* 121 */         mr.scaleX = val;
/*     */         return;
/*     */       
/*     */       case SCALE_Y:
/* 125 */         mr.scaleY = val;
/*     */         return;
/*     */       
/*     */       case SCALE_Z:
/* 129 */         mr.scaleZ = val;
/*     */         return;
/*     */     } 
/*     */     
/* 133 */     Config.warn("SetFloat not supported for: " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumModelVariable parse(String str) {
/* 139 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 141 */       EnumModelVariable enummodelvariable = VALUES[i];
/*     */       
/* 143 */       if (enummodelvariable.getName().equals(str))
/*     */       {
/* 145 */         return enummodelvariable;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\EnumModelVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */