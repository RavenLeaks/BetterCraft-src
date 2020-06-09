/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import optifine.Config;
/*     */ import optifine.MathUtils;
/*     */ 
/*     */ public enum EnumFunctionType
/*     */ {
/*  11 */   PLUS("+", 2, 0),
/*  12 */   MINUS("-", 2, 0),
/*  13 */   MUL("*", 2, 1),
/*  14 */   DIV("/", 2, 1),
/*  15 */   MOD("%", 2, 1),
/*  16 */   NEG("neg", 1),
/*  17 */   PI("pi", 0),
/*  18 */   SIN("sin", 1),
/*  19 */   COS("cos", 1),
/*  20 */   TAN("tan", 1),
/*  21 */   ATAN("atan", 1),
/*  22 */   ATAN2("atan2", 2),
/*  23 */   TORAD("torad", 1),
/*  24 */   TODEG("todeg", 1),
/*  25 */   MIN("min", 2),
/*  26 */   MAX("max", 2),
/*  27 */   CLAMP("clamp", 3),
/*  28 */   ABS("abs", 1),
/*  29 */   FLOOR("floor", 1),
/*  30 */   CEIL("ceil", 1),
/*  31 */   FRAC("frac", 1),
/*  32 */   ROUND("round", 1),
/*  33 */   SQRT("sqrt", 1),
/*  34 */   FMOD("fmod", 2),
/*  35 */   TIME("time", 0);
/*     */   private String name;
/*     */   private int countArguments;
/*     */   
/*     */   static {
/*  40 */     VALUES = values();
/*     */   }
/*     */   private int precedence; public static EnumFunctionType[] VALUES;
/*     */   EnumFunctionType(String name, int countArguments) {
/*  44 */     this.name = name;
/*  45 */     this.countArguments = countArguments;
/*     */   }
/*     */ 
/*     */   
/*     */   EnumFunctionType(String name, int countArguments, int precedence) {
/*  50 */     this.name = name;
/*  51 */     this.countArguments = countArguments;
/*  52 */     this.precedence = precedence;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  57 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountArguments() {
/*  62 */     return this.countArguments;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPrecedence() {
/*  67 */     return this.precedence;
/*     */   } public float eval(IExpression[] arguments) {
/*     */     float f, f1, f2, f3;
/*     */     Minecraft minecraft;
/*     */     WorldClient worldClient;
/*  72 */     if (arguments.length != this.countArguments) {
/*     */       
/*  74 */       Config.warn("Invalid number of arguments, function: " + this + ", arguments: " + arguments.length + ", should be: " + this.countArguments);
/*  75 */       return 0.0F;
/*     */     } 
/*     */ 
/*     */     
/*  79 */     switch (this) {
/*     */       
/*     */       case PLUS:
/*  82 */         return arguments[0].eval() + arguments[1].eval();
/*     */       
/*     */       case MINUS:
/*  85 */         return arguments[0].eval() - arguments[1].eval();
/*     */       
/*     */       case MUL:
/*  88 */         return arguments[0].eval() * arguments[1].eval();
/*     */       
/*     */       case DIV:
/*  91 */         return arguments[0].eval() / arguments[1].eval();
/*     */       
/*     */       case MOD:
/*  94 */         f = arguments[0].eval();
/*  95 */         f1 = arguments[1].eval();
/*  96 */         return f - f1 * (int)(f / f1);
/*     */       
/*     */       case NEG:
/*  99 */         return -arguments[0].eval();
/*     */       
/*     */       case PI:
/* 102 */         return 3.1415927F;
/*     */       
/*     */       case SIN:
/* 105 */         return MathHelper.sin(arguments[0].eval());
/*     */       
/*     */       case COS:
/* 108 */         return MathHelper.cos(arguments[0].eval());
/*     */       
/*     */       case TAN:
/* 111 */         return (float)Math.tan(arguments[0].eval());
/*     */       
/*     */       case ATAN:
/* 114 */         return (float)Math.atan(arguments[0].eval());
/*     */       
/*     */       case ATAN2:
/* 117 */         return (float)MathHelper.atan2(arguments[0].eval(), arguments[1].eval());
/*     */       
/*     */       case TORAD:
/* 120 */         return MathUtils.toRad(arguments[0].eval());
/*     */       
/*     */       case TODEG:
/* 123 */         return MathUtils.toDeg(arguments[0].eval());
/*     */       
/*     */       case MIN:
/* 126 */         return Math.min(arguments[0].eval(), arguments[1].eval());
/*     */       
/*     */       case MAX:
/* 129 */         return Math.max(arguments[0].eval(), arguments[1].eval());
/*     */       
/*     */       case CLAMP:
/* 132 */         return MathHelper.clamp(arguments[0].eval(), arguments[1].eval(), arguments[2].eval());
/*     */       
/*     */       case null:
/* 135 */         return MathHelper.abs(arguments[0].eval());
/*     */       
/*     */       case FLOOR:
/* 138 */         return MathHelper.floor(arguments[0].eval());
/*     */       
/*     */       case CEIL:
/* 141 */         return MathHelper.ceil(arguments[0].eval());
/*     */       
/*     */       case FRAC:
/* 144 */         return (float)MathHelper.frac(arguments[0].eval());
/*     */       
/*     */       case ROUND:
/* 147 */         return Math.round(arguments[0].eval());
/*     */       
/*     */       case SQRT:
/* 150 */         return MathHelper.sqrt(arguments[0].eval());
/*     */       
/*     */       case FMOD:
/* 153 */         f2 = arguments[0].eval();
/* 154 */         f3 = arguments[1].eval();
/* 155 */         return f2 - f3 * MathHelper.floor(f2 / f3);
/*     */       
/*     */       case TIME:
/* 158 */         minecraft = Minecraft.getMinecraft();
/* 159 */         worldClient = minecraft.world;
/*     */         
/* 161 */         if (worldClient == null)
/*     */         {
/* 163 */           return 0.0F;
/*     */         }
/*     */         
/* 166 */         return (float)(worldClient.getTotalWorldTime() % 24000L) + minecraft.getRenderPartialTicks();
/*     */     } 
/*     */     
/* 169 */     Config.warn("Unknown function type: " + this);
/* 170 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumFunctionType parse(String str) {
/* 177 */     for (int i = 0; i < VALUES.length; i++) {
/*     */       
/* 179 */       EnumFunctionType enumfunctiontype = VALUES[i];
/*     */       
/* 181 */       if (enumfunctiontype.getName().equals(str))
/*     */       {
/* 183 */         return enumfunctiontype;
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\EnumFunctionType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */