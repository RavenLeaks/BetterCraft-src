/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.util.math.Vec2f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovementInput
/*    */ {
/*    */   public float moveStrafe;
/*    */   public float field_192832_b;
/*    */   public boolean forwardKeyDown;
/*    */   public boolean backKeyDown;
/*    */   public boolean leftKeyDown;
/*    */   public boolean rightKeyDown;
/*    */   public boolean jump;
/*    */   public boolean sneak;
/*    */   
/*    */   public void updatePlayerMoveState() {}
/*    */   
/*    */   public Vec2f getMoveVector() {
/* 25 */     return new Vec2f(this.moveStrafe, this.field_192832_b);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\MovementInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */