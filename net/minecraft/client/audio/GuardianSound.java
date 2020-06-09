/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityGuardian;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.util.SoundCategory;
/*    */ 
/*    */ public class GuardianSound
/*    */   extends MovingSound
/*    */ {
/*    */   private final EntityGuardian guardian;
/*    */   
/*    */   public GuardianSound(EntityGuardian guardian) {
/* 13 */     super(SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.HOSTILE);
/* 14 */     this.guardian = guardian;
/* 15 */     this.attenuationType = ISound.AttenuationType.NONE;
/* 16 */     this.repeat = true;
/* 17 */     this.repeatDelay = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 25 */     if (!this.guardian.isDead && this.guardian.hasTargetedEntity()) {
/*    */       
/* 27 */       this.xPosF = (float)this.guardian.posX;
/* 28 */       this.yPosF = (float)this.guardian.posY;
/* 29 */       this.zPosF = (float)this.guardian.posZ;
/* 30 */       float f = this.guardian.getAttackAnimationScale(0.0F);
/* 31 */       this.volume = 0.0F + 1.0F * f * f;
/* 32 */       this.pitch = 0.7F + 0.5F * f;
/*    */     }
/*    */     else {
/*    */       
/* 36 */       this.donePlaying = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\audio\GuardianSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */