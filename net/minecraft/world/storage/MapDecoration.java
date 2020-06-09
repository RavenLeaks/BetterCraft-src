/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ public class MapDecoration
/*     */ {
/*     */   private final Type field_191181_a;
/*     */   private byte x;
/*     */   private byte y;
/*     */   private byte rotation;
/*     */   
/*     */   public MapDecoration(Type p_i47236_1_, byte p_i47236_2_, byte p_i47236_3_, byte p_i47236_4_) {
/*  14 */     this.field_191181_a = p_i47236_1_;
/*  15 */     this.x = p_i47236_2_;
/*  16 */     this.y = p_i47236_3_;
/*  17 */     this.rotation = p_i47236_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getType() {
/*  22 */     return this.field_191181_a.func_191163_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public Type func_191179_b() {
/*  27 */     return this.field_191181_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getX() {
/*  32 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getY() {
/*  37 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getRotation() {
/*  42 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191180_f() {
/*  47 */     return this.field_191181_a.func_191160_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  52 */     if (this == p_equals_1_)
/*     */     {
/*  54 */       return true;
/*     */     }
/*  56 */     if (!(p_equals_1_ instanceof MapDecoration))
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  62 */     MapDecoration mapdecoration = (MapDecoration)p_equals_1_;
/*     */     
/*  64 */     if (this.field_191181_a != mapdecoration.field_191181_a)
/*     */     {
/*  66 */       return false;
/*     */     }
/*  68 */     if (this.rotation != mapdecoration.rotation)
/*     */     {
/*  70 */       return false;
/*     */     }
/*  72 */     if (this.x != mapdecoration.x)
/*     */     {
/*  74 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  78 */     return (this.y == mapdecoration.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  85 */     int i = this.field_191181_a.func_191163_a();
/*  86 */     i = 31 * i + this.x;
/*  87 */     i = 31 * i + this.y;
/*  88 */     i = 31 * i + this.rotation;
/*  89 */     return i;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/*  94 */     PLAYER(false),
/*  95 */     FRAME(true),
/*  96 */     RED_MARKER(false),
/*  97 */     BLUE_MARKER(false),
/*  98 */     TARGET_X(true),
/*  99 */     TARGET_POINT(true),
/* 100 */     PLAYER_OFF_MAP(false),
/* 101 */     PLAYER_OFF_LIMITS(false),
/* 102 */     MANSION(true, 5393476),
/* 103 */     MONUMENT(true, 3830373);
/*     */ 
/*     */     
/*     */     private final byte field_191175_k;
/*     */ 
/*     */     
/*     */     private final boolean field_191176_l;
/*     */ 
/*     */     
/*     */     private final int field_191177_m;
/*     */ 
/*     */     
/*     */     Type(boolean p_i47344_3_, int p_i47344_4_) {
/* 116 */       this.field_191175_k = (byte)ordinal();
/* 117 */       this.field_191176_l = p_i47344_3_;
/* 118 */       this.field_191177_m = p_i47344_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte func_191163_a() {
/* 123 */       return this.field_191175_k;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_191160_b() {
/* 128 */       return this.field_191176_l;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_191162_c() {
/* 133 */       return (this.field_191177_m >= 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int func_191161_d() {
/* 138 */       return this.field_191177_m;
/*     */     }
/*     */ 
/*     */     
/*     */     public static Type func_191159_a(byte p_191159_0_) {
/* 143 */       return values()[MathHelper.clamp(p_191159_0_, 0, (values()).length - 1)];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\MapDecoration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */