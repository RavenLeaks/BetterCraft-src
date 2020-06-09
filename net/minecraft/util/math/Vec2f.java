/*    */ package net.minecraft.util.math;
/*    */ 
/*    */ public class Vec2f
/*    */ {
/*  5 */   public static final Vec2f ZERO = new Vec2f(0.0F, 0.0F);
/*  6 */   public static final Vec2f ONE = new Vec2f(1.0F, 1.0F);
/*  7 */   public static final Vec2f UNIT_X = new Vec2f(1.0F, 0.0F);
/*  8 */   public static final Vec2f NEGATIVE_UNIT_X = new Vec2f(-1.0F, 0.0F);
/*  9 */   public static final Vec2f UNIT_Y = new Vec2f(0.0F, 1.0F);
/* 10 */   public static final Vec2f NEGATIVE_UNIT_Y = new Vec2f(0.0F, -1.0F);
/* 11 */   public static final Vec2f MAX = new Vec2f(Float.MAX_VALUE, Float.MAX_VALUE);
/* 12 */   public static final Vec2f MIN = new Vec2f(Float.MIN_VALUE, Float.MIN_VALUE);
/*    */   
/*    */   public final float x;
/*    */   public final float y;
/*    */   
/*    */   public Vec2f(float xIn, float yIn) {
/* 18 */     this.x = xIn;
/* 19 */     this.y = yIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\Vec2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */