/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class MovementInputFromOptions
/*    */   extends MovementInput
/*    */ {
/*    */   private final GameSettings gameSettings;
/*    */   
/*    */   public MovementInputFromOptions(GameSettings gameSettingsIn) {
/* 11 */     this.gameSettings = gameSettingsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updatePlayerMoveState() {
/* 16 */     this.moveStrafe = 0.0F;
/* 17 */     this.field_192832_b = 0.0F;
/*    */     
/* 19 */     if (this.gameSettings.keyBindForward.isKeyDown()) {
/*    */       
/* 21 */       this.field_192832_b++;
/* 22 */       this.forwardKeyDown = true;
/*    */     }
/*    */     else {
/*    */       
/* 26 */       this.forwardKeyDown = false;
/*    */     } 
/*    */     
/* 29 */     if (this.gameSettings.keyBindBack.isKeyDown()) {
/*    */       
/* 31 */       this.field_192832_b--;
/* 32 */       this.backKeyDown = true;
/*    */     }
/*    */     else {
/*    */       
/* 36 */       this.backKeyDown = false;
/*    */     } 
/*    */     
/* 39 */     if (this.gameSettings.keyBindLeft.isKeyDown()) {
/*    */       
/* 41 */       this.moveStrafe++;
/* 42 */       this.leftKeyDown = true;
/*    */     }
/*    */     else {
/*    */       
/* 46 */       this.leftKeyDown = false;
/*    */     } 
/*    */     
/* 49 */     if (this.gameSettings.keyBindRight.isKeyDown()) {
/*    */       
/* 51 */       this.moveStrafe--;
/* 52 */       this.rightKeyDown = true;
/*    */     }
/*    */     else {
/*    */       
/* 56 */       this.rightKeyDown = false;
/*    */     } 
/*    */     
/* 59 */     this.jump = this.gameSettings.keyBindJump.isKeyDown();
/* 60 */     this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
/*    */     
/* 62 */     if (this.sneak) {
/*    */       
/* 64 */       this.moveStrafe = (float)(this.moveStrafe * 0.3D);
/* 65 */       this.field_192832_b = (float)(this.field_192832_b * 0.3D);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\MovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */